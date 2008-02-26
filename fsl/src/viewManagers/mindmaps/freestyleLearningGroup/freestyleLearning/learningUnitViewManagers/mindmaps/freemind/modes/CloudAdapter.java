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
/*$Id: CloudAdapter.java,v 1.1 2004/08/24 17:02:58 jbrenni Exp $*/

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes;

import java.awt.Color;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.main.FreeMindMain;

public abstract class CloudAdapter extends LineAdapter implements MindMapCloud {

    //
    // Constructors
    //
    public CloudAdapter(MindMapNode target,FreeMindMain frame) {
        this(target, frame, "standardcloudcolor", "standardcloudstyle");
    }

    /** For derived classes.*/
    protected  CloudAdapter(MindMapNode target,FreeMindMain frame, String standardColorPropertyString, String standardStylePropertyString)  {
        super(target, frame, standardColorPropertyString, standardStylePropertyString);
        NORMAL_WIDTH = 3;
    }

    public Color getExteriorColor() {
        return getColor().darker();
    }

}
