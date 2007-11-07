/*
 * FSLLearningUnitViewSortElementsDialog.java
 *
 * Created on 30. Mai 2005, 13:53
 */

package freestyleLearning.learningUnitViewAPI.elementsStructurePanel.dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import freestyleLearning.learningUnitViewAPI.*;
import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.independent.util.*;
/**
 *
 * @author  Mirko Wahn
 */
public class FSLLearningUnitViewSortElementsDialog implements FLGDialogInputVerifier {
    public final static int SORT_ASCENDING = 1;
    public final static int SORT_DESCENDING = 2;
    private FLGInternationalization internationalization;
    private JPanel dialogContentComponent;
    private JRadioButton radioButton_sortAscending;
    private JRadioButton radioButton_sortDescending;
    private int sortSelection;
    
    /** Creates a new instance of FSLLearningUnitViewSortElementsDialog */
    public FSLLearningUnitViewSortElementsDialog() {
        internationalization = new FLGInternationalization("freestyleLearning.learningUnitViewAPI.elementsStructurePanel.internationalization",
        FSLLearningUnitViewNewElementDialog.class.getClassLoader());
        buildIndependentUI();
    }
    
    public boolean showDialog() {
        int returnValue = FLGOptionPane.showConfirmDialog(
            dialogContentComponent, internationalization.getString("dialog.sortElements.title"),
            FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE);
        return returnValue == FLGOptionPane.OK_OPTION;
    }
    
    protected void buildIndependentUI() {
        dialogContentComponent = new JPanel(new FLGColumnLayout());
        dialogContentComponent.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        radioButton_sortAscending = new JRadioButton(internationalization.getString("radioButton.sortAscending.label"));
        radioButton_sortDescending = new JRadioButton(internationalization.getString("radioButton.sortDescending.label"));
        radioButton_sortAscending.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sortSelection = SORT_ASCENDING;
            }
        });
        radioButton_sortDescending.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sortSelection = SORT_DESCENDING;
            }
        });
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton_sortAscending);
        buttonGroup.add(radioButton_sortDescending);
        radioButton_sortAscending.setSelected(true);
        sortSelection = SORT_ASCENDING;
        dialogContentComponent.add(radioButton_sortAscending, FLGColumnLayout.LEFTEND);
        dialogContentComponent.add(radioButton_sortDescending, FLGColumnLayout.LEFTEND);
    }
    
    public String verifyInput() {
        return null;
    }
    
    public int getSortSelection() {
        return sortSelection;
    }
    
    public static void main(String[] args) {
        System.out.println((new FSLLearningUnitViewSortElementsDialog()).showDialog());
        System.exit(0);
    }
    
}
