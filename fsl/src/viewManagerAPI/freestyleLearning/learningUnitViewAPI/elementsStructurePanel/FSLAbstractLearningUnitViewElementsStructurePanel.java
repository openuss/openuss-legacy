/* Generated by Freestyle Learning Group */

package freestyleLearning.learningUnitViewAPI.elementsStructurePanel;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.util.*;
import java.io.*;

import freestyleLearning.learningUnitViewAPI.*;
import freestyleLearning.learningUnitViewAPI.elementsStructurePanel.dialogs.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.*;
import freestyleLearningGroup.independent.gui.*;

abstract public class FSLAbstractLearningUnitViewElementsStructurePanel extends JPanel implements FSLLearningUnitViewElementsStructurePanel {
    protected boolean viewIsActive;
    protected boolean editMode;
    protected FSLLearningUnitViewManager learningUnitViewManager;
    protected FSLLearningUnitViewElementsManager learningUnitViewElementsManager;
    protected String activeLearningUnitViewElementId;
    protected String secondaryActiveLearningUnitViewElementId;
    protected FSLLearningUnitViewElementsStructureEditToolBar editToolBar;
    protected FSLLearningUnitViewElementStructureTree structureTree;
    protected FSLLearningUnitEventGenerator learningUnitEventGenerator;
    private boolean automaticActivation;
    private JLabel label_learningUnitViewManagerTitle;
    private JPanel standardPanel;
    private JPanel blankPanel;
    private JScrollPane scrollPane;
    protected boolean folderImport;
    protected DefaultTreeModel importedFolderFilesStructure;
    protected String selectedMediaType;
    protected ArrayList importedFileList;
    
    public void init(FSLLearningUnitViewManager learningUnitViewManager,
        FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
            this.learningUnitViewManager = learningUnitViewManager;
            this.learningUnitEventGenerator = learningUnitEventGenerator;
            importedFileList = new ArrayList();
            learningUnitEventGenerator.addLearningUnitListener(
               new FSLLearningUnitViewElementsStructurePanel_LearningUnitAdapter());
            this.editMode = editMode;
            learningUnitViewManager.addLearningUnitViewListener(
                new FSLLearningUnitViewElementsStructurePanel_LearningUnitViewAdapter());
            structureTree = new FSLLearningUnitViewElementStructureTree();
            structureTree.init(learningUnitViewManager, learningUnitViewElementsManager,
            	learningUnitEventGenerator, editMode);
            setTreeNodeIconProvider(new FSLAbstractLearningUnitViewElementsStructurePanel_DefaultTreeNodeIconProvider());
            createDefaultEditToolBar();
    }
    
    protected void createDefaultEditToolBar() {
        editToolBar = new FSLLearningUnitViewElementsStructureEditToolBar();
        editToolBar.init(learningUnitViewManager, learningUnitEventGenerator, this, editMode,
        FSLLearningUnitViewElementsStructureEditToolBar.DEFAULT_EDIT_TOOLBAR);
        buildIndependentUI();
    }
    
    protected void createRemoveOnlyEditToolBar() {
        editToolBar = new FSLLearningUnitViewElementsStructureEditToolBar();
        editToolBar.init(learningUnitViewManager, learningUnitEventGenerator, this, editMode,
        FSLLearningUnitViewElementsStructureEditToolBar.REMOVE_ONLY_EDIT_TOOLBAR);
        buildIndependentUI();
    }
    
    /**
    public void setStructureTreeEnabled(boolean enabled) {
        structureTree.setEnabled(enabled);
    }**/
    
    /**
     * @return <code>boolean</code> true if folder import is supported
     */
    public boolean getFolderImport() {
        return folderImport;
    }
    
    /**
     * Creates new Learning Unit View Elements.
     * @param <code>String</code> parentId, the element to add new children
     * @param <code>int</code> position
     * @param <code>String</code> selectedLearningUnitViewElementId
     * @return <code>java.util.List</code> newElements
     */
    public java.util.List createElements(String parentId, int position, String selectedLearningUnitViewElementId) {
        ArrayList newElements = new ArrayList();
        importedFileList.clear();
        
        TreeNode root = (TreeNode)importedFolderFilesStructure.getRoot();
        root.getChildCount();
        
        
        
        // insert root, create new FSL-Element
        String elementId = learningUnitViewElementsManager.createLearningUnitViewElementId();
        File rootFile = (File)((DefaultMutableTreeNode)root).getUserObject();
        
        //String title = rootFile.getName();
        
        String title = editToolBar.getLearningUnitViewElementTitle();
        
        FSLLearningUnitViewElement newElement = createLearningUnitViewElement(elementId, parentId, title, true);
        
        switch (position) {
            case FSLLearningUnitViewNewElementDialog.INSERT_BEFORE:
                learningUnitViewElementsManager.insertLearningUnitViewElementBefore(newElement,
                selectedLearningUnitViewElementId);
                break;
            case FSLLearningUnitViewNewElementDialog.INSERT_AFTER:
                learningUnitViewElementsManager.insertLearningUnitViewElementAfter(newElement,
                selectedLearningUnitViewElementId);
                break;
            default:
                learningUnitViewElementsManager.addLearningUnitViewElement(newElement,
                learningUnitViewElementsManager.isOriginalElementsOnly());
                break;
        }
        
        newElement.setModified(true);
        newElements.add(newElement);
        importedFileList.add(rootFile);
        
        // insert children
        for (int i=0; i<root.getChildCount(); i++) {
            // get child
            TreeNode child = (TreeNode)importedFolderFilesStructure.getChild(root,i);
            File file = (File) ((DefaultMutableTreeNode)child).getUserObject();
            if (file.isDirectory()) {
                // check for elements in folder
                newElements.addAll(createElements(child,elementId));
            } else {
                // create new FSL-Element and insert as child
                parentId = elementId;
                String id = learningUnitViewElementsManager.createLearningUnitViewElementId();
                File childFile = (File)((DefaultMutableTreeNode)child).getUserObject();
                title = childFile.getName();
                newElement = createLearningUnitViewElement(id, parentId, title, false);
                learningUnitViewElementsManager.addLearningUnitViewElement(newElement,
                learningUnitViewElementsManager.isOriginalElementsOnly());
                newElement.setModified(true);
                newElements.add(newElement);
                importedFileList.add(childFile);
            }
        }
        
        learningUnitViewManager.setElementSpecififcFileProperties(importedFileList, newElements, selectedMediaType);
        
        // activate root element
        activeLearningUnitViewElementId = elementId;
        FSLLearningUnitViewEvent event = FSLLearningUnitViewEvent.createElementActivatedEvent(learningUnitViewManager.getLearningUnitViewManagerId(),
        		activeLearningUnitViewElementId, secondaryActiveLearningUnitViewElementId, false);
        learningUnitViewManager.fireLearningUnitViewEvent(event);
        
        return newElements;
    }
    
    private java.util.List createElements(TreeNode directory, String parentId) { 
        ArrayList newElements = new ArrayList();
        
        // insert directory
        String elementId = learningUnitViewElementsManager.createLearningUnitViewElementId();
        File directoryFile = (File)((DefaultMutableTreeNode)directory).getUserObject();
        String title = directoryFile.getName();
        FSLLearningUnitViewElement newElement = createLearningUnitViewElement(elementId, parentId, title, true);
        learningUnitViewElementsManager.addLearningUnitViewElement(newElement,
        learningUnitViewElementsManager.isOriginalElementsOnly());
        newElement.setModified(true);
        newElements.add(newElement);
        importedFileList.add(directoryFile);
        
        // check for children and add them
        parentId = elementId;
        for (int i=0; i<directory.getChildCount(); i++) {
            // get child
            TreeNode child = (TreeNode)importedFolderFilesStructure.getChild(directory,i);
            File childFile = (File) ((DefaultMutableTreeNode)child).getUserObject();
            if (childFile.isDirectory()) {
                // check for elements in folder
                newElements.addAll(createElements(child,elementId));
            } else {
                // create new FSL-Element and insert as child
                String id = learningUnitViewElementsManager.createLearningUnitViewElementId();
                File file = (File)((DefaultMutableTreeNode)child).getUserObject();
                title = file.getName();
                newElement = createLearningUnitViewElement(id, parentId, title, false);
                learningUnitViewElementsManager.insertLearningUnitViewElementAfter(newElement,parentId);
                newElement.setModified(true);
                newElements.add(newElement);
                importedFileList.add(file);
            }
        }
        
        return newElements;
    }
    
    public void setTreeNodeIconProvider(FSLLearningUnitViewElementsStructurePanelTreeNodeIconProvider treeNodeIconProvider) {
        structureTree.setTreeNodeIconProvider(treeNodeIconProvider);
    }
    
    public void setAutomaticActivation(boolean automaticActivation) {
        this.automaticActivation = automaticActivation;
    }
    
    public void setLearningUnitViewElementsManager(FSLLearningUnitViewElementsManager learningUnitViewElementsManager) {
        this.learningUnitViewElementsManager = learningUnitViewElementsManager;
        this.structureTree.setLearningUnitViewElementsManager(learningUnitViewElementsManager);
        this.editToolBar.setLearningUnitViewElementsManager(learningUnitViewElementsManager);
        activeLearningUnitViewElementId = null;
        secondaryActiveLearningUnitViewElementId = null;
        if (viewIsActive) buildDependentUI();
    }
    
    public void updateUI() {
        super.updateUI();
        if (structureTree != null) structureTree.updateUI();
        if (label_learningUnitViewManagerTitle != null) {
            int baseFontSize = ((Integer)UIManager.get("FSLLearningUnitViewElementsStructurePanel.BaseFontSize")).intValue();
            Font font = new Font("SansSerif", Font.BOLD, baseFontSize);
            label_learningUnitViewManagerTitle.setFont(font);
        }
    }
    
    public void activateBlank() {
        scrollPane.setViewportView(blankPanel);
    }
    
    public void activateStandard() {
        scrollPane.setViewportView(structureTree);
    }
    
    protected void buildIndependentUI() {
        setLayout(new BorderLayout());
        setOpaque(false);
        standardPanel = new JPanel();
        blankPanel = new JPanel();
        blankPanel.setOpaque(false);
        standardPanel.setLayout(new BorderLayout());
        standardPanel.setOpaque(false);
        scrollPane = new JScrollPane(structureTree);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        label_learningUnitViewManagerTitle = new JLabel(learningUnitViewManager.getLearningUnitViewManagerTitle());
        label_learningUnitViewManagerTitle.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        centerPanel.add(label_learningUnitViewManagerTitle, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        standardPanel.add(centerPanel, BorderLayout.CENTER);
        standardPanel.add(editToolBar, BorderLayout.NORTH);
        add(standardPanel);
        updateUI();
    }
    
    protected void buildDependentUI() {
        structureTree.repaint();
    }
    
    public abstract FSLLearningUnitViewElement createLearningUnitViewElement(String id, String parentId, String title, boolean folder);
    
    public abstract void modifyLearningUnitViewElement(FSLLearningUnitViewElement element);
    
    public FSLLearningUnitViewNewAndModifyElementDialogViewSpecificPane
    getLearningUnitViewNewAndModifyElementDialogViewSpecificPane() {
        return null;
    }
    
    public static class FSLAbstractLearningUnitViewElementsStructurePanel_DefaultTreeNodeIconProvider implements
    FSLLearningUnitViewElementsStructurePanelTreeNodeIconProvider {
        private Image loadImage(String imageFileName) {
            return FLGImageUtility.loadImageAndWait(getClass().getResource("images/" + imageFileName));
        }
        
        public Icon getElementsStructurePanelTreeNodeIcon(FSLLearningUnitViewElement element, boolean leaf,
        boolean selected, boolean expanded) {
            if (element.getFolder()) {
                if (selected)
                    return new ImageIcon(loadImage("treeNodeFolderSelected.gif"));
                else
                    return new ImageIcon(loadImage("treeNodeFolderNotSelected.gif"));
            }
            else {
                if (selected) return new ImageIcon(loadImage("treeNodeSelected.gif"));
                else
                    return new ImageIcon(loadImage("treeNodeNotSelected.gif"));
            }
        }
    }
    
    
    class FSLLearningUnitViewElementsStructurePanel_LearningUnitAdapter extends FSLLearningUnitVetoableAdapter {
        public void learningUnitEditModeChanged(FSLLearningUnitEvent event) {
            editMode = event.isEditMode();
            if (editMode)
                setBorder(BorderFactory.createLineBorder((Color)UIManager.get("FSLColorRed"), 5));
            else
                setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        }
        
        public void learningUnitUserViewChanged(FSLLearningUnitEvent event) {
            if (viewIsActive) buildDependentUI();
        }
    }
    
    
    class FSLLearningUnitViewElementsStructurePanel_LearningUnitViewAdapter extends FSLLearningUnitViewVetoableAdapter {
        public void learningUnitViewElementsSelected(FSLLearningUnitViewEvent event) {
         if (event.getLearningUnitViewManagerId().equals(learningUnitViewManager.getLearningUnitViewManagerId())) {
                if (automaticActivation) {
                    activeLearningUnitViewElementId = null;
                    String[] learningUnitViewElementIds = event.getLearningUnitViewElementIds();
                    if (learningUnitViewElementIds != null && learningUnitViewElementIds.length > 0) {
                        activeLearningUnitViewElementId = learningUnitViewElementIds[0];
                    }
                    SwingUtilities.invokeLater(
                    new Runnable() {
                        public void run() {
                            FSLLearningUnitViewVetoableEvent vetoableEvent =
                            FSLLearningUnitViewVetoableEvent.createElementActivatingEvent(learningUnitViewManager.getLearningUnitViewManagerId(),
                            activeLearningUnitViewElementId, secondaryActiveLearningUnitViewElementId, false);
                            learningUnitViewManager.fireLearningUnitViewEvent(vetoableEvent);
                            if (!vetoableEvent.isVeto()) {
                                FSLLearningUnitViewEvent nonVetoableEvent =
                                FSLLearningUnitViewEvent.createElementActivatedEvent(learningUnitViewManager.getLearningUnitViewManagerId(),
                                activeLearningUnitViewElementId, secondaryActiveLearningUnitViewElementId, false);
                                learningUnitViewManager.fireLearningUnitViewEvent(nonVetoableEvent);
                            }
                        }
                    });
                }
            }
        }
        
        public void learningUnitViewElementActivated(FSLLearningUnitViewEvent event) {
            if (event.getLearningUnitViewManagerId().equals(learningUnitViewManager.getLearningUnitViewManagerId())) {
                activeLearningUnitViewElementId = event.getActiveLearningUnitViewElementId();
                secondaryActiveLearningUnitViewElementId = event.getSecondaryActiveLearningUnitViewElementId();
            }
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    scrollPane.getHorizontalScrollBar().setValue(0);
                }
            });
        }
        
        public void learningUnitViewElementsRemoved(FSLLearningUnitViewEvent event) {
            if (event.getLearningUnitViewManagerId().equals(learningUnitViewManager.getLearningUnitViewManagerId())) {
                if (automaticActivation) {
                    boolean sendElementActivatedEvent = false;
                    if (!learningUnitViewElementsManager.isEmpty()) {
                        for (int i = 0; i < event.getLearningUnitViewElementIds().length; i++) {
                            String elementId = event.getLearningUnitViewElementIds() [i];
                            if (elementId.equals(activeLearningUnitViewElementId)) {
                                if (learningUnitViewElementsManager.getTopLevelLearningUnitViewElementsIds().length > 0) {
                                    activeLearningUnitViewElementId =
                                    learningUnitViewElementsManager.getTopLevelLearningUnitViewElementsIds() [0];
                                    sendElementActivatedEvent = true;
                                }
                            }
                            if (elementId.equals(secondaryActiveLearningUnitViewElementId)) {
                                secondaryActiveLearningUnitViewElementId =
                                learningUnitViewElementsManager.getTopLevelLearningUnitViewElementsIds() [0];
                                sendElementActivatedEvent = true;
                            }
                        }
                    }
                    else {
                        activeLearningUnitViewElementId = null;
                        secondaryActiveLearningUnitViewElementId = null;
                        sendElementActivatedEvent = true;
                    }
                    if (sendElementActivatedEvent) {
                        SwingUtilities.invokeLater(
                        new Runnable() {
                            public void run() {
                                learningUnitViewManager.fireLearningUnitViewEvent(FSLLearningUnitViewEvent.createElementActivatedEvent(learningUnitViewManager.getLearningUnitViewManagerId(),
                                activeLearningUnitViewElementId, secondaryActiveLearningUnitViewElementId, false));
                            }
                        });
                    }
                }
            }
        }
    }
}