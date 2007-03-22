package org.openuss.web.docmanagement.examarea;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.docmanagement.BigFile;
import org.openuss.docmanagement.BigFileImpl;
import org.openuss.docmanagement.DocConstants;
import org.openuss.docmanagement.DocManagementException;
import org.openuss.docmanagement.ExaminationService;
import org.openuss.docmanagement.File;
import org.openuss.docmanagement.FileImpl;
import org.openuss.docmanagement.Folder;
import org.openuss.docmanagement.FolderImpl;
import org.openuss.docmanagement.NotAFileException;
import org.openuss.docmanagement.NotAFolderException;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.ResourceAlreadyExistsException;
import org.openuss.web.docmanagement.AbstractEnrollmentDocPage;
import org.openuss.web.docmanagement.FileTableEntry;
import org.apache.log4j.Logger;

@Bean(name="examAreaViewBacker", scope=Scope.SESSION)
@View
public class ExamAreaViewBacker extends AbstractEnrollmentDocPage{
	
	public static final Logger logger = Logger.getLogger(ExamAreaViewBacker.class);
	
	@Property(value="#{examinationService}")
	public ExaminationService examinationService;
	
	public String fileFacesPath;
	
	public String fileName;
	
	
	
	/**
	 * action method called if change file button is clicked
	 * @return
	 */
	public String changeFile(){
		return DocConstants.NEWDOCUMENTTOFOLDER;
	}
	
	/**
	 * action method called if new file button is clicked
	 * @return
	 */
	public String newFile(){
/*		BigFile bf = new BigFileImpl();
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
 */
		return DocConstants.NEWDOCUMENTTOFOLDER;	
	}
	

	/** 
	 * @return ArrayList, which contains all files of current selected folder for display in dataTable
	 */
	public ArrayList<FileTableEntry> getData() {

		logger.debug("generating file table entries ");		
		ArrayList<FileTableEntry> al = new ArrayList<FileTableEntry>();
		/*
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
 */
		return al;

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
	/*
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
		*/
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
	/*
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
		*/	
		return null;
	}	
	

	public ExaminationService getExaminationService() {
		return examinationService;
	}

	public void setExaminationService(ExaminationService examinationService) {
		this.examinationService = examinationService;
	}

	public String getFileFacesPath() {
		return fileFacesPath;
	}

	public void setFileFacesPath(String fileFacesPath) {
		this.fileFacesPath = fileFacesPath;
	}

	public String getPath() {
		return "/"+DocConstants.EXAMAREA+"/"+enrollment.getId().toString();
	}

	
}