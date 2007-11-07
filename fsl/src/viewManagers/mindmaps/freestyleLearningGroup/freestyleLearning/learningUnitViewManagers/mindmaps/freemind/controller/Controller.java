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
/*$Id: Controller.java,v 1.6 2004/09/01 04:46:43 jbrenni Exp $*/

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.controller;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.main.FreeMindMain;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes.MindMap;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes.Mode;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes.ModesCreator;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.view.MapModule;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.view.mindmapview.MapView;

/**
 * Provides the methods to edit/change a Node. Forwards all messages to
 * MapModel(editing) or MapView(navigation).
 */

public class Controller {

    private MapModuleManager mapModuleManager;// new MapModuleManager();

    private Map modes; //hash of all possible modes

    private Mode mode; //The current mode

    private FreeMindMain frame;

    private JToolBar toolbar;

    private NodeMouseMotionListener nodeMouseMotionListener;

    private NodeKeyListener nodeKeyListener;

    private NodeDragListener nodeDragListener;

    private NodeDropListener nodeDropListener;

    private MapMouseMotionListener mapMouseMotionListener;

    private MapMouseWheelListener mapMouseWheelListener;

    private ModesCreator modescreator = new ModesCreator(this);

    private PageFormat pageFormat = null;

    private PrinterJob printerJob = null;

    private boolean antialiasEdges = false;

    private boolean antialiasAll = false;

    private Map fontMap = new HashMap();

    boolean isPrintingAllowed = true;

    boolean menubarVisible = true;

    boolean toolbarVisible = true;

    boolean leftToolbarVisible = true;

    Action moveToRoot;

    private boolean editable;

    /**
     * @return Returns the editable.
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * @param editable
     *            The editable to set.
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    //
    // Constructors
    //

    public Controller(FreeMindMain frame) {
        checkJavaVersion();

        this.frame = frame;
        modes = modescreator.getAllModes();
        mapModuleManager = new MapModuleManager(this);

        nodeMouseMotionListener = new NodeMouseMotionListener(this);
        nodeKeyListener = new NodeKeyListener(this);
        nodeDragListener = new NodeDragListener(this);
        nodeDropListener = new NodeDropListener(this);

        mapMouseMotionListener = new MapMouseMotionListener(this);
        mapMouseWheelListener = new MapMouseWheelListener(this);
    }

    //
    // get/set methods
    //

    public void checkJavaVersion() {
        if (System.getProperty("java.version").compareTo("1.4.0") < 0) {
            String message = "Warning: FreeMindAdapter requires version Java 1.4.0 or higher (your version: "
                    + System.getProperty("java.version") + ").";
            System.err.println(message);
            JOptionPane.showMessageDialog(null, message, "FreeMindAdapter",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public String getProperty(String property) {
        return frame.getProperty(property);
    }

    public void setProperty(String property, String value) {
        frame.setProperty(property, value);
    }

    public FreeMindMain getFrame() {
        return frame;
    }

    public URL getResource(String resource) {
        return getFrame().getResource(resource);
    }

    public String getResourceString(String resource) {
        try {
            return frame.getInternationalization(resource);
        } catch (Exception ex) {
            System.err.println("Warning - resource string not found:"
                    + resource);
            return resource;
        }
    }

    /** Returns the current model */
    public MindMap getModel() {
        if (getMapModule() != null) {
            return getMapModule().getModel();
        }
        return null;
    }

    public MapView getView() {
        if (getMapModule() != null) {
            return getMapModule().getView();
        } else {
            System.err
                    .println("[Freemind-Developer-Internal-Warning (do not write a bug report, please)]: Tried to get view without being able to get map module.");
        }
        return null;
    }

    Map getModes() {
        return modes;
    }

    public Mode getMode() {
        return mode;
    }

    public MapModuleManager getMapModuleManager() {
        return mapModuleManager;
    }

    private MapModule getMapModule() {
        return getMapModuleManager().getMapModule();
    }

    private JToolBar getToolBar() {
        return toolbar;
    }

    //

    public Font getFontThroughMap(Font font) {
        if (!fontMap.containsKey(font.toString())) {
            fontMap.put(font.toString(), font);
        }
        return (Font) fontMap.get(font.toString());
    }

    //
    public void setAntialiasEdges(boolean antialiasEdges) {
        this.antialiasEdges = antialiasEdges;
    }

    public void setAntialiasAll(boolean antialiasAll) {
        this.antialiasAll = antialiasAll;
    }

    public boolean getAntialiasEdges() {
        return antialiasEdges;
    }

    public boolean getAntialiasAll() {
        return antialiasAll;
    }

    public Font getDefaultFont() {
        // Maybe implement handling for cases when the font is not
        // available on this system.

        int fontSize = Integer.parseInt(getFrame().getProperty(
                "defaultfontsize"));
        int fontStyle = Integer.parseInt(getFrame().getProperty(
                "defaultfontstyle"));
        String fontFamily = getProperty("defaultfont");

        return getFontThroughMap(new Font(fontFamily, fontStyle, fontSize));
    }

    public boolean changeToMode(String mode) {
        if (getMode() != null && mode.equals(getMode().toString())) {
            return true;
        }

        //Check if the mode is available
        Mode newmode = (Mode) modes.get(mode);
        if (newmode == null) {
            errorMessage(getResourceString("mode_na") + ": " + mode);
            return false;
        }

        if (getMapModule() != null) {
            getMapModuleManager().setMapModule(null);
            getMapModuleManager().mapModuleChanged();
        }
        this.mode = newmode;

        setTitle();
        getMode().activate();

        return true;
    }

    public void setToolbarVisible(boolean visible) {
        toolbarVisible = visible;
        toolbar.setVisible(toolbarVisible);
    }

    public NodeKeyListener getNodeKeyListener() {
        return nodeKeyListener;
    }

    public NodeMouseMotionListener getNodeMouseMotionListener() {
        return nodeMouseMotionListener;
    }

    public MapMouseMotionListener getMapMouseMotionListener() {
        return mapMouseMotionListener;
    }

    public MapMouseWheelListener getMapMouseWheelListener() {
        return mapMouseWheelListener;
    }

    public NodeDragListener getNodeDragListener() {
        return nodeDragListener;
    }

    public NodeDropListener getNodeDropListener() {
        return nodeDropListener;
    }

    public void setFrame(FreeMindMain frame) {
        this.frame = frame;
    }

    /**
     * I don't understand how this works now (it's called twice etc.) but it
     * _works_ now. So let it alone or fix it to be understandable, if you have
     * the time ;-)
     */
    public void moveToRoot() {
        if (getMapModule() != null) {
            getView().moveToRoot();
        }
    }

    // (PN) %%%
    //    public void select( NodeView node) {
    //        getView().select(node,false);
    //        getView().setSiblingMaxLevel(node.getModel().getNodeLevel()); // this
    // level is default
    //    }
    //
    //    void selectBranch( NodeView node, boolean extend ) {
    //        getView().selectBranch(node,extend);
    //    }
    //        
    //    boolean isSelected( NodeView node ) {
    //        return getView().isSelected(node);
    //    }
    //
    //    void centerNode() {
    //        getView().centerNode(getView().getSelected());
    //    }
    //
    //    private MindMapNode getSelected() {
    //        return getView().getSelected().getModel();
    //    }

    public void informationMessage(Object message) {
        JOptionPane.showMessageDialog(getFrame().getContentPane(), message
                .toString(), "FreeMindAdapter", JOptionPane.INFORMATION_MESSAGE);
    }

    public void informationMessage(Object message, JComponent component) {
        JOptionPane.showMessageDialog(component, message.toString(),
                "FreeMindAdapter", JOptionPane.INFORMATION_MESSAGE);
    }

    public void errorMessage(Object message) {
        JOptionPane.showMessageDialog(getFrame().getContentPane(), message
                .toString(), "FreeMindAdapter", JOptionPane.ERROR_MESSAGE);
    }

    public void errorMessage(Object message, JComponent component) {
        JOptionPane.showMessageDialog(component, message.toString(),
                "FreeMindAdapter", JOptionPane.ERROR_MESSAGE);
    }

    public void obtainFocusForSelected() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (getView() != null) { // is null if the last map was closed.
                    getView().getSelected().requestFocus();
                } else {
                    // fc, 6.1.2004: bug fix, that open and quit are not working
                    // if no map is present.
                    // to avoid this, the menu bar gets the focus, and
                    // everything seems to be all right!!
                    // but I cannot avoid thinking of this change to be a bad
                    // hack ....
                    // getFrame().getFreeMindMenuBar().requestFocus();
                }
            }
        });
    }

    //
    // Map Navigation
    //

    //
    // other
    //

    public void setZoom(float zoom) {
        getView().setZoom(zoom);
    }

    //////////////
    // Private methods. Internal implementation
    ////////////

    //
    // Node editing
    //
    // (PN)
    //    private void getFocus() {
    //        getView().getSelected().requestFocus();
    //    }

    //
    // Multiple Views management
    //

    /**
     * Set the Frame title with mode and file if exist
     */
    public void setTitle() {
        
    }

    //
    // program/map control
    //

    public void quit() {
        while (getView() != null) {
            boolean closingNotCancelled = getMapModuleManager().close();
            if (!closingNotCancelled) {
                return;
            }
        }

        System.exit(0);
    }

    private boolean acquirePrinterJobAndPageFormat() {
        if (printerJob == null) {
            try {
                printerJob = PrinterJob.getPrinterJob();
            } catch (SecurityException ex) {
                isPrintingAllowed = false;
                return false;
            }
        }
        pageFormat = (pageFormat == null) ? printerJob.defaultPage()
                : pageFormat;
        return true;
    }

    //////////////
    // Inner Classes
    ////////////

    /**
     * Manages the list of MapModules. As this task is very complex, I exported
     * it from Controller to this class to keep Controller simple.
     */
    public class MapModuleManager {
        // Variable below: The instances of mode, ie. the Model/View pairs.
        // Normally, the
        // order should be the order of insertion, but such a Map is not
        // available.
        private Map mapModules = new HashMap();

        private MapModule mapModule; //reference to the current mapmodule,

        // could be done

        //with an index to mapModules, too.
        // private String current;

        private Controller c;

        MapModuleManager(Controller c) {
            this.c = c;
        }

        Map getMapModules() {
            return mapModules;
        }

        public MapModule getMapModule() {
            return mapModule;
        }

        public void newMapModule(MindMap map) {
            MapModule mapModule = new MapModule(map, new MapView(map, c),
                    getMode());
            setMapModule(mapModule);
            addToMapModules(mapModule.toString(), mapModule);
        }

        public void updateMapModuleName() {
            getMapModules().remove(getMapModule().toString());
            //removeFromViews() doesn't work because MapModuleChanged()
            //must not be called at this state
            getMapModule().rename();
            addToMapModules(getMapModule().toString(), getMapModule());
        }

        void nextMapModule() {
            List keys = new LinkedList(getMapModules().keySet());
            int index = keys.indexOf(getMapModule().toString());
            ListIterator i = keys.listIterator(index + 1);
            if (i.hasNext()) {
                changeToMapModule((String) i.next());
            } else if (keys.iterator().hasNext()) {
                // Change to the first in the list
                changeToMapModule((String) keys.iterator().next());
            }
        }

        void previousMapModule() {
            List keys = new LinkedList(getMapModules().keySet());
            int index = keys.indexOf(getMapModule().toString());
            ListIterator i = keys.listIterator(index);
            if (i.hasPrevious()) {
                changeToMapModule((String) i.previous());
            } else {
                Iterator last = keys.listIterator(keys.size() - 1);
                if (last.hasNext()) {
                    changeToMapModule((String) last.next());
                }
            }
        }

        //Change MapModules

        public boolean tryToChangeToMapModule(String mapModule) {
            if (mapModule != null && getMapModules().containsKey(mapModule)) {
                changeToMapModule(mapModule);
                return true;
            } else {
                return false;
            }
        }

        void changeToMapModule(String mapModule) {
            MapModule map = (MapModule) (getMapModules().get(mapModule));
            changeToMapModuleWithoutHistory(map);
        }

        void changeToMapModuleWithoutHistory(MapModule map) {
            if (map.getMode() != getMode()) {
                changeToMode(map.getMode().toString());
            }
            setMapModule(map);
            mapModuleChanged();
        }

        public void changeToMapOfMode(Mode mode) {
            for (Iterator i = getMapModules().keySet().iterator(); i.hasNext();) {
                String next = (String) i.next();
                if (((MapModule) getMapModules().get(next)).getMode() == mode) {
                    changeToMapModule(next);
                    return;
                }
            }
        }

        //private

        private void mapModuleChanged() {
            setTitle();
            c.obtainFocusForSelected();
        }

        private void setMapModule(MapModule mapModule) {
            this.mapModule = mapModule;
            frame.setView(mapModule != null ? mapModule.getView() : null);
        }

        private void addToMapModules(String key, MapModule value) {
            // begin bug fix, 20.12.2003, fc.
            // check, if already present:
            String extension = "";
            int count = 1;
            while (mapModules.containsKey(key + extension)) {
                extension = "<" + (++count) + ">";
            }
            // rename map:
            value.setName(key + extension);
            mapModules.put(key + extension, value);
            // end bug fix, 20.12.2003, fc.
            moveToRoot(); // Only for the new modules move to root
            mapModuleChanged();
        }

        private void changeToAnotherMap(String toBeClosed) {
            List keys = new LinkedList(getMapModules().keySet());
            for (ListIterator i = keys.listIterator(); i.hasNext();) {
                String key = (String) i.next();
                if (!key.equals(toBeClosed)) {
                    changeToMapModule(key);
                    return;
                }
            }
        }

        /**
         * Close the currently active map, return false if closing cancelled.
         */
        private boolean close() {
            // (DP) The mode controller does not close the map
            boolean closingNotCancelled = getMode().getModeController().close(
                    false);
            if (!closingNotCancelled) {
                return false;
            }

            String toBeClosed = getMapModule().toString();
            mapModules.remove(toBeClosed);
            if (mapModules.isEmpty()) {
                setMapModule(null);
                frame.setView(null);
            } else {
                changeToMapModule((String) mapModules.keySet().iterator()
                        .next());
            }
            mapModuleChanged();
            return true;
        }

        // }}

    }

    //
    // Node navigation
    //
    private class MoveToRootAction extends AbstractAction {
        MoveToRootAction(Controller controller) {
            super(controller.getResourceString("move_to_root"));
        }

        public void actionPerformed(ActionEvent event) {
            moveToRoot();
        }
    }

    // --- Additional FSL support ---

    public Action getMoveToRootAction() {
        return new MoveToRootAction(this);
    }

    public void zoom(float zoom) {
        getView().setZoom(zoom);
    }

    public void print() {
        if (!acquirePrinterJobAndPageFormat()) {
            return;
        }

        printerJob.setPrintable(getView(), pageFormat);

        if (printerJob.printDialog()) {
            try {
                printerJob.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}//Class Controller

