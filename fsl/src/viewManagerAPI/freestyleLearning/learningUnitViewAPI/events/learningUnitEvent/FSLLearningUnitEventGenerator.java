/* Generated by Freestyle Learning Group */

package freestyleLearning.learningUnitViewAPI.events.learningUnitEvent;

public interface FSLLearningUnitEventGenerator {
    public void addLearningUnitListener(FSLLearningUnitListener listener);

    public void removeLearningUnitListener(FSLLearningUnitListener listener);

    public void fireLearningUnitEvent(FSLLearningUnitEvent event);
    
    public void requestEditMode(boolean enableEditMode);
}
