package freestyleLearning.homeCore.usersManager;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import freestyleLearning.homeCore.usersManager.data.xmlBindingSubclasses.FSLUserDescriptor;
import freestyleLearning.homeCore.usersManager.data.xmlBindingSubclasses.FSLUsersDescriptor;
import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.util.FLGInternationalization;

/**
 * This class will be exported to the external administrator tool. TO DO: Export this class to the
 * external administrator tool and complete the code.
 */
class FSLUsersManagementDialog {
    private static String INITIAL_PASSWORD = "freestyle";
    private JPanel dialogContentComponent;
    private FLGInternationalization internationalization;
    private Vector usersData;

    FSLUsersManagementDialog() {
        internationalization = new FLGInternationalization("freestyleLearning.homeCore.usersManager.internationalization",
            FSLUserLoginDialog.class.getClassLoader());
        buildIndependentUI();
    }

    public boolean showDialog(FSLUsersDescriptor usersDescriptor) {
        buildDependentUI();
        usersData = new Vector();
        for (int i = 0; i < usersDescriptor.getUsersDescriptors().size(); i++) {
            FSLUserDescriptor userDescriptor = (FSLUserDescriptor)usersDescriptor.getUsersDescriptors().get(i);
            String[] userEntry = new String[3];
            userEntry[0] = userDescriptor.getId();
            userEntry[1] = userDescriptor.getUserName();
            userEntry[2] = userDescriptor.getUserPassword();
            usersData.add(userEntry);
        }
        int returnValue = FLGOptionPane.showConfirmDialog(dialogContentComponent, internationalization.getString("dialog.usersManagement.title"),
            FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE);
        if (returnValue == FLGOptionPane.OK_OPTION) {
            usersDescriptor.emptyUsersDescriptors();
            for (int i = 0; i < usersData.size(); i++) {
                String[] userEntry = (String[]) usersData.get(i);
                FSLUserDescriptor userDescriptor = new FSLUserDescriptor();
                userDescriptor.setId(userEntry[0]);
                userDescriptor.setUserName(userEntry[1]);
                userDescriptor.setUserPassword(userEntry[2]);
                usersDescriptor.getUsersDescriptors().add(userDescriptor);
            }
        }
        return returnValue == FLGOptionPane.OK_OPTION;
    }

    void buildDependentUI() {
        // fill the list
    }

    private void buildIndependentUI() {
        dialogContentComponent = new JPanel(new BorderLayout());
        dialogContentComponent.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        dialogContentComponent.add(new JButton("TEST"));
        // buttons, actionListener
    }

    private void addUser() {
        // called when addUser-Button is pressed
        // determine new id - Muster ist user1, user2
    }

    private void removeSelectedUser() {
    }
}
