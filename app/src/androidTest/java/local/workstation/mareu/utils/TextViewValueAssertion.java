package local.workstation.mareu.utils;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import local.workstation.mareu.R;

import static org.junit.Assert.assertEquals;

public class TextViewValueAssertion implements ViewAssertion {
    private final int mItemPosition;
    private final int mExpectedId;
    private final String mExpectedText;

    private TextViewValueAssertion(int itemPosition, int expectedId, String expectedText) {
        mItemPosition = itemPosition;
        mExpectedId = expectedId;
        mExpectedText = expectedText;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(mItemPosition);
        assert viewHolder != null;
        TextView textView = viewHolder.itemView.findViewById(mExpectedId);

        assertEquals(textView.getText().toString(), mExpectedText);
    }

    public static TextViewValueAssertion matchesDescriptionAtItemPosition(int itemPosition, String expectedText) {
        return new TextViewValueAssertion(itemPosition, R.id.description_item, expectedText);
    }

    public static TextViewValueAssertion matchesParticipantsAtItemPosition(int itemPosition, String expectedText) {
        return new TextViewValueAssertion(itemPosition, R.id.participants_item, expectedText);
    }
}