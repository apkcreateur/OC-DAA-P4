package local.workstation.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import local.workstation.mareu.R;

/**
 * Add new meeting
 */
public class AddMeetingActivity extends AppCompatActivity {

    private TextInputLayout mRoomNameTextInputLayout;
    private TextInputLayout mTopicTextInputLayout;

    private Button mAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        mRoomNameTextInputLayout = findViewById(R.id.room_name);
        mTopicTextInputLayout = findViewById(R.id.topic);

        mAddButton = findViewById(R.id.add_button);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateTextInputLayout(mRoomNameTextInputLayout) | !validateTextInputLayout(mTopicTextInputLayout)) {
                    Toast.makeText(v.getContext(), R.string.error_add_new_meeting, Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(v.getContext(), R.string.add_new_meeting, Toast.LENGTH_LONG).show();
                // TODO returns serialized data
                finish();
            }
        });
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
