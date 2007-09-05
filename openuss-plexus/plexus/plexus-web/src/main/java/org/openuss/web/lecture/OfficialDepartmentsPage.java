package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.DepartmentType;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.OrganisationService;
import org.openuss.lecture.UniversityInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;




/** Backing Bean for the views departments.xhtml and departmentstable.xhtml.
 * 
 * @author Tianyu Wang
 * @author Weijun Chen
 * @author Kai Stettner
 *
 */
@Bean(name = "views$public$department$officialDepartments", scope = Scope.REQUEST)
@View
public class OfficialDepartmentsPage extends BasePage{

	private static final Logger logger = Logger.getLogger(InstitutesPage.class);

	private static final long serialVersionUID = 5069635767478432045L;
	
	private DepartmentTable departments = new DepartmentTable();

	@Property(value = "#{universityInfo}")
	protected UniversityInfo universityInfo;

	@Property(value = "#{departmentService}")
	private DepartmentService departmentService;
	
	@Property(value = "#{organisationService}")
	private OrganisationService organisationService;
	
	@Prerender
	public void prerender() throws Exception {
		crumbs.clear();
	}	
	
	private DepartmentInfo currentDepartment() {
		
		DepartmentInfo department = departments.getRowData();
		
		return department;
	}
	
	/**
	 * Store the selected university into session scope and go to university main page.
	 * @return Outcome
	 */
	public String selectDepartment() {
		logger.debug("Starting method selectDepartment");
		DepartmentInfo department = currentDepartment();
		logger.debug("Returning to method selectDepartment");
		logger.debug(department.getId());	
		setSessionBean(Constants.DEPARTMENT_INFO, department);
		
		return Constants.DEPARTMENT_PAGE;
	}
	
	/**
	 * Store the selected department into session scope and go to department remove confirmation page.
	 * @return Outcome
	 */
	public String selectDepartmentAndConfirmRemove() {
		logger.debug("Starting method selectDepartmentAndConfirmRemove");
		DepartmentInfo currentDepartment = currentDepartment();
		logger.debug("Returning to method selectDepartmentAndConfirmRemove");
		logger.debug(currentDepartment.getId());	
		setSessionBean(Constants.DEPARTMENT_INFO, currentDepartment);
		
		return Constants.DEPARTMENT_CONFIRM_REMOVE_PAGE;
	}
	
	/**
	 * Disables the chosen department. This is just evident for the search indexing.
	 * @return Outcome
	 */
	public String disableDepartment() {
		logger.debug("Starting method disableDepartment");
		DepartmentInfo currentDepartment = currentDepartment();
		// setOrganisationStatus(true) = Enabled
		// setOrganisationStatus(false) = Disabled
		departmentService.setDepartmentStatus(currentDepartment.getId(), false);
		
		addMessage(i18n("message_department_disabled"));
		return Constants.SUCCESS;
	}
	
	/**
	 * Enables the chosen department. This is just evident for the search indexing.
	 * @return Outcome
	 */
	public String enableDepartment() {
		logger.debug("Starting method enableDepartment");
		DepartmentInfo currentDepartment = currentDepartment();
		// setOrganisationStatus(true) = Enabled
		// setOrganisationStatus(false) = Disabled
		departmentService.setDepartmentStatus(currentDepartment.getId(), true);
		
		addMessage(i18n("message_department_enabled"));
		return Constants.SUCCESS;
	}
	
	/**
	 * Bookmarks the chosen department and therefore sets a link on the MyUni Page for the department.
	 * @return Outcome
	 */
	public String shortcutDepartment() throws DesktopException {
		logger.debug("Starting method shortcutDepartment");
		DepartmentInfo currentDepartment = currentDepartment();
		desktopService2.linkDepartment(desktopInfo.getId(), currentDepartment.getId());

		addMessage(i18n("message_university_shortcut_created"));
		return Constants.SUCCESS;
	}
	
	public Boolean getBookmarked()
	{
		try {
			DepartmentInfo currentDepartment = currentDepartment();
			return desktopService2.isDepartmentBookmarked(currentDepartment.getId(), user.getId());
		} catch (Exception e) {
			
		}
		
		return false;
	}

	public String removeShortcut()
	{
		try {
			DepartmentInfo currentDepartment = currentDepartment();
			desktopService2.unlinkDepartment(desktopInfo.getId(), currentDepartment.getId());
		} catch (Exception e) {
			addError(i18n("institute_error_remove_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}
		
		addMessage(i18n("institute_success_remove_shortcut"));
		return Constants.SUCCESS;
	}
	
	private DataPage<DepartmentInfo> dataPage;
	
	public DataPage<DepartmentInfo> fetchDataPage(int startRow, int pageSize) {
		
		if (dataPage == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch institutes data page at " + startRow + ", "+ pageSize+" sorted by "+departments.getSortColumn());
			}
			List<DepartmentInfo> departmentList = new ArrayList<DepartmentInfo>(departmentService.findDepartmentsByUniversityAndTypeAndEnabled(universityInfo.getId(), DepartmentType.OFFICIAL, true));
			logger.info("Departments:"+departmentList);
			if (departmentList != null) {
				logger.info("Size:"+departmentList.size());
			}
			
			sort(departmentList);
			dataPage = new DataPage<DepartmentInfo>(departmentList.size(),0,departmentList);
	}
		return dataPage;
	}

	private void sort(List<DepartmentInfo> departmentList) {
		if (StringUtils.equals("shortcut", departments.getSortColumn())) {
			Collections.sort(departmentList, new ShortcutComparator());
		} else if (StringUtils.equals("city", departments.getSortColumn())){
			Collections.sort(departmentList, new CityComparator());
		} else if (StringUtils.equals("country", departments.getSortColumn())){
			Collections.sort(departmentList, new CountryComparator());
		} else {
			Collections.sort(departmentList, new NameComparator());
		}
	}
	
	public DepartmentService getDepartmentService() {
		return departmentService;
	}
	
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public UniversityInfo getUniversityInfo() {
		return universityInfo;
	}

	public void setUniversityInfo(UniversityInfo universityInfo) {
		this.universityInfo = universityInfo;
	}
	
	/* ----------- departments sorting comparators -------------*/
	
	private class NameComparator implements Comparator<DepartmentInfo> {
		public int compare(DepartmentInfo f1, DepartmentInfo f2) {
			if (departments.isAscending()) {
				return f1.getName().compareToIgnoreCase(f2.getName());
			} else {
				return f2.getName().compareToIgnoreCase(f1.getName());
			}
		}
	}

	private class CityComparator implements Comparator<DepartmentInfo> {
		public int compare(DepartmentInfo f1, DepartmentInfo f2) {
			if (departments.isAscending()) {
				return f1.getCity().compareToIgnoreCase(f2.getCity());
			} else {
				return f2.getCity().compareToIgnoreCase(f1.getCity());
			}
		}
	}
	
	private class CountryComparator implements Comparator<DepartmentInfo> {
		public int compare(DepartmentInfo f1, DepartmentInfo f2) {
			if (departments.isAscending()) {
				return f1.getCountry().compareToIgnoreCase(f2.getCountry());
			} else {
				return f2.getCountry().compareToIgnoreCase(f1.getCountry());
			}
		}
	}

	private class ShortcutComparator implements Comparator<DepartmentInfo> {
		public int compare(DepartmentInfo f1, DepartmentInfo f2) {
			if (departments.isAscending()) {
				return f1.getShortcut().compareToIgnoreCase(f2.getShortcut());
			} else {
				return f2.getShortcut().compareToIgnoreCase(f1.getShortcut());
			}
		}
	}

	public String confirmRemoveDepartment(){
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
	
	
	
	

	private class DepartmentTable extends AbstractPagedTable<DepartmentInfo> {

		private static final long serialVersionUID = -6077435481342714879L;

		@Override
		public DataPage<DepartmentInfo> getDataPage(int startRow, int pageSize) {
			return fetchDataPage(startRow, pageSize);
		}
		
	}


}