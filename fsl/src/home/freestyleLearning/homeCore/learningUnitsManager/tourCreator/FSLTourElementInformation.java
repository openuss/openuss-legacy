/*
 * FSLTourElementInformation.java
 *
 * Created on 11. September 2003, 12:52
 */

package freestyleLearning.homeCore.learningUnitsManager.tourCreator;

import freestyleLearning.homeCore.learningUnitsManager.tourCreator.data.xmlBindingSubclasses.FSLTourElementLinkTarget;
import freestyleLearningGroup.independent.tourCreator.FLG2TourCreatorElementInformation;

/**
 * @author  awmiwa
 */
public class FSLTourElementInformation extends FSLTourElementLinkTarget implements FLG2TourCreatorElementInformation {
    
    public java.awt.Image getElementImage() {
        return null;
    }
    
    public String getTargetLearningUnitName() {
        return null;
    }
    
    public String getTargetLearningUnitViewElementName() {
        return null;
    }
    
    public String getTargetLearningUnitViewManagerName() {
        return null;
    }
    
    public void setElementImage(java.awt.Image a_elementImage) {
    }
    
    public void setTargetLearningUnitName(String targetLarningUnitName) {
    }
    
    public void setTargetLearningUnitViewElementName(String targetLarningUnitViewElementName) {
    }
    
    public void setTargetLearningUnitViewManagerName(String targetLarningUnitViewManagerName) {
    }
    
}