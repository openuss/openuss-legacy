package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.el.ValueBinding;
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
	private List<UniversityInfo> allEnabledUniversities;
	private List<UniversityInfo> allDisabledUniversities;
	
	private List<SelectItem> departmentItems;

	private List<DepartmentInfo> allDepartments;
	private List<DepartmentInfo> allEnabledDepartments;
	private List<DepartmentInfo> allDisabledDepartments;
	
	private DepartmentType departmentType;
	
	private ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{visit.locale}");
	private String locale = (String)binding.getValue(getFacesContext());
	private ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(locale));
	
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
		instituteInfo.setEnabled(false);

		Long instituteId = instituteService.create(instituteInfo, user.getId());
		instituteInfo.setId(instituteId);

		return Constants.INSTITUTE_PAGE;
	}

	@SuppressWarnings( { "unchecked" })
	public List<SelectItem> getAllUniversities() {
		
		universityItems = new ArrayList<SelectItem>();
		
		allEnabledUniversities = universityService.findUniversitiesByEnabled(true);
		allDisabledUniversities = universityService.findUniversitiesByEnabled(false);
		
		Iterator<UniversityInfo> iterEnabled =  allEnabledUniversities.iterator();
		UniversityInfo universityEnabled;
		
		if (iterEnabled.hasNext()) {
			SelectItem item = new SelectItem(Constants.UNIVERSITIES_ENABLED,bundle.getString("universities_enabled"));
			universityItems.add(item);
		}
		while (iterEnabled.hasNext()) {
			universityEnabled = iterEnabled.next();
			SelectItem item = new SelectItem(universityEnabled.getId(),universityEnabled.getName());
			universityItems.add(item);
		}
		
		Iterator<UniversityInfo> iterDisabled =  allDisabledUniversities.iterator();
		UniversityInfo universityDisabled;
		
		if (iterDisabled.hasNext()) {
			SelectItem item = new SelectItem(Constants.UNIVERSITIES_DISABLED,bundle.getString("universities_disabled"));
			universityItems.add(item);
		}
		while (iterDisabled.hasNext()) {
			universityDisabled = iterDisabled.next();
			SelectItem item = new SelectItem(universityDisabled.getId(),universityDisabled.getName());
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

		allEnabledDepartments = departmentService.findDepartmentsByUniversityAndEnabled(universityId, true);
		allDisabledDepartments = departmentService.findDepartmentsByUniversityAndEnabled(universityId, false);
		
		Iterator<DepartmentInfo> iterEnabled =  allEnabledDepartments.iterator();
		DepartmentInfo departmentEnabled;
		
		if (iterEnabled.hasNext()) {
			SelectItem item = new SelectItem(Constants.DEPARTMENTS_ENABLED,bundle.getString("departments_enabled"));
			departmentItems.add(item);
		}
		while (iterEnabled.hasNext()) {
			departmentEnabled = iterEnabled.next();
			SelectItem item = new SelectItem(departmentEnabled.getId(),departmentEnabled.getName());
			departmentItems.add(item);
		}
		
		
		Iterator<DepartmentInfo> iterDisabled = allDisabledDepartments.iterator();
		DepartmentInfo departmentDisabled;
		
		if (iterDisabled.hasNext()) {
			SelectItem item = new SelectItem(Constants.DEPARTMENTS_DISABLED,bundle.getString("departments_disabled"));
			departmentItems.add(item);
		}
		while (iterDisabled.hasNext()){
			departmentDisabled = iterDisabled.next();
			SelectItem item = new SelectItem(departmentDisabled.getId(), departmentDisabled.getName());
			departmentItems.add(item);
		}
		
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
