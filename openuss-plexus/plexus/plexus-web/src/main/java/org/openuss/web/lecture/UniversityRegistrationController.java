package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityType;
import org.openuss.web.Constants;


@Bean(name=Constants.UNIVERSITY_REGISTRATION_CONTROLLER, scope=Scope.REQUEST)
@View
public class UniversityRegistrationController extends AbstractUniversityPage{

	private static final Logger logger = Logger.getLogger(UniversityRegistrationController.class);
	
	public String start() {
		
		logger.debug("start registration process");
		universityInfo = new UniversityInfo();
		setSessionBean(Constants.UNIVERSITY_INFO, universityInfo);
		
		
		return Constants.UNIVERSITY_REGISTRATION_STEP1_PAGE;
	}
	
	public String registrate() throws DesktopException, LectureException {
		
		universityInfo.setOwnerName(user.getName());
		universityInfo.setEnabled(true);
	
		universityInfo.setUniversityType(UniversityType.MISC);
		Long universityId = universityService.createUniversity(universityInfo, user.getId());
		universityInfo.setId(universityId);
		//TODO send notification email
		//FIXME this should be part of the business layer
		//desktopService.linkUniversity(desktop.getId(), organisation.getId());
				
		return Constants.UNIVERSITY;
	}
	
}
