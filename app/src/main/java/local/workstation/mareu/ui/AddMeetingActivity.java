package local.workstation.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;
import local.workstation.mareu.R;
import local.workstation.mareu.model.Meeting;
import local.workstation.mareu.service.MeetingApiServiceException;

import static local.workstation.mareu.tool.Validator.validEmail;
import static local.workstation.mareu.ui.meeting_list.ListMeetingActivity.sApiService;

/**
 * Add new meeting
 */
public class AddMeetingActivity extends AppCompatActivity {
    private boolean mError;

    private List<String> mRooms;
    @BindView(R.id.room_name_layout) TextInputLayout mRoomNameTextInputLayout;
    @BindView(R.id.room_name) AutoCompleteTextView mRoomNameAutoCompleteTextView;

    @BindView(R.id.topic_layout) TextInputLayout mTopicTextInputLayout;
    @BindView(R.id.topic) TextInputEditText mTopicTextInputEditText;

    @BindView(R.id.date_layout) TextInputLayout mDateTextInputLayout;
    @BindView(R.id.date) TextInputEditText mDateTextInputEditText;
    @BindView(R.id.from_layout) TextInputLayout mStartTimeTextInputLayout;
    @BindView(R.id.from) TextInputEditText mStartTimeTextInputEditText;
    @BindView(R.id.to_layout) TextInputLayout mEndTimeTextInputLayout;
    @BindView(R.id.to) TextInputEditText mEndTimeTextInputEditText;

    @BindView(R.id.participants) TextInputLayout mEmailsTextInputLayout;
    @BindView(R.id.emails_group) ChipGroup mEmailsChipGroup;
    @BindView(R.id.emails) TextInputEditText mEmailsTextInputEditText;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Global -->
        setContentView(R.layout.activity_add_meeting);
        ButterKnife.bind(this);
        mError = false;
        // Global <--

        // Meeting room -->
        mRooms = sApiService.getRooms();

        mRoomNameAutoCompleteTextView.setAdapter(new ArrayAdapter<>(
                this,
                R.layout.room_item,
                mRooms));
        // Meeting room <--

        // Meeting participants -->
        mEmailsTextInputEditText.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String value = Objects.requireNonNull(mEmailsTextInputEditText.getText()).toString().trim();

                    if (!value.isEmpty()) {
                        if (!validEmail(value)) {
                            mEmailsTextInputLayout.setError(getText(R.string.error_invalid_email));

                            return false;
                        } else {
                            final Chip email = new Chip(AddMeetingActivity.this);
                            email.setText(value);
                            email.setCloseIconVisible(true);
                            email.setOnCloseIconClickListener(v1 -> mEmailsChipGroup.removeView(email));

                            mEmailsChipGroup.addView(email);
                            mEmailsTextInputEditText.setText("");
                            mEmailsTextInputLayout.setError(null);

                            return true;
                        }
                    }
                }
            }
            return false;
        });
        // Meeting participants <--
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_meeting, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_meeting) {
            addMeeting();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnTouch(R.id.room_name)
    boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            mRoomNameAutoCompleteTextView.showDropDown();
            return true;
        }
        return (event.getAction() == MotionEvent.ACTION_UP);
    }

    @OnClick(R.id.date)
    void displayDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog mDatePickerDialog;

        mDatePickerDialog = new DatePickerDialog(AddMeetingActivity.this,
                (view, year, month, dayOfMonth) -> {
                    Calendar cal = Calendar.getInstance();
                    cal.set(year, month, dayOfMonth);
                    mDateTextInputEditText.setText(DateFormat.getDateFormat(getApplicationContext()).format(cal.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        mDatePickerDialog.show();
    }

    @OnClick({R.id.from, R.id.to})
    void displayTimePicker(View v) {
        final int id = v.getId();

        Calendar time = Calendar.getInstance();
        TimePickerDialog mTimePickerDialog;

        int roundedMinutes = time.get(Calendar.MINUTE) % 15;
        time.add(Calendar.MINUTE, roundedMinutes > 0 ? (15 - roundedMinutes) : 0);

        mTimePickerDialog = new TimePickerDialog(AddMeetingActivity.this,
                (view, hourOfDay, minute) -> {
                    Calendar tim = Calendar.getInstance();
                    tim.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    tim.set(Calendar.MINUTE, minute);
                    if (id == R.id.from)
                        mStartTimeTextInputEditText.setText(DateFormat.getTimeFormat(getApplicationContext()).format(tim.getTime()));
                    else if (id == R.id.to)
                        mEndTimeTextInputEditText.setText(DateFormat.getTimeFormat(getApplicationContext()).format(tim.getTime()));
                },
                time.get(Calendar.HOUR_OF_DAY),
                time.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(AddMeetingActivity.this));

        mTimePickerDialog.show();
    }

    @OnTextChanged(R.id.emails)
    void afterTextChanged(Editable s) {
        String value = s.toString();

        if (value.length() > 0) {
            char lastChar = value.charAt(value.length() - 1);

            if (lastChar == ' ' || lastChar == ',') {
                value = value.substring(0, value.length() - 1);
                value = value.trim();
                if (!value.isEmpty()) {
                    if (!validEmail(value)) {
                        mEmailsTextInputLayout.setError(getText(R.string.error_invalid_email));
                    } else {
                        final Chip email = new Chip(AddMeetingActivity.this);
                        email.setText(value);
                        email.setCloseIconVisible(true);
                        email.setOnCloseIconClickListener(v -> mEmailsChipGroup.removeView(email));

                        mEmailsChipGroup.addView(email);
                        mEmailsTextInputEditText.setText("");
                        mEmailsTextInputLayout.setError(null);
                    }
                }
            }
        }
    }

    private void addMeeting() {
        String roomName = validateTextInput(mRoomNameTextInputLayout);
        String topic = validateTextInput(mTopicTextInputLayout);
        Calendar date = validateDateInput(mDateTextInputLayout);
        Calendar start = validateTimeInput(mStartTimeTextInputLayout);
        Calendar end = validateTimeInput(mEndTimeTextInputLayout);
        List<String> participants = validateEmailInput(mEmailsTextInputLayout, mEmailsChipGroup);

        if (date != null && start != null && end != null) {
            start.set(Calendar.YEAR, date.get(Calendar.YEAR));
            start.set(Calendar.MONTH, date.get(Calendar.MONTH));
            start.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));

            end.set(Calendar.YEAR, date.get(Calendar.YEAR));
            end.set(Calendar.MONTH, date.get(Calendar.MONTH));
            end.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));

            if (start.compareTo(end) >= 0) {
                mEndTimeTextInputLayout.setError(getText(R.string.error_time_comparison));
                mError = true;
            }
        }

        if (mError) {
            Toast.makeText(this.getApplicationContext(), R.string.error_add_new_meeting, Toast.LENGTH_LONG).show();
            mError = false;
        } else {
            try {
                sApiService.addMeeting(new Meeting(
                        roomName,
                        start,
                        end,
                        topic,
                        participants));

                Toast.makeText(this.getApplicationContext(), R.string.add_new_meeting, Toast.LENGTH_LONG).show();

                finish();
            } catch (MeetingApiServiceException e) {
                mRoomNameTextInputLayout.setError(getText(R.string.error_meeting_room_already_booked));
                Toast.makeText(this.getApplicationContext(), R.string.error_meeting_room_already_booked, Toast.LENGTH_LONG).show();
                mError = false;
            }
        }
    }

    private String validateTextInput(TextInputLayout inputValue) {
        String tmpValue = Objects.requireNonNull(inputValue.getEditText()).getText().toString().trim();

        if (tmpValue.isEmpty()) {
            inputValue.setError(getText(R.string.error_empty_field));
            mError = true;
            return null;
        } else {
            inputValue.setError(null);
            return tmpValue;
        }
    }

    private Calendar validateDateInput(TextInputLayout inputValue) {
        String tmpValue = Objects.requireNonNull(inputValue.getEditText()).getText().toString().trim();

        if (tmpValue.isEmpty()) {
            inputValue.setError(getText(R.string.error_empty_field));
            mError = true;
            return null;
        } else {
            // valid date format ?
            try {
                Date dDate = android.text.format.DateFormat.getDateFormat(getApplicationContext()).parse(tmpValue);
                Calendar date = Calendar.getInstance();
                date.setTime(Objects.requireNonNull(dDate));
                inputValue.setError(null);
                return date;
            } catch (ParseException e) {
                inputValue.setError(getText(R.string.error_invalid_date_format));
                mError = true;
                return null;
            }
        }
    }

    private Calendar validateTimeInput(TextInputLayout inputValue) {
        String tmpValue = Objects.requireNonNull(inputValue.getEditText()).getText().toString().trim();

        if (tmpValue.isEmpty()) {
            inputValue.setError(getText(R.string.error_empty_field));
            mError = true;
            return null;
        } else {
            // valid time format ?
            try {
                Date dTime = android.text.format.DateFormat.getTimeFormat(getApplicationContext()).parse(tmpValue);
                Calendar Time = Calendar.getInstance();
                Time.setTime(Objects.requireNonNull(dTime));
                inputValue.setError(null);
                return Time;
            } catch (ParseException e) {
                inputValue.setError(getText(R.string.error_invalid_time_format));
                mError = true;
                return null;
            }
        }
    }

    private List<String> validateEmailInput(TextInputLayout inputValue, ChipGroup emails) {
        inputValue.setError(null);
        int nb = emails.getChildCount();
        List<String> lEmails = new ArrayList<>();

        if (nb == 0) {
            inputValue.setError(getText(R.string.error_empty_field));
            mError = true;
            return null;
        } else {
            for (int i = 0; i < nb; i++) {
                Chip tmpEmail = (Chip) emails.getChildAt(i);
                String email = tmpEmail.getText().toString();

                    lEmails.add(email);
            }
            return lEmails;
        }
    }
}
