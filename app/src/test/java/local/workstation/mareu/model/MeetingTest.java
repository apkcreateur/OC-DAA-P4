package local.workstation.mareu.model;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static local.workstation.mareu.service.DummyMeetingGenerator.fromTime;
import static org.junit.Assert.*;

public class MeetingTest {
    @Test
    public void givenValidEmailAddresses_whenInstatiateMeeting_thenGetValidParticipants() {
        List<String> participants  = Arrays.asList(
                "p.roger@gmail.com",
                "s.ramen@gmail.fr");

        Meeting meeting = new Meeting("Salle 1",
                fromTime("11:00"),
                "Just a meeting",
                participants);

        assertThat(participants, CoreMatchers.is(meeting.getParticipants()));
    }

    @Test
    public void givenInvalidEmailAddresses_whenInstatiateMeeting_thenGetValidParticipants() {
        // Valid email addresses
        List<String> participants  = Arrays.asList(
                "p.roger@gmail.com",
                "s.ramen@gmail.fr",
                "tests");

        Meeting meeting = new Meeting("Salle 1",
                fromTime("11:00"),
                "Just a meeting",
                participants);

        // Remove invalid email address
        participants.remove("test");

        // Compare
        assertThat(participants, CoreMatchers.is(meeting.getParticipants()));
    }
}