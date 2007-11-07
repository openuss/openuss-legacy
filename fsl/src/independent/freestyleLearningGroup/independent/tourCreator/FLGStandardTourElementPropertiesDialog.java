package freestyleLearningGroup.independent.tourCreator;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import freestyleLearning.learningUnitViewAPI.*;
import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.independent.gui.documents.*;

/**
 *
 * @author Steffen Wachenfeld
 */
public class FLGStandardTourElementPropertiesDialog extends javax.swing.JDialog {

    /**
     * JSpinner used for display time
     */
    private javax.swing.JSpinner m_spinnerDisplayTime = null;

    /**
     * Will be set true if Frame represents properties of multiple elements
     */
    private FLGStandardTour m_currentTour;
    private int[] mi_currentIndexes_;

    /**
     * Holding the displayed image
     */
    private Image m_displayedImage;

    /**
     * Current Directory for selected Media
     */
    private File m_currentMediaFileDirectory;

    /**
     * Audio File Chooser
     */
    private static FLGMediaFileChooser m_mediaFileChooser;

    /**
     * Reference to MediaPlayerDialog
     */
    private FLGStandardTourMediaPlayerDialog m_mediaPlayerDialog;

    /**
     * InvisibleRadioButtons
     */
    private JRadioButton m_buttonNoDisplayTimeSelected;
    private JRadioButton m_buttonNoDisplayBehaviorSelected;

    private JFileChooser fileChooser;


    /**
     * Creates new form FLGStandardTourElementPropertiesDialog
     */
    public FLGStandardTourElementPropertiesDialog(java.awt.Frame a_parent, boolean ab_modal) {
        super(a_parent, ab_modal);
        initComponents();

        //manual Internationalization for Tabs (unsupported by Sun ONE Studio)
        for(int li_tabNr = 0 ; li_tabNr < m_tabbedPaneMain.getTabCount(); li_tabNr++){
           String ls_tempString = FLGStandardTourCreator.getInternationalization().getString(m_tabbedPaneMain.getTitleAt(li_tabNr));
           m_tabbedPaneMain.setTitleAt(li_tabNr, ls_tempString);
        }//for

        //init JSpinner and required SpinnerModel
        javax.swing.SpinnerNumberModel l_spinnerNumberModel =
           new javax.swing.SpinnerNumberModel(FLGTourElement.PRESET_DISPLAY_TIME, //value
                                              FLGTourElement.MIN_DISPLAY_TIME,    //minimum
                                              FLGTourElement.MAX_DISPLAY_TIME,    //maximum
                                              FLGTourElement.DELTA_DISPLAY_TIME); //stepsize
        this.m_spinnerDisplayTime = new javax.swing.JSpinner(l_spinnerNumberModel);
        this.m_panelDisplayTimeCustom.add(this.m_spinnerDisplayTime);

        //create AudioFileChooser
        if(m_mediaFileChooser == null)
        {
            m_mediaFileChooser = new FLGMediaFileChooser(FLGMediaFileChooser.AUDIO);
            //disable multiple file select
            m_mediaFileChooser.setMultiSelectionEnabled(false);
        }//if

        //create invisible RadioButtons for noOption if multiSelect ;-)
        this.m_buttonNoDisplayTimeSelected     = new JRadioButton();
        this.m_buttonNoDisplayBehaviorSelected = new JRadioButton();
        this.m_buttonGroupDisplayTime.add(this.m_buttonNoDisplayTimeSelected);
        this.m_buttonGroupMediaAutoplay.add(this.m_buttonNoDisplayBehaviorSelected);

        //Size and position
        java.awt.Dimension l_screenDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(400, 550);
        setLocation((int)(l_screenDimension.width - getWidth())/2, (int)(l_screenDimension.height - getHeight())/2);
    }//constructor
    
    public static void main(String[] args) {
        FLGStandardTourCreator creator = new FLGStandardTourCreator();
        FLGStandardTourElementPropertiesDialog dialog = new FLGStandardTourElementPropertiesDialog(null, true);
        dialog.setVisible(true);
        System.exit(0);
    }
    
    private void initComponents() {
        fileChooser = new JFileChooser();
        m_buttonGroupDisplayTime = new javax.swing.ButtonGroup();
        m_buttonGroupMediaAutoplay = new javax.swing.ButtonGroup();
        m_panelMainPanel = new FLGEffectPanel("FSLMainFrameColor1", "FLGDialog.background", true);
        m_panelMainPanel = new FLGEffectPanel();
        m_panelGeneralInfo = new javax.swing.JPanel();
        m_panelGeneralInfoDescriptors = new javax.swing.JPanel();
        m_labelNumberText = new javax.swing.JLabel();
        m_panelNameText = new javax.swing.JLabel();
        m_panelGeneralInfoContent = new javax.swing.JPanel();
        m_labelNumber = new javax.swing.JLabel();
        m_textFieldName = new javax.swing.JTextField();
        m_textFieldName.setDocument(new FLGNameDocument(true));
        m_tabbedPaneMain = new javax.swing.JTabbedPane();
        m_panelImagePanel = new javax.swing.JPanel();
        m_labelImage = new javax.swing.JLabel();
        m_labelImage.setBorder(BorderFactory.createEmptyBorder(5,5,5,5)); 
        m_panelTabDisplay = new javax.swing.JPanel();
        m_panelDisplayTime = new javax.swing.JPanel();
        m_buttonDisplayTimeInfinite = new javax.swing.JRadioButton();
        m_buttonDisplayTimeHidden = new javax.swing.JRadioButton();
        m_panelDisplayTimeCustom = new javax.swing.JPanel();
        m_buttonDisplayTimeCustom = new javax.swing.JRadioButton();
        m_panelDisplayMediaAutoplay = new javax.swing.JPanel();
        m_buttonAutoplayNever = new javax.swing.JRadioButton();
        m_buttonAutoplayIfTimeflow = new javax.swing.JRadioButton();
        m_buttonAutoplayAlways = new javax.swing.JRadioButton();
        m_panelDisplayBehavior = new javax.swing.JPanel();
        m_buttonWaitForEndOfMedia = new javax.swing.JCheckBox();
        m_buttonDisplayNextElementIfEndOfMedia = new javax.swing.JCheckBox();
        m_panelTabInformation = new javax.swing.JPanel();
        m_panelInfo = new javax.swing.JPanel();
        m_labelInfo = new javax.swing.JLabel();
        m_panelDescription = new javax.swing.JPanel();
        m_scrollPaneDescription = new javax.swing.JScrollPane();
        m_textAreaDescription = new javax.swing.JTextArea();
        m_panelTabMedia = new javax.swing.JPanel();
        m_panelMediaInfo = new javax.swing.JPanel();
        m_labelSelectedMedia = new javax.swing.JLabel();
        m_panelMediaButtons = new javax.swing.JPanel();
        m_buttonSelectMedia = new javax.swing.JButton();
        m_buttonTestMedia = new javax.swing.JButton();
        m_panelMediaPlayer = new javax.swing.JPanel();
        m_panelButtonPanel = new FLGEffectPanel("FLGDialog.background", true);
        m_buttonOK = new FLGTextButton3D(FLGStandardTourCreator.getInternationalization().getString("dialog.buttonText.ok"), FLGUIUtilities.BASE_COLOR4);
        m_buttonCancel = new FLGTextButton3D(FLGStandardTourCreator.getInternationalization().getString("dialog.buttonText.cancel"), FLGUIUtilities.BASE_COLOR4);

        setTitle(java.util.ResourceBundle.getBundle("freestyleLearningGroup/independent/tourCreator/internationalization").getString("dialog.elementProperties.title"));
        setModal(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        m_panelMainPanel.setLayout(new java.awt.BorderLayout(0, 10));

        m_panelMainPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(10, 10, 10, 10)));
        m_panelGeneralInfo.setLayout(new java.awt.BorderLayout(10, 0));

        m_panelGeneralInfo.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new javax.swing.border.TitledBorder(FLGStandardTourCreator.getInternationalization().getString("dialog.elementProperties.panelGeneral.borderText")),
            new javax.swing.border.EmptyBorder(5,5,5,5)));
        m_panelGeneralInfo.setOpaque(false);
        m_panelGeneralInfoDescriptors.setLayout(new java.awt.GridLayout(2, 1));

        m_panelGeneralInfoDescriptors.setOpaque(false);
        m_labelNumberText.setText(FLGStandardTourCreator.getInternationalization().getString("dialog.elementProperties.panelGeneral.textNumber"));
        m_labelNumberText.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        m_panelGeneralInfoDescriptors.add(m_labelNumberText);

        m_panelNameText.setText(FLGStandardTourCreator.getInternationalization().getString("dialog.elementProperties.panelGeneral.textName"));
        m_panelGeneralInfoDescriptors.add(m_panelNameText);

        m_panelGeneralInfo.add(m_panelGeneralInfoDescriptors, java.awt.BorderLayout.WEST);

        m_panelGeneralInfoContent.setLayout(new java.awt.GridLayout(2, 1));

        m_panelGeneralInfoContent.setOpaque(false);
        m_labelNumber.setText("-1");
        m_labelNumber.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        m_panelGeneralInfoContent.add(m_labelNumber);

        m_textFieldName.setBackground(new java.awt.Color(255, 255, 255));
        m_textFieldName.setColumns(40);
        m_textFieldName.setText("unnamed");
        m_panelGeneralInfoContent.add(m_textFieldName);

        m_panelGeneralInfo.add(m_panelGeneralInfoContent, java.awt.BorderLayout.CENTER);

        m_panelMainPanel.add(m_panelGeneralInfo, java.awt.BorderLayout.NORTH);

        m_panelImagePanel.setLayout(new java.awt.BorderLayout());

        m_labelImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_labelImage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        
        JPanel buttonPanel_image = new JPanel(new FlowLayout());
        JButton button_changeImage = new JButton(FLGStandardTourCreator.getInternationalization().getString("button.changeImage.text"));
        button_changeImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                fileChooser.setFileFilter(new FLGUIUtilities.FLGFileFilter( new String[] { ".jpg", ".gif" },
                    FLGStandardTourCreator.getInternationalization().getString("dialog.imageFiles.description"))); 
                if (fileChooser.showOpenDialog(FLGStandardTourElementPropertiesDialog.this) == JFileChooser.APPROVE_OPTION) {
                   setElementImage(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
        buttonPanel_image.add(button_changeImage);
        JPanel panelImage = new JPanel(new BorderLayout());
        panelImage.add(m_labelImage, java.awt.BorderLayout.CENTER);
        panelImage.add(buttonPanel_image, java.awt.BorderLayout.SOUTH);
        panelImage.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(FLGStandardTourCreator.getInternationalization().getString("dialog.elementProperties.image.border")),
            BorderFactory.createEmptyBorder(5,5,5,5)));
        
        m_panelImagePanel.add(panelImage, java.awt.BorderLayout.CENTER);
        
        m_tabbedPaneMain.addTab("dialog.elementProperties.tabTitle.icon", m_panelImagePanel);

        m_panelTabDisplay.setLayout(new java.awt.GridLayout(3, 1));

        m_panelDisplayTime.setLayout(new java.awt.GridLayout(3, 1, 5, 0));

        m_panelDisplayTime.setBorder(new javax.swing.border.TitledBorder(FLGStandardTourCreator.getInternationalization().getString("dialog.elementProperties.panelDisplayTime.borderText")));
        m_buttonDisplayTimeInfinite.setText(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelDisplayTime.buttonInfiniteOption"));
        m_buttonGroupDisplayTime.add(m_buttonDisplayTimeInfinite);
        m_buttonDisplayTimeInfinite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_buttonDisplayTimeInfiniteActionPerformed(evt);
            }
        });

        m_panelDisplayTime.add(m_buttonDisplayTimeInfinite);

        m_buttonDisplayTimeHidden.setText(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelDisplayTime.buttonHiddenOption"));
        m_buttonDisplayTimeHidden.setToolTipText("Element will not be displayed if selected");
        m_buttonGroupDisplayTime.add(m_buttonDisplayTimeHidden);
        m_buttonDisplayTimeHidden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_buttonDisplayTimeHiddenActionPerformed(evt);
            }
        });

        m_panelDisplayTime.add(m_buttonDisplayTimeHidden);

        m_panelDisplayTimeCustom.setLayout(new java.awt.GridLayout(1, 2));

        m_buttonDisplayTimeCustom.setText(FLGStandardTourCreator.getInternationalization().getString("tourCreatorOptionsDialog.panelDisplayTime.buttonCustomOption"));
        m_buttonGroupDisplayTime.add(m_buttonDisplayTimeCustom);
        m_buttonDisplayTimeCustom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_buttonDisplayTimeCustomActionPerformed(evt);
            }
        });

        m_panelDisplayTimeCustom.add(m_buttonDisplayTimeCustom);

        m_panelDisplayTime.add(m_panelDisplayTimeCustom);

        m_panelTabDisplay.add(m_panelDisplayTime);

        m_panelDisplayMediaAutoplay.setLayout(new java.awt.GridLayout(3, 1, 5, 0));

        m_panelDisplayMediaAutoplay.setBorder(new javax.swing.border.TitledBorder(FLGStandardTourCreator.getInternationalization().getString("dialog.elementProperties.panelDisplayMediaAutoplay.borderText")));
        m_buttonAutoplayNever.setText(FLGStandardTourCreator.getInternationalization().getString("dialog.elementProperties.panelDisplayMediaAutoplay.never"));
        m_buttonGroupMediaAutoplay.add(m_buttonAutoplayNever);
        m_panelDisplayMediaAutoplay.add(m_buttonAutoplayNever);

        m_buttonAutoplayIfTimeflow.setText(FLGStandardTourCreator.getInternationalization().getString("dialog.elementProperties.panelDisplayMediaAutoplay.ifTimeflowEnabled"));
        m_buttonGroupMediaAutoplay.add(m_buttonAutoplayIfTimeflow);
        m_panelDisplayMediaAutoplay.add(m_buttonAutoplayIfTimeflow);

        m_buttonAutoplayAlways.setText(FLGStandardTourCreator.getInternationalization().getString("dialog.elementProperties.panelDisplayMediaAutoplay.always"));
        m_buttonGroupMediaAutoplay.add(m_buttonAutoplayAlways);
        m_panelDisplayMediaAutoplay.add(m_buttonAutoplayAlways);

        // temporary disabled
//        m_panelTabDisplay.add(m_panelDisplayMediaAutoplay);

        m_panelDisplayBehavior.setLayout(new java.awt.GridLayout(3, 1, 5, 0));

        m_panelDisplayBehavior.setBorder(new javax.swing.border.TitledBorder(FLGStandardTourCreator.getInternationalization().getString("dialog.elementProperties.panelDisplayBehavior.borderText")));
        m_buttonWaitForEndOfMedia.setText(FLGStandardTourCreator.getInternationalization().getString("dialog.elementProperties.panelDisplayBehavior.buttonWaitForEndOfMedia"));
        m_panelDisplayBehavior.add(m_buttonWaitForEndOfMedia);

        m_buttonDisplayNextElementIfEndOfMedia.setText(FLGStandardTourCreator.getInternationalization().getString("dialog.elementProperties.panelDisplayBehavior.buttonDisplayNextElementIfEndOfMedia"));
        m_panelDisplayBehavior.add(m_buttonDisplayNextElementIfEndOfMedia);

        // temporary disabled
//        m_panelTabDisplay.add(m_panelDisplayBehavior);

        m_tabbedPaneMain.addTab("dialog.elementProperties.tabTitle.displayOptions", m_panelTabDisplay);

        m_panelTabInformation.setLayout(new java.awt.BorderLayout());

        m_panelInfo.setLayout(new java.awt.BorderLayout());

        m_panelInfo.setBorder(new javax.swing.border.TitledBorder(FLGStandardTourCreator.getInternationalization().getString("dialog.elementProperties.panelInternalInfo.borderText")));
        m_labelInfo.setText("noInfo");
        m_panelInfo.add(m_labelInfo, java.awt.BorderLayout.CENTER);

        m_panelTabInformation.add(m_panelInfo, java.awt.BorderLayout.NORTH);

        m_panelDescription.setLayout(new java.awt.BorderLayout());

        m_panelDescription.setBorder(new javax.swing.border.TitledBorder(FLGStandardTourCreator.getInternationalization().getString("dialog.elementProperties.panelDescription.borderText")));
        m_textAreaDescription.setColumns(40);
        m_textAreaDescription.setRows(4);
        m_textAreaDescription.setTabSize(3);
        m_scrollPaneDescription.setViewportView(m_textAreaDescription);

        m_panelDescription.add(m_scrollPaneDescription, java.awt.BorderLayout.CENTER);

        m_panelTabInformation.add(m_panelDescription, java.awt.BorderLayout.CENTER);

        m_tabbedPaneMain.addTab("dialog.elementProperties.tabTitle.information", m_panelTabInformation);

        m_panelTabMedia.setLayout(new java.awt.BorderLayout());

        m_panelMediaInfo.setLayout(new java.awt.BorderLayout());

        m_panelMediaInfo.setBorder(new javax.swing.border.TitledBorder(FLGStandardTourCreator.getInternationalization().getString("dialog.elementProperties.panelMedia.borderText")));
        m_labelSelectedMedia.setText(FLGStandardTourCreator.getInternationalization().getString("dialog.elementProperties.panelMedia.selectedMedia"));
        m_panelMediaInfo.add(m_labelSelectedMedia, java.awt.BorderLayout.CENTER);

        m_buttonSelectMedia.setText(FLGStandardTourCreator.getInternationalization().getString("dialog.elementProperties.panelMedia.selectMediaButton"));
        m_buttonSelectMedia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_buttonSelectMediaActionPerformed(evt);
            }
        });

        m_panelMediaButtons.add(m_buttonSelectMedia);

        m_buttonTestMedia.setText("Test");
        m_buttonTestMedia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_buttonTestMediaActionPerformed(evt);
            }
        });

        m_panelMediaButtons.add(m_buttonTestMedia);

        m_panelMediaInfo.add(m_panelMediaButtons, java.awt.BorderLayout.EAST);

        m_panelTabMedia.add(m_panelMediaInfo, java.awt.BorderLayout.NORTH);

        m_panelMediaPlayer.setLayout(new java.awt.BorderLayout());

        m_panelTabMedia.add(m_panelMediaPlayer, java.awt.BorderLayout.CENTER);

        // temporary disabled
//        m_tabbedPaneMain.addTab("dialog.elementProperties.tabTitle.mediaOptions", m_panelTabMedia);

        for (int i = 0; i < m_tabbedPaneMain.getTabCount(); i++) {
            ((JComponent)m_tabbedPaneMain.getComponentAt(i)).setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        }
        
        m_panelMainPanel.add(m_tabbedPaneMain, java.awt.BorderLayout.CENTER);

        getContentPane().add(m_panelMainPanel, java.awt.BorderLayout.CENTER);

        m_buttonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_buttonOKActionPerformed(evt);
            }
        });

        m_panelButtonPanel.add(m_buttonOK);

        m_buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_buttonCancelActionPerformed(evt);
            }
        });

        m_panelButtonPanel.add(m_buttonCancel);

        getContentPane().add(m_panelButtonPanel, java.awt.BorderLayout.SOUTH);
        rootPane.setDefaultButton(m_buttonOK);
        m_buttonCancel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "ESCAPE");
        m_buttonCancel.getActionMap().put("ESCAPE",
            new AbstractAction() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    m_buttonCancelActionPerformed(evt);
                }
            });
        pack();
    }
    
    private void m_buttonTestMediaActionPerformed(java.awt.event.ActionEvent evt) {
        //if not exactly one element selected return
        if(mi_currentIndexes_.length != 1)
            return;

        //get reference to currently selected tour element
        FLGStandardTourElement l_tempTourElement = (FLGStandardTourElement)m_currentTour.getElementAt(mi_currentIndexes_[0]);

        //play if there is an associated media file
        File l_associatedFile = ((FLGStandardTourElement)this.m_currentTour.getElementAt(this.mi_currentIndexes_[0])).getAssociatedMediaFile();
        if(l_associatedFile != null)
        {
            FLGStandardTourMediaPlayerDialog l_mediaPlayerDialog = FLGStandardTourCreator.getInstance().getMediaPlayerDialog();
            l_mediaPlayerDialog.hide();
            l_mediaPlayerDialog.setMediaFile(l_associatedFile, this.m_panelMediaPlayer);
        }//if
    }
    
    private void setElementImage(String imageAbsolutePath) {
        // create scaled image
        Image newImage = new ImageIcon(imageAbsolutePath).getImage();
        ImageObserver observer = new ImageObserver() {
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        };
        int height = newImage.getHeight(observer);
        int width  = newImage.getWidth(observer);
        double scaleFactor = Math.min(256./width, 192./height);
        m_displayedImage = newImage.getScaledInstance((int)(width*scaleFactor), (int)(height*scaleFactor), Image.SCALE_SMOOTH);      
        
        // update dialog display
        Icon icon = new ImageIcon(m_displayedImage);
        m_labelImage.setIcon(icon);

        // update element information
        FLGStandardTourElement tourElement = (FLGStandardTourElement)m_currentTour.getElementAt(mi_currentIndexes_[0]);        
        tourElement.getFLG2TourCreatorElementInformation().setElementImage(m_displayedImage);
        tourElement.deriveSmallImage();
    }    
    
    private void m_buttonSelectMediaActionPerformed(java.awt.event.ActionEvent evt) {
        //if not exactly one element selected return
        if(mi_currentIndexes_.length != 1) return;

        //get reference to currently selected tour element
        FLGStandardTourElement l_tempTourElement = (FLGStandardTourElement)m_currentTour.getElementAt(mi_currentIndexes_[0]);

        //create File Open Dialog
        if(l_tempTourElement.getAssociatedMediaFile() != null)
        {
            //set old Media File
            m_mediaFileChooser.setSelectedFile(l_tempTourElement.getAssociatedMediaFile());
        }else if(this.m_currentMediaFileDirectory != null)
        {
            //set current audio directory
            m_mediaFileChooser.setSelectedFile(this.m_currentMediaFileDirectory);
        }

        try{
            //show dialog
            int li_returnValue = m_mediaFileChooser.showOpenDialog(this);

            // process return value
            if(li_returnValue == m_mediaFileChooser.APPROVE_OPTION)
            {
                //APPROVED:
                File l_selectedFile = m_mediaFileChooser.getSelectedFile();

                //clear player panel
                 this.m_panelMediaPlayer.removeAll();

                //select file
                l_tempTourElement.setAssociatedMediaFile(l_selectedFile);

                //update current directory
                this.m_currentMediaFileDirectory = m_mediaFileChooser.getCurrentDirectory();

                //update associated Media File display
                this.updateAssociatedMediaFileInfo();
            }//if APPROVED
        }catch(HeadlessException he){
            he.printStackTrace();
        }
    }
    private void m_buttonDisplayTimeCustomActionPerformed(java.awt.event.ActionEvent evt) {
        // enable Spinner
        this.m_spinnerDisplayTime.setEnabled(true);
    }
    private void m_buttonDisplayTimeHiddenActionPerformed(java.awt.event.ActionEvent evt) {
        // disable Spinner
        this.m_spinnerDisplayTime.setEnabled(false);
    }
    private void m_buttonDisplayTimeInfiniteActionPerformed(java.awt.event.ActionEvent evt) {
        // disable Spinner
        this.m_spinnerDisplayTime.setEnabled(false);
    }
    private void m_buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {
        FLGStandardTourCreator.getMediaPlayerDialog().stopMediaPlayback();
        this.m_panelMediaPlayer.removeAll();
        this.hide();
    }

    private void m_buttonOKActionPerformed(java.awt.event.ActionEvent evt) {
        FLGStandardTourCreator.getMediaPlayerDialog().stopMediaPlayback();
        this.setAndHide();
    }

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {
        //hide
        FLGStandardTourCreator.getMediaPlayerDialog().stopMediaPlayback();
        this.m_panelMediaPlayer.removeAll();
        this.hide();
    }


    /**
     * Refreshes the displayed property settings and shows the dialog.
     * @param a_tour the tour containing the selected elements
     * @param ai_selectedIndexes_ the index numbers of the selected elements
     */
    public void refreshAndShow(FLGStandardTour a_tour, int[] ai_selectedIndexes_)
    {
        //check tourElements
        if(ai_selectedIndexes_ == null || ai_selectedIndexes_.length <= 0)
            return;

        //hide Media Player if visible (includes implicit prefetch)
        FLGStandardTourCreator.getInstance().getMediaPlayerDialog().hide();

        //store parameters
        this.m_currentTour = a_tour;
        this.mi_currentIndexes_ = ai_selectedIndexes_;

        //update associated Media File display
        this.updateAssociatedMediaFileInfo();

        //single
        if(mi_currentIndexes_.length == 1)
        {
            FLGStandardTourElement l_tempTourElement = (FLGStandardTourElement)m_currentTour.getElementAt(mi_currentIndexes_[0]);

            //enable all tabs
            this.m_tabbedPaneMain.setEnabledAt(0, true);
            this.m_tabbedPaneMain.setEnabledAt(1, true);
            this.m_tabbedPaneMain.setEnabledAt(2, true);
            //refresh number
            this.m_labelNumber.setText("" + (mi_currentIndexes_[0]+1));
            //refresh name
            this.m_textFieldName.setText(l_tempTourElement.getElementName());
            this.m_textFieldName.setEditable(true);


            //refresh Image Tab
            this.m_displayedImage = l_tempTourElement.getImage();
            m_labelImage.setIcon(new javax.swing.ImageIcon(this.m_displayedImage));


            //refresh Display Option Tab
            int li_displayTime = l_tempTourElement.getDisplayTime();
            //unlimited
            if(li_displayTime == FLGTourElement.UNLIMITED_DISPLAY_TIME)
            {
                this.m_buttonDisplayTimeInfinite.setSelected(true);
                //this.m_buttonDisplayTimeCustom.setSelected(false);
                //this.m_buttonDisplayTimeHidden.setSelected(false);
                this.m_spinnerDisplayTime.setEnabled(false);
            }
            //hidden
            else if(li_displayTime == FLGTourElement.HIDDEN_DISPLAY_TIME)
            {
                    this.m_buttonDisplayTimeHidden.setSelected(true);
                    //this.m_buttonDefaultDisplayTimeCustom.setSelected(false);
                    //this.m_buttonDefaultDisplayTimeInfinite.setSelected(false);
                    this.m_spinnerDisplayTime.setEnabled(false);
            }
            //custom
            else
            {
                this.m_buttonDisplayTimeCustom.setSelected(true);
                //this.m_buttonDisplayTimeCustom.setSelected(false);
                //this.m_buttonDisplayTimeHidden.setSelected(false);
                this.m_spinnerDisplayTime.setValue(new Integer(li_displayTime));
                this.m_spinnerDisplayTime.setEnabled(true);
            }//else

            //Autoplay Options
            //never
            if(l_tempTourElement.getMediaPlaybackMode() == FLGTourElement.MEDIA_AUTO_PLAYBACK_MODE_NEVER)
            {
                this.m_buttonAutoplayNever.setSelected(true);
            }
            //if timeflow enabled
            else if(l_tempTourElement.getMediaPlaybackMode() == FLGTourElement.MEDIA_AUTO_PLAYBACK_MODE_IF_TIMEFLOW_ENABLED)
            {
                this.m_buttonAutoplayIfTimeflow.setSelected(true);
            }
            //always
            else if(l_tempTourElement.getMediaPlaybackMode() == FLGTourElement.MEDIA_AUTO_PLAYBACK_MODE_ALWAYS)
            {
                this.m_buttonAutoplayAlways.setSelected(true);
            }//if..else

            //Display behavior
            this.m_buttonWaitForEndOfMedia.setSelected(l_tempTourElement.getWaitForEndOfMedia());
            this.m_buttonDisplayNextElementIfEndOfMedia.setSelected(l_tempTourElement.getNextElementOnEndOfMedia());
            this.m_buttonWaitForEndOfMedia.setEnabled(true);
            this.m_buttonDisplayNextElementIfEndOfMedia.setEnabled(true);

            //refresh Information Tab
            FLG2TourCreatorElementInformation l_elementInformation = l_tempTourElement.getFLG2TourCreatorElementInformation();
            String ls_info  =   "<html><u>Unit:</u><br>"
                               + l_elementInformation.getTargetLearningUnitName() + " (ID: " + l_elementInformation.getTargetLearningUnitId() + ")<br>"
                               + "<u>Manager:</u><br>"
                               + l_elementInformation.getTargetLearningUnitViewManagerName() + " (ID: " + l_elementInformation.getTargetLearningUnitViewManagerId() + ")<br>"
                               + "<u>Element:</u><br>"
                               + l_elementInformation.getTargetLearningUnitViewElementName() + " (ID: " + l_elementInformation.getTargetLearningUnitViewElementId() + ")</html>";
            this.m_labelInfo.setText(ls_info);

            //refresh description
            this.m_textAreaDescription.setText(l_tempTourElement.getDescription());
            this.m_textAreaDescription.setEnabled(true);

        }//single
        /*****************************************************
         * multiple
         ****************************************************/
        else
        {
            //disable tabs except display time
            this.m_tabbedPaneMain.setEnabledAt(0, false);
            this.m_tabbedPaneMain.setEnabledAt(1, true);
            this.m_tabbedPaneMain.setEnabledAt(2, false);
            this.m_tabbedPaneMain.setSelectedIndex(1);

            //refresh number
            //always
            String ls_number = "" + (mi_currentIndexes_[0]+1);
            for(int i=1; i < mi_currentIndexes_.length; i++)
            {
                ls_number += ", " + (mi_currentIndexes_[i]+1);
            }//for
            this.m_labelNumber.setText(ls_number);

            //refresh name
            this.m_textFieldName.setText("- (multiple elements)");
            this.m_textFieldName.setEditable(false);

            //Display Time (deselect visible radio buttons and Spinner)
            this.m_buttonNoDisplayTimeSelected.setSelected(true);
            this.m_spinnerDisplayTime.setEnabled(false);

            //Autoplay Options (deselect visible radio buttons)
            this.m_buttonNoDisplayBehaviorSelected.setSelected(true);

            //Display behavior
            this.m_buttonWaitForEndOfMedia.setEnabled(false);
            this.m_buttonDisplayNextElementIfEndOfMedia.setEnabled(false);

            //refresh image
            //this.m_displayedImage = ((FLGStandardTourElement)m_currentTour.getElementAt(mi_currentIndexes_[0])).getMultipleElementImage();
            //m_labelImage.setIcon(new javax.swing.ImageIcon(this.m_displayedImage));

        }//multiple

        //show
        this.show();
    }//refreshAndShow


    /**
     * Method to set the changed properties and to hide the properties dialog
     */
    private void setAndHide()
    {
        FLGStandardTourElement l_tempTourElement = null;

        //set
        if(this.mi_currentIndexes_.length == 1)
        {
            //getting target Element
            l_tempTourElement = (FLGStandardTourElement)this.m_currentTour.getElementAt(mi_currentIndexes_[0]);

            //set image
            l_tempTourElement.setImage(this.m_displayedImage);

            //set name
            l_tempTourElement.setElementName(this.m_textFieldName.getText());

            //set display time
            if(this.m_buttonDisplayTimeInfinite.isSelected())
                l_tempTourElement.setDisplayTime(FLGTourElement.UNLIMITED_DISPLAY_TIME);
            else if(this.m_buttonDisplayTimeHidden.isSelected())
                l_tempTourElement.setDisplayTime(FLGTourElement.HIDDEN_DISPLAY_TIME);
            else
                l_tempTourElement.setDisplayTime(((Number)this.m_spinnerDisplayTime.getValue()).intValue());

            //Autoplay Options
            //never
            if(this.m_buttonAutoplayNever.isSelected())
            {
                l_tempTourElement.setMediaPlaybackMode(FLGTourElement.MEDIA_AUTO_PLAYBACK_MODE_NEVER);
            }
            //if timeflow enabled
            else if(this.m_buttonAutoplayIfTimeflow.isSelected())
            {
                l_tempTourElement.setMediaPlaybackMode(FLGTourElement.MEDIA_AUTO_PLAYBACK_MODE_IF_TIMEFLOW_ENABLED);
            }
            //always
            else if(this.m_buttonAutoplayAlways.isSelected())
            {
                l_tempTourElement.setMediaPlaybackMode(FLGTourElement.MEDIA_AUTO_PLAYBACK_MODE_ALWAYS);
            }//if..else

            //Display next element on End of Media
            if(this.m_buttonDisplayNextElementIfEndOfMedia.isSelected())
                l_tempTourElement.setNextElementOnEndOfMedia(true);
            else
                l_tempTourElement.setNextElementOnEndOfMedia(false);

            //Wait for End of Media
            if(this.m_buttonWaitForEndOfMedia.isSelected())
                l_tempTourElement.setWaitForEndOfMedia(true);
            else
                l_tempTourElement.setWaitForEndOfMedia(false);

            //set description
            l_tempTourElement.setDescription(this.m_textAreaDescription.getText());
        }
        else
        {
            //set for multiple

            //set display time
            if(this.m_buttonDisplayTimeInfinite.isSelected())
            {
                for(int i=0; i < this.mi_currentIndexes_.length; i++)
                {
                    //getting target Element
                    l_tempTourElement = (FLGStandardTourElement)this.m_currentTour.getElementAt(this.mi_currentIndexes_[i]);
                    l_tempTourElement.setDisplayTime(FLGTourElement.UNLIMITED_DISPLAY_TIME);
                }//for
            }
            else if(this.m_buttonDisplayTimeHidden.isSelected())
            {
                for(int i=0; i < this.mi_currentIndexes_.length; i++)
                {
                    //getting target Element
                    l_tempTourElement = (FLGStandardTourElement)this.m_currentTour.getElementAt(this.mi_currentIndexes_[i]);
                    l_tempTourElement.setDisplayTime(FLGTourElement.HIDDEN_DISPLAY_TIME);
                }//for
            }
            else if(this.m_buttonDisplayTimeCustom.isSelected())
            {
                for(int i=0; i < this.mi_currentIndexes_.length; i++)
                {
                    //getting target Element
                    l_tempTourElement = (FLGStandardTourElement)this.m_currentTour.getElementAt(this.mi_currentIndexes_[i]);
                    l_tempTourElement.setDisplayTime(((Number)this.m_spinnerDisplayTime.getValue()).intValue());
                }//for
            }

            //Autoplay Options
            //never
            if(this.m_buttonAutoplayNever.isSelected())
            {
                for(int i=0; i < this.mi_currentIndexes_.length; i++)
                {
                    //getting target Element
                    l_tempTourElement = (FLGStandardTourElement)this.m_currentTour.getElementAt(this.mi_currentIndexes_[i]);
                    l_tempTourElement.setMediaPlaybackMode(FLGTourElement.MEDIA_AUTO_PLAYBACK_MODE_NEVER);
                }//for
            }
            //if timeflow enabled
            else if(this.m_buttonAutoplayIfTimeflow.isSelected())
            {
                for(int i=0; i < this.mi_currentIndexes_.length; i++)
                {
                    //getting target Element
                    l_tempTourElement = (FLGStandardTourElement)this.m_currentTour.getElementAt(this.mi_currentIndexes_[i]);
                    l_tempTourElement.setMediaPlaybackMode(FLGTourElement.MEDIA_AUTO_PLAYBACK_MODE_IF_TIMEFLOW_ENABLED);
                }//for
            }
            //always
            else if(this.m_buttonAutoplayAlways.isSelected())
            {
                for(int i=0; i < this.mi_currentIndexes_.length; i++)
                {
                    //getting target Element
                    l_tempTourElement = (FLGStandardTourElement)this.m_currentTour.getElementAt(this.mi_currentIndexes_[i]);
                    l_tempTourElement.setMediaPlaybackMode(FLGTourElement.MEDIA_AUTO_PLAYBACK_MODE_ALWAYS);
                }//for
            }//if..else

        }//else (multipleElements)

        this.m_currentTour.fireTourChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, this.m_currentTour.getSize()-1));

        //hide dialog
        this.m_panelMediaPlayer.removeAll();
        this.hide();

    }//setAndHide

    /**
     * Updates the information displayed about the associated media file
     */
    private void updateAssociatedMediaFileInfo()
    {
        String ls_selectedMedia = FLGStandardTourCreator.getInternationalization().getString("dialog.elementProperties.panelMedia.selectedMedia");

        if(this.mi_currentIndexes_.length != 1)
        {
            //not exactly one selected tour element
            this.m_labelSelectedMedia.setText(ls_selectedMedia + "-");
            this.m_buttonSelectMedia.setEnabled(false);
            this.m_buttonTestMedia.setEnabled(false);
        }else
        {
            File l_associatedFile = ((FLGStandardTourElement)this.m_currentTour.getElementAt(this.mi_currentIndexes_[0])).getAssociatedMediaFile();
            if(l_associatedFile != null)
            {
               this.m_labelSelectedMedia.setText(ls_selectedMedia + l_associatedFile.getName());
               this.m_buttonTestMedia.setEnabled(true);
            }else
            {
               this.m_labelSelectedMedia.setText(ls_selectedMedia + "-");
               this.m_buttonTestMedia.setEnabled(false);
            }
            this.m_buttonSelectMedia.setEnabled(true);
        }
    }//updateAssociatedMediaFileInfo

    // Variables declaration - do not modify
    private javax.swing.JRadioButton m_buttonDisplayTimeInfinite;
    private javax.swing.JPanel m_panelTabDisplay;
    private javax.swing.JTabbedPane m_tabbedPaneMain;
    private javax.swing.JPanel m_panelImagePanel;
    private javax.swing.JPanel m_panelMainPanel;
    private javax.swing.JRadioButton m_buttonAutoplayAlways;
    private javax.swing.JPanel m_panelGeneralInfoContent;
    private javax.swing.JTextArea m_textAreaDescription;
    private javax.swing.JLabel m_labelInfo;
    private javax.swing.JPanel m_panelDisplayTimeCustom;
    private javax.swing.JPanel m_panelDescription;
    private javax.swing.JLabel m_panelNameText;
    private javax.swing.JPanel m_panelTabInformation;
    private javax.swing.JTextField m_textFieldName;
    private javax.swing.JButton m_buttonSelectMedia;
    private javax.swing.JPanel m_panelGeneralInfo;
    private javax.swing.JPanel m_panelDisplayBehavior;
    private javax.swing.JPanel m_panelTabMedia;
    private javax.swing.JButton m_buttonOK;
    private javax.swing.JPanel m_panelButtonPanel;
    private javax.swing.JPanel m_panelMediaInfo;
    private javax.swing.JPanel m_panelGeneralInfoDescriptors;
    private javax.swing.JRadioButton m_buttonAutoplayIfTimeflow;
    private javax.swing.JButton m_buttonCancel;
    private javax.swing.JPanel m_panelDisplayTime;
    private javax.swing.JRadioButton m_buttonDisplayTimeCustom;
    private javax.swing.JCheckBox m_buttonDisplayNextElementIfEndOfMedia;
    private javax.swing.JLabel m_labelImage;
    private javax.swing.JLabel m_labelNumberText;
    private javax.swing.JPanel m_panelDisplayMediaAutoplay;
    private javax.swing.JScrollPane m_scrollPaneDescription;
    private javax.swing.JRadioButton m_buttonDisplayTimeHidden;
    private javax.swing.ButtonGroup m_buttonGroupDisplayTime;
    private javax.swing.JPanel m_panelMediaPlayer;
    private javax.swing.JPanel m_panelMediaButtons;
    private javax.swing.JButton m_buttonTestMedia;
    private javax.swing.ButtonGroup m_buttonGroupMediaAutoplay;
    private javax.swing.JLabel m_labelNumber;
    private javax.swing.JLabel m_labelSelectedMedia;
    private javax.swing.JRadioButton m_buttonAutoplayNever;
    private javax.swing.JCheckBox m_buttonWaitForEndOfMedia;
    private javax.swing.JPanel m_panelInfo;
    // End of variables declaration

}
