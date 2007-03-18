package org.openuss.web.docmanagement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.myfaces.custom.tree2.TreeModel;
import org.apache.myfaces.custom.tree2.TreeModelBase;
import org.apache.myfaces.custom.tree2.TreeNode;
import org.apache.myfaces.custom.tree2.TreeNodeBase;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.View;
import org.openuss.docmanagement.BigFile;
import org.openuss.docmanagement.BigFileImpl;
import org.openuss.docmanagement.DistributionService;
import org.openuss.docmanagement.DistributionServiceImpl;
import org.openuss.docmanagement.File;
import org.openuss.docmanagement.FileImpl;
import org.openuss.docmanagement.Folder;
import org.openuss.docmanagement.FolderImpl;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.Resource;
import org.openuss.docmanagement.ResourceAlreadyExistsException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.docmanagement.DocConstants;

@Bean(name="distributionViewBacker", scope=Scope.SESSION)
@View
public class DistributionViewBacker{

	@Property(value="#{distributionService}")
	public DistributionService distributionService;

	@Property(value="#{fileController}")
	public FileController fileController;
	
	@Property(value="#{folderController}")
	public FolderController folderController;

	private static final Logger logger = Logger.getLogger(DistributionViewBacker.class);

	//path of current selected item in treeModel
	public String facesPath;
	
	//path of current selected item in repository
	public String path;
	
	//current TreeModel displayed by tree2 component
	public TreeModel treeModel;

	//type of current selected item
	public int type;
	
	public TreeModel getTree(){
		//TODO cache treeModel to prevent loading model 5 times a pageload
		Folder folder = new FolderImpl();
		try {
			folder = distributionService.getMainFolder(null);
		} catch (PathNotFoundException e) {
			logger.error("Path not found");
		} catch (ResourceAlreadyExistsException e) {
			logger.error("Resource already exists");
		}
		this.treeModel = new TreeModelBase(folder2TreeNodeBase(folder));  
		return 	this.treeModel;
	}
	
	private TreeNodeBase folder2TreeNodeBase(Folder folder){
		TreeNodeBase tn = new TreeNodeBase("folder", folder.getName(), folder.getPath(), (folder.getSubnodes()==null));
		Folder subFolder = null;
		File subFile = null;
		if (folder.getSubnodes()!=null){
			Collection<Resource> v = folder.getSubnodes();
			Iterator i = v.iterator();
			while (i.hasNext()){
				Object o = i.next();
				//TODO add links
				subFolder = null; subFile = null; 
				if (o instanceof Folder) {
					subFolder = (Folder) o;					
				}
				if (o instanceof File) {
					subFile = (File) o;					
				}
				if (subFolder!=null) tn.getChildren().add(folder2TreeNodeBase(subFolder));
				if (subFile!=null) tn.getChildren().add(file2TreeNodeBase(subFile));

			}
		}
		return tn;		
	}
	
	private TreeNodeBase file2TreeNodeBase(File subFile) {
		TreeNodeBase tn = new TreeNodeBase("file", subFile.getName(), subFile.getPath(), true);
		return tn;
	}

	

	public String addTestStructure(){
		try{
			((DistributionServiceImpl)distributionService).buildTestStructure();
		} catch (Exception e){
			logger.error(e);
		}
		return DocConstants.DOCUMENTEXPLORER;
	}

	public String clearRepository(){
		try{
			((DistributionServiceImpl)distributionService).clearRepository();
		} catch (Exception e){
			logger.error(e);
		}
		return DocConstants.DOCUMENTEXPLORER;
	}
	
	
	public String changeFolder(){
		try {
			folderController.setFolder(distributionService.getFolder(treeModel.getNodeById(this.facesPath).getIdentifier().substring(1)));
		} catch (PathNotFoundException e) {
			logger.error("Path not found: ", e);
		} catch (ResourceAlreadyExistsException e) {
			logger.error("Rsource already exists: ", e);		
		}
		return DocConstants.EDITFOLDER;
	}
	
	public String newFolder(){
		Folder folder = new FolderImpl();
		folder.setPath(this.path.substring(1));
		folderController.setFolder(folder);
		return DocConstants.EDITFOLDER;
	}
	
	public String changeFile(){
		try {
			String path = treeModel.getNodeById(this.facesPath).getIdentifier();
			if (path.startsWith("/")) path = path.substring(1);
			File file = distributionService.getFile(path);
			BigFile bigFile = distributionService.getFile(file);
			fileController.setFile(bigFile);
			//fileController.setFile(distributionService.getFile(distributionService.getFile(treeModel.getNodeById(this.facesPath).getIdentifier().substring(1))));
			fileController.setOld(true);
		} catch (PathNotFoundException e) {
			logger.error("Path not found: ", e);
		} catch (ResourceAlreadyExistsException e) {
			logger.error("Rsource already exists: ", e);		
		}		
		return DocConstants.NEWDOCUMENTTOFOLDER;
	}
	
	public String newFile(){
		BigFile bf = new BigFileImpl();
		String path = this.path;
		if (path.startsWith("/")) path = path.substring(1);
		bf.setPath(path);
		fileController.setFile(bf);
		fileController.setOld(false);
		return DocConstants.NEWDOCUMENTTOFOLDER;	
	}
	
	public DistributionService getDistributionService() {
		return distributionService;
	}

	public void setDistributionService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}

	public ArrayList getData() {
		logger.debug("generating file table entries ");
		ArrayList<File> al = new ArrayList<File>();
		if (path!=null){
			Folder folder = new FolderImpl();
			String path = this.path;
			if (path.startsWith("/")) path = path.substring(1);
			try {
				folder = distributionService.getFolder(path);
			} catch (PathNotFoundException e) {
				logger.error("PathNotFound, e");
			} catch (ResourceAlreadyExistsException e) {
				logger.error("", e);
			}
			Collection subnodes = folder.getSubnodes();
			//check if folder has subnodes
			if (subnodes!=null){
				Iterator nodeIterator = subnodes.iterator();
				while (nodeIterator.hasNext()){
					Resource r = (Resource) nodeIterator.next();
					if (r instanceof File) {
						al.add((File)r);					
					}
				}
			}
		}
		logger.debug("file table entries generated");
		return al;

	}

	public String getPath() {		
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFacesPath() {
		return facesPath;
	}

	public void setFacesPath(String facesPath) {
		this.path = treeModel.getNodeById(facesPath).getIdentifier();
		this.type = 0;
		if (treeModel.getNodeById(facesPath).getType().equals("folder")) this.type=1;
		if (treeModel.getNodeById(facesPath).getType().equals("file")) this.type=2;		
		logger.debug("Path is now: "+this.path);
		this.facesPath = facesPath;
	}

	public FileController getFileController() {
		return fileController;
	}

	public void setFileController(FileController fileController) {
		this.fileController = fileController;
	}

	public FolderController getFolderController() {
		return folderController;
	}

	public void setFolderController(FolderController folderController) {
		this.folderController = folderController;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}	
}

	
	
