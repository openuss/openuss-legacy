/*
 * FSLNewUserDialog.java
 *
 * Created on August 12, 2003, 1:09 PM
 */

package freestyleLearning.homeCore.usersManager;

import freestyleLearning.homeCore.usersManager.data.xmlBindingSubclasses.*;
import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.independent.gui.documents.*;
import freestyleLearningGroup.independent.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * This dialog allows to create a new user
 * @author Norman Lahme, Mirko Wahn
 * @version 1.0
 */
public class FSLNewUserDialog implements FLGDialogInputVerifier {
    private JPanel dialogContentComponent;
    private FLGInternationalization internationalization;
    private JTextField textField_newUserName;
    private JPasswordField pwField_password;
    private JPasswordField pwField_confirmPassword;
    private JCheckBox checkBox_authorRoleAllowed;
    private JCheckBox checkBox_enableOpenUSS;
    private JComboBox comboBox_userType;
    private JComboBox comboBox_language;
    private JTextField textField_firstName;
    private JTextField textField_lastName;
    private JTextField textField_year;
    private JTextField textField_subject;
    private JTextField textField_personalID;
    private JTextField textField_title;
    private JTextField textField_address;
    private JTextField textField_city;
    private JTextField textField_zip;
    private JTextField textField_land;
    private JTextField textField_phone;
    private JTextField textField_function;
    private JTextField textField_email;
    private JTextField textField_facultyID;
    private JTextField textField_openUssServerName;
    private JLabel label_titel;
    private JLabel label_firstName;
    private JLabel label_lastName;
    private JLabel label_email;
    private JLabel label_address;
    private JLabel label_zip;
    private JLabel label_city;
    private JLabel label_land;
    private JLabel label_phone;
    private JLabel label_year;
    private JLabel label_language;
    private JLabel label_function;
    private JLabel label_subject;
    private JLabel label_personalID;
    private JLabel label_facultyID;
    private JLabel label_mandatoryEntries;
    private JLabel label_openUssServername;
    private JTabbedPane tabbedPane;
    private JPanel fslPanel;
    private JPanel openUssPanel;
    private JPanel openUssServerPanel;
    private JPanel openUssUserDataPanel;
    private JPanel openUssConfigurationPanel;
    private String[] languageIds = { "en_US", "de_DE" };
    private boolean isAuthorRoleAllowed;
    protected String userType;
    protected String code = "FSL-OpenUSS";
    protected String firstName;
    protected String lastName;
    protected String userName;
    protected String password;
    protected String retypePassword;
    protected String email;
    protected String year;
    protected String subject;
    protected String personalID;
    protected String title;
    protected String address;
    protected String city;
    protected String postcode;
    protected String land;
    protected String telephonenumber;
    protected String function;
    protected String faculty_id;
    protected String language_id;      
    protected String openUssServerName = "openuss01.uni-muenster.de";

    /** Creates a new instance of FSLNewUserDialog */
    public FSLNewUserDialog() {
        internationalization = new FLGInternationalization("freestyleLearning.homeCore.usersManager.internationalization",
            FSLUserLoginDialog.class.getClassLoader());
        isAuthorRoleAllowed = false;
        buildIndependentUI();
    }

    public String getUserName() {
        return textField_newUserName.getText();
    }

    public String getPassword() {
        return new String(pwField_password.getPassword());
    }

    private String getConfirmedPassword() {
        return new String(pwField_confirmPassword.getPassword());
    }

    public boolean isPasswordConfirmed() {
        String password = new String(pwField_password.getPassword());
        String confirmedPassword = new String(pwField_confirmPassword.getPassword());
        if (password != null) {
            return (password.equals(confirmedPassword));
        }
        return false;
    }

    public Object[] getUserRoles() {
        Vector roles = new Vector();
        roles.add(FSLUserDescriptor.LEARNER_ROLE);
        if (isAuthorRoleAllowed) {
            roles.add(FSLUserDescriptor.AUTHOR_ROLE);
        }
        return roles.toArray();
    }
    
    public boolean isAuthorRoleAllowed() {
        return isAuthorRoleAllowed;
    }

    public boolean showDialog() {
        buildDependentUI();
        int returnValue = FLGOptionPane.showConfirmDialog(this, dialogContentComponent, internationalization.getString("dialog.newUser.title"),
            FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE);
        return returnValue == FLGOptionPane.OK_OPTION;
    }

    void buildDependentUI() {
        // initialize and show dialog components
        textField_newUserName.setText("");
        pwField_password.setText("");
        pwField_confirmPassword.setText("");
        checkBox_authorRoleAllowed.setSelected(false);
    }

    private void buildIndependentUI() {
        // create dialog components
        fslPanel = new JPanel(new FLGColumnLayout());
        fslPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        openUssUserDataPanel = new JPanel(new FLGColumnLayout());
        openUssUserDataPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(internationalization.getString("border.title.userData")),
            BorderFactory.createEmptyBorder(5,5,5,5)));
        
        // FSL-Panel
        textField_newUserName = new JTextField(20);
        pwField_password = new JPasswordField(20);
        pwField_confirmPassword = new JPasswordField(20);
        pwField_password.setFont(textField_newUserName.getFont());
        pwField_confirmPassword.setFont(textField_newUserName.getFont());
        checkBox_authorRoleAllowed = new JCheckBox(internationalization.getString("checkBox.authorRoleAllowed.text"));
        checkBox_authorRoleAllowed.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    isAuthorRoleAllowed = checkBox_authorRoleAllowed.isSelected();
                }
            });
        fslPanel.add(new JLabel(internationalization.getString("label.username.title")), FLGColumnLayout.LEFT);
        fslPanel.add(textField_newUserName, FLGColumnLayout.LEFTEND);
        fslPanel.add(new JLabel(internationalization.getString("label.password.title")), FLGColumnLayout.LEFT);
        fslPanel.add(pwField_password, FLGColumnLayout.LEFTEND);
        fslPanel.add(new JLabel(internationalization.getString("label.confirmPassword.title")), FLGColumnLayout.LEFT);
        fslPanel.add(pwField_confirmPassword, FLGColumnLayout.LEFTEND);
        fslPanel.add(new JLabel(""), FLGColumnLayout.LEFT);
        fslPanel.add(checkBox_authorRoleAllowed, FLGColumnLayout.LEFTEND);
        JPanel fslOuterPanel = new JPanel(new BorderLayout());
        fslOuterPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5,5,5,5), 
            BorderFactory.createTitledBorder(internationalization.getString("border.fslHome.text"))));
        fslOuterPanel.add(fslPanel, BorderLayout.CENTER);
        JPanel fslLabelPanel = new JPanel(new FLGColumnLayout());
        fslLabelPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        fslLabelPanel.add(new JLabel(internationalization.getString("label.fslWebHint.text1")), FLGColumnLayout.LEFTEND);
        fslLabelPanel.add(new JLabel(internationalization.getString("label.fslWebHint.text2")), FLGColumnLayout.LEFTEND);
        fslOuterPanel.add(fslLabelPanel, BorderLayout.SOUTH);
        
        // OpenUSS-Panel
        openUssPanel = new JPanel(new BorderLayout());
        openUssPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        checkBox_enableOpenUSS = new JCheckBox(internationalization.getString("label.checkBox.enableOpenUSS"));
        checkBox_enableOpenUSS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enableUserDataPanel(checkBox_enableOpenUSS.isSelected());
            }
        });        
        comboBox_userType = new JComboBox();
        comboBox_userType.addItem(internationalization.getString("label.userRole.learner"));
        comboBox_userType.addItem(internationalization.getString("label.userRole.author"));
        textField_firstName = new JTextField(20);
        textField_lastName = new JTextField(20);
        textField_email = new JTextField(20);
        textField_year = new JTextField(20);
        textField_year.setDocument(new FLGDigitDocument());
        textField_subject = new JTextField(20);
        textField_personalID = new JTextField(20);
        textField_title = new JTextField(20);
        textField_address = new JTextField(20);
        textField_zip = new JTextField(20);
        textField_zip.setDocument(new FLGDigitDocument());
        textField_city = new JTextField(20);
        textField_land = new JTextField(20);
        textField_phone = new JTextField(20);
        textField_phone.setDocument(new FLGDigitDocument());
        textField_function = new JTextField(20);
        textField_facultyID = new JTextField(20);
        textField_openUssServerName = new JTextField(20);
        //textField_openUssServerName.setText(openUssServerName);
        
        comboBox_language = new JComboBox();
        comboBox_language.addItem(internationalization.getString("comboBox.item.en"));
        comboBox_language.addItem(internationalization.getString("comboBox.item.de"));
        for (int i = 0; i < comboBox_language.getItemCount(); i++) {
            if (languageIds[i].startsWith(java.util.Locale.getDefault().getLanguage())) {
                    comboBox_language.setSelectedIndex(i);
                    break;
            }
        }
        
        label_titel = new JLabel(internationalization.getString("label.textField.title"));
        label_firstName = new JLabel(internationalization.getString("label.textField.firstName"));
        label_lastName = new JLabel(internationalization.getString("label.textField.lastName"));
        label_email = new JLabel(internationalization.getString("label.textField.email"));
        label_address = new JLabel(internationalization.getString("label.textField.address"));
        label_zip = new JLabel(internationalization.getString("label.textField.zip"));
        label_city = new JLabel(internationalization.getString("label.textField.city"));
        label_land = new JLabel(internationalization.getString("label.textField.land"));
        label_phone = new JLabel(internationalization.getString("label.textField.phone"));
        label_year = new JLabel(internationalization.getString("label.textField.year"));
        label_language = new JLabel(internationalization.getString("label.textField.language"));
        label_function = new JLabel(internationalization.getString("label.textField.function"));
        label_facultyID = new JLabel(internationalization.getString("label.textField.facultyID"));
        label_subject = new JLabel(internationalization.getString("label.textField.subject"));
        label_personalID = new JLabel(internationalization.getString("label.textField.personalID"));
        label_openUssServername = new JLabel(internationalization.getString("label.textField.openUssServerName"));
        openUssUserDataPanel.add(label_language, FLGColumnLayout.LEFT);
        openUssUserDataPanel.add(comboBox_language, FLGColumnLayout.LEFTEND);        
        openUssUserDataPanel.add(label_firstName, FLGColumnLayout.LEFT);
        openUssUserDataPanel.add(textField_firstName, FLGColumnLayout.LEFTEND);
        openUssUserDataPanel.add(label_lastName, FLGColumnLayout.LEFT);
        openUssUserDataPanel.add(textField_lastName, FLGColumnLayout.LEFTEND);
        openUssUserDataPanel.add(label_email, FLGColumnLayout.LEFT);
        openUssUserDataPanel.add(textField_email, FLGColumnLayout.LEFTEND);
        label_mandatoryEntries = new JLabel(internationalization.getString("label.text.mandatory"));
        openUssUserDataPanel.add(new JLabel(" "), FLGColumnLayout.LEFTEND);
        
        checkBox_enableOpenUSS.setSelected(false);
        enableUserDataPanel(false);

        openUssServerPanel = new JPanel(new FLGColumnLayout());
        openUssServerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(internationalization.getString("border.openUssServerPanel.title")),
            BorderFactory.createEmptyBorder(5,5,5,5)));
        openUssServerPanel.add(label_openUssServername, FLGColumnLayout.LEFT);
        openUssServerPanel.add(textField_openUssServerName, FLGColumnLayout.LEFTEND);
        openUssServerPanel.add(new JLabel(" "), FLGColumnLayout.LEFTEND);

        JPanel openUssConfigurationPanel = new JPanel(new FLGColumnLayout());
        openUssConfigurationPanel.add(checkBox_enableOpenUSS, FLGColumnLayout.LEFTEND);
        openUssConfigurationPanel.add(new JLabel(" "), FLGColumnLayout.LEFTEND);
                
        openUssPanel.add(openUssConfigurationPanel, BorderLayout.NORTH);
        openUssPanel.add(openUssUserDataPanel, BorderLayout.CENTER);
//        openUssPanel.add(openUssServerPanel, BorderLayout.SOUTH);
        
        // TabbedPane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab(internationalization.getString("label.tabbedPane.fsl"), fslOuterPanel);
        //tabbedPane.addTab(internationalization.getString("label.tabbedPane.openUSS"), openUssPanel);
      
        dialogContentComponent = new JPanel(new BorderLayout());
        dialogContentComponent.add(tabbedPane, BorderLayout.CENTER);
        
        textField_newUserName.requestFocusInWindow();
    }
    
    public static void main(String[] args) {
        FSLNewUserDialog dialog = new FSLNewUserDialog();
        FLGOptionPane.showConfirmDialog(dialog, dialog.dialogContentComponent, "Titel", FLGOptionPane.OK_OPTION, FLGOptionPane.DEFAULT_OPTION);
        System.exit(0);
    }
        
    private void enableUserDataPanel(boolean enabled) {
        comboBox_userType.setEnabled(enabled);
        textField_firstName.setEnabled(enabled);
        textField_lastName.setEnabled(enabled);
        textField_email.setEnabled(enabled);
        textField_year.setEnabled(enabled);
        textField_subject.setEnabled(enabled);
        textField_personalID.setEnabled(enabled);
        textField_title.setEnabled(enabled);
        textField_address.setEnabled(enabled);
        textField_zip.setEnabled(enabled);
        textField_city.setEnabled(enabled);
        textField_land.setEnabled(enabled);
        textField_phone.setEnabled(enabled);
        textField_function.setEnabled(enabled);
        textField_facultyID.setEnabled(enabled);
        textField_openUssServerName.setEnabled(enabled);
        comboBox_language.setEnabled(enabled);
        label_titel.setEnabled(enabled);
        label_firstName.setEnabled(enabled);
        label_lastName.setEnabled(enabled);
        label_email.setEnabled(enabled);
        label_address.setEnabled(enabled);
        label_zip.setEnabled(enabled);
        label_city.setEnabled(enabled);
        label_land.setEnabled(enabled);
        label_phone.setEnabled(enabled);
        label_year.setEnabled(enabled);
        label_language.setEnabled(enabled);
        label_function.setEnabled(enabled);
        label_facultyID.setEnabled(enabled);
        label_subject.setEnabled(enabled);
        label_personalID.setEnabled(enabled);
        label_mandatoryEntries.setEnabled(enabled);
        label_openUssServername.setEnabled(enabled);
    }
    
    public boolean isOpenUssUserRequested() {
        return checkBox_enableOpenUSS.isSelected();
    }

    public String verifyInput() {    
        String errorString = "";
        if (textField_newUserName.getText().length() == 0) {
            errorString += internationalization.getString("label.username.title") + ": " +
                internationalization.getString("text.missingValue") + "\n";
        }
        if (getPassword().length() == 0) {
            errorString += internationalization.getString("label.password.title") + ": " +
                internationalization.getString("text.missingValue") + "\n";
        }
        if (getConfirmedPassword().length() == 0) {
            errorString += internationalization.getString("label.confirmPassword.title") + ": " +
                internationalization.getString("text.missingValue") + "\n";
        }
        if (!isPasswordConfirmed()) {
            errorString += internationalization.getString("text.passwordNotConfirmed") + "\n";
        }

        userType = "Student";
        code = "s3S5u7L4s2f";
        openUssServerName = textField_openUssServerName.getText();
        firstName = textField_firstName.getText();
        lastName = textField_lastName.getText();
        userName = textField_newUserName.getText();
        password = getPassword();
        retypePassword = getConfirmedPassword();
        email = textField_email.getText();
        year = textField_year.getText();
        subject = textField_subject.getText();
        personalID = textField_personalID.getText();
        title = textField_title.getText();
        address = textField_title.getText();
        city = textField_city.getText();
        postcode = textField_zip.getText();
        land = textField_land.getText();
        telephonenumber = textField_phone.getText();
        function = textField_function.getText();
        faculty_id = textField_facultyID.getText();
        language_id = languageIds[comboBox_language.getSelectedIndex()];   
        
        if (checkBox_enableOpenUSS.isSelected()) {
            if (firstName.length() == 0) {
                errorString += internationalization.getString("label.textField.firstName") + ": " +
                    internationalization.getString("text.missingValue") + "\n";
            }
            if (lastName.length() == 0) {
                errorString += internationalization.getString("label.textField.lastName") + ": " +
                    internationalization.getString("text.missingValue") + "\n";
            }
            if (email.length() == 0) {
                errorString += internationalization.getString("label.textField.email") + ": " +
                    internationalization.getString("text.missingValue") + "\n";
            }
        }
        
        if (errorString.length() > 0) {
            return errorString;
        }
        return null;
    }
    
}
