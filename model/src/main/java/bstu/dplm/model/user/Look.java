package bstu.dplm.model.user;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Look {
    private String comment;
    private String looklike;

    @Column(name = "COMMENT", length = 100)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(name = "LOOKLIKE", nullable = false, length = 45)
    public String getLooklike() {
        return looklike;
    }

    public void setLooklike(String looklike) {
        this.looklike = looklike;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("comment",comment ).append("looklike", looklike).toString();
    }

/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Look look = (Look) o;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(looklike, look.looklike).isEquals();
    }

    @Override
    public int hashCode() {
        {
            return new HashCodeBuilder(7, 47).append(looklike).toHashCode();
        }
    }*/
}
