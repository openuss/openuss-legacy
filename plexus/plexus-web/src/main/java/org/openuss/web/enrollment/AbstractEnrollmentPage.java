package org.openuss.web.enrollment;

import java.util.ArrayList;
import java.util.List;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.EnrollmentInfo;
import org.openuss.lecture.EnrollmentService;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.LectureService;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

/**
 * @author Ingo Dueppe
 */
public class AbstractEnrollmentPage extends BasePage {
	private static final long serialVersionUID = 1394531398550932611L;
	
	//TODO remove dao object from web layer
	@Property(value = "#{enrollment}")
	protected Enrollment enrollment;
	
	//TODO refactor backing beans to use this value object instead of dao object enrollment
	@Property(value = "#{" + Constants.ENROLLMENT_INFO + "}")
	protected EnrollmentInfo enrollmentInfo;
	
	@Property(value = "#{lectureService}")
	protected LectureService lectureService;
	
	@Property(value = "#{systemService}")
	protected SystemService systemService;
	
	@Property(value = "#{enrollmentService}")
	protected EnrollmentService enrollmentService;
	
	@Property(value = "#{faculty}")
	protected Faculty faculty;
	
	@Property(value = "#{breadCrumbs}")
	protected List<BreadCrumb> breadCrumbs;

	@Prerender
	public void prerender() throws Exception {
		if (enrollment != null) {
			enrollment = enrollmentService.getEnrollment(enrollment);
			enrollmentInfo = enrollmentService.getEnrollmentInfo(enrollment);
			faculty = enrollment.getFaculty();
			setSessionBean("faculty", faculty);
		}
		if ((enrollment == null)||(enrollmentInfo == null)) {
			addMessage(i18n("message_error_enrollment_page"));
			redirect(Constants.OUTCOME_BACKWARD);
			return;
		} else {
			setSessionBean(Constants.ENROLLMENT, enrollment);
			setSessionBean(Constants.ENROLLMENT_INFO, enrollmentInfo);
			setSessionBean(Constants.FACULTY, enrollment.getFaculty());
			generateBreadCrumbs();
			setSessionBean(Constants.BREADCRUMBS, breadCrumbs);
		} 
	}
	
	private void generateBreadCrumbs(){
		breadCrumbs = new ArrayList<BreadCrumb>();
		BreadCrumb facultyCrumb = new BreadCrumb();
		//TODO set short faculty name + shortcut
		facultyCrumb.setName(faculty.getName());
		facultyCrumb.setLink(getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+PageLinks.FACULTY_PAGE+"?faculty="+faculty.getId());
		facultyCrumb.setHint(faculty.getName());
		
		BreadCrumb enrollmentCrumb = new BreadCrumb();
		enrollmentCrumb.setName(enrollmentInfo.getName());
		enrollmentCrumb.setLink(getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+PageLinks.ENROLLMENT_PAGE+"?enrollment="+enrollmentInfo.getId());
		enrollmentCrumb.setHint(enrollment.getName());
		
		breadCrumbs.add(facultyCrumb);
		breadCrumbs.add(enrollmentCrumb);
	}
	
	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	public EnrollmentService getEnrollmentService() {
		return enrollmentService;
	}

	public void setEnrollmentService(EnrollmentService enrollmentService) {
		this.enrollmentService = enrollmentService;
	}

	public EnrollmentInfo getEnrollmentInfo() {
		return enrollmentInfo;
	}

	public void setEnrollmentInfo(EnrollmentInfo enrollmentInfo) {
		this.enrollmentInfo = enrollmentInfo;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public List<BreadCrumb> getBreadCrumbs() {
		return breadCrumbs;
	}

	public void setBreadCrumbs(List<BreadCrumb> breadCrumbs) {
		this.breadCrumbs = breadCrumbs;
	}

	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
}
