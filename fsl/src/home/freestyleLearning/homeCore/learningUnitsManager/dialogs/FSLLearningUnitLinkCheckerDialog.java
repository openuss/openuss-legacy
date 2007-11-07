package freestyleLearning.homeCore.learningUnitsManager.dialogs;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import freestyleLearning.homeCore.learningUnitsManager.FSLLearningUnitsManager;
import freestyleLearningGroup.independent.gui.FLGColumnLayout;
import freestyleLearningGroup.independent.gui.FLGDialogInputVerifier;
import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.util.FLGInternationalization;

public class FSLLearningUnitLinkCheckerDialog implements FLGDialogInputVerifier {
	private FLGInternationalization internationalization;
	private JPanel linkDialogPanel;
	private JCheckBox linkInOtherUnits_checkBox;
	private JCheckBox linkInOtherViews_checkBox;
	
	/**
	 * Constructor.
	 */
	public FSLLearningUnitLinkCheckerDialog() {
        internationalization = new FLGInternationalization("freestyleLearning.homeCore.learningUnitsManager.dialogs.internationalization",
        		FSLLearningUnitsManager.class.getClassLoader());
        buildIndependentUI();
	}
	
	private void buildIndependentUI() {
		linkDialogPanel = new JPanel();
		linkDialogPanel.setLayout(new FLGColumnLayout());
		linkInOtherUnits_checkBox = new JCheckBox(
				internationalization.getString("dialog.linkChecker.linkInOtherUnits_checkBox"));
		linkInOtherUnits_checkBox.setSelected(false);
		linkInOtherViews_checkBox = new JCheckBox(
				internationalization.getString("dialog.linkChecker.linkInOtherViews_checkBox"));
		linkInOtherViews_checkBox.setSelected(true);
		linkDialogPanel.add(linkInOtherViews_checkBox, FLGColumnLayout.LEFTEND);
		linkDialogPanel.add(linkInOtherUnits_checkBox, FLGColumnLayout.LEFTEND);
	}
	
    /**
     * Opens Link Checker Dialog.
     * @return <code>true</code> if user selects OK from OptionPane. 
     */
    public boolean showDialog() {
    	int returnValue = FLGOptionPane.showConfirmDialog(linkDialogPanel, internationalization.getString("dialog.linkChecker.title"), 
				FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE);
        return returnValue == FLGOptionPane.OK_OPTION;
    }
    
    /**
     * Verifies user selection.
     * @return <code>String</code> containing hints for dialog inputs
     */
    public String verifyInput() {
    	String verifyMessage = null;
    	if(linkInOtherUnits_checkBox.isSelected()
    			&& linkInOtherViews_checkBox.isSelected()) {
    		verifyMessage = internationalization.getString("dialog.linkChecker");
    	}
    	return verifyMessage;
    }
    
    /**
     * @return <code>true</code> if user wants to check links in other units.
     */
    public boolean getLinkInOtherUnits() {
    	return linkInOtherUnits_checkBox.isSelected();
    }
    
    /**
     * @return <code>true</code> if user wants to check links in other views of same unit.
     */
    public boolean getLinkInOtherViews() {
    	return linkInOtherViews_checkBox.isSelected();
    }
}
