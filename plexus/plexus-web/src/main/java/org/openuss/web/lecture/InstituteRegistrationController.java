package org.openuss.web.lecture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentType;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityInfo;
import org.openuss.web.Constants;

/**
 * Backing bean for the institute registration. Is responsible starting the
 * wizard, binding the values and registering the institute.
 * 
 * @author Julian Reimann
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
@Bean(name = Constants.INSTITUTE_REGISTRATION_CONTROLLER, scope = Scope.SESSION)
@View
public class InstituteRegistrationController extends AbstractLecturePage {

	private static final Logger logger = Logger.getLogger(InstituteRegistrationController.class);
	private Long selectedUniversity;
	private Long selectedDepartment;
	private InstituteInfo instituteInfo;

	@SuppressWarnings("unchecked")
	public String start() {
		logger.debug("Start institute registration process");

		instituteInfo = new InstituteInfo();

		selectedUniversity = null;
		selectedDepartment = null;

		// If we are coming from a certain department
		// we take the department's id and university id
		// for the new institute
		/*
		 * if(departmentInfo != null) { if(departmentInfo.getUniversityId() !=
		 * null) selectedUniversity = departmentInfo.getUniversityId(); }
		 * 
		 * if(selectedUniversity == null) selectedUniversity =
		 * chooseUniversity();
		 */
		// Preselect the first university
		List<UniversityInfo> allEnabledUniversities;
		allEnabledUniversities = universityService.findUniversitiesByEnabled(true);

		if (allEnabledUniversities != null && !allEnabledUniversities.isEmpty()) {
			selectedUniversity = allEnabledUniversities.get(0).getId();
		}

		return Constants.INSTITUTE_REGISTRATION_STEP1_PAGE;
	}

	public String registrate() throws DesktopException, LectureException, DocumentApplicationException, IOException {
		logger.debug("Starting method registrate");
		// create institute
		instituteInfo.setEnabled(false);
		Long instituteId = instituteService.create(instituteInfo, user.getId());
		instituteInfo.setId(instituteId);
		// FIXME Should be done in business layer - automatically start the department application process
		instituteService.applyAtDepartment(instituteId, selectedDepartment, user.getId());
		setBean(Constants.INSTITUTE_INFO, instituteInfo);
		addMessage(i18n("institute_registration_success"));
		return Constants.INSTITUTE_PAGE;
	}

	@SuppressWarnings("unchecked")
	public List<SelectItem> getAllUniversities() {
		List<SelectItem> universityItems = new ArrayList<SelectItem>();
		List<UniversityInfo> allEnabledUniversities = universityService.findUniversitiesByEnabled(true);
		List<UniversityInfo> allDisabledUniversities = universityService.findUniversitiesByEnabled(false);

		if (!allEnabledUniversities.isEmpty()) {
			universityItems.add(new SelectItem(Constants.UNIVERSITIES_ENABLED, i18n("universities_enabled")));
			for (UniversityInfo university : allEnabledUniversities) {
				universityItems.add(new SelectItem(university.getId(), university.getName()));
			}
		}

		if (!allDisabledUniversities.isEmpty()) {
			universityItems.add(new SelectItem(Constants.UNIVERSITIES_ENABLED, i18n("universities_disabled")));
			for (UniversityInfo university : allDisabledUniversities) {
				universityItems.add(new SelectItem(university.getId(), university.getName()));
			}
		}

		return universityItems;
	}

	@SuppressWarnings("unchecked")
	public List<SelectItem> getAllDepartments() {
		logger.debug("getting departments for university:" + selectedUniversity);
		List<SelectItem> departmentItems = new ArrayList<SelectItem>();

		if (selectedUniversity == null || selectedUniversity < 0L) {
			departmentItems.add(new SelectItem(Constants.DEPARTMENTS_NO_UNIVERSITY_SELECTED,
					i18n("institute_registration_choose_university")));
		} else {
			List<DepartmentInfo> allEnabledDepartments = departmentService.findDepartmentsByUniversityAndEnabled(selectedUniversity, true);
			if (!allEnabledDepartments.isEmpty()) {
				departmentItems.add(new SelectItem(Constants.DEPARTMENTS_ENABLED, i18n("departments_enabled")));
				sortInDepartments(allEnabledDepartments, departmentItems);
			}

			List<DepartmentInfo> allDisabledDepartments = departmentService.findDepartmentsByUniversityAndEnabled(selectedUniversity, false);
			if (!allDisabledDepartments.isEmpty()) {
				departmentItems.add(new SelectItem(Constants.DEPARTMENTS_DISABLED, i18n("departments_disabled")));
				sortInDepartments(allDisabledDepartments, departmentItems);
			}
		}
		return departmentItems;
	}

	private void sortInDepartments(List<DepartmentInfo> departments, List<SelectItem> departmentItems) {
		String suffixOffical = " - (" + i18n("departmenttype_official") + ")";
		String suffixNonOffical = " - (" + i18n("departmenttype_non_offical") + ")";

		for (DepartmentInfo department : departments) {
			if (department.getDepartmentType() == DepartmentType.OFFICIAL) {
				departmentItems.add(new SelectItem(department.getId(), department.getName() + suffixOffical));
			} else if (department.getDepartmentType() == DepartmentType.NONOFFICIAL) {
				departmentItems.add(new SelectItem(department.getId(), department.getName() + suffixNonOffical));
			}
		}
	}

	public void processUniversitySelectChanged(ValueChangeEvent event) {
		Long universityId = null;

		try {
			universityId = (Long) event.getNewValue();
		} catch (Exception e) {
			logger.debug("ValueChangeEvent: Error: New value is could not be cast to Long");
		}
		if (universityId != null) {
			logger.info("ValueChangeEvent: Changing university id for new institute to " + universityId);
			selectedUniversity = universityId;
		}
	}

	public void processDepartmentSelectChanged(ValueChangeEvent event) {
		Long departmentId = null;
		try {
			departmentId = (Long) event.getNewValue();
		} catch (Exception e) {
			logger.debug("ValueChangeEvent: Error: New value is could not be cast to Long");
		}

		if (departmentId != null) {
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
