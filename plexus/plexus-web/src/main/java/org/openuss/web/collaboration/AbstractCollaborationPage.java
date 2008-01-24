package org.openuss.web.collaboration;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.openuss.collaboration.WorkspaceInfo;
import org.openuss.collaboration.WorkspaceService;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FolderInfo;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

public class AbstractCollaborationPage extends AbstractCoursePage {
	
	public static final Logger logger = Logger.getLogger(AbstractCollaborationPage.class);
			
	@Property(value = "#{workspaceService}")
	protected WorkspaceService workspaceService;
	
	@Property(value = "#{documentService}")
	protected DocumentService documentService;
	
	/** workspace that is currently edited. */
	@Property(value="#{"+Constants.COLLABORATION_WORKSPACE_INFO+"}")
	protected WorkspaceInfo workspaceInfo = null;
	
	@Property(value = "#{collaboration_current_folder}")
	protected FolderInfo currentFolder;
	
	@Override
	public void prerender() throws Exception {
		super.prerender();
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
	
	public List<FolderInfo> getCurrentPath() {
		logger.debug("getting current path");
		if (currentFolder != null && currentFolder.getId() != null) {
			return documentService.getFolderPath(retrieveActualFolder());
		} else {
			return new ArrayList<FolderInfo>();
		}
	}
	
}
