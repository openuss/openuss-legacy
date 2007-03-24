package org.openuss.web.docmanagement.workingplace;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.docmanagement.CollaborationService;
import org.openuss.web.docmanagement.AbstractDocPage;

@Bean(name = "wPVersionBacker", scope = Scope.SESSION)
@View
public class WPVersionBacker extends AbstractDocPage {

	public static final Logger logger = Logger
			.getLogger(WPVersionBacker.class);

	@Property(value = "#{collaborationService}")
	public CollaborationService collaborationService;

	public String fileFacesPath;
	
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
	
	
	
}