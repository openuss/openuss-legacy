/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.independent.plotter;

import java.util.Vector;

/**
 * Eine <code>FLGPolygonGroup2D</code> repr&auml;sentiert eine Gruppe von Polygonen. F�r die
 * gesamte Funktionsgruppe wird ein Wertebereich (= sp&auml;terer Zeichenbereich) festgelegt.<p>
 * <b>Beispiel</b>: Erzeugt eine Polygonengruppe, die nur das im Beispiel des <code>FLGPolygon2D</code> enth&auml;lt. <p> <pre>
 *  FLGPolygon2D polygon = new FLGPolygon2D("Untergrenze");
 *  polygon.add(new FLGPoint2D(-10,0.5));
 *  polygon.add(new FLGPoint2D(10,0.5));
 *
 *  FLGPolygonGroup group = new FLGPolygonGroup();
 *  group.add(polygon);
 *  </pre> </p>
 * @see	FLGPolygon2D
 * @see	FLGPlotter2D
 */
public class FLGPolygonGroup2D {
    private double minX = Double.POSITIVE_INFINITY;
    private double maxX = Double.NEGATIVE_INFINITY;
    private double minY = Double.POSITIVE_INFINITY;
    private double maxY = Double.NEGATIVE_INFINITY;
    private Vector polygons = new Vector();
    //
    // Konstruktoren
    //

    /** Erzeugt eine leere Polygongruppe. */
    public FLGPolygonGroup2D() {
    }
    //
    // Access Methods
    //

    /**
     * Liefert den minimalen x-Wert der Polygonengruppe.
     * @return	minX.
     */
    public double getMinX() { return minX; }

    /**
     * Liefert den maximalen x-Wert der Polygonengruppe.
     * @return	maxX.
     */
    public double getMaxX() { return maxX; }

    /**
     * Liefert den minimalen y-Wert der Polygonengruppe.
     * @return	minY.
     */
    public double getMinY() { return minY; }

    /**
     * Liefert den maximalen y-Wert der Polygonengruppe.
     * @return	maxY.
     */
    public double getMaxY() { return maxY; }

    /**
     * Setzt den minimalen y-Wert der Polygonengruppe.
     * @param	y der neue minimale y-Wert.
     */
    public void setMinY(double y) { minY = y; }

    /**
     * Setzt den maximalen y-Wert der Polygonengruppe.
     * @param	y der neue maximale y-Wert.
     */
    public void setMaxY(double y) { maxY = y; }

    /**
     * Setzt die Grenzen der x- und y-Werte. Sinnvoll, wenn die FLGPolygonGroup zu Beginn noch keine Elemente besitzt.
     * @param	minX der neue minimale x-Wert.
     * @param	maxX der neue maximale x-Wert.
     * @param	minY der neue minimale y-Wert.
     * @param	maxY der neue maximale y-Wert.
     */
    public void setBounds(double minX, double maxX, double minY, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    /**
     * Liefert die Anzahl der Polygone der Polygonengruppe.
     * @return	Anzahl der Polygone.
     * @deprecated	<code>getNoPolygons()</code> benutzen.
     */
    public int size() { return polygons.size(); }

    /**
     * Liefert die Anzahl der Polygone der Polygonengruppe.
     * @return	Anzahl der Polygone.
     */
    public int getNoPolygons() { return polygons.size(); }

    /**
     * F�gt das spezifizierte Polygon der Polygonengruppe hinzu.
     * @param	polygon	das Polygon.
     */
    public void add(FLGPolygon2D polygon) {
        polygons.addElement(polygon);
        double offs;
        if (size() > 1) {
            if (polygon.getMinX() < minX) {
                minX = polygon.getMinX();
                offs = (maxX - minX) * 0.000001;
                minX -= offs;
            }
            if (polygon.getMaxX() > maxX) {
                maxX = polygon.getMaxX();
                offs = (maxX - minX) * 0.000001;
                maxX += offs;
            }
            if (polygon.getMinY() < minY) {
                minY = polygon.getMinY();
                offs = (maxY - minY) * 0.000001;
                minY -= offs;
            }
            if (polygon.getMaxY() > maxY) {
                maxY = polygon.getMaxY();
                offs = (maxY - minY) * 0.000001;
                maxY += offs;
            }
        }
        else { // AS: Sonderbahndlung beim ersten Polygon der Gruppe
            if (polygon.getMinX() < minX) minX = polygon.getMinX();
            if (polygon.getMaxX() > maxX) maxX = polygon.getMaxX();
            if (polygon.getMinY() < minY) minY = polygon.getMinY();
            if (polygon.getMaxY() > maxY) maxY = polygon.getMaxY();
            offs = (maxX - minX) * 0.000001;
            maxX += offs;
            minX -= offs;
            offs = (maxY - minY) * 0.000001;
            maxY += offs;
            minY -= offs;
        }
    }

    /**
     * Liefert das Polygon an der spezifizierten Stelle <code>ind</code>.
     * @return	den Punkt an der Stelle <code>ind</code>.
     * @param	ind	Position des zu lesenden Polygons.
     */
    public FLGPolygon2D get(int ind) {
        return (FLGPolygon2D)polygons.elementAt(ind);
    }

	/*
	 * Initialisiert die Polygonengruppe.
	 * Ist die Polygonengruppe leer, werden
	 * die minimalen x und y Werte mit <code>Double.POSITIVE_INFINITY</code> und
	 * die maximalen x und y Werte mit <code>Double.NEGATIVE_INFINITY</code> initialisiert.
	 * Enth&auml;lt die Polygonengruppe eins oder mehrere Polygone,
	 * werden die tats&auml;chlichen minimalen
	 * und maximalen Werte von x und y &uuml;ber alle Polygone berechnet.
	public void init() {
		// initial values for minX and maxX
		minX = Double.POSITIVE_INFINITY;	maxX = Double.NEGATIVE_INFINITY;
		minY = Double.POSITIVE_INFINITY;	maxY = Double.NEGATIVE_INFINITY;
		
		// calculate minX, maxX, minY, maxY
		for (int p = 0; p < polygons.size(); p++) {
			FLGPolygon2D aPolygon = (FLGPolygon2D) polygons.elementAt(p);
			aPolygon.init();
			if (aPolygon.getMinX() < minX)	minX = aPolygon.getMinX();
			if (aPolygon.getMaxX() > maxX)	maxX = aPolygon.getMaxX();
			if (aPolygon.getMinY() < minY)	minY = aPolygon.getMinY();
			if (aPolygon.getMaxY() > maxY)	maxY = aPolygon.getMaxY();			
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