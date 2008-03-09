package org.openuss.migration.from20to30;

import org.apache.log4j.Logger;

import java.util.Collection;

import org.openuss.lecture.Course;
import org.openuss.lecture.CourseDao;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;


/**
 * 
 * @author Ingo Dueppe
 */
public class PatchCoursePermission {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PatchCoursePermission.class);
	
	private CourseService courseService;
	
	private CourseDao courseDao;

	public void patch() {
		Collection<Course> courses = courseDao.loadAll();
		for (Course course: courses) {
			CourseInfo courseInfo = courseService.findCourse(course.getId());
			logger.debug("updating "+courseInfo.getName());
//			courseInfo.setShortcut(courseInfo.getShortcut().replace("/", "-"));
			courseService.updateCourse(courseInfo);
		}
	}
	
	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}
	
	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

}
