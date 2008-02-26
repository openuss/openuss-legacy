package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector;

import java.awt.*;
import java.io.*;
import java.net.*;
import javax.xml.bind.*;

import freestyleLearning.learningUnitViewAPI.FSLAbstractLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewPrimaryActivationButton;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewXMLDocument;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewsActivator;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitsActivator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEvent;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitVetoableAdapter;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBinding.SelectorDescriptor;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBinding.ViewElement;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBinding.ViewElementGridObject;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBinding.ViewElementLink;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBinding.ViewElementLinkTarget;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBindingSubclasses.FLGSelectorDescriptor;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBindingSubclasses.FLGSelectorElement;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBindingSubclasses.FLGSelectorElementGridObject;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBindingSubclasses.FLGSelectorElementLink;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.data.xmlBindingSubclasses.FLGSelectorElementLinkTarget;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.elementInteractionPanel.FLGSelectorElementInteractionPanel;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.elementsContentsPanel.FLGSelectorElementsContentsPanel;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.elementsStructurePanel.FLGSelectorElementsStructurePanel;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.events.FLGSelectorEvent;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.statusPanel.FLGSelectorStatusPanel;
import freestyleLearningGroup.independent.gui.FLGImageUtility;
import freestyleLearningGroup.independent.util.FLGInternationalization;
import freestyleLearningGroup.independent.util.FLGLongLastingOperationStatus;

/**
 * FLGSelectorManager.
 * Manager Class for Selector View. 
 * @author Carsten Fiedler
 */
public class FLGSelectorManager extends FSLAbstractLearningUnitViewManager {
    private FSLLearningUnitViewPrimaryActivationButton primaryActivationButton;
    private FLGInternationalization internationalization;

	/**
	 * Inits FLGSelector.
	 * @param FSLLearningUnitsActivator learningUnitsActivator
	 * @param FSLLearningUnitViewsActivator learningUnitViewsActivator
	 * @param FSLLearningUnitEventGenerator learningUnitEventGenerator
	 * @param String learningUnitViewManagerId
	 * @param String learningUnitViewManagerTitle
	 * @param File learningUnitViewManagerCodeDirectory
	 * @param boolean editMode
	 * @param boolean originalElementsOnly
	 * @param FLGLongLastingOperationStatus progressStatus
	 */
	public void init(FSLLearningUnitsActivator learningUnitsActivator, FSLLearningUnitViewsActivator learningUnitViewsActivator, 
		FSLLearningUnitEventGenerator learningUnitEventGenerator, String learningUnitViewManagerId, String learningUnitViewManagerTitle, 
	    File learningUnitViewManagerCodeDirectory, boolean editMode, boolean originalElementsOnly, 
	    FLGLongLastingOperationStatus progressStatus) {
			super.init(learningUnitsActivator, learningUnitViewsActivator, learningUnitEventGenerator,
					learningUnitViewManagerId, learningUnitViewManagerTitle, learningUnitViewManagerCodeDirectory, editMode,
					originalElementsOnly, progressStatus);
			int stepSize = progressStatus.getStepSize();
            progressStatus.setStatusValue(progressStatus.getStatusValue() + (int)(stepSize / 5.));
            internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.internationalization",
                getClass().getClassLoader());
            // init activation button
            primaryActivationButton = new FSLLearningUnitViewPrimaryActivationButton(loadImage("primaryActivationButton.gif"));
            primaryActivationButton.setToolTipText(internationalization.getString("button.primaryActivation.toolTipText"));
            // init structure panel
            elementsStructurePanel = new FLGSelectorElementsStructurePanel();
            elementsStructurePanel.init(this, learningUnitEventGenerator, editMode);
            elementsStructurePanel.setAutomaticActivation(true);
            progressStatus.setStatusValue(progressStatus.getStatusValue() + (int)(stepSize / 5.));
            // init interaction panel
            elementInteractionPanel = new FLGSelectorElementInteractionPanel();
            elementInteractionPanel.init(this, learningUnitEventGenerator, editMode);
            progressStatus.setStatusValue(progressStatus.getStatusValue() + (int)(stepSize / 5.));
            // init content panel 
            elementsContentsPanel = new FLGSelectorElementsContentsPanel();
            elementsContentsPanel.init(this, learningUnitEventGenerator, editMode);
            progressStatus.setStatusValue(progressStatus.getStatusValue() + (int)(stepSize / 5.));
            // init status panel
            statusPanel = new FLGSelectorStatusPanel();
            statusPanel.init(this, learningUnitEventGenerator, editMode);
            progressStatus.setStatusValue(progressStatus.getStatusValue() + (int)(stepSize / 5.));
            learningUnitEventGenerator.addLearningUnitListener(new Selector_LearningUnitAdapter());
            setCachingEnabled(false);
    		FSLLearningUnitViewEvent selectorEvent = FLGSelectorEvent.createViewSpecificEvent(FLGSelectorEvent.SELECTOR_USER_ROLE_CHANGED,originalElementsOnly);
            fireLearningUnitViewEvent(selectorEvent);
	}
	
	/**
	 * Returns Primary Activation Button.
	 * @return FSLLearningUnitViewPrimaryActivationButton primaryActivationButton
	 */
    public FSLLearningUnitViewPrimaryActivationButton getPrimaryActivationButton() {
        return primaryActivationButton;
    }
    
    /**
     * Gets URL to Help-Files.
     * @return URL mainHelpPageUrl
     */
    public URL getMainHelpPageUrl() {
        return getClass().getClassLoader().getResource("freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/selector/help");
    }
    
    /**
     * Reloads Learning Unit View Data.
     **/
    public void reloadData() {
    	super.reloadLearningUnitViewData();
    }

    /**
     * Registers Datatypes for JAXB-Parser.
     * @return Dispatcher dispatcher
     */
	protected Dispatcher createDispatcher() {
        Dispatcher d = FLGSelectorDescriptor.newDispatcher();
        d.register(SelectorDescriptor.class, FLGSelectorDescriptor.class);
        d.register(ViewElement.class, FLGSelectorElement.class);
        d.register(ViewElementLink.class, FLGSelectorElementLink.class);
        d.register(ViewElementLinkTarget.class, FLGSelectorElementLinkTarget.class);
        d.register(ViewElementGridObject.class, FLGSelectorElementGridObject.class);
        return d;
    }
	
	/**
	 * Returns FLGSelectorDescriptor.
	 * @return FLGSelectorDescriptor
	 */
	public FLGSelectorDescriptor getDescriptor() {
		return (FLGSelectorDescriptor) createXMLDocumentFromElementsManager(learningUnitViewElementsManager,false) ;
	}
	
	/**
	 * Creates a Learning Unit View XML Document.
	 * @return FSLLearningUnitViewXMLDocument FLGSelectorDescriptor 
	 */
	protected FSLLearningUnitViewXMLDocument createLearningUnitViewXMLDocument() {
		return new FLGSelectorDescriptor();
    }
        
    private Image loadImage(String imageFileName) {
    	return FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/selector/images/" +
            imageFileName));
    }
    
    /**
     * Selector_LearningUnitAdapter.
     * Inner class for setting user role identifier.
     */
    class Selector_LearningUnitAdapter extends FSLLearningUnitVetoableAdapter {
    	public void learningUnitUserViewChanged(FSLLearningUnitEvent event) {
    		if(learningUnitViewElementsManager!=null) {
	    		if(event.isOriginalElementsOnly()) {
	    			userRoleIsAuthor=true;
	    			learningUnitViewElementsManager.setOriginalElementsOnly(true);
	        		FSLLearningUnitViewEvent selectorEvent = FLGSelectorEvent.createViewSpecificEvent(FLGSelectorEvent.SELECTOR_USER_ROLE_CHANGED,userRoleIsAuthor);
	                fireLearningUnitViewEvent(selectorEvent);
	    		} else {
	    			userRoleIsAuthor=false;
	    			learningUnitViewElementsManager.setOriginalElementsOnly(false);
	        		FSLLearningUnitViewEvent selectorEvent = FLGSelectorEvent.createViewSpecificEvent(FLGSelectorEvent.SELECTOR_USER_ROLE_CHANGED,userRoleIsAuthor);
	                fireLearningUnitViewEvent(selectorEvent);
	    		}
	    		// repaint content panel
	    		FSLLearningUnitViewEvent selectorEvent = FLGSelectorEvent.createViewSpecificEvent(FLGSelectorEvent.SELECTOR_REPAINT_CONTENT_PANEL);
	            fireLearningUnitViewEvent(selectorEvent);
    		}
    	}
    }
}
