/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.io;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;


/**
 * Diese von Canvas abgeleitete Klasse dient als Darstellungfläche für die Formel
 * im Formeleditor & im Applet
 */
public final class FormulaCanvas extends Canvas {
	
    private static final long serialVersionUID = 7395449575054120748L;

	private final org.openuss.openformula.graphics.Succession primaryObject;
    
    final Cursor cursor;

    // --Recycle Bin (20.01.04 18:37): protected Timer doBlinkingCursor;
    private final boolean leftLine;

    /**
     * Erzeugt eine neue Zeichenfläche
     * @param readonly Die Zeichenfläche soll nicht bearbeitebar sein.
     * @param leftLine Links soll ein grauer Balken eingeblendet werden
     * @param startSize Gr��e des gr��ten Buchstaben in Pixeln
     * @param stopSize Buchstabengr��e bei maximaler Verkleinerung in Pixeln
     */
    public FormulaCanvas(final boolean readonly, final boolean leftLine, 
                         final int startSize, final int stopSize) {
        cursor = new Cursor(new Component2Cursor(this), startSize, stopSize);
        primaryObject = cursor.getPrimaryElement();

        this.leftLine = leftLine;

        if (!readonly) {
            cursor.activateResumeBuffer();
            primaryObject.insertCursorAt(0);

            addMouseListener(cursor);
            addMouseMotionListener(cursor);
            addKeyListener(cursor);
        } else {
            cursor.setReadonly(true);
        }
    }

    /**
     * Cursor-Objekt erfragen
     * @return Cursor-Objekt der Zeichenfläche
     */
    final Cursor getMyCursor() {
        return cursor;
    }

    // todo: in Cursor verlagern!

    /*class BlinkCursor extends Thread {
                                    
        /*public void run() {
            Graphics g = primaryObject.getGraphicsHandle();
                                    
            boolean vis = cursor.getVisiblity();
            cursor.setVisibility(!vis);
                                    
            /*if (vis)
                System.out.println("Blink off");
            else
                System.out.println("Blink on");*/
    /*if (g != null) {
        cursor.paint(g);
        // todo: Geht nicht ohne den folgenden Code, keine Ahnung, warum...
        g.setColor(Color.white);
        g.drawLine(0, 0, 3, 3);
                                    
        // graphics-Objekt aufl�sen
        g.dispose();
    } //else
        //System.out.println("Can't paint!");
                                    
    //doBlinkingCursor.schedule(new BlinkCursor(), 500);
    // Erneut eintragen. Stellt sicher, dass sich die Blink-Ereignisse nicht ansammeln k�nnen.
                                    
    //cursor.getComponent().requestFocusInWindow();
                                    
    }
                                    
    } */
    public final void paint(final Graphics g) {
        if (g == null) {
            return;
        }

        final Font font = new Font("Courier New", Font.PLAIN, 
                                   primaryObject.getMySize());
        g.setFont(font);


        //System.out.println("Repaint!");
        cursor.setVisibility(true);

        //if (doBlinkingCursor != null)
        //    doBlinkingCursor.cancel();
        //doBlinkingCursor = new Timer();
        //doBlinkingCursor.schedule(new BlinkCursor(), 500);
        //primaryObject.paint(g, 3, primaryObject.getHeightAboveBaseline(g) + 5);
        if (leftLine) {
            cursor.paint(5, 2);
            g.setColor(new Color(200, 200, 200));

            final Dimension d = getSize();
            g.fillRect(0, 0, 4, d.height - 18);

            for (int i = 0; i < 19; i++) {
                g.setColor(new Color(255 - (i * 3), 255 - (i * 3), 
                                     255 - (i * 3)));
                g.drawLine(0, d.height - i, 3, d.height - i);
            }

            //int width=cursor.getRequiredWidth();
            //int height=cursor.getRequiredHeight();
            //System.out.println("width:"+width+" height:"+height);
            //g.drawRect(5,2,cursor.getRequiredWidth(), cursor.getRequiredHeight());
        } else {
            cursor.paint();

            //int width=cursor.getRequiredWidth();
            //int height=cursor.getRequiredHeight();
            //System.out.println("width:"+width+" height:"+height);
            //g.drawRect(0,0,cursor.getRequiredWidth(), cursor.getRequiredHeight());
        }
    }
}