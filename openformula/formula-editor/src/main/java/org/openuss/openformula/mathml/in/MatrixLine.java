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
 * Eine horizontale Matrixzeile. Ist Bestandteil des durch den Parser erzeugten Syntaxbaumes.
 */
public final class MatrixLine {
    private final MatrixElements elements;

    public MatrixLine(final MatrixElements elements) {
        this.elements = elements;
    }

    public final int getWidth() {
        return elements.getWidth();
    }

    public final void createStructure(final BuildStructure[][] structure, 
                                      final int width, final int height) {
        elements.createStructure(structure, width, height);
    }
}