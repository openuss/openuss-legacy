package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.ldap.AttributeMappingInfo;
import org.openuss.security.ldap.AuthenticationDomainInfo;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * Abstract class which can be used to derive backing beans for authentication domains
 * overview views
 * 
 * @author Christian Grelle
 * @author Peter Schuh
 * 
 */
public abstract class AbstractLdapDomainsOverviewPage extends BasePage {

	protected static final Logger logger = Logger.getLogger(AbstractLdapDomainsOverviewPage.class);

	protected AuthenticationDomainTable authenticationDomains = new AuthenticationDomainTable();

	@Property(value = "#{ldapConfigurationService}")
	protected LdapConfigurationService ldapConfigurationService;

	@Prerender
	public void prerender() throws Exception {
	}

	protected AuthenticationDomainInfo currentAuthenticationDomain() {
		AuthenticationDomainInfo authenticationDomain = authenticationDomains.getRowData();
		return authenticationDomain;
	}

	/**
	 * Store the selected authentication domain into session scope and go to authentication domain
	 * main page.
	 * 
	 * @return Outcome
	 */
	public String selectAuthenticationDomain() {
		AuthenticationDomainInfo authenticationDomain = currentAuthenticationDomain();
		setSessionBean(Constants.AUTHENTICATIONDOMAIN_INFO, authenticationDomain);
		//TODO: CHRISTIAN: WRONG OUTCOME!!!
		return Constants.DEPARTMENT_PAGE;
	}

	/**
	 * Store the selected department into session scope and go to authentication domain
	 * remove confirmation page.
	 * 
	 * @return Outcome
	 */
	public String selectAuthenticationDomainAndConfirmRemove() {
		AuthenticationDomainInfo currentAuthenticationDomain = currentAuthenticationDomain();
		setSessionBean(Constants.AUTHENTICATIONDOMAIN_INFO, currentAuthenticationDomain);

		return Constants.DOMAIN_CONFIRM_REMOVE_PAGE;
	}
		
	public String removeAuthenticationDomain() throws Exception {
			AuthenticationDomainInfo currentAuthenticationDomain = currentAuthenticationDomain();
			ldapConfigurationService.deleteDomain(currentAuthenticationDomain);
			setSessionBean("authenticationDomainInfo", null);
			//TODO: CHRISTIAN: WRONG MESSAGE!!!
			addMessage(i18n("message_department_removed"));
			return Constants.LDAP_DOMAIN_PAGE;				
	}	

	protected DataPage<AuthenticationDomainInfo> dataPage;

	public abstract DataPage<AuthenticationDomainInfo> fetchDataPage(int startRow,
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
/*
	public UniversityInfo getUniversityInfo() {
		return universityInfo;
	}

	public void setUniversityInfo(UniversityInfo universityInfo) {
		this.universityInfo = universityInfo;
	}
*/
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
	
	public String confirmRemoveAuthenticationDomain() {
		AuthenticationDomainInfo authenticationDomainInfo = currentAuthenticationDomain();
		setSessionBean(Constants.DOMAIN, authenticationDomainInfo);
		return "removed";
	}

	public AuthenticationDomainTable getAuthenticationDomains() {
		return authenticationDomains;
	}
	
	/*
	
	public String confirmRemoveDepartment() {
		DepartmentInfo departmentInfo = currentDepartment();
		setSessionBean(Constants.DEPARTMENT, departmentInfo);
		return "removed";
	}

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
	protected class AuthenticationDomainTable extends AbstractPagedTable<AuthenticationDomainInfo> {
/*
		protected static final long serialVersionUID = -6077435481342714879L;
*/
		@Override
		public DataPage<AuthenticationDomainInfo> getDataPage(int startRow, int pageSize) {
			return fetchDataPage(startRow, pageSize);
		}

	}

/*
	protected class DepartmentTable extends AbstractPagedTable<DepartmentInfo> {

		protected static final long serialVersionUID = -6077435481342714879L;

		@Override
		public DataPage<DepartmentInfo> getDataPage(int startRow, int pageSize) {
			return fetchDataPage(startRow, pageSize);
		}

	}
*/
}