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
 * Stellt die Wurzel dar.
 */
public final class Root extends HorizontalEnter {
    //Succession main;
    //final int defaultSize=50;
    private final Succession base;

    public Root(final Basic parent) {
        super(parent);
        base = new Succession(this, getMySizeClass() + 5, true);
    }

    // --Recycle Bin START (20.01.04 18:37):
    //    public Root(final Basic parent, final int sizeClass) {
    //        super(parent, sizeClass);
    //        base = new Succession(this, getMySizeClass() + 5, true);
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)
    public final BuildStructure getBaseStructure() {
        return base;
    }

    public final void paint(final Graphics g, final int atX, final int atY) {
        int height = main.getHeightAboveBaseline(g);
        final int underObject = main.getHeightUnderBaseline(g);

        final int mySize = main.getMySize();

        if (height < mySize) {
            height = mySize;
        }

        // todo: Irgendwie anders...
        final int baseWidth = base.getWidth(g);

        final int l1y = (mySize * 2) / 5; // Entfernung der ersten Linie von BottomLine
        int l1x = mySize / 10; // Länge der ersten Linie

        if (l1x < (baseWidth + 3)) {
            l1x = baseWidth + 3;
        }

        final int baseY = l1y + 4 + base.getHeightUnderBaseline(g);

        int l2y = mySize / 7; // Tiefe der zweiten Linie unter BottomLine (von ende erster Linie)

        if (l2y < underObject) {
            l2y = underObject;
        }

        int l2x = mySize / 10; // Vertikale länge der zweiten Linie

        if (l2x < 3) {
            l2x = 3;
        }

        int l3x = mySize / 10; // Vertikale Länge der dritten Linie

        if (l3x < 3) {
            l3x = 3;
        }

        final int l4x = mySize / 20; // Verlängerung über dritte Linie hinaus, bis Unterobjekt startet

        int l5x = mySize / 20; // Verlängerung nach Unterobjekt, bis Abschlusslinie beginnt

        if (l5x < 3) {
            l5x = 3;
        }

        final int l6y = mySize / 5; // Länge der Abschlusslinie

        if (l3x < 3) {
            l3x = 3;
        }

        int dist = mySize / 15;

        if (dist < 2) {
            dist = 2;
        }

        final int width = main.getWidth(g);

        g.drawLine(atX, atY - l1y, atX + l1x, atY - l1y);
        g.drawLine(atX + l1x, atY - l1y, atX + l1x + l2x, atY + l2y);
        g.drawLine(atX + l1x + l2x, atY + l2y, atX + l1x + l2x + l3x, 
                   atY - height - dist);

        g.drawLine(atX + l1x + l2x + l3x, atY - height - dist, 
                   atX + l1x + l2x + l3x + l4x + width + l5x, 
                   atY - height - dist);
        g.drawLine(atX + l1x + l2x + l3x + l4x + width + l5x, 
                   atY - height - dist, 
                   atX + l1x + l2x + l3x + l4x + width + l5x, 
                   atY - height - dist + l6y);

        base.paint(g, atX + 1, atY - baseY);
        main.paint(g, atX + l1x + l2x + l3x + l4x, atY);
    }

    // Breite des Zeichens, ohne Abstand zu Nachbarzeichen.
    public final int getWidthUncached(final Graphics g) {
        final int width = main.getWidth(g);
        final int mySize = main.getMySize();

        final int baseWidth = base.getWidth(g);

        int l1x = mySize / 10; // Länge der ersten Linie

        if (l1x < (baseWidth + 3)) {
            l1x = baseWidth + 3;
        }

        int l2x = mySize / 10; // Vertikale länge der zweiten Linie

        if (l2x < 3) {
            l2x = 3;
        }

        int l3x = mySize / 10; // Vertikale Länge der dritten Linie

        if (l3x < 3) {
            l3x = 3;
        }

        final int l4x = mySize / 20; // Verlängerung über dritte Linie hinaus, bis Unterobjekt startet
        int l5x = mySize / 20; // Verlängerung nach Unterobjekt, bis Abschlusslinie beginnt

        if (l5x < 3) {
            l5x = 3;
        }

        return l1x + l2x + l3x + l4x + width + l5x;
    }

    // Höhe über der Basislinie des Zeichens. (Basislinie ist die Unterkante eines 'a'. Der Bogen vcm 'g' liegt unterhalb der Basislinie.
    public final int getHeightAboveBaselineUncached(final Graphics g) {
        int mySize = main.getMySize();

        int height = main.getHeightAboveBaseline(g);

        if (height < mySize) {
            height = mySize;
        }

        final int l1y = (mySize * 2) / 5; // Entfernung der ersten Linie von BottomLine

        mySize = l1y + 4 + base.getHeight(g);

        if (height < mySize) {
            height = mySize;
        }

        int dist = mySize / 15;

        if (dist < 2) {
            dist = 2;
        }

        return height + dist;
    }

    // Höhe unterhalb der Basislinie, also z.B. der Bogen vom 'g'.
    public final int getHeightUnderBaselineUncached(final Graphics g) {
        final int mySize = main.getMySize();
        final int l2y = mySize / 7; // Tiefe der zweiten Linie unter BottomLine (von ende erster Linie)

        int height = main.getHeightUnderBaseline(g);

        if (height < l2y) {
            height = l2y;
        }

        return height;
    }

    public final boolean findSelectedObjects(final int currentX, 
                                             final int currentY, 
                                             final int fromX, final int fromY, 
                                             final int toX, final int toY, 
                                             final Graphics g) {
        final int mySize = main.getMySize();
        final int baseWidth = base.getWidth(g);

        int l1x = mySize / 10; // Länge der ersten Linie

        if (l1x < (baseWidth + 3)) {
            l1x = baseWidth + 3;
        }

        if (fromX < (currentX + l1x)) {
            if (toX < (currentX + l1x)) {
                final int l1y = (mySize * 2) / 5; // Entfernung der ersten Linie von BottomLine
                final int baseY = l1y + 4 + base.getHeightUnderBaseline(g);

                return base.findSelectedObjects(currentX + 1, currentY - 
                                                baseY, fromX, fromY, toX, toY, g);
            } else {
                return false;
            }
        } else if (fromX >= (currentX + l1x)) {
            if (toX >= (currentX + l1x)) {
                int l2x = mySize / 10; // Vertikale länge der zweiten Linie

                if (l2x < 3) {
                    l2x = 3;
                }

                int l3x = mySize / 10; // Vertikale Länge der dritten Linie

                if (l3x < 3) {
                    l3x = 3;
                }

                final int l4x = mySize / 20; // Verlängerung über dritte Linie hinaus, bis Unterobjekt startet

                return main.findSelectedObjects(currentX + l1x + l2x + l3x + 
                                                l4x, currentY, fromX, fromY, 
                                                toX, toY, g);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // Das Objekt (in der Regel den Cursor) nach rechts verschieben. Dabei etwa bei toYPositon positionieren
    public final void moveUp(final Basic from, final int toXPosition) {
        final Graphics g = getGraphicsHandle();

        if ((from == main) || (from == parent)) {
            base.searchObjectAtRelativeXPosition(g, toXPosition, true);
        } else {
            parent.moveUp(this, toXPosition);
        }

        // interne Verschiebung aufaddieren
    }

    // Das Objekt (in der Regel den Cursor) nach oben verschieben. Dabei etwa bei toYPosition positionieren
    public final void moveDown(final Basic from, final int toXPosition) {
        final Graphics g = getGraphicsHandle();

        //System.out.println("Position:"+toXPosition+"  - "+posMainW+","+posBaseW);
        if ((from == base) || (from == parent)) {
            main.searchObjectAtRelativeXPosition(g, toXPosition, false);
        } else {
            parent.moveDown(this, toXPosition);
        }

        // interne Verschiebung aufaddieren
    }

    public final boolean canEnterUpOrDownWithCursor() {
        return true;
    }

    public final void generateMathMLCode(final MathMLExport mmle) {
        mmle.ifNessesaryInsertInvisibleOperator();

        if (base.containsElements()) {
            mmle.addTextLine("<mroot>");

            mmle.increaseLayer();
            main.generateMathMLCode(mmle);
            base.generateMathMLCode(mmle);
            mmle.decreaseLayer();

            mmle.addTextLine("</mroot>");
        } else {
            mmle.addTextLine("<msqrt>");

            mmle.increaseLayer();
            main.generateMathMLCode(mmle);
            mmle.decreaseLayer();

            mmle.addTextLine("</msqrt>");
        }

        mmle.nextElement(true);
    }
}