/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.graphics;

import java.awt.*;


/**
 * Schnittstelle, die - Abh�ngig von der verwendeten Java-Version - den Grafikkontext
 * anpasst, so dass Funktionen wie z.B. Antialaisting aktiviert werden k�nnen.
 * */
public interface RenderInterface {
    public void modifyGraphicsHandle(Graphics g);
}