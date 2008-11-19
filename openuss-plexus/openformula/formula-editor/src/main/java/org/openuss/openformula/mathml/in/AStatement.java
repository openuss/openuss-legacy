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
 * Implementiert die Behandlung eines MathML-Statements, dass inerhalb eines &lt;mrow&gt;-Block steht
 */
public final class AStatement extends InterpreterBasis {
    private final Statement statement;

    /**
     * Ein neues Statement wird gespeichert.
     * @param statement
     */
    public AStatement(final Statement statement) {
        this.statement = statement;
    }

    public final void createStructure(final BuildStructure structure) {
        statement.createStructure(structure);
    }
}