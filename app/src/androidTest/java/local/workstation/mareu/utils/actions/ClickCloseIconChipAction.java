package local.workstation.mareu.utils.actions;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.hamcrest.Matcher;

public class ClickCloseIconChipAction implements ViewAction {
    private final int mAtPosition;

    private ClickCloseIconChipAction(int atPosition) {
        mAtPosition = atPosition - 1;
    }

    @Override
    public Matcher<View> getConstraints() {
        return ViewMatchers.isAssignableFrom(ChipGroup.class);
    }

    @Override
    public String getDescription() {
        return "Click on close icon";
    }

    @Override
    public void perform(UiController uiController, View view) {
        Chip chip = (Chip) ((ChipGroup) view).getChildAt(mAtPosition);
        chip.performCloseIconClick();

    }

    public static ClickCloseIconChipAction clickOnCloseIconChip(int atPosition) {
        return new ClickCloseIconChipAction(atPosition);
    }
}
