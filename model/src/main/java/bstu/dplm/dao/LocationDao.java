package bstu.dplm.dao;

import bstu.dplm.model.game.Location;
import org.springframework.stereotype.Repository;

@Repository
public class LocationDao extends GenericDao<Location>
{
    protected LocationDao()
    {
        super(Location.class);
    }
}
