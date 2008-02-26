package freestyleLearningGroup.independent.gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;

/**
 * Diese Klasse dient dazu aus einem vorhandenen Icon ein neues Icon
 * herzustellen. Dazu werden neben dem vorhanden Icon die Skalierungsfaktoren angegeben.
 */
public class FLGScaledIcon implements Icon {
    private Icon icon;
    private double scaleX;
    private double scaleY;

    /**
     * Erzeugt ein neues Icon, indem das &uuml;bergebene Icon mit den
     * gew&uunschten Skalierungsfaktoren vergr&ouml;ssert oder verkleinert wird.
     */
    public FLGScaledIcon(Icon icon, double scaleX, double scaleY) {
        this.icon = icon;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    /**
     * Liefert die H&ouml;he des Icons.
     * @return die H&ouml;he.
     */
    public int getIconHeight() {
        return (int)(icon.getIconHeight() * scaleY);
    }

    /**
     * Liefert die Breite des Icons.
     * @return die Breite.
     */
    public int getIconWidth() {
        return (int)(icon.getIconWidth() * scaleX);
    }

    /** Zeichnet das Icon. */
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D)g;
        g2.scale(scaleX, scaleY);
        icon.paintIcon(c, g2, x, y);
        g2.scale(1 / scaleX, 1 / scaleY);
    }
}
