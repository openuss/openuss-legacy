package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.security.ldap.AuthenticationDomainInfo;
import org.openuss.security.ldap.LdapServerInfo;
import org.openuss.security.ldap.UserDnPatternInfo;
import org.openuss.web.Constants;

/**
 * Backing bean for the ldap_domain registration. Is responsible starting the
 * wizard, binding the values and registrating the department.
 * 
 * @author
 * 
 */

@Bean(name = Constants.LDAP_SERVER_REGISTRATION_CONTROLLER, scope = Scope.REQUEST)
@View
public class LdapServerRegistrationController extends AbstractLdapServerPage {

	
	private static final Logger logger = Logger.getLogger(LdapServerRegistrationController.class);
	/*
	protected UniversityInfo universityInfo = (UniversityInfo) this.getSessionBean(Constants.UNIVERSITY_INFO);

	private List<SelectItem> localeItems;
	
	private List<SelectItem> universityItems;
	
	private List<UniversityInfo> allEnabledUniversities;
	private List<UniversityInfo> allDisabledUniversities;

	private ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{visit.locale}");
	private String locale = (String) binding.getValue(getFacesContext());
	private ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(locale));

	*/
	
	public String start() {
		logger.debug("start registration process");
		
		ldapServerInfo = new LdapServerInfo();
		setSessionBean(Constants.SERVER_INFO, ldapServerInfo);
		
		return Constants.LDAP_SERVER_REGISTRATION_STEP1_PAGE;
	}


	
	public void processValueChangeUserDnPattern(ValueChangeEvent valueChangeEvent) {
		List selectedIds = (ArrayList) valueChangeEvent.getNewValue();
		ldapServerInfo.setUserDnPatternIds(selectedIds);
	}		
	
	
	public List<SelectItem> getAllAuthenticationDomains() {

		List<SelectItem> domainItems = new ArrayList<SelectItem>();

		List<AuthenticationDomainInfo> domains = ldapConfigurationService.getAllDomains();
		
		for (AuthenticationDomainInfo domain : domains) {
				domainItems.add(new SelectItem(domain.getId(), domain.getName()));
			}
	
		return domainItems;
	}

	public List<SelectItem> getAllUserDnPatterns() {

		List<SelectItem> userDnPatternItems = new ArrayList<SelectItem>();
		List<UserDnPatternInfo> userDnPatterns = ldapConfigurationService.getAllUserDnPatterns();
		
		for (UserDnPatternInfo userDnPattern : userDnPatterns) {
			userDnPatternItems.add(new SelectItem(userDnPattern.getId(), userDnPattern.getName()));
			}
	
		return userDnPatternItems;
	}
	
	
	/*
	private List<SelectItem> roleAttributeKeyIds = new Vector<SelectItem>();
	
	public List<SelectItem> getRoleAttributeKeyIds() {
		return roleAttributeKeyIds;
	}

	public void setRoleAttributeKeyIds(List<SelectItem> roleAttributeKeyIds) {
		this.roleAttributeKeyIds = roleAttributeKeyIds;
	}
	
	// ================================================================================
	
	
	
	
	
	
	
	
	private Long roleAttributeKeyId;
	
	public Long getRoleAttributeKeyId(){
		return roleAttributeKeyId;
	}
	public void setRoleAttributeKeyId(Long roleAttributeKeyId){
		this.roleAttributeKeyId = roleAttributeKeyId;
	}
	*/
/*		
	List<RoleAttributeKeyInfo> roleAttributeKeyList = ldapConfigurationService.getAllRoleAttributeKeys();
	
	@SuppressWarnings( { "unchecked" })
	public List<SelectItem> getAllUniversities() {

		universityItems = new ArrayList<SelectItem>();

		allEnabledUniversities = universityService.findUniversitiesByEnabled(true);
		allDisabledUniversities = universityService.findUniversitiesByEnabled(false);

		Iterator<UniversityInfo> iterEnabled = allEnabledUniversities.iterator();
		UniversityInfo universityEnabled;

		if (iterEnabled.hasNext()) {
			SelectItem item = new SelectItem(Constants.UNIVERSITIES_ENABLED, bundle.getString("universities_enabled"));
			universityItems.add(item);
		}
		while (iterEnabled.hasNext()) {
			universityEnabled = iterEnabled.next();
			SelectItem item = new SelectItem(universityEnabled.getId(), universityEnabled.getName());
			universityItems.add(item);
		}

		Iterator<UniversityInfo> iterDisabled = allDisabledUniversities.iterator();
		UniversityInfo universityDisabled;

		if (iterDisabled.hasNext()) {
			SelectItem item = new SelectItem(Constants.UNIVERSITIES_DISABLED, bundle.getString("universities_disabled"));
			universityItems.add(item);
		}
		while (iterDisabled.hasNext()) {
			universityDisabled = iterDisabled.next();
			SelectItem item = new SelectItem(universityDisabled.getId(), universityDisabled.getName());
			universityItems.add(item);
		}
		return universityItems;
	}
*/
	public String register() /*throws DesktopException, LectureException*/ {
		/*// create department
		if (user.getId().longValue() != Constants.USER_SUPER_ADMIN && departmentInfo.getUniversityId() == null)
			departmentInfo.setUniversityId(universityInfo.getId());

		// by default set department enabled
		departmentInfo.setEnabled(true);
		*/
		ldapConfigurationService.createLdapServer(ldapServerInfo);

		return Constants.LDAP_ATTRIBUTEMAPPING_PAGE;
	}
/*
	public String registrate() throws DesktopException, LectureException {
		// create department
		if (user.getId().longValue() != Constants.USER_SUPER_ADMIN && departmentInfo.getUniversityId() == null)
			departmentInfo.setUniversityId(universityInfo.getId());

		// by default set department enabled
		departmentInfo.setEnabled(true);
		departmentService.create(departmentInfo, user.getId());

		return Constants.DEPARTMENT_PAGE;
	}

	public String getTransformedLocale() {
		if (departmentInfo.getLocale().equals("en")) {
			return bundle.getString("transform_locale_en");
		} else if (departmentInfo.getLocale().equals("de")) {
			return bundle.getString("transform_locale_de");
		} else if (departmentInfo.getLocale().equals("ru")) {
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
	*/


}
