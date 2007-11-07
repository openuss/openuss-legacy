package freestyleLearningGroup.independent.plotter.parser;

/**
 * Diese <code>Exception</code> wird geworfen, wenn es beim Parsen einer Funktion zu formalen
 * Fehlern kommt, bspw. zu wenige Operanden angegeben werden.
 */
public class FLGFpFunctionFormatException extends Exception {
    /** Erzeugt eine <code>LGFpFunctionFormatException</code> mit der Meldung "Formel ist fehlerhaft!". */
    public FLGFpFunctionFormatException() {
        super("Formel ist fehlerhaft!");
    }
}
