package ev.projects.meetingmanager.models;

import java.util.Date;

public class PersonBinding {

    private String fullName;

    private Date invitationDate;

    public PersonBinding() {
    }

    public PersonBinding(String fullName, Date invitationDate) {
        this.fullName = fullName;
        this.invitationDate = invitationDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getInvitationDate() {
        return invitationDate;
    }

    public void setInvitationDate(Date invitationDate) {
        this.invitationDate = invitationDate;
    }

    @Override
    public String toString() {
        return "PersonBinding{" +
                "fullName='" + fullName + '\'' +
                ", invitationDate=" + invitationDate +
                '}';
    }

}
