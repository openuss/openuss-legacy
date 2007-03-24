package org.openuss.web.enrollment;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.EnrollmentService;
import org.openuss.lecture.LectureService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * @author Ingo Dueppe
 */
public class AbstractEnrollmentPage extends BasePage {
	private static final long serialVersionUID = 1394531398550932611L;
	
	@Property(value = "#{enrollment}")
	protected Enrollment enrollment;
	
	@Property(value = "#{lectureService}")
	protected LectureService lectureService;
	
	@Property(value = "#{enrollmentService}")
	protected EnrollmentService enrollmentService;

	@Prerender
	public void prerender() throws Exception {
		if (enrollment == null) {
			enrollment = (Enrollment) getSessionBean(Constants.ENROLLMENT);
		}
		if (enrollment == null) {
			addMessage(i18n("message_error_enrollment_page"));
			redirect(Constants.OUTCOME_BACKWARD);
		} else {
			enrollment = enrollmentService.getEnrollment(enrollment);
		} 
		setSessionBean(Constants.ENROLLMENT, enrollment);
		setSessionBean(Constants.FACULTY, enrollment.getFaculty());
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
}
