/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.mathml.in;

import org.openuss.openformula.graphics.UnderOverScript;


/**
 * Ein (grafisch) übergeordnetes und/oder untergeordnetes Objekt einfügen.
 * Ist Bestandteil des durch den Parser erzeugten Syntaxbaumes.
 */
public final class InsertUnderOver extends Statement {
    private final Row main;
    private final Row above;
    private final Row under;

    public InsertUnderOver(final Row main, final Row under, final Row above) {
        this.main = main;
        this.above = above;
        this.under = under;
    }

    public final void createStructure(final BuildStructure structure) {
        final UnderOverScript uos = new UnderOverScript(
                                            structure.getParentObject(), 
                                            above != null, under != null);
        main.createStructure(uos.getMainStructure());

        if (under != null) {
            under.createStructure(uos.getUnderStructure());
        }

        if (above != null) {
            above.createStructure(uos.getAboveStructure());
        }

        structure.add(uos);
    }
}