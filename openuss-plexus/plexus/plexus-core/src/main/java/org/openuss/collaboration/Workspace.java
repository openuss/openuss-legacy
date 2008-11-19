package org.openuss.collaboration;

import java.util.Set;

import org.openuss.foundation.DomainObject;
import org.openuss.security.User;

/**
 * A workspace is a specific collaboration workspace that is made linked to an
 * existing course defined period of time (like a specific semester) by one
 * institute. A Course can have as much workspaces as they want.
 * 
 * @author Projektseminar WS 07/08, Team Collaboration
 */
public interface Workspace extends DomainObject {

	/**
	 * ID workspace identifier
	 */
	public Long getId();

	public void setId(Long id);

	/**
	 * ID of the Domain the workspaqce belongs to
	 */
	public Long getDomainId();

	public void setDomainId(Long domainId);

	/**
	 * specific name of each workspace
	 */
	public String getName();

	public void setName(String name);

	public Set<User> getUser();

	public void setUser(Set<User> user);

}