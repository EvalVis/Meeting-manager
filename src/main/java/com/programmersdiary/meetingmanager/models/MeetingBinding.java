package com.programmersdiary.meetingmanager.models;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Schema(description = "Meeting details including all required information for creating or updating a meeting")
public class MeetingBinding {

    @Schema(description = "Name of the meeting", example = "Weekly Team Standup", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    
    @Schema(description = "Full name of the person responsible for organizing the meeting", example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String responsiblePerson;
    
    @Schema(description = "Detailed description of the meeting purpose and agenda", example = "Weekly standup to discuss project progress and blockers", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
    
    @Schema(description = "Category of the meeting", requiredMode = Schema.RequiredMode.REQUIRED)
    private MeetingCategory category;
    
    @Schema(description = "Type of the meeting", requiredMode = Schema.RequiredMode.REQUIRED)
    private MeetingType type;
    
    @Schema(description = "Start date and time of the meeting", example = "2024-01-15T10:00:00.000Z", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date startDate;
    
    @Schema(description = "End date and time of the meeting", example = "2024-01-15T11:00:00.000Z", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date endDate;
    
    @Schema(description = "List of meeting participants")
    private List<PersonBinding> participants = new ArrayList<>();

    @SuppressWarnings("unused")
    public MeetingBinding() {

    }

    public MeetingBinding(String name, String responsiblePerson, String description, MeetingCategory category, MeetingType type, Date startDate, Date endDate) {
        this.name = name;
        this.responsiblePerson = responsiblePerson;
        this.description = description;
        this.category = category;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    @SuppressWarnings("unused")
    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getDescription() {
        return description;
    }

    @SuppressWarnings("unused")
    public void setDescription(String description) {
        this.description = description;
    }

    public MeetingCategory getCategory() {
        return category;
    }

    @SuppressWarnings("unused")
    public void setCategory(MeetingCategory category) {
        this.category = category;
    }

    public MeetingType getType() {
        return type;
    }

    @SuppressWarnings("unused")
    public void setType(MeetingType type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    @SuppressWarnings("unused")
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @SuppressWarnings("unused")
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<PersonBinding> getParticipants() {
        return participants;
    }

    @SuppressWarnings("unused")
    public void setParticipants(List<PersonBinding> participants) {
        this.participants = participants;
    }

    public void addParticipant(PersonBinding personBinding) {
        participants.add(personBinding);
    }

    public void removeParticipant(String personFullName) {
        participants.removeIf(participant -> participant.getFullName().equalsIgnoreCase(personFullName));
    }

    public boolean findParticipant(String participantFullName) {
        for(PersonBinding participant: participants) {
            if(participant.getFullName().equalsIgnoreCase(participantFullName)) {
                return true;
            }
        }
        return false;
    }

    public boolean meetingsIntersect(MeetingBinding meetingBindingToCompare) {
        return !(getEndDate().before(meetingBindingToCompare.getStartDate()) ||
                getStartDate().after(meetingBindingToCompare.getEndDate()));
    }

    public static MeetingBinding findMeetingByName(Collection<MeetingBinding> meetingBindingCollection, String name) {
        for(MeetingBinding meetingBinding: meetingBindingCollection) {
            if(meetingBinding.getName().equalsIgnoreCase(name)) {
                return meetingBinding;
            }
        }
        return null;
    }

    public boolean anyFieldNull() {
        return name == null || responsiblePerson == null || description == null || category == null
                || type == null || startDate == null || endDate == null;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MeetingBinding: { name= ");
        stringBuilder.append(name);
        stringBuilder.append(", responsiblePerson= ");
        stringBuilder.append(responsiblePerson);
        stringBuilder.append(", description= ");
        stringBuilder.append(description);
        stringBuilder.append(", category= ");
        stringBuilder.append(category);
        stringBuilder.append(", type= ");
        stringBuilder.append(type);
        stringBuilder.append(", startDate= ");
        stringBuilder.append(startDate.toString());
        stringBuilder.append(", endDate= ");
        stringBuilder.append(endDate.toString());
        stringBuilder.append(", participants= [");
        for(PersonBinding participant: participants) {
            stringBuilder.append("{ ");
            stringBuilder.append(participant.toString());
            stringBuilder.append(" }, ");
        }
        stringBuilder.append("]}");
        return stringBuilder.toString();
    }

}
