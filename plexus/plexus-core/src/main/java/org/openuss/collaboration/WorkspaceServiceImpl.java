// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.collaboration;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.FolderInfo;
import org.openuss.security.Authority;
import org.openuss.security.Group;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.security.acl.LectureAclEntry;

/**
 * @author  Projektseminar WS 07/08, Team Collaboration
 * @see org.openuss.collaboration.WorkspaceService
 */
public class WorkspaceServiceImpl extends org.openuss.collaboration.WorkspaceServiceBase {
	
	private static final Logger LOGGER = Logger.getLogger(WorkspaceServiceImpl.class);

	@Override
	protected void handleCreateWorkspace(WorkspaceInfo workspaceInfo) {
		Validate.notNull(workspaceInfo, "WorkspaceInfo cannot be null.");
		Validate.notNull(workspaceInfo.getDomainId(), "domainId cannot be null.");
		
		final Workspace workspaceEntity = this.getWorkspaceDao().workspaceInfoToEntity(workspaceInfo);
		Validate.notNull(workspaceEntity, "Cannot transform workspaceInfo to entity.");

		this.getWorkspaceDao().create(workspaceEntity);
		Validate.notNull(workspaceEntity, "Id of workspace cannot be null.");
		
		workspaceInfo.setId(workspaceEntity.getId());
		
		getSecurityService().createObjectIdentity(workspaceEntity, null);//new DefaultDomainObject(workspaceInfo.getDomainId()));
		getSecurityService().setPermissions(getSecurityService().getUserObject(getSecurityService().getCurrentUser()), workspaceEntity, LectureAclEntry.OGCRUD);
	}

	@SuppressWarnings("unchecked") // NOPMD by Administrator on 13.03.08 13:11
	@Override
	protected List handleFindWorkspaceMembers(Long workspaceId) { 
		Validate.notNull(workspaceId, "workspaceId cannot be null.");
		final Workspace workspace = this.getWorkspaceDao().load(workspaceId);
		Validate.notNull(workspace, "No workspace could be found with the workspaceId " + workspaceId);
		
		final Collection<User> users = workspace.getUser();
		final List<UserInfo> members = new ArrayList<UserInfo>(users.size());
		for (User u : users) {
			members.add(this.getUserDao().toUserInfo(u));
		}
		return members;
	}

	@SuppressWarnings("unchecked") // NOPMD by Administrator on 13.03.08 13:11
	@Override
	protected List handleFindWorkspacesByDomain(Long domainId) {
		Validate.notNull(domainId, "domainId cannot be null.");
		return this.getWorkspaceDao().findByDomainId(WorkspaceDao.TRANSFORM_WORKSPACEINFO, domainId);
	}

	@Override
	protected WorkspaceInfo handleGetWorkspace(Long workspaceId) {
		Validate.notNull(workspaceId, "Parameter workspaceId must not be null!");
		return (WorkspaceInfo)getWorkspaceDao().load(WorkspaceDao.TRANSFORM_WORKSPACEINFO, workspaceId);
	}

	@Override
	protected void handleRemoveWorkspace(Long workspaceId) throws DocumentApplicationException {
		Validate.notNull(workspaceId, "workspaceId cannot be null.");
		
		final Workspace workspaceEntity = getWorkspaceDao().load(workspaceId);
		final WorkspaceInfo workspaceInfo = getWorkspaceDao().toWorkspaceInfo(workspaceEntity);
		
		final FolderInfo folderEntry = getDocumentService().getFolder(workspaceInfo);
		getDocumentService().removeFolderEntry(folderEntry.getId());
		
		getWorkspaceDao().remove(workspaceEntity);
	}

	@Override
	protected void handleUpdateWorkspace(WorkspaceInfo workspaceInfo) {
		LOGGER.debug("Starting method handleUpdateWorkspace");
		Validate.notNull(workspaceInfo, "Parameter workspaceInfo must not be null.");
		Validate.notNull(workspaceInfo.getId(), "Parameter workspaceInfo must contain a valid course id.");

		final Workspace workspaceEntity = getWorkspaceDao().workspaceInfoToEntity(workspaceInfo);
		getWorkspaceDao().update(workspaceEntity);
	}

	@SuppressWarnings("unchecked") // NOPMD by Administrator on 13.03.08 13:11
	@Override
	protected void handleUpdateWorkspaceMembers(List userIds, Long workspaceId) {
		LOGGER.debug("Starting method handleUpdateWorkspaceMembers");
		Validate.notNull(workspaceId, "Parameter workspaceId must not be null.");
		Validate.notNull(userIds, "Parameter userId must not be null.");

		final Workspace workspace = getWorkspaceDao().load(workspaceId);
		
		final Group group = getSecurityService().getGroupByName("GROUP_COURSE_" + workspace.getDomainId() + "_PARTICIPANTS");
		
		final List<Authority> members = group.getMembers();
		
		workspace.getUser().clear();

		for (Authority auth : members) {
			final User user = getSecurityService().getUserObject(auth.getId());
			
			if (user != null && userIds.contains(user.getId())) {
				workspace.getUser().add(user);
				getSecurityService().setPermissions(user, workspace, LectureAclEntry.WORKSPACE_PARTICIPANT);
			} else {
				getSecurityService().removePermission(user, workspace);
				getSecurityService().setPermissions(user, workspace, LectureAclEntry.NOTHING);
			}
		} 
		
		getWorkspaceDao().update(workspace);
	}

	@SuppressWarnings("unchecked") // NOPMD by Administrator on 13.03.08 13:11
	@Override
	protected List handleFindWorkspacesByDomainAndUser(Long domainId, UserInfo user) {
		Validate.notNull(domainId, "domainId cannot be null.");
		Validate.notNull(user, "userId cannot be null.");
		
		final List<Workspace> workspaces = getWorkspaceDao().findByDomainId(domainId);
		final List<WorkspaceInfo> workspaceInfos = new ArrayList<WorkspaceInfo>(workspaces.size());
		for (Workspace ws : workspaces) {
			if (ws.getUser().contains(user)) {
				workspaceInfos.add(getWorkspaceDao().toWorkspaceInfo(ws));
			}
		}
		
		return workspaceInfos;
	}

	@Override
	protected List handleFindWorkspacesByUser(User user) throws Exception {
		List<BigInteger> workspaceIds = getWorkspaceDao().findByUser(user);
		List<Workspace> workspaces = new ArrayList<Workspace>();
		for (BigInteger id : workspaceIds){
			workspaces.add(getWorkspaceDao().load(new Long(id.longValue())));
		}
		return workspaces;
	}
	
	protected void handleRemoveUserFromWorkspaces(User user) {
		List<Workspace> workspaces = findWorkspacesByUser(user);
		List<Long> userIds;
		Set<User> workspaceMembers;
		for (Workspace workspace : workspaces){
			workspaceMembers = workspace.getUser();
			userIds = new ArrayList<Long>();
			for (User workspaceMember : workspaceMembers){
				if (!user.getId().equals(workspaceMember.getId())){
					userIds.add(workspaceMember.getId());
				}
			}
			updateWorkspaceMembers(userIds, workspace.getId());
		}
	}

	
}