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
 * Created by IntelliJ IDEA.
 * User: Jan Kirchhoff
 * Date: 13.10.2003
 * Time: 19:34:56
 * To change this template use Options | File Templates.
 */
public final class Statements extends InterpreterBasis {
    private final AStatement statement;
    private final Statements next;

    public Statements(final AStatement statement) {
        this.statement = statement;
        next = null;
    }

    public Statements(final AStatement statement, final Statements next) {
        this.statement = statement;
        this.next = next;
    }

    public final void createStructure(final BuildStructure structure) {
        //System.out.println("statements");
        statement.createStructure(structure);

        if (next != null) {
            next.createStructure(structure);
        }

        //System.out.println("/statements");
    }
}