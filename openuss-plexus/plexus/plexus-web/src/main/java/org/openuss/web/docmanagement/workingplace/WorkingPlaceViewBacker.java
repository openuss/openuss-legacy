package org.openuss.web.docmanagement.workingplace;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.docmanagement.CollaborationService;
import org.openuss.web.docmanagement.AbstractEnrollmentDocPage;

@Bean(name = "workingPlaceViewBacker", scope = Scope.SESSION)
@View
public class WorkingPlaceViewBacker extends AbstractEnrollmentDocPage {

	public static final Logger logger = Logger
			.getLogger(WorkingPlaceViewBacker.class);

	@Property(value = "#{collaborationService}")
	public CollaborationService collaborationService;

	public String fileFacesPath;
	
	public String folderPath;
	
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
		this.folderPath = folderPath;
	}
	
	
	
}