package org.openuss.web.docmanagement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.apache.myfaces.custom.tree2.TreeModel;
import org.apache.myfaces.custom.tree2.TreeModelBase;
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
import org.openuss.docmanagement.Folder;
import org.openuss.docmanagement.FolderImpl;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.Resource;
import org.openuss.docmanagement.ResourceAlreadyExistsException;
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

	/**
	 *	path of current selected folder in treeModel
	 */
	public String folderFacesPath;
	

	/**
	 * path of current selected folder in repository
	 */
	public String folderPath;
	
	/**
	 * Path of selected File in faces
	 */
	public String fileFacesPath;
	

	/**
	 * current TreeModel for tree2 component
	 */
	public TreeModel treeModel;
	
	
	/**
	 * list which contains the files displayed in curren folder
	 */
	public ArrayList<File> data;

	//type of current selected item
	
	/**
	 * @return treeModel displayed by tree2 component
	 */
	public TreeModel getTree(){
		//TODO cache treeModel to prevent loading model 5 times a pageload
		Folder folder = new FolderImpl();
		try {
			//TODO change to getMainFolder(currentEnrollment.getId());
			folder = distributionService.getMainFolder(null);
		} catch (PathNotFoundException e) {
			logger.error("Path not found");
		} catch (ResourceAlreadyExistsException e) {
			logger.error("Resource already exists");
		}
		//change model only if model has changed
		if (treeModel!=null){
			if (!this.treeModel.equals(new TreeModelBase(folder2TreeNodeBase(folder))))this.treeModel = new TreeModelBase(folder2TreeNodeBase(folder));
		} else if(treeModel==null) this.treeModel = new TreeModelBase(folder2TreeNodeBase(folder));
		return 	this.treeModel;
	}
	
	/**
	 *  converts Folder object to TreeNodeBase object 
	 * @param folder Folder object to be converted
	 * @return TreeNodeBase object as result of conversion
	 */
	private TreeNodeBase folder2TreeNodeBase(Folder folder){
		if (folder==null) return new TreeNodeBase();
		TreeNodeBase tn = new TreeNodeBase("folder", folder.getName(), folder.getPath(), (folder.getSubnodes()==null));
		Folder subFolder = null;
		if (folder.getSubnodes()!=null){
			Collection<Resource> v = folder.getSubnodes();
			Iterator i = v.iterator();
			while (i.hasNext()){
				Object o = i.next();
				//TODO add links
				subFolder = null; 
				if (o instanceof Folder) {
					subFolder = (Folder) o;					
				}	
				if (subFolder!=null) tn.getChildren().add(folder2TreeNodeBase(subFolder));
				}
		}
		return tn;		
	}
	

	/**
	 * only for testing purposes 
	 * @return
	 */	
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
	
	
	/**
	 * Action method called if change folder button is clicked
	 * @return
	 */
	public String changeFolder(){
		try {
			String path = getFolderPath();
			if (path.startsWith("/")) path = path.substring(1);			
			folderController.setFolder(distributionService.getFolder(path));
		} catch (PathNotFoundException e) {
			logger.error("Path not found: ", e);
		} catch (ResourceAlreadyExistsException e) {
			logger.error("Rsource already exists: ", e);		
		}
		return DocConstants.EDITFOLDER;
	}
	
	/**
	 * Action method called if new folder button is clicked
	 * @return
	 */
	public String newFolder(){
		Folder folder = new FolderImpl();
		String path = getFolderPath();
		if (path.startsWith("/")) path = path.substring(1);	
		folder.setPath(path);
		folderController.setFolder(folder);
		return DocConstants.EDITFOLDER;
	}
	
	/**
	 * action method called if change file button is clicked
	 * @return
	 */
	public String changeFile(){
		try {
			
			String path = this.data.get((new Integer(getFileFacesPath())).intValue()).getPath();
			if (path.startsWith("/")) path = path.substring(1);
			File file = distributionService.getFile(path);
			BigFile bigFile = distributionService.getFile(file);
			fileController.setFile(bigFile);
			fileController.setOld(true);
		} catch (PathNotFoundException e) {
			logger.error("Path not found: ", e);
		} catch (ResourceAlreadyExistsException e) {
			logger.error("Rsource already exists: ", e);		
		}	
		return DocConstants.NEWDOCUMENTTOFOLDER;
	}
	
	/**
	 * action method called if new file button is clicked
	 * @return
	 */
	public String newFile(){
		BigFile bf = new BigFileImpl();
		String path = getFolderPath();
		if (path.startsWith("/")) path = path.substring(1);
		bf.setPath(path);
		fileController.setFile(bf);
		fileController.setOld(false);
		return DocConstants.NEWDOCUMENTTOFOLDER;	
	}
	

	/** 
	 * @return ArrayList, which contains all files of current selected folder for display in dataTable
	 */
	public ArrayList<File> getData() {
		logger.debug("generating file table entries ");
		ArrayList<File> al = new ArrayList<File>();
		String path = getFolderPath();		
		if (path!=null){
			Folder folder = new FolderImpl();
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
		this.data = al;
		return al;

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

	public String getFolderFacesPath() {
		return folderFacesPath;
	}

	public void setFolderFacesPath(String folderFacesPath) {
		setFolderPath(treeModel.getNodeById(folderFacesPath).getIdentifier());
		this.folderFacesPath = folderFacesPath;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}	

	public DistributionService getDistributionService() {
		return distributionService;
	}
	
	public void setDistributionService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}

	public String getFileFacesPath() {
		return fileFacesPath;
	}

	public void setFileFacesPath(String fileFacesPath) {
		this.fileFacesPath = fileFacesPath;
	}
}

	
	
