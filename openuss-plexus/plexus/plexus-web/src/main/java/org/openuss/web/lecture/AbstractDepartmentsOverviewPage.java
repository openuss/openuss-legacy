package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.OrganisationService;
import org.openuss.lecture.UniversityInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * Abstract class which can be used to derive backing beans for department
 * overview views
 * 
 * @author Tianyu Wang
 * @author Weijun Chen
 * @author Kai Stettner
 * @author Malte Stockmann
 * 
 */

public abstract class AbstractDepartmentsOverviewPage extends BasePage {

	protected static final Logger logger = Logger
			.getLogger(DepartmentsPage.class);

	protected static final long serialVersionUID = 5069635747478432045L;

	protected DepartmentTable departments = new DepartmentTable();

	@Property(value = "#{universityInfo}")
	protected UniversityInfo universityInfo;

	@Property(value = "#{departmentService}")
	protected DepartmentService departmentService;

	@Property(value = "#{organisationService}")
	protected OrganisationService organisationService;

	@Prerender
	public void prerender() throws Exception {
	}

	protected DepartmentInfo currentDepartment() {
		DepartmentInfo department = departments.getRowData();
		return department;
	}

	/**
	 * Store the selected university into session scope and go to university
	 * main page.
	 * 
	 * @return Outcome
	 */
	public String selectDepartment() {
		DepartmentInfo department = currentDepartment();
		setSessionBean(Constants.DEPARTMENT_INFO, department);

		return Constants.DEPARTMENT_PAGE;
	}

	/**
	 * Store the selected department into session scope and go to department
	 * remove confirmation page.
	 * 
	 * @return Outcome
	 */
	public String selectDepartmentAndConfirmRemove() {
		logger.debug("Starting method selectDepartmentAndConfirmRemove");
		DepartmentInfo currentDepartment = currentDepartment();
		setSessionBean(Constants.DEPARTMENT_INFO, currentDepartment);

		return Constants.DEPARTMENT_CONFIRM_REMOVE_PAGE;
	}

	/**
	 * Store the selected department into session scope and go to department
	 * disable confirmation page.
	 * 
	 * @return Outcome
	 */
	public String selectDepartmentAndConfirmDisable() {
		logger.debug("Starting method selectDepartmentAndConfirmDisable");
		DepartmentInfo currentDepartment = currentDepartment();
		setSessionBean(Constants.DEPARTMENT_INFO, currentDepartment);

		return Constants.DEPARTMENT_CONFIRM_DISABLE_PAGE;
	}

	/**
	 * Enables the chosen department. This is just evident for the search
	 * indexing.
	 * 
	 * @return Outcome
	 */
	public String enableDepartment() {
		logger.debug("Starting method enableDepartment");
		DepartmentInfo currentDepartment = currentDepartment();
		departmentService.setDepartmentStatus(currentDepartment.getId(), true);

		addMessage(i18n("message_department_enabled"));
		return Constants.SUCCESS;
	}

	/**
	 * Bookmarks the chosen department and therefore sets a link on the MyUni
	 * Page for the department.
	 * 
	 * @return Outcome
	 */
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

	/* ----------- departments sorting comparators ------------- */

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

	protected class DepartmentTable extends AbstractPagedTable<DepartmentInfo> {

		protected static final long serialVersionUID = -6077435481342714879L;

		@Override
		public DataPage<DepartmentInfo> getDataPage(int startRow, int pageSize) {
			return fetchDataPage(startRow, pageSize);
		}

	}

}