package bstu.dplm.dao;

import bstu.dplm.model.game.Quiz;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizDao extends GenericDao<Quiz>
{
    protected QuizDao()
    {
        super(Quiz.class);
    }

    public List<Quiz> getQuizByName(String name)
    {
        return this.sessionFactory.getCurrentSession().createCriteria(Quiz.class).add(Restrictions.eq("name", name)).list();
    }
}