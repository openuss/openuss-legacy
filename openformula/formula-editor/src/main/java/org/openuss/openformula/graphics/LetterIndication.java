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
 * Created by IntelliJ IDEA.
 * User: Jan Kirchhoff
 * Date: 28.10.2003
 * Time: 19:04:10
 * To change this template use Options | File Templates.
 */
public final class LetterIndication extends Basic {
    public static final int Dot = 0;
    public static final int DoubleDot = 1;
    public static final int TribbleDot = 2;
    public static final int Hat = 3;
    public static final int OverLine = 4;
    public static final int Tilde = 5;
    private static final int heightAboveLetterPercent = 50;

    // eingebundener Buchstabe
    private final Letter letter;
    private int type;

    public LetterIndication(final Basic parent, final Letter letter, 
                            final int type) {
        super(parent);
        this.letter = letter;
        this.type = type;
        letter.setNewParent(this);
    }

    /**
     * Linie im normalen Koordinatensystem zeichen. Diese ggf. dicker wie normal zeichen
     * @param g graphics-Kontext
     * @param x1 x-Koordinate Anfang
     * @param y1 y-Koordinate Anfang
     * @param x2 x-Koordinate Ende
     * @param y2 y-Koordinate Ende
     */
    private void paintLine(final Graphics g, final int x1, final int y1, 
                           final int x2, final int y2) {
        final int ms = getMySize();

        if (ms > 20) {
            for (int i = 0; i < 2; i++)
                for (int j = 0; j < 2; j++)
                    g.drawLine(x1 + i, y1 + j, x2 + i, y2 + j);
        } else {
            g.drawLine(x1, y1, x2, y2);
        }
    }

    public final Letter getLetter() {
        return letter;
    }

    public final int getType() {
        return type;
    }

    public final void setType(final int type) {
        this.type = type;
    }

    public final int getWidthUncached(final Graphics g) {
        return letter.getWidth(g);
    }

    public final int getHeightAboveBaselineUncached(final Graphics g) {
        return letter.getHeightAboveBaseline(g) + 
               ((getMySize() * heightAboveLetterPercent) / 100);
    }

    public final int getHeightUnderBaselineUncached(final Graphics g) {
        return letter.getHeightUnderBaseline(g);
    }

    public final void paint(final Graphics g, final int atX, final int atY) {
        letter.paint(g, atX, atY);

        int pos = atY - letter.getHeightAboveBaseline(g);
        final int width = letter.getWidth(g) - 1;
        final int ha = (getMySize() * heightAboveLetterPercent) / 100;

        int wc = ha / 5;

        if (Cursor.isRealyOldJavaVersion()) {
            if (wc < 3) {
                wc = 3;
            }

            pos--;
        } else if (wc < 2) {
            wc = 2;
        }

        switch (type) {
        case TribbleDot:
            g.fillOval((atX + ((width * 3) / 4)) - (wc / 2) + 1, pos - wc, wc, 
                       wc);
            g.fillOval((atX + (width / 4)) - (wc / 2) - 1, pos - wc, wc, wc);

        case Dot:
            g.fillOval((atX + (width / 2)) - (wc / 2), pos - wc, wc, wc);

            break;

        case DoubleDot:
            g.fillOval((atX + ((width * 3) / 4)) - (wc / 2) + 1, pos - wc, wc, 
                       wc);
            g.fillOval((atX + (width / 4)) - (wc / 2) - 1, pos - wc, wc, wc);

            break;

        case Hat:
            paintLine(g, atX, pos, atX + (width / 2), pos - (ha / 2));
            paintLine(g, atX + width, pos, atX + (width / 2), pos - (ha / 2));

            break;

        case OverLine:
            paintLine(g, atX, pos - 1, (atX + width) - 1, pos - 1);

            break;

        case Tilde:

            final double startSin = -Math.PI / 4.2;
            final double stopSin = (Math.PI * 10.0) / 4.0;
            final double haD = ha;

            //if (haD < 5) haD = 5;
            double lastsin = 0; //Math.sin(startSin) * haD / 4.0 + haD / 3.0;

            for (double i = 0; i < width; i++) {
                final double sin = ((Math.sin(startSin + 
                                                      (((stopSin - startSin) * i) / (width - 1))) * haD) / 9.0) + 
                                   (haD / 5.0);

                if (i != 0) {
                    g.drawLine((atX + (int) i) - 1, pos - (int) lastsin - 1, 
                               atX + (int) i, pos - (int) sin - 1);

                    //g.drawLine(atX + (int) i, pos - (int) sin - 4, atX + (int) i, pos - (int) sin );
                    final int ms = getMySize();

                    if (ms > 20) {
                        g.drawLine((atX + (int) i) - 1, pos - (int) lastsin, 
                                   atX + (int) i, pos - (int) sin);
                    }

                    if (ms > 30) {
                        g.drawLine((atX + (int) i) - 1, pos - (int) lastsin + 1, 
                                   atX + (int) i, pos - (int) sin + 1);
                    }
                }

                lastsin = sin;
            }

            break;

        default:
            throw new RuntimeException("Unknown Type");
        }
    }

    public final void generateMathMLCode(final MathMLExport mmle) {
        mmle.addTextLine("<mover>");

        mmle.increaseLayer();

        {
            letter.generateMathMLCode(mmle);

            final String str;

            switch (type) {
            case Dot:
                str = "&DiacriticalDot;";

                break;

            case DoubleDot:
                str = "&DoubleDot;";

                break;

            case TribbleDot:
                str = "&TripleDot;";

                break;

            case Hat:
                str = "&Hat;";

                break;

            case OverLine:
                str = "&OverBar;";

                break;

            case Tilde:
                str = "&Tilde;";

                break;

            default:
                throw new RuntimeException("Unknown Type");
            }

            mmle.addTextLine("<mo>" + str + "</mo>");
        }

        mmle.decreaseLayer();

        mmle.addTextLine("</mover>");
        mmle.nextElement(true);
    }
}