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
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import org.openuss.openformula.design.language.Language;
import org.openuss.openformula.graphics.InsertSymbol;
import org.openuss.openformula.io.EditorFrame;


/**
 * Klasse, die die Buttons im Formeleditor erzeugt und mit der Darstellungsfläche verk�pft.
 */
public final class ButtonField extends Panel implements ActionListener {
    private static final long serialVersionUID = -7032868784523921463L;
    
	private final Language language;
    private final org.openuss.openformula.io.Cursor cursor;
    private Frame frame;

    /**
     * Erzeugt das ButtonFielt,
     * @param cursor Zu verwendende Cursor-Komponente
     * @param language Zu nutzende Sprache
     */
    public ButtonField(final org.openuss.openformula.io.Cursor cursor, 
                       final Language language) {
        //setLayout(new GridLayout(3, 1));
        final GridBagLayout gb = new GridBagLayout();
        setLayout(gb);

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        for (int i = 1; i <= 7; i++) {
            final BuildLine l = new BuildLine(i, cursor, language);
            gb.setConstraints(l, gbc);
            add(l);
        }

        this.language = language;
        this.cursor = cursor;
    }

    /**
     * Stellt das Men� zur Verf�gung, dass von dem Formeleditor bereitgestellt wird.
     * @param frame Zielframe, in dem das Men� eingefügt wird.
     */
    public final void insertMenu(final Frame frame) {
        final MenuBar menuBar = new MenuBar();
        this.frame = frame;

        Menu menu = new Menu(language.getWord("formula"));
        menu.add(language.getWord("deleteChanges"));
        menu.addSeparator();
        menu.add(language.getWord("exit"));
        menu.addActionListener(this);
        menuBar.add(menu);

        menu = new Menu(language.getWord("edit"));
        menu.add(new MenuItem(language.getWord("undo"), 
                              new MenuShortcut(KeyEvent.VK_Z)));
        menu.add(new MenuItem(language.getWord("redo"), 
                              new MenuShortcut(KeyEvent.VK_Y)));
        menu.addSeparator();
        menu.add(new MenuItem(language.getWord("cut"), 
                              new MenuShortcut(KeyEvent.VK_X)));
        menu.add(new MenuItem(language.getWord("copy"), 
                              new MenuShortcut(KeyEvent.VK_C)));
        menu.add(new MenuItem(language.getWord("insert"), 
                              new MenuShortcut(KeyEvent.VK_V)));
        menu.add(new MenuItem(language.getWord("delete")));
        menu.addSeparator();
        menu.add(new MenuItem(language.getWord("selectAll"), 
                              new MenuShortcut(KeyEvent.VK_A)));
        menu.addActionListener(this);
        menuBar.add(menu);

        frame.setMenuBar(menuBar);
    }

    public final void paint(final Graphics g) {
        super.paint(g);

        g.setColor(new Color(200, 200, 200));

        final Dimension d = getSize();
        g.fillRect(0, 0, d.width, d.height);
    }

    public final void actionPerformed(final ActionEvent e) {
        // Tastenkommandos (mit Strg) ignoriern, werden schon von Cursor abgearbeitet
        if (e.getModifiers() != 0) {
            return;
        }

        final String str = e.getActionCommand();

        if (str.equalsIgnoreCase(language.getWord("deleteChanges"))) {
            if (frame instanceof EditorFrame) {
                ((EditorFrame) frame).close(false);
            }
        } else if (str.equalsIgnoreCase(language.getWord("exit"))) {
            if (frame instanceof EditorFrame) {
                ((EditorFrame) frame).close(true);
            }
        } else if (str.equalsIgnoreCase(language.getWord("undo"))) {
            cursor.keyPressed(
                    new KeyEvent(this, 0, 0, InputEvent.CTRL_MASK, 
                                 KeyEvent.VK_Z, 'z'));
        } else if (str.equalsIgnoreCase(language.getWord("redo"))) {
            cursor.keyPressed(
                    new KeyEvent(this, 0, 0, InputEvent.CTRL_MASK, 
                                 KeyEvent.VK_Y, 'y'));
        } else if (str.equalsIgnoreCase(language.getWord("cut"))) {
            cursor.keyPressed(
                    new KeyEvent(this, 0, 0, InputEvent.CTRL_MASK, 
                                 KeyEvent.VK_X, 'x'));
        } else if (str.equalsIgnoreCase(language.getWord("copy"))) {
            cursor.keyPressed(
                    new KeyEvent(this, 0, 0, InputEvent.CTRL_MASK, 
                                 KeyEvent.VK_C, 'c'));
        } else if (str.equalsIgnoreCase(language.getWord("insert"))) {
            cursor.keyPressed(
                    new KeyEvent(this, 0, 0, InputEvent.CTRL_MASK, 
                                 KeyEvent.VK_V, 'v'));
        } else if (str.equalsIgnoreCase(language.getWord("delete"))) {
            cursor.keyPressed(
                    new KeyEvent(this, 0, 0, 0, KeyEvent.VK_DELETE, 'x'));
        } else if (str.equalsIgnoreCase(language.getWord("selectAll"))) {
            cursor.keyPressed(
                    new KeyEvent(this, 0, 0, InputEvent.CTRL_MASK, 
                                 KeyEvent.VK_A, 'a'));
        }
    }
}

/**
 * Erzeugt eine einzelne Button-Zeile.
 */
final class BuildLine extends Panel {
    private static final long serialVersionUID = 4977167007230998200L;
    
	private final static int width1 = 70;
    private final static int height1 = 80;
    private final static int width2 = 44;
    private final static int height2 = 47;
    private final static int widthSym = 37;
    private final static int heightSym = 30;

    /**
     * Eine einzelne Button-Linie wird als Panel erzeugt
     * @param line Auswahl der Linie. G�ltig ist ein Wert zwischen 1 und 6 für die einzelnen Zeilen
     * @param cursor Die zu verwendene Cursor-Componente
     * @param language Sprachmodul
     */
    public BuildLine(final int line, 
                     final org.openuss.openformula.io.Cursor cursor, 
                     final Language language) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setFont(new Font("SansSerif", Font.PLAIN, 15));

        //NewFlxButton b;
        switch (line) {
        case 1:
            add(new NewFlxButton(
                        "<math><mrow><mfrac><mrow><mi>x</mi></mrow><mrow><mi>y</mi>" + "</mrow></mfrac></mrow></math>", 
                        language.getWord("fraction"), width1, height1, 
                        new InsertSymbol(InsertSymbol.Fraction, cursor)));
            add(new NewFlxButton(
                        "<math><mrow><munderover><mo>&#931;</mo><mrow><mi>i</mi><mo>=</mo>" + "<mi>x</mi></mrow><mrow><mi>y</mi></mrow></munderover></mrow></math>", 
                        language.getWord("sum"), width1, height1, 
                        new InsertSymbol(InsertSymbol.Sum, cursor)));
            add(new NewFlxButton(
                        "<math><mrow><munderover><mo>&#928;</mo><mrow><mi>i</mi><mo>=</mo>" + "<mi>x</mi></mrow><mrow><mi>y</mi></mrow></munderover></mrow></math>", 
                        language.getWord("product"), width1, height1, 
                        new InsertSymbol(InsertSymbol.Factor, cursor)));
            add(new NewFlxButton(
                        "<math><mrow><munderover><mo>&#8747;</mo><mrow><mi>a</mi></mrow><mrow>" + "<mi>b</mi></mrow></munderover></mrow></math>", 
                        language.getWord("integral"), width1, height1, 
                        new InsertSymbol(InsertSymbol.Integral, cursor)));
            add(new NewFlxButton(
                        "<math><mrow><mroot><mrow><mi>x</mi></mrow><mrow><mi>n</mi>" + "</mrow></mroot></mrow></math>", 
                        language.getWord("root"), width1, height1, 
                        new InsertSymbol(InsertSymbol.SquareRoot, cursor)));
            add(new NewFlxButton(
                        "<math><mrow><mtable><mtr><mtd><mrow></mrow></mtd><mtd><mrow>" + "</mrow></mtd></mtr><mtr><mtd><mrow></mrow></mtd><mtd><mrow>" + 
                            "</mrow></mtd></mtr></mtable></mrow></math>", 
                        language.getWord("matrix"), width1, height1, 
                        new InsertSymbol(InsertSymbol.Matrix, cursor)));

            break;

        case 2:
            add(new NewFlxButton(
                        "<math><mrow><mfenced open=\"(\" close=\")\"><mrow><mi>x</mi>" + "</mrow></mfenced></mrow></math>", 
                        "", width2, height2, 
                        new InsertSymbol(InsertSymbol.Parenthesis, cursor)));
            add(new NewFlxButton(
                        "<math><mrow><mfenced open=\"[\" close=\"]\"><mrow><mi>x</mi>" + "</mrow></mfenced></mrow></math>", 
                        "", width2, height2, 
                        new InsertSymbol(InsertSymbol.SquareBracket, cursor)));
            add(new NewFlxButton(
                        "<math><mrow><mfenced open=\"|\" close=\"|\"><mrow><mi>x</mi>" + "</mrow></mfenced></mrow></math>", 
                        "", width2, height2, 
                        new InsertSymbol(InsertSymbol.VerticalLine, cursor)));
            add(new NewFlxButton(
                        "<math><mrow><mfenced open=\"{\" close=\"}\"><mrow><mi>x</mi>" + "</mrow></mfenced></mrow></math>", 
                        "", width2, height2, 
                        new InsertSymbol(InsertSymbol.CurlyBracket, cursor)));
            add(new NewFlxButton(
                        "<math><mrow><mfenced open=\"{\" close=\" \"><mrow><mi>x</mi>" + "</mrow></mfenced></mrow></math>", 
                        "", width2, height2, 
                        new InsertSymbol(InsertSymbol.LeftCurlyBracket, cursor)));
            add(new NewFlxButton(
                        "<math><mrow><mfenced open=\" \" close=\"}\"><mrow><mi>x</mi>" + "</mrow></mfenced></mrow></math>", 
                        "", width2, height2, 
                        new InsertSymbol(InsertSymbol.RightCurlyBracket, cursor)));
            add(new NewFlxButton(
                        "<math><mrow><msup><mrow><mi>x</mi></mrow><mrow><mi>m</mi></mrow>" + "</msup></mrow></math>", 
                        "", width2, height2, 
                        new org.openuss.openformula.graphics.InsertSymbol(
                                org.openuss.openformula.graphics.InsertSymbol.SuperScript, 
                                cursor)));
            add(new NewFlxButton(
                        "<math><mrow><msub><mrow><mi>x</mi></mrow><mrow><mi>n</mi></mrow>" + "</msub></mrow></math>", 
                        "", width2, height2, 
                        new org.openuss.openformula.graphics.InsertSymbol(
                                org.openuss.openformula.graphics.InsertSymbol.SubScript, 
                                cursor)));
            add(new NewFlxButton(
                        "<math><mrow><msubsup><mrow><mi>x</mi></mrow><mrow><mi>n</mi>" + "</mrow><mrow><mi>m</mi></mrow></msubsup></mrow></math>", 
                        "", width2, height2, 
                        new org.openuss.openformula.graphics.InsertSymbol(
                                org.openuss.openformula.graphics.InsertSymbol.SubSuperScript, 
                                cursor)));
            add(new NewFlxButton(
                        "<math><mrow><mover><mrow><mo>:</mo><mo>=</mo></mrow><mrow><mi>!</mi>" + "</mrow></mover></mrow></math>", 
                        "", width2, height2, 
                        new org.openuss.openformula.graphics.InsertSymbol(
                                org.openuss.openformula.graphics.InsertSymbol.OverScript, 
                                cursor)));
            add(new NewFlxButton(
                        "<math><mrow><munder><mrow><mi>l</mi><mo>&InvisibleTimes;</mo>" + "<mi>i</mi><mo>&InvisibleTimes;</mo><mi>m</mi></mrow><mrow>" + 
                            "<mi>n</mi><mo>&#8594;</mo><mi>x</mi></mrow></munder></mrow>" + 
                            "</math>", "", width2, height2, 
                        new org.openuss.openformula.graphics.InsertSymbol(
                                org.openuss.openformula.graphics.InsertSymbol.UnderScript, 
                                cursor)));
            add(new NewFlxButton(
                        "<math><mrow><munderover><mrow><mo>&#8746;</mo></mrow><mrow>" + "<mi>i</mi><mo>=</mo><mn>1</mn></mrow><mrow><mi>n</mi></mrow>" + 
                            "</munderover></mrow></math>", "", width2, height2, 
                        new org.openuss.openformula.graphics.InsertSymbol(
                                org.openuss.openformula.graphics.InsertSymbol.UnderOverScript, 
                                cursor)));

            break;

        case 3:
            add(new NewFlxButton("<math><mrow><mo>&#8704;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // für alle
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 8704, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8707;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // existiert ein
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x2203, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8708;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // existiert nicht
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x2204, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8712;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // Element von
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x2208, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8713;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // kein Element von
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x2209, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8745;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // Schnittmenge
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x2229, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8746;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // Vereinigung
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x222A, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8800;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // ungleich
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x2260, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8801;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // identisch
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x2261, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8776;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // �hnlich
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x2248, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8804;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // kleiner gleich
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x2264, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8805;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // gr��er gleich
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x2265, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8942;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // Doppelpfeil links, rechts
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x22EE, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8943;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // Doppelpfeil links, rechts
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x22EF, cursor)));

            break;

        case 4:
            add(new NewFlxButton("<math><mrow><mo>&#8743;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // logisch Und
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x2227, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8744;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // logisch Oder
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x2228, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#172;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // not
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 172, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#177;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // Plus/Minus
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0xB1, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8734;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // undendlich
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x221E, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8709;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // leere Menge
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x2205, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8592;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // Pfeil links
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x2190, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8594;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // Pfeil rechts
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x2192, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8596;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // Pfeil links, rechts
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x2194, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8656;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // Doppelpfeil links
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x21D0, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8658;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // Doppelpfeil rechts
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x21D2, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8660;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // Doppelpfeil links, rechts
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x21D4, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8944;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // Doppelpfeil links, rechts
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x22F0, cursor)));
            add(new NewFlxButton("<math><mrow><mo>&#8945;</mo></mrow></math>", 
                                 "", widthSym, heightSym, // Doppelpfeil links, rechts
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.InsertSymbolChar, 
                                         (char) 0x22F1, cursor)));

            break;

        case 5:

            Canvas c = new Canvas() {
                private static final long serialVersionUID = -5931538989128820762L;

				public void paint(Graphics g) {
                    g.setColor(new Color(230, 230, 230));


                    //g.drawLine(0,0,630,0);
                    g.drawLine(0, 1, 630, 1);

                    g.setColor(new Color(100, 100, 100));
                    g.drawLine(0, 0, 630, 0);
                }
            };

            c.setSize(new Dimension(583, 2));
            add(c);

            break;

        case 6:
            add(new NewFlxButton(
                        "<math><mrow><mover><mi>x</mi><mo>&DiacriticalDot;</mo>" + "</mover></mrow></math>", 
                        "", widthSym, heightSym, 
                        new org.openuss.openformula.graphics.InsertSymbol(
                                org.openuss.openformula.graphics.InsertSymbol.AddDotToLetter, 
                                cursor)));
            add(new NewFlxButton(
                        "<math><mrow><mover><mi>x</mi><mo>&DoubleDot;</mo>" + "</mover></mrow></math>", 
                        "", widthSym, heightSym, 
                        new org.openuss.openformula.graphics.InsertSymbol(
                                org.openuss.openformula.graphics.InsertSymbol.AddDoubleDotToLetter, 
                                cursor)));
            add(new NewFlxButton(
                        "<math><mrow><mover><mi>x</mi><mo>&TripleDot;</mo>" + "</mover></mrow></math>", 
                        "", widthSym, heightSym, 
                        new org.openuss.openformula.graphics.InsertSymbol(
                                org.openuss.openformula.graphics.InsertSymbol.AddTripleDotToLetter, 
                                cursor)));
            add(new NewFlxButton("<math><mrow><mover><mi>x</mi><mo>&Hat;</mo>" + "</mover></mrow></math>", 
                                 "", widthSym, heightSym, 
                                 new org.openuss.openformula.graphics.InsertSymbol(
                                         org.openuss.openformula.graphics.InsertSymbol.AddHatToLetter, 
                                         cursor)));
            add(new NewFlxButton(
                        "<math><mrow><mover><mi>x</mi><mo>&OverBar;</mo>" + "</mover></mrow></math>", 
                        "", widthSym, heightSym, 
                        new org.openuss.openformula.graphics.InsertSymbol(
                                org.openuss.openformula.graphics.InsertSymbol.AddOverBarToLetter, 
                                cursor)));
            add(new NewFlxButton(
                        "<math><mrow><mover><mi>x</mi><mo>&Tilde;</mo>" + "</mover></mrow></math>", 
                        "", widthSym, heightSym, 
                        new org.openuss.openformula.graphics.InsertSymbol(
                                org.openuss.openformula.graphics.InsertSymbol.AddTildeToLetter, 
                                cursor)));

            c = new Canvas() {
                private static final long serialVersionUID = -7924586133799260708L;

				public void paint(final Graphics g) {
                    super.paint(g);
                    g.setColor(new Color(200, 200, 200));

                    final Dimension d = getSize();
                    g.fillRect(0, 0, d.width, d.height);

                    g.setColor(new Color(230, 230, 230));
                    g.drawLine(d.width / 2, 0, d.width / 2, d.height);

                    g.setColor(new Color(100, 100, 100));
                    g.drawLine((d.width / 2) - 1, 0, (d.width / 2) - 1, 
                               d.height);
                }
            };


            c.setSize(new Dimension(10, 30));
            add(c);

            final org.openuss.openformula.graphics.InsertSymbol sym = 
                    new org.openuss.openformula.graphics.InsertSymbol(
                            cursor);

            add(new SymbolChoice(SymbolChoice.BigGreek, sym));

            add(new Panel() {
                private static final long serialVersionUID = -7688966360923266104L;

				public void paint(final Graphics g) {
                    setSize(new Dimension(2, 2));

                    super.paint(g);

                    g.setColor(new Color(200, 200, 200));

                    final Dimension d = getSize();
                    g.fillRect(0, 0, d.width, d.height);
                }
            });

            add(new SymbolChoice(SymbolChoice.LittleGreek, sym));

        case 7:
            setSize(new Dimension(2, 2));
        }
    }

    public final void paint(final Graphics g) {
        super.paint(g);

        g.setColor(new Color(200, 200, 200));

        final Dimension d = getSize();
        g.fillRect(0, 0, d.width, d.height);
    }
}

/*class FlxButton extends Button implements ActionListener, FocusListener {

    org.openuss.openformula.graphics.InsertSymbol iSymbol;

    public FlxButton(String caption, org.openuss.openformula.graphics.InsertSymbol iSymbol) {
        super(caption);
        this.iSymbol = iSymbol;

        addKeyListener(iSymbol.getCursor());

        addActionListener(this);
        addFocusListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        //iSymbol.TransferFocusToDrawArea();
        iSymbol.InsertNow();
    }

    public void focusGained(FocusEvent e) {
        //System.out.println("Get");
    }

    public void focusLost(FocusEvent e) {
        //System.out.println("Lost");
    }
} */

/**
 * Stellt die Auswahlfelder für die griechischen Symbole bereit.
 */
final class SymbolChoice extends Choice implements ItemListener {
	
    private static final long serialVersionUID = 3449254898754829455L;
    
	final static int BigGreek = 0;
    final static int LittleGreek = 1;

    // --Recycle Bin (20.01.04 18:35): final static int Relation = 2;
    // --Recycle Bin (20.01.04 18:35): final static int Arrow = 3;
    private final org.openuss.openformula.graphics.InsertSymbol iSymbol;

    // --Recycle Bin (20.01.04 18:35): private final int map;

    /**
     * Stellt eins der Symbolfelder dar.
     * @param map bei 1 kleingeschriebene , bei 2 gro�geschriebene griechische Symbole
     * @param sym Modul, in dem das Symbol eingefügt werden soll
     */
    public SymbolChoice(final int map, 
                        final org.openuss.openformula.graphics.InsertSymbol sym) {
        iSymbol = sym;
        addItemListener(this);

        setFont(new Font("TimesRoman", Font.PLAIN, 20));

        //this.map = map;
        switch (map) {
        case BigGreek:
            add((char) 0x0391 + " - Alpha");
            add((char) 0x0392 + " - Beta");
            add((char) 0x0393 + " - Gamma");
            add((char) 0x0394 + " - Delta");
            add((char) 0x0395 + " - Epsilon");
            add((char) 0x0396 + " - Zeta");
            add((char) 0x0397 + " - Eta");
            add((char) 0x0398 + " - Theta");
            add((char) 0x0399 + " - Iota");
            add((char) 0x039a + " - Kappa");
            add((char) 0x039b + " - Lambda");
            add((char) 0x039c + " - Mu");
            add((char) 0x039d + " - Nu");
            add((char) 0x039e + " - Xi");
            add((char) 0x039f + " - Omicron");
            add((char) 0x03a0 + " - Pi");
            add((char) 0x03a1 + " - Rho");
            add((char) 0x03a3 + " - Sigma");
            add((char) 0x03a4 + " - Tau");
            add((char) 0x03a5 + " - Upsilon");
            add((char) 0x03a6 + " - Phi");
            add((char) 0x03a7 + " - Chi");
            add((char) 0x03a8 + " - Psi");
            add((char) 0x03a9 + " - Omega");

            break;

        case LittleGreek:
            add((char) 0x03b1 + " - alpha");
            add((char) 0x03b2 + " - beta");
            add((char) 0x03b3 + " - gamma");
            add((char) 0x03b4 + " - delta");
            add((char) 0x03b5 + " - epsilon");
            add((char) 0x03b6 + " - zeta");
            add((char) 0x03b7 + " - eta");
            add((char) 0x03b8 + " - theta");
            add((char) 0x03b9 + " - iota");
            add((char) 0x03ba + " - kappa");
            add((char) 0x03bb + " - lambda");
            add((char) 0x03bc + " - mu");
            add((char) 0x03bd + " - nu");
            add((char) 0x03be + " - xi");
            add((char) 0x03bf + " - omicron");
            add((char) 0x03c0 + " - pi");
            add((char) 0x03c1 + " - rho");
            add((char) 0x03c3 + " - sigma");
            add((char) 0x03c4 + " - tau");
            add((char) 0x03c5 + " - upsilon");
            add((char) 0x03c6 + " - phi");
            add((char) 0x03c7 + " - chi");
            add((char) 0x03c8 + " - psi");
            add((char) 0x03c9 + " - omega");

            break;
        }
    }

    public final void itemStateChanged(final ItemEvent e) {
        final String str = getSelectedItem();
        final char c = str.charAt(0);

        switch (c) {
        case 0xB1: // Sonderbehandlung +- -Zeichen
            iSymbol.InsertLetter(c);

            break;

        default:
            iSymbol.InsertSymbolLetter(c);
        }
    }
}