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
 * Ein einzelnes Matrixelement. Ist Bestandteil des durch den Parser erzeugten Syntaxbaumes.
 */
public final class MatrixElement {
    private final Row element;

    public MatrixElement(final Row element) {
        this.element = element;
    }

    public final void createStructure(final BuildStructure[][] structure, 
                                      final int width, final int height) {
        //System.out.println("==> "+width+","+height);
        final BuildStructure struct;

        try {
            struct = structure[width][height];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Fehlerhafte Ausdehnung der Matrix.");
        }

        element.createStructure(struct);
    }
}