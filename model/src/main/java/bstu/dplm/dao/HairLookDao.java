package bstu.dplm.dao;

import bstu.dplm.model.user.HairLook;
import org.springframework.stereotype.Repository;

@Repository
public class HairLookDao extends GenericDao<HairLook>
{
    protected HairLookDao()
    {
        super(HairLook.class);
    }
}
