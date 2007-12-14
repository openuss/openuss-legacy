package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.elementsContentsPanel;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.text.html.HTMLDocument;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.elementsContentsPanel.FSLAbstractLearningUnitViewElementContentPanel;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEvent;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitVetoableAdapter;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewVetoableAdapter;
import freestyleLearning.learningUnitViewAPI.util.FSLLearningUnitViewUtilities;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBinding.Audio;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.data.xmlBindingSubclasses.FLGAudioElement;
import freestyleLearningGroup.independent.gui.FLGHtmlPane;
import freestyleLearningGroup.independent.gui.FLGHtmlPaneEditButtonsFactory;
import freestyleLearningGroup.independent.gui.FLGScrollPane;
import freestyleLearningGroup.independent.util.FLGFileUtility;
import freestyleLearningGroup.independent.util.FLGInternationalization;

/**
 * @author Freestyle Learning Group
 */
public class FLGAudioElementContentPanel extends FSLAbstractLearningUnitViewElementContentPanel {
    private FLGScrollPane scrollPane;
    private FLGHtmlPane elementContentHtmlPane;
    private FLGHtmlPane elementTitleHtmlPane;
    private JPanel scrollPaneView;
    private boolean active;
    private JComponent[] editToolBarComponents;
    private FLGInternationalization internationalization; 

    /**
     * Initializes this class.
     * @param learningUnitViewManager the learning unit view manager
     * @param learningUnitEventGenerator the learning unit events generator.
     * @param editMode the edit mode.
     */
    public void init(FSLLearningUnitViewManager learningUnitViewManager,
        FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
            super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
            learningUnitEventGenerator.addLearningUnitListener(new FLGAudioElementContentPanel_LearningUnitAdapter());
            learningUnitViewManager.addLearningUnitViewListener(new FLGAudioElementContentPanel_LearningUnitViewAdapter());
            internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.audio.elementsContentsPanel.internationalization",
                    getClass().getClassLoader());
    }

    protected java.awt.Component getPrintableComponent() {
        return scrollPaneView;
    }

    public boolean isModifiedByUserInput() {
        return elementContentHtmlPane.isModifiedByUserInput();
    }

    /** Method for saving the contents. */
    public void saveUserChanges() {
        //The actual audio element
        Audio actualAudio = (Audio)learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewManager.getActiveLearningUnitViewElementId(), false);
        //File for later use
        File htmlFile = new File("");
        //from the audio element, a FLGAudioElement is get
        FLGAudioElement learningUnitViewElement = (FLGAudioElement)actualAudio;
        //the viewElementId of the activeLearningUnitViewElement
        String elementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
        //if there is no htmlfile for that element
        if (learningUnitViewElement.getHtmlFileName() == null) {
            //new htmlfile is created in the specific folder (author-/ or learner-directory)
            htmlFile = learningUnitViewElementsManager.createNewFileForElementsExternalData(FLGAudioElement.ELEMENT_TYPE_AUDIO_ELEMENT,
                ".html", elementId);
            //the element-htmlfilename is set to the htmlfile's name
            learningUnitViewElement.setHtmlFileName(htmlFile + "");
        }
        //if there is a htmlfile already
        else {
            //new htmlfile must be created
            //get the relative filename, this varies from author- to learner-mode
            String relativeFileName = learningUnitViewElementsManager.getRelativeFileNameVersionForWriting(learningUnitViewElement.getHtmlFileName(),
                learningUnitViewElement, FLGAudioElement.ELEMENT_TYPE_AUDIO_ELEMENT, ".html");
            //if the relative htmlfilename starts with "author", we are in the author-mode (or administrator!)
            if (relativeFileName.startsWith("author")) {
                //create new htmlfile with the specific pre- and suffixes in the original-data-directory.
                htmlFile = FLGFileUtility.createNewFile("author_" + FLGAudioElement.ELEMENT_TYPE_AUDIO_ELEMENT,
                    ".html", learningUnitViewElementsManager.getLearningUnitViewOriginalDataDirectory());
            }
            //if not in auhtor-mode, we are a learner
            else if (relativeFileName.startsWith("learner")) {
                //create new htmlfile with the specific pre- and suffixes in the user-data-directory
                htmlFile = FLGFileUtility.createNewFile("learner_" + FLGAudioElement.ELEMENT_TYPE_AUDIO_ELEMENT,
                    ".html", learningUnitViewElementsManager.getLearningUnitViewUserDataDirectory());
            }
            //the name of the htmlfile is set to the elements-htmlfilename
            learningUnitViewElement.setHtmlFileName("" + htmlFile);
        }
        FLGFileUtility.writeStringIntoFile(elementContentHtmlPane.getText(), htmlFile);
    }

    public void updateUI() {
        super.updateUI();
        if (elementContentHtmlPane != null)
            FSLLearningUnitViewUtilities.updateHtmlPaneUI(elementContentHtmlPane);
        if (elementTitleHtmlPane != null)
            FSLLearningUnitViewUtilities.updateHtmlPaneUI(elementTitleHtmlPane);
    }

    protected void setActiveLearningUnitViewElementPanel(boolean active) {
        super.setActiveLearningUnitViewElementPanel(active);
        elementContentHtmlPane.setEditable(editMode && activeLearningUnitViewElementPanel);
        elementContentHtmlPane.setEnabled(editMode && activeLearningUnitViewElementPanel);
    }

    protected JComponent[] getEditToolBarComponents() {
        return editToolBarComponents;
    }

    /** Method for building the panel. Once invoked at the loading of the manager. */
    protected void buildIndependentUI() {
        setLayout(new java.awt.BorderLayout());
        elementContentHtmlPane = new FLGHtmlPane();
        elementTitleHtmlPane = new FLGHtmlPane();
        elementContentHtmlPane.setEnabled(true);
        elementContentHtmlPane.setEditable(true);
//        elementTitleHtmlPane.setEnabled(false);
        elementTitleHtmlPane.setEditable(false);
        elementContentHtmlPane.addHyperlinkListener(new FSLLearningUnitViewElementContentPanel_HyperlinkAdapter());
        scrollPaneView = new JPanel(new BorderLayout());
        scrollPaneView.add(elementTitleHtmlPane, BorderLayout.NORTH);
        scrollPaneView.add(elementContentHtmlPane, BorderLayout.CENTER);
        scrollPane = new FLGScrollPane(scrollPaneView);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        add(scrollPane);
        // toolbar
        FLGHtmlPaneEditButtonsFactory.FLGFileCreator fileCreator = new FLGHtmlPaneEditButtonsFactory.FLGFileCreator() {
            public File createFile(String fileExtension) {
                return learningUnitViewElementsManager.createNewFileForElementsExternalData("image", fileExtension,
                    learningUnitViewElementId);
            }
        };
        FLGHtmlPaneEditButtonsFactory.FLGLinkEditor linkEditor = new FLGHtmlPaneEditButtonsFactory.FLGLinkEditor() {
            public String linkSelectedToEdit(String htmlAttributeValue) {
                return learningUnitViewManager.editLearningUnitViewElementLink(htmlAttributeValue, learningUnitViewElementId);
            }
        };
        editToolBarComponents = FLGHtmlPaneEditButtonsFactory.createDefaultHtmlPaneEditComponents(fileCreator, linkEditor);
        updateUI();
    }

    /**
     * Method who actualizes the contentpane Everytime the user clicks on an element in the structure-panel
     * this method is invoked.
     */
    public void buildDependentUI(boolean reloadIfAlreadyLoaded) {
        boolean contentAvailable = false;
        if (learningUnitViewElementsManager != null && learningUnitViewManager.getActiveLearningUnitViewElementId() != null) {
            FLGAudioElement learningUnitViewElement =
                (FLGAudioElement)learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewManager.getActiveLearningUnitViewElementId(), false);
            if (learningUnitViewElement != null) {
                String titlePane;
                titlePane = "<html><body><h1>" + learningUnitViewElement.getTitle() + "</h1><br>";
                //if a soundfile for this element exists, it is named here.
                if (learningUnitViewElement.getSoundFileName() != null) {
                    titlePane = titlePane + internationalization.getString("elementsContentsPanel.soundFile") + " " 
                    + learningUnitViewElement.getSoundFileName() + "<hr></body></html>";
                }
                //If there is no soundfile available for the element..
                else if (learningUnitViewElement.getSoundFileName() == null) {
                    titlePane = titlePane + internationalization.getString("elementsContentsPanel.soundFile") + "<font color=red> not available...</font><hr></body></html>";
                }
                elementTitleHtmlPane.setText(titlePane);
                if (learningUnitViewElement.getHtmlFileName() != null) {
                    elementContentHtmlPane.loadFile(learningUnitViewElementsManager.resolveRelativeFileName(learningUnitViewElement.getHtmlFileName(),
                        learningUnitViewElement), reloadIfAlreadyLoaded);
                }
                else {
                    elementContentHtmlPane.setBase(learningUnitViewElementsManager.resolveRelativeFileName("dummyFileName",
                        learningUnitViewElement));
                    elementContentHtmlPane.setText("<html><body><p></p></body></html>");
                }
                contentAvailable = true;
            }
        }
        if (!contentAvailable) {
            elementTitleHtmlPane.setText("<html><body><p></p></body></html>");
            elementContentHtmlPane.setText("<html><body><p></p></body></html>");
        }
        elementContentHtmlPane.setEnabled(editMode && activeLearningUnitViewElementPanel && contentAvailable);
        elementContentHtmlPane.setEditable(editMode && activeLearningUnitViewElementPanel && contentAvailable);
    }

    class FLGAudioElementContentPanel_LearningUnitAdapter extends FSLLearningUnitVetoableAdapter {
        public void learningUnitEditModeChanged(FSLLearningUnitEvent event) {
            if (!event.isEditMode()) {
                elementContentHtmlPane.select(0, 0);
            }
        }
    }


    class FLGAudioElementContentPanel_LearningUnitViewAdapter extends FSLLearningUnitViewVetoableAdapter {
        public void learningUnitViewElementsUserVersionCreated(FSLLearningUnitViewEvent event) {
            if (event.getLearningUnitViewManagerId().equals(learningUnitViewManager.getLearningUnitViewManagerId())) {
                for (int i = 0; i < event.getLearningUnitViewElementIds().length; i++) {
                    if (event.getLearningUnitViewElementIds() [i].equals(learningUnitViewElementId)) {
                        String htmlFileName = ((FLGAudioElement)learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewElementId,
                            false)).getHtmlFileName();
                        if (htmlFileName != null) {
                            File file = learningUnitViewElementsManager.resolveRelativeFileName(htmlFileName,
                                learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewElementId, false));
                            try {
                                ((HTMLDocument)elementContentHtmlPane.getDocument()).setBase(file.getParentFile().toURL());
                            }
                            catch (Exception e) { System.out.println(e); }
                        }
                    }
                }
            }
        }
    }
}
