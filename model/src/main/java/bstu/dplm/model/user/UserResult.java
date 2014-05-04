package bstu.dplm.model.user;

import bstu.dplm.model.game.Answer;
import bstu.dplm.model.game.Location;
import bstu.dplm.model.game.Question;
import bstu.dplm.model.game.Quiz;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usr_result")
public class UserResult
{
    long id;
    boolean isRight;
    String answerText;
    String sessionId;
    Quiz quiz;
    Question question;
    Answer answer;
    Location location;

    @Id
    @Column(name = "ID_RESULT", precision = 11, scale = 0)
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @Column(name = "IS_RIGHT")
    public boolean getIsRight()
    {
        return isRight;
    }

    public void setIsRight(boolean isRight)
    {
        this.isRight = isRight;
    }

    @Column(name = "ANSWER", length = 45)
    public String getAnswerText()
    {
        return answerText;
    }

    public void setAnswerText(String answerText)
    {
        this.answerText = answerText;
    }

    @Column(name = "SESSION_ID", length = 100)
    public String getSessionId()
    {
        return sessionId;
    }

    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }

    @ManyToOne
    @JoinColumn(name = "ID_TEST_LIST")
    public Quiz getQuiz()
    {
        return quiz;
    }

    public void setQuiz(Quiz quiz)
    {
        this.quiz = quiz;
    }

    @ManyToOne
    @JoinColumn(name = "ID_TEST")
    public Question getQuestion()
    {
        return question;
    }

    public void setQuestion(Question question)
    {
        this.question = question;
    }

    @ManyToOne
    @JoinColumn(name = "ANSWER_ID")
    public Answer getAnswer()
    {
        return answer;
    }

    public void setAnswer(Answer answer)
    {
        this.answer = answer;
    }

    @ManyToOne
    @JoinColumn(name = "ID_MAP")
    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }
}
