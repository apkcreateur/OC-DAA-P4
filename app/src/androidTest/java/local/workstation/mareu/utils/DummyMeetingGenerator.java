package local.workstation.mareu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import local.workstation.mareu.model.Meeting;

public abstract class DummyMeetingGenerator {
    public static Calendar tomorrow = generateTomorrow();
    public static int tomorrowYear = tomorrow.get(Calendar.YEAR);
    public static int tomorrowMonth = tomorrow.get(Calendar.MONTH) + 1;
    public static int tomorrowDay = tomorrow.get(Calendar.DAY_OF_MONTH);
    public static int tomorrowStartAt = tomorrow.get(Calendar.HOUR_OF_DAY);

    public static Calendar tomorrowEnd = (Calendar) tomorrow.clone();
    public static int tomorrowEndAt = tomorrowStartAt + 1;

    private static Calendar yesterday = generateYesterday();
    public static int yesterdayYear = yesterday.get(Calendar.YEAR);
    public static int yesterdayMonth = yesterday.get(Calendar.MONTH) + 1;
    public static int yesterdayDay = yesterday.get(Calendar.DAY_OF_MONTH);

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

    private static Calendar fromTime(String time) {
        // convert string to time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        // get now
        Calendar datetime = Calendar.getInstance();
        // set time
        try {
            datetime.setTime(Objects.requireNonNull(sdf.parse(time)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return datetime;
    }

    private static Calendar generateTomorrow() {
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        tomorrow.set(Calendar.HOUR_OF_DAY, 9);
        tomorrow.set(Calendar.MINUTE, 0);

        return tomorrow;
    }

    private static Calendar generateYesterday() {
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);

        return yesterday;
    }

    private static List<String> DUMMY_MEETING_ROOMS = Arrays.asList(
            "Room 1", "Room 2", "Room 3", "Room 4", "Room 5",
            "Room 6", "Room 7", "Room 8", "Room 9", "Room 10");

    private static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("Room 1",
                    fromTime("14:00"),
                    fromTime("15:00"),
                    "Java vs Kotlin",
                    participants4),
            new Meeting("Room 2",
                    fromTime("14:15"),
                    fromTime("15:15"),
                    "I like Java",
                    participants1),
            new Meeting("Room 3",
                    fromTime("14:00"),
                    fromTime("15:00"),
                    "Project a world!",
                    participants2),
            new Meeting("Room 1",
                    fromTime("15:00"),
                    fromTime("16:00"),
                    "Java part 1",
                    participants4),
            new Meeting("Room 4",
                    fromTime("09:00"),
                    fromTime("11:00"),
                    "Project Foo!",
                    participants4),
            new Meeting("Room 3",
                    fromTime("15:00"),
                    fromTime("16:00"),
                    "Kotlin part 1",
                    participants1),
            new Meeting("Room 9",
                    fromTime("16:45"),
                    fromTime("17:45"),
                    "Job interview",
                    participants2),
            new Meeting("Room 2",
                    fromTime("16:00"),
                    fromTime("17:00"),
                    "Java part 2",
                    participants4),
            new Meeting("Room 5",
                    fromTime("13:30"),
                    fromTime("14:30"),
                    "job interview",
                    participants2),
            new Meeting("Room 5",
                    fromTime("11:45"),
                    fromTime("12:45"),
                    "Little big project",
                    participants3),
            new Meeting("Room 7",
                    fromTime("13:45"),
                    fromTime("14:45"),
                    "What else?",
                    participants3),
            new Meeting("Room 10",
                    fromTime("11:00"),
                    fromTime("12:00"),
                    "Once upon a time",
                    participants4)
    );

    /**
     * Generate list of meeting rooms
     * @return list of mmeeting rooms
     */
    public static List<String> generateRooms() {
        return new ArrayList<>(DUMMY_MEETING_ROOMS);
    }

    /**
     * Generate list of meetings
     * @return list of mmeetings
     */
    public static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}

