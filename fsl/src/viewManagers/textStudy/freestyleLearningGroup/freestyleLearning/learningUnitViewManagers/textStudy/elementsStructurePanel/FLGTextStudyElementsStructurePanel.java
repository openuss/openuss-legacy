/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.textStudy.elementsStructurePanel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.*;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElement;
import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewManager;
import freestyleLearning.learningUnitViewAPI.elementsStructurePanel.FSLAbstractLearningUnitViewElementsStructurePanel;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.FSLLearningUnitEventGenerator;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.FSLLearningUnitViewEvent;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.textStudy.FLGTextStudyManager;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.textStudy.data.xmlBindingSubclasses.FLGTextStudyElement;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.textStudy.openOffice.LogFrame;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.textStudy.openOffice.OOoTextStudyImporter;
import freestyleLearningGroup.independent.gui.FLGColumnLayout;
import freestyleLearningGroup.independent.gui.FLGEditToolBarButton;
import freestyleLearningGroup.independent.gui.FLGImageUtility;
import freestyleLearningGroup.independent.gui.FLGMediaFileChooser;
import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.gui.FLGUIUtilities;
import freestyleLearningGroup.independent.util.FLGFileUtility;
import freestyleLearningGroup.independent.util.FLGInternationalization;

public class FLGTextStudyElementsStructurePanel extends FSLAbstractLearningUnitViewElementsStructurePanel {
    private FLGInternationalization internationalization;
    private FLGEditToolBarButton openOfficeImportButton;
    private FLGEditToolBarButton styleSheetImportButton;
    private File styleSheetFile = null;
    private JRadioButton individualStyleSheet_radioButton;
    private JButton importButton;
    private JTextField importstyleSheetPath;
    
    /**
     * Inits FLGTextStudyElementsStructurePanel.
     * @param <code>FSLLearningUnitViewManager</code> learningUnitViewManager
     * @param <code>FSLLearningUnitEventGenerator</code> learningUnitEventGenerator
     * @param <code>boolean</code> editMode
     */
    public void init(FSLLearningUnitViewManager learningUnitViewManager,
    		FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
        super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
        internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.textStudy.elementsStructurePanel.internationalization", getClass().getClassLoader());
        
        openOfficeImportButton = new FLGEditToolBarButton(loadImage("openOfficeImportButton.gif"));
        openOfficeImportButton.setToolTipText(internationalization.getString("button.tooltip.oooImport"));
        openOfficeImportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    public void run() {
                        startOOoImport();
                    }
                }).start();
            }
        });
        // temporary disabled
        //editToolBar.add(openOfficeImportButton);
        
        styleSheetImportButton = new FLGEditToolBarButton(loadImage("styleSheetImportButton.gif"));
        styleSheetImportButton.setToolTipText(internationalization.getString("button.tooltip.styleSheetImport"));
        styleSheetImportButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		importStyleSheet();
        	}
        });
        editToolBar.add(styleSheetImportButton);
    }
    
    private void importStyleSheet() {
    	JPanel stylePanel = new JPanel();
    	stylePanel.setLayout(new FLGColumnLayout());
    	
    	// radio buttons
    	JRadioButton defaultStyleSheet_radioButton = new JRadioButton(internationalization.getString("dialog.styleSheet.default.text"));
    	defaultStyleSheet_radioButton.setSelected(true);
    	defaultStyleSheet_radioButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    				importButton.setEnabled(false);
    		    	importstyleSheetPath.setText("");
    		    	importstyleSheetPath.setEnabled(false);
    		}
    	});
    	stylePanel.add(defaultStyleSheet_radioButton,FLGColumnLayout.LEFTEND);
    	individualStyleSheet_radioButton = new JRadioButton(internationalization.getString("dialog.styleSheet.individual.text"));
    	individualStyleSheet_radioButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    				importButton.setEnabled(true);
    		    	importstyleSheetPath.setEnabled(true);
    		}
    	});
    	stylePanel.add(individualStyleSheet_radioButton,FLGColumnLayout.LEFTEND);
    	ButtonGroup buttonGroup = new ButtonGroup();
    	buttonGroup.add(defaultStyleSheet_radioButton);
    	buttonGroup.add(individualStyleSheet_radioButton);
    	
    	// import style sheet path
    	importstyleSheetPath = new JTextField(20);
    	importstyleSheetPath.setBorder(new EmptyBorder(5,5,5,5));
    	importstyleSheetPath.setEnabled(false);
    	stylePanel.add(importstyleSheetPath,FLGColumnLayout.CENTER);
    	
    	// import button
    	importButton = new JButton(internationalization.getString("dialog.styleSheet.importButton.text"));
    	importButton.setEnabled(false);
    	importButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			JFileChooser fileDialog = new JFileChooser();
		    	fileDialog.setDialogTitle("Stylesheet-Import");
		    	java.awt.Dimension screenDim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		        fileDialog.setLocation((int)(screenDim.getWidth() - fileDialog.getWidth()) / 2,
		        (int)(screenDim.getHeight() - fileDialog.getHeight()) / 2);
		        String[] fileExtensions = { ".css" };
		        fileDialog.setFileFilter(new FLGUIUtilities.FLGFileFilter(fileExtensions,".css"));
		        // open dialog
		        if (fileDialog.showOpenDialog(new JPanel()) == JFileChooser.APPROVE_OPTION) {
		        	styleSheetFile = fileDialog.getSelectedFile();
		        	importstyleSheetPath.setText(styleSheetFile.getAbsolutePath());
		        }		
    		}
    	});
    	stylePanel.add(importButton, FLGColumnLayout.CENTEREND);

    	// option pane
    	int returnValue = FLGOptionPane.showConfirmDialog(stylePanel, internationalization.getString("dialog.styleSheet.title"),
                FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE);
    	if (returnValue==FLGOptionPane.OK_OPTION) { 
       		if(!defaultStyleSheet_radioButton.isSelected()) {
       			//  copy stylesheet file into text study folder
        		File sourceFile = styleSheetFile;
        		File destinationFile = learningUnitViewElementsManager.createNewFileForElementsExternalData("styleSheet", ".css", activeLearningUnitViewElementId);
        		FLGFileUtility.copyFile(sourceFile, destinationFile);
        		// update all elements descriptors
        		String[] elementsIds = learningUnitViewElementsManager.getAllLearningUnitViewElementIds();
        		for (int i=0; i< elementsIds.length; i++) {
        			FLGTextStudyElement element = (FLGTextStudyElement) learningUnitViewElementsManager.getLearningUnitViewElement(elementsIds[i],false);
        			if (!element.getFolder()) {
           				element.setStyleSheetFileName(destinationFile.getName());
        				element.setModified(true);
        			}
        		}
			    learningUnitViewElementsManager.setModified(true);
        		// update descriptors and refresh current presentation
			    FSLLearningUnitViewEvent event = FSLLearningUnitViewEvent.createElementsModifiedEvent(learningUnitViewManager.getLearningUnitViewManagerId(),elementsIds);
		        learningUnitViewManager.fireLearningUnitViewEvent(event);
        	} else {
        		// update all elements descriptors
        		String[] elementsIds = learningUnitViewElementsManager.getAllLearningUnitViewElementIds();
        		for (int i=0; i< elementsIds.length; i++) {
        			FLGTextStudyElement element = (FLGTextStudyElement) learningUnitViewElementsManager.getLearningUnitViewElement(elementsIds[i],false);
        			if (!element.getFolder()) {
           				element.setStyleSheetFileName(null);
        				element.setModified(true);
        			}
        		}
			    learningUnitViewElementsManager.setModified(true);
        		// update descriptors and refresh current presentation
			    FSLLearningUnitViewEvent event = FSLLearningUnitViewEvent.createElementsModifiedEvent(learningUnitViewManager.getLearningUnitViewManagerId(),elementsIds);
		        learningUnitViewManager.fireLearningUnitViewEvent(event);
       		}
       	} 
    }
    
    public void modifyLearningUnitViewElement(FSLLearningUnitViewElement element) {
        FLGTextStudyElement textStudyElement = (FLGTextStudyElement)element;
        textStudyElement.setLastModificationDate(String.valueOf(new Date().getTime()));
        if (!textStudyElement.getFolder()) {
            textStudyElement.setType(FLGTextStudyElement.ELEMENT_TYPE_TEXT);
        }
        else {
            textStudyElement.setType(FLGTextStudyElement.ELEMENT_TYPE_FOLDER);
        }
    }
    
    public FSLLearningUnitViewElement createLearningUnitViewElement(String id, String parentId, String title, boolean folder) {
        FLGTextStudyElement newElement = new FLGTextStudyElement();
        newElement.setId(id);
        newElement.setParentId(parentId);
        newElement.setTitle(title);
        newElement.setFolder(folder);
        newElement.setLastModificationDate(String.valueOf(new Date().getTime()));
        if (!folder) newElement.setType(FLGTextStudyElement.ELEMENT_TYPE_TEXT);
        else {
            newElement.setType(FLGTextStudyElement.ELEMENT_TYPE_FOLDER);
        }
        return newElement;
    }
    
    /**
     * is called after button OOoImport an TextStudy interaction panel was pressed.
     * Starts the import of OpenOffice file.
     *
     * @author Ruediger Niemeier fsl@ruedi24.de
     */
    private void startOOoImport() {
    	final LogFrame log = LogFrame.getInstance();
        final String textStudyDirectory = ((File) super.learningUnitViewElementsManager.getLearningUnitViewDataDirectory()).getAbsolutePath();
        log.add("textStudyDirectory is " + textStudyDirectory);
        FLGMediaFileChooser fileChooser = new FLGMediaFileChooser(FLGMediaFileChooser.OPEN_OFFICE_STARWRITER);
        fileChooser.setDialogTitle(openOfficeImportButton.getToolTipText());
        if (fileChooser.showDialog()) {
            log.show();
            final File file = fileChooser.getSelectedFile();
            log.add("startOOoImport");
            log.add("selected file is " + file.getAbsolutePath());
            try {
                OOoTextStudyImporter importer = new OOoTextStudyImporter();
                importer.importTextStudy(file, textStudyDirectory);

                FLGOptionPane.showMessageDialog(internationalization.getString("message.importSuccess.text"),
                openOfficeImportButton.getToolTipText(), FLGOptionPane.INFORMATION_MESSAGE);

                reloadData();
            }
            catch (Exception e) {
                e.printStackTrace();
                log.add("Error:"+e.toString());
                FLGOptionPane.showMessageDialog(internationalization.getString("message.importFailed.text"),
                openOfficeImportButton.getToolTipText(), FLGOptionPane.ERROR_MESSAGE);
            }
            finally {
                log.dispose();
            }
        }
    }
    
    private void reloadData() {
        ((FLGTextStudyManager)learningUnitViewManager).reloadData();
    }
    
    private Image loadImage(String imageFileName) {
        return FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource(
            "freestyleLearningGroup/freestyleLearning/learningUnitViewManagers/textStudy/images/" + imageFileName));
    }
}
