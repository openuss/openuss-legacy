package freestyleLearning.homeCore.learningUnitsManager.dialogs;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.independent.util.*;

/**
 * FSLLearningUnitSynchronizeLoginDialog.
 * Dialog for entering OpenUSS-User-Account-Data.
 * @author Carsten Fiedler
 */
public class FSLLearningUnitSynchronizeLoginDialog implements FLGDialogInputVerifier {
	private JPanel dialogContentComponent;
    private FLGInternationalization internationalization;
    private JTextField textField_userName;
    private JPasswordField passwordField_userPassword;
    private String userName;
    private String userPassword;
	
    /**
     * Verifies user input.
     * @return <code>String</code> containing hints for dialog inputs
     */
    public String verifyInput() {
    	String errorString = "";
        if (textField_userName.getText().length() == 0) { 
        	errorString += "\n" + internationalization.getString("dialog.openUssSynchronizationLogin.label.username.title") + ": " +
                internationalization.getString("text.missingValue") + "\n";
        }
        if (new String(passwordField_userPassword.getPassword()).length() == 0) {
            errorString += "\n" + internationalization.getString("dialog.openUssSynchronizationLogin.label.password.title") + ": " +
                internationalization.getString("text.missingValue") + "\n";
        }
        if (errorString.length() > 0) {
            return errorString;
        }
        else {
            return null;
        }
    }    
 	
    /**
     * Constructor.
     */
    public FSLLearningUnitSynchronizeLoginDialog() {
    	internationalization = new FLGInternationalization("freestyleLearning.homeCore.learningUnitsManager.dialogs.internationalization",
    			FSLLearningUnitSynchronizeLoginDialog.class.getClassLoader());
    	buildIndependentUI();
    }

    /**
     * Shows dialog.
     * @return <code>boolean</code> true, if user clicked OK.
     */
    public boolean showDialog() {
    	buildDependentUI();
    	int returnValue = FLGOptionPane.showConfirmDialog(this, dialogContentComponent, internationalization.getString("dialog.openUssSynchronizationLogin.title"),
    			FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE);
    	if (returnValue == FLGOptionPane.OK_OPTION) {
    		userName = textField_userName.getText();
    		userPassword = new String(passwordField_userPassword.getPassword());
    	}
    	return returnValue == FLGOptionPane.OK_OPTION;
    }

    private void buildDependentUI() {
    	userName = null;
    	userPassword = null;
    	textField_userName.setText("");
    	passwordField_userPassword.setText("");
    }

    /**
     * @return <code>String</code> username
     */
    public String getUserName() {
    	return userName;
    }

    /**
     * @return <code>String</code> userpassword
     */
    public String getUserPassword() {
    	return userPassword;
    }

    private void buildIndependentUI() {
    	dialogContentComponent = new JPanel(new FLGColumnLayout());
    	dialogContentComponent.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	textField_userName = new JTextField(20);
    	passwordField_userPassword = new JPasswordField(20);
    	passwordField_userPassword.setFont(textField_userName.getFont());
    	dialogContentComponent.add(new JLabel(internationalization.getString("dialog.openUssSynchronizationLogin.label.username.title")), FLGColumnLayout.LEFT);
    	dialogContentComponent.add(textField_userName, FLGColumnLayout.LEFTEND);
    	dialogContentComponent.add(new JLabel(internationalization.getString("dialog.openUssSynchronizationLogin.label.password.title")), FLGColumnLayout.LEFT);
    	dialogContentComponent.add(passwordField_userPassword, FLGColumnLayout.LEFTEND);
    }
}    
