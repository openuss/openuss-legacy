/* Generated by Freestyle Learning Group */

package freestyleLearning.homeCore.usersManager.data.xmlBindingSubclasses;

import freestyleLearning.homeCore.usersManager.data.xmlBinding.UserDescriptor;

public class FSLUserDescriptor extends UserDescriptor {
    /** Possible roles assigned to a user */
    public final static String ADMINISTRATOR_ROLE = "administrator";
    public final static String AUTHOR_ROLE = "author";
    public final static String LEARNER_ROLE = "learner";

    /** Create a new <code>FSL_UserDescriptor</code> */
    public static FSLUserDescriptor createFSL_UserDescriptor(String userID, String userName, String password, Object[] roles) {
        FSLUserDescriptor result = new FSLUserDescriptor();
        result.setId(userID);
        result.setUserName(userName);
        result.setUserPassword(password);
        result.emptyAllowedUserRoles();
        for (int i = 0; i < roles.length; i++)
            result.getAllowedUserRoles().add(roles[i]);
        result.setCurrentUserRole(roles[0].toString());
        result.setLearningUnitsUserDataDirectoryName(userID);
        return result;
    }

    /** Returns the username as a string reprasentation for this object. */
    public String toString() {
        return getUserName();
    }
}
