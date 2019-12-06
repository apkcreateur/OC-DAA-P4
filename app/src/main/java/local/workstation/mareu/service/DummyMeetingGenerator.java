package local.workstation.mareu.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import local.workstation.mareu.model.Meeting;

public abstract class DummyMeetingGenerator {

    public static List<String> participants1 = Arrays.asList(
            "s.poli@yahoo.fr");

    public static List<String> participants2 = Arrays.asList(
            "p.roger@gmail.com",
            "s.ramen@gmail.fr");

    public static List<String> participants3 = Arrays.asList(
            "d.dout@yahoo.fr",
            "p.roger@gmail.com",
            "s.diego@gmail.fr");

    public static List<String> participants4 = Arrays.asList(
            "d.doret@gmail.com",
            "m.justal@outlook.fr",
            "f.tolken@gmail.fr",
            "s.decourt@gmail.fr");

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("Room 1",
                    Time.valueOf("14:00"),
                    "Java vs Kotlin",
                    participants4),
            new Meeting("Room 2",
                    Time.valueOf("14:15"),
                    "I like Java",
                    participants1),
            new Meeting("Room 3",
                    Time.valueOf("14:00"),
                    "Project a world!"),
            new Meeting("Room 1",
                    Time.valueOf("15:00"),
                    "Java part 1",
                    participants4),
            new Meeting("Room 4",
                    Time.valueOf("09:00"),
                    "Project Foo!",
                    participants4),
            new Meeting("Room 3",
                    Time.valueOf("15:00"),
                    "Kotlin part 1",
                    participants1),
            new Meeting("Room 9",
                    Time.valueOf("16:45"),
                    "Job interview",
                    participants2),
            new Meeting("Room 2",
                    Time.valueOf("16:00"),
                    "Java part 2",
                    participants4),
            new Meeting("Room 5",
                    Time.valueOf("13:30"),
                    "job interview",
                    participants2),
            new Meeting("Room 5",
                    Time.valueOf("11:45"),
                    "Little big project",
                    participants3),
            new Meeting("Room 7",
                    Time.valueOf("13:45"),
                    "What else?",
                    participants3),
            new Meeting("Room 10",
                    Time.valueOf("11:00"),
                    "Once upon a time",
                    participants4)
    );

    /**
     * Generate list of meetings
     * @return list of mmeetings
     */
    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
