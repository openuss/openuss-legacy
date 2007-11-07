package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro.events.learningUnitViewEvent;

import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;

public class FLGIntroViewEvent extends FSLLearningUnitViewEvent {
    public static final int INTRO_PLAY_BUTTON_PRESSED = 1;
    public static final int INTRO_PAUSE_BUTTON_PRESSED = 2;
    public static final int INTRO_STOP_BUTTON_PRESSED = 3;
    public static final int INTRO_END_OF_VIDEO_REACHED = 4;
    public static final int INTRO_VIDEO_PLAYING_TIME_PROGRESS = 5;
    public int maximumVideoPlayingTime = 0;
    public int actualVideoPlayingTime = 0;
    private int eventSpecificType = -1;

    public int getEventSpecificType() {
        return eventSpecificType;
    }

    public static FSLLearningUnitViewEvent createViewSpecificEvent(int eventSpecificType) {
        FLGIntroViewEvent event = new FLGIntroViewEvent();
        event.eventType = FSLLearningUnitViewEvent.VIEW_SPECIFIC_EVENT_OCCURRED;
        event.eventSpecificType = eventSpecificType;
        return event;
    }
}
