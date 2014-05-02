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
@Table(name = "quiz")
public class Quiz
{

    private long id;
    private String name;
    private Set<Question> questions = new HashSet<Question>();

    @Id
    @Column(name = "ID_TEST_QST_LIST", precision = 11, scale = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @Column(name = "NAME", nullable = false, length = 45)
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @ManyToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "questions_for_quiz", joinColumns = @JoinColumn(name = "ID_QUIZ", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "ID_QUESTION", nullable = false, updatable = false))
    @Cascade(CascadeType.ALL)
    public Set<Question> getQuestions()
    {
        return questions;
    }

    public void setQuestions(Set<Question> questions)
    {
        this.questions = questions;
    }

    public String toString()
    {
        return new ToStringBuilder(this).append("id", id).append("name", name).append("questions", questions).toString();
    }

/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quiz quiz = (Quiz) o;

        return new EqualsBuilder().append(id, quiz.id).isEquals();
    }

    @Override
    public int hashCode() {
        {
            return new HashCodeBuilder(11, 43).append(id).toHashCode();
        }
    }*/
}
