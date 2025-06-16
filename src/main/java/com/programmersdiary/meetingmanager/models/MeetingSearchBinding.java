package com.programmersdiary.meetingmanager.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(description = "Search criteria for filtering meetings. All fields are optional.")
public class MeetingSearchBinding {

    @Schema(description = "Search for meetings containing this text in the name", example = "grand standup")
    private String nameHas;

    @Schema(description = "Search for meetings containing this text in the description", example = "standup")
    private String descriptionHas;
    
    @Schema(description = "Filter by responsible person's name", example = "John Doe")
    private String responsiblePerson;
    
    @Schema(description = "Filter by meeting category")
    private MeetingCategory meetingCategory;
    
    @Schema(description = "Filter by meeting type")
    private MeetingType meetingType;
    
    @Schema(description = "Filter meetings that start from this date onwards", example = "2024-01-01T00:00:00.000Z")
    private Date DateFrom;
    
    @Schema(description = "Filter meetings that end before this date", example = "2024-12-31T23:59:59.000Z")
    private Date DateTo;
    
    @Schema(description = "Filter meetings with at least this many participants", example = "2")
    private Integer minParticipantsCount;
    
    @Schema(description = "Filter meetings with at most this many participants", example = "10")
    private Integer maxParticipantsCount;

    public MeetingSearchBinding() {

    }

    @SuppressWarnings("unused")
    public MeetingSearchBinding(
            String nameHas,
            String descriptionHas,
            String responsiblePerson,
            MeetingCategory meetingCategory,
            MeetingType meetingType,
            Date dateFrom,
            Date dateTo,
            Integer minParticipantsCount,
            Integer maxParticipantsCount
    ) {
        this.nameHas = nameHas;
        this.descriptionHas = descriptionHas;
        this.responsiblePerson = responsiblePerson;
        this.meetingCategory = meetingCategory;
        this.meetingType = meetingType;
        DateFrom = dateFrom;
        DateTo = dateTo;
        this.minParticipantsCount = minParticipantsCount;
        this.maxParticipantsCount = maxParticipantsCount;
    }

    @SuppressWarnings("unused")
    public String getDescriptionHas() {
        return descriptionHas;
    }

    public void setDescriptionHas(String descriptionHas) {
        this.descriptionHas = descriptionHas;
    }

    @SuppressWarnings("unused")
    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    @SuppressWarnings("unused")
    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    @SuppressWarnings("unused")
    public MeetingCategory getMeetingCategory() {
        return meetingCategory;
    }

    @SuppressWarnings("unused")
    public void setMeetingCategory(MeetingCategory meetingCategory) {
        this.meetingCategory = meetingCategory;
    }

    @SuppressWarnings("unused")
    public MeetingType getMeetingType() {
        return meetingType;
    }

    @SuppressWarnings("unused")
    public void setMeetingType(MeetingType meetingType) {
        this.meetingType = meetingType;
    }

    @SuppressWarnings("unused")
    public Date getDateFrom() {
        return DateFrom;
    }

    @SuppressWarnings("unused")
    public void setDateFrom(Date dateFrom) {
        DateFrom = dateFrom;
    }

    @SuppressWarnings("unused")
    public Date getDateTo() {
        return DateTo;
    }

    public void setDateTo(Date dateTo) {
        DateTo = dateTo;
    }

    @SuppressWarnings("unused")
    public Integer getMinParticipantsCount() {
        return minParticipantsCount;
    }

    @SuppressWarnings("unused")
    public void setMinParticipantsCount(Integer minParticipantsCount) {
        this.minParticipantsCount = minParticipantsCount;
    }

    @SuppressWarnings("unused")
    public Integer getMaxParticipantsCount() {
        return maxParticipantsCount;
    }

    @SuppressWarnings("unused")
    public void setMaxParticipantsCount(Integer maxParticipantsCount) {
        this.maxParticipantsCount = maxParticipantsCount;
    }

    public boolean matchesSearch(MeetingBinding meetingBinding) {
        return matchesName(meetingBinding) && matchesDescription(meetingBinding) &&
                matchesResponsiblePerson(meetingBinding) && matchesMeetingCategory(meetingBinding) &&
                matchesMeetingType(meetingBinding) && matchesDateInterval(meetingBinding) &&
                matchesParticipantBoundaries(meetingBinding);
    }

    private boolean matchesName(MeetingBinding meetingBinding) {
        return nameHas == null || meetingBinding.getName().contains(nameHas);
    }

    private boolean matchesDescription(MeetingBinding meetingBinding) {
        return descriptionHas == null || meetingBinding.getDescription().contains(descriptionHas);
    }

    private boolean matchesResponsiblePerson(MeetingBinding meetingBinding) {
        return responsiblePerson == null || responsiblePerson.isEmpty() ||
                meetingBinding.getResponsiblePerson().equalsIgnoreCase(responsiblePerson);
    }

    private boolean matchesMeetingCategory(MeetingBinding meetingBinding) {
        return meetingCategory == null || meetingCategory == meetingBinding.getCategory();
    }

    private boolean matchesMeetingType(MeetingBinding meetingBinding) {
        return meetingType == null || meetingType == meetingBinding.getType();
    }

    private boolean matchesDateInterval(MeetingBinding meetingBinding) {
        return !((DateFrom != null && meetingBinding.getStartDate().before(DateFrom)) ||
                (DateTo != null && meetingBinding.getEndDate().after(DateTo)));
    }

    private boolean matchesParticipantBoundaries(MeetingBinding meetingBinding) {
        int participantCount = meetingBinding.getParticipants().size();
        return (minParticipantsCount == null || minParticipantsCount <= participantCount) &&
                (maxParticipantsCount == null || maxParticipantsCount >= participantCount);
    }

}
