package org.openuss.docmanagement.webdav;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.AuthenticationManager;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.jackrabbit.util.Base64;
import org.apache.log4j.Logger;
import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopService;
import org.openuss.lecture.Enrollment;
import org.openuss.security.SecurityService;
import org.openuss.security.User;

/**
 * @author David Ullrich
 * @version 0.8
 */
public class DavSessionProviderImpl implements DavSessionProvider {
	private final Logger logger = Logger.getLogger(DavSessionProviderImpl.class);
	
	// TODO auslagern in Konfigurationsdatei
	private static final String REPOSITORY_USERNAME = "username";
	private static final String REPOSITORY_PASSWORD = "password";
	
	private Repository repository;
	private AuthenticationManager authenticationManager;
	private SecurityService securityService;
	private DesktopService desktopService;

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavSessionProvider#attachSession(javax.servlet.http.HttpServletRequest, org.openuss.docmanagement.webdav.DavService)
	 */
	public boolean attachSession(HttpServletRequest request, DavService davService) throws DavException {
		try {
			// retrieve user authentication information from request
			SimpleCredentials userCredentials = getCredentials(request);
			if (userCredentials == null) {
				// user authentication missing
				throw new DavException(HttpStatus.SC_UNAUTHORIZED);
			}
			logger.debug("Authentication requested for user '" + userCredentials.getUserID() + "'");

			// try to authenticate user to system
			Authentication authentication;
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userCredentials.getUserID(), new String(userCredentials.getPassword()));
			authentication = getAuthenticationManager().authenticate(authenticationToken);
			logger.debug("Authentication successful: " + (authentication != null));

			// retrieve subscribed enrollments for user an attach them to service class
			davService.setSubscribedEnrollments(getSubscribedEnrollments(userCredentials.getUserID()));

			// establish session with repository
			Credentials repositoryCredentials = new SimpleCredentials(REPOSITORY_USERNAME, REPOSITORY_PASSWORD.toCharArray());
			Session session = getRepository().login(repositoryCredentials);

			// check, if login to repository was successful
			if (session == null) {
				logger.debug("Could not establish a session with repository.");
				return false;
			}
			
			// attach Session to request
			logger.debug("Attaching session '" + session + "'.");
			davService.setSession(session);
			
			// Session successfully attached
			return true;
		} catch (AuthenticationException ex) {
			// authentication failed
			logger.debug("Authentication exception occurred.");
			logger.debug("Exception: " + ex.getMessage());
			// rethrow exception as DavException -> request username password
			throw new DavException(HttpStatus.SC_UNAUTHORIZED, ex.getMessage());
		} catch (LoginException ex) {
			// login to repository failed -> internal server error
			logger.error("Login exception occurred.");
			logger.error("Exception: " + ex.getMessage());
			// rethrow exception as DavException
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		} catch (RepositoryException ex) {
			// undefined repository exception -> service unavailable
			logger.error("Repository exception occurred.");
			logger.error("Exception: " + ex.getMessage());
			// rethrow exception as DavException
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see org.openuss.docmanagement.webdav.DavSessionProvider#releaseSession(org.openuss.docmanagement.webdav.DavService)
	 */
	public void releaseSession(DavService davService) {
		// retrieve Session from Service
		Session session = davService.getSession();
		
		// test, if Session is not null
		if (session != null) {
			// retrieve names of lock tokens and release them
			String[] lockTokens = session.getLockTokens();
            for (int i = 0; i < lockTokens.length; i++) {
            	session.removeLockToken(lockTokens[i]);
            }

            // close session with repository
            session.logout();
            logger.debug("Releasing session '" + session + "'.");
		}

		// release DavSession from request
		davService.setSession(null);
	}
	
	/**
	 * Retrieves the user credentials from a {@link HttpServletRequest}.
	 * Only basic user authentication is supported. See RFC2617 for further details.
	 * @param request Reference to the request of the servlet.
	 * @return The decoded SimpleCredentials.
	 * @throws DavException
	 */
	private SimpleCredentials getCredentials(HttpServletRequest request) throws DavException {
		// get value of authorization header field
		String authorizationHeader = request.getHeader("Authorization");
		
		// header has to be present
		if (authorizationHeader != null) {
			// field was set -> decode
			String[] headerFields = authorizationHeader.split(" ");
			
			// authorization header has to be like 'Basic credentials'
			if ((headerFields.length >= 2) && headerFields[0].equalsIgnoreCase(HttpServletRequest.BASIC_AUTH)) {
				// credentials are base64 encoded
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				
				try {
					// decode credentials
					Base64.decode(headerFields[1], outputStream);
					String decodedCredentials = outputStream.toString("ISO-8859-1");
					
					// decoded credentials should be like 'username:password'
					int position = decodedCredentials.indexOf(":");
					
					// minimum length for username and password is 1
					if ((position < 1) || (position >= (decodedCredentials.length() - 1))) {
						throw new DavException(HttpStatus.SC_BAD_REQUEST, "Missing username or password.");
					}
					
					// strip username and password from decoded credentials
					String username = decodedCredentials.substring(0, position);
					String password = decodedCredentials.substring(position + 1);
					
					// create new instance of simple credentials and return it
					return new SimpleCredentials(username, password.toCharArray());
				} catch (IOException ex) {
					logger.error("IO exception occurred.");
					logger.error("Exception: " + ex.getMessage());
					// rethrow exception as DavException
					throw new DavException(HttpStatus.SC_BAD_REQUEST, ex.getMessage());
				}
			}
			
			// invalid format of authorization header
			throw new DavException(HttpStatus.SC_BAD_REQUEST, "Invalid or not supported value of authorization header.");
		}
		
		// no authorization information found in request
		return null;
	}
	
	/**
	 * Retrieves the list of subscribed enrollments for a user.
	 * @param username The ID of the user whose subscribed enrollments to be retrieved.
	 * @return The list of subscribed enrollments.
	 * @throws DavException
	 */
	private List<Enrollment> getSubscribedEnrollments(String username) throws DavException {
		try {
			// get reference to user, his/her desktop and request subscribed enrollments
			User user = securityService.getUserByName(username);
			Desktop desktop = desktopService.getDesktopByUser(user);
			List<Enrollment> subscribedEnrollments = desktop.getEnrollments();
			logger.debug("List of subscribed enrollments: " + subscribedEnrollments.toString());
			
			return subscribedEnrollments;
		} catch (DesktopException ex) {
			// error while getting desktop for user -> internal server error
			logger.error("Login exception occurred.");
			logger.error("Exception: " + ex.getMessage());
			// rethrow exception as DavException -> internal server error
			throw new DavException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}
	
	/**
	 * Getter for {@link Repository}.
	 * @return The Repository.
	 */
	public Repository getRepository() {
		return repository;
	}
	
	/**
	 * Setter for {@link Repository}.
	 * @param repository The Repository to set.
	 */
	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	/**
	 * Getter for {@link AuthenticationManager}.
	 * @return The AuthenticationManager.
	 */
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	/**
	 * Setter for {@link AuthenticationManager}.
	 * @param authenticationManager The AuthenticationManager to set.
	 */
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	/**
	 * Getter for {@link SecurityService}.
	 * @return The SecurityService.
	 */
	public SecurityService getSecurityService() {
		return securityService;
	}
	
	/**
	 * Setter for {@link SecurityService}.
	 * @param securityService The SecurityService to set.
	 */
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	/**
	 * Getter for {@link DesktopService}.
	 * @return The DesktopService.
	 */
	public DesktopService getDesktopService() {
		return desktopService;
	}
	
	/**
	 * Setter for {@link DesktopService}.
	 * @param desktopService The DesktopService to set.
	 */
	public void setDesktopService(DesktopService desktopService) {
		this.desktopService = desktopService;
	}
}
