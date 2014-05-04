package bstu.dplm.dao;

import bstu.dplm.model.game.MapObject;
import org.springframework.stereotype.Repository;

@Repository
public class MapObjectDao extends GenericDao<MapObject> {

    protected MapObjectDao() {
        super(MapObject.class);
    }
}
