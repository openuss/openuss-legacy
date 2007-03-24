package org.openuss.web.docmanagement.examarea;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.openuss.docmanagement.NotAFileException;
import org.openuss.docmanagement.NotAFolderException;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.ResourceAlreadyExistsException;
import org.openuss.docmanagement.SystemFolderException;
import org.openuss.web.docmanagement.AbstractDocPage;
import org.openuss.web.docmanagement.FileTableEntry;
import org.apache.log4j.Logger;


@Bean(name="versionViewBacker", scope=Scope.SESSION)
@View
public class VersionViewBacker extends AbstractDocPage{
	
	public static final Logger logger = Logger.getLogger(VersionViewBacker.class);
	
	@Property(value="#{examinationService}")
	public ExaminationService examinationService;

	@Property(value="#{examAreaViewBacker}")
	public ExamAreaViewBacker examAreaViewBacker;
	
	public String fileFacesPath;
	
	public ArrayList<FileTableEntry> data;
	
	public FileTableEntry baseVersion;
	
	/**
	 * @return ArrayList, which contains all files of current selected folder
	 *         for display in dataTable
	 */
	public ArrayList<FileTableEntry> getData() {
		ArrayList<FileTableEntry> al = new ArrayList<FileTableEntry>();
		FileTableEntry fte = examAreaViewBacker.getData().get(new Integer(examAreaViewBacker.getFileFacesPath()).intValue());
		this.baseVersion = fte;
		try {
			List l = examinationService.getVersions(fileTableEntry2File(fte));
			Iterator i = l.iterator();
			File f; 
			while (i.hasNext()){
				f = (File)i.next();
				f.setName(baseVersion.getName());
				al.add(file2FTE(f));
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
		this.data = al;
		return al;
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
		bigFile.setName(baseVersion.getName());
		triggerDownload(bigFile);
		return DocConstants.EXAMEXPLORER;
	}

	public ExaminationService getExaminationService() {
		return examinationService;
	}

	public void setExaminationService(ExaminationService examinationService) {
		this.examinationService = examinationService;
	}

	public ExamAreaViewBacker getExamAreaViewBacker() {
		return examAreaViewBacker;
	}

	public void setExamAreaViewBacker(ExamAreaViewBacker examAreaViewBacker) {
		this.examAreaViewBacker = examAreaViewBacker;
	}

	public String getFileFacesPath() {
		return fileFacesPath;
	}

	public void setFileFacesPath(String fileFacesPath) {
		this.fileFacesPath = fileFacesPath;
	}
}