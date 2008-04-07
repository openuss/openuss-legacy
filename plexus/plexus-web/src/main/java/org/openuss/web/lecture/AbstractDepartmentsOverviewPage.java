package org.openuss.web.lecture;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.DepartmentServiceException;
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
 * @author Sebastian Roekens
 * 
 */
public abstract class AbstractDepartmentsOverviewPage extends BasePage {

	protected static final Logger logger = Logger.getLogger(AbstractDepartmentsOverviewPage.class);

	protected static final long serialVersionUID = 5069635747478432045L;

	protected DepartmentTable departments = new DepartmentTable();

	@Property(value = "#{universityInfo}")
	protected UniversityInfo universityInfo;

	@Property(value = "#{departmentInfo}")
	protected DepartmentInfo departmentInfo;
	
	@Property(value = "#{departmentService}")
	protected DepartmentService departmentService;

	@Property(value = "#{organisationService}")
	protected OrganisationService organisationService;

	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
	}

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
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
		setBean(Constants.DEPARTMENT_INFO, department);

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
		departmentInfo = currentDepartment();
		setBean(Constants.DEPARTMENT_INFO, departmentInfo);

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
		departmentInfo = currentDepartment();
		setBean(Constants.DEPARTMENT_INFO, departmentInfo);

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

	/**
	 * Bookmarks the chosen department and therefore sets a link on the MyUni
	 * Page for the department.
	 * 
	 * @return Outcome
	 */
	public String shortcutDepartment() throws DesktopException {
		logger.debug("Starting method shortcutDepartment");
		DepartmentInfo currentDepartment = currentDepartment();
		if (desktopInfo == null || desktopInfo.getId() == null){
			refreshDesktop();
		}
		desktopService2.linkDepartment(desktopInfo.getId(), currentDepartment
				.getId());

		addMessage(i18n("message_department_shortcut_created"));
		return Constants.SUCCESS;
	}

	public Boolean getBookmarked() {
		if (desktopInfo  == null || desktopInfo.getId() == null){
			refreshDesktop();
		}
		if (desktopInfo == null || desktopInfo.getId() == null){
			return false;
		}
		if (departments == null || departments.page == null || departments.page.getData().size()==0 || departments.getRowIndex() == -1){
			return false;
		}
		return desktopInfo.getDepartmentInfos().contains(currentDepartment());
	}

	public String removeShortcut() {
		try {
			DepartmentInfo currentDepartment = currentDepartment();
			if (desktopInfo == null || desktopInfo.getId() == null){
				refreshDesktop();
			}
			desktopService2.unlinkDepartment(desktopInfo.getId(),
					currentDepartment.getId());
		} catch (Exception e) {
			addError(i18n("institute_error_remove_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}

		addMessage(i18n("institute_success_remove_shortcut"));
		return Constants.SUCCESS;
	}

	public abstract List<DepartmentInfo> fetchDepartmentList(int startRow, int pageSize);

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

	public String confirmRemoveDepartment() {
		DepartmentInfo departmentInfo = currentDepartment();
		setBean(Constants.DEPARTMENT_INFO, departmentInfo);
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

		DataPage<DepartmentInfo> page;
		
		@Override
		public DataPage<DepartmentInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<DepartmentInfo> departmentList = fetchDepartmentList(startRow, pageSize);
				sort(departmentList);
				page = new DataPage<DepartmentInfo>(departmentList.size(),0,departmentList);
			}
			return page;
		}

	}

	public DepartmentInfo getDepartmentInfo() {
		return departmentInfo;
	}

	public void setDepartmentInfo(DepartmentInfo departmentInfo) {
		this.departmentInfo = departmentInfo;
	}

}