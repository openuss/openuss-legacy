package org.openuss.docmanagement.webdav;

import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	transient private Repository repository;

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavSessionProvider#attachSession(org.apache.jackrabbit.webdav.WebdavRequest)
	 */
	public boolean attachSession(HttpServletRequest request, DavService davService) throws DavException {
		try {
			// retrieve user authentication information from request
			Credentials userCredentials = getCredentials(request);
			if (userCredentials == null) {
				// user authentication to OpenUSS failed
				throw new DavException(HttpServletResponse.SC_UNAUTHORIZED);
			}

			// try to authenticate user to system
			// TODO Benutzer gegen OpenUSS authentifizieren

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
		} catch (NoSuchWorkspaceException ex) {
			// workspace not found
			logger.debug("NoSuchWorkspace exception occurred.");
			logger.debug("Exception: " + ex.getMessage());
			// rethrow exception as DavException
			throw new DavException(DavServletResponse.SC_NOT_FOUND, ex.getMessage());
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

	/* (non-Javadoc)
	 * @see org.apache.jackrabbit.webdav.DavSessionProvider#releaseSession(org.apache.jackrabbit.webdav.WebdavRequest)
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
	private Credentials getCredentials(HttpServletRequest request) {
		// TODO credentials aus Request extrahieren
		return new SimpleCredentials("username", "geheim".toCharArray());
	}
	
	/**
	 * @return
	 */
	public Repository getRepository() {
		if (repository == null) {
			try {
				InitialContext context = new InitialContext();
				Context environment = (Context) context.lookup("java:comp/env");
				repository = (Repository) environment.lookup("jcr/repository");
			} catch (Exception ex) {
				
			}
		}
		return repository;
	}
	
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
}
