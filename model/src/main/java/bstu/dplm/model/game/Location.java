package bstu.dplm.model.game;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "location")
public class Location {
    private long id;
    private String name;
    private String comment;
    private String picture;
    private Date created;
    private long idCreator;
    Set<LocationProperty> properties = new HashSet<LocationProperty>();

    @Id
    @Column(name = "MAP_ID", precision = 11, scale = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "MAP_NM", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "MAP_COMMENT", length = 400)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(name = "MAP_PIC", length = 45)
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Column(name = "CREATE_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Column(name = "ID_CREATOR", nullable = false, precision = 11, scale = 0)
    public long getIdCreator() {
        return idCreator;
    }

    public void setIdCreator(long idCreator) {
        this.idCreator = idCreator;
    }

    @OneToMany(mappedBy = "location")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    public Set<LocationProperty> getProperties() {
        return properties;
    }

    public void setProperties(Set<LocationProperty> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id",id ).append("comment", comment).append("name",name).append("picture",picture).append("created",created).append("creator", idCreator).append("properties",properties).toString();
    }

/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return new EqualsBuilder().append(id, location.id).isEquals();
    }

    @Override
    public int hashCode() {
        {
            return new HashCodeBuilder(15, 39).append(id).toHashCode();
        }
    }*/

    public void fixReferences()
    {
        for (LocationProperty property : properties) {
            property.setLocation(this);
        }
    }
}
