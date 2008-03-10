package org.openuss.web.lecture;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.security.ldap.RoleAttributeKeyInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * Abstract class which can be used to derive backing beans for roleAttributeKeys
 * overview views
 * 
 * @author Christian Grelle
 * @author Peter Schuh
 * 
 */
public abstract class AbstractLdapRoleAttributeKeysOverviewPage extends BasePage {

	protected static final Logger logger = Logger.getLogger(AbstractLdapRoleAttributeKeysOverviewPage.class);

	protected RoleAttributeKeyTable roleAttributeKeys = new RoleAttributeKeyTable();

	@Property(value = "#{ldapConfigurationService}")
	protected LdapConfigurationService ldapConfigurationService;

	@Prerender
	public void prerender() throws Exception {
	}

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
			logger.debug("Starting method selectRoleAttributeKeyAndRemove");
			RoleAttributeKeyInfo currentRoleAttributeKey = (RoleAttributeKeyInfo) getSessionBean(Constants.ROLEATTRIBUTEKEY_INFO);
			if (currentRoleAttributeKey.getAttributeMappingIds() == null || currentRoleAttributeKey.getAttributeMappingIds().size()==0) {
				ldapConfigurationService.deleteRoleAttributeKey(currentRoleAttributeKey);
				setSessionBean(Constants.ROLEATTRIBUTEKEY_INFO, null);
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
	
	protected DataPage<RoleAttributeKeyInfo> dataPage;

	public abstract DataPage<RoleAttributeKeyInfo> fetchDataPage(int startRow,
			int pageSize);

	protected void sort(List<RoleAttributeKeyInfo> roleAttributeKeyList) {
				Collections.sort(roleAttributeKeyList, new NameComparator());		
	}

	public LdapConfigurationService getLdapConfigurationService() {
		return ldapConfigurationService;
	}

	public void setLdapConfigurationService(LdapConfigurationService ldapConfigurationService) {
		this.ldapConfigurationService = ldapConfigurationService;
	}

	protected class NameComparator implements Comparator<RoleAttributeKeyInfo> {
		public int compare(RoleAttributeKeyInfo f1, RoleAttributeKeyInfo f2) {
			if (roleAttributeKeys.isAscending()) {
				return f1.getName().compareToIgnoreCase(f2.getName());
			} else {
				return f2.getName().compareToIgnoreCase(f1.getName());
			}
		}
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

		@Override
		public DataPage<RoleAttributeKeyInfo> getDataPage(int startRow, int pageSize) {
			return fetchDataPage(startRow, pageSize);
		}

	}
}