package org.openuss.web.docmanagement;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.View;
import org.openuss.docmanagement.DistributionService;
import org.openuss.docmanagement.DocManagementException;
import org.openuss.docmanagement.DocRights;
import org.openuss.docmanagement.Folder;
import org.openuss.docmanagement.FolderImpl;
import org.openuss.docmanagement.DocConstants;
import org.openuss.docmanagement.NotAFileException;
import org.openuss.docmanagement.NotAFolderException;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.ResourceAlreadyExistsException;
import org.apache.log4j.Logger;

@Bean(name="facultyFolderController", scope=Scope.SESSION)
@View
public class FacultyFolderController extends ExceptionHandler{
	
	public Folder folder;
	
	public boolean visibleForAll;
	
	@Property(value = "#{distributionService}")
	public DistributionService distributionService;

	public static final Logger logger = Logger.getLogger(FacultyFolderController.class);
	
	public String save(){
		//visibility is only 0, if folder is a new folder
		boolean old = (folder.getVisibility()!=0);
		
		if (!old) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ValueBinding valueBinding = facesContext.getApplication().createValueBinding("#{facultyViewBacker}");
			FacultyViewBacker fvb = (FacultyViewBacker)valueBinding.getValue(facesContext);
			folder.setPath(fvb.getFolderPath());
		}
		if (visibleForAll) folder.setVisibility(DocRights.EDIT_ASSIST|DocRights.READ_ALL);
		else if (!visibleForAll) folder.setVisibility(DocRights.EDIT_ASSIST|DocRights.READ_ASSIST);
		try {
			distributionService.changeFolder(folder, old);
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
		return DocConstants.FACULTY_EXPLORER;
	}
	
	public Folder getFolder() {
		if (folder==null) folder = new FolderImpl();
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public DistributionService getDistributionService() {
		return distributionService;
	}

	public void setDistributionService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}

	public boolean isVisibleForAll() {
		return (folder.getVisibility()&DocRights.READ_ALL)>0;
	}

	public void setVisibleForAll(boolean visibleForAll) {
		this.visibleForAll = visibleForAll;
	}

}