/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.io;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.openuss.openformula.design.ButtonField;
import org.openuss.openformula.design.language.Language;
import org.openuss.openformula.design.language.Translation;


/**
 * Stellt den eigentlichen Formeleditor in einem Fenster dar. Diese Routiene verfügt
 * über eine Main-Routine, mit der der Formeleditor direkt gestartet werden kann.
 */
public final class EditorFrame extends Frame {
    private static final long serialVersionUID = -6758878254221495574L;
    
	private final FormulaCanvas draw;
    private boolean closed = false;
    private Cursor outputCursor;

    /**
     * Konstruktor wird für zwei Zwecke genutzt: a) Nutzung des Formeleditors als
     * eigenständiges Java-Programm. (Ausbaubedürftig.) b) Nutzung innerhalb des Applets
     * als Editor.
     */
    public EditorFrame() {
        super("OpenUSS Formula Editor");

        setLayout(new BorderLayout());

        draw = new FormulaCanvas(false, false, 35, 10);
        add(draw, BorderLayout.CENTER);

        final ButtonField bf = new ButtonField(draw.cursor, 
                                               new Language(new Translation()));
        bf.insertMenu(this);
        add(bf, BorderLayout.NORTH);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent e) {
                close(true);
            }
        });

        addKeyListener(draw.cursor);
        draw.requestFocus();

        //draw.requestFocusInWindow();
    }

    /**
     * Hauptfunktion - erstellt die Zeichenfläche
     * @param args Argumente des Programmes - werden nicht ausgewertet
     */
    public static void main(final String[] args) {
        final EditorFrame desktop = new EditorFrame();

        desktop.setSize(750, 570);
        desktop.setVisible(true);
        desktop.toFront();
        desktop.draw.requestFocus();
    }

    /**
     *  Überträgt den Focus auf das Fenster
     */
    public final void transferFocus() {
        draw.requestFocus();
    }

    /**
     * Gibt den Cursor des Formulars zurück
     * @return Cursor-Objekt
     */
    public final Cursor getFormulaCursor() {
        return draw.cursor;
    }

    /**
     * Setzt einen neuen Cursor. Sollte mit Vorsicht genossen werden.
     * @param cursor
     */
    public final void writeOutputToCursor(final Cursor cursor) {
        outputCursor = cursor;
    }

    /**
     * Überprüft, ob das Fenster geschlossen wurde.
     * @return true, wenn das Fenster geschlossen wurde
     */
    public final boolean isClosed() {
        return closed;
    }

    /**
     * Das Fenster schließen.
     */
    public void close(boolean saveData) {
        removeAll();
        removeNotify();
        closed = true;

        if (saveData && (outputCursor != null)) {
            outputCursor.getPrimaryElement().selectAll();
            outputCursor.getPrimaryElement().deleteSelection();
            outputCursor.parse(draw.cursor.generateMathMLCode(false));
            outputCursor.repaint();
            outputCursor.getPrimaryElement().deleteCursor();
        }
    }
}