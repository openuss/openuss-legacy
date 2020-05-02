/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.graphics;

import java.awt.*;

import org.openuss.openformula.mathml.out.MathMLExport;


/**
 * TestCross, Klasse existiert für Debugzwecke. Sie stellt ein Rechteck mit einem
 * Kreuz dar.
 */
public final class TestCross extends Basic {
    private final int size;

    public TestCross(final Basic parent, final int size) {
        super(parent);
        this.size = size;
    }

    public final int getWidthUncached(final Graphics g) {
        return size;
    }

    public final int getHeightAboveBaselineUncached(final Graphics g) {
        return size;
    }

    public final int getHeightUnderBaselineUncached(final Graphics g) {
        return 2;
    }

    public final void paint(final Graphics g, final int atX, final int atY) {
        g.drawRect(atX, atY - size, size, size);

        /*g.drawLine(atX, atY - size, atX + size, atY);
        g.drawLine(atX + size, atY - size, atX, atY);*/
        final int vX = size / 2;
        final int vY = 0;

        final int biegungsHoehe = getMySize() / 3;

        //final int abflachungsHoehe = getMySize() / 5;
        int lastx = -1;
        int lasty = -1;

        for (int i = 0; i < size; i++) {
            double k;

            if (i < biegungsHoehe) {
                k = biegungsHoehe - i;
            } else if ((size - i) < biegungsHoehe) {
                k = biegungsHoehe - (size - i);
            } else {
                k = 0;
            }

            k /= 6.0;

            k = k * k;

            /* int big=2;
             if (i < abflachungsHoehe)
                 big--;
             else if ((size - i) < abflachungsHoehe)
                 big--;*/
            final int curX = atX + vX + (int) k;
            final int curY = (atY + vY) - i;

            if (lastx != -1) {
                g.drawLine(lastx, lasty, curX, curY);
                g.drawLine(lastx + 1, lasty, curX + 1, curY);

                //g.drawLine(lastx+big/2, lasty, curX+big/2, curY);
            }

            lastx = atX + vX + (int) k;
            lasty = (atY + vY) - i;
        }
    }

    public final void generateMathMLCode(final MathMLExport mmle) {
        mmle.ifNessesaryInsertInvisibleOperator();
        mmle.addTextLine("<ms>XCROSS</ms>");
        mmle.nextElement(true);
    }
}