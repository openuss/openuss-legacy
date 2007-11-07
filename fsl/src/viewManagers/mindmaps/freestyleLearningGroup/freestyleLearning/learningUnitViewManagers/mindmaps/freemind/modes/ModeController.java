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
/*$Id: ModeController.java,v 1.5 2004/09/01 04:46:44 jbrenni Exp $*/

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JPopupMenu;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.main.XMLParseException;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.view.mindmapview.NodeView;

public interface ModeController {

    public void load(File file) throws FileNotFoundException, IOException, XMLParseException;
    public boolean save(File file);
    public void addNew(NodeView target, int newNodeMode, KeyEvent e);
    public void newMap(String title);
    public boolean save();
    public boolean saveAs();
    public void open();
    //    public void edit(NodeView node, NodeView toBeSelected);
    public boolean close(boolean force);
    public void doubleClick(MouseEvent e);
    public void plainClick(MouseEvent e);
    public void toggleFolded();

    public boolean isBlocked();
    public void edit(KeyEvent e, boolean addNew, boolean editLong);
    public void mouseWheelMoved(MouseWheelEvent e);
    /** This extends the currently selected nodes. 
        @return true, if the method changed the selection.*/
    public boolean extendSelection(MouseEvent e);

    public void showPopupMenu(MouseEvent e);
    /** This returns a context menu for an object placed in the background pane.*/
    public JPopupMenu getPopupForModel(java.lang.Object obj);

    public void nodeChanged(MindMapNode n);
    public void anotherNodeSelected(MindMapNode n);
    
    // --- Additional FSL support ---
    
    public boolean isEditable();
    public void setEditable(boolean editable);
    public void setLinkIdForSelectedNode(String link);
    public String getLinkIdForSelectedNode();
    public void nodeEntered(MouseEvent e);
    public void nodeExited(MouseEvent e);
    
}
