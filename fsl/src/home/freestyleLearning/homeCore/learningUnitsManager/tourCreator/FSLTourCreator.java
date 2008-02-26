/*
 * FSLTourCreator.java
 *
 * Created on September 10, 2003, 1:59 PM
 */

package freestyleLearning.homeCore.learningUnitsManager.tourCreator;

import freestyleLearning.learningUnitViewAPI.elementInteractionPanel.FSLLearningUnitViewElementInteractionButton;
import freestyleLearningGroup.independent.tourCreator.FLGTourCreator;

/**
 * Extended version of the FLGTourCreator which can place its capture button
 * into any requesting component.
 * @author Mirko Wahn
 */
public interface FSLTourCreator extends FLGTourCreator {
    
    /**
     * @return Capture Button to be placed on the right hand side of the InteractionPanel
     */
    FSLLearningUnitViewElementInteractionButton getCaptureButton();      
    
}
