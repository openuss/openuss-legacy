package freestyleLearningGroup.independent.tourCreator;

/**
 * Interface used to define the element information to be transferred from FLG to the TourCreator.
 * @author  Steffen Wachenfeld
 */
public interface FLG2TourCreatorElementInformation extends freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementLinkTarget{
    
    /**
     * Methods inherited from freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementLinkTarget
     *
    String getTargetLearningUnitId();

    String getTargetLearningUnitViewManagerId();

    String getTargetLearningUnitViewElementId();

    void setTargetLearningUnitId(String targetLarningUnitId);

    void setTargetLearningUnitViewManagerId(String targetLarningUnitViewManagerId);

    void setTargetLearningUnitViewElementId(String targetLarningUnitViewElementId);
    
    ************************************************************************************/
    
    /**
     * Should return a screenshot-like image of the active element
     * bounding size: 256 x 256
     */
    java.awt.Image getElementImage();
    
    String getTargetLearningUnitName();

    String getTargetLearningUnitViewManagerName();

    String getTargetLearningUnitViewElementName();
    
    /**
     * Should set a screenshot-like image of the active element
     * bounding size: 256 x 256
     */
    void setElementImage(java.awt.Image a_elementImage);

    void setTargetLearningUnitName(String targetLarningUnitName);

    void setTargetLearningUnitViewManagerName(String targetLarningUnitViewManagerName);

    void setTargetLearningUnitViewElementName(String targetLarningUnitViewElementName);
}
