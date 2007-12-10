/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.mathml.in;

import org.openuss.openformula.graphics.Matrix;


/**
 * Eine Matrix einfügen. Dies ist das oberste Element der Matrix - Struktur.
 * Ist Bestandteil des durch den Parser erzeugten Syntaxbaumes.
 */
public final class InsertMatrix extends Statement {
    private final MatrixLines lines;

    public InsertMatrix(final MatrixLines lines) {
        this.lines = lines;
    }

    public final void createStructure(final BuildStructure structure) {
        final int m = lines.getWidth();
        final int n = lines.getHeight();
        final Matrix matrix = new Matrix(structure.getParentObject(), m, n);

        lines.createStructure(matrix.getMatrixBuildStructure(), 0, n - 1);

        structure.add(matrix);
    }
}