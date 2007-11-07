/*
 * FSLExportLearningUnitViewStructureDialog.java
 *
 * Created on 3. Juni 2005, 12:18
 */

package freestyleLearning.homeCore.learningUnitsManager.dialogs;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import freestyleLearning.homeCore.learningUnitsManager.*;
import freestyleLearningGroup.independent.util.*;
import freestyleLearningGroup.independent.gui.*;

/**
 *
 * @author  Mirko Wahn
 */
public class FSLExportLearningUnitViewStructureDialog implements FLGDialogInputVerifier {
    private JPanel componentsPanel;
    private JCheckBox[] checkBoxes_learningUnitViewManagers;
    private JRadioButton[] radioButtons_learningUnitViewManagers;
    private String[][] installedLearningUnitViewManagersIdsAndTitles;
    private FLGInternationalization internationalization;
    
    /** Creates a new instance of FSLExportLearningUnitViewStructureDialog */
    public FSLExportLearningUnitViewStructureDialog(String[][] installedLearningUnitViewManagersIdsAndTitles) {
        internationalization = new FLGInternationalization("freestyleLearning.homeCore.learningUnitsManager.dialogs.internationalization",
            getClass().getClassLoader());
        this.installedLearningUnitViewManagersIdsAndTitles = installedLearningUnitViewManagersIdsAndTitles;
        buildIndependentUI();
    }
    
    protected void buildIndependentUI() {
        componentsPanel = new JPanel(new BorderLayout());
        JPanel panel_selectStructureFrom = new JPanel(new FLGColumnLayout());
        JPanel panel_exportStructureTo = new JPanel(new FLGColumnLayout());
        JPanel panel_image = new JPanel(new GridLayout(1,1));
        panel_selectStructureFrom.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(internationalization.getString("border.selectFromPanel.title")),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        panel_exportStructureTo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(internationalization.getString("border.exportToPanel.title")),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        panel_image.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        checkBoxes_learningUnitViewManagers = new JCheckBox[installedLearningUnitViewManagersIdsAndTitles.length];
        radioButtons_learningUnitViewManagers = new JRadioButton[installedLearningUnitViewManagersIdsAndTitles.length];
        ButtonGroup bg_selectFrom = new ButtonGroup();
        for (int i = 0; i < installedLearningUnitViewManagersIdsAndTitles.length; i++) {
            // select from panel
            radioButtons_learningUnitViewManagers[i] = new JRadioButton(installedLearningUnitViewManagersIdsAndTitles[i] [1]);
            radioButtons_learningUnitViewManagers[i].addActionListener(new RadioButtonActionAdapter());
            bg_selectFrom.add(radioButtons_learningUnitViewManagers[i]);
            panel_selectStructureFrom.add(radioButtons_learningUnitViewManagers[i], FLGColumnLayout.LEFTEND);
            // export to panel
            checkBoxes_learningUnitViewManagers[i] = new JCheckBox(installedLearningUnitViewManagersIdsAndTitles[i] [1]);
            panel_exportStructureTo.add(checkBoxes_learningUnitViewManagers[i], FLGColumnLayout.LEFTEND);
        }
        JScrollPane sp_exportFrom = new JScrollPane(panel_selectStructureFrom);
        JScrollPane sp_exportTo = new JScrollPane(panel_exportStructureTo);
        sp_exportFrom.setBorder(BorderFactory.createEmptyBorder());
        sp_exportTo.setBorder(BorderFactory.createEmptyBorder());
        
        panel_image.add(new JLabel(new ImageIcon(
            getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/dialogs/images/exportStructure.gif"))));
        
        int height = panel_selectStructureFrom.getPreferredSize().height;
        panel_selectStructureFrom.setPreferredSize(new Dimension(200, height));
        panel_exportStructureTo.setPreferredSize(new Dimension(200, height));
        panel_image.setPreferredSize(new Dimension(100,50));

        componentsPanel.add(sp_exportFrom, BorderLayout.WEST);
        componentsPanel.add(panel_image, BorderLayout.CENTER);
        componentsPanel.add(sp_exportTo, BorderLayout.EAST);
    }
    
    private class RadioButtonActionAdapter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JRadioButton source = (JRadioButton)e.getSource();
            for (int i = 0; i < radioButtons_learningUnitViewManagers.length; i++) {
                checkBoxes_learningUnitViewManagers[i].setEnabled(!checkBoxes_learningUnitViewManagers[i].getText().equals(source.getText()));
            }
        }
    }
    
    public boolean showDialog() {
        int returnValue = FLGOptionPane.showConfirmDialog(this, 
            componentsPanel, internationalization.getString("label.exportStructureDialog.title"),
            FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE);
        return returnValue == FLGOptionPane.OK_OPTION;
    }
    
    public static void main(String[] args) {
        String[][] idsandtitles = { 
            { "test", "Test Unit" }, { "bla", "Blubb"}, { "blubb", "Blah"}, 
            { "test", "Test Unit" }, { "bla", "Blubb"}, { "blubb", "Blah"}, 
            { "test", "Test Unit" }, { "bla", "Blubb"}, { "blubb", "Blah"},
            { "test", "Test Unit" }, { "bla", "Blubb"}, { "blubb", "Blah"}, 
            { "test", "Test Unit" }, { "bla", "Blubb"}, { "blubb", "Blah"}
        };
        FSLExportLearningUnitViewStructureDialog dialog = 
            new FSLExportLearningUnitViewStructureDialog(idsandtitles);
        System.out.println(dialog.showDialog());
        System.exit(0);
    }
    
    /**
     *  Return IDs of View Managers to which the structure is to be exported to
     *  @return <code>String[]</code> array containing selected view manager ids
     */
    public String[] getExportToLearningUnitViewManagerIds() {
        java.util.List exportToManagerIds = new ArrayList();
        for (int i = 0; i < installedLearningUnitViewManagersIdsAndTitles.length; i++) {
            if (checkBoxes_learningUnitViewManagers[i].isSelected() && !radioButtons_learningUnitViewManagers[i].isSelected()) {
                exportToManagerIds.add(installedLearningUnitViewManagersIdsAndTitles[i][0]);
            }
        }
        return (String[])exportToManagerIds.toArray(new String[exportToManagerIds.size()]);    
    }
    
    /**
     *  Return ID of View Manager from which the structure is to be exported
     *  @return <code>String</code> selected view manager id
     */
    public String getExportFromLearningUnitViewManagerId() {
        for (int i = 0; i < installedLearningUnitViewManagersIdsAndTitles.length; i++) {
            if (radioButtons_learningUnitViewManagers[i].isSelected()) {
                return installedLearningUnitViewManagersIdsAndTitles[i][0];
            }
        }
        return null;
    }
    
    public String verifyInput() {
        boolean sourceSelectionOK = false;
        boolean targetSelectionOK = false;
        String verifyMessage = null;
        for (int i = 0; i < radioButtons_learningUnitViewManagers.length; i++) {
            if (radioButtons_learningUnitViewManagers[i].isSelected()) sourceSelectionOK = true;
        }
        
        for (int i = 0; i < checkBoxes_learningUnitViewManagers.length; i++) {
            if (checkBoxes_learningUnitViewManagers[i].isSelected() 
                && !radioButtons_learningUnitViewManagers[i].isSelected()) targetSelectionOK = true;
        }
        if (!sourceSelectionOK) {
            verifyMessage = internationalization.getString("exportStructure.noSourceSelected.message") + " \n";
        }
        if (!targetSelectionOK) {
            if (verifyMessage == null) verifyMessage = "";
            verifyMessage += internationalization.getString("exportStructure.invalidTargetSelected.message") + " \n";
        }
        return verifyMessage;
    }    
    
    
}

