package local.workstation.mareu.ui.meeting_list;

import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.List;

import local.workstation.mareu.R;
import local.workstation.mareu.model.Meeting;
import local.workstation.mareu.view.ItemMeeting;

public class ItemMeetingRecyclerViewAdapter extends RecyclerView.Adapter<ItemMeeting> {
    private List<Meeting> mMeetings;

    ItemMeetingRecyclerViewAdapter(List<Meeting> meetings) {
        mMeetings = meetings;
    }

    @NonNull
    @Override
    public ItemMeeting onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_item, parent, false);
        return new ItemMeeting(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemMeeting holder, int position) {
        Meeting meeting = mMeetings.get(position);
        String desc = TextUtils.join(" - ", Arrays.asList(
                meeting.getRoomName(),
                DateFormat.getTimeInstance(DateFormat.SHORT).format(meeting.getDatetime()),
                meeting.getTopic()));

        holder.mDescriptionTextView.setText(desc);
        holder.mParticipantsTextView.setText(
                TextUtils.join(", ",
                meeting.getParticipants()));
        ((GradientDrawable)holder.mImageView.getBackground()).setColor(meeting.getColor());
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }
}
