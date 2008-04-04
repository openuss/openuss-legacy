package org.openuss.web.lecture;

import java.util.List;

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
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.lecture.InstituteServiceException;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$public$institute$institutes", scope = Scope.REQUEST)
@View
public class InstitutesPage extends BasePage {

	protected static final Logger logger = Logger.getLogger(InstitutesPage.class);

	private static final long serialVersionUID = 5069935782478432045L;

	private InstituteTable institutes = new InstituteTable();

	@Property(value = "#{instituteService}")
	private InstituteService instituteService;

	@Property(value = "#{"+Constants.DEPARTMENT_INFO+"}")
	private DepartmentInfo departmentInfo;

	@Property(value = "#{"+Constants.DEPARTMENT_SERVICE+"}")
	private DepartmentService departmentService;

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (departmentInfo != null && departmentInfo.getId() != null){
			departmentInfo = departmentService.findDepartment(departmentInfo.getId());
			setBean(Constants.DEPARTMENT_INFO, departmentInfo);
		}
	}

	/**
	 * Store the selected institute into session scope and go to institute main
	 * page.
	 * 
	 * @return Outcome
	 */
	public String selectInstitute() {
		logger.debug("Starting method selectInstitute");
		InstituteInfo currentInstitute = currentInstitute();
		logger.debug("Returning to method selectInstitute");
		logger.debug(currentInstitute.getId());	
		setBean(Constants.INSTITUTE_INFO, currentInstitute);

		return Constants.INSTITUTE_PAGE;
	}

	public String shortcutInstitute() throws DesktopException {
		logger.debug("Starting method shortcutInstitute");
		InstituteInfo currentInstitute = currentInstitute();
		// desktopService.linkInstitute(desktop, currentInstitute);
		if (desktopInfo == null || desktopInfo.getId() == null){
			refreshDesktop();
		}
		desktopService2.linkInstitute(desktopInfo.getId(), currentInstitute.getId());

		addMessage(i18n("message_institute_shortcut_created"));
		return Constants.SUCCESS;
	}

	public Boolean getBookmarked() {
		if (desktopInfo == null || desktopInfo.getId() == null){
			refreshDesktop();
		}
		if (desktopInfo == null || desktopInfo.getId() == null){
			return false;
		}
		if (institutes == null || institutes.dataPage == null || institutes.dataPage.getData().size()==0){
			return true;
		}
		return desktopInfo.getInstituteInfos().contains(currentInstitute());
	}

	public String removeShortcut() {
		try {
			InstituteInfo currentInstitute = currentInstitute();
			if (desktopInfo==null){
				refreshDesktop();
			}
			desktopService2.unlinkInstitute(desktopInfo.getId(), currentInstitute.getId());
		} catch (Exception e) {
			addError(i18n("institute_error_remove_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}

		addMessage(i18n("institute_success_remove_shortcut"));
		return Constants.SUCCESS;
	}

	private InstituteInfo currentInstitute() {
		logger.debug("Starting method currentInstitute");
		InstituteInfo instituteDetails = institutes.getRowData();
		logger.debug(instituteDetails.getName());
		logger.debug(instituteDetails.getOwnerName());
		logger.debug(instituteDetails.getId());
		InstituteInfo newInstituteInfo = new InstituteInfo();
		newInstituteInfo.setId(instituteDetails.getId());
		return newInstituteInfo;
	}

	/**
	 * Store the selected institute into session scope and go to institute
	 * disable confirmation page.
	 * 
	 * @return Outcome
	 */
	public String selectInstituteAndConfirmDisable() {
		logger.debug("Starting method selectInstituteAndConfirmDisable");
		InstituteInfo currentInstitute = currentInstitute();
		logger.debug("Returning to method selectInstituteAndConfirmDisable");
		logger.debug(currentInstitute.getId());	
		setBean(Constants.INSTITUTE_INFO, currentInstitute);

		return Constants.INSTITUTE_CONFIRM_DISABLE_PAGE;
	}

	/**
	 * Enables the chosen institute. This is just evident for the search
	 * indexing.
	 * 
	 * @return Outcome
	 */
	public String enableInstitute() {
		logger.debug("Starting method enableInstitute");
		InstituteInfo currentInstitute = currentInstitute();
		try {
			instituteService.setInstituteStatus(currentInstitute.getId(), true);
			addMessage(i18n("message_institute_enabled"));
		} catch (InstituteServiceException iae) {
			addError(i18n("message_institute_enabled_failed_department_disabled"));
		} catch (Exception ex) {
			addError(i18n("message_institute_enabled_failed"));
		}
		return Constants.SUCCESS;
	}

	/**
	 * Store the selected institute into session scope and go to institute
	 * remove confirmation page.
	 * 
	 * @return Outcome
	 */
	public String selectInstituteAndConfirmRemove() {
		logger.debug("Starting method selectInstituteAndConfirmRemove");
		InstituteInfo currentInstitute = currentInstitute();
		logger.debug("Returning to method selectInstituteAndConfirmRemove");
		logger.debug(currentInstitute.getId());	
		setBean(Constants.INSTITUTE_INFO, currentInstitute);

		return Constants.INSTITUTE_CONFIRM_REMOVE_PAGE;
	}


	public InstituteTable getInstitutes() {
		return institutes;
	}

	private class InstituteTable extends AbstractPagedTable<InstituteInfo> {

		private static final long serialVersionUID = -6072435481342714879L;

		private DataPage<InstituteInfo> dataPage;
		
		@SuppressWarnings("unchecked")
		@Override
		public DataPage<InstituteInfo> getDataPage(int startRow, int pageSize) {
			logger.debug("Starting method getDataPage");
			if (dataPage == null) {
				if (logger.isDebugEnabled()) {
					logger.debug("fetch institutes data page at " + startRow + ", " + pageSize + " sorted by "
							+ institutes.getSortColumn());
				}

				DepartmentInfo departmentInfo = (DepartmentInfo) getBean(Constants.DEPARTMENT_INFO);
				// get all institutes. Does not depend whether it is enabled or
				// disabled
				List<InstituteInfo> instituteList = getInstituteService().findInstitutesByDepartment(
						departmentInfo.getId());
				sort(instituteList);
				dataPage = new DataPage<InstituteInfo>(instituteList.size(), 0, instituteList);

			}
			return dataPage;
		}
	}

	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	/* ----------- institute sorting comparators ------------- */


	public DepartmentInfo getDepartmentInfo() {
		return departmentInfo;
	}

	public void setDepartmentInfo(DepartmentInfo departmentInfo) {
		this.departmentInfo = departmentInfo;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}	
}