/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.notes.elementsContentsPanel;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementsManager;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.elementsContentsPanel.FSLAbstractLearningUnitViewElementContentPanel;
import freestyleLearning.learningUnitViewAPI.elementsContentsPanel.FSLAbstractLearningUnitViewElementsContentsPanel;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewVetoableAdapter;
import freestyleLearning.learningUnitViewAPI.util.FSLLearningUnitViewUtilities;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.notes.data.xmlBindungSubclasses.FLGNoteElement;

public class FLGNotesElementsContentsPanel extends FSLAbstractLearningUnitViewElementsContentsPanel {
    private FLGNotesNoteElementContentPanel[] noteElementContentPanels;

    public void init(FSLLearningUnitViewManager learningUnitViewManager,
        FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
            super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
            learningUnitViewManager.addLearningUnitViewListener(new FLGNotesElementsContentsPanel_LearningUnitViewAdapter());
            noteElementContentPanels = new FLGNotesNoteElementContentPanel[2];
            noteElementContentPanels[0] = new FLGNotesNoteElementContentPanel();
            noteElementContentPanels[1] = new FLGNotesNoteElementContentPanel();
            for (int i = 0; i < noteElementContentPanels.length; i++) {
                noteElementContentPanels[i].init(learningUnitViewManager, learningUnitEventGenerator, editMode);
            }
    }

    public void setLearningUnitViewElementsManager(FSLLearningUnitViewElementsManager learningUnitViewElementsManager) {
        for (int i = 0; i < 2; i++) {
            noteElementContentPanels[i].setLearningUnitViewElementsManager(learningUnitViewElementsManager);
        }
        super.setLearningUnitViewElementsManager(learningUnitViewElementsManager);
    }

    public void updateUI() {
        for (int i = 0; i < 2; i++) {
            if (noteElementContentPanels != null) noteElementContentPanels[i].updateUI();
        }
        super.updateUI();
    }

    protected FSLAbstractLearningUnitViewElementContentPanel
        getElementContentPanel(int index, String learningUnitViewElementId) {
            FLGNoteElement learningUnitViewElement =
                (FLGNoteElement)learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewElementId, false);
            if (learningUnitViewElement != null) {
                if (learningUnitViewElement.getFolder()) {
                    return null;
                }
                else {
                    return noteElementContentPanels[index];
                }
            }
            else
                return null;
    }

    class FLGNotesElementsContentsPanel_LearningUnitViewAdapter extends FSLLearningUnitViewVetoableAdapter {
        public void learningUnitViewElementLinksRemoved(FSLLearningUnitViewEvent event) {
            if (event.getLearningUnitViewManagerId().equals(learningUnitViewManager.getLearningUnitViewManagerId())) {
                FLGNoteElement element = (FLGNoteElement)learningUnitViewElementsManager.getLearningUnitViewElement(event.getLearningUnitViewElementId(), true);
                if (element.getHtmlFileName() != null) {
                    element.setHtmlFileName(FSLLearningUnitViewUtilities.updateExternalHtmlFilesOnLinksRemovedEvent(event,
                        learningUnitViewElementsManager, element, element.getHtmlFileName(), "note", ".html"));
                }
            }
        }
    }
}