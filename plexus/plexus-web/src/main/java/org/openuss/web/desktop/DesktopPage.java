package org.openuss.web.desktop;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.Subject;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;


/**
 * DesktopViewController is the mvc bean to handle the desktop view. 
 * @author Ingo Dueppe
 */
@Bean(name="views$secured$desktop$desktop", scope=Scope.REQUEST)
@View
public class DesktopPage extends BasePage {

	private static final Logger logger = Logger.getLogger(DesktopPage.class);

	private EnrollmentDataProvider enrollmentsProvider = new EnrollmentDataProvider();
	private SubjectDataProvider subjectsProvider = new SubjectDataProvider();
	private FacultyDataProvider facultiesProvider = new FacultyDataProvider();

	@Prerender
	public void prerender() {
		logger.debug("prerender desktop");
		refreshDesktop();
	}
	
	private void refreshDesktop() {
		if (user != null) {
			try {
				if (desktop == null) {
					logger.error("No desktop found for user " + user.getUsername() + ". Create new one.");
					desktop = desktopService.getDesktopByUser(user);
				} else {
					logger.debug("refreshing desktop data");
					desktop = desktopService.getDesktop(desktop);
				}
				setSessionBean(Constants.DESKTOP, desktop);
			} catch (DesktopException e) {
				logger.error(e);
				addError(i18n(e.getMessage()));
			}
		}
	}
	
	/**
	 * Show selected faculty
	 * @return outcome
	 */
	public String showFaculty() {
		logger.debug("showFaculty");
		Faculty faculty = facultiesProvider.getRowData();
		setSessionBean(Constants.FACULTY, faculty);
		return Constants.FACULTY;
	}
	
	/**
	 * Remove selected faculty from desktop
	 * @return outcome = DESKTOP
	 */
	public String removeFaculty() throws DesktopException {
		logger.debug("remove faculty");
		Faculty faculty = facultiesProvider.getRowData();
		desktopService.unlinkFaculty(desktop, faculty);
		addMessage(i18n("desktop_message_removed_faculty_succeed", faculty.getName()));
		return Constants.DESKTOP;
	}
	
	/**
	 * Show selected enrollment 
	 * @return outcome 
	 */
	public String showEnrollment() {
		logger.debug("showEnrollment");
		Enrollment enrollment = enrollmentsProvider.getRowData();
		setSessionBean(Constants.ENROLLMENT, enrollment);
		return Constants.ENROLLMENT_PAGE;
	}
	
	/**
	 * Remove enrollment 
	 * @return outcome
	 */
	public String removeEnrollment() {
		logger.debug("remove enrollment");
		Enrollment enrollment = enrollmentsProvider.getRowData();
		try {
			desktopService.unlinkEnrollment(desktop, enrollment);
			addMessage(i18n("desktop_mesage_removed_enrollment_succeed", enrollment.getShortcut()));
		} catch (DesktopException e) {
			logger.debug(e);
			addError(i18n(e.getMessage()));
		}
		return Constants.DESKTOP;
	}
	
	/**
	 * Remove subject
	 * @return outcome
	 */
	public String showSubject() {
		logger.debug("showSubject");
		Subject subject = subjectsProvider.getRowData();
		setSessionBean(Constants.SUBJECT, subject);
		return Constants.SUBJECT_ENROLLMENT_SELECTION_PAGE;
	}
	
	public String removeSubject() {
		logger.debug("remove subject");
		Subject subject = subjectsProvider.getRowData();
		try {
			desktopService.unlinkSubject(desktop, subject);
			addMessage(i18n("desktop_message_removed_subject_succeed",subject.getName()));
		} catch (DesktopException e) {
			logger.debug(e);
			addError(i18n(e.getMessage()));
		}
		return Constants.DESKTOP;
	}
	
	/* ------------------ data models ------------------- */
	private class EnrollmentDataProvider extends AbstractPagedTable<Enrollment> {
		
		private static final long serialVersionUID = 255073655670856663L;
		
		private DataPage<Enrollment> page;

		@Override
		public DataPage<Enrollment> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<Enrollment> enrollments = new ArrayList(desktop.getEnrollments());
				sort(enrollments);
				page = new DataPage<Enrollment>(enrollments.size(),0,enrollments);
			}
			return page;
		}
	}

	private class SubjectDataProvider extends AbstractPagedTable<Subject> {
		
		private static final long serialVersionUID = -932405920082089168L;
		
		private DataPage<Subject> page;
		
		@Override
		public DataPage<Subject> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<Subject> subjects = new ArrayList(desktop.getSubjects());
				sort(subjects);
				page = new DataPage<Subject>(subjects.size(),0,subjects);
			}
			return page;
		}
	}

	private class FacultyDataProvider extends AbstractPagedTable<Faculty> {
		private static final long serialVersionUID = 2146461373793139193L;
		
		private DataPage<Faculty> page;
		
		@Override
		public DataPage<Faculty> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<Faculty> faculties = new ArrayList(desktop.getFaculties());
				sort(faculties);
				page = new DataPage<Faculty>(faculties.size(),0,faculties);
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

	public FacultyDataProvider getFacultiesProvider() {
		return facultiesProvider;
	}

	public void setFacultiesProvider(FacultyDataProvider facultiesProvider) {
		this.facultiesProvider = facultiesProvider;
	}

	public SubjectDataProvider getSubjectsProvider() {
		return subjectsProvider;
	}

	public void setSubjectsProvider(SubjectDataProvider subjectsProvider) {
		this.subjectsProvider = subjectsProvider;
	}
}
