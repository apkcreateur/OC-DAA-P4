package local.workstation.mareu.ui;

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
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static local.workstation.mareu.utils.TextInputLayoutErrorValueAssertion.matchesErrorText;
import static local.workstation.mareu.utils.TextInputLayoutNoErrorValueAssertion.matchesNoErrorText;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Enclosed.class)
public class AddMeetingActivityTest {

    public static class ComponentSingleTests {
        private static final String sTopic = "New Meeting!";

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
    }

    @RunWith(Parameterized.class)
    public static class ComponentParamTests {

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
         * Check that the unfilled fields display a valid error message
         */
        @Test
        public void givenNoValue_whenClickToAddMeeting_thenGetErrorMessage() {
            onView(withId(R.id.action_add_meeting)).perform(click());
            onView(withId(viewId))
                    .check(matchesErrorText(mActivityRule.getActivity().getString(R.string.error_empty_field)));
        }
    }

}
