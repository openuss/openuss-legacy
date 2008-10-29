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
 * Den Cursor einfügen. Ist Bestandteil des durch den Parser erzeugten Syntaxbaumes.
 */
public final class InsertCursor extends Statement {
    public final void createStructure(final BuildStructure structure) {
        structure.insertCursor();
    }
}