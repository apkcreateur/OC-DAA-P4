package local.workstation.mareu.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import local.workstation.mareu.R;

public class ItemMeeting extends RecyclerView.ViewHolder {

    public final ImageView mImageView;
    public final TextView mDescriptionTextView;
    public final TextView mParticipantsTextView;

    public ItemMeeting(@NonNull View itemView) {
        super(itemView);

        mImageView = itemView.findViewById(R.id.circle_item);
        mDescriptionTextView = itemView.findViewById(R.id.description_item);
        mParticipantsTextView = itemView.findViewById(R.id.participants_item);
    }
}
