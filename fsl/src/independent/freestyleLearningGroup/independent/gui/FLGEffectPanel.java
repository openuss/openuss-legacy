/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.independent.gui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.geom.Rectangle2D;
import java.net.URL;

import javax.swing.JPanel;
import javax.swing.UIManager;

public class FLGEffectPanel extends JPanel {
    private static final int TEXTURE_EFFECT = 1;
    private static final int SOLID_COLOR_EFFECT = 2;
    private static final int GRADIENT_COLOR_EFFECT = 3;
    private Image textureImage;
    private String uiManagerTopColorId;
    private String uiManagerBottomColorId;
    private String uiManagerColorId;
    private int effectType = -1;
    private boolean withBorderArea;

    public FLGEffectPanel() {
        setEffect((String)null, false);
    }

    public FLGEffectPanel(URL textureImageURL, boolean withBorderArea) {
        setEffect(textureImageURL, withBorderArea);
    }

    public FLGEffectPanel(String uiManagerColorId, boolean withBorderArea) {
        setEffect(uiManagerColorId, withBorderArea);
    }

    public FLGEffectPanel(String uiManagerTopColorId, String uiManagerBottomColorId, boolean withBorderArea) {
        setEffect(uiManagerTopColorId, uiManagerBottomColorId, withBorderArea);
    }

    public void setEffect(URL textureImageURL, boolean withBorderArea) {
        textureImage = FLGImageUtility.loadImageAndWait(textureImageURL);
        this.withBorderArea = withBorderArea;
        effectType = TEXTURE_EFFECT;
    }

    public void setEffect(String uiManagerTopColorId, String uiManagerBottomColorId, boolean withBorderArea) {
        this.uiManagerTopColorId = uiManagerTopColorId;
        this.uiManagerBottomColorId = uiManagerBottomColorId;
        this.withBorderArea = withBorderArea;
        effectType = GRADIENT_COLOR_EFFECT;
    }

    public void setEffect(String uiManagerColorId, boolean withBorderArea) {
        this.uiManagerColorId = uiManagerColorId;
        this.withBorderArea = withBorderArea;
        effectType = SOLID_COLOR_EFFECT;
    }

    public void paint(Graphics g) {
    	if(!(effectType == -1)) {
	    	switch (effectType) {
	            case SOLID_COLOR_EFFECT:
	                paintSolidColorEffect(g);
	                break;
	            case GRADIENT_COLOR_EFFECT:
	                paintGradientColorEffect(g);
	                break;
	            case TEXTURE_EFFECT:
	                paintTextureEffect(g);
	                break;
	        }
	        super.paint(g);
    	}
    }

    private void paintTextureEffect(Graphics g) {
        int imageWidth = textureImage.getWidth(this);
        int imageHeight = textureImage.getHeight(this);
        int displayWidth = getBounds().width;
        int displayHeight = getBounds().height;
        int noCols = (displayWidth / imageWidth) + 1;
        int noRows = (displayHeight / imageHeight) + 1;
        Insets insets = getInsets();
        if (!withBorderArea) {
            g.setClip(insets.left, insets.top, displayWidth - insets.right - insets.left,
                displayHeight - insets.top - insets.bottom);
        }
        if (imageWidth > 0) {
            for (int i = 0; i < noRows; i++)
                for (int j = 0; j < noCols; j++)
                    g.drawImage(textureImage, j * imageWidth, i * imageHeight, this);
        }
        g.setClip(0, 0, displayWidth, displayHeight);
    }

    private void paintSolidColorEffect(Graphics g) {
        if (uiManagerColorId != null) {
            if (uiManagerColorId != null) {
                Color color = (Color)UIManager.get(uiManagerColorId);
                if (color != null) g.setColor(color);
            }
            int height = (int)getSize().getHeight();
            int width = (int)getSize().getWidth();
            int x = 0;
            int y = 0;
            if (!withBorderArea) {
                x = x + getInsets().left;
                y = y + getInsets().top;
                height = height - getInsets().top - getInsets().bottom;
                width = width - getInsets().left - getInsets().right;
            }
            g.fillRect(x, y, width, height);
        }
    }

    private void paintGradientColorEffect(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        int height = (int)getSize().getHeight();
        int width = (int)getSize().getWidth();
        int x = 0;
        int y = 0;
        if (!withBorderArea) {
            x = x + getInsets().left;
            y = y + getInsets().top;
            height = height - getInsets().top - getInsets().bottom;
            width = width - getInsets().left - getInsets().right;
        }
        if (uiManagerTopColorId != null && uiManagerBottomColorId != null) {
            if (UIManager.get(uiManagerTopColorId) != null && UIManager.get(uiManagerBottomColorId) != null)
                g2d.setPaint(
                    new GradientPaint(0, 0, (Color)UIManager.get(uiManagerTopColorId), 0, height, (Color)UIManager.get(uiManagerBottomColorId), true));
        }
        g2d.fill(new Rectangle2D.Double(x, y, width, height));
    }

    public boolean isOpaque() { return false; }
    
    public void configurationChanged() {
        repaint();
    }
    
}
