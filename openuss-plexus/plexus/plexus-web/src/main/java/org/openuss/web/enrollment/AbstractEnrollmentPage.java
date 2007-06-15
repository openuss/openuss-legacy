package org.openuss.web.enrollment;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.EnrollmentInfo;
import org.openuss.lecture.EnrollmentService;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.LectureService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

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
	
	@Property(value = "#{enrollmentService}")
	protected EnrollmentService enrollmentService;
	
	@Property(value = "#{faculty}")
	protected Faculty faculty;

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
		} 
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
}
