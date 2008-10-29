/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.design;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.openuss.openformula.design.language.Language;
import org.openuss.openformula.io.EditorFrame;

/**
 * Erzeugt die Buttonleiste in der "einfachen" Ansicht im Formeleditor. Stellt
 * den Button für Kopieren und Editieren zur Verfügung.
 */
public final class AppletButtons extends Panel implements ActionListener {
	private static final long serialVersionUID = -2438405365904158196L;
	private final Language language;
	private final org.openuss.openformula.io.Cursor cursor;
	private EditorFrame editorFrame;

	// --Recycle Bin (20.01.04 18:35): private final boolean readonly;

	/**
	 * Konstruktor, basiert auf Panel.
	 * 
	 * @param cursor
	 *            Cursor-Objekt der Arbeitsumgebung
	 * @param language
	 *            Zu wählende Sprache
	 * @param readonly
	 *            Bei true Darstellung im ReadOnly-Modus
	 */
	public AppletButtons(final org.openuss.openformula.io.Cursor cursor, final Language language,
			final boolean readonly) {
		this.language = language;
		this.cursor = cursor;

		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBackground(new Color(200, 200, 200));

		Button but = new Button(language.getWord("startEdit"));

		if (!readonly) {
			but.addActionListener(this);
		} else {
			but.setEnabled(false);
		}

		add(but);
		but = new Button(language.getWord("copyFormula"));
		but.addActionListener(this);
		add(but);

		// final Label label=new Label(" (build
		// "+org.openuss.openformula.io.RunAsApplet.formulaBuildNumber+")");
		// label.setBackground(new Color(200, 200, 200));
		// add(label);
		validate();
		add(new Panel() {
			private static final long serialVersionUID = 1811547609849117814L;

			public void paint(final Graphics g) {
				setSize(new Dimension(2, 2));

				super.paint(g);

				g.setColor(new Color(200, 200, 200));

				final Dimension d = getSize();
				g.fillRect(0, 0, d.width, d.height);
			}
		});

		// this.readonly = readonly;
	}

	public final void paint(final Graphics g) {
		super.paint(g);

		g.setColor(new Color(200, 200, 200));

		final Dimension d = getSize();
		g.fillRect(0, 0, d.width, d.height);
	}

	private void buildEditorFrame() {
		editorFrame = new EditorFrame();
		editorFrame.setSize(700, 550);
		editorFrame.setVisible(true);
		editorFrame.toFront();
		editorFrame.requestFocus();
		editorFrame.writeOutputToCursor(cursor);
		editorFrame.getFormulaCursor().parse(cursor.generateMathMLCode(false));
	}

	public final void actionPerformed(final ActionEvent e) {
		final String str = e.getActionCommand();

		if (str.equals(language.getWord("startEdit"))) {
			if (editorFrame != null) {
				if (editorFrame.isClosed()) {
					buildEditorFrame();
				}
			} else {
				buildEditorFrame();
			}

			// EditorFrame.main(null);
		} else if (str.equals(language.getWord("copyFormula"))) {
			org.openuss.openformula.io.Cursor.Zwischenablage = cursor.generateMathMLCode(false);
		}
	}
}