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
 * Abstract class which can be used to derive backing beans for department
 * overview views
 * 
 * 
 */
public abstract class AbstractLdapUserDnPatternsOverviewPage extends BasePage {

	protected static final Logger logger = Logger.getLogger(AbstractLdapUserDnPatternsOverviewPage.class);

	/*protected static final long serialVersionUID = 5069635747478432045L; */

	protected UserDnPatternTable userDnPatterns = new UserDnPatternTable();
/*
	@Property(value = "#{universityInfo}")
	protected UniversityInfo universityInfo;
*/
	@Property(value = "#{ldapConfigurationService}")
	protected LdapConfigurationService ldapConfigurationService;
/*
	@Property(value = "#{organisationService}")
	protected OrganisationService organisationService;
*/
	@Prerender
	public void prerender() throws Exception {
	}

	protected UserDnPatternInfo currentUserDnPattern() {
		UserDnPatternInfo userDnPattern = userDnPatterns.getRowData();
		return userDnPattern;
	}

	/**
	 * Store the selected university into session scope and go to university
	 * main page.
	 * 
	 * @return Outcome
	 */
	public String selectUserDnPattern() {
		UserDnPatternInfo userDnPattern = currentUserDnPattern();
		setSessionBean(Constants.USERDNPATTERN_INFO, userDnPattern);

		return Constants.DEPARTMENT_PAGE;
	}
	/*
	public String selectDepartment() {
		DepartmentInfo department = currentDepartment();
		setSessionBean(Constants.DEPARTMENT_INFO, department);

		return Constants.DEPARTMENT_PAGE;
	}
*/
	/**
	 * Store the selected department into session scope and go to department
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
			addMessage(i18n("message_department_removed"));
			return Constants.LDAP_USERDNPATTERN_PAGE;
		
		
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
	
	public String confirmRemoveUserDnPattern() {
		UserDnPatternInfo userDnPatternInfo = currentUserDnPattern();
		setSessionBean(Constants.USERDNPATTERN, userDnPatternInfo);
		return "removed";
	}

	public UserDnPatternTable getUserDnPatterns() {
		return userDnPatterns;
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
	protected class UserDnPatternTable extends AbstractPagedTable<UserDnPatternInfo> {
/*
		protected static final long serialVersionUID = -6077435481342714879L;
*/
		@Override
		public DataPage<UserDnPatternInfo> getDataPage(int startRow, int pageSize) {
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