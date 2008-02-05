package freestyleLearningGroup.independent.tourCreator;

import freestyleLearningGroup.independent.util.*;
import freestyleLearning.learningUnitViewAPI.elementInteractionPanel.*;
import freestyleLearningGroup.independent.gui.FLGImageUtility;
import freestyleLearningGroup.independent.gui.FLGOptionPane;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

//import freestyleLearningGroup.independent.gui.FLGOptionPane;
import java.applet.*;

/**
 * Reference implementation of a FLGTourCreator.
 * @author Steffen Wachenfeld
 */
public class FLGStandardTourCreator extends javax.swing.JFrame implements FLGTourCreator {    
    //singleton
    private static FLGStandardTourCreator m_tourCreatorSingleton;
    
    public static final int ACTION_SHOW_ELEMENT = 1;
    public static final int ACTION_ELEMENT_PROPERTIES = 2;
    
    protected FLG2TourCreatorInteractor m_tourInteractor = null;
    private   String                    ms_windowTitle   = "FLG TourCreator 1.0";
    protected FLGStandardTour           m_currentTour    = new FLGStandardTour();
    
    protected boolean mb_tourModified = false;
    protected boolean mb_tourWasNeverSaved = true;
    protected boolean mb_isInPresentationMode = false;
    private static FLGInternationalization m_internationalization;
    
    public FLGStandardTourCreatorOptions          m_currentTourCreatorOptions = new FLGStandardTourCreatorOptions();
    
    private FLGStandardTourCreatorOptionsFrame        m_tourCreatorOptionsFrame;
    private FLGStandardTourElementPropertiesDialog    m_elementPropertiesDialog;
    private FLGStandardTourCreatorPresentationControl m_presentationControl;
    private static FLGStandardTourListCellRenderer    m_cellRenderer = new FLGStandardTourListCellRenderer();
    private FLGAboutFrame                             m_aboutFrame;
    
    /*****
     * GUI
     ****/
    private javax.swing.AbstractButton m_buttonCapture;
    private javax.swing.AbstractButton m_buttonToPresentationMode;
    private javax.swing.AbstractButton m_buttonMoveDown;
    private javax.swing.AbstractButton m_buttonMoveUp;
    private javax.swing.AbstractButton m_buttonShowMediaPlayerDialog;
    
    /*********
     * Sound
     ********/
    //public java.applet.AudioClip m_soundCapture;
    //public java.applet.AudioClip m_soundSliderMove;
    //public java.applet.AudioClip m_soundDisplay;
    //public java.applet.AudioClip m_soundMove;
    private boolean mb_enableSounds = true;
    
    private static FLGStandardTourMediaPlayerDialog m_mediaPlayerDialog;
    
    // added by MW:
    protected javax.swing.JComboBox cb_tours;
    protected java.util.List m_toursVector = new ArrayList();
    
    protected void setCurrentTour(FLGStandardTour tour) {
        this.m_currentTour = tour;
        this.registerAtCurrentTour();
        this.jList1.setModel(this.m_currentTour);
    }
    
    protected void loadTours() {
    }
    
    protected void saveTours() {
    }
    
    protected void performTourSelectedAction(java.awt.event.ActionEvent e) {
    }
    // end MW
    
    /** Creates new form FLGStandardTourCreator */
    protected FLGStandardTourCreator() {
        //System.out.println("Entering StandardTourCreator constructor");
        m_internationalization = new FLGInternationalization("freestyleLearningGroup.independent.tourCreator.internationalization", getClass().getClassLoader());
        this.initComponents();
        this.updateJFrameTitle();
        
        //required for subclasses!!!
        m_tourCreatorSingleton = this;
        
        /**
         * init sounds
         */
        try{
            //m_soundCapture = java.applet.Applet.newAudioClip(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/sounds/capture.wav"));
            //m_soundSliderMove = java.applet.Applet.newAudioClip(getClass().getClassLoader().getResource(this.m_currentTourCreatorOptions.ms_soundSliderMove));
            //m_soundDisplay = java.applet.Applet.newAudioClip(getClass().getClassLoader().getResource(this.m_currentTourCreatorOptions.ms_soundDisplay));
            //m_soundMove = java.applet.Applet.newAudioClip(getClass().getClassLoader().getResource(this.m_currentTourCreatorOptions.ms_soundMove));
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("Auto disabling sounds... continuing");
            mb_enableSounds = false;
        }
        
        /**
         * Init elementPropertuesDialog
         */
        this.m_elementPropertiesDialog = new FLGStandardTourElementPropertiesDialog(this, true);
        
        /**
         * Init presentation control
         */
        this.m_presentationControl = new FLGStandardTourCreatorPresentationControl(this, this.m_currentTour);
        
        /**
         * Registers the tour creator and the presentation control as data listener at the current tour
         */
        this.registerAtCurrentTour();
        
        
        /**
         * IconImage for TourCreator frame
         */
        this.setIconImage(new javax.swing.ImageIcon(FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/tourCreatorIcon.gif"))).getImage());
        
        
        /**
         * buttons - disabled to prevent capturing this frame
         *
         * //capture button
         * java.awt.Image l_captureButtonImage = FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/buttonCapture.gif"));
         * m_buttonCapture = new FSLLearningUnitViewElementInteractionButton(l_captureButtonImage);
         * //m_buttonCApture = new javax.swing.JButton();
         * //m_buttonCapture.setText(m_internationalization.getString("button.capture.text"));
         * m_buttonCapture.setToolTipText(m_internationalization.getString("button.capture.toolTipText"));
         * m_buttonCapture.addActionListener(new java.awt.event.ActionListener() {
         * public void actionPerformed(java.awt.event.ActionEvent l_evt) {
         * m_buttonCaptureActionPerformed(l_evt);
         * }
         * });
         * m_panelButtonPanel.add(m_buttonCapture);
         * //end capture button
         */
        
        //moveUp button
        java.awt.Image l_upButtonImage = FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/up.gif"));
        m_buttonMoveUp = new FSLLearningUnitViewElementInteractionButton(l_upButtonImage);
        m_buttonMoveUp.setToolTipText(m_internationalization.getString("button.moveUp.toolTipText"));
        m_buttonMoveUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent l_evt) {
                m_editMenuItemMoveUpActionPerformed(l_evt);
            }
        });
        m_panelButtonPanel.add(m_buttonMoveUp);
        
        //moveDown button
        java.awt.Image l_downButtonImage = FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/down.gif"));
        m_buttonMoveDown = new FSLLearningUnitViewElementInteractionButton(l_downButtonImage);
        m_buttonMoveDown.setToolTipText(m_internationalization.getString("button.moveDown.toolTipText"));
        m_buttonMoveDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent l_evt) {
                m_editMenuItemMoveDownActionPerformed(l_evt);
            }
        });
        m_panelButtonPanel.add(m_buttonMoveDown);
        
        //toPresentaitonMode button
        java.awt.Image l_toPresentationModeButtonImage = FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/toPresentationMode.gif"));
        m_buttonToPresentationMode = new FSLLearningUnitViewElementInteractionButton(l_toPresentationModeButtonImage);
        m_buttonToPresentationMode.setToolTipText(m_internationalization.getString("button.toPresentationMode.toolTipText"));
        m_buttonToPresentationMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent l_evt) {
                m_buttonToPresentationModeActionPerformed(l_evt);
            }
        });
        m_panelButtonPanel.add(m_buttonToPresentationMode);
        
        //button: hide/show MediaPlayerDialog
        java.awt.Image l_showMediaPlayerDialogButtonImage = FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/buttonShowMediaPlayerDialog.gif"));
        m_buttonShowMediaPlayerDialog = new FSLLearningUnitViewElementInteractionButton(l_showMediaPlayerDialogButtonImage);
        this.m_buttonShowMediaPlayerDialog.setToolTipText(FLGStandardTourCreator.getInternationalization().getString("button.showMediaPlayerDialog.toolTipText"));
        m_buttonShowMediaPlayerDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(FLGStandardTourCreator.getMediaPlayerDialog().isVisible())
                    FLGStandardTourCreator.getMediaPlayerDialog().hide();
                else
                    FLGStandardTourCreator.getMediaPlayerDialog().show();
            }
        });
        //        this.m_panelButtonPanel.add(m_buttonShowMediaPlayerDialog);
        
        
        cb_tours = new JComboBox();
        cb_tours.setRenderer(new TourSelectionComboBoxListCellRenderer());
        cb_tours.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performTourSelectionAction(e);
                checkMenuItemStatusEnabled();
            }
        });
        // may be placed somewhere different...
        m_panelTop.add(cb_tours, BorderLayout.NORTH);
        
        java.awt.Dimension screenDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        //Default panel width + space for list scrollbar and right and left border
        this.setSize(FLGStandardTourListCellRenderer.DEFAULT_PANEL_DIMENSION.width + 50, screenDimension.height * 3 / 4);
        setLocation((int)(screenDimension.width - getWidth()), 0);
    }//FLGStandardTourCreator
    
    private void initComponents() {
        m_listPopupMenu = new javax.swing.JPopupMenu();
        m_listPopupMenuItemMoveUp = new javax.swing.JMenuItem();
        m_listPopupMenuItemMoveDown = new javax.swing.JMenuItem();
        m_listPopupMenuItemMoveTo = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        m_listPopupMenuItemDisplay = new javax.swing.JMenuItem();
        m_listPopupMenuItemDelete = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        m_listPopupMenuItemProperties = new javax.swing.JMenuItem();
        m_panelElementList = new freestyleLearningGroup.independent.gui.FLGEffectPanel("FSLMainFrameColor4", true);
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        m_panelTop = new javax.swing.JPanel();
        m_panelButtonPanel = new freestyleLearningGroup.independent.gui.FLGEffectPanel("FSLMainFrameColor1", "FSLMainFrameColor4", true);
        m_tourCreatorMenuBar = new javax.swing.JMenuBar();
        m_tourMenu = new javax.swing.JMenu();
        m_tourMenuItemNew = new javax.swing.JMenuItem();
        m_tourMenuSeparator1 = new javax.swing.JSeparator();
        m_tourMenuItemOpen = new javax.swing.JMenuItem();
        m_tourMenuItemSave = new javax.swing.JMenuItem();
        m_tourMenuItemDelete = new javax.swing.JMenuItem();
        m_tourMenuItemSaveAs = new javax.swing.JMenuItem();
        m_tourMenuSeparator2 = new javax.swing.JSeparator();
        m_tourMenuItemExit = new javax.swing.JMenuItem();
        m_editMenu = new javax.swing.JMenu();
        m_editMenuItemMoveUp = new javax.swing.JMenuItem();
        m_editMenuItemMoveDown = new javax.swing.JMenuItem();
        m_editMenuItemMoveTo = new javax.swing.JMenuItem();
        m_editMenuSeparator1 = new javax.swing.JSeparator();
        m_editMenuItemDisplay = new javax.swing.JMenuItem();
        m_editMenuItemDelete = new javax.swing.JMenuItem();
        m_editMenuSeparator2 = new javax.swing.JSeparator();
        m_editMenuItemProperties = new javax.swing.JMenuItem();
        m_optionsMenu = new javax.swing.JMenu();
        m_optionsMenuItemEditOptions = new javax.swing.JMenuItem();
        m_optionsMenuItemInfo = new javax.swing.JMenuItem();
        
        m_listPopupMenuItemMoveUp.setText(m_internationalization.getString("menu.edit.moveUp"));
        m_listPopupMenuItemMoveUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_editMenuItemMoveUpActionPerformed(evt);
            }
        });
        
        m_listPopupMenu.add(m_listPopupMenuItemMoveUp);
        m_listPopupMenuItemMoveDown.setText(m_internationalization.getString("menu.edit.moveDown"));
        m_listPopupMenuItemMoveDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_editMenuItemMoveDownActionPerformed(evt);
            }
        });
        
        m_listPopupMenu.add(m_listPopupMenuItemMoveDown);
        m_listPopupMenuItemMoveTo.setText(m_internationalization.getString("menu.edit.moveTo") + "...");
        m_listPopupMenuItemMoveTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_editMenuItemMoveToActionPerformed(evt);
            }
        });
        
        m_listPopupMenu.add(m_listPopupMenuItemMoveTo);
        m_listPopupMenu.add(jSeparator1);
        m_listPopupMenuItemDisplay.setText(m_internationalization.getString("menu.edit.display"));
        m_listPopupMenuItemDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_editMenuItemDisplayActionPerformed(evt);
            }
        });
        
        m_listPopupMenu.add(m_listPopupMenuItemDisplay);
        m_listPopupMenuItemDelete.setText(m_internationalization.getString("menu.edit.delete"));
        m_listPopupMenuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_editMenuItemDeleteActionPerformed(evt);
            }
        });
        
        m_listPopupMenu.add(m_listPopupMenuItemDelete);
        m_listPopupMenu.add(jSeparator2);
        m_listPopupMenuItemProperties.setText(m_internationalization.getString("menu.edit.properties") + "...");
        m_listPopupMenuItemProperties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_editMenuItemPropertiesActionPerformed(evt);
            }
        });
        
        m_listPopupMenu.add(m_listPopupMenuItemProperties);
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                setVisible(false);
            }
        });
        
        m_panelElementList.setLayout(new java.awt.BorderLayout());
        
        m_panelElementList.setOpaque(false);
        jScrollPane1.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(10, 10, 10, 10)));
        jScrollPane1.setOpaque(false);
        jList1.setModel(this.m_currentTour);
        jList1.setCellRenderer(m_cellRenderer);
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
                checkMenuItemStatusEnabled();            
            }
        });
        
        jScrollPane1.setViewportView(jList1);
        
        m_panelElementList.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        
        getContentPane().add(m_panelElementList, java.awt.BorderLayout.CENTER);
        
        m_panelTop.setLayout(new java.awt.BorderLayout());
        
        m_panelButtonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        
        m_panelTop.add(m_panelButtonPanel, java.awt.BorderLayout.CENTER);
        
        getContentPane().add(m_panelTop, java.awt.BorderLayout.NORTH);
        
        m_tourCreatorMenuBar.setBorderPainted(false);
        m_tourMenu.setMnemonic(java.awt.event.KeyEvent.VK_T);
        m_tourMenu.setText(m_internationalization.getString("menu.tour"));
        m_tourMenuItemNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        m_tourMenuItemNew.setText(m_internationalization.getString("menu.tour.new") + "...");
        m_tourMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_tourMenuItemNewActionPerformed(evt);
            }
        });
        
        m_tourMenu.add(m_tourMenuItemNew);
        //        m_tourMenu.add(m_tourMenuSeparator1);
        m_tourMenuItemOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        m_tourMenuItemOpen.setText(m_internationalization.getString("menu.tour.open") + "...");
        m_tourMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_tourMenuItemOpenActionPerformed(evt);
            }
        });
        
        //        m_tourMenu.add(m_tourMenuItemOpen);
        m_tourMenuItemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        m_tourMenuItemSave.setText(m_internationalization.getString("menu.tour.save") + "...");
        m_tourMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_tourMenuItemSaveActionPerformed(evt);
            }
        });
        
        m_tourMenu.add(m_tourMenuItemSave);
        m_tourMenuItemSaveAs.setMnemonic(java.awt.event.KeyEvent.VK_A);
        m_tourMenuItemSaveAs.setText(m_internationalization.getString("menu.tour.saveAs") + "...");
        m_tourMenuItemSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_tourMenuItemSaveAsActionPerformed(evt);
            }
        });
        
        m_tourMenu.add(m_tourMenuItemDelete);
        m_tourMenuItemDelete.setMnemonic(java.awt.event.KeyEvent.VK_L);
        m_tourMenuItemDelete.setText(m_internationalization.getString("menu.tour.delete") + "...");
        m_tourMenuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_tourMenuItemDeleteActionPerformed(evt);
            }
        });
        
        //        m_tourMenu.add(m_tourMenuItemSaveAs);
        m_tourMenu.add(m_tourMenuSeparator2);
        m_tourMenuItemExit.setMnemonic(java.awt.event.KeyEvent.VK_X);
        m_tourMenuItemExit.setText(m_internationalization.getString("menu.tour.exit"));
        m_tourMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_tourMenuItemExitActionPerformed(evt);
            }
        });
        
        m_tourMenu.add(m_tourMenuItemExit);
        m_tourCreatorMenuBar.add(m_tourMenu);
        m_editMenu.setMnemonic(java.awt.event.KeyEvent.VK_E);
        m_editMenu.setText(m_internationalization.getString("menu.edit"));
        m_editMenuItemMoveUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_UP, java.awt.event.InputEvent.CTRL_MASK));
        m_editMenuItemMoveUp.setText(m_internationalization.getString("menu.edit.moveUp"));
        m_editMenuItemMoveUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_editMenuItemMoveUpActionPerformed(evt);
            }
        });
        
        m_editMenu.add(m_editMenuItemMoveUp);
        m_editMenuItemMoveDown.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DOWN, java.awt.event.InputEvent.CTRL_MASK));
        m_editMenuItemMoveDown.setText(m_internationalization.getString("menu.edit.moveDown"));
        m_editMenuItemMoveDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_editMenuItemMoveDownActionPerformed(evt);
            }
        });
        
        m_editMenu.add(m_editMenuItemMoveDown);
        m_editMenuItemMoveTo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_RIGHT, java.awt.event.InputEvent.CTRL_MASK));
        m_editMenuItemMoveTo.setText(m_internationalization.getString("menu.edit.moveTo") + "...");
        m_editMenuItemMoveTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_editMenuItemMoveToActionPerformed(evt);
            }
        });
        
        m_editMenu.add(m_editMenuItemMoveTo);
        m_editMenu.add(m_editMenuSeparator1);
        m_editMenuItemDisplay.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_LEFT, java.awt.event.InputEvent.CTRL_MASK));
        m_editMenuItemDisplay.setText(m_internationalization.getString("menu.edit.display"));
        m_editMenuItemDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_editMenuItemDisplayActionPerformed(evt);
            }
        });
        
        m_editMenu.add(m_editMenuItemDisplay);
        m_editMenuItemDelete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        m_editMenuItemDelete.setText(m_internationalization.getString("menu.edit.delete"));
        m_editMenuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_editMenuItemDeleteActionPerformed(evt);
            }
        });
        
        m_editMenu.add(m_editMenuItemDelete);
        m_editMenu.add(m_editMenuSeparator2);
        m_editMenuItemProperties.setText(m_internationalization.getString("menu.edit.properties") + "...");
        m_editMenuItemProperties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_editMenuItemPropertiesActionPerformed(evt);
            }
        });
        
        m_editMenu.add(m_editMenuItemProperties);
        m_tourCreatorMenuBar.add(m_editMenu);
        m_optionsMenu.setMnemonic(java.awt.event.KeyEvent.VK_O);
        m_optionsMenu.setText(m_internationalization.getString("menu.options"));
        m_optionsMenuItemEditOptions.setText(m_internationalization.getString("menu.options.editOptions") + "...");
        m_optionsMenuItemEditOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_optionsMenuItemEditOptionsActionPerformed(evt);
            }
        });
        
        // temporary disabled
        //        m_optionsMenu.add(m_optionsMenuItemEditOptions);
        m_optionsMenuItemInfo.setText(m_internationalization.getString("menu.options.info") + "...");
        m_optionsMenuItemInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_optionsMenuItemInfoActionPerformed(evt);
            }
        });
        
        // m_optionsMenu.add(m_optionsMenuItemInfo);
        // m_tourCreatorMenuBar.add(m_optionsMenu);
        setJMenuBar(m_tourCreatorMenuBar);
        checkMenuItemStatusEnabled();
        pack();
    }
    
    protected void checkMenuItemStatusEnabled() {
        m_editMenuItemMoveUp.setEnabled(jList1.getSelectedValue() != null);
        m_editMenuItemMoveDown.setEnabled(jList1.getSelectedValue() != null);
        m_editMenuItemMoveTo.setEnabled(jList1.getSelectedValue() != null);
        m_editMenuItemDisplay.setEnabled(jList1.getSelectedValue() != null);
        m_editMenuItemDelete.setEnabled(jList1.getSelectedValue() != null);
        m_editMenuItemProperties.setEnabled(jList1.getSelectedValue() != null);
    }
    
    /**
     * Perform action for menu item "New" selected.
     * May be overwritten by subclasses.
     * @param <code>evt</code> ActionEvent from menu item selection
     */
    public void m_tourMenuItemNewActionPerformed(java.awt.event.ActionEvent evt) {
        //ask for save if rememberToSaveOnNewOption is set and tour is modified
        if(this.m_currentTourCreatorOptions.getRememberToSaveOnNewOption() && this.mb_tourModified) {
            //ask whether to abort (save will be done by called method)
            if(this.showRememberToSaveDialog() == JOptionPane.CANCEL_OPTION)
                return;
        }//if
        this.unregisterAtCurrentTour();
        
        //creating the new tour
        this.m_currentTour = new FLGStandardTour();
        this.registerAtCurrentTour();
        this.jList1.setModel(this.m_currentTour);
        this.mb_tourWasNeverSaved = true;
        this.mb_tourModified = true;
        this.updateJFrameTitle();
    }
    
    /**
     * Perform action for menu item "SaveAs" selected.
     * May be overwritten by subclasses.
     * @param <code>evt</code> ActionEvent from menu item selection
     */
    public void m_tourMenuItemSaveAsActionPerformed(java.awt.event.ActionEvent evt) {
        this.askForTourName();
        this.saveCurrentTourAs(this.m_currentTour.getTourName());
    }
    
    /**
     * Perform action for menu item "Save" selected.
     * May be overwritten by subclasses.
     * @param <code>evt</code> ActionEvent from menu item selection
     */
    public void m_tourMenuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {
        this.saveCurrentTour();
    }
    
   /**
     * Perform action for menu item "Delete" selected.
     * May be overwritten by subclasses.
     * @param <code>evt</code> ActionEvent from menu item selection
     */
    public void m_tourMenuItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        
    }
    
    
    private void m_optionsMenuItemInfoActionPerformed(java.awt.event.ActionEvent evt) {
        if(this.m_aboutFrame == null)
            this.m_aboutFrame = new FLGAboutFrame();
        this.m_aboutFrame.refreshAndShow();
    }
    
    private void m_buttonToPresentationModeActionPerformed(java.awt.event.ActionEvent evt) {
        this.hide();
        this.mb_isInPresentationMode = true;
        
        //better not:
        //enable Time Flow
        //this.m_presentationControl.enableTimeFlow();
        //set slider to zero
        
        this.m_cellRenderer.setZeroPanelImage(m_currentTour.getTourIcon().getImage());
        this.m_presentationControl.refreshAndShow(this.m_currentTour);
    }
    
    public void openPresentationMode() {
    	 this.hide();
         this.mb_isInPresentationMode = true;
         
         //better not:
         //enable Time Flow
         //this.m_presentationControl.enableTimeFlow();
         //set slider to zero
         
         this.m_cellRenderer.setZeroPanelImage(m_currentTour.getTourIcon().getImage());
         this.m_presentationControl.refreshAndShow(this.m_currentTour);
    }
    
    private void m_tourMenuItemOpenActionPerformed(java.awt.event.ActionEvent evt) {
        //ask for save if rememberToSaveOnOpenOption is set and tour is modified
        if(this.m_currentTourCreatorOptions.getRememberToSaveOnOpenOption() && this.mb_tourModified) {
            //ask whether to abort (save will be done by called method)
            if(this.showRememberToSaveDialog() == JOptionPane.CANCEL_OPTION)
                return;
        }//if
        this.unregisterAtCurrentTour();
        
        //open new tour
        
        //TODO
    }
    private void m_editMenuItemPropertiesActionPerformed(java.awt.event.ActionEvent evt) {
        
        this.showElementPropertiesDialog();
        
    }
    private void m_optionsMenuItemEditOptionsActionPerformed(java.awt.event.ActionEvent evt) {
        // show Option Frame
        this.showOptionsFrame();
    }
    private void m_editMenuItemDisplayActionPerformed(java.awt.event.ActionEvent evt) {
        //display first selected
        int li_selectionIndex = this.jList1.getMinSelectionIndex();
        this.displayElement(li_selectionIndex);
    }
    private void m_editMenuItemMoveDownActionPerformed(java.awt.event.ActionEvent evt) {
        //retrieve selection
        int[] li_selectedIndexes_ = this.jList1.getSelectedIndices();
        
        //check selection
        if(li_selectedIndexes_ == null || li_selectedIndexes_.length <= 0)
            return;
        
        //query for target Position
        int li_targetPosition = li_selectedIndexes_[li_selectedIndexes_.length-1]+1;
        
        //move just one element ;-)
        if(this.m_currentTour.moveElements(li_targetPosition, li_targetPosition, li_selectedIndexes_[0])) {
            //modify also selection in jList
            this.jList1.addSelectionInterval(li_selectedIndexes_[0]+1, li_targetPosition);
        }//if
    }
    private void m_editMenuItemMoveUpActionPerformed(java.awt.event.ActionEvent evt) {
        //retrieve selection
        int[] li_selectedIndexes_ = this.jList1.getSelectedIndices();
        
        //check selection
        if(li_selectedIndexes_ == null || li_selectedIndexes_.length <= 0)
            return;
        
        //query for target Position
        int li_targetPosition = li_selectedIndexes_[0]-1;
        
        //move just one element ;-)
        if(this.m_currentTour.moveElements(li_targetPosition, li_targetPosition, li_selectedIndexes_[li_selectedIndexes_.length-1])) {
            //modify selection
            this.jList1.addSelectionInterval(li_targetPosition, li_selectedIndexes_[li_selectedIndexes_.length-1]-1);
        }//if
        
    }
    private void m_editMenuItemMoveToActionPerformed(java.awt.event.ActionEvent evt) {
        //retrieve selection
        int[] li_selectedIndexes_ = this.jList1.getSelectedIndices();
        
        //check selection
        if(li_selectedIndexes_ == null || li_selectedIndexes_.length <= 0)
            return;
        
        //query for target Position
        int li_targetPosition = 0;
        String ls_targetPosition = JOptionPane.showInputDialog(this,
        m_internationalization.getString("dialog.moveTo.message") + (this.m_currentTour.getSize() - li_selectedIndexes_.length + 1) + ")",
        m_internationalization.getString("dialog.moveTo.title"),
        JOptionPane.QUESTION_MESSAGE);
        if(ls_targetPosition == null || ls_targetPosition.length() <= 0)
            return;
        
        try {
            li_targetPosition = Integer.parseInt(ls_targetPosition) - 1;
        }
        catch(NumberFormatException l_nfe) {
            l_nfe.printStackTrace();
            System.out.println("Debug Info: Cannot parse this String: " + ls_targetPosition);
        }
        
        if(this.m_currentTour.moveElements(li_selectedIndexes_[0], li_selectedIndexes_[li_selectedIndexes_.length-1], li_targetPosition)) {
            //on success also change selection
            this.jList1.setSelectionInterval(li_targetPosition, li_targetPosition + li_selectedIndexes_.length - 1);
        }//if
        
    }
    private void m_tourMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
    }
    
    public boolean removeSelectedTourElements(java.util.List elements) {
        
        return true;
    }
    
    public boolean removeSelectedTourElementsByIndex(int[] li_selectedIndexes_) {
        //check selection
        if(li_selectedIndexes_ == null || li_selectedIndexes_.length <= 0)
            return false;
        
        //ask if user is sure to delete the selected elements
        if(this.m_currentTourCreatorOptions.getAskBeforeDeleteElements()) {
            String ls_message = "";
            if(li_selectedIndexes_.length == 1) {
                ls_message = m_internationalization.getString("dialog.deleteElements.singleFirst")
                + " " + (li_selectedIndexes_[0]+1) + " "
                + m_internationalization.getString("dialog.deleteElements.singleSecond");
            }
            else {
                ls_message = m_internationalization.getString("dialog.deleteElements.multipleFirst")
                + (li_selectedIndexes_[0]+1) + " "
                + m_internationalization.getString("dialog.deleteElements.multipleSecond")
                + (li_selectedIndexes_[li_selectedIndexes_.length-1]+1)
                + " (" +  li_selectedIndexes_.length + " "
                + m_internationalization.getString("dialog.deleteElements.multipleThird");
            }
            
            FLGOptionPane l_tempDialog = new FLGOptionPane(ls_message,
                m_internationalization.getString("dialog.title.deleteElements"),
                FLGOptionPane.OK_CANCEL_OPTION,
                FLGOptionPane.QUESTION_MESSAGE);
            l_tempDialog.setSize(400,150);
            java.awt.Dimension l_screenDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            l_tempDialog.setLocation((int)(l_screenDimension.width - l_tempDialog.getWidth())/2, (int)(l_screenDimension.height - l_tempDialog.getHeight())/2);
            int li_returnValue = FLGOptionPane.showDialog(l_tempDialog);
            //show tour creator again
            this.show();
            
            if (li_returnValue != FLGOptionPane.OK_OPTION)
                return false;
        }
        
        //remove selected items (automatically firing data model changed)
        this.m_currentTour.removeElements(li_selectedIndexes_[0], li_selectedIndexes_[li_selectedIndexes_.length-1]);
        
        return true;
    }
    
    private void m_editMenuItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {
//        //check for context action
//        boolean lb_contextAction = false;
//        
//        //retrieve selection
//        int[] li_selectedIndexes_ = this.jList1.getSelectedIndices();
        
        removeSelectedTourElementsByIndex(this.jList1.getSelectedIndices());
    }
        

private void jList1MouseClicked(java.awt.event.MouseEvent evt) {
        int li_index = jList1.locationToIndex(evt.getPoint());
        
        //non-left click
        if(
            //check non-leftButtons
            evt.getButton() != evt.BUTTON1 ||
            //check ALT+leftButton
            ( evt.getButton() == evt.BUTTON1 && ((evt.getModifiersEx() & evt.ALT_DOWN_MASK) != 0) )) 
        {
            //show popup Menu
            if(jList1.isSelectedIndex(li_index)) {
                this.m_listPopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
        else {  //left click
            if (evt.getClickCount() == 2 && li_index >= 0) {                
                if(this.m_currentTourCreatorOptions.getDoubleClickOnElementAction() == FLGStandardTourCreator.ACTION_SHOW_ELEMENT) {
                    this.displayElement(li_index);
                }
                else if(this.m_currentTourCreatorOptions.getDoubleClickOnElementAction() == FLGStandardTourCreator.ACTION_ELEMENT_PROPERTIES) {
                    //show properties
                    this.m_elementPropertiesDialog.refreshAndShow(this.m_currentTour, this.jList1.getSelectedIndices());
                }
            }
        }
    }
    
    private void m_buttonCaptureActionPerformed(java.awt.event.ActionEvent evt) {
        //play capture sound
        //if(this.m_currentTourCreatorOptions.mb_playSoundCapture)
          //  this.playSound(this.m_soundCapture);
        
        //creating new tour element and temporary ElementInformation
        FLGStandardTourElement l_tempTourElement= new FLGStandardTourElement();
        FLG2TourCreatorElementInformation l_tempElementInformation = this.m_tourInteractor.getCurrentFLG2TourCreatorElementInformation();
        
        //setting link target
        l_tempTourElement.setFLG2TourCreatorElementInformation(l_tempElementInformation);
        
        //creating small image
        l_tempTourElement.deriveSmallImage();
        
        //setting display time to default
        l_tempTourElement.setDisplayTime(this.m_currentTourCreatorOptions.getDefaultDisplayTime());
        
        //adding new tour element
        this.m_currentTour.addElement(l_tempTourElement);
    }
    
    private void exitForm(java.awt.event.WindowEvent evt) {
        this.pretendToExit();
    }
    
    /**
     * Cell Renderer class for tour selection combo box.
     * Displays Icons and Texts from JLabel objects contained in combo box.
     */
    class TourSelectionComboBoxListCellRenderer extends JLabel implements ListCellRenderer {
        public TourSelectionComboBoxListCellRenderer() {
            setOpaque(true);
            setPreferredSize(FLGStandardTourListCellRenderer.DEFAULT_PANEL_DIMENSION);
            setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        }
        
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (isSelected) {
                setForeground(Color.white);
                setBackground(new Color(0,0,64));
            }
            else {
                setForeground(Color.black);
                setBackground(Color.white);
            }
            if (value instanceof JLabel) {
                setText(((JLabel)value).getText());
                setIcon(((JLabel)value).getIcon());
            }
            else {
                if (value != null) setText(value.toString());
                else setText("");
            }
            return this;
        }
    }
    
    /**
     * Performs reaction to selection from tour selection combo box
     * May be overwritten in subclasses.
     * @param <code>ActionEvent</code>ActionEvent originated from combo box selection
     */
    protected void performTourSelectionAction(ActionEvent e) {
        // will be overridden in subclasses
    }
    
    protected void updateComponents() {
        // Fill tour selection combo box
        cb_tours.removeAllItems();
        for (int i = 0; i < m_toursVector.size(); i++) {
            if (((FLGStandardTour)(m_toursVector.get(i))).getTourIcon() != null) {
                cb_tours.addItem(new JLabel(((FLGStandardTour)(m_toursVector.get(i))).getTourName(),
                ((FLGStandardTour)(m_toursVector.get(i))).getTourIcon(), JLabel.HORIZONTAL));
            }
            else {
                cb_tours.addItem(new JLabel(((FLGStandardTour)m_toursVector.get(i)).getTourName(),
                new ImageIcon(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/tourCreatorIcon.gif")), JLabel.HORIZONTAL));
            }
        }
    }
    
    /**********************************************************************************/
    /**
     * Opens a Dialog and asks for a Tour Name.
     */
    protected void askForTourName(){
        
        //some Dialog
        
    }//askForTourName
    
    /**
     * Called if one or more Element of the currentTour has changed
     * @param a_listDataEvent event object holding further information
     */
    public void contentsChanged(ListDataEvent a_listDataEvent) {
        this.mb_tourModified = true;
        this.updateJFrameTitle();
    }//contentsChanged
    
    /**
     * The specified element will be displayed if existent. Will automatially set the media to the assicoiated file.
     * Illegal element numbers will result in unset media file.
     */
    public void displayElement(int ai_elementNumber) {
        if(ai_elementNumber >= 0 && ai_elementNumber <= this.m_currentTour.getSize()) {
            /**
        	if(this.m_currentTourCreatorOptions.mb_playSoundDisplay) {
                this.playSound(this.m_soundDisplay);
            }**/
            //get element
            FLGStandardTourElement l_tempTourElement = (FLGStandardTourElement)this.m_currentTour.getElementAt(ai_elementNumber);
            File l_mediaFile = l_tempTourElement.getAssociatedMediaFile();
            //display element
            this.m_tourInteractor.displayFLG2TourCreatorElementInformation(l_tempTourElement.getFLG2TourCreatorElementInformation());
            //set associated media file
            this.getMediaPlayerDialog().setMediaFile(l_mediaFile, null);
            //Autoplay
            if( l_mediaFile != null &&
            (l_tempTourElement.getMediaPlaybackMode() == FLGTourElement.MEDIA_AUTO_PLAYBACK_MODE_ALWAYS
            ||
            (l_tempTourElement.getMediaPlaybackMode() == FLGTourElement.MEDIA_AUTO_PLAYBACK_MODE_IF_TIMEFLOW_ENABLED
            && m_presentationControl.getTimeFLowEnabled()
            ))) {
                //Autoplay Media
                this.getMediaPlayerDialog().startMediaPlayback();
            }//if
            
            
        }
        //illegal element number
        else {
            //unset media file and player dialog
            this.getMediaPlayerDialog().setMediaFile(null, null);
        }//if..else
        
    }//displayElement
    
    /**
     * Returns current tour creator options.
     */
    public FLGStandardTourCreatorOptions getCurrentOptions() {
        return this.m_currentTourCreatorOptions;
    }//getCurrentOptions
    
    /**
     * Returns a panel representing the specified tourElement
     */
    public javax.swing.JPanel getElementPanel(int ai_elementIndex) {
        if(ai_elementIndex >= 0) {
            //return normal panel
            return (javax.swing.JPanel) this.m_cellRenderer.getListCellRendererComponent(jList1, m_currentTour.getElementAt(ai_elementIndex), ai_elementIndex, false, true);
        }
        else {
            //creating pseudo element panel
            FLGStandardTourElement l_tempTourElement= new FLGStandardTourElement();
            
            l_tempTourElement.setElementName(this.m_currentTour.getTourName());
            
            //process total display time !!
            l_tempTourElement.setDisplayTime(this.getTotalTourDisplayTime());
            
            //description
            if(l_tempTourElement.getDisplayTime() == FLGStandardTourElement.UNLIMITED_DISPLAY_TIME) {
                //infinite total time
                l_tempTourElement.setDescription(m_internationalization.getString("tour.desciption.infiniteTotalDisplayTime"));
            }
            else if(l_tempTourElement.getDisplayTime() == FLGStandardTourElement.HIDDEN_DISPLAY_TIME) {
                //all elements hidden
                l_tempTourElement.setDescription(m_internationalization.getString("tour.description.onlyHiddenElements"));
            }
            else {
                //finite display time
                l_tempTourElement.setDescription(m_internationalization.getString("tour.description.finiteTotalTime") + " " + l_tempTourElement.getDisplayTime() + "s");
            }
            
            return (javax.swing.JPanel) this.m_cellRenderer.getListCellRendererComponent(jList1, l_tempTourElement, ai_elementIndex, false, true);
        }
        
    }//getElementPanel
    
    /**
     * Returns the singleton instance of this Standard Tour Creator
     */
    public static FLGStandardTourCreator getInstance() {
        if(m_tourCreatorSingleton == null)
            m_tourCreatorSingleton = new FLGStandardTourCreator();
        
        return m_tourCreatorSingleton;
    }//getInstance
    
    /**
     * Returns a reference to the StandardTourCreator internationalization object
     */
    public static FLGInternationalization getInternationalization() {
        return  m_internationalization;
    }//getInternationalization
    
    /**
     * Returns the MediaPlayerDialog
     */
    public static FLGStandardTourMediaPlayerDialog getMediaPlayerDialog() {
        if(m_mediaPlayerDialog == null)
            m_mediaPlayerDialog = new FLGStandardTourMediaPlayerDialog(getInstance());
        
        return m_mediaPlayerDialog;
    }//getMediaPlayerDialog
    
    /**
     * Returns the Element properties dialog
     */
    public FLGStandardTourElementPropertiesDialog getPropertiesDialog() {
        return this.m_elementPropertiesDialog;
    }//getPropertiesDialog
    
    /**
     * Returns the total display time of the current tour
     */
    public int getTotalTourDisplayTime() {
        int li_totalTime = 0;
        int li_tempTime;
        for(int i = this.m_currentTour.getSize()-1; i >= 0 ; i--) {
            li_tempTime = ((FLGStandardTourElement)this.m_currentTour.getElementAt(i)).getDisplayTime();
            if(li_tempTime == FLGStandardTourElement.UNLIMITED_DISPLAY_TIME) {
                return FLGStandardTourElement.UNLIMITED_DISPLAY_TIME;
            }
            else if(li_tempTime > 0) {
                li_totalTime += li_tempTime;
            }
        }//for
        
        if(li_totalTime == 0)
            return FLGStandardTourElement.HIDDEN_DISPLAY_TIME;
        else
            return li_totalTime;
    }//getTotalTourDisplayTime
    
    /**
     * Returns information about the save status of the current tour.
     * @return true if tour was modified after last save, else false.
     */
    public boolean getTourWasModified() {
        return this.mb_tourModified;
    }//getTourWasModified
    
    /**
     * Returns information about the save status of the current tour.
     * @return true if tour was never saved, else false.
     */
    public boolean getTourWasNeverSaved() {
        return this.mb_tourWasNeverSaved;
    }//getTourNeverSaved
    
    /**
     * Called if one or more elements have been added to the current tour.
     * @param a_listDataEvent event object holding further information
     */
    public void intervalAdded(ListDataEvent a_listDataEvent) {
        this.mb_tourModified = true;
        this.updateJFrameTitle();
    }//intervalAdded
    
    /**
     * Called if one or more elements have been removed from the current tour.
     * @param a_listDataEvent event object holding further information
     */
    public void intervalRemoved(ListDataEvent a_listDataEvent) {
        this.mb_tourModified = true;
        this.updateJFrameTitle();
    }//intervalRemoved
    
    /**
     * Returns the current status of the tour creator
     * @return true if in presentation mode, else false.
     */
    public boolean isInPresentationMode() {
        return this.mb_isInPresentationMode;
    }//isInPresentationMode
    
    /**
     * This method should be called for switching to non-presentation mode.
     */
    public void leavePresentationMode() {
        if(this.mb_isInPresentationMode) {
            this.mb_isInPresentationMode = false;
            this.m_presentationControl.leavePresentationMode();
            
            this.refreshAndShow();
        }//if
    }//leavePresentationMode
    
    /**
     * Loads a tour from disk.
     * @comment XML based import by Mirco Wahn!?
     */
    private void loadTour(/*parameters?*/) {
        if(this.mb_tourModified) {
            //alte Tour speichern?
        }
        //tour laden
        this.mb_tourModified = false;
        this.updateJFrameTitle();
    }//loadTour
    
    /**
     * Method which can be called e.g. by a FLG2TourCreatorInteractor to trigger the capture mechanism.
     * If ShowWindowOnPerformCaptureAction is set to true, tour creator will be shown.
     * Will also switch to non-presentation mode if necessary.
     */
    public void performCaptureAction(java.awt.event.ActionEvent e) {
        //disable TimeFlow if active
        this.m_presentationControl.disableTimeFlow();
        
        //act as if captured from within tour creator
        this.m_buttonCaptureActionPerformed(e);
        if(this.m_currentTourCreatorOptions.getShowWindowOnPerformCaptureAction())
            this.refreshAndShow();
        
    }//performCaptureAction
    
    /**
     * plays on of the prepared sounds (once)
     */
    public void playSound(java.applet.AudioClip a_audioClip) {
        if(this.mb_enableSounds)
            a_audioClip.play();
    }//playSound
    
    /**
     * This method hides the frame after closing the current tour.
     */
    private void pretendToExit() {
        //ask for save if rememberToSaveOnExitOption is set and tour is modified
        if(this.m_currentTourCreatorOptions.getRememberToSaveOnExitOption() && this.mb_tourModified) {
            //ask whether to abort (save will be done by called method)
            if(this.showRememberToSaveDialog() == JOptionPane.CANCEL_OPTION)
                return;
        }//if
        this.unregisterAtCurrentTour();
        
        //pretend to exit by hiding frame
        this.hide();
        
        //creating the new tour
        this.m_currentTour = new FLGStandardTour();
        this.registerAtCurrentTour();
        this.jList1.setModel(this.m_currentTour);
        //this.ms_currentTourFileName = "untitled.tour";
        this.mb_tourWasNeverSaved = true;
        this.mb_tourModified = false;
        this.updateJFrameTitle();
    }//pretendToExit
    
    public void refreshAndShow() {
        if(this.mb_isInPresentationMode) {
            this.m_presentationControl.refreshAndShow(this.m_currentTour);
        }//if
        else {
            this.show();
        }
    }//refreshAndShow
    
    /**
     * Registers the StandardTourCreator and its PresentationControl as DataListener at the currentTour(DataModel).
     */
    protected void registerAtCurrentTour() {
        ((FLGStandardTour)this.m_currentTour).addListDataListener(this);
        ((FLGStandardTour)this.m_currentTour).addListDataListener(this.m_presentationControl);
    }//initCurrentTour
    
    /**
     * Method used for registration of FLG2TourCreatorInteractor at the TourCreator.
     * @param a_tourInteractor the FLG2TourCreatorInteractor to be registered
     * @return error code out of {REGISTRATION_SUCCESS, REGISTRATION_ERROR}
     */
    public int registerFLG2TourCreatorInteractor(FLG2TourCreatorInteractor a_tourInteractor) {
        m_tourInteractor = a_tourInteractor;
        return this.REGISTRATION_SUCCESS;
    }//registerFLG2TourCreatorInteractor
    
    /**
     * Will save the current tour to disk.
     * @comment write2XML by Mirco Wahn!?
     */
    protected void saveCurrentTour(){
        //save as? -> change TourName
        if(this.mb_tourWasNeverSaved) {
            this.askForTourName();
        }
        this.saveCurrentTourAs(this.m_currentTour.getTourName());
    }//saveCurrentTour
    
    /**
     * Will save the current tour under specified fileName to disk.
     * @param as_tourFileName filename to be used for saving the tour
     * @comment write2XML by Mirco Wahn!?
     */
    protected void saveCurrentTourAs(String as_tourName){
        //save tour
        this.mb_tourWasNeverSaved = false;
        this.mb_tourModified = false;
        this.updateJFrameTitle();
    }//saveCurrentTourAs
    
    /**
     * Will automatically leave presentationMode to prevent that two windows will be visible.
     */
    public void show() {
        if(!this.mb_isInPresentationMode) {
            super.show();
        }
        else {
            System.out.println("Tour Creator: Leaving presentation mode");
            this.leavePresentationMode();
        }
    }//show
    
    /**
     * Opens an elementPropertyDialog window.
     */
    private void showElementPropertiesDialog(){
        this.m_elementPropertiesDialog.refreshAndShow(this.m_currentTour, this.jList1.getSelectedIndices());
    }//showElementPropertiesDialog
    
    /**
     * Shows an error dialog
     * @param as_title Title of the dialog window
     * @param as_message Displayed error message
     */
    public static int showErrorDialog(String as_title, String as_message) {
        FLGOptionPane l_tempDialog = new FLGOptionPane(
        as_message,
        as_title,
        FLGOptionPane.OK_OPTION,
        FLGOptionPane.ERROR_MESSAGE);
        l_tempDialog.setSize(300,150);
        java.awt.Dimension l_screenDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        l_tempDialog.setLocation((int)(l_screenDimension.width - l_tempDialog.getWidth())/2, (int)(l_screenDimension.height - l_tempDialog.getHeight())/2);
        return FLGOptionPane.showDialog(l_tempDialog);
    }//showErrorDialog
    
    private void showOptionsFrame() {
        if(this.m_tourCreatorOptionsFrame == null)
            this.m_tourCreatorOptionsFrame = new FLGStandardTourCreatorOptionsFrame(this);
        
        //lets option frame refresh its settings and display itself
        this.m_tourCreatorOptionsFrame.refreshAndShow();
    }//showOptionsFrame
    
    /**
     * Shows a dialog asking whether to save, not save or to abort. If save is chosen, saveCurrentTour() will be called.
     * @return value of the chosen option
     */
    private int showRememberToSaveDialog(){
        //ask user whether to save, not save or to abort
        
        FLGOptionPane l_tempDialog = new FLGOptionPane(
        m_internationalization.getString("dialog.saveTourChanges.message"),
        m_internationalization.getString("dialog.saveTourChanges.title"),
        FLGOptionPane.YES_NO_CANCEL_OPTION,
        FLGOptionPane.QUESTION_MESSAGE);
        
        l_tempDialog.setSize(450,150);
        java.awt.Dimension l_screenDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        l_tempDialog.setLocation((int)(l_screenDimension.width - l_tempDialog.getWidth())/2, (int)(l_screenDimension.height - l_tempDialog.getHeight())/2);
        int li_returnValue = FLGOptionPane.showDialog(l_tempDialog);
        
        //show tour creator again
        this.show();
        
        switch(li_returnValue) {
            case FLGOptionPane.CANCEL_OPTION:
                return FLGOptionPane.CANCEL_OPTION;
                
            case FLGOptionPane.NO_OPTION:
                return FLGOptionPane.NO_OPTION;
                
            default: //YES_OPTION
                this.saveCurrentTour();
                return FLGOptionPane.YES_OPTION;
        }//switch
    }//showRememberToSaveDialog
    
    /**
     * Unregisters this dataListener at the current tour. To be invoked before creating or loading new tours.
     */
    protected void unregisterAtCurrentTour() {
        this.m_currentTour.removeListDataListener(this);
    }//unregisterAtCurrentTour
    
    /**
     * This method updates the Frame title and displays the current tour name.
     * If the currently opened tour is modified an additional * is displayed
     */
    public void updateJFrameTitle(){
        String ls_title = this.ms_windowTitle; // + " - " + m_currentTour.getTourName();
        if(mb_tourModified) ls_title += "*";
        //ls_title += " (" + ((this.mb_tourWasNeverSaved)? "-" : this.ms_currentTourFileName) + ")";
        this.setTitle(ls_title);
    }//updateJFrameTitle
    
    // Variables declaration - do not modify
    private javax.swing.JMenuItem m_editMenuItemMoveUp;
    private javax.swing.JMenuItem m_listPopupMenuItemMoveUp;
    private javax.swing.JMenuItem m_tourMenuItemSave;
    private javax.swing.JMenuItem m_tourMenuItemDelete;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator m_tourMenuSeparator2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator m_tourMenuSeparator1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuBar m_tourCreatorMenuBar;
    private javax.swing.JMenu m_tourMenu;
    private javax.swing.JPanel m_panelElementList;
    private javax.swing.JSeparator m_editMenuSeparator2;
    private javax.swing.JSeparator m_editMenuSeparator1;
    private javax.swing.JMenuItem m_tourMenuItemNew;
    private javax.swing.JMenuItem m_editMenuItemProperties;
    private javax.swing.JMenuItem m_listPopupMenuItemProperties;
    private javax.swing.JPanel m_panelButtonPanel;
    private javax.swing.JMenu m_editMenu;
    private javax.swing.JPopupMenu m_listPopupMenu;
    private javax.swing.JMenuItem m_editMenuItemMoveTo;
    private javax.swing.JMenuItem m_tourMenuItemExit;
    private javax.swing.JMenuItem m_listPopupMenuItemMoveTo;
    private javax.swing.JMenuItem m_editMenuItemDelete;
    private javax.swing.JMenuItem m_listPopupMenuItemDelete;
    private javax.swing.JMenuItem m_optionsMenuItemEditOptions;
    private javax.swing.JMenuItem m_editMenuItemDisplay;
    private javax.swing.JMenuItem m_listPopupMenuItemDisplay;
    private javax.swing.JMenuItem m_optionsMenuItemInfo;
    private javax.swing.JMenu m_optionsMenu;
    private javax.swing.JMenuItem m_tourMenuItemSaveAs;
    protected javax.swing.JList jList1;
    private javax.swing.JMenuItem m_tourMenuItemOpen;
    private javax.swing.JPanel m_panelTop;
    private javax.swing.JMenuItem m_editMenuItemMoveDown;
    private javax.swing.JMenuItem m_listPopupMenuItemMoveDown;
    // End of variables declaration
    
}
