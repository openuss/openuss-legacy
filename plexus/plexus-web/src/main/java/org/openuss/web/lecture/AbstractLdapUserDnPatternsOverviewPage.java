package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.ldap.AttributeMappingInfo;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.security.ldap.RoleAttributeKeyInfo;
import org.openuss.security.ldap.UserDnPatternInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * Abstract class which can be used to derive backing beans for userDnPatterns
 * overview views
 * 
 * 
 */
public abstract class AbstractLdapUserDnPatternsOverviewPage extends BasePage {

	protected static final Logger logger = Logger.getLogger(AbstractLdapUserDnPatternsOverviewPage.class);

	protected UserDnPatternTable userDnPatterns = new UserDnPatternTable();

	@Property(value = "#{ldapConfigurationService}")
	protected LdapConfigurationService ldapConfigurationService;

	@Prerender
	public void prerender() throws Exception {
	}

	protected UserDnPatternInfo currentUserDnPattern() {
		UserDnPatternInfo userDnPattern = userDnPatterns.getRowData();
		return userDnPattern;
	}

	/**
	 * Store the selected userDnPattern into session scope and go to userDnPattern
	 * main page.
	 * 
	 * @return Outcome
	 */
	public String selectUserDnPattern() {
		UserDnPatternInfo userDnPattern = currentUserDnPattern();
		setSessionBean(Constants.USERDNPATTERN_INFO, userDnPattern);
		//TODO: CHRISTIAN: WRONG OUTCOME!!!
		return Constants.DEPARTMENT_PAGE;
	}

	/**
	 * Store the selected userDnPattern into session scope and go to userDnPattern
	 * remove confirmation page.
	 * 
	 * @return Outcome
	 */
	
	public String selectUserDnPatternAndRemove() throws Exception {
		try {
		logger.debug("Starting method selectRoleAttributeKeyAndRemove");
		UserDnPatternInfo currentUserDnPattern = currentUserDnPattern();
		setSessionBean(Constants.ROLEATTRIBUTEKEY_INFO, currentUserDnPattern);
		ldapConfigurationService.deleteUserDnPattern(currentUserDnPattern);
		setSessionBean("userDnPatternInfo", null);
		return Constants.LDAP_USERDNPATTERN_PAGE;
		}
		catch (Exception e) {
			addMessage(i18n("message_department_cannot_be_removed"));
			return Constants.LDAP_USERDNPATTERN_PAGE;
		}
	}	
	
		
	public String removeUserDnPattern() throws Exception {
		UserDnPatternInfo currentUserDnPattern = currentUserDnPattern();
			ldapConfigurationService.deleteUserDnPattern(currentUserDnPattern);
			setSessionBean("userDnPatternInfo", null);
			//TODO: CHRISTIAN: WRONG MESSAGE!!!
			addMessage(i18n("message_department_removed"));
			return Constants.LDAP_USERDNPATTERN_PAGE;		
	}
	

	protected DataPage<UserDnPatternInfo> dataPage;

	public abstract DataPage<UserDnPatternInfo> fetchDataPage(int startRow,
			int pageSize);
/*
	protected DataPage<DepartmentInfo> dataPage;

	public abstract DataPage<DepartmentInfo> fetchDataPage(int startRow,
			int pageSize);

	protected void sort(List<DepartmentInfo> departmentList) {
		if (StringUtils.equals("shortcut", departments.getSortColumn())) {
			Collections.sort(departmentList, new ShortcutComparator());
		} else if (StringUtils.equals("city", departments.getSortColumn())) {
			Collections.sort(departmentList, new CityComparator());
		} else if (StringUtils.equals("country", departments.getSortColumn())) {
			Collections.sort(departmentList, new CountryComparator());
		} else {
			Collections.sort(departmentList, new NameComparator());
		}
	}
*/
	public LdapConfigurationService getLdapConfigurationService() {
		return ldapConfigurationService;
	}

	public void setLdapConfigurationService(LdapConfigurationService ldapConfigurationService) {
		this.ldapConfigurationService = ldapConfigurationService;
	}

	/* ----------- departments sorting comparators ------------- */
/*
	protected class NameComparator implements Comparator<DepartmentInfo> {
		public int compare(DepartmentInfo f1, DepartmentInfo f2) {
			if (departments.isAscending()) {
				return f1.getName().compareToIgnoreCase(f2.getName());
			} else {
				return f2.getName().compareToIgnoreCase(f1.getName());
			}
		}
	}

	protected class CityComparator implements Comparator<DepartmentInfo> {
		public int compare(DepartmentInfo f1, DepartmentInfo f2) {
			if (departments.isAscending()) {
				return f1.getCity().compareToIgnoreCase(f2.getCity());
			} else {
				return f2.getCity().compareToIgnoreCase(f1.getCity());
			}
		}
	}

	protected class CountryComparator implements Comparator<DepartmentInfo> {
		public int compare(DepartmentInfo f1, DepartmentInfo f2) {
			if (departments.isAscending()) {
				return f1.getCountry().compareToIgnoreCase(f2.getCountry());
			} else {
				return f2.getCountry().compareToIgnoreCase(f1.getCountry());
			}
		}
	}

	protected class ShortcutComparator implements Comparator<DepartmentInfo> {
		public int compare(DepartmentInfo f1, DepartmentInfo f2) {
			if (departments.isAscending()) {
				return f1.getShortcut().compareToIgnoreCase(f2.getShortcut());
			} else {
				return f2.getShortcut().compareToIgnoreCase(f1.getShortcut());
			}
		}
	}
*/
	
	public String confirmRemoveUserDnPattern() {
		UserDnPatternInfo userDnPatternInfo = currentUserDnPattern();
		setSessionBean(Constants.USERDNPATTERN, userDnPatternInfo);
		return "removed";
	}

	public UserDnPatternTable getUserDnPatterns() {
		return userDnPatterns;
	}
	
	/*
	
	public DepartmentTable getDepartments() {
		return departments;
	}

	public OrganisationService getOrganisationService() {
		return organisationService;
	}

	public void setOrganisationService(OrganisationService organisationService) {
		this.organisationService = organisationService;
	}
*/
	protected class UserDnPatternTable extends AbstractPagedTable<UserDnPatternInfo> {

		@Override
		public DataPage<UserDnPatternInfo> getDataPage(int startRow, int pageSize) {
			return fetchDataPage(startRow, pageSize);
		}

	}
}