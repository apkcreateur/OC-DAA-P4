package local.workstation.mareu.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import local.workstation.mareu.model.Meeting;

import static local.workstation.mareu.utils.CalendarUtil.afterOrSameDate;
import static local.workstation.mareu.utils.CalendarUtil.beforeOrSameDate;
import static local.workstation.mareu.utils.CalendarUtil.sameDate;

public class MeetingUtil {

    /**
     * Return ordered meetings list filter by date
     * @param date selected date before which filter
     * @param meetings meetings list to filter
     * @return filtered meetings list
     */
    public static List<Meeting> getMeetingsBeforeOrSameDate(Calendar date, List<Meeting> meetings) {
        List<Meeting> tmp = new ArrayList<>();

        for (Meeting m: meetings)
            if (beforeOrSameDate(m.getStart(), date))
                tmp.add(m);

        Collections.sort(tmp);

        return tmp;
    }

    /**
     * Return ordered meetings list filter by date
     * @param date selected date
     * @param meetings meetings list to filter
     * @return filtered meetings list
     */
    public static List<Meeting> getMeetingsMatchDate(Calendar date, List<Meeting> meetings) {
        List<Meeting> tmp = new ArrayList<>();

        for (Meeting m: meetings)
            if (sameDate(m.getStart(), date))
                tmp.add(m);

        Collections.sort(tmp);

        return tmp;
    }

    /**
     * Return ordered meetings list filter by date
     * @param date selected date from which to filter
     * @param meetings meetings list to filter
     * @return filtered meetings list
     */
    public static List<Meeting> getMeetingsAfterOrSameDate(Calendar date, List<Meeting> meetings) {
        List<Meeting> tmp = new ArrayList<>();

        for (Meeting m: meetings)
            if (afterOrSameDate(m.getStart(), date))
                tmp.add(m);

        Collections.sort(tmp);

        return tmp;
    }

    /**
     * Return ordered meetings list filter by room name
     * @param roomName selected room name
     * @param meetings meetings list to filter
     * @return filtered meetings list
     */
    public static List<Meeting> getMeetingsMatchRoomName(String roomName, List<Meeting> meetings) {
        List<Meeting> tmp = new ArrayList<>();

        for (Meeting m: meetings)
            if (m.getRoomName().trim().equals(roomName.trim()))
                tmp.add(m);

        Collections.sort(tmp);

        return tmp;
    }
}
