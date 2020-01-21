package local.workstation.mareu.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import static local.workstation.mareu.service.MeetingApiService.DateFilter;
import static local.workstation.mareu.service.MeetingApiService.DateFilter.AFTER;
import static local.workstation.mareu.service.MeetingApiService.DateFilter.BEFORE;
import static local.workstation.mareu.service.MeetingApiService.DateFilter.MATCH;

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

        Log.d("TAG", "onCreate");
        mFloatingActionButton.setOnClickListener(v -> startActivity(new Intent(ListMeetingActivity.this, AddMeetingActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("TAG", "before init onResume");
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
        switch (item.getItemId()) {
            case R.id.filter:
                return true;
            case R.id.filter_raz:
                init();
                return true;
            case R.id.filter_before_date:
                performDateFilter(BEFORE);
                return true;
            case R.id.filter_match_date:
                performDateFilter(MATCH);
                return true;
            case R.id.filter_after_date:
                performDateFilter(AFTER);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void performDateFilter(DateFilter filterType) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog mDatePickerDialog;

        mDatePickerDialog = new DatePickerDialog(ListMeetingActivity.this,
                (view, year, month, dayOfMonth) -> {
                    Calendar cal = Calendar.getInstance();
                    cal.set(year, month, dayOfMonth);

                    init(cal, filterType);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        mDatePickerDialog.show();
    }

    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        sApiService.delMeeting(event.getMeetingId());

        Toast.makeText(getApplicationContext(), R.string.toast_text_delete_meeting, Toast.LENGTH_SHORT).show();

        init();
    }

    private void init() {
        Log.d("TAG", "init");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mItemMeetingRecyclerViewAdapter = new ItemMeetingRecyclerViewAdapter(this, sApiService);
        mRecyclerView.setAdapter(mItemMeetingRecyclerViewAdapter);
    }

    private void init(Calendar date, DateFilter filterType) {
        Log.d("TAG", "init with date");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mItemMeetingRecyclerViewAdapter = new ItemMeetingRecyclerViewAdapter(this, sApiService, date, filterType);
        mRecyclerView.setAdapter(mItemMeetingRecyclerViewAdapter);
    }

}
