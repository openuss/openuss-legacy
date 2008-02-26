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
/*$Id: RootNodeView.java,v 1.1 2004/08/24 17:02:58 jbrenni Exp $*/

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.view.mindmapview;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.LinkedList;
import java.util.ListIterator;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes.MindMapNode;

/**
 * This is a RootNode with different placing of children
 * and different painting than a normal NodeView
 */
public class RootNodeView extends NodeView {

    //
    // Constructors
    //
    
    public RootNodeView(MindMapNode model, MapView map) {
	super(model,map);
    }
    
    public NodeView getParentView() {
	return null;
    }
    

    /**
     * Returns the Point where the OutEdge
     * should leave the Node.
     */
    Point getOutPoint() {
	Dimension size = getSize();
	return new Point(getLocation().x + size.width, getLocation().y + size.height / 2);
    }

    /**
     * Returns the Point where the InEdge
     * should arrive the Node.
     */
    Point getInPoint() {
	Dimension size = getSize();
	return new Point(getLocation().x, getLocation().y + size.height / 2);
    }

    EdgeView getEdge() {
	return null;
    }

    void setEdge(EdgeView edge) {
    }

    LinkedList getLeft() {
	LinkedList all = getChildrenViews();
	LinkedList left = new LinkedList();
	for (ListIterator e = all.listIterator();e.hasNext();) {
	    NodeView node = (NodeView)e.next();
	    if (node == null) continue;
	    if (node.isLeft()) left.add(node);
	}
	return left;
    }

    LinkedList getRight() {
	LinkedList all = getChildrenViews();
	LinkedList right = new LinkedList();
	for (ListIterator e = all.listIterator();e.hasNext();) {
	    NodeView node = (NodeView)e.next();
	    if (node == null) continue;
	    if (!node.isLeft()) right.add(node);
	}
	return right;
    }

    void insert(MindMapNode newNode) {
        NodeView newView = newNodeView(newNode,getMap());
        newView.update();
        // decide left or right only if not actually set:
        if(newNode.isLeft()==null)
            newView.setLeft( getLeft().size() <= getRight().size() );
        ListIterator it = newNode.childrenFolded();
        if (it != null) {
            for (;it.hasNext();) {
                MindMapNode child = (MindMapNode)it.next();
                newView.insert(child);
            }
        }
    }	

    public boolean dropAsSibling (double xCoord) {
        return false;
    }

    /** @return true if should be on the left, false otherwise.*/
    public boolean dropPosition (double xCoord) {
       return xCoord < getSize().width*1/2 ; 
    }

    public void setDraggedOver(Point p) {
        setDraggedOver ((dropPosition(p.getX())) ? NodeView.DRAGGED_OVER_SON_LEFT : NodeView.DRAGGED_OVER_SON); 
    }

    //
    // Navigation
    //

    public NodeView getNextSibling() {
	return this;
    }

    public NodeView getPreviousSibling() {
	return this;
    }


    //
    // Layout
    //

    public Dimension getPreferredSize() {
	int width = (int)(super.getPreferredSize().width*1.1);
	int height = (int)(super.getPreferredSize().height*2);
	return new Dimension(width,height);
    }	

    public void paintSelected(Graphics2D graphics, Dimension size) {
       if( this.isSelected() ) {
          graphics.setColor(selectedColor);
          graphics.fillOval(1,1,size.width-1,size.height-1);
       }
    }


    /**
     * Paints the node
     */
    public void paint(Graphics graphics) {
	Graphics2D g = (Graphics2D)graphics;
	Dimension size = getSize();
	if (this.getModel()==null) return;

        paintSelected(g, size);
        paintDragOver(g, size);

	//Draw a root node
	setHorizontalAlignment(CENTER);

	g.setColor(Color.gray);
	g.setStroke(new BasicStroke(1.0f));
        setRendering(g);
	g.drawOval(1,1,size.width-2,size.height-2);
        if (!getMap().getController().getAntialiasAll()) {
           g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF); }

	super.paint(g);
    }

   public void paintDragOver(Graphics2D graphics, Dimension size) {
        if (getDraggedOver() == DRAGGED_OVER_SON) {
              graphics.setPaint( new GradientPaint(size.width/4,0,map.getBackground(), size.width*3/4, 0, dragColor));
              graphics.fillRect(size.width/4, 0, size.width-1, size.height-1); 
        } else if (getDraggedOver() == DRAGGED_OVER_SON_LEFT) {
              graphics.setPaint( new GradientPaint(size.width*3/4,0,map.getBackground(), size.width/4, 0, dragColor));
              graphics.fillRect(0, 0, size.width*3/4, size.height-1);
        }
    }


   protected void setRendering(Graphics2D g) {
      if (getMap().getController().getAntialiasEdges() || getMap().getController().getAntialiasAll()) {
         g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); }}


}



//     /**
//      * Determines the logical Position of all of its direct children.
//      */
//     public void layoutChildren() {
// 	if (getChildrenViews().size() == 0) return;//nothing to do
// 	layoutRoot();
    //    } 
  
