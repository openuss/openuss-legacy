/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.independent.util;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Die Klasse <code>FLGMathExtension</code> enth&auml;lt einige n&uuml;tzliche Funktionen zum
 * komfortablen Handling von <code>double</code> Zahlen.
 */
public class FLGMathExtension {
    // Formatierungs-Objekt zur Umwandlung einer Zahl in einen String
    private static NumberFormat numberFormat = NumberFormat.getNumberInstance();

    /** Niemand soll diese Klasse instantiieren. */
    private FLGMathExtension() { } // AS

    /**
     * Konvertiert eine double-Zahl in einen String mit der angegebenen maximalen Anzahl von
     * Nachkommastellen und ohne E-Notation.
     * @param number	die zu konvertierende Zahl
     * @maximumFractionDigits	die maximale Anzahl von Nachkommastellen im String
     */
    public static String doubleToString(double number, int maximumFractionDigits) {
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(maximumFractionDigits);
        return numberFormat.format(number);
    }

    /**
     * Konvertiert eine double-Zahl in einen String mit der angegebenen maximalen Anzahl von
     * Nachkommastellen und ohne E-Notation.
     * @param number	die zu konvertierende Zahl
     * @minimumFractionDigits	die minimale Anzahl von Nachkommastellen im String
     * @maximumFractionDigits	die maximale Anzahl von Nachkommastellen im String
     */
    public static String doubleToString(double number, int minimumFractionDigits, int maximumFractionDigits) {
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(maximumFractionDigits);
        numberFormat.setMinimumFractionDigits(minimumFractionDigits);
        return numberFormat.format(number);
    }

    /**
     * Konvertiert einen String in eine double-Zahl. Falls der String keine double-Zahl darstellt,
     * dann wird eine ParseException geworfen.
     * @param text	der zu konvertierende Text
     */
    public static double stringToDouble(String text) throws ParseException {
        numberFormat = NumberFormat.getNumberInstance();
        return numberFormat.parse(text).doubleValue();
    }

    /**
     * Testet die beiden spezifizierten <code>double</code> Zahlen auf Gleichheit. Signifikant sind die
     * ersten neun Nachkommastellen.
     * @param	a,&nbsp;b	die zu vergleichenden <code>double</code> Zahlen.
     * @return	<code>true</code>, falls <i>a</i> und <i>b</i> bis auf die ersten 9
     * Nachkommastellen gleich sind, sonst <code>false</code>.
     */
    public static boolean equals(double a, double b) {
        return equals(a, b, 9);
    }

    /**
     * Testet die beiden spezifizierten <code>double</code> Zahlen auf Gleichheit und zwar bis zu der
     * spezifizierten Nachkkommastelle.
     * @param	a,&nbsp;b	die zu vergleichenden <code>double</code> Zahlen.
     * @param precision	die Anzahl der Nachkommastellen, die gleich sein m&uuml;ssen um von Gleichheit auszugehen.
     * @return	<code>true</code>, falls <i>a</i> und <i>b</i> bis auf die ersten <i>precision</i>
     * Nachkommastellen gleich sind, sonst <code>false</code>.
     */
    public static boolean equals(double a, double b, int precision) {
        double maxDifference = 1d / Math.pow(10, precision);
        return Math.abs(a - b) < maxDifference;
    }

    /**
     * Testet, ob die spezifizierte <code>double</code> Zahl eine nat&uuml;rliche Zahl darstellt,
     * indem sie zu einer <code>long</code> gerundet wird und auf Gleichheit gepr&uum;lft wird.
     * Signifikant siind auch hier die ersten 9 Nachkommastellen.
     * @param	a	die zu &uuml;berpr&uuml;fende Zahl.
     * @return	<code>true</code>, falls <i>a</i> als Nachkommastellen fr&uuml;hestens
     * an der zehnten Stelle eine nicht <i>Null</i> hat, sonst <code>false</code>.
     */
    public static boolean isNaturalNumber(double a) {
        long l = Math.round(a);
        return equals(a, l);
    }

    /**
     * Rundet die spezifizierte <code>double</code> Zahl mit der spezifizierten Pr&auml;zision.
     * @param		a					die zu rundende Zahl.
     * @param		precision	die gew&uuml;nschte Pr&auml;zision.
     * @return						die gerundete Zahl.
     */
    public static double roundDouble(double d, int precision) {
        return Math.round(d * Math.pow(10d, precision)) / Math.pow(10d, precision);
    }

    /**
     * Rundet die spezifizierte <code>float</code> Zahl mit der spezifizierten Pr&auml;zision.
     * @param		f					die zu rundende Zahl.
     * @param		precision	die gew&uuml;nschte Pr&auml;zision.
     * @return						die gerundete Zahl.
     */
    public static float roundFloat(float f, int precision) {
        if (precision > 8)
            // Ein float kann nur max. 8 signifikante Dezimalstellen halten
                return f;
        else
            return (float)(Math.round(f * Math.pow(10d, precision)) / Math.pow(10d, precision));
    }

    /**
     * Schneidet die Nachkommastellen der angegebenen <code>double</code>-Zahl ab und liefert das
     * Resultat als <code>long</code>.
     * @param		d					die abzurundende Zahl
     * @return						die abgerundete Zahl
     */
    public static long trunc(double d) {
        // Den Nachkommaanteil subtrahieren, dann normal runden.
        return Math.round(d - (d % 1));
    }

    /**
     * Schneidet die Nachkommastellen der angegebenen <code>float</code>-Zahl ab und liefert das
     * Resultat als <code>long</code>.
     * @param		f					die abzurundende Zahl
     * @return						die abgerundete Zahl
     */
    public static long trunc(float f) {
        // Den Nachkommaanteil subtrahieren, dann normal runden.
        return Math.round(f - (f % 1));
    }

    /**
     * Ermittelt die Anzahl signifikanter Dezimalstellen (=Pr&auml;zision) der angegebenen
     * <code>double</code>-Zahl (signifikante Dezimalstellen hier interpretiert als solche
     * Dezimalstellen, die ungleich Null sind).<p> Diese Funktion eignet sich nur f&uuml;r
     * Gleitkommazahlen, die nicht das Ergebnis einer Gleitkomma-Rechenoperation sind, sondern z. B. vom Benutzer als String
     * eingegeben wurden und mittels <code>Double.parseDouble((String) aValue);</code> ausgewertet wurden. Durch
     * Rundungsfehler k&ouml;nnen n&auml;lich bei Gleitkomma-Rechenoperationen aus relativ "glatten"
     * Dezimalzahlen Ergebnisse entstehen, deren Ziffern in hinteren Stellen ungleich Null sind,
     * obwohl sie Null sein m&uuml;ssten. F&uuml;r solche Zahlen w&uuml;rde eine
     * unverh&auml;ltnism&auml;ssig hohe Zahl von signifikanten Dezimalstellen ermittelt.
     * @param		d					<code>double</code>-Zahl, deren Ziffern zu &uuml;berpr&uuml;fen sind
     * @return						die Anzahl von Dezimalstellen,die ungleich Null sind
     */
    public static int getPrecision(double d) {
        String s = "" + d;
        // In der String-Darstellung der Gleitkommazahl werden die Ziffern != 0 gez�hlt.
        // Der Dezimalpunkt wird ignoriert, ebenso die Ziffern im Exponenten, d. h. die
        // Z�hlung wird abgebrochen, wenn ein "E" gefunden wird.
        char[] c = s.toCharArray();
        int nonZeroDigits = 0;
        for (int i = 0; i < s.length(); i++) {
            if ((c[i] > '0') & (c[i] <= '9')) nonZeroDigits++;
            if ((c[i] == 'E') | (c[i] == 'e')) break;
        }
        // f�r den Fall 0.0 eine 1 zur�ckgeben
        if (nonZeroDigits == 0) nonZeroDigits = 1;
        return nonZeroDigits;
    }

    /**
     * Ermittelt die Anzahl signifikanter Dezimalstellen (=Pr&auml;zision) der angegebenen
     * <code>float</code>-Zahl, (signifikante Dezimalstellen hier interpretiert als solche
     * Dezimalstellen, die ungleich Null sind).<p> Diese Funktion eignet sich nur f&uuml;r
     * Gleitkommazahlen, die nicht das Ergebnis einer Gleitkomma-Rechenoperation sind, sondern z. B. vom Benutzer als String
     * eingegeben wurden und mittels <code>Float.parseFloat((String) aValue);</code> ausgewertet wurden. Durch
     * Rundungsfehler k&ouml;nnen n&auml;lich bei Gleitkomma-Rechenoperationen aus relativ "glatten"
     * Dezimalzahlen Ergebnisse entstehen, deren Ziffern in hinteren Stellen ungleich Null sind,
     * obwohl sie Null sein m&uuml;ssten. F&uuml;r solche Zahlen w&uuml;rde eine
     * unverh&auml;ltnism&auml;ssig hohe Zahl von signifikanten Dezimalstellen ermittelt.
     * @param		d					<code>float</code>-Zahl, deren Ziffern zu &uuml;berpr&uuml;fen sind
     * @return						die Anzahl von Dezimalstellen,die ungleich Null sind
     */
    public static int getPrecision(float f) {
        String s = "" + f;
        // In der String-Darstellung der Gleitkommazahl werden die Ziffern != 0 gez�hlt.
        // Der Dezimalpunkt wird ignoriert, ebenso die Ziffern im Exponenten, d. h. die
        // Z�hlung wird abgebrochen, wenn ein "E" gefunden wird.
        char[] c = s.toCharArray();
        int nonZeroDigits = 0;
        for (int i = 0; i < s.length(); i++) {
            if ((c[i] > '0') & (c[i] <= '9')) nonZeroDigits++;
            if ((c[i] == 'E') | (c[i] == 'e')) break;
        }
        // f�r den Fall 0.0 eine 1 zur�ckgeben
        if (nonZeroDigits == 0) nonZeroDigits = 1;
        return nonZeroDigits;
    }

    /**
     * Ermittelt die Anzahl der Nachkommastellen der angegebenen <code>float</code>-Zahl.<p>
     * Diese Funktion eignet sich nur f&uuml;r Gleitkommazahlen, die nicht das Ergebnis einer
     * Gleitkomma-Rechenoperation sind, sondern z. B. vom Benutzer als String eingegeben wurden
     * und mittels <code>Float.parseFloat((String) aValue);</code> ausgewertet wurden.
     * Funktioniert z. Zt. nicht mit Zahlen >=10E7.
     * @param        f                    <code>float</code>-Zahl, deren Ziffern zu &uuml;berpr&uuml;fen sind
     * @return                        die Anzahl der Nachkommastellen
     */
    public static int getFractionDigits(float f) {
        // Diese Funktion arbeitet nur richtig, wenn f durch ""+f nicht
        // in E-Notation in einen String umgewandelt wird. Daher mu� f <10E7 sein.
        // Im Fall von 0.x wird in toString() bei kleinen Zahlen schon ab ca. .0001
        // auf die E-Notation zur�ckgegriffen. Daher 1 addieren.
        if (f < 0.001) f = f + 1;
        String s = "" + f;
        // In der String-Darstellung der Gleitkommazahl werden die Ziffern
        // hinter dem Dezimalpunkt gez�hlt.
        char[] c = s.toCharArray();
        int digits = 0;
        boolean pointFound = false;
        for (int i = 0; i < s.length(); i++) {
            if (c[i] == '.') pointFound = true;
            if ((c[i] >= '0') && (c[i] <= '9') && (pointFound)) digits++;
            if ((c[i] == 'E') | (c[i] == 'e')) break;
        }
        // Im Fall von x.0 nat�rlich nicht 1, sondern 0 zur�ckgeben.
        if ((f % 1) == 0) digits = 0;
        return digits;
    }

    /**
     * Ermittelt die Anzahl der Nachkommastellen der angegebenen <code>double</code>-Zahl.<p>
     * Diese Funktion eignet sich nur f&uuml;r Gleitkommazahlen, die nicht das Ergebnis einer
     * Gleitkomma-Rechenoperation sind, sondern z. B. vom Benutzer als String eingegeben wurden
     * und mittels <code>Double.parseDouble((String) aValue);</code> ausgewertet wurden.
     * Funktioniert z. Zt. nicht mit Zahlen >=10E7.
     * @param		f					<code>double</code>-Zahl, deren Ziffern zu &uuml;berpr&uuml;fen sind
     * @return						die Anzahl der Nachkommastellen
     */
    public static int getFractionDigits(double d) {
        // Diese Funktion arbeitet nur richtig, wenn d durch ""+d nicht
        // in E-Notation in einen String umgewandelt wird. Daher mu� d <10E7 sein.
        // Im Fall von 0.x wird in toString() bei kleinen Zahlen schon ab .0001
        // auf die E-Notation zur�ckgegriffen. Daher 1 addieren.
        if (trunc(d) == 0) d = d + 1;
        String s = "" + d;
        // In der String-Darstellung der Gleitkommazahl werden die Ziffern
        // hinter dem Dezimalpunkt gez�hlt.
        char[] c = s.toCharArray();
        int digits = 0;
        boolean pointFound = false;
        for (int i = 0; i < s.length(); i++) {
            if (c[i] == '.') pointFound = true;
            if ((c[i] >= '0') && (c[i] <= '9') && (pointFound)) digits++;
            if ((c[i] == 'E') | (c[i] == 'e')) break;
        }
        // Im Fall von x.0 nat�rlich nicht 1, sondern 0 zur�ckgeben.
        if ((d % 1) == 0) digits = 0;
        return digits;
    }
}