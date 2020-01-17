package local.workstation.mareu.ui.meeting_list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import local.workstation.mareu.R;
import local.workstation.mareu.model.Meeting;
import local.workstation.mareu.service.MeetingApiService;
import local.workstation.mareu.view.ItemMeeting;

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
    private MeetingApiService mApiService;
    private List<Meeting> mMeetings;

    ItemMeetingRecyclerViewAdapter(Context context, MeetingApiService apiService) {
        mContext = context;
        mApiService = apiService;
        mMeetings = mApiService.getMeetings();
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
                new SimpleDateFormat("HH:ss").format(meeting.getStart().getTime()),
                meeting.getTopic()));

        holder.mDescriptionTextView.setText(desc);
        holder.mParticipantsTextView.setText(
                TextUtils.join(", ",
                meeting.getParticipants()));
        ((GradientDrawable)holder.mImageView.getBackground()).setColor(meeting.getColor());

        // Delete meeting
        // TODO Utiliser EventBus
        // notify datastatechange
        holder.mDeleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApiService.delMeeting(meeting.getId());
                mMeetings = mApiService.getMeetings();

                Toast.makeText(mContext, R.string.toast_text_delete_meeting, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }
}
