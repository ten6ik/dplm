package bstu.dplm.dao;

import bstu.dplm.model.user.UserPriviliges;
import org.springframework.stereotype.Repository;

@Repository
public class PriviliegiesDao extends GenericDao<UserPriviliges>
{
    protected PriviliegiesDao()
    {
        super(UserPriviliges.class);
    }
}
