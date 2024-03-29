/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro.elementInteractionPanel;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.elementInteractionPanel.FSLAbstractLearningUnitViewElementInteractionPanel;
import freestyleLearning.learningUnitViewAPI.elementInteractionPanel.FSLLearningUnitViewElementInteractionButton;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewAdapter;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro.data.xmlBindingSubclasses.FLGIntroElement;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro.events.learningUnitViewEvent.FLGIntroViewEvent;
import freestyleLearningGroup.independent.gui.FLGImageUtility;
import freestyleLearningGroup.independent.util.FLGInternationalization;

public class FLGIntroElementInteractionPanel extends FSLAbstractLearningUnitViewElementInteractionPanel {
    private FSLLearningUnitViewElementInteractionButton playButton;
    private FSLLearningUnitViewElementInteractionButton pauseButton;
    private FSLLearningUnitViewElementInteractionButton stopButton;
    private FLGInternationalization internationalization;
    private String selectedLearningUnitViewElementId;

    public FLGIntroElementInteractionPanel() {
        internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro.elementInteractionPanel.internationalization",
            getClass().getClassLoader());
        playButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonPlay.gif"));
        playButton.setToolTipText(internationalization.getString("button.tooltip.play"));
        pauseButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonPause.gif"));
        pauseButton.setToolTipText(internationalization.getString("button.tooltip.pause"));
        stopButton = new FSLLearningUnitViewElementInteractionButton(loadImage("buttonStop.gif"));
        stopButton.setToolTipText(internationalization.getString("button.tooltip.stop"));
        initButtonStatus();
        playButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    play();
                }
            });
        pauseButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    pause();
                }
            });
        stopButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    stop();
                }
            });
    }

    public void init(FSLLearningUnitViewManager learningUnitViewManager,
        FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
            super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
            learningUnitViewManager.addLearningUnitViewListener(new FLGIntroElementInteractionPanel_LearningUnitViewAdapter());
            this.setFullScreenModeAllowed(true);
            this.setScaleModeAllowed(true);
    }

    private void play() {
        playButton.setEnabled(false);
        pauseButton.setEnabled(true);
        stopButton.setEnabled(true);
        FSLLearningUnitViewEvent introViewEvent =
            FLGIntroViewEvent.createViewSpecificEvent(FLGIntroViewEvent.INTRO_PLAY_BUTTON_PRESSED);
        learningUnitViewManager.fireLearningUnitViewEvent(introViewEvent);
    }

    private void pause() {
        pauseButton.setEnabled(false);
        playButton.setEnabled(true);
        FSLLearningUnitViewEvent introViewEvent =
            FLGIntroViewEvent.createViewSpecificEvent(FLGIntroViewEvent.INTRO_PAUSE_BUTTON_PRESSED);
        learningUnitViewManager.fireLearningUnitViewEvent(introViewEvent);
    }

    private void stop() {
        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
        FSLLearningUnitViewEvent introViewEvent =
            FLGIntroViewEvent.createViewSpecificEvent(FLGIntroViewEvent.INTRO_STOP_BUTTON_PRESSED);
        learningUnitViewManager.fireLearningUnitViewEvent(introViewEvent);
    }

    protected void buildDependentUI() {
        super.buildDependentUI();
        if (learningUnitViewElementsManager != null) {
            FLGIntroElement introViewElement = (FLGIntroElement)learningUnitViewElementsManager.getLearningUnitViewElement(activeLearningUnitViewElementId, false);
            if (introViewElement != null) {
                if (introViewElement.getFolder()) {
                    enableButtons(false);
                }
            }
        }
        else {
            enableButtons(false);
        }
    }

    private void enableButtons(boolean enabled) {
        playButton.setEnabled(enabled);
        pauseButton.setEnabled(enabled);
        stopButton.setEnabled(enabled);
    }

    private void initButtonStatus() {
        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
    }

    protected void insertViewSpecificInteractionComponents() {
        add(playButton);
        add(pauseButton);
        add(stopButton);
    }

    private Image loadImage(String imageFileName) {
        return FLGImageUtility.loadImageAndWait(getClass().getResource("images/" + imageFileName));
    }

    class FLGIntroElementInteractionPanel_LearningUnitViewAdapter extends FSLLearningUnitViewAdapter {
        public void learningUnitViewDeactivated(FSLLearningUnitViewEvent event) {
            stop();
        }

        public void learningUnitViewSpecificEventOccurred(FSLLearningUnitViewEvent e) {
            FLGIntroViewEvent introViewEvent = (FLGIntroViewEvent)e;
            int introViewEventType = introViewEvent.getEventSpecificType();
            if (introViewEventType == FLGIntroViewEvent.INTRO_END_OF_VIDEO_REACHED) {
                initButtonStatus();
            }
        }

        public void learningUnitViewElementActivated(FSLLearningUnitViewEvent event) {
            if (!event.isFullScreenModeChanged()) {
                if (event.getLearningUnitViewManagerId().equals(learningUnitViewManager.getLearningUnitViewManagerId())) {
                    FLGIntroElement learningUnitViewElement =
                        (FLGIntroElement)learningUnitViewElementsManager.getLearningUnitViewElement(event.getActiveLearningUnitViewElementId(), false);
                    if (learningUnitViewElement != null && !learningUnitViewElement.getFolder()) {
                        initButtonStatus();
                    }
                    else {
                        enableButtons(false);
                    }
                }
            }
        }
    }
}
