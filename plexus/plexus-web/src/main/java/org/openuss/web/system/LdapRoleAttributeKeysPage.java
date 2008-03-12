package org.openuss.web.system;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.security.ldap.RoleAttributeKeyInfo;
import org.openuss.web.Constants;

/** 
 * Backing Bean for the view secured/system/departments.xhtml
 * 
 * 
 * @author Peter Schuh
 * @author Christian Grelle
 */
@Bean(name = "views$secured$system$ldap$ldap_roleattributekeys", scope = Scope.REQUEST)
@View

public class LdapRoleAttributeKeysPage extends AbstractLdapRoleAttributeKeysOverviewPage{
	
	protected RoleAttributeKeyTable roleAttributeKeys = new RoleAttributeKeyTable();
	

	protected RoleAttributeKeyInfo currentRoleAttributeKey() {
		RoleAttributeKeyInfo roleAttributeKey = roleAttributeKeys.getRowData();
		return roleAttributeKey;
	}

	/**
	 * Store the selected roleAttributeKey into session scope and go to roleAttributeKey
	 * edit page.
	 * 
	 * @return Outcome
	 */
	public String selectRoleAttributeKeyAndEdit() {
		RoleAttributeKeyInfo roleAttributeKey = currentRoleAttributeKey();
		setSessionBean(Constants.ROLEATTRIBUTEKEY_INFO, roleAttributeKey);		
		return Constants.LDAP_ROLEATTRIBUTEKEY_REGISTRATION_STEP1_PAGE;
	}

	/**
	 * Store the selected roleAttributeKey into session scope and go to roleAttributeKey
	 * remove confirmation page.
	 * 
	 * @return Outcome
	 */
	public String selectRoleAttributeKeyAndConfirmRemove() throws Exception {
		logger.debug("Starting method selectRoleAttributeKeyAndConfirmRemove");		
		setSessionBean(Constants.ROLEATTRIBUTEKEY_INFO, currentRoleAttributeKey());
		
		return Constants.ROLEATTRIBUTEKEY_CONFIRM_REMOVE_PAGE;
	}	
	
	public String removeRoleAttributeKey() throws Exception {
		try {
			logger.debug("Starting method removeRoleAttributeKey");
			RoleAttributeKeyInfo currentRoleAttributeKey = (RoleAttributeKeyInfo) getSessionBean(Constants.ROLEATTRIBUTEKEY_INFO);
			if (currentRoleAttributeKey.getAttributeMappingIds() == null || currentRoleAttributeKey.getAttributeMappingIds().size()==0) {
				ldapConfigurationService.deleteRoleAttributeKey(currentRoleAttributeKey);
				setSessionBean(Constants.ROLEATTRIBUTEKEY_INFO, null);
				addMessage(i18n("message_ldap_roleattributekey_removed"));
				return Constants.LDAP_ROLEATTRIBUTEKEY_PAGE;
			} else {
				addMessage(i18n("message_ldap_roleattributekey_still_in_use_cannot_be_removed"));
				return Constants.LDAP_ROLEATTRIBUTEKEY_PAGE;
			  }
			}
			catch (Exception e) {
				addMessage(i18n("message_ldap_roleattributekey_cannot_be_removed"));
				return Constants.LDAP_ROLEATTRIBUTEKEY_PAGE;
			}
	}
	
	
	public LdapConfigurationService getLdapConfigurationService() {
		return ldapConfigurationService;
	}

	public void setLdapConfigurationService(LdapConfigurationService ldapConfigurationService) {
		this.ldapConfigurationService = ldapConfigurationService;
	}

	
	public String confirmRemoveRoleAttributeKey() {
		RoleAttributeKeyInfo roleAttributeKeyInfo = currentRoleAttributeKey();
		setSessionBean(Constants.ROLEATTRIBUTEKEY, roleAttributeKeyInfo);
		return "removed";
	}

	public RoleAttributeKeyTable getRoleAttributeKeys() {
		return roleAttributeKeys;
	}
	
	
	protected class RoleAttributeKeyTable extends AbstractPagedTable<RoleAttributeKeyInfo> {
		
		private DataPage<RoleAttributeKeyInfo> dataPage;

		@Override
		public DataPage<RoleAttributeKeyInfo> getDataPage(int startRow, int pageSize) {
			if (dataPage == null) {
				if (logger.isDebugEnabled()) {
					logger.debug("fetch roleattributekeys data page at " + startRow + ", "+ pageSize+" sorted by "+roleAttributeKeys.getSortColumn());
				}
				List<RoleAttributeKeyInfo> roleAttributeKeyList = ldapConfigurationService.getAllRoleAttributeKeys();
				

				logger.info("RoleAttributeKeys:"+roleAttributeKeyList);
				if (roleAttributeKeyList != null) {
					logger.info("Size:"+roleAttributeKeyList.size());
				}
				
				sort(roleAttributeKeyList);
				dataPage = new DataPage<RoleAttributeKeyInfo>(roleAttributeKeyList.size(),0,roleAttributeKeyList);
			}
			
			return dataPage;
		}

	}	
}