/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.independent.plotter;

import java.io.Serializable;
import java.util.Vector;

/**
 * <code>FLGFunctionGroup3D</code> repr&auml;sentiert eine Gruppe von <code>FLGFunction3D</code>
 * Objekten. F�r die gesamte Funktionsgruppe wird ein Wertebereich (= sp&auml;terer
 * Zeichenbereich) festgelegt.<p> Zudem wird die <i>Aufl&ouml;sung</i>
 * des Zeichenbereiches festgelegt. Mit zunehmender Aufl&ouml;sung steigt die Genauigkeit, aber
 * auch die Rechenleistung. Ein Wert um die <code>20</code> hat sich bew&auml;hrt.<p>
 * <b>Beispiel</b>: Erzeugt eine Funktionsgruppe, die die Beispielsfunktion aus
 * <code>FLGFunction3D</code> enth&auml;lt. <p> <pre>
 *		FLGFunction3D function = new FLGFunction3D(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
 *																							Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY) {
 *			public double calculate(double x, double z) {
 *				return x+z;
 *			}
 *		};
 *
 *		FLGFunctionGroup3D functionGroup = new FLGFunctionGroup3D(0, 360, 0, 360, 20);
 *		functionGroup.addFunction(function);
 *  </pre> </p>
 * @see	FLGFunction3D
 * @see	FLGPlotter3D
 */
public class FLGFunctionGroup3D implements Serializable {
    private double minX, maxX, minZ, maxZ;
    private double minY = Double.POSITIVE_INFINITY;
    private double maxY = Double.NEGATIVE_INFINITY;
    private Vector functions = new Vector();
    private int res;
    private boolean calcMinMaxY = true;

    /**
     * Erzeugt eine leere Funktionengruppe mit dem spezifizierten Wertebereich und der
     * spezifizierten Aufl&ouml;sung f�r die <i>y</i>-Berechnung.
     * @param	minX,&nbsp;maxX	die Unter- und Obergrenze f&uuml;r x.
     * @param	minZ,&nbsp;maxZ	die Unter- und Obergrenze f&uuml;r z.
     * @param res							die Aufl&ouml;sung f�r die <i>y</i>-Berechnung. Je
     * gr&ouml;sser <i>res</i> desto genauer die Berechnung, aber desto h&ouml;her ist auch die Rechenbelastung.
     */
    public FLGFunctionGroup3D(double minX, double maxX, double minZ, double maxZ, int res) {
        this.minX = minX;
        this.maxX = maxX;
        this.minZ = minZ;
        this.maxZ = maxZ;
        this.res = res;
    }

    /**
     * Liefert die Anzahl der Funktionen in dieser Funktionengruppe.
     * @return	die Anzahl der Funktionen.
     */
    public int getNoFunctions() { return functions.size(); }

    /**
     * Liefert die Untergrenze f&uuml;r <i>x</i> dieser Funktiongruppe.
     * @return minimales <i>x</i>.
     */
    public double getMinX() { return minX; }

    /**
     * Liefert die Obergrenze f&uuml;r <i>x</i> dieser Funktiongruppe.
     * @return maximales <i>x</i>.
     */
    public double getMaxX() { return maxX; }

    /**
     * Liefert den kleinsten <i>y</i>-Wert dieser Funktionengruppe.
     * @return	kleinstes <i>y</i>.
     */
    public double getMinY() { return minY; }

    /**
     * Liefert den gr&ouml;ssten <i>y</i>-Wert dieser
     * @return	gr&ouml;sstes <i>y</i>.
     */
    public double getMaxY() { return maxY; }

    /**
     * Liefert die Untergrenze f&uuml;r <i>z</i> dieser Funktiongruppe.
     * @return minimales <i>z</i>.
     */
    public double getMinZ() { return minZ; }

    /**
     * Liefert die Obergrenze f&uuml;r <i>z</i> dieser Funktiongruppe.
     * @return maximales <i>z</i>.
     */
    public double getMaxZ() { return maxZ; }

    /**
     * Setzt den minimalen y-Wert der Funktionsgruppe.
     * @param	y der neue minimale y-Wert.
     */
    public void setMinY(double y) { minY = y; }

    /**
     * Setzt den maximalen y-Wert der Funktionsgruppe.
     * @param	y der neue maximale y-Wert.
     */
    public void setMaxY(double y) { maxY = y; }

    /**
     * Setzt den minimalen x-Wert der Funktionsgruppe.
     * @param	x der neue minimale x-Wert.
     */
    public void setMinX(double x) { minX = x; }

    /**
     * Setzt den maximalen x-Wert der Funktionsgruppe.
     * @param	x der neue maximale x-Wert.
     */
    public void setMaxX(double x) { maxX = x; }

    /**
     * Setzt den minimalen z-Wert der Funktionsgruppe.
     * @param	z der neue minimale z-Wert.
     */
    public void setMinZ(double z) { minZ = z; }

    /**
     * Setzt den maximalen z-Wert der Funktionsgruppe.
     * @param	z der neue maximale z-Wert.
     */
    public void setMaxZ(double z) { maxZ = z; }

    /**
     * Grenzen der Y-Achse automatisch ermitteln?
     * @param	b true, falls sie automatisch ermittelt werden sollen
     */
    public void setAutoCalculateYBounds(boolean b) {
        calcMinMaxY = b;
    }

    /**
     * F�gt die spezifizierte Funktion der Funktionengruppe hinzu.
     * @param	function	die hinzuzuf&uuml;gende <code>FLGFunction3D</code>.
     */
    public void addFunction(FLGFunction3D function) {
        functions.addElement(function);
        if (calcMinMaxY) {
            double fctX, fctZ, fctY;
            boolean changedMaxY = false;
            boolean changedMinY = false;
            for (int z = 0; z < (res + 1); z++) {
                for (int x = 0; x < (res + 1); x++) {
                    fctX = minX + ((double)x * (maxX - minX)) / (double)res;
                    fctZ = minZ + ((double)z * (maxZ - minZ)) / (double)res;
                    if (function.contains(fctX, fctZ)) {
                        fctY = function.calculate(fctX, fctZ);
                        if (fctY > maxY) {
                            maxY = fctY;
                            changedMaxY = true;
                        }
                        if (fctY < minY) {
                            minY = fctY;
                            changedMinY = true;
                        }
                    }
                }
            }
            double offs = (maxY - minY) * 0.000001;
            if (changedMaxY) maxY += offs;
            if (changedMinY) minY -= offs;
        }
    }

    public void removeFunction(int ind) {
        if (ind >= 0 && ind < functions.size())
            functions.removeElementAt(ind);
    }

    /**
     * Liefert die Funktion der Funktionengruppe, die an der spezifizierten Stelle steht.
     * @param	ind	der Index der Funktion.
     */
    public FLGFunction3D getFunction(int ind) {
        if (ind >= 0 && ind < functions.size())
            return (FLGFunction3D)functions.elementAt(ind);
        return null;
    }
}
