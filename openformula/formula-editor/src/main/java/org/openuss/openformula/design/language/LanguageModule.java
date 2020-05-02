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
 * Basisinterface für die Sprachsteuerung.
 */
public interface LanguageModule {
    /**
     * In die übergebene Hastable m�ssen für folgende Schl�ssel die Ausdr�cke in der
     * gew�nschten Sprache als Wert eingetragen werden:
     * formula, exit, edit, undo, redo, cut, copy, insert, delete, selectAll,
     * fraction, sum, product, integral, root, matrix, startEdit, copyFormula
     * @param table Die zu bearbeitende Hashtable
     */
    public void initLanguage(Hashtable table);
}