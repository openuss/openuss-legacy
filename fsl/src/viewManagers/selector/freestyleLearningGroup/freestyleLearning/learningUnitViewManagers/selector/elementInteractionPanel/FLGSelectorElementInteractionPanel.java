package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.elementInteractionPanel;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.elementInteractionPanel.FSLAbstractLearningUnitViewElementInteractionPanel;
import freestyleLearning.learningUnitViewAPI.elementInteractionPanel.FSLLearningUnitViewElementInteractionButton;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEvent;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitVetoableAdapter;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewVetoableAdapter;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBindingSubclasses.FLGSelectorElement;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.dialogs.FLGSelectorPlayerConfigDialog;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.dialogs.FLGSelectorStartDialog;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.events.FLGSelectorEvent;
import freestyleLearningGroup.independent.gui.FLGImageUtility;
import freestyleLearningGroup.independent.util.FLGInternationalization;

/**
 * FLGSelectorElementInteractionPanel.
 * Manager Class for Selector Interaction Panel Elements.
 * If multi player mode shall be activated uncomment line in method
 * insertViewSpecificInteractionComponents().
 * @author Carsten Fiedler
 */
public class FLGSelectorElementInteractionPanel extends FSLAbstractLearningUnitViewElementInteractionPanel {
    private FLGInternationalization internationalization;
    private FSLLearningUnitViewElementInteractionButton playButton;
    private FSLLearningUnitViewElementInteractionButton stopButton;
    private FSLLearningUnitViewElementInteractionButton multiplayerButton;
    private FLGSelectorStartDialog startDialog;
    private FLGSelectorPlayerConfigDialog playerDialog;
    // play mode is flag for single and multi player mode
    private boolean playMode = false;
    private boolean multiPlayerRun = false;
    // variables for user selection
    private int width = 6;
	private int height = 6;
	private int gameSpeed = 2;
	private double gameLength = 1.0;
    
    /**
     * Constructor.
     * Sets Internationalization and inits start dialog.
     */
    public FLGSelectorElementInteractionPanel() {
        internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.elementInteractionPanel.internationalization",
        getClass().getClassLoader());
      	startDialog = new FLGSelectorStartDialog();
      	playerDialog = new FLGSelectorPlayerConfigDialog();
      	playerDialog.init();
    }
    
    /**
     * Inits Interaction Panel.
     * @param FSLLearningUnitViewManager learningUnitViewManager
     * @param FSLLearningUnitEventGenerator learningUnitEventGenerator
     * @param boolean editMode
     */
    public void init(FSLLearningUnitViewManager learningUnitViewManager,
    FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
        super.init(learningUnitViewManager,learningUnitEventGenerator,editMode);
        learningUnitViewManager.addLearningUnitViewListener(new FLGSelectorLearningUnitViewAdapter());
        learningUnitEventGenerator.addLearningUnitListener(new FLGSelector_LearningUnitAdapter());
    }
    
    /**
     * Sets specific Interaction Buttons,
     * Play- and Stop-Button.
     */
    protected void insertViewSpecificInteractionComponents() {
    	// add playButton
        playButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonPlay.gif"));
        playButton.setToolTipText(internationalization.getString("button.tooltip.play"));
        playButton.addActionListener(
        new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                play();
            }
        });
        multiplayerButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonMultiplayer.gif"));
        multiplayerButton.setToolTipText(internationalization.getString("button.tooltip.multiplayer"));
        multiplayerButton.addActionListener(
        new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playMultiplayer();
            }
        });
        // enable play buttons if user selected text view element,
        // if grid objects exist and user did not activate edit mode
        String id = learningUnitViewManager.getActiveLearningUnitViewElementId();
        learningUnitViewElementsManager = learningUnitViewManager.getLearningUnitViewElementsManager();
        if (id!=null && learningUnitViewElementsManager!=null) {
        	FLGSelectorElement viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewElement(id,false);
        	if (viewElement!=null) {
	        	if (!viewElement.getFolder() && !editMode
	            		&& !(viewElement.getLearningUnitViewElementGridObjects().isEmpty())) {
	            	if(playMode){
	                	playButton.setEnabled(false);
	                	multiplayerButton.setEnabled(false);
	                } else {
	                	playButton.setEnabled(true);
	                	multiplayerButton.setEnabled(true);
	                }
	            }
        	}
        } else {
        	playButton.setEnabled(false);
        	multiplayerButton.setEnabled(false);
        }
        // add stopButton
        stopButton = new FSLLearningUnitViewElementInteractionButton(loadImage("stopButton.gif"));
        stopButton.setToolTipText(internationalization.getString("button.tooltip.stop"));
        stopButton.addActionListener(
        new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        }
        );
        if(playMode){
        	stopButton.setEnabled(true);
        } else {
        	stopButton.setEnabled(false);
        }
        if (editMode) {
        	playButton.setEnabled(false);
        	multiplayerButton.setEnabled(false);
        }
        // add buttons into panel
        add(playButton);
        
        /** if multi player mode shall be activated, then uncomment this line **/
        //add(multiplayerButton);
        
        add(stopButton);
    }
    
    protected void setFullScreenMode(boolean fullScreenRequested) {
        this.fullScreenSelected = fullScreenRequested;
        FSLLearningUnitViewEvent event = FSLLearningUnitViewEvent.createFullScreenModeChangedEvent(learningUnitViewManager.getLearningUnitViewManagerId(),
            activeLearningUnitViewElementId, fullScreenRequested, true);
        learningUnitViewManager.fireLearningUnitViewEvent(event);
    }
    
    private void play() {
        // open dialog for selector start
    	startDialog.init(width, height, gameSpeed, gameLength);
        if(startDialog.userWantsToPlay()) {
            playMode = true;
            setFullScreenModeAllowed(true);
            // get user selection
            width = startDialog.getSelectedPlayGroundWidth();
            height = startDialog.getSelectedPlayGroundHeight();
            gameSpeed = startDialog.getSelectedGameSpeed();
            gameLength = startDialog.getSelectedGameLength();
            buildDependentUI();
            // load playground into content panel and start music panel
            FSLLearningUnitViewEvent selectorEvent = FLGSelectorEvent.createViewSpecificEvent(FLGSelectorEvent.SELECTOR_PLAY_MODE_ENTERED,
            		width, height, gameSpeed, gameLength);
            learningUnitViewManager.fireLearningUnitViewEvent(selectorEvent);
        }
    }
    
    private void playMultiplayer() {
    	// open dialog for setting up players
    	if(playerDialog.showDialog()) {
    		startDialog.init(width,height,gameSpeed,gameLength);
            if(startDialog.userWantsToPlay()) {
            	multiPlayerRun=true;
	    		playMode=true;
	            setFullScreenModeAllowed(true);
	            // get user selection
	            width = startDialog.getSelectedPlayGroundWidth();
	            height = startDialog.getSelectedPlayGroundHeight();
	            gameSpeed = startDialog.getSelectedGameSpeed();
	            gameLength = startDialog.getSelectedGameLength();
	            buildDependentUI();
	            // load playground into content panel and start music panel
	            FSLLearningUnitViewEvent selectorEvent = FLGSelectorEvent.createViewSpecificEvent(FLGSelectorEvent.SELECTOR_MULTI_PLAYER_MODE_ENTERED,
	            		width, height, gameSpeed, gameLength, playerDialog.getPlayers());
	            learningUnitViewManager.fireLearningUnitViewEvent(selectorEvent);
            }
    	}
    }

    private void stop() {
    	// stop selector run
        setFullScreenModeAllowed(false);
        playMode=false;
        buildDependentUI();
        FSLLearningUnitViewEvent selectorStatusPanelEvent = FLGSelectorEvent.createViewSpecificEvent(FLGSelectorEvent.SELECTOR_RESET_STATUS_PANEL);
        learningUnitViewManager.fireLearningUnitViewEvent(selectorStatusPanelEvent);
        FSLLearningUnitViewEvent selectorEvent = FLGSelectorEvent.createViewSpecificEvent(FLGSelectorEvent.SELECTOR_PLAY_MODE_EXITED);
        learningUnitViewManager.fireLearningUnitViewEvent(selectorEvent);
    }
    
    private void stopMultiPlayerRun(boolean isLastMultiPlayerRun) {
    	multiPlayerRun=false;
        setFullScreenModeAllowed(true);
        buildDependentUI();
        FSLLearningUnitViewEvent selectorStatusPanelEvent = FLGSelectorEvent.createViewSpecificEvent(
        		FLGSelectorEvent.SELECTOR_RESET_STATUS_PANEL);
        learningUnitViewManager.fireLearningUnitViewEvent(selectorStatusPanelEvent);
        FSLLearningUnitViewEvent selectorEvent = FLGSelectorEvent.createViewSpecificEvent(
        		isLastMultiPlayerRun,FLGSelectorEvent.SELECTOR_MULTI_PLAYER_RUN_EXITED);
        learningUnitViewManager.fireLearningUnitViewEvent(selectorEvent);
    }
    
    private Image loadImage(String imageFileName) {
        return FLGImageUtility.loadImageAndWait(getClass().getResource("images/" + imageFileName));
    }
    
    /**
     * FLGSelectorLearningUnitViewAdapter.
     * Inner class for learning unit view event handling.
     */
    class FLGSelectorLearningUnitViewAdapter extends FSLLearningUnitViewVetoableAdapter {
    	public void learningUnitViewDeactivated(FSLLearningUnitViewEvent event) {
      		setFullScreenModeAllowed(false);
      		if (activeLearningUnitViewElementId!=null) {
	      		FLGSelectorElement viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewElement(activeLearningUnitViewElementId,false);
	      		playMode=false;
	      		if (viewElement!=null) {
		      		if (viewElement.getLearningUnitViewElementGridObjects().isEmpty()) {
		      			playButton.setEnabled(false);
		      		}
		      	} else {
		      		playButton.setEnabled(true);
		      	}
	      		stopButton.setEnabled(false);
		        multiplayerButton.setEnabled(true);
		        buildDependentUI();
      		}
     	}
    	
        public void learningUnitViewSpecificEventOccurred(FSLLearningUnitViewEvent event) {
            FLGSelectorEvent selectorEvent = (FLGSelectorEvent)event;
            if (selectorEvent.getEventSpecificType() == FLGSelectorEvent.SELECTOR_PLAY_MODE_TIMEOUT) {
            	stop();
            }
            if (selectorEvent.getEventSpecificType() == FLGSelectorEvent.SELECTOR_MULTI_PLAYER_RUN_TIMEOUT) {
            	stopMultiPlayerRun(selectorEvent.getLastMultiPlayerRun());
            }
            if (selectorEvent.getEventSpecificType() == FLGSelectorEvent.SELECTOR_MULTI_PLAYER_MODE_TIMEOUT) {
    			setFullScreenModeAllowed(false);
    			playMode=false;
    			buildDependentUI();
            }
            if (selectorEvent.getEventSpecificType() == FLGSelectorEvent.SELECTOR_USER_ROLE_CHANGED) {
            	// check, if view element has elements for playing
            	learningUnitViewElementsManager = learningUnitViewManager.getLearningUnitViewElementsManager();
            	if (learningUnitViewElementsManager!=null) {
	            	FLGSelectorElement viewElement = (FLGSelectorElement) learningUnitViewElementsManager.getLearningUnitViewElement(activeLearningUnitViewElementId,false);
	            	if (viewElement!=null) {
	            		if (viewElement.getLearningUnitViewElementGridObjects().isEmpty()) {
	            			playButton.setEnabled(false);
	            			stopButton.setEnabled(true);
	            			multiplayerButton.setEnabled(false);
	            			buildDependentUI();
	            		}
	            	}
            	}
            }
        }
    }
    
    class FLGSelector_LearningUnitAdapter extends FSLLearningUnitVetoableAdapter {
        public void learningUnitEditModeChanged(FSLLearningUnitEvent event) {
        	editMode = event.isEditMode();
            	if(editMode) {
            		setFullScreenModeAllowed(false);
	                playButton.setEnabled(false);
	                stopButton.setEnabled(false);
	                multiplayerButton.setEnabled(false);
	                buildDependentUI();
	            } else {
	               	buildDependentUI();
	            }
        }
    }
}
