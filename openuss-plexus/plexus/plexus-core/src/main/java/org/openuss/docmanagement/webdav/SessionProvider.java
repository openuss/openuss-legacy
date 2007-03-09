package org.openuss.docmanagement.webdav;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.AuthenticationManager;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.DisabledException;
import org.acegisecurity.LockedException;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.ui.WebAuthenticationDetails;
import org.apache.jackrabbit.util.Base64;
import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavServletResponse;
import org.apache.log4j.Logger;

/**
 * @author David Ullrich
 * @version 0.6
 */
public class SessionProvider {
	private final Logger logger = Logger.getLogger(SessionProvider.class);
	
	private final String REPOSITORY_USERNAME = "username";
	private final String REPOSITORY_PASSWORD = "password";
	private Repository repository;
	private AuthenticationManager authenticationManager;

	/**
	 * @param request
	 * @param davService
	 * @return
	 * @throws DavException
	 */
	public boolean attachSession(HttpServletRequest request, DavService davService) throws DavException {
		try {
			// retrieve user authentication information from request
			SimpleCredentials userCredentials = getCredentials(request);
			if (userCredentials == null) {
				// user authentication missing
				throw new DavException(HttpServletResponse.SC_UNAUTHORIZED);
			}
			logger.debug("Authentication requested for user '" + userCredentials.getUserID() + "'");

			// try to authenticate user to system
			Authentication authentication;
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userCredentials.getUserID(), new String(userCredentials.getPassword()));
			authentication = getAuthenticationManager().authenticate(authenticationToken);
			logger.debug("Authentication successful: " + (authentication != null));

			// TODO authentication object verwenden

			// establish session with repository
			Credentials repositoryCredentials = new SimpleCredentials(REPOSITORY_USERNAME, REPOSITORY_PASSWORD.toCharArray());
			Session session = getRepository().login(repositoryCredentials);

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
			throw new DavException(DavServletResponse.SC_UNAUTHORIZED, ex.getMessage());
		} catch (ServletException ex) {
			// invalid or not supported credentials
			logger.debug("Servlet exception occurred.");
			logger.debug("Exception: " + ex.getMessage());
			// rethrow exception as DavException -> bad request
			throw new DavException(DavServletResponse.SC_BAD_REQUEST, ex.getMessage());
		} catch (LoginException ex) {
			// login to repository failed -> service unavailable
			logger.debug("Login exception occurred.");
			logger.debug("Exception: " + ex.getMessage());
			// rethrow exception as DavException
			throw new DavException(DavServletResponse.SC_SERVICE_UNAVAILABLE, ex.getMessage());
		} catch (RepositoryException ex) {
			// undefined repository exception -> service unavailable
			logger.debug("Repository exception occurred.");
			logger.debug("Exception: " + ex.getMessage());
			// rethrow exception as DavException
			throw new DavException(DavServletResponse.SC_SERVICE_UNAVAILABLE, ex.getMessage());
		}
	}

	/**
	 * @param davService
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
	 * @param request
	 * @return
	 */
	private SimpleCredentials getCredentials(HttpServletRequest request) throws ServletException {
		// get value of authorization header field
		String authorizationHeader = request.getHeader("Authorization");
		
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
						throw new ServletException("Missing username or password.");
					}
					// strip username and password from decoded credentials
					String username = decodedCredentials.substring(0, position);
					String password = decodedCredentials.substring(position + 1);
					// create new instance of simple credentials and return it
					return new SimpleCredentials(username, password.toCharArray());
				} catch (IOException ex) {
					// rethrow exception as servlet exception
					throw new ServletException(ex.getMessage());
				}
			}
			// invalid format of authorization header
			throw new ServletException("Invalid or not supported value of authorization header.");
		}
		// no authorization information found in request
		return null;
	}
	
	/**
	 * @return
	 */
	public Repository getRepository() {
		return repository;
	}
	
	/**
	 * @param repository
	 */
	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	/**
	 * @return the authenticationManager
	 */
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	/**
	 * @param authenticationManager the authenticationManager to set
	 */
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
}
