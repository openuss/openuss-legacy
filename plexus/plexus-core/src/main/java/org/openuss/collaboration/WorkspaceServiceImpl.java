// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.collaboration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.documents.FolderInfo;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @author  Projektseminar WS 07/08, Team Collaboration
 * @see org.openuss.collaboration.WorkspaceService
 */
public class WorkspaceServiceImpl extends
		org.openuss.collaboration.WorkspaceServiceBase {
	
	private static final Logger logger = Logger.getLogger(WorkspaceServiceImpl.class);

	@Override
	protected void handleCreateWorkspace(WorkspaceInfo workspaceInfo)
			throws Exception {
		Validate.notNull(workspaceInfo, "WorkspaceInfo cannot be null.");
		Validate.notNull(workspaceInfo.getDomainId(), "domainId cannot be null.");
		
		// Transform VO to entity
		Workspace workspaceEntity = this.getWorkspaceDao().workspaceInfoToEntity(workspaceInfo);
		Validate.notNull(workspaceEntity, "Cannot transform workspaceInfo to entity.");

		// Save Entity
		this.getWorkspaceDao().create(workspaceEntity);
		Validate.notNull(workspaceEntity, "Id of workspace cannot be null.");
		
		// Update input parameter for aspects to get the right domain objects. 
		workspaceInfo.setId(workspaceEntity.getId());
		
		getSecurityService().createObjectIdentity(workspaceEntity, null);
	}

	@Override
	protected List handleFindWorkspaceMembers(Long workspaceId)
			throws Exception {
		Validate.notNull(workspaceId, "workspaceId cannot be null.");
		Workspace workspace = this.getWorkspaceDao().load(workspaceId);
		Validate.notNull(workspace, "No workspace could be found with the workspaceId " + workspaceId);
		
		Collection<User> users = workspace.getUser();
		List<UserInfo> members = new ArrayList<UserInfo>(users.size());
		for (User u : users) {
			members.add(this.getUserDao().toUserInfo(u));
		}
		return members;
	}

	@Override
	protected List handleFindWorkspacesByDomain(Long domainId) throws Exception {
		Validate.notNull(domainId, "domainId cannot be null.");
		return this.getWorkspaceDao().findByDomainId(WorkspaceDao.TRANSFORM_WORKSPACEINFO, domainId);
	}

	@Override
	protected WorkspaceInfo handleGetWorkspace(Long workspaceId)
			throws Exception {
		Validate.notNull(workspaceId, "Parameter workspaceId must not be null!");
		return (WorkspaceInfo)getWorkspaceDao().load(WorkspaceDao.TRANSFORM_WORKSPACEINFO, workspaceId);
	}

	@Override
	protected void handleRemoveWorkspace(Long workspaceId) throws Exception {
		Validate.notNull(workspaceId, "workspaceId cannot be null.");
		
		Workspace workspaceEntity = getWorkspaceDao().load(workspaceId);
		WorkspaceInfo workspaceInfo = getWorkspaceDao().toWorkspaceInfo(workspaceEntity);
		
		FolderInfo folderEntry = getDocumentService().getFolder(workspaceInfo);
		getDocumentService().removeFolderEntry(folderEntry.getId());
		
		getWorkspaceDao().remove(workspaceEntity);
	}

	@Override
	protected void handleUpdateWorkspace(WorkspaceInfo workspaceInfo)
			throws Exception {
		logger.debug("Starting method handleUpdateWorkspace");
		Validate.notNull(workspaceInfo, "Parameter workspaceInfo must not be null.");
		Validate.notNull(workspaceInfo.getId(), "Parameter workspaceInfo must contain a valid course id.");

		// Transform VO to Entity
		Workspace workspaceEntity = getWorkspaceDao().workspaceInfoToEntity(workspaceInfo);
		// Update Course
		getWorkspaceDao().update(workspaceEntity);
	}

	@Override
	protected void handleUpdateWorkspaceMembers(List userId, Long workspaceId)
			throws Exception {
		logger.debug("Starting method handleUpdateWorkspaceMembers");
		Validate.notNull(workspaceId, "Parameter workspaceId must not be null.");
		Validate.notNull(userId, "Parameter userId must not be null.");

		Workspace workspace = getWorkspaceDao().load(workspaceId);
		
		// remove permissions
		for (User user : workspace.getUser()) {
			getSecurityService().removePermission(user, workspace);
		}
		
		workspace.getUser().clear();
		
		for (Long id : (List<Long>)userId) {
			User member = getUserDao().load(id); 
			
			workspace.getUser().add(member);
			getSecurityService().setPermissions(member, workspace, 
					LectureAclEntry.WORKSPACE_PARTICIPANT);
		}
		
		getWorkspaceDao().update(workspace);
	}

	@Override
	protected List handleFindWorkspacesByDomainAndUser(Long domainId,
			User user) throws Exception {
		Validate.notNull(domainId, "domainId cannot be null.");
		Validate.notNull(user, "userId cannot be null.");
		
		List<Workspace> workspaces = getWorkspaceDao().findByDomainId(domainId);
		List<WorkspaceInfo> workspaceInfos = new ArrayList<WorkspaceInfo>(workspaces.size());
		for (Workspace ws : workspaces) {
			if (ws.getUser().contains(user)) {
				workspaceInfos.add(getWorkspaceDao().toWorkspaceInfo(ws));
			}
		}
		
		return workspaceInfos;
	}
}