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
 * Stellt den Bruch dar.
 */
public final class Fraction extends HorizontalEnter {
    private final static int lineLongerThenMinWidth = 1;
    final static int positionAtSizePercent = 30;
    private final Succession base;

    public Fraction(final Basic parent) {
        super(parent);
        base = new Succession(this, true);
    }

    /**
     * Erm�glicht Zugriff auf den "Nenner" des Bruches.
     * (Die Main-Struktur ist der "Zähler".)
     * @return Succession-Objekt des Zählers
     */
    public final BuildStructure getBaseStructure() {
        return base;
    }

    public final void paint(final Graphics g, final int atX, final int atY) {
        final int middle = (parent.getMySize() * positionAtSizePercent) / 100;

        final int mainW = main.getWidth(g);
        final int baseW = base.getWidth(g);

        final int width = ((mainW < baseW) ? baseW : mainW) + 
                          (lineLongerThenMinWidth * 2);

        final int posMainW = (width - mainW) / 2;
        final int posBaseW = (width - baseW) / 2;

        g.drawLine(atX, atY - middle, atX + width, atY - middle);

        final int under = main.getHeightUnderBaseline(g);
        main.paint(g, atX + lineLongerThenMinWidth + posMainW, 
                   atY - middle - under - 3);

        final int above = base.getHeightAboveBaseline(g);
        base.paint(g, atX + lineLongerThenMinWidth + posBaseW, 
                   atY - middle + above + 3);
    }

    public final int getWidthUncached(final Graphics g) {
        final int mainW = main.getWidth(g);
        final int baseW = base.getWidth(g);

        return ((mainW < baseW) ? baseW : mainW) + 
               (lineLongerThenMinWidth * 2);
    }

    public final int getHeightAboveBaselineUncached(final Graphics g) {
        final int middle = (parent.getMySize() * positionAtSizePercent) / 100;
        final int over = main.getHeight(g) + 3;

        return middle + over;
    }

    public final int getHeightUnderBaselineUncached(final Graphics g) {
        final int middle = (parent.getMySize() * positionAtSizePercent) / 100;
        final int under = base.getHeight(g) + 5;

        return under - middle;
    }

    public final boolean findSelectedObjects(final int currentX, 
                                             final int currentY, 
                                             final int fromX, final int fromY, 
                                             final int toX, final int toY, 
                                             final Graphics g) {
        final int middle = (parent.getMySize() * positionAtSizePercent) / 100;

        final int mainW = main.getWidth(g);
        final int baseW = base.getWidth(g);

        if (fromY < (currentY - middle)) {
            if (toY < (currentY - middle)) {
                final int width = ((mainW < baseW) ? baseW : mainW) + 
                                  (lineLongerThenMinWidth * 2);
                final int posMainW = (width - mainW) / 2;
                final int under = main.getHeightUnderBaseline(g);

                return main.findSelectedObjects(currentX + 
                                                lineLongerThenMinWidth + 
                                                posMainW, 
                                                currentY - middle - under - 3, 
                                                fromX, fromY, toX, toY, g);
            } else {
                return false;
            }
        } else {
            if (toY > (currentY - middle)) {
                final int width = ((mainW < baseW) ? baseW : mainW) + 
                                  (lineLongerThenMinWidth * 2);
                final int posBaseW = (width - baseW) / 2;
                final int above = base.getHeightAboveBaseline(g);

                return base.findSelectedObjects(currentX + 
                                                lineLongerThenMinWidth + 
                                                posBaseW, 
                                                currentY - middle + above + 3, 
                                                fromX, fromY, toX, toY, g);
            } else {
                return false;
            }
        }
    }

    public final void moveUp(final Basic from, final int toXPosition) {
        final Graphics g = getGraphicsHandle();

        final int mainW = main.getWidth(g);
        final int baseW = base.getWidth(g);

        final int width = ((mainW < baseW) ? baseW : mainW) + 
                          (lineLongerThenMinWidth * 2);

        final int posMainW = (width - mainW) / 2;
        final int posBaseW = (width - baseW) / 2;

        if (from == base) { // Von unten nach oben gehen
            main.searchObjectAtRelativeXPosition(g, 
                                                 toXPosition - posMainW + 
                                                 posBaseW, true);
        } else if (from == parent) { // Von aussen nach oben gehen
            main.searchObjectAtRelativeXPosition(g, toXPosition - posMainW, 
                                                 true);
        } else // Objekt verlassen
        {
            parent.moveUp(this, toXPosition + posMainW + 
                          lineLongerThenMinWidth);
        }

        // interne Verschiebung aufaddieren
    }

    public final void moveDown(final Basic from, final int toXPosition) {
        final Graphics g = getGraphicsHandle();

        final int mainW = main.getWidth(g);
        final int baseW = base.getWidth(g);

        final int width = ((mainW < baseW) ? baseW : mainW) + 
                          (lineLongerThenMinWidth * 2);

        final int posMainW = (width - mainW) / 2;
        final int posBaseW = (width - baseW) / 2;

        if (from == main) { // Von unten nach oben gehen
            base.searchObjectAtRelativeXPosition(g, 
                                                 (toXPosition + posMainW) - 
                                                 posBaseW, false);
        } else if (from == parent) { // Von aussen nach oben gehen
            base.searchObjectAtRelativeXPosition(g, toXPosition - posBaseW, 
                                                 false);
        } else // Objekt verlassen
        {
            parent.moveDown(this, 
                            toXPosition + posBaseW + lineLongerThenMinWidth);
        }

        // interne Verschiebung aufaddieren
    }

    public final boolean canEnterUpOrDownWithCursor() {
        return true;
    }

    public final void generateMathMLCode(final MathMLExport mmle) {
        mmle.ifNessesaryInsertInvisibleOperator();

        mmle.addTextLine("<mfrac>");

        mmle.increaseLayer();
        main.generateMathMLCode(mmle);
        base.generateMathMLCode(mmle);
        mmle.decreaseLayer();

        mmle.addTextLine("</mfrac>");
        mmle.nextElement(true);
    }
}