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
 * Die Klasse dient zur Darstellung von "Tiefergestelltem".
 */
public final class Subscript extends HorizontalEnter {
    // Todo: neues konzept zur Aufteilung - über "middle" positionieren
    final static int increaseSizeClass = 3;

    // --Recycle Bin (20.01.04 18:37): final static int minPixelDif = 4;
    public Subscript(final Basic parent) {
        super(parent, parent.getMySizeClass() + increaseSizeClass);
    }

    // --Recycle Bin START (20.01.04 18:37):
    //    public Subscript(final Basic parent, final int sizeClass) {
    //        super(parent, sizeClass + increaseSizeClass);
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)
    public final void paint(final Graphics g, final int atX, final int atY) {
        /*int subY = main.getMySize() / 10;
        if (subY < minPixelDif) subY = minPixelDif;
        main.paint(g, atX, atY + subY);*/
        final int middle = (parent.getMySize() * Fraction.positionAtSizePercent) / 100;

        //int mainW = main.getWidth(g);
        //int baseW = base.getWidth(g);
        //int width = ((mainW < baseW) ? baseW : mainW) ;
        //int posMainW = (width - mainW) / 2;
        //int posBaseW = (width - baseW) / 2;
        //g.drawLine(atX, atY - middle, atX + width, atY - middle);
        //int under = main.getHeightUnderBaseline(g);
        //main.paint(g, atX , atY - middle - under - 1);
        final int above = main.getHeightAboveBaseline(g);
        main.paint(g, atX, atY - middle + above + 1);
    }

    public final int getWidthUncached(final Graphics g) {
        return main.getWidth(g);
    }

    public final int getHeightAboveBaselineUncached(final Graphics g) {
        final int middle = (parent.getMySize() * Fraction.positionAtSizePercent) / 100;

        return middle - 1;
    }

    public final int getHeightUnderBaselineUncached(final Graphics g) {
        final int middle = (parent.getMySize() * Fraction.positionAtSizePercent) / 100;
        final int under = main.getHeight(g) + 3;

        return under - middle;
    }

    public final boolean findSelectedObjects(final int currentX, 
                                             final int currentY, 
                                             final int fromX, final int fromY, 
                                             final int toX, final int toY, 
                                             final Graphics g) {
        final int middle = (parent.getMySize() * Fraction.positionAtSizePercent) / 100;

        //int mainW = main.getWidth(g);
        //int baseW = base.getWidth(g);
        if (toY > (currentY - middle)) {
            //int width = ((mainW < baseW) ? baseW : mainW) ;
            //int posBaseW = (width - baseW) / 2;
            final int above = main.getHeightAboveBaseline(g);

            return main.findSelectedObjects(currentX, 
                                            currentY - middle + above + 1, 
                                            fromX, fromY, toX, toY, g);
        } else {
            return false;
        }
    }

    public final void generateMathMLCode(final MathMLExport mmle) {
        final String str = mmle.returnAndDelteteLastElement();

        mmle.addTextLine("<msub>");

        mmle.increaseLayer();
        mmle.addTextLine("<mrow>");

        mmle.increaseLayer();
        mmle.addText(str);
        mmle.decreaseLayer();
        mmle.addTextLine("</mrow>");

        main.generateMathMLCode(mmle);
        mmle.decreaseLayer();

        mmle.addTextLine("</msub>");
        mmle.nextElement(true);
    }
}