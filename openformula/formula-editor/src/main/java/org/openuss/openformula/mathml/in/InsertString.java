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
 * Einen Text einfügen. Ist Bestandteil des durch den Parser erzeugten Syntaxbaumes.
 */
public final class InsertString extends Statement {
    private final String txt;

    public InsertString(final String txt, final int left, final int right) {
        firstSymbol = new Symbol(0, left, right);
        this.txt = txt;
    }

    public final void createStructure(final BuildStructure structure) {
        final org.openuss.openformula.graphics.Basic parent = 
                structure.getParentObject();

        for (int i = 0; i < txt.length(); i++) {
            structure.add(
                    new org.openuss.openformula.graphics.Letter(
                            parent, txt.charAt(i)));
        }
    }
}