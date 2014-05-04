package bstu.dplm.webservice;

import edu.schema.bstu.dplm.datatypes.v1.UserResultsType;
import edu.schema.bstu.dplm.servicetypes.v1.*;
import edu.schema.bstu.dplm.datatypes.v1.*;
import edu.wsdl.bstu.dplm.serviceinterface.v1.ServiceInterface;
import junit.framework.Assert;
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
        LocationType location = new LocationType();
        location.setName("Test location");
        location.setComment("Comment");
        location.setPicture("Picture");
        location.setCreated(Calendar.getInstance());
        location.setIdCreator(1l);

        location = update(location);

        location.setComment("Changed comment");

        location = update(location);

        LocationPropertiesType property = new LocationPropertiesType();
        property.setX("X");
        property.setY("Y");
        MapObjectType obj = new MapObjectType();
        obj.setComment("Test object");
        obj.setName("Giga object");
        obj.setView("Object rendering");
        property.setMapObject(obj);
        location.getProperties().add(property);

        location = update(location);

        location.getProperties().get(0).getMapObject().setComment("Changed comment");

        location = update(location);

        FunctionType function = new FunctionType();
        function.setComment("Some comment");
        function.setFunction("Some usefull function1");
        location.getProperties().get(0).getMapObject().setFunction(function);

        location = update(location);

        location.getProperties().get(0).getMapObject().getFunction().setComment("bu-bu-buuu");

        location = update(location);

        QuizType quiz = new QuizType();
        quiz.setName("Mega quizz");
        location.getProperties().get(0).getMapObject().getQuiz().add(quiz);

        location = update(location);

        location.getProperties().get(0).getMapObject().getQuiz().get(0).setName("Chnage name");

        location = update(location);
        QuestionType question = new QuestionType();
        question.setText("Who am i?");
        location.getProperties().get(0).getMapObject().getQuiz().get(0).getQuestions().add(question);

        location = update(location);

        question = location.getProperties().get(0).getMapObject().getQuiz().get(0).getQuestions().get(0);
        question.setText("Am I right?");

        AnswerType answer = new AnswerType();
        answer.setText("Yes");
        answer.setIsAnswer(false);
        question.getAnswers().add(answer);

        AnswerType answer2 = new AnswerType();
        answer2.setText("No");
        answer2.setIsAnswer(true);
        question.getAnswers().add(answer2);

        location = update(location);

        System.out.println("\n\n\t\tDone");

    }

    public LocationType update(LocationType location)
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
        UserType user;
        AuthorizeRequestType request = new AuthorizeRequestType();
        AuthorizeParametersType parameters = new AuthorizeParametersType();
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
        List<UserType> users;
        RetrieveUserByCriteriaRequestType request = new RetrieveUserByCriteriaRequestType();
        SearchUserParametersType parameters = new SearchUserParametersType();
        parameters.setLogin("login");
        request.setSearchUserCriteria(parameters);

        RetrieveUserByCriteriaResponseType resp = serviceInterface.searchUserByCriteria(request);
        users = resp.getUser();
        for(UserType user : users)
        System.out.println(user.getId());
        System.out.println("\n\n\t\tDone");
    }

    @Test
    public void saveUserProgress()
    {
        RetrieveUserByCriteriaRequestType request = new RetrieveUserByCriteriaRequestType();
        SearchUserParametersType parameters = new SearchUserParametersType();
        parameters.setLogin("login");
        request.setSearchUserCriteria(parameters);

        RetrieveUserByCriteriaResponseType response = serviceInterface.searchUserByCriteria(request);
        UserType user = response.getUser().get(0);
        assertThat("User expected", user, is(notNullValue()));

        RetrieveLocationsRequestType retrieveLocationsRequest = new RetrieveLocationsRequestType();
        RetrieveLocationsResponseType responseL = serviceInterface.getLocations(retrieveLocationsRequest);

        LocationType location = responseL.getLocation().get(105);
        assertThat("Location expected", location, is(notNullValue()));


        UserResultsType userResult = new UserResultsType();
        userResult.setSessionId("Some session " + new Date());
        userResult.setAnswerText("No");
        userResult.setLocation(location);
        userResult.setQuiz(location.getProperties().get(0).getMapObject().getQuiz().get(0));
        userResult.setQuestion(userResult.getQuiz().getQuestions().get(0));
        userResult.setUserId(user.getId());

        int before = getUserResults(user.getId()).size();

        UpdateUserResultsRequestType resultsRequestType = new UpdateUserResultsRequestType();
        resultsRequestType.setUserResult(userResult);

        serviceInterface.updateUserResults(resultsRequestType);

        int after = getUserResults(user.getId()).size();

        assertThat("not added", after-before, is(1));


       // user = updateUser(user);
    }


    List<UserResultsType> getUserResults(long userId){
        RetrieveUserResultsRequestType retrieveUserResultsRequestType = new RetrieveUserResultsRequestType();
        retrieveUserResultsRequestType.setUserId(userId);

        RetrieveUserResultsResponseType responseUR = serviceInterface.retrieveUserResults(retrieveUserResultsRequestType);
        return responseUR.getUserResult();
    }

    UserType updateUser(UserType user)
    {
        UpdateUserRequestType request = new UpdateUserRequestType();
        request.setUser(user);
        UpdateUserResponseType response = serviceInterface.updateUser(request);

        RetrieveUserByCriteriaRequestType request2 = new RetrieveUserByCriteriaRequestType();
        SearchUserParametersType parameters = new SearchUserParametersType();
        parameters.setLogin(user.getLogin());
        request2.setSearchUserCriteria(parameters);

        return serviceInterface.searchUserByCriteria(request2).getUser().get(0);
    }
}
