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
 * Die Klasse dient zur Darstellung von Objekten, die überhalb und unterhalb von Objekten
 * dargestellt werden.
 */
public final class UnderOverScript extends HorizontalEnter {
    /**
     * Abstand zwischen den Elementen
     */
    private final static int abstand = 2;
    private Succession under;
    private Succession above;

    /**
     * Erzeugung eines über/Unterscript-Objekt. Ein übergeordnetes Objekt wird eingezeichnet,
     * falls <code>above</code> <code>true</code> ist, ein untergeordnetes Objekt wird eingezeichnet, falls <code>unter</code> <code>true</code> ist.
     * <code>above</code> und <code>under</code> d�rfen nicht beide <code>false</code> sein.
     * @param parent übergeordnetes Objekt
     * @param above Hochgestelltes Objekt einzeichnen
     * @param under Tiefgestelltes Objekt einzeichnen
     */
    public UnderOverScript(final Basic parent, final boolean above, 
                           final boolean under) {
        super(parent);
        main.setUseCorrectSize(true);

        if ((above == false) && (under == false)) {
            throw new RuntimeException("above & under false is not possible");
        }

        if (under) {
            this.under = new Succession(this, 
                                        parent.getMySizeClass() + 
                                        Subscript.increaseSizeClass, true);
            this.under.setUseCorrectSize(true);
        } else {
            this.under = null;
        }

        if (above) {
            this.above = new Succession(this, 
                                        parent.getMySizeClass() + 
                                        Subscript.increaseSizeClass, true);
            this.above.setUseCorrectSize(true);
        } else {
            this.above = null;
        }
    }

    public final int getWidthUncached(final Graphics g) {
        int a = (above == null) ? 0 : above.getWidth(g);
        int k = (under == null) ? 0 : under.getWidth(g);
        a = (a < k) ? k : a;

        k = main.getWidth(g);
        a = (a < k) ? k : a;

        return a;
    }

    public final BuildStructure getAboveStructure() {
        return above;
    }

    public final BuildStructure getUnderStructure() {
        return under;
    }

    public final int getHeightAboveBaselineUncached(final Graphics g) {
        if (above != null) {
            return main.getHeightAboveBaseline(g) + abstand + 
                   above.getHeight(g);
        } else {
            return main.getHeightAboveBaseline(g);
        }
    }

    public final int getHeightUnderBaselineUncached(final Graphics g) {
        if (under != null) {
            return main.getHeightUnderBaseline(g) + abstand + 
                   under.getHeight(g);
        } else {
            return main.getHeightUnderBaseline(g);
        }
    }

    public final void paint(final Graphics g, final int atX, final int atY) {
        final int aw = (above == null) ? 0 : above.getWidth(g);
        final int uw = (under == null) ? 0 : under.getWidth(g);
        int w = (aw < uw) ? uw : aw;
        final int mw = main.getWidth(g);
        w = (w < mw) ? mw : w;

        main.paint(g, atX + ((w - mw) / 2), atY);

        if (above != null) {
            final int pa = main.getHeightAboveBaseline(g) + abstand + 
                           above.getHeightUnderBaseline(g);
            above.paint(g, atX + ((w - aw) / 2), atY - pa);
        }

        if (under != null) {
            final int pu = main.getHeightUnderBaseline(g) + abstand + 
                           under.getHeightAboveBaseline(g);
            under.paint(g, atX + ((w - uw) / 2), atY + pu);
        }
    }

    public final boolean findSelectedObjects(final int currentX, 
                                             final int currentY, 
                                             final int fromX, final int fromY, 
                                             final int toX, final int toY, 
                                             final Graphics g) {
        final int pa = main.getHeightAboveBaseline(g) + abstand;

        final int aw = (above == null) ? 0 : above.getWidth(g);
        final int uw = (under == null) ? 0 : under.getWidth(g);
        int w = (aw < uw) ? uw : aw;
        final int mw = main.getWidth(g);
        w = (w < mw) ? mw : w;

        if ((above != null) && (fromY < (currentY - pa))) {
            if (toY < (currentY - pa)) {
                return above.findSelectedObjects(currentX + ((w - aw) / 2), 
                                                 currentY - pa - 
                                                 above.getHeightUnderBaseline(g), 
                                                 fromX, fromY, toX, toY, g);
            } else {
                return false;
            }
        }

        final int pu = main.getHeightUnderBaseline(g) + abstand;

        if ((under != null) && (fromY > (currentY + pu))) {
            if (toY > (currentY + pu)) {
                return under.findSelectedObjects(currentX + ((w - uw) / 2), 
                                                 currentY + pu + 
                                                 under.getHeightAboveBaseline(g), 
                                                 fromX, fromY, toX, toY, g);
            } else {
                return false;
            }
        }

        if ((toY > (currentY - pa)) && (toY < (currentY + pu))) {
            return main.findSelectedObjects(currentX + ((w - mw) / 2), currentY, 
                                            fromX, fromY, toX, toY, g);
        }

        return false;
    }

    public final boolean canEnterUpOrDownWithCursor() {
        return true;
    }

    public final void moveUp(final Basic from, final int toXPosition) {
        final Graphics g = getCursor().getGraphics();
        final int aw = (above == null) ? 0 : above.getWidth(g);
        final int uw = (under == null) ? 0 : under.getWidth(g);
        int w = (aw < uw) ? uw : aw;
        final int mw = main.getWidth(g);
        w = (w < mw) ? mw : w;

        if (from == under) {
            main.searchObjectAtRelativeXPosition(g, 
                                                 toXPosition + 
                                                 ((mw - uw) / 2), true);
        } else if ((from == main) && (above != null)) {
            above.searchObjectAtRelativeXPosition(g, 
                                                  toXPosition + 
                                                  ((aw - mw) / 2), true);
        } else {
            parent.moveUp(this, toXPosition + ((w - aw) / 2));
        }
    }

    public final void moveDown(final Basic from, final int toXPosition) {
        final Graphics g = getCursor().getGraphics();
        final int aw = (above == null) ? 0 : above.getWidth(g);
        final int uw = (under == null) ? 0 : under.getWidth(g);
        int w = (aw < uw) ? uw : aw;
        final int mw = main.getWidth(g);
        w = (w < mw) ? mw : w;

        if (from == above) {
            main.searchObjectAtRelativeXPosition(g, 
                                                 toXPosition + 
                                                 ((mw - aw) / 2), false);
        } else if ((from == main) && (under != null)) {
            under.searchObjectAtRelativeXPosition(g, 
                                                  toXPosition + 
                                                  ((uw - mw) / 2), false);
        } else {
            parent.moveDown(this, toXPosition + ((w - aw) / 2));
        }
    }

    public final void generateMathMLCode(final MathMLExport mmle) {
        final String str;

        if (under == null) {
            str = "mover";
        } else if (above == null) {
            str = "munder";
        } else {
            str = "munderover";
        }

        mmle.addTextLine("<" + str + ">");

        mmle.increaseLayer();
        main.generateMathMLCode(mmle);

        if (under != null) {
            under.generateMathMLCode(mmle);
        }

        if (above != null) {
            above.generateMathMLCode(mmle);
        }

        mmle.decreaseLayer();

        mmle.addTextLine("</" + str + ">");
        mmle.nextElement(true);
    }
}