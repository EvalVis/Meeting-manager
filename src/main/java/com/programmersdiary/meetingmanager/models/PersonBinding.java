package com.programmersdiary.meetingmanager.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(description = "Person information for meeting participants")
public class PersonBinding {

    @Schema(description = "Full name of the person", example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fullName;

    @Schema(description = "Date when the person was invited to the meeting", example = "2024-01-10T09:00:00.000Z", requiredMode = Schema.RequiredMode.REQUIRED)
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
