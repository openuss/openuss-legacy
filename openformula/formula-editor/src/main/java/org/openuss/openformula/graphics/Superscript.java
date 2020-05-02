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
 * Diese Klasse verwaltet h�hergeschriebene Objekte
 */
public final class Superscript extends HorizontalEnter {
    //final int posOverPercent = 110;
    private int previousHeight = -1;

    public Superscript(final Basic parent) {
        super(parent, parent.getMySizeClass() + Subscript.increaseSizeClass);
    }

    // --Recycle Bin START (20.01.04 18:37):
    //    public Superscript(final Basic parent, final int sizeClass) {
    //        super(parent, sizeClass + Subscript.increaseSizeClass);
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)
    public final void paint(final Graphics g, final int atX, final int atY) {
        //int middle = parent.getMySize() * Fraction.positionAtSizePercent / 100;
        //int under = main.getHeightUnderBaseline(g);
        //main.paint(g, atX, atY - middle - under - 1);
        int ha = (((Succession) parent).getHeightAboveBaselineOfPreviousObject(g, 
                                                                               this) * 110) / 100;
        ha -= main.getHeightAboveBaseline(g);

        final int middle = ((parent.getMySize() * Fraction.positionAtSizePercent) / 100) + 
                           main.getHeightUnderBaseline(g);
        ;

        //int under =
        if (middle > ha) {
            main.paint(g, atX, atY - middle - 1);
        } else {
            main.paint(g, atX, atY - ha - 1);
        }
    }

    public final int getWidthUncached(final Graphics g) {
        return main.getWidth(g);
    }

    public final int getHeightAboveBaselineUncached(final Graphics g) {
        final int hra = main.getHeightAboveBaseline(g);
        final int ha = ((((Succession) parent).getHeightAboveBaselineOfPreviousObject(g, 
                                                                                      this) * 110) / 100) - 
                       hra;

        final int middle = ((parent.getMySize() * Fraction.positionAtSizePercent) / 100) + 
                           main.getHeightUnderBaseline(g);

        if (middle > ha) {
            return middle + hra + 2;
        } else {
            return ha + hra + 2;
        }

        //int middle = parent.getMySize() * Fraction.positionAtSizePercent / 100;
        //int over = main.getHeight(g) + 2;
        //return middle + over;
    }

    /**
     * Diese Methode scannt den Vorgänger im übergeordneten Succession-Objekt auf Änderungen.
     * Falls diese vorliegen, wird ein objectChanged() ausgeführt.
     * @param g Graphic-Kontext
     * @return Höhe des Objektes
     */
    public final int getHeightAboveBaseline(final Graphics g) {
        final int prev = ((Succession) parent).getHeightAboveBaselineOfPreviousObject(g, 
                                                                                      this);

        if (previousHeight != prev) {
            objectChanged();
            previousHeight = prev;
        }

        return super.getHeightAboveBaseline(g);
    }

    public final int getHeightUnderBaselineUncached(final Graphics g) {
        return 0;
    }

    public final boolean findSelectedObjects(final int currentX, 
                                             final int currentY, 
                                             final int fromX, final int fromY, 
                                             final int toX, final int toY, 
                                             final Graphics g) {
        final int middle = (parent.getMySize() * Fraction.positionAtSizePercent) / 100;

        //int mainW = main.getWidth(g);
        //int baseW = base.getWidth(g);
        if (fromY < (currentY - middle)) {
            if (toY < (currentY - middle)) {
                //int width = ((mainW < baseW) ? baseW : mainW);
                //int posMainW = (width - mainW) / 2;
                final int under = main.getHeightUnderBaseline(g);

                return main.findSelectedObjects(currentX, 
                                                currentY - middle - under - 1, 
                                                fromX, fromY, toX, toY, g);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public final void generateMathMLCode(final MathMLExport mmle) {
        final String str = mmle.returnAndDelteteLastElement();

        mmle.addTextLine("<msup>");

        mmle.increaseLayer();
        mmle.addTextLine("<mrow>");

        mmle.increaseLayer();
        mmle.addText(str);
        mmle.decreaseLayer();
        mmle.addTextLine("</mrow>");

        main.generateMathMLCode(mmle);
        mmle.decreaseLayer();

        mmle.addTextLine("</msup>");
        mmle.nextElement(true);
    }
}