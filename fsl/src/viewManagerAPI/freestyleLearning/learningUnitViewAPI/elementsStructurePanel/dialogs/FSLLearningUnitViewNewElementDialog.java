/* Generated by Freestyle Learning Group */

package freestyleLearning.learningUnitViewAPI.elementsStructurePanel.dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import freestyleLearning.learningUnitViewAPI.*;
import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.independent.util.*;

public class FSLLearningUnitViewNewElementDialog implements FLGDialogInputVerifier {
    public static final int INSERT_BEFORE = 1;
    public static final int INSERT_AFTER = 2;
    public static final int INSERT_AS_CHILD = 3;
    private FLGInternationalization internationalization;
    private JPanel dialogContentComponent;
    private JPanel viewSpecificPaneContainer;
    private FSLLearningUnitViewNewAndModifyElementDialogViewSpecificPane viewSpecificPane;
    private JTextField textField_elementTitle;
    private JCheckBox checkBox_elementFolder;
    private JRadioButton radioButton_insertBefore;
    private JRadioButton radioButton_insertAfter;
    private JRadioButton radioButton_insertAsChild;
    private String elementTitle;
    private boolean elementFolder;
    private int insertCommand;
    private FSLLearningUnitViewElement referenceLearningUnitViewElement;
    private boolean activateDefaultImportFolderComp;
    private boolean folderImportSupported = false;
    private boolean folderImportSelected = false;
    private JCheckBox importFolderComponent;
    private boolean activateFolderComponent = true;
    
    /**
     * @param <code>JComponent</code> importFolderComp
     * @param <code>boolean</code> folderImportSupported
     */
    public FSLLearningUnitViewNewElementDialog(boolean activateFolderComponent, boolean activateDefaultImportFolderComp, boolean folderImportSupported) {
    	internationalization = new FLGInternationalization("freestyleLearning.learningUnitViewAPI.elementsStructurePanel.internationalization",
                FSLLearningUnitViewNewElementDialog.class.getClassLoader());
    	this.folderImportSupported = folderImportSupported;
    	this.activateFolderComponent = activateFolderComponent;
    	if (activateDefaultImportFolderComp) {
    		// default component
    		importFolderComponent = new JCheckBox(internationalization.getString("label.importFolderCheckBox"));
    		importFolderComponent.setEnabled(folderImportSupported);
    		importFolderComponent.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	folderImportSelected = importFolderComponent.isSelected();
                }
            });
    	}
    	buildIndependentUI();
    }

    /**
     * @param <code>FSLLearningUnitViewElementsManager</code> elementsManager
     * @param <code>String referenceElementId</code>
     * @param <code>FSLLearningUnitViewNewAndModifyElementDialogViewSpecificPane</code> viewSpecificPane
     * @return <code>boolean</code> true if user clicked ok
     */
    public boolean showDialog(FSLLearningUnitViewElementsManager elementsManager, String referenceElementId,
        FSLLearningUnitViewNewAndModifyElementDialogViewSpecificPane viewSpecificPane) {
            this.viewSpecificPane = viewSpecificPane;
            this.referenceLearningUnitViewElement = elementsManager.getLearningUnitViewElement(referenceElementId, false);
            buildDependentUI(elementsManager, referenceElementId, viewSpecificPane);
            int returnValue = FLGOptionPane.showConfirmDialog(this, dialogContentComponent, internationalization.getString("dialog.newElement.title"),
                FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE);
            if (returnValue == FLGOptionPane.OK_OPTION) {
                elementTitle = textField_elementTitle.getText();
                elementFolder = checkBox_elementFolder.isSelected();
                insertCommand = determineInsertCommand();
            }
            return returnValue == FLGOptionPane.OK_OPTION;
    }

    /**
     * @return <code>String</code> elementTitle
     */
    public String getElementTitle() {
        return elementTitle;
    }

    /**
     * @param <code>String</code> title
     */
    public void setElementTitle(String title) {
    	if(textField_elementTitle.getText().equals(""))textField_elementTitle.setText(title);
    }

    /**
     * @return <code>boolean</code> true if element is folder
     */
    public boolean isElementFolder() {
        return elementFolder;
    }

    /**
     * @return <code>int</code> insertCommand
     */
    public int getInsertCommand() {
        return insertCommand;
    }
    
    /**
     * @return <code>String</code> errorString
     */
    public String verifyInput() {
        String errorString = "";
        if (textField_elementTitle.getText().length() == 0) {
            errorString += internationalization.getString("label.elementTitle.title") + ": " +
                internationalization.getString("text.missingValue") + "\n";
        }
        if (viewSpecificPane != null) {
            String viewSpecificPaneErrorString = viewSpecificPane.verifyInput(null, referenceLearningUnitViewElement,
                checkBox_elementFolder.isSelected(), determineInsertCommand());
            if (viewSpecificPaneErrorString != null) errorString += viewSpecificPaneErrorString;
        }
        if (errorString.length() > 0) return errorString;
        else
            return null;
    }

    public boolean folderImportIsSelected() {
    	return folderImportSelected;
    }
    
    public void deselectFolderImport() {
    	
    }
    
    private void buildDependentUI(FSLLearningUnitViewElementsManager elementsManager, String referenceElementId,
        FSLLearningUnitViewNewAndModifyElementDialogViewSpecificPane viewSpecificPane) {
	    	// folder import
    	    if (importFolderComponent != null) {
	    		if (folderImportSupported) {
		        	importFolderComponent.setEnabled(true);
		        } else {
		        	importFolderComponent.setEnabled(false);
		        }
    	    }
    		if (!elementsManager.isEmpty()) {
                if (referenceElementId != null) {
                    if (elementsManager.isOriginalElementsOnly()) {
                        radioButton_insertBefore.setEnabled(true);
                        radioButton_insertAfter.setEnabled(true);
                        // insert before & insertAfter
                    }
                    else {
                        if (elementsManager.getLearningUnitViewUserElement(referenceElementId) != null &&
                            elementsManager.getLearningUnitViewOriginalElement(referenceElementId) == null) {
                                // a pure user element
                                radioButton_insertBefore.setEnabled(true);
                                radioButton_insertAfter.setEnabled(true);
                        }
                        else {
                            // a pure original element or a user element which overrides an original element
                            radioButton_insertBefore.setEnabled(false);
                            String nextSiblingElementId = elementsManager.getNextSiblingId(referenceElementId);
                            if (nextSiblingElementId == null ||
                                (elementsManager.getLearningUnitViewUserElement(nextSiblingElementId) != null &&
                                elementsManager.getLearningUnitViewOriginalElement(nextSiblingElementId) == null)) {
                                    radioButton_insertAfter.setEnabled(true);
                            }
                            else {
                                radioButton_insertAfter.setEnabled(false);
                            }
                        }
                    }
                }
                else {
                    // normal add mit parent = none
                }
            }
            else {
                // normal add mit parent = none
            }
            textField_elementTitle.setText("");
            checkBox_elementFolder.setSelected(false);
            radioButton_insertBefore.setSelected(false);
            radioButton_insertAfter.setSelected(true);
            radioButton_insertAsChild.setSelected(false);
            viewSpecificPaneContainer.removeAll();
            if (viewSpecificPane != null) {
   	             viewSpecificPane.setInputFieldsDefaults(null);
	             viewSpecificPane.setEnabled(true);
	             viewSpecificPaneContainer.add((JComponent)viewSpecificPane);
	        }
	        viewSpecificPaneContainer.revalidate();
    }

    private void buildIndependentUI() {
        dialogContentComponent = new JPanel(new BorderLayout());
        dialogContentComponent.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        // element name und aktivierbar
        JPanel mainElementInputPanel = new JPanel(new FLGColumnLayout());
        dialogContentComponent.add(mainElementInputPanel, BorderLayout.NORTH);
        textField_elementTitle = new JTextField(20);
        mainElementInputPanel.add(
            new JLabel(internationalization.getString("label.elementTitle.title")), FLGColumnLayout.LEFT);
        mainElementInputPanel.add(textField_elementTitle, FLGColumnLayout.LEFTEND);
        checkBox_elementFolder = new JCheckBox();
       	checkBox_elementFolder.setSelected(true);
        checkBox_elementFolder.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (viewSpecificPane != null) {
                        viewSpecificPane.setEnabled(!checkBox_elementFolder.isSelected());
                    }
                }
            });
         if(activateFolderComponent) {
        	 mainElementInputPanel.add(new JLabel(internationalization.getString("label.elementFolder.title")), FLGColumnLayout.LEFT);
        	 mainElementInputPanel.add(checkBox_elementFolder, FLGColumnLayout.LEFTEND);
        } 
        radioButton_insertBefore = new JRadioButton(internationalization.getString("label.insertBefore.title"));
        mainElementInputPanel.add(new JLabel(""), FLGColumnLayout.LEFT);
        mainElementInputPanel.add(radioButton_insertBefore, FLGColumnLayout.LEFTEND);
        radioButton_insertAfter = new JRadioButton(internationalization.getString("label.insertAfter.title"));
        mainElementInputPanel.add(new JLabel(""), FLGColumnLayout.LEFT);
        mainElementInputPanel.add(radioButton_insertAfter, FLGColumnLayout.LEFTEND);
        radioButton_insertAsChild = new JRadioButton(internationalization.getString("label.insertAsChild.title"));
        mainElementInputPanel.add(new JLabel(""), FLGColumnLayout.LEFT);
        mainElementInputPanel.add(radioButton_insertAsChild, FLGColumnLayout.LEFTEND);
        ButtonGroup checkBoxGroup = new ButtonGroup();
        checkBoxGroup.add(radioButton_insertBefore);
        checkBoxGroup.add(radioButton_insertAfter);
        checkBoxGroup.add(radioButton_insertAsChild);
        
        // component for folder import  
        if(folderImportSupported && importFolderComponent != null) {
        	mainElementInputPanel.add(importFolderComponent, FLGColumnLayout.LEFTEND);
        } 
        
        viewSpecificPaneContainer = new JPanel(new BorderLayout());
        viewSpecificPaneContainer.setOpaque(false);
        dialogContentComponent.add(viewSpecificPaneContainer, BorderLayout.CENTER);
    }

    private int determineInsertCommand() {
        int command = 0;
        if (radioButton_insertBefore.isSelected()) command = INSERT_BEFORE;
        if (radioButton_insertAfter.isSelected()) command = INSERT_AFTER;
        if (radioButton_insertAsChild.isSelected()) command = INSERT_AS_CHILD;
        return command;
    }
}