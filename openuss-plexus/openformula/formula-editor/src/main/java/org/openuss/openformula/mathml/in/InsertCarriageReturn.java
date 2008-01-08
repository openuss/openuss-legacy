/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  OpenFormula
 * Copyright:    Copyright (c) OpenUSS
 * Company:      University of Muenster
 * @author  Jan Kirchhoff
 * @version 1.0
 */
package org.openuss.openformula.mathml.in;

import org.openuss.openformula.graphics.CarriageReturn;


/**
 * Created by IntelliJ IDEA.
 * User: Proke
 * Date: 22.01.2004
 * Time: 01:39:20
 * To change this template use Options | File Templates.
 */
public class InsertCarriageReturn extends Statement {
    public final void createStructure(final BuildStructure structure) {
        structure.add(new CarriageReturn(structure.getParentObject()));
    }
}