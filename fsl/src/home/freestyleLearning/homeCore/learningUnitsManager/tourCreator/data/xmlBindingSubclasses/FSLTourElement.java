/*
 * FSLTourElement.java
 *
 * Created on September 10, 2003, 5:38 PM
 */

package freestyleLearning.homeCore.learningUnitsManager.tourCreator.data.xmlBindingSubclasses;

import java.awt.Image;

import freestyleLearning.homeCore.learningUnitsManager.tourCreator.FSLTourElementInformation;
import freestyleLearning.homeCore.learningUnitsManager.tourCreator.data.xmlBinding.TourElement;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementLinkTarget;
import freestyleLearningGroup.independent.tourCreator.FLG2TourCreatorElementInformation;
import freestyleLearningGroup.independent.tourCreator.FLGTourElement;

/**
 * @author  awmiwa
 */
public class FSLTourElement extends TourElement implements FLGTourElement {
    
    public FSLLearningUnitViewElementLinkTarget getFSLLearningUnitViewElementLinkTarget() {
        return (FSLLearningUnitViewElementLinkTarget)super.getLearningUnitViewElementLinks().get(0);
    }
    
    public void setFSLLearningUnitViewElementLinkTarget(FSLLearningUnitViewElementLinkTarget linkTarget) {
        super.getLearningUnitViewElementLinks().set(0, linkTarget);
    }
    
    public void setImage(Image image) {
    }
    
    public Image getImage() {
        return null;
    }
    
    public Image getSmallImage() {
        return null;
    }
    
    public FLG2TourCreatorElementInformation getFLG2TourCreatorElementInformation() {
        FSLTourElementInformation information = new FSLTourElementInformation();
        return information;
    }
    
    public void setFLG2TourCreatorElementInformation(FLG2TourCreatorElementInformation a_elementInformation) {
    }
    
}
