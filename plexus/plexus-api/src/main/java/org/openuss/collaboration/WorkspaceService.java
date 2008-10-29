package org.openuss.collaboration;

import java.util.List;

import org.openuss.security.UserInfo;

/**
 * WorkspaceService is the central service for the workspace hierarchie
 * administration.
 * 
 * @author Projektseminar WS 07/08, Team Collaboration
 */
public interface WorkspaceService {

	/**
	 * Creates a new workspace under the given workspace info
	 * 
	 * @param workspaceInfo
	 *            the new info from the workspace to create
	 */
	public void createWorkspace(WorkspaceInfo workspaceInfo);

	/**
	 * Deletes an already created workspace under the given workspace id
	 * 
	 * @param workspaceId
	 *            the DB id from the workspace to remove
	 */
	public void removeWorkspace(Long workspaceId);

	/**
	 * Updates a workspace under the given workspace info
	 * 
	 * @param workspaceInfo
	 *            the info from the workspace with the new information to update
	 */
	public void updateWorkspace(org.openuss.collaboration.WorkspaceInfo workspaceInfo);

	/**
	 * Modifys an existing workspace in order to add or remove workspace members
	 * 
	 * @param userId
	 *            list of the user that belong to workspace
	 * @param workspaceId
	 *            id of the current workspace to modify
	 */
	public void updateWorkspaceMembers(List userId, Long workspaceId);

	/**
	 * find all members of a workspace
	 * 
	 * @param workspaceId
	 *            id of the current workspace
	 * @return a List of user-objects
	 */
	public List findWorkspaceMembers(Long workspaceId);

	/**
	 * finds all Workspaces associated with a domain object.
	 * 
	 * @param domainId
	 *            id of the current Course
	 * @return a List of all the workspaces linked to a Course
	 */
	public List findWorkspacesByDomain(Long domainId);

	/**
	 * get a WorkspaceInfo for a Id.
	 * 
	 * @param workspaceId
	 *            id of the current workspace
	 * @return a workspaceInfo according to a concrete Id
	 */
	public WorkspaceInfo getWorkspace(Long workspaceId);

	/**
	 * find Workspaces for a domain object and a courseMember.
	 * 
	 * @param domainId
	 *            a domain object with workspaces
	 * @param user
	 *            a UserInfo in the course
	 * @return a List of all workspaces of a domain a courseMember takes part
	 */
	public List findWorkspacesByDomainAndUser(Long domainId, UserInfo user);

	public List findWorkspacesByUser(org.openuss.security.User user);

}
