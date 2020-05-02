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
 * Eine Wurzel einfügen. Ist Bestandteil des durch den Parser erzeugten Syntaxbaumes.
 */
public final class InsertSquareRoot extends Statement {
    private final Row main;
    private final Row base;

    public InsertSquareRoot(final Row main) {
        this.main = main;
        this.base = null;
    }

    public InsertSquareRoot(final Row main, final Row base) {
        this.main = main;
        this.base = base;
    }

    public final void createStructure(final BuildStructure structure) {
        final org.openuss.openformula.graphics.Root squareroot = 
                new org.openuss.openformula.graphics.Root(
                        structure.getParentObject());
        main.createStructure(squareroot.getMainStructure());

        if (base != null) {
            base.createStructure(squareroot.getBaseStructure());
        }

        structure.add(squareroot);
    }
}