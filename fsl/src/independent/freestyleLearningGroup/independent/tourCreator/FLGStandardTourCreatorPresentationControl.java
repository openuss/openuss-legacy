package freestyleLearningGroup.independent.tourCreator;


import freestyleLearning.learningUnitViewAPI.elementInteractionPanel.*;
import freestyleLearningGroup.independent.gui.FLGImageUtility;

/**
 * This is the control frame to present tours. Either this or the TourCreatorFrame will be visible.
 * @author  Steffen Wachenfeld
 */
public class FLGStandardTourCreatorPresentationControl extends javax.swing.JFrame implements javax.swing.event.ListDataListener{
    
    private FLGStandardTourCreator m_tourCreator = null;
    private FLGStandardTour        m_currentTour = null;
    private boolean mb_showCurrentElementPanel = true;
    private String ms_windowTitle = "Tour Viewer 1.0";
    private int mi_tempSliderPosition = 0;
    
    //for time flow
    private boolean mb_timeFlowEnabled = true;
    private int mi_remainingElementTime = 0;
    private java.util.Timer     m_timerDecrement;
    private java.util.Timer     m_timerTrigger;
    
    //buttons
    javax.swing.AbstractButton m_buttonMoveSliderToZero;
    javax.swing.AbstractButton m_buttonMoveSliderToPrevious;
    javax.swing.AbstractButton m_buttonMoveSliderToNext;
    freestyleLearningGroup.independent.gui.FLGAbstractImageButton m_buttonTimeFlow;
    javax.swing.AbstractButton m_buttonShowCurrentElementPanel;
    javax.swing.AbstractButton m_buttonShowMediaPlayerDialog;
    java.awt.Image m_disableTimeFlowButtonImage;
    java.awt.Image m_enableTimeFlowButtonImage;
    
    private class TimerTaskDecrement extends java.util.TimerTask{
        public void run(){
            mi_remainingElementTime--;
            updateTitle();
        }//run
    }
    
    private class TimerTaskTrigger extends java.util.TimerTask{
        public void run(){
            //stop decrementing
            m_timerDecrement.cancel();
            
            //to zero if last element
            if(m_slider.getValue() == m_slider.getMaximum()) {
                //move to zero
                m_slider.setValue(0);
            }
            else {
                //display next
                int li_sliderPos = m_slider.getValue();
                
                if(li_sliderPos == m_slider.getMaximum()) {
                    m_slider.setValue(0);
                }
                else {
                    //else next
                    m_slider.setValue(li_sliderPos + 1);
                    //display if not hidden
                    if(((FLGStandardTourElement)m_currentTour.getElementAt(li_sliderPos)).getDisplayTime() != FLGStandardTourElement.HIDDEN_DISPLAY_TIME)
                        m_tourCreator.displayElement(li_sliderPos);
                }
                triggerTimerControl();
            }
        }//run
    }//class TimerTaskTrigger
    
    
    /** Creates new form FLGStandardTourCreatorPresentationControl */
    public FLGStandardTourCreatorPresentationControl(FLGStandardTourCreator a_tourCreator, FLGStandardTour a_tour) {
        initComponents();
        
        /**
         * IconImage
         */
        this.setIconImage(new javax.swing.ImageIcon(FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/tourCreatorIcon.gif"))).getImage());
        
        //remember reference to owning tour creator
        this.m_tourCreator = a_tourCreator;
        this.m_currentTour = a_tour;
        
        /*******************************************************************************************
         * buttons
         ******************************************************************************************/
        
        //moveSliderToZero
        java.awt.Image l_moveSliderToZeroButtonImage = FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/back.gif"));
        m_buttonMoveSliderToZero = new FSLLearningUnitViewElementInteractionButton(l_moveSliderToZeroButtonImage);
        m_buttonMoveSliderToZero.setToolTipText(FLGStandardTourCreator.getInternationalization().getString("button.moveSliderToZero.toolTipText"));
        m_buttonMoveSliderToZero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopTimer();
                m_slider.setValue(0);
            }
        });
        this.m_panelControlButtons.add(m_buttonMoveSliderToZero);
        
        //moveSliderToPrevious
        java.awt.Image l_moveSliderToPreviousButtonImage = FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/previous.gif"));
        m_buttonMoveSliderToPrevious = new FSLLearningUnitViewElementInteractionButton(l_moveSliderToPreviousButtonImage);
        m_buttonMoveSliderToPrevious.setToolTipText(FLGStandardTourCreator.getInternationalization().getString("button.moveSliderToPrevious.toolTipText"));
        m_buttonMoveSliderToPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopTimer();
                m_slider.setValue(m_slider.getValue() - 1);
                if(m_slider.getValue() > 0) {
                    triggerTimerControl();
                    m_tourCreator.displayElement(m_slider.getValue() - 1);
                }
            }
        });
        this.m_panelControlButtons.add(m_buttonMoveSliderToPrevious);
        
        //button: moveSliderToNext
        java.awt.Image l_moveSliderToNextButtonImage = FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/next.gif"));
        m_buttonMoveSliderToNext = new FSLLearningUnitViewElementInteractionButton(l_moveSliderToNextButtonImage);
        m_buttonMoveSliderToNext.setToolTipText(FLGStandardTourCreator.getInternationalization().getString("button.moveSliderToNext.toolTipText"));
        m_buttonMoveSliderToNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopTimer();
                if(m_slider.getValue() < m_slider.getMaximum()) {
                    m_slider.setValue(m_slider.getValue() + 1);
                    //check for the case no elements exist!!
                    if(m_slider.getValue() > 0) {
                        triggerTimerControl();
                        m_tourCreator.displayElement(m_slider.getValue() - 1);
                    }
                }//if not yet last
            }
        });
        this.m_panelControlButtons.add(m_buttonMoveSliderToNext);
        
        
        //button: TimeFlow
        m_disableTimeFlowButtonImage = FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/disableTimeflow.gif"));
        m_enableTimeFlowButtonImage = FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/enableTimeflow.gif"));
        m_buttonTimeFlow = new FSLLearningUnitViewElementInteractionButton(m_disableTimeFlowButtonImage);
        m_buttonTimeFlow.setToolTipText(FLGStandardTourCreator.getInternationalization().getString("button.disableTimeFLow.toolTipText"));
        m_buttonTimeFlow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(mb_timeFlowEnabled)
                    disableTimeFlow();
                else {
                    enableTimeFlow();
                    triggerTimerControl();
                }
            }//actionPerformed
        });
        this.m_panelControlButtons.add(m_buttonTimeFlow);
        
        
        //button: hide/show Panel
        java.awt.Image l_showCurrentElementPanelButtonImage = FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/buttonShowPanel.gif"));
        m_buttonShowCurrentElementPanel = new FSLLearningUnitViewElementInteractionButton(l_showCurrentElementPanelButtonImage);
        this.m_buttonShowCurrentElementPanel.setToolTipText(FLGStandardTourCreator.getInternationalization().getString("button.hideCurrentElementPanel.toolTipText"));
        
        m_buttonShowCurrentElementPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_buttonShowCurrentElementPanelActionPerformed(evt);
            }
        });
        this.m_panelControlButtons.add(m_buttonShowCurrentElementPanel);
        
        
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
        this.m_panelControlButtons.add(m_buttonShowMediaPlayerDialog);
        
        
        //Size and position
        java.awt.Dimension l_screenDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        // width: panelwidth + 2 * (10insets + 3 border), height dependent on showPanel
        this.setSize(FLGStandardTourListCellRenderer.DEFAULT_PANEL_DIMENSION.width + 26, ((mb_showCurrentElementPanel)? 210 : 140));
        setLocation((int)(l_screenDimension.width - getWidth())/2, (int)(l_screenDimension.height - getHeight())/2);
        //System.out.println("Leaving StandardTourCreatorPresentationControl constructor");
    }//constructor
    
    
    private void initComponents() {
        m_popupMenuElementPanel = new javax.swing.JPopupMenu();
        m_menuItemProperties = new javax.swing.JMenuItem();
        m_panelControlButtons = new freestyleLearningGroup.independent.gui.FLGEffectPanel("FSLMainFrameColor1", "FSLMainFrameColor3", true);
        m_panelSlider = new freestyleLearningGroup.independent.gui.FLGEffectPanel("FSLMainFrameColor3", true);
        m_slider = new javax.swing.JSlider();
        m_panelCurrentElement = new freestyleLearningGroup.independent.gui.FLGEffectPanel("FSLMainFrameColor3", true);
        
        m_menuItemProperties.setText(FLGStandardTourCreator.getInternationalization().getString("menu.edit.properties") + "...");
        m_menuItemProperties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_menuItemPropertiesActionPerformed(evt);
            }
        });
        
        m_popupMenuElementPanel.add(m_menuItemProperties);
        
        setTitle(this.ms_windowTitle);
        setName("TourViewer 1.0");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        
        m_panelControlButtons.setPreferredSize(new java.awt.Dimension(320, 45));
        getContentPane().add(m_panelControlButtons, java.awt.BorderLayout.NORTH);
        
        m_panelSlider.setLayout(new java.awt.BorderLayout());
        
        m_panelSlider.setMinimumSize(FLGStandardTourListCellRenderer.DEFAULT_PANEL_DIMENSION);
        m_panelSlider.setPreferredSize(FLGStandardTourListCellRenderer.DEFAULT_PANEL_DIMENSION);
        m_panelSlider.setOpaque(false);
        m_slider.setBackground((java.awt.Color)javax.swing.UIManager.get("FSLMainFrameColor3"));
        m_slider.setMajorTickSpacing(5);
        m_slider.setMaximum(10);
        m_slider.setMinorTickSpacing(1);
        m_slider.setPaintLabels(true);
        m_slider.setPaintTicks(true);
        m_slider.setSnapToTicks(true);
        m_slider.setValue(0);
        m_slider.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(0, 10, 10, 10)));
        m_slider.setMaximumSize(FLGStandardTourListCellRenderer.DEFAULT_PANEL_DIMENSION);
        m_slider.setMinimumSize(FLGStandardTourListCellRenderer.DEFAULT_PANEL_DIMENSION);
        m_slider.setPreferredSize(FLGStandardTourListCellRenderer.DEFAULT_PANEL_DIMENSION);
        m_slider.setRequestFocusEnabled(false);
        m_slider.setOpaque(false);
        m_slider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                m_sliderStateChanged(evt);
            }
        });
        
        m_slider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                m_sliderMouseReleased(evt);
            }
        });
        
        m_panelSlider.add(m_slider, java.awt.BorderLayout.CENTER);
        
        getContentPane().add(m_panelSlider, java.awt.BorderLayout.CENTER);
        
        m_panelCurrentElement.setLayout(new java.awt.BorderLayout());
        
        m_panelCurrentElement.setMinimumSize(FLGStandardTourListCellRenderer.DEFAULT_PANEL_DIMENSION);
        m_panelCurrentElement.setPreferredSize(FLGStandardTourListCellRenderer.DEFAULT_PANEL_DIMENSION);
        m_panelCurrentElement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                m_panelCurrentElementMousePressed(evt);
            }
        });
        getContentPane().add(m_panelCurrentElement, java.awt.BorderLayout.SOUTH);    
        pack();
    }
    
    private void m_menuItemPropertiesActionPerformed(java.awt.event.ActionEvent evt) {
        // Display properties curent element if right mouse click
        if(this.m_slider.getValue() > 0) {
            int[] li_tempArray_ = {this.m_slider.getValue()-1};
            this.m_tourCreator.getPropertiesDialog().refreshAndShow(this.m_currentTour, li_tempArray_);
        }//if
        else {
            //zero panel clicked -> start tour?
        }
    }
    
    private void m_panelCurrentElementMousePressed(java.awt.event.MouseEvent evt) {
        //non-left click -> show popup Menu
        if(
        //check non-leftButtons
        evt.getButton() != evt.BUTTON1 ||
        //check ALT+leftButton (for MAC users)
        ( evt.getButton() == evt.BUTTON1 && ((evt.getModifiersEx() & evt.ALT_DOWN_MASK) != 0) )
        ) {
            //show popup Menu
            if( this.m_slider.getValue() > 0 ) {
                disableTimeFlow();
                this.m_popupMenuElementPanel.show(evt.getComponent(), evt.getX(), evt.getY());
            }//if not zero panel
        }
        else
            //left click -> directly to properties
        {
            disableTimeFlow();
            m_menuItemPropertiesActionPerformed(null);
        }
    }
    
    private void m_sliderMouseReleased(java.awt.event.MouseEvent evt) {
        stopTimer();
        //in case that slider value == zero methods will handle that ;-)
        this.triggerTimerControl();
        this.m_tourCreator.displayElement(this.m_slider.getValue() - 1);
    }
    
    private void m_buttonShowCurrentElementPanelActionPerformed(java.awt.event.ActionEvent evt) {
        //if showing remove
        if(mb_showCurrentElementPanel) {
            this.getContentPane().remove(m_panelCurrentElement);
            this.getContentPane().validate();
            this.m_buttonShowCurrentElementPanel.setToolTipText(FLGStandardTourCreator.getInternationalization().getString("button.showCurrentElementPanel.toolTipText"));
            this.mb_showCurrentElementPanel = false;
        }
        else {//else show
            this.mb_showCurrentElementPanel = true;
            this.m_sliderStateChanged(null);
            this.m_buttonShowCurrentElementPanel.setToolTipText(FLGStandardTourCreator.getInternationalization().getString("button.hideCurrentElementPanel.toolTipText"));
            this.getContentPane().add(m_panelCurrentElement, java.awt.BorderLayout.SOUTH);
            this.getContentPane().validate();
        }       
        pack();
    }
    
    private void m_sliderStateChanged(javax.swing.event.ChangeEvent evt) {
        int li_newSliderPosition = this.m_slider.getValue();
        
        //set/reset display Time
        if(li_newSliderPosition > 0)
            this.mi_remainingElementTime = ((FLGStandardTourElement)this.m_currentTour.getElementAt(li_newSliderPosition-1)).getDisplayTime();
        else
            this.mi_remainingElementTime = 0;
        this.updateTitle();
        this.m_buttonShowMediaPlayerDialog.setEnabled(li_newSliderPosition-1 > 0 && 
            ((FLGStandardTourElement)this.m_currentTour.getElementAt(li_newSliderPosition-1)).getAssociatedMediaFile() != null);
            
        //perhaps play sound
        if(li_newSliderPosition != this.mi_tempSliderPosition) {
            this.mi_tempSliderPosition = li_newSliderPosition;
            
            /**
            if(this.m_tourCreator.m_currentTourCreatorOptions.mb_playSoundSliderMove) {
                this.m_tourCreator.playSound(m_tourCreator.m_soundSliderMove);
            }//if
            **/
        }//if
        
        //update panel if visible
        if(this.mb_showCurrentElementPanel) {
            // if slider =0 a pseudo Panel will be generated
            if( (li_newSliderPosition >= 0) && (li_newSliderPosition <= this.m_currentTour.getSize())) {
                //don't worry, just one Panel will added ;-)
                javax.swing.JPanel l_tempPanel = this.m_tourCreator.getElementPanel(li_newSliderPosition -1);
                l_tempPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(10, 10, 10, 10)));
                this.m_panelCurrentElement.removeAll();
                this.m_panelCurrentElement.add(l_tempPanel, java.awt.BorderLayout.CENTER);
            }//if
            
        }//if panel visible
    }
    
    private void exitForm(java.awt.event.WindowEvent evt) {
        //show tour creator (will automatically close presentation control
        this.m_tourCreator.leavePresentationMode();
    }
    
    /**
     * Disables the timeFlow. The next element will not be displayed automatically.
     */
    public void disableTimeFlow() {
        //return if already disabled
        if(!this.mb_timeFlowEnabled)
            return;
        
        //stop thread/timer
        if(this.m_timerTrigger!=null && this.m_timerDecrement != null) {
            this.m_timerTrigger.cancel();
            this.m_timerDecrement.cancel();
        }
        
        //set status
        this.mb_timeFlowEnabled = false;
        
        //change button
        m_buttonTimeFlow.setImage(m_enableTimeFlowButtonImage);
        m_buttonTimeFlow.setToolTipText(FLGStandardTourCreator.getInternationalization().getString("button.enableTimeFLow.toolTipText"));
        m_buttonTimeFlow.repaint();
        
        
    }//disableTimeFlow
    
    
    /**
     * Enables the timeFlow. The next element will be displayed automatically.
     */
    public void enableTimeFlow() {
        //return if already enabled
        if(this.mb_timeFlowEnabled)
            return;
        
        //set status
        this.mb_timeFlowEnabled = true;
        
        //change button
        m_buttonTimeFlow.setImage(m_disableTimeFlowButtonImage);
        m_buttonTimeFlow.setToolTipText(FLGStandardTourCreator.getInternationalization().getString("button.disableTimeFLow.toolTipText"));
        m_buttonTimeFlow.repaint();
        
        //update title
        if(this.m_slider.getValue() > 0)
            this.mi_remainingElementTime = ((FLGStandardTourElement)this.m_currentTour.getElementAt(this.m_slider.getValue()-1)).getDisplayTime();
        else
            this.mi_remainingElementTime = 0;
        this.updateTitle();
        
        
    }//enableTimeFlow
    
    
    /**
     * Returns the timeflow status.
     * @return true if timeflow enabled, else false.
     */
    public boolean getTimeFLowEnabled() {
        return this.mb_timeFlowEnabled;
    }//getTimeFlowEnabled
    
    
    /**
     * This method should only be called by a tour creator switching to noon-presentation mode or by someone who knows, what he is doing.
     */
    public void leavePresentationMode() {
        //disable time flow
        stopTimer();
        this.disableTimeFlow();
        
        //hide window
        this.hide();
    }//leavePresentationMode
    
    
    
    private void refresh() {
        //getListSize
        int li_listSize = this.m_currentTour.getSize();
        
        //update slider
        if(this.m_slider.getMaximum() != li_listSize) {
            this.m_slider.setMaximum(li_listSize);
            
            //labels for slider (Integer, JComponent)
            java.util.Hashtable l_tempLabels = new java.util.Hashtable();
            l_tempLabels.put(new Integer(li_listSize), new javax.swing.JLabel("" + li_listSize));
            //inner labels 0..
            int i = 0;
            do{
                l_tempLabels.put(new Integer(i), new javax.swing.JLabel("" + i));
                i += 5 * ((li_listSize/50)+1);
            }while(i <= li_listSize - 3*((li_listSize/50)+1));
            
            this.m_slider.setLabelTable(l_tempLabels);
        }//if
        
        //update panel
        this.m_sliderStateChanged(null);
        
        //update tourname/title
        this.updateTitle();
        
    }//refresh
    
    
    public void refreshAndShow(FLGStandardTour a_tour) {
        this.m_currentTour = a_tour;
        this.refresh();
        
        this.show();
    }//refrehAndShow
    
    
    private void stopTimer() {
        if(this.m_timerTrigger!=null)
            this.m_timerTrigger.cancel();
        if(this.m_timerDecrement!=null)
            this.m_timerDecrement.cancel();
    }//stopTimer
    
    
    
    private void triggerTimerControl() {
        //DEBUG
        //System.out.println("timerControl triggered");
        
        if(!this.mb_timeFlowEnabled || this.m_slider.getValue()==0)
            return;
        
        if(this.mi_remainingElementTime > 0) {
            this.stopTimer();
            this.m_timerTrigger = new java.util.Timer();
            this.m_timerTrigger.schedule(new TimerTaskTrigger(), this.mi_remainingElementTime * 1000);
            this.m_timerDecrement = new java.util.Timer();
            this.m_timerDecrement.scheduleAtFixedRate(new TimerTaskDecrement(), 990, 1000);
        }
        else if(this.mi_remainingElementTime == FLGStandardTourElement.HIDDEN_DISPLAY_TIME) {
            //next element
            //if current element is last element -> set to zero
            int li_sliderPos = m_slider.getValue();
            if(li_sliderPos == this.m_slider.getMaximum()) {
                this.m_slider.setValue(0);
            }
            else {
                //else next
                m_slider.setValue(li_sliderPos + 1);
                if(((FLGStandardTourElement)m_currentTour.getElementAt(li_sliderPos)).getDisplayTime() != FLGStandardTourElement.HIDDEN_DISPLAY_TIME)
                    m_tourCreator.displayElement(li_sliderPos);
                this.triggerTimerControl();
            }
        }//hidden
        
    }//triggerTimerControl
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new FLGStandardTourCreatorPresentationControl(null, null).show();
    }
    
    public void contentsChanged(javax.swing.event.ListDataEvent listDataEvent) {
        this.refresh();
    }
    
    public void intervalAdded(javax.swing.event.ListDataEvent listDataEvent) {
        this.refresh();
    }
    
    public void intervalRemoved(javax.swing.event.ListDataEvent listDataEvent) {
        this.refresh();
    }
    
    /**
     * This method updates the Frame title and displays the current tour name.
     * If the currently opened tour is modified an additional * is displayed
     */
    public void updateTitle(){
        String ls_title = this.ms_windowTitle + " - " + this.m_currentTour.getTourName();
        if(this.m_tourCreator.getTourWasModified()) ls_title += "*";
        
        //display element seconds if time flow enabled and non zero element active
        if(this.mb_timeFlowEnabled && (this.m_slider.getValue() > 0)) {
            //display seconds of current element
            if(this.mi_remainingElementTime >= 0)
                ls_title += " (" + this.mi_remainingElementTime + "s)";
        }
        else if(this.m_slider.getValue() == 0) {
            //display total display time
            int li_tempTourTime = this.m_tourCreator.getTotalTourDisplayTime();
            if(li_tempTourTime == FLGStandardTourElement.HIDDEN_DISPLAY_TIME)
                ls_title += " (Total: 0.0s)";
            else if(li_tempTourTime == FLGStandardTourElement.UNLIMITED_DISPLAY_TIME)
                ls_title += " (Total: infinite)";
            else
                ls_title += " (Total: " + li_tempTourTime + "s)";
            
        }
        
        this.setTitle(ls_title);
    }//updateJFrameTitle
    
    // Variables declaration - do not modify
    private javax.swing.JMenuItem m_menuItemProperties;
    private javax.swing.JPanel m_panelControlButtons;
    private javax.swing.JSlider m_slider;
    private javax.swing.JPanel m_panelSlider;
    private javax.swing.JPopupMenu m_popupMenuElementPanel;
    private javax.swing.JPanel m_panelCurrentElement;
    // End of variables declaration
    
}
