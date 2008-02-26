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
/*$Id: MindMapMode.java,v 1.3 2004/08/26 16:37:47 jbrenni Exp $*/

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes.mindmapmode;

import java.io.File;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.controller.Controller;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes.Mode;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes.ModeController;

public class MindMapMode implements Mode {

    private Controller c;
    private MindMapController modecontroller;
    private final String MODENAME = "MindMap";

    public MindMapMode() {
    }

    public void init (Controller c) {
	this.c = c;
	modecontroller = new MindMapController(this);
    }

    public String toString() {
	return MODENAME;
    }

    /**
     * Called whenever this mode is chosen in the program.
     * (updates Actions etc.)
     */
    public void activate() {
       c.getMapModuleManager().changeToMapOfMode(this);
    }

    public void restore(String restoreable) {
	try {
	    getModeController().load(new File(restoreable));
	} catch (Exception e) {
	    System.err.println("Error restoring file:"+e);
        e.printStackTrace();
	}
    }
    
    public Controller getController() {
	return c;
    }

    public ModeController getModeController() {
	return modecontroller;
    }

    public MindMapController getMindMapController() {
	return (MindMapController)getModeController();
    }

}
