/* Generated by Freestyle Learning Group */

package freestyleLearning.learningUnitViewAPI.elementsStructurePanel.dialogs;

import javax.swing.*;

import freestyleLearning.learningUnitViewAPI.*;
import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.independent.util.*;

public class FSLLearningUnitViewMoveElementDialog {
    public static final int INSERT_BEFORE = 1;
    public static final int INSERT_AFTER = 2;
    public static final int INSERT_AS_CHILD = 3;
    private FLGInternationalization internationalization;
    private JPanel dialogContentComponent;
    private JRadioButton radioButton_insertBefore;
    private JRadioButton radioButton_insertAfter;
    private JRadioButton radioButton_insertAsChild;
    private int insertCommand;

    public FSLLearningUnitViewMoveElementDialog() {
        internationalization = new FLGInternationalization("freestyleLearning.learningUnitViewAPI.elementsStructurePanel.internationalization",
            getClass().getClassLoader());
        buildIndependentUI();
    }

    public boolean showDialog(FSLLearningUnitViewElementsManager learningUnitViewElementsManager, String referenceElementId) {
        buildDependentUI(learningUnitViewElementsManager, referenceElementId);
        int returnValue = FLGOptionPane.showConfirmDialog(dialogContentComponent, internationalization.getString("dialog.moveElement.title"),
            FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE);
        if (radioButton_insertBefore.isSelected()) insertCommand = INSERT_BEFORE;
        if (radioButton_insertAfter.isSelected()) insertCommand = INSERT_AFTER;
        if (radioButton_insertAsChild.isSelected()) insertCommand = INSERT_AS_CHILD;
        return returnValue == FLGOptionPane.OK_OPTION;
    }

    public int getInsertCommand() {
        return insertCommand;
    }

    private void buildIndependentUI() {
        dialogContentComponent = new JPanel(new FLGColumnLayout());
        dialogContentComponent.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        radioButton_insertBefore = new JRadioButton(internationalization.getString("label.insertBefore.title"));
        dialogContentComponent.add(radioButton_insertBefore, FLGColumnLayout.LEFTEND);
        radioButton_insertAfter = new JRadioButton(internationalization.getString("label.insertAfter.title"));
        dialogContentComponent.add(radioButton_insertAfter, FLGColumnLayout.LEFTEND);
        radioButton_insertAsChild = new JRadioButton(internationalization.getString("label.insertAsChild.title"));
        dialogContentComponent.add(radioButton_insertAsChild, FLGColumnLayout.LEFTEND);
        ButtonGroup checkBoxGroup = new ButtonGroup();
        checkBoxGroup.add(radioButton_insertBefore);
        checkBoxGroup.add(radioButton_insertAfter);
        checkBoxGroup.add(radioButton_insertAsChild);
    }

    private void buildDependentUI(FSLLearningUnitViewElementsManager elementsManager, String referenceElementId) {
        radioButton_insertAfter.setSelected(true);
        if (elementsManager.isOriginalElementsOnly()) {
            radioButton_insertBefore.setEnabled(true);
            radioButton_insertAfter.setEnabled(true);
        }
        else {
            // user is a learner
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
                if (nextSiblingElementId == null || (elementsManager.getLearningUnitViewUserElement(nextSiblingElementId) != null &&
                    elementsManager.getLearningUnitViewOriginalElement(nextSiblingElementId) == null)) {
                        radioButton_insertAfter.setEnabled(true);
                }
                else {
                    radioButton_insertAfter.setEnabled(false);
                }
            }
        }
    }
}
