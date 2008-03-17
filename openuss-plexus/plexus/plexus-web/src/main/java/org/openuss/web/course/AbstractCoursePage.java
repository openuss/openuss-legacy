package org.openuss.web.course;

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
import org.openuss.lecture.PeriodInfo;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * @author Kai Stettner
 * @author Ingo Düppe
 * @author Sebastian Roekens
 * 
 */
public class AbstractCoursePage extends BasePage {

	private static final long serialVersionUID = 1394531398550932611L;

	private static final Logger logger = Logger
			.getLogger(AbstractCoursePage.class);

	@Property(value = "#{universityInfo}")
	protected UniversityInfo universityInfo;

	@Property(value = "#{universityService}")
	protected UniversityService universityService;

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
	 * Refreshing CourseInfo before apply values phase. This ensures that
	 * courseInfo is recreated before the commands are processed.'
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing course object in request scope");
		if (courseInfo != null && courseInfo.getId() != null) {
			courseInfo = courseService.findCourse(courseInfo.getId());
		}
		setBean(Constants.COURSE_INFO, courseInfo);
	}

	@Prerender
	public void prerender() throws Exception {
		if (courseInfo != null && courseInfo.getId() != null) {
			courseInfo = courseService.findCourse(courseInfo.getId());
		}
		if (courseInfo == null) {
			addError(i18n("message_error_course_page"));
			redirect(Constants.FAILURE);
			return;
		} else {
			if (courseInfo.getCourseTypeId()==null){
				courseInfo = getCourseService().findCourse(courseInfo.getId());
			}
			courseTypeInfo = courseTypeService.findCourseType(courseInfo
					.getCourseTypeId());
			instituteInfo = instituteService.findInstitute(courseTypeInfo
					.getInstituteId());
			breadcrumbs.loadCourseCrumbs(courseInfo);
			setBean(Constants.COURSE_INFO, courseInfo);
			setBean(Constants.INSTITUTE_INFO, instituteInfo);
		}
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public CourseInfo getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(CourseInfo courseInfo) {
		this.courseInfo = courseInfo;
	}

	public CourseTypeInfo getCourseTypeInfo() {
		return courseTypeInfo;
	}

	public void setCourseTypeInfo(CourseTypeInfo courseTypeInfo) {
		this.courseTypeInfo = courseTypeInfo;
	}

	public InstituteInfo getInstituteInfo() {
		return instituteInfo;
	}

	public void setInstituteInfo(InstituteInfo instituteInfo) {
		this.instituteInfo = instituteInfo;
	}

	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public DepartmentInfo getDepartmentInfo() {
		return departmentInfo;
	}

	public void setDepartmentInfo(DepartmentInfo departmentInfo) {
		this.departmentInfo = departmentInfo;
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

	public CourseTypeService getCourseTypeService() {
		return courseTypeService;
	}

	public void setCourseTypeService(CourseTypeService courseTypeService) {
		this.courseTypeService = courseTypeService;
	}

	public PeriodInfo getPeriodInfo() {
		return periodInfo;
	}

	public void setPeriodInfo(PeriodInfo periodInfo) {
		this.periodInfo = periodInfo;
	}

}
