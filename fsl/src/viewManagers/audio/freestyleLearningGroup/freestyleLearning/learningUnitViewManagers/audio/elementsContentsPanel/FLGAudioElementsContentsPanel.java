package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.elementsContentsPanel;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementsManager;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.elementsContentsPanel.FSLAbstractLearningUnitViewElementContentPanel;
import freestyleLearning.learningUnitViewAPI.elementsContentsPanel.FSLAbstractLearningUnitViewElementsContentsPanel;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewVetoableAdapter;
import freestyleLearning.learningUnitViewAPI.util.FSLLearningUnitViewUtilities;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBindingSubclasses.FLGAudioElement;

/**
 * @author Freestyle Learning Group
 * @author Gunnar Thies
 * @version 1.0, 15/04/03
 */
public class FLGAudioElementsContentsPanel extends FSLAbstractLearningUnitViewElementsContentsPanel {
    private FLGAudioElementContentPanel[] audioElementContentPanels;

    /**
     * Initializes this class.
     * @param learningUnitViewManager the learning unit view manager
     * @param learningUnitEventGenerator the learning unit events generator.
     * @param editMode the edit mode.
     */
    public void init(FSLLearningUnitViewManager learningUnitViewManager,
        FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
            super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
            learningUnitViewManager.addLearningUnitViewListener(new FLGAudioElementsContentsPanel_LearningUnitViewAdapter());
            audioElementContentPanels = new FLGAudioElementContentPanel[2];
            audioElementContentPanels[0] = new FLGAudioElementContentPanel();
            audioElementContentPanels[1] = new FLGAudioElementContentPanel();
            for (int i = 0; i < audioElementContentPanels.length; i++) {
                audioElementContentPanels[i].init(learningUnitViewManager, learningUnitEventGenerator, editMode);
            }
    }

    public void setLearningUnitViewElementsManager(FSLLearningUnitViewElementsManager learningUnitViewElementsManager) {
        for (int i = 0; i < 2; i++) {
            audioElementContentPanels[i].setLearningUnitViewElementsManager(learningUnitViewElementsManager);
        }
        super.setLearningUnitViewElementsManager(learningUnitViewElementsManager);
    }

    public void updateUI() {
        for (int i = 0; i < 2; i++) {
            if (audioElementContentPanels != null) audioElementContentPanels[i].updateUI();
        }
        super.updateUI();
    }

    public void updateContentsPanel() {
        for (int i = 0; i < 2; i++) {
            if (audioElementContentPanels != null) audioElementContentPanels[i].buildDependentUI(true);
        }
    }

    protected FSLAbstractLearningUnitViewElementContentPanel
        getElementContentPanel(int index, String learningUnitViewElementId) {
            FLGAudioElement learningUnitViewElement =
                (FLGAudioElement)learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewElementId, false);
            if (learningUnitViewElement != null) {
                if (learningUnitViewElement.getFolder()) {
                    return null;
                }
                else {
                    return audioElementContentPanels[index];
                }
            }
            else
                return null;
    }

    class FLGAudioElementsContentsPanel_LearningUnitViewAdapter extends FSLLearningUnitViewVetoableAdapter {
        public void learningUnitViewElementLinksRemoved(FSLLearningUnitViewEvent event) {
            if (event.getLearningUnitViewManagerId().equals(learningUnitViewManager.getLearningUnitViewManagerId())) {
                FLGAudioElement element = (FLGAudioElement)learningUnitViewElementsManager.getLearningUnitViewElement(event.getLearningUnitViewElementId(), true);
                if (element.getHtmlFileName() != null) {
                    element.setHtmlFileName(FSLLearningUnitViewUtilities.updateExternalHtmlFilesOnLinksRemovedEvent(event,
                        learningUnitViewElementsManager, element, element.getHtmlFileName(), "audio", ".html"));
                }
            }
        }
    }
}
