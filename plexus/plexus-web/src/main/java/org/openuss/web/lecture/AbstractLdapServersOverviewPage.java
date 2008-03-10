package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.ldap.AttributeMappingInfo;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.security.ldap.LdapServerInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * Abstract class which can be used to derive backing beans for ldapServers
 * overview views
 * 
 * @author Christian Grelle
 * @author Peter Schuh 
 * 
 */
public abstract class AbstractLdapServersOverviewPage extends BasePage {

	protected static final Logger logger = Logger.getLogger(AbstractLdapServersOverviewPage.class);

	protected LdapServerTable ldapServers = new LdapServerTable();

	@Property(value = "#{ldapConfigurationService}")
	protected LdapConfigurationService ldapConfigurationService;

	@Prerender
	public void prerender() throws Exception {
	}

	protected LdapServerInfo currentLdapServer() {
		LdapServerInfo ldapServer = ldapServers.getRowData();
		return ldapServer;
	}

	/**
	 * Store the selected university into session scope and go to ldapServers
	 * main page.
	 * 
	 * @return Outcome
	 */
	public String selectLdapServer() {
		LdapServerInfo ldapServer = currentLdapServer();
		setSessionBean(Constants.SERVER_INFO, ldapServer);
		//TODO: CHRISTIAN: WRONG OUTCOME!!!!!
		return Constants.DEPARTMENT_PAGE;
	}

	/**
	 * Store the selected ldapServer into session scope and go to ldapServer
	 * remove confirmation page.
	 * 
	 * @return Outcome
	 */
	public String selectLdapServerAndConfirmRemove() {
		LdapServerInfo currentLdapServer = currentLdapServer();
		setSessionBean(Constants.SERVER_INFO, currentLdapServer);

		return Constants.SERVER_CONFIRM_REMOVE_PAGE;
	}
		
	public String removeLdapServer() throws Exception {
		LdapServerInfo currentLdapServer = currentLdapServer();
			ldapConfigurationService.deleteLdapServer(currentLdapServer);
			setSessionBean("ldapServerInfo", null);
			addMessage(i18n("message_department_removed"));
			return Constants.LDAP_SERVER_PAGE;
		
		
	}
	
	
	
	
	/*
	
	public String selectDepartmentAndConfirmRemove() {
		logger.debug("Starting method selectDepartmentAndConfirmRemove");
		DepartmentInfo currentDepartment = currentDepartment();
		setSessionBean(Constants.DEPARTMENT_INFO, currentDepartment);

		return Constants.DEPARTMENT_CONFIRM_REMOVE_PAGE;
	}
*/
	/**
	 * Store the selected department into session scope and go to department
	 * disable confirmation page.
	 * 
	 * @return Outcome
	 */
/*
	public String selectDepartmentAndConfirmDisable() {
		logger.debug("Starting method selectDepartmentAndConfirmDisable");
		DepartmentInfo currentDepartment = currentDepartment();
		setSessionBean(Constants.DEPARTMENT_INFO, currentDepartment);

		return Constants.DEPARTMENT_CONFIRM_DISABLE_PAGE;
	}
*/
	/**
	 * Enables the chosen department. This is just evident for the search
	 * indexing.
	 * 
	 * @return Outcome
	 */
/*
	public String enableDepartment() {
		logger.debug("Starting method enableDepartment");
		DepartmentInfo currentDepartment = currentDepartment();
		try {
			departmentService.setDepartmentStatus(currentDepartment.getId(), true);
			addMessage(i18n("message_department_enabled"));
		} catch(DepartmentServiceException iae) {
			addMessage(i18n("message_department_enabled_failed_university_disabled"));
		} catch(Exception ex){
			addMessage(i18n("message_department_enabled_failed"));
		}
		return Constants.SUCCESS;
	}
	*/
	/**
	 * Bookmarks the chosen department and therefore sets a link on the MyUni
	 * Page for the department.
	 * 
	 * @return Outcome
	 */
/*
	public String shortcutDepartment() throws DesktopException {
		logger.debug("Starting method shortcutDepartment");
		DepartmentInfo currentDepartment = currentDepartment();
		desktopService2.linkDepartment(desktopInfo.getId(), currentDepartment
				.getId());

		addMessage(i18n("message_department_shortcut_created"));
		return Constants.SUCCESS;
	}

	public Boolean getBookmarked() {
		try {
			DepartmentInfo currentDepartment = currentDepartment();
			return desktopService2.isDepartmentBookmarked(currentDepartment
					.getId(), user.getId());
		} catch (Exception e) {

		}

		return false;
	}

	public String removeShortcut() {
		try {
			DepartmentInfo currentDepartment = currentDepartment();
			desktopService2.unlinkDepartment(desktopInfo.getId(),
					currentDepartment.getId());
		} catch (Exception e) {
			addError(i18n("institute_error_remove_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}

		addMessage(i18n("institute_success_remove_shortcut"));
		return Constants.SUCCESS;
	}
*/
	protected DataPage<LdapServerInfo> dataPage;

	public abstract DataPage<LdapServerInfo> fetchDataPage(int startRow,
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
	
	public String confirmRemoveLdapServer() {
		LdapServerInfo ldapServerInfo = currentLdapServer();
		setSessionBean(Constants.SERVER, ldapServerInfo);
		return "removed";
	}

	public LdapServerTable getLdapServers() {
		return ldapServers;
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
	protected class LdapServerTable extends AbstractPagedTable<LdapServerInfo> {

		@Override
		public DataPage<LdapServerInfo> getDataPage(int startRow, int pageSize) {
			return fetchDataPage(startRow, pageSize);
		}

	}
}