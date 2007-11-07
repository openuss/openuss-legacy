package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mediaPool.events.learningUnitViewEvent;

import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;

public class FLGMediaPoolViewEvent extends FSLLearningUnitViewEvent {
    public static final int MEDIA_PLAY_BUTTON_PRESSED = 2;
    public static final int MEDIA_PAUSE_BUTTON_PRESSED = 3;
    public static final int MEDIA_STOP_BUTTON_PRESSED = 5;
    public static final int MEDIA_END_OF_MEDIA_REACHED = 8;
    public static final int MEDIA_PLAYING_TIME_PROGRESS = 9;
    public static final int MEDIA_SCALE_MODE_CHANGED = 11;
    public static final int MEDIA_SCALE_INCREASED = 12;
    public static final int MEDIA_SCALE_DECREASED = 13;
    public static final int MEDIA_SCALE_PAGE_WIDTH = 14;
    public static final int MEDIA_SCALE_PAGE_HEIGHT = 15;
    public static final int MEDIA_SCALE_PAGE_100 = 16;
    public static final int MEDIA_NEXT_PAGE = 17;
    public static final int MEDIA_PREVIOUS_PAGE = 18;
    public static final int MEDIA_NEW_PAGE_DISPLAYED = 19;
    public static final int MEDIA_PROGRESS_STEP_SET = 20;
    public static final int MEDIA_VOLUME_STEP_SET = 21;
    public int maximumPlayingTime = 0;
    public double actualPlayingTimeSeconds = 0;
    public int eventSpecificType = -1;
    public int currentPage = 1;
    public int pageCount = 1;
    public int volumeStep = 1;

    public int getEventSpecificType() {
        return eventSpecificType;
    }
    
    public static FSLLearningUnitViewEvent createViewSpecificEvent(int eventSpecificType, String activeLearningUnitViewElementID) {
        FLGMediaPoolViewEvent event = new FLGMediaPoolViewEvent();
        event.activeLearningUnitViewElementId = activeLearningUnitViewElementID;
        event.eventType = FSLLearningUnitViewEvent.VIEW_SPECIFIC_EVENT_OCCURRED;
        event.eventSpecificType = eventSpecificType;
        return event;
    } 
}
