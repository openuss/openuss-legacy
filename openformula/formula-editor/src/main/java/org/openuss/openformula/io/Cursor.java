/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.io;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.Vector;

import org.openuss.openformula.graphics.Basic;
import org.openuss.openformula.graphics.CurlyBracket;
import org.openuss.openformula.graphics.Fraction;
import org.openuss.openformula.graphics.Letter;
import org.openuss.openformula.graphics.LetterIndication;
import org.openuss.openformula.graphics.PaintSymbol;
import org.openuss.openformula.graphics.Parenthesis;
import org.openuss.openformula.graphics.Root;
import org.openuss.openformula.graphics.SquareBracket;
import org.openuss.openformula.graphics.Subscript;
import org.openuss.openformula.graphics.Succession;
import org.openuss.openformula.graphics.SummIntegralSign;
import org.openuss.openformula.graphics.Superscript;
import org.openuss.openformula.graphics.Symbol;
import org.openuss.openformula.graphics.TestCross;
import org.openuss.openformula.graphics.UnderOverScript;
import org.openuss.openformula.mathml.in.MathMLImport;
import org.openuss.openformula.mathml.out.MathMLExport;


/**
 * Verwaltet den Cursor, dient ausserdem als Listener des Objektes für Tasten- und Mauseingaben.
 * Tasten- und Mauseingaben sollen also direkt an dieses Objekt weitergeleitet werden,
 * da sie hier korrekt verarbeitet werden können.
 */
public final class Cursor implements KeyListener, MouseListener,
                                     MouseMotionListener {
    // Stellt den Cursor optisch da, verwaltet Eingaben über Tastatur und Maus

    /**
     * Unicode-Nummer für Summenzeichen (es gibt mehrere, dieses sieht am besten aus.)
     */
    public final static char SumLetter = (char) 0x3A3;

    /**
     * Unicode-Nummer für Faktorzeichen
     */
    public final static char FaktorLetter = (char) 0x3A0;

    /**
     * Tats�chliche Nutzung der Höhe des Zeichens für Summe und Faktor
     */
    public final static int SumFakPercentUsage = 77;

    /**
     * Unicode-Nummer für Integralzeichen
     */
    public final static char IntegralLetter = (char) 0x222B;

    /**
     * Tats�chliche Nutzung der H�he des Zeichens für das Integral
     */
    public final static int IntegralPercentUsage = 100;

    /**
     * Realisation der Zwischenablage:
     * Da Browser beim Starten eines Appletes nur ein mal das Applet selbst in den Speicher laden
     * kann über static-Strings kommuniziert werden, da alle Applets auf den gleichen String zugreifen
     * ==> Perfekt für eine einfache Relaisation der Zwischenablage... :-)
     */
    public static String Zwischenablage;

    // Java-Version wurde schon bestimmt.
    private static boolean knowJavaVersion = false;

    // Ist veraltete Java-Version - gewisse Funktionen stehen nicht zur
    // Verf�gung und m�ssen umgangen werden.
    private static boolean realyOldJavaVesion = true;
    private final Succession mainElement;

    // Koordinaten des Cursors
    //protected int currentX, currentY;
    // Koordinaten des unterliegenden Striches
    //protected int underlineFromX, underlineToX;
    private int objLeftX = -1;
    private int objTopY = -1;
    private int objRightX = -1;
    private int objBottomY = -1;
    private int cursorPosX = -1;

    // --Recycle Bin (20.01.04 18:37): private int bottomLineY=-1;
    // Zur Zeit Sichtbar - der Cursor als grafische Komponente
    private boolean visible;
    private boolean readonly;
    private final CursorDistribution comp;

    // --Recycle Bin (20.01.04 18:37): protected String lastXMLCode;
    //final int sizeAbove = 33;
    //final int sizeUnder = 5;
    private final String allowedLetters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890=|!%/\\#*+~,.;:-)(<> []{}?������'";
    private int mouseFromX;
    private int mouseFromY;
    private Succession parent;
    private Vector undoBuffer;
    private Vector redoBuffer;

    // --Recycle Bin (20.01.04 18:37): protected final int returning = 0;
    // --Recycle Bin (20.01.04 18:37): protected Language language;

    /**
     * Erzeugt ein neues Cursor-Objekt, das auf dem gegebenen Ausgabe-Interface zeichnet.
     * @param comp Ausgabe-Interface
     */
    public Cursor(final CursorDistribution comp) {
        this.comp = comp;


        //comp.addFocusListener(this);
        parent = new Succession(this, true);
        mainElement = parent;

        mouseFromX = -1;
        mouseFromY = -1;

        visible = true;
        readonly = false;
    }

    /**
     * Erzeugt ein neues Cursor-Objekt, das auf dem gegebenen Ausgabe-Interface zeichnet.
     * Die Gr��e der Darstellung kann festgelegt werden.
     * @param comp Ausgabe-Interface
     * @param startSize Schriftgr��e bei maximal-gro�er Darstellung in Pixeln.
     * @param stopSize Schriftgr��e bei minimal-gro�er Darstellung in Pixeln.
     */
    public Cursor(final CursorDistribution comp, final int startSize, 
                  final int stopSize) {
        this.comp = comp;


        //comp.addFocusListener(this);
        parent = new Succession(this, true, startSize, stopSize);
        mainElement = parent;

        mouseFromX = -1;
        mouseFromY = -1;

        visible = true;
        readonly = false;
    }

    /**
     * Den Cursor als Linie zeichen. Diese Linie wird - abh�ngig vom Blinkstatus -
     * entweder schwarz oder wei� gezeichnet. (Blinken ist zur Zeit nicht aktiviert.)
     * @param g Grafikkontext
     */
    private void paint(final Graphics g) {
        if (readonly) {
            return;
        }

        if (cursorPosX < 0) {
            return;
        }

        g.setColor(visible ? Color.black : Color.white);


        //g.drawLine(currentX,currentY-sizeAbove,currentX,currentY+sizeUnder);
        //int sizeAbove = parent.getHeightAboveBaseline(g);
        //int sizeUnder = parent.getHeightUnderBaseline(g);
        g.fillRect(cursorPosX, objTopY, 1, objBottomY - objTopY);

        g.fillRect(objLeftX, objBottomY + 2, objRightX - objLeftX, 1);


        //System.out.println(""+currentX+" "+currentY);
        //currentX++;
        g.setColor(Color.black);
    }

    /**
     * Position des Cursors festlegen
     * @param atX X-Position des Cursors
     * @param atY Y-Position des Cursors
     * @param fromX Startpunkt der Unterlinie
     * @param toX Endpunkt der Unterlinie
     */
    public final void setPositon(final int objLeftX, final int objTopY, 
                                 final int objRightX, final int objBottomY, 
                                 final int cursorPosX, final int bottomLineY) {
        this.objLeftX = objLeftX;
        this.objTopY = objTopY;
        this.objRightX = objRightX;
        this.objBottomY = objBottomY;

        this.cursorPosX = cursorPosX;


        //this.bottomLineY=bottomLineY;
        visible = true;
    }

    /**
     * Festlegen, ob der Cursor zur Zeit sichtbar ist. (==> Blinkender Cursor)
     * @param visible true : Cursor sichtbar
     */
    public final void setVisibility(final boolean visible) {
        this.visible = visible;
    }

    // --Recycle Bin START (20.01.04 18:37):
    //    /**
    //     * Erfragen, ob der Cursor zur Zeit dargestellt wird.
    //     * @return true : Cursor wird - falls m�glich - eingezeichnet.
    //     */
    //
    //    public final boolean getVisiblity() {
    //        return visible;
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)

    /**
     * Erfragt, ob die zurückgegebenen Daten gecachet werden sollen
     * @return true : Cache benutzen
     */

    /*public boolean isDrawCached() {
        return DrawCached;
    } */

    /**
     * Festlegen, ob Bilder gecachet werden sollen.
     * @param drawCached true : Cache benutzen
     */

    /*public void setDrawCached(boolean drawCached) {
        DrawCached = drawCached;
    }*/

    /**
     * Das Objekt einem neuen "Parent" zuordnen - also das Objekt, das den Cursor zur Zeit hat.
     * Das Objekt, dass den Cursor bisher hatte, wird von dieser Funktion über die �nderung informiert.
     * @param parent Neues
     */
    public final void setNewParent(final Succession parent) {
        if ((this.parent != null) && (this.parent != parent)) {
            this.parent.deleteCursor();
        }

        this.parent = parent;
    }

    /**
     * An der Cursorposition wird dieses Objekt eingefügt.
     * @param object
     */
    public final void insertObject(final Basic object) {
        parent.insertObjectAtCursor(object);
    }

    /**
     * Erfragt, ob der Cursor als ReadOnly markiert ist.
     * @return true, wenn ReadOnly
     */
    public final boolean isReadonly() {
        return readonly;
    }

    /**
     * Setzt die Eigenschaft von "ReadOnly". Der Cursor wird nicht mehr dargestellt.
     * Sonstige Änderungen gibt es nicht, externen Komponenten sollten beachten,
     * nun keine Benutzereingaben mehr an diese Komponenten durchzustellen.
     * Einige Komponenten zeichnen sich mit diesem Flag etwas anders.
     * @param readonly Auf True wird Read-Only-Modus aktiviert.
     */
    public final void setReadonly(final boolean readonly) {
        this.readonly = readonly;
    }

    public final void keyPressed(final KeyEvent e) {
        final int code = e.getKeyCode();

        // Wir gehen zuerst davon aus, dass sich was �ndert.
        // Wenn die Taste nicht ausgewertet wird, �ndern wir diesen Status.
        // Achtung: Bei �nderungen beachten, dass nicht nur der Cursor,
        // sondern auch gewisse Markierungen/Auswahlen ber�cksichtigt werden
        boolean somethingChanged = true;
        final int mod = e.getModifiers();

        if (mod == 0) { // Keine Sondertaste wie SHIFT gedr�ckt

            switch (code) {
            case KeyEvent.VK_RIGHT:
                parent.moveRight(false);
                somethingChanged = false;
                repaint();

                break;

            case KeyEvent.VK_LEFT:
                parent.moveLeft(false);
                somethingChanged = false;
                repaint();

                break;

            case KeyEvent.VK_UP:
                parent.moveUpWithCursorCheck(parent, 0);
                somethingChanged = false;
                repaint();

                break;

            case KeyEvent.VK_DOWN:
                parent.moveDownWithCursorCheck(parent, 0);
                somethingChanged = false;
                repaint();

                break;

            case KeyEvent.VK_BACK_SPACE:
                parent.deleteLeftElement();

                break;

            case KeyEvent.VK_DELETE:
                parent.deleteRightElement();

                break;

            case KeyEvent.VK_HOME:
                parent.moveToFirstElement();
                somethingChanged = false;
                repaint();

                break;

            case KeyEvent.VK_END:
                parent.moveToLastElement();
                somethingChanged = false;
                repaint();

                break;

            case KeyEvent.VK_ENTER:
                parent.newLine();
                repaint();

                break;

            default:
                somethingChanged = false;
            }
        } else if (mod == InputEvent.SHIFT_MASK) {
            switch (code) {
            case KeyEvent.VK_RIGHT:
                parent.moveRight(true);
                somethingChanged = false;
                repaint();

                break;

            case KeyEvent.VK_LEFT:
                parent.moveLeft(true);
                somethingChanged = false;
                repaint();

                break;

            /*case KeyEvent.VK_UP:
                parent.moveUpWithShift();
                somethingChanged = false;
                repaint();
                break;*/
            case KeyEvent.VK_BACK_SPACE:
                parent.deleteLeftElement();

                break;

            case KeyEvent.VK_DELETE:
                parent.deleteRightElement();

                break;

            default:
                somethingChanged = false;
            }
        } else if (mod == InputEvent.CTRL_MASK) {
            switch (code) {
            case KeyEvent.VK_W:
                parent.insertObjectAtCursor(new Root(parent));

                break;

            case KeyEvent.VK_T:
                parent.insertObjectAtCursor(new Subscript(parent));

                break;

            case KeyEvent.VK_H:
                parent.insertObjectAtCursor(new Superscript(parent));

                break;

            case KeyEvent.VK_B:
                parent.insertObjectAtCursor(new Fraction(parent));

                break;

            case KeyEvent.VK_S:
                parent.insertObjectAtCursor(
                        new SummIntegralSign(parent, SumLetter, 
                                             SumFakPercentUsage));

                break;

            case KeyEvent.VK_I:
                parent.insertObjectAtCursor(
                        new SummIntegralSign(parent, IntegralLetter, 
                                             IntegralPercentUsage));

                break;

            case KeyEvent.VK_F:
                parent.insertObjectAtCursor(
                        new SummIntegralSign(parent, FaktorLetter, 
                                             SumFakPercentUsage));

                break;

            case KeyEvent.VK_Q:
                parent.insertObjectAtCursor(
                        new TestCross(parent, parent.getMySize()));

                break;

            case KeyEvent.VK_K:
                parent.insertObjectAtCursor(new Parenthesis(parent));

                break;

            case KeyEvent.VK_E:
                parent.insertObjectAtCursor(new SquareBracket(parent));

                break;

            case KeyEvent.VK_G:
                parent.insertObjectAtCursor(
                        new CurlyBracket(parent, true, true));

                break;

            /*  case KeyEvent.VK_M:
                  parent.insertObjectAtCursor(new Matrix(parent, 2, 2));
                  break;*/
            /*case KeyEvent.VK_X:
                lastXMLCode = generateMathMLCode();
                System.out.println(lastXMLCode);
                break;*/
            /*case KeyEvent.VK_Y:
                MathMLImport.parseMathML(lastXMLCode, parent.getInsertAtCursorStructure());
                break;*/
            case KeyEvent.VK_U:
                parent.insertObjectAtCursor(
                        new UnderOverScript(parent, true, false));

                break;

            case KeyEvent.VK_D:

                final Basic b = parent.getObjectAtCursor();

                if (b instanceof Letter) {
                    parent.exchangeObject(b, 
                                          new LetterIndication(parent, 
                                                               (Letter) b, 
                                                               LetterIndication.Tilde));
                }

                break;

            // Kopieren...
            case KeyEvent.VK_C:
                somethingChanged = false;
                Zwischenablage = generateMathMLCodeFromSelected();

                //System.out.println(Zwischenablage);
                break;

            // Entfernen
            case KeyEvent.VK_X:
                Zwischenablage = generateMathMLCodeFromSelected();
                parent.deleteSelection();

                //System.out.println(Zwischenablage);
                break;

            // Einfügen
            case KeyEvent.VK_V:
                parent.deleteSelection();
                MathMLImport.parseMathML(Zwischenablage, 
                                         parent.getInsertAtCursorStructure());

                break;

            // Alle Markieren
            case KeyEvent.VK_A:
                mainElement.selectAll();
                somethingChanged = false;
                repaint();

                break;

            // R�ckg�ngig
            case KeyEvent.VK_Z:

                if (undoBuffer != null) {
                    final int last = undoBuffer.size() - 1;
                    somethingChanged = false;

                    if (last < 1) {
                        break;
                    }

                    redoBuffer.addElement(undoBuffer.elementAt(last));
                    undoBuffer.removeElementAt(last);

                    mainElement.selectAll();
                    mainElement.deleteSelection();

                    MathMLImport.parseMathML(
                            (String) undoBuffer.elementAt(last - 1), 
                            mainElement);

                    repaint();
                }

                break;

            // Wiederherstellen:
            case KeyEvent.VK_Y:

                if (undoBuffer != null) {
                    final int last = redoBuffer.size() - 1;
                    somethingChanged = false;

                    if (last < 0) {
                        break;
                    }

                    mainElement.selectAll();
                    mainElement.deleteSelection();

                    MathMLImport.parseMathML(
                            (String) redoBuffer.elementAt(last), mainElement);

                    undoBuffer.addElement(redoBuffer.elementAt(last));
                    redoBuffer.removeElementAt(last);

                    repaint();
                }

                break;

            default:
                somethingChanged = false;

                break;
            }
        } else {
            somethingChanged = false;
        }

        // Neuzeichnen aller Komponenten
        if (somethingChanged) {
            somethingChanged();
        }
    }

    public final void activateResumeBuffer() {
        undoBuffer = new Vector();
        redoBuffer = new Vector();
        somethingChanged();
    }

    public final void keyReleased(final KeyEvent e) {
    }

    public final void keyTyped(final KeyEvent e) {
        final char c = e.getKeyChar();

        //System.out.println("" + c);
        if (allowedLetters.indexOf(c) == -1) {
            return;
        }

        if (c == '*') {
            if (isRealyOldJavaVersion()) {
                parent.insertObjectAtCursor(
                        new PaintSymbol(parent, (char) 0x22c5));
            } else {
                parent.insertObjectAtCursor(new Symbol(parent, (char) 0x22c5));
            }
        } else {
            parent.insertObjectAtCursor(new Letter(parent, c));
        }

        somethingChanged();
    }

    // Handling Interface MouseListener
    public final void mouseClicked(final MouseEvent e) {
    }

    public final void mouseEntered(final MouseEvent e) {
    }

    public final void mouseExited(final MouseEvent e) {
        mouseFromX = -1;
        mouseFromY = -1;
    }

    public final void mousePressed(final MouseEvent e) {
        final int mod = e.getModifiers();

        if (mod == InputEvent.BUTTON1_MASK) {
            int x = e.getX();
            final int y = e.getY();

            // todo: Anpassen auf endg�ltige Version (Startposition der Erfassung)
            // z.B. Hinterlegung in Basic-Klasse oder so...
            final Graphics g = mainElement.getGraphicsHandle();

            final int minY = 0;
            final int baselineY = mainElement.getHeightAboveBaseline(g) + 
                                  minY;
            final int maxY = baselineY + 
                             mainElement.getHeightUnderBaseline(g);

            final int minX = 3;
            final int maxX = mainElement.getWidth(g) + minX;

            if ((y < minY) || (y > maxY) || ((x < minX) || (x > maxX + 50))) {
                return;
            }

            if (x > maxX) {
                x = maxX - 1;
            }

            mouseFromX = x;
            mouseFromY = y;

            //System.out.println("Pressed..:");
        }
    }

    public final void mouseReleased(final MouseEvent e) {
        if (mouseFromX != -1) {
            int x = e.getX();
            final int y = e.getY();

            final Graphics g = mainElement.getGraphicsHandle();

            final int minY = 0;
            final int baselineY = mainElement.getHeightAboveBaseline(g) + 
                                  minY;
            final int maxY = baselineY + 
                             mainElement.getHeightUnderBaseline(g);

            final int minX = 3;
            final int maxX = mainElement.getWidth(g) + minX;

            if ((y < minY) || (y > maxY) || ((x < minX) || (x > maxX + 50))) {
                return;
            }

            if (x > maxX) {
                x = maxX - 1;
            }


            //System.out.println("Moved.."+mouseFromX);
            mainElement.findSelectedObjects(minX, baselineY, mouseFromX, 
                                            mouseFromY, x, y, g);
        }

        mouseFromX = -1;
        mouseFromY = -1;

        //System.out.println("Released..");
    }

    // Handling Interface MouseMotionListener
    public final void mouseDragged(final MouseEvent e) {
        if (mouseFromX != -1) {
            int x = e.getX();
            final int y = e.getY();

            final Graphics g = mainElement.getGraphicsHandle();

            final int minY = 0;
            final int baselineY = mainElement.getHeightAboveBaseline(g) + 
                                  minY;
            final int maxY = baselineY + 
                             mainElement.getHeightUnderBaseline(g);

            final int minX = 3;
            final int maxX = mainElement.getWidth(g) + minX;

            if ((y < minY) || (y > maxY) || ((x < minX) || (x > maxX + 50))) {
                return;
            }

            if (x > maxX) {
                x = maxX - 1;
            }


            //System.out.println("Moved.."+mouseFromX);
            mainElement.findSelectedObjects(minX, baselineY, mouseFromX, 
                                            mouseFromY, x, y, g);
        }
    }

    public final void mouseMoved(final MouseEvent e) {
    }

    public final Succession getParent() {
        return parent;
    }

    /**
     * Erzeugt den MathML-Code für die gesamte Formel.
     * @param insertCursor Zeigt an, ob Informationen über die Position des Cursors gespeichert werden sollen
     * @return MathML-Code
     */
    public final String generateMathMLCode(final boolean insertCursor) {
        final MathMLExport mmle = new MathMLExport(insertCursor);

        mmle.increaseLayer();
        mainElement.generateMathMLCode(mmle);
        mmle.decreaseLayer();

        return mmle.getXMLCode();
    }

    /**
     * Erzeugt den MathML-Code für den ausgew�hlten (=markierten) Bereich.
     * @return MathML-Code
     */
    public String generateMathMLCodeFromSelected() {
        final MathMLExport mmle = new MathMLExport();

        mmle.increaseLayer();
        parent.generateMathMLCodeForSelected(mmle);
        mmle.decreaseLayer();

        return mmle.getXMLCode();
    }

    /**
     * Den übergebenen MathML-Code parsen und einfügen. Repaint() wird nicht aufgerufen.
     * @param mathMLCode Einzufügender MathMLCode
     */
    public final void parse(final String mathMLCode) {
        mainElement.insertCursorAt(0);
        MathMLImport.parseMathML(mathMLCode, 
                                 mainElement.getInsertAtCursorStructure());
    }

    /**
     * Breite des Gesammt-Objektes bestimmen.
     * @return Notwendige Breite
     */
    public final int getRequiredWidth() {
        return mainElement.getWidth(getGraphics());
    }

    /**
     * H�he des gesammt-Objektes bestimmen
     * @return Notwenige H�he
     */
    public final int getRequiredHeight() {
        return mainElement.getHeight(getGraphics());
    }

    /**
     * Objekt zeichnen.
     */
    public final void paint() {
        final Graphics g = getGraphics();
        mainElement.paint(g, 3, mainElement.getHeightAboveBaseline(g) + 1);
        paint(g);
    }

    /**
     * Objekt an die übergebenen Koordinaten zeichen
     * @param x X-Koordinate
     * @param y Y-Koordinate
     */
    public final void paint(final int x, final int y) {
        final Graphics g = getGraphics();
        mainElement.paint(g, 3 + x, 
                          mainElement.getHeightAboveBaseline(g) + 1 + y);
        paint(g);
    }

    /**
     * Das Neuzeichnen der Formel starten.
     */
    public final void repaint() {
        comp.repaint();
    }

    /**
     * Wenn sich irgendwas an der Objektstruktur ver�ndert hat, muss der Undo-Buffer einen neuen
     * "Abzug" der Datenstruktur ziehen
     */
    public final void somethingChanged() {
        //comp.paint(getGraphics());
        if (undoBuffer != null) {
            final int rs = undoBuffer.size();

            if (rs > 1000) {
                // Alle ab Index 250 kopieren - also die ersten 250 Blockweise wegwerfen.
                final Vector v = new Vector();

                for (int i = 250; i < rs; i++) {
                    v.addElement(undoBuffer.elementAt(i));
                }

                undoBuffer.removeAllElements();
                undoBuffer = v;
            }

            String str1 = "";

            if (rs > 1) {
                str1 = (String) undoBuffer.elementAt(rs - 1);
            }

            final String str2 = generateMathMLCode(true);

            //System.out.println(undoBuffer.size()+"="+str2);
            if (!str1.equals(str2)) {
                undoBuffer.addElement(str2);
                redoBuffer.removeAllElements();
            }
        }

        repaint();

        //comp.paint(getGraphics());
    }

    /**
     * Aktuellen Grafikhandle erfragen
     * @return Grafikhandle
     */
    public final Graphics getGraphics() {
        return comp.getGraphics();
    }

    /**
     * Falls sinnvoll und m�glich, Windows-Fokus übertragen
     */
    public final void requestFocus() {
        comp.requestFocus();
    }

    /*public Image createImage(int width, int height) {
        return comp.createImage(width, height);
    } */
    /*public ImageObserver getImageObserver() {
        return comp;
    } */

    /**
     * Gibt das Succession-Objekt zurück, das sich am weitesten oben in der Hirachie befindet
     * @return "Oberstes" Succession-Objekt
     */
    public final Succession getPrimaryElement() {
        return mainElement;
    }

    /*public Cursor getMyCursor() {
        return this;
    }*/

    /**
     * Die Java-Version überpr�fen.
     * @return true, wenn die Version gleich 1.1. ist. (Versionen kleiner 1.1. werden nicht unterst�tzt.)
     */
    public static boolean isRealyOldJavaVersion() {
        //System.out.println("check!");
        if (!knowJavaVersion) {
            try {
                final SecurityManager security = System.getSecurityManager();

                if (security != null) {
                    security.checkPropertyAccess("java.version");
                }

                final String str = System.getProperty("java.version");
                realyOldJavaVesion = (str.indexOf("1.1.") == 0);
                System.out.println("Running on Java Version " + str + 
                                   (realyOldJavaVesion ? " (old)" : ""));
                knowJavaVersion = true;
            } catch (SecurityException e) {
                System.out.println("No access to version number.");
                knowJavaVersion = true;


                // Zur Sicherheit auf Zusatzfunktionen verzichten.
                realyOldJavaVesion = true;
            }
        }

        //System.out.println("It is "+realyOldJavaVesion);
        //return true;
        return realyOldJavaVesion;
    }
}