package freestyleLearningGroup.independent.tourCreator;

import freestyleLearning.learningUnitViewAPI.*;
/**
 * Dummy Frame to emulate FSL
 * @author  Steffen Wachenfeld
 */
public class DummyMainFrame extends javax.swing.JFrame implements FLG2TourCreatorInteractor {
    
    static FLGTourCreator m_tourCreator;
    
    /** 
     * Creates new form testMainFrame
     */
    public DummyMainFrame() {
        initComponents();
        m_tourCreator = FLGStandardTourCreator.getInstance();
        if( m_tourCreator.registerFLG2TourCreatorInteractor(this)==m_tourCreator.REGISTRATION_SUCCESS )
            System.out.println("Registration of dummyMainFrame as FSL2TourCreatorInteractor was SUCCESSFUL :o)");
        else
            System.out.println("Registration of dummyMainFrame as FSL2TourCreatorInteractor FAILED :o(");
        this.show();
    }

    private void initComponents() {//GEN-BEGIN:initComponents
        jPanel1 = new javax.swing.JPanel();
        tf_unitId = new javax.swing.JTextField();
        tf_unitViewElementId = new javax.swing.JTextField();
        tf_unitViewManagerId = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DummyFSLMainFrame");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        tf_unitId.setText("unitId");
        tf_unitId.setMinimumSize(new java.awt.Dimension(4, 100));
        tf_unitId.setPreferredSize(new java.awt.Dimension(150, 20));
        jPanel1.add(tf_unitId);

        tf_unitViewElementId.setText("unitViewElementId");
        tf_unitViewElementId.setPreferredSize(new java.awt.Dimension(150, 20));
        jPanel1.add(tf_unitViewElementId);

        tf_unitViewManagerId.setText("unitViewManagerId");
        tf_unitViewManagerId.setPreferredSize(new java.awt.Dimension(150, 20));
        jPanel1.add(tf_unitViewManagerId);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jButton1.setText("Start Tour Creator\n");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel2.add(jButton1);

        jButton2.setText("capture");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel2.add(jButton2);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        pack();
    }//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         this.m_tourCreator.performCaptureAction(evt);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ((FLGStandardTourCreator)this.m_tourCreator).refreshAndShow();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    /**
     * Main method for testing the StandardTourCreator.
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new DummyMainFrame();
    }
    
    public void displayFLG2TourCreatorElementInformation(FLG2TourCreatorElementInformation target) {
        this.tf_unitId.setText(target.getTargetLearningUnitId());
        this.tf_unitViewElementId.setText(target.getTargetLearningUnitViewElementId());
        this.tf_unitViewManagerId.setText(target.getTargetLearningUnitViewManagerId());
    }    
    
    public FLG2TourCreatorElementInformation getCurrentFLG2TourCreatorElementInformation() {
        FLG2TourCreatorElementInformation l_elementInformation = new FLG2TourCreatorStandardElementInformation(this.tf_unitId.getText(), this.tf_unitViewElementId.getText(), this.tf_unitViewManagerId.getText());
        l_elementInformation.setElementImage(java.awt.Toolkit.getDefaultToolkit().createImage("c://AmyWeber.jpg"));
        return l_elementInformation;
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton1;
    private javax.swing.JTextField tf_unitId;
    private javax.swing.JTextField tf_unitViewElementId;
    private javax.swing.JTextField tf_unitViewManagerId;
    // End of variables declaration//GEN-END:variables
    
}
