/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.independent.plotter;

import java.awt.Color;
import java.awt.Stroke;
import java.util.Vector;

/**
 * Ein <code>FLGPolygon2D</code> repr&auml;sentiert ein Polygon. Dem Polygon ist zudem eine Farbe
 * zugeordnet, Standardfarbe ist schwarz. Wenn diese Funktion einem Plotter zugeordnet werden
 * soll, dessen Legende angezeigt wird, ist der Name der Funktion entweder im Konstruktor mit anzugeben, oder explizit durch
 * die Methode <code>setName(String name)</code> zu setzen.<p> <b>Beispiel</b>:	Erzeugt ein Polygon, welches
 * eine Konstante von <code>0.5</code> in einem Wertebereich von <code>-10</code> bis
 * <code>10</code> repr&auml;sentiert, z.B. eine Untergrenze. Das Polygon wird rot gezeichnet werden. <p> <pre>
 *  FLGPolygon2D polygon = new FLGPolygon2D("Untergrenze");
 *  polygon.add(new FLGPoint2D(-10,0.5));
 *  polygon.add(new FLGPoint2D(10,0.5));
 *  polygon.setColor(Color.red);
 *  </pre> </p> Initial sind die minimalen <i>x</i> und <i>y</i> Werte mit
 * <code>Double.POSITIVE_INFINITY</code> und die maximalen <i>x</i> und <i>y</i> Werte mit
 * <code>Double.NEGATIVE_INFINITY</code> initialisiert.
 * @see	FLGPolygonGroup2D
 * @see	FLGPlotter2D
 * @see freestyleLearningGroup.independent.util.FLGGraphics
 */
public class FLGPolygon2D {
    private double minX = Double.POSITIVE_INFINITY;
    private double maxX = Double.NEGATIVE_INFINITY;
    private double minY = Double.POSITIVE_INFINITY;
    private double maxY = Double.NEGATIVE_INFINITY;
    private Color color = Color.black;
    private Stroke stroke = null;
    private Vector points = new Vector();

    /** Der Name der Polygongruppe, der in der Legende angezeigt werden soll. */
    private String name = null;
    //
    // Konstruktoren
    //

    /** Erzeugt ein uninitialisiertes FLGPolygon2D. Die Standardfarbe f&uuml;r ein Polygon ist schwarz. */
    public FLGPolygon2D() {
    }

    /**
     * Erzeugt ein leere Polygongruppe mit dem spezifizierten Namen f&uuml;r die Legende.
     * @param	name	der Name der Polygongruppe.
     */
    public FLGPolygon2D(String name) {
        setName(name);
    }
    //
    // Access Methods
    //

    /**
     * Setzt den spezifizierten Namen f&uuml;r die Legende.
     * @param	name	der neue Name der Polygongruppe.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Liefert den Namen f&uuml;r die Legende.
     * @return	den Namen der Polygongruppe oder <code>null</code>.
     */
    public String getName() {
        return name;
    }

    /**
     * Liefert den minimalen x-Wert des Polygons.
     * @return	minX.
     */
    public double getMinX() { return minX; }

    /**
     * Liefert den maximalen x-Wert des Polygons.
     * @return	maxX.
     */
    public double getMaxX() { return maxX; }

    /**
     * Liefert den minimalen y-Wert des Polygons.
     * @return	minY.
     */
    public double getMinY() { return minY; }

    /**
     * Liefert den maximalen y-Wert des Polygons.
     * @return	maxY.
     */
    public double getMaxY() { return maxY; }

    /**
     * Setzt die Farbe des Polygons.
     * @param	c	die Farbe.
     */
    public void setColor(Color c) { this.color = c; }

    /**
     * Liefert die Farbe des Polygons.
     * @return	die Farbe.
     */
    public Color getColor() { return color; }

    /**
     * Weist dem Polygon die spezifizierte Strichart zu.<p> Die Zuweisung von Linienarten wird
     * empfohlen, da dadurch die &quot;Lesbarkeit&quot; deutlich erh&ouml;ht wird, wenn der
     * Plotter auf einem nicht-farbf&auml;higen Drucker ausgegeben wird. Eine Reihe von vordefinierten Linienarten sind in der
     * Klasse <code>FLGGraphics</code> zu finden. <p> <pre>
	 *  polygon.setStroke(FLGGraphics.getStroke(FLGGraphics.STROKE_DASHED));
	 *  </pre> </p>
     * @param	stroke	die Linienart.
     * @see freestyleLearningGroup.independent.util.FLGGraphics
     */
    public void setStroke(Stroke stroke) { this.stroke = stroke; }

    /**
     * Liefert die der Funktion zugeordnete Farbe.
     * @return die Lienienart.
     */
    public Stroke getStroke() { return stroke; }

    /**
     * F�gt den spezifizierten FLGPoint2D dem Polygon hinzu.
     * @param	point	der Punkt.
     */
    public void add(FLGPoint2D point) {
        points.addElement(point);
        double offs;
        if (size() > 1) {
            if (point.getX() < minX) {
                minX = point.getX();
                offs = (maxX - minX) * 0.000001;
                minX -= offs;
            }
            if (point.getX() > maxX) {
                maxX = point.getX();
                offs = (maxX - minX) * 0.000001;
                maxX += offs;
            }
            if (point.getY() < minY) {
                minY = point.getY();
                offs = (maxY - minY) * 0.000001;
                minY -= offs;
            }
            if (point.getY() > maxY) {
                maxY = point.getY();
                offs = (maxY - minY) * 0.000001;
                maxY += offs;
            }
        }
        else { // AS: Sonderbahndlung beim ersten Polygon der Gruppe
            if (point.getX() < minX) minX = point.getX();
            if (point.getX() > maxX) maxX = point.getX();
            if (point.getY() < minY) minY = point.getY();
            if (point.getY() > maxY) {
                maxY = point.getY();
            }
            offs = (maxX - minX) * 0.000001;
            maxX += offs;
            minX -= offs;
            offs = (maxY - minY) * 0.000001;
            maxY += offs;
            minY -= offs;
        }
    }

    /**
     * Liefert den Punkt des Polygons an der spezifizierten Stelle <code>no</code>.
     * @return	den Punkt an der Stelle <code>no</code>.
     * @param	no	Position des zu lesenden Punktes.
     */
    public FLGPoint2D get(int no) { return (FLGPoint2D)points.elementAt(no); }

    /**
     * Liefert die Anzahl der Punkte des Polygons.
     * @return	Anzahl der Punkte.
     */
    public int size() { return points.size(); }

	/*
	 * Initialisiert das Polygon.
	 * Enth�lt das Polygon bereits Punkte werden die tats&auml;chlichen minimalen
	 * und maximalen Werte von x und y berechnet.
 	 * @deprecated	Die <code>init()</code> Methode ist nur vorhanden um
	 * die Lauff�higkeit vorhandener Programme zu gew&auml;hrleisten. Neue
	 * Programme sollten diese Methode nicht mehr verwenden.
	
	public void init() {
		// initial values for minX and maxX
		// AS minX = Double.POSITIVE_INFINITY;	maxX = Double.NEGATIVE_INFINITY;
		// AS minY = Double.POSITIVE_INFINITY;	maxY = Double.NEGATIVE_INFINITY;
		
		// calculate minX, maxX, minY, maxY
		for (int d = 0; d < points.size(); d++) {
			FLGPoint2D aPoint = (FLGPoint2D) points.elementAt(d);
			if (aPoint.getX() < minX)	minX = aPoint.getX();
			if (aPoint.getX() > maxX)	maxX = aPoint.getX();
			if (aPoint.getY() < minY)	minY = aPoint.getY();
			if (aPoint.getY() > maxY)	maxY = aPoint.getY();			
		}
		
		double offs = (maxX - minX) * 0.000001;
		maxX += offs;
		minX -= offs;

		offs = (maxY - minY) * 0.000001;
		maxY += offs;
		minY -= offs;
	}
	*/
}