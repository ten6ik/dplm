package bstu.dplm.dao;

import bstu.dplm.model.game.Quiz;
import bstu.dplm.model.user.UserResult;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserResultsDao extends GenericDao<UserResult> {

    public UserResultsDao() {
        super(UserResult.class);
    }

    public List<UserResult> getUserResults(Long id){

        return this.sessionFactory.getCurrentSession().createCriteria(UserResult.class).add(Restrictions.eq("userId", id)).list();
    }

    public List<UserResult> getUserResultsByQuiz(long id){

        Query query = this.sessionFactory.getCurrentSession().createQuery("from UserResult where quiz.id = :code ");
        query.setParameter("code", id);
        List<UserResult> list = query.list();

        return list;//this.sessionFactory.getCurrentSession().createCriteria(UserResult.class).createAlias("quiz", "lq").add(Restrictions.eq("lq.id", id)).list();
    }
}
