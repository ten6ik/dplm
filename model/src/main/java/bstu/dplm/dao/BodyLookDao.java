package bstu.dplm.dao;

import bstu.dplm.model.user.BodyLook;
import org.springframework.stereotype.Repository;

@Repository
public class BodyLookDao extends GenericDao<BodyLook>
{
    protected BodyLookDao()
    {
        super(BodyLook.class);
    }
}
