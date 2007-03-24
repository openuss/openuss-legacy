package org.openuss.web.docmanagement.examarea;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.docmanagement.BigFile;
import org.openuss.docmanagement.BigFileImpl;
import org.openuss.docmanagement.DocConstants;
import org.openuss.docmanagement.DocManagementException;
import org.openuss.docmanagement.ExamArea;
import org.openuss.docmanagement.ExaminationService;
import org.openuss.docmanagement.File;
import org.openuss.docmanagement.NotAFileException;
import org.openuss.docmanagement.NotAFolderException;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.Resource;
import org.openuss.docmanagement.ResourceAlreadyExistsException;
import org.openuss.docmanagement.SystemFolderException;

import org.openuss.web.docmanagement.AbstractDocPage;
import org.openuss.web.docmanagement.FileTableEntry;
import org.apache.log4j.Logger;

@Bean(name = "examAreaViewBacker", scope = Scope.SESSION)
@View
public class ExamAreaViewBacker extends AbstractDocPage {

	public static final Logger logger = Logger
			.getLogger(ExamAreaViewBacker.class);

	@Property(value = "#{examinationService}")
	public ExaminationService examinationService;

	public String fileFacesPath;

	public String fileName;
	
	public ArrayList<FileTableEntry> data;
	
	public Date deadline;

	@Property(value = "#{examFileController}")
	public ExamFileController examFileController;

	/**
	 * action method called if new file button is clicked
	 * 
	 * @return
	 */
	public String newFile() {
		BigFile bf = new BigFileImpl();
		String path = DocConstants.EXAMAREA + "/"
				+ enrollment.getId().toString();
		bf.setPath(path);
		try {
			ExamArea ea = examinationService.getExamArea(enrollment);
			if (!hasWritePermission(ea)) {
				noPermission();
				return DocConstants.EXAMEXPLORER;
			}
		} catch (NotAFolderException e) {
			handleNotAFolderException(e);
		} catch (PathNotFoundException e) {
			handlePathNotFoundException(e);
		} catch (ResourceAlreadyExistsException e) {
			handleResourceAlreadyExistsException(e);
		} catch (NotAFileException e) {
			handleNotAFileException(e);
		} catch (SystemFolderException e) {
			handleDocManagementException(e);		
		} catch (DocManagementException e) {
			handleDocManagementException(e);
		}
		examFileController.setFile(bf);
		return DocConstants.EXAMNEWDOCUMENT;
	}

	/**
	 * @return ArrayList, which contains all files of current selected folder
	 *         for display in dataTable
	 */
	public ArrayList<FileTableEntry> getData() {
		ExamArea examArea = null;
		logger.debug("generating file table entries ");
		ArrayList<FileTableEntry> al = new ArrayList<FileTableEntry>();
		try {
			examArea = examinationService.getExamArea(enrollment);
			this.deadline = examArea.getDeadline();
			if (!hasReadPermission(examArea)) {
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
		} catch (SystemFolderException e) {
			handleDocManagementException(e);		
		} catch (DocManagementException e) {
			handleDocManagementException(e);
		}
		addFiles(al, examArea);
		logger.debug("file table entries generated");
		this.data = al;
		return al;

	}

	/**
	 * convenience method to add files to arraylist
	 * 
	 * @param al
	 * @param folder
	 */
	private void addFiles(ArrayList<FileTableEntry> al, ExamArea examArea) {
		if (examArea == null)
			return;
		Collection subnodes = examArea.getSubnodes();
		// check if examArea has subnodes
		if (subnodes != null) {
			Iterator nodeIterator = subnodes.iterator();
			while (nodeIterator.hasNext()) {
				Resource r = (Resource) nodeIterator.next();
				if (hasReadPermission(r)) {
					if (r instanceof File) {
						FileTableEntry fte = file2FTE((File) r);
						al.add((FileTableEntry) fte);
					}
				}
			}
		}
	}


	public String downloadSelected(){
		return DocConstants.EXAMEXPLORER;
	}
	
	/**
	 * Action methods to trigger download
	 * 
	 * @return
	 */
	public String download() {

		FileTableEntry fte = this.data.get((new Integer(getFileFacesPath())).intValue());
		File file = fileTableEntry2File(fte);
		if (!hasReadPermission(file)) {
			noPermission();
			return DocConstants.EXAMEXPLORER;		
		}		
		BigFile bigFile = new BigFileImpl();
		try {
			bigFile = examinationService.getSubmission(file);			
		} catch (NotAFolderException e) {
			handleNotAFolderException(e);
		} catch (PathNotFoundException e) {
			handlePathNotFoundException(e);
		} catch (ResourceAlreadyExistsException e) {
			handleResourceAlreadyExistsException(e);
		} catch (NotAFileException e) {
			handleNotAFileException(e);
		} catch (SystemFolderException e) {
			handleDocManagementException(e);		
		} catch (DocManagementException e) {
			handleDocManagementException(e);
		}
		triggerDownload(bigFile);
		return DocConstants.EXAMEXPLORER;
	}


	public String saveDeadline(){
		ExamArea examArea;
		try {
			examArea = examinationService.getExamArea(enrollment);
			examArea.setDeadline(new Timestamp(deadline.getTime()));
			examinationService.setDeadline(examArea);
		} catch (NotAFolderException e) {
			handleNotAFolderException(e);
		} catch (PathNotFoundException e) {
			handlePathNotFoundException(e);
		} catch (ResourceAlreadyExistsException e) {
			handleResourceAlreadyExistsException(e);
		} catch (NotAFileException e) {
			handleNotAFileException(e);
		} catch (SystemFolderException e) {
			handleDocManagementException(e);		
		} catch (DocManagementException e) {
			handleDocManagementException(e);
		}
		return DocConstants.EXAMEXPLORER;
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
		return "/" + DocConstants.EXAMAREA + "/"
				+ enrollment.getId().toString();
	}

	public ExamFileController getExamFileController() {
		return examFileController;
	}

	public void setExamFileController(ExamFileController examFileController) {
		this.examFileController = examFileController;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	public String getDeadlineString(){
		if (this.deadline==null){
			try {
				this.deadline = examinationService.getExamArea(enrollment).getDeadline();
			} catch (NotAFolderException e) {
				handleNotAFolderException(e);
			} catch (PathNotFoundException e) {
				handlePathNotFoundException(e);
			} catch (ResourceAlreadyExistsException e) {
				handleResourceAlreadyExistsException(e);
			} catch (NotAFileException e) {
				handleNotAFileException(e);
			} catch (SystemFolderException e) {
				handleDocManagementException(e);		
			} catch (DocManagementException e) {
				handleDocManagementException(e);
			}
		}			
		SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
		return df.format(this.deadline.getTime());		
	}

}