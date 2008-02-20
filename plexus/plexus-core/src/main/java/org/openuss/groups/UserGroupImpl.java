// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.groups;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.hibernate.CallbackException;
import org.hibernate.Session;
import org.hibernate.classic.Lifecycle;

/**
 * @see org.openuss.groups.UserGroup
 * @author Lutz D. Kramer
 */
public class UserGroupImpl extends UserGroupBase implements Lifecycle, UserGroup {
	
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 4662369621392780664L;

	/**
	 * @see org.openuss.groups.UserGroup#isPasswordCorrect(java.lang.String)
	 */
	public Boolean isPasswordCorrect(String password) {
		return StringUtils.equalsIgnoreCase(getPassword(), password);
	}

	public boolean onDelete(Session s) throws CallbackException {
		return false;
	}

	public void onLoad(Session s, Serializable id) {
	}

	public boolean onSave(Session s) throws CallbackException {
		return false;
	}

	public boolean onUpdate(Session s) throws CallbackException {
		return false;
	}
}