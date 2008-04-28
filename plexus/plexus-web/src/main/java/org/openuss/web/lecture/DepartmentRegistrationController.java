package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.el.ValueBinding;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentType;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/**
 * Backing bean for the department registration. Is responsible starting the
 * wizard, binding the values and registrating the department.
 * 
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */

@Bean(name = Constants.DEPARTMENT_REGISTRATION_CONTROLLER, scope = Scope.REQUEST)
@View
public class DepartmentRegistrationController extends AbstractDepartmentPage {

	@Property (value="#{"+Constants.DEPARTMENT_APPLICATION+"}")
	private DepartmentInfo departmentApplication;

	private static final Logger logger = Logger.getLogger(DepartmentRegistrationController.class);

	private List<SelectItem> localeItems;

	private ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{visit.locale}");
	private String locale = (String) binding.getValue(getFacesContext());
	private ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(locale));

	public String start() {
		logger.debug("start registration process");
		departmentApplication = new DepartmentInfo();
		//set department type to official
		departmentApplication.setDepartmentType(DepartmentType.OFFICIAL);
		// FIXME Do not use session bean for navigation - DepartmentApplication define
		setSessionBean(Constants.DEPARTMENT_APPLICATION, departmentApplication);
		return Constants.DEPARTMENT_REGISTRATION_STEP1_PAGE;
	}

	public List<SelectItem> getSupportedDepartmentTypes() {
		localeItems = new ArrayList<SelectItem>();

		SelectItem item1 = new SelectItem(DepartmentType.OFFICIAL, i18n("departmenttype_official"));
		SelectItem item2 = new SelectItem(DepartmentType.NONOFFICIAL, i18n("departmenttype_non_offical"));

		localeItems.add(item1);
		localeItems.add(item2);

		return localeItems;
	}

	public String registrate() throws DesktopException, LectureException {
		// create department
		if (departmentApplication.getUniversityId() == null){
			departmentApplication.setUniversityId(getUniversityInfo().getId());
		}

		// by default set department enabled
		departmentInfo.setEnabled(true);
		departmentService.create(departmentInfo, user.getId());
		
		addMessage(i18n("department_registration_success"));

		return Constants.DEPARTMENT_PAGE;
	}

	public String getTransformedLocale() {
		if (departmentApplication.getLocale().equals("en")) {
			return bundle.getString("transform_locale_en");
		} else if (departmentApplication.getLocale().equals("de")) {
			return bundle.getString("transform_locale_de");
		} else if (departmentApplication.getLocale().equals("ru")) {
			return bundle.getString("transform_locale_ru");
		} else {
			return "";
		}
	}

	public String getTransformedDepartmentType() {
		if (departmentApplication.getDepartmentType().getValue() == 0) {
			return bundle.getString("departmenttype_official");
		} else if (departmentApplication.getDepartmentType().getValue() == 1) {
			return bundle.getString("departmenttype_non_offical");
		} else {
			return "";
		}
	}

	public DepartmentInfo getDepartmentApplication() {
		return departmentApplication;
	}

	public void setDepartmentApplication(DepartmentInfo departmentApplication) {
		this.departmentApplication = departmentApplication;
	}

}
