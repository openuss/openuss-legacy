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
/*$Id: EdgeAdapter.java,v 1.3 2004/09/01 04:46:44 jbrenni Exp $*/

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.main.FreeMindMain;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.main.Tools;

public abstract class EdgeAdapter extends LineAdapter implements MindMapEdge {

	public static final int WIDTH_PARENT = -1;
	public static final int WIDTH_THIN = 0;

    public EdgeAdapter(MindMapNode target,FreeMindMain frame) {
        this(target, frame, "standardedgecolor", "standardedgestyle");
    }

    /** For derived classes.*/
    protected  EdgeAdapter(MindMapNode target,FreeMindMain frame, String standardColorPropertyString, String standardStylePropertyString)  {
        super(target, frame, standardColorPropertyString, standardStylePropertyString);
        NORMAL_WIDTH = WIDTH_PARENT;
    }

    //
    // Attributes
    //

    public Color getColor() {
        if(color==null) {
            if (getTarget().isRoot()) {
                String stdcolor = getFrame().getProperty(standardColorPropertyString);
                if (stdcolor.length() == 7) {
                    return Tools.xmlToColor(stdcolor);
                }
                return Color.blue;
            }
            return getSource().getEdge().getColor();
        }
        return color;
    }

    public int getWidth() {
        if (width==WIDTH_PARENT) {
            if (getTarget().isRoot()) {
                return WIDTH_THIN; }
            return getSource().getEdge().getWidth(); }
        return width; }
    
    public int getWidthWithParentInformation() {
        return width;
    }

    public Stroke getStroke() {
        if (width==WIDTH_THIN)
            return null;
        if(stroke==null) {
            if (getTarget().isRoot()) {
                return null;
            }
            return getSource().getEdge().getStroke();
        }
        return stroke;
    }
		
    public void setWidth(int width) {
        this.width = width;
        stroke = ((width==WIDTH_PARENT) || (width==WIDTH_THIN)) ? null :
            new BasicStroke(getWidth(),BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER); }

    public String getStyle() {
        if(style==null) {
            if (getTarget().isRoot()) {
                return getFrame().getProperty(standardStylePropertyString);
            }
            return getSource().getEdge().getStyle();
        }
        return style;
    }

    ///////////
    // Private Methods
    /////////

    private MindMapNode getSource() {
        return target.getParentNode();
    }
}
