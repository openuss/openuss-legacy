package org.openuss.docmanagement;

import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.ItemExistsException;
import javax.jcr.LoginException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.openuss.docmanagement.RepositoryAccess;

/**
 * @author David Ullrich
 * @version 0.5
 */
public abstract class ResourceDao {

		public Session login(Repository repository) throws LoginException, RepositoryException {
		Session session = repository.login(new SimpleCredentials(
				RepositoryAccess.USERNAME, RepositoryAccess.PASSWORD.toCharArray()));
		return session;
	}

	public void logout(Session session) throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException {
		session.save();
		session.logout();
	}


}
