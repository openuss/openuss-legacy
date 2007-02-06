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
@Bean(name="views$secured$enrollment$main",scope=Scope.REQUEST)
@View
public class EnrollmentPage extends AbstractLecturePage {

	private static final long serialVersionUID = 1394531398550932611L;
	
	@Property(value = "#{sessionScope.enrollment}")
	protected Enrollment enrollment;
	
	@Override
	@Prerender
	public void prerender() throws LectureException {
		if (enrollment == null) {
			enrollment = (Enrollment) getBean(Constants.ENROLLMENT);
		}
		if (enrollment == null) {
			addMessage(i18n("message_error_enrollment_page"));
			redirect("view:backward"); // back to where he came from
		} else { 
			enrollment = lectureService.getEnrollment(enrollment.getId());
		}
		faculty = enrollment.getFaculty();
	}
	
	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}


}
