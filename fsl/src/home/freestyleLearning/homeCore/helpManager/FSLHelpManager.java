/* Generated by Freestyle Learning Group */

package freestyleLearning.homeCore.helpManager;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import freestyleLearning.homeCore.helpManager.dialogs.*;
import freestyleLearning.homeCore.learningUnitsManager.*;
import freestyleLearning.homeCore.programConfigurationManager.event.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.*;
import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.independent.util.*;

public class FSLHelpManager {
    private FLGInternationalization internationalization;
    private FSLHelpManagerDialog helpManagerDialog;
    private FSLHelpHistoryManager historyManager;
    private FSLLearningUnitViewsManager viewsManager;
    private String programDirectoryAbsolutePath;
    private JMenuItem menuItem_help;
    private FSLProgramConfigurationEventGenerator programConfigurationEventGenerator;
    private FLGSystemInformationPanel systemInfoPanel;
    private FLGOptionPane systemInfoDialog;
    private FSLHelpManagerDialog imprintDialog;

    /**
     * Manager class for FSL online help
     * @author Mirko Wahn
     * @version 1.0
     */
    public FSLHelpManager(FSLProgramConfigurationEventGenerator programConfigurationEventGenerator) {
        this.programConfigurationEventGenerator = programConfigurationEventGenerator;
        internationalization = new FLGInternationalization("freestyleLearning.homeCore.helpManager.internationalization",
            FSLHelpManager.class.getClassLoader());
        systemInfoPanel = new FLGSystemInformationPanel(FLGUIUtilities.SHADE_BLUE_DARK, FLGUIUtilities.SHADE_BLUE_LIGHT, 
            FLGUIUtilities.SHADE_BLUE_DARK, FLGUIUtilities.SHADE_BLUE_LIGHT);
        systemInfoDialog = new FLGOptionPane(null, systemInfoPanel,
            internationalization.getString("dialog.systemInformationDialog.title"),
            FLGOptionPane.OK_OPTION, FLGOptionPane.PLAIN_MESSAGE, false);
    }
    
    public FSLHelpManager() {
        internationalization = new FLGInternationalization("freestyleLearning.homeCore.helpManager.internationalization",
            FSLHelpManager.class.getClassLoader());
    }

    public void init(FSLLearningUnitsManager learningUnitsManager, String programDirectoryAbsolutePath,
        FLGLongLastingOperationStatus progressStatus) {
            historyManager = new FSLHelpHistoryManager();
            this.viewsManager = learningUnitsManager.getLearningUnitViewsManager();
            learningUnitsManager.addLearningUnitListener(new FSLHelpManager_LearningUnitListener());
            helpManagerDialog = new FSLHelpManagerDialog(programConfigurationEventGenerator,
                internationalization.getString("window.title"),
                learningUnitsManager, programDirectoryAbsolutePath);
            progressStatus.setStatusValue(progressStatus.getStatusValue() + (int)(progressStatus.getStepSize() / 9.));
            historyManager.init(helpManagerDialog);
            progressStatus.setStatusValue(progressStatus.getStatusValue() + (int)(progressStatus.getStepSize() / 9.));
            helpManagerDialog.init(historyManager, viewsManager);
    }

    public JMenu getHelpMenu() {
        JMenu helpMenu = new JMenu(internationalization.getString("menu.help.title"));
        helpMenu.setMnemonic(internationalization.getString("menu.help.mnemonic").charAt(0));
        menuItem_help = createMenuItem("menu.help.help.title");
        menuItem_help.setMnemonic(internationalization.getString("menu.help.help.mnemonic").charAt(0));
        menuItem_help.setAccelerator(KeyStroke.getKeyStroke("F1"));
        menuItem_help.getActionMap().put("help",
            new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    menuItem_help.getAction().actionPerformed(e);
                }
            });
        helpMenu.add(menuItem_help);
        helpMenu.addSeparator();
        JMenuItem menuItem = createMenuItem("menu.help.impressum.title");
        menuItem.setMnemonic(internationalization.getString("menu.help.systemInfo.mnemonic").charAt(0));
        // temporary disabled
        // helpMenu.add(menuItem);
        menuItem = createMenuItem("menu.help.programInfo.title");
        menuItem.setMnemonic(internationalization.getString("menu.help.programInfo.mnemonic").charAt(0));
        helpMenu.add(menuItem);
        menuItem = createMenuItem("menu.help.systemInfo.title");
        menuItem.setMnemonic(internationalization.getString("menu.help.systemInfo.mnemonic").charAt(0));
        helpMenu.add(menuItem);
        return helpMenu;
    }

    private JMenuItem createMenuItem(String menuItemId) {
        Action action = new AbstractAction(internationalization.getString(menuItemId)) {
            public void actionPerformed(ActionEvent e) {
                menuItemSelected(((JMenuItem)e.getSource()).getActionCommand());
            }
        };
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setActionCommand(menuItemId);
        return menuItem;
    }

    private void menuItemSelected(String menuItemId) {
        if (menuItemId == "menu.help.help.title") showMainHelpWindow();
        if (menuItemId == "menu.help.programInfo.title") showProgramInformation();
        if (menuItemId == "menu.help.systemInfo.title") showSystemInformation();
        if (menuItemId == "menu.help.impressum.title") showImprint();
    }
    
    private void showImprint() {
        if (imprintDialog == null) {
            String title = internationalization.getString("menu.help.impressum.title");
            java.net.URL baseUrl = getClass().getClassLoader().getResource("freestyleLearning/homeCore/help/flg/");
            String fileName = "flg/flg.html";
            imprintDialog = new FSLHelpManagerDialog(title);
            imprintDialog.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            imprintDialog.showPage(fileName, baseUrl);
        }
        imprintDialog.setVisible(true);
    }
    
    private void showMainHelpWindow() {
        helpManagerDialog.setVisible(true);
    }
    
    private void showSystemInformation() {
        systemInfoDialog.setVisible(true);
    }

    private void showProgramInformation() {
        final FLGImageDialog programInformationDialog =
            new FLGImageDialog(internationalization.getString("help.programInfo.title"),
            FSLHelpManager.class.getClassLoader().getResource("freestyleLearning/homeCore/images/splash.gif"));
        FLGInternationalization internationalization_version = new FLGInternationalization("freestyleLearning.homeCore.mainFrame.internationalization",
            FSLHelpManager.class.getClassLoader());
        String buttonLabel = internationalization_version.getString("frameTitle");
        JButton dismissButton = new JButton(buttonLabel);
        dismissButton.setOpaque(false);
        AbstractAction dismissAction = new AbstractAction(dismissButton.getText()) {
            public void actionPerformed(ActionEvent e) {
                programInformationDialog.dispose();
            }
        };
        dismissButton.setAction(dismissAction);
        dismissButton.setBounds(28, 200, dismissButton.getPreferredSize().width, dismissButton.getPreferredSize().height);
        programInformationDialog.setDefaultButton(dismissButton);
        programInformationDialog.addToLayeredPane(dismissButton, 2);
    }
    
    
    class FSLHelpManager_LearningUnitListener extends FSLLearningUnitAdapter {
        public void learningUnitActivated(FSLLearningUnitEvent event) {
            helpManagerDialog.setMainHelpText();
        }
    }
    
}
