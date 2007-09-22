// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.desktop;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.Institute;
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
		Validate.notNull(desktop, "Parameter desktop must not be null!");
		Validate.notNull(desktop.getId(),"Parameter desktop must provide a valid id.");
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
	protected void handleLinkInstitute(Desktop desktop, Institute institute) throws Exception {
		if (institute == null) {
			throw new NullPointerException("Institute must not be null!");
		}
		desktop = getDesktop(desktop);
		if (!desktop.getInstitutes().contains(institute)) {
			desktop.linkInstitute(institute);
			saveDesktop(desktop);
		}
	}

	@Override
	protected void handleLinkCourseType(Desktop desktop, CourseType courseType) throws Exception {
		if (courseType == null) {
			throw new NullPointerException("CourseType must not be null!");
		}
		desktop = getDesktop(desktop);
		if (!desktop.getCourseTypes().contains(courseType)) {
			desktop.linkCourseType(courseType);
			saveDesktop(desktop);
		}
	}

	@Override
	protected void handleLinkCourse(Desktop desktop, Course course) throws Exception {
		if (course == null) {
			throw new NullPointerException("Course must not be null!");
		}
		desktop = getDesktop(desktop);
		if (!desktop.getCourses().contains(course)) {
			desktop.linkCourse(course);
			saveDesktop(desktop);
		}
	}

	@Override
	protected void handleUnlinkInstitute(Desktop desktop, Institute institute) throws Exception {
		desktop = getDesktop(desktop);
		institute = getInstituteDao().load(institute.getId());
		if (desktop.getUser().equals(institute.getOwner())) {
			throw new DesktopException("error_must_not_remove_owned_institute");
		}
		desktop.unlinkInstitute(institute);
		saveDesktop(desktop);
	}

	@Override
	protected void handleUnlinkCourseType(Desktop desktop, CourseType courseType) throws Exception {
		desktop.unlinkCourseType(courseType);
		saveDesktop(desktop);
	}

	@Override
	protected void handleUnlinkCourse(Desktop desktop, Course course) throws Exception {
		desktop.unlinkCourse(course);
		saveDesktop(desktop);
	}

	@Override
	protected void handleUnlinkAllFromCourse(Course course) throws Exception {
		Collection<Desktop> desktops = getDesktopDao().findByCourse(course);
		for (Desktop desktop : desktops) {
			unlinkCourse(desktop, course);
		}
	}

	@Override
	protected void handleUnlinkAllFromInstitute(Institute institute) throws Exception {
		Collection<Desktop> desktops = getDesktopDao().findByInstitute(institute);
		for (Desktop desktop : desktops) {
			unlinkInstitute(desktop, institute);
		}
	}

	@Override
	protected void handleUnlinkAllFromCourseType(CourseType courseType) throws Exception {
		Collection<Desktop> desktops = getDesktopDao().findByCourseType(courseType);
		for (Desktop desktop: desktops) {
			unlinkCourseType(desktop, courseType);
		}
	}
}