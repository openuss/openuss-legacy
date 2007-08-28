package org.openuss.web.course;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
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
import org.openuss.web.PageLinks;

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
	
	@Property(value = "#{periodInfo}")
	protected PeriodInfo periodInfo;

	
	
	

	@Prerender
	public void prerender() throws Exception {
		if (courseInfo.getId() != null) {
			courseInfo = courseService.findCourse(courseInfo.getId());
			courseTypeInfo = courseTypeService.findCourseType(courseInfo.getCourseTypeId());
			instituteInfo = instituteService.findInstitute(courseTypeInfo.getInstituteId());
		}
		if (courseInfo == null) {
			addMessage(i18n("message_error_course_page"));
			redirect(Constants.OUTCOME_BACKWARD);
			return;
		} else {
			setSessionBean(Constants.COURSE_INFO, courseInfo);
			setSessionBean(Constants.INSTITUTE_INFO, instituteInfo);
			//generateBreadCrumbs();
		}
	}

	public void generateBreadCrumbs() {
		crumbs.clear();
		BreadCrumb instituteCrumb = new BreadCrumb();

		instituteCrumb.setName(instituteInfo.getShortcut());
		instituteCrumb.setLink(PageLinks.INSTITUTE_PAGE);
		instituteCrumb.addParameter("institute", instituteInfo.getId());
		instituteCrumb.addParameter("period", courseInfo.getPeriodId());
		instituteCrumb.setHint(instituteInfo.getName());

		BreadCrumb courseCrumb = new BreadCrumb();
		courseCrumb.setName(courseInfo.getShortcut());
		courseCrumb.setLink(PageLinks.COURSE_PAGE);
		courseCrumb.addParameter("course", courseInfo.getId());
		courseCrumb.setHint(courseInfo.getName());

		crumbs.add(instituteCrumb);
		crumbs.add(courseCrumb);
	}

//	public Course getCourse() {
//		return course;
//	}
//
//	public void setCourse(Course course) {
//		this.course = course;
//	}
//
//	public LectureService getLectureService() {
//		return lectureService;
//	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
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

	public LectureService getLectureService() {
		return lectureService;
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
