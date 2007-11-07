package freestyleLearningGroup.independent.gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;

public class MemoryImageButton extends FLGAbstractImageButton {
    public MemoryImageButton(Image image) {
        setImage(image);
    }

    public void setAction(final Action action) {
        // Handle enabling of actions
        action.addPropertyChangeListener(
            new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent event) {
                    setEnabled(action.isEnabled());
                }
            });
        // Delegate actions to the actions
        addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    action.actionPerformed(e);
                }
            });
    }
}
