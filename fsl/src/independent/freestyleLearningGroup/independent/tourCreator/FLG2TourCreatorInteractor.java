package freestyleLearningGroup.independent.tourCreator;


/**
 * This interface has to be implemented by classes that want to use a tour creator.
 * @author  Steffen Wachenfeld
 */
public interface FLG2TourCreatorInteractor {
    
    /**
     * Returns the information on the currently displayed element
     */
    FLG2TourCreatorElementInformation getCurrentFLG2TourCreatorElementInformation();
    
    void displayFLG2TourCreatorElementInformation(FLG2TourCreatorElementInformation a_elementInformation);
    
}
