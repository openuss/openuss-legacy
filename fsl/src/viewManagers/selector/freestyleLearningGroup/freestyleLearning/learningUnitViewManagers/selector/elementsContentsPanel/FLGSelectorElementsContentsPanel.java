package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.elementsContentsPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.elementsContentsPanel.FSLAbstractLearningUnitViewElementContentPanel;
import freestyleLearning.learningUnitViewAPI.elementsContentsPanel.FSLAbstractLearningUnitViewElementsContentsPanel;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEvent;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitVetoableAdapter;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewVetoableAdapter;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBindingSubclasses.FLGSelectorElement;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.elementsContentsPanel.FLGSelectorConfigurationContentPanel.FLGSelectorLearningUnitViewAdapter;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.events.FLGSelectorEvent;
import freestyleLearningGroup.independent.gui.FLGSingleLayout;
import freestyleLearningGroup.independent.gui.FLGUIUtilities;

/**
 * FLGSelectorElementsContentsPanel.
 * Manager Class for Selector Content Panels.
 * @author Carsten Fiedler
 */
public class FLGSelectorElementsContentsPanel extends FSLAbstractLearningUnitViewElementsContentsPanel {
	private FLGSelectorConfigurationContentPanel configurationContentPanel;
	private FLGSelectorMainContentPanel playGroundContentPanel;
	//private FLGSelectorEvaluationContentPanel evaluationContentPanel;
	private boolean playMode = false;
	private boolean editMode = false;
	private boolean evaluationMode = false;
	private boolean userRoleIsAuthor = false;
	
	/**
	 * Inits Selector Elements Contents Panel.
	 * @param FSLLearningUnitViewManager learningUnitViewManager
	 * @param FSLLearningUnitEventGenerator learningUnitEventGenerator
	 * @param boolean editMode
	 */
	public void init(FSLLearningUnitViewManager learningUnitViewManager,
	        FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
		super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
		this.editMode=editMode;
		configurationContentPanel = new FLGSelectorConfigurationContentPanel();
		configurationContentPanel.init(learningUnitViewManager,learningUnitEventGenerator,editMode);
		playGroundContentPanel = new FLGSelectorMainContentPanel();
		playGroundContentPanel.init(learningUnitViewManager,learningUnitEventGenerator,editMode);
		learningUnitViewManager.addLearningUnitViewListener(new FLGSelectorElementsContentsPanel_LearningUnitViewAdapter());
		learningUnitEventGenerator.addLearningUnitListener(new FLGSelectorContentsPanel_LearningUnitAdapter());
		learningUnitViewManager.addLearningUnitViewListener(new FLGSelectorLearningUnitViewAdapter());
	}
    
	/**
	 * Returns Element Content Panel.
	 * @param int index
	 * @param String learningUnitViewElementId
	 * @return FSLAbstractLearningUnitViewElementContentPanel ElementContentPanel
	 */
    protected FSLAbstractLearningUnitViewElementContentPanel getElementContentPanel(int index, String learningUnitViewElementId) {
    	String activeLearningUnitViewElementId = learningUnitViewElementId;
    	FLGSelectorElement learningUnitViewElement = null;
        if (userRoleIsAuthor) {
        	learningUnitViewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                	activeLearningUnitViewElementId);
    	} else {
    		// check for user element
    		learningUnitViewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewUserElement(
        		activeLearningUnitViewElementId);
    		// if no user element
    		if(learningUnitViewElement==null) {
    			// get original element
    			learningUnitViewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewOriginalElement(
                    	activeLearningUnitViewElementId);
    		}
    	}
    	if (learningUnitViewElement != null && !learningUnitViewElement.getFolder()) {
            if (configurationContentPanel!=null) {
            	configurationContentPanel.deactiveMediaPlayer();
            }
        	if (editMode) {
        		return configurationContentPanel;
        	} else if (evaluationMode) {
        		//return evaluationContentPanel;
        	} else if (playMode) {
        		return playGroundContentPanel; 
        	} 
        }
        return null;
    }

    /**
     * Listener for view specific events.
     * Listener manages loading from playground (main content panel) or configuration content panel.
     */
    class FLGSelectorElementsContentsPanel_LearningUnitViewAdapter extends FSLLearningUnitViewVetoableAdapter {
    	public void learningUnitViewDeactivated(FSLLearningUnitViewEvent event) {
    		playMode = false;
            buildDependentUI(false,false);
    	}
    	
    	public void learningUnitViewSpecificEventOccurred(FSLLearningUnitViewEvent event) {
            FLGSelectorEvent selectorEvent = (FLGSelectorEvent)event;
            if (selectorEvent.getEventSpecificType() == FLGSelectorEvent.SELECTOR_PLAY_MODE_ENTERED) {
                playMode = true;
                buildDependentUI(false,false);
            	// open full screen mode
                String activeLearningUnitViewElementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
            	FSLLearningUnitViewEvent event2 = FSLLearningUnitViewEvent.createFullScreenModeChangedEvent(learningUnitViewManager.getLearningUnitViewManagerId(),
                        activeLearningUnitViewElementId, true, true);
                learningUnitViewManager.fireLearningUnitViewEvent(event2);
            }
            if (selectorEvent.getEventSpecificType() == FLGSelectorEvent.SELECTOR_MULTI_PLAYER_MODE_ENTERED) {
            	playMode = true;
                buildDependentUI(false,false);
            	// open full screen mode
                String activeLearningUnitViewElementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
            	FSLLearningUnitViewEvent event2 = FSLLearningUnitViewEvent.createFullScreenModeChangedEvent(learningUnitViewManager.getLearningUnitViewManagerId(),
                        activeLearningUnitViewElementId, true, true);
                learningUnitViewManager.fireLearningUnitViewEvent(event2);
            }
            if (selectorEvent.getEventSpecificType() == FLGSelectorEvent.SELECTOR_MULTI_PLAYER_RUN_EXITED) {
            	playMode = true;
                buildDependentUI(false,false);
                if (!selectorEvent.getLastMultiPlayerRun()) {
                	// open full screen mode
	                String activeLearningUnitViewElementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
	            	FSLLearningUnitViewEvent event2 = FSLLearningUnitViewEvent.createFullScreenModeChangedEvent(learningUnitViewManager.getLearningUnitViewManagerId(),
	                        activeLearningUnitViewElementId, true, true);
	                learningUnitViewManager.fireLearningUnitViewEvent(event2);
                }
                
            }
            if (selectorEvent.getEventSpecificType() == FLGSelectorEvent.SELECTOR_PLAY_MODE_EXITED) {

                // close full screen mode
                String activeLearningUnitViewElementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
            	FSLLearningUnitViewEvent event2 = FSLLearningUnitViewEvent.createFullScreenModeChangedEvent(learningUnitViewManager.getLearningUnitViewManagerId(),
                        activeLearningUnitViewElementId, false, true);
                learningUnitViewManager.fireLearningUnitViewEvent(event2);
                
                playMode = false;
                buildDependentUI(false,false);
                
            }
        }     	
    }
    
    /**
     * Listener for setting edit mode identifier.
     */
    class FLGSelectorContentsPanel_LearningUnitAdapter extends FSLLearningUnitVetoableAdapter {
        public void learningUnitEditModeChanged(FSLLearningUnitEvent event) {
        	if (event.getEventType() == FSLLearningUnitEvent.LEARNING_UNIT_EDIT_MODE_CHANGED) {
        		if (event.isEditMode()) { 
        			editMode = true;
        			buildDependentUI(false,false);
        		} else {
        			editMode = false;
        			buildDependentUI(false,false);
        		}
        	}
        }
    }
    
    /**
    public void updateUI() {
       if (configurationContentPanel!=null) {
    	   configurationContentPanel.updateUI();
       }
       super.updateUI();
    }**/
    
    /**
     * FLGSelectorLearningUnitViewAdapter.
     * Inner class for learning unit view event handling.
     */
    class FLGSelectorLearningUnitViewAdapter extends FSLLearningUnitViewVetoableAdapter {
        public void learningUnitViewSpecificEventOccurred(FSLLearningUnitViewEvent event) {
            FLGSelectorEvent selectorEvent = (FLGSelectorEvent)event;
            if (selectorEvent.getEventSpecificType() == FLGSelectorEvent.SELECTOR_REPAINT_CONTENT_PANEL) {
            	userRoleIsAuthor = selectorEvent.getUserRole();
            	//updateUI();
            	buildDependentUI(false,false);
             }
        }
    }

}
