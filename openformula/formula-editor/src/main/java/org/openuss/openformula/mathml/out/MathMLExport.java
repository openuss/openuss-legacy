/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.mathml.out;

/**
 * Diese Klasse wird zur Erzeugung des MathMLExport-Code verwendet. Sie stellt ein
 * Mehr-Ebenen-Konzept zur Verf�gung, um den teilweise etwas komplexen Code richtig zu
 * erzeugen. (Siehe zugeh�rige Diplomarbeit.)
 */
public final class MathMLExport {
    /**
     * Startsymbol f�r MathML-Code
     */
    private final static String mathMlHeader = "<math>";

    /**
         * Endsymbol f�r MathML-Code
         */
    private final static String mathMlFooter = "</math>";

    /**
     * Konstante, die bestimmt, ob "sch�ner" oder "platzsparender" MathML-Code erzeugt wird.
     */
    private final static boolean generateNiceXMLCode = false;
    private Layer current;

    //private final Layer top;
    private final boolean insertCursor;

    /**
     * Diese Klasse wird verwendet, um die Objektstruktur des Formeleditor als MathMLExport-Code darzustellen.
     */
    public MathMLExport() {
        //top = new Layer();
        current = new Layer();
        insertCursor = false;
    }

    /**
     * Das gleiche wie oben, allerdings kann hier gesteuert werden, ob die Position des Cursors
     * gespeichert werden soll. Das Tag <cursor> veranlasst sp�ter den Parser, den Cursor an diese
     * Position zu setzen.
     * @param insertCursor
     */
    public MathMLExport(final boolean insertCursor) {
        //top = new Layer();
        current = new Layer();
        this.insertCursor = insertCursor;
    }

    /**
     * Eine Ebene in der Baumstruktur nach oben gehen. Kann z.B. durch das einf�gen eines
     * zus�tzlichen Leerzeichens vor dem MathMLExport-Code dargestellt werden.
     */
    public final void increaseLayer() {
        current.increaseLayer();
    }

    /**
     * Reduziert die Ebene um eins. Gegenst�ck zu IncreaseLayer()
     */
    public final void decreaseLayer() {
        current.decreaseLayer();
    }

    /**
     * Eine Textzeile in den tempor�ren Buffer zuf�gen. Es wird kein unsichtbarer Operator eingef�gt.
     * @param str Die zuzuf�gende Zeile
     */
    public final void addTextLine(final String str) {
        current.addTextLine(str);
    }

    /**
     * F�gt ein einzelnes Zeichen ein. Es wird - soweit bekannt - automatisch korrekt codiert.
     * Zahlen werden dabei zu einem String zusammengef�gt
     * @param c Einzuf�gendes Zeichen
     */
    public final void addLetter(final char c) {
        current.addLetter(c);
    }

    /**
     * F�gt einen Textblock zu.
     * @param str Der zuzuf�gende Textblock
     */
    public final void addText(final String str) {
        current.addText(str);
    }

    /**
     * F�gt, falls das vorhergehende Zeichen kein Operator war, ein "unsichtbares
     * Multiplikationssymbol" ein.
     */
    public final void ifNessesaryInsertInvisibleOperator() {
        current.ifNessesaryInsertInvisibleOperator();
    }

    /* public void RequireOperator() {
                 current.RequireOperator();
         }
                                    
         public void DonttRequireOperator() {
             current.DonttRequireOperator();
         }*/

    /**
     * Gibt den erzeugten XML-Code zur�ck. Die Bearbeitungsebene wird auf die
     * untere Ebene Verschoben
     * @return Gibt den XML-Code zur�ck.
     */
    public final String getXMLCode() {
        // MathMLExport-Startcode
        return mathMlHeader + formatString(current.getText()) + mathMlFooter;
    }

    // --Recycle Bin START (20.01.04 18:37):
    //    /**
    //     * Gibt den erzeugten XML-Code ohne Header und <math> bzw. </math>  und Footer zur�ck.
    //     * @return Interner XML-Code
    //     */
    //
    //    public final String getXMLCodeWithoutHeaderFooter() {
    //        return formatString(current.getText()).toString();
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)

    /**
     * Das Element ist abgeschlossen, ab nun folgt das n�chste Element.
     * @param reqOperator Ein Zwischenoperator wird ben�tigt, also ein "unsichtbares"
     * Multiplikationszeichen
     */
    public final void nextElement(final boolean reqOperator) {
        current.nextElement(reqOperator);
    }

    // --Recycle Bin START (20.01.04 18:37):
    //    public final boolean isOperatorRequired() {
    //        return current.isOperatorRequired();
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)
    public final void insertCursorIfNessesary() {
        if (insertCursor) {
            addText("<cursor/>");
            nextElement(false);
        }
    }

    private static StringBuffer formatString(final StringBuffer str) {
        if (generateNiceXMLCode) {
            final StringBuffer out = new StringBuffer(" ");

            for (int i = 0; i < str.length(); i++) {
                final char c = str.charAt(i);

                if (c == '\n') {
                    out.append("\n ");
                } else {
                    out.append(c);
                }
            }

            out.setLength(out.length() - 1);

            return out;
        } else {
            return str;
        }
    }

    /**
     * L�scht das zuletzt eingegebene Element und gibt es als String zur�ck.
     * Kann mit addText() wieder eingef�gt werden.
     * @return Das letzte Element
     */
    public final String returnAndDelteteLastElement() {
        return current.returnAndDelteteLastElement();
    }

    /**
     * Implementierung des Ebenenkonzeptes.
     */
    protected final class Layer {
        //protected int currentLayer;
        final StringBuffer allData = new StringBuffer();
        StringBuffer buffer = new StringBuffer();
        StringBuffer cur = new StringBuffer();
        final Layer parent;
        StringBuffer chars = new StringBuffer();

        //final static int letter = 0;
        //final static int digits = 1;
        boolean lastElementReqOperator = false;

        //protected final String allowedLetters = "abcdefghijklmnoprstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890=|!%/\\#*+~,.;:-_)(<> []?������";
        final String operators = ",=|%/\\#*+~,.;:-_<>?" + 0xB1;

        public Layer() {
            parent = null;
        }

        Layer(final Layer parent) {
            this.parent = parent;
        }

        public final void addTextLine(final String str) {
            ifNessesaryAddDigits(); // Eigenes Objekt f�r Zahlen einf�gen, wenn n�tig

            cur.append(str);

            if (generateNiceXMLCode) {
                cur.append("\n");
            }
        }

        public final void addText(final String str) {
            ifNessesaryAddDigits(); // Eigenes Objekt f�r Zahlen einf�gen, wenn n�tig

            cur.append(str);
        }

        public final void addText(final StringBuffer str) {
            ifNessesaryAddDigits(); // Eigenes Objekt f�r Zahlen einf�gen, wenn n�tig

            cur.append(str.toString());
        }

        public final void nextElement(final boolean reqOperator) {
            allData.append(buffer.toString());
            buffer = cur;
            cur = new StringBuffer();

            lastElementReqOperator = reqOperator;
        }

        public final boolean isOperatorRequired() {
            return lastElementReqOperator;
        }

        public final String returnAndDelteteLastElement() {
            ifNessesaryAddDigits();

            final StringBuffer str = buffer.append(cur.toString());
            buffer = new StringBuffer();
            cur = new StringBuffer();

            return str.toString();
        }

        public final void increaseLayer() {
            current = new Layer(current);
        }

        public final void decreaseLayer() {
            ifNessesaryAddDigits(); // Eigenes Objekt f�r Zahlen einf�gen, wenn n�tig

            if (parent == null) {
                throw new RuntimeException("Unterste Ebene erreicht.");
            }

            current = parent;
            parent.addText(formatString(allData.append(buffer.toString())
                                               .append(cur.toString())));
        }

        public final StringBuffer getText() {
            ifNessesaryAddDigits(); // Eigenes Objekt f�r Zahlen einf�gen, wenn n�tig

            if (parent != null) {
                decreaseLayer();

                return parent.getText();
            } else {
                return formatString(allData.append(buffer.toString())
                                           .append(cur.toString()));
            }
        }

        //protected final String allowedLetters = "abcdefghijklmnoprstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890=|!%/\\#*+~,.;:-_)(<> []?������";
        public final void ifNessesaryInsertInvisibleOperator() {
            ifNessesaryAddDigits(); // Eigenes Objekt f�r Zahlen einf�gen, wenn n�tig

            // todo: Problem: Steht so in Standard, geht aber so nirgendwo... ??!
            if (lastElementReqOperator) {
                addTextLine("<mo>&InvisibleTimes;</mo>");
                lastElementReqOperator = false;
            }
        }

        /*public void RequireOperator() {
            lastElementReqOperator = true;
        }
                                                                        
        public void DonttRequireOperator() {
            lastElementReqOperator = false;
        } */
        final void ifNessesaryAddDigits() {
            if (chars.length() != 0) {
                final String str = chars.toString();
                chars = new StringBuffer();

                try {
                    Integer.parseInt(str);


                    // �berpr�fung, ob g�ltiger mathematischer Ausdruck
                    // Falls nicht - unten Zeichen einzelnd einf�gen
                    ifNessesaryInsertInvisibleOperator();
                    addTextLine("<mn>" + str + "</mn>");
                    nextElement(true);
                } catch (NumberFormatException e) {
                    for (int i = 0; i < str.length(); i++)
                        addLetter(str.charAt(i), false);
                }
            }
        }

        //protected final String noPreOpReq = ")]"
        public final void addLetter(final char c) {
            addLetter(c, true);
        }

        final void addLetter(final char c, final boolean checkDigit) {
            if (checkDigit) {
                if (Character.isDigit(c) || (c == '.')) {
                    chars.append(c);

                    return;
                }

                ifNessesaryAddDigits();
            }

            if (Character.isLetter(c)) {
                ifNessesaryInsertInvisibleOperator();

                switch (c) {
                case '�':
                    addTextLine("<mi>&auml;</mi>");

                    break;

                case '�':
                    addTextLine("<mi>&auml;</mi>");

                    break;

                case '�':
                    addTextLine("<mi>&uuml;</mi>");

                    break;

                case '�':
                    addTextLine("<mi>&Auml;</mi>");

                    break;

                case '�':
                    addTextLine("<mi>&Ouml;</mi>");

                    break;

                case '�':
                    addTextLine("<mi>&Uuml;</mi>");

                    break;

                case '�':
                    addTextLine("<mi>&szlig;</mi>");

                    break;

                default:
                    addTextLine("<mi>" + c + "</mi>");
                }

                nextElement(true);

                return;
            }

            switch (c) {
            case '(':
            case '[':
                ifNessesaryInsertInvisibleOperator();
                addTextLine("<mi>" + c + "</mi>");
                nextElement(false);

                return;

            case ')':
            case ']':
            case '!':
                addTextLine("<mi>" + c + "</mi>");
                nextElement(true);

                return;
            }

            if (c == ' ') {
                addTextLine("<mspace/>");
                nextElement(false);

                return;
            }

            if (operators.indexOf(c) != -1) {
                switch (c) {
                case 0xB1:
                    addTextLine("<mo>&PlusMinus;</mo>");

                    break;

                case '<':
                    addTextLine("<mo>&lt;</mo>");

                    break;

                case '>':
                    addTextLine("<mo>&gt;</mo>");

                    break;

                case '"':
                    addTextLine("<mo>&quot;</mo>");

                    break;

                case '&':
                    addTextLine("<mo>&amp;</mo>");

                    break;

                default:
                    addTextLine("<mo>" + c + "</mo>");
                }

                nextElement(false);

                return;
            }

            ifNessesaryInsertInvisibleOperator();
            addTextLine("<mo>&#" + (int) c + ";</mo>");
            nextElement(true);

            //System.out.println("Unknown Symbol: " + c);
        }
    }
}