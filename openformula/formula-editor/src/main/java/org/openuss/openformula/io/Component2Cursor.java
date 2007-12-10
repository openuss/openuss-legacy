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
 * Zeichnet die Formel in eine Componente, am besten vom Typ Canvas.
 */
public final class Component2Cursor implements CursorDistribution {
    private final Component comp;

    public Component2Cursor(final Component comp) {
        this.comp = comp;
    }

    public final void repaint() {
        comp.repaint();
    }

    public final Graphics getGraphics() {
        return comp.getGraphics();
    }

    public final void requestFocus() {
        comp.requestFocus();
    }
}