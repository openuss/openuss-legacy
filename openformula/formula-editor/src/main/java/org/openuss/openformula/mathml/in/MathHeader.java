/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.mathml.in;

import org.openuss.openformula.mathml.in.java_cup.runtime.Symbol;


/**
 * Oberstes Objekt im Syntaxbaum. Ist Bestandteil des durch den Parser erzeugten Syntaxbaumes.
 */
public final class MathHeader extends InterpreterBasis {
    private final InterpreterBasis row;

    public MathHeader(final Symbol firstSymbol) {
        super(firstSymbol);
        row = null;
    }

    public MathHeader(final Symbol firstSymbol, final InterpreterBasis row) {
        super(firstSymbol);
        this.row = row;
    }

    public final void createStructure(final BuildStructure structure) {
        if (row != null) {
            row.createStructure(structure);
        }
    }
}