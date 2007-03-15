package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;


@Bean(name=Constants.FACULTY_REGISTRATION_CONTROLLER, scope=Scope.REQUEST)
@View
public class FacultyRegistrationController extends AbstractLecturePage{

	private static final Logger logger = Logger.getLogger(FacultyRegistrationController.class);

	private static final long serialVersionUID = 1L;
	
	public String start() {
		logger.debug("start registration process");
		faculty = Faculty.Factory.newInstance();
		setSessionBean(Constants.FACULTY, faculty);
		return Constants.FACULTY_REGISTRATION_STEP1_PAGE;
	}
	
	public String registrate() throws DesktopException, LectureException {
		// connect faculty to user
		faculty.setOwner(user);
		// create faculty
		lectureService.createFaculty(faculty);
		//TODO send notification email
		//FIXME this should be part of the business layer
		desktopService.linkFaculty(desktop, faculty);
		return Constants.FACULTY;
	}
	
}
