/* Generated by Freestyle Learning Group */

package freestyleLearning.homeCore.programConfigurationManager.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import freestyleLearning.homeCore.mainFrame.FSLMainFrame;
import freestyleLearningGroup.independent.gui.FLGColumnLayout;
import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.util.FLGInternationalization;
import freestyleLearningGroup.independent.util.FLGSystemInformationPanel;

/**
 * This dialog allows the adjustment of the main colors of the Freestyle Learning Home UI. So a
 * user can customize the look of the program. TO DO: Load/Save the colors in a xml file in the directory of the current user.
 * @author Norman Lahme
 * @version 1.0
 */
public class FSLProgramConfigurationDialog {
    private final int BORDER_WIDTH = FSLMainFrame.EMPTY_BORDER_SIZE;
    private FLGInternationalization internationalization;
    private JTabbedPane dialogContentComponent;
    private JPanel colorPanel;
    private JPanel colorInnerPanel;
    private JPanel fontPanel; 
    private JPanel fontInnerPanel;
    private JPanel guiPanel;
    private JPanel fslConfigPanel;
    private JPanel fslChangingViewsPanel;
    private JPanel fslChangingViewsInnerPanel;
    private JPanel fslFrameConfigurationPanel;
    private JPanel fslFrameConfigurationInnerPanel;
    private FLGSystemInformationPanel systemInfoPanel;
    private JButton[] mainFrameBaseColorButtons;
    private JComboBox comboBox_contentPanelBaseFontSize;
    private JComboBox comboBox_structurePanelBaseFontSize;
    private JCheckBox checkBox_enableAutomaticSelection;
    private JCheckBox checkBox_displayWelcomeScreen;
    private JCheckBox checkBox_rememberFrameStatus;
    private JRadioButton[] radioButtons_lookAndFeel = new JRadioButton[3];
    private int contentPanelBaseFontSize;
    private int structurePanelBaseFontSize;
    private Color[] mainFrameBaseColors;
    public Hashtable lookAndFeelClasses;

    public FSLProgramConfigurationDialog() {
        internationalization = new FLGInternationalization("freestyleLearning.homeCore.programConfigurationManager.internationalization",
            getClass().getClassLoader());
        mainFrameBaseColors = new Color[FSLMainFrame.NO_MAINFRAMECOLORS];
        buildIndependentUI();
    }
    
    public boolean showDialog(Hashtable fslConfiguration) {
        this.initColorButtons();
        String elementsContentsPanelBaseFontSizeString =
            "" + UIManager.get("FSLLearningUnitViewElementsContentsPanel.BaseFontSize");
        String structurePanelBaseFontSizeString = "" + UIManager.get("FSLLearningUnitViewElementsStructurePanel.BaseFontSize");
        comboBox_structurePanelBaseFontSize.setSelectedItem(structurePanelBaseFontSizeString);
        comboBox_contentPanelBaseFontSize.setSelectedItem(elementsContentsPanelBaseFontSizeString);
        if (((String)fslConfiguration.get("automaticSelectionEnabled")).equalsIgnoreCase("true")) {
            checkBox_enableAutomaticSelection.setSelected(true);
        }
        else {
            checkBox_enableAutomaticSelection.setSelected(false);
        }
        if (((String)fslConfiguration.get("displayWelcomeScreenEnabled")).equalsIgnoreCase("true")) {
            checkBox_displayWelcomeScreen.setSelected(true);
        }
        else {
            checkBox_displayWelcomeScreen.setSelected(false);
        }
        if (((String)fslConfiguration.get("rememberFrameStatusEnabled")).equalsIgnoreCase("true")) {
            checkBox_rememberFrameStatus.setSelected(true);
        }
        else {
            checkBox_rememberFrameStatus.setSelected(false);
        }
        int returnValue = FLGOptionPane.showConfirmDialog(dialogContentComponent,
            internationalization.getString("dialog.programConfigurationDialog.title"),
            FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE, true);
        for (int i = 0; i < mainFrameBaseColors.length; i++) {
            mainFrameBaseColors[i] = mainFrameBaseColorButtons[i].getBackground();
        }
        contentPanelBaseFontSize = Integer.parseInt((String)comboBox_contentPanelBaseFontSize.getSelectedItem());
        structurePanelBaseFontSize = Integer.parseInt((String)comboBox_structurePanelBaseFontSize.getSelectedItem());
        return returnValue == FLGOptionPane.OK_OPTION;
    }

    public Color[] getMainFrameBaseColors() {
        return mainFrameBaseColors;
    }

    public int getContentPanelBaseFontSize() {
        return contentPanelBaseFontSize;
    }

    public int getStructurePanelBaseFontSize() {
        return structurePanelBaseFontSize;
    }

    public boolean getAutomaticSelectionEnabled() {
        return checkBox_enableAutomaticSelection.isSelected();
    }

    public boolean getDisplayWelcomeScreenEnabled() {
        return checkBox_displayWelcomeScreen.isSelected();
    }
    
    public boolean getRememberFrameStatusEnabled() {
        return checkBox_rememberFrameStatus.isSelected();
    }

    public void buildIndependentUI() {
        // GUI Panel
        guiPanel = new JPanel(new BorderLayout());
        colorPanel = new JPanel(new FLGColumnLayout());
        colorPanel.setBorder(BorderFactory.createTitledBorder(internationalization.getString("border.title.colors")));
        colorInnerPanel = new JPanel(new FLGColumnLayout());
        colorInnerPanel.setBorder(BorderFactory.createEmptyBorder(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH));
        mainFrameBaseColorButtons = new JButton[FSLMainFrame.NO_MAINFRAMECOLORS];
        initColorButtons();
        colorPanel.add(colorInnerPanel, FLGColumnLayout.LEFTEND);
        // FontPanel
        fontPanel = new JPanel(new FLGColumnLayout());
        fontInnerPanel = new JPanel(new FLGColumnLayout());
        fontInnerPanel.setBorder(BorderFactory.createEmptyBorder(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH));
        fontPanel.setBorder(BorderFactory.createTitledBorder(internationalization.getString("border.title.fonts")));
        fontInnerPanel.add(
            new JLabel(internationalization.getString("label.structurePanelBaseFontSize.title")), FLGColumnLayout.LEFT);
        comboBox_structurePanelBaseFontSize = new JComboBox(
            new String[] { "10", "12", "14", "16", "18", "20", "22", "24", "26", "28" });
        JButton resetStructurePanelFontSizeButton =
            new JButton(internationalization.getString("button.label.defaultFontSize"));
        resetStructurePanelFontSizeButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    comboBox_structurePanelBaseFontSize.setSelectedItem("" +
                        UIManager.get("FSLLearningUnitViewDefaultBaseFontSize"));
                }
            });
        fontInnerPanel.add(comboBox_structurePanelBaseFontSize, FLGColumnLayout.LEFT);
        fontInnerPanel.add(resetStructurePanelFontSizeButton, FLGColumnLayout.LEFTEND);
        fontInnerPanel.add(
            new JLabel(internationalization.getString("label.contentPanelBaseFontSize.title")), FLGColumnLayout.LEFT);
        comboBox_contentPanelBaseFontSize = new JComboBox(
            new String[] { "10", "12", "14", "16", "18", "20", "22", "24", "26", "28" });
        JButton resetElementsPanelFontSizeButton = new JButton(internationalization.getString("button.label.defaultFontSize"));
        resetElementsPanelFontSizeButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    comboBox_contentPanelBaseFontSize.setSelectedItem("" +
                        UIManager.get("FSLLearningUnitViewDefaultBaseFontSize"));
                }
            });
        fontInnerPanel.add(comboBox_contentPanelBaseFontSize, FLGColumnLayout.LEFT);
        fontInnerPanel.add(resetElementsPanelFontSizeButton, FLGColumnLayout.LEFTEND);
        fontPanel.add(fontInnerPanel, FLGColumnLayout.LEFTEND);
        guiPanel.add(colorPanel, BorderLayout.NORTH);
        guiPanel.add(fontPanel, BorderLayout.CENTER);
        // FSLConfigurationPanel
        fslConfigPanel = new JPanel(new BorderLayout());
        fslChangingViewsPanel = new JPanel(new FLGColumnLayout());
        fslChangingViewsInnerPanel = new JPanel(new FLGColumnLayout());
        fslFrameConfigurationPanel = new JPanel(new FLGColumnLayout());
        fslFrameConfigurationInnerPanel = new JPanel(new FLGColumnLayout());
//        fslConfigPanel.setBorder(BorderFactory.createEmptyBorder(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH));
        fslFrameConfigurationPanel.setBorder(BorderFactory.createEmptyBorder(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH));
        fslChangingViewsPanel.setBorder(BorderFactory.createTitledBorder(internationalization.getString("border.title.changeViews")));
        fslChangingViewsInnerPanel.setBorder(BorderFactory.createEmptyBorder(BORDER_WIDTH, BORDER_WIDTH,
            BORDER_WIDTH, BORDER_WIDTH));
        fslFrameConfigurationPanel.setBorder(BorderFactory.createTitledBorder(internationalization.getString("border.title.frameConfiguration")));
        fslFrameConfigurationInnerPanel.setBorder(BorderFactory.createEmptyBorder(BORDER_WIDTH, BORDER_WIDTH,
            BORDER_WIDTH, BORDER_WIDTH));
        checkBox_enableAutomaticSelection = new JCheckBox(internationalization.getString("button.label.automaticSelection"));
        fslChangingViewsInnerPanel.add(checkBox_enableAutomaticSelection, FLGColumnLayout.LEFTEND);
        checkBox_displayWelcomeScreen = new JCheckBox(internationalization.getString("button.label.displayWelcomeScreen"));
        fslChangingViewsInnerPanel.add(checkBox_displayWelcomeScreen, FLGColumnLayout.LEFTEND);
        checkBox_rememberFrameStatus = new JCheckBox(internationalization.getString("button.label.rememberFrameStatus"));
        fslFrameConfigurationInnerPanel.add(checkBox_rememberFrameStatus, FLGColumnLayout.LEFTEND);    
        fslChangingViewsPanel.add(fslChangingViewsInnerPanel, FLGColumnLayout.LEFTEND);
        fslFrameConfigurationPanel.add(fslFrameConfigurationInnerPanel, FLGColumnLayout.LEFTEND);
        fslConfigPanel.add(fslChangingViewsPanel, BorderLayout.NORTH);
        fslConfigPanel.add(fslFrameConfigurationPanel, BorderLayout.CENTER);
        // System Information Panel
//        systemInfoPanel = new FLGSystemInformationPanel(); 
        // add components to tabbedPanes
        dialogContentComponent = new JTabbedPane();        
        dialogContentComponent.addTab(internationalization.getString("tab.gui.title"), guiPanel);
        dialogContentComponent.addTab(internationalization.getString("tab.fslConfig.title"), fslConfigPanel);
//        dialogContentComponent.addTab(internationalization.getString("tab.systemInfo.title"), systemInfoPanel);
    }

    private void initColorButtons() {
        colorInnerPanel.removeAll();
        for (int i = 1; i <= mainFrameBaseColors.length; i++) {
            final JButton colorButton = new JButton();
            try {
                String presentLAF = UIManager.getLookAndFeel().getClass().getName();
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                SwingUtilities.updateComponentTreeUI(colorButton);
                UIManager.setLookAndFeel(presentLAF);
            }
            catch(Exception e) {
                System.out.println(e);
            }
            colorButton.setBackground((Color)UIManager.get("FSLMainFrameColor" + i));
            colorButton.setBorder(BorderFactory.createLoweredBevelBorder());
            colorButton.setPreferredSize(new Dimension(40, 20));
            colorButton.setActionCommand("FSLMainFrameColor" + i);
            colorButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton)e.getSource();
                        JColorChooser colorChooser = new JColorChooser();
                        Color newColor = JColorChooser.showDialog(null, internationalization.getString("tab.Colors.title"),
                            button.getBackground());
                        if (newColor != null) button.setBackground(newColor);
                    }
                });
            colorInnerPanel.add(
                new JLabel(internationalization.getString("label.text.mainFrameColor") + " " + i + ": "), FLGColumnLayout.LEFT);
            colorInnerPanel.add(colorButton, FLGColumnLayout.LEFT);
            JButton resetColorButton = new JButton(internationalization.getString("button.label.defaultColor"));
            colorInnerPanel.add(resetColorButton, FLGColumnLayout.LEFTEND);
            final int index = i;
            resetColorButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        colorButton.setBackground((Color)UIManager.get("FSLMainFrameDefaultColor" + index));
                    }
                });
            mainFrameBaseColorButtons[i - 1] = colorButton;
        }
    }
}