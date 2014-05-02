package bstu.dplm.model.bstu.dplm.dao;

import bstu.dplm.dao.UserDao;
import bstu.dplm.model.user.BodyLook;
import bstu.dplm.model.user.User;
import bstu.dplm.model.user.UserPriviliges;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test.context.xml")
@Transactional
public class UserDaoTest
{
    @Resource
    SessionFactory sessionFactory;

    @Resource
    UserDao dao;

    @Test
    public void save()
    {
        User user = new User();
        user.setLogin("login4");
        user.setPassword("pass");
        user.setLastSession(new Date());
        user.setRegistration(new Date());

        user.setBodyLook(new BodyLook());
        user.getBodyLook().setComment("coat");
        user.getBodyLook().setLooklike("temp");
        user.getBodyLook().setId(1l);

        UserPriviliges userPriviliges = new UserPriviliges();
        userPriviliges.setName("Read");
        userPriviliges.setComment("Privileage to read");
        //user.getPriviliges().add(userPriviliges);

        user = dao.saveOrUpdate(user);

        System.out.println(user.getId());
    }
}
