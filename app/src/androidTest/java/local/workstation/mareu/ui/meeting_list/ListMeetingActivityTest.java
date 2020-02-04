package local.workstation.mareu.ui.meeting_list;

import android.text.format.DateFormat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import local.workstation.mareu.R;
import local.workstation.mareu.di.DI;
import local.workstation.mareu.service.MeetingApiServiceException;
import local.workstation.mareu.ui.AddMeetingActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static local.workstation.mareu.utils.dummies.DummyMeetingGenerator.DELETE_ITEM_POSITION;
import static local.workstation.mareu.utils.dummies.DummyMeetingGenerator.EXPECTED_DESCRIPTION_24H;
import static local.workstation.mareu.utils.dummies.DummyMeetingGenerator.EXPECTED_DESCRIPTION_12H;
import static local.workstation.mareu.utils.dummies.DummyMeetingGenerator.EXPECTED_ITEM_POSITION;
import static local.workstation.mareu.utils.dummies.DummyMeetingGenerator.EXPECTED_PARTICIPANTS;
import static local.workstation.mareu.utils.dummies.DummyMeetingGenerator.ITEMS_COUNT;
import static local.workstation.mareu.utils.dummies.DummyMeetingGenerator.generateRooms;
import static local.workstation.mareu.utils.assertions.RecyclerViewItemCountAssertion.itemCountAssertion;
import static local.workstation.mareu.utils.matchers.ToastMatcher.isToast;
import static org.hamcrest.core.IsNull.notNullValue;

import static local.workstation.mareu.utils.dummies.DummyMeetingGenerator.generateMeetings;
import static local.workstation.mareu.utils.assertions.TextViewValueAssertion.matchesDescriptionAtItemPosition;
import static local.workstation.mareu.utils.assertions.TextViewValueAssertion.matchesParticipantsAtItemPosition;
import static local.workstation.mareu.utils.actions.ClickButtonAction.clickToDeleteButton;

@RunWith(AndroidJUnit4.class)
public class ListMeetingActivityTest {

    /**
     * Define rule to initialize ListMeetingActivity with test data
     */
    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityRule =
            new ActivityTestRule<ListMeetingActivity>(ListMeetingActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    try {
                        DI.initializeMeetingApiService(generateRooms(), generateMeetings());
                    } catch (MeetingApiServiceException e) {
                        e.printStackTrace();
                    }
                }
            };

    /**
     * Initialize ListMeetingActivity with test data and check that it is not empty
     */
    @Before
    public void setUp() {
        ListMeetingActivity activity = mActivityRule.getActivity();
        assertThat(activity, notNullValue());
    }

    /**
     * Check item count
     */
    @Test
    public void givenMeetingsList_whenStartMainActivity_thenGetValidMeetingCount() {
        onView(ViewMatchers.withId(R.id.list))
                .check(itemCountAssertion(ITEMS_COUNT));
    }

    /**
     * Check first item as a valid description
     */
    @Test
    public void givenMeetingsList_whenStartMainActivity_thenDisplayValidMeetingDescriptionAtFirstPosition() {
        ListMeetingActivity activity = mActivityRule.getActivity();
        String expected_description;

        if (DateFormat.is24HourFormat(activity.getApplicationContext()))
            expected_description = EXPECTED_DESCRIPTION_24H;
        else
            expected_description = EXPECTED_DESCRIPTION_12H;

        onView(withId(R.id.list))
                .check(matchesDescriptionAtItemPosition(EXPECTED_ITEM_POSITION, expected_description));
    }

    /**
     * Check first item as a valid list of participants
     */
    @Test
    public void givenMeetingsList_whenStartMainActivity_thenDisplayValidMeetingParticipantsAtFirstPosition() {
        onView(withId(R.id.list))
                .check(matchesParticipantsAtItemPosition(EXPECTED_ITEM_POSITION, EXPECTED_PARTICIPANTS));
    }

    /**
     * Check that the deletion of the third item is effective
     */
    @Test
    public void givenMeetingList_whenPerformAClickOnItemDeleteImageButton_thenRemoveOneMeeting() {
        onView(ViewMatchers.withId(R.id.list))
                .check(itemCountAssertion(ITEMS_COUNT));
        onView(ViewMatchers.withId(R.id.list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(DELETE_ITEM_POSITION, clickToDeleteButton()));

//        // TODO sometimes the check doesn't work
//        onView(withText(R.string.toast_text_delete_meeting))
//                .inRoot(isToast())
//                .check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.list))
                .check(itemCountAssertion(ITEMS_COUNT - 1));
    }

    /**
     * Check that the AddMeetingActivity is displayed when we click on the add meeting button
     */
    @Test
    public void givenMeetingList_whenPerformAClickOnAddMeetingFloatingActionButton_thenStartAddMeetingActivity() {
        Intents.init();
        onView(ViewMatchers.withId(R.id.add))
                .perform(click());
        intended(hasComponent(AddMeetingActivity.class.getName()));
        Intents.release();
    }
}