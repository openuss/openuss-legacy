/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.graphics.matrixmenu;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.openuss.openformula.graphics.Matrix;
import org.openuss.openformula.io.Cursor;


/**
 * Klasse zur Erzeugung des Fensters, in der die Ausdehnung einer einzuf�genden Matrix
 * einzugeben ist.
 */
public final class MatrixFrame extends Frame implements ActionListener {
    private static final long serialVersionUID = -2730246143346326287L;
    
	// private Component setFocus;
    private final TextField textX;
    private final TextField textY;
    private final Cursor cursor;

    /**
     * Fenter erzeugen
     * @param cursor Zu verwendende Cursor-Komponente
     */
    public MatrixFrame(final Cursor cursor) {
        super("Matrix erstellen...");


        //setFocus=cursor.getComponent();
        this.cursor = cursor;

        setLayout(new FlowLayout());
        textX = new TextField("2");
        textX.addActionListener(this);
        add(textX);

        add(new Label(" Zeilen x "));

        textY = new TextField("2");
        textY.addActionListener(this);
        add(textY);
        add(new Label(" Spalten "));

        final Button b = new Button("Einfügen!");
        b.addActionListener(this);
        add(b);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent e) {
                removeNotify();
                MatrixFrame.this.cursor.requestFocus();
            }
        });

        setBackground(new Color(200, 200, 200));
        pack();
        setVisible(true);
    }

    /**
     * Gibt die Komponente zurück, auf der der Fokus fixiert werden soll, wenn
     * die Komponente angezeigt wird.
     * @return Zu fixierende Komponente
     */
    public final Component giveFocus() {
        return textX;
    }

    public final void actionPerformed(final ActionEvent e) {
        //System.out.println("War hier...");
        try {
            final int x = Integer.parseInt(textX.getText());
            final int y = Integer.parseInt(textY.getText());

            if ((x < 1) | (x > 10)) {
                return;
            }

            if ((y < 1) | (y > 10)) {
                return;
            }

            cursor.insertObject(new Matrix(cursor.getParent(), x, y));
            cursor.somethingChanged();
            removeNotify();
            cursor.requestFocus();
        } catch (Exception ee) {
        }
    }
}