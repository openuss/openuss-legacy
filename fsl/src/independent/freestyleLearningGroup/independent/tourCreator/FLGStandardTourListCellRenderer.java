package freestyleLearningGroup.independent.tourCreator;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.DefaultListCellRenderer;
import freestyleLearningGroup.independent.gui.FLGImageUtility;

/**
 * Class which renders the list cells representing the tourElements.
 * @author  Steffen Wachenfeld
 */
public class FLGStandardTourListCellRenderer extends javax.swing.JPanel implements javax.swing.ListCellRenderer {
    
    private javax.swing.JLabel m_jLabel = new javax.swing.JLabel();
    public static final java.awt.Dimension DEFAULT_PANEL_DIMENSION = new java.awt.Dimension(300, 64);
    public static final int DEFAULT_PANEL_DESCRIPTION_LENGTH = 30;
    
    private static java.awt.Image m_zeroPanelImage;
    
    
    /** Creates a new instance of FLGStandardTourListCellRenderer */
    public FLGStandardTourListCellRenderer() {     
//        m_zeroPanelImage = FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/tourCreator/images/tourCreator2_64x64.gif"));
        this.setLayout(new java.awt.BorderLayout());
        this.setMinimumSize(FLGStandardTourListCellRenderer.DEFAULT_PANEL_DIMENSION);
        this.setMaximumSize(FLGStandardTourListCellRenderer.DEFAULT_PANEL_DIMENSION);
        this.setPreferredSize(FLGStandardTourListCellRenderer.DEFAULT_PANEL_DIMENSION);
        this.add(m_jLabel);
        m_jLabel.setPreferredSize(DEFAULT_PANEL_DIMENSION);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(5,5,5,5));
    }
    
    public void setZeroPanelImage(java.awt.Image image) {
        m_zeroPanelImage = image;
    }
    
    public java.awt.Component getListCellRendererComponent(
        javax.swing.JList a_jList,
        Object a_cellObject,        // value to display
        int ai_cellIndex,           // cell index
        boolean ab_isSelected,      // is the cell selected
        boolean ab_cellHasFocus)    // the list and the cell have the focus
        {
        
        //setting font type
        Font l_tempFont = a_jList.getFont().deriveFont(Font.PLAIN);
        m_jLabel.setFont(l_tempFont);
        
        //check if a_cellObject is of type FLGTourElement
        if(a_cellObject instanceof FLGStandardTourElement) {
            FLGStandardTourElement l_tourElement = (FLGStandardTourElement)a_cellObject;
            
            //Name
            String ls_labelText = "<HTML>";
            if(ai_cellIndex >= 0) {
                ls_labelText += "<B>" + (ai_cellIndex+1) + ".  " + l_tourElement.getElementName() + "</B> ";
                
                //Display time
                if(l_tourElement.getDisplayTime() == FLGTourElement.UNLIMITED_DISPLAY_TIME)
                    ls_labelText   += "(&#8734;)";
                else if (l_tourElement.getDisplayTime() == FLGTourElement.HIDDEN_DISPLAY_TIME)
                    ls_labelText   += "(hidden)";
                else
                    ls_labelText   += "("   + l_tourElement.getDisplayTime() + "s)";
                
                //Description eventually shortened
                String ls_description = l_tourElement.getDescription();
                if(ls_description.length() > this.DEFAULT_PANEL_DESCRIPTION_LENGTH)
                    ls_description = ls_description.substring(0, this.DEFAULT_PANEL_DESCRIPTION_LENGTH) + "...";
                ls_labelText       += "<P><I>" + ls_description + "</I></P></HTML>";
                
                m_jLabel.setText(ls_labelText);
                javax.swing.Icon l_icon = new ImageIcon(l_tourElement.getSmallImage());
                m_jLabel.setIcon(l_icon);
                
            }// normal elemet
            else {
                // Zero Element
                ls_labelText += "<B> Tour: " + l_tourElement.getElementName() + "</B> ";
                
                //zero panel description unshortened
                ls_labelText       += "<P><I>" + l_tourElement.getDescription() + "</I></P></HTML>";
                
                m_jLabel.setText(ls_labelText);
                if (m_zeroPanelImage != null) {
                    m_jLabel.setIcon(new ImageIcon(m_zeroPanelImage.getScaledInstance(-1, 64, java.awt.Image.SCALE_SMOOTH)));
                }
            }
        }//if instanceof
        
        if(ab_isSelected) {
            this.setOpaque(true);
            this.setBackground(a_jList.getSelectionBackground());
            this.setForeground(a_jList.getSelectionForeground());
            this.m_jLabel.setForeground(a_jList.getSelectionForeground());
        }
        else {
            this.setBackground(a_jList.getBackground());
            this.setForeground(a_jList.getForeground());
            this.m_jLabel.setForeground(a_jList.getForeground());
        }
        
        setEnabled(a_jList.isEnabled());
        
        
        return this;
        
    }//getListCellRendererComponent
    
    
    
    
    
    /**
     * Overriding methods for performance !!
     *
     * public void repaint(long tm, int x, int y, int width, int height)
     * {
     * //override
     * }
     *
     * public void repaint(java.awt.Rectangle r)
     * {
     * //override
     * }
     *
     * public void revalidate()
     * {
     * //override
     * }
     *
     * public void validate()
     * {
     * //override
     * }*/
}
