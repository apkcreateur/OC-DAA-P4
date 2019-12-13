package local.workstation.mareu.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Meeting {
    private static Integer sLastId = 0;

    private Integer mId;
    private String mRoomName;
    private Date mDatetime;
    private String mTopic;
    private List<String> mParticipants;

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
        mParticipants = participants;
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
}
