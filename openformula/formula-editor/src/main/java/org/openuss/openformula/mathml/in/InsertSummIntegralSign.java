/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.mathml.in;

import org.openuss.openformula.graphics.Basic;
import org.openuss.openformula.graphics.SummIntegralSign;
import org.openuss.openformula.io.Cursor;


/**
 * Ein Faktorzeichen, ein Summenzeichen oder ein Integralzeichen einfügen.
 * Ist Bestandteil des durch den Parser erzeugten Syntaxbaumes.
 */
public final class InsertSummIntegralSign extends Statement {
    private final char c;
    private final Row bottom;
    private final Row top;

    public InsertSummIntegralSign(final String txt, final Row bottom, 
                                  final Row top) {
        c = InsertCharSymbol.parse(txt);
        this.bottom = bottom;
        this.top = top;
    }

    public final void createStructure(final BuildStructure structure) {
        final SummIntegralSign sis;
        final Basic parent = structure.getParentObject();

        switch (c) {
        case Cursor.SumLetter:
            sis = new SummIntegralSign(parent, Cursor.SumLetter, 
                                       Cursor.SumFakPercentUsage);

            break;

        case Cursor.IntegralLetter:
            sis = new SummIntegralSign(parent, Cursor.IntegralLetter, 
                                       Cursor.IntegralPercentUsage);

            break;

        case Cursor.FaktorLetter:
            sis = new SummIntegralSign(parent, Cursor.FaktorLetter, 
                                       Cursor.SumFakPercentUsage);

            break;

        default:
            throw new RuntimeException(
                    "Nicht kompatible XML-Struktur. mrow benutzen!");
        }

        top.createStructure(sis.getTopStructure());
        bottom.createStructure(sis.getBottomStructure());

        structure.add(sis);
    }
}