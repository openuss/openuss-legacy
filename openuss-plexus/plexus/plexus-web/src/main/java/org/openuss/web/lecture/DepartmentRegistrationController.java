package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentType;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;
import org.openuss.lecture.UniversityInfo;

@Bean(name=Constants.DEPARTMENT_REGISTRATION_CONTROLLER, scope=Scope.REQUEST)
@View
public class DepartmentRegistrationController extends AbstractDepartmentPage{

	private static final Logger logger = Logger.getLogger(InstituteRegistrationController.class);

	protected UniversityInfo universityInfo = (UniversityInfo)this.getSessionBean(Constants.UNIVERSITY_INFO);
	
	public String start() {
		
		logger.debug("start registration process");
		departmentInfo = new DepartmentInfo() ;
		setSessionBean(Constants.DEPARTMENT_INFO, departmentInfo);
		
		return Constants.DEPARTMENT_REGISTRATION_STEP1_PAGE;
	}
	
	public String registrate() throws DesktopException, LectureException {
		
		//create department
		departmentInfo.setUniversityId(universityInfo.getId());
		departmentInfo.setDepartmentType(DepartmentType.OFFICIAL);
		departmentInfo.setOwnerName(user.getName());
		departmentInfo.setEnabled(true);
		departmentService.create(departmentInfo, user.getId());
	
		//TODO send notification email
		//FIXME this should be part of the business layer
		//desktopService.linkDepartment(desktop, department);
		
		return Constants.DEPARTMENT;
	}
	
}
