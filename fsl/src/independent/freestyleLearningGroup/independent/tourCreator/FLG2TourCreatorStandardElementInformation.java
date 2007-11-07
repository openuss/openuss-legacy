package freestyleLearningGroup.independent.tourCreator;

/**
 * Class used to exchange data between FSL and the Tour Creator.
 * @author Steffen Wachenfeld
 */
public class FLG2TourCreatorStandardElementInformation implements FLG2TourCreatorElementInformation {
    
    private String ms_unitId              = "noUnitId";
    private String ms_unitViewElementId   = "noElementId";
    private String ms_unitViewManagerId   = "noManageId";
    private String ms_unitName            = "noUnitName";
    private String ms_unitViewElementName = "noElementName";
    private String ms_unitViewManagerName = "noManagerName";
    private java.awt.Image m_elementImage = null;
    
    /**
     * Creates a new instance of dummyFSLLearningUnitViewElementLinkTarget
     */
    public FLG2TourCreatorStandardElementInformation() {
    }
    
    public FLG2TourCreatorStandardElementInformation(String a_unitId, String a_unitViewElementId, String a_unitViewManagerId) {
        this();
        this.ms_unitId = a_unitId;
        this.ms_unitViewElementId = a_unitViewElementId;
        this.ms_unitViewManagerId = a_unitViewManagerId;
    }
    
    /**
     * Should return a screenshot-like image of the active element
     * bounding size: 256 x 256
     */
    public java.awt.Image getElementImage()
    {
        return this.m_elementImage;
    }//getElementImage
    
    public String getTargetLearningUnitId() {
        return this.ms_unitId;
    }
    
    public String getTargetLearningUnitViewElementId() {
        return this.ms_unitViewElementId;
    }
    
    public String getTargetLearningUnitViewManagerId() {
        return this.ms_unitViewManagerId;
    }
    
    public String getTargetLearningUnitName() {
        return this.ms_unitName;
    }
    
    public String getTargetLearningUnitViewElementName() {
        return this.ms_unitViewElementName;
    }
    
    public String getTargetLearningUnitViewManagerName() {
        return this.ms_unitViewManagerName;
    }
    
    public void setTargetLearningUnitId(String targetLearningUnitId) {
        this.ms_unitId = targetLearningUnitId;
    }
    
    /** 
     * Should set a screenshot-like image of the active element
     * bounding size: 256 x 256
     */
    public void setElementImage(java.awt.Image a_elementImage) {
        this.m_elementImage = a_elementImage;
    }
    
    public void setTargetLearningUnitViewElementId(String targetLearningUnitViewElementId) {
        this.ms_unitViewElementId = targetLearningUnitViewElementId;
    }
    
    public void setTargetLearningUnitViewManagerId(String targetLearningUnitViewManagerId) {
        this.ms_unitViewManagerId = targetLearningUnitViewManagerId;
    }

    public void setTargetLearningUnitName(String as_targetLarningUnitName) {
        this.ms_unitName = as_targetLarningUnitName;
    }
    
    public void setTargetLearningUnitViewElementName(String as_targetLarningUnitViewElementName) {
        this.ms_unitViewElementName = as_targetLarningUnitViewElementName;
    }
    
    public void setTargetLearningUnitViewManagerName(String as_targetLarningUnitViewManagerName) {
        this.ms_unitViewManagerName = as_targetLarningUnitViewManagerName;
    }
    
}
