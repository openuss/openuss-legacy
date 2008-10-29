/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.design.language;

import java.util.Hashtable;


/**
 * Stellt Sprachtabelle f�r Formeleditor zur Verf�gung.
 */
public final class Language {
    private final Hashtable table;

    /**
     * Initialiesiert das Sprachmodul.
     * @param module
     * @see org.openuss.openformula.design.AppletButtons
     * @see org.openuss.openformula.design.ButtonField
     */
    public Language(final LanguageModule module) {
        table = new Hashtable();
        module.initLanguage(table);
    }

    /**
     * Gibt f�r den Parameter das Wort in der initialiesierten Sprache zur�ck.
     * @param symbolName Schl�ssel des Wortes
     * @return Wort in initialisierter Sprache
     */
    public final String getWord(final String symbolName) {
        final String str = (String) table.get(symbolName);

        if (str == null) {
            throw new RuntimeException("Unknown symbol name");
        }

        return str;
    }
}