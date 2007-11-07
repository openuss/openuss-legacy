package freestyleLearningGroup.independent.tourCreator;

import freestyleLearningGroup.independent.gui.FLGImageUtility;
import freestyleLearningGroup.independent.gui.FLGTextButton3D;
import freestyleLearningGroup.independent.gui.FLGUIUtilities;
import freestyleLearningGroup.independent.gui.FLGEffectPanel;
import freestyleLearningGroup.independent.gui.FLGColumnLayout;


/**
 * This class is used to display set options of a StandardTourCreator and allows modifications.
 * An owning StandardTourCreator has to be given at creation time.
 * Current options will be pulled from the owning StandardTourCreator on every refreshAndShow().
 * @author  Steffen Wachenfeld
 */
public class FLGStandardTourCreatorOptionsFrame extends javax.swing.JFrame {
    
    private FLGStandardTourCreator m_owningTourCreator;
    
    private javax.swing.JSpinner m_spinnerDefaultDisplayTime;
    
    /** 
     * Creates new form FLGStandardTourCreatorOptionsFrame
     * @param a_tourCreator the owning StandardTourCreator which will be queried for its options.
     */
    public FLGStandardTourCreatorOptionsFrame(FLGStandardTourCreator a_tourCreator)
    {
        initComponents();
        this.m_owningTourCreator = a_tourCreator;
        
        /**
         * IconImage
         */
        this.setIconImage(new javax.swing.ImageIcon(FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/propertiesIcon.gif"))).getImage());
       
        
        
        //init JSpinner and required SpinnerModel
                                                            //value, minimum, maximum, stepsize
        javax.swing.SpinnerNumberModel l_spinnerNumberModel = new javax.swing.SpinnerNumberModel(FLGTourElement.PRESET_DISPLAY_TIME,
                                                                                                 FLGTourElement.MIN_DISPLAY_TIME,
                                                                                                 FLGTourElement.MAX_DISPLAY_TIME,
                                                                                                 FLGTourElement.DELTA_DISPLAY_TIME);
        this.m_spinnerDefaultDisplayTime = new javax.swing.JSpinner(l_spinnerNumberModel);
        this.m_panelDefaultDisplayTimeCustom.add(this.m_spinnerDefaultDisplayTime);
        
        //Size and position
        java.awt.Dimension l_screenDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(400, 350);
        setLocation((int)(l_screenDimension.width - getWidth())/2, (int)(l_screenDimension.height - getHeight())/2);
        //System.out.println("Leaving StandardTourCreatorOptionsFrame constructor");
    }//constructor
    
    private void initComponents() {//GEN-BEGIN:initComponents
        m_buttonGroupDoubleClick = new javax.swing.ButtonGroup();
        m_buttonGroupDefaultDisplayTime = new javax.swing.ButtonGroup();
        m_panelMainPanel = new FLGEffectPanel("FLGDialog.background", true);
        m_panelButtonPanel = new javax.swing.JPanel();
        m_buttonOK = new FLGTextButton3D(FLGStandardTourCreator.getInternationalization().getString("dialog.buttonText.ok"), FLGUIUtilities.BASE_COLOR4);
        m_bottonCancel = new FLGTextButton3D(FLGStandardTourCreator.getInternationalization().getString("dialog.buttonText.cancel"), FLGUIUtilities.BASE_COLOR4);
        m_tabbedPaneMain = new javax.swing.JTabbedPane();
        m_panelTabGeneral = new javax.swing.JPanel();
        m_panelInnerTabGeneral = new javax.swing.JPanel();
        m_panelShowTourCreatorOnPerformCaptureAction = new javax.swing.JPanel();
        m_buttonShowTourCreatorOnPerformCaptureAction = new javax.swing.JCheckBox();
        m_panelAskForSaveOptions = new javax.swing.JPanel();
        m_buttonAskForSaveOnNew = new javax.swing.JCheckBox();
        m_buttonAskForSaveOnOpen = new javax.swing.JCheckBox();
        m_buttonAskForSaveOnExit = new javax.swing.JCheckBox();
        m_panelTabTourElements = new javax.swing.JPanel();
        m_panelInnerTabTourElements = new javax.swing.JPanel();
        m_panelAskBeforeDeleteOptions = new javax.swing.JPanel();
        m_buttonAskBeforeDelete = new javax.swing.JCheckBox();
        m_panelDoubleClickOptions = new javax.swing.JPanel();
        m_buttonDoubleClickActionSHOW_ELEMENT = new javax.swing.JRadioButton();
        m_buttonDoubleClickActionELEMENT_PROPERTIES = new javax.swing.JRadioButton();
        m_panelDefaultDisplayTimeOptions = new javax.swing.JPanel();
        m_buttonDefaultDisplayTimeInfinite = new javax.swing.JRadioButton();
        m_buttonDefaultDisplayTimeHidden = new javax.swing.JRadioButton();
        m_panelDefaultDisplayTimeCustom = new javax.swing.JPanel();
        m_buttonDefaultDisplayTimeCustom = new javax.swing.JRadioButton();
        m_panelTabSound = new javax.swing.JPanel();
        m_panelPlaySounds = new javax.swing.JPanel();
        m_buttonPlaySoundsCapture = new javax.swing.JCheckBox();
        m_buttonPlaySoundsDisplay = new javax.swing.JCheckBox();
        m_buttonPlaySoundsMove = new javax.swing.JCheckBox();
        m_buttonPlaySoundsSliderMove = new javax.swing.JCheckBox();

        setTitle("Tour Creator Options");
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        m_panelMainPanel.setLayout(new java.awt.BorderLayout());

        m_panelMainPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(10, 10, 10, 10)));
        m_panelButtonPanel.setOpaque(false);
        m_buttonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_buttonOKActionPerformed(evt);
            }
        });

        m_panelButtonPanel.add(m_buttonOK);

        m_bottonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_buttonCancelActionPerformed(evt);
            }
        });

        m_panelButtonPanel.add(m_bottonCancel);

        m_panelMainPanel.add(m_panelButtonPanel, java.awt.BorderLayout.SOUTH);

        m_panelTabGeneral.setLayout(new java.awt.BorderLayout());

        m_panelInnerTabGeneral.setLayout(new java.awt.BorderLayout());

        m_panelShowTourCreatorOnPerformCaptureAction.setLayout(new java.awt.GridLayout(1, 2, 5, 0));

        m_panelShowTourCreatorOnPerformCaptureAction.setBorder(new javax.swing.border.TitledBorder(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelTitle.autoShowOnCapture")));
        m_buttonShowTourCreatorOnPerformCaptureAction.setSelected(true);
        m_buttonShowTourCreatorOnPerformCaptureAction.setText(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelAutoShowOnCapture.buttonEnableOption"));
        m_panelShowTourCreatorOnPerformCaptureAction.add(m_buttonShowTourCreatorOnPerformCaptureAction);

        m_panelInnerTabGeneral.add(m_panelShowTourCreatorOnPerformCaptureAction, java.awt.BorderLayout.NORTH);

        m_panelAskForSaveOptions.setLayout(new java.awt.GridLayout(3, 2, 5, 0));

        m_panelAskForSaveOptions.setBorder(new javax.swing.border.TitledBorder(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelTitle.askForSave")));
        m_buttonAskForSaveOnNew.setText(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelAskForSave.buttonOnNewTourOption"));
        m_panelAskForSaveOptions.add(m_buttonAskForSaveOnNew);

        m_buttonAskForSaveOnOpen.setText(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelAskForSave.buttonOnOpenTourOption"));
        m_panelAskForSaveOptions.add(m_buttonAskForSaveOnOpen);

        m_buttonAskForSaveOnExit.setText(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelAskForSave.buttonOnExitOption"));
        m_panelAskForSaveOptions.add(m_buttonAskForSaveOnExit);

        m_panelInnerTabGeneral.add(m_panelAskForSaveOptions, java.awt.BorderLayout.CENTER);

        m_panelTabGeneral.add(m_panelInnerTabGeneral, java.awt.BorderLayout.NORTH);

        m_tabbedPaneMain.addTab("General", m_panelTabGeneral);

        m_panelTabTourElements.setLayout(new java.awt.BorderLayout());

        m_panelInnerTabTourElements.setLayout(new java.awt.BorderLayout());

        m_panelAskBeforeDeleteOptions.setLayout(new java.awt.GridLayout(1, 2, 5, 0));

        m_panelAskBeforeDeleteOptions.setBorder(new javax.swing.border.TitledBorder(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelTitle.onDelete")));
        m_buttonAskBeforeDelete.setSelected(true);
        m_buttonAskBeforeDelete.setText(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelAskBeforeDelete.buttonEnableOption"));
        m_panelAskBeforeDeleteOptions.add(m_buttonAskBeforeDelete);

        m_panelInnerTabTourElements.add(m_panelAskBeforeDeleteOptions, java.awt.BorderLayout.NORTH);

        m_panelDoubleClickOptions.setLayout(new java.awt.GridLayout(2, 1, 5, 0));

        m_panelDoubleClickOptions.setBorder(new javax.swing.border.TitledBorder(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelTitle.onDoubleClick")));
        m_buttonDoubleClickActionSHOW_ELEMENT.setSelected(true);
        m_buttonDoubleClickActionSHOW_ELEMENT.setText(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelOnDoubleClick.buttonDisplayElementOption"));
        m_buttonGroupDoubleClick.add(m_buttonDoubleClickActionSHOW_ELEMENT);
        m_panelDoubleClickOptions.add(m_buttonDoubleClickActionSHOW_ELEMENT);

        m_buttonDoubleClickActionELEMENT_PROPERTIES.setText(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelOnDoubleClick.buttonDisplayPropertiesOption"));
        m_buttonGroupDoubleClick.add(m_buttonDoubleClickActionELEMENT_PROPERTIES);
        m_panelDoubleClickOptions.add(m_buttonDoubleClickActionELEMENT_PROPERTIES);

        m_panelInnerTabTourElements.add(m_panelDoubleClickOptions, java.awt.BorderLayout.CENTER);

        m_panelDefaultDisplayTimeOptions.setLayout(new java.awt.GridLayout(3, 1, 5, 0));

        m_panelDefaultDisplayTimeOptions.setBorder(new javax.swing.border.TitledBorder(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelTitle.displayTime")));
        m_buttonDefaultDisplayTimeInfinite.setText(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelDisplayTime.buttonInfiniteOption"));
        m_buttonGroupDefaultDisplayTime.add(m_buttonDefaultDisplayTimeInfinite);
        m_buttonDefaultDisplayTimeInfinite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_buttonDefaultDisplayTimeInfiniteActionPerformed(evt);
            }
        });

        m_panelDefaultDisplayTimeOptions.add(m_buttonDefaultDisplayTimeInfinite);

        m_buttonDefaultDisplayTimeHidden.setText(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelDisplayTime.buttonHiddenOption"));
        m_buttonGroupDefaultDisplayTime.add(m_buttonDefaultDisplayTimeHidden);
        m_panelDefaultDisplayTimeOptions.add(m_buttonDefaultDisplayTimeHidden);

        m_panelDefaultDisplayTimeCustom.setLayout(new java.awt.GridLayout(1, 2));

        m_buttonDefaultDisplayTimeCustom.setText(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelDisplayTime.buttonCustomOption"));
        m_buttonGroupDefaultDisplayTime.add(m_buttonDefaultDisplayTimeCustom);
        m_buttonDefaultDisplayTimeCustom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_buttonDefaultDisplayTimeCustomActionPerformed(evt);
            }
        });

        m_panelDefaultDisplayTimeCustom.add(m_buttonDefaultDisplayTimeCustom);

        m_panelDefaultDisplayTimeOptions.add(m_panelDefaultDisplayTimeCustom);

        m_panelInnerTabTourElements.add(m_panelDefaultDisplayTimeOptions, java.awt.BorderLayout.SOUTH);

        m_panelTabTourElements.add(m_panelInnerTabTourElements, java.awt.BorderLayout.NORTH);

        m_tabbedPaneMain.addTab("Tour Elements", m_panelTabTourElements);

        m_panelTabSound.setLayout(new java.awt.BorderLayout());

        m_panelPlaySounds.setLayout(new java.awt.GridLayout(4, 1, 5, 0));

        m_panelPlaySounds.setBorder(new javax.swing.border.TitledBorder(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelTitle.playSounds")));
        m_buttonPlaySoundsCapture.setText(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelPlaySounds.buttonOnCaptureOption"));
        m_panelPlaySounds.add(m_buttonPlaySoundsCapture);

        m_buttonPlaySoundsDisplay.setText(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelPlaySounds.buttonOnDisplayOption"));
        m_panelPlaySounds.add(m_buttonPlaySoundsDisplay);

        m_buttonPlaySoundsMove.setText(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelPlaySounds.buttonOnMoveOption"));
        m_panelPlaySounds.add(m_buttonPlaySoundsMove);

        m_buttonPlaySoundsSliderMove.setText(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelPlaySounds.buttonOnSliderMoveOption"));
        m_panelPlaySounds.add(m_buttonPlaySoundsSliderMove);

        m_panelTabSound.add(m_panelPlaySounds, java.awt.BorderLayout.NORTH);

        m_tabbedPaneMain.addTab("Sound", m_panelTabSound);

        m_panelMainPanel.add(m_tabbedPaneMain, java.awt.BorderLayout.CENTER);

        getContentPane().add(m_panelMainPanel, java.awt.BorderLayout.CENTER);

        pack();
    }//GEN-END:initComponents
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        System.out.println("KeyPressed");
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER)
            this.m_buttonOKActionPerformed(null);
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE)
            this.m_buttonCancelActionPerformed(null);
    }    private void m_buttonDefaultDisplayTimeCustomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-LAST:event_formKeyPressed
        // enable Spinner//GEN-FIRST:event_m_buttonDefaultDisplayTimeCustomActionPerformed
        this.m_spinnerDefaultDisplayTime.setEnabled(true);
    }//GEN-LAST:event_m_buttonDefaultDisplayTimeCustomActionPerformed
    private void m_buttonDefaultDisplayTimeInfiniteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_buttonDefaultDisplayTimeInfiniteActionPerformed
        // disable Spinner
        this.m_spinnerDefaultDisplayTime.setEnabled(false);
    }//GEN-LAST:event_m_buttonDefaultDisplayTimeInfiniteActionPerformed
    private void m_buttonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_buttonOKActionPerformed
        this.setAndHide();
    }//GEN-LAST:event_m_buttonOKActionPerformed
    private void m_buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_buttonCancelActionPerformed
        // hide property frame
        this.hide();
    }//GEN-LAST:event_m_buttonCancelActionPerformed
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
       this.hide();
    }//GEN-LAST:event_exitForm
    
    public void refreshAndShow()
    {
        //refresh
        FLGStandardTourCreatorOptions l_tempOptions = this.m_owningTourCreator.getCurrentOptions();
        
        //refresh DoubleClick options
        if(l_tempOptions.getDoubleClickOnElementAction() == FLGStandardTourCreator.ACTION_SHOW_ELEMENT)
        {
            this.m_buttonDoubleClickActionSHOW_ELEMENT.setSelected(true);
            //this.m_buttonDoubleClickActionELEMENT_PROPERTIES.setSelected(false);
        }
        else if(l_tempOptions.getDoubleClickOnElementAction() == FLGStandardTourCreator.ACTION_ELEMENT_PROPERTIES)
        {   
            //this.m_buttonDoubleClickActionSHOW_ELEMENT.setSelected(true);
            this.m_buttonDoubleClickActionELEMENT_PROPERTIES.setSelected(true);
        }
        
        //refresh RememberToSave options
        this.m_buttonAskForSaveOnExit.setSelected(l_tempOptions.getRememberToSaveOnExitOption());
        this.m_buttonAskForSaveOnNew.setSelected(l_tempOptions.getRememberToSaveOnNewOption());
        this.m_buttonAskForSaveOnOpen.setSelected(l_tempOptions.getRememberToSaveOnOpenOption());
        
        //refresh PlaySounds options
        this.m_buttonPlaySoundsCapture.setSelected(l_tempOptions.mb_playSoundCapture);
        this.m_buttonPlaySoundsDisplay.setSelected(l_tempOptions.mb_playSoundDisplay);
        this.m_buttonPlaySoundsMove.setSelected(l_tempOptions.mb_playSoundMove);
        this.m_buttonPlaySoundsSliderMove.setSelected(l_tempOptions.mb_playSoundSliderMove);
        
        //refresh AskBeforeDelete options
        this.m_buttonAskBeforeDelete.setSelected(l_tempOptions.getAskBeforeDeleteElements());
        
        //refresh ShowTourCreatorOnPerformCaptureAction options
        this.m_buttonShowTourCreatorOnPerformCaptureAction.setSelected(l_tempOptions.getShowWindowOnPerformCaptureAction());
        
        //refresh DefaultDisplayTime option
        if(l_tempOptions.getDefaultDisplayTime() <= 0)
        {
            if(l_tempOptions.getDefaultDisplayTime() == FLGStandardTourElement.HIDDEN_DISPLAY_TIME)
                this.m_buttonDefaultDisplayTimeHidden.setSelected(true);
            else
                this.m_buttonDefaultDisplayTimeInfinite.setSelected(true);
            //this.m_buttonDefaultDisplayTimeCustom.setSelected(false);
            this.m_spinnerDefaultDisplayTime.setEnabled(false);
        }
        else
        {
            this.m_buttonDefaultDisplayTimeCustom.setSelected(true);
            //this.m_buttonDefaultDisplayTimeInfinite.setSelected(false);
            this.m_spinnerDefaultDisplayTime.setValue(new Integer(l_tempOptions.getDefaultDisplayTime()));
            this.m_spinnerDefaultDisplayTime.setEnabled(true);
        }
        
        //show
        this.show();
    }//refreshAndShow
    
    
    private void setAndHide()
    {
        //set
        FLGStandardTourCreatorOptions l_tempOptions = this.m_owningTourCreator.getCurrentOptions();
        
        //set DoubleClick options
        if(this.m_buttonDoubleClickActionSHOW_ELEMENT.isSelected())
        {
            l_tempOptions.setDoubleClickOnElementAction(FLGStandardTourCreator.ACTION_SHOW_ELEMENT);
        }
        else if(this.m_buttonDoubleClickActionELEMENT_PROPERTIES.isSelected())
        {
            l_tempOptions.setDoubleClickOnElementAction(FLGStandardTourCreator.ACTION_ELEMENT_PROPERTIES);
        }
        
        //set RememberToSaveOptions
        l_tempOptions.setRememberToSaveOnExitOption(this.m_buttonAskForSaveOnExit.isSelected());
        l_tempOptions.setRememberToSaveOnNewOption(this.m_buttonAskForSaveOnNew.isSelected());
        l_tempOptions.setRememberToSaveOnOpenOption(this.m_buttonAskForSaveOnOpen.isSelected());
        
        //refresh PlaySounds options
        l_tempOptions.mb_playSoundCapture = this.m_buttonPlaySoundsCapture.isSelected();
        l_tempOptions.mb_playSoundDisplay = this.m_buttonPlaySoundsDisplay.isSelected();
        l_tempOptions.mb_playSoundMove = this.m_buttonPlaySoundsMove.isSelected();
        l_tempOptions.mb_playSoundSliderMove = this.m_buttonPlaySoundsSliderMove.isSelected();
        
        //set AskBeforeDelete option
        l_tempOptions.setAskBeforeDeleteElements(this.m_buttonAskBeforeDelete.isSelected());
        
        //set ShowTourCreatorOnPerformCaptureAction
        l_tempOptions.setShowWindowOnPerformCaptureAction(this.m_buttonShowTourCreatorOnPerformCaptureAction.isSelected());
        
        //set DefaultDisplayTime option
        if(this.m_buttonDefaultDisplayTimeInfinite.isSelected())
        {
            l_tempOptions.setDefaultDisplayTime(FLGTourElement.UNLIMITED_DISPLAY_TIME);
        }
        else if(this.m_buttonDefaultDisplayTimeHidden.isSelected())
        {
            l_tempOptions.setDefaultDisplayTime(FLGTourElement.HIDDEN_DISPLAY_TIME);
        }
        else //custom
        {
            l_tempOptions.setDefaultDisplayTime(((Number)this.m_spinnerDefaultDisplayTime.getValue()).intValue());
        }//else
        
        //hide
        this.hide();
    }//setAndHide
      
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox m_buttonAskBeforeDelete;
    private javax.swing.JPanel m_panelShowTourCreatorOnPerformCaptureAction;
    private javax.swing.JTabbedPane m_tabbedPaneMain;
    private javax.swing.JPanel m_panelDefaultDisplayTimeOptions;
    private javax.swing.JCheckBox m_buttonAskForSaveOnOpen;
    private javax.swing.JPanel m_panelDoubleClickOptions;
    private javax.swing.JPanel m_panelMainPanel;
    private javax.swing.JPanel m_panelInnerTabGeneral;
    private javax.swing.JCheckBox m_buttonAskForSaveOnNew;
    private javax.swing.JCheckBox m_buttonPlaySoundsSliderMove;
    private javax.swing.JCheckBox m_buttonPlaySoundsMove;
    private javax.swing.JCheckBox m_buttonShowTourCreatorOnPerformCaptureAction;
    private javax.swing.JCheckBox m_buttonPlaySoundsCapture;
    private javax.swing.JPanel m_panelPlaySounds;
    private javax.swing.ButtonGroup m_buttonGroupDefaultDisplayTime;
    private javax.swing.JCheckBox m_buttonPlaySoundsDisplay;
    private javax.swing.JPanel m_panelTabGeneral;
    private javax.swing.JRadioButton m_buttonDefaultDisplayTimeCustom;
    private javax.swing.JButton m_buttonOK;
    private javax.swing.JPanel m_panelButtonPanel;
    private javax.swing.JRadioButton m_buttonDoubleClickActionELEMENT_PROPERTIES;
    private javax.swing.JPanel m_panelAskForSaveOptions;
    private javax.swing.JRadioButton m_buttonDefaultDisplayTimeHidden;
    private javax.swing.ButtonGroup m_buttonGroupDoubleClick;
    private javax.swing.JButton m_bottonCancel;
    private javax.swing.JPanel m_panelTabTourElements;
    private javax.swing.JPanel m_panelDefaultDisplayTimeCustom;
    private javax.swing.JCheckBox m_buttonAskForSaveOnExit;
    private javax.swing.JPanel m_panelAskBeforeDeleteOptions;
    private javax.swing.JPanel m_panelTabSound;
    private javax.swing.JRadioButton m_buttonDefaultDisplayTimeInfinite;
    private javax.swing.JRadioButton m_buttonDoubleClickActionSHOW_ELEMENT;
    private javax.swing.JPanel m_panelInnerTabTourElements;
    // End of variables declaration//GEN-END:variables
    
}
