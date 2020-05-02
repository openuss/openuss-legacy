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

import org.openuss.openformula.mathml.in.BuildStructure;


/**
 * Zwischenklasse, die Objekte verwaltet, die ein Unterobjekt haben und bei einer
 * vertikalen Bewegung mit dem Cursor betreten werden können.
 */
public abstract class HorizontalEnter extends Basic {
    final Succession main;

    public HorizontalEnter(final Basic parent) {
        super(parent);
        main = new Succession(this, true);
    }

    public HorizontalEnter(final Basic parent, final int sizeClass) {
        super(parent, sizeClass);

        main = new Succession(this, true);
    }

    /**
     * Gibt ein BuildStructure-Objekt zurück, durch das auf das Haupt-Unterobjekt main zugegriffen werden
     * kann, um dies zu erweitern.
     * @return Objekt zum Zugriff auf Base
     */
    public final BuildStructure getMainStructure() {
        return main;
    }

    // Objekt kann betreten werden
    public final boolean canEnterLeftOrRighWithCursor() {
        return true;
    }

    // Cursor an Unterobjekt weitergeben
    public final void insertCursorAt(final int position) {
        main.insertCursorAt(position);
    }

    abstract public boolean findSelectedObjects(int currentX, int currentY, 
                                                int fromX, int fromY, int toX, 
                                                int toY, Graphics g);/* {
    return main.determinateSelectedObjects(currentX, currentY, fromX, fromY, toX, toY, g);
    }   */
}