/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.questionAndAnswer.elementsContentsPanel;

import java.awt.BorderLayout;
import java.io.File;
import java.util.*;
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
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.questionAndAnswer.data.xmlBindingSubclasses.FLGQuestionAndAnswerElement;
import freestyleLearningGroup.independent.gui.FLGHtmlPane;
import freestyleLearningGroup.independent.gui.FLGHtmlPaneEditButtonsFactory;
import freestyleLearningGroup.independent.gui.FLGScrollPane;
import freestyleLearningGroup.independent.util.FLGFileUtility;

public class FLGQuestionAndAnswerTextElementContentPanel extends FSLAbstractLearningUnitViewElementContentPanel {
    private FLGScrollPane scrollPane;
    private FLGHtmlPane elementContentHtmlPane;
    private FLGHtmlPane elementTitleHtmlPane;
    private JPanel scrollPaneView;
    private boolean active;
    private JComponent[] editToolBarComponents;

    public void init(FSLLearningUnitViewManager learningUnitViewManager,
        FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
            super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
            learningUnitEventGenerator.addLearningUnitListener(new FLGQuestionAndAnswer_LearningUnitAdapter());
            learningUnitViewManager.addLearningUnitViewListener(new FLGQuestionAndAnswer_LearningUnitViewAdapter());
    }

    protected java.awt.Component getPrintableComponent() {
        return scrollPaneView;
    }

    public boolean isModifiedByUserInput() {
        return elementContentHtmlPane.isModifiedByUserInput();
    }

    public void saveUserChanges() {
        File htmlFile;
        FLGQuestionAndAnswerElement learningUnitViewElement =
            (FLGQuestionAndAnswerElement)learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewElementId, true);
        learningUnitViewElement.setLastModificationDate(String.valueOf(new Date().getTime()));
        if (learningUnitViewElement.getHtmlFileName() == null) {
            htmlFile = learningUnitViewElementsManager.createNewFileForElementsExternalData(FLGQuestionAndAnswerElement.ELEMENT_TYPE_TEXT,
                ".html", learningUnitViewElementId);
            learningUnitViewElement.setHtmlFileName(htmlFile.getName());
        }
        else {
            String relativeFileName = learningUnitViewElementsManager.getRelativeFileNameVersionForWriting(learningUnitViewElement.getHtmlFileName(),
                learningUnitViewElement, FLGQuestionAndAnswerElement.ELEMENT_TYPE_TEXT, ".html");
            htmlFile = learningUnitViewElementsManager.resolveRelativeFileName(relativeFileName, learningUnitViewElement);
            learningUnitViewElement.setHtmlFileName(relativeFileName);
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
    }

    protected JComponent[] getEditToolBarComponents() {
        return editToolBarComponents;
    }

    protected void buildIndependentUI() {
        setLayout(new java.awt.BorderLayout());
        elementContentHtmlPane = new FLGHtmlPane();
        elementTitleHtmlPane = new FLGHtmlPane();
        elementContentHtmlPane.setSupportWebSearches(true);
        elementContentHtmlPane.setEditable(editMode);
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

    protected void buildDependentUI(boolean reloadIfAlreadyLoaded) {
        boolean contentAvailable = false;
        if (learningUnitViewElementsManager != null && learningUnitViewElementId != null) {
            FLGQuestionAndAnswerElement learningUnitViewElement =
                (FLGQuestionAndAnswerElement)learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewElementId, false);
            if (learningUnitViewElement != null) {
                elementTitleHtmlPane.setText("<html><body><h1>" + learningUnitViewElement.getTitle() + "</body></html>");
                if (learningUnitViewElement.getHtmlFileName() != null) {
                    File htmlFile = learningUnitViewElementsManager.resolveRelativeFileName(
                        learningUnitViewElement.getHtmlFileName(), learningUnitViewElement);
                    if (htmlFile.exists()) {
                       elementContentHtmlPane.loadFile(htmlFile, reloadIfAlreadyLoaded);
                    }             
                    else {
                        elementContentHtmlPane.setBase(learningUnitViewElementsManager.resolveRelativeFileName(
                            "dummyFileName", learningUnitViewElement));
                        elementContentHtmlPane.setText("<html><body><p></p></body></html>");
                    }
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
        elementContentHtmlPane.setEditable(editMode && activeLearningUnitViewElementPanel && contentAvailable);
    }

/*
   class FLGQuestionAndAnswerElementContentPanel_HyperlinkListener implements HyperlinkListener {
      public void hyperlinkUpdate(HyperlinkEvent e) {
         if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            String url = e.getURL().toString();
            String linkId = url.substring(url.lastIndexOf("/") + 1);
            learningUnitViewManager.followLearningUnitViewElementLink(linkId, learningUnitViewElementId);
         }
      }
   }
*/

    class FLGQuestionAndAnswer_LearningUnitAdapter extends FSLLearningUnitVetoableAdapter {
        public void learningUnitEditModeChanged(FSLLearningUnitEvent event) {
            if (!event.isEditMode()) {
                elementContentHtmlPane.select(0, 0);
            }
        }
    }


    class FLGQuestionAndAnswer_LearningUnitViewAdapter extends FSLLearningUnitViewVetoableAdapter {
        public void learningUnitViewElementsUserVersionCreated(FSLLearningUnitViewEvent event) {
            if (event.getLearningUnitViewManagerId().equals(learningUnitViewManager.getLearningUnitViewManagerId())) {
                for (int i = 0; i < event.getLearningUnitViewElementIds().length; i++) {
                    if (event.getLearningUnitViewElementIds() [i].equals(learningUnitViewElementId)) {
                        String htmlFileName = ((FLGQuestionAndAnswerElement)learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewElementId,
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