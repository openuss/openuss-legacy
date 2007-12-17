/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.independent.world3D;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import freestyleLearningGroup.independent.plotter.FLGCoordSystem;

/**
 * Eine 3D Welt, die eine beliebige Anzahl von 3D Objekten enthalten kann. Die Welt kann rotiert
 * oder verschoben werden.<p> Ein <code>LGWorld3D</code> Objekt ist keine Komponente und kann
 * daher nicht einfach einem Panel oder &auml;hnlichem hinzugef&uuml;gt werden. Vielmehr muss die <code>paint</code>-Methode
 * der Welt aus der <code>paint</code>-Methode der Komponente heraus aufgerufen werden, die auch an den Konstruktor
 * der 3D Welt &uuml;bergeben wurde. <p> <b>Beispiel</b>: Erstellt wird eine 3D Welt, die eine Pyramide als einziges Objekt
 * hat. Die Pyramide rotiert um zwei Achsen in 1-Grad Schritten. <p> <pre>
 * import freestyleLearningGroup.independent.world3D.*;
 *
 * import javax.swing.*;
 * import java.awt.*;
 * import java.awt.image.*;
 *
 * public class World3DSample extends JComponent implements Runnable {
 *
 *	Thread		runner = null;
 *	LGWorld3D	world3D;
 *	int				screenWidth, screenHeight;
 *	
 *	public World3DSample() {
 *		screenWidth = getSize().width;
 *		screenHeight = getSize().height;
 *		
 *		world3D = new LGWorld3D(this);
 *		
 *		LGFace[] faces  = new LGFace[5];
 *
 *		LGPoint[] points = new LGPoint[5];
 *		points[0]  = new LGPoint(0.0, 0.0, 0.0);
 *		points[1]  = new LGPoint(0.0, 100.0, 0.0);
 *		points[2]  = new LGPoint(100.0, 100.0, 0.0);
 *		points[3]  = new LGPoint(100.0, 0.0, 0.0);
 *		points[4]  = new LGPoint(50.0, 50.0, 100.0);
 *		
 *		int[] pointsInd = {0, 1, 2, 3};
 *		faces[0]  = new LGFace(pointsInd, Color.yellow);
 *		faces[1]  = new LGFace(0, 1, 4, Color.red);
 *		faces[2]  = new LGFace(1, 2, 4, Color.magenta);
 *		faces[3]  = new LGFace(2, 3, 4, Color.green);
 *		faces[4]  = new LGFace(3, 0, 4, Color.blue);
 *		
 *		LGObj3D[] objects = new LGObj3D[1];
 *		
 *		objects[0] = new LGObj3D(points, faces, null, 0, true, false, world3D);
 *		
 *		world3D.setObjects(objects);
 *	}
 *
 *	public void start() {
 *		if (runner == null) {
 *			world3D.frames = 0;
 *			runner = new Thread(this);
 *			runner.setPriority(Thread.MAX_PRIORITY);
 *			runner.start();
 *		}
 *	}
 *	
 *	public void run() {
 *		while (true) {
 *			repaint();
 *			world3D.rotate(0,1,1,0);
 *			try { Thread.sleep(10); }
 *			catch (InterruptedException e) { log(e);}
 *		}
 *		
 *	}
 *	
 *	public void stop() {
 *		if (runner != null) {
 *			runner.stop();
 *			runner = null;
 *		}
 *	}
 *	
 *	public void update(Graphics g) {
 *		paint(g);
 *	}
 *	
 *								
 *	public synchronized void paint(Graphics g) {
 *		world3D.paint(g);
 *	}
 *	
 *	public static void main(String[] args) {
 *		
 *		World3DSample sample = new World3DSample();
 *		
 *		JPanel panel = new JPanel(new GridLayout(1,1));
 *		panel.add(sample);
 *		
 *		JFrame frame = new JFrame("Beispiel 1");
 *		frame.getContentPane().add("Center", panel);
 *		frame.setSize(600,400);
 *		frame.setVisible(true);
 *		sample.start();
 *	}
 *
 * }
 *
 *		<img src="../../../../../images/World3D.gif" width="600" height="400">
 *  </pre> </p>
 */
public class FLGWorld3D {
    private static double[] sinTab = new double[361];
    private static double[] cosTab = new double[361];
    // nicht mehr n�tig durch static initializer, AS
    // Folgeauskommentierungen weiter unten...
    // private static boolean	classInitialized = false;
    private static int MAX_NO_TR_MATRICES = 20;

    /** Der Betrachtungspunkt der 3D Welt, initial <code>(0,0,1000)</code>. */
    public FLGPoint viewPoint = new FLGPoint(0, 0, 1000);

    /** Die Objekte der 3D Welt. */
    public FLGObj3D[] objects;

    /** Das Koordinatensystem der 3D Welt. */
    public FLGCoordSystem coordSystem = null;

    /** Wie oft das Modell bereits gezeichnet wurde. */
    public long frames = 0;
    private double scaleFactor = 1;
    private int displayAreaWidth;
    private int displayAreaHeight;
    private double[] [] [] trMatrices;
    private int[] objectsInd = new int[500];
    private Component displayComponent;

    static {
        for (int x = 0; x < 361; x++) {
            sinTab[x] = Math.sin((x * 2d * Math.PI) / 360d);
            cosTab[x] = Math.cos((x * 2d * Math.PI) / 360d);
        }
    }

    /**
     * //    * @deprecated	kann vereinfacht werden durch einen static initializer (s.o.). Aus
     * Testgr�nden hab ich aber nur auskommentiert und nicht gel�scht. AS //    * static void
     * initClass() { // AS classInitialized = true; for (int x = 0;
     * x < 361; x++) { sinTab[x] = Math.sin( (x*2d*Math.PI) / 360d );
     * //    * cosTab[x] = Math.cos( (x*2d*Math.PI) / 360d ); } }
     */

    /**
     * Erzeugt eine 3D Welt und erstellt einen Bezug zu der spezifizierten <code>Component</code> als Grafikkontext.
     * @param	component	der Grafikkontext
     */
    public FLGWorld3D(Component component) {
        // AS if (!(classInitialized)) initClass();
        this.displayComponent = component;
        trMatrices = new double[MAX_NO_TR_MATRICES] [3] [3];
        initTrMatrices();
    }

    /**
     * Weist der leeren 3D Welt die spezifizierten Objekte zu.
     * @param objects	die Objekte dieser Welt.
     */
    public void setObjects(FLGObj3D[] objects) { this.objects = objects; }

    /**
     * Wenn die 3D-Welt mit einem Koordinatensystem gezeichnet werden soll, muss es &uuml;ber
     * diese Funkion der 3D Welt zugewiesen werden.<p> Da ein Koordinatensystem nur f&uuml;r
     * <code>FLGFunctionGroup3D</code> Objekte erzeugt werden kann, wird diese Methode nur im
     * Zusammenhang mit einem 3D Plotter benutzt.
     * @param coordSystem	das Koordinatensystem der Welt.
     * @see freestyleLearningGroup.independent.plotter.FLGCoordSystem
     * @see freestyleLearningGroup.independent.plotter.FLGPlotter3D
     * @see freestyleLearningGroup.independent.plotter.FLGFunctionGroup3D
     */
    public void setCoordSystem(FLGCoordSystem coordSystem) { this.coordSystem = coordSystem; }

    /**
     * Liefert den <code>viewPoint</code> der 3D Welt.
     * @return	der Betrachtungspunkt.
     */
    public FLGPoint getViewPoint() { return viewPoint; }

    /**
     * Liefert die Transformationsmatrix f&uuml;r den spezifizierten Index.
     * @param		ind	Index der gew&uuml;nschten Transformationsmatrix.
     * @return	die Transformationsmatrix, eine 3x3 Matrix vom Typ <code>double</code>.
     */
    public double[] [] getTrMatrix(int ind) { return trMatrices[ind]; }

    /**
     * Setzten den spezifizierten Skalierungsfaktor f&uuml;r diese Welt. Bspw. werden mit einem
     * Skalierungsfaktor von <code>2.0</code> die Objekte dieser 3D Welt in doppelter Gr&ouml;sse gezeichnet.
     * @param factor	der Skalierungsfaktor.
     */
    public void setScaleFactor(double factor) { this.scaleFactor = factor; }

    /**
     * Liefert den Skalierungsfaktor dieser Welt. Der Standardwert ist Eins.
     * @return	der Skalierungsfaktor.
     */
    public double getScaleFactor() { return scaleFactor; }

    /**
     * Liefert den <i>x</i>-Wert der Mitte der Anzeigefl&auml;che dieser Welt.
     * <DL><DT><B>Note:</B><DD> Dieser Wert ist erst <i>nach</i> dem ersten Zeichnen korrekt.</DD></DL>
     * @return	der <i>x</i>-Offset der Anzeigefl&auml;che.
     */
    public double getDisplayAreaOffsX() { return displayAreaWidth / 2; }

    /**
     * Liefert den <i>y</i>-Wert der Mitte der Anzeigefl&auml;che dieser Welt.
     * <DL><DT><B>Note:</B><DD> Dieser Wert ist erst <i>nach</i> dem ersten Zeichnen korrekt.</DD></DL>
     * @return	der <i>y</i>-Offset der Anzeigefl&auml;che.
     */
    public double getDisplayAreaOffsY() { return displayAreaHeight / 2; }

    /**
     * Liefert eine Tabelle mit Sinuswerten der Gradzahlen <code>0-360</code> in 1-Grad Schritten.
     * @return	die Sinustabelle als <code>double</code>-Array.
     */
    public static double[] getSinTab() {
        // AS if (!(classInitialized))	initClass();
        return sinTab;
    }

    /**
     * Liefert eine Tabelle mit Kosinuswerten der Gradzahlen <code>0-360</code> in 1-Grad Schritten.
     * @return	die Kosinustabelle als <code>double</code>-Array.
     */
    public static double[] getCosTab() {
        // AS if (!(classInitialized))	initClass();
        return sinTab;
    }

    /**
     * Initialisiert die Transformationsmatrizen. Die Hauptdiagonale jeder 3x3 Matrix wird mit 1en
     * belegt, der Rest ist mit 0en gef&uuml;llt.
     */
    public void initTrMatrices() {
        int length = trMatrices.length;
        for (int m = 0; m < length; m++)
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (i == j) {
                        trMatrices[m] [i] [j] = 1d;
                    }
                    else {
                        trMatrices[m] [i] [j] = 0d;
                    }
    }

    /**
     * Die Objekte, deren Index in der spezifizierten Reichweite liegen werden quick sortiert.
     * Entscheidend f&uuml; ist der <i>z</i>-Wert der <code>FLGObj3D</code>s, sprich ihre "Tiefe" im Raum.
     * @param left,&nbsp;right	die R�nder der Reichweite, die sortiert werden soll.
     */
    public void sortObjects(int left, int right) {
        int i, j, tempE;
        double e;
        i = left;
        j = right;
        if (j > i) {
            e = objects[objectsInd[(left + right) >> 1]].getZ();
            do {
                while (objects[objectsInd[i]].getZ() < e) { i++; }
                while (objects[objectsInd[j]].getZ() > e) { j--; }
                if (i <= j) {
                    tempE = objectsInd[i];
                    objectsInd[i] = objectsInd[j];
                    objectsInd[j] = tempE;
                    i++;
                    j--;
                }
            }
            while (i <= j);
            sortObjects(left, j);
            sortObjects(i, right);
        }
    }

    /**
     * Rotiert die durch den Index spezifizierte Transformationsmatrix um die spezifizierten Winkel.
     * @param trMatrixInd	der Index der zu rotierenden Transformationsmatrix.
     * @param angleX			der Winkel um wieviel die x-Achse rotiert werden soll.
     * @param angleY			der Winkel um wieviel die y-Achse rotiert werden soll.
     * @param angleZ			der Winkel um wieviel die z-Achse rotiert werden soll.
     */
    public void rotate(int trMatrixInd, int angleX, int angleY, int angleZ) {
        double[] [] matr = trMatrices[trMatrixInd];
        double cx, cy, cz;
        if (angleX != 0) {
            for (int c = 0; c < 3; c++) { // for all columns of the matrix
                cy = matr[1] [c];
                cz = matr[2] [c];
                matr[1] [c] = cosTab[angleX] * cy - sinTab[angleX] * cz;
                matr[2] [c] = sinTab[angleX] * cy + cosTab[angleX] * cz;
            }
        }
        if (angleY != 0) {
            for (int c = 0; c < 3; c++) { // for all columns of the matrix
                cx = matr[0] [c];
                cz = matr[2] [c];
                matr[0] [c] = cosTab[angleY] * cx + sinTab[angleY] * cz;
                matr[2] [c] = -sinTab[angleY] * cx + cosTab[angleY] * cz;
            }
        }
        if (angleZ != 0) {
            for (int c = 0; c < 3; c++) { // for all columns of the matrix
                cx = matr[0] [c];
                cy = matr[1] [c];
                matr[0] [c] = cosTab[angleZ] * cx - sinTab[angleZ] * cy;
                matr[1] [c] = sinTab[angleZ] * cx + cosTab[angleZ] * cy;
            }
        }
    }

    /**
     * Zeichnen der 3D Welt. Wird nicht direkt vom Benutzer aufgerufen.
     * @param g	der Grafikkontext, in dem gezeichnet wird.
     */
    synchronized public void paint(Graphics g) {
        // clear image
        displayAreaWidth = displayComponent.getBounds().width;
        displayAreaHeight = displayComponent.getBounds().height;
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, displayAreaWidth, displayAreaHeight);
        if (coordSystem != null) coordSystem.paintBack(g);
        for (int o = 0; o < objects.length; o++) {
            objectsInd[o] = o;
            // recalculate objects z-Coordinate when rotating, ...
        }
        sortObjects(0, objects.length - 1);
        for (int o = 0; o < objects.length; o++) objects[objectsInd[o]].paint(g);
        if (coordSystem != null) coordSystem.paintFront(g);
        g.setColor(new Color(0, 0, 0));
    }
}