/*
 * FSLCreateAutomaticLinksDialog.java
 *
 * Created on 4. Juli 2005, 17:21
 */

package freestyleLearning.homeCore.learningUnitsManager.dialogs;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import freestyleLearning.homeCore.learningUnitsManager.*;
import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.independent.util.*;

/**
 * @author  Mirko Wahn (Freestyle Learning Group)
 */
public class FSLCreateAutomaticLinksDialog implements FLGDialogInputVerifier{
    public static final int LINK_ONE_PER_PARAGRAPH = 0;
    public static final int LINK_ONE_PER_PAGE = 1;
    public static final int LINK_ALL = 2;
    public static final int CREATE_LINK = 0;
    public static final int RECOVER_STATE = 1;
    public int task = CREATE_LINK;
    private int linkMethodSelection = LINK_ALL;
    private FLGInternationalization internationalization;
    private JTabbedPane tabbedPane;
    private JPanel panel_stateList;
    private JCheckBox checkBox_createIndex;
    private JCheckBox checkBox_linkAuthorFiles;
    private JCheckBox checkBox_linkUserFiles;
    private JRadioButton[] radioButtons_linkMethods;
    private JCheckBox[] checkBoxes_learningUnitViewManagers;
    private JCheckBox[] checkBoxes_deleteStates;
    private JRadioButton[] radioButtons_learningUnitViewManagers;
    private JRadioButton[] radioButtons_recoveryStates;
    private String[][] installedLearningUnitViewManagersIdsAndTitles;
    private String[][] supportingViewManagerIdsAndTitles;
    private String[] stateLabels;
    private String[] stateDirectoryNames;
    private String linkTargetName;
    private JButton button_deleteState;
    private boolean userRoleIsAuthor;
    private String currentUserName;
    
    /**
     *  Create new instance of FSLCreateAutomaticLinksDialog.
     *  @param <code>installedLearningUnitViewManagersIdsAndTitles</code> 2-dim array of ids andzzz titles of view managers to select targets from
     *  @param <code>supportingViewManagerIdsAndTitles</code> 2-dim array of ids and titles of view managers supporting automatic link creation
     */
    public FSLCreateAutomaticLinksDialog(String[][] installedLearningUnitViewManagersIdsAndTitles, 
        String[][] supportingViewManagerIdsAndTitles, 
        String[] stateDirectoryNames, boolean userRoleIsAuthor, String currentUserName) {
            internationalization = new FLGInternationalization("freestyleLearning.homeCore.learningUnitsManager.dialogs.internationalization",
                FSLCreateAutomaticLinksDialog.class.getClassLoader());
            this.installedLearningUnitViewManagersIdsAndTitles = installedLearningUnitViewManagersIdsAndTitles;
            this.supportingViewManagerIdsAndTitles = supportingViewManagerIdsAndTitles;
            this.userRoleIsAuthor = userRoleIsAuthor;
            this.currentUserName = currentUserName;
            this.stateDirectoryNames = stateDirectoryNames;
            this.stateLabels = createStateLabels(stateDirectoryNames);
            buildIndependentUI();
    }
    
    /**
     *  Create and init UI components
     */
    protected void buildIndependentUI() {
        JPanel panel_selectStructureFrom = new JPanel(new FLGColumnLayout());
        JPanel panel_exportStructureTo = new JPanel(new FLGColumnLayout());
        JPanel panel_image = new JPanel(new GridLayout(1,1));
        panel_selectStructureFrom.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(internationalization.getString("border.createLinks.selectFromPanel.title")),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        panel_exportStructureTo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(internationalization.getString("border.createLinks.selectToPanel.title")),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        panel_image.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        // CheckBox for creating index
        checkBox_createIndex = new JCheckBox(internationalization.getString("checkBox.createIndex.label"), true);
        // CheckBoxes for selecting user role files
        checkBox_linkAuthorFiles = new JCheckBox(internationalization.getString("checkBox.linkAuthorFiles.label"), true);
        checkBox_linkAuthorFiles.setEnabled(userRoleIsAuthor);
        checkBox_linkAuthorFiles.setSelected(userRoleIsAuthor);
        checkBox_linkUserFiles = new JCheckBox(internationalization.getString("checkBox.linkUserFiles.label") + " (" + currentUserName + ")", true);
        JPanel panel_selectionOptions = new JPanel(new FLGColumnLayout());
//        panel_selectionOptions.add(checkBox_createIndex, FLGColumnLayout.LEFTEND);
        panel_selectionOptions.add(checkBox_linkAuthorFiles, FLGColumnLayout.LEFTEND);
        panel_selectionOptions.add(checkBox_linkUserFiles, FLGColumnLayout.LEFTEND);
        
        // RadioButtons for linking method selection
        radioButtons_linkMethods = new JRadioButton[3];
        radioButtons_linkMethods[LINK_ONE_PER_PARAGRAPH] = new JRadioButton(internationalization.getString("radioButton.onePerParagraph.label"));
        radioButtons_linkMethods[LINK_ONE_PER_PAGE] = new JRadioButton(internationalization.getString("radioButton.onePerPage.label"));
        radioButtons_linkMethods[LINK_ALL] = new JRadioButton(internationalization.getString("radioButton.everyOccurence.label"));
        ButtonGroup bg_linkMethods = new ButtonGroup();
        JPanel panel_linkMethod = new JPanel(new FLGColumnLayout());
        for (int i = 0; i < radioButtons_linkMethods.length; i++) {
            if (linkMethodSelection == i) radioButtons_linkMethods[i].setSelected(true);
            radioButtons_linkMethods[i].addActionListener(new LinkMethodsActionAdapter());
            bg_linkMethods.add(radioButtons_linkMethods[i]);
            panel_linkMethod.add(radioButtons_linkMethods[i], FLGColumnLayout.LEFTEND);
        }
        
        checkBoxes_learningUnitViewManagers = new JCheckBox[supportingViewManagerIdsAndTitles.length];
        radioButtons_learningUnitViewManagers = new JRadioButton[installedLearningUnitViewManagersIdsAndTitles.length];
        ButtonGroup bg_selectFrom = new ButtonGroup();
        for (int i = 0; i < installedLearningUnitViewManagersIdsAndTitles.length; i++) {
            // select from panel
            radioButtons_learningUnitViewManagers[i] = new JRadioButton(installedLearningUnitViewManagersIdsAndTitles[i] [1]);
            radioButtons_learningUnitViewManagers[i].addActionListener(new RadioButtonActionAdapter());
            bg_selectFrom.add(radioButtons_learningUnitViewManagers[i]);
            panel_selectStructureFrom.add(radioButtons_learningUnitViewManagers[i], FLGColumnLayout.LEFTEND);
        }
        for (int i = 0; i < supportingViewManagerIdsAndTitles.length; i++) {
            // export to panel
            checkBoxes_learningUnitViewManagers[i] = new JCheckBox(supportingViewManagerIdsAndTitles[i] [1]);
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

        JPanel optionPanel = new JPanel(new FLGColumnLayout());
        optionPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(internationalization.getString("border.createLinks.optionPanel.title")),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));      
        optionPanel.add(panel_linkMethod, FLGColumnLayout.LEFT);
        optionPanel.add(new JLabel("              "), FLGColumnLayout.LEFT);
        optionPanel.add(panel_selectionOptions, FLGColumnLayout.LEFTEND);

        JPanel componentsPanel = new JPanel(new BorderLayout());
        componentsPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        componentsPanel.add(optionPanel, BorderLayout.NORTH);
        componentsPanel.add(panel_image, BorderLayout.CENTER);
        componentsPanel.add(sp_exportTo, BorderLayout.WEST);
        componentsPanel.add(sp_exportFrom, BorderLayout.EAST);
        
        // Recovery components
        JPanel recoveryPanel = new JPanel(new BorderLayout());
        recoveryPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5), 
            BorderFactory.createTitledBorder(internationalization.getString("border.stateListPanel.title"))));
        panel_stateList = new JPanel(new FLGColumnLayout());
        panel_stateList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        button_deleteState = new JButton(internationalization.getString("button.deleteState.text"));
        button_deleteState.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSelectedStates();
            }
        });
        button_deleteState.setEnabled(false);
        buttonPanel.add(button_deleteState, FLGColumnLayout.LEFTEND);

        radioButtons_recoveryStates = new JRadioButton[stateLabels.length];
        checkBoxes_deleteStates = new JCheckBox[stateLabels.length];
        ButtonGroup gb_stateSelection = new ButtonGroup();
        for (int i = 0; i < stateLabels.length; i++) {
            radioButtons_recoveryStates[i] = new JRadioButton(stateLabels[i], false);
            radioButtons_recoveryStates[i].setEnabled((stateLabels[i].indexOf(currentUserName) > 0) || ((stateLabels[i].indexOf(currentUserName) < 0) && userRoleIsAuthor));
            gb_stateSelection.add(radioButtons_recoveryStates[i]);
            checkBoxes_deleteStates[i] = new JCheckBox(internationalization.getString("checkBox.delete.text"), false);
            checkBoxes_deleteStates[i].setEnabled((stateLabels[i].indexOf(currentUserName) > 0) || ((stateLabels[i].indexOf(currentUserName) < 0) && userRoleIsAuthor));
            checkBoxes_deleteStates[i].addActionListener(new DeleteCheckBoxesActionListener());
            
            if (!stateLabels[i].equals("---")) {
                panel_stateList.add(radioButtons_recoveryStates[i], FLGColumnLayout.LEFT);
                panel_stateList.add(new JLabel("          "), FLGColumnLayout.LEFT);
                panel_stateList.add(checkBoxes_deleteStates[i], FLGColumnLayout.LEFTEND);
            }
        }
        
        JScrollPane sp_recoveryList = new JScrollPane(panel_stateList);
        sp_recoveryList.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5,5,5,5), 
            BorderFactory.createLineBorder(Color.lightGray)));
        sp_recoveryList.setPreferredSize(new Dimension(panel_stateList.getPreferredSize().width, 200));
        sp_recoveryList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp_recoveryList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        recoveryPanel.add(sp_recoveryList, BorderLayout.CENTER);
        recoveryPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // add panels to tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab(internationalization.getString("tab.createLink.title"), componentsPanel);
        tabbedPane.addTab(internationalization.getString("tab.recovery.title"), recoveryPanel);
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                task = tabbedPane.getModel().getSelectedIndex();
            }
        });
    }
    
    public File getRecoveryStateDirectory() {
        File recoveryStateDirectory = null;
        for (int i = 0; i < radioButtons_recoveryStates.length; i++) {
            if (radioButtons_recoveryStates[i].isSelected()) {
                recoveryStateDirectory = new File(stateDirectoryNames[i]);
            }
        }
        return recoveryStateDirectory;
    }
    
    public int getTastState() {
        return task;
    }
    
    private void buildDependentUI() {
        panel_stateList.removeAll();
        for (int i = 0; i < stateLabels.length; i++) {
            checkBoxes_deleteStates[i] = new JCheckBox(internationalization.getString("checkBox.delete.text"), false);
            checkBoxes_deleteStates[i].setEnabled((stateLabels[i].indexOf(currentUserName) > 0) || ((stateLabels[i].indexOf(currentUserName) < 0) && userRoleIsAuthor));
            checkBoxes_deleteStates[i].addActionListener(new DeleteCheckBoxesActionListener());
            if (!stateLabels[i].equals("---")) {
                panel_stateList.add(radioButtons_recoveryStates[i], FLGColumnLayout.LEFT);
                panel_stateList.add(new JLabel("          "), FLGColumnLayout.LEFT);
                panel_stateList.add(checkBoxes_deleteStates[i], FLGColumnLayout.LEFTEND);
            }
        }
        tabbedPane.repaint();
    }
    
    private void deleteSelectedStates() {
        for (int i = 0; i < stateLabels.length; i++) {
            File stateDir = new File(stateDirectoryNames[i]);
            if (checkBoxes_deleteStates[i].isSelected()) {
                // delete backup dir
                if (FLGOptionPane.showConfirmDialog(
                    internationalization.getString("dialog.deleteQuestion.message1") + "\n" + stateDirectoryNames[i] + " " + internationalization.getString("dialog.deleteQuestion.message2"), 
                    internationalization.getString("dialog.deleteQuestion.title"), 
                    FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.QUESTION_MESSAGE) == FLGOptionPane.APPROVE_OPTION) {
                        FLGFileUtility.deleteDirectory(stateDir);
                        stateLabels[i] = "---";
                }
            }
        }
        button_deleteState.setEnabled(false);
        buildDependentUI();
    }
    
    private String[] createStateLabels(String[] filenameList) {         
        String[] stateLabels = new String[filenameList.length];
        String[] directoryNames = new String[filenameList.length];
        for (int i = 0; i < filenameList.length; i++) {
            if (filenameList[i].lastIndexOf(System.getProperty("file.separator")) > 0) {
                directoryNames[i] = filenameList[i].substring(filenameList[i].lastIndexOf(System.getProperty("file.separator")) + 1);
                String viewManagerDirectory = directoryNames[i].substring(0, directoryNames[i].indexOf("_"));
                viewManagerDirectory = viewManagerDirectory.substring(0,1).toUpperCase() + viewManagerDirectory.substring(1);
                String month = directoryNames[i].substring(directoryNames[i].indexOf("_") + 5, directoryNames[i].indexOf("_") + 8);
                String day = directoryNames[i].substring(directoryNames[i].indexOf("_") + 9, directoryNames[i].indexOf("_") + 11);
                String hour = directoryNames[i].substring(directoryNames[i].indexOf("_") + 12, directoryNames[i].indexOf("_") + 14);
                String minute = directoryNames[i].substring(directoryNames[i].indexOf("_") + 15, directoryNames[i].indexOf("_") + 17);
                String year = directoryNames[i].substring(directoryNames[i].lastIndexOf("_") + 1);
                stateLabels[i] = viewManagerDirectory + ", " + year + "-" + month + "-" + day + " " + hour + ":" + minute;
                if (FLGUtilities.contains(filenameList[i], currentUserName, true)) {
                    stateLabels[i] += " (" + internationalization.getString("label.userData.text") + " " + currentUserName + ")";
                }
                else {
                    stateLabels[i] += " (" + internationalization.getString("label.originalData.text") + ")";                    
                }
            }
            else stateLabels[i] = "---";
        }
        return stateLabels;
    }
    
    private class DeleteCheckBoxesActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (((JCheckBox)e.getSource()).isSelected()) {
                button_deleteState.setEnabled(true);
            }
            else {
                boolean enabled = false;
                for (int cb_ix = 0; cb_ix < checkBoxes_deleteStates.length; cb_ix++) {
                    if (checkBoxes_deleteStates[cb_ix].isSelected()) {
                        enabled = true;
                    }
                }
                button_deleteState.setEnabled(enabled);
            }
        }        
    }
    
    private class LinkMethodsActionAdapter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < radioButtons_linkMethods.length; i++) {
                if (e.getSource() == radioButtons_linkMethods[i]) {
                    linkMethodSelection = i;
                }
            }
        }        
    }
 
    private class RadioButtonActionAdapter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JRadioButton source = (JRadioButton)e.getSource();
            linkTargetName = source.getText();
        }
    }
    
    // for test purposes only
    public static void main(String[] args) {
        String[][] idsandtitles1 = { 
            { "test", "Test Unit" }, { "bla", "Bla"}, { "blubb", "Blubb"} };
        String[][] idsandtitles2 = { 
            { "test", "Test Unit" }, { "bla", "Bla"}, { "blubb", "Blubb"}, 
            { "test", "Test Unit" }, { "bla", "Bla"}, { "blubb", "Blubb"}
        };
        String[] dirNames = { "D:\\Java\\FSL\\dev\\bin\\learningUnits\\testtest\\textStudy_Tue_Aug_09_10-20-27_CEST_2005", "Heute" };
        
        FSLCreateAutomaticLinksDialog dialog = 
            new FSLCreateAutomaticLinksDialog(idsandtitles1, idsandtitles2, dirNames, true, "Hubi");
        System.out.println(dialog.showDialog());
        System.exit(0);
    }
    
    /**
     *  Return file type selection
     *  @return <code>true</code> if author file checkbox selected
     */
    public boolean getLinkAuthorFiles() {
        return checkBox_linkAuthorFiles.isSelected();
    }
    
    /**
     *  Return file type selection
     *  @return <code>true</code> if user file checkbox selected
     */
    public boolean getLinkUserFiles() {
        return checkBox_linkUserFiles.isSelected();
    }
    
    /**
     *  Return currently selected linking method
     *  @return <code>value</code> matching linking selection
     */
    public int getLinkingMethod() {
        return linkMethodSelection;
    }
    
    /**
     *  Return if new search index is to be created before searching for link sources
     *  @return <code>true</code> if index is to be created
     */
    public boolean getCreateIndex() {
        return checkBox_createIndex.isSelected();
    }
    
    /**
     *  Return IDs of View Managers where links will be created
     *  @return <code>String[]</code> array containing selected view manager ids
     */
    public String[] getLinkSourceViewManagerIds() {
        java.util.List createLinksInManagerIds = new java.util.ArrayList();
        for (int i = 0; i < supportingViewManagerIdsAndTitles.length; i++) {
            if (checkBoxes_learningUnitViewManagers[i].isSelected()) {
                createLinksInManagerIds.add(supportingViewManagerIdsAndTitles[i][0]);
            }
        }
        return (String[])createLinksInManagerIds.toArray(new String[createLinksInManagerIds.size()]);    
    }

    /**
     *  Return ID of View Manager to which elements should be linked to
     *  @return <code>String</code> link target view manager id
     */
    public String getLinkTargetViewManagarId() {
        String targetManagerId = null;
        for (int i = 0; i < radioButtons_learningUnitViewManagers.length; i++) {
            if (radioButtons_learningUnitViewManagers[i].getText().equals(linkTargetName)) {
                targetManagerId = installedLearningUnitViewManagersIdsAndTitles[i][0];
                break;
            }
        }
        return targetManagerId;
    }
    
    /**
     *  Display dialog to select link target view and link source views.
     *  @return <code>true</code> if OK (approve option) has been selected
     */
    public boolean showDialog() {
        int returnValue = FLGOptionPane.showConfirmDialog(this, 
            tabbedPane, internationalization.getString("dialog.createAutomaticLinks.title"),
            FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE);
        return returnValue == FLGOptionPane.OK_OPTION;
    }
    
    /**
     * @return <code>String</code> containing hints for dialog inputs
     */
    public String verifyInput() {
        String verifyMessage = null;
        switch (task) {
            case CREATE_LINK: {
                boolean sourceSelectionOK = false;
                boolean targetSelectionOK = false;
                boolean linkFilesSelectionOK = false;
                for (int i = 0; i < radioButtons_learningUnitViewManagers.length; i++) {
                    if (radioButtons_learningUnitViewManagers[i].isSelected()) targetSelectionOK = true;
                }
                for (int i = 0; i < checkBoxes_learningUnitViewManagers.length; i++) {
                    if (checkBoxes_learningUnitViewManagers[i].isSelected()) sourceSelectionOK = true;
                }
                if (checkBox_linkAuthorFiles.isSelected() || checkBox_linkUserFiles.isSelected()) {
                    linkFilesSelectionOK = true;
                }
                if (!sourceSelectionOK) {
                    verifyMessage = internationalization.getString("createLinks.noSourceSelected.message") + " \n";
                }
                if (!targetSelectionOK) {
                    if (verifyMessage == null) verifyMessage = "";
                    verifyMessage += internationalization.getString("createLinks.invalidTargetSelected.message") + " \n";
                }
                if (!linkFilesSelectionOK) {
                    if (verifyMessage == null) verifyMessage = "";
                    verifyMessage += internationalization.getString("createLinks.invalidLinkSourceSelection.message") + " \n";
                }
                return verifyMessage;
            }
            case RECOVER_STATE: {
                boolean recoveryStateSelectionOK = false;
                for (int i = 0; i < radioButtons_recoveryStates.length; i++) {
                    if (radioButtons_recoveryStates[i].isSelected()) recoveryStateSelectionOK = true;
                }
                if (!recoveryStateSelectionOK) {
                    if (verifyMessage == null) verifyMessage = "";
                    verifyMessage += internationalization.getString("recovery.invalidStateSelection.message") + " \n";
                }
             }
        }
        return verifyMessage;
    }    
  
}
