package za.org.grassroot.core.domain;

import za.org.grassroot.core.util.UIDGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by luke on 2015/10/20.
 */
@Entity
@Table(name="paid_group")
public class PaidGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "uid", nullable = false, unique = true)
    private String uid;

    /*
    Entity may get created before it is made active, hence the two different timestamps
     */
    @Basic
    @Column(name="created_date_time", insertable = true, updatable = false)
    private Timestamp createdDateTime;

    /*
    Since this is a record for a specific duration of time, we have many-to-one on groups (so, one group can be paid
    by a certain account for a certain period, a different one later, etc., but only ever one at a time).
     */
    @ManyToOne
    @JoinColumn(name="group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

    @Basic
    @Column(name="active_date_time")
    private Timestamp activeDateTime;

    @ManyToOne
    @JoinColumn(name="user_added_id")
    private User addedByUser;

    @ManyToOne
    @JoinColumn(name="user_removed_id", nullable = true)
    private User removedByUser;

    @Basic
    @Column(name="expire_date_time")
    private Timestamp expireDateTime;

    /*
    Constructors
     */

    /*
    If we are just passed the group and account, assume that active time is now, expiry time forever
    todo: check the group has a name, and is otherwise well formed, or else throw an error
    todo: perform same sort of checks on the account
     */

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        if (createdDateTime == null) {
            createdDateTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
        }
    }

    public PaidGroup(Group group, Account account, User addedByUser) {

        this.uid = UIDGenerator.generateId();
        this.group = group;
        this.account = account;
        this.addedByUser = addedByUser;

        this.activeDateTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
        this.expireDateTime = Timestamp.valueOf("2999-12-31 23:59:59");

    }

    /*
    Constructor with custom active and expiry dates
     */

    public PaidGroup(Group group, Account account, User addedByUser, Timestamp activeDateTime, Timestamp expireDateTime) {

        this.uid = UIDGenerator.generateId();
        this.group = group;
        this.account = account;
        this.addedByUser = addedByUser;
        this.activeDateTime = activeDateTime;
        this.expireDateTime = expireDateTime;

    }

    private PaidGroup() {
        // For JPA
    }

    /*
    Getters and setters
    Some things should be immutable -- group, added user, active timestamp, so leaving out setters for them.
    */

    public Long getId() {
        return id;
    }

    public String getUid() { return uid; }

    public Timestamp getCreatedDateTime() {
        return createdDateTime;
    }

    public Group getGroup() {
        return group;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) { this.account = account; } // we may want to reassign a group at some point

    public Timestamp getActiveDateTime() {
        return activeDateTime;
    }

    public User getAddedByUser() { return addedByUser; }

    public User getRemovedByUser() { return removedByUser; }

    public void setRemovedByUser(User removedByUser) { this.removedByUser = removedByUser; }

    public void setActiveDateTime(Timestamp activeDateTime) { this.activeDateTime = activeDateTime; }

    public Timestamp getExpireDateTime() {
        return expireDateTime;
    }

    public void setExpireDateTime(Timestamp expireDateTime) { this.expireDateTime = expireDateTime; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof PaidGroup)) {
            return false;
        }

        PaidGroup paidGroup = (PaidGroup) o;

        if (getUid() != null ? !getUid().equals(paidGroup.getUid()) : paidGroup.getUid() != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return getUid() != null ? getUid().hashCode() : 0;
    }
}
