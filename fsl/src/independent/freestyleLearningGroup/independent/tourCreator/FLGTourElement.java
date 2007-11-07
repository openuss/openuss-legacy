package freestyleLearningGroup.independent.tourCreator;


import java.awt.Image;




/**
 * This interface has to be implemented by all customized FLG tour element classes.
 * @author Steffen Wachenfeld
 */
public interface FLGTourElement {
    
    /**
     * Display Time
     */
    //smallest increment of display time on sliders etc.
    public static final int DELTA_DISPLAY_TIME     =     1;
    //used for elements that shall be hidden
    public static final int HIDDEN_DISPLAY_TIME    =     0;
    //max. finite display time
    public static final int MAX_DISPLAY_TIME       =  3600;
    //min. finite and positive display time
    public static final int MIN_DISPLAY_TIME       =     1;
    //default display time
    public static final int PRESET_DISPLAY_TIME    =    10;
    //used for elements that shall be shown unlimited time
    public static final int UNLIMITED_DISPLAY_TIME =    -1;
    
    /**
     * Media Playback Modes
     */
    //never automatically starts playback
    public static final int MEDIA_AUTO_PLAYBACK_MODE_NEVER = 0;
    //automatically starts playback if timeflow enabled
    public static final int MEDIA_AUTO_PLAYBACK_MODE_IF_TIMEFLOW_ENABLED = 1;
    //always automatically starts playback
    public static final int MEDIA_AUTO_PLAYBACK_MODE_ALWAYS = 2;
    
    /**
     * Returns the description text of this element
     * @return this elements description
     */
    public String getDescription();
    
    
    /**
     * This method returns the display time of this element. Time is an int value (10th of a second).
     * Values of 0 will be interpreted as hidden Element, values <0 will cause display for unlimited time.
     * @return the display time of the element
     */
    public int getDisplayTime();
    
    
    /**
     * Returns the (user given) Name of this element.
     * @return The elements name
     */
    public String getElementName();
    
    
    /**
     * This method returns the LinkTarget object of a tour element
     * @return link target object
     */
    public FLG2TourCreatorElementInformation getFLG2TourCreatorElementInformation();
    
    
    /**
     * Returns the image of this tour element
     */
    public Image getImage();
    
    
    /**
     * Returns the small image of this tour element
     */
    public Image getSmallImage();
    
    /**
     * Sets the description text of this element
     * @param as_descriptionText this elements description
     */
    public void setDescription(String as_descriptionText);
    
    /**
     * This method set the display time of this element. Time is given as int value in 10th of a second.
     * Values of 0 will be interpreted as hidden Element, values <0 will be displayed for unlimited time.
     * @param ai_displayTime the time the element shall be displayed in 10th of a second.
     */
    public void setDisplayTime(int ai_displayTime);
    
    
    /**
     * Sets the elements name.
     * @param as_newElementName the new element name
     */
    public void setElementName(String as_newElementName);
    
    
    /**
     * This method sets the tour elements linkTarget object. Only one Element can be registered!
     */
    public void setFLG2TourCreatorElementInformation(FLG2TourCreatorElementInformation a_elementInformation);
 
    
    /**
     * This method will set the image of this tour element. The small image will automatically be derived.
     * @param image the given image
     */
    public void setImage(Image a_image);
}
