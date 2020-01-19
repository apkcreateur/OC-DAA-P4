package local.workstation.mareu.utils;

import android.os.IBinder;
import android.view.WindowManager;

import androidx.test.espresso.Root;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * @see <a href="https://stackoverflow.com/questions/29896223/android-espresso-how-to-check-that-toast-message-is-not-shown/35077824">How to check Toast message - StackOverflow</a>
 */
public class ToastMatcher extends TypeSafeMatcher<Root> {

    @Override
    protected boolean matchesSafely(Root item) {
        int type = item.getWindowLayoutParams().get().type;
        if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
            IBinder windowToken = item.getDecorView().getWindowToken();
            IBinder appToken = item.getDecorView().getApplicationWindowToken();
            return windowToken == appToken;
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("is toast");
    }
}
