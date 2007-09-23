package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.el.ValueBinding;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentType;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityInfo;
import org.openuss.web.Constants;


/**
 * Backing bean for the department registration. Is responsible starting the wizard, binding the values and registrating
 * the department.
 * 
 * @author Kai Stettner
 *
 */

@Bean(name=Constants.DEPARTMENT_REGISTRATION_CONTROLLER, scope=Scope.REQUEST)
@View
public class DepartmentRegistrationController extends AbstractDepartmentPage{

	private static final Logger logger = Logger.getLogger(DepartmentRegistrationController.class);

	protected UniversityInfo universityInfo = (UniversityInfo)this.getSessionBean(Constants.UNIVERSITY_INFO);
	
	private List<SelectItem> localeItems;
	
	private List<SelectItem> universityItems;
	private List<UniversityInfo> allEnabledUniversities;
	private List<UniversityInfo> allDisabledUniversities;
		
	private ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{visit.locale}");
	private String locale = (String)binding.getValue(getFacesContext());
	private ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(locale));
	


	public String start() {
		
		logger.debug("start registration process");
		departmentInfo = new DepartmentInfo() ;
		setSessionBean(Constants.DEPARTMENT_INFO, departmentInfo);
		
		return Constants.DEPARTMENT_REGISTRATION_STEP1_PAGE;
	}
	
	public List<SelectItem> getSupportedDepartmentTypes() {
		

	
		localeItems = new ArrayList<SelectItem>();
	
		SelectItem item1 = new SelectItem(DepartmentType.OFFICIAL, bundle.getString("departmenttype_official"));
		SelectItem item2 = new SelectItem(DepartmentType.NONOFFICIAL, bundle.getString("departmenttype_non_offical"));
		
		localeItems.add(item1);
		localeItems.add(item2);

		return localeItems;
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
	
	public String registrate() throws DesktopException, LectureException {
		
		//create department
		if (user.getId().longValue()!= Constants.USER_SUPER_ADMIN && departmentInfo.getUniversityId()== null)
			departmentInfo.setUniversityId(universityInfo.getId());
		
		//by default set department enabled
		departmentInfo.setEnabled(true);
		departmentService.create(departmentInfo, user.getId());
			
		return Constants.DEPARTMENT_PAGE;
	}
	
	public String getTransformedLocale() {
		if (departmentInfo.getLocale().toString().equals("en")) {
			return bundle.getString("transform_locale_en");
		} else if (departmentInfo.getLocale().toString().equals("de")) {
			return bundle.getString("transform_locale_de");
		} else if (departmentInfo.getLocale().toString().equals("ru")) {
			return bundle.getString("transform_locale_ru");
		} else {
			return "";
		}
	}
	
	public String getTransformedDepartmentType() {
		if (departmentInfo.getDepartmentType().getValue() == 0) {
			return bundle.getString("departmenttype_official");
		} else if (departmentInfo.getDepartmentType().getValue() == 1) {
			return bundle.getString("departmenttype_non_offical");
		} else {
			return "";
		}
	}
	
}
