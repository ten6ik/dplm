package bstu.dplm.model.bstu.dplm.dao;

import bstu.dplm.dao.LocationDao;
import bstu.dplm.model.game.*;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test.context.xml")
@Transactional(rollbackFor = Exception.class)
public class LocationDaoTest {

    @Resource
    LocationDao locationDao;

    @Resource
    SessionFactory sessionFactory;

//    @Test
 //   @Rollback(false)
    public void saveLocation()
    {
        Location location = new Location();

        location.setName("Sample Location");
        location.setCreated(new Date());
        location.setIdCreator(1l);

        LocationProperty property = new LocationProperty();
        property.setX("50");
        property.setY("60");
        location.getProperties().add(property);
        property.setLocation(location);

        MapObject mapObject = new MapObject();
        mapObject.setName("Test Object");
        mapObject.setView("View1");
        property.setMapObject(mapObject);

        Function function = new Function();
        function.setFunction("TestFunction");
        mapObject.setFunction(function);

        Quiz quiz = new Quiz();

        quiz.setName("Mega quiz");
        mapObject.getQuiz().add(quiz);

      //  System.out.println(quiz);

        Question question = new Question();
        question.setText("Who am i?");
        quiz.getQuestions().add(question);

        Answer answer1 = new Answer();
        answer1.setText("Idiot");
        question.getAnswers().add(answer1);

        Answer answer2 = new Answer();
        answer2.setText("Dragon");
        answer2.setIsAnswer(true);
        question.getAnswers().add(answer2);
        System.out.println(location);

        locationDao.saveOrUpdate(location);

        //sessionFactory.getCurrentSession().saveOrUpdate(question);

        System.out.println(location.getId());
    }
    @Test
    @Rollback(false)
    public void getLocation()
    {
        System.out.println(locationDao.getById(89L));

        //System.out.println(sessionFactory.getCurrentSession().load(Question.class, 41l));
    }
}
