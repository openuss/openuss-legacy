package org.openuss.web.course;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.Institute;
import org.openuss.lecture.LectureService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

/**
 * @author Ingo Dueppe
 */
public class AbstractCoursePage extends BasePage {
	private static final long serialVersionUID = 1394531398550932611L;

//	/**
//	 * TODO remove DAO object from web layer
//	 * @deprecated
//	 */
//	@Property(value = "#{course}")
//	protected Course course;

	@Property(value = "#{" + Constants.COURSE + "}")
	protected CourseInfo courseInfo;

	@Property(value = "#{lectureService}")
	protected LectureService lectureService;

	@Property(value = "#{courseService}")
	protected CourseService courseService;

	@Property(value = "#{institute}")
	protected Institute institute;

	@Prerender
	public void prerender() throws Exception {
		if (courseInfo != null) {
			courseInfo = courseService.getCourseInfo(courseInfo.getId());
		}
		if (courseInfo == null) {
			addError(i18n("message_error_course_page"));
			redirect(Constants.OUTCOME_BACKWARD);
			return;
		} else {
			institute = lectureService.getInstitute(courseInfo.getInstituteId());

			setSessionBean(Constants.COURSE, courseInfo);
			setSessionBean(Constants.COURSE_INFO, courseInfo);
			setSessionBean(Constants.INSTITUTE, institute);
			generateBreadCrumbs();
		}
	}

	public void generateBreadCrumbs() {
		crumbs.clear();
		BreadCrumb instituteCrumb = new BreadCrumb();

		instituteCrumb.setName(institute.getShortcut());
		instituteCrumb.setLink(PageLinks.INSTITUTE_PAGE);
		instituteCrumb.addParameter("institute", institute.getId());
		instituteCrumb.addParameter("period", courseInfo.getPeriodId());
		instituteCrumb.setHint(institute.getName());

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

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}
}
