package org.openuss.web.lecture;

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
	public String selectRoleAttributeKeyAndRemove() throws Exception {
		try {
		logger.debug("Starting method selectRoleAttributeKeyAndRemove");
		RoleAttributeKeyInfo currentRoleAttributeKey = currentRoleAttributeKey();
		setSessionBean(Constants.ROLEATTRIBUTEKEY_INFO, currentRoleAttributeKey);
		ldapConfigurationService.deleteRoleAttributeKey(currentRoleAttributeKey);
		setSessionBean("roleAttributeKeyInfo", null);
		return Constants.LDAP_ROLEATTRIBUTEKEY_PAGE;
		}
		catch (Exception e) {
			//TODO: CHRISTIAN: WRONG MESSAGE!!!
			addMessage(i18n("message_department_cannot_be_removed"));
			return Constants.LDAP_ROLEATTRIBUTEKEY_PAGE;
		}
	}	
		/*
	public String removeDepartment() throws Exception {
		try {
			departmentService.removeDepartment(departmentInfo.getId());
			setSessionBean("departmentInfo", null);
			setSessionBean("instituteInfo", null);
			setSessionBean("courseTypeInfo", null);
			setSessionBean("courseInfo", null);
			addMessage(i18n("message_department_removed"));
			return Constants.DEPARTMENTS_PAGE;
		} catch (Exception e) {
			addMessage(i18n("message_department_cannot_be_removed"));
			return Constants.OUTCOME_BACKWARD;
		}
		
	}
	
	
	
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
	protected DataPage<RoleAttributeKeyInfo> dataPage;

	public abstract DataPage<RoleAttributeKeyInfo> fetchDataPage(int startRow,
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
	
	public String confirmRemoveRoleAttributeKey() {
		RoleAttributeKeyInfo roleAttributeKeyInfo = currentRoleAttributeKey();
		setSessionBean(Constants.ROLEATTRIBUTEKEY, roleAttributeKeyInfo);
		return "removed";
	}

	public RoleAttributeKeyTable getRoleAttributeKeys() {
		return roleAttributeKeys;
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


*/
	protected class RoleAttributeKeyTable extends AbstractPagedTable<RoleAttributeKeyInfo> {

		@Override
		public DataPage<RoleAttributeKeyInfo> getDataPage(int startRow, int pageSize) {
			return fetchDataPage(startRow, pageSize);
		}

	}
}