package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;


/**
 * Backing bean for the institute registration. Is responsible starting the wizard, binding the values and registrating
 * the institute.
 * 
 * @author Kai Stettner
 *
 */

@Bean(name=Constants.INSTITUTE_REGISTRATION_CONTROLLER, scope=Scope.REQUEST)
@View
public class InstituteRegistrationController extends AbstractLecturePage{

	private static final Logger logger = Logger.getLogger(InstituteRegistrationController.class);
	

	public String start() {
		logger.debug("start registration process2");

		instituteInfo = new InstituteInfo();
		setSessionBean(Constants.INSTITUTE_INFO, instituteInfo);
		return Constants.INSTITUTE_REGISTRATION_STEP1_PAGE;
	}
	
	public String registrate() throws DesktopException, LectureException {
		logger.debug("Starting method registrate");
		// connect institute to user
		instituteInfo.setOwnerName(user.getName());
		// create institute
		
		//lectureService.createInstitute(institute);
		instituteInfo.setEnabled(false);
		logger.debug(instituteInfo.getEnabled());
		logger.debug(instituteInfo.getOwnerName());
		logger.debug(instituteInfo.getName());
		logger.debug(instituteInfo.getEmail());
		Long instituteId = instituteService.create(instituteInfo, user.getId());
		instituteInfo.setId(instituteId);
		logger.debug(instituteInfo.getId());
		logger.debug(user.getId());
		
		
		//TODO send notification email
		//FIXME this should be part of the business layer
		//desktopService.linkInstitute(desktop, institute);
		desktopService2.linkInstitute(desktopInfo.getId(), instituteId);
		return Constants.INSTITUTE;
	}
	
}
