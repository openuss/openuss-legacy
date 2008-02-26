package freestyleLearningGroup.independent.gui;

import javax.swing.Icon;

/** Serviceklass zur Erzeugung von Icons. */
public class FLGIconFactory {
    /** Niemand soll eine Instanz dieser Klasse erstellen. */
    private FLGIconFactory() { }

    /**
     * Liefert ein leeres Icon der spezifizierten Dimension.
     * @param width,&nbsp;height	Dimension des Icons.
     * @return das leere Icon.
     */
    public static Icon createEmptyIcon(int width, int height) {
        return new FLGEmptyIcon(width, height);
    }

    /**
     * Liefert ein neues Icon, indem das &uuml;bergebene Icon mit den
     * gew&uunschten Skalierungsfaktoren vergr&ouml;ssert oder verkleinert wird.
     */
    public static Icon createScaledIcon(Icon icon, double scaleX, double scaleY) {
        return new FLGScaledIcon(icon, scaleX, scaleY);
    }
}
