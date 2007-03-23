package org.openuss.web.docmanagement.workingplace;

import java.io.IOException;
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
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.docmanagement.BigFile;
import org.openuss.docmanagement.BigFileImpl;
import org.openuss.docmanagement.CollaborationService;
import org.openuss.docmanagement.DocConstants;
import org.openuss.docmanagement.DocManagementException;
import org.openuss.docmanagement.File;
import org.openuss.docmanagement.FileImpl;
import org.openuss.docmanagement.Folder;
import org.openuss.docmanagement.FolderImpl;
import org.openuss.docmanagement.Link;
import org.openuss.docmanagement.NotAFileException;
import org.openuss.docmanagement.NotAFolderException;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.Resource;
import org.openuss.docmanagement.ResourceAlreadyExistsException;
import org.openuss.web.docmanagement.AbstractEnrollmentDocPage;
import org.openuss.web.docmanagement.FileTableEntry;

@Bean(name = "workingPlaceViewBacker", scope = Scope.SESSION)
@View
public class WorkingPlaceViewBacker extends AbstractEnrollmentDocPage {

	public static final Logger logger = Logger
			.getLogger(WorkingPlaceViewBacker.class);

	@Property(value = "#{collaborationService}")
	public CollaborationService collaborationService;

	@Property(value = "#{wpFolderController}")
	public WPFolderController wpFolderController;

	@Property(value = "#{wpFileController}")
	public WPFileController wpFileController;

	public String fileFacesPath;
	
	public String folderPath;
	
	public TreeModel treeModel;
	
	public ArrayList<FileTableEntry> data;
	
	
	public TreeModel getTree(){

		Folder folder = new FolderImpl();
		try {
			folder = collaborationService.getWorkingPlace(enrollment);
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
		this.treeModel=new TreeModelBase(folder2TreeNodeBase(folder));
		
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
			Folder f = collaborationService.getFolder(path);
			if (!hasWritePermission(f)){
				noPermission();
				return DocConstants.WPNEWFOLDER;
			}
			wpFolderController.setFolder(f);
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
		return DocConstants.WPNEWFOLDER;
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
		wpFolderController.setFolder(folder);
		return DocConstants.WPNEWFOLDER;
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
			File file = collaborationService.getFile(path);
			if (!hasWritePermission(file)){
				noPermission();
				return DocConstants.WPNEWDOCUMENT; 
			}
			bigFile = collaborationService.getFile(file);
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

		wpFileController.setFile(bigFile);
		wpFileController.setOld(true);
		return DocConstants.WPNEWDOCUMENT;
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
			Folder f = collaborationService.getFolder(path);
			if (!hasWritePermission(f)){
				noPermission();
				return DocConstants.WPNEWDOCUMENT;
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
		wpFileController.setFile(bf);
		wpFileController.setOld(false);
		return DocConstants.WPNEWDOCUMENT;	
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
				folder = collaborationService.getFolder(path);
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
		fte.setName(link.getName());
		fte.setMessage(link.getMessage());
		fte.setDistributionTime(link.getDistributionDate());
		fte.setVisibility(link.getVisibility());		
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
		fte.setOwner(f.getOwner());
		fte.setViewed(f.getViewed());
		fte.setViewer(auth.getName());
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
			File file = collaborationService.getFile(path);
			if (!hasReadPermission(file)){
				noPermission();
				return DocConstants.WPEXPLORER;
			}
			bigFile = collaborationService.getFile(file);
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
		return DocConstants.WPEXPLORER;
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
				fte.getVisibility(), 
				fte.getOwner(),
				fte.getViewed(), 
				fte.getViewer());
	}
		
	
	
	
	public CollaborationService getCollaborationService() {
		return collaborationService;
	}

	public void setCollaborationService(CollaborationService collaborationService) {
		this.collaborationService = collaborationService;
	}

	public String getFileFacesPath() {
		return fileFacesPath;
	}

	public void setFileFacesPath(String fileFacesPath) {
		this.fileFacesPath = fileFacesPath;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = treeModel.getNodeById(folderPath).getIdentifier();
	}

	public WPFolderController getWpFolderController() {
		return wpFolderController;
	}

	public void setWpFolderController(WPFolderController folderController) {
		wpFolderController = folderController;
	}

	public WPFileController getWpFileController() {
		return wpFileController;
	}

	public void setWpFileController(WPFileController fileController) {
		wpFileController = fileController;
	}
	
	
	
}