package org.openuss.web.course;

import java.util.ArrayList;
import java.util.List;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.Institute;
import org.openuss.lecture.LectureService;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

/**
 * @author Ingo Dueppe
 */
public class AbstractCoursePage extends BasePage {
	private static final long serialVersionUID = 1394531398550932611L;
	
	//TODO remove dao object from web layer
	@Property(value = "#{course}")
	protected Course course;
	
	//TODO refactor backing beans to use this value object instead of dao object course
	@Property(value = "#{" + Constants.COURSE_INFO + "}")
	protected CourseInfo courseInfo;
	
	@Property(value = "#{lectureService}")
	protected LectureService lectureService;
	
	@Property(value = "#{systemService}")
	protected SystemService systemService;
	
	@Property(value = "#{courseService}")
	protected CourseService courseService;
	
	@Property(value = "#{institute}")
	protected Institute institute;
	
	@Property(value = "#{crumbs}")
	protected List<BreadCrumb> crumbs;

	@Prerender
	public void prerender() throws Exception {
		if (course != null) {
			course = courseService.getCourse(course);
			courseInfo = courseService.getCourseInfo(course);
			institute = course.getInstitute();
			setSessionBean("institute", institute);
		}
		if ((course == null)||(courseInfo == null)) {
			addMessage(i18n("message_error_course_page"));
			redirect(Constants.OUTCOME_BACKWARD);
			return;
		} else {
			setSessionBean(Constants.COURSE, course);
			setSessionBean(Constants.COURSE_INFO, courseInfo);
			setSessionBean(Constants.INSTITUTE, course.getInstitute());
			generateBreadCrumbs();
			setSessionBean(Constants.BREADCRUMBS, crumbs);
		} 
	}
	
	public void generateBreadCrumbs(){
		crumbs = new ArrayList<BreadCrumb>();
		BreadCrumb instituteCrumb = new BreadCrumb();
		
		instituteCrumb.setName(institute.getShortcut());
		instituteCrumb.setLink(getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+PageLinks.INSTITUTE_PAGE+"?institute="+institute.getId()+"&period="+course.getPeriod().getId());
		instituteCrumb.setHint(institute.getName());
		
		BreadCrumb courseCrumb = new BreadCrumb();
		courseCrumb.setName(courseInfo.getShortcut());
		courseCrumb.setLink(getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+PageLinks.COURSE_PAGE+"?course="+courseInfo.getId());
		courseCrumb.setHint(course.getName());
		
		crumbs.add(instituteCrumb);
		crumbs.add(courseCrumb);
	}
	
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public LectureService getLectureService() {
		return lectureService;
	}

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

	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public List<BreadCrumb> getCrumbs() {
		return crumbs;
	}

	public void setCrumbs(List<BreadCrumb> crumbs) {
		this.crumbs = crumbs;
	}
}
