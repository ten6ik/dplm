package bstu.dplm.webservice;

import bstu.dplm.dao.*;
import bstu.dplm.model.game.Location;
import bstu.dplm.model.user.BodyLook;
import bstu.dplm.model.user.EyeLook;
import bstu.dplm.model.user.HairLook;
import bstu.dplm.model.user.User;
import edu.schema.bstu.dplm.datatypes.v1.Look;
import edu.schema.bstu.dplm.datatypes.v1.UserPriviliges;
import edu.schema.bstu.dplm.servicetypes.v1.*;
import edu.wsdl.bstu.dplm.serviceinterface.v1.ServiceInterface;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jws.WebParam;
import java.util.ArrayList;
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
    public RetrieveUserByLoginResponseType searchUser(@WebParam(partName = "request", name = "searchUserRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") RetrieveUserByLoginRequestType request) {
        return null;
    }

    @Override
    public DeleteUserResponseType deleteUser(@WebParam(partName = "request", name = "deleteUserRequest", targetNamespace = "http://edu/schema/bstu/dplm/servicetypes/v1") DeleteUserRequestType request) {
        return null;
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

        Location location =  mapper.map(request.getLocation(), Location.class);

        location.fixReferences();

        System.out.println(location);

        response.setLocation(mapper.map(locationDao.saveOrUpdate(location), edu.schema.bstu.dplm.datatypes.v1.Location.class));

        return response;
    }
}
