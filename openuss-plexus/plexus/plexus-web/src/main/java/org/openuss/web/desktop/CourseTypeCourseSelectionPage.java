package org.openuss.web.desktop;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.CourseTypeInfo;
import org.openuss.lecture.CourseTypeService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;


/**
 * CourseTypeCourseSelectionPage is the controller bean to handle the desktop view. 
 * 
 * @deprecated
 * 
 * @author Ingo Dueppe
 */
@Bean(name="views$secured$desktop$coursetypecourseselection", scope=Scope.REQUEST)
@View
public class CourseTypeCourseSelectionPage extends BasePage {

	private static final Logger logger = Logger.getLogger(CourseTypeCourseSelectionPage.class);
	
	private CourseDataProvider coursesProvider = new CourseDataProvider();
	
	@Property(value="#{courseType}")
	private CourseTypeInfo courseTypeInfo;
	
	@Property(value="#{courseTypeService}")
	private CourseTypeService courseTypeService;
	
	@Property(value="#{courseService}")
	private CourseService courseService;
	
	@Prerender
	public void prerender() {
		logger.debug("prerender courseType course selction");
		if (courseTypeInfo == null) {
			addError(i18n("message_error_no_coursetype_selected"));
			redirect(Constants.OUTCOME_BACKWARD);
		} else {
			courseTypeInfo = courseTypeService.findCourseType(courseTypeInfo.getId());
		}
		setSessionBean(Constants.COURSE_TYPE, courseTypeInfo);
		breadcrumbs.clear();
	}
	
	/* ------------------ data models ------------------- */
	private class CourseDataProvider extends AbstractPagedTable<CourseInfo> {
		private static final long serialVersionUID = 6604486126694733013L;
		
		private DataPage<CourseInfo> page;

		@Override
		public DataPage<CourseInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<CourseInfo> courses = courseService.findCoursesByCourseType(courseTypeInfo.getId());
				sort(courses);
				page = new DataPage<CourseInfo>(courses.size(),0,courses);
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

	public CourseTypeInfo getCourseTypeInfo() {
		return courseTypeInfo;
	}

	public void setCourseTypeInfo(CourseTypeInfo courseTypeInfo) {
		this.courseTypeInfo = courseTypeInfo;
	}

	public CourseTypeService getCourseTypeService() {
		return courseTypeService;
	}

	public void setCourseTypeService(CourseTypeService courseTypeService) {
		this.courseTypeService = courseTypeService;
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}
}
