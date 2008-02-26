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
/*$Id: ForkNodeView.java,v 1.1 2004/08/24 17:02:58 jbrenni Exp $*/

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.view.mindmapview;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes.MindMapNode;

/**
 * This class represents a single Fork-Style Node of a MindMap
 * (in analogy to TreeCellRenderer).
 */
public class ForkNodeView extends NodeView {


    //
    // Constructors
    //
    
    public ForkNodeView(MindMapNode model, MapView map) {
	super(model,map);
    }

    public Dimension getPreferredSize() {
	return new Dimension(super.getPreferredSize().width,
                             super.getPreferredSize().height + 3 + getEdge().getRealWidth());
    }	
  
    /**
     * Paints the node
     */
    public void paint(Graphics graphics) {
	Graphics2D g = (Graphics2D)graphics;
	Dimension size = getSize();
	//Dimension size = getPreferredSize();

	if (this.getModel()==null) return;

        paintSelected(g, size);
        paintDragOver(g, size);

        int edgeWidth = getEdge().getRealWidth();

	//Draw a standard node
        setRendering(g);
	g.setColor(getEdge().getColor());
	g.setStroke(getEdge().getStroke());
	g.drawLine(0,          size.height-edgeWidth/2-1,
                   size.width, size.height-edgeWidth/2-1);
   
	super.paint(g);
    }
}









