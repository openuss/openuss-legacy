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
import org.openuss.lecture.DepartmentServiceException;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.lecture.InstituteServiceException;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$public$institute$institutesoverview", scope = Scope.REQUEST)
@View
public class InstitutesOverviewPage extends BasePage {

	protected static final Logger logger = Logger.getLogger(InstitutesOverviewPage.class);

	private static final long serialVersionUID = 5069930000478432045L;

	private InstituteTable institutesOverview = new InstituteTable();

	@Property(value = "#{instituteService}")
	private InstituteService instituteService;

	@Property(value = "#{departmentService}")
	private DepartmentService departmentService;

	@Property(value = "#{"+Constants.DEPARTMENT_INFO+"}")
	private DepartmentInfo departmentInfo;
	
	@Prerender
	public void prerender() throws Exception {
		if (departmentInfo!=null && departmentInfo.getId()!=null){
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
		if (institutesOverview == null || institutesOverview.getRowIndex() == -1){
			//prevent errors in preprocess phase
			return false;
		}		
		return desktopInfo.getInstituteInfos().contains(currentInstitute());
	}

	public String removeShortcut() {
		try {
			InstituteInfo currentInstitute = currentInstitute();
			if (desktopInfo == null || desktopInfo.getId() == null){
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
		InstituteInfo instituteDetails = institutesOverview.getRowData();
		InstituteInfo newInstituteInfo = new InstituteInfo();
		newInstituteInfo.setId(instituteDetails.getId());
		return newInstituteInfo;
	}

	/**
	 * Disables the chosen institute. This is just evident for the search
	 * indexing.
	 * 
	 * @return Outcome
	 */
	public String disableInstitute() {
		logger.debug("Starting method disableInstitute");
		InstituteInfo currentInstitute = currentInstitute();
		instituteService.setInstituteStatus(currentInstitute.getId(), false);

		addMessage(i18n("message_institute_disabled"));
		return Constants.SUCCESS;
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
			return Constants.SUCCESS;
		} catch (InstituteServiceException ise) {
			String departmentName;
			try {
				departmentName = departmentService.findDepartment(currentInstitute.getDepartmentId()).getName();
			} catch (DepartmentServiceException dse) {
				departmentName = "";
			}
			addError(i18n("message_institute_enabled_failed_department_disabled_detailed", new Object[] {
					currentInstitute.getName(), departmentName }));
			return Constants.FAILURE;
		}
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
							+ institutesOverview.getSortColumn());
				}

				DepartmentInfo departmentInfo = (DepartmentInfo) getBean(Constants.DEPARTMENT_INFO);
				// get all institutes. Does not depend whether it is enabled or disabled
				List<InstituteInfo> instituteList = getInstituteService().findInstitutesByDepartmentAndEnabled(departmentInfo.getId(), true);
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

	public InstituteTable getInstitutesOverview() {
		return institutesOverview;
	}

	public void setInstitutesOverview(InstituteTable institutesOverview) {
		this.institutesOverview = institutesOverview;
	}

	/* ----------- institute sorting comparators ------------- */

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public DepartmentInfo getDepartmentInfo() {
		return departmentInfo;
	}

	public void setDepartmentInfo(DepartmentInfo departmentInfo) {
		this.departmentInfo = departmentInfo;
	}
}