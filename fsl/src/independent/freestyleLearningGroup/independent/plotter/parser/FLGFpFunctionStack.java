package freestyleLearningGroup.independent.plotter.parser;

import java.util.Vector;

/**
 * Der <code>LGFpFunctionStack</code> ist ein aufgebohrter Stack, der neben
 * den Standardstackfunktionen wie <code>push</code> und <code>pop</code>
 * auch die mathematische Priorit&auml;t der obersten Funktion auslesen kann.
 */
public class FLGFpFunctionStack {
    private Vector vector;

    /** Erzeugt einen leeren Funktionen-Stack. */
    public FLGFpFunctionStack() {
        vector = new Vector();
    }

    /**
     * Legt die spezifizierte Funktion oben auf den Stack.
     * @param   f   die hinzuzuf&uuml;gende Funktion.
     */
    public void push(FLGFpFunction f) { vector.addElement((Object)f); }

    /**
     * Liefert die Priorit&auml;t der an oberster Stelle im Stack stehenden Funktion oder Null, wenn der Stack leer ist.
     * @return  die Priorit&auml;t der obersten Funktion.
     */
    public int getPriority() {
        FLGFpFunction top = top();
        if (top != null) return top.getPriority();
        else
            return 0;
    }

    /**
     * Liefert die oberste Funktion des Funktionen-Stacks ohne sie vom
     * Stack zu entfernen oder liefert <code>null</code>, wenn der Stack leer ist.
     * @return  die oberste Funktion des Stacks oder <code>null</code>.
     */
    public FLGFpFunction top() {
        if (isEmpty()) return null;
        else
            return (FLGFpFunction)vector.elementAt(vector.size() - 1);
    }

    /**
     * Liefert die oberste Funktion des Funktionen-Stacks und entfernt sie vom
     * Stack oder liefert <code>null</code>, wenn der Stack leer ist.
     * @return  die oberste Funktion des Stacks oder <code>null</code>.
     */
    public FLGFpFunction pop() {
        if (isEmpty()) return null;
        else {
            int ind = vector.size() - 1;
            FLGFpFunction f = (FLGFpFunction)vector.elementAt(ind);
            vector.removeElementAt(ind);
            return f;
        }
    }

    /**
     * Liefert die Anzahl der Funktionen im Funktionen-Stack.
     * @return  die Anzahlder Funktionen im Stack.
     */
    public int size() { return vector.size(); }

    /**
     * Testet ob der Funktionen-Stack leer ist.
     * @return  <code>true</code> wenn der Stack keine Funktion enth&aumlt, sonst <code>false</code>.
     */
    public boolean isEmpty() { return (vector.size() == 0); }
}
