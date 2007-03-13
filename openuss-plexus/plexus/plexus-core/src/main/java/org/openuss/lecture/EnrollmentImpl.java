// OpenUSS - Open Source University Support System
//
// Attention: Generated code! Do not modify by hand!
// Generated by: HibernateEntity.vsl in andromda-hibernate-cartridge.
//
package org.openuss.lecture;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;

/**
 * @see org.openuss.lecture.Enrollment
 */
public class EnrollmentImpl extends org.openuss.lecture.EnrollmentBase implements org.openuss.lecture.Enrollment,	SaveOrUpdateEventListener {

	private static final Logger logger = Logger.getLogger(EnrollmentImpl.class);

	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -5670314763540215921L;

	/**
	 * Generates a guid shortcut on persisting if no shortcut is already set.
	 */
	private void generateShortcut() {
		logger.debug("auto-generate shortcut for enrollment");
		// FIXME make this method robust against unique key violations
		String subjectShortcut = getSubject().getShortcut();
		String id = String.valueOf(this.getId());
		int index = subjectShortcut.length();
		if (index + id.length() >= 30)
			index -= id.length();
		String shortcut = subjectShortcut.substring(0, index - 1) + id;
		this.setShortcut(shortcut);

	}

	public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
		if (StringUtils.isBlank(getShortcut())) {
			generateShortcut();
		}
	}

	@Override
	public boolean isPasswordCorrect(String password) {
		return StringUtils.equalsIgnoreCase(getPassword(), password);
	}

}