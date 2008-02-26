package freestyleLearning.learningUnitViewAPI.contextDependentInteractionPanel;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementsManager;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;

/**
 * This class is the interface for the contextDependentInteractionPanel, which
 * can be implemented by context-dependent view-managern, to show their interaction-buttons in other views.
 * @author Gunnar Thies
 * @version 1.0, 15/04/03
 */
public interface FSLLearningUnitViewContextDependentInteractionPanel {
    void init(FSLLearningUnitViewManager learningUnitViewManager,
        FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode);

    void setLearningUnitViewElementsManager(FSLLearningUnitViewElementsManager learningUnitViewElementsManager);

    void updateUI();
}
