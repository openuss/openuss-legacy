package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.CourseType;
import org.openuss.lecture.CourseTypeService;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.LectureService;
import org.openuss.lecture.PeriodInfo;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Abstract Lecture Page
 * 
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 * @author Kai Stettner
 */
public abstract class AbstractLecturePage extends BasePage {

	protected static final Logger logger = Logger.getLogger(AbstractLecturePage.class);

	@Property(value = "#{institute}")
	protected Institute institute;
	
	@Property(value = "#{instituteInfo}")
	protected InstituteInfo instituteInfo;
	
	@Property(value = "#{periodInfo}")
	protected PeriodInfo periodInfo;
	
	@Property(value = "#{departmentInfo}")
	protected DepartmentInfo departmentInfo;
	
	@Property(value = "#{universityInfo}")
	protected UniversityInfo universityInfo;

	@Property(value = "#{lectureService}")
	protected LectureService lectureService;
	
	@Property(value = "#{universityService}")
	protected UniversityService universityService;
	
	@Property(value = "#{departmentService}")
	protected DepartmentService departmentService;
	
	@Property(value = "#{instituteService}")
	protected InstituteService instituteService;
	
	@Property(value = "#{courseTypeService}")
	protected CourseTypeService courseTypeService;

	@Property(value = "#{sessionScope.courseType}")
	protected CourseType courseType;
	
	@Property(value = "#{courseInfo}")
	protected CourseInfo courseInfo;
	
	@Property(value = "#{courseService}")
	protected CourseService courseService;

	/**
	 * Refreshing institute entity
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing institute session object");
		if (instituteInfo != null) {
			instituteInfo = instituteService.findInstitute(instituteInfo.getId());
		} else {
			instituteInfo = (InstituteInfo) getSessionBean(Constants.INSTITUTE_INFO);
		}
		setSessionBean(Constants.INSTITUTE_INFO, instituteInfo);
	}

	@Prerender
	public void prerender() throws LectureException {
		logger.debug("prerender - refreshing institute session object");
		refreshInstitute();
		refreshDepartment();
		if (instituteInfo == null) {
			addError(i18n("message_error_no_institute_selected"));
			redirect(Constants.DESKTOP);
		} 
	}

	private void refreshInstitute() {
		logger.debug("Starting method refresh institute");
		if (instituteInfo != null) {
			instituteInfo = instituteService.findInstitute(instituteInfo.getId());
			setSessionBean(Constants.INSTITUTE_INFO, instituteInfo);
		}
	}

	private void refreshDepartment(){
		logger.debug("Starting method refresh department");
		if (instituteInfo != null) {
			 departmentInfo = departmentService.findDepartment(instituteInfo.getDepartmentId());
			setSessionBean(Constants.DEPARTMENT_INFO, departmentInfo);}
	}
	

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}
	
	public InstituteInfo getInstituteInfo() {
		return instituteInfo;
	}

	public void setInstituteInfo(InstituteInfo instituteInfo) {
		this.instituteInfo = instituteInfo;
	}

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}
	
	public DepartmentInfo getDepartmentInfo() {
		return departmentInfo;
	}

	public void setDepartmentInfo(DepartmentInfo departmentInfo) {
		this.departmentInfo = departmentInfo;
	}

	public PeriodInfo getPeriodInfo() {
		return periodInfo;
	}

	public void setPeriodInfo(PeriodInfo periodInfo) {
		this.periodInfo = periodInfo;
	}

	public UniversityInfo getUniversityInfo() {
		return universityInfo;
	}

	public void setUniversityInfo(UniversityInfo universityInfo) {
		this.universityInfo = universityInfo;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public void setUniversityService(UniversityService universityService) {
		this.universityService = universityService;
	}

	public CourseType getCourseType() {
		return courseType;
	}

	public void setCourseType(CourseType courseType) {
		this.courseType = courseType;
	}

	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	public CourseTypeService getCourseTypeService() {
		return courseTypeService;
	}

	public void setCourseTypeService(CourseTypeService courseTypeService) {
		this.courseTypeService = courseTypeService;
	}

	public UniversityService getUniversityService() {
		return universityService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
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
	
	
}
