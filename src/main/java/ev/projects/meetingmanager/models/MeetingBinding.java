package ev.projects.meetingmanager.models;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class MeetingBinding {

    private String name;
    private String responsiblePerson;
    private String description;
    private MeetingCategory category;
    private MeetingType type;
    private Date StartDate;
    private Date EndDate;
    private List<PersonBinding> participants = new ArrayList<>();

    public MeetingBinding() {

    }

    public MeetingBinding(String name, String responsiblePerson, String description, MeetingCategory category, MeetingType type, Date startDate, Date endDate) {
        this.name = name;
        this.responsiblePerson = responsiblePerson;
        this.description = description;
        this.category = category;
        this.type = type;
        StartDate = startDate;
        EndDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MeetingCategory getCategory() {
        return category;
    }

    public void setCategory(MeetingCategory category) {
        this.category = category;
    }

    public MeetingType getType() {
        return type;
    }

    public void setType(MeetingType type) {
        this.type = type;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public List<PersonBinding> getParticipants() {
        return participants;
    }

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
        stringBuilder.append(StartDate.toString());
        stringBuilder.append(", endDate= ");
        stringBuilder.append(EndDate.toString());
        stringBuilder.append(", participants= [");
        for(PersonBinding participant: participants) {
            stringBuilder.append("{ ");
            stringBuilder.append(participant.toString());
            stringBuilder.append(" }, ");
        }
        return stringBuilder.toString();
    }

}
