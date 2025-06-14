package com.programmersdiary.meetingmanager.models;

import java.util.Date;

public class PersonBinding {

    private String fullName;

    private Date invitationDate;

    @SuppressWarnings("unused")
    public PersonBinding() {
    }

    public PersonBinding(String fullName, Date invitationDate) {
        this.fullName = fullName;
        this.invitationDate = invitationDate;
    }

    public String getFullName() {
        return fullName;
    }

    @SuppressWarnings("unused")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @SuppressWarnings("unused")
    public Date getInvitationDate() {
        return invitationDate;
    }

    @SuppressWarnings("unused")
    public void setInvitationDate(Date invitationDate) {
        this.invitationDate = invitationDate;
    }

    public boolean anyFieldNull() {
        return fullName == null || invitationDate == null;
    }

    @Override
    public String toString() {
        return "PersonBinding{" +
                "fullName='" + fullName + '\'' +
                ", invitationDate=" + invitationDate +
                '}';
    }

}
