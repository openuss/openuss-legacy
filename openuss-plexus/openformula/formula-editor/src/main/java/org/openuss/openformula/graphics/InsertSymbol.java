/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.graphics;

import org.openuss.openformula.graphics.matrixmenu.MatrixFrame;
import org.openuss.openformula.io.Cursor;


/**
 * Die Klasse bildet die Schnittstelle zwischen den Buttons und der grafischen Darstellung.
 * Die einzelnen Objekte dieser Klasse werden mit einer Konstante initialisiert, die beschreibt,
 * was einzuf�gen ist. Bei Aufruf von InsertNow() wird dieses Objekt erstellt und eingef�gt.
 * Des weiteren stehen Funktionen zur Verf�gung, die einzelne Zeichen einf�gen k�nnen.
 */
public final class InsertSymbol {
    /**
     * Konstante f�r "Bruch einf�gen"
     */
    public final static int Fraction = 0;

    /**
     * Konstante f�r "Summe einf�gen"
     */
    public final static int Sum = 1;

    /**
     * Konstante f�r "Faktorzeichen einf�gen"
     */
    public final static int Factor = 2;

    /**
     * Konstante f�r "Integralzeichen einf�gen"
     */
    public final static int Integral = 3;

    /**
     * Konstante f�r "Wurzel einf�gen"
     */
    public final static int SquareRoot = 4;

    /**
     * Konstante f�r "Hochschreiben-Objekt einf�gen"
     */
    public final static int SuperScript = 5;

    /**
     * Konstante f�r "Tiefschreiben-Objekt einf�gen"
     */
    public final static int SubScript = 6;

    /**
     * Konstante f�r "Hoch-&Tiefschreiben-Objekt einf�gen"
     */
    public final static int SubSuperScript = 7;

    /**
     * Konstante f�r "Runde Klammer-Objekt einf�gen"
     */
    public final static int Parenthesis = 8;

    /**
     * Konstante f�r "Eckige Klammern-Objekt einf�gen"
     */
    public final static int SquareBracket = 9;

    /**
     * Konstante f�r "Geschweifte-Klammer-Objekt einf�gen"
     */
    public final static int CurlyBracket = 10;

    /**
     * Konstante f�r "Linke Geschweifte-Klammer-Objekt einf�gen"
     */
    public final static int LeftCurlyBracket = 21;

    /**
     * Konstante f�r "Rechte Geschweifte-Klammer-Objekt einf�gen"
     */
    public final static int RightCurlyBracket = 22;

    /**
         * Konstante f�r "Vertikale Linie einf�gen"
         */
    public final static int VerticalLine = 23;

    /**
     * Konstante f�r "Matrix einf�gen"
     */
    public final static int Matrix = 11;

    /**
     * Konstante f�r "�berschreiben-Objekt einf�gen"
     */
    public final static int UnderScript = 12;

    /**
     * Konstante f�r "Unterschreiben-Objekt einf�gen"
     */
    public final static int OverScript = 13;

    /**
     * Konstante f�r "�ber-&Unterschreiben-Objekt einf�gen"
     */
    public final static int UnderOverScript = 14;

    /**
     * Konstante f�r "Punkt �ber letztem Zeichen anf�gen"
     */
    public final static int AddDotToLetter = 15;

    /**
     * Konstante f�r "Zwei Punkte �ber letztem Zeichen anf�gen"
     */
    public final static int AddDoubleDotToLetter = 16;

    /**
     * Konstante f�r "Drei Punkte �ber letztem Zeichen anf�gen"
     */
    public final static int AddTripleDotToLetter = 17;

    /**
     * Konstante f�r "Hut �ber letztem Zeichen anf�gen"
     */
    public final static int AddHatToLetter = 18;

    /**
     * Konstante f�r "Balken �ber letztem Zeichen anf�gen"
     */
    public final static int AddOverBarToLetter = 19;

    /**
     * Tilde f�r "Balken �ber letztem Zeichen anf�gen"
     */
    public final static int AddTildeToLetter = 20;

    /**
     * Konstante f�r "Sonderzeichen einf�gen"
     */
    public final static int InsertSymbolChar = 1000;
    private int insertThis;
    private Cursor cursor;
    private char c;

    /**
     * Erzeugt ein Objekt, das bei Aufruf von InsertNow() das hier eingestellte
     * Objekt einf�gt.
     * @param insertThis Konstante, die informiert, was erstellt werden soll
     * @param cursor Umgebung, in welcher das Objekt eingef�gt werden soll.
     */
    public InsertSymbol(final int insertThis, final Cursor cursor) {
        if (insertThis >= 1000) {
            throw new RuntimeException("Char fehlt!");
        }

        this.insertThis = insertThis;
        this.cursor = cursor;
    }

    /**
     * Erzeugt ein Objekt, das bei Aufruf von InsertNow() das hier eingestellte
     * Objekt einf�gt. Erlaubt als zus�tzlichen Parameter die �bergabe eines Zeichens.
     * (Wird zur Zeit nur von "InsertCharSymbol" verwendet.)
     * @param insertThis
     * @param c
     * @param cursor
     */
    public InsertSymbol(final int insertThis, final char c, final Cursor cursor) {
        this.insertThis = insertThis;
        this.cursor = cursor;
        this.c = c;
    }

    /**
     * Erzeugt ein Objekt, mit dem durch Aufruf z.B. von InsertSymbolLetter Zeichen direkt
     * eingef�gt werden k�nnen. InsertNow() darf bei einem Objekt, dass mit diesem
     * Konstruktor erzeugt wurde, nicht aufgerufen werden.
     * @param cursor
     */
    public InsertSymbol(final Cursor cursor) {
        this.insertThis = -1;
        this.cursor = cursor;
    }

    /**
     * Diese Methode erstellt ein Objekt gem�� der beim Konstruktor �bergebenen
     * Konstante und f�gt das an der Cursorposition ein.
     */
    public final void InsertNow() {
        final Succession parent = cursor.getParent();

        switch (insertThis) {
        case Fraction:
            cursor.insertObject(new Fraction(parent));

            break;

        case Sum:
            cursor.insertObject(
                    new SummIntegralSign(parent, Cursor.SumLetter, 
                                         Cursor.SumFakPercentUsage));

            break;

        case Factor:
            cursor.insertObject(
                    new SummIntegralSign(parent, Cursor.FaktorLetter, 
                                         Cursor.SumFakPercentUsage));

            break;

        case Integral:
            cursor.insertObject(
                    new SummIntegralSign(parent, Cursor.IntegralLetter, 
                                         Cursor.IntegralPercentUsage));

            break;

        case SquareRoot:
            cursor.insertObject(new Root(parent));

            break;

        case SuperScript:
            cursor.insertObject(new Superscript(parent));

            break;

        case SubScript:
            cursor.insertObject(new Subscript(parent));

            break;

        case SubSuperScript:
            cursor.insertObject(new SubSuperscript(parent));

            break;

        case Parenthesis:
            cursor.insertObject(new Parenthesis(parent));

            break;

        case SquareBracket:
            cursor.insertObject(new SquareBracket(parent));

            break;

        case CurlyBracket:
            cursor.insertObject(new CurlyBracket(parent, true, true));

            break;

        case LeftCurlyBracket:
            cursor.insertObject(new CurlyBracket(parent, true, false));

            break;

        case RightCurlyBracket:
            cursor.insertObject(new CurlyBracket(parent, false, true));

            break;

        case VerticalLine:
            cursor.insertObject(new VerticalLine(parent));

            break;

        case Matrix:

            final MatrixFrame frame = new MatrixFrame(cursor);
            frame.setLocation(100, 100);
            frame.giveFocus().requestFocus();

            return;

        case UnderScript:
            cursor.insertObject(new UnderOverScript(parent, false, true));

            break;

        case OverScript:
            cursor.insertObject(new UnderOverScript(parent, true, false));

            break;

        case UnderOverScript:
            cursor.insertObject(new UnderOverScript(parent, true, true));

            break;

        case AddDotToLetter:
        case AddDoubleDotToLetter:
        case AddTripleDotToLetter:
        case AddHatToLetter:
        case AddOverBarToLetter:
        case AddTildeToLetter:

            final Basic b = parent.getObjectAtCursor();
            final int type; // Unsch�n - aber wie soll man es besser machen ? Den Code n mal kopieren ? Auch doof!

            if (insertThis == AddDotToLetter) {
                type = LetterIndication.Dot;
            } else if (insertThis == AddDoubleDotToLetter) {
                type = LetterIndication.DoubleDot;
            } else if (insertThis == AddTripleDotToLetter) {
                type = LetterIndication.TribbleDot;
            } else if (insertThis == AddHatToLetter) {
                type = LetterIndication.Hat;
            } else if (insertThis == AddOverBarToLetter) {
                type = LetterIndication.OverLine;
            } else {
                type = LetterIndication.Tilde;
            }

            if (b instanceof Letter) {
                parent.exchangeObject(b, 
                                      new LetterIndication(parent, (Letter) b, 
                                                           type));
            } else if (b instanceof LetterIndication) {
                if (((LetterIndication) b).getType() == type) {
                    parent.exchangeObject(b, ((LetterIndication) b).getLetter());
                } else {
                    ((LetterIndication) b).setType(type);
                }
            }

            break;

        case InsertSymbolChar:
            InsertSymbolLetter(c);

            break;

        default:
            throw new RuntimeException("No Symbol defined.");
        }

        TransferFocusToDrawArea();
    }

    /**
     * Das Symbol "c" wird an der Positon des Cursors eingef�gt. "Symbole" sind Zeichen
     * wie z.B. griechische Zeichen oder Pfeile.
     * @param c Einzuf�gendes Zeichen
     */
    public final void InsertSymbolLetter(final char c) {
        switch (c) {
        case 0xB1: // Sonderbehandlung +- -Zeichen
            InsertLetter(c);

            break;

        case 0x2203:
        case 0x2204:
        case 0x21D0:
        case 0x21D2:
        case 0x21D4:
        case 0x22EE:
        case 0x22EF:
        case 0x22F0:
        case 0x22F1:
            InsertJVIEWLetter(c);

            break;

        case 0x2209:
        case 0x2200:
        case 0x2208:
        case 0x222A:
        case 0x2227:
        case 0x2228:
        case 0x2205:

            if (Cursor.isRealyOldJavaVersion()) {
                InsertJVIEWLetter(c);

                break;
            }

        default:
            cursor.insertObject(new Symbol(cursor.getParent(), c));
        }

        TransferFocusToDrawArea();
    }

    /**
     * F�rt ein normales Zeichen an der Position des Cursors ein.
     * @param c Einzuf�gendes Zeichen
     */
    public final void InsertLetter(final char c) {
        cursor.insertObject(new Letter(cursor.getParent(), c));
        TransferFocusToDrawArea();
    }

    private void InsertJVIEWLetter(final char c) {
        cursor.insertObject(new PaintSymbol(cursor.getParent(), c));
        TransferFocusToDrawArea();
    }

    private void TransferFocusToDrawArea() {
        cursor.somethingChanged();
        cursor.requestFocus();
    }

    // --Recycle Bin START (20.01.04 18:37):
    //    /**
    //     * Gibt den Cursor zur�ck, mit dem das Objekt initialisiert wurde.
    //     * @return Zeichnungskontext
    //     */
    //
    //    public final Cursor getCursor() {
    //        return cursor;
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)
}