package org.openuss.web.desktop;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.Course;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.CourseType;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * DesktopViewController is the mvc bean to handle the desktop view.
 * 
 * @author Ingo Dueppe
 */
@Bean(name = "views$secured$desktop$desktop", scope = Scope.REQUEST)
@View
public class DesktopPage extends BasePage {

	private static final Logger logger = Logger.getLogger(DesktopPage.class);

	private CourseDataProvider coursesProvider = new CourseDataProvider();
	private CourseTypeDataProvider courseTypesProvider = new CourseTypeDataProvider();
	private FacultyDataProvider facultiesProvider = new FacultyDataProvider();

	@Prerender
	public void prerender() {
		logger.debug("prerender desktop");
		refreshDesktop();
	}

	private void refreshDesktop() {
		if (user != null) {
			try {
				if (desktop == null) {
					logger.error("No desktop found for user " + user.getUsername() + ". Create new one.");
					desktop = desktopService.getDesktopByUser(user);
				} else {
					logger.debug("refreshing desktop data");
					desktop = desktopService.getDesktop(desktop);
				}
				setSessionBean(Constants.DESKTOP, desktop);
			} catch (DesktopException e) {
				logger.error(e);
				addError(i18n(e.getMessage()));
			}
		}
	}

	/**
	 * Show selected faculty
	 * 
	 * @return outcome
	 */
	public String showFaculty() {
		logger.debug("showFaculty");
		Faculty faculty = facultiesProvider.getRowData();
		setSessionBean(Constants.FACULTY, faculty);
		return Constants.FACULTY;
	}

	/**
	 * Remove selected faculty from desktop
	 * 
	 * @return outcome = DESKTOP
	 */
	public String removeFaculty() throws DesktopException {
		logger.debug("remove faculty");
		Faculty faculty = facultiesProvider.getRowData();
		desktopService.unlinkFaculty(desktop, faculty);
		addMessage(i18n("desktop_message_removed_faculty_succeed", faculty.getName()));
		return Constants.DESKTOP;
	}

	/**
	 * Show selected course
	 * 
	 * @return outcome
	 */
	public String showCourse() {
		logger.debug("showCourse");
		Course course = coursesProvider.getRowData();
		setSessionBean(Constants.COURSE, course);
		return Constants.COURSE_PAGE;
	}

	/**
	 * Remove course
	 * 
	 * @return outcome
	 */
	public String removeCourse() {
		logger.debug("remove course");
		Course course = coursesProvider.getRowData();
		try {
			desktopService.unlinkCourse(desktop, course);
			addMessage(i18n("desktop_mesage_removed_course_succeed", course.getShortcut()));
		} catch (DesktopException e) {
			logger.debug(e);
			addError(i18n(e.getMessage()));
		}
		return Constants.DESKTOP;
	}

	/**
	 * Remove courseType
	 * 
	 * @return outcome
	 */
	public String showCourseType() {
		logger.debug("showCourseType");
		CourseType courseType = courseTypesProvider.getRowData();
		setSessionBean(Constants.COURSE_TYPE, courseType);
		return Constants.COURSE_TYPE_COURSE_SELECTION_PAGE;
	}

	public String removeCourseType() {
		logger.debug("remove courseType");
		CourseType courseType = courseTypesProvider.getRowData();
		try {
			desktopService.unlinkCourseType(desktop, courseType);
			addMessage(i18n("desktop_message_removed_coursetype_succeed", courseType.getName()));
		} catch (DesktopException e) {
			logger.debug(e);
			addError(i18n(e.getMessage()));
		}
		return Constants.DESKTOP;
	}

	/* ------------------ data models ------------------- */
	private class CourseDataProvider extends AbstractPagedTable<Course> {

		private static final long serialVersionUID = 255073655670856663L;

		private DataPage<Course> page;

		@Override
		public DataPage<Course> getDataPage(int startRow, int pageSize) {
			List<Course> courses = new ArrayList(desktop.getCourses());
			sort(courses);
			page = new DataPage<Course>(courses.size(), 0, courses);
			return page;
		}
	}

	private class CourseTypeDataProvider extends AbstractPagedTable<CourseType> {

		private static final long serialVersionUID = -932405920082089168L;

		private DataPage<CourseType> page;

		@Override
		public DataPage<CourseType> getDataPage(int startRow, int pageSize) {
			List<CourseType> courseTypes = new ArrayList(desktop.getCourseTypes());
			sort(courseTypes);
			page = new DataPage<CourseType>(courseTypes.size(), 0, courseTypes);
			return page;
		}
	}

	private class FacultyDataProvider extends AbstractPagedTable<Faculty> {
		private static final long serialVersionUID = 2146461373793139193L;

		private DataPage<Faculty> page;

		@Override
		public DataPage<Faculty> getDataPage(int startRow, int pageSize) {
			List<Faculty> faculties = new ArrayList(desktop.getFaculties());
			sort(faculties);
			page = new DataPage<Faculty>(faculties.size(), 0, faculties);
			return page;
		}
	}

	public CourseDataProvider getCoursesProvider() {
		return coursesProvider;
	}

	public void setCoursesProvider(CourseDataProvider coursesProvider) {
		this.coursesProvider = coursesProvider;
	}

	public FacultyDataProvider getFacultiesProvider() {
		return facultiesProvider;
	}

	public void setFacultiesProvider(FacultyDataProvider facultiesProvider) {
		this.facultiesProvider = facultiesProvider;
	}

	public CourseTypeDataProvider getCourseTypesProvider() {
		return courseTypesProvider;
	}

	public void setCourseTypesProvider(CourseTypeDataProvider courseTypesProvider) {
		this.courseTypesProvider = courseTypesProvider;
	}
}
