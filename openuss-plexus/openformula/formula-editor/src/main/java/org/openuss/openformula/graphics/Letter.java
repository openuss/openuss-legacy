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

import org.openuss.openformula.mathml.out.MathMLExport;


/**
 * Created by IntelliJ IDEA.
 * User: Proke
 * Date: 03.09.2003
 * Time: 16:52:29
 * To change this template use Options | File Templates.
 */
public class Letter extends Basic {
    private final static int usageOfSmallLetterPercent = 60;
    final static int usageOfLetterPercent = 80;
    private final static int usageOfLetterUnderlinePercent = 10;
    private final static int usageOfLetterUnderlineWithPercent = 95;

    // Dieses Objekt stellt einen Buchstaben dar.
    final char letter;
    private final String smallLetters = "acegmnopqrsuvwxyz";

    // alle Buchstaben, die irgendwas "underline" haben - inklusive der grichischen Zeichen
    private final String deepLetters = "jgpqy|[](){};," + (char) 946 + 
                                       (char) 947 + (char) 950 + (char) 951 + 
                                       (char) 956 + (char) 958 + (char) 961 + 
                                       (char) 966 + (char) 967 + (char) 968;

    public Letter(final Basic parent, final char letter) {
        super(parent);


        //if (letter == '*') letter = 0x22c5;
        this.letter = letter;
    }

    public int getWidthUncached(final Graphics g) {
        modifyGraphicsHandle(g);

        final FontMetrics fm = g.getFontMetrics();

        return fm.charWidth(letter);
    }

    public int getHeightAboveBaselineUncached(final Graphics g) {
        modifyGraphicsHandle(g);

        final FontMetrics fm = g.getFontMetrics();

        if (smallLetters.indexOf(letter) != -1) {
            return (fm.getAscent() * usageOfSmallLetterPercent) / 100;
        } else {
            return (fm.getAscent() * usageOfLetterPercent) / 100;
        }
    }

    public int getHeightUnderBaselineUncached(final Graphics g) {
        modifyGraphicsHandle(g);

        final FontMetrics fm = g.getFontMetrics();

        if (deepLetters.indexOf(letter) == -1) {
            return (fm.getDescent() * usageOfLetterUnderlinePercent) / 100;
        } else {
            return (fm.getDescent() * usageOfLetterUnderlineWithPercent) / 100;
        }
    }

    public void paint(final Graphics g, final int atX, final int atY) {
        //paintUncached(g, atX, atY);
        modifyGraphicsHandle(g);
        g.drawString("" + letter, atX, atY);
    }

    /*public void paintUncached(Graphics g, int atX, int atY) {
        // So wird die Schriftgröße korrekt berücksichtigt
        //Graphics gg = getGraphicsHandle();
        //gg.setColor(g.getColor());
                                    
                                    
        //int gw=getWidth(gg);
        //int gha=getHeightAboveBaseline(gg);
                                    
        //gg.drawLine(atX,atY-gha,atX+gw,atY-gha);
        //gg.drawLine(atX,atY,atX+gw,atY);
                                    
                                    
        /*for (double d=0.0;d<=1.0;d+=0.2)
        {
            int gh=(int)(gha*d);
            gg.drawLine(atX,atY-gh,atX+gw,atY-gh);
        } */

    //gg.drawLine(atX,atY-gha,atX+gw,atY-gha);
    //protected final String allowedLetters = "abcdefghijklmnoprstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890=|!%/\\#*+~,.;:-_)(<> []?ÄÖÜöäü";
    public void generateMathMLCode(final MathMLExport mmle) {
        final char c = letter;


        //if (c == 0x22c5) c = '*';
        mmle.addLetter(c);

        // Handling in MathMLExport->Layer->AddLetter();
    }
}