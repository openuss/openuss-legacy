/* Generated by Freestyle Learning Group */

package freestyleLearning.learningUnitViewAPI.statusPanel;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementsManager;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;

public interface FSLLearningUnitViewStatusPanel {
    void init(FSLLearningUnitViewManager learningUnitViewManager,
        FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode);

    void setLearningUnitViewElementsManager(FSLLearningUnitViewElementsManager learningUnitViewElementsManager);

    void updateUI();
}