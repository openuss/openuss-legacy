package org.openuss.web.lecture;

import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.security.ldap.RoleAttributeKeyInfo;
import org.openuss.web.Constants;

/**
 * Backing bean for the ldap_domain registration. Is responsible starting the
 * wizard, binding the values and registrating the department.
 * 
 * @author
 * 
 */

@Bean(name = Constants.LDAP_ROLEATTRIBUTEKEY_REGISTRATION_CONTROLLER, scope = Scope.REQUEST)
@View
public class LdapRoleAttributeKeyRegistrationController extends AbstractLdapRoleAttributeKeyPage {

	
	private static final Logger logger = Logger.getLogger(LdapRoleAttributeKeyRegistrationController.class);

	public String start() {
		logger.debug("start registration process");
		
		roleAttributeKeyInfo = new RoleAttributeKeyInfo();
		setSessionBean(Constants.ROLEATTRIBUTEKEY_INFO, roleAttributeKeyInfo);
		
		return Constants.LDAP_ROLEATTRIBUTEKEY_REGISTRATION_STEP1_PAGE;
	}

	/*
	public List<SelectItem> getSupportedDepartmentTypes() {
		localeItems = new ArrayList<SelectItem>();

		SelectItem item1 = new SelectItem(DepartmentType.OFFICIAL, bundle.getString("departmenttype_official"));
		SelectItem item2 = new SelectItem(DepartmentType.NONOFFICIAL, bundle.getString("departmenttype_non_offical"));

		localeItems.add(item1);
		localeItems.add(item2);

		return localeItems;
	}
	
	
*/	
	@SuppressWarnings( { "unchecked" })
	public List<SelectItem> getAllRoleAttributeKeys() {
		return ldapConfigurationService.getAllRoleAttributeKeys();
	}
/*		
	
	
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
	public String register() {
		ldapConfigurationService.createRoleAttributeKey(roleAttributeKeyInfo);
		return Constants.LDAP_ROLEATTRIBUTEKEY_PAGE;
	}
	
	public String save() {
		ldapConfigurationService.saveRoleAttributeKey(roleAttributeKeyInfo);
		return Constants.LDAP_ROLEATTRIBUTEKEY_PAGE;
	}

}
