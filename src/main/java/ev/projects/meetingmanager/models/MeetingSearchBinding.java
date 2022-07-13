package ev.projects.meetingmanager.models;

import java.util.Date;

public class MeetingSearchBinding {

    private String descriptionHas;
    private String responsiblePerson;
    private MeetingCategory meetingCategory;
    private MeetingType meetingType;
    private Date DateFrom;
    private Date DateTo;
    private Integer minParticipantsCount;
    private Integer maxParticipantsCount;

    public MeetingSearchBinding() {

    }

    @SuppressWarnings("unused")
    public MeetingSearchBinding(String descriptionHas, String responsiblePerson,
                                MeetingCategory meetingCategory, MeetingType meetingType,
                                Date dateFrom, Date dateTo,
                                Integer minParticipantsCount, Integer maxParticipantsCount) {
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
        return matchesDescription(meetingBinding) && matchesResponsiblePerson(meetingBinding) &&
                matchesMeetingCategory(meetingBinding) && matchesMeetingType(meetingBinding) &&
                matchesDateInterval(meetingBinding) && matchesParticipantBoundaries(meetingBinding);
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
