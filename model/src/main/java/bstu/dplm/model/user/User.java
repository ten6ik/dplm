package bstu.dplm.model.user;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cascade;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user", schema = "darkproject")
public class User
{

    private long id;
    private String login;
    private String password;
    private Date registration;
    private Date lastSession;
    private HairLook hairLook;
    private EyeLook eyeLook;
    private BodyLook bodyLook;
    private Set<UserPriviliges> priviliges = new HashSet<UserPriviliges>();
    private Set<UserResult> results = new HashSet<UserResult>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USR", precision = 10, scale = 0)
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @Column(name = "USR_LOGIN", nullable = false, length = 45)
    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    @Column(name = "USR_PASSWORD", nullable = false, length = 45)
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Column(name = "USR_REG_TIME")
    @Temporal(TemporalType.DATE)
    public Date getRegistration()
    {
        return registration;
    }

    public void setRegistration(Date registration)
    {
        this.registration = registration;
    }

    @Column(name = "USR_LOGIN_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastSession()
    {
        return lastSession;
    }

    public void setLastSession(Date lastSession)
    {
        this.lastSession = lastSession;
    }

    @OneToOne
    @JoinColumn(name = "ID_HEAR")
    public HairLook getHairLook()
    {
        return hairLook;
    }

    public void setHairLook(HairLook hairLook)
    {
        this.hairLook = hairLook;
    }

    @OneToOne
    @JoinColumn(name = "ID_EYE")
    public EyeLook getEyeLook()
    {
        return eyeLook;
    }

    public void setEyeLook(EyeLook eyeLook)
    {
        this.eyeLook = eyeLook;
    }

    @OneToOne
    @JoinColumn(name = "ID_BODY")
    public BodyLook getBodyLook()
    {
        return bodyLook;
    }

    public void setBodyLook(BodyLook bodyLook)
    {
        this.bodyLook = bodyLook;
    }

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "user_privs", joinColumns = @JoinColumn(name = "ID_USR", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "ID_PRIV", nullable = false, updatable = false))
    public Set<UserPriviliges> getPriviliges()
    {
        return priviliges;
    }

    public void setPriviliges(Set<UserPriviliges> priviliges)
    {
        this.priviliges = priviliges;
    }

    @OneToMany
    @JoinColumn(name = "ID_USR")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    public Set<UserResult> getResults()
    {
        return results;
    }

    public void setResults(Set<UserResult> results)
    {
        this.results = results;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("id", id).append("login", login).append("password", password)
                .append("registration", registration).append("lastSession", lastSession)
                .append("priviliges", priviliges).append("eyeLook", eyeLook).append("hairLook", hairLook)
                .append("bodyLook", bodyLook).toString();
    }

    /*
     * @Override public boolean equals(Object o) { if (this == o) return true;
     * if (o == null || getClass() != o.getClass()) return false;
     * 
     * User user = (User) o;
     * 
     * return new EqualsBuilder().append(id, user.id).isEquals(); }
     * 
     * @Override public int hashCode() { { return new HashCodeBuilder(6,
     * 48).append(id).toHashCode(); } }
     */
}
