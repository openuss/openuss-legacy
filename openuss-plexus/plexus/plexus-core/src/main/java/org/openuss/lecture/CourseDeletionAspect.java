package org.openuss.lecture;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopService2;

/**
 * Aspect for Course Creation and Deletion.
 * @author Florian Dondorf
 */
public class CourseDeletionAspect {

	private static final Logger logger = Logger.getLogger(CourseDeletionAspect.class);
	private DesktopService2 desktopService;
	
	public void deleteBookmarksOfCourse (Long courseId) {
		logger.debug("----------> BEGIN method deleteBookmarksOfCourses <----------");
		
		Validate.notNull(courseId, "CourseCreationAspect.bookmarkCourse - the userId cannot be null.");
		
		try {
			// Unlink all 
			desktopService.unlinkAllFromCourse(courseId);
		} catch (DesktopException de) {
			logger.error(de.getMessage());
		}
		
		logger.debug("----------> End method deleteBookmarksOfCourses <----------");
	}

	public DesktopService2 getDesktopService() {
		return desktopService;
	}

	public void setDesktopService(DesktopService2 desktopService) {
		this.desktopService = desktopService;
	}

}
