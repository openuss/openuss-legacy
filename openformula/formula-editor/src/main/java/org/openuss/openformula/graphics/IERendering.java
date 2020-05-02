/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.graphics;

import java.awt.*;


/**
 * Bearbeitet den Grafik-Kontext so, wie es beim Internet Explorer sowie der beiliegenden
 * Java-Version 1.1 notwendig ist.
 */
public final class IERendering implements RenderInterface {
    private final Basic basic;

    /**
     * Erzeugt ein Konfigurationsobjekt für den Internet Explorer, das einem grafischen
     * Objekt zugeordnet werden kann.
     * @param basic Das zugrunde liegende Objekt
     */
    public IERendering(final Basic basic) {
        this.basic = basic;
    }

    /**
     * F�hrt die beim Internet Explorer einzig sinvolle Änderung am
     * Grafikkontext durch - stellt die Schriftgröße richtig.
     * @param g Zu bearbeitender Grafikkontext
     */
    public final void modifyGraphicsHandle(final Graphics g) {
        final Font font = new Font("Serif", Font.PLAIN, basic.getMySize());
        g.setFont(font);
    }
}