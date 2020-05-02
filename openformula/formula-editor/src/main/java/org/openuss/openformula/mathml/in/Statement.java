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
 * Created by IntelliJ IDEA.
 * User: Jan Kirchhoff
 * Date: 13.10.2003
 * Time: 20:03:14
 * To change this template use Options | File Templates.
 */
/**
 * Markierungsklasse, dient nur zur Vermeidung von Fehlern.
 * Signalisiert, dass die zugehörige Klasse ein Token innerhalb einer <mrow>-Struktur ist.
 */
public abstract class Statement extends InterpreterBasis {
    public void createStructure(final BuildStructure structure) {
        structure.insertCursor();
    }
}