package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.elementInteractionPanel;

import java.awt.Image;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import freestyleLearning.learningUnitViewAPI.*;
import freestyleLearning.learningUnitViewAPI.elementInteractionPanel.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.contextDependentInteractionPanel.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBinding.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBindingSubclasses.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.elementInteractionPanel.dialogs.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.elementsContentsPanel.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.events.learningUnitViewEvent.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.statusPanel.*;
import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.independent.media.*;
import freestyleLearningGroup.independent.util.*;

/**
 * This class adds control-buttons, to control the content of the audio-manager.
 * With these buttons you can play, record and open existing media files and assign them to an element of the learning unit.
 * @author Gunnar Thies
 */
public class FLGAudioElementInteractionPanel extends FSLAbstractLearningUnitViewElementInteractionPanel {
    private FSLLearningUnitViewElementInteractionButton openMediaButton, playButton, stopButton, pauseButton, recordButton;
    private FLGInternationalization internationalization;
    private FLGMediaPlayer player;
    private File currentAudioFile;
    private FLGAudioRecorder recorder;
    private FLGAudioChooseFileDialog chooseFilePanel;
    private FLGAudioFileOverwriteDialog overwriteFilePanel;
    private File mediaFile;
    private String mediaFileName;
    private boolean overwriteIt;
    private FSLLearningUnitViewManager manager;
    private String soundFileName;
    private Audio actualAudio;
    private boolean deleted = true;
    private boolean recording = false;
    private boolean pausedPlaying = false;
    private boolean playing = false;
    private String activatedLearningUnitViewElementId;
    private FLGAudioElementsContentsPanel content;
    private FLGAudioStatusPanel statusPanel;
    private boolean sampleOver;
    private FLGAudioContextDependentInteractionPanel contextDependentInteractionPanel;

    /**
     * FLGAudioElementInteractionPanel-Constructor
     * The internationalization is loaded...
     */
    public FLGAudioElementInteractionPanel() {
        internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.elementInteractionPanel.internationalization",
            getClass().getClassLoader());
    }

    /**
     * Initializes this class.
     * @param learningUnitViewManager the learning unit view manager
     * @param learningUnitEventGenerator the learning unit events generator.
     * @param editMode the edit mode.
     */
    public void init(FSLLearningUnitViewManager learningUnitViewManager,
        FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
            super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
            manager = learningUnitViewManager;
            learningUnitViewManager.addLearningUnitViewListener(new FLGAudioElementInteractionPanel_Adapter());
    }

    /** This method adds the button sto the panel. */
    protected void insertViewSpecificInteractionComponents() {
        add(recordButton);
        addSeparator();
        add(playButton);
        add(pauseButton);
        add(stopButton);
        addSeparator();
        add(openMediaButton);
    }

    /** This method configures the buttons and adds ActionListeners to them. */
    protected void buildIndependentUI() {
        super.buildIndependentUI();
        openMediaButton = new FSLLearningUnitViewElementInteractionButton(loadImage("openMediaButton.gif"));
        openMediaButton.setToolTipText(internationalization.getString("button.tooltip.openMedia"));
        recordButton = new FSLLearningUnitViewElementInteractionButton(loadImage("recordButton.gif"));
        recordButton.setToolTipText(internationalization.getString("button.tooltip.record"));
        playButton = new FSLLearningUnitViewElementInteractionButton(loadImage("playButton.gif"));
        playButton.setToolTipText(internationalization.getString("button.tooltip.play"));
        stopButton = new FSLLearningUnitViewElementInteractionButton(loadImage("stopButton.gif"));
        stopButton.setToolTipText(internationalization.getString("button.tooltip.stop"));
        pauseButton = new FSLLearningUnitViewElementInteractionButton(loadImage("pauseButton.gif"));
        pauseButton.setToolTipText(internationalization.getString("button.tooltip.pause"));
        enableButtons(false);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
        recordButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    record();
                }
            });
        playButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    play();
                }
            });
        stopButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    stop();
                }
            });
        pauseButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    pause();
                }
            });
        openMediaButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    openMediaFile();
                }
            });
    }

    protected void buildDependentUI() {
        super.buildDependentUI();
    }

    /** Opens a ChooseMediFileDialog */
    private void openMediaFile() {
        showChooseMediaFileDialog();
    }

    public boolean isPlaying() {
        return playing;
    }

    public boolean isPaused() {
        return pausedPlaying;
    }

    public boolean isRecording() {
        return recording;
    }

    /**
     * Method for enable or disable all buttons.
     * @param enabled true for enable, false for disable all the buttons.
     */
    private void enableButtons(boolean enabled) {
        openMediaButton.setEnabled(enabled);
        recordButton.setEnabled(enabled);
        playButton.setEnabled(enabled);
        stopButton.setEnabled(!enabled);
        pauseButton.setEnabled(!enabled);
    }

    private Image loadImage(String imageFileName) {
        return FLGImageUtility.loadImageAndWait(getClass().getResource("images/" + imageFileName));
    }

    public boolean sampleOver() {
        return sampleOver;
    }

    /** Method for the play-button. The audio-file (if there is one) of an element is played. */
    public void play() {
        sampleOver = false;
        //get the actual AudioElement
        FLGAudioElement actualAudio = (FLGAudioElement)learningUnitViewElementsManager.getLearningUnitViewElement(activeLearningUnitViewElementId, false);
        //get its Soundfilename
        String soundFileName = actualAudio.getSoundFileName();
        //Thread for displaying the actual time of the played sample in the status-panel
        Thread showTime;
        showTime = new Thread() {
            public void run() {
                while (playing == true | pausedPlaying == true) {
                    SwingUtilities.invokeLater(
                        new Runnable() {
                            public void run() {
                                int time = (int)player.getActualPlayingTime();
                                FSLLearningUnitViewEvent audioViewEvent =
                                    FLGAudioViewEvent.createViewSpecificEvent(FLGAudioViewEvent.AUDIO_PLAYING_TIME_PROGRESS);
                                ((FLGAudioViewEvent)audioViewEvent).actualAudioPlayingTime = time;
                                ((FLGAudioViewEvent)audioViewEvent).maximumAudioPlayingTime = player.getMaximumPlayingTime();
                                learningUnitViewManager.fireLearningUnitViewEvent(audioViewEvent);
                            }
                        });
                    try {
                        sleep(1000);
                    }
                    catch (InterruptedException e) {
                        break;
                    }
                }
            }
        };
        //when pause was pressed before, just play along
        if (pausedPlaying == true) {
            playButton.setEnabled(false);
            pauseButton.setEnabled(true);
            player.start();
            pausedPlaying = false;
            playing = true;
        }
        //Look, if there is a soundfile specified...
        else {
            if (soundFileName != null) {
                //location of the soundfile
                File soundFile = learningUnitViewElementsManager.resolveRelativeFileName(soundFileName, actualAudio);
                //disable other buttons
                enableButtons(false);
                pauseButton.setEnabled(true);
                stopButton.setEnabled(true);
                try {
                    //new player-object
                    player = new FLGMediaPlayer();
                    //load mediafile
                    player.loadMedia(soundFile);
                    playing = true;
                    //start player
                    player.start();
                    //start the time-displaying thread
                    showTime.start();
                    //add a medialistener
                    player.addMediaListener(
                        new FLGMediaListener() {
                            public void endOfMediaReached() {
                                player.stop();
                                playing = false;
                                sampleOver = true;
                                //update buttons in cdi-panel
                                updateCDIPanel();
                                //fire end-event
                                FSLLearningUnitViewEvent audioViewEvent =
                                    FLGAudioViewEvent.createViewSpecificEvent(FLGAudioViewEvent.AUDIO_END_OF_AUDIO_REACHED);
                                learningUnitViewManager.fireLearningUnitViewEvent(audioViewEvent);
                                //update elementInteractionPanelButtons
                                actualizeButtons();
                            }
                        });
                } catch (Exception e) { System.out.println("Error in MediaPlayer"); }
            }
            else { System.out.println("No soundfile available!"); }
        }
    }

    /** Method for update the buttons. */
    private void actualizeButtons() {
        FLGAudioElement audioElement = (FLGAudioElement)learningUnitViewElementsManager.getLearningUnitViewElement(activeLearningUnitViewElementId, false);
        if(audioElement != null) {
	        //if the audio-element is a folder
	        if (audioElement.getFolder()) {
	            enableButtons(false);
	            stopButton.setEnabled(false);
	            pauseButton.setEnabled(false);
	            if (playing == true) {
	                stopButton.setEnabled(true);
	                pauseButton.setEnabled(true);
	                playButton.setEnabled(false);
	            }
	            else if (pausedPlaying == true) {
	                stopButton.setEnabled(true);
	                playButton.setEnabled(true);
	                pauseButton.setEnabled(false);
	            }
	            else if (recording == true) {
	                stopButton.setEnabled(true);
	                playButton.setEnabled(false);
	                pauseButton.setEnabled(false);
	            }
	        }
	        else //it is not a folder
	        {
	            enableButtons(false);
	            if (playing == true) {
	                stopButton.setEnabled(true);
	                pauseButton.setEnabled(true);
	                playButton.setEnabled(false);
	            }
	            else if (pausedPlaying == true) {
	                stopButton.setEnabled(true);
	                playButton.setEnabled(true);
	                pauseButton.setEnabled(false);
	            }
	            else if (recording == true) {
	                stopButton.setEnabled(true);
	                playButton.setEnabled(false);
	                pauseButton.setEnabled(false);
	            }
	            else {
	                Audio actualAudio = (Audio)learningUnitViewElementsManager.getLearningUnitViewElement(activeLearningUnitViewElementId, false);
	                enableButtons(true);
	                //if there is no soundfile available, the playbutton is disabled
	                if (actualAudio.getSoundFileName() == null) { playButton.setEnabled(false); }
	            }
	        }
        }
    }

    /** Method for updating the CDI-Panel */
    private void updateCDIPanel() {
        contextDependentInteractionPanel = (FLGAudioContextDependentInteractionPanel)manager.getContextDependentInteractionPanel();
        contextDependentInteractionPanel.setContextDependentInteractionPanelButtonStatus();
    }

    /** Method for pausing the audiofile. */
    public void pause() {
        if (playing == true) {
            enableButtons(false);
            stopButton.setEnabled(true);
            playButton.setEnabled(true);
            pauseButton.setEnabled(false);
            player.pause();
            playing = false;
            pausedPlaying = true;
        }
        updateCDIPanel();
    }

    /** Method for updating the contentsPanel */
    public void actualizeContentsPanel() {
        content = (FLGAudioElementsContentsPanel)learningUnitViewManager.getElementsContentsPanel();
        content.updateContentsPanel();
    }

    /**
     * Method for record an audio element. If a soundfile is already there, a new one is created and set as
     * soundfilename for the chosen element
     */
    private void record() {
        //The actual audio element
        Audio actualAudio = (Audio)learningUnitViewElementsManager.getLearningUnitViewElement(activeLearningUnitViewElementId, false);
        //File for later use
        File soundFile = new File("");
        //overwriteIt is set to true, then (if there is no soundfile yet) the if-clause will be accessed
        overwriteIt = true;
        //if there is an soundfile already...this dialog asks if it should be replaced.
        if (actualAudio.getSoundFileName() != null) {
            overwriteFilePanel = new FLGAudioFileOverwriteDialog();
            //overwrite true says overwrite the existing soundfile, else the if-clause will not be accessed.
            overwriteIt = overwriteFilePanel.showDialog(learningUnitViewElementsManager, activeLearningUnitViewElementId);
        } 
        //if-clause for the creating of a soundfile
        if (overwriteIt) {
            //from the audio element, a FLGAudioElement is get
            FLGAudioElement learningUnitViewElement = (FLGAudioElement)actualAudio;
            //the viewElementId of the activeLearningUnitViewElement
            String elementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
            //if there is no soundfile
            if (learningUnitViewElement.getSoundFileName() == null) {
                //new soundfile (in *.wav-format) is created in the specific folder (author-/ or learner-directory)
                soundFile = learningUnitViewElementsManager.createNewFileForElementsExternalData(FLGAudioElement.ELEMENT_TYPE_AUDIO_ELEMENT,
                    ".wav", activeLearningUnitViewElementId);
                //the element-soundfilename is set to the soundfile's name
                learningUnitViewElement.setSoundFileName(soundFile + "");
                
                System.out.println("Recording: " + soundFile.getAbsolutePath());
                
            }
            //if there is a soundfile already
            else {
                //new soundfile must be created
                //get the relative filename, this varies from author- to learner-mode
                String relativeFileName = learningUnitViewElementsManager.getRelativeFileNameVersionForWriting(learningUnitViewElement.getSoundFileName(),
                    learningUnitViewElement, FLGAudioElement.ELEMENT_TYPE_AUDIO_ELEMENT, ".wav");
                //if the relative soundfilename starts with "author", we are in the author-mode (or administrator!)
                if (relativeFileName.startsWith("author")) {
                    //create new soundfile with the specific pre- and suffixes in the original-data-directory.
                    soundFile = FLGFileUtility.createNewFile("author_" + FLGAudioElement.ELEMENT_TYPE_AUDIO_ELEMENT,
                        ".wav", learningUnitViewElementsManager.getLearningUnitViewOriginalDataDirectory());
                }
                //if not in auhtor-mode, we are a learner
                else if (relativeFileName.startsWith("learner")) {
                    ////create new soundfile with the specific pre- and suffixes in the user-data-directory
                    soundFile = FLGFileUtility.createNewFile("learner_" + FLGAudioElement.ELEMENT_TYPE_AUDIO_ELEMENT,
                        ".wav", learningUnitViewElementsManager.getLearningUnitViewUserDataDirectory());
                }
                //the name of the soundfile is set to the elements-soundfilename
                learningUnitViewElement.setSoundFileName("" + soundFile);
            }
            recorder = new FLGAudioRecorder(soundFile.getAbsolutePath());
            recording = true;
            //disable other buttons (not stop)
            enableButtons(false);
            stopButton.setEnabled(true);
            //start recording
            recorder.startRecording();
            //fire event that says, that the recording has begun
            FSLLearningUnitViewEvent audioViewEvent =
                FLGAudioViewEvent.createViewSpecificEvent(FLGAudioViewEvent.AUDIO_RECORD_BUTTON_PRESSED);
            learningUnitViewManager.fireLearningUnitViewEvent(audioViewEvent);
            //says the manager-class that something changed at the elements
            setUpdated();
            //update contents-panel that the soundfile is shown immediately
            actualizeContentsPanel();
            //actualize buttons ;-)
            actualizeButtons();
            actualAudio.setSoundFileName(soundFile.getName());
        }
        //nothing will be recorder, because overwrite was false.
    }

    /** Method to say, that some elements are changed by the user, and saving is needed. */
    private void setUpdated() {
        learningUnitViewElementsManager.setOriginalElementsModified(true);
        learningUnitViewElementsManager.setUserElementsModified(true);
    }

    /** Method for stop the playing of an audiofile. */
    public void stop() {
        FSLLearningUnitViewEvent audioViewEvent =
            FLGAudioViewEvent.createViewSpecificEvent(FLGAudioViewEvent.AUDIO_STOP_BUTTON_PRESSED);
        learningUnitViewManager.fireLearningUnitViewEvent(audioViewEvent);
        if (playing == true) {
            playing = false;
            player.stop();
            player = null;
        }
        else if (pausedPlaying == true) {
            pausedPlaying = false;
            player.stop();
        }
        else if (recording == true) {
            recording = false;
            recorder.stopRecording();
        }
        //update buttons and cdi-panel
        actualizeButtons();
        updateCDIPanel();
    }

    /** Method for showing the ChooseMediaFIleDialog. */
    private void showChooseMediaFileDialog() {
        actualizeContentsPanel();
        FLGAudioElement actualAudio = (FLGAudioElement)learningUnitViewElementsManager.getLearningUnitViewElement(activeLearningUnitViewElementId, false);
        String soundFileName = actualAudio.getSoundFileName();
        chooseFilePanel = new FLGAudioChooseFileDialog();
        //if the user pressed on OK after the choosing of a file then the name of the soundfile is set
        //otherwise nothing happens.
        if (chooseFilePanel.showDialog(learningUnitViewElementsManager, activeLearningUnitViewElementId, soundFileName)) {
            
        	mediaFile = chooseFilePanel.getMediaFileName();
        	if(mediaFile != null) {
            mediaFileName = mediaFile.getName();
            //the name of the soundfile attached to the element must be updated
            if (mediaFileName != null) {
                //if there was a changing, set the new soundfilename
//                File destinationMediaFile = learningUnitViewElementsManager.createNewFileForElementsExternalData("audio",
//                    getExtension(mediaFileName), activeLearningUnitViewElementId);
                File destinationMediaFile = learningUnitViewElementsManager.resolveRelativeFileName(mediaFileName, actualAudio);
                // copy source media file to learning unit view directory
                FLGUIUtilities.startLongLastingOperation();
                FLGFileUtility.copy(mediaFile, destinationMediaFile);             
                FLGUIUtilities.stopLongLastingOperation();
                actualAudio.setSoundFileName(destinationMediaFile.getName());
                setUpdated();
                //and the content-panel is updated too, to show the new name of the attached soundfile
                actualizeContentsPanel();
            }
        	
        
        //actualize buttons anyway
        actualizeButtons();
        setUpdated();
        	}
        }
    }
    
    private String getExtension(String fileName) {
        int extensionStartsAt = fileName.lastIndexOf(".");
        return fileName.substring(extensionStartsAt);
    }

    class FLGAudioElementInteractionPanel_Adapter extends FSLLearningUnitViewAdapter {
        public void learningUnitViewElementActivated(FSLLearningUnitViewEvent event) {
            if (event.getLearningUnitViewManagerId().equals(learningUnitViewManager.getLearningUnitViewManagerId())) {
                activeLearningUnitViewElementId = event.getActiveLearningUnitViewElementId();
                if (activeLearningUnitViewElementId != null && learningUnitViewElementsManager != null) {
                    FLGAudioElement audioElement = (FLGAudioElement)learningUnitViewElementsManager.getLearningUnitViewElement(activeLearningUnitViewElementId, false);
                    if (audioElement != null) {
                        actualizeButtons();
                    }
                }
            }
        }
        public void learningUnitViewDeactivated(FSLLearningUnitViewEvent event) { 
        	stop();
        }
    }
}
