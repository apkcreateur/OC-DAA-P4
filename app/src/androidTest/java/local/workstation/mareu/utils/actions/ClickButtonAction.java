package local.workstation.mareu.utils.actions;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import static androidx.test.espresso.action.ViewActions.click;

import org.hamcrest.Matcher;

import local.workstation.mareu.R;

public class ClickButtonAction implements ViewAction {
    private ViewAction mClick = click();
    private final int mExpectedId;

    private ClickButtonAction(int expectedId) {
        mExpectedId = expectedId;
    }

    @Override
    public Matcher<View> getConstraints() {
        return mClick.getConstraints();
    }

    @Override
    public String getDescription() {
        return mClick.getDescription();
    }

    @Override
    public void perform(UiController uiController, View view) {
        mClick.perform(uiController, view.findViewById(mExpectedId));
    }

    public static ClickButtonAction clickToDeleteButton() {
        return new ClickButtonAction(R.id.delete_item);
    }
}
