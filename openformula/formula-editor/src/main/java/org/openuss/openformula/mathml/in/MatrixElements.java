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
 * Hat ein Matrixelement, weißt Verknüpfung zu nächsten Element auf. Wirkt umständlich,
 * ist aber wegen der Struktur des Parsers notwendig.
 * Ist Bestandteil des durch den Parser erzeugten Syntaxbaumes.
 */
public final class MatrixElements {
    private final MatrixElement thisone;
    private final MatrixElements next;

    public MatrixElements(final MatrixElement thisone, 
                          final MatrixElements next) {
        this.thisone = thisone;
        this.next = next;
    }

    public MatrixElements(final MatrixElement thisone) {
        this.thisone = thisone;
        this.next = null;
    }

    public final int getWidth() {
        if (next == null) {
            return 1;
        } else {
            return next.getWidth() + 1;
        }
    }

    public final void createStructure(final BuildStructure[][] structure, 
                                      final int width, final int height) {
        thisone.createStructure(structure, width, height);

        if (next != null) {
            next.createStructure(structure, width + 1, height);
        }
    }
}