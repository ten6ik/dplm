package bstu.dplm.model.bstu.dplm.dao;

import bstu.dplm.dao.MapObjectDao;
import bstu.dplm.model.game.Answer;
import bstu.dplm.model.game.MapObject;
import bstu.dplm.model.game.Question;
import bstu.dplm.model.game.Quiz;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test.context.xml")
@Transactional
public class MapDaoTest {

    @Resource
    SessionFactory sessionFactory;

    @Resource
    MapObjectDao dao;


    @Test
    @Rollback(false)
    public void save(){

        MapObject mapObject = new MapObject();
        mapObject.setName("test Name");
        mapObject.setView("test view");

        Quiz quiz = new Quiz();
        quiz.setName("test quiz name");
        Question question = new Question();
        question.setText("Am I boss?");
        quiz.getQuestions().add(question);

        Answer answer1 = new Answer();
        answer1.setText("No");
        question.getAnswers().add(answer1);

        Answer answer2 = new Answer();
        answer2.setText("Maybe");
        answer2.setIsAnswer(true);
        question.getAnswers().add(answer2);

        Set<Question> questions = new HashSet<Question>();
        questions.add(question);

        quiz.setQuestions(questions);

        mapObject.getQuiz().add(quiz);
        mapObject = dao.saveOrUpdate(mapObject);
    }

    @Test
    @Rollback(false)
    public void update(){

        MapObject mapObject = new MapObject();
        mapObject.setId(159);
        mapObject.setName("changed Name");
        mapObject.setView("test view");

        Quiz quiz = new Quiz();
        quiz.setName("test quiz name");
        Question question = new Question();
        question.setText("Am I boss?");
        quiz.getQuestions().add(question);

        Answer answer1 = new Answer();
        answer1.setText("No");
        question.getAnswers().add(answer1);

        Answer answer2 = new Answer();
        answer2.setText("Maybe");
        answer2.setIsAnswer(true);
        question.getAnswers().add(answer2);

        Set<Question> questions = new HashSet<Question>();
        questions.add(question);

        quiz.setQuestions(questions);

        mapObject.getQuiz().add(quiz);
        dao.saveOrUpdate(mapObject);
    }
}
