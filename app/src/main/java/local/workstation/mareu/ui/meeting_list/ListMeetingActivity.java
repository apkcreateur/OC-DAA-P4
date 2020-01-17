package local.workstation.mareu.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import local.workstation.mareu.R;
import local.workstation.mareu.di.DI;
import local.workstation.mareu.events.DeleteMeetingEvent;
import local.workstation.mareu.service.MeetingApiService;
import local.workstation.mareu.ui.AddMeetingActivity;

/**
 * Display list of meetings (main activity)
 *
 * Use an fake meeting API Service to manage meetings.
 */
public class ListMeetingActivity extends AppCompatActivity {
    public static MeetingApiService sApiService;

    @BindView(R.id.list) RecyclerView mRecyclerView;
    @BindView(R.id.add) FloatingActionButton mFloatingActionButton;
    private ItemMeetingRecyclerViewAdapter mItemMeetingRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);
        ButterKnife.bind(this);

        sApiService = DI.getApiService();

        init();

        mFloatingActionButton.setOnClickListener(v -> startActivity(new Intent(ListMeetingActivity.this, AddMeetingActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();

        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        sApiService.delMeeting(event.getMeetingId());

        Toast.makeText(getApplicationContext(), R.string.toast_text_delete_meeting, Toast.LENGTH_SHORT).show();

        init();
    }

    private void init() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mItemMeetingRecyclerViewAdapter = new ItemMeetingRecyclerViewAdapter(this, sApiService);
        mRecyclerView.setAdapter(mItemMeetingRecyclerViewAdapter);
    }

}
