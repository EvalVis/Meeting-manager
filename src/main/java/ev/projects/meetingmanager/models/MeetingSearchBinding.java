package ev.projects.meetingmanager.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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

    public String getDescriptionHas() {
        return descriptionHas;
    }

    public void setDescriptionHas(String descriptionHas) {
        this.descriptionHas = descriptionHas;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public MeetingCategory getMeetingCategory() {
        return meetingCategory;
    }

    public void setMeetingCategory(MeetingCategory meetingCategory) {
        this.meetingCategory = meetingCategory;
    }

    public MeetingType getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(MeetingType meetingType) {
        this.meetingType = meetingType;
    }

    public Date getDateFrom() {
        return DateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        DateFrom = dateFrom;
    }

    public Date getDateTo() {
        return DateTo;
    }

    public void setDateTo(Date dateTo) {
        DateTo = dateTo;
    }

    public Integer getMinParticipantsCount() {
        return minParticipantsCount;
    }

    public void setMinParticipantsCount(Integer minParticipantsCount) {
        this.minParticipantsCount = minParticipantsCount;
    }

    public Integer getMaxParticipantsCount() {
        return maxParticipantsCount;
    }

    public void setMaxParticipantsCount(Integer maxParticipantsCount) {
        this.maxParticipantsCount = maxParticipantsCount;
    }

    public List<MeetingBinding> filterMeetings(Collection<MeetingBinding> meetingBindings) {
        List<MeetingBinding> filteredMeetings = new ArrayList<>();
        for(MeetingBinding meetingBinding: meetingBindings) {
            if(matchesSearch(meetingBinding)) {
                filteredMeetings.add(meetingBinding);
            }
        }
        return filteredMeetings;
    }

    private boolean matchesSearch(MeetingBinding meetingBinding) {
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
