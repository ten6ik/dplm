package bstu.dplm.model.game;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "answer")
public class Answer {

    private long id;
    private String text;
    private boolean isAnswer;

    @Id
    @Column(name = "ID_TEST_VARIANT", precision = 11, scale = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "VARIANT", nullable = false, length = 45)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "IS_ANSWER")
    public boolean getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(boolean isAnswer) {
        this.isAnswer = isAnswer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(id).append(text).append(isAnswer).toString();
    }

/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        return new EqualsBuilder().append(id, answer.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }*/
}
