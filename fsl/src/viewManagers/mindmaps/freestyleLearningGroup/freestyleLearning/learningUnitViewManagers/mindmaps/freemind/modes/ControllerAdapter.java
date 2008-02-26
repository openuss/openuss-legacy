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
/*$Id: ControllerAdapter.java,v 1.6 2004/09/01 04:46:44 jbrenni Exp $*/

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.JTextComponent;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.controller.Controller;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.main.ExampleFileFilter;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.main.FreeMindMain;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.main.Tools;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.main.XMLParseException;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.view.MapModule;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.view.mindmapview.MapView;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.view.mindmapview.NodeView;

/**
 * Derive from this class to implement the Controller for your mode. Overload
 * the methods you need for your data model, or use the defaults. There are some
 * default Actions you may want to use for easy editing of your model. Take
 * MindMapController as a sample.
 */
public abstract class ControllerAdapter implements ModeController {

    Mode mode;

    private int noOfMaps = 0; //The number of currently open maps

    private Clipboard clipboard;

    private int status;

    static final Color selectionColor = new Color(200, 220, 200);

    private boolean editable;

    public Action copyAction;

    public Action cutAction;

    public Action pasteAction;

    public Action italicAction;

    public Action boldAction;

    public Action increaseFontSizeAction;

    public Action decreaseFontSizeAction;

    public Action nodeColorAction;

    public Action forkAction;

    public Action bubbleAction;

    public Action cloudAction;

    public Action cloudColorAction;

    public Action editAction;

    public Action newChildAction;

    public Action removeAction;

    public Action newSiblingAction;

    public Action newPreviousSiblingAction;

    public Action nodeUpAction;

    public Action nodeDownAction;

    public Action findAction;

    public Action findNextAction;

    public Action toggleFoldedAction;

    public Action setImageByFileChooserAction;

    public ControllerAdapter() {
    }

    public ControllerAdapter(Mode mode) {
        this.mode = mode;

        DropTarget dropTarget = new DropTarget(getFrame().getViewport(),
                new FileOpener());

        clipboard = getFrame().getViewport().getToolkit().getSystemSelection();

        // SystemSelection is a strange clipboard used for instance on
        // Linux. To get data into this clipboard user just selects the area
        // without pressing Ctrl+C like on Windows.

        if (clipboard == null) {
            clipboard = getFrame().getViewport().getToolkit()
                    .getSystemClipboard();
        }

    }

    //
    // Methods that should be overloaded
    //

    protected abstract MindMapNode newNode();

    /**
     * You _must_ implement this if you use one of the following actions:
     * OpenAction, NewMapAction.
     */
    public MapAdapter newModel(String title) {
        throw new java.lang.UnsupportedOperationException();
    }

    /**
     * You may want to implement this... It returns the FileFilter that is used
     * by the open() and save() JFileChoosers.
     */
    protected FileFilter getFileFilter() {
        return null;
    }

    public void nodeChanged(MindMapNode n) {
    }

    public void anotherNodeSelected(MindMapNode n) {
    }

    public void doubleClick(MouseEvent e) {
        if (editable) {
            /* perform action only if one selected node. */
            if (getSelecteds().size() != 1)
                return;
            MindMapNode node = ((NodeView) (e.getComponent())).getModel();
            // edit the node only if the node is a leaf (fc 0.7.1)
            if (node.hasChildren()) {
                // the emulate the plain click.
                plainClick(e);
                return;
            }
            if (!e.isAltDown() && !e.isControlDown() && !e.isShiftDown()
                    && !e.isPopupTrigger()
                    && e.getButton() == MouseEvent.BUTTON1
                    && (node.getLink() == null)) {
                edit(null, false, true);
            }
        }
    }

    public void plainClick(MouseEvent e) {
        /* perform action only if one selected node. */
        if (getSelecteds().size() != 1)
            return;
        MindMapNode node = ((NodeView) (e.getComponent())).getModel();
        if (getView().getSelected().followLink(e.getX())) {
            loadURL();
        } else {
            if (!node.hasChildren()) {
                // the emulate the plain click.
                doubleClick(e);
                return;
            }
            toggleFolded();
        }
    }

    //
    // Map Management
    //

    /**
     * Get text identification of the map
     */

    protected String getText(String textId) {
        return getController().getResourceString(textId);
    }

    protected boolean binOptionIsTrue(String option) {
        return getFrame().getProperty(option).equals("true");
    }

    public void newMap(String title) {
        if (noOfMaps > 0) 
            close(true);
        getController().getMapModuleManager().newMapModule(newModel(title));
        getController().moveToRoot();
        mapOpened(true);
    }

    protected void newMap(MindMap map) {
        getController().getMapModuleManager().newMapModule(map);
        mapOpened(true);
    }

    /**
     * You may decide to overload this or take the default and implement the
     * functionality in your MapModel (implements MindMap)
     */
    public void load(File file) throws FileNotFoundException, IOException,
            XMLParseException {
        if (noOfMaps > 0) 
            close(true);
        MapAdapter model = newModel("Loading...");
        model.load(file);
        getController().getMapModuleManager().newMapModule(model);
        mapOpened(true);
        getController().moveToRoot();
    }

    public boolean save() {
        if (getModel().isSaved())
            return true;
        if (getModel().getFile() == null || getModel().isReadOnly()) {
            return saveAs();
        } else {
            return save(getModel().getFile());
        }
    }

    /**
     * fc, 24.1.2004: having two methods getSelecteds with different return
     * values (linkedlists of models resp. views) is asking for trouble.
     * 
     * @see MapView
     */
    protected LinkedList getSelecteds() {
        LinkedList selecteds = new LinkedList();
        ListIterator it = getView().getSelecteds().listIterator();
        if (it != null) {
            while (it.hasNext()) {
                NodeView selected = (NodeView) it.next();
                selecteds.add(selected.getModel());
            }
        }
        return selecteds;
    }

    /**
     * Return false is the action was cancelled, e.g. when it has to lead to
     * saving as.
     */
    public boolean save(File file) {
        return getModel().save(file);
    }

    /** @return returns the new JMenuItem. */
    protected JMenuItem add(JMenu menu, Action action, String keystroke) {
        JMenuItem item = menu.add(action);
        item.setAccelerator(KeyStroke.getKeyStroke(getFrame().getProperty(
                keystroke)));
        return item;
    }

    protected void add(JMenu menu, Action action) {
        menu.add(action);
    }

    //
    // Dialogs with user
    //

    public void open() {
        JFileChooser chooser = null;
        if ((getMap() != null) && (getMap().getFile() != null)
                && (getMap().getFile().getParentFile() != null)) {
            chooser = new JFileChooser(getMap().getFile().getParentFile());
        } else {
            chooser = new JFileChooser();
        }
        //chooser.setLocale(currentLocale);
        if (getFileFilter() != null) {
            chooser.addChoosableFileFilter(getFileFilter());
        }
        int returnVal = chooser.showOpenDialog(getView());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                load(chooser.getSelectedFile());
            } catch (Exception ex) {
                handleLoadingException(ex);
            }
            {
            }
        }
        getController().setTitle();
    }

    public void handleLoadingException(Exception ex) {
        String exceptionType = ex.getClass().getName();
        if (exceptionType
                .equals("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.main.XMLParseException")) {
            int showDetail = JOptionPane.showConfirmDialog(getView(),
                    getText("map_corrupted"), "FreeMindAdapter",
                    JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            if (showDetail == JOptionPane.YES_OPTION) {
                getController().errorMessage(ex);
            }
        } else if (exceptionType.equals("java.io.FileNotFoundException")) {
            getController().errorMessage(ex.getMessage());
        } else {
            getController().errorMessage(ex);
        }
    }

    /**
     * Save as; return false is the action was cancelled
     */
    public boolean saveAs() {
        JFileChooser chooser = null;
        if ((getMap().getFile() != null)
                && (getMap().getFile().getParentFile() != null)) {
            chooser = new JFileChooser(getMap().getFile().getParentFile());
        } else {
            chooser = new JFileChooser();
            chooser.setSelectedFile(new File(((MindMapNode) getMap().getRoot())
                    .toString()
                    + ".mm"));
        }
        //chooser.setLocale(currentLocale);
        if (getFileFilter() != null) {
            chooser.addChoosableFileFilter(getFileFilter());
        }

        chooser.setDialogTitle(getText("save_as"));
        int returnVal = chooser.showSaveDialog(getView());
        if (returnVal != JFileChooser.APPROVE_OPTION) {// not ok pressed
            return false;
        }

        // |= Pressed O.K.
        File f = chooser.getSelectedFile();
        //Force the extension to be .mm
        String ext = Tools.getExtension(f.getName());
        if (!ext.equals("mm")) {
            f = new File(f.getParent(), f.getName() + ".mm");
        }

        if (f.exists()) { // If file exists, ask before overwriting.
            int overwriteMap = JOptionPane.showConfirmDialog(getView(),
                    getText("map_already_exists"), "FreeMindAdapter",
                    JOptionPane.YES_NO_OPTION);
            if (overwriteMap != JOptionPane.YES_OPTION) {
                return false;
            }
        }

        try { // We have to lock the file of the map even when it does not exist
            // yet
            String lockingUser = getModel().tryToLock(f);
            if (lockingUser != null) {
                getFrame().getController().informationMessage(
                        Tools.expandPlaceholders(
                                getText("map_locked_by_save_as"), f.getName(),
                                lockingUser));
                return false;
            }
        } catch (Exception e) { // Throwed by tryToLock
            getFrame().getController().informationMessage(
                    Tools.expandPlaceholders(
                            getText("locking_failed_by_save_as"), f.getName()));
            return false;
        }

        save(f);
        //Update the name of the map
        getController().getMapModuleManager().updateMapModuleName();
        return true;
    }

    /**
     * Return false if user has canceled.
     */
    public boolean close(boolean force) {
        String[] options = { getText("yes"), getText("no"), getText("cancel") };
        if (!getModel().isSaved() && !force) {
            String text = getText("save_unsaved") + "\n"
                    + getMapModule().toString();
            String title = getText("save");
            int returnVal = JOptionPane.showOptionDialog(getFrame()
                    .getContentPane(), text, title,
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (returnVal == JOptionPane.YES_OPTION) {
                boolean savingNotCancelled = save();
                if (!savingNotCancelled) {
                    return false;
                }
            } else if (returnVal == JOptionPane.CANCEL_OPTION) {
                return false;
            }
        }

        getModel().destroy();
        mapOpened(false);
        return true;
    }

    /**
     * Call this method if you have opened a map for this mode with true, and if
     * you have closed a map of this mode with false. It updates the Actions
     * that are dependent on whether there is a map or not. --> What to do if
     * either newMap or load or close are overwritten by a concrete
     * implementation? uups.
     */
    public void mapOpened(boolean open) {
        if (open) {
            if (getFrame().getView() != null) {
                DropTarget dropTarget = new DropTarget(getFrame().getView(),
                        new FileOpener());
            }
            noOfMaps++;
        } else {
            noOfMaps--;
        }
    }

    /**
     * Overwrite this to set all of your actions which are dependent on whether
     * there is a map or not.
     */
    protected void setAllActions(boolean enabled) {
    }

    //
    // Node editing
    //

    private JPopupMenu popupmenu;

    /**
     * listener, that blocks the controler if the menu is active (PN) Take care!
     * This listener is also used for modelpopups (as for graphical links).
     */
    private class ControllerPopupMenuListener implements PopupMenuListener {
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            setBlocked(true); // block controller
        }

        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            setBlocked(false); // unblock controller
        }

        public void popupMenuCanceled(PopupMenuEvent e) {
            setBlocked(false); // unblock controller
        }

    }

    /**
     * Take care! This listener is also used for modelpopups (as for graphical
     * links).
     */
    protected final ControllerPopupMenuListener popupListenerSingleton = new ControllerPopupMenuListener();

    public void showPopupMenu(MouseEvent e) {
    }

    /** Default implementation: no context menu. */
    public JPopupMenu getPopupForModel(java.lang.Object obj) {
        return null;
    }

    private static final int SCROLL_SKIPS = 8;

    private static final int SCROLL_SKIP = 10;

    private static final int HORIZONTAL_SCROLL_MASK = InputEvent.SHIFT_MASK
            | InputEvent.BUTTON1_MASK | InputEvent.BUTTON2_MASK
            | InputEvent.BUTTON3_MASK;

    private static final int ZOOM_MASK = InputEvent.CTRL_MASK;

    // |= oldX >=0 iff we are in the drag

    public void mouseWheelMoved(MouseWheelEvent e) {
        if (isBlocked()) {
            return; // block the scroll during edit (PN)
        }

        if ((e.getModifiers() & ZOOM_MASK) != 0) {
            // fc, 18.11.2003: when control pressed, then the zoom is changed.
            float newZoomFactor = 1f + Math.abs((float) e.getWheelRotation()) / 10f;
            if (e.getWheelRotation() < 0)
                newZoomFactor = 1 / newZoomFactor;
            float newZoom = ((MapView) e.getComponent()).getZoom()
                    * newZoomFactor;
            // round the value due to possible rounding problems.
            newZoom = (float) Math.rint(newZoom * 1000f) / 1000f;
            getController().setZoom(newZoom);
            // end zoomchange
        } else if ((e.getModifiers() & HORIZONTAL_SCROLL_MASK) != 0) {
            for (int i = 0; i < SCROLL_SKIPS; i++) {
                ((MapView) e.getComponent()).scrollBy(SCROLL_SKIP
                        * e.getWheelRotation(), 0);
            }
        } else {
            for (int i = 0; i < SCROLL_SKIPS; i++) {
                ((MapView) e.getComponent()).scrollBy(0, SCROLL_SKIP
                        * e.getWheelRotation());
            }
        }
    }

    // edit begins with home/end or typing (PN 6.2)
    public void edit(KeyEvent e, boolean addNew, boolean editLong) {
        if (getView().getSelected() != null) {
            if (e == null || !addNew) {
                edit(getView().getSelected(), getView().getSelected(), e,
                        false, false, editLong);
            } else if (!isBlocked()) {
                addNew(getView().getSelected(), NEW_SIBLING_BEHIND, e);
            }
            if (e != null) {
                e.consume();
            }
        }
    }

    private void changeComponentHeight(JComponent component, int difference,
            int minimum) {
        Dimension preferredSize = component.getPreferredSize();
        System.out.println("pf:" + preferredSize);
        if (preferredSize.getHeight() + difference >= minimum) {
            System.out.println("pf:" + preferredSize);
            component.setPreferredSize(new Dimension((int) preferredSize
                    .getWidth(), (int) preferredSize.getHeight() + difference));
        }
    }

    /** Private variable to hold the last value of the "Enter confirms" state. */
    private static Tools.BooleanHolder booleanHolderForConfirmState;

    private void editLong(final NodeView node, final String text,
            final KeyEvent firstEvent) {

        String inputString = JOptionPane.showInputDialog(getFrame()
                .getInternationalization("edit_node"), text);
        if (inputString != null)
            getModel().changeNode(node.getModel(), inputString);

    }

    // this enables from outside close the edit mode
    private FocusListener textFieldListener = null;

    private void closeEdit() {
        if (this.textFieldListener != null) {
            textFieldListener.focusLost(null); // hack to close the edit
        }
    }

    // status, currently: default, blocked (PN)
    // (blocked to protect against particular events e.g. in edit mode)
    private boolean isBlocked = false;

    public boolean isBlocked() {
        return this.isBlocked;
    }

    private void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    private void edit(final NodeView node, final NodeView prevSelected,
            final KeyEvent firstEvent, final boolean isNewNode,
            final boolean parentFolded, final boolean editLong) {

        if (node == null) {
            return;
        }

        setBlocked(true); // locally "modal" stated

        String text = node.getModel().toString();
        editLong(node, text, firstEvent);
        setBlocked(false);

    }

    public final int NEW_CHILD_WITHOUT_FOCUS = 1; // old model of insertion

    public final int NEW_CHILD = 2;

    public final int NEW_SIBLING_BEHIND = 3;

    public final int NEW_SIBLING_BEFORE = 4;

    private boolean mouseOverHyperlink = false;

    private boolean hyperlinkExitedLast = true;

    public void addNew(final NodeView target, final int newNodeMode,
            final KeyEvent e) {
        closeEdit();

        MindMapNode newNode = newNode();
        final MindMapNode targetNode = target.getModel();

        switch (newNodeMode) {
        case NEW_SIBLING_BEFORE:
        case NEW_SIBLING_BEHIND:
            if (targetNode.isRoot()) {
//                getController()
//                        .errorMessage(
//                                getText("new_node_as_sibling_not_possible_for_the_root"));
                setBlocked(false);
                return;
            }
            MindMapNode parent = targetNode.getParentNode();
            int childPosition = parent.getChildPosition(targetNode);
            if (newNodeMode == NEW_SIBLING_BEHIND) {
                childPosition++;
            }
            if (targetNode.isLeft() != null) {
                newNode.setLeft(targetNode.isLeft().getValue());
            }
            getModel().insertNodeInto(newNode, parent, childPosition);
            select(newNode.getViewer());
            getFrame().repaint(); //  getLayeredPane().repaint();
            edit(newNode.getViewer(), target, e, true, false, true);
            break;

        case NEW_CHILD:
        case NEW_CHILD_WITHOUT_FOCUS:
            final boolean parentFolded = targetNode.isFolded();
            if (parentFolded) {
                getModel().setFolded(targetNode, false);
            }
            int position = getFrame().getProperty("placenewbranches").equals(
                    "last") ? targetNode.getChildCount() : 0;
            // Here the NodeView is created for the node. }
            getModel().insertNodeInto(newNode, targetNode, position);
            getFrame().repaint(); //  getLayeredPane().repaint();
            if (newNodeMode == NEW_CHILD) {
                select(newNode.getViewer());
            }
            final NodeView editView = newNode.getViewer();
            edit(editView, targetNode.getViewer(), e, true, parentFolded, false);
            break;
        }
    }

    //     public void toggleFolded() {
    //         MindMapNode node = getSelected();
    //         // fold the node only if the node is not a leaf (PN 0.6.2)
    //         if (node.hasChildren()
    //             || node.isFolded()
    //             || Tools.safeEquals(getFrame()
    //                 .getProperty("enable_leaves_folding"),"true")) {
    //           getModel().setFolded(node, !node.isFolded());
    //         }
    //         getView().selectAsTheOnlyOneSelected(node.getViewer());
    //     }

    public void toggleFolded() {
        /*
         * Retrieve the information whether or not all nodes have the same
         * folding state.
         */
        Tools.BooleanHolder state = null;
        boolean allNodeHaveSameFoldedStatus = true;
        for (ListIterator it = getSelecteds().listIterator(); it.hasNext();) {
            MindMapNode node = (MindMapNode) it.next();
            if (state == null) {
                state = new Tools.BooleanHolder();
                state.setValue(node.isFolded());
            } else {
                if (node.isFolded() != state.getValue()) {
                    allNodeHaveSameFoldedStatus = false;
                    break;
                }
            }
        }
        /* if the folding state is ambiguous, the nodes are folded. */
        boolean fold = true;
        if (allNodeHaveSameFoldedStatus && state != null) {
            fold = !state.getValue();
        }
        MindMapNode lastNode = null;
        for (ListIterator it = getView().getSelectedsByDepth().listIterator(); it
                .hasNext();) {
            MindMapNode node = ((NodeView) it.next()).getModel();
            // fold the node only if the node is not a leaf (PN 0.6.2)
            if (node.hasChildren()
                    || node.isFolded()
                    || Tools.safeEquals(getFrame().getProperty(
                            "enable_leaves_folding"), "true")) {
                getModel().setFolded(node, fold);
            }
            lastNode = node;
        }
        if (lastNode != null)
            getView().selectAsTheOnlyOneSelected(lastNode.getViewer());
        // Only adds to number of changes if in edit mode => folded-toggling in
        // non-edit mode should not be saved, thus should not make the map
        // dirty.
        if (editable)
            getMap().toggledFoldedInEditMode();
    }

    /**
     * If any children are folded, unfold all folded children. Otherwise, fold
     * all children.
     */
    protected void toggleChildrenFolded() {
        // have NodeAdapter; need NodeView
        MindMapNode parent = getSelected();
        ListIterator children_it = parent.getViewer().getChildrenViews()
                .listIterator();
        boolean areAnyFolded = false;
        while (children_it.hasNext() && !areAnyFolded) {
            NodeView child = (NodeView) children_it.next();
            if (child.getModel().isFolded()) {
                areAnyFolded = true;
            }
        }

        // fold the node only if the node is not a leaf (PN 0.6.2)
        boolean enableLeavesFolding = Tools.safeEquals(getFrame().getProperty(
                "enable_leaves_folding"), "true");

        children_it = parent.getViewer().getChildrenViews().listIterator();
        while (children_it.hasNext()) {
            MindMapNode child = ((NodeView) children_it.next()).getModel();
            if (child.hasChildren() || enableLeavesFolding || child.isFolded()) {
                getModel().setFolded(child, !areAnyFolded); // (PN 0.6.2)
            }
        }
        getView().selectAsTheOnlyOneSelected(parent.getViewer());

        getController().obtainFocusForSelected(); // focus fix
    }

    protected void setLinkByTextField() {
        // Requires J2SDK1.4.0!
        String inputValue = JOptionPane.showInputDialog(
                getText("edit_link_manually"), getModel()
                        .getLink(getSelected()));
        if (inputValue != null) {
            if (inputValue.equals("")) {
                inputValue = null; // In case of no entry unset link
            }
            getModel().setLink(getSelected(), inputValue);
        }
    }

    protected void setLinkByFileChooser() {
        String relative = getLinkByFileChooser(getFileFilter());
        if (relative != null)
            getModel().setLink(getSelected(), relative);
    }

    public void setImage(File input) {
        ExampleFileFilter filter = new ExampleFileFilter();
        filter.addExtension("jpg");
        filter.addExtension("jpeg");
        filter.addExtension("png");
        filter.addExtension("gif");
        filter.setDescription("JPG, PNG and GIF Images");

        // Are there any selected nodes with pictures?
        boolean picturesAmongSelecteds = false;
        for (ListIterator e = getSelecteds().listIterator(); e.hasNext();) {
            String link = ((MindMapNode) e.next()).getLink();
            if (link != null) {
                if (filter.accept(new File(link))) {
                    picturesAmongSelecteds = true;
                    break;
                }
            }
        }

        try {
            if (picturesAmongSelecteds) {
                for (ListIterator e = getSelecteds().listIterator(); e
                        .hasNext();) {
                    MindMapNode node = (MindMapNode) e.next();
                    if (node.getLink() != null) {
                        String possiblyRelative = node.getLink();
                        String relative = Tools
                                .isAbsolutePath(possiblyRelative) ? new File(
                                possiblyRelative).toURL().toString()
                                : possiblyRelative;
                        if (relative != null) {
                            String strText = "<html><img src=\"" + relative
                                    + "\">";
                            node.setLink(null);
                            getModel().changeNode(node, strText);
                        }
                    }
                }
            } else {
                URL url = null;
                String relative = null;
                try {
                    url = input.toURL();
                    relative = url.toString();
                } catch (MalformedURLException ex) {
                    getController().errorMessage(getText("url_error"));
                    relative = null;
                }
                //Create relative URL
                try {
                    relative = Tools.toRelativeURL(getMap().getFile().toURL(),
                            url);
                } catch (MalformedURLException ex) {
                    getController().errorMessage(getText("url_error"));
                    relative = null;
                }
                if (relative != null) {
                    // FSL adjusted: put old text under image, if available
                    String oldText = getSelected().toString();
                    String strText;
                    if (oldText.equals("")) 
                        strText = "<html><p align=center><img src=\"" + relative + "\"></p>";
                    else
                        strText = "<html><p align=center><img src=\"" + relative + "\"><br>" + oldText + "</p>";
                    getModel().changeNode((MindMapNode) getSelected(), strText);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    protected String getLinkByFileChooser(FileFilter fileFilter) {
        URL link;
        String relative = null;
        File input;
        JFileChooser chooser = null;
        if (getMap().getFile() == null) {
            JOptionPane.showMessageDialog(getFrame().getContentPane(),
                    getText("not_saved_for_link_error"), "FreeMindAdapter",
                    JOptionPane.WARNING_MESSAGE);
            return null;
            // In the previous version Freemind automatically displayed save
            // dialog. It happened very often, that user took this save
            // dialog to be an open link dialog; as a result, the new map
            // overwrote the linked map.

        }
        if ((getMap().getFile() != null)
                && (getMap().getFile().getParentFile() != null)) {
            chooser = new JFileChooser(getMap().getFile().getParentFile());
        } else {
            chooser = new JFileChooser();
        }

        if (fileFilter != null) {
            // Set filters, make sure AcceptAll filter comes first
            chooser.setFileFilter(fileFilter);
        } else {
            chooser.setFileFilter(chooser.getAcceptAllFileFilter());
        }

        int returnVal = chooser.showOpenDialog(getFrame().getContentPane());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            input = chooser.getSelectedFile();
            try {
                link = input.toURL();
                relative = link.toString();
            } catch (MalformedURLException ex) {
                getController().errorMessage(getText("url_error"));
                return null;
            }
            if (getFrame().getProperty("links").equals("relative")) {
                //Create relative URL
                try {
                    relative = Tools.toRelativeURL(getMap().getFile().toURL(),
                            link);
                } catch (MalformedURLException ex) {
                    getController().errorMessage(getText("url_error"));
                    return null;
                }
            }
        }
        return relative;
    }

    public void loadURL(String relative) {
        if (!editable) {
            getFrame().hyperlinkActivated(relative);
        }
    }

    public void loadURL() {
        String link = getSelected().getLink();
        if (link != null) {
            loadURL(link);
        }
    }

    //
    // Convenience methods
    //

    protected Mode getMode() {
        return mode;
    }

    protected void setMode(Mode mode) {
        this.mode = mode;
    }

    protected MapModule getMapModule() {
        return getController().getMapModuleManager().getMapModule();
    }

    public MapAdapter getMap() {
        if (getMapModule() != null) {
            return (MapAdapter) getMapModule().getModel();
        } else {
            return null;
        }
    }

    public URL getResource(String name) {
        return getFrame().getResource(name);
    }

    public Controller getController() {
        return getMode().getController();
    }

    public FreeMindMain getFrame() {
        return getController().getFrame();
    }

    private MapAdapter getModel() {
        return (MapAdapter) getController().getModel();
    }

    public MapView getView() {
        return getController().getView();
    }

    protected void updateMapModuleName() {
        getController().getMapModuleManager().updateMapModuleName();
    }

    private NodeAdapter getSelected() {
        return (NodeAdapter) getView().getSelected().getModel();
    }

    public boolean extendSelection(MouseEvent e) {
        NodeView newlySelectedNodeView = (NodeView) e.getSource();
        //MindMapNode newlySelectedNode = newlySelectedNodeView.getModel();
        boolean extend = e.isControlDown();
        boolean range = e.isShiftDown();
        boolean branch = e.isAltGraphDown() || e.isAltDown(); /*
                                                               * windows alt,
                                                               * linux altgraph
                                                               * ....
                                                               */
        boolean retValue = false;

        if (extend || range || branch
                || !getView().isSelected(newlySelectedNodeView)) {
            if (!range) {
                if (extend)
                    getView().toggleSelected(newlySelectedNodeView);
                else
                    select(newlySelectedNodeView);
                retValue = true;
            } else {
                retValue = getView().selectContinuous(newlySelectedNodeView);
                //                 /* fc, 25.1.2004: replace getView by controller methods.*/
                //                 if (newlySelectedNodeView != getView().getSelected() &&
                //                     newlySelectedNodeView.isSiblingOf(getView().getSelected())) {
                //                     getView().selectContinuous(newlySelectedNodeView);
                //                     retValue = true;
                //                 } else {
                //                     /* if shift was down, but no range can be selected, then the
                // new node is simply selected: */
                //                     if(!getView().isSelected(newlySelectedNodeView)) {
                //                         getView().toggleSelected(newlySelectedNodeView);
                //                         retValue = true;
                //                     }
            }
            if (branch) {
                getView().selectBranch(newlySelectedNodeView, extend);
                retValue = true;
            }
        }

        if (retValue) {
            e.consume();

        }
        return retValue;
    }

    private void select(NodeView node) {
        getView().selectAsTheOnlyOneSelected(node);
        getView().setSiblingMaxLevel(node.getModel().getNodeLevel()); // this
        // level
        // is
        // default
    }

    ////////////
    //  Actions
    ///////////

    protected class NewMapAction extends AbstractAction {
        ControllerAdapter c;

        public NewMapAction(ControllerAdapter controller) {
            super(getText("new"),
                    new ImageIcon(getResource("images/New24.gif")));
            c = controller;
            //Workaround to get the images loaded in jar file.
            //they have to be added to jar manually with full path from root
            //I really don't like this, but it's a bug of java
        }

        public void actionPerformed(ActionEvent e) {
            c.newMap("New Map");
        }
    }

    protected class OpenAction extends AbstractAction {
        ControllerAdapter mc;

        public OpenAction(ControllerAdapter modeController) {
            super(getText("open"), new ImageIcon(
                    getResource("images/Open24.gif")));
            mc = modeController;
        }

        public void actionPerformed(ActionEvent e) {
            mc.open();
            getController().setTitle(); // Possible update of read-only
        }
    }

    protected class SaveAction extends AbstractAction {
        ControllerAdapter mc;

        public SaveAction(ControllerAdapter modeController) {
            super(getText("save"), new ImageIcon(
                    getResource("images/Save24.gif")));
            mc = modeController;
        }

        public void actionPerformed(ActionEvent e) {
            mc.save();
            getController().setTitle(); // Possible update of read-only
        }
    }

    protected class SaveAsAction extends AbstractAction {
        ControllerAdapter mc;

        public SaveAsAction(ControllerAdapter modeController) {
            super(getText("save_as"), new ImageIcon(
                    getResource("images/SaveAs24.gif")));
            mc = modeController;
        }

        public void actionPerformed(ActionEvent e) {
            mc.saveAs();
            getController().setTitle(); // Possible update of read-only
        }
    }

    public boolean find() {
        boolean found = false;
        String what = JOptionPane.showInputDialog(getView().getSelected(),
                getText("find_what"));
        if (!(what == null || what.equals(""))) {
            found = getView().getModel().find(
                    getView().getSelected().getModel(), what, /* caseSensitive= */
                    false);
            getView().repaint();
            if (!found) {
                getController()
                        .informationMessage(
                                getText("no_found_from").replaceAll("\\$1",
                                        what).replaceAll("\\$2",
                                        getView().getModel().getFindFromText()),
                                getView().getSelected());
            }
        }
        return found;
    }

    public boolean findNext() {
        boolean found = false;
        String what = getView().getModel().getFindWhat();
        if (what == null) {
            getController().informationMessage(getText("no_previous_find"),
                    getView().getSelected());
        } else {
            found = getView().getModel().findNext();
            getView().repaint();
            if (!found) {
                getController()
                        .informationMessage(
                                getText("no_more_found_from").replaceAll(
                                        "\\$1", what).replaceAll("\\$2",
                                        getView().getModel().getFindFromText()),
                                getView().getSelected());
            }
        }
        return found;
    }

    protected class GotoLinkNodeAction extends AbstractAction {
        MindMapNode source;

        public GotoLinkNodeAction(String text, MindMapNode source) {
            super("", new ImageIcon(getResource("images/Link.png")));
            // only display a reasonable part of the string. the rest is
            // available via the short description (tooltip).
            String adaptedText = new String(text);
            adaptedText = adaptedText.replaceAll("<html>", "");
            if (adaptedText.length() > 40)
                adaptedText = adaptedText.substring(0, 40) + " ...";
            putValue(Action.NAME, getText("follow_link") + adaptedText);
            putValue(Action.SHORT_DESCRIPTION, text);
            this.source = source;
        }

        public void actionPerformed(ActionEvent e) {
            getMap().displayNode(source, null);
        }
    }

    //
    // Node editing
    //

    protected class EditAction extends AbstractAction {
        public EditAction() {
            super(getText("edit"));
        }

        public void actionPerformed(ActionEvent e) {
            edit(null, false, true);
        }
    }

    protected class EditLongAction extends AbstractAction {
        public EditLongAction() {
            super(getText("edit_long_node"));
        }

        public void actionPerformed(ActionEvent e) {
            edit(null, false, true);
        }
    }

    // old model of inserting node
    protected class NewChildWithoutFocusAction extends AbstractAction {
        public NewChildWithoutFocusAction() {
            super(getText("new_node"));
        }

        public void actionPerformed(ActionEvent e) {
            addNew(getView().getSelected(), NEW_CHILD_WITHOUT_FOCUS, null);
        }
    }

    // new model of inserting node
    protected class NewSiblingAction extends AbstractAction {
        public NewSiblingAction() {
            super(getText("new_sibling_behind"));
        }

        public void actionPerformed(ActionEvent e) {
            addNew(getView().getSelected(), NEW_SIBLING_BEHIND, null);
        }
    }

    protected class NewChildAction extends AbstractAction {
        public NewChildAction() {
            super(getText("new_child"));
        }

        public void actionPerformed(ActionEvent e) {
            addNew(getView().getSelected(), NEW_CHILD, null);
        }
    }

    protected class NewPreviousSiblingAction extends AbstractAction {
        public NewPreviousSiblingAction() {
            super(getText("new_sibling_before"));
        }

        public void actionPerformed(ActionEvent e) {
            addNew(getView().getSelected(), NEW_SIBLING_BEFORE, null);
        }
    }

    protected class RemoveAction extends AbstractAction {
        public RemoveAction() {
            super(getText("remove_node"));
        }

        public void actionPerformed(ActionEvent e) {
            if (getMapModule() != null) {
                getView().getModel().cut();
                getController().obtainFocusForSelected();
            }
        }
    }

    protected class NodeUpAction extends AbstractAction {
        public NodeUpAction() {
            super(getText("node_up"));
        }

        public void actionPerformed(ActionEvent e) {
            MindMapNode selected = getView().getSelected().getModel();
            if (!selected.isRoot()) {
                MindMapNode parent = selected.getParentNode();
                int index = getModel().getIndexOfChild(parent, selected);
                int newIndex = getModel().moveNodeTo(selected, parent, index,
                        -1);
                getModel().removeNodeFromParent(selected);
                getModel().insertNodeInto(selected, parent, newIndex);
                //                 int maxindex = parent.getChildCount(); // (PN)
                //                 if(index - 1 <0) {
                //                     getModel().insertNodeInto(selected,parent,maxindex, -1);
                //                 } else {
                //                     getModel().insertNodeInto(selected,parent,index - 1, -1);
                //                 }
                getModel().nodeStructureChanged(parent);
                getView().selectAsTheOnlyOneSelected(selected.getViewer());

                getController().obtainFocusForSelected(); // focus fix
            }
        }
    }

    protected class NodeDownAction extends AbstractAction {
        public NodeDownAction() {
            super(getText("node_down"));
        }

        public void actionPerformed(ActionEvent e) {
            MindMapNode selected = getView().getSelected().getModel();
            if (!selected.isRoot()) {
                MindMapNode parent = selected.getParentNode();
                int index = getModel().getIndexOfChild(parent, selected);
                int newIndex = getModel()
                        .moveNodeTo(selected, parent, index, 1);
                getModel().removeNodeFromParent(selected);
                getModel().insertNodeInto(selected, parent, newIndex);
                //                 int maxindex = parent.getChildCount(); // (PN)
                //                 if(index + 1 > maxindex) {
                //                     getModel().insertNodeInto(selected,parent,0, 1);
                //                 } else {
                //                     getModel().insertNodeInto(selected,parent,index + 1, 1);
                //                 }
                getModel().nodeStructureChanged(parent);
                getView().selectAsTheOnlyOneSelected(selected.getViewer());

                getController().obtainFocusForSelected(); // focus fix
            }
        }
    }

    protected class ToggleFoldedAction extends AbstractAction {
        public ToggleFoldedAction() {
            super(getText("toggle_folded"));
        }

        public void actionPerformed(ActionEvent e) {
            toggleFolded();
        }
    }

    protected class ToggleChildrenFoldedAction extends AbstractAction {
        public ToggleChildrenFoldedAction() {
            super(getText("toggle_children_folded"));
        }

        public void actionPerformed(ActionEvent e) {
            toggleChildrenFolded();
        }
    }

    protected class SetLinkByFileChooserAction extends AbstractAction {
        public SetLinkByFileChooserAction() {
            super(getText("set_link_by_filechooser"));
        }

        public void actionPerformed(ActionEvent e) {
            setLinkByFileChooser();
        }
    }

    protected class SetLinkByTextFieldAction extends AbstractAction {
        public SetLinkByTextFieldAction() {
            super(getText("set_link_by_textfield"));
        }

        public void actionPerformed(ActionEvent e) {
            setLinkByTextField();
        }
    }

    protected class FollowLinkAction extends AbstractAction {
        public FollowLinkAction() {
            super(getText("follow_link"));
        }

        public void actionPerformed(ActionEvent e) {
            loadURL();
        }
    }

    protected class CopyAction extends AbstractAction {
        public CopyAction(Object controller) {
            super(getText("copy"), new ImageIcon(
                    getResource("images/Copy24.gif")));
        }

        public void actionPerformed(ActionEvent e) {
            if (getMapModule() != null) {
                Transferable copy = getView().getModel().copy();
                if (copy != null) {
                    clipboard.setContents(copy, null);
                }
            }
        }
    }

    protected class CopySingleAction extends AbstractAction {
        public CopySingleAction(Object controller) {
            super(getText("copy_single"));
        }

        public void actionPerformed(ActionEvent e) {
            if (getMapModule() != null) {
                Transferable copy = getView().getModel().copySingle();
                if (copy != null) {
                    clipboard.setContents(copy, null);
                }
            }
        }
    }

    protected class CutAction extends AbstractAction {
        public CutAction(Object controller) {
            super(getText("cut"),
                    new ImageIcon(getResource("images/Cut24.gif")));
        }

        public void actionPerformed(ActionEvent e) {
            if (getMapModule() != null) {
                Transferable copy = getView().getModel().cut();
                if (copy != null) {
                    clipboard.setContents(copy, null);
                    getController().obtainFocusForSelected();
                }
            }
        }
    }

    protected class PasteAction extends AbstractAction {
        public PasteAction(Object controller) {
            super(getText("paste"), new ImageIcon(
                    getResource("images/Paste24.gif")));
        }

        public void actionPerformed(ActionEvent e) {
            if (clipboard != null) {
                getModel().paste(clipboard.getContents(this),
                        getView().getSelected().getModel());
            }
        }
    }

    protected class FileOpener implements DropTargetListener {
        private boolean isDragAcceptable(DropTargetDragEvent event) {
            // check if there is at least one File Type in the list
            DataFlavor[] flavors = event.getCurrentDataFlavors();
            for (int i = 0; i < flavors.length; i++) {
                if (flavors[i].isFlavorJavaFileListType()) {
                    //              event.acceptDrag(DnDConstants.ACTION_COPY);
                    return true;
                }
            }
            //      event.rejectDrag();
            return false;
        }

        private boolean isDropAcceptable(DropTargetDropEvent event) {
            // check if there is at least one File Type in the list
            DataFlavor[] flavors = event.getCurrentDataFlavors();
            for (int i = 0; i < flavors.length; i++) {
                if (flavors[i].isFlavorJavaFileListType()) {
                    return true;
                }
            }
            return false;
        }

        public void drop(DropTargetDropEvent dtde) {
            if (!isDropAcceptable(dtde)) {
                dtde.rejectDrop();
                return;
            }
            dtde.acceptDrop(DnDConstants.ACTION_COPY);
            try {
                Object data = dtde.getTransferable().getTransferData(
                        DataFlavor.javaFileListFlavor);
                if (data == null) {
                    // Shouldn't happen because dragEnter() rejects drags w/out
                    // at least
                    // one javaFileListFlavor. But just in case it does ...
                    dtde.dropComplete(false);
                    return;
                }
                Iterator iterator = ((List) data).iterator();
                while (iterator.hasNext()) {
                    File file = (File) iterator.next();
                    load(file);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(getView(),
                        "Couldn't open dropped file(s). Reason: "
                                + e.getMessage()
                //getText("file_not_found")
                        );
                dtde.dropComplete(false);
                return;
            }
            dtde.dropComplete(true);
        }

        public void dragEnter(DropTargetDragEvent dtde) {
            if (!isDragAcceptable(dtde)) {
                dtde.rejectDrag();
                return;
            }
        }

        public void dragOver(DropTargetDragEvent e) {
        }

        public void dragExit(DropTargetEvent e) {
        }

        public void dragScroll(DropTargetDragEvent e) {
        }

        public void dropActionChanged(DropTargetDragEvent e) {
        }
    }

    protected class EditCopyAction extends AbstractAction {
        private JTextComponent textComponent;

        public EditCopyAction(JTextComponent textComponent) {
            super(getText("copy"));
            this.textComponent = textComponent;
        }

        public void actionPerformed(ActionEvent e) {
            String selection = textComponent.getSelectedText();
            if (selection != null) {
                clipboard.setContents(new StringSelection(selection), null);
            }
        }
    }

    private class EditPopupMenu extends JPopupMenu {
        //private JTextComponent textComponent;

        public EditPopupMenu(JTextComponent textComponent) {
            //this.textComponent = textComponent;
            this.add(new EditCopyAction(textComponent));
        }
    }

    //  --- Additional FSL support ---

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

    public void setLinkIdForSelectedNode(String link) {
        getModel().setLink(getSelected(), link);
    }

    public String getLinkIdForSelectedNode() {
        return getModel().getLink(getSelected());
    }

    public void nodeEntered(MouseEvent e) {
        // If the folding of a node was toggled, the entered event was fired
        // twice. This renders the resetting of the status panel incorrect,
        // so now it is forced that another enter-event can only occur if
        // there was an exit-event before.
        if (hyperlinkExitedLast) {
            NodeView nodeEntered = ((NodeView) e.getSource());
            if (getModel().getLink((NodeAdapter) nodeEntered.getModel()) != null) {
                mouseOverHyperlink = true;
                hyperlinkExitedLast = false;
                getFrame().hyperlinkEntered(nodeEntered.getModel().getLink());
            }
        }
    }

    public void nodeExited(MouseEvent e) {
        if (mouseOverHyperlink) {
            mouseOverHyperlink = false;
            hyperlinkExitedLast = true;
            NodeView nodeExited = ((NodeView) e.getSource());
            getFrame().hyperlinkExited(nodeExited.getModel().getLink());
        }

    }
}