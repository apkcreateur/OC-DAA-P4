package local.workstation.mareu.ui.meeting_list;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import local.workstation.mareu.R;
import local.workstation.mareu.di.DI;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static local.workstation.mareu.utils.RecyclerViewItemCountAssertion.itemCountAssertion;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

import static local.workstation.mareu.utils.DummyMeetingGenerator.generateMeetings;
import static local.workstation.mareu.utils.DummyMeetingGenerator.DELETE_ITEM_POSITION;
import static local.workstation.mareu.utils.DummyMeetingGenerator.EXPECTED_DESCRIPTION;
import static local.workstation.mareu.utils.DummyMeetingGenerator.EXPECTED_ITEM_POSITION;
import static local.workstation.mareu.utils.DummyMeetingGenerator.EXPECTED_PARTICIPANTS;
import static local.workstation.mareu.utils.DummyMeetingGenerator.ITEMS_COUNT;
import static local.workstation.mareu.utils.TextViewValueAssertion.matchesDescriptionAtItemPosition;
import static local.workstation.mareu.utils.TextViewValueAssertion.matchesParticipantsAtItemPosition;
import static local.workstation.mareu.utils.ClickButtonAction.clickToDeleteButton;

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
                    DI.initializeMeetingApiService(generateMeetings());
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
        onView(withId(R.id.list))
                .check(matchesDescriptionAtItemPosition(EXPECTED_ITEM_POSITION, EXPECTED_DESCRIPTION));
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
    public void givenMeetingList_whenDeleteAction_thenRemoveOneMeeting() {
        onView(ViewMatchers.withId(R.id.list))
                .check(itemCountAssertion(ITEMS_COUNT));
        onView(ViewMatchers.withId(R.id.list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(DELETE_ITEM_POSITION, clickToDeleteButton()));
        onView(withText(R.string.toast_text_delete_meeting))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.list))
                .check(itemCountAssertion(ITEMS_COUNT - 1));
    }
}