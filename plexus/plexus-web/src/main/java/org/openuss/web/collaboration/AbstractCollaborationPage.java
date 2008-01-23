package org.openuss.web.collaboration;

import org.apache.shale.tiger.managed.Property;
import org.openuss.collaboration.WorkspaceInfo;
import org.openuss.collaboration.WorkspaceService;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

public class AbstractCollaborationPage extends AbstractCoursePage {
	@Property(value = "#{workspaceService}")
	protected WorkspaceService workspaceService;
	
	/** workspace that is currently edited. */
	@Property(value="#{"+Constants.COLLABORATION_WORKSPACE_INFO+"}")
	protected WorkspaceInfo workspaceInfo = null;
	
	public WorkspaceInfo getWorkspaceInfo() {
		return workspaceInfo;
	}
	public void setWorkspaceInfo(WorkspaceInfo workspaceInfo) {
		this.workspaceInfo = workspaceInfo;
	}
	
	public WorkspaceService getWorkspaceService() {
		return workspaceService;
	}
	public void setWorkspaceService(WorkspaceService workspaceService) {
		this.workspaceService = workspaceService;
	}
}
