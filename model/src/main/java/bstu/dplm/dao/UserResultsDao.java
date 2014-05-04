package bstu.dplm.dao;

import bstu.dplm.model.game.Quiz;
import bstu.dplm.model.user.UserResult;
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

    public List<UserResult> getUserResultsByQuiz(Quiz quiz){

        return this.sessionFactory.getCurrentSession().createCriteria(UserResult.class).add(Restrictions.eq("quiz", quiz.getId())).list();
    }
}
