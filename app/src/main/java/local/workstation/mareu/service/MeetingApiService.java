package local.workstation.mareu.service;

import java.util.List;

import local.workstation.mareu.model.Meeting;

/**
 * Meeting API client
 */
public interface MeetingApiService {
    /**
     * Get all meetings
     * @return list of meetings
     */
    List<Meeting> getMeetings();

    /**
     * Add a meeting
     * @param meeting meeting to add
     */
    void addMeeting(Meeting meeting);

    /**
     * Remove a meeting
     * @param idMeeting meeting to delete using the unique identifier
     */
    void delMeeting(Integer idMeeting);
}
