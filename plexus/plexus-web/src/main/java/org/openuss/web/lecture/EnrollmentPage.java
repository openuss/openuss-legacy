package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/**
 * @author Ingo Dueppe
 * 
 */
@Bean(name = "views$secured$enrollment$main", scope = Scope.REQUEST)
@View
public class EnrollmentPage extends AbstractLecturePage {

	private static final long serialVersionUID = 1394531398550932611L;

	@Property(value = "#{sessionScope.enrollment}")
	protected Enrollment enrollment;

	@Property(value = "#{param.enrollmentId}")
	protected Long enrollmentId;

	@Override
	@Prerender
	public void prerender() throws LectureException {
		if (enrollmentId != null) {
			// fetch enrollment by id
			enrollment = lectureService.getEnrollment(enrollmentId);
		} else if (enrollment != null) {
			// refresh session enrollment object
			enrollment = lectureService.getEnrollment(enrollment.getId());
		}
		if (enrollment == null) {
			addMessage(i18n("message_error_enrollment_not_exists"));
			redirect(Constants.DESKTOP); // back to where he came from
		} else {
			faculty = enrollment.getFaculty();
			setSessionBean(Constants.ENROLLMENT, enrollment);
			setSessionBean(Constants.FACULTY, faculty);
		}
	}

	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

}
