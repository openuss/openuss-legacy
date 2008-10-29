package org.openuss.collaboration;

import org.openuss.security.SecurityService;

/**
 * <p>
 * Spring Service base class for
 * <code>org.openuss.collaboration.WorkspaceService</code>, provides access to
 * all services and entities referenced by this service.
 * </p>
 * 
 * @see org.openuss.collaboration.WorkspaceService
 */
public abstract class WorkspaceServiceBase implements WorkspaceService {

	private SecurityService securityService;

	/**
	 * Sets the reference to <code>securityService</code>.
	 */
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	/**
	 * Gets the reference to <code>securityService</code>.
	 */
	protected org.openuss.security.SecurityService getSecurityService() {
		return this.securityService;
	}

	private org.openuss.documents.DocumentService documentService;

	/**
	 * Sets the reference to <code>documentService</code>.
	 */
	public void setDocumentService(org.openuss.documents.DocumentService documentService) {
		this.documentService = documentService;
	}

	/**
	 * Gets the reference to <code>documentService</code>.
	 */
	protected org.openuss.documents.DocumentService getDocumentService() {
		return this.documentService;
	}

	private org.openuss.lecture.CourseService courseService;

	/**
	 * Sets the reference to <code>courseService</code>.
	 */
	public void setCourseService(org.openuss.lecture.CourseService courseService) {
		this.courseService = courseService;
	}

	/**
	 * Gets the reference to <code>courseService</code>.
	 */
	protected org.openuss.lecture.CourseService getCourseService() {
		return this.courseService;
	}

	private org.openuss.collaboration.WorkspaceDao workspaceDao;

	/**
	 * Sets the reference to <code>workspace</code>'s DAO.
	 */
	public void setWorkspaceDao(org.openuss.collaboration.WorkspaceDao workspaceDao) {
		this.workspaceDao = workspaceDao;
	}

	/**
	 * Gets the reference to <code>workspace</code>'s DAO.
	 */
	protected org.openuss.collaboration.WorkspaceDao getWorkspaceDao() {
		return this.workspaceDao;
	}

	private org.openuss.security.UserDao userDao;

	/**
	 * Sets the reference to <code>user</code>'s DAO.
	 */
	public void setUserDao(org.openuss.security.UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * Gets the reference to <code>user</code>'s DAO.
	 */
	protected org.openuss.security.UserDao getUserDao() {
		return this.userDao;
	}

	/**
	 * @see org.openuss.collaboration.WorkspaceService#createWorkspace(org.openuss.collaboration.WorkspaceInfo)
	 */
	public void createWorkspace(org.openuss.collaboration.WorkspaceInfo workspaceInfo) {
		try {
			this.handleCreateWorkspace(workspaceInfo);
		} catch (Throwable th) {
			throw new org.openuss.collaboration.WorkspaceServiceException(
					"Error performing 'org.openuss.collaboration.WorkspaceService.createWorkspace(org.openuss.collaboration.WorkspaceInfo workspaceInfo)' --> "
							+ th, th);
		}
	}

	/**
	 * Performs the core logic for
	 * {@link #createWorkspace(org.openuss.collaboration.WorkspaceInfo)}
	 */
	protected abstract void handleCreateWorkspace(org.openuss.collaboration.WorkspaceInfo workspaceInfo)
			throws java.lang.Exception;

	/**
	 * @see org.openuss.collaboration.WorkspaceService#removeWorkspace(java.lang.Long)
	 */
	public void removeWorkspace(java.lang.Long workspaceId) {
		try {
			this.handleRemoveWorkspace(workspaceId);
		} catch (Throwable th) {
			throw new org.openuss.collaboration.WorkspaceServiceException(
					"Error performing 'org.openuss.collaboration.WorkspaceService.removeWorkspace(java.lang.Long workspaceId)' --> "
							+ th, th);
		}
	}

	/**
	 * Performs the core logic for {@link #removeWorkspace(java.lang.Long)}
	 */
	protected abstract void handleRemoveWorkspace(java.lang.Long workspaceId) throws java.lang.Exception;

	/**
	 * @see org.openuss.collaboration.WorkspaceService#updateWorkspace(org.openuss.collaboration.WorkspaceInfo)
	 */
	public void updateWorkspace(org.openuss.collaboration.WorkspaceInfo workspaceInfo) {
		try {
			this.handleUpdateWorkspace(workspaceInfo);
		} catch (Throwable th) {
			throw new org.openuss.collaboration.WorkspaceServiceException(
					"Error performing 'org.openuss.collaboration.WorkspaceService.updateWorkspace(org.openuss.collaboration.WorkspaceInfo workspaceInfo)' --> "
							+ th, th);
		}
	}

	/**
	 * Performs the core logic for
	 * {@link #updateWorkspace(org.openuss.collaboration.WorkspaceInfo)}
	 */
	protected abstract void handleUpdateWorkspace(org.openuss.collaboration.WorkspaceInfo workspaceInfo)
			throws java.lang.Exception;

	/**
	 * @see org.openuss.collaboration.WorkspaceService#updateWorkspaceMembers(java.util.List,
	 *      java.lang.Long)
	 */
	public void updateWorkspaceMembers(java.util.List userId, java.lang.Long workspaceId) {
		try {
			this.handleUpdateWorkspaceMembers(userId, workspaceId);
		} catch (Throwable th) {
			throw new org.openuss.collaboration.WorkspaceServiceException(
					"Error performing 'org.openuss.collaboration.WorkspaceService.updateWorkspaceMembers(java.util.List userId, java.lang.Long workspaceId)' --> "
							+ th, th);
		}
	}

	/**
	 * Performs the core logic for
	 * {@link #updateWorkspaceMembers(java.util.List, java.lang.Long)}
	 */
	protected abstract void handleUpdateWorkspaceMembers(java.util.List userId, java.lang.Long workspaceId)
			throws java.lang.Exception;

	/**
	 * @see org.openuss.collaboration.WorkspaceService#findWorkspaceMembers(java.lang.Long)
	 */
	public java.util.List findWorkspaceMembers(java.lang.Long workspaceId) {
		try {
			return this.handleFindWorkspaceMembers(workspaceId);
		} catch (Throwable th) {
			throw new org.openuss.collaboration.WorkspaceServiceException(
					"Error performing 'org.openuss.collaboration.WorkspaceService.findWorkspaceMembers(java.lang.Long workspaceId)' --> "
							+ th, th);
		}
	}

	/**
	 * Performs the core logic for {@link #findWorkspaceMembers(java.lang.Long)}
	 */
	protected abstract java.util.List handleFindWorkspaceMembers(java.lang.Long workspaceId) throws java.lang.Exception;

	/**
	 * @see org.openuss.collaboration.WorkspaceService#findWorkspacesByDomain(java.lang.Long)
	 */
	public java.util.List findWorkspacesByDomain(java.lang.Long domainId) {
		try {
			return this.handleFindWorkspacesByDomain(domainId);
		} catch (Throwable th) {
			throw new org.openuss.collaboration.WorkspaceServiceException(
					"Error performing 'org.openuss.collaboration.WorkspaceService.findWorkspacesByDomain(java.lang.Long domainId)' --> "
							+ th, th);
		}
	}

	/**
	 * Performs the core logic for
	 * {@link #findWorkspacesByDomain(java.lang.Long)}
	 */
	protected abstract java.util.List handleFindWorkspacesByDomain(java.lang.Long domainId) throws java.lang.Exception;

	/**
	 * @see org.openuss.collaboration.WorkspaceService#getWorkspace(java.lang.Long)
	 */
	public org.openuss.collaboration.WorkspaceInfo getWorkspace(java.lang.Long workspaceId) {
		try {
			return this.handleGetWorkspace(workspaceId);
		} catch (Throwable th) {
			throw new org.openuss.collaboration.WorkspaceServiceException(
					"Error performing 'org.openuss.collaboration.WorkspaceService.getWorkspace(java.lang.Long workspaceId)' --> "
							+ th, th);
		}
	}

	/**
	 * Performs the core logic for {@link #getWorkspace(java.lang.Long)}
	 */
	protected abstract org.openuss.collaboration.WorkspaceInfo handleGetWorkspace(java.lang.Long workspaceId)
			throws java.lang.Exception;

	/**
	 * @see org.openuss.collaboration.WorkspaceService#findWorkspacesByDomainAndUser(java.lang.Long,
	 *      org.openuss.security.UserInfo)
	 */
	public java.util.List findWorkspacesByDomainAndUser(java.lang.Long domainId, org.openuss.security.UserInfo user) {
		try {
			return this.handleFindWorkspacesByDomainAndUser(domainId, user);
		} catch (Throwable th) {
			throw new org.openuss.collaboration.WorkspaceServiceException(
					"Error performing 'org.openuss.collaboration.WorkspaceService.findWorkspacesByDomainAndUser(java.lang.Long domainId, org.openuss.security.UserInfo user)' --> "
							+ th, th);
		}
	}

	/**
	 * Performs the core logic for
	 * {@link #findWorkspacesByDomainAndUser(java.lang.Long, org.openuss.security.UserInfo)}
	 */
	protected abstract java.util.List handleFindWorkspacesByDomainAndUser(java.lang.Long domainId,
			org.openuss.security.UserInfo user) throws java.lang.Exception;

	/**
	 * @see org.openuss.collaboration.WorkspaceService#findWorkspacesByUser(org.openuss.security.User)
	 */
	public java.util.List findWorkspacesByUser(org.openuss.security.User user) {
		try {
			return this.handleFindWorkspacesByUser(user);
		} catch (Throwable th) {
			throw new org.openuss.collaboration.WorkspaceServiceException(
					"Error performing 'org.openuss.collaboration.WorkspaceService.findWorkspacesByUser(org.openuss.security.User user)' --> "
							+ th, th);
		}
	}

	/**
	 * Performs the core logic for
	 * {@link #findWorkspacesByUser(org.openuss.security.User)}
	 */
	protected abstract java.util.List handleFindWorkspacesByUser(org.openuss.security.User user)
			throws java.lang.Exception;

	/**
	 * Gets the current <code>principal</code> if one has been set, otherwise
	 * returns <code>null</code>.
	 * 
	 * @return the current principal
	 */
	protected java.security.Principal getPrincipal() {
		return org.andromda.spring.PrincipalStore.get();
	}
}