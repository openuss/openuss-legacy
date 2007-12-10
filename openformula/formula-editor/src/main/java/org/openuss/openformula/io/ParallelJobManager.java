/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.io;

/**
 * Diese Klasse dient zum Umgehen von Sicherheitsbeschr�nkungen, die im Internet Explorer
 * das �bertragen des MathML-Code zum Server behindert.
 * In diesem wird der Sicherheitskontext vom Hauptthread vor�bergehend ge�ndert, wenn eine
 * Funktion des Applets von JavaScript aus aufgerufen wird. Dies macht es f�r kurze Zeit
 * unm�glich, mit externen Server zu kommunizieren.
 * Da diese �nderung des Sicherheitskontexts jedoch nicht f�r zus�tzlich erzeugte bereits
 * laufende Threads gilt, �bernimmt beim IE dieser die Daten�bertragung.
 */
public final class ParallelJobManager extends Thread {
    private final RunAsApplet app;
    private String identifier;
    private boolean doSubmit = false;
    private final int interval = 100;

    /**
     * Erzeugt einen neuen Thread, der zur Daten�bertragung beim IE ben�tigt wird.
     * @param app Das zugrunde liegende Applet
     */
    public ParallelJobManager(final RunAsApplet app) {
        this.app = app;
        this.identifier = "";

        //jobs=new ArrayList();
    }

    /**
     * Informiert den Thread, dass die Daten �bertragen werden m�ssen.
     */
    public final void submitData() {
        doSubmit = true;
        interrupt();
    }

    /**
     * Die Run-Funktion des Threads. �berpr�ft alle 100 ms, ob die Daten gesendet werden m�ssen.
     * (Bedauerlicherweise kann man den Thread nicht einfach ewig schlafen lassen und ihn aufwecken,
     * wenn man dies braucht, da dann der falsche Sicherheitskontext �bernommen wird.)
     */
    public final void run() {
        while (!doSubmit) {
            try {
                Thread.sleep(interval);
                Thread.yield();
            } catch (InterruptedException e) {
                //System.out.println("Interrupt!");
            }
        }
        //System.out.println("Sending...");
        identifier = app.sendData();

        //System.out.println("Done & Die!");
    }

    /**
     * Gibt die Identifier zur�ck. Wenn noch nicht gesendet werden konnte, ist die R�ckgabe der leere String.
     * @return Identifier der Formel
     */
    public final String getIdentifier() {
        return identifier;
    }
}