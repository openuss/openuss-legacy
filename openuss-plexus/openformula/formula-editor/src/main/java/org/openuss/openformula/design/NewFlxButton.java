/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.design;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.openuss.openformula.graphics.InsertSymbol;
import org.openuss.openformula.graphics.Succession;
import org.openuss.openformula.io.Component2Cursor;
import org.openuss.openformula.io.Cursor;
import org.openuss.openformula.mathml.in.MathMLImport;


/**
 * Erzeugt die im eigentliche Formeleditor genutzten Buttons, bei denen die
 * grafische Komponente im oberen Teil dargestellt wird. <br>
 * Auf Wunsch kann im unteren Teil eine Beschreibung angegeben werden.<br>
 * Die grafische Komponente wird durch MathML-Code beschrieben.<br>
 * Es findet keine Überprüfung statt, ob die Komponente eingezeichnet werden kann.
 */
public final class NewFlxButton extends Canvas implements MouseListener {
    private static final long serialVersionUID = -2738227748833975846L;

	private final InsertSymbol iSymbol;

    //Cursor cursor;
    private final Succession primaryObject;
    private final String descr;
    private final int sizeX;
    private final int sizeY;
    private boolean pressed;

    // --Recycle Bin START (20.01.04 18:35):
    //    /**
    //     * Erzeugt einen der oben beschriebenen Buttons.
    //     * @param mathMLCode Der MathML-Code, der den Button beschreibt.
    //     * @param descr Die Beschreibung des Buttons. Wird im unteren Teil des Buttons zentriert angezeigt.
    //     * @param sizeX Horizontale Ausdehnung
    //     * @param sizeY Vertikale Ausdehnung
    //     */
    //    public NewFlxButton(final String mathMLCode, final String descr, final int sizeX, final int sizeY) {
    //
    //        final Cursor cursor = new Cursor(new Component2Cursor(this), 20, 5);
    //        primaryObject = cursor.getPrimaryElement();
    //        primaryObject.insertCursorAt(0);
    //        MathMLImport.parseMathML(mathMLCode, primaryObject.getInsertAtCursorStructure());
    //
    //        this.descr = descr;
    //        this.sizeX = sizeX;
    //        this.sizeY = sizeY;
    //
    //        setSize(sizeX, sizeY);
    //        addMouseListener(this);
    //        pressed = false;
    //
    //        iSymbol = null;
    //    }
    // --Recycle Bin STOP (20.01.04 18:35)

    /**
     * Erzeugt einen der oben beschriebenen Buttons.
     * @param mathMLCode Der MathML-Code, der den Button beschreibt.
     * @param descr Die Beschreibung des Buttons. Wird im unteren Teil des Buttons zentriert angezeigt.
     * @param sizeX Horizontale Ausdehnung
     * @param sizeY Vertikale Ausdehnung
     * @param symbol Vorgagang, der bei Betätigung dieses Knopfes ausgelöst werden soll.
     */
    public NewFlxButton(final String mathMLCode, final String descr, 
                        final int sizeX, final int sizeY, 
                        final InsertSymbol symbol) {
        final Cursor cursor = new Cursor(new Component2Cursor(this), 20, 5);
        primaryObject = cursor.getPrimaryElement();
        primaryObject.insertCursorAt(0);
        MathMLImport.parseMathML(mathMLCode, 
                                 primaryObject.getInsertAtCursorStructure());


        //descr="";
        this.descr = descr;
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        setSize(sizeX, sizeY);
        addMouseListener(this);
        pressed = false;

        iSymbol = symbol;
    }

    public final void paint(final Graphics g) {
        super.paint(g);

        final Dimension d = getSize();


        //d.width-=50;
        //d.height-=50;
        g.setColor(new Color(200, 200, 200));
        g.fillRect(0, 0, d.width, d.height);

        if (!pressed) {
            g.setColor(new Color(60, 60, 60));
        } else {
            g.setColor(new Color(240, 240, 240));
        }

        g.drawLine(d.width - 1, d.height - 1, d.width - 1, 0);
        g.drawLine(d.width - 1, d.height - 1, 0, d.height - 1);

        g.setColor(new Color(130, 130, 130));

        if (!pressed) {
            g.drawLine(d.width - 2, d.height - 2, d.width - 2, 1);
            g.drawLine(d.width - 2, d.height - 2, 1, d.height - 2);

            g.setColor(new Color(240, 240, 240));
        } else {
            g.drawLine(1, 1, d.width - 2, 1);
            g.drawLine(1, 1, 1, d.height - 2);

            g.setColor(new Color(60, 60, 60));
        }

        g.drawLine(0, 0, d.width - 2, 0);
        g.drawLine(0, 0, 0, d.height - 2);

        g.setFont(new Font("SansSerif", Font.PLAIN, 15));

        final FontMetrics fm = g.getFontMetrics();
        int ha;
        int hu;

        if (descr.equals("")) {
            ha = 0;
            hu = 0;
        } else {
            ha = fm.getAscent();
            hu = fm.getDescent();
        }

        final int strposX;
        final int strposY;
        final int strheight;

        final int dw = fm.stringWidth(descr);

        if (dw < (sizeX - 5)) {
            strposX = (sizeX - dw) / 2;
        } else {
            strposX = 2;
        }

        strposY = sizeY - hu - 2;
        strheight = ha + hu + 2;

        g.setColor(Color.darkGray);

        final int delta = pressed ? 1 : 0;

        g.drawString(descr, strposX + delta, strposY + delta);


        //g.drawLine(5, d.height-ha-hu-2, d.width-5, d.height-ha-hu-2 );
        ha = primaryObject.getHeightAboveBaseline(g);
        hu = primaryObject.getHeightUnderBaseline(g);

        g.setColor(new Color(160, 160, 160));

        primaryObject.paint(g, 
                            ((d.width - primaryObject.getWidth(g)) / 2) + 
                            delta, 
                            ((d.height - strheight - (ha + hu)) / 2) - 1 + ha + 
                            delta);

        g.setColor(Color.black);

        primaryObject.paint(g, 
                            ((d.width - primaryObject.getWidth(g)) / 2) - 1 + 
                            delta, 
                            (((d.height - strheight - (ha + hu)) / 2) + ha) - 2 + 
                            delta);
    }

    public final void mouseClicked(final MouseEvent e) {
    }

    public final void mouseEntered(final MouseEvent e) {
    }

    public final void mouseExited(final MouseEvent e) {
        if (pressed) {
            pressed = false;
            repaint();
        }
    }

    public final void mousePressed(final MouseEvent e) {
        pressed = true;
        repaint();
    }

    public final void mouseReleased(final MouseEvent e) {
        if (pressed && (iSymbol != null)) {
            iSymbol.InsertNow();
        }

        repaint();
        pressed = false;
    }
}