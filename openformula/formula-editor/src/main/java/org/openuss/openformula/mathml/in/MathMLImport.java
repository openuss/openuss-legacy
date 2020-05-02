/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.mathml.in;

import java.io.Reader;
import java.io.StringReader;


/**
  * Diese Klasse ist für das Interpretieren von MathML-Code zust�ndig. Dazu wird der übergebene
 * Code gescannt und geparst, anschliessend wird der dabei entstandene Syntaxbaum in die
 * Objektstrutkut des Formeleditors überf�hrt.
  */
public final class MathMLImport {
    /**
     * Den übergebenen mathml-Code übersetzen und die zur Abbildung notwendigen
     * Objekte in die BuildStructure einfügen.
     * @param str Zu parsender mathml-Code
     * @param structure Objekt, in das die aufzubauende Struktur eingef�gt werden kann.
     */
    public static void parseMathML(String str, final BuildStructure structure) {
        // EditorFrame...
        if ((str == null) || str.equals("")) {
            return;
        } else {
            // Header-M�ll entfernen
            int i = str.indexOf("<math");

            if (i == -1) {
                return;
            }

            str = str.substring(i);

            i = str.indexOf("</math>");
            str = str.substring(0, i + 7);

            if (i == -1) {
                return;
            }
        }

        // Scannen...
        final Scanner myScanner = new Scanner(
                                          new UnicodeEscapes(
                                                  (Reader) new StringReader(str)));
        final parser myParser = new parser(myScanner);

        try {
            final InterpreterBasis basis = (InterpreterBasis) myParser.parse().value;
            basis.createStructure(structure);
        } catch (Exception e) {
            //System.out.println("War nix:" + e.getMessage());
            e.printStackTrace();
        }
    }
}