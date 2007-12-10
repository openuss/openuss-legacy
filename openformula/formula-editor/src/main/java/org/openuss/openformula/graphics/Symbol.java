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
 * Stellt ein Symbol, wie z.B. ein griechisches Zeichen oder einen Pfeil dar.
 */
public class Symbol extends Letter {
    //String symbolicName;
    public Symbol(final Basic parent, final char letter) {
        super(parent, letter);

        //this.symbolicName=symbolicName;
    }

    public final void modifyGraphicsHandle(final Graphics g) {
        final Font font = new Font("TimesRoman", Font.PLAIN, getMySize());
        g.setFont(font);
    }

    public final void generateMathMLCode(final MathMLExport mmle) {
        final int ch = letter;

        //mmle.addTextLine("<mi> &"+symbolicName+"; </mi>");
        if (ch == 0x22c5) {
            mmle.addTextLine("<mo>*</mo>");
            mmle.nextElement(true);
        } else if ((ch == 0x221E) || (ch == 0x2205)) // Unendlich, leere Menge sind keine Operatoren
        {
            mmle.ifNessesaryInsertInvisibleOperator();
            mmle.addTextLine("<mi>&#" + ch + ";</mi>");
            mmle.nextElement(true);
        } else {
            mmle.addTextLine("<mo>&#" + ch + ";</mo>");
            mmle.nextElement(false);
        }
    }
}