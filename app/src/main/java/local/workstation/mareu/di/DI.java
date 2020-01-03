package local.workstation.mareu.di;

import java.util.List;

import local.workstation.mareu.model.Meeting;
import local.workstation.mareu.service.FakeMeetingApiService;
import local.workstation.mareu.service.MeetingApiService;

public class DI {
    private static MeetingApiService sService = new FakeMeetingApiService();

    /**
     * Get instance on @{link MeetingApiService}
     *
     * @return FakeMeetingApiService();
     */
    public static MeetingApiService getApiService() {
        return sService;
    }

    /**
     * Initialize FakeMeetingApiService for tests
     *
     * @param meetings list of meetings
     */
    public static void initializeMeetingApiService(List<String> rooms, List<Meeting> meetings) {
        // Purge
        sService = new FakeMeetingApiService();
        sService.delAllRooms();

        // Initialize
        for (String room: rooms)
            sService.addRoom(room);

        for (Meeting meeting: meetings)
            sService.addMeeting(meeting);
    }
}
