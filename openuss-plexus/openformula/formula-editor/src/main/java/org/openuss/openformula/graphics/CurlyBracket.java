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
 * Stellt die geschweifte Klammer dar.
 */
public final class CurlyBracket extends HorizontalEnter {
    final boolean left;
    final boolean right;

    public CurlyBracket(final Basic parent, final boolean left, 
                        final boolean right) {
        super(parent);

        if (!left && !right) {
            throw new RuntimeException("Zumindest eine Klammer wird benötigt.");
        }

        this.left = left;
        this.right = right;
    }

    public final int getWidthUncached(final Graphics g) {
        final double biegungsHoehe = getMySize() / 4;
        double k = biegungsHoehe/*/3.0*/
        ;

        if (left && right) {
            k *= 2;
        }

        return main.getWidth(g) + (int) k + 4;
    }

    public final int getHeightAboveBaselineUncached(final Graphics g) {
        return main.getHeightAboveBaseline(g);
    }

    public final int getHeightUnderBaselineUncached(final Graphics g) {
        return main.getHeightUnderBaseline(g);
    }

    public final void paint(final Graphics g, final int atX, final int atY) {
        final int sizeY = main.getHeight(g);
        final int sizeX = main.getWidth(g);

        final int biegungsHoehe = getMySize() / 4;
        final double kmax = biegungsHoehe;

        final int dfX = 0;
        final int dY = main.getHeightUnderBaseline(g);
        final int dsX = sizeX + ((left ? 2 : 1) * (int) kmax);

        if (left) {
            main.paint(g, atX + (int) kmax + 2, atY);
        } else {
            main.paint(g, atX + 2, atY);
        }

        final double kmed = kmax / 2;

        int lastLeftX = -1;
        int lastRigthX = -1;
        int lasty = -1;

        for (int i = 0; i < sizeY; i++) { // todo: wg. Antialaisting auf Polygon umstellen ?

            double k;

            if (i < biegungsHoehe) {
                k = biegungsHoehe - i;
            } else if ((sizeY - i) < biegungsHoehe) {
                k = biegungsHoehe - (sizeY - i);
            } else if (Math.abs((sizeY / 2) - i) < biegungsHoehe) {
                k = -biegungsHoehe + Math.abs((sizeY / 2) - i);
            }
            /*else if ((sizeY - i) < biegungsHoehe)
                k=0;*/
            else {
                k = 0;
            }

            //k /= 3.0;
            if (k > 0) {
                k = (k * k) / (biegungsHoehe * 2);
            } else {
                k = -(k * k) / (biegungsHoehe * 2);
            }

            if (((i == 0) || (i == sizeY - 1)) && (k < 1)) {
                k = 1;
            }

            if ((i == sizeY / 2) && (k > -1)) {
                k = -1;
            }

            k += kmed;

            final int curLeftX = atX + dfX + (int) k + 1;
            final int curRightX = (atX + dsX) - (int) k + 1;

            final int curY = (atY + dY) - i;

            if (lastLeftX != -1) {
                if (left) {
                    g.drawLine(lastLeftX, lasty, curLeftX, curY);
                    g.drawLine(lastLeftX + 1, lasty, curLeftX + 1, curY);

                    if (right) {
                        g.drawLine(lastRigthX, lasty, curRightX, curY);
                        g.drawLine(lastRigthX + 1, lasty, curRightX + 1, curY);
                    }
                } else {
                    g.drawLine(lastRigthX, lasty, curRightX, curY);
                    g.drawLine(lastRigthX + 1, lasty, curRightX + 1, curY);
                }
            }

            lastRigthX = curRightX;
            lastLeftX = curLeftX;
            lasty = curY;
        }
    }

    public final boolean findSelectedObjects(final int currentX, 
                                             final int currentY, 
                                             final int fromX, final int fromY, 
                                             final int toX, final int toY, 
                                             final Graphics g) {
        final double biegungsHoehe = getMySize() / 6;
        final double k = biegungsHoehe/*/3.0*/
        ;

        return main.findSelectedObjects(currentX + (left ? (int) k : 0) + 2, 
                                        currentY, fromX, fromY, toX, toY, g);
    }

    public final void generateMathMLCode(final MathMLExport mmle) {
        mmle.ifNessesaryInsertInvisibleOperator();

        mmle.addTextLine("<mfenced open=\"{\" close=\"}\">");

        mmle.increaseLayer();
        main.generateMathMLCode(mmle);
        mmle.decreaseLayer();

        mmle.addTextLine("</mfenced>");
        mmle.nextElement(true);
    }
}