package local.workstation.mareu.ui;

import android.view.KeyEvent;

import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import local.workstation.mareu.R;
import local.workstation.mareu.ui.meeting_list.ListMeetingActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static local.workstation.mareu.utils.ChipValueAssertion.matchesChipTextAtPosition;
import static local.workstation.mareu.utils.TextInputLayoutErrorValueAssertion.matchesErrorText;
import static local.workstation.mareu.utils.TextInputLayoutNoErrorValueAssertion.matchesNoErrorText;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

@RunWith(Enclosed.class)
public class AddMeetingActivityTest {

    public static class ComponentSingleTests {
        private static final String sRoomName = "Room 3";
        private static final String sTopic = "New Meeting!";
        private static final String sEmail1 = "mickael@gmail.com";
        private static final String sEmail2 = "john@outlook.com";
        private static final String sEmailInvalid = "test";

        /**
         * Define rules to initialize AddMeetingActivity
         */
        @Rule
        public ActivityTestRule<ListMeetingActivity> mActivityRuleInit =
                new ActivityTestRule<>(ListMeetingActivity.class);

        @Rule
        public ActivityTestRule<AddMeetingActivity> mActivityRule =
                new ActivityTestRule<>(AddMeetingActivity.class);

        /**
         * Initialize AddMeetingActivity and check that it is not empty
         */
        @Before
        public void setUp() {
            AddMeetingActivity activity = mActivityRule.getActivity();
            assertThat(activity, notNullValue());
        }

        /**
         * Check selected entry of the Room name field
         */
        @Test
        public void givenRoom_whenClickToAddMeeting_thenGetRoomWithoutError() {
            onView(withId(R.id.room_name)).perform(click());
            onView(withText(sRoomName))
                    .inRoot(RootMatchers.isPlatformPopup())
                    .perform(click());

            onView(withId(R.id.action_add_meeting)).perform(click());
            onView(withId(R.id.room_name)).check(matches(withText(sRoomName)));
            onView(withId(R.id.room_name_layout)).check(matchesNoErrorText());
        }

        /**
         * Check the correct entry of the Topic field
         */
        @Test
        public void givenTopic_whenClickToAddMeeting_thenGetTopicWithoutError() {
            onView(withId(R.id.topic)).perform(typeText(sTopic));
            onView(withId(R.id.action_add_meeting)).perform(click());

            onView(withId(R.id.topic)).check(matches(withText(sTopic)));
            onView(withId(R.id.topic)).check(matches(withHint(R.string.meeting_topic)));
            onView(withId(R.id.topic_layout)).check(matchesNoErrorText());
        }

        /**
         * Check the correct entry of the Emails field (press Enter key)
         */
        @Test
        public void givenValidEmail_whenPressEnterKey_thenGetEmailWithoutError() {
            onView(withId(R.id.emails)).perform(typeText(sEmail1));
            onView(withId(R.id.emails)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

            onView(withId(R.id.emails_group)).check(matchesChipTextAtPosition(1, sEmail1));
            onView(withId(R.id.emails)).check(matches(withHint(R.string.list_of_participants)));
            onView(withId(R.id.participants)).check(matchesNoErrorText());
        }

        /**
         * Check the correct entries of the Emails field (press Enter key)
         */
        @Test
        public void givenTwoValidEmail_whenPressEnterKey_thenGetEmailsWithoutError() {
            onView(withId(R.id.emails)).perform(typeText(sEmail1));
            onView(withId(R.id.emails)).perform(pressKey(KeyEvent.KEYCODE_ENTER));
            onView(withId(R.id.emails)).perform(typeText(sEmail2));
            onView(withId(R.id.emails)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

            onView(withId(R.id.emails_group)).check(matchesChipTextAtPosition(1, sEmail1));
            onView(withId(R.id.emails_group)).check(matchesChipTextAtPosition(2, sEmail2));
            onView(withId(R.id.emails)).check(matches(withHint(R.string.list_of_participants)));
            onView(withId(R.id.participants)).check(matchesNoErrorText());
        }

        /**
         * Check invalid entry of the Emails field (press Enter key)
         */
        @Test
        public void givenInvalidEmail_whenPressEnterKey_thenGetErrorMessage() {
            AddMeetingActivity activity = mActivityRule.getActivity();

            onView(withId(R.id.emails)).perform(typeText(sEmailInvalid));
            onView(withId(R.id.emails)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

            onView(withId(R.id.participants))
                    .check(matchesErrorText(activity.getString(R.string.error_invalid_email)));
            onView(withId(R.id.emails)).check(matches(withHint(R.string.list_of_participants)));
        }

        /**
         * Check invalid second entry of the Emails field (press Enter key)
         */
        @Test
        public void givenOneValidEmailAndOneInvalidEmail_whenPressEnterKey_thenGetMessageError() {
            AddMeetingActivity activity = mActivityRule.getActivity();

            onView(withId(R.id.emails)).perform(typeText(sEmail1));
            onView(withId(R.id.emails)).perform(pressKey(KeyEvent.KEYCODE_ENTER));
            onView(withId(R.id.emails)).perform(typeText(sEmailInvalid));
            onView(withId(R.id.emails)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

            onView(withId(R.id.emails_group)).check(matchesChipTextAtPosition(1, sEmail1));
            onView(withId(R.id.participants))
                    .check(matchesErrorText(activity.getString(R.string.error_invalid_email)));
            onView(withId(R.id.emails)).check(matches(withHint(R.string.list_of_participants)));
        }
    }

    @RunWith(Parameterized.class)
    public static class ComponentParamTestsCheckEmptyFields {

        /**
         * Test data
         * @return list of field identifiers to check
         */
        @Parameters
        public static Collection<Object> data() {
            return Arrays.asList(new Object[] {
                    R.id.room_name_layout,
                    R.id.topic_layout,
                    R.id.date_layout,
                    R.id.to_layout,
                    R.id.from_layout,
                    R.id.participants
            });
        }

        @Parameterized.Parameter
        public int viewId;

        /**
         * Define rules to initialize AddMeetingActivity
         */
        @Rule
        public ActivityTestRule<ListMeetingActivity> mActivityRuleInit =
                new ActivityTestRule<>(ListMeetingActivity.class);

        @Rule
        public ActivityTestRule<AddMeetingActivity> mActivityRule =
                new ActivityTestRule<>(AddMeetingActivity.class);

        /**
         * Initialize AddMeetingActivity and check that it is not empty
         */
        @Before
        public void setUp() {
            AddMeetingActivity activity = mActivityRule.getActivity();
            assertThat(activity, notNullValue());
        }

        /**
         * Check that the unfilled fields display a valid error message
         */
        @Test
        public void givenNoValue_whenClickToAddMeeting_thenGetErrorMessage() {
            AddMeetingActivity activity = mActivityRule.getActivity();

            onView(withId(R.id.action_add_meeting)).perform(click());
            onView(withId(viewId))
                    .check(matchesErrorText(activity.getString(R.string.error_empty_field)));

            onView(withText(R.string.error_add_new_meeting))
                    .inRoot(withDecorView(not(is(activity.getWindow().getDecorView()))))
                    .check(matches(isDisplayed()));
        }
    }

    @RunWith(Parameterized.class)
    public static class ComponentParamTestsCheckEmailsField {
        private static final String sEmail1 = "mickael@gmail.com";
        private static final String sEmail2 = "john@outlook.com";
        private static final String sEmailInvalid = "test";

        /**
         * Test data
         *
         * @return list of internal field delimiter to check
         */
        @Parameters
        public static Collection<Object> data() {
            return Arrays.asList(new Object[]{" ", ","});
        }

        @Parameterized.Parameter
        public String internalFieldDelimiter;

        /**
         * Define rules to initialize AddMeetingActivity
         */
        @Rule
        public ActivityTestRule<ListMeetingActivity> mActivityRuleInit =
                new ActivityTestRule<>(ListMeetingActivity.class);

        @Rule
        public ActivityTestRule<AddMeetingActivity> mActivityRule =
                new ActivityTestRule<>(AddMeetingActivity.class);

        /**
         * Initialize AddMeetingActivity and check that it is not empty
         */
        @Before
        public void setUp() {
            AddMeetingActivity activity = mActivityRule.getActivity();
            assertThat(activity, notNullValue());
        }

        /**
         * Check the correct entry of the Emails field (press Enter key)
         */
        @Test
        public void givenValidEmail_whenTypeTextWithDelimiter_thenGetEmailWithoutError() {
            onView(withId(R.id.emails)).perform(typeText(sEmail1 + internalFieldDelimiter));

            onView(withId(R.id.emails_group)).check(matchesChipTextAtPosition(1, sEmail1));
            onView(withId(R.id.emails)).check(matches(withHint(R.string.list_of_participants)));
            onView(withId(R.id.participants)).check(matchesNoErrorText());
        }

        /**
         * Check the correct entries of the Emails field (press Enter key)
         */
        @Test
        public void givenTwoValidEmail_whenTypeTextWithDelimiter_thenGetEmailsWithoutError() {
            onView(withId(R.id.emails)).perform(typeText(sEmail1 + internalFieldDelimiter));
            onView(withId(R.id.emails)).perform(typeText(sEmail2 + internalFieldDelimiter));

            onView(withId(R.id.emails_group)).check(matchesChipTextAtPosition(1, sEmail1));
            onView(withId(R.id.emails_group)).check(matchesChipTextAtPosition(2, sEmail2));
            onView(withId(R.id.emails)).check(matches(withHint(R.string.list_of_participants)));
            onView(withId(R.id.participants)).check(matchesNoErrorText());
        }

        /**
         * Check invalid entry of the Emails field (press Enter key)
         */
        @Test
        public void givenInvalidEmail_whenTypeTextWithDelimiter_thenGetErrorMessage() {
            AddMeetingActivity activity = mActivityRule.getActivity();

            onView(withId(R.id.emails)).perform(typeText(sEmailInvalid + internalFieldDelimiter));

            onView(withId(R.id.participants))
                    .check(matchesErrorText(activity.getString(R.string.error_invalid_email)));
            onView(withId(R.id.emails)).check(matches(withHint(R.string.list_of_participants)));
        }

        /**
         * Check invalid entry of the Emails field (press Enter key)
         */
        @Test
        public void givenOneValidEmailAndOneInvalidEmail_whenTypeTextWithDelimiter_thenGetErrorMessage() {
            AddMeetingActivity activity = mActivityRule.getActivity();

            onView(withId(R.id.emails)).perform(typeText(sEmail1 + internalFieldDelimiter));
            onView(withId(R.id.emails)).perform(typeText(sEmailInvalid + internalFieldDelimiter));

            onView(withId(R.id.emails_group)).check(matchesChipTextAtPosition(1, sEmail1));
            onView(withId(R.id.participants))
                    .check(matchesErrorText(activity.getString(R.string.error_invalid_email)));
            onView(withId(R.id.emails)).check(matches(withHint(R.string.list_of_participants)));
        }
    }

}
