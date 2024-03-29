/* Generated by Freestyle Learning Group */

package freestyleLearning.homeCore.learningUnitsManager.learningUnitsStructureTree;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.tree.*;

import freestyleLearning.homeCore.learningUnitsManager.FSLLearningUnitsManager;
import freestyleLearning.homeCore.learningUnitsManager.learningUnitsStructureTree.dialogs.FSLLearningUnitsStructureTreeMoveElementDialog;
import freestyleLearning.homeCore.learningUnitsManager.data.xmlBindingSubclasses.FSLLearningUnitDescriptor;
import freestyleLearning.homeCore.learningUnitsManager.data.xmlBindingSubclasses.FSLLearningUnitsDescriptor;
import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.util.FLGInternationalization;

/**
 * @author Carsten Fiedler
 * Carsten Fiedler modified 01.09.2005
 */
public abstract class FSLAbstractLearningUnitsStructureTree {
	protected FSLLearningUnitsDescriptor learningUnitsDescriptor;
    protected static FLGInternationalization internationalization;
    protected FSLLearningUnitsManager learningUnitsManager;
    protected FSLLearningUnitsStructureTreeMoveElementDialog moveElementDialog;
    protected JTree structureTree; 
    protected DefaultTreeModel structureTreeModel;
    protected DefaultMutableTreeNode root;
    protected DefaultMutableTreeNode selectedNode = null;
    protected DefaultMutableTreeNode targetNode;
    protected TreePath selectionPath;
    protected JPopupMenu popup;
    protected FSLLearningUnitDescriptor selectedLearningUnitDescriptor;
    protected FSLLearningUnitsStructureTreePanel learningUnitsStructureTreePanel;
    protected String dragSourceElementId;
    protected String dropAllowedTargetElementId = null;
    protected String dropNotAllowedTargetElementId = null;
    protected String elementId = null;
    protected boolean dragging;
    protected boolean insertChildIsAllowed;
    
    FSLAbstractLearningUnitsStructureTree(FSLLearningUnitsStructureTreePanel learningUnitsStructureTreePanel,FSLLearningUnitsDescriptor learningUnitsDescriptor, FSLLearningUnitsManager learningUnitsManager) {
    	internationalization = new FLGInternationalization("freestyleLearning.homeCore.learningUnitsManager.learningUnitsStructureTree.internationalization",FSLLearningUnitsStructureTree.class.getClassLoader());
    	this.learningUnitsStructureTreePanel = learningUnitsStructureTreePanel;
    	this.learningUnitsDescriptor = learningUnitsDescriptor;
    	this.learningUnitsManager = learningUnitsManager;
    	selectedNode = null;
    	dropAllowedTargetElementId = null;
    	dropNotAllowedTargetElementId = null;
    	elementId = null;
    	buildSructureTreeFromFSLLearningUnitsDescriptor(learningUnitsDescriptor);
     	initPopupMenu();
    }
    
    public void buildSructureTreeFromFSLLearningUnitsDescriptor(FSLLearningUnitsDescriptor learningUnitsDescriptor) {
    	/***************************************************
    	 * insert code for building specific structure tree 
    	 ***************************************************/
      	structureTree = new JTree(structureTreeModel);
    	structureTree.setRootVisible(false);
    	structureTree.setEditable(false);
    }
    
    public JTree getStructureTree() {
    	return structureTree;
    }

    public FSLLearningUnitDescriptor getSelectedLearningUnit() {
    	return selectedLearningUnitDescriptor;
    }

    /*
     * Method initializes popup menu with language and registers ActionListener.
     * Default menu items:
     * - new
     * - rename
     * - remove 
     * Inner Class popupActionListener has to be defined. 
     */
    public void initPopupMenu() {
      	PopupActionListener popupActionListener = new PopupActionListener();
        popup = new JPopupMenu();
        JMenuItem mi;
        // add new
        mi = new JMenuItem("new");
        mi.setFont(new Font("SansSerif", Font.PLAIN, 16));
        mi.addActionListener(popupActionListener);
        popup.add(mi);
        // rename 
        mi = new JMenuItem(internationalization.getString("rename"));
        mi.setFont(new Font("SansSerif", Font.PLAIN, 16));
        mi.addActionListener(popupActionListener);
        popup.add(mi);
        // remove
        mi = new JMenuItem(internationalization.getString("remove"));
        mi.setFont(new Font("SansSerif", Font.PLAIN, 16));
        mi.addActionListener(popupActionListener);
        popup.add(mi);
        popup.setOpaque(true);
    }
    
    private void moveNode(int selection) {
    	FSLLearningUnitDescriptor dragSource = (FSLLearningUnitDescriptor) selectedNode.getUserObject();
    	
    	if (selection==0) {
    		// add sibling to root
    		
    		/*** update learning units descriptor ***/
           	learningUnitsDescriptor.getLearningUnitsDescriptors().remove(dragSource);
           	// if dragSource has parent, remove parentId
           	if (dragSource.getParentID()!=null || dragSource.getParentID()!="") {
           		dragSource.setParentID(null);
           	}
           	learningUnitsDescriptor.getLearningUnitsDescriptors().add(dragSource);
           	learningUnitsDescriptor.setModified(true);
			learningUnitsManager.saveLearningUnitsDescriptor(learningUnitsDescriptor);
           	
    		/*** update tree **/
           	learningUnitsStructureTreePanel.updateTree(learningUnitsDescriptor);
        }
    	
    	if (selection==1) {
    		// create sibling before target
    		
    		/*** update learning units descriptor ***/
    		learningUnitsDescriptor.getLearningUnitsDescriptors().remove(dragSource);
    		java.util.List learningUnitDescriptorList = learningUnitsDescriptor.getLearningUnitsDescriptors();
    		Iterator iter = learningUnitDescriptorList.iterator();
    		Vector descVector = new Vector();
    		while (iter.hasNext()) {
           		FSLLearningUnitDescriptor learningUnitDescriptor = (FSLLearningUnitDescriptor) iter.next();
           		if (learningUnitDescriptor==(FSLLearningUnitDescriptor)targetNode.getUserObject()) {
           			// set parent id
           			dragSource.setParentID(learningUnitDescriptor.getParentID());
           			// add target and following descriptors into vector
           			descVector.add(learningUnitDescriptor);
           			while (iter.hasNext()) {
           				learningUnitDescriptor = (FSLLearningUnitDescriptor)iter.next();
           				descVector.add(learningUnitDescriptor);
           			}
           		}
           	}
    		// remove target and all following descriptors
			iter = descVector.iterator();
			while (iter.hasNext()) {
				FSLLearningUnitDescriptor learningUnitDescriptor = (FSLLearningUnitDescriptor) iter.next();
				learningUnitsDescriptor.getLearningUnitsDescriptors().remove(learningUnitDescriptor);	
			}
    		// add dragSource
           	learningUnitsDescriptor.getLearningUnitsDescriptors().add(dragSource);
           	// add following descriptors from vector
           	iter = descVector.iterator();
           	while (iter.hasNext()) {
           		learningUnitsDescriptor.getLearningUnitsDescriptors().add((FSLLearningUnitDescriptor)iter.next());
           		learningUnitsDescriptor.setModified(true);
				learningUnitsManager.saveLearningUnitsDescriptor(learningUnitsDescriptor);
           	}
   		
           	/*** update tree ***/
           	learningUnitsStructureTreePanel.updateTree(learningUnitsDescriptor);
		}
    		
    	if (selection==2) {
    		// create sibling behind target
    		
    		/*** update learning units descriptor ***/
    		learningUnitsDescriptor.getLearningUnitsDescriptors().remove(dragSource);
    		java.util.List learningUnitDescriptorList = learningUnitsDescriptor.getLearningUnitsDescriptors();
    		Iterator iter = learningUnitDescriptorList.iterator();
    		Vector descVector = new Vector();
    		while (iter.hasNext()) {
    			FSLLearningUnitDescriptor learningUnitDescriptor = (FSLLearningUnitDescriptor) iter.next();
           		if (learningUnitDescriptor==(FSLLearningUnitDescriptor)targetNode.getUserObject()) {
           			// set parent id
           			dragSource.setParentID(learningUnitDescriptor.getParentID());
           			// store everything behind target
           			while (iter.hasNext()) {
           				learningUnitDescriptor = (FSLLearningUnitDescriptor)iter.next();
           				descVector.add(learningUnitDescriptor);
           			}
           		}
    		}
    		// remove everything behind target
    		iter = descVector.iterator();
			while (iter.hasNext()) {
				FSLLearningUnitDescriptor learningUnitDescriptor = (FSLLearningUnitDescriptor) iter.next();
				learningUnitsDescriptor.getLearningUnitsDescriptors().remove(learningUnitDescriptor);	
			}
    		// add drag source
			learningUnitsDescriptor.getLearningUnitsDescriptors().add(dragSource);
			// sotre following descriptors
			iter = descVector.iterator();
			while (iter.hasNext()) {
				FSLLearningUnitDescriptor learningUnitDescriptor = (FSLLearningUnitDescriptor) iter.next();
				learningUnitsDescriptor.getLearningUnitsDescriptors().add(learningUnitDescriptor);
				learningUnitsDescriptor.setModified(true);
				learningUnitsManager.saveLearningUnitsDescriptor(learningUnitsDescriptor);
			}
						
    		/*** update tree ***/
           	learningUnitsStructureTreePanel.updateTree(learningUnitsDescriptor);
    	}
         
    	if (selection==3) {
    		// create child to target
    		
    		/*** update learning units descriptor ***/
    		learningUnitsDescriptor.getLearningUnitsDescriptors().remove(dragSource);
    		java.util.List learningUnitDescriptorList = learningUnitsDescriptor.getLearningUnitsDescriptors();
    		Iterator iter = learningUnitDescriptorList.iterator();
    		Vector descVector = new Vector();
    		while (iter.hasNext()) {
    			FSLLearningUnitDescriptor learningUnitDescriptor = (FSLLearningUnitDescriptor) iter.next();
           		if (learningUnitDescriptor==(FSLLearningUnitDescriptor)targetNode.getUserObject()) {
           			// set parent id
           			dragSource.setParentID(learningUnitDescriptor.getId());
           			// store everything behind target
           			while (iter.hasNext()) {
           				learningUnitDescriptor = (FSLLearningUnitDescriptor)iter.next();
           				descVector.add(learningUnitDescriptor);
           			}
           		}
    		}
    		// remove everything behind target
    		iter = descVector.iterator();
			while (iter.hasNext()) {
				FSLLearningUnitDescriptor learningUnitDescriptor = (FSLLearningUnitDescriptor) iter.next();
				learningUnitsDescriptor.getLearningUnitsDescriptors().remove(learningUnitDescriptor);	
			}
			// add drag source
			learningUnitsDescriptor.getLearningUnitsDescriptors().add(dragSource);
			// sotre following descriptors
			iter = descVector.iterator();
			while (iter.hasNext()) {
				FSLLearningUnitDescriptor learningUnitDescriptor = (FSLLearningUnitDescriptor) iter.next();
				learningUnitsDescriptor.getLearningUnitsDescriptors().add(learningUnitDescriptor);
				learningUnitsDescriptor.setModified(true);
				learningUnitsManager.saveLearningUnitsDescriptor(learningUnitsDescriptor);
			}
						
    		/*** update tree ***/
           	learningUnitsStructureTreePanel.updateTree(learningUnitsDescriptor);
    	}
        selectedLearningUnitDescriptor=null;
    	initPopupMenu();
   }
    
    public void setSelectionPath(FSLLearningUnitDescriptor learningUnitDescriptor) {
    	// search learning unit descriptor and activate
    	Enumeration treeElements = root.breadthFirstEnumeration();
    	treeElements.nextElement();
    	while (treeElements.hasMoreElements()) {
    		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) treeElements.nextElement();
    		FSLLearningUnitDescriptor learnUnitDesc = (FSLLearningUnitDescriptor) (treeNode.getUserObject());
    		if (learningUnitDescriptor.getId().equals(learnUnitDesc.getId())) {
    			structureTree.setSelectionPath(new TreePath(treeNode.getPath()));
    			selectedLearningUnitDescriptor = learningUnitDescriptor;
    			selectedNode = treeNode;
    		}
    	}
    }
    
    /*
	 * Inner class for managing popup actions.
	 */
    class PopupActionListener implements ActionListener {
    	public void actionPerformed(ActionEvent ae) {
    		// new 
    		if (ae.getActionCommand().equals("new")) {
    			// do something
    		}
    		// rename
    		if (ae.getActionCommand().equals("rename")) {
               	// do something
            }
    		// remove
            if (ae.getActionCommand().equals("remove")) {
            	// do something
            }
        }
    }
    
    class FSLTreeCellRenderer extends DefaultTreeCellRenderer {
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
            boolean leaf, int row, boolean hasFocus) {
        	DefaultMutableTreeNode elementNode = (DefaultMutableTreeNode)value;
               	String elementNodeId = ((FSLLearningUnitDescriptor)elementNode.getUserObject()).getId();
               	// colors for normal unselected elements
            	setTextNonSelectionColor(Color.black);
            	setBackgroundNonSelectionColor(null);
            	// colors for normal selected elements
            	setTextSelectionColor(Color.white);
            	setBackgroundSelectionColor(new Color(50, 50, 122));
            	if (dragging) {
            		// colors for unselectable targetNode
            		if (dropNotAllowedTargetElementId!=null && elementNode==targetNode) {
            			setBackgroundNonSelectionColor(new Color(128, 0, 0));
            			setTextNonSelectionColor(Color.white);
            		}
            		// colors for selectable targetNode
            		if (dropNotAllowedTargetElementId==null && elementNode==targetNode && targetNode!=null) {
            			setBackgroundNonSelectionColor(Color.yellow);
            			setTextNonSelectionColor(Color.black);
            		}
            	}
            	super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
               	setText(value.toString());
               	// set Icons
            	// categories are represented with folders
            	// learning units are represented with closed documents
            	// first ceck, if node is category
            	List learningUnitDescriptorList = learningUnitsDescriptor.getLearningUnitsDescriptors();
            	Iterator iter = learningUnitDescriptorList.iterator();
            	while (iter.hasNext()) {
            		FSLLearningUnitDescriptor learningUnitDescriptor = (FSLLearningUnitDescriptor) iter.next();
            		if (elementNodeId.equals(learningUnitDescriptor.getId())
       					&& learningUnitDescriptor.getFolder()) {
            			if(selected) {
            				// category, load selected folder icon
            				setIcon(new ImageIcon(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/learningUnitsStructureTree/images/treeNodeFolderSelected.gif")));
            				break;
            			} else {
            				// category, load not selected folder icon
            				setIcon(new ImageIcon(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/learningUnitsStructureTree/images/treeNodeFolderNotSelected.gif")));
            				break;
            			}
            		} else if ((elementNodeId.equals(learningUnitDescriptor.getId())
       					&& !learningUnitDescriptor.getFolder())
						|| (elementNodeId.equals(learningUnitDescriptor.getId())
						&& !learningUnitDescriptor.getFolder())) {
            			if (selected) {
            				// learning unit, load selected document icon
            				setIcon(new ImageIcon(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/learningUnitsStructureTree/images/treeNodeSelected.gif")));
            			} else {
            				// learning unit, load not selected document icon
            				setIcon(new ImageIcon(getClass().getClassLoader().getResource("freestyleLearning/homeCore/learningUnitsManager/learningUnitsStructureTree/images/treeNodeNotSelected.gif")));
            			}
            		}
            	}
            	return this;
			}
    }

    class TreeMouseDragAndDropListener implements MouseMotionListener {
    	public void mouseDragged(MouseEvent e) {
    		insertChildIsAllowed=false;
      		if (dragging) {
        		// dragNode / elementId is drag target
                if (selectionPath != null && structureTree.getPathForLocation(e.getX(), e.getY())!=null) {
                	TreePath dragPath = structureTree.getPathForLocation(e.getX(), e.getY());
                	elementId = ((FSLLearningUnitDescriptor) (((DefaultMutableTreeNode)dragPath.getLastPathComponent()).getUserObject())).getId();
                	targetNode = (DefaultMutableTreeNode)dragPath.getLastPathComponent();
                } else {
                	elementId = "root";
                	targetNode = root;
                }
                
                /*** drag resctrictions ***/
                
                // drag categories
                if (((FSLLearningUnitDescriptor)selectedNode.getUserObject()).getFolder()) {
                   	// ok folder	
                	if (elementId != dragSourceElementId
						&& targetNode!=root) {
                		// do not permit dragging on children and their children
                		Enumeration children = selectedNode.depthFirstEnumeration();
                		if (children!=null) {
                			while (children.hasMoreElements()) {
                				FSLLearningUnitDescriptor childDescriptor = (FSLLearningUnitDescriptor)((DefaultMutableTreeNode)children.nextElement()).getUserObject();
                				if (childDescriptor.getId().equals(elementId)) {
                					dropAllowedTargetElementId = null;
                					dropNotAllowedTargetElementId = elementId;
                					break;
                				} else {
                					// check if grand child 
                					dropAllowedTargetElementId = elementId;
                					dropNotAllowedTargetElementId = null;
                  					if (((FSLLearningUnitDescriptor)targetNode.getUserObject()).getFolder()) {
                          				insertChildIsAllowed=true;
                          			}
                   				}
                			}
                		} else {
                			dropAllowedTargetElementId = elementId;
        					dropNotAllowedTargetElementId = null;
        					if (((FSLLearningUnitDescriptor)targetNode.getUserObject()).getFolder()) {
                  				insertChildIsAllowed=true;
                       		}
                		}
                	}
   				
              	// drag into nirvana, under root
      			} else if (targetNode==root) {
      				dropAllowedTargetElementId = elementId;
   					dropNotAllowedTargetElementId = null;
   					insertChildIsAllowed=true;
   					

       			// drag learning units
              	} else if (!((FSLLearningUnitDescriptor)selectedNode.getUserObject()).getFolder()
              		&& elementId != dragSourceElementId
					&& targetNode!=root) {
              			dropAllowedTargetElementId = elementId;
              			dropNotAllowedTargetElementId = null;
              			if (((FSLLearningUnitDescriptor)targetNode.getUserObject()).getFolder()) {
              				insertChildIsAllowed=true;
              			}
               	}
                structureTree.repaint();
        	}
            Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
            structureTree.scrollRectToVisible(r);
     
        }
    	
    	public void mouseMoved(MouseEvent e) {
            // dragging = false;
        }
    }

    class TreeMouseAdapter extends MouseAdapter {
    	protected Enumeration children;
    	
    	public void mousePressed(MouseEvent e) {
    		if (structureTree.getLastSelectedPathComponent()!=null && e.getButton()==MouseEvent.BUTTON1) {
    			selectedNode = (DefaultMutableTreeNode) structureTree.getLastSelectedPathComponent();
     			selectionPath = structureTree.getLeadSelectionPath();
    			selectedLearningUnitDescriptor = (FSLLearningUnitDescriptor) selectedNode.getUserObject();
    			dragSourceElementId = ((FSLLearningUnitDescriptor) selectedNode.getUserObject()).getId();
    			structureTree.setEditable(false);
    			dragging = true;
    			if (e.getClickCount() == 2) {
    				if (!selectedLearningUnitDescriptor.getFolder()) {
    					// close option pane
    					learningUnitsStructureTreePanel.closeOptionPane();
    					// load learning unit
    					new Thread() {
    						public void run() {
    							learningUnitsManager.learningUnitSelected();
    						}
    					}.start();
    				}
    			}
    		}
        }


    	// drop node and delete it from old tree position
        public void mouseReleased(MouseEvent e) {
        	if (dragging) {
           		if (dropAllowedTargetElementId!= null && targetNode!=root) {
        			// open optionPane
        			moveElementDialog = new FSLLearningUnitsStructureTreeMoveElementDialog();
        			int returnVal = moveElementDialog.showDialog(insertChildIsAllowed);
        			if (moveElementDialog.getInsertCommand()==1 && returnVal==FLGOptionPane.OK_OPTION) {
        				// create sibling before target
        				moveNode(1);
        			} else if (moveElementDialog.getInsertCommand()==2 && returnVal==FLGOptionPane.OK_OPTION) {
        				// create sibling behind target
        				moveNode(2);
        			} else if (moveElementDialog.getInsertCommand()==3 && returnVal==FLGOptionPane.OK_OPTION) {
        				// create child
        				moveNode(3);
        			} 
        		} else if (targetNode==root) {
        			moveNode(0);
        		}
           		insertChildIsAllowed=false;
        	}
        	targetNode=null;
        	elementId=null;
        	selectionPath=null;
        	dragging = false;
            dragSourceElementId = null;
            dropAllowedTargetElementId = null;
            dropNotAllowedTargetElementId = null;
            structureTree.repaint();
      }
    }
}