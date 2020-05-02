/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.io;

import java.awt.*;


/**
 * Stellt eine Schnittstelle zwischen Cursor und verschiedenen grafischen Komponenten dar.
 * Erm�glicht das Rendern in ein Bild oder die Darstellung in einmen FormulaCanvas o.a.
 */
public interface CursorDistribution {
    /**
     * Die Zeichenfläche der grafischen "Komponente" muss neu erstellt werden.
     * Dazu muss sie z.B. die Paint-Methode ihrer Haupt-Succession-Komponente aufrufen.
     */
    public void repaint();

    /**
     * Der Grafik-Handle der Komponente muss erzeugt und zurückgegeben werden
     * @return Zugrundeliegender Grafikkontext, mit dem Cursor arbeiten soll
     */
    public Graphics getGraphics();

    /**
     * Falls es sich bei der "Komponente" um ein Objekt der Klasse Component handelt,
     * so soll sie bei Aufruf den Focus anfordern.
     */
    public void requestFocus();
}