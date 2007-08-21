package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentType;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityInfo;
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
	
	private List<SelectItem> universityItems;
	private List<UniversityInfo> allUniversities;
	
	private List<SelectItem> departmentItems;

	private List<DepartmentInfo> allDepartments;
	
	private DepartmentType departmentType;
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
	
	public List<SelectItem> getAllUniversities() {
		
		universityItems = new ArrayList<SelectItem>();
		
		allUniversities = universityService.findAllUniversities();
		
		Iterator<UniversityInfo> iter =  allUniversities.iterator();
		UniversityInfo university;
		
		while (iter.hasNext()) {
			university = iter.next();
			SelectItem item = new SelectItem(university.getId(),university.getName());
			universityItems.add(item);
		}
		
		return universityItems;
	}
	
	public List<SelectItem> getAllDepartments(){
		departmentItems = new ArrayList<SelectItem>();
		
		Long universityId = universityInfo.getId();
		allUniversities = universityService.findAllUniversities();
		if (universityId == null)
			universityId = allUniversities.get(0).getId();
			
		logger.info("universityId:"+universityInfo.getId());
		allDepartments = departmentService.findDepartmentsByUniversity(universityId);
		Iterator<DepartmentInfo> iter = allDepartments.iterator();
		DepartmentInfo department;
		
		while (iter.hasNext()){
			department = iter.next();
			SelectItem item = new SelectItem(department.getId(), department.getName());
			departmentItems.add(item);
		}
		
		logger.info("DepartmentId:" + allDepartments.get(0).getId());
		return departmentItems;
	
	}
	

	
	public void processUniversitySelectChanged(ValueChangeEvent event) {
		final Long universityId = (Long) event.getNewValue();
		logger.info("universityId in event" + event.getNewValue().toString());
		logger.info("universityId in processUniversitySelcetChanged:" + universityId);
		universityInfo = universityService.findUniversity(universityId);
		
		setSessionBean(Constants.UNIVERSITY_INFO, universityInfo);
		getAllDepartments();
	}
	
	public void processDepartmentSelectChanged(ValueChangeEvent event) {
		final Long departmentId = (Long) event.getNewValue();
		departmentInfo = departmentService.findDepartment(departmentId);
		setSessionBean(Constants.DEPARTMENT_INFO, departmentInfo);
			}	
	
}
