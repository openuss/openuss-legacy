package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.lecture.Institute;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;


@Bean(name=Constants.INSTITUTE_REGISTRATION_CONTROLLER, scope=Scope.REQUEST)
@View
public class InstituteRegistrationController extends AbstractLecturePage{

	private static final Logger logger = Logger.getLogger(InstituteRegistrationController.class);

	public String start() {
		logger.debug("start registration process");
		institute = Institute.Factory.newInstance();
		setSessionBean(Constants.INSTITUTE, institute);
		return Constants.INSTITUTE_REGISTRATION_STEP1_PAGE;
	}
	
	public String registrate() throws DesktopException, LectureException {
		// connect institute to user
		//FIXME:setOwnerName?
		institute.setOwner(user);
		// create institute
		lectureService.createInstitute(institute);
		//TODO send notification email
		//FIXME this should be part of the business layer
		desktopService.linkInstitute(desktop, institute);
		return Constants.INSTITUTE;
	}
	
}
