package bstu.dplm.dao;

import bstu.dplm.model.game.MapObject;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MapObjectDao extends GenericDao<MapObject> {

    protected MapObjectDao() {
        super(MapObject.class);
    }

    public List<MapObject> getMapObjectsByName(String name)
    {
        return this.sessionFactory.getCurrentSession().createCriteria(MapObject.class).add(Restrictions.eq("name", name)).list();
    }

}
