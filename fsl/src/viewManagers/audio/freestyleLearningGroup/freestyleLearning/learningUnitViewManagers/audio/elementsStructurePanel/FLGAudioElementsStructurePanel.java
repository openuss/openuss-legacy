package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.elementsStructurePanel;

import java.util.*;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElement;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementsManager;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.elementsStructurePanel.FSLAbstractLearningUnitViewElementsStructurePanel;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBindingSubclasses.FLGAudioElement;
import freestyleLearningGroup.independent.util.FLGInternationalization;

/**
 * This class builds the structure-panel and the element-tree
 * @author Freestyle Learning Group
 * @author Gunnar Thies
 * @version 1.0, 15/04/03
 */
public class FLGAudioElementsStructurePanel extends FSLAbstractLearningUnitViewElementsStructurePanel {
    private FLGInternationalization internationalization;

    public void init(FSLLearningUnitViewManager learningUnitViewManager,
        FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
            super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
            super.createRemoveOnlyEditToolBar();
            internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.internationalization",
                getClass().getClassLoader());
    }

    public void modifyLearningUnitViewElement(FSLLearningUnitViewElement element) {
        FLGAudioElement audioElement = (FLGAudioElement)element;
        audioElement.setLastModificationDate(String.valueOf(new Date().getTime()));
        if (audioElement.getFolder()) audioElement.setType(FLGAudioElement.ELEMENT_TYPE_FOLDER);
        else
            audioElement.setType(FLGAudioElement.ELEMENT_TYPE_AUDIO_ELEMENT);
    }

    public FSLLearningUnitViewElement createLearningUnitViewElement(String id, String parentId, String title, 
    		boolean folder) {
        FLGAudioElement newElement = new FLGAudioElement();
        // setting FSLLearningUnitViewElement attributes
        newElement.setId(id);
        newElement.setParentId(parentId);
        newElement.setTitle(title);
        newElement.setFolder(folder);
        newElement.setLastModificationDate(String.valueOf(new Date().getTime()));
        if (folder) newElement.setType(FLGAudioElement.ELEMENT_TYPE_FOLDER);
        else
            newElement.setType(FLGAudioElement.ELEMENT_TYPE_AUDIO_ELEMENT);
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
                String lastModificationDate = targetParentElement.getLastModificationDate();
                if (targetParentId.equals("none")) {
                    parentId = "_topLevelFolder@" + targetLearningUnitViewManagerId;
                }
                else {
                    parentId = targetParentElement.getParentId() + "@" + targetLearningUnitViewManagerId;
                }
                String title = targetParentElement.getTitle();
                if (learningUnitViewManager.getLearningUnitViewElementsManager().getLearningUnitViewElement(id, false)
                    == null) {
                        FLGAudioElement parentFolderElement =
                            (FLGAudioElement)createLearningUnitViewElement(id, parentId, title, true);
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
                    FLGAudioElement parentFolderElement =
                        (FLGAudioElement)createLearningUnitViewElement(id, "none", title, true);
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
            String elementTitle = targetTitle;
            String elementId = targetLearningUnitViewElementId + "@" +
                targetLearningUnitViewManager.getLearningUnitViewManagerId();
            // create parent elements
            createParentHierarchy(targetLearningUnitViewManager, targetElement);
            // create new audio element
            String targetElementParentId = targetElement.getParentId();
            String elementParentId = null;
            if (targetElementParentId.equals("none")) {
                elementParentId = "_topLevelFolder@" + targetLearningUnitViewManager.getLearningUnitViewManagerId();
            }
            else {
                elementParentId = targetElement.getParentId() + "@" +
                    targetLearningUnitViewManager.getLearningUnitViewManagerId();
            }
            FLGAudioElement newContextDependentElement =
                (FLGAudioElement)createLearningUnitViewElement(elementId, elementParentId, elementTitle, false);
            // set attributes of audio element
            newContextDependentElement.setTargetViewElementId(targetLearningUnitViewElementId);
            newContextDependentElement.setTargetViewManagerId(targetLearningUnitViewManager.getLearningUnitViewManagerId());
            newContextDependentElement.setHtmlFileName(null);
            newContextDependentElement.setSoundFileName(null);
            newContextDependentElement.setModified(true);
            learningUnitViewElementsManager.setModified(true);
            learningUnitViewElementsManager.insertLearningUnitViewElementAfter(newContextDependentElement, null);
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
