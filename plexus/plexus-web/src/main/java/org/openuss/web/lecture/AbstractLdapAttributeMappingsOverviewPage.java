package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.ldap.AttributeMappingInfo;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * Abstract class which can be used to derive backing beans for attributeMapping
 * overview views
 * 
 * @author Christian Grelle
 * @author Peter Schuh
 * 
 */
public abstract class AbstractLdapAttributeMappingsOverviewPage extends BasePage {

	protected static final Logger logger = Logger.getLogger(AbstractLdapRoleAttributeKeysOverviewPage.class);
	

	protected AttributeMappingTable attributeMappings = new AttributeMappingTable();

	@Property(value = "#{ldapConfigurationService}")
	protected LdapConfigurationService ldapConfigurationService;

	@Prerender
	public void prerender() throws Exception {
	}

	protected AttributeMappingInfo currentAttributeMapping() {
		AttributeMappingInfo attributeMapping = attributeMappings.getRowData();
		return attributeMapping;
	}

	/**
	 * Store the selected attributeMapping into session scope and go to attributeMapping
	 * main page.
	 * 
	 * @return Outcome
	 */
	public String selectAttributeMapping() {
		AttributeMappingInfo attributeMapping = currentAttributeMapping();
		setSessionBean(Constants.ATTRIBUTEMAPPING_INFO, attributeMapping);
		//TODO: CHRISTIAN: WRONG OUTCOME!!!!!
		return Constants.DEPARTMENT_PAGE;
	}

	/**
	 * Store the selected attributeMapping into session scope and go to attributeMapping
	 * remove confirmation page.
	 * 
	 * @return Outcome
	 */
	public String selectAttributeMappingAndConfirmRemove() {
		AttributeMappingInfo currentAttributeMapping = currentAttributeMapping();
		setSessionBean(Constants.ATTRIBUTEMAPPING_INFO, currentAttributeMapping);

		return Constants.ATTRIBUTEMAPPING_CONFIRM_REMOVE_PAGE;
	}
		
	public String removeAttributeMapping() throws Exception {
			AttributeMappingInfo currentAttributeMapping = currentAttributeMapping();
			ldapConfigurationService.deleteAttributeMapping(currentAttributeMapping);
			setSessionBean("attributeMappingInfo", null);
			addMessage(i18n("message_department_removed"));
			return Constants.LDAP_ATTRIBUTEMAPPING_PAGE;
		
		
	}
	
	
	
	
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

	protected DataPage<AttributeMappingInfo> dataPage;

	public abstract DataPage<AttributeMappingInfo> fetchDataPage(int startRow,
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
	
	public String confirmRemoveAttributeMapping() {
		AttributeMappingInfo attributeMappingInfo = currentAttributeMapping();
		setSessionBean(Constants.ATTRIBUTEMAPPING, attributeMappingInfo);
		return "removed";
	}

	public AttributeMappingTable getAttributeMappings() {
		return attributeMappings;
	}
	
	/*
	
	public String confirmRemoveDepartment() {
		DepartmentInfo departmentInfo = currentDepartment();
		setSessionBean(Constants.DEPARTMENT, departmentInfo);
		return "removed";
	}
	*/

	protected class AttributeMappingTable extends AbstractPagedTable<AttributeMappingInfo> {
		
		@Override
		public DataPage<AttributeMappingInfo> getDataPage(int startRow, int pageSize) {
			return fetchDataPage(startRow, pageSize);
		}

	}
}