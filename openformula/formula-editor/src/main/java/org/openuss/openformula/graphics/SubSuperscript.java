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

import org.openuss.openformula.mathml.in.BuildStructure;
import org.openuss.openformula.mathml.out.MathMLExport;


/**
 * Diese Klasse dient zur Darstellung von Hoch-&Tiefgestelltem
 */
public final class SubSuperscript extends HorizontalEnter {
    private final Succession base;

    public SubSuperscript(final Basic parent) {
        super(parent, parent.getMySizeClass() + Subscript.increaseSizeClass);
        base = new Succession(this, 
                              parent.getMySizeClass() + 
                              Subscript.increaseSizeClass, true);
    }

    public final BuildStructure getBaseStructure() {
        return base;
    }

    public final void paint(final Graphics g, final int atX, final int atY) {
        final int middle = (parent.getMySize() * Fraction.positionAtSizePercent) / 100;

        //int mainW = main.getWidth(g);
        //int baseW = base.getWidth(g);
        //int width = ((mainW < baseW) ? baseW : mainW) ;
        //int posMainW = (width - mainW) / 2;
        //int posBaseW = (width - baseW) / 2;
        //g.drawLine(atX, atY - middle, atX + width, atY - middle);
        final int under = main.getHeightUnderBaseline(g);
        main.paint(g, atX, atY - middle - under - 1);

        final int above = base.getHeightAboveBaseline(g);
        base.paint(g, atX, atY - middle + above + 1);
    }

    public final int getWidthUncached(final Graphics g) {
        final int mainW = main.getWidth(g);
        final int baseW = base.getWidth(g);

        return ((mainW < baseW) ? baseW : mainW);
    }

    public final int getHeightAboveBaselineUncached(final Graphics g) {
        final int middle = (parent.getMySize() * Fraction.positionAtSizePercent) / 100;
        final int over = main.getHeight(g) + 2;

        return middle + over;
    }

    public final int getHeightUnderBaselineUncached(final Graphics g) {
        final int middle = (parent.getMySize() * Fraction.positionAtSizePercent) / 100;
        final int under = base.getHeight(g) + 3;

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
            if (toY > (currentY - middle)) {
                //int width = ((mainW < baseW) ? baseW : mainW) ;
                //int posBaseW = (width - baseW) / 2;
                final int above = base.getHeightAboveBaseline(g);

                return base.findSelectedObjects(currentX, 
                                                currentY - middle + above + 1, 
                                                fromX, fromY, toX, toY, g);
            } else {
                return false;
            }
        }
    }

    // Das Objekt (in der Regel den Cursor) nach oben verschieben. Dabei etwa bei toYPosition positionieren
    public final void moveUp(final Basic from, final int toXPosition) {
        final Graphics g = getGraphicsHandle();

        //System.out.println("Position:"+toXPosition+"  - "+posMainW+","+posBaseW);
        if ((from == base) || (from == parent)) {
            main.searchObjectAtRelativeXPosition(g, toXPosition, true);
        } else {
            parent.moveUp(this, toXPosition);
        }

        // interne Verschiebung aufaddieren
    }

    // Das Objekt (in der Regel den Cursor) nach rechts verschieben. Dabei etwa bei toYPositon positionieren
    public final void moveDown(final Basic from, final int toXPosition) {
        final Graphics g = getGraphicsHandle();

        if ((from == main) || (from == parent)) {
            base.searchObjectAtRelativeXPosition(g, toXPosition, false);
        } else {
            parent.moveDown(this, toXPosition);
        }

        // interne Verschiebung aufaddieren
    }

    public final boolean canEnterUpOrDownWithCursor() {
        return true;
    }

    public final void generateMathMLCode(final MathMLExport mmle) {
        final String str = mmle.returnAndDelteteLastElement();

        mmle.addTextLine("<msubsup>");

        mmle.increaseLayer();
        mmle.addTextLine("<mrow>");

        mmle.increaseLayer();
        mmle.addText(str);
        mmle.decreaseLayer();
        mmle.addTextLine("</mrow>");

        base.generateMathMLCode(mmle);
        main.generateMathMLCode(mmle);
        mmle.decreaseLayer();

        mmle.addTextLine("</msubsup>");
        mmle.nextElement(true);
    }
}