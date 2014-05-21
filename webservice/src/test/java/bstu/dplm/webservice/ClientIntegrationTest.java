package bstu.dplm.webservice;

import bstu.dplm.model.user.UserResult;
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
    public void test() {
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

    public LocationType update(LocationType location) {
        UpdateLocationRequestType updateLocationRequestType = new UpdateLocationRequestType();
        updateLocationRequestType.setLocation(location);

        UpdateLocationResponseType response = serviceInterface.updateLocation(updateLocationRequestType);

        GetLocationRequestType getLocationRequestType = new GetLocationRequestType();
        getLocationRequestType.setId(response.getLocation().getId());

        GetLocationResponseType res = serviceInterface.getLocation(getLocationRequestType);

        return res.getLocation();
    }

    @Test
    public void authorizeUserTest() {
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
    public void searchUserTest() {
        List<UserType> users;
        RetrieveUserByCriteriaRequestType request = new RetrieveUserByCriteriaRequestType();
        SearchUserParametersType parameters = new SearchUserParametersType();
        parameters.setLogin("login");
        request.setSearchUserCriteria(parameters);

        RetrieveUserByCriteriaResponseType resp = serviceInterface.searchUserByCriteria(request);
        users = resp.getUser();
        for (UserType user : users)
            System.out.println(user.getId());
        System.out.println("\n\n\t\tDone");
    }

    @Test
    public void saveUserProgress() {
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

        assertThat("not added", after - before, is(1));
    }


    List<UserResultsType> getUserResults(long userId) {
        RetrieveUserResultsRequestType retrieveUserResultsRequestType = new RetrieveUserResultsRequestType();
        retrieveUserResultsRequestType.setUserId(userId);

        RetrieveUserResultsResponseType responseUR = serviceInterface.retrieveUserResults(retrieveUserResultsRequestType);
        return responseUR.getUserResult();
    }

    UserType updateUser(UserType user) {
        UpdateUserRequestType request = new UpdateUserRequestType();
        request.setUser(user);
        UpdateUserResponseType response = serviceInterface.updateUser(request);

        RetrieveUserByCriteriaRequestType request2 = new RetrieveUserByCriteriaRequestType();
        SearchUserParametersType parameters = new SearchUserParametersType();
        parameters.setLogin(user.getLogin());
        request2.setSearchUserCriteria(parameters);

        return serviceInterface.searchUserByCriteria(request2).getUser().get(0);
    }

    @Test
    public void completeQuiz() {
        RetrieveUserByCriteriaRequestType request = new RetrieveUserByCriteriaRequestType();
        SearchUserParametersType parameters = new SearchUserParametersType();
        parameters.setLogin("raziel");
        request.setSearchUserCriteria(parameters);

        RetrieveUserByCriteriaResponseType response = serviceInterface.searchUserByCriteria(request);
        UserType user = response.getUser().get(0);
        assertThat("User expected", user, is(notNullValue()));

        //  RetrieveLocationsRequestType retrieveLocationsRequest = new RetrieveLocationsRequestType();
        //  RetrieveLocationsResponseType responseL = serviceInterface.getLocations(retrieveLocationsRequest);

        // LocationType location = responseL.getLocation().get(105);
        //  assertThat("Location expected", location, is(notNullValue()));

        LocationType location = new LocationType();
        location.setName("Complete location");
        location.setComment("Comment");
        location.setPicture("Picture");
        location.setCreated(Calendar.getInstance());
        location.setIdCreator(user.getId());

        LocationPropertiesType property = new LocationPropertiesType();
        property.setX("123");
        property.setY("321");
        MapObjectType obj = new MapObjectType();
        obj.setComment("Some dragon");
        obj.setName("Gephest");
        obj.setView("shadow picture");
        property.setMapObject(obj);
        location.getProperties().add(property);

        FunctionType function = new FunctionType();
        function.setComment("Some function");
        function.setFunction("Sleeping");
        location.getProperties().get(0).getMapObject().setFunction(function);

        QuizType quiz = new QuizType();
        quiz.setName("Wake the dragon");
        location.getProperties().get(0).getMapObject().getQuiz().add(quiz);

        QuestionType question = new QuestionType();
        question.setText("How many fingers has the dragon?");
        location.getProperties().get(0).getMapObject().getQuiz().get(0).getQuestions().add(question);

        AnswerType answer = new AnswerType();
        answer.setText("two");
        answer.setIsAnswer(false);
        question.getAnswers().add(answer);

        AnswerType answer2 = new AnswerType();
        answer2.setText("three");
        answer2.setIsAnswer(false);
        question.getAnswers().add(answer2);

        AnswerType answer3 = new AnswerType();
        answer3.setText("four");
        answer3.setIsAnswer(true);
        question.getAnswers().add(answer3);

        QuestionType question2 = new QuestionType();
        question2.setText("Where is diamond sword?");
        location.getProperties().get(0).getMapObject().getQuiz().get(0).getQuestions().add(question2);

        AnswerType answer12 = new AnswerType();
        answer12.setText("here");
        answer12.setIsAnswer(true);
        question2.getAnswers().add(answer12);

        AnswerType answer22 = new AnswerType();
        answer22.setText("there");
        answer22.setIsAnswer(false);
        question2.getAnswers().add(answer22);

        AnswerType answer32 = new AnswerType();
        answer32.setText("nowhere");
        answer32.setIsAnswer(false);
        question2.getAnswers().add(answer32);

        QuestionType question3 = new QuestionType();
        question3.setText("Do you like dragons?");
        location.getProperties().get(0).getMapObject().getQuiz().get(0).getQuestions().add(question3);

        AnswerType answer13 = new AnswerType();
        answer13.setText("yes");
        answer13.setIsAnswer(true);
        question3.getAnswers().add(answer13);

        AnswerType answer23 = new AnswerType();
        answer23.setText("no");
        answer23.setIsAnswer(false);
        question3.getAnswers().add(answer23);

        location = update(location);

        RetrieveUserByCriteriaRequestType requestPupil = new RetrieveUserByCriteriaRequestType();
        SearchUserParametersType parametersForRequest = new SearchUserParametersType();
        parametersForRequest.setLogin("login");
        requestPupil.setSearchUserCriteria(parametersForRequest);

        RetrieveUserByCriteriaResponseType responsePupil = serviceInterface.searchUserByCriteria(request);
        UserType pupil = responsePupil.getUser().get(0);
        assertThat("User expected", pupil, is(notNullValue()));

        UserResultsType userResult1 = new UserResultsType();
        userResult1.setSessionId("Pupil session " + new Date());

        userResult1.setLocation(location);
        userResult1.setQuiz(location.getProperties().get(0).getMapObject().getQuiz().get(0));


        userResult1.setQuestion(userResult1.getQuiz().getQuestions().get(0));
        userResult1.setAnswerText("three");
        userResult1.setUserId(user.getId());

        UpdateUserResultsRequestType resultsRequestType = new UpdateUserResultsRequestType();
        resultsRequestType.setUserResult(userResult1);

        serviceInterface.updateUserResults(resultsRequestType);

        UserResultsType userResult2 = new UserResultsType();
        userResult2.setSessionId("Pupil session " + new Date());

        userResult2.setLocation(location);
        userResult2.setQuiz(location.getProperties().get(0).getMapObject().getQuiz().get(0));


        userResult2.setQuestion(userResult2.getQuiz().getQuestions().get(1));
        userResult2.setAnswerText("here");
        userResult2.setUserId(user.getId());

        UpdateUserResultsRequestType resultsRequestType2 = new UpdateUserResultsRequestType();
        resultsRequestType2.setUserResult(userResult2);

        serviceInterface.updateUserResults(resultsRequestType2);

        UserResultsType userResult3 = new UserResultsType();
        userResult3.setSessionId("Pupil session " + new Date());

        userResult3.setLocation(location);
        userResult3.setQuiz(location.getProperties().get(0).getMapObject().getQuiz().get(0));


        userResult3.setQuestion(userResult3.getQuiz().getQuestions().get(2));
        AnswerType thirdAnswer = new AnswerType();
        thirdAnswer.setId(userResult3.getQuiz().getQuestions().get(2).getAnswers().get(0).getId());
        thirdAnswer.setText(userResult3.getQuiz().getQuestions().get(2).getAnswers().get(0).getText());
        userResult3.setAnswer(thirdAnswer);
        userResult3.setUserId(user.getId());

        UpdateUserResultsRequestType resultsRequestType3 = new UpdateUserResultsRequestType();
        resultsRequestType3.setUserResult(userResult3);

        serviceInterface.updateUserResults(resultsRequestType3);

        CompleteQuizRequestType completeQuizRequestType = new CompleteQuizRequestType();
        completeQuizRequestType.setQuiz(userResult1.getQuiz());
        completeQuizRequestType.setPupilId(userResult1.getUserId());

        CompleteQuizResponseType completeQuizResponseType = serviceInterface.completeQuiz(completeQuizRequestType);//new CompleteQuizResponseType();
        System.out.println(completeQuizResponseType.getMark());

        for (UserResultsType result : completeQuizResponseType.getUserResult()) {
            System.out.println(result.getAnswerText() + " is " + result.getIsRight());
        }

        // assertThat("not added", after-before, is(1));
    }

    @Test
    public void testSometing(){

        int first = 3;
        int second = 8;
        double firstD = 3;
        double secondD = 8;

        float result = (first / second);
        float result2 = (first % second);
        float result3 = (float)(firstD / secondD);
        float result4 = (float)(firstD % secondD);
        System.out.println(result + "    " +result2 + "     " + result3 + "    " + result4 + "   ");
    }

}
