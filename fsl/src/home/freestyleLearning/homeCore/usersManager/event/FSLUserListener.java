/* Generated by Freestyle Learning Group */

package freestyleLearning.homeCore.usersManager.event;

public interface FSLUserListener {
    void userChanged(FSLUserEvent userEvent);
    
    void userLogout(FSLUserEvent userEvent);

    void userRoleChanged(FSLUserEvent userEvent);
}
