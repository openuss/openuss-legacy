/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBindingSubclasses;

import java.io.*;
import java.util.*;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElement;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementLink;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementsManager;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBinding.ViewElement;

/**
 * FLGSelectorElement.
 * Class for managing Selector View Elements.
 * @author Carsten Fiedler
 */
public class FLGSelectorElement extends ViewElement implements FSLLearningUnitViewElement {
    public static String ELEMENT_TYPE_FOLDER = "folder";
    public static String ELEMENT_TYPE_TEXT = "text";
    private boolean modified;

    /**
     * Checks if FLGSelectorElement is modified.
     * @return boolean
     */
    public boolean isModified() {
        return modified;
    }

    /**
     * Sets boolean for Modification.
     * @param boolean modified
     */
    public void setModified(boolean modified) {
        this.modified = modified;
    }

    /**
     * Copy current Selector Element.
     * @return FSLLearningUnitViewElement
     */
    public FSLLearningUnitViewElement deepCopy() {
    	// System.out.println("FLGSelectorElement, deppCoy for: " + getTitle());
        FLGSelectorElement copy = new FLGSelectorElement();
        this.getLearningUnitViewElementGridObjects();
        FSLLearningUnitViewElementsManager.copyLearningUnitViewElement(this, copy);
        copy.setMusicFileName(getMusicFileName());
        // copy grid objects
        java.util.List gridObjectsList = this.getLearningUnitViewElementGridObjects();
        for (int i=0;i<gridObjectsList.size();i++) {
        	FLGSelectorElementGridObject gridObject = (FLGSelectorElementGridObject) gridObjectsList.get(i);
        	copy.getLearningUnitViewElementGridObjects().add(gridObject);
        }
        return copy;
     }

    /**
     * Gets relative paths from external Learning Unit View Elements.
     * @param FSLLearningUnitViewElementsManager learningUnitViewElementsManager
     * @return String[] pathsList
     */
    public String[] getLearningUnitViewElementExternalFilesRelativePaths(FSLLearningUnitViewElementsManager
        learningUnitViewElementsManager) {
    		ArrayList pathsList = new ArrayList();
    		// grid objects
    		if (!getFolder()) {
            	// get all grid objects
    	    	java.util.List gridObjectList = getLearningUnitViewElementGridObjects();
            	if (gridObjectList!=null) {
            		// get all images
            		for (int i=0;i<gridObjectList.size();i++) {
            			FLGSelectorElementGridObject gridObject = (FLGSelectorElementGridObject) gridObjectList.get(i);
            			if (gridObject.getType().equals(FLGSelectorElementGridObject.ELEMENT_TYPE_IMAGE)) {
            				// if image, store it
            				pathsList.add(gridObject.getId());
            			}
            		}
            	}
    		} 
    		// if music exits, add file
    		if (getMusicFileName()!=null && !getMusicFileName().equals("")){
    			pathsList.add(getMusicFileName());
     		} 
    		return (String[])pathsList.toArray(new String[0]);
    }
    
    /**
     * Gets Selector Learning Unit View Element Link to Learning Unit View Element Id.
     * @param String learningUnitViewElementLinkId
     * @return FSLLearningUnitViewElementLink learningUnitViewElementLink
     */
    public FSLLearningUnitViewElementLink getLearningUnitViewElementLink(String learningUnitViewElementLinkId) {
        for (int i = 0; i < getLearningUnitViewElementLinks().size(); i++) {
            FSLLearningUnitViewElementLink learningUnitViewElementLink =
                (FSLLearningUnitViewElementLink)getLearningUnitViewElementLinks().get(i);
            if (learningUnitViewElementLink.getId().equals(learningUnitViewElementLinkId))
                return learningUnitViewElementLink;
        }
        return null;
    }

    /**
     * Adds a new Selector Learning Unit View Element Link.
     * @return FSLLearningUnitViewElementLink Learning Unit View Element Link
     */
    public FSLLearningUnitViewElementLink addNewLearningUnitViewElementLink() {
        FLGSelectorElementLink learningUnitViewElementLink = new FLGSelectorElementLink();
        learningUnitViewElementLink.emptyLearningUnitViewElementLinkTargets();
        return FSLLearningUnitViewElementsManager.addLearningUnitViewElementLink(learningUnitViewElementLink, this);
    }

    /** 
     * Sets Selector Learning Unit View Element Type.  
     * @param String type
     
    public void setType(String type) {
        if (!type.equals(getType())) {
            this.emptyLearningUnitViewElementLinks();
            if (type.equals(ELEMENT_TYPE_FOLDER)) {
                //this.setHtmlFileName(null);
            }
            super.setType(type);
        }
    }*/
}