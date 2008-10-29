/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.graphics;

import java.awt.Graphics;

import org.openuss.openformula.io.Cursor;


/**
 * Created by IntelliJ IDEA.
 * User: Jan Kirchhoff
 * Date: 22.10.2003
 * Time: 16:38:45
 * To change this template use Options | File Templates.
 */
public final class PaintSymbol extends Symbol {
    public PaintSymbol(final Basic parent, final char letter) {
        super(parent, letter);
    }

    public final int getWidthUncached(final Graphics g) {
        if ((letter >= 0x22EE) && (letter <= 0x22F1)) {
            return getMySize() + 2;
        }

        if ((letter == 0x21D0) || (letter == 0x21D2) || (letter == 0x21D4)) {
            return ((3 * getMySize()) / 4) + 2;
        } else {
            return (getMySize() / 2) + 2;
        }
    }

    public final int getHeightAboveBaselineUncached(final Graphics g) {
        return (getMySize() * Letter.usageOfLetterPercent) / 100;
    }

    public final int getHeightUnderBaselineUncached(final Graphics g) {
        return 0;
    }

    private void paintLine(final Graphics g, final int atX, final int atY, 
                           final float x1, final float y1, final float x2, 
                           final float y2) {
        final int ms = getMySize();
        final float size = ms / 100.0f;
        final int ix1 = (int) (x1 * size) + atX;
        final int iy1 = ((int) (y1 * size) + atY) - ms;
        final int ix2 = (int) (x2 * size) + atX;
        final int iy2 = ((int) (y2 * size) + atY) - ms;

        if (ms > 20) {
            for (int i = 0; i < 2; i++)
                for (int j = 0; j < 2; j++)
                    g.drawLine(ix1 + i, iy1 + j, ix2 + i, iy2 + j);
        } else {
            g.drawLine(ix1, iy1, ix2, iy2);
        }
    }

    private void paintArc(final Graphics g, final int atX, final int atY, 
                          final float x1, final float y1, final float x2, 
                          final float y2, final int startAngle, 
                          final int lengthAngle) {
        final int ms = getMySize();
        final float size = ms / 100.0f;
        final int ix1 = (int) (x1 * size) + atX;
        final int iy1 = ((int) (y1 * size) + atY) - ms;
        final int ix2 = (int) (x2 * size) + atX;
        final int iy2 = ((int) (y2 * size) + atY) - ms;

        if (ms > 20) {
            for (int i = 0; i < 2; i++)
                for (int j = 0; j < 2; j++)
                    g.drawArc(ix1 + i, iy1 + j, ix2 - ix1, iy2 - iy1, 
                              startAngle, lengthAngle);
        } else {
            g.drawArc(ix1, iy1, ix2 - ix1, iy2 - iy1, startAngle, lengthAngle);
        }
    }

    private void paintFullCircle(final Graphics g, final int atX, final int atY, 
                                 final float x1, final float y1, final float x2, 
                                 final float y2) {
        final int ms = getMySize();
        final float size = ms / 100.0f;

        final int ix1 = (int) (x1 * size) + atX;
        final int iy1 = ((int) (y1 * size) + atY) - ms;
        final int ix2 = (int) (x2 * size) + atX;
        final int iy2 = ((int) (y2 * size) + atY) - ms;

        int xa = ix2 - ix1;
        int ya = iy2 - iy1;

        if (Cursor.isRealyOldJavaVersion()) {
            if (xa < 3) {
                xa = 3;
            }

            if (ya < 3) {
                xa = 3;
            }
        }

        g.fillOval(ix1, iy1, xa, ya);
    }

    public final void paint(final Graphics g, final int atX, final int atY) {
        //System.out.println("can "+g.getFont().canDisplay(letter));
        switch (letter) {
        case 0x2200: // für alle
            paintLine(g, atX, atY, 0, 35, 25, 95);
            paintLine(g, atX, atY, 25, 95, 50, 35);
            paintLine(g, atX, atY, 12, 60, 38, 60);

            break;

        case 0x2204: // es existiert nicht
            paintLine(g, atX, atY, 40, 28, 10, 100);

        case 0x2203: // es existiert
            paintLine(g, atX, atY, 0, 35, 50, 35);
            paintLine(g, atX, atY, 8, 67, 50, 67);
            paintLine(g, atX, atY, 0, 95, 50, 95);
            paintLine(g, atX, atY, 50, 35, 50, 95);

            break;

        case 0x2209: // nicht Element von
            paintLine(g, atX, atY, 40, 45, 10, 100);

        case 0x2208: // Element von
            paintArc(g, atX, atY, 5, 55, 55, 95, 90, 180);
            paintLine(g, atX, atY, 30, 55, 45, 55);
            paintLine(g, atX, atY, 30, 95, 45, 95);
            paintLine(g, atX, atY, 5, 75, 45, 75);

            break;

        case 0x222A: // Vereinigung
            paintArc(g, atX, atY, 0, 50, 50, 95, 180, 180);
            paintLine(g, atX, atY, 0, 35, 0, 73);
            paintLine(g, atX, atY, 50, 35, 50, 73);

            break;

        case 0x2227: // und
            paintLine(g, atX, atY, 3, 95, 25, 55);
            paintLine(g, atX, atY, 47, 95, 25, 55);

            break;

        case 0x2228: // oder
            paintLine(g, atX, atY, 25, 95, 3, 55);
            paintLine(g, atX, atY, 25, 95, 47, 55);

            break;

        case 0x2205: // leere Menge
            paintArc(g, atX, atY, 0, 45, 50, 95, 0, 360);
            paintLine(g, atX, atY, 0, 95, 50, 45);

            break;

        case 0x21D0: // Doppelpfeil links
            paintLine(g, atX, atY, 12, 60, 75, 60);
            paintLine(g, atX, atY, 0, 70, 20, 50);
            paintLine(g, atX, atY, 12, 80, 75, 80);
            paintLine(g, atX, atY, 0, 70, 20, 90);

            break;

        case 0x21D2: // Doppelpfeil rechts
            paintLine(g, atX, atY, 0, 60, 63, 60);
            paintLine(g, atX, atY, 75, 70, 55, 50);
            paintLine(g, atX, atY, 0, 80, 63, 80);
            paintLine(g, atX, atY, 75, 70, 55, 90);

            break;

        case 0x21D4: // Doppelpfeil links & rechts
            paintLine(g, atX, atY, 12, 60, 63, 60);
            paintLine(g, atX, atY, 75, 70, 55, 50);
            paintLine(g, atX, atY, 12, 80, 63, 80);
            paintLine(g, atX, atY, 75, 70, 55, 90);
            paintLine(g, atX, atY, 0, 70, 20, 50);
            paintLine(g, atX, atY, 0, 70, 20, 90);

            break;

        case 0x22c5:
            paintFullCircle(g, atX, atY, 17, 62, 33, 78);

            break;

        case 0x22EE:

            for (int i = -32; i <= 32; i += 32)
                paintFullCircle(g, atX, atY, 45, 59 + i, 55, 69 + i);

            break;

        case 0x22EF:

            for (int i = -32; i <= 32; i += 32)
                paintFullCircle(g, atX, atY, 45 + i, 59, 55 + i, 69);

            break;

        case 0x22F0:

            for (int i = -32; i <= 32; i += 32)
                paintFullCircle(g, atX, atY, 45 + i, 59 - i, 55 + i, 69 - i);

            break;

        case 0x22F1:

            for (int i = -32; i <= 32; i += 32)
                paintFullCircle(g, atX, atY, 45 + i, 59 + i, 55 + i, 69 + i);

            break;

        default:
            throw new RuntimeException("PaintSymbol: Unbekanntes Zeichen!");
        }
    }
}