package bstu.dplm.model.game;

import javax.persistence.*;

@Entity
@Table(name = "mark")
public class Mark {

    private long id;
    private long quiztId;
    private String teacherLogin;
    private int mark;
    private long percent;
    private String pupilLogin;

    @Id
    @Column(name = "ID_MARK", precision = 11, scale = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "ID_QUIZ", nullable = false, length = 200)
    public long getQuiztId() {
        return quiztId;
    }

    public void setQuiztId(long quiztId) {
        this.quiztId = quiztId;
    }

    @Column(name = "LOGIN_CREATOR", nullable = false, length = 200)
    public String getTeacherLogin() {
        return teacherLogin;
    }

    public void setTeacherLogin(String teacherLogin) {
        this.teacherLogin = teacherLogin;
    }

    @Column(name = "MARK", nullable = false, length = 200)
    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Column(name = "PERCENT", nullable = false, length = 200)
    public long getPercent() {
        return percent;
    }

    public void setPercent(long percent) {
        this.percent = percent;
    }

    @Column(name = "LOGIN_PUPIL", nullable = false, length = 200)
    public String getPupilLogin() {
        return pupilLogin;
    }

    public void setPupilLogin(String pupilLogin) {
        this.pupilLogin = pupilLogin;
    }
}
