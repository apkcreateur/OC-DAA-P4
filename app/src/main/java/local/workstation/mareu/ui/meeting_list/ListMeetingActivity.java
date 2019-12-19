package local.workstation.mareu.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import local.workstation.mareu.R;
import local.workstation.mareu.di.DI;
import local.workstation.mareu.service.MeetingApiService;
import local.workstation.mareu.ui.AddMeetingActivity;

/**
 * Display list of meetings (main activity)
 *
 * Use an fake meeting API Service to manage meetings.
 */
public class ListMeetingActivity extends AppCompatActivity {
    private static final int ADD_MEETING_ACTIVITY_REQUEST_CODE = 1;
    private MeetingApiService mApiService;
    private RecyclerView mRecyclerView;
    private ItemMeetingRecyclerViewAdapter mItemMeetingRecyclerViewAdapter;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);

        mApiService = DI.getApiService();

        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mItemMeetingRecyclerViewAdapter = new ItemMeetingRecyclerViewAdapter(this, mApiService);
        mRecyclerView.setAdapter(mItemMeetingRecyclerViewAdapter);

        mFloatingActionButton = findViewById(R.id.add);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addMeetingActivity = new Intent(ListMeetingActivity.this, AddMeetingActivity.class);
                startActivityForResult(addMeetingActivity, ADD_MEETING_ACTIVITY_REQUEST_CODE);
            }
        });
    }
}
