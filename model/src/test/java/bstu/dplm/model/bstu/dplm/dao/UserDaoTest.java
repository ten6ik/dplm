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
import java.util.*;

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

    @Test
    public void authorize(){
        User user = dao.authorize("raziel", "pass1");
        System.out.println(user);
    }

    @Test
    public void searchByLogin(){
        String login = "login";

        List<User> users = dao.searchUsers(login,null,null,null);

        for (User user : users) {
            System.out.println(user.getId());
        }
    }

    @Test
    public void searchByPriv(){
        UserPriviliges priv = new UserPriviliges();
        priv.setId(2);
        priv.setComment("low privs");
        priv.setName("pupil");
        User user = new User();
        user.getPriviliges().add(priv);

        List<User> users = dao.searchUsers(null,null,null,priv);

        for (User us : users) {
            System.out.println(us.getId());
        }
    }

    @Test
    public void searchByTime(){
        Calendar start = new GregorianCalendar(2014, 3, 26);
        Calendar end = new GregorianCalendar(2014, 3, 28);

        List<User> users = dao.searchUsers(null,start.getTime(),end.getTime(),null);

        for (User user : users) {
            System.out.println("Between 26 april and 28 april: " +user.getId() + " with time " +user.getLastSession());
        }
        List<User> usersReg = dao.searchUsers(null,start.getTime(),null,null);
        for (User user : usersReg) {
            System.out.println("Reg time is 26 april: " +user.getId() + " with time " +user.getRegistration());
        }

        List<User> usersLastSession = dao.searchUsers(null,null,new GregorianCalendar(2014, 3, 27).getTime(),null);
        for (User user : usersLastSession) {
            System.out.println("Last Session time is 27 april: " +user.getId() + " with time " +user.getLastSession());
        }
    }
}
