package local.workstation.mareu.utils;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import local.workstation.mareu.model.Meeting;

public abstract class DummyMeetingGenerator {
    public static int ITEMS_COUNT = 12;
    public static final int EXPECTED_ITEM_POSITION = 0;
    public static final int DELETE_ITEM_POSITION = 3;
    public static final String EXPECTED_DESCRIPTION = "Room 1 - 14:00 - Java vs Kotlin";
    public static final String EXPECTED_PARTICIPANTS =
            "d.doret@gmail.com, m.justal@outlook.fr, f.tolken@gmail.fr, s.decourt@gmail.fr";

    private static List<String> participants1 = Collections.singletonList(
            "s.poli@yahoo.fr");

    private static List<String> participants2 = Arrays.asList(
            "p.roger@gmail.com",
            "s.ramen@gmail.fr");

    private static List<String> participants3 = Arrays.asList(
            "d.dout@yahoo.fr",
            "p.roger@gmail.com",
            "s.diego@gmail.fr");

    private static List<String> participants4 = Arrays.asList(
            "d.doret@gmail.com",
            "m.justal@outlook.fr",
            "f.tolken@gmail.fr",
            "s.decourt@gmail.fr");

    private static Date fromTime(String time) {
        // convert string to time
        Time tmp = Time.valueOf(time + ":00");
        // get now
        Date date = new Date();
        // set time
        date.setHours(tmp.getHours());
        date.setMinutes(tmp.getMinutes());
        date.setSeconds(0);

        return date;
    }

    private static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("Room 1",
                    fromTime("14:00"),
                    "Java vs Kotlin",
                    participants4),
            new Meeting("Room 2",
                    fromTime("14:15"),
                    "I like Java",
                    participants1),
            new Meeting("Room 3",
                    fromTime("14:00"),
                    "Project a world!",
                    participants2),
            new Meeting("Room 1",
                    fromTime("15:00"),
                    "Java part 1",
                    participants4),
            new Meeting("Room 4",
                    fromTime("09:00"),
                    "Project Foo!",
                    participants4),
            new Meeting("Room 3",
                    fromTime("15:00"),
                    "Kotlin part 1",
                    participants1),
            new Meeting("Room 9",
                    fromTime("16:45"),
                    "Job interview",
                    participants2),
            new Meeting("Room 2",
                    fromTime("16:00"),
                    "Java part 2",
                    participants4),
            new Meeting("Room 5",
                    fromTime("13:30"),
                    "job interview",
                    participants2),
            new Meeting("Room 5",
                    fromTime("11:45"),
                    "Little big project",
                    participants3),
            new Meeting("Room 7",
                    fromTime("13:45"),
                    "What else?",
                    participants3),
            new Meeting("Room 10",
                    fromTime("11:00"),
                    "Once upon a time",
                    participants4)
    );

    /**
     * Generate list of meetings
     * @return list of mmeetings
     */
    public static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}

