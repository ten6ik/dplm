package bstu.dplm.model.game;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "object")
public class MapObject
{
    private long id;
    private String name;
    private String comment;
    private String view;
    private Function function;
    private Set<Quiz> quizes = new HashSet<Quiz>();

    @Id
    @Column(name = "ID_OBJ", precision = 11, scale = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @Column(name = "OBJ_NM", nullable = false, length = 45)
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "OBJ_COMMENT", length = 400)
    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    @Column(name = "OBJ_VIEW", nullable = false, length = 45)
    public String getView()
    {
        return view;
    }

    public void setView(String view)
    {
        this.view = view;
    }

    @ManyToOne
    @JoinColumn(name = "FUNC_ID")
    @Cascade(CascadeType.SAVE_UPDATE)
    public Function getFunction()
    {
        return function;
    }

    public void setFunction(Function function)
    {
        this.function = function;
    }

    @OneToMany
    @JoinColumn(name = "ID_OBJ")
    @Cascade(CascadeType.SAVE_UPDATE)
    public  Set<Quiz> getQuiz()
    {
        return quizes;
    }

    public void setQuiz( Set<Quiz> quizes)
    {
        this.quizes = quizes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id",id ).append("comment", comment).append("function",function).append("name",name).append("quiz",quizes).append("view",view).toString();
    }

/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapObject mapObject = (MapObject) o;

        return new EqualsBuilder().append(id, mapObject.id).isEquals();
    }

    @Override
    public int hashCode() {
        {
            return new HashCodeBuilder(13, 41).append(id).toHashCode();
        }
    }*/
}
