/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.mathml.in;

import org.openuss.openformula.graphics.*;


/**
 * Eine Klammer einfügen. Ist Bestandteil des durch den Parser erzeugten Syntaxbaumes.
 */
public final class InsertFence extends Statement {
    private final String open;
    private final String close;
    private final Row main;

    public InsertFence(final String open, final String close, final Row main) {
        this.main = main;
        this.open = open;
        this.close = close;
    }

    public final void createStructure(final BuildStructure structure) {
        final char openP;
        final char closeP;

        if (open.indexOf("open=\"") != 0) {
            throw new RuntimeException(
                    "1. Fence-Parameter fehlerhaft. Erwarte 'open'.");
        }

        if (close.indexOf("close=\"") != 0) {
            throw new RuntimeException(
                    "2. Fence-Parameter fehlerhaft. Erwarte 'close'.");
        }

        openP = open.charAt(6);
        closeP = close.charAt(7);

        final HorizontalEnter fence;
        final Basic parent = structure.getParentObject();

        if ((openP == '(') && (closeP == ')')) {
            fence = new Parenthesis(parent);
        } else if ((openP == '{') && (closeP == '}')) {
            fence = new CurlyBracket(parent, true, true);
        } else if ((openP == '{') && (closeP == ' ')) {
            fence = new CurlyBracket(parent, true, false);
        } else if ((openP == ' ') && (closeP == '}')) {
            fence = new CurlyBracket(parent, false, true);
        } else if ((openP == '|') && (closeP == '|')) {
            fence = new VerticalLine(parent);
        } else if ((openP == '[') && (closeP == ']')) {
            fence = new SquareBracket(parent);
        } else {
            throw new RuntimeException(
                    "Unbekannte oder unpassende Klammerpaare");
        }

        main.createStructure(fence.getMainStructure());

        structure.add(fence);

        //System.out.println("fence open=" + openP);
        //System.out.println("fence close=" + closeP);
    }
}