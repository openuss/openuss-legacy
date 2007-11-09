/* Generated by Freestyle Learning Group */

package freestyleLearning.learningUnitViewAPI.elementsStructurePanel;

import java.util.Date;

import freestyleLearning.learningUnitViewAPI.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.*;

/**
 * FSLLearningUnitViewElementsStructurePanel.
 * @author Freestyle Learning Group
 */
public interface FSLLearningUnitViewElementsStructurePanel {

	FSLLearningUnitViewElement createLearningUnitViewElement(
        String id, String parentId, String title, boolean folder);

    void init(FSLLearningUnitViewManager learningUnitViewManager,
        FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode);

    void setLearningUnitViewElementsManager(FSLLearningUnitViewElementsManager learningUnitViewElementsManager);

    void setAutomaticActivation(boolean automaticActivation);
        
    void activateBlank();
    
    void activateStandard();

    void updateUI();
}