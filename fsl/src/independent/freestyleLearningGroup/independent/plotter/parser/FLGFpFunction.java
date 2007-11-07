package freestyleLearningGroup.independent.plotter.parser;

import java.io.Serializable;

/**
 * Die <code>LGFpFunction</code> ist die Basisklasse, deren abgeleiteten
 * Klasse konkrete mathematische Funktionen repr&auml;sentieren.<p>
 * Namenskonvention f&uuml;r abgeleitete Klassen: <code>LGFp</code> + Name der Funktion.<p>
 * Immer wenn eine neue Funktion erstellt wird, muss diese in der Klasse
 * <code>LGFpFunctionFactory</code> eingetragen werden, damit sie von der
 * darin enthaltenen Methode <code>parseFunction</code> gekannt wird.<p>
 * @see <{freestyleLearningGroup.independent.plotter.parser.FLGFpFunctionFactory}>
 */
public abstract class FLGFpFunction implements Serializable {
    /** Der erste Operand dieser <code>LGFpFunction</code>, wenn vorhanden. */
    protected FLGFpFunction op1 = null;

    /** Der zweite Operand dieser <code>LGFpFunction</code>, wenn vorhanden. */
    protected FLGFpFunction op2 = null;

    /**
     * Liefert das Ergebnis dieser Funktion. Diese Methode muss in der abgeleiteten Klasse
     * &uuml;berschrieben werden.<p> Zu beachten ist dabei, dass immer vor der Berechnung die
     * <code>compute</code>-Methoden der Operanden dieser Funktion
     * aufgerufen werden muss (s. Beispiel). Einzige Ausnahmen sind die
     * Sonderf&auml;lle <code>LGFpConst</code> und <code>LGFpVar</code>, die einfach ihren Wert
     * zur&uuml;ckliefern.<p> <b>Beispiel</b> Kosinius-Funktion:  <p> <pre>
     *     public double compute(double[] varList) {
     *       return Math.cos(op1.compute(varList));
     *     }
     *  </pre> </p> <DL><DT><B>Note:</B><DD> Die <code>init</code>-Methode muss aufgerufen worden
     * sein, bevor die <code>compute</code>-Methode richtige Ergebnisse liefern kann.</DD></DL>
     * @param       varList ein Array mit den Variablen dieser Funktion.
     * @return                  das Ergebnis dieser <code>LGFpFunction</code>.
     * @see #init(LGFpFunctionStack operandenStack)
     */
    public abstract double compute(double[] varList);
    // AS public double     compute(double[] varList) { return 0.0; }

    /**
     * Liefert die mathematische Priorit&aumlt dieser <code>LGFpFunction</code>.
     * Diese Methode muss in der abgeleiteten Klasse implementiert werden.
     * Eine Konstante hat momentan die Priorit&aumlt 0, eine Negation die Priorit&aumlt 100.<p><p>
     * <b>Beispiel</b> Kosinius-Funktion:  <p> <pre>
     *      public int getPriority() { return 4; }
     *  </pre> </p>
     * @return  die mathematische Priorit&aumlt dieser <code>LGFpFunction</code>.
     */
    public abstract int getPriority();
    // AS public int            getPriority()                           { return 0; }

    /**
     * Eine <code>LGFpFunction</code> soll dann <code>true</code> liefern,
     * wenn sie nur einen Parameter zur Berechnung braucht. <code>LGFpFunction</code> liefert in
     * dieser Implementierung <code>false</code>. Diese Methode muss also in der abgeleiteten
     * Klasse &uuml;berschrieben werden, wenn nur ein Parameter ben&ouml;tigt wird.<p><p>
     * <b>Beispiel</b> Kosinius-Funktion:  <p> <pre>
     *          public boolean isFunction() { return true; }
     *  </pre> </p>
     * @return  <code>false</code>.
     */
    public boolean isFunction() { return false; }

    /**
     * Initialisiert die <code>LGFpFunction</code>. Der Operanden-Stack wird
     * &uuml;bergeben und die <code>init</code>-Methode "nimmt" sich die ben&ouml;tigte
     * Anzahl Operanden. Per default werden zwei Operanden gespeichert. Wird eine andere
     * Anzahl Operanden ben&ouml;tigt muss diese Methode in der abgeleiteten Klasse
     * entsprechend &uuml;berschrieben werden.<p><p> <b>Beispiel</b>:  <p> <pre>
     *  public void init(LGFpFunctionStack operandenStack) throws LGFpFunctionFormatException {
     *      if (operandenStack.size() < 1) throw new LGFpFunctionFormatException();
     *      else {
     *          op1 = operandenStack.pop();
     *          log("" + this + ":::::" + op1 );
     *          operandenStack.push(this);
     *      }
     *  }
     *  </pre> </p>
     * @param           operandenStack                          die vorhandenen Operanden.
     * @exception   LGFpFunctionFormatException wird gefeuert, wenn im Operanden-Stack
     * nicht gen&uuml;gend Operanden vorliegen.
     */
    public void init(FLGFpFunctionStack operandenStack) throws FLGFpFunctionFormatException {
        if (operandenStack.size() < 2) throw new FLGFpFunctionFormatException();
        else {
            op2 = operandenStack.pop();
            op1 = operandenStack.pop();
            log("" + this + ":::::" + op1 + ", " + op2);
            operandenStack.push(this);
        }
    }

    /**
     * Liefert eine textuelle Repr&auml;sentation dieser <code>LGFpFunction</code>
     * in der Form <code>Name:Priorit&auml;t</code>.
     * @return  die textuelle Repr&auml;sentation, bspw. <code>"LGFpDiv:2"</code>.
     */
    public String toString() { return getClass().getName() + ":" + getPriority(); }

    /**
     * Ersetzt das übliche <code>System.out.println<code>.
     * @see <{LGLog}>
     */
    private static void log(Object msgObj) { }
}
