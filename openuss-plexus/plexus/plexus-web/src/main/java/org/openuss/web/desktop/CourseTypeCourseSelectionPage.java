package org.openuss.web.desktop;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.Course;
import org.openuss.lecture.LectureService;
import org.openuss.lecture.CourseType;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;


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

	@Property(value="#{systemService}")
	SystemService systemService;
	
	@Property(value = "#{crumbs}")
	protected List<BreadCrumb> crumbs;
	
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
		generateBreadCrumbs();
	}
	
	private void generateBreadCrumbs(){
		crumbs = new ArrayList<BreadCrumb>();
		BreadCrumb instituteCrumb = new BreadCrumb();
		
		instituteCrumb.setName(courseType.getInstitute().getShortcut());
		instituteCrumb.setLink(getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+PageLinks.INSTITUTE_PAGE+"?institute="+courseType.getInstitute().getId());
		instituteCrumb.setHint(courseType.getInstitute().getName());
		crumbs.add(instituteCrumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}
	
	/* ------------------ data models ------------------- */
	private class CourseDataProvider extends AbstractPagedTable<Course> {
		private static final long serialVersionUID = 6604486126694733013L;
		
		private DataPage<Course> page;

		@Override
		public DataPage<Course> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<Course> courses = new ArrayList(courseType.getCourses());
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

	public List<BreadCrumb> getCrumbs() {
		return crumbs;
	}

	public void setCrumbs(List<BreadCrumb> crumbs) {
		this.crumbs = crumbs;
	}

	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
}
