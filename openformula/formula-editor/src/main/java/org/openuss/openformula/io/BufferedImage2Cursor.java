/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.io;

import java.awt.*;
import java.awt.image.BufferedImage;

import org.openuss.openformula.graphics.Succession;


/**
 * Nutzt für die Darstellung der Cursor-Komponente ein BufferdImage.
 * Das prim�re Objekt muss an diese Klasse übergeben werden.
 * todo: Kann man doch �ndern, oder ?
 */
public final class BufferedImage2Cursor implements CursorDistribution {
    private BufferedImage image;
    private Succession primaryObject = null;

    public BufferedImage2Cursor() {
        image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    }

    public final void repaint() {
        if (primaryObject == null) {
            throw new RuntimeException("Primary Object not defined!");
        }

        Graphics g = image.getGraphics();
        final int width = primaryObject.getWidth(g) + 6;
        final int height = primaryObject.getHeight(g) + 6;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g = image.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);

        primaryObject.paint(g, 3, primaryObject.getHeightAboveBaseline(g) + 3);
    }

    public final Succession getPrimaryObject() {
        return primaryObject;
    }

    public final void setPrimaryObject(final Succession primaryObject) {
        this.primaryObject = primaryObject;
    }

    public final BufferedImage getImage() {
        return image;
    }

    public final Graphics getGraphics() {
        return image.getGraphics();
    }

    public final void requestFocus() {
    }
}