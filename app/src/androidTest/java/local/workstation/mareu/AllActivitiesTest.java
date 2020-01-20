package local.workstation.mareu;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import local.workstation.mareu.di.DI;
import local.workstation.mareu.service.MeetingApiServiceException;
import local.workstation.mareu.ui.meeting_list.ListMeetingActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static local.workstation.mareu.utils.assertions.RecyclerViewItemCountAssertion.itemCountAssertion;
import static local.workstation.mareu.utils.assertions.TextInputLayoutErrorValueAssertion.matchesErrorText;
import static local.workstation.mareu.utils.dummydata.DummyCalendarGenerator.generateDateTimeFromTomorrow;
import static local.workstation.mareu.utils.dummydata.DummyCalendarGenerator.generateTomorrowDateTime;
import static local.workstation.mareu.utils.dummydata.DummyEmailGenerator.VALID_EMAIL_1;
import static local.workstation.mareu.utils.dummydata.DummyMeetingGenerator.ITEMS_COUNT;
import static local.workstation.mareu.utils.dummydata.DummyMeetingGenerator.ROOM_NAME;
import static local.workstation.mareu.utils.dummydata.DummyMeetingGenerator.TOPIC;
import static local.workstation.mareu.utils.dummydata.DummyMeetingGenerator.generateMeetings;
import static local.workstation.mareu.utils.dummydata.DummyMeetingGenerator.generateRooms;
import static local.workstation.mareu.utils.matchers.ToastMatcher.isToast;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class AllActivitiesTest {

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
     * Scenario: book an available room
     */
    @Test
    public void whenWeReserveAnAvailableRoom_thenItIsValidated() {
        // Init
        Calendar from = generateDateTimeFromTomorrow(3,9,0);
        Calendar to = generateDateTimeFromTomorrow(3,10,0);

        // Click to add meeting
        onView(ViewMatchers.withId(R.id.add)).perform(click());

        // Fill in the fields
        // room name
        onView(withId(R.id.room_name)).perform(click());
        onView(withText(ROOM_NAME))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());
        // topic
        onView(withId(R.id.topic)).perform(typeText(TOPIC));
        // date
        onView(withId(R.id.date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(
                        from.get(Calendar.YEAR),
                        from.get(Calendar.MONTH) + 1,
                        from.get(Calendar.DAY_OF_MONTH)));
        onView(withText(android.R.string.ok)).perform(click());
        // from (time)
        onView(withId(R.id.from)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(
                        from.get(Calendar.HOUR_OF_DAY),
                        from.get(Calendar.MINUTE)));
        onView(withText(android.R.string.ok)).perform(click());
        // to (time)
        onView(withId(R.id.to)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(
                        to.get(Calendar.HOUR_OF_DAY),
                        to.get(Calendar.MINUTE)));
        onView(withText(android.R.string.ok)).perform(click());
        // email
        onView(withId(R.id.emails)).perform(typeText(VALID_EMAIL_1 + " "));

        // Validate the entry
        onView(withId(R.id.action_add_meeting)).perform(click());

        // Check that the meeting has been added
        onView(withText(R.string.add_new_meeting))
                .inRoot(isToast())
                .check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.list))
                .check(itemCountAssertion(ITEMS_COUNT + 1));
    }

    /**
     * Scenario: book an unavailable room
     */
    @Test
    public void whenWeReserveAnUnavailableRoom_thenItIsRefused() {
        // Init
        ListMeetingActivity activity = mActivityRule.getActivity();
        Calendar from = generateTomorrowDateTime(9,0);
        Calendar to = generateTomorrowDateTime(10,0);

        // Click to add meeting
        onView(ViewMatchers.withId(R.id.add)).perform(click());

        // Fill in the fields
        // room name
        onView(withId(R.id.room_name)).perform(click());
        onView(withText(ROOM_NAME))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());
        // topic
        onView(withId(R.id.topic)).perform(typeText(TOPIC));
        // date
        onView(withId(R.id.date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(
                        from.get(Calendar.YEAR),
                        from.get(Calendar.MONTH) + 1,
                        from.get(Calendar.DAY_OF_MONTH)));
        onView(withText(android.R.string.ok)).perform(click());
        // from (time)
        onView(withId(R.id.from)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(
                        from.get(Calendar.HOUR_OF_DAY),
                        from.get(Calendar.MINUTE)));
        onView(withText(android.R.string.ok)).perform(click());
        // to (time)
        onView(withId(R.id.to)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(
                        to.get(Calendar.HOUR_OF_DAY),
                        to.get(Calendar.MINUTE)));
        onView(withText(android.R.string.ok)).perform(click());
        // email
        onView(withId(R.id.emails)).perform(typeText(VALID_EMAIL_1 + " "));

        // Validate the entry
        onView(withId(R.id.action_add_meeting)).perform(click());

        // Check that the addition is refused
        onView(withText(R.string.error_meeting_room_already_booked))
                .inRoot(isToast())
                .check(matches(isDisplayed()));
        onView(withId(R.id.room_name_layout))
                .check(matchesErrorText(activity.getString(R.string.error_meeting_room_already_booked)));
    }

    /**
     * Scenario: abort an booked in progress
     */
    @Test
    public void whenMakingReservationAndWhenWeClickToCancel_thenItIsAborted() {
        // Click to add meeting
        onView(ViewMatchers.withId(R.id.add))
                .perform(click());

        // Abort
        onView(withContentDescription(R.string.abc_action_bar_up_description))
                .perform(click());

        // Check abort
        // TODO sometimes the check doesn't work
        onView(withText(R.string.abort_add_meeting))
                    .inRoot(isToast())
                    .check(matches(isDisplayed()));
    }
}