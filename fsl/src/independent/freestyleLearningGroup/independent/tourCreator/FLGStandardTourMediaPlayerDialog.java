package freestyleLearningGroup.independent.tourCreator;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.media.*;
import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import freestyleLearning.learningUnitViewAPI.elementInteractionPanel.*;
import freestyleLearningGroup.independent.gui.FLGImageUtility;
import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.media.FLGMediaException;

/**
 *
 * @author Steffen Wachenfeld
 */
public class FLGStandardTourMediaPlayerDialog extends javax.swing.JDialog implements ControllerListener {
    
    //Dialog name
    private String ms_dialogName = "Media Player";
    
    //current Media File
    private File m_currentMediaFile;
    
    //Player
    private Player m_player;
    private Container m_targetContainer = null;
    private Component m_visual = null;
    private Component m_control = null;
    private int mi_videoWidth = 0;
    private int mi_videoHeight = 0;
    private int mi_controlHeight = 30;
    private int mi_insetWidth = 10;
    private int mi_insetHeight = 30;
    
    //buttons
    private javax.swing.AbstractButton m_buttonPlayMedia;
    private javax.swing.AbstractButton m_buttonPauseMedia;
    private javax.swing.AbstractButton m_buttonStopMedia;
    
    /** Creates new form FLGStandardTourMediaPlayerDialog */
    public FLGStandardTourMediaPlayerDialog(java.awt.Frame a_parent) {
        super(a_parent, false);
        initComponents();
        
        /*******************************************************************************************
         * buttons
         ******************************************************************************************
        
        //button: playMedia
        java.awt.Image l_playMediaButtonImage = FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/buttonPlay.gif"));
        m_buttonPlayMedia = new FSLLearningUnitViewElementInteractionButton(l_playMediaButtonImage);
        m_buttonPlayMedia.setToolTipText(FLGStandardTourCreator.getInternationalization().getString("button.playMedia.toolTipText"));
        m_buttonPlayMedia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //DEBUG
                System.out.println("play button pressed");
                //disable time flow
                //play Media file
                startMediaPlayback();
            }
        });
        this.m_panelControlButtons.add(m_buttonPlayMedia);

        //button: pauseMedia
        java.awt.Image l_pauseMediaButtonImage = FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/buttonPause.gif"));
        m_buttonPauseMedia = new FSLLearningUnitViewElementInteractionButton(l_pauseMediaButtonImage);
        m_buttonPauseMedia.setToolTipText(FLGStandardTourCreator.getInternationalization().getString("button.pauseMedia.toolTipText"));
        m_buttonPauseMedia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //DEBUG
                System.out.println("pause button pressed");
                //disable time flow
                //pause media
                pauseMediaPlayback();
            }
        });
        this.m_panelControlButtons.add(m_buttonPauseMedia);
        
        //button: stopMedia
        java.awt.Image l_stopMediaButtonImage = FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/buttonStop.gif"));
        m_buttonStopMedia = new FSLLearningUnitViewElementInteractionButton(l_stopMediaButtonImage);
        m_buttonStopMedia.setToolTipText(FLGStandardTourCreator.getInternationalization().getString("button.stopMedia.toolTipText"));
        m_buttonStopMedia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //DEBUG
                System.out.println("stop button pressed");
                //disable time flow
                //stop media
                stopMediaPlayback();
            }
        });
        this.m_panelControlButtons.add(m_buttonStopMedia);
        */
        
        
               
        //Size and position
        //java.awt.Dimension l_screenDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        //this.setSize(200, 150);
        pack();
        this.setSize(this.getPreferredSize());
    }
    private void initComponents() {//GEN-BEGIN:initComponents

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        pack();
    }//GEN-END:initComponents
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        
    }//GEN-LAST:event_closeDialog

    /** 
     * Method to react on player events. If the players prefetching process has
     * finished a new <CODE>Component</CODE> is created containing a visual component
     * for displaying the video and a control panel component for conrolling the player
     * if these are not already existing. On the end of the video the player is set to
     * the beginning and is started again. "player.start()" is uncommented to provide
     * the player from starting automatically.
     * @param ce An event fired by the player
     */  
    public void controllerUpdate(ControllerEvent a_ce)
    {

        if (a_ce instanceof RealizeCompleteEvent) {
          //DEBUG
          System.out.println("RealizeCompleteEventReceived");
          m_player.prefetch();
        }

        else if (a_ce instanceof PrefetchCompleteEvent)
        {
           //DEBUG
           System.out.println("PrefetchCompleteEventReceived");

           if(m_targetContainer != null)
           {
              m_targetContainer.removeAll();

              if ((m_visual = m_player.getVisualComponent()) != null) {
                 Dimension l_size = m_visual.getPreferredSize();
                 mi_videoWidth = l_size.width;
                 mi_videoHeight = l_size.height;
                 //m_visual.setSize(mi_videoWidth, mi_videoHeight);
                 m_targetContainer.add("Center", m_visual);
              }
              else
              {
                 mi_videoWidth = 320;
                 mi_videoHeight = 0;
              }//if..else

              if ((m_control = m_player.getControlPanelComponent()) != null) {
                mi_controlHeight = m_control.getPreferredSize().height;
                m_control.setSize(m_control.getPreferredSize().width, mi_controlHeight);
                m_targetContainer.add("North", m_control);
              }
              else
                mi_controlHeight = 0;  

              //in any case
              m_targetContainer.validate();
           }//if (m_targetContainer != null)
           else
           {

              if ((m_visual = m_player.getVisualComponent()) != null)
              {
                Dimension l_size = m_visual.getPreferredSize();
                mi_videoWidth = l_size.width;
                mi_videoHeight = l_size.height;
                if(!this.getContentPane().isAncestorOf(m_visual))
                  this.getContentPane().add("Center", m_visual);
              }
              else
              {
                mi_videoWidth = 320;
                mi_videoHeight = 0;
              }//if..else

              if ((m_control = m_player.getControlPanelComponent()) != null)
              {
                 mi_controlHeight = m_control.getPreferredSize().height;
                 if( !this.getContentPane().isAncestorOf(m_control))
                   this.getContentPane().add("North", m_control);
              }
          }//if..else (m_targetContainer != null)

          this.updateTitle(); 
          this.setSize(mi_videoWidth + mi_insetWidth, mi_videoHeight + mi_controlHeight + mi_insetHeight);
          this.validate();
        }//if prefetchComplete 

        else if (a_ce instanceof EndOfMediaEvent)
        {
          //DEBUG
          System.out.println("EndOfMediaEventReceived");
          m_player.setMediaTime(new Time(0));
          //player.start();
        }
    }//controllerUpdate
  
  /**
   * Stops the media playback without changing the MediaTime.
   */
  public void pauseMediaPlayback()
  {
     if(m_player != null)
     {
        m_player.stop();
     }
  }//pauseMediaPlayback

    
    /**
     * Starts the playback from the current position.
     */
    public void startMediaPlayback()
    {
        //check if Media was successfully loaded
        if(m_player != null)
        {
            //DEBUG
            System.out.println("Player.start()...");
            m_player.start();
        }//if
    }//startMediaPlayback
    
    
    /**
     * Stops the media playback and sets the MediaTime to zero.
     */
    public void stopMediaPlayback()
    {
        //stop playback (implicitly sets position to zero)
        if ( (m_player != null) 
          && (m_player.getState() == Controller.Started) )
        {
            m_player.stop();
            m_player.setMediaTime(new Time(0));
        }//if
    }//stopMediaPlayback
    
    
    /**
     * Sets the media File of this player Dialog
     */
    public void setMediaFile(File a_mediaFile, Container a_targetContainer)
    {
        //stop old Player 
        this.stopMediaPlayback();
        this.m_targetContainer = a_targetContainer;
        
        this.m_currentMediaFile = a_mediaFile;

        //Create a new player for the given File
        try
        {
            //destroy old player if existent
            if(m_player != null)
            {
                m_player.removeControllerListener(this);
                m_player.close();
                m_player = null;
            }//if
            //this.getContentPane().remove(m_control);
            //this.getContentPane().remove(m_visual);
            if(a_mediaFile != null)
            {
                m_player = Manager.createPlayer(a_mediaFile.toURL());
                m_player.addControllerListener(this);
                m_player.realize();
            }//if
        }
        catch (NoPlayerException e)
        {
            FLGStandardTourCreator.showErrorDialog("Error creating player", 
                                                   "Unable to create a player for the given object " + e.getMessage());
            e.printStackTrace();
        }
        catch (IOException e)
        {
            FLGStandardTourCreator.showErrorDialog("Error loading media file", 
                                                   "Unable to connect the player to the given object " + e.getMessage());
            e.printStackTrace();
        }//try..catch

        //update Title
        updateTitle();
        
    }//setMediaFile
    
    
    /**
     * Updates/Refreshes the Dialog title, displaying the name of the currently loaded Media File.
     */
    private void updateTitle()
    {
        String ls_newTitle = this.ms_dialogName;
        if(this.m_currentMediaFile == null)
        {
            //if no Media
            ls_newTitle += " - no media selected";
        }else
        {
            //display Media Filename
            ls_newTitle += " - " + this.m_currentMediaFile.getName();
            //if load media succeeded, display Media Length
            if(   m_player != null 
               && (m_player.getState() == Controller.Started || m_player.getState() == Controller.Prefetched))
            {
                ls_newTitle += " (" + (m_player.getDuration().getNanoseconds()/1000000000) + "s)";
            }   
        }
        this.setTitle(ls_newTitle);
    }//updateTitle
    
    
  
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
