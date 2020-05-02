/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.mathml.in;

/**
 * Einen Bruch einfügen. Ist Bestandteil des durch den Parser erzeugten Syntaxbaumes.
 */
public final class InsertFraction extends Statement {
    private final Row main;
    private final Row base;

    public InsertFraction(final Row main, final Row base) {
        this.main = main;
        this.base = base;
    }

    public final void createStructure(final BuildStructure structure) {
        final org.openuss.openformula.graphics.Fraction fraction = 
                new org.openuss.openformula.graphics.Fraction(
                        structure.getParentObject());
        main.createStructure(fraction.getMainStructure());
        base.createStructure(fraction.getBaseStructure());
        structure.add(fraction);
    }
}