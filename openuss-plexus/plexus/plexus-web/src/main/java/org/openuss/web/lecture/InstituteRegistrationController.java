package org.openuss.web.lecture;

import java.io.IOException;
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
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityInfo;
import org.openuss.security.Roles;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.web.Constants;
import org.openuss.web.upload.UploadedDocument;


/**
 * Backing bean for the institute registration. Is responsible starting the 
 * wizard, binding the values and registering the institute.
 * 
 * @author Julian Reimann
 * @author Kai Stettner
 *
 */

@Bean(name=Constants.INSTITUTE_REGISTRATION_CONTROLLER, scope=Scope.SESSION)
@View
public class InstituteRegistrationController extends AbstractLecturePage{

	private static final Logger logger = Logger.getLogger(InstituteRegistrationController.class);
	private Long selectedUniversity;
	private Long selectedDepartment;
	private InstituteInfo instituteInfo;
	
	private ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{visit.locale}");
	private String locale = (String)binding.getValue(getFacesContext());
	private ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(locale));
	
	
	public String start() {
		logger.debug("Start institute registration process");

		instituteInfo = new InstituteInfo();
		
		selectedUniversity = null;
		selectedDepartment = null;
		
		// If we are coming from a certain department
		// we take the department's id and university id
		// for the new institute
/*		if(departmentInfo != null)
		{
			if(departmentInfo.getUniversityId() != null)
				selectedUniversity = departmentInfo.getUniversityId();
		}
		
		if(selectedUniversity == null)
			selectedUniversity = chooseUniversity();
*/	
		// Preselect the first university
		List<UniversityInfo> allEnabledUniversities;
		allEnabledUniversities = universityService.findUniversitiesByEnabled(true);
		
		if(allEnabledUniversities != null && !allEnabledUniversities.isEmpty())
		{
			selectedUniversity = allEnabledUniversities.get(0).getId();
		}
		
		
		return Constants.INSTITUTE_REGISTRATION_STEP1_PAGE;
	}
	
	private Long chooseUniversity()
	{
		return 0L;
	}
	
	public String registrate() throws DesktopException, LectureException, DocumentApplicationException, IOException {
		logger.debug("Starting method registrate");
		
		// create institute
		instituteInfo.setEnabled(false);

		Long instituteId = instituteService.create(instituteInfo, user.getId());		
		instituteInfo.setId(instituteId);
		
		// automatically start the department application process
		instituteService.applyAtDepartment(instituteId, selectedDepartment, user.getId());

		setSessionBean(Constants.INSTITUTE_INFO, instituteInfo);
		return Constants.INSTITUTE_PAGE;
	}

	@SuppressWarnings( { "unchecked" })
	public List<SelectItem> getAllUniversities() {
		List<SelectItem> universityItems;
		List<UniversityInfo> allEnabledUniversities;
		List<UniversityInfo> allDisabledUniversities;
		
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
	
	
	@SuppressWarnings( { "unchecked" })
	public List<SelectItem> getAllDepartments()
	{
		List<DepartmentInfo> allEnabledDepartments;
		List<DepartmentInfo> allDisabledDepartments;
		List<SelectItem> departmentItems = new ArrayList<SelectItem>();
		
		if (selectedUniversity == null || selectedUniversity < 0L)
		{
			SelectItem item = new SelectItem(Constants.DEPARTMENTS_NO_UNIVERSITY_SELECTED, bundle.getString("institute_registration_choose_university"));
			departmentItems.add(item);
			return departmentItems;
		}
		else
		{
			allEnabledDepartments = departmentService.findDepartmentsByUniversityAndEnabled(selectedUniversity, true);
			allDisabledDepartments = departmentService.findDepartmentsByUniversityAndEnabled(selectedUniversity, false);
		
			Iterator<DepartmentInfo> iterEnabled =  allEnabledDepartments.iterator();
			DepartmentInfo departmentEnabled;
		
			// Add a label for the enabled departments to the list
			if (iterEnabled.hasNext())
			{
				SelectItem item = new SelectItem(Constants.DEPARTMENTS_ENABLED,bundle.getString("departments_enabled"));
				departmentItems.add(item);
			}
			
			while (iterEnabled.hasNext()) {
				departmentEnabled = iterEnabled.next();
				if(departmentEnabled.getDepartmentType().getValue() == 0) {
					SelectItem item = new SelectItem(departmentEnabled.getId(),departmentEnabled.getName()+" - ("+bundle.getString("departmenttype_official")+")");
					departmentItems.add(item);
				} else if(departmentEnabled.getDepartmentType().getValue() == 1) {
					SelectItem item = new SelectItem(departmentEnabled.getId(),departmentEnabled.getName()+" - ("+bundle.getString("departmenttype_non_offical")+")");
					departmentItems.add(item);
				} else {
					// do nothing
				}
			}
		
			Iterator<DepartmentInfo> iterDisabled = allDisabledDepartments.iterator();
			DepartmentInfo departmentDisabled;
			
			// Add a label for the disabled departments to the list
			if (iterDisabled.hasNext()) {
				SelectItem item = new SelectItem(Constants.DEPARTMENTS_DISABLED,bundle.getString("departments_disabled"));
				departmentItems.add(item);
			}
			
			while (iterDisabled.hasNext()){
				departmentDisabled = iterDisabled.next();
				if(departmentDisabled.getDepartmentType().getValue() == 0) {
					SelectItem item = new SelectItem(departmentDisabled.getId(),departmentDisabled.getName()+" - ("+bundle.getString("departmenttype_official")+")");
					departmentItems.add(item);
				} else if(departmentDisabled.getDepartmentType().getValue() == 1) {
					SelectItem item = new SelectItem(departmentDisabled.getId(),departmentDisabled.getName()+" - ("+bundle.getString("departmenttype_non_offical")+")");
					departmentItems.add(item);
				} else {
					// do nothing
				}
			}
		}
		
		return departmentItems;
	
	}
	
	public void processUniversitySelectChanged(ValueChangeEvent event)
	{
		Long universityId = null;

		try {
			universityId = (Long)event.getNewValue();
		} catch (Exception e) {
			logger.debug("ValueChangeEvent: Error: New value is could not be cast to Long");
		}
		if(universityId != null)
		{
			logger.info("ValueChangeEvent: Changing university id for new institute to " + universityId);
			selectedUniversity = universityId;
		}
	}
	
	public void processDepartmentSelectChanged(ValueChangeEvent event)
	{
		Long departmentId = null;
		try {
			departmentId = (Long) event.getNewValue();
		} catch (Exception e) {
			logger.debug("ValueChangeEvent: Error: New value is could not be cast to Long");
		}
		
		if(departmentId != null)
		{
			logger.info("ValueChangeEvent: Changing department id for new institute to " + departmentId);
			selectedDepartment = departmentId;
		}
	}

	public Long getSelectedUniversity() {
		return selectedUniversity;
	}

	public void setSelectedUniversity(Long selectedUniversity) {
		this.selectedUniversity = selectedUniversity;
	}

	public Long getSelectedDepartment() {
		return selectedDepartment;
	}

	public void setSelectedDepartment(Long selectedDepartment) {
		this.selectedDepartment = selectedDepartment;
	}

	public InstituteInfo getInstituteInfo() {
		return instituteInfo;
	}

	public void setInstituteInfo(InstituteInfo newInstitute) {
		this.instituteInfo = newInstitute;
	}
	
}
