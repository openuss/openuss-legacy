// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.collaboration;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.discussion.Forum;
import org.openuss.discussion.ForumWatch;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseDao;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseType;

/**
 * @see org.openuss.collaboration.WorkspaceService
 */
public class WorkspaceServiceImpl extends
		org.openuss.collaboration.WorkspaceServiceBase {
	
	private static final Logger logger = Logger.getLogger(WorkspaceServiceImpl.class);

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
		Course course = this.getCourseDao().load(workspaceInfo.getCourseId());
		course.getWorkspaces().add(workspaceEntity);
		workspaceEntity.setCourse(course);
		
		// Save Entity
		this.getWorkspaceDao().create(workspaceEntity);
		Validate.notNull(workspaceEntity, "Id of workspace cannot be null.");
		
		// FIXME - Kai, Indexing should not base on VOs!
		// Kai: Do not delete this!!! Set id of institute VO for indexing
		// Update input parameter for aspects to get the right domain objects. 
		workspaceInfo.setId(workspaceEntity.getId());

		// add object identity to security
		getSecurityService().createObjectIdentity(workspaceEntity, workspaceEntity.getCourse());
		
		this.getCourseDao().update(course);

		// Set Security
	//FIXME: don't know what this does:	this.getSecurityService().createObjectIdentity(workspaceEntity, workspaceEntity.getCourseType());
		
//		updateAccessTypePermission(workspaceEntity);
	}

	/**
	 * @see org.openuss.collaboration.WorkspaceDocumentService#removeWorkspace(java.lang.Long)
	 */
	protected void handleRemoveWorkspace(java.lang.Long workspaceId)
			throws java.lang.Exception {
		Validate.notNull(workspaceId, "workspaceId cannot be null.");
		
		// Transform VO to entity
		Workspace workspaceEntity = this.getWorkspaceDao().load(workspaceId);

		getWorkspaceDao().remove(workspaceEntity);
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
		Validate.notNull(courseId, "courseId cannot be null.");
		System.out.println("============ courseId: " + courseId);
		Course course = this.getCourseDao().load(courseId);
		return this.getWorkspaceDao().findByCourse(WorkspaceDao.TRANSFORM_WORKSPACEINFO, course);
	}

	@Override
	protected WorkspaceInfo handleGetWorkspace(Long workspaceId)
			throws Exception {
		Validate.notNull(workspaceId, "Parameter workspaceId must not be null!");
		return (WorkspaceInfo)getWorkspaceDao().load(WorkspaceDao.TRANSFORM_WORKSPACEINFO, workspaceId);
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

}