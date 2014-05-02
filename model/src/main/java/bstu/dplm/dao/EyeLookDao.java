package bstu.dplm.dao;

import bstu.dplm.model.user.EyeLook;
import org.springframework.stereotype.Repository;

@Repository
public class EyeLookDao extends GenericDao<EyeLook>
{
    protected EyeLookDao()
    {
        super(EyeLook.class);
    }
}
