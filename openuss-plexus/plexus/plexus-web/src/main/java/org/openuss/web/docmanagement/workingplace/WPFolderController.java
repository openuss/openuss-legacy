package org.openuss.web.docmanagement.workingplace;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.View;
import org.openuss.docmanagement.CollaborationService;
import org.openuss.docmanagement.DocManagementException;
import org.openuss.docmanagement.DocRights;
import org.openuss.docmanagement.Folder;
import org.openuss.docmanagement.FolderImpl;
import org.openuss.docmanagement.DocConstants;
import org.openuss.docmanagement.NotAFileException;
import org.openuss.docmanagement.NotAFolderException;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.ResourceAlreadyExistsException;
import org.openuss.web.docmanagement.AbstractEnrollmentDocPage;
import org.apache.log4j.Logger;

@Bean(name="wpFolderController", scope=Scope.SESSION)
@View
public class WPFolderController extends AbstractEnrollmentDocPage{

	public Folder folder;
	
	@Property(value = "#{collaborationService}")
	public CollaborationService collaborationService;

	public static final Logger logger = Logger.getLogger(WPFolderController.class);
	
	public String save(){
		//visibility is only 0, if folder is a new folder
		boolean old = (folder.getVisibility()!=0);
		
		if (!old) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ValueBinding valueBinding = facesContext.getApplication().createValueBinding("#{workingPlaceViewBacker}");
			WorkingPlaceViewBacker wpvb = (WorkingPlaceViewBacker)valueBinding.getValue(facesContext);
			folder.setPath(wpvb.getFolderPath());
		}
		folder.setVisibility(DocRights.READ_ALL|DocRights.EDIT_ALL);
		try {
			collaborationService.addFolder(folder);			
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
		return DocConstants.WPEXPLORER;
	}
	
	public Folder getFolder() {
		if (folder==null) folder = new FolderImpl();
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public CollaborationService getCollaborationService() {
		return collaborationService;
	}

	public void setCollaborationService(CollaborationService collaborationService) {
		this.collaborationService = collaborationService;
	}

}