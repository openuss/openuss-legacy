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
/*$Id: ModesCreator.java,v 1.2 2004/08/28 05:33:03 jbrenni Exp $*/

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.controller.Controller;


/**
 * This class creates all the modes that are available. To add your own mode,
 * simply import it, and create it in getAllModes() (just do the same whats done
 * with MindMapMode). Thats all!
 */
public class ModesCreator {
    private Controller c;
    private Map modes = new TreeMap();

    public ModesCreator(Controller c) {
	this.c = c;
    }

    public Map getAllModes() {
	Mode mode;
	//Copy these two lines for every new Mode,
	//and replace MindMapMode(c) with YourNewMode(c)

	String modestring = c.getFrame().getProperty("modes");
	
	StringTokenizer tokens = new StringTokenizer(modestring,",");

	while (tokens.hasMoreTokens()) {
	    String modename = tokens.nextToken();
	    try {
		mode = (Mode)Class.forName(modename).newInstance();
		mode.init(c);
		modes.put(mode.toString(), mode);
	    } catch (Exception ex) {
		System.err.println("Mode "+modename+" could not be loaded.");
		ex.printStackTrace();
	    }
	}

	

	//	mode = new MindMapMode(c);
	//	modes.put(mode.toString(), mode);
	/*	try {
	mode = (Mode)Class.forName("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes.browsemode.BrowseMode").newInstance();
	mode.init(c);
	modes.put(mode.toString(), mode);

	} catch (Exception ex) {
	    System.err.println("Tjuschi");
	ex.printStackTrace();}

	//	mode = new FileMode(c);
	//	modes.put(mode.toString(), mode);

	//	mode = new SchemeMode(c);
	//	modes.put(mode.toString(), mode);
	*/


	return modes;
    }
}
