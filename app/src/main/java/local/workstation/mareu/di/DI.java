package local.workstation.mareu.di;

import local.workstation.mareu.service.FakeMeetingApiService;
import local.workstation.mareu.service.MeetingApiService;

public class DI {
    private static MeetingApiService mService = new FakeMeetingApiService();

    /**
     * Get instance on @{link MeetingApiService}
     * @return FakeMeetingApiService();
     */
    public static MeetingApiService getApiService() {
        return mService;
    }

    /**
     * Get always a new instance on {@link MeetingApiService}.
     *
     * Userful for tests, so we ensure the context is clean.
     * @return FakeMeetingApiService();
     */
    public static MeetingApiService getNewInstanceApiService() {
        return new FakeMeetingApiService();
    }
}
