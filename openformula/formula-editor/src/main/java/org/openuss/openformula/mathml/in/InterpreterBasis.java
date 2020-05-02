/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.mathml.in;

import org.openuss.openformula.mathml.in.java_cup.runtime.Symbol;


/**
 * Basisklasse für alle Bestandteile des Syntaxbaumes
 */
abstract class InterpreterBasis extends sym {
    Symbol firstSymbol;

    /**
     * Ein Symbol zuordnen, um Fehlermeldungen anzeigen zu können.
     * Dieses Symbol entspricht einem Token. Bei Fehlermeldungen wird auf
     * die Zeile und die Spalte dieses Symboles verwiesen.
     * @param firstSymbol
     */
    public InterpreterBasis(final Symbol firstSymbol) {
        this.firstSymbol = firstSymbol;
    }

    /**
     * Ein neues InterpreterBasis-Symbol erzeugen.
     */
    public InterpreterBasis() {
        this.firstSymbol = null;
    }

    /**
     * Die geparste mathml-Objektcode-Struktur in die interne Struktur des Formeleditors übertragen.
     * @param structure Struktur zum Einfügen in die Formeleditorstruktur
     */
    public abstract void createStructure(BuildStructure structure);

    /**
     * Line des Objektes ermitteln, n�tzlich zur Fehleranalyse
     * @return Zeilennummer
     */
    public final int getLine() {
        if (firstSymbol == null) {
            return -1;
        }

        return firstSymbol.left;
    }

    /**
     * Spalte des Objektes ermitteln, n�tzlich zur Fehleranalyse
     * @return Spaltennummer
     */
    public final int getColumn() {
        if (firstSymbol == null) {
            return -1;
        }

        return firstSymbol.right;
    }
}