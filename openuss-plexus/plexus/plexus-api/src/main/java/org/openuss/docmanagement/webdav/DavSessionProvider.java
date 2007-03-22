package org.openuss.docmanagement.webdav;

import javax.jcr.Repository;
import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.AuthenticationManager;
import org.openuss.desktop.DesktopService;
import org.openuss.security.SecurityService;

/**
 * @author David Ullrich <lechuck@uni-muenster.de>
 * @version 0.9
 */
public interface DavSessionProvider {
	/**
	 * Retrieves user credentials from {@link HttpServletRequest}, verifies valid username password
	 * combination, retrieves list of subscribed {@link Enrollment}s and creates a {@link Session}
	 * with the {@link Repository}.
	 * The list of subscriptions and the session are turned over to the {@link DavService}.
	 * @param request Reference to the request of the servlet.
	 * @param davService The service class.
	 * @return True, if attaching session was successful.
	 * @throws DavException
	 */
	public boolean attachSession(HttpServletRequest request, DavService davService) throws DavException;

	/**
	 * Closes {@link Session} and removes it from service class.
	 * @param davService The service class.
	 */
	public void releaseSession(DavService davService);

	/**
	 * Getter for {@link Repository}.
	 * @return The Repository.
	 */
	public Repository getRepository();
	
	/**
	 * Setter for {@link Repository}.
	 * @param repository The Repository to set.
	 */
	public void setRepository(Repository repository);

	/**
	 * Getter for {@link AuthenticationManager}.
	 * @return The AuthenticationManager.
	 */
	public AuthenticationManager getAuthenticationManager();

	/**
	 * Setter for {@link AuthenticationManager}.
	 * @param authenticationManager The AuthenticationManager to set.
	 */
	public void setAuthenticationManager(AuthenticationManager authenticationManager);
	
	/**
	 * Getter for {@link SecurityService}.
	 * @return The SecurityService.
	 */
	public SecurityService getSecurityService();
	
	/**
	 * Setter for {@link SecurityService}.
	 * @param securityService The SecurityService to set.
	 */
	public void setSecurityService(SecurityService securityService);
	
	/**
	 * Getter for {@link DesktopService}.
	 * @return The DesktopService.
	 */
	public DesktopService getDesktopService();
	
	/**
	 * Setter for {@link DesktopService}.
	 * @param desktopService The DesktopService to set.
	 */
	public void setDesktopService(DesktopService desktopService);
}
