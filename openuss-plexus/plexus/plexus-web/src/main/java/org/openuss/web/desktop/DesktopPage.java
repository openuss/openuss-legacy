package org.openuss.web.desktop;

import javax.faces.component.UIData;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopService;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.Subject;
import org.openuss.security.User;
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

	private static final long serialVersionUID = -3558991501240784974L;
	
	private UIData facultyTable;
	private UIData enrollmentTable;
	private UIData subjectTable;
	
	@Property(value="#{sessionScope.desktop}")
	private Desktop desktop;
	
	@Property(value="#{sessionScope.user}")
	private User user;
	
	@Property(value="#{desktopService}")
	private DesktopService desktopService;

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
		Faculty faculty = (Faculty) facultyTable.getRowData();
		setSessionBean(Constants.FACULTY, faculty);
		return Constants.FACULTY;
	}
	
	/**
	 * Remove selected faculty from desktop
	 * @return outcome = DESKTOP
	 */
	public String removeFaculty() throws DesktopException {
		logger.debug("remove faculty");
		Faculty faculty = (Faculty) facultyTable.getRowData();
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
		//TODO load enrollment and move forward
		//TODO check security?
		return Constants.SUCCESS;
	}
	
	/**
	 * Remove enrollment 
	 * @return outcome
	 */
	public String removeEnrollment() {
		logger.debug("remove enrollment");
		Enrollment enrollment = (Enrollment) enrollmentTable.getRowData();
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
		// TODO move to period select page
		return Constants.SUCCESS;
	}
	
	public String removeSubject() {
		logger.debug("remove subject");
		Subject subject = (Subject) subjectTable.getRowData();
		try {
			desktopService.unlinkSubject(desktop, subject);
			addMessage(i18n("desktop_message_removed_subject_succeed",subject.getName()));
		} catch (DesktopException e) {
			logger.debug(e);
			addError(i18n(e.getMessage()));
		}
		return Constants.DESKTOP;
	}

	public UIData getFacultyTable() {
		return facultyTable;
	}

	public void setFacultyTable(UIData facultyTable) {
		this.facultyTable = facultyTable;
	}

	public UIData getEnrollmentTable() {
		return enrollmentTable;
	}

	public void setEnrollmentTable(UIData enrollmentTable) {
		this.enrollmentTable = enrollmentTable;
	}

	public UIData getSubjectTable() {
		return subjectTable;
	}

	public void setSubjectTable(UIData subjectTable) {
		this.subjectTable = subjectTable;
	}

	public Desktop getDesktop() {
		return desktop;
	}

	public void setDesktop(Desktop desktop) {
		this.desktop = desktop;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public DesktopService getDesktopService() {
		return desktopService;
	}

	public void setDesktopService(DesktopService desktopService) {
		this.desktopService = desktopService;
	}
}
