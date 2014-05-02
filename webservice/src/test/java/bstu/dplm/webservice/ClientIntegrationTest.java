package bstu.dplm.webservice;

import bstu.dplm.model.game.*;
import edu.schema.bstu.dplm.datatypes.v1.*;
import edu.schema.bstu.dplm.datatypes.v1.Answer;
import edu.schema.bstu.dplm.datatypes.v1.Function;
import edu.schema.bstu.dplm.datatypes.v1.Location;
import edu.schema.bstu.dplm.datatypes.v1.MapObject;
import edu.schema.bstu.dplm.datatypes.v1.Question;
import edu.schema.bstu.dplm.datatypes.v1.Quiz;
import edu.schema.bstu.dplm.servicetypes.v1.*;
import edu.wsdl.bstu.dplm.serviceinterface.v1.ServiceInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/client.context.xml")
public class ClientIntegrationTest {

    @Resource
    ServiceInterface serviceInterface;

    @Test
    public void test()
    {
        Location location = new Location();
        location.setName("Test location");
        location.setComment("Comment");
        location.setPicture("Picture");
        location.setCreated(Calendar.getInstance());
        location.setIdCreator(1l);

        location = update(location);

        location.setComment("Changed comment");

        location = update(location);

        LocationProperties property = new LocationProperties();
        property.setX("X");
        property.setY("Y");
        MapObject obj = new MapObject();
        obj.setComment("Test object");
        obj.setName("Giga object");
        obj.setView("Object rendering");
        property.setMapObject(obj);
        location.getProperties().add(property);

        location = update(location);

        location.getProperties().get(0).getMapObject().setComment("Changed comment");

        location = update(location);

        Function function = new Function();
        function.setComment("Some comment");
        function.setFunction("Some usefull function1");
        location.getProperties().get(0).getMapObject().setFunction(function);

        location = update(location);

        location.getProperties().get(0).getMapObject().getFunction().setComment("bu-bu-buuu");

        location = update(location);

        Quiz quiz = new Quiz();
        quiz.setName("Mega quizz");
        location.getProperties().get(0).getMapObject().getQuiz().add(quiz);

        location = update(location);

        location.getProperties().get(0).getMapObject().getQuiz().get(0).setName("Chnage name");

        location = update(location);
        Question question = new Question();
        question.setText("Who am i?");
        location.getProperties().get(0).getMapObject().getQuiz().get(0).getQuestions().add(question);

        location = update(location);

        question = location.getProperties().get(0).getMapObject().getQuiz().get(0).getQuestions().get(0);
        question.setText("Am I right?");

        Answer answer = new Answer();
        answer.setText("Yes");
        answer.setIsAnswer(false);
        question.getAnswers().add(answer);

        Answer answer2 = new Answer();
        answer2.setText("No");
        answer2.setIsAnswer(true);
        question.getAnswers().add(answer2);

        location = update(location);

        System.out.println("\n\n\t\tDone");

    }

    public Location update(Location location)
    {
        UpdateLocationRequestType updateLocationRequestType = new UpdateLocationRequestType();
        updateLocationRequestType.setLocation(location);

        UpdateLocationResponseType response = serviceInterface.updateLocation(updateLocationRequestType);

        GetLocationRequestType getLocationRequestType = new GetLocationRequestType();
        getLocationRequestType.setId(response.getLocation().getId());

        GetLocationResponseType res = serviceInterface.getLocation(getLocationRequestType);

        return res.getLocation();
    }

    @Test
    public void authorizeUserTest(){
        User user;
        AuthorizeRequestType request = new AuthorizeRequestType();
        AuthorizeParameters parameters = new AuthorizeParameters();
        parameters.setLogin("raziel");
        parameters.setPassword("pass1");
        request.setAuthorizeParameters(parameters);

        AuthorizeResponseType resp = serviceInterface.authorize(request);
        user = resp.getUser();
        System.out.println(user.toString());
        System.out.println("\n\n\t\tDone");
    }

    @Test
    public void searchUserTest(){
        List<User> users;
        RetrieveUserByCriteriaRequestType request = new RetrieveUserByCriteriaRequestType();
        SearchUserParameters parameters = new SearchUserParameters();
        parameters.setLogin("login");
        request.setSearchUserCriteria(parameters);

        RetrieveUserByCriteriaResponseType resp = serviceInterface.searchUserByCriteria(request);
        users = resp.getUser();
        for(User user : users)
        System.out.println(user.getId());
        System.out.println("\n\n\t\tDone");
    }
}
