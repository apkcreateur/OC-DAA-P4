package local.workstation.mareu.events;

public class DeleteMeetingEvent {
    /**
     * Meeting to delete by unique ID
     */
    private int meetingId;

    /**
     * Constructor.
     * @param meetingId meeting to delete by unique ID
     */
    public DeleteMeetingEvent(int meetingId) {
        this.meetingId = meetingId;
    }

    public int getMeetingId() {
        return meetingId;
    }
}
