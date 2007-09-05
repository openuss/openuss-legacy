package org.openuss.web.lecture;

import java.util.ArrayList;
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
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityType;
import org.openuss.web.Constants;


/**
 * Backing bean for the university registration. Is responsible starting the wizard, binding the values and registrating
 * the university.
 * 
 * @author Kai Stettner
 *
 */


@Bean(name=Constants.UNIVERSITY_REGISTRATION_CONTROLLER, scope=Scope.REQUEST)
@View
public class UniversityRegistrationController extends AbstractUniversityPage{

	private static final Logger logger = Logger.getLogger(UniversityRegistrationController.class);
	
	private List<SelectItem> localeItems;
	
	public List<SelectItem> getSupportedOrganizationTypes() {
		
		ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{visit.locale}");
		String locale = (String)binding.getValue(getFacesContext());
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
		universityInfo = new UniversityInfo();
		setSessionBean(Constants.UNIVERSITY_INFO, universityInfo);
		
		return Constants.UNIVERSITY_REGISTRATION_STEP1_PAGE;
	}
	
	public String registrate() throws DesktopException, LectureException {
		//set University by default enabled
		universityInfo.setEnabled(true);
		// create university
		Long universityId = universityService.createUniversity(universityInfo, user.getId());
		universityInfo.setId(universityId);
					
		return Constants.UNIVERSITY_PAGE;
	}
	
}
