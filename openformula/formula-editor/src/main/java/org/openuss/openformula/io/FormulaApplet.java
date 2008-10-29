package org.openuss.openformula.io;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;

import org.openuss.openformula.design.AppletButtons;
import org.openuss.openformula.design.language.Language;
import org.openuss.openformula.design.language.Translation;

/**
 * 
 * @author Ingo Dueppe
 * 
 */
public class FormulaApplet extends Applet {

	private static final long serialVersionUID = -3587578232858833655L;

	public static final int formulaBuildNumber = 38;

	public static boolean versionShowed = false;

	/**
	 * Defaukt-MathML-Code of error message.
	 */
	public static final String defaultErrorInMathML = "<math><mrow><mi>N</mi><mo>"
			+ "&InvisibleTimes;</mo><mi>e</mi><mo>&InvisibleTimes;</mo><mi>t</mi><mo>"
			+ "&InvisibleTimes;</mo><mi>w</mi><mo>&InvisibleTimes;</mo><mi>o</mi><mo>"
			+ "&InvisibleTimes;</mo><mi>r</mi><mo>&InvisibleTimes;</mo><mi>k</mi><mspace/>"
			+ "<mi>o</mi><mo>&InvisibleTimes;</mo><mi>r</mi><mspace/><mi>d</mi><mo>"
			+ "&InvisibleTimes;</mo><mi>a</mi><mo>&InvisibleTimes;</mo><mi>t</mi><mo>"
			+ "&InvisibleTimes;</mo><mi>a</mi><mo>&InvisibleTimes;</mo><mi>b</mi><mo>"
			+ "&InvisibleTimes;</mo><mi>a</mi><mo>&InvisibleTimes;</mo><mi>s</mi><mo>"
			+ "&InvisibleTimes;</mo><mi>e</mi><mspace/><mi>e</mi><mi>r</mi><mo>"
			+ "&InvisibleTimes;</mo><mi>r</mi><mo>&InvisibleTimes;</mo><mi>o</mi>"
			+ "<mo>&InvisibleTimes;</mo><mi>r</mi><mi>!</mi></mrow></math>";

	private FormulaCanvas draw;

	private boolean readonly;
	
	private String mathMLFromServer;

	/**
	 * {@inheritDoc} Possible Parameters: <code>formula</code> contains the
	 * formula. <code>readonly</code> read only mode. <code>language</code>
	 * language
	 */
	@Override
	public final void init() {
		super.init();

		setBackground(Color.white);
		setLayout(new BorderLayout());

		draw = new FormulaCanvas(true, true, 35, 10);
		add(draw, BorderLayout.CENTER);

		mathMLFromServer = getParameter("formula");
		System.out.println(mathMLFromServer);
		if (mathMLFromServer != null) {
			draw.getMyCursor().parse(mathMLFromServer);
		} else {
			draw.getMyCursor().parse(defaultErrorInMathML);
		}

		readonly = "true".equalsIgnoreCase(getParameter("readonly"));

		final String language = getParameter("language");
		if (language != null) {
			Translation.setLanguage(language);
		}

		add(new AppletButtons(draw.cursor, new Language(new Translation()), readonly), BorderLayout.NORTH);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[][] getParameterInfo() {
		final String[][] pinfo = {
				{ "readonly", "boolean", "true: Formula is readonly, User can't edit. Default: false." },
				{ "id", "string", "get the formula with this ID. Default: No ID, create new." },
				{ "language", "string", "Language String. Default is german." }, };

		return pinfo;
	}

	@Override
	public String getAppletInfo() {
		String info = "-------------------------------------------------------------------------\n"
				+ "OpenUSS Formula Editor Build " + formulaBuildNumber + "\n"
				+ "written by Jan Kirchhoff (jkirchhoff@gmx.de) \n"
				+ "-------------------------------------------------------------------------\n";
		return info;
	}

	public String getFormula() {
		return draw.getMyCursor().generateMathMLCode(false);
	}

	public void setFormula(String formula) {
		draw.getMyCursor().parse(formula);
	}

	/**
	 * Das Applett auf den Ursprungszustand zurücksetzen. (Die ursprüngliche
	 * Formel wird wieder angezeigt.)
	 */
	public final void reset() {
		draw.getMyCursor().getPrimaryElement().selectAll();
		draw.getMyCursor().getPrimaryElement().deleteSelection();
		draw.getMyCursor().parse(mathMLFromServer);
		draw.repaint();
	}

}
