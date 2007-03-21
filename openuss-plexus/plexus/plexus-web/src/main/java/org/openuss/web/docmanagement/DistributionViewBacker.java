package org.openuss.web.docmanagement;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.faces.context.FacesContext;

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
import org.openuss.docmanagement.DocManagementException;
import org.openuss.docmanagement.File;
import org.openuss.docmanagement.FileImpl;
import org.openuss.docmanagement.Folder;
import org.openuss.docmanagement.FolderImpl;
import org.openuss.docmanagement.Link;
import org.openuss.docmanagement.LinkImpl;
import org.openuss.docmanagement.NotAFileException;
import org.openuss.docmanagement.NotAFolderException;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.Resource;
import org.openuss.docmanagement.ResourceAlreadyExistsException;
import org.openuss.docmanagement.DocConstants;


@Bean(name="distributionViewBacker", scope=Scope.SESSION)
@View
public class DistributionViewBacker extends AbstractEnrollmentDocPage{
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
	 * String to save the path to a link, when it should be deleted
	 */
	private String savePathToLink;
	
	/**
	 * @return treeModel displayed by tree2 component
	 */
	public TreeModel getTree(){
		//TODO cache treeModel to prevent loading model 5 times a pageload
		Folder folder = new FolderImpl();
		try {
			folder = distributionService.getMainFolder(enrollment);
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
		if (!hasReadPermission(folder)) folder = null;
		if (folder==null) return new TreeNodeBase();
		TreeNodeBase tn = new TreeNodeBase("folder", folder.getName(), folder.getPath(), (folder.getSubnodes()==null));
		Folder subFolder = null;
		if (folder.getSubnodes()!=null){
			Collection<Resource> v = folder.getSubnodes();
			Iterator i = v.iterator();
			while (i.hasNext()){
				Object o = i.next();
				subFolder = null; 
				if (o instanceof Folder) {
					subFolder = (Folder) o;					
				}	
				if (subFolder!=null) {
					if (hasReadPermission(subFolder)){
						tn.getChildren().add(folder2TreeNodeBase(subFolder));
					}
				}
			}
		}
		return tn;		
	}
	
	/**
	 * Action method called if change folder button is clicked
	 * @return
	 */
	public String changeFolder(){
		String path = getFolderPath();
		if (path.startsWith("/")) path = path.substring(1);			
		try {
			Folder f = distributionService.getFolder(path);
			if (!hasWritePermission(f)){
				noPermission();
				return DocConstants.EDITFOLDER;
			}
			folderController.setFolder(f);
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
			if (!hasWritePermission(file)){
				noPermission();
				return DocConstants.NEWDOCUMENTTOFOLDER; 
			}
			bigFile = distributionService.getFile(file);
		} catch (NotAFolderException e) {
			handleNotAFolderException(e);
		} catch (PathNotFoundException e) {
			handlePathNotFoundException(e);
		} catch (ResourceAlreadyExistsException e) {
			handleResourceAlreadyExistsException(e);
		} catch (NotAFileException e) {
			return changeLink();			
		} catch (DocManagementException e) {
			handleDocManagementException(e);
		}		

		fileController.setFile(bigFile);
		fileController.setOld(true);
		fileController.setLink(false);
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
		try{
			Folder f = distributionService.getFolder(path);
			if (!hasWritePermission(f)){
				noPermission();
				return DocConstants.NEWDOCUMENTTOFOLDER;
			}
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
				if (!hasReadPermission(folder)){
					noPermission();
					return al;
				}
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

			addFiles(al, folder);
		}
		logger.debug("file table entries generated");
		this.data = al;
		return al;

	}

	/**
	 * convenience method to add files to arraylist
	 * @param al
	 * @param folder
	 */
	private void addFiles(ArrayList<FileTableEntry> al, Folder folder) {
		Collection subnodes = folder.getSubnodes();
		//check if folder has subnodes			
		if (subnodes!=null){
			Iterator nodeIterator = subnodes.iterator();
			while (nodeIterator.hasNext()){
				Resource r = (Resource) nodeIterator.next();
				if (hasReadPermission(r)) {
					if (r instanceof File) {
						FileTableEntry fte = file2FTE((File)r);
						al.add((FileTableEntry) fte);
					}
					if (r instanceof Link) {
						FileTableEntry fte = link2FTE((Link)r);
						al.add((FileTableEntry) fte);
					}
				}					
			}
		}
	}
	
	private FileTableEntry link2FTE(Link link){
		FileTableEntry fte = file2FTE((File)link.getTarget());
		fte.setPath(link.getPath());
		return fte;	
	}

	/**
	 * convenience method to change a File to a filetableentry
	 * @param r
	 * @return
	 */
	private FileTableEntry file2FTE(File f) {		
		FileTableEntry fte = new FileTableEntry();		
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
		return fte;
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
			if (!hasReadPermission(file)){
				noPermission();
				return DocConstants.DOCUMENTEXPLORER;
			}
			bigFile = distributionService.getFile(file);
		} catch (NotAFolderException e) {
			handleNotAFolderException(e);
		} catch (PathNotFoundException e) {
			handlePathNotFoundException(e);
		} catch (ResourceAlreadyExistsException e) {
			handleResourceAlreadyExistsException(e);
		} catch (NotAFileException e) {
			return downloadLink();
		} catch (DocManagementException e) {
			handleDocManagementException(e);
		}		
		triggerDownload(bigFile);     
		return DocConstants.DOCUMENTEXPLORER;
	}

	private void triggerDownload(BigFile bigFile) {
		FacesContext context = FacesContext.getCurrentInstance();
	    HttpServletResponse response = 
		         (HttpServletResponse) context.getExternalContext().getResponse();
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
	

	
	/**
	 * Action method which starts the download of selected files in one zip-file
	 * @return
	 */
	public String downloadSelected(){
		String zipName = getFolderPath().substring(getFolderPath().lastIndexOf("/")+1)+".zip";
		//get zipped file
		InputStream in = getZipFile(zipName);	
		if (in==null) handleDocManagementException(new DocManagementException("error zipping files"));
		//trigger download of zipped file
		triggerDownload(zipName, in);
        
		//delete zipFile
        try {
			in.close();
		} catch (IOException e) {
			handleDocManagementException(new DocManagementException("error deleting temporary file"));
		}
		try {
			distributionService.delTempZip(zipName);
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
        return DocConstants.DOCUMENTEXPLORER;
	}

	/**
	 * convenience method which trigger download of zipped files
	 * @param zipName
	 * @param in
	 */
	private void triggerDownload(String zipName, InputStream in) {
		FacesContext context = FacesContext.getCurrentInstance();
	    HttpServletResponse response = 
		         (HttpServletResponse) context.getExternalContext().getResponse();
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
	}

	/**
	 * convenience method, which zippes selected files to one zip file
	 * @param zipName
	 * @return
	 */
	private InputStream getZipFile(String zipName) {
		ArrayList<BigFile> files = new ArrayList<BigFile>();
		Iterator i = this.data.iterator();
		FileTableEntry fte;
		while (i.hasNext()){
			fte = (FileTableEntry)i.next();
			if (fte.isChecked()) files.add(fileTableEntry2BigFile(fte));
		}	
		InputStream in;
		try {
			in = distributionService.zipMe(files, zipName);
			return in;
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
					File f = fileTableEntry2File(fte);
					this.savePathToLink = f.getPath();					
					if (!hasWritePermission(f)){
						noPermission();
						return DocConstants.DOCUMENTEXPLORER;
					}
					//check if f is a file or a link
					f = distributionService.getFile(f.getPath());
					distributionService.delFile(f, getDeleteLinks().equals(DocConstants.DELETE_LINKS));
				} catch (NotAFolderException e) {
					handleNotAFolderException(e);
				} catch (PathNotFoundException e) {
					handlePathNotFoundException(e);
				} catch (ResourceAlreadyExistsException e) {
					handleResourceAlreadyExistsException(e);
				} catch (NotAFileException e) {
					return delLink(this.savePathToLink);
				} catch (DocManagementException e) {
					handleDocManagementException(e);
				}		
		}	
		
		return DocConstants.DOCUMENTEXPLORER;
	}
	
	/**
	 * Action method which empties the trash bin
	 * @return
	 */
	public String clearTrash(){		
		try {			
			distributionService.clearEnrollmentTrash(enrollment);
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
		return DocConstants.DOCUMENTEXPLORER;
	}
	
	/**
	 * Action method which triggers the delete of a folder 
	 * @return
	 */
	public String deleteFolder(){
		try {
			Folder folder = distributionService.getFolder(this.folderPath);
			if (!hasWritePermission(folder)){
				noPermission();
				return DocConstants.DOCUMENTEXPLORER;
			}
			distributionService.delFolder(folder, getDeleteLinks().equals(DocConstants.DELETE_LINKS));
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
		return DocConstants.DOCUMENTEXPLORER;
	}
	
	public String delLink(String path){
		if (path.startsWith("/")) path = path.substring(1);
		try {
			Link link = distributionService.getLink(path);
			distributionService.delLink(link);
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
		return DocConstants.DOCUMENTEXPLORER;
	}
	
	public String changeLink(){
		String path = this.data.get((new Integer(getFileFacesPath())).intValue()).getPath();
		Link link = new LinkImpl();
		if (path.startsWith("/")) path = path.substring(1);		
		try {
			link = distributionService.getLink(path);
			if (!hasWritePermission(link)){
				noPermission();
				return DocConstants.NEWDOCUMENTTOFOLDER; 
			}			
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
		BigFile file = new BigFileImpl(link.getDistributionDate(), null, 0, link.getMessage(), "", link.getName(), link.getPath(), null, 1, link.getVisibility(), null);
		fileController.setFile(file);
		fileController.setLinkToEdit(link);
		fileController.setOld(true);
		fileController.setLink(true);
		return DocConstants.NEWDOCUMENTTOFOLDER;
	}
	
	public String downloadLink(){
		String path = this.data.get((new Integer(getFileFacesPath())).intValue()).getPath();
		if (path.startsWith("/")) path = path.substring(1);
		
		BigFile bigFile = new BigFileImpl();
		try {
			Link link = distributionService.getLink(path);
			File file = (File) link.getTarget();
			if (!hasReadPermission(link)){
				noPermission();
				return DocConstants.DOCUMENTEXPLORER;
			}
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
		triggerDownload(bigFile);     
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
	
	public boolean isFolderTrash(){
		if (this.folderPath==null) return false;
		return this.folderPath.endsWith(DocConstants.TRASH_NAME);
	}

}

	
	
