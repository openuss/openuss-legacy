package org.openuss.web.docmanagement.workingplace;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
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
import org.openuss.docmanagement.NotAFileException;
import org.openuss.docmanagement.NotAFolderException;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.ResourceAlreadyExistsException;
import org.openuss.docmanagement.SystemFolderException;
import org.openuss.web.docmanagement.AbstractDocPage;
import org.openuss.web.docmanagement.FileTableEntry;

@Bean(name = "wpVersionBacker", scope = Scope.SESSION)
@View
public class WPVersionBacker extends AbstractDocPage {

	public static final Logger logger = Logger
			.getLogger(WPVersionBacker.class);

	@Property(value = "#{collaborationService}")
	public CollaborationService collaborationService;

	@Property(value = "#{workingPlaceViewBacker}")
	public WorkingPlaceViewBacker workingPlaceViewBacker;

	public ArrayList<FileTableEntry> data;
	
	public FileTableEntry baseVersion;	
	
	public String fileFacesPath;
	
	/**
	 * @return ArrayList, which contains all files of current selected folder
	 *         for display in dataTable
	 */
	public ArrayList<FileTableEntry> getData() {
		ArrayList<FileTableEntry> al = new ArrayList<FileTableEntry>();
		FileTableEntry fte = workingPlaceViewBacker.getData().get(new Integer(workingPlaceViewBacker.getFileFacesPath()).intValue());
		this.baseVersion = fte;
		try {
			List l = collaborationService.getVersions(fileTableEntry2File(fte));
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
			return DocConstants.WPEXPLORER;		
		}		
		BigFile bigFile = new BigFileImpl();
		try {
			bigFile = collaborationService.getFile(file);			
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
		return DocConstants.WPEXPLORER;
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

	public WorkingPlaceViewBacker getWorkingPlaceViewBacker() {
		return workingPlaceViewBacker;
	}

	public void setWorkingPlaceViewBacker(
			WorkingPlaceViewBacker workingPlaceViewBacker) {
		this.workingPlaceViewBacker = workingPlaceViewBacker;
	}
	
	
	
}