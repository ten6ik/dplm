package bstu.dplm.webservice;

import bstu.dplm.dao.BodyLookDao;
import bstu.dplm.dao.EyeLookDao;
import bstu.dplm.dao.HairLookDao;
import bstu.dplm.dao.LocationDao;
import bstu.dplm.dao.PriviliegiesDao;
import bstu.dplm.dao.UserDao;
import bstu.dplm.model.game.Location;
import bstu.dplm.model.user.BodyLook;
import bstu.dplm.model.user.EyeLook;
import bstu.dplm.model.user.HairLook;
import bstu.dplm.model.user.User;
import edu.schema.bstu.dplm.datatypes.v1.UserPriviliges;
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

    @Override
    public UpdateUserResponseType updateUser(@WebParam(partName = "request", name = "updateUserRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") UpdateUserRequestType request) {

        UpdateUserResponseType resp = new UpdateUserResponseType();

        User daoUser = mapper.map(request.getUser(), User.class);

        daoUser.updateReferences();

        User updatedDaoUser = userDao.saveOrUpdate(daoUser);

        resp.setUser(mapper.map(updatedDaoUser, edu.schema.bstu.dplm.datatypes.v1.User.class));

        return resp;
    }

    @Override
    public RetrieveUsersResponseType getUsers(@WebParam(partName = "request", name = "getUsersRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") RetrieveUsersRequestType request) {

        RetrieveUsersResponseType resp = new RetrieveUsersResponseType();

        List<User> userList = userDao.getAll();

        List<edu.schema.bstu.dplm.datatypes.v1.User> responseList = new ArrayList<edu.schema.bstu.dplm.datatypes.v1.User>();

        for (User user : userList) {
            responseList.add(mapper.map(user, edu.schema.bstu.dplm.datatypes.v1.User.class));
        }

        resp.setUsers(responseList);

        return resp;
    }

    @Override
    public AuthorizeResponseType authorize(@WebParam(partName = "request", name = "authorizeRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") AuthorizeRequestType request) {

        AuthorizeResponseType resp = new AuthorizeResponseType();

        User user = userDao.authorize(request.getAuthorizeParameters().getLogin(), request.getAuthorizeParameters().getPassword());

        resp.setUser(mapper.map(user, edu.schema.bstu.dplm.datatypes.v1.User.class));

        return resp;
    }

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
        List<edu.schema.bstu.dplm.datatypes.v1.User> respList = new ArrayList<edu.schema.bstu.dplm.datatypes.v1.User>();
        for (User user : list) {
            respList.add(mapper.map(user, edu.schema.bstu.dplm.datatypes.v1.User.class));
        }
        resp.setUser(respList);
        return resp;
    }

    @Override
    public GetLocationResponseType getLocation(@WebParam(partName = "request", name = "getLocationRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") GetLocationRequestType request) {

        GetLocationResponseType response = new GetLocationResponseType();
        response.setLocation(mapper.map(locationDao.getById(request.getId()), edu.schema.bstu.dplm.datatypes.v1.Location.class));
        return response;
    }

    @Override
    public RetrieveLooksResponseType getLooks(@WebParam(partName = "request", name = "getLooksRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") RetrieveLooksRequestType request) {
        RetrieveLooksResponseType resp = new RetrieveLooksResponseType();

        List<EyeLook> eyeLooks = eyeLookDao.getAll();
        List<BodyLook> bodyLooks = bodyLookDao.getAll();
        List<HairLook> hairLooks = hairLookDao.getAll();

        List<edu.schema.bstu.dplm.datatypes.v1.Look> responseEyeList = new ArrayList<edu.schema.bstu.dplm.datatypes.v1.Look>();
        List<edu.schema.bstu.dplm.datatypes.v1.Look> responseBodyList = new ArrayList<edu.schema.bstu.dplm.datatypes.v1.Look>();
        List<edu.schema.bstu.dplm.datatypes.v1.Look> responseHairList = new ArrayList<edu.schema.bstu.dplm.datatypes.v1.Look>();

        for (EyeLook eyeLook : eyeLooks) {
            responseEyeList.add(mapper.map(eyeLook, edu.schema.bstu.dplm.datatypes.v1.Look.class));
        }
        for (HairLook hairLook : hairLooks) {
            responseHairList.add(mapper.map(hairLook, edu.schema.bstu.dplm.datatypes.v1.Look.class));
        }
        for (BodyLook bodyLook : bodyLooks) {
            responseBodyList.add(mapper.map(bodyLook, edu.schema.bstu.dplm.datatypes.v1.Look.class));
        }

        resp.setBodyLook(responseBodyList);
        resp.setEyeLook(responseEyeList);
        resp.setHairLook(responseHairList);

        return resp;
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
        List<edu.schema.bstu.dplm.datatypes.v1.Location> responseList = new ArrayList<edu.schema.bstu.dplm.datatypes.v1.Location>();

        for (Location location : daoList) {
            responseList.add(mapper.map(location, edu.schema.bstu.dplm.datatypes.v1.Location.class));
        }

        resp.setLocation(responseList);
        return resp;
    }

    @Override
    public RetrievePriviligesResponseType getPrivs(@WebParam(partName = "request", name = "getPrivsRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") RetrievePriviligesRequestType request) {
        RetrievePriviligesResponseType resp = new RetrievePriviligesResponseType();

        List<bstu.dplm.model.user.UserPriviliges> listDao = priviligiesDao.getAll();
        List<UserPriviliges> responseList = new ArrayList<UserPriviliges>();

        for (bstu.dplm.model.user.UserPriviliges privilige : listDao) {
            responseList.add(mapper.map(privilige, UserPriviliges.class));
        }

        resp.setPriviliges(responseList);

        return resp;
    }

    @Override
    public UpdateLocationResponseType updateLocation(@WebParam(partName = "request", name = "updateLocationRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") UpdateLocationRequestType request) {

        UpdateLocationResponseType response = new UpdateLocationResponseType();

        Location location = mapper.map(request.getLocation(), Location.class);

        location.fixReferences();

        System.out.println(location);

        response.setLocation(mapper.map(locationDao.saveOrUpdate(location), edu.schema.bstu.dplm.datatypes.v1.Location.class));

        return response;
    }
}
