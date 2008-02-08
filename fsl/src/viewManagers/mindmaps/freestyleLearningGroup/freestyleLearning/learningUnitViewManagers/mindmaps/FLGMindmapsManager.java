/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.util.Vector;
import javax.xml.bind.Dispatcher;

import freestyleLearning.learningUnitViewAPI.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.data.xmlBinding.*; 
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.data.xmlBindingSubclasses.*; 
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.elementInteractionPanel.*; 
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.elementsContentsPanel.*; 
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.elementsStructurePanel.*; 
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.statusPanel.*; 
import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.independent.util.*;

public class FLGMindmapsManager extends FSLAbstractLearningUnitViewManager {
    private final static float TOTAL_PROGRESS_STATUS_STEPS = 5;
    private FSLLearningUnitViewPrimaryActivationButton primaryActivationButton;
    private FLGInternationalization internationalization;
    
    public void init(FSLLearningUnitsActivator learningUnitsActivator,
            FSLLearningUnitViewsActivator learningUnitViewsActivator,
            FSLLearningUnitEventGenerator learningUnitEventGenerator,
            String learningUnitViewManagerId,
            String learningUnitViewManagerTitle,
            File learningUnitViewManagerCodeDirectory, boolean editMode,
            boolean originalElementsOnly,
            FLGLongLastingOperationStatus progressStatus) {
        super.init(learningUnitsActivator, learningUnitViewsActivator,
        learningUnitEventGenerator, learningUnitViewManagerId,
        learningUnitViewManagerTitle,
        learningUnitViewManagerCodeDirectory, editMode,
        originalElementsOnly, progressStatus);
        int stepSize = progressStatus.getStepSize();
        progressStatus.setStatusValue(progressStatus.getStatusValue() + (int) (stepSize / TOTAL_PROGRESS_STATUS_STEPS));
        internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.internationalization", getClass().getClassLoader());
        primaryActivationButton = new FSLLearningUnitViewPrimaryActivationButton(loadImage("primaryActivationButton.gif"));
        primaryActivationButton.setToolTipText(internationalization.getString("button.primaryActivation.toolTipText"));
        elementsStructurePanel = new FLGMindmapsElementsStructurePanel();
        elementsStructurePanel.init(this, learningUnitEventGenerator, editMode);
        elementsStructurePanel.setAutomaticActivation(true);
        progressStatus.setStatusValue(progressStatus.getStatusValue() + (int) (stepSize / TOTAL_PROGRESS_STATUS_STEPS));
        elementInteractionPanel = new FLGMindmapsElementInteractionPanel();
        elementInteractionPanel.init(this, learningUnitEventGenerator, editMode);
        progressStatus.setStatusValue(progressStatus.getStatusValue() + (int) (stepSize / TOTAL_PROGRESS_STATUS_STEPS));
        elementsContentsPanel = new FLGMindmapsElementsContentsPanel();
        elementsContentsPanel.init(this, learningUnitEventGenerator, editMode);
        progressStatus.setStatusValue(progressStatus.getStatusValue() + (int) (stepSize / TOTAL_PROGRESS_STATUS_STEPS));
        statusPanel = new FLGMindmapsStatusPanel();
        statusPanel.init(this, learningUnitEventGenerator, editMode);
        progressStatus.setStatusValue(progressStatus.getStatusValue() + (int) (stepSize / TOTAL_PROGRESS_STATUS_STEPS));
    }
    
    public URL getMainHelpPageUrl() {
        return getClass().getClassLoader().getResource("freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/mindmaps/help");
    }
    
    public FSLLearningUnitViewPrimaryActivationButton getPrimaryActivationButton() {
        return primaryActivationButton;
    }
    
    public java.awt.Component getPrintableComponent() {
        return elementsContentsPanel.getPrintableComponent();
    }    
    
    protected Dispatcher createDispatcher() {
        Dispatcher d = FLGMindmapsDescriptor.newDispatcher();
        d.register(MindmapsDescriptor.class, FLGMindmapsDescriptor.class);
        d.register(ViewElement.class, FLGMindmapsElement.class);
        d.register(ViewElementLink.class, FLGMindmapsElementLink.class);
        d.register(ViewElementLinkTarget.class, FLGMindmapsElementLinkTarget.class);
        return d;
    }
    
    public FSLLearningUnitViewElement findLearningUnitViewElement(String contentFileName) {
        String elementId = null;
        FSLLearningUnitViewElement element = null;
        if (learningUnitViewElementsManager != null) {
            String[] elementIds = learningUnitViewElementsManager
            .getAllLearningUnitViewElementIds();
            for (int i = 0; i < elementIds.length; i++) {
                FLGMindmapsElement mindmapsElement = (FLGMindmapsElement) learningUnitViewElementsManager
                .getLearningUnitViewElement(elementIds[i], false);
                if (mindmapsElement != null) {
                    if (mindmapsElement.getMindmapFileName() != null
                    && mindmapsElement.getMindmapFileName().equals(
                    contentFileName)) {
                        elementId = elementIds[i];
                        element = mindmapsElement;
                        break;
                    }
                }
            }
        }
        return element;
    }
    
    protected FSLLearningUnitViewXMLDocument createLearningUnitViewXMLDocument() {
        return new FLGMindmapsDescriptor();
    }
    
    private Image loadImage(String imageFileName) {
        return FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/mindmaps/images/" + imageFileName));
    }
    
    public void reloadData() {
        super.reloadLearningUnitViewData();
    }
    
    protected void removeUnnecessaryExternalElementFiles(boolean originalElements) {
        File directoryToClean;
        Vector learningUnitViewElements;
        if (originalElements) {
            directoryToClean = learningUnitViewElementsManager.getLearningUnitViewOriginalDataDirectory();
            learningUnitViewElements = learningUnitViewElementsManager.getLearningUnitViewOriginalElements();
        }
        else {
            directoryToClean = learningUnitViewElementsManager.getLearningUnitViewUserDataDirectory();
            learningUnitViewElements = learningUnitViewElementsManager.getLearningUnitViewUserElements();
        }
        Vector absoluteFilesToKeep = new Vector();
        absoluteFilesToKeep.add(new File(directoryToClean, LEARNING_UNIT_VIEW_DATA_FILENAME));
        for (int i = 0; i < learningUnitViewElements.size(); i++) {
            FSLLearningUnitViewElement learningUnitViewElement = (FSLLearningUnitViewElement)learningUnitViewElements.get(i);
            String[] relativePathsToKeep = learningUnitViewElement.getLearningUnitViewElementExternalFilesRelativePaths(learningUnitViewElementsManager);
            if (relativePathsToKeep != null) {
                for (int j = 0; j < relativePathsToKeep.length; j++) {
                    absoluteFilesToKeep.add(new File(directoryToClean, relativePathsToKeep[j]));
                }
            }
        }
        FLGFileUtility.cleanDirectory(directoryToClean, (File[]) absoluteFilesToKeep.toArray(
        new File[] { }));
    }
    
}