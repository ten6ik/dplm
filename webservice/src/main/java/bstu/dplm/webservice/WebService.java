package bstu.dplm.webservice;

import bstu.dplm.dao.*;
import bstu.dplm.model.game.*;
import bstu.dplm.model.user.*;
import edu.schema.bstu.dplm.servicetypes.v1.AuthorizeRequestType;
import edu.schema.bstu.dplm.servicetypes.v1.AuthorizeResponseType;
import edu.schema.bstu.dplm.servicetypes.v1.DeleteUserRequestType;
import edu.schema.bstu.dplm.servicetypes.v1.DeleteUserResponseType;
import edu.schema.bstu.dplm.servicetypes.v1.GetLocationRequestType;
import edu.schema.bstu.dplm.servicetypes.v1.GetLocationResponseType;
import edu.schema.bstu.dplm.servicetypes.v1.PingRequestType;
import edu.schema.bstu.dplm.servicetypes.v1.PingResponseType;
import edu.schema.bstu.dplm.servicetypes.v1.RetrieveLocationsRequestType;
import edu.schema.bstu.dplm.servicetypes.v1.RetrieveLocationsResponseType;
import edu.schema.bstu.dplm.servicetypes.v1.RetrieveLooksRequestType;
import edu.schema.bstu.dplm.servicetypes.v1.RetrieveLooksResponseType;
import edu.schema.bstu.dplm.servicetypes.v1.RetrievePriviligesRequestType;
import edu.schema.bstu.dplm.servicetypes.v1.RetrievePriviligesResponseType;
import edu.schema.bstu.dplm.servicetypes.v1.RetrieveUserByCriteriaRequestType;
import edu.schema.bstu.dplm.servicetypes.v1.RetrieveUserByCriteriaResponseType;
import edu.schema.bstu.dplm.servicetypes.v1.RetrieveUsersRequestType;
import edu.schema.bstu.dplm.servicetypes.v1.RetrieveUsersResponseType;
import edu.schema.bstu.dplm.servicetypes.v1.UpdateLocationRequestType;
import edu.schema.bstu.dplm.servicetypes.v1.UpdateLocationResponseType;
import edu.schema.bstu.dplm.servicetypes.v1.UpdateUserRequestType;
import edu.schema.bstu.dplm.servicetypes.v1.UpdateUserResponseType;
import edu.schema.bstu.dplm.datatypes.v1.*;
import edu.schema.bstu.dplm.servicetypes.v1.*;
import edu.wsdl.bstu.dplm.serviceinterface.v1.ServiceInterface;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
public class WebService implements ServiceInterface {

    @Resource
    Mapper mapper = new DozerBeanMapper();

    @Resource
    PriviliegiesDao priviligiesDao;
    @Resource
    EyeLookDao eyeLookDao;
    @Resource
    BodyLookDao bodyLookDao;
    @Resource
    HairLookDao hairLookDao;
    @Resource
    UserDao userDao;
    @Resource
    LocationDao locationDao;
    @Resource
    MapObjectDao mapObjectDao;
    @Resource
    UserResultsDao userResultDao;
    @Resource
    MarkDao markDao;

    @Override
    public CompleteQuizResponseType completeQuiz(@WebParam(partName = "request", name = "completeQuizRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") CompleteQuizRequestType request) {

        double rightResults = 0;
        double maxResult = request.getQuiz().getQuestions().size();
        CompleteQuizResponseType responseType = new CompleteQuizResponseType();

        List<UserResult> userResults = userResultDao.getUserResultsByQuiz(request.getQuiz().getId());

        for (UserResult userResult : userResults) {
            if(userResult.getUserId() == request.getPupilId()) {
                responseType.getUserResult().add(mapper.map(userResult, UserResultsType.class));
                if (userResult.getIsRight()) {
                    rightResults++;
                }
            }
        }
        Mark mark = new Mark();
        float percent = (float)(rightResults / maxResult);

        mark.setPercent(percent*100);
        mark.setQuiztId(request.getQuiz().getId());

        User teacher = userDao.getById(userResults.get(0).getLocation().getIdCreator());
        mark.setTeacherLogin(teacher.getLogin());

        mark.setPupilLogin(userDao.getById(userResults.get(0).getUserId()).getLogin());

        responseType.setMark(mapper.map(markDao.saveOrUpdate(mark), MarkType.class));

        return responseType;
    }

    @Override
    public UpdateMarkResponseType updateMark(@WebParam(partName = "request", name = "updateMarkRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") UpdateMarkRequestType request) {

        UpdateMarkResponseType responseType = new UpdateMarkResponseType();

        Mark daoMark = mapper.map(request.getMark(), Mark.class);

        responseType.setMark(mapper.map(markDao.saveOrUpdate(daoMark),MarkType.class));

        return responseType;
    }

    @Override
    public RetrieveUsersResponseType getUsers(@WebParam(partName = "request", name = "getUsersRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") RetrieveUsersRequestType request) {

        RetrieveUsersResponseType resp = new RetrieveUsersResponseType();

        List<User> userList = userDao.getAll();

        List<UserType> responseList = new ArrayList<UserType>();

        for (User user : userList) {
            responseList.add(mapper.map(user, UserType.class));
        }

        resp.setUsers(responseList);

        return resp;
    }

    @Override
    public UpdateUserResponseType updateUser(@WebParam(partName = "request", name = "updateUserRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") UpdateUserRequestType request) {

        UpdateUserResponseType resp = new UpdateUserResponseType();

        User daoUser = mapper.map(request.getUser(), User.class);

        User updatedDaoUser = userDao.saveOrUpdate(daoUser);

        resp.setUser(mapper.map(updatedDaoUser, UserType.class));

        return resp;
    }

    @Override
    public RetrieveUserResponseType retrieveUser(@WebParam(partName = "request", name = "retrieveUserRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") RetrieveUserRequestType request) {
        RetrieveUserResponseType resp = new RetrieveUserResponseType();

        User user;

        if (request.getId() != null) {
            user = userDao.getById(request.getId());
        } else if (request.getLogin() != null) {
            user = userDao.searchUsers(request.getLogin(), null, null, null).get(0);
        } else {
            throw new IllegalArgumentException("user id or login expected");
        }

        resp.setUser(mapper.map(user, UserType.class));

        return resp;
    }
    @Override
    public AuthorizeResponseType authorize(@WebParam(partName = "request", name = "authorizeRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") AuthorizeRequestType request) {

        AuthorizeResponseType resp = new AuthorizeResponseType();

        User user = userDao.authorize(request.getAuthorizeParameters().getLogin(), request.getAuthorizeParameters().getPassword());

        resp.setUser(mapper.map(user, UserType.class));

        return resp;
    }

    @Deprecated
    @Override
    public DeleteUserResponseType deleteUser(@WebParam(partName = "request", name = "deleteUserRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") DeleteUserRequestType request) {

        Assert.notNull(request.getUser(), "User must not be null!");

        DeleteUserResponseType resp = new DeleteUserResponseType();
        User user = mapper.map(request.getUser(), User.class);

        return null;
    }

    @Override
    public RetrieveUserByCriteriaResponseType searchUserByCriteria(@WebParam(partName = "request", name = "searchUserByCriteriaRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") RetrieveUserByCriteriaRequestType request) {

        RetrieveUserByCriteriaResponseType resp = new RetrieveUserByCriteriaResponseType();
        bstu.dplm.model.user.UserPriviliges priv = null;
        String login = null;
        Date start = null;
        Date end = null;
        if (request.getSearchUserCriteria().getPriviliges() != null) {
            priv = mapper.map(request.getSearchUserCriteria().getPriviliges(), bstu.dplm.model.user.UserPriviliges.class);
        }
        if (request.getSearchUserCriteria().getLogin() != null) {
            login = request.getSearchUserCriteria().getLogin();
        }

        if (request.getSearchUserCriteria().getRegistration() != null) {
            start = request.getSearchUserCriteria().getRegistration().getTime();
        }
        if (request.getSearchUserCriteria().getLastSession() != null) {
            end = request.getSearchUserCriteria().getLastSession().getTime();
        }

        List<User> list = userDao.searchUsers(login, start, end, priv);
        List<UserType> respList = new ArrayList<UserType>();
        for (User user : list) {
            respList.add(mapper.map(user, UserType.class));
        }
        resp.setUser(respList);
        return resp;
    }

    @Override
    public RetrievePriviligesResponseType getPrivs(@WebParam(partName = "request", name = "getPrivsRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") RetrievePriviligesRequestType request) {
        RetrievePriviligesResponseType resp = new RetrievePriviligesResponseType();

        List<bstu.dplm.model.user.UserPriviliges> listDao = priviligiesDao.getAll();
        List<UserPriviligesType> responseList = new ArrayList<UserPriviligesType>();

        for (bstu.dplm.model.user.UserPriviliges privilige : listDao) {
            responseList.add(mapper.map(privilige, UserPriviligesType.class));
        }

        resp.setPriviliges(responseList);

        return resp;
    }

    @Override
    public RetrieveLooksResponseType getLooks(@WebParam(partName = "request", name = "getLooksRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") RetrieveLooksRequestType request) {
        RetrieveLooksResponseType resp = new RetrieveLooksResponseType();

        List<EyeLook> eyeLooks = eyeLookDao.getAll();
        List<BodyLook> bodyLooks = bodyLookDao.getAll();
        List<HairLook> hairLooks = hairLookDao.getAll();

        List<LookType> responseEyeList = new ArrayList<LookType>();
        List<LookType> responseBodyList = new ArrayList<LookType>();
        List<LookType> responseHairList = new ArrayList<LookType>();

        for (EyeLook eyeLook : eyeLooks) {
            responseEyeList.add(mapper.map(eyeLook, LookType.class));
        }
        for (HairLook hairLook : hairLooks) {
            responseHairList.add(mapper.map(hairLook, LookType.class));
        }
        for (BodyLook bodyLook : bodyLooks) {
            responseBodyList.add(mapper.map(bodyLook, LookType.class));
        }

        resp.setBodyLook(responseBodyList);
        resp.setEyeLook(responseEyeList);
        resp.setHairLook(responseHairList);

        return resp;
    }

    @Override
    public RetrieveUserResultsResponseType retrieveUserResults(@WebParam(partName = "request", name = "retrieveUserResultsRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") RetrieveUserResultsRequestType request) {

        RetrieveUserResultsResponseType response = new RetrieveUserResultsResponseType();

        List<UserResult> list = userResultDao.getUserResults(request.getUserId());

        for (UserResult userResult : list) {
            response.getUserResult().add(mapper.map(userResult, UserResultsType.class));
        }

        return response;
    }

    @Override
    public UpdateUserResultsResponseType updateUserResults(@WebParam(partName = "request", name = "updateUserResultsRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") UpdateUserResultsRequestType request) {

        UpdateUserResultsResponseType resp = new UpdateUserResultsResponseType();

        UserResult userResult = mapper.map(request.getUserResult(), UserResult.class);

        if (request.getUserResult().getAnswer() == null && request.getUserResult().getAnswerText() == null) {

            throw new IllegalArgumentException("Answer is expected");
        }
        for (AnswerType answer : request.getUserResult().getQuestion().getAnswers()) {

            if (answer.getIsAnswer()) {
                if(userResult.getAnswerText()== null){
                    userResult.setAnswerText("");
                }
                if ((userResult.getAnswer() != null && userResult.getAnswer().getId() == answer.getId())) {
                    userResult.setIsRight(true);
                } else if (userResult.getAnswerText().equals(answer.getText())) {
                    userResult.setIsRight(true);

                    userResult.setAnswer(new Answer());
                    userResult.getAnswer().setId(answer.getId());
                } else {
                    userResult.setIsRight(false);
                }
            }
        }

        UserResultsType userResultsType = mapper.map(userResultDao.saveOrUpdate(userResult), UserResultsType.class);

        resp.setUserResult(userResultsType);

        return resp;
    }

    @Override
    public RetrieveMarkForQuizResponseType retrieveMarkForQuiz(@WebParam(partName = "request", name = "retrieveMarkForQuizRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") RetrieveMarkForQuizRequestType request) {

        RetrieveMarkForQuizResponseType responseType = new RetrieveMarkForQuizResponseType();

        Quiz quiz =  mapper.map(request.getQuiz(), Quiz.class);
        Mark mark = markDao.getMark(request.getPupilLogin(), quiz);
        List<UserResult> list = userResultDao.getUserResultsByQuiz(quiz.getId());

        for (UserResult userResult : list) {
            responseType.getUserResult().add(mapper.map(userResult, UserResultsType.class));
        }

        responseType.setMark(mapper.map(mark, MarkType.class));

        return responseType;
    }

    @Override
    public UpdateMapObjectResponseType updateMapObject(@WebParam(partName = "request", name = "updateMapObjectRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") UpdateMapObjectRequestType request) {

        UpdateMapObjectResponseType resp = new UpdateMapObjectResponseType();

        MapObject mapObject = mapper.map(request.getMapObject(), MapObject.class);

        resp.setMapObject(mapper.map(mapObjectDao.saveOrUpdate(mapObject), MapObjectType.class));

        return resp;
    }

    @Override
    public GetLocationResponseType getLocation(@WebParam(partName = "request", name = "getLocationRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") GetLocationRequestType request) {

        GetLocationResponseType response = new GetLocationResponseType();
        response.setLocation(mapper.map(locationDao.getById(request.getId()), LocationType.class));
        return response;
    }

    @Override
    public PingResponseType ping(
            @WebParam(partName = "request", name = "pingRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") PingRequestType request) {
        System.out.println("\n\t\tWeb service has been called\n");
        PingResponseType resp = new PingResponseType();
        resp.setData("Hello world");
        return resp;
    }

    @Override
    public RetrieveLocationsResponseType getLocations(@WebParam(partName = "request", name = "getLocationsRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") RetrieveLocationsRequestType request) {
        RetrieveLocationsResponseType resp = new RetrieveLocationsResponseType();

        List<Location> daoList = locationDao.getAll();
        List<LocationType> responseList = new ArrayList<LocationType>();

        for (Location location : daoList) {
            responseList.add(mapper.map(location, LocationType.class));
        }

        resp.setLocation(responseList);
        return resp;
    }

    @Override
    public UpdateLocationResponseType updateLocation(@WebParam(partName = "request", name = "updateLocationRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") UpdateLocationRequestType request) {

        UpdateLocationResponseType response = new UpdateLocationResponseType();

        Location location = mapper.map(request.getLocation(), Location.class);

        location.fixReferences();

        System.out.println(location);

        response.setLocation(mapper.map(locationDao.saveOrUpdate(location), LocationType.class));

        return response;
    }
}
