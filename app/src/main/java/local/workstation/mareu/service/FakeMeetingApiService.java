package local.workstation.mareu.service;

import java.util.ArrayList;
import java.util.List;

import local.workstation.mareu.model.Meeting;

/**
 * Dummy mock for the Meeting Api Service
 */
public class FakeMeetingApiService implements MeetingApiService {
    private List<Meeting> mMeetings = new ArrayList<>();
    private List<String> mRooms = new ArrayList<>();

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
        for (String r: mRooms) {
            if (r.equals(room)) {
                mRooms.remove(r);
                break;
            }
        }
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
