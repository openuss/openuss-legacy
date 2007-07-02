package org.openuss.desktop.lecture;

import org.apache.log4j.Logger;
import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopService;
import org.openuss.lecture.Course;
import org.openuss.lecture.Institute;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.LectureListener;
import org.openuss.lecture.LectureService;
import org.openuss.lecture.LectureServiceException;
import org.openuss.lecture.CourseType;
import org.openuss.security.User;


/**
 * Listener for Lecture Desktop Connection. 
 * @author Ingo Dueppe
 */
public class DesktopLectureAdapter implements LectureListener{

	private static final Logger logger = Logger.getLogger(DesktopLectureAdapter.class);
	
	private DesktopService desktopService;
	
	private LectureService lectureService;
	
	public void removingCourse(Course course) throws LectureException {
		if (desktopService == null) {
			throw new IllegalStateException("desktopService property must not be null!");
		}
		try {
			desktopService.unlinkAllFromCourse(course);
		} catch (DesktopException e) {
			throw new LectureException("Desktop Lecture Adapter couldn't unlink course", e);
		}
	}

	public void removingInstitute(Institute institute) throws LectureException {
		if (desktopService == null) {
			throw new IllegalStateException("desktopService property must not be null!");
		}
		try {
			desktopService.unlinkAllFromInstitute(institute);
		} catch (DesktopException e) {
			throw new LectureException("Desktop Lecture Adapter couldn't unlink institute", e);
		}
		
	}

	public void removingCourseType(CourseType courseType) throws LectureException {
		if (desktopService == null) {
			throw new IllegalStateException("desktopService property must not be null!");
		}
		try {
			desktopService.unlinkAllFromCourseType(courseType);
		} catch (DesktopException e) {
			throw new LectureException("Desktop Lecture Adapter couldn't unlink courseType",e);
		}
		
	}

	public DesktopService getDesktopService() {
		return desktopService;
	}
	
	public void setDesktopService(DesktopService desktopService) {
		this.desktopService = desktopService;
	}
	
	public LectureService getLectureService() {
		return lectureService;
	}
	
	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
		if (lectureService != null) {
			try {
				lectureService.registerListener(this);
				logger.info("register listener for lecture service");
			} catch (LectureServiceException e) {
				logger.error("Desktop adapter couldn't register listener at lecture service ",e);
			}
		}
	}

	public void createdInstitute(Institute institute) throws LectureException {
		try {
			User user = institute.getOwner();
			Desktop desktop = desktopService.getDesktopByUser(user);
			desktopService.linkInstitute(desktop, institute);
		} catch (DesktopException e) {
			logger.error("Desktop adapter couldn't create new institute link on user desktop",e);
			throw new LectureException(e);
		}
		
	}

	public void updateInstitute(Institute institute) throws LectureException {
		// NOTHING TO DO
	}
}
