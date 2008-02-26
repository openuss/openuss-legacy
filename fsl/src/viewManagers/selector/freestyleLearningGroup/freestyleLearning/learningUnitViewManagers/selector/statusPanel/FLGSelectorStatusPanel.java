package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.statusPanel;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewAdapter;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;
import freestyleLearning.learningUnitViewAPI.statusPanel.FSLAbstractLearningUnitViewStatusPanel;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.events.FLGSelectorEvent;
import freestyleLearningGroup.independent.util.FLGInternationalization;

/**
 * FLGSelectorStatusPanel.
 * @author Carsten Fiedler
 */
public class FLGSelectorStatusPanel extends FSLAbstractLearningUnitViewStatusPanel {
    private FLGInternationalization internationalization;
    private boolean playModeIsActivated = false;
    private double gameLength = 0;
    private Timer timer;
    private int timerCounter=1;
    private AudioClip audioClip;

    /**
     * FLGSelectorStatusPanel.
     * Manager Class for Selector Status Panel.
     * @author Carsten Fiedler
     */
    public FLGSelectorStatusPanel() {
        internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.statusPanel.internationalization",
            getClass().getClassLoader());
    }
    
    /**
     * Inits Status Panel.
     * @param FSLLearningUnitViewManager learningUnitViewManager
     * @param FSLLearningUnitEventGenerator learningUnitEventGenerator
     * @param boolean editMode
     */
    public void init(FSLLearningUnitViewManager learningUnitViewManager,
            FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
        super.init(learningUnitViewManager,learningUnitEventGenerator,editMode);
        learningUnitViewManager.addLearningUnitViewListener(new FLGSelectorStatusPanel_LearningUnitViewAdapter());
        audioClip = java.applet.Applet.newAudioClip(
        		getClass().getClassLoader().getResource("freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/selector/statusPanel/sounds/lastSeconds.wav"));
    }
    
    /**
     * Builds dependent UI:
     * - Welcome text.
     * - Remaining time.
     * - View element title.
     */
	public void buildDependentUI() {
		int minutes=0;
		int seconds=0;
		int gameLengthInSeconds = (int) (gameLength*60);
		if (gameLengthInSeconds>0) {
			minutes = (int) ((gameLengthInSeconds-timerCounter)/60);
			seconds = (int) (gameLengthInSeconds-minutes*60-timerCounter);
		}
		if (activeLearningUnitViewElementId==null) {
            setText(internationalization.getString("text.welcome"));
        } else if (activeLearningUnitViewElementId!=null && playModeIsActivated) {
           	if (timerCounter>=gameLength*60-10) {
        		setForeground(Color.RED);
        		audioClip.play();
        	} 
           	if (seconds<10) {
        		setText(internationalization.getString("selector.statusPanel.text.time") + " " + Integer.toString(minutes) + ":0" + Integer.toString(seconds));
        	} else {
        		setText(internationalization.getString("selector.statusPanel.text.time") + " " + Integer.toString(minutes) + ":" + Integer.toString(seconds));
        	}
        } else if (activeLearningUnitViewElementId!=null && playModeIsActivated==false) {
        	setText(learningUnitViewElementsManager.getLearningUnitViewElement(activeLearningUnitViewElementId,false).getTitle());
        }
    }

	private void stopTimer() {
		if (timer!=null) {
			timer.stop();
		}
		timerCounter=1;
		setForeground(Color.BLACK);
	}
	
	private void startTimer() {
		// timer for setting remaining seconds
    	timer = new javax.swing.Timer(1000, new ActionListener() {  
    		int index = 0;
    		public void actionPerformed(ActionEvent e) {
            	buildDependentUI();
            	timerCounter++;
             }
         });
         timer.setRepeats(true);
         timer.start();
	}
	
	class FLGSelectorStatusPanel_LearningUnitViewAdapter extends FSLLearningUnitViewAdapter {
		public void learningUnitViewDeactivated(FSLLearningUnitViewEvent event) {
        	stopTimer();
        	playModeIsActivated=false;
        	buildDependentUI();
		}
		
        public void learningUnitViewSpecificEventOccurred(FSLLearningUnitViewEvent event) {
            FLGSelectorEvent selectorEvent = (FLGSelectorEvent)event;
            if (selectorEvent.getEventSpecificType()== FLGSelectorEvent.SELECTOR_PLAY_MODE_ENTERED) {
            	gameLength = selectorEvent.getGameLength();
            	playModeIsActivated=true;
            	startTimer();
            } 
            if (selectorEvent.getEventSpecificType()== FLGSelectorEvent.SELECTOR_MULTI_PLAYER_RUN_UPADTE_STATUS_PANEL) {
            	gameLength = selectorEvent.getGameLength();
            	playModeIsActivated=true;
            	startTimer();
            }
            if (selectorEvent.getEventSpecificType()==FLGSelectorEvent.SELECTOR_RESET_STATUS_PANEL) {
            	stopTimer();
            	playModeIsActivated=false;
            	buildDependentUI();
            } 
        }
    }    
}
