package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JDialog;
import javax.swing.UIManager;
import javax.xml.bind.Dispatcher;

import freestyleLearning.homeCore.learningUnitsManager.FSLLearningUnitViewsManager;
import freestyleLearning.learningUnitViewAPI.FSLAbstractLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewContextDependentActivationButton;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElement;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementsManager;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewSecondaryActivationButton;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewXMLDocument;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewsActivator;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitsActivator;
import freestyleLearning.learningUnitViewAPI.contextDependentInteractionPanel.FSLLearningUnitViewContextDependentInteractionPanel;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewAdapter;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewVetoableAdapter;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.contextDependentInteractionPanel.FLGAudioContextDependentInteractionPanel;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBinding.Audio;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBinding.AudioDescriptor;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBinding.AudioLink;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBinding.AudioLinkTarget;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBindingSubclasses.FLGAudioDescriptor;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBindingSubclasses.FLGAudioElement;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBindingSubclasses.FLGAudioElementLink;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBindingSubclasses.FLGAudioElementLinkTarget;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.elementInteractionPanel.FLGAudioElementInteractionPanel;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.elementsContentsPanel.FLGAudioElementsContentsPanel;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.elementsStructurePanel.FLGAudioElementsStructurePanel;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.statusPanel.FLGAudioStatusPanel;
import freestyleLearningGroup.independent.gui.FLGImageUtility;
import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.gui.FLGUIUtilities;
import freestyleLearningGroup.independent.util.FLGFileUtility;
import freestyleLearningGroup.independent.util.FLGInternationalization;
import freestyleLearningGroup.independent.util.FLGLongLastingOperationStatus;

/**
 * This class is the manager-class for the Audio-View.
 * Here the panels of the view are loaded and some general information is set.
 * @author Gunnar Thies
 * @version 1.0, 15/04/03
 */
public class FLGAudioManager extends FSLAbstractLearningUnitViewManager {
    private final String LEARNING_UNIT_VIEW_DATA_FILENAME = "contents.xml";
    private FSLLearningUnitViewSecondaryActivationButton secondaryActivationButton;
    private FLGInternationalization internationalization;
    private FSLLearningUnitViewContextDependentActivationButton contextDependentActivationButton;
    private String contextDependentElementId;
    private String learningUnitId;
    private String learningUnitViewManagerId;
    private File learningUnitViewOriginalDataDirectory;
    private File learningUnitViewUserDataDirectory;
    private FLGAudioContextDependentInteractionPanel contextDependentInteractionPanel;
    private FLGAudioContextDependentInteractionPanel CDIJPanel;
    private FLGAudioElementInteractionPanel interactionPanel;
    private FLGAudioManager manager;
    private boolean active = false;
    private String elementId;
    private JDialog cdiDialog;
    private static String thisManagerId;

    /**
     * Initializes this manager.
     * @param learningUnitsActivator the learning units activator.
     * @param learningUnitViewsActivator the learning unit views activator.
     * @param learningUnitEventGenerator the learning unit events generator.
     * @param learningUnitViewManagerId the learning unit view manager's id.
     * @param learningUnitViewManagerTitle the learning unit view manager's title.
     * @param learningUnitViewManagerCodeDirectory the learning unit view manager's code directory.
     * @param editMode the edit mode.
     * @param originalElementsOnly indicating if we have original elements only.
     * @param progressStatus the progress status.
     */
    public void init(FSLLearningUnitsActivator learningUnitsActivator,
        FSLLearningUnitViewsActivator learningUnitViewsActivator, FSLLearningUnitEventGenerator learningUnitEventGenerator,
        String learningUnitViewManagerId, String learningUnitViewManagerTitle, File learningUnitViewManagerCodeDirectory,
        boolean editMode, boolean originalElementsOnly, FLGLongLastingOperationStatus progressStatus) {
            super.init(learningUnitsActivator, learningUnitViewsActivator, learningUnitEventGenerator,
                learningUnitViewManagerId, learningUnitViewManagerTitle, learningUnitViewManagerCodeDirectory, editMode,
                originalElementsOnly, progressStatus);
            //save the name of the manager
            thisManagerId = learningUnitViewManagerId;
            int stepSize = progressStatus.getStepSize();
            progressStatus.setStatusValue(progressStatus.getStatusValue() + (int)(stepSize / 5.));
            //loads the internationalization-file
            internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.internationalization",
                getClass().getClassLoader());
            //adds the secondaryActivationButton to the ToolBar
            secondaryActivationButton = new FSLLearningUnitViewSecondaryActivationButton(loadImage("secondaryActivationButton.gif"));
            secondaryActivationButton.setToolTipText(internationalization.getString("button.secondaryActivation.toolTipText"));
            //creates and initiates the structure-panel
            elementsStructurePanel = new FLGAudioElementsStructurePanel();
            elementsStructurePanel.init(this, learningUnitEventGenerator, editMode);
            elementsStructurePanel.setAutomaticActivation(true);
            //creates and initiates the interaction-panel
            progressStatus.setStatusValue(progressStatus.getStatusValue() + (int)(stepSize / 5.));
            elementInteractionPanel = new FLGAudioElementInteractionPanel();
            elementInteractionPanel.init(this, learningUnitEventGenerator, editMode);
            //creates new CDA-Button and enables it
            contextDependentActivationButton = new
                FSLLearningUnitViewContextDependentActivationButton(loadImage("contextDependentActivationButtonNoContent.gif"));
            contextDependentActivationButton.setToolTipText(internationalization.getString("button.contextDependentActivation.toolTipTextAddNote"));
            //creates and initiates the contents-panel
            progressStatus.setStatusValue(progressStatus.getStatusValue() + (int)(stepSize / 5.));
            elementsContentsPanel = new FLGAudioElementsContentsPanel();
            elementsContentsPanel.init(this, learningUnitEventGenerator, editMode);
            //creates and initiates the status-panel
            progressStatus.setStatusValue(progressStatus.getStatusValue() + (int)(stepSize / 5.));
            statusPanel = new FLGAudioStatusPanel();
            statusPanel.init(this, learningUnitEventGenerator, editMode);
            //creates and initiates the contextDependentInteractionPanel
            contextDependentInteractionPanel = new FLGAudioContextDependentInteractionPanel();
            contextDependentInteractionPanel.init(this, learningUnitEventGenerator, editMode);
            //every step the progressBar is updated, to show the progress of loading the view
            progressStatus.setStatusValue(progressStatus.getStatusValue() + (int)(stepSize / 5.));
    }

    public boolean supportsImportStructure() {
        return false;
    }
    
    // Carsten Fiedler, 13.11.2006
    public java.net.URL getMainHelpPageUrl() {
        return getClass().getClassLoader().getResource("freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/audio/help");
    }
    
    public void introduceLearningUnitViewManager(FSLLearningUnitViewManager learningUnitViewManager) {
        super.introduceLearningUnitViewManager(learningUnitViewManager);
        learningUnitViewManager.addLearningUnitViewListener(new FLGAudioManager_LearningUnitViewAdapter());
        learningUnitViewManager.addLearningUnitViewListener(new FLGAudioManager_LearningUnitViewVetoableAdapter());
    }

    /**
     * This method says, if the view-manager is context-dependent or not.
     * @return always true.
     */
    public boolean isContextDependentLearningUnitViewManager() {
        return true;
    }

    public FSLLearningUnitViewContextDependentInteractionPanel getContextDependentInteractionPanel() {
        return contextDependentInteractionPanel;
    }

    public FSLLearningUnitViewSecondaryActivationButton getSecondaryActivationButton() {
        return secondaryActivationButton;
    }

    public FSLLearningUnitViewContextDependentActivationButton getContextDependentActivationButton() {
        return contextDependentActivationButton;
    }

    /**
     * This method checks, if there is an audio-note for the chosen element
     * @param targetLearningUnitViewElementId the element which is actually chosen
     * @param targetLearningUnitViewManagerId the name of the current active view-manager
     */
    private boolean hasContextDependentElement(String targetLearningUnitViewElementId,
        String targetLearningUnitViewManagerId) {
            String id = targetLearningUnitViewElementId + "@" + targetLearningUnitViewManagerId;
            //if there is an audio-note for the chosen element available, true is returned
            if (getLearningUnitViewElementsManager() != null &&
                getLearningUnitViewElementsManager().getLearningUnitViewElement(id, false) != null)
                    return true;
            //no audio-note available: returns false
            return false;
    }

    // overrides FSLAbstractLearningUnitViewManager methods
    // because abstract xml document does not directly match with needed element structure
    public void loadLearningUnitViewData(String learningUnitId, File learningUnitViewOriginalDataDirectory,
        File learningUnitViewUserDataDirectory) {
            this.learningUnitId = learningUnitId;
            this.learningUnitViewOriginalDataDirectory = learningUnitViewOriginalDataDirectory;
            this.learningUnitViewUserDataDirectory = learningUnitViewUserDataDirectory;
            FSLLearningUnitViewElementsManager learningUnitViewElementsManagerToLoad = null;
            if (cachingEnabled) {
                learningUnitViewElementsManagerToLoad =
                    (FSLLearningUnitViewElementsManager)learningUnitViewElementsManagerCache.get(learningUnitId);
            }
            if (learningUnitViewElementsManagerToLoad == null) {
                FSLLearningUnitViewXMLDocument originalData =
                    loadLearningUnitViewXMLDocument(
                    new File(learningUnitViewOriginalDataDirectory, LEARNING_UNIT_VIEW_DATA_FILENAME));
                FSLLearningUnitViewXMLDocument userData =
                    loadLearningUnitViewXMLDocument(
                    new File(learningUnitViewUserDataDirectory, LEARNING_UNIT_VIEW_DATA_FILENAME));
                setContextDependentElementProperties(originalData);
                setContextDependentElementProperties(userData);
                learningUnitViewElementsManagerToLoad =
                    createElementsManagerFromXMLDocuments(originalData, userData, learningUnitViewOriginalDataDirectory,
                    learningUnitViewUserDataDirectory);
                if (cachingEnabled)
                    learningUnitViewElementsManagerCache.put(learningUnitId, learningUnitViewElementsManagerToLoad);
                lastLoadedLearningUnitViewElementsManager = learningUnitViewElementsManagerToLoad;
            }
    }

    private void setContextDependentElementProperties(FSLLearningUnitViewXMLDocument xmlDocument) {
        // set element properties according to target elements
        for (int i = 0; i < xmlDocument.getLearningUnitViewElements().size(); i++) {
            FLGAudioElement audioElement = (FLGAudioElement)xmlDocument.getLearningUnitViewElements().get(i);
            String targetLearningUnitViewManagerId = audioElement.getTargetViewManagerId();
            String targetLearningUnitViewElementId = audioElement.getTargetViewElementId();
            FSLLearningUnitViewManager targetLearningUnitViewManager =
                learningUnitViewsActivator.getLearningUnitViewManager(targetLearningUnitViewManagerId);
            FSLLearningUnitViewElementsManager targetElementsManager =
                targetLearningUnitViewManager.getLearningUnitViewElementsManager();
            if (targetElementsManager != null) {
                FSLLearningUnitViewElement targetElement =
                    targetElementsManager.getLearningUnitViewElement(targetLearningUnitViewElementId, false);
                String audioElementId;
                String audioElementTitle;
                String audioElementParentId;
                String audioElementType;
                boolean audioElementIsFolder;
                if (!targetLearningUnitViewElementId.equals("_topLevelFolder")) {
                    if (targetElement != null) { // removed?
                        // get properties from target element
                        audioElementId = targetLearningUnitViewElementId + "@" + targetLearningUnitViewManagerId;
                        if (targetElement.getParentId().equals("none")) {
                            audioElementParentId = "_topLevelFolder@" + targetLearningUnitViewManagerId;
                        }
                        else {
                            audioElementParentId = targetElement.getParentId() + "@" + targetLearningUnitViewManagerId;
                        }
                        if (!targetElement.getFolder()) {
                            audioElementIsFolder = false;
                            audioElementTitle = internationalization.getString("text.noteTo") + targetElement.getTitle();
                            audioElementType = FLGAudioElement.ELEMENT_TYPE_AUDIO_ELEMENT;
                        }
                        else {
                            audioElementIsFolder = true;
                            audioElementTitle = targetElement.getTitle();
                            audioElementType = FLGAudioElement.ELEMENT_TYPE_FOLDER;
                        }
                    }
                    else {
                        // audioElementId = "_removed@" + targetLearningUnitViewManagerId;
                        audioElementId = targetLearningUnitViewElementId + "@" + targetLearningUnitViewManagerId;
                        audioElementTitle = "<" + targetLearningUnitViewManager.getLearningUnitViewManagerTitle() +
                            ": Element " + targetLearningUnitViewElementId + " " +
                            internationalization.getString("element.notFound") + ">";
                        audioElementParentId = "_topLevelFolder@" + targetLearningUnitViewManagerId;
                        audioElementIsFolder = false;
                        audioElementType = FLGAudioElement.ELEMENT_TYPE_AUDIO_ELEMENT;
                    }
                }
                else {
                    audioElementId = "_topLevelFolder@" + targetLearningUnitViewManagerId;
                    audioElementTitle = targetLearningUnitViewManager.getLearningUnitViewManagerTitle();
                    audioElementParentId = "none";
                    audioElementIsFolder = true;
                    audioElementType = FLGAudioElement.ELEMENT_TYPE_FOLDER;
                }
                audioElement.setId(audioElementId);
                audioElement.setTitle(audioElementTitle);
                audioElement.setParentId(audioElementParentId);
                audioElement.setType(audioElementType);
                audioElement.setFolder(audioElementIsFolder);
            }
        }
    }

    public boolean saveLearningUnitViewData() {
        boolean savingOK = true;
        //soll gespeichert werden, muss die Aufnhame beendet werden.(ansonsten filenotfounderror)
        try {
            interactionPanel = (FLGAudioElementInteractionPanel)getElementInteractionPanel();
            if (interactionPanel.isRecording()) interactionPanel.stop();
        } catch (Exception e) { }
        //here the html-content is saved by the method in elementsContentsPanel
        //elementsContentsPanel.saveUserChanges();
        FSLLearningUnitViewXMLDocument document;
        if (learningUnitViewElementsManager.isOriginalElementsModified()) {
            document = createXMLDocumentFromElementsManager(learningUnitViewElementsManager, true);
            savingOK = saveLearningUnitViewXMLDocument(document,
                new File(learningUnitViewElementsManager.getLearningUnitViewOriginalDataDirectory(),
                LEARNING_UNIT_VIEW_DATA_FILENAME));
            if (savingOK) {
                learningUnitViewElementsManager.setOriginalElementsModified(false);
                //the files in the author-/ or user-directory should not be deleted
                //danger of deleting stuff from author.
                //removeUnnecessaryExternalElementFiles(true);
                loadLearningUnitViewData(learningUnitId, learningUnitViewOriginalDataDirectory,
                    learningUnitViewUserDataDirectory);
            }
            else
                return false;
        }
        if (learningUnitViewElementsManager.isUserElementsModified()) {
            document = createXMLDocumentFromElementsManager(learningUnitViewElementsManager, false);
            savingOK = saveLearningUnitViewXMLDocument(document,
                new File(learningUnitViewElementsManager.getLearningUnitViewUserDataDirectory(),
                LEARNING_UNIT_VIEW_DATA_FILENAME));
            if (savingOK) {
                learningUnitViewElementsManager.setUserElementsModified(false);
                //the files in the author-/ or user-directory should not be deleted
                //danger of deleting stuff from author.
                //removeUnnecessaryExternalElementFiles(true);
                //removeUnnecessaryExternalElementFiles(false);
                loadLearningUnitViewData(learningUnitId, learningUnitViewOriginalDataDirectory,
                    learningUnitViewUserDataDirectory);
            }
            else
                return false;
        }
        return savingOK;
    }

    private boolean saveLearningUnitViewXMLDocument(FSLLearningUnitViewXMLDocument learningUnitViewXMLDocument, File file) {
        File backUpFile = new File(file.getAbsolutePath() + "~");
        try {
            if (!file.exists()) {
                File directory = file.getParentFile();
                if (!directory.exists()) directory.mkdirs();
            }
            else {
                FLGFileUtility.copyFile(file, backUpFile);
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            learningUnitViewXMLDocument.validate();
            learningUnitViewXMLDocument.marshal(fileOutputStream);
            fileOutputStream.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
            String message = internationalization.getString("dialog.message.errorSaving") + "\n" + file.getPath() + "\n\n" +
                internationalization.getString("dialog.message.checkFileProperties");
            FLGOptionPane.showMessageDialog(message, internationalization.getString("dialog.title.errorSaving"),
                FLGOptionPane.ERROR_MESSAGE);
            FLGFileUtility.copyFile(backUpFile, file);
            return false;
        }
    }

    public FSLLearningUnitViewXMLDocument loadLearningUnitViewXMLDocument(File file) {
        Dispatcher dispatcher = createDispatcher();
        FSLLearningUnitViewXMLDocument document = null;
        FileInputStream fileInputStream = null;
        if (file.exists()) {
            try {
                fileInputStream = new FileInputStream(file);
                document = (FSLLearningUnitViewXMLDocument)dispatcher.unmarshal(fileInputStream);
                document.validate();
                fileInputStream.close();
            }
            catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
        else {
            document = createLearningUnitViewXMLDocument();
        }
        return document;
    }

    public FSLLearningUnitViewElementsManager
        createElementsManagerFromXMLDocuments(FSLLearningUnitViewXMLDocument originalDocument,
        FSLLearningUnitViewXMLDocument userDocument, File learningUnitViewOriginalDataDirectory,
        File learningUnitViewUserDataDirectory) {
            FSLLearningUnitViewElementsManager elementsManager = new FSLLearningUnitViewElementsManager();
            elementsManager.init(this, learningUnitViewManagerId, learningUnitViewOriginalDataDirectory,
                learningUnitViewUserDataDirectory, originalElementsOnly);
            java.util.List originalElements = originalDocument.getLearningUnitViewElements();
            java.util.List userElements = userDocument.getLearningUnitViewElements();
            for (int i = 0; i < originalElements.size(); i++) {
                FSLLearningUnitViewElement element = (FSLLearningUnitViewElement)originalElements.get(i);
                elementsManager.addLearningUnitViewElement(element, true);
            }
            for (int i = 0; i < userElements.size(); i++) {
                elementsManager.addLearningUnitViewElement((FSLLearningUnitViewElement)userElements.get(i), false);
            }
            return elementsManager;
    }

    //wenn das Element noch nicht besteht, wird es angelegt !!!mehr nicht!
    public FSLLearningUnitViewElement activateContextDependentElement(String targetLearningUnitViewManagerId,
        String targetLearningUnitViewElementId, boolean originalElementsOnly) {
            if (targetLearningUnitViewElementId != null) {
                contextDependentElementId = targetLearningUnitViewElementId + "@" + targetLearningUnitViewManagerId;
                FSLLearningUnitViewElement audioElement =
                    getLearningUnitViewElementsManager().getLearningUnitViewElement(contextDependentElementId, false);
                if (audioElement == null) {
                    // create new element
                    FSLLearningUnitViewManager targetLearningUnitViewManager =
                        learningUnitViewsActivator.getLearningUnitViewManager(targetLearningUnitViewManagerId);
                    audioElement = ((FLGAudioElementsStructurePanel)elementsStructurePanel).createContextDependentElement(targetLearningUnitViewManager,
                        targetLearningUnitViewElementId);
                }
                setActiveLearningUnitViewElementId(contextDependentElementId, null);
                return audioElement;
            }
            else
                return null;
    }

    protected Dispatcher createDispatcher() {
        Dispatcher d = FLGAudioDescriptor.newDispatcher();
        d.register(AudioDescriptor.class, FLGAudioDescriptor.class);
        d.register(Audio.class, FLGAudioElement.class);
        d.register(AudioLink.class, FLGAudioElementLink.class);
        d.register(AudioLinkTarget.class, FLGAudioElementLinkTarget.class);
        return d;
    }

    protected FSLLearningUnitViewXMLDocument createLearningUnitViewXMLDocument() {
        return new FLGAudioDescriptor();
    }

    private Image loadImage(String imageFileName) {
        return FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/audio/images/" +
            imageFileName));
    }

    private void refreshContextDependentInteractionButton(String elementId, String managerId) {
        //test if a context-dependent-element is available
        if (hasContextDependentElement(elementId, managerId)) {
            // the the chosen LearningUnitViewElement is set active, so the CDI-panel
            // knows which audio-note to play.
            contextDependentElementId = elementId + "@" + managerId;
            setActiveLearningUnitViewElementId(contextDependentElementId, null);
            // Then the audioElement is checked on given sound-file-names.
            // If there is on, then the button shows the "I-have-context-graphics".
            FLGAudioElement audioElement = (FLGAudioElement)
                getLearningUnitViewElementsManager().getLearningUnitViewElement(contextDependentElementId, false);
            if (audioElement.getSoundFileName() != null) {
                //cDI-Panel is shown
                FSLLearningUnitViewsManager.addContextDependentInteractionComponent(contextDependentInteractionPanel,
                    thisManagerId);
                //sets the status to show the buttons right
                contextDependentInteractionPanel.setContextDependentInteractionPanelButtonStatus();
                //and the buttons shows, that there is some content
                contextDependentActivationButton.setToolTipText(internationalization.getString("button.contextDependentActivation.toolTipTextConfigureAudioElement"));
                contextDependentActivationButton.setImage(loadImage("contextDependentActivationButtonContentExisting.gif"));
            }
            else {
                //has a context-dependent element but no soundfile
                FSLLearningUnitViewsManager.addContextDependentInteractionComponent(null, thisManagerId);
                contextDependentActivationButton.setImage(loadImage("contextDependentActivationButtonNoContent.gif"));
                contextDependentActivationButton.setToolTipText(internationalization.getString("button.contextDependentActivation.toolTipTextAddAudioElement"));
            }
        }
        //has no soundFile or no context-dependent Element
        else {
            FSLLearningUnitViewsManager.addContextDependentInteractionComponent(null, thisManagerId);
            contextDependentActivationButton.setImage(loadImage("contextDependentActivationButtonNoContent.gif"));
            contextDependentActivationButton.setToolTipText(internationalization.getString("button.contextDependentActivation.toolTipTextAddAudioElement"));
        }
        contextDependentActivationButton.repaint();
    }

    //Class for receiving fired events
    class FLGAudioManager_LearningUnitViewAdapter extends FSLLearningUnitViewAdapter {
        public void learningUnitViewElementsCreated(FSLLearningUnitViewEvent event) {
            updateContextDependentActivationButton(event);
        }

        public void learningUnitViewElementsSelected(FSLLearningUnitViewEvent event) {
            updateContextDependentActivationButton(event);
        }

        /**
         * This method is called everytime an element is clicked on.
         * It shows if an audio-note exists for the chosen element in the active view.
         * Then the CDI-Button changes his graphics or not (filled or empty speaker).
         * @param event the event that occured, provides the method with information about the chosen element
         */
        private void updateContextDependentActivationButton(FSLLearningUnitViewEvent event) {
            String activatedManagerId = event.getLearningUnitViewManagerId();
            String[] elementIds = event.getLearningUnitViewElementIds();
            String elementId = elementIds[elementIds.length - 1];
            contextDependentActivationButton.setEnabled(activatedManagerId != getLearningUnitViewManagerId());
            refreshContextDependentInteractionButton(elementId, activatedManagerId);
        }

        /**
         * This method is called everytime a view is activated.
         * It checks only wether the called view-manager is the audio-view or not.
         * If that is the case, then it disables the CDI-Button. Otherwise it enables the CDI-Button.
         * @param event the event that occured, provides the method with information about the called manager
         */
        public void learningUnitViewActivated(FSLLearningUnitViewEvent event) {
            String activatedManagerId = event.getLearningUnitViewManagerId();
            FSLLearningUnitViewManager activatedManager =
                learningUnitViewsActivator.getLearningUnitViewManager(activatedManagerId);
           
            	contextDependentActivationButton.setEnabled(false);
           
            //ask for context-dependent Element and refresh the CDI-button
            refreshContextDependentInteractionButton(activatedManager.getActiveLearningUnitViewElementId() +
                "", activatedManagerId);
        }
        
        public void learningUnitViewElementActivated(FSLLearningUnitViewEvent event) {
            String managerId = event.getLearningUnitViewManagerId();
            String elementId = event.getActiveLearningUnitViewElementId();
            if (hasContextDependentElement(elementId, managerId)) {
                contextDependentActivationButton.setImage(loadImage("contextDependentActivationButtonContentExisting.gif"));
            } else {
                contextDependentActivationButton.setImage(loadImage("contextDependentActivationButtonNoContent.gif"));
            }
            contextDependentActivationButton.setEnabled(true);
            contextDependentActivationButton.repaint();
        }
        
        public void learningUnitViewElementsRemoved(FSLLearningUnitViewEvent event) {
            String[] removedElementIds = event.getLearningUnitViewElementIds();
            String removedElementsViewManager = event.getLearningUnitViewManagerId();
            for (int i = 0; i < removedElementIds.length; i++) {
                String id = removedElementIds[i] + "@" + removedElementsViewManager;
                FLGAudioElement audioElement = (FLGAudioElement)getLearningUnitViewElementsManager().getLearningUnitViewElement(id, false);
                if (audioElement != null) {
                    getLearningUnitViewElementsManager().removeLearningUnitViewElement(id);
                }
            }
        }

        /** This method hides the cdiDialog. Is invoked when closing the fullscreen-window. */
        public void hideCdiPanel() {
            cdiDialog.dispose();
        }

        /**
         * This method is invoked when the fullscreen-window is opened.
         * Shows the cdi-dialog to play samples wehn in fullscreen-mode.
         * Adds listener to it, so the cdi-panel can be closed after closing the fullscreen-window.
         */
        public void learningUnitViewFullScreenModeSelected(FSLLearningUnitViewEvent event) {
         if(contextDependentInteractionPanel != null)   {
        	if(!event.getCloseOtherWindows()) {
	        	if (contextDependentInteractionPanel.isShowing()) {
	                cdiDialog = new JDialog(FLGUIUtilities.getFullScreenWindow(), "", false);
	                FSLLearningUnitViewsManager.addContextDependentInteractionComponent(null, thisManagerId);
	                
	                if(FLGUIUtilities.getFullScreenWindow() != null) {
	                FLGUIUtilities.getFullScreenWindow().addWindowListener(
	                    // WindowListener
	                    new WindowListener() {
	                        public void windowClosing(WindowEvent e) {
	                        }
	                        public void windowClosed(WindowEvent e) {
	                            hideCdiPanel();
	                            FSLLearningUnitViewsManager.addContextDependentInteractionComponent(contextDependentInteractionPanel,
	                                thisManagerId);
	                        }
	                        public void windowOpened(WindowEvent e) { }
	                        public void windowActivated(WindowEvent e) { }
	                        public void windowDeactivated(WindowEvent e) { }
	                        public void windowIconified(WindowEvent e) { }
	                        public void windowDeiconified(WindowEvent e) { }
	                    });
	                }
	                
	                //displays cdi-dialog at the right place
	                Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
	                cdiDialog.getContentPane().setBackground((Color)UIManager.get("FSLMainFrameColor1"));
	                cdiDialog.setLocation((int)(screenDim.getWidth() - 200), 200);
	                cdiDialog.getContentPane().add(contextDependentInteractionPanel);
	                cdiDialog.pack();
	                cdiDialog.show();
	            }
        	}
        }
        }
    }


    /**
     * This class is invoked when one of the arrow-buttons for navigation in the status-panel of other views is clicked on.
     * Is used for updating the cdi-panel
     */
    class FLGAudioManager_LearningUnitViewVetoableAdapter extends FSLLearningUnitViewVetoableAdapter {
        public void learningUnitViewElementActivating(FSLLearningUnitViewEvent event) {
            //get active element
            String activatedElementId = event.getActiveLearningUnitViewElementId();
            //get active view
            String activatedManagerId = event.getLearningUnitViewManagerId();
            //refresh the cdi-button and panel
            refreshContextDependentInteractionButton(activatedElementId, activatedManagerId);
        }
    }
}
