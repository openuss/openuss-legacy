package org.openuss.web.docmanagement.workingplace;

import java.sql.Timestamp;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.View;
import org.apache.log4j.Logger;
import org.openuss.docmanagement.BigFile;
import org.openuss.docmanagement.BigFileImpl;
import org.openuss.docmanagement.CollaborationService;
import org.openuss.docmanagement.DocConstants;
import org.openuss.docmanagement.DocManagementException;
import org.openuss.docmanagement.DocRights;
import org.openuss.docmanagement.NotAFileException;
import org.openuss.docmanagement.NotAFolderException;
import org.openuss.docmanagement.PathNotFoundException;
import org.openuss.docmanagement.ResourceAlreadyExistsException;
import org.openuss.web.docmanagement.AbstractEnrollmentDocPage;



@Bean(name="wpFileController", scope=Scope.SESSION)
@View
public class WPFileController extends AbstractEnrollmentDocPage{
	
	@Property(value = "#{collaborationService}")
	CollaborationService collaborationService;
	
	public BigFile file;
	
	public static final Logger logger = Logger.getLogger(WPFileController.class);
	
	public boolean old;
	

	public String save(){
			//visibility is only 0, if folder is a new folder
			old = (file.getVisibility() != 0);
			if (!old) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ValueBinding valueBinding = facesContext.getApplication()
						.createValueBinding("#{workingPlaceViewBacker}");
				WorkingPlaceViewBacker wpvb = (WorkingPlaceViewBacker) valueBinding
						.getValue(facesContext);
				file.setPath(wpvb.getFolderPath());
			}
			file.setDistributionTime(new Timestamp(System.currentTimeMillis()));
			file.setVisibility(DocRights.EDIT_ALL|DocRights.READ_ALL);
			try {
				collaborationService.addFile(file);
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

	public BigFile getFile() {
		if (file==null) file = new BigFileImpl();
		return file;
	}

	public void setFile(BigFile file) {
		this.file = file;
	}

	public boolean isOld() {
		return old;
	}
	
	public void setOld(boolean old) {
		this.old = old;
	}

	public CollaborationService getCollaborationService() {
		return collaborationService;
	}

	public void setCollaborationService(CollaborationService collaborationService) {
		this.collaborationService = collaborationService;
	}

}