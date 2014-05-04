package bstu.dplm.webservice;

import bstu.dplm.dao.*;
import bstu.dplm.model.game.Location;
import bstu.dplm.model.game.MapObject;
import bstu.dplm.model.user.BodyLook;
import bstu.dplm.model.user.EyeLook;
import bstu.dplm.model.user.HairLook;
import bstu.dplm.model.user.User;
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

    @Override
    public UpdateUserResponseType updateUser(@WebParam(partName = "request", name = "updateUserRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") UpdateUserRequestType request) {

        UpdateUserResponseType resp = new UpdateUserResponseType();

        User daoUser = mapper.map(request.getUser(), User.class);

        daoUser.updateReferences();

        User updatedDaoUser = userDao.saveOrUpdate(daoUser);

        resp.setUser(mapper.map(updatedDaoUser, UserType.class));

        return resp;
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
    public UpdateMapObjectResponseType updateMapObject(@WebParam(partName = "request", name = "updateMapObjectRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") UpdateMapObjectRequestType request) {

        UpdateMapObjectResponseType resp = new UpdateMapObjectResponseType();

        MapObject mapObject = mapper.map(request.getMapObject(), MapObject.class);

        resp.setMapObject(mapper.map(mapObjectDao.saveOrUpdate(mapObject), MapObjectType.class));

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
    public GetLocationResponseType getLocation(@WebParam(partName = "request", name = "getLocationRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") GetLocationRequestType request) {

        GetLocationResponseType response = new GetLocationResponseType();
        response.setLocation(mapper.map(locationDao.getById(request.getId()), LocationType.class));
        return response;
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
    public UpdateLocationResponseType updateLocation(@WebParam(partName = "request", name = "updateLocationRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") UpdateLocationRequestType request) {

        UpdateLocationResponseType response = new UpdateLocationResponseType();

        Location location = mapper.map(request.getLocation(), Location.class);

        location.fixReferences();

        System.out.println(location);

        response.setLocation(mapper.map(locationDao.saveOrUpdate(location), LocationType.class));

        return response;
    }
}
