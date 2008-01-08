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
 * Ein Row-Element, also eine Kollektion von einem oder mehreren verschiedenen Symbolen.
 * Ist Bestandteil des durch den Parser erzeugten Syntaxbaumes.
 */
public final class Row extends InterpreterBasis {
    private final Statements statements;

    public Row(final Symbol firstSymbol) {
        super(firstSymbol);
        statements = null;
    }

    public Row(final Symbol firstSymbol, final Statements statements) {
        super(firstSymbol);
        this.statements = statements;
    }

    public final void createStructure(final BuildStructure structure) {
        //System.out.println("row");
        if (statements != null) {
            statements.createStructure(structure);
        }

        //System.out.println("/row");
    }
}