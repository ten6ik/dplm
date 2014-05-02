package bstu.dplm.dao;

import bstu.dplm.model.user.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class UserDao extends GenericDao<User>
{
    @Resource
    SessionFactory sessionFactory;

    public UserDao()
    {
        super(User.class);
    }

}
