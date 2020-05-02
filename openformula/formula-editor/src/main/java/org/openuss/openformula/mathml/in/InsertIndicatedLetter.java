/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.mathml.in;

import org.openuss.openformula.graphics.Basic;
import org.openuss.openformula.graphics.Letter;
import org.openuss.openformula.graphics.LetterIndication;


/**
 * Ein markiertes hervorgehobenes Zeichen einfügen, z.B. ein a mit Hut: (�)
 * Ist Bestandteil des durch den Parser erzeugten Syntaxbaumes.
 */
public final class InsertIndicatedLetter extends Statement {
    private int type;
    private char letter;

    public InsertIndicatedLetter(final String letter, final String type) {
        if (type.equalsIgnoreCase("&DiacriticalDot;")) {
            this.type = LetterIndication.Dot;
        } else if (type.equalsIgnoreCase("&DoubleDot;")) {
            this.type = LetterIndication.DoubleDot;
        } else if (type.equalsIgnoreCase("&TripleDot;")) {
            this.type = LetterIndication.TribbleDot;
        } else if (type.equalsIgnoreCase("&Hat;")) {
            this.type = LetterIndication.Hat;
        } else if (type.equalsIgnoreCase("&OverBar;")) {
            this.type = LetterIndication.OverLine;
        } else if (type.equalsIgnoreCase("&Tilde;")) {
            this.type = LetterIndication.Tilde;
        } else {
            throw new RuntimeException("Unbekannter Typ!");
        }

        this.letter = InsertCharSymbol.parse(letter);
    }

    public final void createStructure(final BuildStructure structure) {
        final Basic parent = structure.getParentObject();
        final Letter letter = new Letter(parent, this.letter);
        final LetterIndication linc = new LetterIndication(parent, letter, type);

        structure.add(linc);
    }
}