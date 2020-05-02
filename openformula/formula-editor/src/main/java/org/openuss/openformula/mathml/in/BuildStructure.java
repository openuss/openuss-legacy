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
 * Bietet die Schnittstelle zum Succession-Objekt, in das mit diesen Befehlen
 * Elemente eingefügt werden k�nnen.
 */
public interface BuildStructure {
    /**
     * Das gegebene Element in der Struktur einfügen.
     * @param element
     */
    public void add(org.openuss.openformula.graphics.Basic element);

    /**
     * Das übergeordnete Parent-Objekt erfragen.
     * @return Das Parent-Objekt
     */
    public org.openuss.openformula.graphics.Basic getParentObject();

    /**
     * Der Cursor soll hier später eingefügt werden. Wird intern genutzt.
     */
    public void insertCursor();
}