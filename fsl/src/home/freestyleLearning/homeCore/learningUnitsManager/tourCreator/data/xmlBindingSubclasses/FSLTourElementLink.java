/*
 * FSLTourElementLink.java
 *
 * Created on September 10, 2003, 5:38 PM
 */

package freestyleLearning.homeCore.learningUnitsManager.tourCreator.data.xmlBindingSubclasses;

import freestyleLearning.homeCore.learningUnitsManager.tourCreator.data.xmlBinding.TourElementLink;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementLink;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementLinkTarget;

/**
 * @author  awmiwa
 */
public class FSLTourElementLink extends TourElementLink implements FSLLearningUnitViewElementLink {
        
    public FSLLearningUnitViewElementLinkTarget addNewLearningUnitViewElementLinkTarget(String learningUnitId, String learningUnitViewManagerId, String learningUnitViewElementId) {
        FSLTourElementLinkTarget tourElementLinkTarget = new FSLTourElementLinkTarget();
        tourElementLinkTarget.setTargetLearningUnitId(learningUnitId);
        tourElementLinkTarget.setTargetLearningUnitViewManagerId(learningUnitViewManagerId);
        tourElementLinkTarget.setTargetLearningUnitViewElementId(learningUnitViewElementId);
        getLearningUnitViewElementLinkTargets().add(tourElementLinkTarget);
        return tourElementLinkTarget;
    }

}
