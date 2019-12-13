package local.workstation.mareu.model;

import org.junit.jupiter.api.Test;
import org.hamcrest.CoreMatchers;

import static local.workstation.mareu.utils.Util.fromTime;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MeetingTest {
    private final String invalid_email = "test";

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
        List<String> participants  = new LinkedList<>(Arrays.asList(
                "p.roger@gmail.com",
                "s.ramen@gmail.fr",
                invalid_email));

        Meeting meeting = new Meeting("Salle 1",
                fromTime("11:00"),
                "Just a meeting",
                participants);

        // Remove invalid email address
        participants.remove(invalid_email);

        // Compare
        assertThat(participants, CoreMatchers.is(meeting.getParticipants()));
    }
}