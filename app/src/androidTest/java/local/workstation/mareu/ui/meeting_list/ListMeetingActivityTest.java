package local.workstation.mareu.ui.meeting_list;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import local.workstation.mareu.R;
import local.workstation.mareu.di.DI;
import local.workstation.mareu.utils.RecyclerViewItemCountAssertion;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static local.workstation.mareu.utils.DummyMeetingGenerator.DESCRIPTION_EXPECTED;
import static local.workstation.mareu.utils.DummyMeetingGenerator.ITEMS_COUNT;
import static local.workstation.mareu.utils.DummyMeetingGenerator.generateMeetings;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class ListMeetingActivityTest {
    private ListMeetingActivity mActivity;

    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityRule =
            new ActivityTestRule(ListMeetingActivity.class);

    @Before
    public void setUp() {
        DI.initializeMeetingApiService(generateMeetings());

        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    @Test
    public void meetingsList_validItemCount() {
        onView(ViewMatchers.withId(R.id.list))
                .check(new RecyclerViewItemCountAssertion(ITEMS_COUNT));
    }

    @Test
    public void meetingList_firstItem_shouldBeValidValue() {
        onData(ViewMatchers.withId(R.id.list))
                .atPosition(0)
                .onChildView(withId(R.id.description_item))
                .check(matches(withText(DESCRIPTION_EXPECTED)));

    }
}