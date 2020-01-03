package local.workstation.mareu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import local.workstation.mareu.model.Meeting;

/**
 * Dummy mock for the Meeting Api Service
 */
public class FakeMeetingApiService implements MeetingApiService {
    private List<Meeting> mMeetings;
    private final List<String> mRooms;

    public FakeMeetingApiService() {
        mMeetings = new ArrayList<>();
        mRooms = new ArrayList<>(Arrays.asList(
                "Room 1", "Room 2", "Room 3", "Room 4", "Room 5",
                "Room 6", "Room 7", "Room 8", "Room 9", "Room 10"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getRooms() {
        return mRooms;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addRoom(String room) {
        mRooms.add(room);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delRoom(String room) {
        mRooms.remove(room);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delAllRooms() {
        mRooms.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeetings() {
        return mMeetings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMeeting(Meeting meeting) {
        mMeetings.add(meeting);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delMeeting(Integer idMeeting) {
        for (Meeting m: mMeetings) {
            if (m.getId().equals(idMeeting)) {
                mMeetings.remove(m);
                break;
            }
        }
    }
}
