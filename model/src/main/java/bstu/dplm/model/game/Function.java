package bstu.dplm.model.game;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(name = "function")
public class Function
{
    private long id;
    private String function;
    private String comment;

    @Id
    @Column(name = "FUNC_ID", precision = 11, scale = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @Column(name = "FUNC_NM", nullable = false, length = 45)
    public String getFunction()
    {
        return function;
    }

    public void setFunction(String function)
    {
        this.function = function;
    }

    @Column(name = "FUNC_COMMENT", length = 400)
    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id",id ).append("comment", comment).append("function",function).toString();
    }

/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Function function1 = (Function) o;

        return new EqualsBuilder().append(id, function1.id).isEquals();
    }

    @Override
    public int hashCode() {
        {
            return new HashCodeBuilder(16, 38).append(id).toHashCode();
        }
    }*/
}
