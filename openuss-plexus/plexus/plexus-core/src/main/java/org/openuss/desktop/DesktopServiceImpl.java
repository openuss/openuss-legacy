// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.desktop;

import org.apache.log4j.Logger;

import java.util.Collection;

import org.openuss.lecture.Enrollment;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.Subject;
import org.openuss.security.User;

/**
 * @see org.openuss.desktop.DesktopService
 * 
 * @author Ingo Dueppe
 */
public class DesktopServiceImpl extends org.openuss.desktop.DesktopServiceBase {

	private static final Logger logger = Logger.getLogger(DesktopServiceImpl.class);
	
	@Override
	protected Desktop handleCreateDesktop(final User user) throws Exception {
		Desktop desktop = new DesktopImpl();
		desktop.setUser(user);
		getDesktopDao().create(desktop);
		return desktop;
	}

	@Override
	protected Desktop handleGetDesktop(Desktop desktop) throws Exception {
		desktop = getDesktopDao().load(desktop.getId());
		return desktop;
	}

	@Override
	protected Desktop handleGetDesktopByUser(User user) throws Exception {
		Desktop desktop = getDesktopDao().findByUser(user);
		if (desktop == null) {
			// create new desktop
			if (logger.isDebugEnabled()) {
				logger.debug("desktop doesn't exist for user, create new one");
			}
			desktop = createDesktop(user);
		}
		return desktop;
	}

	@Override
	protected void handleSaveDesktop(Desktop desktop) throws Exception {
		if (desktop.getId() != null) {
			getDesktopDao().update(desktop);
		} else {
			getDesktopDao().create(desktop);
		}
	}

	@Override
	protected void handleLinkFaculty(Desktop desktop, Faculty faculty) throws Exception {
		if (faculty == null) {
			throw new NullPointerException("Faculty must not be null!");
		}
		desktop = getDesktop(desktop);
		if (!desktop.getFaculties().contains(faculty)) {
			desktop.linkFaculty(faculty);
			saveDesktop(desktop);
		}
	}

	@Override
	protected void handleLinkSubject(Desktop desktop, Subject subject) throws Exception {
		if (subject == null) {
			throw new NullPointerException("Subject must not be null!");
		}
		desktop = getDesktop(desktop);
		if (!desktop.getSubjects().contains(subject)) {
			desktop.linkSubject(subject);
			saveDesktop(desktop);
		}
	}

	@Override
	protected void handleLinkEnrollment(Desktop desktop, Enrollment enrollment) throws Exception {
		if (enrollment == null) {
			throw new NullPointerException("Enrollment must not be null!");
		}
		desktop = getDesktop(desktop);
		if (!desktop.getEnrollments().contains(enrollment)) {
			desktop.linkEnrollment(enrollment);
			saveDesktop(desktop);
		}
	}

	@Override
	protected void handleUnlinkFaculty(Desktop desktop, Faculty faculty) throws Exception {
		desktop = getDesktop(desktop);
		faculty = getFacultyDao().load(faculty.getId());
		if (desktop.getUser().equals(faculty.getOwner())) {
			throw new DesktopException("error_must_not_remove_owned_faculty");
		}
		desktop.unlinkFaculty(faculty);
		saveDesktop(desktop);
	}

	@Override
	protected void handleUnlinkSubject(Desktop desktop, Subject subject) throws Exception {
		desktop.unlinkSubject(subject);
		saveDesktop(desktop);
	}

	@Override
	protected void handleUnlinkEnrollment(Desktop desktop, Enrollment enrollment) throws Exception {
		desktop.unlinkEnrollment(enrollment);
		saveDesktop(desktop);
	}

	@Override
	protected void handleUnlinkAllFromEnrollment(Enrollment enrollment) throws Exception {
		Collection<Desktop> desktops = getDesktopDao().findByEnrollment(enrollment);
		for (Desktop desktop : desktops) {
			unlinkEnrollment(desktop, enrollment);
		}
	}

	@Override
	protected void handleUnlinkAllFromFaculty(Faculty faculty) throws Exception {
		Collection<Desktop> desktops = getDesktopDao().findByFaculty(faculty);
		for (Desktop desktop : desktops) {
			unlinkFaculty(desktop, faculty);
		}
	}

	@Override
	protected void handleUnlinkAllFromSubject(Subject subject) throws Exception {
		Collection<Desktop> desktops = getDesktopDao().findBySubject(subject);
		for (Desktop desktop: desktops) {
			unlinkSubject(desktop, subject);
		}
	}
}