/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.glossary.elementsContentsPanel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.html.*;
import java.util.*;
import freestyleLearning.learningUnitViewAPI.*;
import freestyleLearning.learningUnitViewAPI.elementsContentsPanel.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.glossary.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.glossary.data.xmlBindingSubclasses.*;
import freestyleLearning.learningUnitViewAPI.util.*;
import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.independent.util.*;

public class FLGGlossaryTextElementContentPanel extends FSLAbstractLearningUnitViewElementContentPanel {
    private FLGScrollPane scrollPane;
    private FLGHtmlPane elementContentHtmlPane;
    private FLGHtmlPane elementTitleHtmlPane;
    private JPanel scrollPaneView;
    private boolean active;
    private JComponent[] editToolBarComponents;
    private FLGInternationalization m_internationalization;
    
    public void init(FSLLearningUnitViewManager learningUnitViewManager, FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
        m_internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.glossary.elementsContentsPanel.internationalization", getClass().getClassLoader());
        super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
        learningUnitEventGenerator.addLearningUnitListener(new FLGGlossaryElementContentPanel_LearningUnitAdapter());
        learningUnitViewManager.addLearningUnitViewListener(new FLGGlossaryElementContentPanel_LearningUnitViewAdapter());
    }
    
    protected java.awt.Component getPrintableComponent() {
        return scrollPaneView;
    }
    
    protected javax.swing.JEditorPane getPrintableEditorPane() {
        FLGHtmlPane printablePane = new FLGHtmlPane();
        FSLLearningUnitViewElement viewElement = learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewElementId, false);
        System.out.println("glossary: element = " + viewElement);
        System.out.println("glossary: element title = " + viewElement.getTitle());
        printablePane.setBase(elementContentHtmlPane.getBase());
        printablePane.setText("<html><body><h3>" + viewElement.getTitle() + "</h3>"
            + FLGHtmlUtilities.createPrintableHtmlText(elementContentHtmlPane.getText()));
        return printablePane;
    }

    public boolean isModifiedByUserInput() {
        boolean modified = elementContentHtmlPane.isModifiedByUserInput()
            || ((FLGGlossaryManager) learningUnitViewManager).isModifiedByMemory();
        return modified;
    }
    
    public void saveUserChanges() {
        File htmlFile;
        FLGGlossaryElement learningUnitViewElement = (FLGGlossaryElement) learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewElementId, true);
        learningUnitViewElement.setLastModificationDate(String.valueOf(new Date().getTime()));
        if (learningUnitViewElement.getHtmlFileName() == null) {
            htmlFile =
                learningUnitViewElementsManager
                .createNewFileForElementsExternalData(
                FLGGlossaryElement.ELEMENT_TYPE_TERM,
                ".html",
                learningUnitViewElementId);
            learningUnitViewElement.setHtmlFileName(htmlFile.getName());
        } else {
            String relativeFileName =
                learningUnitViewElementsManager
                .getRelativeFileNameVersionForWriting(
                learningUnitViewElement.getHtmlFileName(),
                learningUnitViewElement,
                FLGGlossaryElement.ELEMENT_TYPE_TERM,
                ".html");
            htmlFile =
                learningUnitViewElementsManager.resolveRelativeFileName(
                relativeFileName,
                learningUnitViewElement);
            learningUnitViewElement.setHtmlFileName(relativeFileName);
        }
        FLGFileUtility.writeStringIntoFile(elementContentHtmlPane.getText(), htmlFile);
        ((FLGGlossaryManager)learningUnitViewManager).setModifiedByMemory(false);
    }
    
    public void updateUI() {
        super.updateUI();
        if (elementContentHtmlPane != null) {
            FSLLearningUnitViewUtilities.updateHtmlPaneUI(
            elementContentHtmlPane);
        }
        if (elementTitleHtmlPane != null) {
            FSLLearningUnitViewUtilities.updateHtmlPaneUI(elementTitleHtmlPane);
        }
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
        FLGHtmlPaneEditButtonsFactory.FLGFileCreator fileCreator =
        new FLGHtmlPaneEditButtonsFactory.FLGFileCreator() {
            public File createFile(String fileExtension) {
                return learningUnitViewElementsManager.createNewFileForElementsExternalData("image", fileExtension, learningUnitViewElementId);
            }
        };
        FLGHtmlPaneEditButtonsFactory.FLGLinkEditor linkEditor =
        new FLGHtmlPaneEditButtonsFactory.FLGLinkEditor() {
            public String linkSelectedToEdit(String htmlAttributeValue) {
                return learningUnitViewManager.editLearningUnitViewElementLink(htmlAttributeValue, learningUnitViewElementId);
            }
        };
        // Create the edit components...
        JComponent htmlPaneEditComps[] =
        FLGHtmlPaneEditButtonsFactory.createDefaultHtmlPaneEditComponents(fileCreator, linkEditor);
        editToolBarComponents = new JComponent[htmlPaneEditComps.length + 1];
        final FLGEditToolBarToggleButton memoryToggleButton = new FLGEditToolBarToggleButton(new ImageIcon(getClass().getClassLoader().getResource("freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/glossary/images/selected.gif")).getImage(), new ImageIcon(getClass().getClassLoader().getResource("freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/glossary/images/unselected.gif")).getImage(), m_internationalization.getString("tooltip.isMemoryUsable")); //$NON-NLS-1$
        memoryToggleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FLGGlossaryElement currentElement = (FLGGlossaryElement) learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewElementId, true);
                currentElement.setMemoryUsable(memoryToggleButton.isChecked());
                ((FLGGlossaryManager) learningUnitViewManager).setModifiedByMemory(true);
            }
        });
        learningUnitViewManager.addLearningUnitViewListener(new FSLLearningUnitViewAdapter() {
            public void learningUnitViewElementActivated(FSLLearningUnitViewEvent event) {
//                FLGGlossaryElement currentElement = (FLGGlossaryElement) learningUnitViewElementsManager.getLearningUnitViewElement(event.getActiveLearningUnitViewElementId(),true);
//                // Sanity...
//                if (currentElement == null) {
//                    return;
//                }
//                memoryToggleButton.setChecked(currentElement.getMemoryUsable());
            }
        });
        // Copy the html edit comps
        for (int i = 0; i < editToolBarComponents.length - 1; i++) {
            editToolBarComponents[i] = htmlPaneEditComps[i];
        }
        editToolBarComponents[editToolBarComponents.length - 1] =
        memoryToggleButton;
        updateUI();
    }
    
    protected void buildDependentUI(boolean reloadIfAlreadyLoaded) {
        boolean contentAvailable = false;
        if (learningUnitViewElementsManager != null && learningUnitViewElementId != null) {
            FLGGlossaryElement learningUnitViewElement = (FLGGlossaryElement) learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewElementId, false);
            if (learningUnitViewElement != null) {
                elementTitleHtmlPane.setText("<html><body><h1>"+ learningUnitViewElement.getTitle() + "</body></html>");
                if (learningUnitViewElement.getHtmlFileName() != null) {
                    elementContentHtmlPane.loadFile(learningUnitViewElementsManager.resolveRelativeFileName(
                        learningUnitViewElement.getHtmlFileName(), learningUnitViewElement), reloadIfAlreadyLoaded);
                } 
                else {
                    elementContentHtmlPane.setBase(learningUnitViewElementsManager.resolveRelativeFileName(
                        "dummyFileName", learningUnitViewElement));
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
           class FLGGlossaryElementContentPanel_HyperlinkListener implements HyperlinkListener {
              public void hyperlinkUpdate(HyperlinkEvent e) {
                 if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    String url = e.getURL().toString();
                    String linkId = url.substring(url.lastIndexOf("/") + 1);
                    learningUnitViewManager.followLearningUnitViewElementLink(linkId, learningUnitViewElementId);
                 }
              }
           }
         */
    
    class FLGGlossaryElementContentPanel_LearningUnitAdapter extends FSLLearningUnitVetoableAdapter {
        public void learningUnitEditModeChanged(FSLLearningUnitEvent event) {
            if (!event.isEditMode()) {
                elementContentHtmlPane.select(0, 0);
            }
        }
    }
    
    class FLGGlossaryElementContentPanel_LearningUnitViewAdapter extends FSLLearningUnitViewVetoableAdapter {
        public void learningUnitViewElementsUserVersionCreated(FSLLearningUnitViewEvent event) {
            if (event.getLearningUnitViewManagerId().equals(learningUnitViewManager.getLearningUnitViewManagerId())) {
                for (int i = 0; i < event.getLearningUnitViewElementIds().length; i++) {
                    if (event.getLearningUnitViewElementIds() [i].equals(learningUnitViewElementId)) {
                        String htmlFileName = ((FLGGlossaryElement)learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewElementId,false)).getHtmlFileName();
                        if (htmlFileName != null) {
                            File file = learningUnitViewElementsManager.resolveRelativeFileName(htmlFileName, learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewElementId, false));
                            try {
                                ((HTMLDocument)elementContentHtmlPane.getDocument()).setBase(file.getParentFile().toURL());
                            }
                            catch (Exception e) { 
                                System.out.println(e); 
                            }
                        }
                    }
                }
            }
        }
//        public void learningUnitViewElementsUserVersionCreated(FSLLearningUnitViewEvent event) {
//            System.out.println("learningUnitViewElementsUserVersionCreated()");
//            if (event.getLearningUnitViewManagerId().equals(learningUnitViewManager.getLearningUnitViewManagerId())) {
//                for (int i = 0; i < event.getLearningUnitViewElementIds().length; i++) {
//                    if (event.getLearningUnitViewElementIds()[i].equals(learningUnitViewElementId)) {
//                        String htmlFileName = ((FLGGlossaryElement) learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewElementId, false)).getHtmlFileName();
//                        if (htmlFileName != null) {
//                            File file = learningUnitViewElementsManager.resolveRelativeFileName(htmlFileName, learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewElementId,false));
//                            try {
//                                ((HTMLDocument) elementContentHtmlPane.getDocument()).setBase(file.getParentFile().toURL());
//                            }
//                            catch (Exception e) {
//                                System.out.println(e);
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }
}