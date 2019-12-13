package local.workstation.mareu.utils;

import java.sql.Time;
import java.util.Date;

public class Util {
    public static Date fromTime(String time) {
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
}
