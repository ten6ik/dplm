package bstu.dplm.webservice;

import edu.schema.bstu.dplm.datatypes.v1.Answer;
import edu.schema.bstu.dplm.datatypes.v1.AuthorizeParameters;
import edu.schema.bstu.dplm.datatypes.v1.Function;
import edu.schema.bstu.dplm.datatypes.v1.Location;
import edu.schema.bstu.dplm.datatypes.v1.LocationProperties;
import edu.schema.bstu.dplm.datatypes.v1.MapObject;
import edu.schema.bstu.dplm.datatypes.v1.Question;
import edu.schema.bstu.dplm.datatypes.v1.Quiz;
import edu.schema.bstu.dplm.datatypes.v1.SearchUserParameters;
import edu.schema.bstu.dplm.datatypes.v1.User;
import edu.schema.bstu.dplm.datatypes.v1.UserResultsType;
import edu.schema.bstu.dplm.servicetypes.v1.AuthorizeRequestType;
import edu.schema.bstu.dplm.servicetypes.v1.AuthorizeResponseType;
import edu.schema.bstu.dplm.servicetypes.v1.GetLocationRequestType;
import edu.schema.bstu.dplm.servicetypes.v1.GetLocationResponseType;
import edu.schema.bstu.dplm.servicetypes.v1.RetrieveLocationsRequestType;
import edu.schema.bstu.dplm.servicetypes.v1.RetrieveLocationsResponseType;
import edu.schema.bstu.dplm.servicetypes.v1.RetrieveUserByCriteriaRequestType;
import edu.schema.bstu.dplm.servicetypes.v1.RetrieveUserByCriteriaResponseType;
import edu.schema.bstu.dplm.servicetypes.v1.UpdateLocationRequestType;
import edu.schema.bstu.dplm.servicetypes.v1.UpdateLocationResponseType;
import edu.schema.bstu.dplm.servicetypes.v1.UpdateUserRequestType;
import edu.schema.bstu.dplm.servicetypes.v1.UpdateUserResponseType;
import edu.wsdl.bstu.dplm.serviceinterface.v1.ServiceInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

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

    @Test
    public void saveUserProgress()
    {
        RetrieveUserByCriteriaRequestType request = new RetrieveUserByCriteriaRequestType();
        SearchUserParameters parameters = new SearchUserParameters();
        parameters.setLogin("login");
        request.setSearchUserCriteria(parameters);

        RetrieveUserByCriteriaResponseType response = serviceInterface.searchUserByCriteria(request);
        User user = response.getUser().get(0);
        assertThat("User expected", user, is(notNullValue()));

        RetrieveLocationsRequestType retrieveLocationsRequest = new RetrieveLocationsRequestType();
        RetrieveLocationsResponseType responseL = serviceInterface.getLocations(retrieveLocationsRequest);

        Location location = responseL.getLocation().get(105);
        assertThat("Location expected", location, is(notNullValue()));


        UserResultsType userResult = new UserResultsType();
        userResult.setSessionId("Some session " + new Date());
        userResult.setLocation(location);
        userResult.setQuiz(location.getProperties().get(0).getMapObject().getQuiz().get(0));
        userResult.setQuestion(userResult.getQuiz().getQuestions().get(0));
        user.getResults().add(userResult);

        user = updateUser(user);
    }

    User updateUser(User user)
    {
        UpdateUserRequestType request = new UpdateUserRequestType();
        request.setUser(user);
        UpdateUserResponseType response = serviceInterface.updateUser(request);

        RetrieveUserByCriteriaRequestType request2 = new RetrieveUserByCriteriaRequestType();
        SearchUserParameters parameters = new SearchUserParameters();
        parameters.setLogin(user.getLogin());
        request2.setSearchUserCriteria(parameters);

        return serviceInterface.searchUserByCriteria(request2).getUser().get(0);
    }
}
