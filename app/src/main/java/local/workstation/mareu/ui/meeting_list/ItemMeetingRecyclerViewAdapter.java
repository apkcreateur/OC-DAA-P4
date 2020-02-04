package local.workstation.mareu.ui.meeting_list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import local.workstation.mareu.R;
import local.workstation.mareu.events.DeleteMeetingEvent;
import local.workstation.mareu.model.Meeting;
import local.workstation.mareu.view.ItemMeeting;

import static local.workstation.mareu.ui.meeting_list.ListMeetingActivity.sApiService;

/**
 * Display list of meetings
 *
 * For each item:
 *   - displays:
 *     - description (room name - time - topic)
 *     - participants (comma separated list of emails)
 *   - offers a delete meeting button
 */
public class ItemMeetingRecyclerViewAdapter extends RecyclerView.Adapter<ItemMeeting> {
    private Context mContext;
    private List<Meeting> mMeetings;

    ItemMeetingRecyclerViewAdapter(Context context, Calendar date, String room) {
        mContext = context;
        mMeetings = sApiService.getMeetings(date, room);
    }

    @NonNull
    @Override
    public ItemMeeting onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_item, parent, false);
        return new ItemMeeting(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemMeeting holder, int position) {
        final Meeting meeting = mMeetings.get(position);

        @SuppressLint("SimpleDateFormat")
        String desc = TextUtils.join(" - ", Arrays.asList(
                meeting.getRoomName(),
                DateFormat.getTimeFormat(mContext).format(meeting.getStart().getTime()),
                meeting.getTopic()));

        holder.mDescriptionTextView.setText(desc);
        holder.mParticipantsTextView.setText(
                TextUtils.join(", ",
                meeting.getParticipants()));
        ((GradientDrawable)holder.mImageView.getBackground()).setColor(meeting.getColor());

        // Delete meeting
        holder.mDeleteImageButton.setOnClickListener(
                v -> EventBus.getDefault().post(new DeleteMeetingEvent(meeting.getId())));
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }
}
