package freestyleLearningGroup.independent.gui;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/** Ein leeres Icon. Eine Instanz kann von der <code>FLGIconFactory</code> angefordert werden. */
public class FLGEmptyIcon implements Icon {
    private int width;
    private int height;

    /**
     * Erzeugt ein leeres Icon der spezifizierten Dimension.
     * @param width,&nbsp;height	Dimension des Icons.
     * @return das leere Icon.
     */
    public FLGEmptyIcon(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Liefert die H&ouml;he des Icons.
     * @return die H&ouml;he.
     */
    public int getIconHeight() {
        return height;
    }

    /**
     * Liefert die Breite des Icons.
     * @return die Breite.
     */
    public int getIconWidth() {
        return width;
    }

    /** Zeichnet das Icon (nicht;). */
    public void paintIcon(Component c, Graphics g, int x, int y) {
    }
}
