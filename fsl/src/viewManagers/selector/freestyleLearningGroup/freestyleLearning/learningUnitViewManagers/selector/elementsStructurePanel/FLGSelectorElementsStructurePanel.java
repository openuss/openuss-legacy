package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.elementsStructurePanel;

import java.util.Date;

import javax.swing.*;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElement;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.elementsStructurePanel.FSLAbstractLearningUnitViewElementsStructurePanel;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewVetoableAdapter;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBindingSubclasses.FLGSelectorElement;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.events.FLGSelectorEvent;

/**
 * FLGSelectorElementsStructurePanel.
 * Class for managing Selector Structure Panel Elements.
 * @author Carsten Fiedler
 */
public class FLGSelectorElementsStructurePanel extends FSLAbstractLearningUnitViewElementsStructurePanel {

	/**
     * Inits Selector Structure Panel Elements.
     * @param FSLLearningUnitViewManager learningUnitViewManager
     * @param FSLLearningUnitEventGenerator learningUnitEventGenerator
     * @param boolean editMode
     */
    public void init(FSLLearningUnitViewManager learningUnitViewManager,
    	    FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
    	        super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
    	        learningUnitViewManager.addLearningUnitViewListener(new FLGSelectorElementStructurePanel_Adapter());
    }
    
    /**
     * Modifies Learning Unit Selector View Element.
     * @param FSLLearningUnitViewElement element
     */
    public void modifyLearningUnitViewElement(FSLLearningUnitViewElement element) {
        FLGSelectorElement selectorElement = (FLGSelectorElement)element;
        selectorElement.setLastModificationDate(String.valueOf(new Date().getTime()));
        if (!selectorElement.getFolder()) {
        	selectorElement.setType(FLGSelectorElement.ELEMENT_TYPE_TEXT);
        }
        else {
        	selectorElement.setType(FLGSelectorElement.ELEMENT_TYPE_FOLDER);
        }
    }
    
    /**
     * Creates a new Learning Unit Selector View Element.
     * @param String id
     * @param String parentId
     * @param String title
     * @param boolean folder
     * @return FSLLearningUnitViewElement newElement
     */
    public FSLLearningUnitViewElement createLearningUnitViewElement(String id, String parentId, String title, boolean folder) {
    	FLGSelectorElement newElement = new FLGSelectorElement();
        newElement.setId(id);
        newElement.setParentId(parentId);
        newElement.setTitle(title);
        newElement.setFolder(folder);
        newElement.setLastModificationDate(String.valueOf(new Date().getTime()));
        if (!folder) newElement.setType(FLGSelectorElement.ELEMENT_TYPE_TEXT);
        else {
            newElement.setType(FLGSelectorElement.ELEMENT_TYPE_FOLDER);
        }
        return newElement;
    }
       
    /**
    private void reloadData() {
        ((FLGSelectorManager)learningUnitViewManager).reloadData();
    }**/
    
    /**
    private Image loadImage(String imageFileName) {
        return FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource(
            "freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/selector/images/" + imageFileName));
    }**/
    
    /** 
     * FLGSelectorElementStructurePanel_Adapter.
     * Inner class to deactivate structure panel view in play mode. 
     */
    class FLGSelectorElementStructurePanel_Adapter extends FSLLearningUnitViewVetoableAdapter {
    	public void learningUnitViewDeactivated(FSLLearningUnitViewEvent event) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    activateStandard();
                }
            });
    	}
    	
        public void learningUnitViewSpecificEventOccurred(FSLLearningUnitViewEvent event) {
            FLGSelectorEvent selectorEvent = (FLGSelectorEvent)event;
            if (selectorEvent.getEventSpecificType() == FLGSelectorEvent.SELECTOR_PLAY_MODE_ENTERED) {
                    SwingUtilities.invokeLater(new Runnable() {
                      public void run() {
                            activateBlank();
                        }
                    });
            }
            if (selectorEvent.getEventSpecificType() == FLGSelectorEvent.SELECTOR_MULTI_PLAYER_MODE_ENTERED) {
                SwingUtilities.invokeLater(new Runnable() {
                  public void run() {
                        activateBlank();
                    }
                });
            }    
            if (selectorEvent.getEventSpecificType() == FLGSelectorEvent.SELECTOR_PLAY_MODE_EXITED){
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            activateStandard();
                        }
                    });
            }
            if (selectorEvent.getEventSpecificType() == FLGSelectorEvent.SELECTOR_MULTI_PLAYER_MODE_TIMEOUT){
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        activateStandard();
                    }
                });
        }
        }
    }
}
