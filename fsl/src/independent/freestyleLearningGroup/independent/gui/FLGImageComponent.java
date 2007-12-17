/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.independent.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JComponent;

/**
 * FLGImageComponent.
 * @author Freestyle Learning Group
 */
public class FLGImageComponent extends JComponent {
    private Image image;
    private boolean keepProportions;
    private boolean doAntialiazing;
    private Image antialiasedImage;

    public FLGImageComponent(boolean keepProportions) {
        this.keepProportions = keepProportions;
        this.doAntialiazing = true;
    }

    public FLGImageComponent(boolean keepProportions, boolean doAntialiazing) {
        this.keepProportions = keepProportions;
        this.doAntialiazing = doAntialiazing;
    }

    public void setImage(Image image) {
        this.image = image;
        antialiasedImage = null;
        repaint();
    }

    public void setDoAntialiazing(boolean doAnt) {
    	doAntialiazing = doAnt;
    }
    
    public Dimension getPreferredSize() {
        if (image != null) {
            Insets insets = getInsets();
            return new Dimension(image.getWidth(this) + insets.left + insets.right,
                image.getHeight(this) + insets.top + insets.bottom);
        }
        else
            return new Dimension(0, 0);
    }

    public void paint(Graphics g) {
        if (image != null) {
            Dimension size = getSize();
            Insets insets = getInsets();
            int x = insets.left;
            int y = insets.top;
            int maxWidth = size.width - insets.left - insets.right;
            int maxHeight = size.height - insets.top - insets.bottom;
            int scaledImageWidth;
            int scaledImageHeight;
            if (keepProportions) {
                int imageWidth = image.getWidth(this);
                int imageHeight = image.getHeight(this);
                double scaleFactorX = (double)maxWidth / imageWidth;
                double scaleFactorY = (double)maxHeight / imageHeight;
                double scaleFactor = Math.min(scaleFactorX, scaleFactorY);
                scaledImageWidth = (int)(imageWidth * scaleFactor);
                scaledImageHeight = (int)(imageHeight * scaleFactor);
                x += (maxWidth - scaledImageWidth) / 2;
                y += (maxHeight - scaledImageHeight) / 2;
            }
            else {
                scaledImageWidth = maxWidth;
                scaledImageHeight = maxHeight;
            }
            if (doAntialiazing) {
                if (antialiasedImage == null || (Math.abs(antialiasedImage.getWidth(this) - scaledImageWidth) > 20) ||
                    (Math.abs(antialiasedImage.getHeight(this) - scaledImageHeight) > 20)) {
                        antialiasedImage = null;
                        AntialiasedImageGeneratorThread thread = new AntialiasedImageGeneratorThread();
                        thread.setImage(image, scaledImageWidth, scaledImageHeight);
                        thread.start();
                }
                if (antialiasedImage != null) {
                    g.drawImage(antialiasedImage, x, y, scaledImageWidth, scaledImageHeight, this);
                }
                else {
                    g.drawImage(image, x, y, scaledImageWidth, scaledImageHeight, this);
                }
            }
            else {
                g.drawImage(image, x, y, scaledImageWidth, scaledImageHeight, this);
            }
        }
        super.paint(g);
    }

    class AntialiasedImageGeneratorThread extends Thread {
        Image originalImage;
        int width;
        int height;

        public void setImage(Image originalImage, int width, int height) {
            this.originalImage = originalImage;
            this.width = width;
            this.height = height;
        }

        public void run() {
            Image resultImage = FLGImageUtility.createAntiAliasedImage(originalImage, width, height);
            if (image == originalImage) {
                antialiasedImage = resultImage;
                repaint();
            }
        }
    }
}