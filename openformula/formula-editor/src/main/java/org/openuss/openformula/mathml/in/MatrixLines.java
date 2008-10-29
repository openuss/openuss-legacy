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
 * Hat eine Matrixlinie, weißt Verknüpfung zu nächsten Zeile auf. Wirkt umständlich,
 * ist aber wegen der Struktur des Parsers notwendig.
 * Ist Bestandteil des durch den Parser erzeugten Syntaxbaumes.
 */
public final class MatrixLines {
    private final MatrixLine thisone;
    private final MatrixLines next;

    public MatrixLines(final MatrixLine thisone, final MatrixLines next) {
        this.thisone = thisone;
        this.next = next;
    }

    public MatrixLines(final MatrixLine thisone) {
        this.thisone = thisone;
        this.next = null;
    }

    public final int getHeight() {
        // Bei jedem durchlaufen lassen, um die Position zu bestimmen.
        if (next == null) {
            return 1;
        } else {
            return next.getHeight() + 1;
        }
    }

    public final int getWidth() {
        return thisone.getWidth();
    }

    public final void createStructure(final BuildStructure[][] structure, 
                                      final int width, final int height) {
        thisone.createStructure(structure, width, height);

        if (next != null) {
            next.createStructure(structure, width, height - 1);
        }
    }
}