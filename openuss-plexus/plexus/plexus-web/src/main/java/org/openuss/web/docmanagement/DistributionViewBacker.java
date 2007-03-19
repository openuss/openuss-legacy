package org.openuss.web.docmanagement;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

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
import org.openuss.docmanagement.DocManagementException;
import org.openuss.docmanagement.File;
import org.openuss.docmanagement.FileImpl;
import org.openuss.docmanagement.Folder;
import org.openuss.docmanagement.FolderImpl;
import org.openuss.docmanagement.NotAFileException;
import org.openuss.docmanagement.NotAFolderException;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.Resource;
import org.openuss.docmanagement.ResourceAlreadyExistsException;
import org.openuss.docmanagement.DocConstants;

@Bean(name="distributionViewBacker", scope=Scope.SESSION)
@View
public class DistributionViewBacker extends ExceptionHandler{

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
	public ArrayList<FileTableEntry> data;

	/**
	 * String which saves what should happen when linked files are deleted
	 */
	public String deleteLinks;
	
		
	/**
	 * @return treeModel displayed by tree2 component
	 */
	public TreeModel getTree(){
		//TODO cache treeModel to prevent loading model 5 times a pageload
		Folder folder = new FolderImpl();
		//TODO change to getMainFolder(currentEnrollment.getId());
		try {
			folder = distributionService.getMainFolder(null);
		} catch (NotAFolderException e) {
			handleNotAFolderException(e);
		} catch (PathNotFoundException e) {
			handlePathNotFoundException(e);
		} catch (ResourceAlreadyExistsException e) {
			handleResourceAlreadyExistsException(e);
		} catch (NotAFileException e) {
			handleNotAFileException(e);
		} catch (DocManagementException e) {
			handleDocManagementException(e);
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
		String path = getFolderPath();
		if (path.startsWith("/")) path = path.substring(1);			
		try {
			folderController.setFolder(distributionService.getFolder(path));
		} catch (NotAFolderException e) {
			handleNotAFolderException(e);
		} catch (PathNotFoundException e) {
			handlePathNotFoundException(e);
		} catch (ResourceAlreadyExistsException e) {
			handleResourceAlreadyExistsException(e);
		} catch (NotAFileException e) {
			handleNotAFileException(e);
		} catch (DocManagementException e) {
			handleDocManagementException(e);
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
		String path = this.data.get((new Integer(getFileFacesPath())).intValue()).getPath();
		if (path.startsWith("/")) path = path.substring(1);
		BigFile bigFile = new BigFileImpl();
		try {
			File file = distributionService.getFile(path);
			bigFile = distributionService.getFile(file);
		} catch (NotAFolderException e) {
			handleNotAFolderException(e);
		} catch (PathNotFoundException e) {
			handlePathNotFoundException(e);
		} catch (ResourceAlreadyExistsException e) {
			handleResourceAlreadyExistsException(e);
		} catch (NotAFileException e) {
			handleNotAFileException(e);
		} catch (DocManagementException e) {
			handleDocManagementException(e);
		}		

		fileController.setFile(bigFile);
		fileController.setOld(true);
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
	public ArrayList<FileTableEntry> getData() {
		logger.debug("generating file table entries ");		
		ArrayList<FileTableEntry> al = new ArrayList<FileTableEntry>();
		String path = getFolderPath();		
		if (path!=null){
			Folder folder = new FolderImpl();
			if (path.startsWith("/")) path = path.substring(1);
			try {
				folder = distributionService.getFolder(path);
			} catch (NotAFolderException e) {
				handleNotAFolderException(e);
			} catch (PathNotFoundException e) {
				handlePathNotFoundException(e);
			} catch (ResourceAlreadyExistsException e) {
				handleResourceAlreadyExistsException(e);
			} catch (NotAFileException e) {
				handleNotAFileException(e);
			} catch (DocManagementException e) {
				handleDocManagementException(e);
			}		

			Collection subnodes = folder.getSubnodes();
			File f;			
			//check if folder has subnodes			
			if (subnodes!=null){
				Iterator nodeIterator = subnodes.iterator();
				while (nodeIterator.hasNext()){
					Resource r = (Resource) nodeIterator.next();
					if (r instanceof File) {
						FileTableEntry fte = new FileTableEntry();
						f = (File) r;
						fte.setCreated(f.getCreated());
						fte.setDistributionTime(f.getDistributionTime());
						fte.setId(f.getId());
						fte.setLastModification(f.getLastModification());
						fte.setLength(f.getLength());
						fte.setMessage(f.getMessage());
						fte.setMimeType(f.getMimeType());
						fte.setName(f.getName());
						fte.setPath(f.getPath());
						fte.setPredecessor(f.getPredecessor());
						fte.setVersion(f.getVersion());
						fte.setVisibility(f.getVisibility());					
						al.add((FileTableEntry)fte);					
					}
				}
			}
		}
		logger.debug("file table entries generated");
		this.data = al;
		return al;

	}

	/**
	 * Action methods to trigger download
	 * @return 
	 */
	public String download(){
		String path = this.data.get((new Integer(getFileFacesPath())).intValue()).getPath();
		if (path.startsWith("/")) path = path.substring(1);
		
		BigFile bigFile = new BigFileImpl();
		try {
			File file = distributionService.getFile(path);
			bigFile = distributionService.getFile(file);
		} catch (NotAFolderException e) {
			handleNotAFolderException(e);
		} catch (PathNotFoundException e) {
			handlePathNotFoundException(e);
		} catch (ResourceAlreadyExistsException e) {
			handleResourceAlreadyExistsException(e);
		} catch (NotAFileException e) {
			handleNotAFileException(e);
		} catch (DocManagementException e) {
			handleDocManagementException(e);
		}		

	
		FacesContext context = FacesContext.getCurrentInstance();
	    HttpServletResponse response = 
		         (HttpServletResponse) context.getExternalContext().getResponse();
	    String filePath = null;
	    int read = 0;
	    byte[] bytes = new byte[1024];

	    response.setContentType(bigFile.getMimeType());
	    response.setHeader("Content-Disposition", "attachment;filename=\"" +
		         bigFile.getName() + "\""); 
	    OutputStream os = null;	      
        try {
			os = response.getOutputStream();        
			while((read = bigFile.getFile().read(bytes)) != -1){
			   os.write(bytes,0,read);
			}
			os.flush();
			os.close();
		} catch (IOException e) {
			logger.error("IOException: ", e);
		}
        FacesContext.getCurrentInstance().responseComplete();
		return DocConstants.DOCUMENTEXPLORER;
	}
	
	/**
	 * convenience method, which converts a FileTableEntry object into an File object
	 * @param fte FileTableEntry
	 * @return
	 */
	private File fileTableEntry2File(FileTableEntry fte){
		return new FileImpl(
				fte.getDistributionTime(),
				fte.getId(),
				fte.getLastModification(),
				fte.getLength(),
				fte.getMessage(),
				fte.getMimeType(),
				fte.getName(),
				fte.getPath(),
				fte.getPredecessor(),
				fte.getVersion(),
				fte.getVisibility());
	}
		
	
	/**
	 * convenience method, which converts a FileTableEntry object into an BigFile object
	 * @param fte FileTableEntry
	 * @return
	 */
	private BigFile fileTableEntry2BigFile(FileTableEntry fte){
		File f = new FileImpl(
				fte.getDistributionTime(),
				fte.getId(),
				fte.getLastModification(),
				fte.getLength(),
				fte.getMessage(),
				fte.getMimeType(),
				fte.getName(),
				fte.getPath(),
				fte.getPredecessor(),
				fte.getVersion(),
				fte.getVisibility());
		try {
			return distributionService.getFile(f);
		} catch (NotAFolderException e) {
			handleNotAFolderException(e);
		} catch (PathNotFoundException e) {
			handlePathNotFoundException(e);
		} catch (ResourceAlreadyExistsException e) {
			handleResourceAlreadyExistsException(e);
		} catch (NotAFileException e) {
			handleNotAFileException(e);
		} catch (DocManagementException e) {
			handleDocManagementException(e);
		}		
		return null;
	}
	
	//TODO move to service method
	/**
	 * Action method which starts the download of selected files in one zip-file
	 * @return
	 */
	public String downloadSelected(){
		String zipName = getFolderPath().substring(getFolderPath().lastIndexOf("/")+1)+".zip";
		ArrayList<BigFile> files = new ArrayList<BigFile>();
		Iterator i = this.data.iterator();
		FileTableEntry fte;
		while (i.hasNext()){
			fte = (FileTableEntry)i.next();
			if (fte.isChecked()) files.add(fileTableEntry2BigFile(fte));
		}	
		InputStream in = ZipMe.zipMe(files.toArray(new BigFile[0]), zipName);
		
		FacesContext context = FacesContext.getCurrentInstance();
	    HttpServletResponse response = 
		         (HttpServletResponse) context.getExternalContext().getResponse();
	    String filePath = null;
	    int read = 0;
	    byte[] bytes = new byte[1024];
	    response.setContentType(DocConstants.MIMETYPE_ZIP);	    
	    response.setHeader("Content-Disposition", "attachment;filename=\"" +
		         zipName + "\""); 
	    OutputStream os = null;	      
        try {
			os = response.getOutputStream();        
			while((read = in.read(bytes)) != -1){
			   os.write(bytes,0,read);
			}
			os.flush();
			os.close();
		} catch (IOException e) {
			logger.error("IOException: ", e);
		}
        FacesContext.getCurrentInstance().responseComplete();
        
        try {
			in.close();
		} catch (IOException e) {
			logger.error("IOException: ",e);
		}
        //delete zipFile
        boolean success = (new java.io.File(zipName)).delete();
        if (!success) {
            // deletion failed
        	logger.error("temporary file could not be deleted!");
        }
        return DocConstants.DOCUMENTEXPLORER;
	}
	
	/**
	 * action method, which triggers the deletion of selected files
	 * @return
	 */
	public String confirmedDelete(){
		Iterator i = this.data.iterator();
		FileTableEntry fte;
		while (i.hasNext()){
			fte = (FileTableEntry)i.next();
			if (fte.isChecked())
				try {
					distributionService.delFile(fileTableEntry2File(fte), deleteLinks.equals(DocConstants.DELETE_LINKS));
				} catch (NotAFolderException e) {
					handleNotAFolderException(e);
				} catch (PathNotFoundException e) {
					handlePathNotFoundException(e);
				} catch (ResourceAlreadyExistsException e) {
					handleResourceAlreadyExistsException(e);
				} catch (NotAFileException e) {
					handleNotAFileException(e);
				} catch (DocManagementException e) {
					handleDocManagementException(e);
				}		
		}	
		
		return DocConstants.DOCUMENTEXPLORER;
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

	public String getDeleteLinks() {
		if (deleteLinks==null) return DocConstants.DELETE_LINKS;
		return deleteLinks;
	}

	public void setDeleteLinks(String deleteLinks) {
		this.deleteLinks = deleteLinks;
	}
		

}

	
	
