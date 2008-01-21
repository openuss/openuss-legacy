package freestyleLearning.learningUnitViewAPI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import freestyleLearning.homeCore.learningUnitsManager.FSLLearningUnitsManager;
import freestyleLearning.homeCore.learningUnitsManager.data.xmlBindingSubclasses.FSLLearningUnitDescriptor;
import freestyleLearning.homeCore.learningUnitsManager.data.xmlBindingSubclasses.FSLLearningUnitsDescriptor;
import freestyleLearning.homeCore.learningUnitsManager.dialogs.FSLLearningUnitSynchronizeLoginDialog;
import freestyleLearningGroup.independent.gui.FLGColumnLayout;
import freestyleLearningGroup.independent.gui.FLGDialogInputVerifier;
import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.util.FLGInternationalization;

/**
 * Dialog for synchronizing local learning unit views with views on server. 
 * @author Carsten Fiedler
 */
public class FSLLearningUnitViewSynchronizeManager implements FLGDialogInputVerifier {
	private FLGInternationalization internationalization;
	private FSLLearningUnitsManager learningUnitsManager;
	private FSLLearningUnitViewManager learningUnitViewManager;
	private FSLLearningUnitViewOpenUSSWebServiceClient webServiceClient;
	// elements manager for local installed elements
	private FSLLearningUnitViewElementsManager learningUnitViewElementsManager;
	// elements manager from server (import, export) or for elements from fslv-file (import) 
	private FSLLearningUnitViewElementsManager serverElementsManager;
	private boolean viewImport;
	private boolean localSynchronisation;
	private boolean importStructure = false;
	private JPanel mainPanel;
	private JPanel legendPanel;
	private DefaultTreeModel serverStructureTreeModel;
	private DefaultTreeModel localStructureTreeModel;
	private ArrayList selectionPathList_local = new ArrayList();
	private ArrayList selectionPathList_server = new ArrayList();
	private TreePanel localTreePanel;
	private TreePanel serverTreePanel;

	/**
	 * Constructor.
	 * @param <code>FSLLearningUnitsManager</code> learningUnitsManager
	 * @param <code>FSLLearningUnitViewManager</code> learningUnitViewManager
	 * @param <code>FSLLearningUnitViewElementsManager</code> learningUnitViewElementsManager
	 * @param <code>FSLLearningUnitViewOpenUSSWebServiceClient</code> webServiceClient
	 */
	public FSLLearningUnitViewSynchronizeManager(FSLLearningUnitsManager learningUnitsManager, FSLLearningUnitViewManager learningUnitViewManager,
	        FSLLearningUnitViewElementsManager learningUnitViewElementsManager, FSLLearningUnitViewOpenUSSWebServiceClient webServiceClient) {
    	internationalization = new FLGInternationalization("freestyleLearning.learningUnitViewAPI.internationalization",
    			FSLLearningUnitSynchronizeLoginDialog.class.getClassLoader());
    	this.learningUnitViewManager = learningUnitViewManager;
    	this.learningUnitViewElementsManager = learningUnitViewElementsManager;
    	this.learningUnitsManager = learningUnitsManager;
    	this.webServiceClient = webServiceClient;
	}
	
	/**
     * Inits synchronization type. 
     * Possibilities:
     * - local import
     * - server import
     * - server export 
     * @param <code>String</code> path from local view to import, if server import: path = null
     * @param <code>boolean</code> importView = true if user clicks import, else false for export
     * @param <code>boolean</code> localView = true if local import/export, else false for server import/export 
     * @return <code>boolean</code> true, if user clicked "ok"
     */
    public boolean showSynchronizeManagerDialog(String localPathToContentsXmlFile, boolean importView, boolean localSynchronisation) {
      	this.viewImport = importView;
      	this.localSynchronisation = localSynchronisation;
      	int returnValue = 0;
      	if(importView) {
      		// ----- IMPORT -----
      		if(localSynchronisation) {
	      		// local fslv file import
	      		buildElementsStructureFromFslvFile(localPathToContentsXmlFile);
	      	} else {
	      		// server import
	       		buildElementsStructureFromServer();
	      	}
	    	buildIndependentUI();
	    	returnValue = FLGOptionPane.showConfirmDialog(this, new JScrollPane(mainPanel), internationalization.getString("synchronizeDialog.import.title"),
	        		FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE);
    	} else {
    		// ----- EXPORT -----
    		buildElementsStructureFromServer();
    		buildIndependentUI();
    		returnValue = FLGOptionPane.showConfirmDialog(this, new JScrollPane(mainPanel), internationalization.getString("synchronizeDialog.export.title"),
	    			FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE);
    	}
      	return returnValue == FLGOptionPane.OK_OPTION;
    }
    
	private void buildIndependentUI() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		legendPanel = new JPanel();
		legendPanel.setBorder(BorderFactory.createTitledBorder("Legende"));
		legendPanel.setLayout(new FLGColumnLayout());
		JPanel redPanel = new JPanel();
		redPanel.setBackground(Color.RED);
		redPanel.setSize(new Dimension(5,5));
		legendPanel.add(redPanel, FLGColumnLayout.LEFT);
		
		//legendPanel.add(new JLabel("zu überschreibende Elemente"), FLGColumnLayout.LEFTEND);
		legendPanel.add(new JLabel(internationalization.getString("synchronizeDialog.legend.elementsToOverwrite")), 
				FLGColumnLayout.LEFTEND);
		
		JPanel blackPanel = new JPanel();
		blackPanel.setBackground(Color.BLACK);
		blackPanel.setSize(new Dimension(6,6));
		legendPanel.add(blackPanel, FLGColumnLayout.LEFT);
		
		//legendPanel.add(new JLabel("aktuelle Elemente"), FLGColumnLayout.LEFTEND);
		legendPanel.add(new JLabel(internationalization.getString("synchronizeDialog.legend.elementsUpToDate")), 
				FLGColumnLayout.LEFTEND);
		
		mainPanel.add(legendPanel, BorderLayout.SOUTH);
		
		buildTreeModels();
		
		// create local tree panel
		localTreePanel = new TreePanel(internationalization.getString("synchronizeDialog.localTreePanel.headline"), true);
		
	    // create server tree panel
      	// if local import
      	if (viewImport && localSynchronisation) {
      		serverTreePanel = new TreePanel(
    				internationalization.getString("synchronizeDialog.serverTreePanel.localImport.headline"), false);
      	} 
      	// if server import
      	if(viewImport && !localSynchronisation) {
      		serverTreePanel = new TreePanel(
    				internationalization.getString("synchronizeDialog.serverTreePanel.serverImport.headline") 
    					+ " " + getServerName(), false);
      	}
      	// if server export
      	if(!viewImport && !localSynchronisation) {
			serverTreePanel = new TreePanel(
					internationalization.getString("synchronizeDialog.serverTreePanel.serverExport.headline") 
					+ " " + getServerName(), false);
      	}
      				
		// insert button for importing / exporting elements structure
		JButton commitButton = null;
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		if (!viewImport){
			commitButton = new JButton(new ImageIcon(
					getClass().getClassLoader().getResource("freestyleLearning/learningUnitViewAPI/images/exportStructure.gif")));
		} else {
			commitButton = new JButton(new ImageIcon(
					getClass().getClassLoader().getResource("freestyleLearning/learningUnitViewAPI/images/importStructure.gif")));
		}
		commitButton.setPreferredSize(new Dimension(50,50));
		commitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				synchonizeStructures();
			}
		}); 
		buttonPanel.add(commitButton);
		
		// add panels into  main panel
		mainPanel.add(localTreePanel, BorderLayout.WEST);
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		mainPanel.add(serverTreePanel, BorderLayout.EAST);
	}
	
	public boolean getImportStructure() {
		return importStructure;
	}
	
	private void synchonizeStructures() {
		importStructure = true;
		if(viewImport) {
			localTreePanel.updateTreeModel(serverStructureTreeModel);
		} else {
			serverTreePanel.updateTreeModel(localStructureTreeModel);
		}
	}
	
    // builds element structure from contents.xml on server
    private void buildElementsStructureFromServer() {
    	FSLLearningUnitsDescriptor learningUnitsDescriptor = learningUnitsManager.getLearningUnitsDescriptor();
		FSLLearningUnitDescriptor learningUnitDescriptor = learningUnitsDescriptor.getDescriptorById(learningUnitsManager.getActiveLearningUnitId());
		// invoke web service client for downloading context.xml
		File contentsXmlFile = webServiceClient.downloadContentsXmlFile(learningUnitDescriptor.getEnrollmentId(), 
    			learningUnitsManager.getActiveLearningUnitId(), learningUnitViewManager.getLearningUnitViewOriginalDataDirectory().getName());
		// read contents.xml
		StringBuffer fileInfo = new StringBuffer();
		try {
			FileReader reader = new FileReader(contentsXmlFile);
			for(int i=0; i<5; i++) {
				int readedInt = reader.read();
				if(readedInt == -1){
					break;
				}
				fileInfo.append((char) readedInt);
			}
			reader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(!fileInfo.toString().equals("dummy")) { 
			// create elements manager for server elements
			FSLLearningUnitViewXMLDocument document = learningUnitViewManager.loadLearningUnitViewXMLDocument(contentsXmlFile);
				serverElementsManager = learningUnitViewManager.createElementsManagerFromXMLDocuments(
						document, document, new File(learningUnitsManager.getLearningUnitsDirectory().getAbsolutePath() + "\\"),
							new File(learningUnitsManager.getLearningUnitsDirectory().getAbsolutePath() + "\\"));
		}
		// delete temporary contents.xml in learning unit directory
		contentsXmlFile.delete();
    }
    
    // builds element structure from contents.xml in fslv file
    private void buildElementsStructureFromFslvFile(String pathToFslvFile) {
    	File fslvFile = new File(pathToFslvFile);
    	try {
    		final File inputFile = fslvFile;
	        if (inputFile!=null) {
	            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(inputFile));
	            ZipEntry entry = zipInputStream.getNextEntry();
	            while (entry!=null) {
	            	if(entry.getName().equals("contents.xml")) {
	            		FileOutputStream out = new FileOutputStream(learningUnitsManager.getLearningUnitsDirectory().getAbsolutePath() + "\\"
	                    		+ entry.getName());
	                    byte[] buf = new byte[4096];
		                int len;
		                while ((len = zipInputStream.read(buf)) > 0) {
		                	out.write(buf, 0, len);
		                }
		                out.close();
		                File contentsXMLFile = new File(learningUnitsManager.getLearningUnitsDirectory().getAbsolutePath() + "\\"
	                    		+ entry.getName());
		                FSLLearningUnitViewXMLDocument document = learningUnitViewManager.loadLearningUnitViewXMLDocument(contentsXMLFile);
		                serverElementsManager = learningUnitViewManager.createElementsManagerFromXMLDocuments(
		                		document, document, new File(learningUnitsManager.getLearningUnitsDirectory().getAbsolutePath() + "\\"),
		                        new File(learningUnitsManager.getLearningUnitsDirectory().getAbsolutePath() + "\\"));
		                contentsXMLFile.delete();
		                break;
                   	} else {
                   		zipInputStream.closeEntry();
		                entry = zipInputStream.getNextEntry();
	                }
	            }
	            zipInputStream.close();
	        }
    	} catch(Exception e) { e.printStackTrace(); }
    }
  
    private String getServerName() {
    	String serverName = null;
    	FSLLearningUnitsDescriptor learningUnitsDescriptor = learningUnitsManager.getLearningUnitsDescriptor();
    	FSLLearningUnitDescriptor learningUnitDescriptor = learningUnitsDescriptor.getDescriptorById(learningUnitsManager.getActiveLearningUnitId());
    	serverName = learningUnitDescriptor.getOpenUssServerName();
        return serverName;
    }
    
    /**
     * Verifies user input.
     */
	public String verifyInput() {
		/**
		String errorString = "Error";
		return errorString;
		**/
		return null;
	}	

	public void buildTreeModels() {
		// build tree models
		DefaultMutableTreeNode root_local = new DefaultMutableTreeNode(new Integer(0));
		DefaultMutableTreeNode root_server = new DefaultMutableTreeNode(new Integer(0));
	    localStructureTreeModel = new DefaultTreeModel(root_local);
	    serverStructureTreeModel = new DefaultTreeModel(root_server);
	    
	    // build local tree and synchronize server tree colors
		if (learningUnitViewElementsManager != null && serverElementsManager != null) {
			String[] topLevelElementIds_local = learningUnitViewElementsManager.getTopLevelLearningUnitViewElementsIds();
			String[] topLevelElementIds_server = serverElementsManager.getTopLevelLearningUnitViewElementsIds();
		    int end = 0;
			if(topLevelElementIds_server.length == topLevelElementIds_local.length) {
				// same element size
				end = topLevelElementIds_local.length;
			    insertHighLevelNodes(root_local, root_server, topLevelElementIds_local, topLevelElementIds_server, end);
			}
			if(topLevelElementIds_server.length > topLevelElementIds_local.length) {
				// more server elements
			    end = topLevelElementIds_local.length;
			    insertHighLevelNodes(root_local, root_server, topLevelElementIds_local, topLevelElementIds_server, end);
			    // add additional server elements
			    for (int i = end; i < topLevelElementIds_server.length; i++) {
			    	FSLLearningUnitViewElement element_server = serverElementsManager.getLearningUnitViewElement(topLevelElementIds_server[i], false);
					if (viewImport) {
						// paint server elements black because they do not exist on local installation
				        DefaultMutableTreeNode node_server = new DefaultMutableTreeNode(new TreeElement(element_server, 0));
				        root_server.add(node_server);
		           		//TreeNode[] path_server = serverStructureTreeModel.getPathToRoot(node_server);
		           		//selectionPathList_server.add(new TreePath(path_server));
			            addChildNodes(null, node_server, null, topLevelElementIds_server[i], null, serverElementsManager);
				     } else {
				     	// paint server elements red because will be overwritten
				    	 DefaultMutableTreeNode node_server = new DefaultMutableTreeNode(new TreeElement(element_server, 2));
				         root_server.add(node_server);
				         //TreeNode[] path_server = serverStructureTreeModel.getPathToRoot(node_server);
			           	 //selectionPathList_server.add(new TreePath(path_server));
				         addChildNodes(null, node_server, null, topLevelElementIds_server[i], null, serverElementsManager);
				     }
			    }
			}
			if(topLevelElementIds_server.length < topLevelElementIds_local.length){
				// more local elements
			    end = topLevelElementIds_server.length;
			    insertHighLevelNodes(root_local, root_server, topLevelElementIds_local, topLevelElementIds_server, end);
			    // add additional local elements
			    for (int i = end; i < topLevelElementIds_local.length; i++) {
			    	FSLLearningUnitViewElement element_local = learningUnitViewElementsManager.getLearningUnitViewElement(topLevelElementIds_local[i], false);
					if (viewImport) {
						// paint local elements red because they will be overwritten
				        DefaultMutableTreeNode node_local = new DefaultMutableTreeNode(new TreeElement(element_local, 2));
				        root_local.add(node_local);
				        //TreeNode[] path_local = localStructureTreeModel.getPathToRoot(node_local);
			           	//selectionPathList_local.add(new TreePath(path_local));
			            addChildNodes(node_local, null, topLevelElementIds_local[i], null, learningUnitViewElementsManager, null);
				     } else {
				     	// paint local elements black, because the do not exist on server
				    	 DefaultMutableTreeNode node_local = new DefaultMutableTreeNode(new TreeElement(element_local, 0));
				         root_local.add(node_local);
				         //TreeNode[] path_local = localStructureTreeModel.getPathToRoot(node_local);
				         //selectionPathList_local.add(new TreePath(path_local));
				         addChildNodes(node_local, null, topLevelElementIds_local[i], null, learningUnitViewElementsManager, null);
				     }
			    }
			}
		} 
		
		if (learningUnitViewElementsManager == null && serverElementsManager != null) {
			// no local elements
			if(viewImport) {
				addChildNodes(root_server, null, serverElementsManager, 0, false);
			} else {
				addChildNodes(root_server, null, serverElementsManager, 2, false);
			}
		}
		if (learningUnitViewElementsManager != null && serverElementsManager == null) {
			// no server elements
			if(viewImport) {
				addChildNodes(root_local, null, learningUnitViewElementsManager, 2, true);
			} else {
				addChildNodes(root_local, null, learningUnitViewElementsManager, 0, true);
			}
		}
		
	}
	
	private void insertHighLevelNodes(DefaultMutableTreeNode root_local, DefaultMutableTreeNode root_server, 
			String[] topLevelElementIds_local, String[] topLevelElementIds_server, int end) {
		for (int i = 0; i < end; i++) {
	    	FSLLearningUnitViewElement element_local = learningUnitViewElementsManager.getLearningUnitViewElement(topLevelElementIds_local[i], false);
            FSLLearningUnitViewElement element_server = serverElementsManager.getLearningUnitViewElement(topLevelElementIds_server[i], false);
            DefaultMutableTreeNode node_local = null;
            DefaultMutableTreeNode node_server = null;
            try {
            	// get dates to compare
	            Date date_server = new Date();
	            date_server.setTime(Long.parseLong(element_server.getLastModificationDate()));
	            Date date_local = new Date();
	            date_local.setTime(Long.parseLong(element_local.getLastModificationDate()));
	            if (viewImport) {
	            	// set elements and colors for import
		            if(element_server.getId().equals(element_local.getId())) {
		            	if(date_local.after(date_server)) {
		            		// local element is newer, paint it red because it will overwritten
		            		node_local = new DefaultMutableTreeNode(new TreeElement(element_local, 2));
		            		// paint server element black
		            		node_server = new DefaultMutableTreeNode(new TreeElement(element_server, 0));
		            	}
		            	if(date_local.before(date_server)) { 
		            		// local element is older, paint it red because it will overwritten
		            		node_local = new DefaultMutableTreeNode(new TreeElement(element_local, 2));
		            		// paint server element black
		            		node_server = new DefaultMutableTreeNode(new TreeElement(element_server, 0));
		            	}
		            	if(date_local.compareTo(date_server) == 0) {
		            		// elements fit 
		            		// paint local element grey
		            		node_local = new DefaultMutableTreeNode(new TreeElement(element_local, 1));
		            		// paint server element grey
		            		node_server = new DefaultMutableTreeNode(new TreeElement(element_server, 1));
			            }
		                // add nodes to roots
		                root_local.add(node_local);
		                root_server.add(node_server);
		                // children
		                addChildNodes(node_local, node_server, topLevelElementIds_local[i], topLevelElementIds_server[i], learningUnitViewElementsManager, serverElementsManager);
		            } else {
		            	// insert 2 separate element structures because 
		            	// local element -> red
		            	node_local = new DefaultMutableTreeNode(new TreeElement(element_local, 2));
		            	root_local.add(node_local);
		            	addChildNodes(node_local, topLevelElementIds_local[i], learningUnitViewElementsManager, 2, true);
		            	// server element -> black
		            	node_server = new DefaultMutableTreeNode(new TreeElement(element_server, 0));
		            	root_server.add(node_server);
		            	addChildNodes(node_server, topLevelElementIds_server[i], serverElementsManager, 0, false);
		            }
	            }
                if(!viewImport) {
                	// set elements and colors for export
                	if(element_server.getId().equals(element_local.getId())) {
		            	if(date_local.after(date_server)) {
		            		// local element is newer, paint it black
		            		node_local = new DefaultMutableTreeNode(new TreeElement(element_local, 0));
		            		// paint server element red because it will be overwritten
		            		node_server = new DefaultMutableTreeNode(new TreeElement(element_server, 2));
		            	}
		            	if(date_local.before(date_server)) { 
		            		// local element is older, paint it black
		            		node_local = new DefaultMutableTreeNode(new TreeElement(element_local, 2));
		            		// paint server element red because it will be overwritten
		            		node_server = new DefaultMutableTreeNode(new TreeElement(element_server, 0));
		            	}
		            	if(date_local.compareTo(date_server) == 0) {
		            		// elements fit 
		            		// paint local element grey
		            		node_local = new DefaultMutableTreeNode(new TreeElement(element_local, 1));
		            		// paint server element grey
		            		node_server = new DefaultMutableTreeNode(new TreeElement(element_server, 1));
			            }
		            	// add nodes to roots
		            	root_local.add(node_local);
		            	root_server.add(node_server);
		            	// children
		            	addChildNodes(node_local, node_server, topLevelElementIds_local[i], topLevelElementIds_server[i], learningUnitViewElementsManager, serverElementsManager);
                	} else {
		            	// insert 2 separate element structures
		            	// local element -> black
		            	node_local = new DefaultMutableTreeNode(new TreeElement(element_local, 0));
		            	root_local.add(node_local);
		            	addChildNodes(node_local, topLevelElementIds_local[i], learningUnitViewElementsManager, 0, true);
		            	// server element -> red
		            	node_server = new DefaultMutableTreeNode(new TreeElement(element_server, 2));
		            	root_server.add(node_server);
		            	addChildNodes(node_server, topLevelElementIds_server[i], serverElementsManager, 2, false);
                	}
	            }
            } catch(Exception parseExp) {
	            parseExp.printStackTrace();
	        }

        }
	}
	
    private void addChildNodes(DefaultMutableTreeNode parent_local, DefaultMutableTreeNode parent_server, String elementId_local, String elementId_server, 
    		FSLLearningUnitViewElementsManager elementsManager_local, FSLLearningUnitViewElementsManager elementsManager_server) {
    	if (viewImport) {
    		if (elementsManager_local != null && elementsManager_server != null) {
    			String[] childrenIds_local = elementsManager_local.getChildrenIdsOfLearningUnitViewElement(elementId_local);
    			String[] childrenIds_server = elementsManager_server.getChildrenIdsOfLearningUnitViewElement(elementId_server);
		        int end = 0;
		        if(childrenIds_server.length < childrenIds_local.length) {
		        	end = childrenIds_server.length;
		        } else {
		        	end = childrenIds_local.length;
		        }
		        for (int i = 0; i < end; i++) {
	            	// get elements
	            	FSLLearningUnitViewElement element_local = elementsManager_local.getLearningUnitViewElement(childrenIds_local[i], false);
	            	FSLLearningUnitViewElement element_server = elementsManager_server.getLearningUnitViewElement(childrenIds_server[i], false);
	            	// create nodes
	            	DefaultMutableTreeNode childNode_local = null;
	            	DefaultMutableTreeNode childNode_server = null;
	            	try {
			       		// get dates to compare
				        Date date_server = new Date();
				        date_server.setTime(Long.parseLong(element_server.getLastModificationDate()));
				        Date date_local = new Date();
				        date_local.setTime(Long.parseLong(element_local.getLastModificationDate()));
				        // compare dates
				        if(element_server.getId().equals(element_local.getId())) {
				        	if(date_local.after(date_server)) {
				           		// local element is newer, paint it red, it will be overwritten
				           		childNode_local = new DefaultMutableTreeNode(new TreeElement(element_local, 2));
				           		// paint server element black
				           		childNode_server = new DefaultMutableTreeNode(new TreeElement(element_server, 0));
				           		// open branch with elements which will be overwritten
				           		TreeNode[] path_local = localStructureTreeModel.getPathToRoot(childNode_local);
				           		selectionPathList_local.add(new TreePath(path_local));
				           		// open server brach with black elements 
				           		TreeNode[] path_server = serverStructureTreeModel.getPathToRoot(childNode_server);
				           		selectionPathList_server.add(new TreePath(path_server));
				          	}
				           	if(date_local.before(date_server)) { 
				           		// local element is older, paint it red, it will be overwritten
				           		childNode_local = new DefaultMutableTreeNode(new TreeElement(element_local, 2));
				           		// paint server element black
				           		childNode_server = new DefaultMutableTreeNode(new TreeElement(element_server, 0));
				           		// open branch with elements which will be overwritten
				           		TreeNode[] path_local = localStructureTreeModel.getPathToRoot(childNode_local);
				           		selectionPathList_local.add(new TreePath(path_local));
				           		// open server brach with black elements 
				           		TreeNode[] path_server = serverStructureTreeModel.getPathToRoot(childNode_server);
				           		selectionPathList_server.add(new TreePath(path_server));
				           	}
				           	if(date_local.compareTo(date_server) == 0) {
				           		// elements fit
				           		// paint local element grey
				           		childNode_local = new DefaultMutableTreeNode(new TreeElement(element_local, 1));
				           		// paint server element grey
				           		childNode_server = new DefaultMutableTreeNode(new TreeElement(element_server, 1));
				           	}
				           	// add nodes
					       	parent_local.add(childNode_local);
					       	parent_server.add(childNode_server);
					       	addChildNodes(childNode_local, childNode_server, childrenIds_local[i], childrenIds_server[i], elementsManager_local, elementsManager_server);
				        } else {
				        	// insert 2 separate element structures
			            	// local element -> red
				        	childNode_local = new DefaultMutableTreeNode(new TreeElement(element_local, 2));
				        	parent_local.add(childNode_local);
				        	TreeNode[] path_local = localStructureTreeModel.getPathToRoot(childNode_local);
			           		selectionPathList_local.add(new TreePath(path_local));
			            	addChildNodes(childNode_local, childrenIds_local[i], elementsManager_local, 2, true);
			            	// server element -> black
			            	childNode_server = new DefaultMutableTreeNode(new TreeElement(element_server, 0));
			            	parent_server.add(childNode_server);
			            	addChildNodes(childNode_server, childrenIds_server[i], elementsManager_server, 0, false);
			            	TreeNode[] path_server = serverStructureTreeModel.getPathToRoot(childNode_server);
			           		selectionPathList_server.add(new TreePath(path_server));
				        }
			       	} catch(Exception parseExp) {
			       		parseExp.printStackTrace();
				    }
	            }
		        if(childrenIds_server.length > childrenIds_local.length) {
	            	// more server elements, add them
	            	for (int i = end; i < childrenIds_server.length; i++) {
	            		FSLLearningUnitViewElement element_server = elementsManager_server.getLearningUnitViewElement(childrenIds_server[i], false);
	            		// import, paint server element black
	            		DefaultMutableTreeNode childNode_server = childNode_server = new DefaultMutableTreeNode(new TreeElement(element_server, 0));
	            		// open branch to show new elements
	            		TreeNode[] path_server = serverStructureTreeModel.getPathToRoot(childNode_server);
	            		selectionPathList_server.add(new TreePath(path_server));
	            		// add node to parent
	            		parent_server.add(childNode_server);
	            		// check children
	            		addChildNodes(null, childNode_server, null, childrenIds_server[i], null, elementsManager_server);
	            	}
	            }
	            if(childrenIds_local.length > childrenIds_server.length) {
	            	// more local elements, add them
	            	for (int i = end; i < childrenIds_local.length; i++) {
	            		FSLLearningUnitViewElement element_local = elementsManager_local.getLearningUnitViewElement(childrenIds_local[i], false);
	            		// import, paint server element red
	            		DefaultMutableTreeNode childNode_local = childNode_local = new DefaultMutableTreeNode(new TreeElement(element_local, 2));
	            		// open branch to show elements which will be overwritten
	            		TreeNode[] path_local = localStructureTreeModel.getPathToRoot(childNode_local);
	            		selectionPathList_local.add(new TreePath(path_local));
	            		// add node to parent
	            		parent_local.add(childNode_local);
	            		// check children
	            		addChildNodes(childNode_local, null, childrenIds_local[i], null, elementsManager_local, null);
	            	}
	            }
    		} 
	        if (elementsManager_local == null) {
	        	// no local elements, insert black server elements
	        	addChildNodes(parent_server, elementId_server, elementsManager_server, 0, false);
	        }
	        if (elementsManager_server == null) {
	        	// no server elements, insert red local elements
	        	addChildNodes(parent_local, elementId_local, elementsManager_local, 2, true);
	        } 
    	} else {
    		// -------------- view export --------------------- 
    		if (elementsManager_local != null && elementsManager_server != null) {
    			String[] childrenIds_local = elementsManager_local.getChildrenIdsOfLearningUnitViewElement(elementId_local);
    			String[] childrenIds_server = elementsManager_server.getChildrenIdsOfLearningUnitViewElement(elementId_server);
		        int end = 0;
		        if(childrenIds_server.length < childrenIds_local.length) {
		        	end = childrenIds_server.length;
		        } else {
		        	end = childrenIds_local.length;
		        }
		        for (int i = 0; i < end; i++) {
	            	// get elements
	            	FSLLearningUnitViewElement element_local = elementsManager_local.getLearningUnitViewElement(childrenIds_local[i], false);
	            	FSLLearningUnitViewElement element_server = elementsManager_server.getLearningUnitViewElement(childrenIds_server[i], false);
	            	// create nodes
	            	DefaultMutableTreeNode childNode_local = null;
	            	DefaultMutableTreeNode childNode_server = null;
	            	try {
			       		// get dates to compare
				        Date date_server = new Date();
				        date_server.setTime(Long.parseLong(element_server.getLastModificationDate()));
				        Date date_local = new Date();
				        date_local.setTime(Long.parseLong(element_local.getLastModificationDate()));
				        // compare dates
				        if(element_server.getId().equals(element_local.getId())) {
				        	if(date_local.after(date_server)) {
				           		// local element is newer, paint it black
				           		childNode_local = new DefaultMutableTreeNode(new TreeElement(element_local, 0));
				           		// paint server element red because it will be overwritten
				           		childNode_server = new DefaultMutableTreeNode(new TreeElement(element_server, 2));
				           		// open local branch
				           		TreeNode[] path_local = localStructureTreeModel.getPathToRoot(childNode_local);
				           		selectionPathList_local.add(new TreePath(path_local));
				           		// open server brach  
				           		TreeNode[] path_server = serverStructureTreeModel.getPathToRoot(childNode_server);
				           		selectionPathList_server.add(new TreePath(path_server));
				          	}
				           	if(date_local.before(date_server)) { 
				           		// local element is older, paint it black
				           		childNode_local = new DefaultMutableTreeNode(new TreeElement(element_local, 0));
				           		// paint server element red because it will be overwritten
				           		childNode_server = new DefaultMutableTreeNode(new TreeElement(element_server, 2));
				           		// open local tree branch
				           		TreeNode[] path_local = localStructureTreeModel.getPathToRoot(childNode_local);
				           		selectionPathList_local.add(new TreePath(path_local));
				           		// open server tree brach  
				           		TreeNode[] path_server = serverStructureTreeModel.getPathToRoot(childNode_server);
				           		selectionPathList_server.add(new TreePath(path_server));
				           	}
				           	if(date_local.compareTo(date_server) == 0) {
				           		// elements fit
				           		// paint local element grey
				           		childNode_local = new DefaultMutableTreeNode(new TreeElement(element_local, 1));
				           		// paint server element grey
				           		childNode_server = new DefaultMutableTreeNode(new TreeElement(element_server, 1));
				           	}
				           	// add nodes
					       	parent_local.add(childNode_local);
					       	parent_server.add(childNode_server);
					       	addChildNodes(childNode_local, childNode_server, childrenIds_local[i], childrenIds_server[i], elementsManager_local, elementsManager_server);	
				        } else {
				        	// insert 2 separate element structures
			            	// local element -> black
				        	childNode_local = new DefaultMutableTreeNode(new TreeElement(element_local, 0));
				        	parent_local.add(childNode_local);
				        	TreeNode[] path_local = localStructureTreeModel.getPathToRoot(childNode_local);
			           		selectionPathList_local.add(new TreePath(path_local));
			            	addChildNodes(childNode_local, childrenIds_local[i], elementsManager_local, 0, true);
			            	// server element -> red
			            	childNode_server = new DefaultMutableTreeNode(new TreeElement(element_server, 2));
			            	parent_server.add(childNode_server);
			            	TreeNode[] path_server = serverStructureTreeModel.getPathToRoot(childNode_server);
			           		selectionPathList_server.add(new TreePath(path_server));
			            	addChildNodes(childNode_server, childrenIds_server[i], elementsManager_server, 2, false);
				        }
			       	} catch(Exception parseExp) {
			       		parseExp.printStackTrace();
				    }
		        }
		        if(childrenIds_server.length > childrenIds_local.length) {
	            	// more server elements, add them
	            	for (int i = end; i < childrenIds_server.length; i++) {
	            		FSLLearningUnitViewElement element_server = elementsManager_server.getLearningUnitViewElement(childrenIds_server[i], false);
	            		// export, paint server element red
	            		DefaultMutableTreeNode childNode_server = childNode_server = new DefaultMutableTreeNode(new TreeElement(element_server, 2));
	            		// open branch 
	            		TreeNode[] path_server = serverStructureTreeModel.getPathToRoot(childNode_server);
	            		selectionPathList_server.add(new TreePath(path_server));
	            		// add node to parent
	            		parent_server.add(childNode_server);
	            		// check children
	            		addChildNodes(null, childNode_server, null, childrenIds_server[i], null, elementsManager_server);
	            	}
	            }
	            if(childrenIds_local.length > childrenIds_server.length) {
	            	// more local elements, add them
	            	for (int i = end; i < childrenIds_local.length; i++) {
	            		FSLLearningUnitViewElement element_local = elementsManager_local.getLearningUnitViewElement(childrenIds_local[i], false);
	            		// export, paint local element black 
	            		DefaultMutableTreeNode childNode_local = childNode_local = new DefaultMutableTreeNode(new TreeElement(element_local, 0));
	            		// open branch 
	            		TreeNode[] path_local = localStructureTreeModel.getPathToRoot(childNode_local);
	            		selectionPathList_local.add(new TreePath(path_local));
	            		// add node to parent
	            		parent_local.add(childNode_local);
	            		// check children
	            		addChildNodes(childNode_local, null, childrenIds_local[i], null, elementsManager_local, null);
	            	}
	            }
    		} 
	        if (elementsManager_local == null) {
	        	// no local elements, insert red server elements
	        	addChildNodes(parent_server, elementId_server, elementsManager_server, 2, false);
	        }
	        if (elementsManager_server == null) {
	        	// no server elements, insert black local elements
	        	addChildNodes(parent_local, elementId_local, elementsManager_local, 0, true);
	        } 
    	}
    }
    
	// set default color for elements if no server or local elements exist 
	private void addChildNodes(DefaultMutableTreeNode parent, String elementId, FSLLearningUnitViewElementsManager elementsManager, int status, boolean isLocal) {
		String[] childrenIds;
		if(elementId != null) {
			childrenIds = elementsManager.getChildrenIdsOfLearningUnitViewElement(elementId);
		} else {
			childrenIds = elementsManager.getTopLevelLearningUnitViewElementsIds();
		}
		for (int i = 0; i < childrenIds.length; i++) {
			FSLLearningUnitViewElement element = elementsManager.getLearningUnitViewElement(childrenIds[i], false);
			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new TreeElement(element, status));
			parent.add(childNode);
			if (isLocal) {
        		TreeNode[] path_local = localStructureTreeModel.getPathToRoot(childNode);
        		selectionPathList_local.add(new TreePath(path_local));
			} else {
        		TreeNode[] path_server = serverStructureTreeModel.getPathToRoot(childNode);
        		selectionPathList_server.add(new TreePath(path_server));
			}
			addChildNodes(childNode, childrenIds[i], elementsManager, status, isLocal);
		}
	}
    
	public class TreePanel extends JPanel {
		private JTree structureTree;
		
		public TreePanel(String title, boolean local) {
			setBorder(BorderFactory.createTitledBorder(title));
			setLayout(new BorderLayout());
			if(local) {
				structureTree = new JTree(localStructureTreeModel);
				TreePath[] paths = new TreePath[selectionPathList_local.size()];
				for (int i = 0; i < selectionPathList_local.size(); i++) {
					TreePath path = (TreePath) selectionPathList_local.get(i);
					paths[i] = path;
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) path.getLastPathComponent();
					if (treeNode.getParent()!=null) {
						TreeNode[] path_local = localStructureTreeModel.getPathToRoot(treeNode.getPreviousNode()); 
						structureTree.setSelectionPath(new TreePath(path_local));
					}
				}
				structureTree.setSelectionPaths(paths);
			} else {
				structureTree = new JTree(serverStructureTreeModel);
				TreePath[] paths = new TreePath[selectionPathList_server.size()];
				for (int i = 0; i < selectionPathList_server.size(); i++) {
					TreePath path = (TreePath) selectionPathList_server.get(i);
					paths[i] = path;
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) path.getLastPathComponent();
					if (treeNode.getParent()!=null) {
						TreeNode[] path_local = serverStructureTreeModel.getPathToRoot(treeNode.getPreviousNode()); 
						structureTree.setSelectionPath(new TreePath(path_local));
					}
				}
				structureTree.setSelectionPaths(paths);
			}
			
			structureTree.setRootVisible(false);
			structureTree.setEditable(false);
			structureTree.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			structureTree.setCellRenderer(new FSLTreeCellRenderer());
			
			JScrollPane scrollPane = new JScrollPane(structureTree);
			scrollPane.setPreferredSize(new Dimension(400,400));
			add(scrollPane, BorderLayout.CENTER);
		}
		
		public void updateTreeModel(DefaultTreeModel model) {
			structureTree.setModel(model);
		}
	}
	
	public class TreeElement {
		FSLLearningUnitViewElement viewElement;
		// O: black -> new element
		// 1: grey -> element is up to date
		// 2: red -> deleted element 
		int status;
		
		public TreeElement(FSLLearningUnitViewElement viewElement, int status) {
			this.status = status;
			this.viewElement = viewElement;
		}
		
		void setStatus(int status) {
			this.status = status;
		}
		
		void setViewElement(FSLLearningUnitViewElement viewElement) {
			this.viewElement = viewElement;
		}
		
		int getStatus() {
			return status;
		}
		
		FSLLearningUnitViewElement getViewElement() {
			return viewElement;
		}
	}
	
	class FSLTreeCellRenderer extends DefaultTreeCellRenderer {
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
            boolean leaf, int row, boolean hasFocus) {
        	if (value!=null) {
        		DefaultMutableTreeNode elementNode = (DefaultMutableTreeNode)value;
        		try{
	          		TreeElement treeElement = ((TreeElement)elementNode.getUserObject());
	        		if (treeElement.getStatus()==0) {
		        		// grey -> element is up to date
		        		setTextNonSelectionColor(Color.BLACK);
		        		setBackgroundNonSelectionColor(null);
		        		setTextSelectionColor(Color.BLACK);
		        		setBackgroundSelectionColor(null);
		        	}
		        	if (treeElement.getStatus()==1) {
		        		// black for new element
		        		setTextNonSelectionColor(Color.LIGHT_GRAY);
		        		setBackgroundNonSelectionColor(null);
		        		setTextSelectionColor(Color.LIGHT_GRAY);
		        		setBackgroundSelectionColor(null);
		        	} 
		        	if (treeElement.getStatus()==2) {
		        		// red for deleted element
		        		setTextNonSelectionColor(Color.RED);
		        		setBackgroundNonSelectionColor(null);
		        		setTextSelectionColor(Color.RED);
		        		setBackgroundSelectionColor(null);
		        	}
		        	super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
	            	FSLLearningUnitViewElement element = treeElement.getViewElement();
	            	
	            	if(element.getLastModificationDate() != null) {
	            		if(treeElement.getStatus() == 0 || treeElement.getStatus() == 2) {
	            			Date date = new Date();
	            			date.setTime(Long.parseLong(element.getLastModificationDate()));
	                		String dateString = date.toGMTString();
		            		setText(element.getTitle() + " (" + dateString + ")");
	            		} else {
	            			setText(element.getTitle());
	            		}
	            	} else {
	            		Date date = new Date();
	               		setText(element.getTitle() + " (" + date.toGMTString() + ")");
	            	}
	            	if (selected) {
	    				if (!element.getFolder()) {
	    					// selected element
	    					setIcon(new ImageIcon(getClass().getClassLoader().getResource("freestyleLearning/learningUnitViewAPI/images/treeNodeSelected.gif")));
	    				} else {
	    					// selected folder
	    					setIcon(new ImageIcon(getClass().getClassLoader().getResource("freestyleLearning/learningUnitViewAPI/images/treeNodeFolderSelected.gif")));
	    				}
	    			} else {
	    				if (!element.getFolder()) {
	    					// unselected element
	    					setIcon(new ImageIcon(getClass().getClassLoader().getResource("freestyleLearning/learningUnitViewAPI/images/treeNodeNotSelected.gif")));
	    				} else {
	    					// unselected folder
	    					setIcon(new ImageIcon(getClass().getClassLoader().getResource("freestyleLearning/learningUnitViewAPI/images/treeNodeFolderNotSelected.gif")));
	    				}
	    			}
        		} catch(ClassCastException e) {}
            }
        	return this;
        }
	}
}


