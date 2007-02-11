package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/**
 * @author Ingo Dueppe
 *
 */
public class AbstractEnrollmentPage extends AbstractLecturePage {

	private static final long serialVersionUID = 1394531398550932611L;
	
	@Property(value = "#{sessionScope.enrollment}")
	protected Enrollment enrollment;
	
	@Override
	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		if (enrollment == null) {
			addMessage(i18n("message_error_enrollment_page"));
			redirect(Constants.FACULTY_PERIODS_PAGE);
		} 
	}
	
	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}


}
