package org.openuss.web.lecture;

import java.util.ArrayList;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.AccessType;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.LectureService;
import org.openuss.lecture.Subject;
import org.openuss.web.Constants;

/**
 * @author Ingo Dueppe
 * 
 */
@Bean(name = "views$secured$enrollment$main", scope = Scope.REQUEST)
@View
public class EnrollmentPage extends AbstractEnrollmentPage {

	private static final long serialVersionUID = 1394531398550932611L;

	@Property(value = "#{param.enrollmentId}")
	protected Long enrollmentId;
	

	@Override
	@Prerender
	public void prerender() throws LectureException {
		Subject subject = Subject.Factory.newInstance();
		subject.setName("TestSubject");
		subject.setShortcut("TESTSHORT");
		subject.setDescription("TestDescription");
		
		if (enrollment==null) enrollment = Enrollment.Factory.newInstance();		
		enrollment.setAccessType(AccessType.OPEN);
		enrollment.setDocuments(true);
		enrollment.setDiscussion(true);
		enrollment.setMailinglist(true);
		enrollment.setSubject(subject);
		enrollment.setShortcut("SHORTCUT");
		enrollment.setDescription("description");
		enrollment.setId(new Long(42347));
		enrollmentId = enrollment.getId();
		
		faculty = Faculty.Factory.newInstance();
		// has to be set to an id that exists in db
		faculty.setId(new Long(8441));
		faculty.setName("TestFaculty");
		faculty.setShortcut("TF");
		enrollment.setFaculty(faculty);
		
		ArrayList al = new ArrayList();
		al.add(enrollment);
		subject.setEnrollments(al);
		
		/*
		if (enrollmentId != null) {
			// fetch enrollment by id
			enrollment = lectureService.getEnrollment(enrollmentId);
		} else if (enrollment != null) {
			// refresh session enrollment object
			enrollment = lectureService.getEnrollment(enrollment.getId());
		}*/
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
