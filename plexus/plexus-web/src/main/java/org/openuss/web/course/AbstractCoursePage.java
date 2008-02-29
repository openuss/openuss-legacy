package org.openuss.web.course;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.calendar.CalendarInfo;
import org.openuss.calendar.CalendarService;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.CourseTypeInfo;
import org.openuss.lecture.CourseTypeService;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.lecture.LectureService;
import org.openuss.lecture.PeriodInfo;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * @author Kai Stettner
 */
public class AbstractCoursePage extends BasePage {
	private static final long serialVersionUID = 1394531398550932611L;

	@Property(value = "#{lectureService}")
	protected LectureService lectureService;
	
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
	
	//added for course calendar support
	@Property(value = "#{calendarService}")
	private CalendarService calendarService;
	
	@Property(value = "#{" + Constants.OPENUSS4US_CALENDAR + "}")
	private CalendarInfo calendarInfo;
	
	@Property(value = "#{periodInfo}")
	protected PeriodInfo periodInfo;

	
	@Prerender
	public void prerender() throws Exception {
		if (courseInfo != null && courseInfo.getId() != null) {
			courseInfo = courseService.findCourse(courseInfo.getId());
			CalendarInfo calendarInfo = calendarService.getCalendar(courseInfo);
			setSessionAttribute(Constants.OPENUSS4US_CALENDAR, calendarInfo);
		}
		if (courseInfo == null) {
			addError(i18n("message_error_course_page"));
			redirect(Constants.OUTCOME_BACKWARD);
			return;
		} else {
			courseTypeInfo = courseTypeService.findCourseType(courseInfo.getCourseTypeId());
			instituteInfo = instituteService.findInstitute(courseTypeInfo.getInstituteId());
			breadcrumbs.loadCourseCrumbs(courseInfo);
			CalendarInfo calendarInfo = calendarService.getCalendar(courseInfo);
			setSessionBean(Constants.OPENUSS4US_CALENDAR, calendarInfo);
			setSessionBean(Constants.COURSE_INFO, courseInfo);
			setSessionBean(Constants.INSTITUTE_INFO, instituteInfo);	
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

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	public CalendarService getCalendarService() {
		return calendarService;
	}

	public void setCalendarService(CalendarService calendarService) {
		this.calendarService = calendarService;
	}

	public CalendarInfo getCalendarInfo() {
		return calendarInfo;
	}

	public void setCalendarInfo(CalendarInfo calendarInfo) {
		this.calendarInfo = calendarInfo;
	}

}
