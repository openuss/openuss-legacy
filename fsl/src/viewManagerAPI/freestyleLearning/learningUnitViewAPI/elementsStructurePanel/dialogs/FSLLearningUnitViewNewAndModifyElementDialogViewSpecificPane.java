/* Generated by Freestyle Learning Group */

package freestyleLearning.learningUnitViewAPI.elementsStructurePanel.dialogs;

import javax.swing.JComponent;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElement;

// An implementing class must be a subclass of JComponent
public interface FSLLearningUnitViewNewAndModifyElementDialogViewSpecificPane {
    // should result null if the input is ok
    // otherwise it should result a string which has one line for every wrong input field
    String verifyInput(FSLLearningUnitViewElement editedLearningUnitViewElement,
        FSLLearningUnitViewElement referenceLearningUnitViewElement, boolean asFolder, int insertPosition);

    void setInputFieldsDefaults(FSLLearningUnitViewElement learningUnitViewElement);

    // called whenever the folder check box changes
    void setEnabled(boolean enabled);
}