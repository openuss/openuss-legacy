/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro;

import java.awt.Image;
import java.io.File;

import javax.xml.bind.Dispatcher;

import freestyleLearning.learningUnitViewAPI.FSLAbstractLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewPrimaryActivationButton;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewXMLDocument;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewsActivator;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitsActivator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro.data.xmlBinding.IntroDescriptor;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro.data.xmlBinding.ViewElement;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro.data.xmlBinding.ViewElementLink;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro.data.xmlBinding.ViewElementLinkTarget;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro.data.xmlBindingSubclasses.FLGIntroDescriptor;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro.data.xmlBindingSubclasses.FLGIntroElement;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro.data.xmlBindingSubclasses.FLGIntroElementLink;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro.data.xmlBindingSubclasses.FLGIntroElementLinkTarget;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro.elementInteractionPanel.FLGIntroElementInteractionPanel;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro.elementsContentsPanel.FLGIntroElementsContentsPanel;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro.elementsStructurePanel.FLGIntroElementsStructurePanel;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro.statusPanel.FLGIntroStatusPanel;
import freestyleLearningGroup.independent.gui.FLGImageUtility;
import freestyleLearningGroup.independent.util.FLGInternationalization;
import freestyleLearningGroup.independent.util.FLGLongLastingOperationStatus;

public class FLGIntroManager extends FSLAbstractLearningUnitViewManager {
    private FSLLearningUnitViewPrimaryActivationButton primaryActivationButton;
    private FLGInternationalization internationalization;

    public void init(FSLLearningUnitsActivator learningUnitsActivator,
        FSLLearningUnitViewsActivator learningUnitViewsActivator, FSLLearningUnitEventGenerator learningUnitEventGenerator,
        String learningUnitViewManagerId, String learningUnitViewManagerTitle, File learningUnitViewManagerCodeDirectory,
        boolean editMode, boolean originalElementsOnly, FLGLongLastingOperationStatus progressStatus) {
            super.init(learningUnitsActivator, learningUnitViewsActivator, learningUnitEventGenerator,
                learningUnitViewManagerId, learningUnitViewManagerTitle, learningUnitViewManagerCodeDirectory, editMode,
                originalElementsOnly, progressStatus);
            int stepSize = progressStatus.getStepSize();
            progressStatus.setStatusValue(progressStatus.getStatusValue() + (int)(stepSize / 5.));
            internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.intro.internationalization",
                getClass().getClassLoader());
            primaryActivationButton = new FSLLearningUnitViewPrimaryActivationButton(loadImage("primaryActivationButton.gif"));
            primaryActivationButton.setToolTipText(internationalization.getString("button.primaryActivation.toolTipText"));
            elementsStructurePanel = new FLGIntroElementsStructurePanel();
            elementsStructurePanel.init(this, learningUnitEventGenerator, editMode);
            elementsStructurePanel.setAutomaticActivation(true);
            progressStatus.setStatusValue(progressStatus.getStatusValue() + (int)(stepSize / 5.));
            elementInteractionPanel = new FLGIntroElementInteractionPanel();
            elementInteractionPanel.init(this, learningUnitEventGenerator, editMode);
            progressStatus.setStatusValue(progressStatus.getStatusValue() + (int)(stepSize / 5.));
            elementsContentsPanel = new FLGIntroElementsContentsPanel();
            elementsContentsPanel.init(this, learningUnitEventGenerator, editMode);
            progressStatus.setStatusValue(progressStatus.getStatusValue() + (int)(stepSize / 5.));
            statusPanel = new FLGIntroStatusPanel();
            statusPanel.init(this, learningUnitEventGenerator, editMode);
            progressStatus.setStatusValue(progressStatus.getStatusValue() + (int)(stepSize / 5.));
    }

    public boolean supportsImportStructure() {
        return false;
    }
    
    public java.net.URL getMainHelpPageUrl() {
        return getClass().getClassLoader().getResource("freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/intro/help");
    }

    public FSLLearningUnitViewPrimaryActivationButton getPrimaryActivationButton() {
        return primaryActivationButton;
    }

    protected Dispatcher createDispatcher() {
        Dispatcher d = FLGIntroDescriptor.newDispatcher();
        d.register(IntroDescriptor.class, FLGIntroDescriptor.class);
        d.register(ViewElement.class, FLGIntroElement.class);
        d.register(ViewElementLink.class, FLGIntroElementLink.class);
        d.register(ViewElementLinkTarget.class, FLGIntroElementLinkTarget.class);
        return d;
    }

    protected FSLLearningUnitViewXMLDocument createLearningUnitViewXMLDocument() {
        return new FLGIntroDescriptor();
    }

    private Image loadImage(String imageFileName) {
        return FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/intro/images/" +
            imageFileName));
    }
}