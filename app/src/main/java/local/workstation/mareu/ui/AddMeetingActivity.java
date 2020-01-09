package local.workstation.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TimePicker;
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

import local.workstation.mareu.R;
import local.workstation.mareu.model.Meeting;
import local.workstation.mareu.service.MeetingApiServiceException;

import static local.workstation.mareu.ui.meeting_list.ListMeetingActivity.sApiService;

/**
 * Add new meeting
 */
public class AddMeetingActivity extends AppCompatActivity {

    private boolean mError;

    private List<String> mRooms;
    private TextInputLayout mRoomNameTextInputLayout;
    private AutoCompleteTextView mRoomNameAutoCompleteTextView;

    private TextInputLayout mTopicTextInputLayout;

    private TextInputLayout mEmailsTextInputLayout;
    private ChipGroup mEmailsChipGroup;
    private TextInputEditText mEmailsTextInputEditText;

    private TextInputLayout mDateTextInputLayout;
    private TextInputEditText mDateTextInputEditText;

    private TextInputLayout mStartTimeTextInputLayout, mEndTimeTextInputLayout;
    private TextInputEditText mStartTimeTextInputEditText, mEndTimeTextInputEditText;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Global -->
        setContentView(R.layout.activity_add_meeting);
        mError = false;
        // Global <--

        // Meeting room -->
        mRooms = sApiService.getRooms();
        mRoomNameTextInputLayout = findViewById(R.id.room_name_layout);
        mRoomNameAutoCompleteTextView = findViewById(R.id.room_name);

        mRoomNameAutoCompleteTextView.setAdapter(new ArrayAdapter<>(
                this,
                R.layout.room_item,
                mRooms));

        mRoomNameAutoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    mRoomNameAutoCompleteTextView.showDropDown();
                    return true;
                }
                return (event.getAction() == MotionEvent.ACTION_UP);
            }
        });
        // Meeting room <--

        // Meeting topic -->
        mTopicTextInputLayout = findViewById(R.id.topic);
        // Meeting topic <--

        // Meeting participants -->
        mEmailsTextInputLayout = findViewById(R.id.participants);
        mEmailsChipGroup = findViewById(R.id.emails_group);
        mEmailsTextInputEditText = findViewById(R.id.emails);

        mEmailsTextInputEditText.addTextChangedListener(new TextWatcher() {
            private boolean mNewEmail = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mNewEmail = false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count == 1) {
                    if(s.charAt(start) == ' ' || s.charAt(start) == ',' || s.charAt(start) == ';') {
                        mNewEmail = true;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mNewEmail) {
                    String value = s.toString().substring(0, s.length() - 1);
                    value = value.trim();
                    if (!value.isEmpty()) {
                        final Chip email = new Chip(AddMeetingActivity.this);
                        email.setText(value);
                        email.setCloseIconVisible(true);
                        email.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mEmailsChipGroup.removeView(email);
                            }
                        });
                        mEmailsChipGroup.addView(email);
                        mEmailsTextInputEditText.setText("");
                    }
                }
            }
        });
        // Meeting participants <--

        // Meeting date -->
        mDateTextInputLayout = findViewById(R.id.date_layout);
        mDateTextInputEditText = findViewById(R.id.date);

        mDateTextInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog mDatePickerDialog;

                mDatePickerDialog = new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar cal = Calendar.getInstance();
                                cal.set(year, month, dayOfMonth);
                                mDateTextInputEditText.setText(android.text.format.DateFormat.getDateFormat(getApplicationContext()).format(cal.getTime()));
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                mDatePickerDialog.show();
            }
        });
        // Meeting date <--

        // Meeting time -->
        mStartTimeTextInputLayout = findViewById(R.id.from_layout);
        mStartTimeTextInputEditText = findViewById(R.id.from);
        mEndTimeTextInputLayout = findViewById(R.id.to_layout);
        mEndTimeTextInputEditText = findViewById(R.id.to);

        mStartTimeTextInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar time = Calendar.getInstance();

                int unroundedMinutes = time.get(Calendar.MINUTE);
                int mod = unroundedMinutes % 15;
                time.add(Calendar.MINUTE, mod > 0 ? (15 - mod) : 0);

                TimePickerDialog mTimePickerDialog;

                mTimePickerDialog = new TimePickerDialog(AddMeetingActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Calendar tim = Calendar.getInstance();
                                tim.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                tim.set(Calendar.MINUTE, minute);
                                mStartTimeTextInputEditText.setText(android.text.format.DateFormat.getTimeFormat(getApplicationContext()).format(tim.getTime()));
                            }
                        },
                        time.get(Calendar.HOUR_OF_DAY),
                        time.get(Calendar.MINUTE),
                        DateFormat.is24HourFormat(AddMeetingActivity.this));
                mTimePickerDialog.show();
            }
        });

        mEndTimeTextInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar time = Calendar.getInstance();

                int unroundedMinutes = time.get(Calendar.MINUTE);
                int mod = unroundedMinutes % 15;
                time.add(Calendar.MINUTE, mod > 0 ? (15 - mod) : 0);

                TimePickerDialog mTimePickerDialog;

                mTimePickerDialog = new TimePickerDialog(AddMeetingActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Calendar tim = Calendar.getInstance();
                                tim.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                tim.set(Calendar.MINUTE, minute);
                                mEndTimeTextInputEditText.setText(android.text.format.DateFormat.getTimeFormat(getApplicationContext()).format(tim.getTime()));
                            }
                        },
                        time.get(Calendar.HOUR_OF_DAY),
                        time.get(Calendar.MINUTE),
                        DateFormat.is24HourFormat(AddMeetingActivity.this));
                mTimePickerDialog.show();
            }
        });
        // Meeting time <--
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_meeting, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_meeting) {
            add_meeting();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void add_meeting() {
        String roomName = validateTextInput(mRoomNameTextInputLayout);
        String topic = validateTextInput(mTopicTextInputLayout);
        Calendar date = validateDateInput(mDateTextInputLayout);
        Calendar start = validateTimeInput(mStartTimeTextInputLayout);
        Calendar end = validateTimeInput(mEndTimeTextInputLayout);
        List<String> participants = validateEmailInput(mEmailsTextInputLayout, mEmailsChipGroup);

        if (mError) {
            Toast.makeText(this.getApplicationContext(), R.string.error_add_new_meeting, Toast.LENGTH_LONG).show();
            mError = false;
        } else {
            if (date != null && start != null) {
                start.set(Calendar.YEAR, date.get(Calendar.YEAR));
                start.set(Calendar.MONTH, date.get(Calendar.MONTH));
                start.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
            }
            if (date != null && end != null) {
                end.set(Calendar.YEAR, date.get(Calendar.YEAR));
                end.set(Calendar.MONTH, date.get(Calendar.MONTH));
                end.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
            }

            try {
                sApiService.addMeeting(new Meeting(
                        roomName,
                        start,
                        end,
                        topic,
                        participants));

                Toast.makeText(this.getApplicationContext(), R.string.add_new_meeting, Toast.LENGTH_LONG).show();

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);

                finish();
            } catch (MeetingApiServiceException e) {
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
        }
        else {
            int error_count = 0;
            for (int i = 0; i < nb; i++) {
                Chip tmpEmail = (Chip) emails.getChildAt(i);
                String email = tmpEmail.getText().toString();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    error_count++;
                    // TODO doesn't working
                    tmpEmail.setChipStrokeColor(ColorStateList.valueOf(Color.RED));
                } else
                    lEmails.add(email);
            }
            if (error_count >= 1) {
                if (error_count == 1)
                    inputValue.setError(getText(R.string.error_invalid_email));
                else
                    inputValue.setError(getText(R.string.error_invalid_emails));
                mError = true;
                return null;
            }
        }
        if (mError)
            return null;
        else
            return lEmails;
    }
}
