package bstu.dplm.dao;

import bstu.dplm.model.user.User;
import bstu.dplm.model.user.UserPriviliges;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository
public class UserDao extends GenericDao<User> {
    @Resource
    SessionFactory sessionFactory;

    public UserDao() {
        super(User.class);
    }

    public User authorize(String login, String password) {
        return (User) sessionFactory.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("login", login)).add(Restrictions.eq("password", password)).uniqueResult();
    }

    public List<User> searchUsers(String login, Date startPeriodOrRegisterTime,Date endPeriodOrLastSessionTime, UserPriviliges priv){
       Criteria searchCriteria = sessionFactory.getCurrentSession().createCriteria(User.class);
                if(login != null) {
                    searchCriteria.add(Restrictions.like("login", "%" + login + "%"));
                }

        if(startPeriodOrRegisterTime != null && endPeriodOrLastSessionTime != null) {
            searchCriteria.add(Restrictions.between("lastSession", startPeriodOrRegisterTime, endPeriodOrLastSessionTime));
        }
        if(startPeriodOrRegisterTime != null && endPeriodOrLastSessionTime == null){
            searchCriteria.add(Restrictions.eq("registration", startPeriodOrRegisterTime));
        }
        if(startPeriodOrRegisterTime == null && endPeriodOrLastSessionTime != null){
            searchCriteria.add(Restrictions.eq("lastSession", endPeriodOrLastSessionTime));
        }
        if(priv != null) {
            searchCriteria.add(Restrictions.eq("priviliges", priv));
        }

       return searchCriteria.list();
    }

}
