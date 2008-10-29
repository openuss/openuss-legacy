/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.design.language;

import java.util.Hashtable;


/**
 * Created by IntelliJ IDEA.
 * User: Proke
 * Date: 22.01.2004
 * Time: 04:14:17
 * To change this template use Options | File Templates.
 */
public class Translation implements LanguageModule {
    public final static int GERMAN = 0;
    public final static int ENGLISH = 1;
    public static int currentLanguage = 0;

    public final void initLanguage(final Hashtable table) {
        // Menü
        switch (currentLanguage) {
        case GERMAN:

            // Edit Area: Menü
            table.put("formula", "Formel");
            table.put("deleteChanges", "Änderung verwerfen");
            table.put("exit", "Beenden");
            table.put("edit", "Bearbeiten");
            table.put("undo", "Rückgangig");
            table.put("redo", "Wiederholen");
            table.put("cut", "Ausschneiden");
            table.put("copy", "Kopieren");
            table.put("insert", "Einfügen");
            table.put("delete", "Löschen");
            table.put("selectAll", "Alles markieren");


            // Edit Area: Buttons
            table.put("fraction", "Bruch");
            table.put("sum", "Summe");
            table.put("product", "Produkt");
            table.put("integral", "Integral");
            table.put("root", "Wurzel");
            table.put("matrix", "Matrix");


            // View Area: Buttons
            table.put("startEdit", "  Formel bearbeiten ...  ");
            table.put("copyFormula", "  Formel kopieren  ");

            break;

        case ENGLISH:

            // Edit Area: Menü
            table.put("formula", "Formula");
            table.put("deleteChanges", "Ignore changes");
            table.put("exit", "Exit");
            table.put("edit", "Edit");
            table.put("undo", "Undo");
            table.put("redo", "Redo");
            table.put("cut", "Cut");
            table.put("copy", "Copy");
            table.put("insert", "Paste");
            table.put("delete", "Delete");
            table.put("selectAll", "Select All");


            // Buttons
            table.put("fraction", "Fraction");
            table.put("sum", "Sum");
            table.put("product", "Product");
            table.put("integral", "Integral");
            table.put("root", "Root");
            table.put("matrix", "Matrix");


            // View Area: Buttons
            table.put("startEdit", "  Edit Formula ...  ");
            table.put("copyFormula", "  Copy Formula  ");

            break;
        }
    }

    public static int getLanguage() {
        return currentLanguage;
    }

    public static void setLanguage(int language) {
        currentLanguage = language;
    }

    public static void setLanguage(String str) {
        if (str.equalsIgnoreCase("german")) {
            currentLanguage = GERMAN;
        } else if (str.equalsIgnoreCase("english")) {
            currentLanguage = ENGLISH;
        } else {
            System.err.println("Unknown language. Use german.");
        }
    }
}