package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.learningByDoing.events.learningUnitViewEvent;

import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;

public class FLGLearningByDoingViewEvent extends FSLLearningUnitViewEvent {
    // public static final int LEARNINGBYDOING_PLAY_BUTTON_PRESSED = 1;
    private int eventSpecificType = -1;

    public int getEventSpecificType() {
        return eventSpecificType;
    }

    public static FSLLearningUnitViewEvent createViewSpecificEvent(int eventSpecificType) {
        FLGLearningByDoingViewEvent event = new FLGLearningByDoingViewEvent();
        event.eventType = FSLLearningUnitViewEvent.VIEW_SPECIFIC_EVENT_OCCURRED;
        event.eventSpecificType = eventSpecificType;
        return event;
    }
}
