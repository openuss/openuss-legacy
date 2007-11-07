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
/*$Id: ArrowLinkView.java,v 1.1 2004/08/24 17:02:58 jbrenni Exp $*/

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.view.mindmapview;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes.MindMapArrowLink;
// end Convex Hull


/**
 * This class represents a ArrowLink around a node.
 */
public class ArrowLinkView {
    protected MindMapArrowLink arrowLinkModel;
    protected NodeView source;
    protected NodeView target;
    protected int iterativeLevel;
    protected CubicCurve2D arrowLinkCurve;
    static final Stroke DEF_STROKE = new BasicStroke(1);

    /* Note, that source and target are nodeviews and not nodemodels!.*/
    protected ArrowLinkView(MindMapArrowLink arrowLinkModel, NodeView source, NodeView target) {
        this.arrowLinkModel = arrowLinkModel;
        this.source = source;
        this.target = target;
    }
    
    public Rectangle getBounds() {
        if(arrowLinkCurve == null)
            return new Rectangle();
        return arrowLinkCurve.getBounds();
    }

    /** \param iterativeLevel describes the n-th nested arrowLink that is to be painted.*/
    public void paint(Graphics graphics) {
        Point p1, p2, p3, p4;
        boolean targetIsLeft;
        boolean sourceIsLeft;
        Graphics2D g = (Graphics2D) graphics.create();
        /* antialias */  setRendering(g);
        g.setColor(getColor());
        /* set stroke.*/
        g.setStroke(getStroke());
        // if one of the nodes is not present then draw a dashed line:
        if(source == null || target == null)
            g.setStroke(new BasicStroke(getWidth(), BasicStroke.CAP_ROUND,
                                        BasicStroke.JOIN_ROUND, 0, new float[]{0,3,0,3}, 0));

        // determine, whether destination exists:
        if(source == null) {
            p1 = new Point(target.getLinkPoint());
            p1.translate(100,0);
            sourceIsLeft = true;
        } else {
            p1 = source.getLinkPoint();
            sourceIsLeft = source.isLeft();
        }
        if(target == null) {
            p2 = new Point(p1);
            p2.translate(100,0);
            targetIsLeft = true;
        } else {
            p2 = target.getLinkPoint();
            targetIsLeft = target.isLeft();
        }
        // determine point 2 and 3:
        double delx, dely;
        delx = p2.x - p1.x; /* direction of p1 -> p3*/
        dely = p2.y - p1.y;
        double dellength = Math.sqrt(delx*delx + dely*dely);
        int deltax = (int) (getZoom() * dellength);
        p3 = new Point( p1 );
        if(arrowLinkModel.getStartInclination() != null) {
            p3.translate( arrowLinkModel.getStartInclination().x, arrowLinkModel.getStartInclination().y);
        } else {
            // automatic translation in outside direction:
            p3.translate(((sourceIsLeft)?-1:1) * deltax, 0);
        }            
        p4 = new Point( p2 );
        if(arrowLinkModel.getEndInclination() != null) {
            p4.translate( arrowLinkModel.getEndInclination().x, arrowLinkModel.getEndInclination().y);
        } else {
            // automatic translation in outside direction:
            p4.translate(((targetIsLeft)?-1:1) * deltax, 0);
        }
        //
        if(source == null) {
            p1 = p4;
            p3 = p2;
        }
        if(target == null) {
            p2 = p3;
            p4 = p1;
        }
        //
        arrowLinkCurve = new CubicCurve2D.Double();
        arrowLinkCurve.setCurve(p1,p3,p4,p2);
        g.draw(arrowLinkCurve);
        // arrow source:
        if(source != null && !arrowLinkModel.getStartArrow().equals("None")) {
            paintArrow(p1, p3, g);
        }
        // arrow target:
        if(target != null && !arrowLinkModel.getEndArrow().equals("None")) {
            paintArrow(p2, p4, g);
        }
    }

    /** @param p1 is the start point 
        @param p3 is the another point indicating the direction of the arrow.*/
    private void paintArrow(Point p1, Point p3, Graphics2D g) {
        double dx, dy, dxn, dyn;
        dx = p3.x - p1.x; /* direction of p1 -> p3*/
        dy = p3.y - p1.y;
        double length = Math.sqrt(dx*dx + dy*dy) / (getZoom() * 10/*=zoom factor for arrows*/);
        dxn = dx/length; /* normalized direction of p1 -> p3 */
        dyn = dy/length;
        // suggestion of daniel to have arrows that are not so wide open. fc, 7.12.2003.
        double width = .5f;
        Polygon p = new Polygon();
        p.addPoint((int) (p1.x),(int) (p1.y));
        p.addPoint((int) (p1.x + dxn + width * dyn),(int) (p1.y +dyn - width * dxn));
        p.addPoint((int) (p1.x + dxn - width * dyn),(int) (p1.y +dyn + width * dxn));
        p.addPoint((int) (p1.x),(int) (p1.y));
        g.fillPolygon(p);
    }


    /** MAXIMAL_RECTANGLE_SIZE_FOR_COLLISION_DETECTION describes itself. */
    private final int MAXIMAL_RECTANGLE_SIZE_FOR_COLLISION_DETECTION = 16; 


    /** Determines, whether or not a given point p is in an epsilon-neighbourhood for the cubic curve.*/
    public boolean detectCollision(Point p) {
        if(arrowLinkCurve == null)
            return false;
        Rectangle2D rec=getControlPoint(p);
        // flatten the curve and test for intersection (bug fix, fc, 16.1.2004).
        FlatteningPathIterator pi = new FlatteningPathIterator(arrowLinkCurve.getPathIterator(null),MAXIMAL_RECTANGLE_SIZE_FOR_COLLISION_DETECTION/4,10/*=maximal 2^10=1024 points.*/);
        double oldCoordinateX=0, oldCoordinateY=0;
        while (pi.isDone() == false) {
            double[] coordinates = new double[6];
            int type = pi.currentSegment(coordinates);
            switch(type) {
            case PathIterator.SEG_LINETO:
                if(rec.intersectsLine(oldCoordinateX, oldCoordinateY, coordinates[0], coordinates[1]))
                    return true;
                /* this case needs the same action as the next case, thus no "break" */
            case PathIterator.SEG_MOVETO:
                oldCoordinateX=coordinates[0];
                oldCoordinateY=coordinates[1];
                break;
            case PathIterator.SEG_QUADTO:
            case PathIterator.SEG_CUBICTO:
            case PathIterator.SEG_CLOSE:
            default:
                break;
            }
            pi.next();
        }
        return false;
    }


    protected Rectangle2D getControlPoint(Point2D p) {
        // Create a small square around the given point.
        int side = MAXIMAL_RECTANGLE_SIZE_FOR_COLLISION_DETECTION;
        return new Rectangle2D.Double(p.getX() - side / 2, p.getY() - side / 2,
                                      side, side);
    }

   public Color getColor() { 
        return getModel().getColor(); /*new Color(240,240,240)*/ /*selectedColor*/
    }

    public Stroke getStroke() {
        Stroke result = getModel().getStroke();
        if (result==null)
            return DEF_STROKE;
        return result;
    }

    public int getWidth() {
        return getModel().getWidth(); 
    }

    /**
     * Get the width in pixels rather than in width constant (like -1)
     */
    public int getRealWidth() {
       int width = getWidth();
       return (width < 1) ? 1 : width; }

    protected MapView getMap() {
       return (source == null)?target.getMap():source.getMap(); }

    /** fc: This getter is public, because the view gets the model by click on the curve.*/
    public MindMapArrowLink getModel() {
       return arrowLinkModel; }
    

    protected double getZoom() {
       return getMap().getZoom(); }

	
   protected void setRendering(Graphics2D g) {
      if (getMap().getController().getAntialiasEdges() || getMap().getController().getAntialiasAll()) {
         g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); }}


}
