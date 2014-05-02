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
@Table(name = "question")
public class Question {

    private long id;
    private String text;
    private Set<Answer> answers = new HashSet<Answer>();

    @Id
    @Column(name = "ID_TEST", precision = 11, scale = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "QUESTION", nullable = false, length = 200)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @OneToMany
    @JoinColumn(name = "ID_QUEST")
    @Cascade(CascadeType.SAVE_UPDATE)
    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("text", text).append("answers", answers).toString();
    }

/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        return new EqualsBuilder().append(id, question.id).isEquals();
    }

    @Override
    public int hashCode() {
        {
            return new HashCodeBuilder(12, 42).append(id).toHashCode();
        }
    }*/
}
