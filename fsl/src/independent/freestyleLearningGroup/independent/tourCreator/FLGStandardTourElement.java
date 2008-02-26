package freestyleLearningGroup.independent.tourCreator;

import java.awt.Image;
//from Java Media Framework
import java.io.File;

import freestyleLearningGroup.independent.gui.FLGImageUtility;


/**
 * This class represents the standard implementation of a tour element.
 * @author Steffen Wachenfeld
 */
public class FLGStandardTourElement implements FLGTourElement {
    
    /**
     * Link Target represented by this tour element.
     */
    private FLG2TourCreatorElementInformation m_elementInformation = null;
    
    /**
     * This elements description text. Will be initialized by the get method if necessary.
     */
    private String ms_description;
    
    /**
     * time in seconds for which this element will be shown in presentation mode
     */
    private int mi_displayTime = FLGTourElement.UNLIMITED_DISPLAY_TIME;
    
    /**
     * Name of the tour element. Will be initialized by the get method if necessary.
     */
    private String ms_elementName;
    
    /**
     * Associated Media File for this toiur element (e.g. sound or video file)
     */
    private File m_associatedMediaFile;
    private int mb_mediaPlaybackMode = FLGTourElement.MEDIA_AUTO_PLAYBACK_MODE_NEVER;  //automatically starts playback
    private boolean mb_nextElementOnEndOfMedia = true;  //if timeflow enabled, shows next tour element if playback ends
    private boolean mb_waitForEndOfMedia       = true;  //if timeflow enabled, waits for Media playback to end
    
    /**
     * Small image of this tour element. Will be generated using the normal image at element creation time.
     */
    private Image m_smallImage = null;
    
    /**
     * Image used for multiple-element property window
     */
    private static Image m_multipleElementImage = null;
    private static String ms_multipleElementImageFilename = "freestyleLearningGroup/independent/tourCreator/images/tourCreatorIcon.gif";
    
    
    /**
     * Creates a new instance of FLGStandardTourELement
     */
    public FLGStandardTourElement() {
    }//constructor
    
    
    /**
     * Creates a small version (64x64) of the normal associated image.
     */
    public void deriveSmallImage(){       
        //take image and create small image (smooth scaling)
        java.awt.Image l_tempImage = this.m_elementInformation.getElementImage();
        if(l_tempImage.getHeight(null) >= l_tempImage.getWidth(null))
            this.m_smallImage = l_tempImage.getScaledInstance(-1, 64, Image.SCALE_SMOOTH);
        else
            this.m_smallImage = l_tempImage.getScaledInstance(64, -1, Image.SCALE_SMOOTH);
        this.m_smallImage.flush();
        l_tempImage.flush();
    }//deriveSmallImage
    
    
    /**
     * Returns the File object of the media associated to this tour element.
     * @return the File object associated to this tour element, can be null.
     */
    public File getAssociatedMediaFile(){
        return this.m_associatedMediaFile;
    }//getAssociatedMedia
    
    
    /**
     * Returns the description text of this element.
     * If necessary e.g. if description text is null, the text will be set to a default description.
     * @return the elements description text
     */
    public String getDescription() {
        if(this.ms_description == null){
            //setting description to default
            this.ms_description = FLGStandardTourCreator.getInternationalization().getString("standardTourElement.initialDescription");
        }//if
        return this.ms_description;
    }//getDescription
    
    
    /**
     * This method returns the display time of this element. Time is an int value, correct use of this int
     * is possible using the constants defined in interface FLGTourElement.
     * @return the display time of the element
     */
    public int getDisplayTime() {
        return this.mi_displayTime;
    }//getDisplayTime
    
    
    /**
     * Returns the (user given) Name of this element. If the element name is null, it will be set to a default name.
     * @return the elements name
     */
    public String getElementName() {
        if(this.ms_elementName == null) {
            //setting to default name
            this.ms_elementName = FLGStandardTourCreator.getInternationalization().getString("standardTourElement.initialElementName");
            //adding the Freestyle internal name
            try {
                this.ms_elementName += " (" + this.m_elementInformation.getTargetLearningUnitViewElementName() + ")";
            }
            catch(Exception e) {
                System.out.println("FLGStandardTourElement.getElementName(): " + e + " reading element information");                
            }
        }//if
        return this.ms_elementName;
    }//getElementName
    
    
    /**
     * This method returns the FLG2TourCreatorElementInformation object of this tour element.
     * @return FLG2TourCreatorElementInformation object of this tour element
     */
    public FLG2TourCreatorElementInformation getFLG2TourCreatorElementInformation() {
        return this.m_elementInformation;
    }//getFLG2TourCreatorElementInformation
    
    
    /**
     * Returns the image of this tour element.
     * @return the image associated to this tour element.
     */
    public Image getImage() {
        return this.m_elementInformation.getElementImage();
    }//getImage
    
    
    /**
     * Returns the media playback mode
     */
    public int getMediaPlaybackMode() {
        return this.mb_mediaPlaybackMode;
    }//getMediaPlaybackMode
    
    /**
     * Returns the image used for multiple-element property window.
     * Method will cause short delay if called for the first time because the multiple element image will then be loaded on demand.
     * @return the image used to represent all selections of multiple tour elements
     */
    public Image getMultipleElementImage() {
        //load multiple element image if not yet loaded (e.g. if requested for the fist time)
        if(m_multipleElementImage==null)
            m_multipleElementImage = FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource(ms_multipleElementImageFilename));
        
        return m_multipleElementImage;
    }//getMultipleElementImage
    
    
    /**
     * Gets the value which determines if the next element will be diaplayed if End of Media is reached.
     * @return true if enabled, else false
     */
    public boolean getNextElementOnEndOfMedia() {
        return this.mb_nextElementOnEndOfMedia;
    }//getNextElementOnEndOfMedia
    
    /**
     * Returns the small image of this tour element.
     * @return a small (64x64) version of the image associated to this tour element.
     */
    public Image getSmallImage() {
        return this.m_smallImage;
    }//getSmallImage
    
    
    /**
     * Gets the value which determines if the next element will be shown before End of Media is reached.
     * @return true: wait for end of media, false: display next element immediately after display time is over.
     */
    public boolean getWaitForEndOfMedia() {
        return this.mb_waitForEndOfMedia;
    }//getWaitForAudio
    
    
    /**
     * Sets the media File object which is associated to this tour element.
     * @param a_massociatedMediaFile Media file that shall be associated to this tour element, may be null.
     */
    public void setAssociatedMediaFile(File a_associatedMediaFile) {
        //in any case, null reference will leave this tour element without associated media
        this.m_associatedMediaFile = a_associatedMediaFile;
    }//setAssociatedMedia
    
    /**
     * Sets the description text of this element if not null. Empty String as description is allowed.
     * @param as_descriptionText this elements description.
     */
    public void setDescription(String as_descriptionText) {
        //check if description text is not null, empty String allowed.
        if(as_descriptionText != null)
            this.ms_description = as_descriptionText;
    }//setDescription
    
    
    /**
     * This method set the display time of this element. Time is an int value, correct use of this int
     * is possible using the constants defined in interface FLGTourElement.
     * @param ai_displayTime the time the element shall be displayed.
     */
    public void setDisplayTime(int ai_displayTime) {
        if(ai_displayTime < 0)
            this.mi_displayTime = FLGTourElement.UNLIMITED_DISPLAY_TIME;
        else
            this.mi_displayTime = ai_displayTime;
    }//setDisplayTime
    
    
    
    /**
     * Sets the elements name. Will not set the name if argument is null or an empty String.
     * @param as_newElementName the new element name.
     */
    public void setElementName(String as_newElementName) {
        //check if provided name is not null and not empty
        if(as_newElementName != null && as_newElementName.length() > 0)
            this.ms_elementName = as_newElementName;
    }//setElementName
    
    
    /**
     * This method sets the tour elements FLG2TourCreatorElementInformation object.
     * @param a_elementInformation FLG2TourCreatorElementInformation object that shall be connected to this tour element
     */
    public void setFLG2TourCreatorElementInformation(FLG2TourCreatorElementInformation a_elementInformation) {
        this.m_elementInformation = a_elementInformation;
    }//setFLG2TourCreatorElementInformation
    
    
    /**
     * This method will set the image of this tour element. The small image will automatically be derived.
     * @param a_image the given image
     */
    public void setImage(Image a_image) {
        this.m_elementInformation.setElementImage(a_image);
        this.deriveSmallImage();
    }//setImage
    
    
    /**
     * Sets the media playback mode. Mode constants are located in FLGTourElement.
     * @param a_mediaPlaybackMode the mode to be set.
     */
    public void setMediaPlaybackMode(int a_mediaPlaybackMode) {
        this.mb_mediaPlaybackMode = a_mediaPlaybackMode;
    }//getMediaPlaybackMode
    
    
    /**
     * Sets the value which determines if the next element will be diaplayed if End of Media is reached.
     * @param ab_nextElementOnEndOfMedia true to enable, false to disable
     */
    public void setNextElementOnEndOfMedia(boolean ab_nextElementOnEndOfMedia) {
        this.mb_nextElementOnEndOfMedia = ab_nextElementOnEndOfMedia;
    }//setNextElementOnEndOfMedia
    
    
    /**
     * Sets the value which determines if the next element will be shown before End of Media is reached.
     * @param ab_waitForEndOfMedia true to wait for end of media, false to display next element immediately after display time is over.
     */
    public void setWaitForEndOfMedia(boolean ab_waitForEndOfMedia) {
        this.mb_waitForEndOfMedia = ab_waitForEndOfMedia;
    }//setWaitForEndOfMedia
    
}//class
