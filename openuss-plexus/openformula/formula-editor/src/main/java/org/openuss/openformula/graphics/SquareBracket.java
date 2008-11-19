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
 * Stellt die eckige Klammer dar.
 */
public final class SquareBracket extends Parenthesis {
    public SquareBracket(final Basic parent) {
        super(parent);
    }

    public final void paint(final Graphics g, final int atX, final int atY) {
        final int sizeY = main.getHeight(g);
        final int sizeX = main.getWidth(g);

        final int biegungsHoehe = getMySize() / 3;
        double k = biegungsHoehe / 2;

        if (k < 2) {
            k = 2;
        }

        final int dfX = 1;
        final int dY = main.getHeightUnderBaseline(g);
        final int dsX = sizeX + (2 * (int) k) + 1;

        main.paint(g, atX + (int) k + 1, atY);


        // Left
        g.drawLine(atX + dfX, (atY + dY) - sizeY, atX + dfX, atY + dY);
        g.drawLine(atX + dfX + 1, (atY + dY) - sizeY, atX + dfX + 1, atY + dY);

        g.drawLine(atX + dfX, atY + dY, atX + dfX + (int) k, atY + dY);
        g.drawLine(atX + dfX, (atY + dY) - 1, atX + dfX + (int) k, 
                   (atY + dY) - 1);

        g.drawLine(atX + dfX, (atY + dY) - sizeY, atX + dfX + (int) k, 
                   (atY + dY) - sizeY);
        g.drawLine(atX + dfX, (atY + dY + 1) - sizeY, atX + dfX + (int) k, 
                   (atY + dY + 1) - sizeY);


        // Right
        g.drawLine(atX + dsX, (atY + dY) - sizeY, atX + dsX, atY + dY);
        g.drawLine(atX + dsX + 1, (atY + dY) - sizeY, atX + dsX + 1, atY + dY);

        g.drawLine(atX + dsX, atY + dY, (atX + dsX) - (int) k, atY + dY);
        g.drawLine(atX + dsX, (atY + dY) - 1, (atX + dsX) - (int) k, 
                   (atY + dY) - 1);

        g.drawLine(atX + dsX, (atY + dY) - sizeY, (atX + dsX) - (int) k, 
                   (atY + dY) - sizeY);
        g.drawLine(atX + dsX, (atY + dY + 1) - sizeY, (atX + dsX) - (int) k, 
                   (atY + dY + 1) - sizeY);
    }

    public final void generateMathMLCode(final MathMLExport mmle) {
        mmle.ifNessesaryInsertInvisibleOperator();

        mmle.addTextLine("<mfenced open=\"[\" close=\"]\">");

        mmle.increaseLayer();
        main.generateMathMLCode(mmle);
        mmle.decreaseLayer();

        mmle.addTextLine("</mfenced>");
        mmle.nextElement(true);
    }
}