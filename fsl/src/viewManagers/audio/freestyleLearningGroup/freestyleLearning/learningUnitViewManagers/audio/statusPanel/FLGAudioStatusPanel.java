package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.statusPanel;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewAdapter;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;
import freestyleLearning.learningUnitViewAPI.statusPanel.FSLAbstractLearningUnitViewStatusPanel;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.events.learningUnitViewEvent.FLGAudioViewEvent;
import freestyleLearningGroup.independent.util.FLGInternationalization;

/**
 * This class shows the status of an played audio-element when in audio-view.
 * @author Freestyle Learning Group
 * @author Gunnar Thies
 * @version 1.0, 15/04/03
 */
public class FLGAudioStatusPanel extends FSLAbstractLearningUnitViewStatusPanel {
    private FLGInternationalization internationalization;
    private String status = "";

    /** Constructor of this class. */
    public FLGAudioStatusPanel() {
        internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.statusPanel.internationalization",
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
            learningUnitViewManager.addLearningUnitViewListener(new FLGAudioStatusPanel_LearningUnitViewAdapter());
    }

    /** Method to build the panels components. */
    public void buildDependentUI() {
        if (activeLearningUnitViewElementId == null)
            setText(internationalization.getString("text.welcome"));
    }

    /**
     * Adapter-Class, to receive events on pressed buttons in elementInteractionPanel.
     * So the actual time of the played sample can be showed.
     */
    class FLGAudioStatusPanel_LearningUnitViewAdapter extends FSLLearningUnitViewAdapter {
        //events which are fired in elementInteractionPanel are received here:
        public void learningUnitViewSpecificEventOccurred(FSLLearningUnitViewEvent e) {
            FLGAudioViewEvent audioViewEvent = (FLGAudioViewEvent)e;
            if (audioViewEvent.getEventSpecificType() == FLGAudioViewEvent.AUDIO_PLAYING_TIME_PROGRESS) {
                setText(internationalization.getString("text.playing") + " " + audioViewEvent.actualAudioPlayingTime + "/" +
                    audioViewEvent.maximumAudioPlayingTime + " " + internationalization.getString("text.seconds"));
            }
            if (audioViewEvent.getEventSpecificType() == FLGAudioViewEvent.AUDIO_RECORD_BUTTON_PRESSED) {
                setText("Recording...");
            }
            if (audioViewEvent.getEventSpecificType() == FLGAudioViewEvent.AUDIO_END_OF_AUDIO_REACHED) {
                setText("");
            }
            if (audioViewEvent.getEventSpecificType() == FLGAudioViewEvent.AUDIO_STOP_BUTTON_PRESSED) {
                setText("");
            }
        }
    }
}
