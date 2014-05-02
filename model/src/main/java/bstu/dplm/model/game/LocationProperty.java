package bstu.dplm.model.game;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "map_data")
public class LocationProperty
{
    private long id;
    private String x;
    private String y;
    private Location location;
    private MapObject mapObject;

    @Id
    @Column(name = "ID_MAP_DATA", precision = 11, scale = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @Column(name = "COOR_X", length = 45)
    public String getX()
    {
        return x;
    }

    public void setX(String x)
    {
        this.x = x;
    }

    @Column(name = "COOR_Y", length = 45)
    public String getY()
    {
        return y;
    }

    public void setY(String y)
    {
        this.y = y;
    }

    @ManyToOne(cascade = javax.persistence.CascadeType.ALL)
    @JoinColumn(name = "ID_MAP", nullable = false)
    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    @ManyToOne
    @JoinColumn(name = "ID_OBJ")
    @Cascade(CascadeType.SAVE_UPDATE)
    public MapObject getMapObject()
    {
        return mapObject;
    }

    public void setMapObject(MapObject mapObject)
    {
        this.mapObject = mapObject;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id",id ).append("location", location).append("mapObject", mapObject).append("x",x).append("y",y).toString();
    }

/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationProperty property = (LocationProperty) o;

        return new EqualsBuilder().append(id, property.id).isEquals();
    }

    @Override
    public int hashCode() {
        {
            return new HashCodeBuilder(14, 40).append(id).toHashCode();
        }
    }*/
}
