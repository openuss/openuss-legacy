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
 * Created by IntelliJ IDEA.
 * User: Jan Kirchhoff
 * Date: 25.11.2003
 * Time: 15:11:17
 * To change this template use Options | File Templates.
 */
public final class NetscapeRendering implements RenderInterface {
    private final Basic basic;

    public NetscapeRendering(final Basic basic) {
        this.basic = basic;
    }

    public final void modifyGraphicsHandle(final Graphics g) {
        final Font font = new Font("Serif", Font.PLAIN, basic.getMySize());

        g.setFont(font);

        //System.out.println("Netscape Handling");
        try {
            if (g instanceof Graphics2D) {
                final RenderingHints qualityHints = new RenderingHints(
                                                            RenderingHints.KEY_ANTIALIASING, 
                                                            RenderingHints.VALUE_ANTIALIAS_ON);
                qualityHints.put(RenderingHints.KEY_RENDERING, 
                                 RenderingHints.VALUE_RENDER_QUALITY);
                qualityHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, 
                                 RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                qualityHints.put(RenderingHints.KEY_INTERPOLATION, 
                                 RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                ((Graphics2D) g).setRenderingHints(qualityHints);
            }
        } catch (Throwable e) { // Dann rendert er halt ohne Antialaisting...

            //System.out.println("RenderingHints: War nix...");
        }
    }
}