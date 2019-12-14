package local.workstation.mareu.service;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import local.workstation.mareu.model.Meeting;

import static org.junit.Assert.assertEquals;

public class FakeMeetingApiServiceTest {

    private FakeMeetingApiService mApi;
    private Integer mInitialCount;
    private Meeting mMeeting;

    @Before
    public void setUp() {
        mApi = new FakeMeetingApiService();

        List<String> participants = Arrays.asList(
                "p.roger@gmail.com",
                "s.ramen@gmail.fr");
        mMeeting = new Meeting("Salle 1", new Date(), "sujet", participants);

        // Initialize API with 1 Meeting
        mApi.addMeeting(mMeeting);
        mInitialCount = mApi.getMeetings().size();
    }

    @Test
    public void addMeeting() {
        List<String> participants = Arrays.asList(
                "p.roger@gmail.com",
                "s.ramen@gmail.fr");
        Meeting meeting = new Meeting("Salle 2", new Date(), "sujet", participants);

        mApi.addMeeting(meeting);

        assertEquals((int) ++mInitialCount, mApi.getMeetings().size());
    }

    @Test
    public void getMeetings() {
        assertEquals((int) mInitialCount, mApi.getMeetings().size());
    }

    @Test
    public void delMeeting() {
        mApi.delMeeting(mMeeting.getId());

        assertEquals((int) --mInitialCount, mApi.getMeetings().size());
    }
}
