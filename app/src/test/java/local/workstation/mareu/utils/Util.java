package local.workstation.mareu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class Util {
    public static Calendar fromTime(String time) {
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
}
