package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.CourseTypeInfo;
import org.openuss.lecture.CourseTypeService;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.LectureService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Abstract CourseType Page
 * 
 * @author Kai Stettner
 */
public abstract class AbstractCourseTypePage extends BasePage {

	protected static final Logger logger = Logger.getLogger(AbstractCourseTypePage.class);
	
	@Property(value = "#{courseTypeService}")
	protected CourseTypeService courseTypeService;
	
	@Property(value = "#{instituteInfo}")
	protected InstituteInfo instituteInfo;
	
	@Property(value = "#{courseTypeInfo}")
	protected CourseTypeInfo courseTypeInfo;

	/**
	 * Refreshing courseType VO
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing course type session object");
		if (courseTypeInfo != null) {
			courseTypeInfo = courseTypeService.findCourseType(courseTypeInfo.getId());
		} else {
			courseTypeInfo = (CourseTypeInfo) getSessionBean(Constants.COURSE_TYPE_INFO);
		}
		setSessionBean(Constants.COURSE_TYPE_INFO, courseTypeInfo);
	}

	@Prerender
	public void prerender() throws LectureException {
		logger.debug("prerender - refreshing course type session object");
		refreshInstitute();
		if (courseTypeInfo == null) {
			addError(i18n("message_error_no_course_type_selected"));
			redirect(Constants.DESKTOP);
		} else { 
			//generateCrumbs();
		}
	}

	private void refreshInstitute() {
		logger.debug("Starting method refresh course type");
		if (courseTypeInfo != null) {
			courseTypeInfo = courseTypeService.findCourseType(courseTypeInfo.getId());
			setSessionBean(Constants.COURSE_TYPE_INFO, courseTypeInfo);
		}
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

	public InstituteInfo getInstituteInfo() {
		return instituteInfo;
	}

	public void setInstituteInfo(InstituteInfo instituteInfo) {
		this.instituteInfo = instituteInfo;
	}

	/*private void generateCrumbs() {
		logger.debug("Starting method generate crumbs");
		crumbs.clear();
		BreadCrumb courseTypeCrumb = new BreadCrumb();
		courseTypeCrumb.setName(courseTypeInfo.getShortcut());
		courseTypeCrumb.setLink(PageLinks.INSTITUTE_PAGE);
		courseTypeCrumb.addParameter("institute", instituteInfo.getId());
		courseTypeCrumb.setHint(instituteInfo.getName());
		crumbs.add(instituteCrumb);
	}*/
	
	

	
	
	
}
