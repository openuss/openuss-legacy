/* Generated by Freestyle Learning Group */

package freestyleLearning.learningUnitViewAPI.events.learningUnitEvent;

public class FSLLearningUnitVetoableEvent extends FSLLearningUnitEvent {
    public static final int LEARNING_UNIT_ACTIVATING = 100;
    public static final int LEARNING_UNIT_CREATING = 103;
    public static final int LEARNING_UNITS_USER_DIRECTORY_CHANGING = 106;
    protected boolean veto;

    public boolean isVeto() {
        return veto;
    }

    public void setVeto() {
        veto = true;
    }

    public static FSLLearningUnitVetoableEvent createLearningUnitActivatingEvent() {
        FSLLearningUnitVetoableEvent event = new FSLLearningUnitVetoableEvent();
        event.eventType = LEARNING_UNIT_ACTIVATING;
        return event;
    }

    public static FSLLearningUnitVetoableEvent createLearningUnitCreatingEvent() {
        FSLLearningUnitVetoableEvent event = new FSLLearningUnitVetoableEvent();
        event.eventType = LEARNING_UNIT_CREATING;
        return event;
    }

    public static FSLLearningUnitVetoableEvent createLearningUnitsUserDirectoryChangingEvent() {
        FSLLearningUnitVetoableEvent event = new FSLLearningUnitVetoableEvent();
        event.eventType = LEARNING_UNITS_USER_DIRECTORY_CHANGING;
        return event;
    }
}