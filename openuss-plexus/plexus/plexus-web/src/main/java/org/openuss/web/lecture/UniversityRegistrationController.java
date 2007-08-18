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
 * @author Kai Stettner
 *
 */


@Bean(name=Constants.UNIVERSITY_REGISTRATION_CONTROLLER, scope=Scope.REQUEST)
@View
public class UniversityRegistrationController extends AbstractUniversityPage{

	private static final Logger logger = Logger.getLogger(UniversityRegistrationController.class);
	
	private List<SelectItem> localeItems;
	
	private UniversityType universityType;
	
	public List<SelectItem> getSupportedOrganizationTypes() {
		
		ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{visit.locale}");
		String locale = (String)binding.getValue(getFacesContext());
		ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(locale));
	
		localeItems = new ArrayList<SelectItem>();
	
		SelectItem item1 = new SelectItem( universityType.UNIVERSITY, bundle.getString("organizationtype_university"));
		SelectItem item2 = new SelectItem(universityType.COMPANY, bundle.getString("organizationtype_company"));
		SelectItem item3 = new SelectItem(universityType.MISC, bundle.getString("organizationtype_misc"));
		
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
		
		universityInfo.setOwnerName(user.getName());
		universityInfo.setEnabled(true);
	
		Long universityId = universityService.createUniversity(universityInfo, user.getId());
		universityInfo.setId(universityId);
		//TODO send notification email
		//FIXME this should be part of the business layer
		//desktopService.linkUniversity(desktop.getId(), organisation.getId());
				
		return Constants.UNIVERSITY;
	}
	
}
