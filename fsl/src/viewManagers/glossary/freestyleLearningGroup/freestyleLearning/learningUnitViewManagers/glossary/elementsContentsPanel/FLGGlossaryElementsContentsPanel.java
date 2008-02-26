/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.glossary.elementsContentsPanel;

import freestyleLearning.learningUnitViewAPI.*;
import freestyleLearning.learningUnitViewAPI.elementsContentsPanel.*;
import freestyleLearning.learningUnitViewAPI.elementsContentsPanel.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.*;
import freestyleLearning.learningUnitViewAPI.util.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.glossary.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.glossary.data.xmlBindingSubclasses.*;

public class FLGGlossaryElementsContentsPanel extends FSLAbstractLearningUnitViewElementsContentsPanel {
    private FLGGlossaryTextElementContentPanel[] textElementContentPanels;
    private FLGMemoryContentPanel m_memoryContentPanel;
    
    public void init(FSLLearningUnitViewManager learningUnitViewManager, FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
        super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
        learningUnitViewManager.addLearningUnitViewListener(new FLGGlossaryElementsContentsPanel_LearningUnitViewAdapter());
        textElementContentPanels = new FLGGlossaryTextElementContentPanel[2];
        textElementContentPanels[0] = new FLGGlossaryTextElementContentPanel();
        textElementContentPanels[1] = new FLGGlossaryTextElementContentPanel();
        m_memoryContentPanel = new FLGMemoryContentPanel();
        m_memoryContentPanel.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
        for (int i = 0; i < 2; i++) {
            textElementContentPanels[i].init(learningUnitViewManager, learningUnitEventGenerator, editMode);
        }
    }
    
    public javax.swing.JEditorPane getHTMLContentPane() {
        return textElementContentPanels[0].getPrintableEditorPane();
    }

    public void setLearningUnitViewElementsManager(FSLLearningUnitViewElementsManager learningUnitViewElementsManager) {
        for (int i = 0; i < 2; i++) {
            textElementContentPanels[i].setLearningUnitViewElementsManager(
            learningUnitViewElementsManager);
        }
        m_memoryContentPanel.setLearningUnitViewElementsManager(learningUnitViewElementsManager);
        super.setLearningUnitViewElementsManager(learningUnitViewElementsManager);
    }
    
    public void updateUI() {
        for (int i = 0; i < 2; i++) {
            if (textElementContentPanels != null) {
                textElementContentPanels[i].updateUI();
            }
        }
        if (m_memoryContentPanel != null) {
            m_memoryContentPanel.updateUI();
        }
        super.updateUI();
    }
    
    protected FSLAbstractLearningUnitViewElementContentPanel getElementContentPanel(int index, String learningUnitViewElementId) {
        if (((FLGGlossaryManager) learningUnitViewManager).isInMemoryMode()) {
            return m_memoryContentPanel;
        }
        FLGGlossaryElement learningUnitViewElement = (FLGGlossaryElement) learningUnitViewElementsManager.
        getLearningUnitViewElement(learningUnitViewElementId, false);
        if (learningUnitViewElement != null) {
            if (learningUnitViewElement.getFolder()) {
                return null;
            }
            else {
                return textElementContentPanels[index];
            }
        }
        else {
            return null;
        }
    }
    
    class FLGGlossaryElementsContentsPanel_LearningUnitViewAdapter extends FSLLearningUnitViewVetoableAdapter {
        public void learningUnitViewElementLinksRemoved(FSLLearningUnitViewEvent event) {
            if (event.getLearningUnitViewManagerId().equals(learningUnitViewManager.
            getLearningUnitViewManagerId())) {
                FLGGlossaryElement element = (FLGGlossaryElement)
                learningUnitViewElementsManager.getLearningUnitViewElement(event.
                getLearningUnitViewElementId(), true);
                if (element.getHtmlFileName() != null) {
                    element.setHtmlFileName(FSLLearningUnitViewUtilities.updateExternalHtmlFilesOnLinksRemovedEvent(
                        event, learningUnitViewElementsManager, element, element.getHtmlFileName(), FLGGlossaryElement.ELEMENT_TYPE_TERM, ".html"));
                }
            }
        }
    }
}
