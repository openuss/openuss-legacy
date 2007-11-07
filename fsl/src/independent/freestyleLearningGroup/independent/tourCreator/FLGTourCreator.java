package freestyleLearningGroup.independent.tourCreator;

/**
 * TourCreators should implement this Interface to ensure that FLG2TourCreatorInteractors 
 * can register themself at the TourCreator.
 * @author Steffen Wachenfeld
 */
public interface FLGTourCreator extends javax.swing.event.ListDataListener{
    
    static final int REGISTRATION_SUCCESS = 0;
    static final int REGISTRATION_ERROR   = 1;
    
    
    /**
     * Method used for registration of FLG2TourCreatorInteractor at the TourCreator
     * @param a_tourInteractor the FLG2TourCreatorInteractor to be registered
     * @return error code out of {REGISTRATION_SUCCESS, REGISTRATION_ERROR}
     */
    public int registerFLG2TourCreatorInteractor(FLG2TourCreatorInteractor a_tourInteractor);
    
    
    /**
     * Invokes the capture action on the TourCreator.
     */
    public void performCaptureAction(java.awt.event.ActionEvent e);
}
