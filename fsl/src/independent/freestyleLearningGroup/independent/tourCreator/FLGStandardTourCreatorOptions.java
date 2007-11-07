package freestyleLearningGroup.independent.tourCreator;

/**
 * This class is used to represent the set options of a StandardTourCreator. 
 * @author Steffen Wachenfeld
 */
public class FLGStandardTourCreatorOptions {
    
    //ask before options
    private boolean mb_askBeforeDeleteElements = true;
    
    //double click on element action
    private int mi_doubleClickOnElementAction = FLGStandardTourCreator.ACTION_SHOW_ELEMENT;
    
    //remember to save options
    private boolean mb_rememberToSaveOnExitOption = true;
    private boolean mb_rememberToSaveOnNewOption = true;
    private boolean mb_rememberToSaveOnOpenOption = true;
    
    //Default Display time
    private int mi_defaultDisplayTime = FLGTourElement.PRESET_DISPLAY_TIME;
    
    //Show window on capture
    private boolean mb_showWindowOnPerformCaptureAction = true;
    
    public String ms_soundCapture    = "freestyleLearningGroup/independent/tourCreator/sounds/capture.wav";
    public String ms_soundSliderMove = "freestyleLearningGroup/independent/tourCreator/sounds/slider.wav";
    public String ms_soundDisplay    = "freestyleLearningGroup/independent/tourCreator/sounds/display.wav";
    public String ms_soundMove       = "freestyleLearningGroup/independent/tourCreator/sounds/move.wav";
    
    public boolean mb_playSoundCapture    = true;
    public boolean mb_playSoundSliderMove = false;
    public boolean mb_playSoundDisplay    = true;
    public boolean mb_playSoundMove       = true;
    
    /** Creates a new instance of FLGStandardTourCreatorOptions */
    public FLGStandardTourCreatorOptions() {
    }
    
    
    public boolean getAskBeforeDeleteElements()
    {
        return this.mb_askBeforeDeleteElements;
    }//getAskBeforeDelete
    
    
    public int getDefaultDisplayTime()
    {
        return this.mi_defaultDisplayTime;
    }//getDefaultDisplayTime
    
    
    public int getDoubleClickOnElementAction()
    {
        return this.mi_doubleClickOnElementAction;
    }//getDoubleClickOnElementAction
    
    
    public boolean getRememberToSaveOnExitOption()
    {
        return this.mb_rememberToSaveOnExitOption;
    }//getRememberToSaveOnExitOption
    
    
    public boolean getRememberToSaveOnNewOption()
    {
        return this.mb_rememberToSaveOnNewOption;
    }//getRememberToSaveOnNewOption
        
    
    public boolean getRememberToSaveOnOpenOption()
    {
        return this.mb_rememberToSaveOnOpenOption;
    }//getRememberToSaveOnOpenOption
    
    
    public boolean getShowWindowOnPerformCaptureAction()
    {
        return this.mb_showWindowOnPerformCaptureAction;
    }//getShowWindowOnPerformCaptureAction
    
    
    public void setAskBeforeDeleteElements(boolean lb_askBeforeDeleteElements)
    {
        this.mb_askBeforeDeleteElements = lb_askBeforeDeleteElements;
    }//setAskBeforeDeleteELements
    
    
    public void setDefaultDisplayTime(int ai_defaultDisplayTime)
    {
        this.mi_defaultDisplayTime = ai_defaultDisplayTime;
    }//setDefaultDisplayTime
    
    
    public void setDoubleClickOnElementAction(int ai_action)
    {
        this.mi_doubleClickOnElementAction = ai_action;
    }//setDoubleClickOnElementAction
    
    
    public void setRememberToSaveOnExitOption(boolean ab_rememberToSaveOnExit)
    {
        this.mb_rememberToSaveOnExitOption = ab_rememberToSaveOnExit;
    }//setRememberToSaveOnExitOption
    
    
    public void setRememberToSaveOnNewOption(boolean ab_rememberToSaveOnNew)
    {
        this.mb_rememberToSaveOnNewOption = ab_rememberToSaveOnNew;
    }//setRememberToSaveOnNewOption
        
    
    public void setRememberToSaveOnOpenOption(boolean ab_rememberToSaveOnOpen)
    {
        this.mb_rememberToSaveOnOpenOption = ab_rememberToSaveOnOpen;
    }//setRememberToSaveOnOpenOption
    
        
    public void setShowWindowOnPerformCaptureAction(boolean ab_showWindowOnPerformCaptureAction)
    {
        this.mb_showWindowOnPerformCaptureAction = ab_showWindowOnPerformCaptureAction;
    }//setShowWindowOnPerformCaptureAction
}
