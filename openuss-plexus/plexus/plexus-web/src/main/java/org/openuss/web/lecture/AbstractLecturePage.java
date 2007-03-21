package org.openuss.web.lecture;


import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.LectureService;
import org.openuss.lecture.Subject;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Abstract Lecture Page
 * @author Ingo Dueppe
 */
public abstract class AbstractLecturePage extends BasePage {

	private static final Logger logger = Logger.getLogger(AbstractLecturePage.class);

	@Property(value = "#{faculty}")
	protected Faculty faculty;
	
	@Property(value = "#{lectureService}")
	protected LectureService lectureService;

	@Property(value = "#{sessionScope.subject}")
	protected Subject subject;

	/**
	 * Refreshing faculty entity 
	 * @throws Exception 
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing faculty session object");
		if (faculty != null) {
			faculty = lectureService.getFaculty(faculty.getId());
		} else {
			faculty = (Faculty) getSessionBean(Constants.FACULTY);
		}
		setSessionBean(Constants.FACULTY, faculty);
	}
	
	@Prerender
	public void prerender() throws LectureException {
		logger.debug("prerender - refreshing faculty session object");
		if (faculty == null) {
			faculty = (Faculty) getSessionBean(Constants.FACULTY);
		} 
		if (faculty == null) {
			addError(i18n("message_error_no_faculty_selected"));
			redirect(Constants.DESKTOP);
		} else {
			faculty = lectureService.getFaculty(faculty.getId());
			setSessionBean(Constants.FACULTY, faculty);
		}
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
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
