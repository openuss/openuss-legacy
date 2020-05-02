/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.mathml.in;

import org.openuss.openformula.graphics.HorizontalEnter;
import org.openuss.openformula.graphics.SubSuperscript;
import org.openuss.openformula.graphics.Subscript;
import org.openuss.openformula.graphics.Superscript;


/**
 * Ein Hochgestelltes, Tiefgestelltes oder Hoch-&Tiefgestelltes Objekt einfügen.
 * Ist Bestandteil des durch den Parser erzeugten Syntaxbaumes.
 */
public final class InsertSubSuperscript extends Statement {
    private final Row main;
    private final Row sub;
    private final Row sup;

    public InsertSubSuperscript(final Row main, final Row element, 
                                final boolean subscript) {
        this.main = main;

        if (subscript) {
            sub = element;
            sup = null;
        } else {
            sup = element;
            sub = null;
        }
    }

    public InsertSubSuperscript(final Row main, final Row sub, final Row sup) {
        this.main = main;
        this.sub = sub;
        this.sup = sup;
    }

    public final void createStructure(final BuildStructure structure) {
        main.createStructure(structure);

        final HorizontalEnter veObj;

        if (sup == null) {
            veObj = new Subscript(structure.getParentObject());
            sub.createStructure(veObj.getMainStructure());
        } else if (sub == null) {
            veObj = new Superscript(structure.getParentObject());
            sup.createStructure(veObj.getMainStructure());
        } else {
            veObj = new SubSuperscript(structure.getParentObject());
            sup.createStructure(veObj.getMainStructure());
            sub.createStructure(((SubSuperscript) veObj).getBaseStructure());
        }

        structure.add(veObj);
    }
}