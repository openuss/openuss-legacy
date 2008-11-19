package org.openuss.web.collaboration;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.openuss.collaboration.WorkspaceInfo;
import org.openuss.collaboration.WorkspaceService;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FolderInfo;
import org.openuss.security.SecurityService;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

/**
 * Abstract Page for all Workspace-Pages.
 * 
 * @author  Projektseminar WS 07/08, Team Collaboration
 */
public class AbstractCollaborationPage extends AbstractCoursePage {
	
	private static final Logger LOGGER = Logger.getLogger(AbstractCollaborationPage.class);
			
	@Property(value = "#{workspaceService}")
	protected WorkspaceService workspaceService;
	
	@Property(value = "#{documentService}")
	protected DocumentService documentService;
	
	@Property (value="#{securityService}")
	private SecurityService securityService;
	
	/** workspace that is currently edited. */
	@Property(value="#{"+Constants.COLLABORATION_WORKSPACE_INFO+"}")
	protected WorkspaceInfo workspaceInfo = null;
	
	@Property(value = "#{collaboration_current_folder}")
	protected FolderInfo currentFolder;
	
	@Override
	public void prerender() throws Exception { // NOPMD by Administrator on 13.03.08 12:53
		if (courseInfo == null || courseInfo.getId() == null) {
			addError(i18n("message_error_course_page"));
			redirect(Constants.OUTCOME_BACKWARD);
			return;
		}
		
		super.prerender();
		
		if (this.workspaceInfo != null && this.workspaceInfo.getId() != null) {
			this.workspaceInfo = workspaceService.getWorkspace(workspaceInfo.getId());
		}
	}
	
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
	
	public DocumentService getDocumentService() {
		return documentService;
	}
	
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}
	
	public FolderInfo getCurrentFolder() {
		return currentFolder;
	}
	
	public void setCurrentFolder(FolderInfo currentFolder) {
		this.currentFolder = currentFolder;
	}
	
	protected FolderInfo retrieveActualFolder() {
		return documentService.getFolder(workspaceInfo, currentFolder);
	}
	
	/**
	 * Gets the current path
	 * 
	 * @return Array of FolderInfos
	 */
	@SuppressWarnings("unchecked")
	public List<FolderInfo> getCurrentPath() {
		LOGGER.debug("getting current path");
		if (currentFolder != null && currentFolder.getId() != null) {
			return documentService.getFolderPath(retrieveActualFolder());
		} else {
			return new ArrayList<FolderInfo>();
		}
	}
	
	public SecurityService getSecurityService() {
		return securityService;
	}
	
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

}
