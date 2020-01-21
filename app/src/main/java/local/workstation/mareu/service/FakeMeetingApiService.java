package local.workstation.mareu.service;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import local.workstation.mareu.model.Meeting;

import static local.workstation.mareu.utils.MeetingUtil.getMeetingsAfterOrSameDate;
import static local.workstation.mareu.utils.MeetingUtil.getMeetingsBeforeOrSameDate;
import static local.workstation.mareu.utils.MeetingUtil.getMeetingsMatchDate;

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
    public List<Meeting> getMeetings() {
        return mMeetings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeetingsFilteredByDate(Calendar date, DateFilter filterType) {
        Log.d("TAG", "get meetings filtered by date");
        switch (filterType) {
            case BEFORE:
                Log.d("TAG", "getMeetingsBeforeDate");
                return getMeetingsBeforeOrSameDate(date, mMeetings);
            case MATCH:
                Log.d("TAG", "getMeetingsMatchDate");
                return getMeetingsMatchDate(date, mMeetings);
            case AFTER:
                Log.d("TAG", "getMeetingsAfterDate");
                return getMeetingsAfterOrSameDate(date, mMeetings);
            default:
                Log.d("TAG", "getMeetings");
                return getMeetings();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMeeting(Meeting meeting) throws MeetingApiServiceException {
        // Check if the meeting room is free
        for (Meeting m: mMeetings) {
            if (meeting.getRoomName().equals(m.getRoomName())) {
                if (meeting.getStart().equals(m.getStart()) || meeting.getEnd().equals(m.getEnd()))
                    throw new MeetingApiServiceException();
                if (meeting.getStart().after(m.getStart()) && meeting.getStart().before(m.getEnd()))
                    throw new MeetingApiServiceException();
                if (meeting.getEnd().after(m.getStart()) && meeting.getEnd().before(m.getEnd()))
                    throw new MeetingApiServiceException();
            }
        }
        // Add meeting
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
