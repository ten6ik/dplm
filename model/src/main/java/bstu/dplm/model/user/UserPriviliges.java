package bstu.dplm.model.user;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(name = "privs")
public class UserPriviliges
{

    private long id;
    private String name;
    private String comment;

    @Id
    @Column(name = "ID_PRIV", precision = 11, scale = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @Column(name = "PRIV_NM", length = 45, nullable = false)
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "PRIV_COMMENT", length = 100)
    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String toString()
    {
        return new ToStringBuilder(this).append("id", id).append("name", name).append("comment", comment).toString();
    }

/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPriviliges that = (UserPriviliges) o;

        return new EqualsBuilder().append(id, that.id).isEquals();
    }

    @Override
    public int hashCode() {
        {
            return new HashCodeBuilder(5, 49).append(id).toHashCode();
        }
    }*/
}
