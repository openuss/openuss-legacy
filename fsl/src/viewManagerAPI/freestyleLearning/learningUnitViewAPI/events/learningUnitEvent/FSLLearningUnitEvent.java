/* Generated by Freestyle Learning Group */

package freestyleLearning.learningUnitViewAPI.events.learningUnitEvent;

import java.io.File;

public class FSLLearningUnitEvent {
    public static final int LEARNING_UNIT_ACTIVATED = 0;
    public static final int LEARNING_UNIT_MODIFIED = 1;
    public static final int LEARNING_UNIT_INVALIDATED = 2;
    public static final int LEARNING_UNIT_REMOVED = 3;
    public static final int LEARNING_UNIT_CREATED = 4;
    public static final int LEARNING_UNIT_EDIT_MODE_CHANGED = 5;
    public static final int LEARNING_UNIT_USER_VIEW_CHANGED = 6; // original Elements only or not
    public static final int LEARNING_UNITS_USER_DIRECTORY_CHANGED = 7;
    public static final int LEARNING_UNIT_RELOAD_DATA = 8;    
    public static final int LEARNING_UNIT_CHECK_LINKS = 9;
    protected int eventType;
    protected boolean editMode;
    protected boolean originalElementsOnly;
    protected File userDirectory;
    protected String learningUnitId;
    protected String learningUnitTitle;
    protected String learningUnitPath;

    // null, when no learning unit is selected
    public String getLearningUnitId() {
        return learningUnitId;
    }

    // null, when no learning unit is selected
    public String getLearningUnitTitle() {
        return learningUnitTitle;
    }

    public String getLearningUnitPath() {
        return learningUnitPath;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public boolean isOriginalElementsOnly() {
        return originalElementsOnly;
    }

    public File getLearningUnitsUserDirectory() {
        return userDirectory;
    }

    public int getEventType() {
        return eventType;
    }
    
    // Carsten Fiedler
    public static FSLLearningUnitEvent createLearningUnitCheckLinksEvent() {
    		FSLLearningUnitEvent event = new FSLLearningUnitEvent();
            event.eventType = LEARNING_UNIT_CHECK_LINKS;
            return event;
    }
    
    public static FSLLearningUnitEvent createLearningUnitActivatedEvent(String learningUnitId, String learningUnitTitle,
        String learningUnitPath) {
            FSLLearningUnitEvent event = new FSLLearningUnitEvent();
            event.eventType = LEARNING_UNIT_ACTIVATED;
            event.learningUnitId = learningUnitId;
            event.learningUnitTitle = learningUnitTitle;
            event.learningUnitPath = learningUnitPath;
            return event;
    }

    public static FSLLearningUnitEvent createLearningUnitInvalidatedEvent(String learningUnitId) {
        FSLLearningUnitEvent event = new FSLLearningUnitEvent();
        event.eventType = LEARNING_UNIT_INVALIDATED;
        event.learningUnitId = learningUnitId;
        return event;
    }

    public static FSLLearningUnitEvent createLearningUnitCreatedEvent(String learningUnitId) {
        FSLLearningUnitEvent event = new FSLLearningUnitEvent();
        event.eventType = LEARNING_UNIT_CREATED;
        event.learningUnitId = learningUnitId;
        return event;
    }

    public static FSLLearningUnitEvent createLearningUnitRemovedEvent(String learningUnitId) {
        FSLLearningUnitEvent event = new FSLLearningUnitEvent();
        event.eventType = LEARNING_UNIT_REMOVED;
        event.learningUnitId = learningUnitId;
        return event;
    }

    public static FSLLearningUnitEvent createLearningUnitEditModeChangedEvent(boolean editMode) {
        FSLLearningUnitEvent event = new FSLLearningUnitEvent();
        event.eventType = LEARNING_UNIT_EDIT_MODE_CHANGED;
        event.editMode = editMode;
        return event;
    }

    public static FSLLearningUnitEvent createLearningUnitUserViewChangedEvent(boolean originalElementsOnly) {
        FSLLearningUnitEvent event = new FSLLearningUnitEvent();
        event.eventType = LEARNING_UNIT_USER_VIEW_CHANGED;
        event.originalElementsOnly = originalElementsOnly;
        return event;
    }

    public static FSLLearningUnitEvent createLearningUnitsUserDirectoryChangedEvent(File userDirectory) {
        FSLLearningUnitEvent event = new FSLLearningUnitEvent();
        event.eventType = LEARNING_UNITS_USER_DIRECTORY_CHANGED;
        event.userDirectory = userDirectory;
        return event;
    }
}
