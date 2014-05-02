package bstu.dplm.model.user;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "body_look")
public class BodyLook extends Look
{
    private long id;

    @Id
    @Column(name = "ID_BODY", precision = 10, scale = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BodyLook bodyLook = (BodyLook) o;

        return new EqualsBuilder().append(id, bodyLook.id).isEquals();
    }

    @Override
    public int hashCode() {
        {
            return new HashCodeBuilder(10, 44).append(id).toHashCode();
        }
    }*/
}
