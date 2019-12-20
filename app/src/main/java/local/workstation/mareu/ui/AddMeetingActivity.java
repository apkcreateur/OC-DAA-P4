package local.workstation.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import local.workstation.mareu.R;

/**
 * Add new meeting
 */
public class AddMeetingActivity extends AppCompatActivity {

    private TextInputLayout mRoomNameTextInputLayout;
    private TextInputLayout mTopicTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        mRoomNameTextInputLayout = findViewById(R.id.room_name);
        mTopicTextInputLayout = findViewById(R.id.topic);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_meeting, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_meeting:
                add_meeting();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void add_meeting() {
        if (!validateTextInputLayout(mRoomNameTextInputLayout) | !validateTextInputLayout(mTopicTextInputLayout)) {
            Toast.makeText(this.getApplicationContext(), R.string.error_add_new_meeting, Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this.getApplicationContext(), R.string.add_new_meeting, Toast.LENGTH_LONG).show();
        // TODO serialize and return data OR upload to Fake Service API ?
        finish();
    }

    private boolean validateTextInputLayout(TextInputLayout inputValue) {
        String tmpValue = inputValue.getEditText().getText().toString().trim();

        if (tmpValue.isEmpty()) {
            inputValue.setError(getText(R.string.error_field_empty));
            return false;
        } else {
            inputValue.setError(null);
            return true;
        }
    }
}
