package local.workstation.mareu.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import local.workstation.mareu.R;
import local.workstation.mareu.di.DI;
import local.workstation.mareu.events.DeleteMeetingEvent;
import local.workstation.mareu.service.MeetingApiService;
import local.workstation.mareu.ui.AddMeetingActivity;
import local.workstation.mareu.ui.fragments.FilterDialogFragment;

import static local.workstation.mareu.service.MeetingApiService.DateFilter;

/**
 * Display list of meetings (main activity)
 *
 * Use an fake meeting API Service to manage meetings.
 */
public class ListMeetingActivity extends AppCompatActivity implements FilterDialogFragment.OnButtonClickedListener {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filter) {
            performFilter();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private  void performFilter() {
        FilterDialogFragment filterDialog = new FilterDialogFragment(sApiService.getRooms());
        filterDialog.show(getSupportFragmentManager(), "filter");
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

    private void init(Calendar date, DateFilter filterType) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mItemMeetingRecyclerViewAdapter = new ItemMeetingRecyclerViewAdapter(this, sApiService, date, filterType);
        mRecyclerView.setAdapter(mItemMeetingRecyclerViewAdapter);
    }

    private void init(Calendar date) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mItemMeetingRecyclerViewAdapter = new ItemMeetingRecyclerViewAdapter(this, sApiService, date);
        mRecyclerView.setAdapter(mItemMeetingRecyclerViewAdapter);
    }

    private void init(String room) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mItemMeetingRecyclerViewAdapter = new ItemMeetingRecyclerViewAdapter(this, sApiService, room);
        mRecyclerView.setAdapter(mItemMeetingRecyclerViewAdapter);
    }

    private void init(Calendar date, String room) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mItemMeetingRecyclerViewAdapter = new ItemMeetingRecyclerViewAdapter(this, sApiService, date, room);
        mRecyclerView.setAdapter(mItemMeetingRecyclerViewAdapter);
    }

    @Override
    public void onButtonClicked(Calendar date, String room, boolean reset) {
        if (reset)
            init();
        else if (date != null && room.isEmpty())
            init(date);
        else if (date == null && ! room.isEmpty())
            init(room);
        else if (date != null && ! room.isEmpty())
            init(date, room);
    }
}
