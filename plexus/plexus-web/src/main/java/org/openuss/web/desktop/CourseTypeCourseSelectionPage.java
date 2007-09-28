package org.openuss.web.desktop;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.CourseTypeService;
import org.openuss.lecture.LectureService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;


/**
 * CourseTypeCourseSelectionPage is the controller bean to handle the desktop view. 
 * @author Ingo Dueppe
 */
@Bean(name="views$secured$desktop$coursetypecourseselection", scope=Scope.REQUEST)
@View
public class CourseTypeCourseSelectionPage extends BasePage {

	private static final Logger logger = Logger.getLogger(CourseTypeCourseSelectionPage.class);
	
	private CourseDataProvider coursesProvider = new CourseDataProvider();
	
	@Property(value="#{sessionScope.courseType}")
	private CourseType courseType;
	
	@Property(value="#{lectureService}")
	LectureService lectureService;
	
	@Prerender
	public void prerender() {
		logger.debug("prerender courseType course selction");
		if (courseType == null) {
			addError(i18n("message_error_no_coursetype_selected"));
			redirect(Constants.OUTCOME_BACKWARD);
		} else {
			courseType = lectureService.getCourseType(courseType.getId());
		}
		setSessionBean(Constants.COURSE_TYPE, courseType);
		breadcrumbs.clear();
	}
	
	/* ------------------ data models ------------------- */
	private class CourseDataProvider extends AbstractPagedTable<Course> {
		private static final long serialVersionUID = 6604486126694733013L;
		
		private DataPage<Course> page;

		@Override
		public DataPage<Course> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<Course> courses = new ArrayList<Course>(courseType.getCourses());
				sort(courses);
				page = new DataPage<Course>(courses.size(),0,courses);
			}
			return page;
		}
	}

	public CourseDataProvider getCoursesProvider() {
		return coursesProvider;
	}

	public void setCoursesProvider(CourseDataProvider coursesProvider) {
		this.coursesProvider = coursesProvider;
	}

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	public CourseType getCourseType() {
		return courseType;
	}

	public void setCourseType(CourseType courseType) {
		this.courseType = courseType;
	}
}
