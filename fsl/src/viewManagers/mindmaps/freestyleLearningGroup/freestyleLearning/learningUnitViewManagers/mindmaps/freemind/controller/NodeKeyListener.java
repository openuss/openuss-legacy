/*FreeMindAdapter - A Program for creating and viewing Mindmaps
 *Copyright (C) 2000-2001  Joerg Mueller <joergmueller@bigfoot.com>
 *See COPYING for Details
 *
*Modified by Joerg Brenninkmeyer <joerg@brenninkmeyer.name> (2004)
 *for integration into Freestyle Learning <www.freestyle-learning.de>
 *which is also distributed under GNU GPL terms. The code is available
 *at <http://sourceforge.net/projects/openuss/>.
 * 
  *This program is free software; you can redistribute it and/or
 *modify it under the terms of the GNU General Public License
 *as published by the Free Software Foundation; either version 2
 *of the License, or (at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program; if not, write to the Free Software
 *Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
/*$Id: NodeKeyListener.java,v 1.4 2004/09/01 04:46:43 jbrenni Exp $*/

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.main.FreeMindAdapter;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.main.Tools;

/**
 * The KeyListener which belongs to the node and cares for Events like C-D
 * (Delete Node). It forwards the requests to NodeController.
 */
public class NodeKeyListener implements KeyListener {

    private Controller c;

    private String up;

    private String down;

    private String left;

    private String right;

    private boolean disabledKeyType = true;

    private boolean keyTypeAddsNew = false;

    public NodeKeyListener(Controller controller) {
        c = controller;
        up = c.getFrame().getProperty("keystroke_move_up");
        down = c.getFrame().getProperty("keystroke_move_down");
        left = c.getFrame().getProperty("keystroke_move_left");
        right = c.getFrame().getProperty("keystroke_move_right");

        // like in excel - write a letter means edit (PN)
        // on the other hand it doesn't allow key navigation (sdfe)
        // FSL: In order to allow navigation with letter keys,
        // key type has been disabled in properties.
        disabledKeyType = Tools.safeEquals(c.getFrame().getProperty(
                "disable_key_type"), "true");
        keyTypeAddsNew = Tools.safeEquals(c.getFrame().getProperty(
                "key_type_adds_new"), "true");
    }

    //
    // Interface KeyListener
    //

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {

        if (e.isAltDown() || e.isControlDown()) {
            return;
        }

        switch (e.getKeyCode()) {

        case KeyEvent.VK_UP:
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_LEFT:
        case KeyEvent.VK_RIGHT:
            c.getView().move(e);
            return;
        }

        // printable key creates new node in edit mode (PN)
        if (c.isEditable()) {
            if (!disabledKeyType) {
                if (!e.isActionKey()
                        && e.getKeyChar() != KeyEvent.CHAR_UNDEFINED) {
                    c.getMode().getModeController().edit(e, keyTypeAddsNew,
                            true);
                    return; // do not process the (sdfe) navigation
                }
            } else { // FSL specific letter key assignments
                if (e.getKeyChar() != KeyEvent.CHAR_UNDEFINED) {
                    switch (e.getKeyChar()) {
                    case 'e':
                        ((FreeMindAdapter) c.getFrame()).editNode();
                        break;
                    case 'n':
                        ((FreeMindAdapter) c.getFrame()).newChild();
                        break;
                    case 's':
                        ((FreeMindAdapter) c.getFrame()).newSibling();
                        break;
                    case 'S':
                        ((FreeMindAdapter) c.getFrame()).newPreviousSibling();
                        break;
                    case 'J':
                        ((FreeMindAdapter) c.getFrame()).nodeUp();
                        break;
                    case 'j':
                        ((FreeMindAdapter) c.getFrame()).nodeDown();
                        break;
                    case 'x':
                        ((FreeMindAdapter) c.getFrame()).cut();
                        break;
                    case 'c':
                        ((FreeMindAdapter) c.getFrame()).copy();
                        break;
                    case 'v':
                        ((FreeMindAdapter) c.getFrame()).paste();
                        break;
                    case 'b':
                        ((FreeMindAdapter) c.getFrame()).bold();
                        break;
                    case 'i':
                        ((FreeMindAdapter) c.getFrame()).italic();
                        break;
                    case 'L':
                        ((FreeMindAdapter) c.getFrame()).increaseFontSize();
                        break;
                    case 'l':
                        ((FreeMindAdapter) c.getFrame()).decreaseFontSize();
                        break;
                    case 'o':
                        ((FreeMindAdapter) c.getFrame()).cloud();
                        break;
                    case 'T':
                        ((FreeMindAdapter) c.getFrame()).increaseEdgeWidth();
                        break;
                    case 't':
                        ((FreeMindAdapter) c.getFrame()).decreaseEdgeWidth();
                        break;
                    }
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }
}

