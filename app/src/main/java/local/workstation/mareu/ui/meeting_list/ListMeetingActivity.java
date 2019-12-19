package local.workstation.mareu.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import local.workstation.mareu.R;
import local.workstation.mareu.di.DI;
import local.workstation.mareu.service.MeetingApiService;

public class ListMeetingActivity extends AppCompatActivity {
    private MeetingApiService mApiService;
    private RecyclerView mRecyclerView;
    private ItemMeetingRecyclerViewAdapter mItemMeetingRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);

        mApiService = DI.getApiService();

        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mItemMeetingRecyclerViewAdapter = new ItemMeetingRecyclerViewAdapter(this, mApiService);

        mRecyclerView.setAdapter(mItemMeetingRecyclerViewAdapter);
    }
}
