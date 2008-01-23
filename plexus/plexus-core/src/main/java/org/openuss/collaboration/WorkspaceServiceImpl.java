// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.collaboration;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.openuss.foundation.DomainObject;
import org.openuss.lecture.Course;

/**
 * @see org.openuss.collaboration.WorkspaceService
 */
public class WorkspaceServiceImpl extends
		org.openuss.collaboration.WorkspaceServiceBase {

	/**
	 * @see org.openuss.collaboration.WorkspaceDocumentService#createWorkspace(org.openuss.collaboration.WorkspaceInfo,
	 *      org.openuss.foundation.DomainObject)
	 */
	protected void handleCreateWorkspace(
			org.openuss.collaboration.WorkspaceInfo workspaceInfo)
			throws java.lang.Exception {
		Validate.notNull(workspaceInfo, "WorkspaceInfo cannot be null.");
		Validate.notNull(workspaceInfo.getCourseId(), "GetCourseId cannot be null.");
		
		// Transform VO to entity
		Workspace workspaceEntity = this.getWorkspaceDao().workspaceInfoToEntity(workspaceInfo);
		Validate.notNull(workspaceEntity, "Cannot transform workspaceInfo to entity.");

		// Add Course to CourseType and Period
		this.getCourseDao().load(workspaceInfo.getCourseId()).getWorkspaces().add(workspaceEntity);
		
		// Save Entity
		this.getWorkspaceDao().create(workspaceEntity);
		Validate.notNull(workspaceEntity, "Id of workspace cannot be null.");

		// FIXME - Kai, Indexing should not base on VOs!
		// Kai: Do not delete this!!! Set id of institute VO for indexing
		// Update input parameter for aspects to get the right domain objects. 
		workspaceInfo.setId(workspaceEntity.getId());

		// Set Security
	//FIXME: don't know what this does:	this.getSecurityService().createObjectIdentity(workspaceEntity, workspaceEntity.getCourseType());
		
//		updateAccessTypePermission(workspaceEntity);
	}

	/**
	 * @see org.openuss.collaboration.WorkspaceDocumentService#removeWorkspace(java.lang.Long)
	 */
	protected void handleRemoveWorkspace(java.lang.Long workspaceId)
			throws java.lang.Exception {
		// @todo implement protected void handleRemoveWorkspace(java.lang.Long
		// workspaceId)
		// throw new
		// java.lang.UnsupportedOperationException("org.openuss.collaboration.WorkspaceDocumentService.handleRemoveWorkspace(java.lang.Long
		// workspaceId) Not implemented!");
		System.out.println("handleRemoveWorkspace called");
	}

	/**
	 * @see org.openuss.collaboration.WorkspaceDocumentService#saveWorkspaceMembers(java.util.List,
	 *      java.lang.Long)
	 */
	protected void handleSaveWorkspaceMembers(java.util.List userId,
			java.lang.Long workspaceId) throws java.lang.Exception {
		// @todo implement protected void
		// handleSaveWorkspaceMembers(java.util.List userId, java.lang.Long
		// workspaceId)
		// throw new
		// java.lang.UnsupportedOperationException("org.openuss.collaboration.WorkspaceDocumentService.handleSaveWorkspaceMembers(java.util.List
		// userId, java.lang.Long workspaceId) Not implemented!");
		System.out.println("handleRemoveWorkspace called");
	}

	/**
	 * @see org.openuss.collaboration.WorkspaceDocumentService#saveWorkspace(org.openuss.collaboration.WorkspaceInfo)
	 */
	protected void handleSaveWorkspace(
			org.openuss.collaboration.WorkspaceInfo workspaceInfo)
			throws java.lang.Exception {
		// @todo implement protected void
		// handleSaveWorkspace(org.openuss.collaboration.WorkspaceInfo
		// workspaceInfo)
		// throw new
		// java.lang.UnsupportedOperationException("org.openuss.collaboration.WorkspaceDocumentService.handleSaveWorkspace(org.openuss.collaboration.WorkspaceInfo
		// workspaceInfo) Not implemented!");
		System.out.println("handleSaveWorkspace called");
	}

	/**
	 * @see org.openuss.collaboration.WorkspaceDocumentService#getWorkspaceMembers(java.lang.Long)
	 */
	protected java.util.List handleGetWorkspaceMembers(
			java.lang.Long workspaceId) throws java.lang.Exception {
		// @todo implement protected java.util.List
		// handleGetWorkspaceMembers(java.lang.Long workspaceId)
		List list = new ArrayList();
		return list;
	}

	@Override
	protected List handleFindWorkspacesByCourse(Long courseId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected WorkspaceInfo handleGetWorkspace(Long workspaceId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}