package local.workstation.mareu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Calendar;

import local.workstation.mareu.model.Meeting;

import static local.workstation.mareu.utils.Util.fromTime;

class FakeMeetingApiServiceTest {

    private FakeMeetingApiService mApi;
    private Integer mInitialCount;
    private Meeting mMeeting;

    @BeforeEach
    private void setUp() throws MeetingApiServiceException {
        mApi = new FakeMeetingApiService();

        mMeeting = new Meeting(
                "Salle 1",
                fromTime("14:00"),
                fromTime("15:00"),
                "sujet",
                Arrays.asList("p.roger@gmail.com", "s.ramen@gmail.fr"));

        // Initialize API with 1 Meeting
        mApi.addMeeting(mMeeting);

        mInitialCount = mApi.getMeetings().size();
    }

    @Test
    void addMeeting() {
        Meeting meeting = new Meeting(
                "Salle 2",
                Calendar.getInstance(),
                Calendar.getInstance(),
                "sujet",
                Arrays.asList("p.roger@gmail.com", "s.ramen@gmail.fr"));

        try {
            mApi.addMeeting(meeting);
        } catch (MeetingApiServiceException e) {
            // pass
        }
    }

    @ParameterizedTest
    @CsvSource({"14:00,15:00", "13:30,15:00", "14:00,15:30", "14:15,14:45", "13:30,14:30", "14:30,15:30"})
    void addMeetingFail(String start, String end) {
        final Meeting meeting = new Meeting(
                "Salle 1",
                fromTime(start),
                fromTime(end),
                "sujet",
                Arrays.asList("p.roger@gmail.com", "s.ramen@gmail.fr"));

        assertThrows(MeetingApiServiceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mApi.addMeeting(meeting);
            }
        });
    }

    @Test
    void getMeetings() {
        assertEquals((int) mInitialCount, mApi.getMeetings().size());
    }

    @Test
    void delMeeting() {
        mApi.delMeeting(mMeeting.getId());

        assertEquals((int) --mInitialCount, mApi.getMeetings().size());
    }
}
