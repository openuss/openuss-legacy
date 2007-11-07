package freestyleLearningGroup.independent.tourCreator;

import freestyleLearningGroup.independent.gui.FLGImageUtility;
import freestyleLearningGroup.independent.gui.FLGTextButton3D;
import freestyleLearningGroup.independent.gui.FLGUIUtilities;

/**
 * This handy "About Frame" shows the current memory usage and will perform garbage collection if clicked on the icon ;-).
 * @author  Steffen Wachenfeld
 */
public class FLGAboutFrame extends javax.swing.JFrame {
    
    /*
     *private String ms_systemInfo = "<B>Systeminformationen:</B><BR>";
     *private java.util.TimerTask m_timerTask;
     *private java.util.Timer     m_timer;
     */
    
    /** Creates new form FLGAboutFrame */
    public FLGAboutFrame() {
        initComponents();
        
        // IconImage
        this.setIconImage(new javax.swing.ImageIcon(FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/tourCreatorIcon.gif"))).getImage());
        
        //Size and position
        java.awt.Dimension l_screenDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(500, 300);
        setLocation((int)(l_screenDimension.width - getWidth())/2, (int)(l_screenDimension.height - getHeight())/2);
    }//constructor

    private void initComponents() {//GEN-BEGIN:initComponents
        m_panelMainPanel = new freestyleLearningGroup.independent.gui.FLGEffectPanel("FSLMainFrameColor1", "FLGDialog.background", true);
        m_labelImageLabel = new javax.swing.JLabel();
        m_panelInformationPanel = new javax.swing.JPanel();
        m_labelTourCreatorInfo = new javax.swing.JLabel();
        m_buttonPanel = new freestyleLearningGroup.independent.gui.FLGEffectPanel("FLGDialog.background", true);
        m_buttonOK = new FLGTextButton3D(FLGStandardTourCreator.getInternationalization().getString("dialog.buttonText.ok"), FLGUIUtilities.BASE_COLOR4);

        setTitle(FLGStandardTourCreator.getInternationalization().getString("frame.aboutTourCreator.title"));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        m_panelMainPanel.setLayout(new java.awt.GridLayout(1, 2, 10, 0));

        m_labelImageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_labelImageLabel.setIcon(new javax.swing.ImageIcon(FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/tourCreator.gif"))));
        m_labelImageLabel.setToolTipText(FLGStandardTourCreator.getInternationalization().getString("button.collectGarbage.toolTipText"));
        m_labelImageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                m_labelImageLabelMouseClicked(evt);
            }
        });

        m_panelMainPanel.add(m_labelImageLabel);

        m_panelInformationPanel.setLayout(new java.awt.GridLayout(1, 1, 0, 10));

        m_panelInformationPanel.setForeground(new java.awt.Color(204, 204, 204));
        m_panelInformationPanel.setOpaque(false);
        m_labelTourCreatorInfo.setFont(new java.awt.Font("Dialog", 0, 12));
        m_labelTourCreatorInfo.setForeground(new java.awt.Color(50, 50, 140));
        m_labelTourCreatorInfo.setText("noTourCreatorInfo");
        m_labelTourCreatorInfo.setMaximumSize(new java.awt.Dimension(1024, 768));
        m_labelTourCreatorInfo.setMinimumSize(new java.awt.Dimension(200, 120));
        m_labelTourCreatorInfo.setPreferredSize(new java.awt.Dimension(200, 120));
        m_panelInformationPanel.add(m_labelTourCreatorInfo);

        m_panelMainPanel.add(m_panelInformationPanel);

        getContentPane().add(m_panelMainPanel, java.awt.BorderLayout.CENTER);

        m_buttonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_buttonOKActionPerformed(evt);
            }
        });

        m_buttonPanel.add(m_buttonOK);

        getContentPane().add(m_buttonPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }//GEN-END:initComponents

    private void m_buttonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_buttonOKActionPerformed
        this.exitForm(null);
    }//GEN-LAST:event_m_buttonOKActionPerformed
    private void m_labelImageLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_m_labelImageLabelMouseClicked
        java.lang.Runtime.getRuntime().gc();
        System.out.println("Invoking garbage collection!");
    }//GEN-LAST:event_m_labelImageLabelMouseClicked
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        //stop actualization
        //this.m_timer.cancel();
        
        this.hide();
    }//GEN-LAST:event_exitForm
    
    private void refresh()
    {
        /*String ls_systemInfo = "<html>" + this.ms_systemInfo;
        ls_systemInfo += "Freier Speicher: " + java.lang.Runtime.getRuntime().freeMemory()/1024 + " KB<BR>";
        ls_systemInfo += "Reservierter Speicher: " + java.lang.Runtime.getRuntime().totalMemory()/1024 + "KB<BR>";
        ls_systemInfo += "Reservierbarer Speicher: " + java.lang.Runtime.getRuntime().maxMemory()/1024 + "KB";
        ls_systemInfo += "</html>";
        this.m_labelSystemInfo.setText(ls_systemInfo);*/
        
        /* About INFO*/
        String ls_aboutInfo = "<html><b>Tour Creator 1.0 & Tour Viewer 1.0</b><br>"
                            + "<br>"
                            + " Steffen Wachenfeld (in 2003)<br>"
                            + "</html>";
        this.m_labelTourCreatorInfo.setText(ls_aboutInfo);
    }//refresh
    
    
    
    
    public void refreshAndShow()
    {
        //start timer
        //m_timer = new java.util.Timer();
        //this.m_timer.scheduleAtFixedRate(this.m_timerTask, 0, 500);
        
        //show
        this.refresh();
        this.show();
    }//refreshAndShow
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton m_buttonOK;
    private javax.swing.JPanel m_buttonPanel;
    private javax.swing.JLabel m_labelImageLabel;
    private javax.swing.JLabel m_labelTourCreatorInfo;
    private javax.swing.JPanel m_panelInformationPanel;
    private javax.swing.JPanel m_panelMainPanel;
    // End of variables declaration//GEN-END:variables
    
}
