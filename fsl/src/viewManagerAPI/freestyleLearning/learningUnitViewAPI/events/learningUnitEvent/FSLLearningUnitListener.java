/* Generated by Freestyle Learning Group */

package freestyleLearning.learningUnitViewAPI.events.learningUnitEvent;

public interface FSLLearningUnitListener {
    void learningUnitActivated(FSLLearningUnitEvent event);

    void learningUnitCreated(FSLLearningUnitEvent event);

    void learningUnitRemoved(FSLLearningUnitEvent event);

    void learningUnitInvalidated(FSLLearningUnitEvent event);

    void learningUnitEditModeChanged(FSLLearningUnitEvent event);

    void learningUnitUserViewChanged(FSLLearningUnitEvent event);

    void learningUnitsUserDirectoryChanged(FSLLearningUnitEvent event);
}