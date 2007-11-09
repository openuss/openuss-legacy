/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.notes.elementsStructurePanel;

import java.util.Date;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElement;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementsManager;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.elementsStructurePanel.FSLAbstractLearningUnitViewElementsStructurePanel;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.notes.data.xmlBindungSubclasses.FLGNoteElement;
import freestyleLearningGroup.independent.util.FLGInternationalization;

public class FLGNotesElementsStructurePanel extends FSLAbstractLearningUnitViewElementsStructurePanel {
    FLGInternationalization internationalization;

    public void init(FSLLearningUnitViewManager learningUnitViewManager,
        FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
            super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
            super.createRemoveOnlyEditToolBar();
            internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.notes.internationalization",
                getClass().getClassLoader());
    }

    public void modifyLearningUnitViewElement(FSLLearningUnitViewElement element) {
        FLGNoteElement noteElement = (FLGNoteElement)element;
        noteElement.setLastModificationDate(String.valueOf(new Date().getTime()));
        if (noteElement.getFolder()) noteElement.setType(FLGNoteElement.ELEMENT_TYPE_FOLDER);
        else
            noteElement.setType(FLGNoteElement.ELEMENT_TYPE_NOTE);
    }

    public FSLLearningUnitViewElement createLearningUnitViewElement(String id, String parentId, String title, boolean folder) {
        FLGNoteElement newElement = new FLGNoteElement();
        // setting FSLLearningUnitViewElement attributes
        newElement.setId(id);
        newElement.setParentId(parentId);
        newElement.setTitle(title);
        newElement.setFolder(folder);
        newElement.setLastModificationDate(String.valueOf(new Date().getTime()));
        if (folder) newElement.setType(FLGNoteElement.ELEMENT_TYPE_FOLDER);
        else
            newElement.setType(FLGNoteElement.ELEMENT_TYPE_NOTE);
        return newElement;
    }

    private void createParentHierarchy(FSLLearningUnitViewManager targetLearningUnitViewManager,
        FSLLearningUnitViewElement targetElement) {
            String targetLearningUnitViewManagerId = targetLearningUnitViewManager.getLearningUnitViewManagerId();
            if (!targetElement.getParentId().equals("none")) {
                // create target element's parent folder
                FSLLearningUnitViewElement targetParentElement =
                    targetLearningUnitViewManager.getLearningUnitViewElementsManager().getLearningUnitViewElement(targetElement.getParentId(), false);
                String id = targetParentElement.getId() + "@" + targetLearningUnitViewManagerId;
                String parentId;
                String targetParentId = targetParentElement.getParentId();
                if (targetParentId.equals("none")) {
                    parentId = "_topLevelFolder@" + targetLearningUnitViewManagerId;
                }
                else {
                    parentId = targetParentElement.getParentId() + "@" + targetLearningUnitViewManagerId;
                }
                String title = targetParentElement.getTitle();
                if (learningUnitViewManager.getLearningUnitViewElementsManager().getLearningUnitViewElement(id, false)
                    == null) {
                        FLGNoteElement parentFolderElement =
                            (FLGNoteElement)createLearningUnitViewElement(id, parentId, title, true);
                        parentFolderElement.setTargetViewElementId(targetParentElement.getId());
                        parentFolderElement.setTargetViewManagerId(targetLearningUnitViewManagerId);
                        parentFolderElement.setHtmlFileName(null);
                        parentFolderElement.setModified(true);
                        learningUnitViewElementsManager.setModified(true);
                        learningUnitViewElementsManager.insertLearningUnitViewElementAfter(parentFolderElement, null);
                        createParentHierarchy(targetLearningUnitViewManager, targetParentElement);
                }
            }
            else {
                String id = "_topLevelFolder@" + targetLearningUnitViewManagerId;
                if (learningUnitViewElementsManager.getLearningUnitViewElement(id, false) == null) {
                    // create top level folder for Learning Unit View
                    String title = targetLearningUnitViewManager.getLearningUnitViewManagerTitle();
                    FLGNoteElement parentFolderElement =
                        (FLGNoteElement)createLearningUnitViewElement(id, "none", title, true);
                    parentFolderElement.setTargetViewElementId("_topLevelFolder");
                    parentFolderElement.setTargetViewManagerId(targetLearningUnitViewManagerId);
                    parentFolderElement.setHtmlFileName(null);
                    parentFolderElement.setModified(true);
                    learningUnitViewElementsManager.setModified(true);
                    learningUnitViewElementsManager.insertLearningUnitViewElementAfter(parentFolderElement, null);
                }
            }
    }

    public FSLLearningUnitViewElement createContextDependentElement(FSLLearningUnitViewManager targetLearningUnitViewManager,
        String targetLearningUnitViewElementId) {
            FSLLearningUnitViewElementsManager targetElementsManager =
                targetLearningUnitViewManager.getLearningUnitViewElementsManager();
            FSLLearningUnitViewElement targetElement =
                targetElementsManager.getLearningUnitViewElement(targetLearningUnitViewElementId, false);
            String targetTitle = targetElement.getTitle();
            String elementTitle = internationalization.getString("text.noteTo") + targetTitle;
            String elementId = targetLearningUnitViewElementId + "@" +
                targetLearningUnitViewManager.getLearningUnitViewManagerId();
            // create parent elements
            createParentHierarchy(targetLearningUnitViewManager, targetElement);
            // create new note element
            String targetElementParentId = targetElement.getParentId();
            String elementParentId = null;
            if (targetElementParentId.equals("none")) {
                elementParentId = "_topLevelFolder@" + targetLearningUnitViewManager.getLearningUnitViewManagerId();
            }
            else {
                elementParentId = targetElement.getParentId() + "@" +
                    targetLearningUnitViewManager.getLearningUnitViewManagerId();
            }
            FLGNoteElement newContextDependentElement =
                (FLGNoteElement)createLearningUnitViewElement(elementId, elementParentId, elementTitle, false);
            // setting noteElement attributes
            newContextDependentElement.setTargetViewElementId(targetLearningUnitViewElementId);
            newContextDependentElement.setTargetViewManagerId(targetLearningUnitViewManager.getLearningUnitViewManagerId());
            newContextDependentElement.setHtmlFileName(null);
            newContextDependentElement.setModified(true);
            learningUnitViewElementsManager.setModified(true);
            learningUnitViewElementsManager.insertLearningUnitViewElementAfter(newContextDependentElement, null);
            // addLearningUnitViewElement(newContextDependentElement, false);
            FSLLearningUnitViewEvent event = FSLLearningUnitViewEvent.createElementsCreatedEvent(learningUnitViewManager.getLearningUnitViewManagerId(),
                new String[] { newContextDependentElement.getId() });
            learningUnitViewManager.fireLearningUnitViewEvent(event);
            event = FSLLearningUnitViewEvent.createElementActivatedEvent(learningUnitViewManager.getLearningUnitViewManagerId(),
                activeLearningUnitViewElementId, secondaryActiveLearningUnitViewElementId, false);
            learningUnitViewManager.fireLearningUnitViewEvent(event);
            activeLearningUnitViewElementId = newContextDependentElement.getId();
            return newContextDependentElement;
    }
}