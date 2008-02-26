package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.events.learningUnitViewEvent;

import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;

/**
 * This class defines some events, which can be fired at interaction.
 * @author Freestyle Learning Group
 * @author Gunnar Thies
 * @version 1.0, 15/04/03
 */
public class FLGAudioViewEvent extends FSLLearningUnitViewEvent {
    public static final int AUDIO_PLAY_BUTTON_PRESSED = 1;
    public static final int AUDIO_PAUSE_BUTTON_PRESSED = 2;
    public static final int AUDIO_STOP_BUTTON_PRESSED = 3;
    public static final int AUDIO_END_OF_AUDIO_REACHED = 4;
    public static final int AUDIO_RECORD_BUTTON_PRESSED = 5;
    public static final int AUDIO_PLAYING_TIME_PROGRESS = 6;
    public int maximumAudioPlayingTime = 0;
    public int actualAudioPlayingTime = 0;
    private int eventSpecificType = -1;

    public int getEventSpecificType() {
        return eventSpecificType;
    }

    public static FSLLearningUnitViewEvent createViewSpecificEvent(int eventSpecificType) {
        FLGAudioViewEvent event = new FLGAudioViewEvent();
        event.eventType = FSLLearningUnitViewEvent.VIEW_SPECIFIC_EVENT_OCCURRED;
        event.eventSpecificType = eventSpecificType;
        return event;
    }
}
