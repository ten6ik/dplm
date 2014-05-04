package bstu.dplm.dao;

import bstu.dplm.model.game.Mark;
import bstu.dplm.model.game.Quiz;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MarkDao  extends GenericDao<Mark>
{
    protected MarkDao()
    {
        super(Mark.class);
    }

    public List<Mark> getMarks(Quiz quiz){

        return this.sessionFactory.getCurrentSession().createCriteria(Mark.class).add(Restrictions.eq("quiztId", quiz.getId())).list();
    }

    public List<Mark> getMarks(String pupilLogin){

        return this.sessionFactory.getCurrentSession().createCriteria(Mark.class).add(Restrictions.eq("pupilLogin", pupilLogin)).list();
    }

    public Mark getMark(String pupilLogin, Quiz quiz){

        return (Mark) this.sessionFactory.getCurrentSession().createCriteria(Mark.class).add(Restrictions.eq("pupilLogin", pupilLogin)).add(Restrictions.eq("quiztId", quiz.getId())).uniqueResult();
    }
}