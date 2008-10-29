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
 * Diese Klasse verwaltet das Summenzeichen, das Faktorzeichen und das Integralzeichen.
 */
public final class SummIntegralSign extends Basic {
    // Dieses Objekt stellt einen Buchstaben dar.
    private final char letter;
    private final Succession top;
    private final Succession bottom;
    private final int AbstandZwischenBestandteilen = 2;
    private final int GenutzeFlaecheOberhalbProzent;
    private final int GenutzeFlaecheUnterhalbProzent = 25;

    // usedPercent - gibt den prozentsatz der tatsächlichen Nutzung der Schrifthöhe an.
    public SummIntegralSign(final Basic parent, final char letter, 
                            final int usedPercent) {
        super(parent, parent.getMySizeClass() - 1);
        this.letter = letter;
        top = new Succession(this, getMySizeClass() + 6, true);
        bottom = new Succession(this, getMySizeClass() + 6, true);
        GenutzeFlaecheOberhalbProzent = usedPercent;

        /*top.add(new Letter(top,'1'));
        top.add(new Letter(top,'2'));
        bottom.add(new Letter(top,'x'));
        bottom.add(new Letter(top,'='));
        bottom.add(new Letter(top,'1'));*/
    }

    public final Graphics getGraphicsHandle() {
        final Graphics g = super.getGraphicsHandle();
        final Font font = new Font("TimesRoman", Font.PLAIN, getMySize());
        g.setFont(font);

        return g;
    }

    public final BuildStructure getBottomStructure() {
        return bottom;
    }

    public final BuildStructure getTopStructure() {
        return top;
    }

    public final int getWidthUncached(final Graphics g) {
        final Graphics gg = getGraphicsHandle();

        final FontMetrics fm = gg.getFontMetrics();

        final int widthSym = fm.charWidth(letter);
        final int widthTop = top.getWidth(gg);
        final int widthBottom = bottom.getWidth(gg);

        int widthMax = (widthSym > widthTop) ? widthSym : widthTop;
        widthMax = (widthMax > widthBottom) ? widthMax : widthBottom;

        return widthMax;
    }

    public final int getHeightAboveBaselineUncached(final Graphics g) {
        modifyGraphicsHandle(g);

        final FontMetrics fm = g.getFontMetrics();
        final int mv = (fm.getAscent() - parent.getMySize()) / 2;

        return (((fm.getAscent() * GenutzeFlaecheOberhalbProzent) / 100) + 
               top.getHeight(g) + AbstandZwischenBestandteilen) - mv;
    }

    public final int getHeightUnderBaselineUncached(final Graphics g) {
        modifyGraphicsHandle(g);

        final FontMetrics fm = g.getFontMetrics();
        final int mv = (fm.getAscent() - parent.getMySize()) / 2;

        return ((fm.getDescent() * GenutzeFlaecheUnterhalbProzent) / 100) + 
               bottom.getHeight(g) + (2 * AbstandZwischenBestandteilen) + mv;
    }

    public final void paint(final Graphics g, final int atX, final int atY) {
        modifyGraphicsHandle(g);

        final FontMetrics fm = g.getFontMetrics();
        int abovePos = ((fm.getAscent() * GenutzeFlaecheOberhalbProzent) / 100) + 
                       top.getHeightUnderBaseline(g) + 
                       AbstandZwischenBestandteilen;
        int underPos = ((fm.getDescent() * GenutzeFlaecheUnterhalbProzent) / 100) + 
                       bottom.getHeightAboveBaseline(g) + 
                       AbstandZwischenBestandteilen;

        final int mv = (fm.getAscent() - parent.getMySize()) / 2;

        abovePos -= mv;
        underPos += mv;

        final int widthSym = fm.charWidth(letter);
        final int widthTop = top.getWidth(g);
        final int widthBottom = bottom.getWidth(g);

        int widthMax = (widthSym > widthTop) ? widthSym : widthTop;
        widthMax = (widthMax > widthBottom) ? widthMax : widthBottom;

        top.paint(g, atX + ((widthMax - widthTop) / 2), atY - abovePos);
        bottom.paint(g, atX + ((widthMax - widthBottom) / 2), atY + underPos);

        modifyGraphicsHandle(g);
        g.drawString("" + letter, atX + ((widthMax - widthSym) / 2), atY + mv);
    }

    public final boolean canEnterUpOrDownWithCursor() {
        return true;
    }

    // Das Objekt (in der Regel den Cursor) nach oben verschieben. Dabei etwa bei toYPosition positionieren
    public final void moveUp(final Basic from, final int toXPosition) {
        final Graphics gg = getGraphicsHandle();
        final FontMetrics fm = gg.getFontMetrics();

        final int widthSym = fm.charWidth(letter);
        final int widthTop = top.getWidth(gg);
        final int widthBottom = bottom.getWidth(gg);

        int widthMax = (widthSym > widthTop) ? widthSym : widthTop;
        widthMax = (widthMax > widthBottom) ? widthMax : widthBottom;

        if (from == bottom) {
            top.searchObjectAtRelativeXPosition(gg, 
                                                toXPosition - 
                                                ((widthBottom - widthTop) / 2), 
                                                true);
        } else if (from == parent) {
            top.searchObjectAtRelativeXPosition(gg, 
                                                toXPosition - 
                                                ((widthMax - widthTop) / 2), 
                                                true);
        } else {
            parent.moveUp(this, toXPosition + widthTop);
        }
    }

    // Das Objekt (in der Regel den Cursor) nach rechts verschieben. Dabei etwa bei toYPositon positionieren
    public final void moveDown(final Basic from, final int toXPosition) {
        final Graphics gg = getGraphicsHandle();
        final FontMetrics fm = gg.getFontMetrics();

        final int widthSym = fm.charWidth(letter);
        final int widthTop = top.getWidth(gg);
        final int widthBottom = bottom.getWidth(gg);

        int widthMax = (widthSym > widthTop) ? widthSym : widthTop;
        widthMax = (widthMax > widthBottom) ? widthMax : widthBottom;

        if (from == top) {
            bottom.searchObjectAtRelativeXPosition(gg, 
                                                   toXPosition - 
                                                   ((-widthBottom + widthTop) / 2), 
                                                   false);
        } else if (from == parent) {
            bottom.searchObjectAtRelativeXPosition(gg, 
                                                   toXPosition - 
                                                   ((widthMax - widthBottom) / 2), 
                                                   false);
        } else {
            parent.moveDown(this, toXPosition + widthBottom);
        }
    }

    public final boolean findSelectedObjects(final int currentX, 
                                             final int currentY, 
                                             final int fromX, final int fromY, 
                                             final int toX, final int toY, 
                                             final Graphics g) {
        final Graphics gg = getGraphicsHandle();
        gg.setColor(g.getColor());

        final FontMetrics fm = gg.getFontMetrics();

        int abovePos = (fm.getAscent() * GenutzeFlaecheOberhalbProzent) / 100; //+ AbstandZwischenBestandteilen;
        int underPos = (fm.getDescent() * GenutzeFlaecheUnterhalbProzent) / 100; //  + AbstandZwischenBestandteilen;

        final int mv = (fm.getAscent() - parent.getMySize()) / 2;

        abovePos -= mv;
        underPos += mv;

        final int widthSym = fm.charWidth(letter);
        final int widthTop = top.getWidth(gg);
        final int widthBottom = bottom.getWidth(gg);

        int widthMax = (widthSym > widthTop) ? widthSym : widthTop;
        widthMax = (widthMax > widthBottom) ? widthMax : widthBottom;

        if (fromY < (currentY - abovePos)) {
            if (toY >= (currentY - abovePos)) {
                return false;
            }

            return top.findSelectedObjects(currentX + 
                                           ((widthMax - widthTop) / 2), 
                                           currentY - abovePos - 
                                           top.getHeightUnderBaseline(gg), 
                                           fromX, fromY, toX, toY, g);
        }

        if (fromY > (currentY + underPos)) {
            if (toY <= (currentY + underPos)) {
                return false;
            }

            return bottom.findSelectedObjects(currentX + 
                                              ((widthMax - widthBottom) / 2), 
                                              currentY + underPos + 
                                              bottom.getHeightAboveBaseline(gg), 
                                              fromX, fromY, toX, toY, g);
        }

        return false;

        /* int middle = parent.getMySize() * positionAtSizePercent / 100;
                                                                        
         int mainW = main.getWidth(g);
         int baseW = base.getWidth(g);
                                                                        
                                                                        
         if (fromY < (currentY - middle)) {
             if (toY < (currentY - middle)) {
                 int width = ((mainW < baseW) ? baseW : mainW) + lineLongerThenMinWidth * 2;
                 int posMainW = (width - mainW) / 2;
                 int under = main.getHeightUnderBaseline(g);
                 return main.determinateSelectedObjects(currentX + lineLongerThenMinWidth + posMainW, currentY - middle - under - 3, fromX, fromY, toX, toY, g);
             } else
                 return false;
         } else {
             if (toY > (currentY - middle)) {
                 int width = ((mainW < baseW) ? baseW : mainW) + lineLongerThenMinWidth * 2;
                 int posBaseW = (width - baseW) / 2;
                 int above = base.getHeightAboveBaseline(g);
                 return base.determinateSelectedObjects(currentX + lineLongerThenMinWidth + posBaseW, currentY - middle + above + 3, fromX, fromY, toX, toY, g);
             } else
                 return false;
         }*/
    }

    public final void generateMathMLCode(final MathMLExport mmle) {
        mmle.ifNessesaryInsertInvisibleOperator();
        mmle.addTextLine("<munderover>");
        mmle.addTextLine("<mo>&#" + (int) letter + ";</mo>");

        mmle.increaseLayer();
        bottom.generateMathMLCode(mmle);
        top.generateMathMLCode(mmle);
        mmle.decreaseLayer();

        mmle.addTextLine("</munderover>");
        mmle.nextElement(true);
    }
}