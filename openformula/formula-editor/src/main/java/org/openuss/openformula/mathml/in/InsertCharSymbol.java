/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.mathml.in;

import org.openuss.openformula.io.Cursor;

/**
 * Ein Zeichen einfügen. Ist Bestandteil des durch den Parser erzeugten Syntaxbaumes.
 */
public final class InsertCharSymbol extends Statement {
	// private final String txt;

    private char c;

    public InsertCharSymbol(final String txt, final int left, final int right) {
    	//  this.txt = txt;

        c = parse(txt);

        firstSymbol = new org.openuss.openformula.mathml.in.java_cup.runtime.Symbol(0, 
                                                                                                   left, 
                                                                                                   right);
    }

    public static char parse(final String txt) {
        final char c;

        if (txt.charAt(0) == '&') {
            if (txt.charAt(1) == '#') {
                final String str = txt.substring(2, txt.length() - 1);
                c = (char) Integer.parseInt(str);
            } else {
                final String str = txt.substring(1, txt.length() - 1);

                if (str.equals("PlusMinus")) {
                    c = 0xB1;
                } else if (str.equals("lt")) {
                    c = '<';
                } else if (str.equals("gt")) {
                    c = '>';
                } else if (str.equals("quot")) {
                    c = '\"';
                } else if (str.equals("amp")) {
                    c = '&';
                } else if (str.equals("auml")) {
                    c = '�';
                } else if (str.equals("uuml")) {
                    c = '�';
                } else if (str.equals("ouml")) {
                    c = '�';
                } else if (str.equals("Auml")) {
                    c = '�';
                } else if (str.equals("Uuml")) {
                    c = '�';
                } else if (str.equals("Ouml")) {
                    c = '�';
                } else if (str.equals("InvisibleTimes")) // Nix einfügen
                {
                    c = 0;
                } else if (str.equals("szlig")) {
                    c = '�';
                } else {
                    throw new RuntimeException("Unbekanntes Symbol !");
                }
            }
        } else {
            c = txt.charAt(0);
        }

        return c;
    }

    public final void createStructure(final BuildStructure structure) {
        if (c != 0) {
            if ((c < 255) && (c != '*')) {
                structure.add(
                        new org.openuss.openformula.graphics.Letter(
                                structure.getParentObject(), c));
            } else {
                switch (c) {
                // Doppelpfeile und existiert/existiert nicht immer selbstgemacht erstellen
                case 0x2203:
                case 0x2204:
                case 0x21D0:
                case 0x21D2:
                case 0x21D4:
                case 0x22EE:
                case 0x22EF:
                case 0x22F0:
                case 0x22F1:
                    structure.add(
                            new org.openuss.openformula.graphics.PaintSymbol(
                                    structure.getParentObject(), c));

                    break;

                case '*': // Sonderbehandlung für das h�ssliche '*'
                    c = 0x22c5;

                case 0x2200:
                case 0x2208:
                case 0x2209:
                case 0x222A:
                case 0x2227:
                case 0x2228:
                case 0x2205:

                    // Java-Versions-Check
                    if (Cursor.isRealyOldJavaVersion()) {
                        structure.add(
                                new org.openuss.openformula.graphics.PaintSymbol(
                                        structure.getParentObject(), c));

                        break;
                    }

                default:
                    structure.add(
                            new org.openuss.openformula.graphics.Symbol(
                                    structure.getParentObject(), c));
                }
            }
        }
    }
}