package org.openuss.web.lecture;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.ApplicationInfo;
import org.openuss.lecture.InstituteInfo;
import org.openuss.web.Constants;

/**
 * Application page to manage institute application for membership
 * 
 * @author Tianyu Wang
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$lecture$departmentapplicationspage", scope = Scope.REQUEST)
@View
public class DepartmentApplicationsPage extends AbstractDepartmentPage {
	private static final Logger logger = Logger.getLogger(DepartmentApplicationsPage.class);

	private ApplicationsTable applications = new ApplicationsTable();

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (isRedirected()){
			return;
		}
		addPageCrumb();
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("department_manage_institute_applications_header"));
		crumb.setHint(i18n("department_manage_institute_applications_header"));

		breadcrumbs.loadDepartmentCrumbs(departmentInfo);
		breadcrumbs.addCrumb(crumb);
	}

	private class ApplicationsTable extends AbstractPagedTable<ApplicationInfo> {

		private static final long serialVersionUID = 7717723162072514379L;

		private DataPage<ApplicationInfo> page;

		@SuppressWarnings("unchecked")
		@Override
		public DataPage<ApplicationInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<ApplicationInfo> applications = departmentService.findApplicationsByDepartmentAndConfirmed(
						departmentInfo.getId(), false);

				page = new DataPage<ApplicationInfo>(applications.size(), 0, applications);
				sort(applications);
			}
			return page;
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

	private InstituteInfo currentInstitute() {
		logger.debug("Starting method currentInstitute");
		InstituteInfo instituteDetails = applications.getRowData().getInstituteInfo();
		logger.debug(instituteDetails.getName());
		logger.debug(instituteDetails.getOwnerName());
		logger.debug(instituteDetails.getId());
		// Institute institute = Institute.Factory.newInstance();
		InstituteInfo newInstituteInfo = new InstituteInfo();
		// institute.setId(details.getId());
		newInstituteInfo.setId(instituteDetails.getId());
		return newInstituteInfo;
	}

	public String acceptApplication() {
		ApplicationInfo app = applications.getRowData();
		try {
			departmentService.acceptApplication(app.getId(), user.getId());
		} catch (Exception e) {
			handleException(e);
			return Constants.FAILURE;
		}
		return Constants.SUCCESS;
	}

	public String rejectApplication() {
		ApplicationInfo app = applications.getRowData();
		try {
			departmentService.rejectApplication(app.getId());
		} catch (Exception e) {
			handleException(e);
			return Constants.FAILURE;
		}
		return Constants.SUCCESS;
	}

	private void handleException(Exception e) {
		logger.error(e);
		addError(i18n(e.getMessage()));
	}

	public ApplicationsTable getApplications() {
		return applications;
	}

	public void setApplications(ApplicationsTable applications) {
		this.applications = applications;
	}
}