/*
 * FSLWelcomeScreenContentPanel.java
 *
 * Created on 6. November 2005, 11:21
 */

package freestyleLearning.homeCore.welcomeScreen;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;
import javax.xml.bind.*;

import freestyleLearning.homeCore.welcomeScreen.data.xmlBinding.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.*;
import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.independent.util.*;

/**
 *
 * @author  Freestyle Learning Group
 */
public class FSLWelcomeScreenContentPanel extends JPanel {
    
    private FLGScrollPane scrollPane;
    private JPanel scrollPaneView;
    private FLGHtmlPane elementContentHtmlPane;
    private FLGHtmlPane elementTitleHtmlPane;
    private boolean editMode;
    private JComponent[] editToolBarComponents;
    private File welcomeScreenDirectory;
    private File welcomeScreenTitleFile;
    private File welcomeScreenContentFile;
    private File descriptorFile;
    private String titleString;
    private String contentString;
    private WelcomeScreenDescriptor descriptor;
    private WelcomeElement currentWelcomeElement;
    private java.util.List welcomeElements;
    private HyperlinkListener hyperlinkListener;
    private File learningUnitsDirectory;

    
    public void init(FSLLearningUnitEventGenerator learningUnitEventGenerator, 
        HyperlinkListener hyperlinkListener, boolean editMode, File learningUnitsDirectory) {
        this.hyperlinkListener = hyperlinkListener;
        this.editMode = editMode;
        this.learningUnitsDirectory = learningUnitsDirectory;
        learningUnitEventGenerator.addLearningUnitListener(new FSLWelcomeScreenElementContentPanel_LearningUnitAdapter());
        welcomeScreenDirectory = new File(learningUnitsDirectory.getAbsolutePath() + System.getProperty("file.separator") + "welcomeScreen");
        descriptorFile = new File(welcomeScreenDirectory.getAbsolutePath() + System.getProperty("file.separator") + "contents.xml");
        createWelcomeElements(descriptorFile);
    }
    
    private void createWelcomeElements(File decriptorFile) {
        descriptor = loadDescriptor(descriptorFile);
        welcomeElements = descriptor.getWelcomeElements();
        loadWelcomeElement(0);
    }
    
    public String[] getLinkTargetIds(String linkId) {
        String[] linkTargetIds = new String[3];
        java.util.List currentElementLinks = currentWelcomeElement.getWelcomeElementLinks();
        for (int i = 0; i < currentElementLinks.size(); i++) {
            WelcomeElementLink currentLink = (WelcomeElementLink)currentElementLinks.get(i);
            if (currentLink.getId().equals(linkId)) {
                WelcomeElementLinkTarget linkTarget = (WelcomeElementLinkTarget)currentLink.getWelcomeElementLinkTargets().get(0); 
                linkTargetIds[0] = linkTarget.getTargetLearningUnitId();                
                linkTargetIds[1] = linkTarget.getTargetLearningUnitViewManagerId();
                linkTargetIds[2] = linkTarget.getTargetLearningUnitViewElementId();
            }
        }
        return linkTargetIds;
    }
    
    private void loadWelcomeElement(int ix) {
        WelcomeElement element = (WelcomeElement)welcomeElements.get(ix);
        welcomeScreenContentFile = new File(welcomeScreenDirectory.getAbsolutePath() + System.getProperty("file.separator") + element.getHtmlFileName());
        currentWelcomeElement = element;
    }

    public boolean isModifiedByUserInput() {
        return elementContentHtmlPane.isModifiedByUserInput();
    }

    public void saveUserChanges() {
        FLGFileUtility.writeStringIntoFile(elementContentHtmlPane.getText(), welcomeScreenContentFile);
    }

    protected JComponent[] getEditToolBarComponents() {
        return editToolBarComponents;
    }
    
    protected void buildIndependentUI() {
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());
        elementContentHtmlPane = new FLGHtmlPane();
        elementTitleHtmlPane = new FLGHtmlPane();
        elementContentHtmlPane.setSupportWebSearches(true);
        elementContentHtmlPane.setEditable(editMode);
        elementTitleHtmlPane.setEditable(editMode);
        elementContentHtmlPane.addHyperlinkListener(hyperlinkListener);
        elementTitleHtmlPane.addHyperlinkListener(hyperlinkListener);
        scrollPaneView = new JPanel(new BorderLayout());
        scrollPaneView.add(elementTitleHtmlPane, BorderLayout.NORTH);
        scrollPaneView.add(elementContentHtmlPane, BorderLayout.CENTER);
        scrollPane = new FLGScrollPane(scrollPaneView);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        add(scrollPane);        
    }

    protected void buildDependentUI() {
        elementTitleHtmlPane.setEditable(editMode);
        elementContentHtmlPane.setEditable(editMode);
        elementTitleHtmlPane.setText("<html><body><h3>" + currentWelcomeElement.getTitle() + "</h3></body></html>");
        elementContentHtmlPane.loadFile(welcomeScreenContentFile, true);
    }

    protected WelcomeScreenDescriptor loadDescriptor(File descriptorFile) {
        WelcomeScreenDescriptor descriptor = new WelcomeScreenDescriptor();
        if (descriptorFile.exists()) {
            Dispatcher dispatcher = WelcomeScreenDescriptor.newDispatcher();
            FileInputStream descriptorFileInputStream;
            try {
                descriptorFileInputStream = new FileInputStream(descriptorFile);
                descriptor = (WelcomeScreenDescriptor)dispatcher.unmarshal(descriptorFileInputStream);
                descriptorFileInputStream.close();
            }
            catch (Exception e) {
                FLGOptionPane.showMessageDialog("Error loading descriptor: " + e, 
                    "Exeption occurred", FLGOptionPane.ERROR_MESSAGE);
            }
        }
        return descriptor;
    }
    
    protected boolean saveDescriptor(WelcomeScreenDescriptor descriptor, File descriptorFile) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(descriptorFile);
            descriptor.validate();
            descriptor.marshal(fileOutputStream);
            fileOutputStream.close();
            return true;
        }
        catch(Exception e) {
            FLGOptionPane.showMessageDialog("Error saving descriptor: " + e, 
                "Exeption occurred", FLGOptionPane.ERROR_MESSAGE);
            try {
                fileOutputStream.close();
            }
            catch(Exception ex) {
                System.out.println(ex);
            }
        }
        return false;
    }    
    
    private void saveChanges() {
        FLGFileUtility.writeStringIntoFile(elementContentHtmlPane.getText(), welcomeScreenContentFile);        
        String titleHtmlText = elementTitleHtmlPane.getText();
        int titleBeginIndex = titleHtmlText.indexOf("<h3>") + 4;
        int titleEndIndex = titleHtmlText.indexOf("</h3>");
        currentWelcomeElement.setTitle(titleHtmlText.substring(titleBeginIndex, titleEndIndex));
        saveDescriptor(descriptor, descriptorFile);
    }

    class FSLWelcomeScreenElementContentPanel_LearningUnitAdapter extends FSLLearningUnitVetoableAdapter {
        public void learningUnitEditModeChanged(FSLLearningUnitEvent event) {
            editMode = event.isEditMode();
            if (!editMode) {
                elementContentHtmlPane.select(0, 0);
                saveChanges();
                setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
            }
            else {
                setBorder(BorderFactory.createLineBorder(new Color(192,0,0), 5));
            }
        }
    }
}
    
