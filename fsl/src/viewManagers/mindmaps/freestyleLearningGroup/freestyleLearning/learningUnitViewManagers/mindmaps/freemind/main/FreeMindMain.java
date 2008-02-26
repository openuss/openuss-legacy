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
/*$Id: FreeMindMain.java,v 1.3 2004/08/28 05:33:03 jbrenni Exp $*/

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.main;

import java.awt.Container;
import java.io.File;
import java.net.URL;

import javax.swing.JLayeredPane;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.controller.Controller;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.view.mindmapview.MapView;

public interface FreeMindMain {
    
    public MapView getView();

    public void setView(MapView view);

    public Controller getController();

    public void setWaitingCursor(boolean waiting);

    public File getPatternsFile();

    public Container getContentPane();
    
    public String getInternationalization(String key);
    
    /**remove this!*/
    public void repaint();

    public URL getResource(String name);

    public String getProperty(String key);

    public void setProperty(String key, String value);

    /** Returns the path to the directory the freemind auto properties are in, or null, if not present.*/
    public String getFreemindDirectory();

    public JLayeredPane getLayeredPane();

    public Container getViewport();

    public void setTitle(String title);

     // to keep last win size (PN)
    public int getWinHeight();
    public int getWinWidth();
    // public int getWinState();

    /* To obtain a logging element, ask here. */
    public java.util.logging.Logger getLogger(String forClass);
    
    // --- additional FSL support ---
    
    public void hyperlinkEntered(String link);
    
    public void hyperlinkActivated(String link);
    
    public void hyperlinkExited(String link);
    
}
