package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.security.ldap.AttributeMappingInfo;
import org.openuss.security.ldap.RoleAttributeKeyInfo;
import org.openuss.web.Constants;

/**
 * Backing bean for the attributeMapping maintenance.
 * 
 * @author Peter Schuh
 * @author Christian Grelle
 * 
 */

@Bean(name = Constants.LDAP_ATTRIBUTEMAPPING_REGISTRATION_CONTROLLER, scope = Scope.REQUEST)
@View
public class LdapAttributeMappingRegistrationController extends AbstractLdapAttributeMappingPage {

	
	private static final Logger logger = Logger.getLogger(LdapAttributeMappingRegistrationController.class);
	
	private List<String> initiallySelectedRoleAttributeKeyIds = new Vector<String>();
	private List<Long> finallySelectedRoleAttributeKeyIds = new Vector<Long>();

	public String start() {
		logger.debug("start registration process");
		
		attributeMappingInfo = new AttributeMappingInfo();
		setSessionBean(Constants.ATTRIBUTEMAPPING_INFO, attributeMappingInfo);
		
		return Constants.LDAP_ATTRIBUTEMAPPING_REGISTRATION_STEP1_PAGE;
	}
	
	public List<SelectItem> getAllRoleAttributeKeys() {
		List<SelectItem> roleAttributeKeyItems = new ArrayList<SelectItem>();
		List<RoleAttributeKeyInfo> roleAttributeKeys = ldapConfigurationService.getAllRoleAttributeKeys();
		for (RoleAttributeKeyInfo roleAttributeKey : roleAttributeKeys) {
				roleAttributeKeyItems.add(new SelectItem(String.valueOf(roleAttributeKey.getId()), roleAttributeKey.getName()));
			}	
		return roleAttributeKeyItems;
	}


	public void processValueChange(ValueChangeEvent valueChangeEvent) {
	      ArrayList selectedIds = (ArrayList)valueChangeEvent.getNewValue();
	      for (Iterator iterator = selectedIds.iterator(); iterator.hasNext();) {
			String idAsString = (String) iterator.next();
			Long id = Long.valueOf(idAsString);
			finallySelectedRoleAttributeKeyIds.add(id);
			System.out.println(id);
		    logger.info(id);
	      }
	}	
	
	
	// ================================================================================
	
	
	
	
	
	
	
	
	private Long roleAttributeKeyId;
	
	public Long getRoleAttributeKeyId(){
		return roleAttributeKeyId;
	}
	public void setRoleAttributeKeyId(Long roleAttributeKeyId){
		this.roleAttributeKeyId = roleAttributeKeyId;
	}
	
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
		ldapConfigurationService.createAttributeMapping(attributeMappingInfo);

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
