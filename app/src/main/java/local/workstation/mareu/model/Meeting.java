package local.workstation.mareu.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class Meeting {
    private static Integer sLastId = 0;
    private static Random sRandom = new Random();

    private Integer mId;
    private String mRoomName;
    private Date mDatetime;
    private String mTopic;
    private List<String> mParticipants;
    private Integer mColor;

    /**
     * Constructor
     * @param topic topic of the meeting
     * @param roomName name of the meeting room
     * @param datetime meeting time
     */
    public Meeting(String roomName, Date datetime, String topic) {
        this(roomName, datetime, topic, new ArrayList<String>());
    }

    /**
     * Constructor
     * @param topic topic of the meeting
     * @param roomName name of the meeting room
     * @param datetime meeting time
     * @param participants list of email addresses of meeting participants
     */
    public Meeting(String roomName, Date datetime, String topic, List<String> participants) {
        mId = ++sLastId;
        mRoomName = roomName;
        mDatetime = datetime;
        mTopic = topic;
        mParticipants = new ArrayList<>();
        this.setParticipants(participants);
        // Generate random color
        mColor = Color.argb(
                sRandom.nextInt(255),
                sRandom.nextInt(255),
                sRandom.nextInt(255),
                sRandom.nextInt(255));
    }

    public Integer getId() {
        return mId;
    }

    public String getRoomName() {
        return mRoomName;
    }

    public Date getDatetime() {
        return mDatetime;
    }

    public String getTopic() {
        return mTopic;
    }

    public List<String> getParticipants() {
        return mParticipants;
    }

    public Integer getColor() {
        return mColor;
    }

    private void setParticipants(List<String> participants) {
        String email_pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(email_pattern, Pattern.CASE_INSENSITIVE);

        for (String participant : participants) {
            if (pattern.matcher(participant).matches()) {
                mParticipants.add(participant);
            }
        }
    }
}
