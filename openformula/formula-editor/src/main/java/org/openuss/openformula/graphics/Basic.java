/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.graphics;

import java.awt.Graphics;

import org.openuss.openformula.io.Cursor;
import org.openuss.openformula.mathml.out.MathMLExport;


/**
 * Hauptklasse aller darzustellender grafischer Objekte des Formeleditors. <br>
 * Verwaltet z.B. die Gr��e und den Gr��enhistory, die zu verwendende Schriftart usw.
 */

// todo: �berpr�fen, welche Klassen �berhaupt ausserhalb des Packages sichtbar sein m�ssen.
// todo: public class ==> Public k�rzen
// todo: Shift-Cursor up!
public abstract class Basic {
    Basic parent;

    // Wird jeweils vom Parent bezogen - einfachste Taktik, um den Cursor schnell
    // innerhalb der Klassen zu verbreiten - und schnell verf�gbar zu machen.
    private Cursor cursor;
    private int startSize;
    private int stopSize;

    // Cache-Funktionen, dienen zur Beschleunigung, so dass die betreffenden Werte
    // nicht dauernd neu ermittelt werden m�ssen.
    // Wenn auf 0 gesetzt, dann undefiniert.
    private int cachedWidth = 0;
    private int cachedHeight = 0;
    private int cachedHeightAbove = 0;
    private int cachedHeightUnder = 0;

    //---------------------------------------------------------------------------
    // Funktionen zur Gr��enverwaltung der Objekte
    //---------------------------------------------------------------------------
    // Die folgenden Funktionen geben die Soll-Gr��e eines Zeichens in der
    // aktuelllen Gr��enklasse vor
    private int size;
    private int sizeClass;

    //---------------------------------------------------------------------------
    // Externe Funktionen
    //---------------------------------------------------------------------------
    // todo: Schnittstellen �berarbeiten
    // --Recycle Bin START (20.01.04 18:37):
    //    /**
    //     * Passt den �bergebenen Grafikkontext so an, dass einerseits die Schriftgr��e so gesetzt ist,
    //     * wie es das Objekt erwartet und anderseits Antialaisting aktiviert wird.
    //     * @param g Grafikkontext
    //     * @deprecated
    //     */
    //
    //    public static void setRenderingHints(final Graphics g) {
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)
    private RenderInterface renderHandle = null;

    /**
     * Erzeugt ein neues Basic-Objekt, die Gr��e des �bergeordneten Objektes wird beibehalten.
     * @param parent �bergeordnetes Objekt
     */
    public Basic(final Basic parent) {
        if (parent == null) {
            throw new RuntimeException(
                    "Basic nicht mit Null-Pointer initialiesieren oder richtigen Konstruktor nutzen.");
        }

        this.parent = parent;

        cursor = parent.cursor;
        startSize = parent.startSize;
        stopSize = parent.stopSize;

        setSizeClass(parent.getMySizeClass());
    }

    /**
     * Erzeugt ein neues Basic-Objekt, die Gr��e des �bergeordneten Objektes wird �bernommen und
     * um <code>sizeClass</code> Stufen verkleinert.
     * @param parent �bergeordnetes Objekt
     * @param sizeClass Das Objekt um diese Anzahl von Stufen verkleinern. Die tats�chliche Verkleinerung ist Abh�ngig von der Verkleinerung des Ursprungsobhejtes.
     */
    public Basic(final Basic parent, final int sizeClass) {
        if (parent == null) {
            throw new RuntimeException(
                    "Basic nicht mit Null-Pointer initialiesieren oder richtigen Konstruktor nutzen.");
        }

        this.parent = parent;

        cursor = parent.cursor;
        startSize = parent.startSize;
        stopSize = parent.stopSize;

        setSizeClass(sizeClass);
    }

    /**
     * Konstruktor f�r Basisobjektes des in der Hirachie am weitesten oben liegenden Objektes.<br>
     * Mit diesem kann die Gr��e des Objektes festgelegt werden.
     * @param cursor Zu verwendender Darstellungskontext
     * @param startSize Schriftgr��e bei maximal-gro�er Darstellung in Pixeln.
     * @param stopSize Schriftgr��e bei minimal-gro�er Darstellung in Pixeln.
     */
    public Basic(final Cursor cursor, final int startSize, final int stopSize) {
        this.parent = null;

        this.cursor = cursor;
        this.startSize = startSize;
        this.stopSize = stopSize;

        setSizeClass(0);
    }

    /**
     * Zeichnet die grafische Darstellung des Objketes in den �bergebenen Grafikkontext.
     * @param g Grafik-Kontext
     * @param atX Koordinatenanteil der linken Seite
     * @param atY Koordinatenanteil der Baseline
     */
    protected abstract void paint(Graphics g, int atX, int atY);

    /**
     * Informiert das Objekt und alle linear �bergerodneten Objekte, dass sich etwas
     * ver�ndert hat und die gecacheten Daten gel�scht werden m�ssen.
     */
    final void objectChanged() {
        cachedHeight = 0;
        cachedWidth = 0;
        cachedHeightAbove = 0;
        cachedHeightUnder = 0;

        if (parent != null) {
            parent.objectChanged();
        }
    }

    // --Recycle Bin START (20.01.04 18:37):
    //    /**
    //     * Beziehe das �bergeordnete Objekt. Das Objekt am weitesten oben in der Hirachie hat "null" als Parent.
    //     * @return �bergeordnetes Objekt
    //     */
    //    public final Basic getParent() {
    //        return parent;
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)

    /**
     * Das Objekt bekommt einem neuen Parent zugeordnet. Das �berliegende Objekt wird von dieser Funktion
     * nicht �ber die �nderung informiert.
     * @param parent Das neue �bergeorndete Objekt.
     **/
    final void setNewParent(final Basic parent) {
        this.parent = parent;
    }

    // --Recycle Bin START (20.01.04 18:37):
    //    /**
    //     * Das Cursor, falls m�glich, (in der Regel den Cursor) nach links verschieben.
    //     * @param shift Shift-Taste gedr�ckt ? In diesem Fall markieren
    //     */
    //    void moveLeft(final boolean shift) {
    //        if (parent != null)
    //            parent.moveLeft(shift);
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)
    // --Recycle Bin START (20.01.04 18:37):
    //    /**
    //     * Das Cursor, falls m�glich, (in der Regel den Cursor) nach links verschieben.
    //     * @param shift Shift-Taste gedr�ckt ? In diesem Fall markieren
    //     */
    //    void moveRight(final boolean shift) {
    //        if (parent != null)
    //            parent.moveRight(shift);
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)

    /**
     * Das Objekt (in der Regel den Cursor) nach oben verschieben. Dabei etwa bei
     * dem Wert von toYPositon im Koordinatensystem positionieren.
     * Dieser Wert muss durch �berliegende Objekte ggf. angepasst werden.
     * @param from Objekt der letzten Ebene, von der die Bewegung ausgeht
     * @param toXPosition Relative Verschiebung des Cursors auf der X-Achse
     */
    void moveUp(final Basic from, final int toXPosition) {
        if (parent != null) {
            parent.moveUp(this, toXPosition);
        }
    }

    /**
     * Das Objekt (in der Regel den Cursor) nach unten verschieben. Dabei etwa bei
     * dem Wert von toYPositon im Koordinatensystem positionieren.
     * Dieser Wert muss durch untere Objekte ggf. angepasst werden.
     * @param from Objekt der letzten Ebene, von der die Bewegung ausgeht
     * @param toXPosition Relative Verschiebung des Cursors auf der X-Achse
     */
    void moveDown(final Basic from, final int toXPosition) {
        if (parent != null) {
            parent.moveDown(this, toXPosition);
        }
    }

    /**
     *  Bewegt den Cursor an den Anfang des Objektes.
     */
    void moveToFirstElement() {
        if (parent != null) {
            parent.moveToFirstElement();
        }
    }

    // --Recycle Bin START (20.01.04 18:37):
    //    /**
    //     *  Bewegt den Cursor an den Ende des Objektes.
    //     */
    //
    //    void moveToLastElement() {
    //        if (parent != null)
    //            parent.moveToLastElement();
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)

    /**
     * Cursor soll Objekt von links order rechts betreten, wenn Benutzer dar�ber l�uft. (z.B. Wurzel)
     * @return True, wenn das Objekt von links oder rechts mit dem Cursor betreten werden kann.
     */
    boolean canEnterLeftOrRighWithCursor() {
        return false;
    }

    /**
     * Cursor soll Objekt von links order rechts betreten, wenn Benutzer dar�ber l�uft.
     * @return True, wenn das Objekt von oben oder unten mit dem Cursor betreten werden kann.
     */
    boolean canEnterUpOrDownWithCursor() {
        return false;
    }

    // --Recycle Bin START (20.01.04 18:37):
    //    /**
    //     * Objekt wird von Links betreten.
    //     * @param shift Shift-Taste gedr�ckt.
    //     */
    //    public static final void enterFromLeft(final boolean shift) {
    //        throw new RuntimeException("Basic:Can't enter with Cursor.");
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)
    // --Recycle Bin START (20.01.04 18:37):
    //    /**
    //     * Objekt wird von Rechts betreten.
    //     * @param shift Shift-Taste gedr�ckt.
    //     */
    //    public static final void enterFromRight(final boolean shift) {
    //        throw new RuntimeException("Basic:Can't enter with Cursor.");
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)
    // --Recycle Bin START (20.01.04 18:37):
    //    /**
    //     * Beim Cursor ein neues Objekt einf�gen. Der Cursor wird hinter diesem Objekt
    //     * positioniert.
    //     * @param insert Einzuf�gendes Objekt
    //     */
    //    void insertObjectAtCursor(final Basic insert) {
    //        if (parent != null)
    //            parent.insertObjectAtCursor(insert);
    //
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)
    // --Recycle Bin START (20.01.04 18:37):
    //    /**
    //     * Das Element links neben dem Cursor entfernen
    //     */
    //    void deleteLeftElement() {
    //        if (parent != null)
    //            parent.deleteLeftElement();
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)
    // --Recycle Bin START (20.01.04 18:37):
    //    /**
    //     * Das Element rechts neben dem Cursor entfernen
    //     */
    //    void deleteRightElement() {
    //        if (parent != null)
    //            parent.deleteRightElement();
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)
    // --Recycle Bin START (20.01.04 18:37):
    //    /**
    //     * Der Cursor wird entfernt, der Cursor-Parent-Zeiger wird dabei auf null gesetzt.
    //     */
    //    void deleteCursor() {
    //        if (parent != null)
    //            parent.deleteCursor();
    //        else
    //            throw new RuntimeException("Basic:Can't delete Cursor.");
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)
    // --Recycle Bin START (20.01.04 18:37):
    //    /**
    //     * Nachfolgend an das Objekt wird der Cursor eingef�gt.
    //     * @param obj Hinter dieses Objekt wird der Cursor positioniert. Dieses Objekt muss dem n�chsten Succession-Objekt
    //     * in der aufsteigenden Hirachie bekannt sein.
    //     */
    //    private void insertCursorAt(final Basic obj) {
    //        if (parent != null)
    //            parent.insertCursorAt(obj);
    //        else
    //            throw new RuntimeException("Basic:Can't insert Cursor.");
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)

    /**
     * Ermittelt, ob und welche Objekte bei der Mausbewegung von fromX/Y nach toX/Y erfasst wurden.
     * Gibt true zur�ck, falls das Objekt intern seperiert werden konnte,
     * ansonsten wird false zur�ckgegeben, so dass das Objekt von der aufrufenden Klasse
     * als "unteilbar" angesehen werden soll.<br>
     * (Wenn diese Methode �berschrieben wurde, kann davon ausgegangen werden, dass das Objekt
     * in irgend einer Weise unterteilt ist.)
     * @param currentX Die reale X-Position des Objektes relativ zum Fenster
     * @param currentY Die reale Y-Position des Objektes relativ zum Fenster
     * @param fromX Von dieser X-Position aus startete die Mausbewegung
     * @param fromY Von dieser Y-Position aus startete die Mausbewegung
     * @param toX An dieser X-Position endete die Mausbewegung
     * @param toY An dieser Y-Position endete die Mausbewegung
     * @param g Zu verwendender Grafikkontext
     * @return Gibt true zur�ck, falls die Mausbewegung von diesem Objekt verwaltet werden kann.
     */
    boolean findSelectedObjects(final int currentX, final int currentY, 
                                final int fromX, final int fromY, final int toX, 
                                final int toY, final Graphics g) {
        return false;
    }

    /**
     * F�gt den Cursor an der gegebenen Stelle ein. Sollte diese nicht existieren, so wird
     * eine Position gew�hlt, die m�glichst nahe an der Position liegt.
     * (Ein Wert wie Integer.MaxInt positioniert den Cursor also Maximal-Rechts.)
     * @param position Position des Cursors im verwaltenden Successionobjekt
     */
    void insertCursorAt(final int position) {
        throw new RuntimeException("Basic:Can't insert Cursor.");
    }

    /**
     * Der Cursor wird links vom Objekt eingef�gt.
     * Wird insbesondere von Succession nach MoveLeft aufgerufen, falls der Cursor an das
     * �bergeordnete Objekt abgegeben werden soll.
     * @param object Cursor links von diesem Objekt einf�gen
     */
    void insertCurorLeftFromMe(final Basic object) {
        if (parent != null) {
            parent.insertCurorLeftFromMe(this);
        }
        // Trick: this wird �bergeben - Objekt wird so korrekt identifiziert
        else {
            throw new RuntimeException("Basic:Can't insert Cursor.");
        }
    }

    /**
     * Der Cursor wird rechts vom Objekt eingef�gt.
     * Wird insbesondere von Succession nach MoveLeft aufgerufen, falls der Cursor an das
     * �bergeordnete Objekt abgegeben werden soll.
     * @param object Cursor rechts von diesem Objekt einf�gen
     */
    void insertCurorRightFromMe(final Basic object) {
        if (parent != null) {
            parent.insertCurorRightFromMe(this); // dito
        } else {
            throw new RuntimeException("Basic:Can't insert Cursor.");
        }
    }

    // Soll-Gr��e mit hilfe einer Gr��enklasse festlegen
    private void setSizeClass(final int sizeClass) {
        this.sizeClass = sizeClass;


        // Umrechnungsfunktion von Gr��enklasse zur tats�chlichen Gr��e
        size = (int) ((double) (startSize - stopSize) / Math.exp((double) sizeClass / 4.0) + 
               stopSize);
    }

    /**
     * Soll-Schriftgr��e dieses Objektes erfragen
     * @return Schriftgr��e in Pixeln
     */
    public final int getMySize() {
        return size;
    }

    /**
     * Aktuelle Gr��enKLASSE erfragen, die sich aus der Summe aller sizeClass-Anpassungen
     * ergibt, die in der Hirachie existieren.
     * @return Gr��enklasse
     */
    final int getMySizeClass() {
        return sizeClass;
    }

    // Die folgenden Funktionen bestimmen die tats�chliche Gr��e

    /**
     * Breite des Zeichens, ohne Abstand zum Nachbarzeichen.
     * @param g Zu nutzender Grafikkontext
     * @return Die horizontale Ausdehnung des Objektes in Pixeln
     */
    protected abstract int getWidthUncached(Graphics g);

    /**
     * H�he �ber der Basislinie des Zeichens.
     * (Basislinie ist die Unterkante eines 'a'. Der Bogen vcm 'g' liegt unterhalb der Basislinie.)
     * @param g Zu nutzender Grafikkontext
     * @return Die vertikale Ausdehnung des Objektes in Pixeln
     */
    protected abstract int getHeightAboveBaselineUncached(Graphics g);

    /**
     * H�he unterhalb der Basislinie des Zeichens.
     * (Basislinie ist die Unterkante eines 'a'. Der Bogen vcm 'g' liegt unterhalb der Basislinie.)
     * @param g Zu nutzender Grafikkontext
     * @return Die vertikale Ausdehnung des Objektes in Pixeln
     */
    protected abstract int getHeightUnderBaselineUncached(Graphics g);

    /**
     * Die Gesamth�he des Objektes, also einfach getHeightAboveBaseline(g) + getHeightUnderBaseline(g).
     * @param g Zu nutzender Grafikkontext
     * @return Die vertikale Ausdehnung des Objektes in Pixeln
     */
    int getHeightUncached(final Graphics g) {
        return getHeightAboveBaseline(g) + getHeightUnderBaseline(g);
    }

    /**
     * Die Breite des Objektes, wird, falls schon ermittelt, aus dem Cache zur�ckgegeben.
     * Falls unbekannt, wird getWidthUncached(Graphics g) aufgerufen.
     * @param g Zu nutzender Grafikkontext
     * @return Die horizontale Ausdehnung des Objektes in Pixeln
     */
    public final int getWidth(final Graphics g) {
        if (cachedWidth == 0) {
            cachedWidth = getWidthUncached(g);
        }

        return cachedWidth;
    }

    /**
     * Die H�he des Objektes �ber der Basislinie, wird, falls schon ermittelt, aus dem Cache zur�ckgegeben.
     * Falls unbekannt, wird getHeightAboveBaselineUncached(Graphics g) aufgerufen.
     * @param g Zu nutzender Grafikkontext
     * @return Die vertikale Ausdehnung des Objektes in Pixeln
     */
    public int getHeightAboveBaseline(final Graphics g) {
        if (cachedHeightAbove == 0) {
            cachedHeightAbove = getHeightAboveBaselineUncached(g);
        }

        return cachedHeightAbove;
    }

    /**
     * Die H�he des Objektes unterhalb der Basislinie, wird, falls schon ermittelt, aus dem Cache zur�ckgegeben.
     * Falls unbekannt, wird getHeightUnderBaselineUncached(Graphics g) aufgerufen.
     * @param g Zu nutzender Grafikkontext
     * @return Die vertikale Ausdehnung des Objektes in Pixeln
     */
    public final int getHeightUnderBaseline(final Graphics g) {
        if (cachedHeightUnder == 0) {
            cachedHeightUnder = getHeightUnderBaselineUncached(g);
        }

        return cachedHeightUnder;
    }

    /**
     * Die Gesamth�he des Objektes wird, falls schon ermittelt, aus dem Cache zur�ckgegeben.
     * Falls unbekannt, wird getHeightUncached(Graphics g) aufgerufen.
     * @param g Zu nutzender Grafikkontext
     * @return Die vertikale Ausdehnung des Objektes in Pixeln
     */
    public final int getHeight(final Graphics g) {
        if (cachedHeight == 0) {
            cachedHeight = getHeightUncached(g);
        }

        return cachedHeight;
    }

    /**
     * Passt den Grafikkontext an die aktuelle Schriftgr��e an.
     * Aktiviert, falls m�glich, Antialaisting.<br>
     * Unterscheidet zwischen Internet Explorer und Mozilla/Netscape.
     * @param g Zu modifizierender Grafikkontext
     */
    void modifyGraphicsHandle(final Graphics g) {
        if (renderHandle == null) {
            if (Cursor.isRealyOldJavaVersion()) {
                renderHandle = new IERendering(this);
            } else {
                renderHandle = new NetscapeRendering(this);
            }
        }

        renderHandle.modifyGraphicsHandle(g);
    }

    /**
     * Grafikhandle der Komponente anforden.<br>
     * Achtung: Darf nicht im Zusammenhang mit gecachten Daten verwendet werden.
     * Wird automatisch an die Schriftgr��e der aktuellen Klasse angepasst.
     * (Aufruf von modifyGraphicsHandle(g))
     * @return Grafikkontext
     */
    public Graphics getGraphicsHandle() {
        // todo: Anpassen an endg�ltige Version
        //if (cantAccessGraphicHandle != 0)
        //    throw new RuntimeException("Nicht m�glich, da �bergeordnete Objekte Cache verwenden.");
        final Graphics g = cursor.getGraphics();

        if (g == null) {
            return null;
        }

        modifyGraphicsHandle(g);

        return g;
    }

    /**
     * Gibt Cursor-Objekt zur�ck
     * @return Zentrales Cursor-Objekt
     */
    public final Cursor getCursor() {
        return cursor;
    }

    //---------------------------------------------------------------------------
    // Debug-Funktionen
    //---------------------------------------------------------------------------

    /**
     * Debugfunktion. Anzahl der Ebenen �ber der eigenen erfragen.
     * @return Anzahl der �berliegenden Ebenen
     */
    final int determinateLayer() {
        if (parent == null) {
            return 1;
        } else {
            return parent.determinateLayer() + 1;
        }
    }

    //---------------------------------------------------------------------------
    // MathMLExport-out-Funktionen
    //---------------------------------------------------------------------------

    /**
     * Bildet in dem zu �bergebenden MathMLExport-Objekt mmle die Struktur der Formel
     * als (hoffentlich) g�ltigen MathMLExport-Code ab.
     * Dieser kann anschliessend mit mmle.getText() erfragt werden.
     * @param mmle Neu instanziiertes MathMLExport-Objekt.
     */
    protected abstract void generateMathMLCode(MathMLExport mmle);

    void newLine() {
        parent.newLine();
    }
}