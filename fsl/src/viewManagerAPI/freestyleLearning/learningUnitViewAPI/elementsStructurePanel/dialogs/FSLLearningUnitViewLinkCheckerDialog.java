package freestyleLearning.learningUnitViewAPI.elementsStructurePanel.dialogs;

import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import java.awt.event.*;
import freestyleLearningGroup.independent.gui.FLGColumnLayout;
import freestyleLearningGroup.independent.gui.FLGDialogInputVerifier;
import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.util.FLGInternationalization;

/**
 * FSLLearningUnitViewLinkCheckerDialog.
 * @author Carsten Fiedler
 */
public class FSLLearningUnitViewLinkCheckerDialog implements FLGDialogInputVerifier {
	FLGInternationalization internationalization;
	JCheckBox linkInOtherUnits_checkBox;
	JCheckBox linkInOtherViews_checkBox;
	JCheckBox onlyExistingElements_checkBox;
	JPanel linkDialogPanel;
    
	public FSLLearningUnitViewLinkCheckerDialog() {
        internationalization = new FLGInternationalization("freestyleLearning.learningUnitViewAPI.elementsStructurePanel.internationalization",
            FSLLearningUnitViewNewElementDialog.class.getClassLoader());
        buildIndependentUI();
    }

    public int showDialog() {
    	return FLGOptionPane.showConfirmDialog(linkDialogPanel, internationalization.getString("dialog.viewImport.linkConfiguration.title"), 
   			FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE);
    }
	
    public void buildIndependentUI() {
    	// ask if view element links should be deactivated before import
    	linkDialogPanel = new JPanel();
    	linkDialogPanel.setLayout(new FLGColumnLayout());
    	// links in other units
    	linkInOtherUnits_checkBox = new JCheckBox(
    		internationalization.getString("dialog.viewImport.linkConfiguration.linkInOtherUnits_checkBox"));
    	linkInOtherUnits_checkBox.setSelected(true);
    	// links to view elements in same unit
    	linkInOtherViews_checkBox = new JCheckBox(
    			internationalization.getString("dialog.viewImport.linkConfiguration.linkInOtherViews_checkBox"));
    	linkInOtherViews_checkBox.setSelected(true);
    	// only existing links to view elements in same unit
    	onlyExistingElements_checkBox = new JCheckBox(
    			internationalization.getString("dialog.viewImport.linkConfiguration.onlyExistingElements_checkBox"));
    	
    	linkInOtherViews_checkBox.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			onlyExistingElements_checkBox.setEnabled(linkInOtherViews_checkBox.isSelected());
    			onlyExistingElements_checkBox.setSelected(false);
    		} 
    	});
    	// add checkboxes into panel
    	linkDialogPanel.add(linkInOtherUnits_checkBox, FLGColumnLayout.LEFTEND);
    	linkDialogPanel.add(linkInOtherViews_checkBox, FLGColumnLayout.LEFTEND);
    	linkDialogPanel.add(onlyExistingElements_checkBox, FLGColumnLayout.CENTEREND);
    }
    
	public String verifyInput() {
		return new String();
	}

	public boolean checkLinkInOtherUnits() {
		return linkInOtherUnits_checkBox.isSelected();
	}
	
	public boolean checkLinkInOtherViews() {
		return linkInOtherUnits_checkBox.isSelected();
	}
	
	public boolean checkLinksOnlyOnExistingElements() {
		return onlyExistingElements_checkBox.isSelected();
	}
}
