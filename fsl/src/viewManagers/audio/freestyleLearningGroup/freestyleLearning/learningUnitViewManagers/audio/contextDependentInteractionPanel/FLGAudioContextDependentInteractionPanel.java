package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.contextDependentInteractionPanel;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElement;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementsManager;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.contextDependentInteractionPanel.FSLAbstractLearningUnitViewContextDependentInteractionPanel;
import freestyleLearning.learningUnitViewAPI.elementInteractionPanel.FSLLearningUnitViewElementInteractionButton;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.elementInteractionPanel.FLGAudioElementInteractionPanel;
import freestyleLearningGroup.independent.gui.FLGImageUtility;
import freestyleLearningGroup.independent.util.FLGInternationalization;

/**
 * This class builds the ContextDependentInteractionPanel, which is able to play
 * soundfiles while beeing in another view than the Audio-View.
 * @author Gunnar Thies
 * @version 1.0, 15/04/03
 */
public class FLGAudioContextDependentInteractionPanel extends FSLAbstractLearningUnitViewContextDependentInteractionPanel {
    private FSLLearningUnitViewElementInteractionButton playButton, stopButton, pauseButton;
    private FLGInternationalization internationalization;
    private FLGAudioElementInteractionPanel elementInteractionPanel;
    private FSLLearningUnitViewManager manager;
    private FSLLearningUnitViewElement activeElement;
    private JPanel contextDependentInteractionPanel;
    private JPanel interactionButtons;
    private FSLLearningUnitEventGenerator _learningUnitEventGenerator;
    private boolean playing = false;
    private boolean pausedPlaying = false;

    /**
     * Initializes this class.
     * @param learningUnitViewManager the learning unit view manager
     * @param learningUnitEventGenerator the learning unit events generator.
     * @param editMode the edit mode.
     */
    public void init(FSLLearningUnitViewManager learningUnitViewManager,
        FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
            internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.contextDependentInteractionPanel.internationalization",
                getClass().getClassLoader());
            manager = learningUnitViewManager;
            _learningUnitEventGenerator = learningUnitEventGenerator;
            buildDependentUI();
    }

    /**
     * Method for enabling or diabling all buttons at once.
     * @param b false for disable, true for enable all three buttons
     */
    private void enableButtons(boolean b) {
        playButton.setEnabled(b);
        stopButton.setEnabled(b);
        pauseButton.setEnabled(b);
    }

    /** Method for building the panel with its buttons. */
    protected void buildDependentUI() {
        //setLayout(new FlowLayout());
        setVisible(true);
        setOpaque(false);
        try {
            playButton = new FSLLearningUnitViewElementInteractionButton(loadImage("playButton.gif"));
            playButton.setToolTipText(internationalization.getString("button.tooltip.play"));
            stopButton = new FSLLearningUnitViewElementInteractionButton(loadImage("stopButton.gif"));
            stopButton.setToolTipText(internationalization.getString("button.tooltip.stop"));
            pauseButton = new FSLLearningUnitViewElementInteractionButton(loadImage("pauseButton.gif"));
            pauseButton.setToolTipText(internationalization.getString("button.tooltip.pause"));
            playButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //asks for elementInteractionPanel, to use its funktionalities.
                        elementInteractionPanel = (FLGAudioElementInteractionPanel)manager.getElementInteractionPanel();
                        //boolean for the status of playing
                        playing = true;
                        playButton.setEnabled(false);
                        pauseButton.setEnabled(true);
                        //start thread which waits for the end of the sample.
                        startPlayWatcherThread();
                        //use the function of the play-button of elementInteractionPanel
                        elementInteractionPanel.play();
                    }
                });
            stopButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //asks for elementInteractionPanel, to use its funktionalities.
                        elementInteractionPanel = (FLGAudioElementInteractionPanel)manager.getElementInteractionPanel();
                        playButton.setEnabled(true);
                        pauseButton.setEnabled(true);
                        //use the function of the stop-button of elementInteractionPanel
                        elementInteractionPanel.stop();
                    }
                });
            pauseButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //asks for elementInteractionPanel, to use its funktionalities.
                        elementInteractionPanel = (FLGAudioElementInteractionPanel)manager.getElementInteractionPanel();
                        pausedPlaying = true;
                        playing = false;
                        pauseButton.setEnabled(false);
                        playButton.setEnabled(true);
                        //use the function of the pause-button of elementInteractionPanel
                        elementInteractionPanel.pause();
                    }
                });
            //adds the three buttons to the panel.
            
            add(new JLabel("Audio Notiz anhören"));
            
            add(playButton);
            add(pauseButton);
            add(stopButton);
        } catch (Exception e) { }
    }

    /** Method for setting the interaction-buttons. Is used by elementIneractionPanel, when smaple is paused or stopped... */
    public void setContextDependentInteractionPanelButtonStatus() {
        elementInteractionPanel = (FLGAudioElementInteractionPanel)manager.getElementInteractionPanel();
        if (elementInteractionPanel.isPlaying() == true) {
            enableButtons(true);
            playButton.setEnabled(false);
        }
        else if (elementInteractionPanel.isPaused() == true) {
            enableButtons(true);
            pauseButton.setEnabled(false);
        }
        else if (elementInteractionPanel.isRecording() == true) {
            enableButtons(false);
            stopButton.setEnabled(true);
        }
        else
            enableButtons(true);
    }

    /** This method implements a thread, which waits for the end of playing a sample. After that all buttons can be enabled ! */
    private void startPlayWatcherThread() {
        Thread thread = new Thread() {
            public void run() {
                //returns, if the sample is over...then it leaves the while-prozedure
                while (elementInteractionPanel.sampleOver() != true) {
                    try {
                        sleep(1000);
                    }
                    catch (InterruptedException e) {
                        break;
                    }
                }
                enableButtons(true);
            }
        };
        thread.start();
    }

    public void setLearningUnitViewElementsManager(FSLLearningUnitViewElementsManager learningUnitViewElementsManager) {
    }

    private Image loadImage(String imageFileName) {
        return FLGImageUtility.loadImageAndWait(getClass().getResource("images/" + imageFileName));
    }

    public void updateUI() {
    }
}
