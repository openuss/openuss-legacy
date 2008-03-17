package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.CourseTypeInfo;
import org.openuss.lecture.CourseTypeService;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.PeriodInfo;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Abstract CourseType Page
 * 
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
public abstract class AbstractCourseTypePage extends BasePage {

	protected static final Logger logger = Logger.getLogger(AbstractCourseTypePage.class);
	
	@Property(value = "#{universityService}")
	protected UniversityService universityService;
	
	@Property(value = "#{universityInfo}")
	protected UniversityInfo universityInfo;
	
	@Property(value = "#{departmentInfo}")
	protected DepartmentInfo departmentInfo;
	
	@Property(value = "#{departmentService}")
	protected DepartmentService departmentService;
	
	@Property(value = "#{instituteInfo}")
	protected InstituteInfo instituteInfo;

	@Property(value = "#{instituteService}")
	protected InstituteService instituteService;
	
	@Property(value = "#{courseTypeInfo}")
	protected CourseTypeInfo courseTypeInfo;
	
	@Property(value = "#{courseTypeService}")
	protected CourseTypeService courseTypeService;
	
	@Property(value = "#{courseInfo}")
	protected CourseInfo courseInfo;
	
	@Property(value = "#{courseService}")
	protected CourseService courseService;
	
	@Property(value = "#{periodInfo}")
	protected PeriodInfo periodInfo;
	
	

	/**
	 * Refreshing courseType VO
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing course type session object");
		refreshCourseType();
		refreshInstitute();
	}

	@Prerender
	public void prerender() throws LectureException {
		logger.debug("prerender - refreshing course type session object");
		refreshCourseType();
		refreshInstitute();
		if (courseTypeInfo == null) {
			addError(i18n("message_error_no_course_type_selected"));
			redirect(Constants.DESKTOP);
			return;
		}
		if (instituteInfo == null) {
			addError(i18n("message_error_no_institute_selected"));
			redirect(Constants.DESKTOP);
			return;
		} 
		if (!courseTypeInfo.getInstituteId().equals(instituteInfo.getId())){
			addError(i18n("message_error_institute_coursetype"));
			redirect(Constants.DESKTOP);
			return;
		}
	}

	private void refreshInstitute() {
		logger.debug("Starting method refresh course type");
		if (instituteInfo != null && instituteInfo.getId()!= null) {
			instituteInfo = instituteService.findInstitute(instituteInfo.getId());
			setBean(Constants.INSTITUTE_INFO, instituteInfo);
		}
	}

	private void refreshCourseType() {
		logger.debug("Starting method refresh course type");
		if (courseTypeInfo != null) {
			courseTypeInfo = courseTypeService.findCourseType(courseTypeInfo.getId());
			setBean(Constants.COURSE_TYPE_INFO, courseTypeInfo);
		}
	}
	
	public UniversityInfo getUniversityInfo() {
		return universityInfo;
	}

	public void setUniversityInfo(UniversityInfo universityInfo) {
		this.universityInfo = universityInfo;
	}

	public UniversityService getUniversityService() {
		return universityService;
	}

	public void setUniversityService(UniversityService universityService) {
		this.universityService = universityService;
	}

	public DepartmentInfo getDepartmentInfo() {
		return departmentInfo;
	}

	public void setDepartmentInfo(DepartmentInfo departmentInfo) {
		this.departmentInfo = departmentInfo;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
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
	
	public PeriodInfo getPeriodInfo() {
		return periodInfo;
	}

	public void setPeriodInfo(PeriodInfo periodInfo) {
		this.periodInfo = periodInfo;
	}

	public CourseInfo getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(CourseInfo courseInfo) {
		this.courseInfo = courseInfo;
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
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
