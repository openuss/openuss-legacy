package org.openuss.web.desktop;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.LectureService;
import org.openuss.lecture.Subject;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;


/**
 * SubjectEnrollmentSelectionPage is the controller bean to handle the desktop view. 
 * @author Ingo Dueppe
 */
@Bean(name="views$secured$desktop$subjectenrollmenselection", scope=Scope.REQUEST)
@View
public class SubjectEnrollmentSelectionPage extends BasePage {

	private static final Logger logger = Logger.getLogger(SubjectEnrollmentSelectionPage.class);
	
	private EnrollmentDataProvider enrollmentsProvider = new EnrollmentDataProvider();
	
	@Property(value="#{sessionScope.subject}")
	private Subject subject;
	
	@Property(value="#{lectureService}")
	LectureService lectureService;

	@Prerender
	public void prerender() {
		logger.debug("prerender subject enrollment selction");
		if (subject == null) {
			addError(i18n("message_error_no_subject_selected"));
			redirect(Constants.OUTCOME_BACKWARD);
		} else {
			subject = lectureService.getSubject(subject.getId());
		}
		setSessionBean(Constants.SUBJECT, subject);
	}
	
	/* ------------------ data models ------------------- */
	private class EnrollmentDataProvider extends AbstractPagedTable<Enrollment> {
		private static final long serialVersionUID = 6604486126694733013L;
		
		private DataPage<Enrollment> page;

		@Override
		public DataPage<Enrollment> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<Enrollment> enrollments = new ArrayList(subject.getEnrollments());
				sort(enrollments);
				page = new DataPage<Enrollment>(enrollments.size(),0,enrollments);
			}
			return page;
		}
	}

	public EnrollmentDataProvider getEnrollmentsProvider() {
		return enrollmentsProvider;
	}

	public void setEnrollmentsProvider(EnrollmentDataProvider enrollmentsProvider) {
		this.enrollmentsProvider = enrollmentsProvider;
	}

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
}
