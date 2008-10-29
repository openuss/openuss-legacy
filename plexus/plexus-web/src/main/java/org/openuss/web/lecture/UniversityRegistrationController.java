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
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityType;
import org.openuss.web.Constants;

/**
 * Backing bean for the university registration. Is responsible starting the
 * wizard, binding the values and registrating the university.
 * 
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
@Bean(name = Constants.UNIVERSITY_REGISTRATION_CONTROLLER, scope = Scope.REQUEST)
@View
public class UniversityRegistrationController extends AbstractUniversityPage {

	private static final Logger logger = Logger.getLogger(UniversityRegistrationController.class);

	@Property(value = "#{"+Constants.UNIVERSITY_APPLICATION+"}")
	protected UniversityInfo universityApplication;	
	
	private List<SelectItem> localeItems;

	private ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{visit.locale}");
	private String locale = (String) binding.getValue(getFacesContext());
	private ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(locale));

	public List<SelectItem> getSupportedOrganizationTypes() {

		ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{visit.locale}");
		String locale = (String) binding.getValue(getFacesContext());
		ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(locale));

		localeItems = new ArrayList<SelectItem>();

		SelectItem item1 = new SelectItem(UniversityType.UNIVERSITY, bundle.getString("organizationtype_university"));
		SelectItem item2 = new SelectItem(UniversityType.COMPANY, bundle.getString("organizationtype_company"));
		SelectItem item3 = new SelectItem(UniversityType.MISC, bundle.getString("organizationtype_misc"));

		localeItems.add(item1);
		localeItems.add(item2);
		localeItems.add(item3);

		return localeItems;
	}

	public String start() {
		logger.debug("start registration process");
		universityApplication = new UniversityInfo();
		// FIXME Do not use session bean for navigation - UniversityApplication define
		setSessionBean(Constants.UNIVERSITY_APPLICATION, universityApplication);
		return Constants.UNIVERSITY_REGISTRATION_STEP1_PAGE;
	}

	public String registrate() throws DesktopException, LectureException {
		// set University by default enabled
		universityApplication.setEnabled(true);
		// create university
		Long universityId = universityService.createUniversity(universityApplication, user.getId());
		universityApplication.setId(universityId);
		// FIXME Do not use session bean for navigation - UniversityRegistration remove
		setSessionBean(Constants.UNIVERSITY_APPLICATION, null);
		universityInfo.setId(universityId);
		addMessage(i18n("university_registration_success"));
		
		return Constants.UNIVERSITY_PAGE;
	}

	public String getTransformedLocale() {
		if (universityApplication.getLocale().equals("en")) {
			return bundle.getString("transform_locale_en");
		} else if (universityApplication.getLocale().equals("de")) {
			return bundle.getString("transform_locale_de");
		} else if (universityApplication.getLocale().equals("ru")) {
			return bundle.getString("transform_locale_ru");
		} else {
			return "";
		}
	}

	public String getTransformedUniversityType() {
		if (universityApplication.getUniversityType().getValue() == -1) {
			return bundle.getString("organizationtype_misc");
		} else if (universityApplication.getUniversityType().getValue() == 0) {
			return bundle.getString("organizationtype_university");
		} else if (universityApplication.getUniversityType().getValue() == 2) {
			return bundle.getString("organizationtype_company");
		} else {
			return "";
		}
	}

	public UniversityInfo getUniversityApplication() {
		return universityApplication;
	}

	public void setUniversityApplication(UniversityInfo universityApplication) {
		this.universityApplication = universityApplication;
	}

}
