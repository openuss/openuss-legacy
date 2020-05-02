/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.io;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ResourceBundle;

import org.openuss.openformula.design.AppletButtons;
import org.openuss.openformula.design.language.Language;
import org.openuss.openformula.design.language.Translation;


/**
 * Stellt das auf der Webseite abgebildete Applet dar.
 * 
 * @author Lofi Dewanto
 * @author Ingo D�ppe
 *          Update the application context
 */
public final class RunAsApplet extends Applet implements ActionListener {
    
	private static final long serialVersionUID = 7163084051769667018L;
	
	/**
     * Aktuelle Build-Version des Formeleditors. Wird zur Abgrenzung von veralteten
     * Versionen gelegentlich erh�ht.
     */
    public static final int formulaBuildNumber = 37;
    public static boolean versionShowed = false;

    // Constants für formula configuration
    static ResourceBundle formulaConfig = ResourceBundle.getBundle(
                                                  "org.openuss.openformula.io.FormulaConfig");

    /**
     * Standard-MathML-Code für Fehlermeldungen.
     */
    public static final String defaultErrorInMathML = "<math><mrow><mi>N</mi><mo>" + 
                                                      "&InvisibleTimes;</mo><mi>e</mi><mo>&InvisibleTimes;</mo><mi>t</mi><mo>" + 
                                                      "&InvisibleTimes;</mo><mi>w</mi><mo>&InvisibleTimes;</mo><mi>o</mi><mo>" + 
                                                      "&InvisibleTimes;</mo><mi>r</mi><mo>&InvisibleTimes;</mo><mi>k</mi><mspace/>" + 
                                                      "<mi>o</mi><mo>&InvisibleTimes;</mo><mi>r</mi><mspace/><mi>d</mi><mo>" + 
                                                      "&InvisibleTimes;</mo><mi>a</mi><mo>&InvisibleTimes;</mo><mi>t</mi><mo>" + 
                                                      "&InvisibleTimes;</mo><mi>a</mi><mo>&InvisibleTimes;</mo><mi>b</mi><mo>" + 
                                                      "&InvisibleTimes;</mo><mi>a</mi><mo>&InvisibleTimes;</mo><mi>s</mi><mo>" + 
                                                      "&InvisibleTimes;</mo><mi>e</mi><mspace/><mi>e</mi><mi>r</mi><mo>" + 
                                                      "&InvisibleTimes;</mo><mi>r</mi><mo>&InvisibleTimes;</mo><mi>o</mi>" + 
                                                      "<mo>&InvisibleTimes;</mo><mi>r</mi><mi>!</mi></mrow></math>";
    private FormulaCanvas draw;
    protected String formula;
    private String mathMLfromServer;
    private boolean readonly;
    private ParallelJobManager wfss;
    protected final Thread externCallerThread = null;
    protected String idNumber;

    /**
     * Initialiesiert das Applet, wird vom Browser aufgerufen.
     * Als Parameter stehen zur Verf�gung:
     * <code>id        </code>Diese ID vom Server laden.
     * <code>readonly  </code>Das Applet im NurLese-Modus betreiben
     * <code>language  </code>Das Applet mit einer Sprache anzeigen, siehe Klasse Translation
     */
    public final void init() {
        if (!versionShowed) {
            System.out.println(
                    "-------------------------------------------------------------------------");
            System.out.println("OpenUSS Formula Editor Build " + 
                               formulaBuildNumber);
            System.out.println("written by Jan Kirchhoff (jkirchhoff@gmx.de)");
            System.out.println(
                    "-------------------------------------------------------------------------\n");
            versionShowed = true;
        }

        setBackground(Color.white);
        setLayout(new BorderLayout());

        draw = new FormulaCanvas(true, true, 35, 10);
        add(draw, BorderLayout.CENTER);

        final String id = getParameter("id");

        if (id != null) {
            readData(id);
        }

        final String ro = getParameter("readonly");

        if ((ro != null) && (ro.equalsIgnoreCase("true"))) {
            readonly = true;
        } else {
            readonly = false;
        }

        final String language = getParameter("language");

        if (language != null) {
            Translation.setLanguage(language);
        }

        add(new AppletButtons(draw.cursor, new Language(new Translation()), 
                              readonly), BorderLayout.NORTH);

        if (Cursor.isRealyOldJavaVersion()) {
            // IE Send Handling
            wfss = new ParallelJobManager(this);
            wfss.start();
        } else {
            // Netscape Handling
            wfss = null;
        }
    }

    /**
     * Liest die zur id passenden Daten aus der aktuellen Base-URL + "GetFormula.po"
     * und stellt sie im Formeleditor-Viewer da.
     * Kann extern von JavaScript aufgerufen werden, wenn die Formel zurückgesetzt
     * oder aktualiesiert werden soll.
     * @param id Die id der Formel.
     */
    private void readData(final String id) {
        URL url = getCodeBase();
        String urlStr = url.getHost();

        if (url.getPort() != 80) {
            urlStr = urlStr + ":" + url.getPort();
        }

        if (urlStr.equals(":-1")) {
            urlStr = "localhost:8080";
        }

        urlStr = "http://" + urlStr + 
                 formulaConfig.getString("formula.GetServlet") + id;
        
        mathMLfromServer = "";

        try {
            url = new URL(urlStr);

            final BufferedReader in = new BufferedReader(
                                              new InputStreamReader(
                                                      url.openStream()));

            while (in.ready())
                mathMLfromServer = mathMLfromServer + in.readLine();

            //System.out.println(mathMLfromServer);
            if (mathMLfromServer != null) {
                draw.getMyCursor().parse(mathMLfromServer);
            } else {
                draw.getMyCursor().parse(defaultErrorInMathML);
            }
        } catch (Exception e) {
            System.out.println("Can't connect or wrong data: " + 
                               e.toString());
            e.printStackTrace();
            draw.getMyCursor().parse(defaultErrorInMathML);
        }
    }

    /**
     * Parameter des Applets ermitteln und zurückgeben
     * @return Applet-Parameter
     */
    public final String[][] getParameterInfo() {
        final String[][] pinfo = 
        {
            {
                "readonly", "boolean", 
                "true: Formula is readonly, User can't edit. Default: false."
            }, 
            { "id", "string", "get the formula with this ID. Default: No ID, create new." }, 
            { "language", "string", "Language String. Default is german." }, 
        };

        //{"url", "url", "get the formula from this URL"}};
        return pinfo;
    }

    @Override
    public final void paint(final Graphics g) {
        super.paint(g);
    }

    /**
     * Das Applett auf den Ursprungszustand zurücksetzen.
     * (Die ursprüngliche Formel wird wieder angezeigt.)
     */
    public final void reset() {
    	draw.getMyCursor().getPrimaryElement().selectAll();
        draw.getMyCursor().getPrimaryElement().deleteSelection();
        draw.getMyCursor().parse(mathMLfromServer);
        draw.repaint();
    }

    /**
     * Die Daten vom Server übertragen. Funktioniert sowohl mit IE als auch mit Netscape/Mozilla
     * @return Neue ID der Formel
     */
    public final String submit() {
        if (wfss != null) {
            //System.out.println("Extern Submit Request incomming (through JavaScript?)! Synchronize with Thread!");
            wfss.submitData();

            int k = 10;

            while (wfss.getIdentifier().equals("")) {
                try {
                    Thread.yield();
                    Thread.sleep(100);

                    //System.out.println("x" + wfss.getId());
                } catch (InterruptedException e) {
                }

                if (k-- == 0) {
                    //System.out.println("failed after 5 sec. No id determinated!");
                    return "error - thread lock";
                }
            }

            final String id = wfss.getIdentifier();

            //System.out.println("Done. Id is " + id + "!");
            return id;
        } else {
            //System.out.println("Extern Submit incomming!");
            final String id = sendData();

            //System.out.println("Done. Id is " + id + "!");
            return id;
        }
    }

    /**
     * Save with Base URL + "SaveFormula.po"
     * Die Daten zum Server übertragen. Sollte nicht durch JavaScript aufgerufen werden,
     * dazu sollte <code>submit</code> verwendet werden.
     * @return Neue id der Formel
     */
    public final String sendData() {
        if (readonly) {
            return "";
        }

        mathMLfromServer = draw.getMyCursor().generateMathMLCode(false);

        URL url = getCodeBase();
        String urlStr = url.getHost();

        if (url.getPort() != 80) {
            urlStr = urlStr + ":" + url.getPort();
        }

        if (urlStr.equals(":-1")) {
            urlStr = "localhost:8080";
        }

        urlStr = "http://" + urlStr + 
                 formulaConfig.getString("formula.SaveServlet") + getParameter("id"); 
        
        System.out.println("Sending" + urlStr);
        
        try {
            url = new URL(urlStr);

            final URLConnection uc = url.openConnection();
            uc.setDoInput(true);
            uc.setDoOutput(true);
            uc.setUseCaches(false);
            uc.setDefaultUseCaches(false);
            uc.setRequestProperty("Content-Type", "plain/text");
            
            PrintWriter out = new PrintWriter(uc.getOutputStream());
            out.println();
            out.print("formula="+mathMLfromServer);
            out.close();          
            
//           uc.setRequestProperty("formula", mathMLfromServer);
            
            final BufferedReader in = new BufferedReader(
                                              new InputStreamReader(
                                                      uc.getInputStream()));

            if (in.ready()) {
                final String id = in.readLine();

                if (id.startsWith("accept as ")) {
                    System.out.println("Submit of data succed!");
                    return id.substring(10);
                }
            }
        } catch (Exception e) {
            System.out.println("Can't connect or wrong data: " + 
                               e.toString());
        }

        return "error";
    }

    public final void stop() {
    	submit();
    }

    public final void actionPerformed(final ActionEvent e) {
    	System.out.println("ActionEvent "+e.getActionCommand());
    }

//    private static String encode(final String str) {
//        try {
//			return URLEncoder.encode(str,"UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			return "";
//		}
//    }

    public static String decode(final String str) {
        try {
			return URLDecoder.decode(str,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
    }
}