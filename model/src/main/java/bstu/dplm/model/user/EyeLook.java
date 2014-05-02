package bstu.dplm.model.user;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(name = "eye_look")
public class EyeLook extends Look
{
    private long id;

    @Id
    @Column(name = "ID_EYE", precision = 10, scale = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id",id ).toString();
    }

/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EyeLook eyeLook = (EyeLook) o;

        return new EqualsBuilder().append(id, eyeLook.id).isEquals();
    }

    @Override
    public int hashCode() {
        {
            return new HashCodeBuilder(9, 45).append(id).toHashCode();
        }
    }*/
}
