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
 * Created by IntelliJ IDEA.
 * User: Proke
 * Date: 25.09.2003
 * Time: 21:33:23
 * To change this template use Options | File Templates.
 */
public class Parenthesis extends HorizontalEnter {
    public Parenthesis(final Basic parent) {
        super(parent);
    }

    public final int getWidthUncached(final Graphics g) {
        final double biegungsHoehe = getMySize() / 3;
        final double k = biegungsHoehe/*/2*/
        ;

        return main.getWidth(g) + (int) k + 3;
    }

    public final int getHeightAboveBaselineUncached(final Graphics g) {
        return main.getHeightAboveBaseline(g);
    }

    public final int getHeightUnderBaselineUncached(final Graphics g) {
        return main.getHeightUnderBaseline(g);
    }

    public void paint(final Graphics g, final int atX, final int atY) {
        final int sizeY = main.getHeight(g);
        final int sizeX = main.getWidth(g);

        final int biegungsHoehe = getMySize() / 3;
        double k = biegungsHoehe / 2; //*biegungsHoehe;

        final int dfX = 0;
        final int dY = main.getHeightUnderBaseline(g);
        final int dsX = sizeX + (2 * (int) k);

        main.paint(g, atX + (int) k + 1, atY);

        int lastLeftX = -1;
        int lastRigthX = -1;
        int lasty = -1;

        for (int i = 0; i < sizeY; i++) { // todo: wg. Antialaisting auf Polygon umstellen ?

            //double k;
            if (i < biegungsHoehe) {
                k = biegungsHoehe - i;
            } else if ((sizeY - i) < biegungsHoehe) {
                k = biegungsHoehe - (sizeY - i);
            } else {
                k = 0;
            }


            //k /= 6.0;
            //k = k * k;
            k = ((k * k) / (biegungsHoehe * 2)) + 0.5;

            if (((i == 0) || (i == sizeY - 1)) && (k < 1)) {
                k = 1;
            }

            final int curLeftX = atX + dfX + (int) k;
            final int curRightX = (atX + dsX) - (int) k;

            final int curY = (atY + dY) - i;

            if (lastLeftX != -1) {
                g.drawLine(lastLeftX, lasty, curLeftX, curY);
                g.drawLine(lastLeftX + 1, lasty, curLeftX + 1, curY);

                g.drawLine(lastRigthX, lasty, curRightX, curY);
                g.drawLine(lastRigthX + 1, lasty, curRightX + 1, curY);
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
        /*double biegungsHoehe = getMySize() / 3;
        double k= biegungsHoehe/6.0;
        k = k * k;*/
        final double biegungsHoehe = getMySize() / 3;
        final double k = biegungsHoehe / 2;

        return main.findSelectedObjects(currentX + (int) k + 1, currentY, fromX, 
                                        fromY, toX, toY, g);
    }

    public void generateMathMLCode(final MathMLExport mmle) {
        mmle.ifNessesaryInsertInvisibleOperator();

        mmle.addTextLine("<mfenced open=\"(\" close=\")\">");

        mmle.increaseLayer();
        main.generateMathMLCode(mmle);
        mmle.decreaseLayer();

        mmle.addTextLine("</mfenced>");
        mmle.nextElement(true);
    }
}