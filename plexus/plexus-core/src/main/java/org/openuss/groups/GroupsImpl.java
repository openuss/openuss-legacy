// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.groups;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.CallbackException;
import org.hibernate.Session;
import org.hibernate.classic.Lifecycle;
import org.openuss.lecture.CourseImpl;

/**
 * @see org.openuss.groups.Groups
 */
public class GroupsImpl extends GroupsBase implements Groups, Lifecycle {

	private static final Logger logger = Logger.getLogger(CourseImpl.class);
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 1090396615520890497L;

	/**
	 * @see org.openuss.groups.Groups#isPasswordCorrect(java.lang.String)
	 */
	public java.lang.Boolean isPasswordCorrect(java.lang.String password) {
		return StringUtils.equalsIgnoreCase(getPassword(), password);
	}

	public boolean onDelete(Session arg0) throws CallbackException {
		return false;
	}

	public void onLoad(Session arg0, Serializable arg1) {
	}

	public boolean onSave(Session arg0) throws CallbackException {
		return false;
	}

	public boolean onUpdate(Session arg0) throws CallbackException {
		return false;
	}

}