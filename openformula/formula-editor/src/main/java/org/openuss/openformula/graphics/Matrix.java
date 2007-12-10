/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.graphics;

import java.awt.*;

import org.openuss.openformula.mathml.in.BuildStructure;
import org.openuss.openformula.mathml.out.MathMLExport;


/**
 * Created by IntelliJ IDEA.
 * User: Jan Kirchhoff
 * Date: 29.09.2003
 * Time: 17:00:43
 * To change this template use Options | File Templates.
 */
public final class Matrix extends Basic {
    private final static int TeilerAbstandX = 5;
    private final static int TeilerAbstandY = 5;
    private final Succession[][] elements;
    private final int countX;
    private final int countY;

    /**
     * Eine Matrix (oder ein Vektor) mit der Ausdehnung countX, countY erstellen.
     * @param parent Übergeordnetes Objekt
     * @param countX Anzahl der Elemente in horizontaler Richtung
     * @param countY Anzahl der Elemente in vertikaler Richtung
     */
    public Matrix(final Basic parent, final int countX, final int countY) {
        super(parent);

        this.countX = countX;
        this.countY = countY;

        elements = new Succession[countX][countY];

        for (int i = 0; i < countX; i++) {
            //Elements[i] = new Succession[countY];
            for (int j = 0; j < countY; j++)
                elements[i][j] = new Succession(this, true);
        }

        //getCursor().setNewParent(Elements[0][0]);
        //Elements[0][0].insertCursorAt(0);

        /*Elements[0][0].addObject(new Fraction(Elements[0][0]));
        Elements[0][0].addObject(new Letter(Elements[0][0],'x'));
        Elements[0][0].addObject(new Letter(Elements[0][0],'x'));*/
    }

    public final BuildStructure[][] getMatrixBuildStructure() {
        return elements;
    }

    /**
     * Ein untergeordnetes Objekt finden
     * @param toFind Zu findendes Objekt
     * @return Koordinaten des Objektes als Punkt
     */
    private int[] findElement(final Succession toFind) {
        final int[] k = new int[2];

        for (int i = 0; i < countX; i++) {
            for (int j = 0; j < countY; j++) {
                if (elements[i][j] == toFind) {
                    k[0] = i;
                    k[1] = j;

                    return k;
                }
            }
        }

        throw new RuntimeException(
                "Element nicht gefunden. Falsches Parent-Objekt.");
    }

    public final void paint(final Graphics g, final int atX, final int atY) {
        int AbstandX = parent.getMySize() / TeilerAbstandX;
        int AbstandY = parent.getMySize() / TeilerAbstandY;

        if (AbstandX < 2) {
            AbstandX = 2;
        }

        if (AbstandY < 2) {
            AbstandY = 2;
        }

        final int[] hLineSize = new int[countX];
        final int[] hLinePos = new int[countX + 1];

        final int[] vLinePos = new int[countY];
        final int[] vOverLine = new int[countY];
        final int[] vUnderLine = new int[countY];

        for (int i = 0; i < countX; i++)
            hLineSize[i] = 0;

        for (int j = 0; j < countY; j++) {
            vLinePos[j] = 0;
            vOverLine[j] = 0;
            vUnderLine[j] = 0;
        }

        for (int i = 0; i < countX; i++) {
            for (int j = 0; j < countY; j++) {
                int cur = elements[i][j].getWidth(g);

                if (hLineSize[i] < cur) {
                    hLineSize[i] = cur;
                }

                cur = elements[i][j].getHeightAboveBaseline(g);

                if (vOverLine[j] < cur) {
                    vOverLine[j] = cur;
                }

                cur = elements[i][j].getHeightUnderBaseline(g);

                if (vUnderLine[j] < cur) {
                    vUnderLine[j] = cur;
                }
            }
        }

        hLinePos[0] = 0;

        for (int i = 1; i < countX; i++) {
            hLinePos[i] = hLinePos[i - 1] + hLineSize[i - 1] + AbstandX;
        }

        vLinePos[0] = vUnderLine[0]; // Abstand der Baseline von relativ 0,0

        for (int j = 1; j < countY; j++) {
            // Abstand vom bisherigen Zeichen, dessen Höhe über der BaseLine, der Default-Abstand
            // und des eigenen Abstandes unter der Baseline
            vLinePos[j] = vLinePos[j - 1] + vOverLine[j - 1] + AbstandY + 
                          vUnderLine[j];
        }

        final int maxHeight = vLinePos[countY - 1] + vOverLine[countY - 1];
        final int middle = (parent.getMySize() * Fraction.positionAtSizePercent) / 100;

        final int posY = (-maxHeight / 2) + middle;

        for (int i = 0; i < countX; i++) {
            for (int j = 0; j < countY; j++) {
                final int dx = (hLineSize[i] - elements[i][j].getWidth(g)) / 2;
                elements[i][j].paint(g, atX + hLinePos[i] + dx, 
                                     atY - vLinePos[j] - posY);
            }
        }

        //for ()
    }

    // --Recycle Bin START (20.01.04 18:37):
    //    public static int[] getLineHeight(final Graphics g) {
    //        return new int[20];
    //    }
    // --Recycle Bin STOP (20.01.04 18:37)
    public final int getHeightUncached(final Graphics g) {
        int sum = 0;

        //int AbstandX=parent.getMySize() / TeilerAbstandX;
        int AbstandY = parent.getMySize() / TeilerAbstandY;

        if (AbstandY < 2) {
            AbstandY = 2;
        }

        for (int i = 0; i < countY; i++) {
            int maxAbove = 0;
            int maxUnder = 0;

            for (int j = 0; j < countX; j++) {
                final int curAbove = elements[j][i].getHeightAboveBaseline(g);
                final int curUnder = elements[j][i].getHeightUnderBaseline(g);

                if (curAbove > maxAbove) {
                    maxAbove = curAbove;
                }

                if (curUnder > maxUnder) {
                    maxUnder = curUnder;
                }
            }

            sum += (maxUnder + maxAbove + AbstandY);

            //System.out.println("Sum:"+sum+" maxU:"+maxUnder+" maxA:"+maxAbove);
        }

        return sum - AbstandY;
    }

    public final int getWidthUncached(final Graphics g) {
        int sum = 0;

        int AbstandX = parent.getMySize() / TeilerAbstandX;

        //int AbstandY=parent.getMySize() / TeilerAbstandY;
        if (AbstandX < 2) {
            AbstandX = 2;
        }

        for (int i = 0; i < countX; i++) {
            int max = 0;

            for (int j = 0; j < countY; j++) {
                final int cur = elements[i][j].getWidth(g);

                if (cur > max) {
                    max = cur;
                }
            }

            sum += (max + AbstandX);
        }

        return sum;
    }

    public final int getHeightAboveBaselineUncached(final Graphics g) {
        final int middle = (parent.getMySize() * Fraction.positionAtSizePercent) / 100;

        return (getHeight(g) / 2) + 1 + middle;
    }

    public final int getHeightUnderBaselineUncached(final Graphics g) {
        final int middle = (parent.getMySize() * Fraction.positionAtSizePercent) / 100;

        return ((getHeight(g) / 2) + 1) - middle;
    }

    public final boolean canEnterLeftOrRighWithCursor() {
        return true;
    }

    public final void insertCursorAt(final int position) {
        //throw new RuntimeException("Basic:Can't insert Cursor.");
        int pos = position;

        if (pos < 0) {
            pos = 0;
        }

        if (pos >= countX) {
            pos = countX - 1;
        }

        elements[pos][countY / 2].insertCursorAt(position);
    }

    public final void insertCurorLeftFromMe(final Basic object) {
        final int[] p = findElement((Succession) object);

        final int x = p[0];
        final int y = p[1];

        if (x == 0) {
            parent.insertCurorLeftFromMe(this);
        } else {
            elements[x - 1][y].insertCursorAt(Integer.MAX_VALUE);
        }
    }

    public final void insertCurorRightFromMe(final Basic object) {
        final int[] p = findElement((Succession) object);

        final int x = p[0];
        final int y = p[1];

        if (x == (countX - 1)) {
            parent.insertCurorRightFromMe(this);
        } else {
            elements[x + 1][y].insertCursorAt(0);
        }
    }

    private int getVerticalLineWidth(final Graphics g, final int line) {
        int max = 0;

        for (int i = 0; i < countY; i++) {
            final int cur = elements[line][i].getWidth(g);

            if (max < cur) {
                max = cur;
            }
        }

        return max;
    }

    private int getHorizontalLineHeight(final Graphics g, final int line) {
        int max = 0;

        for (int i = 0; i < countX; i++) {
            final int cur = elements[i][line].getHeight(g);

            if (max < cur) {
                max = cur;
            }
        }

        return max;
    }

    //public void insertCurorLeftFromMe(Basic object) {
    //}
    public final void moveUp(final Basic from, final int toXPosition) {
        final int[] p = findElement((Succession) from);
        int AbstandX = parent.getMySize() / TeilerAbstandX;

        if (AbstandX < 2) {
            AbstandX = 2;
        }

        final int x = p[0];
        final int y = p[1];

        final Graphics g = getGraphicsHandle();

        if (y == (countX - 1)) {
            int sum = 0;

            for (int i = 0; i < x; i++)
                sum += (getVerticalLineWidth(g, i) + AbstandX);

            final int lw = getVerticalLineWidth(g, x) - 
                           elements[x][y].getWidth(g);


            // System.out.print("==>LW:"+lw/2+" + Sum:"+sum+" + XP:"+toXPosition+"=");
            sum += ((lw / 2) + toXPosition);


            //System.out.println(sum);
            parent.moveUp(this, sum);
        } else {
            final int lw = -elements[x][y].getWidth(g) + 
                           elements[x][y + 1].getWidth(g);
            elements[x][y + 1].searchObjectAtRelativeXPosition(g, 
                                                               toXPosition + 
                                                               (lw / 2), true);
        }
    }

    public final void moveDown(final Basic from, final int toXPosition) {
        final int[] p = findElement((Succession) from);
        int AbstandX = parent.getMySize() / TeilerAbstandX;

        if (AbstandX < 2) {
            AbstandX = 2;
        }

        final int x = p[0];
        final int y = p[1];

        final Graphics g = getGraphicsHandle();

        //y=countX-1;
        if (y == 0) {
            int sum = 0;

            for (int i = 0; i < x; i++)
                sum += (getVerticalLineWidth(g, i) + AbstandX);

            final int lw = getVerticalLineWidth(g, x) - 
                           elements[x][y].getWidth(g);


            //System.out.print("==>LW:"+lw/2+" + Sum:"+sum+" + XP:"+toXPosition+"=");
            sum += ((lw / 2) + toXPosition);


            //System.out.println(sum);
            parent.moveDown(this, sum);
        } else {
            final int lw = -elements[x][y].getWidth(g) + 
                           elements[x][y - 1].getWidth(g);
            elements[x][y - 1].searchObjectAtRelativeXPosition(g, 
                                                               toXPosition + 
                                                               (lw / 2), false);
        }
    }

    /**
     * Ermittelt aus den relativen Koordinaten x & y, die sich auf die linke untere Ecke der Matrix beziehen,
     * die Koordinaten der Elemente (Index 0 für x und 2 für y) sowie die relative Verschiebung (Index 1 für x
     * und 3 für y)
     * @param g Graphic-Handle
     * @param x X-Koordinate, relativ
     * @param y Y-Koordinate, relativ
     * @return Integer-Array, Struktur siehe Beschreibung
     */
    private int[] getPositonFromRelativeKoord(final Graphics g, final int x, 
                                              final int y) {
        final int[] v = new int[4];

        int AbstandX = parent.getMySize() / TeilerAbstandX;
        int AbstandY = parent.getMySize() / TeilerAbstandY;

        if (AbstandX < 2) {
            AbstandX = 2;
        }

        if (AbstandY < 2) {
            AbstandY = 2;
        }

        // x-Koordinate ermitteln
        for (int i = 0; i < v.length; i++)
            v[i] = 0;

        int abs = AbstandX / 2;
        int xd = 0;
        int i;

        for (i = 0; i < (countX - 1); i++) {
            final int delta = getVerticalLineWidth(g, i) + abs;

            if ((xd + delta) > x) {
                break;
            }

            xd += (delta + abs);
        }

        v[0] = i;
        v[1] = xd + abs;

        abs = AbstandY / 2;

        int yd = 0;

        for (i = 0; i < (countX - 1); i++) {
            final int delta = getHorizontalLineHeight(g, i) + abs;

            if ((yd + delta) > y) {
                break;
            }

            yd += (delta + abs);
        }

        v[2] = i;
        v[3] = yd + abs;

        return v;
    }

    public final boolean findSelectedObjects(int currentX, int currentY, 
                                             final int fromX, final int fromY, 
                                             final int toX, final int toY, 
                                             final Graphics g) {
        //Zeile bestimmen
        //int dx=currentX-
        //for (int i=)
        final int relX = currentX;

        final int gHUB = getHeightUnderBaseline(g);
        final int relY = gHUB + currentY;

        //int AbstandX = parent.getMySize() / TeilerAbstandX;
        //int AbstandY = parent.getMySize() / TeilerAbstandY;
        //if (AbstandX < 2) AbstandX = 2;
        //if (AbstandY < 2) AbstandY = 2;
        //System.out.println(relX+" "+relY);
        final int[] v = getPositonFromRelativeKoord(g, -relX + fromX, 
                                                    relY - fromY); // Ermittle, in welchem Feld from ist
        final int[] k = getPositonFromRelativeKoord(g, -relX + toX, relY - 
                                                    toY); // Das gleiche mit to

        //System.out.println(v[0] + "," + v[2] + "  -   " + k[0] + "," + k[2]);
        if ((v[0] != k[0]) || (v[2] != k[2])) {
            return false;
        }

        //System.out.println(v[0]+","+v[2]+" at rel. "+v[1]+","+v[3]);
        final Succession selected = elements[v[0]][v[2]];


        //relX-=v[1]; relY-=v[2]-selected.getHeightUnderBaseline(g);
        //System.out.println("relX:"+relX+",relY:"+relY+"  FromX:"+fromX+",FromY:"+fromY);
        currentX += (v[1] + ((getVerticalLineWidth(g, v[0]) - selected.getWidth(g)) / 2)); //... plus verschiebung nach rechts durch verlagerte Mitte & MinimalAbstand!
        currentY += (gHUB - v[3] - selected.getHeightUnderBaseline(g));

        //System.out.println("currentX:"+currentX+",currentY:"+currentY);
        return selected.findSelectedObjects(currentX, currentY, fromX, fromY, 
                                            toX, toY, g);

        //return false;
    }

    public final void generateMathMLCode(final MathMLExport mmle) {
        mmle.ifNessesaryInsertInvisibleOperator();

        mmle.addTextLine("<mtable>");

        mmle.increaseLayer();

        for (int j = elements[0].length - 1; j >= 0; j--) {
            mmle.addTextLine("<mtr>");
            mmle.increaseLayer();

            for (int i = 0; i < elements.length; i++) {
                mmle.addTextLine("<mtd>");
                mmle.increaseLayer();

                elements[i][j].generateMathMLCode(mmle);

                mmle.decreaseLayer();
                mmle.addTextLine("</mtd>");
            }

            mmle.decreaseLayer();
            mmle.addTextLine("</mtr>");
        }

        mmle.decreaseLayer();

        mmle.addTextLine("</mtable>");

        mmle.nextElement(true);
    }
}