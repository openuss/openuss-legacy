package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.elementsContentsPanel;

import javax.swing.*;

import freestyleLearning.learningUnitViewAPI.elementsContentsPanel.FSLAbstractLearningUnitViewElementContentPanel;

/**
 * FLGSelectorEvaluationContentPanel.
 * Manager Class for Selector Evaluation Panel.
 * @author Carsten Fiedler
 */
public class FLGSelectorEvaluationContentPanel extends  FSLAbstractLearningUnitViewElementContentPanel {
	private boolean isModifiedByUserInput = false;
		
    /**
     * Gets Edit-Toolbar. Inherited method has to be overwritten,
     * but is not needed in evaluation mode.
     * @return null 
     */
    protected JComponent[] getEditToolBarComponents() {
        return null;
    }
    
    /**
     * Returns if Content Panel has been modified by user input.
     * @return boolean 
     */
    public boolean isModifiedByUserInput() {
    	return isModifiedByUserInput;
    }
	
    /**
     * Saves User Changes.
     */
    public void saveUserChanges() {
    }
    
    /**
     * Builds independent UI.
     */
    protected void buildIndependentUI() {

    }
    
    /**
     * Builds dependent UI.
     * @param boolean reloadIfAlreadyLoaded
     */
    protected void buildDependentUI(boolean reloadIfAlreadyLoaded) {
    }
}
