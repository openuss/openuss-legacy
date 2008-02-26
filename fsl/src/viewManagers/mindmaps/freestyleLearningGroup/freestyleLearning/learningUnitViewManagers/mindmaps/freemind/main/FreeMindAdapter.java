/*
 * FreeMindAdapter - A Program for creating and viewing Mindmaps Copyright (C)
 * 2000-2001 Joerg Mueller <joergmueller@bigfoot.com> See COPYING for Details
 * 
 * Modified by Joerg Brenninkmeyer <joerg@brenninkmeyer.name> (2004)
 * for integration into Freestyle Learning <www.freestyle-learning.de>
 * which is also distributed under GNU GPL terms. The code is available
 * at <http://sourceforge.net/projects/openuss/>.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */
/* $Id: FreeMindAdapter.java,v 1.6 2004/09/01 04:46:45 jbrenni Exp $ */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElement;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.FLGMindmapsManager;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.controller.Controller;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes.MindIcon;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes.mindmapmode.MindMapController;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes.mindmapmode.MindMapMapModel;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.view.mindmapview.MapView;
import freestyleLearningGroup.independent.util.FLGInternationalization;

// The FreeMindAdapter is still a JInternalFrame; this way, the architecture of
// FreeMind can be used without dramatic modifications.
public class FreeMindAdapter extends JInternalFrame implements FreeMindMain {

    // The following path is looked for starting at the folder of this package.
    // Resources currently include only the sub-folder "images".
    // Note: relative Path didn't work with JAR, so just use this package's path
    // for the ressources (and leave empty string).
    private static final String RESOURCES_PATH = "";

    // The following file is looked for at the folder of this package.
    private static final String FREEMIND_PROPERTIES_FILE = "freemind.properties";

    private static final String INTERNATIONALIZATION_LOCATION = "freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.internationalization";

    // The properties to be found at FREEMIND_PROPERTIES_FILE
    private Properties props;

    private JScrollPane scrollPane;

    private File patternsFile;

    Controller c; //the one and only controller

    private FLGInternationalization internationalization;

    private FLGMindmapsManager flgMindmapsManager;

    private Vector hyperlinkListeners;
    
    public FreeMindAdapter(FLGMindmapsManager flgMindmapsManager, JScrollPane scrollPane) {
        super("FreeMindAdapter", true, true, true, true);

        // Set FSL Mindmaps Manager
        this.flgMindmapsManager = flgMindmapsManager;
        
        // Set Scrollpane for content element panel
        this.scrollPane = scrollPane;

        // Get FSL Internationalization
        internationalization = new FLGInternationalization(
                INTERNATIONALIZATION_LOCATION, getClass().getClassLoader());

        // Initialize Vector for Hyperlink Listeners
        hyperlinkListeners = new Vector();

        // Get Properties from JAR-file.
        URL mindmapsPropertiesURL = this.getClass().getResource(
                FREEMIND_PROPERTIES_FILE);
        props = new Properties();
        try {
            InputStream propertiesInputStream = mindmapsPropertiesURL.openStream();
            try {
                props.load(propertiesInputStream);
            } 
            catch (Exception exception) {
                System.err.println("Error while trying to load Freemind-Properties from JAR-file.");
            } 
            finally {
                propertiesInputStream.close();
            }
        } 
        catch (IOException exception) {
            System.err.println("IOException while trying to load Freemind-Properties from JAR-file.");
            System.err.println(exception);
        }

        //Layout everything
        getContentPane().setLayout(new BorderLayout());

        c = new Controller(this);

        if (Tools.safeEquals(getProperty("antialiasEdges"), "true")) {
            c.setAntialiasEdges(true);
        }
        if (Tools.safeEquals(getProperty("antialiasAll"), "true")) {
            c.setAntialiasAll(true);
        }

        //Create the scroll pane

        // getContentPane().add(scrollPane, BorderLayout.CENTER);

        addInternalFrameListener(new InternalFrameAdapter() {
            public void windowClosing(InternalFrameEvent e) {
                c.quit();
            }

            public void windowActivated(InternalFrameEvent e) {
                //This doesn't work the first time, it's called too early to
                // get Focus
                if ((getView() != null) && (getView().getSelected() != null)) {
                    getView().getSelected().requestFocus();
                }
            }
        });

        // SwingUtilities.updateComponentTreeUI(this); // Propagate LookAndFeel
        // to
        // JComponents

        c.changeToMode(getProperty("initial_mode"));

        //		 Hide title bar of JInternalFrame
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);

    }//Constructor

    public File getPatternsFile() {
        return patternsFile;
    }

    public Container getViewport() {
        return scrollPane.getViewport();
    }

    // maintain this methods to keep the last state/size of the window (PN)
    public int getWinHeight() {
        return getRootPane().getHeight();
    }

    public int getWinWidth() {
        return getRootPane().getWidth();
    }

    public URL getResource(String name) {
        return this.getClass().getResource(RESOURCES_PATH + name);
    }

    public String getProperty(String key) {
        return props.getProperty(key);
    }

    public void setProperty(String key, String value) {
        props.setProperty(key, value);
    }

    public String getFreemindDirectory() {
        return System.getProperty("user.home")
                + System.getProperty("file.separator")
                + getProperty("properties_folder");
    }

    public MapView getView() {
        return c.getView();
    }
    
    public Controller getController() {
        return c;
    }

    public void setView(MapView view) {
        scrollPane.setViewportView(view);
        if (view != null)
            view.setAutoscrolls(true);//for some reason this doesn't work
    }

    private String transpose(String input, char findChar, String replaceString) {
        String res = new String();
        for (int i = 0; i < input.length(); ++i) {
            char d = input.charAt(i);
            if (d == findChar)
                res += replaceString;
            else
                res += d;
        }
        return res;
    }

    public void setWaitingCursor(boolean waiting) {
        if (waiting) {
            getRootPane().getGlassPane().setCursor(
                    Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            getRootPane().getGlassPane().setVisible(true);
        } else {
            getRootPane().getGlassPane().setCursor(
                    Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            getRootPane().getGlassPane().setVisible(false);
        }
    }

    public String getInternationalization(String key) {
        return internationalization.getString(key);
    }

    public java.util.logging.Logger getLogger(String forClass) {
        return java.util.logging.Logger.getLogger(forClass);
    }

    // --- Additional FSL support ---

    public void setEditable(boolean editable) {
        getController().setEditable(editable);
        ((MindMapController) getController().getMode().getModeController())
                .setEditable(editable);
        ((MindMapController) getController().getMode().getModeController())
                .updateActions();
    }

    public String getXMLRepresentationOfMindmap() {
        String xmlRepresentationOfMindmap = new String();
        try {
            xmlRepresentationOfMindmap = ((MindMapMapModel) getController().getModel()).getXMLRepresentation();
        } 
        catch (IOException e) {
            System.err.println("Failed to get XML Representation of Mindmap.");
        }
        return xmlRepresentationOfMindmap;
    }

    public boolean isModified() {
        return !getController().getModel().isSaved();
    }

    public void insertLink() {
        String newLink = flgMindmapsManager.editLearningUnitViewElementLink(
            c.getMode().getModeController().getLinkIdForSelectedNode(),
            flgMindmapsManager.getActiveLearningUnitViewElementId());
        c.getMode().getModeController().setLinkIdForSelectedNode(newLink);
    }

    public void addHyperlinkListener(HyperlinkListener hyperlinkListener) {
        hyperlinkListeners.add(hyperlinkListener);
    }

    public void hyperlinkEntered(String link) {
        URL url = createFSLOrExternalURL(link);
        HyperlinkEvent hyperlinkEvent = new HyperlinkEvent(this, HyperlinkEvent.EventType.ENTERED, url, link);
        fireHyperlinkEvent(hyperlinkEvent);
    }

    public void hyperlinkActivated(String link) {
        URL url = createFSLOrExternalURL(link);
        HyperlinkEvent hyperlinkEvent = new HyperlinkEvent(this, HyperlinkEvent.EventType.ACTIVATED, url, link);
        fireHyperlinkEvent(hyperlinkEvent);
    }

    public void hyperlinkExited(String link) {
        URL url = createFSLOrExternalURL(link);
        HyperlinkEvent hyperlinkEvent = new HyperlinkEvent(this, HyperlinkEvent.EventType.EXITED, url, link);
        fireHyperlinkEvent(hyperlinkEvent);
    }

    private URL createFSLOrExternalURL(String link) {
        String urlString;
        URL url;
        if (link.startsWith("l")) { // FSL link
            String filePrefix = "file://";
            String activeViewElementId = flgMindmapsManager .getActiveLearningUnitViewElementId();
            FSLLearningUnitViewElement activeViewElement = flgMindmapsManager.getLearningUnitViewElementsManager().getLearningUnitViewElement(activeViewElementId, false);
            String absolutePathFileName = flgMindmapsManager.getLearningUnitViewElementsManager().resolveRelativeFileName(link, activeViewElement).getAbsolutePath();
            urlString = filePrefix + absolutePathFileName;

        } 
        else {
            urlString = link; // external link string is recognized without
        }
        // modifications
        try {
            url = new URL(urlString);
        } 
        catch (MalformedURLException e) {
            url = null;
        }
        return url;
    }

    private void fireHyperlinkEvent(HyperlinkEvent e) {
        for (int i = 0; i < hyperlinkListeners.size(); i++)
            ((HyperlinkListener) hyperlinkListeners.elementAt(i)).hyperlinkUpdate(e);
    }

    public void zoom(float zoom) {
        c.zoom(zoom);
    }

    public void cut() {
        getMindmapController().cutAction
                .actionPerformed(createStandardActionEvent());
    }

    private ActionEvent createStandardActionEvent() {
        return new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "");
    }

    public void copy() {
        getMindmapController().copyAction
                .actionPerformed(createStandardActionEvent());
    }

    public void paste() {
        getMindmapController().pasteAction
                .actionPerformed(createStandardActionEvent());
    }

    public void bold() {
        getMindmapController().boldAction
                .actionPerformed(createStandardActionEvent());
    }

    public void italic() {
        getMindmapController().italicAction
                .actionPerformed(createStandardActionEvent());
    }

    public void increaseFontSize() {
        getMindmapController().increaseFontSizeAction
                .actionPerformed(createStandardActionEvent());
    }

    public void decreaseFontSize() {
        getMindmapController().decreaseFontSizeAction
                .actionPerformed(createStandardActionEvent());
    }

    public void nodeColor() {
        getMindmapController().nodeColorAction
                .actionPerformed(createStandardActionEvent());
    }

    public void fork() {
        getMindmapController().forkAction
                .actionPerformed(createStandardActionEvent());
    }

    public void bubble() {
        getMindmapController().bubbleAction
                .actionPerformed(createStandardActionEvent());
    }
    
    public void cloud() {
        getMindmapController().cloudAction
                .actionPerformed(createStandardActionEvent());
    }

    public void cloudColor() {
        getMindmapController().cloudColorAction
                .actionPerformed(createStandardActionEvent());
    }

    public void editNode() {
        getMindmapController().editAction
                .actionPerformed(createStandardActionEvent());
    }

    public void newChild() {
        getMindmapController().newChildAction
                .actionPerformed(createStandardActionEvent());
    }

    public void newSibling() {
        getMindmapController().newSiblingAction
                .actionPerformed(createStandardActionEvent());
    }

    public void newPreviousSibling() {
        getMindmapController().newPreviousSiblingAction
                .actionPerformed(createStandardActionEvent());
    }

    public void nodeUp() {
        getMindmapController().nodeUpAction
                .actionPerformed(createStandardActionEvent());
    }

    public void nodeDown() {
        getMindmapController().nodeDownAction
                .actionPerformed(createStandardActionEvent());
    }

    public boolean find() {
        return getMindmapController().find();
    }

    public boolean findNext() {
        return getMindmapController().findNext();
    }

    public void toggleFolded() {
        getMindmapController().toggleFoldedAction
                .actionPerformed(createStandardActionEvent());
    }

    public void edgeColor() {
        getMindmapController().edgeColorAction
                .actionPerformed(createStandardActionEvent());
    }

    public void increaseEdgeWidth() {
        getMindmapController().increaseEdgeWidth();
    }

    public void decreaseEdgeWidth() {
        getMindmapController().decreaseEdgeWidth();
    }

    public void followLink() {
        getMindmapController().loadURL();
    }

    public void insertImage(File imageFile) {
        getMindmapController().setImage(imageFile);
    }
    
    public void insertIcon(String iconName) {
        MindIcon mindIcon= new MindIcon(iconName);
        getMindmapController().addNodeIcon(mindIcon);
    }
    
    public void removeNodeIcon() {
        getMindmapController().removeNodeIcon();
    }
    
   public MapView getPrintPanel() {
        return getView().getCopy();
   }

    private MindMapController getMindmapController() {
        return ((MindMapController) c.getMode().getModeController());
    }

}