package org.openuss.web.lecture;

import java.util.List;
import java.util.ArrayList;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;
import org.openuss.lecture.ApplicationInfo;


/**
 * 
 * @author Kai Stettner
 */
@Bean(name = "views$secured$lecture$departmentinstitutes", scope = Scope.REQUEST)
@View
public class DepartmentInstitutesPage extends AbstractDepartmentPage {
	
	private static final long serialVersionUID = -202786789652385870L;
	
	ApplicationsTable applicationsTable = new ApplicationsTable();

	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("department_command_institutes"));
		crumb.setHint(i18n("department_command_institutes"));
		
		breadcrumbs.loadDepartmentCrumbs(departmentInfo);
		breadcrumbs.addCrumb(crumb);
		
		// TODO Remove old crumb code
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}	
	
	public ApplicationsTable getApplicationsTable()
	{
		return applicationsTable;
	}
	
	public void setApplicationsTable(ApplicationsTable applications) {
		this.applicationsTable = applications;
	}
	
	public List<ApplicationInfo> testData()
	{
		List<ApplicationInfo> testData = new ArrayList<ApplicationInfo>();
		
		ApplicationInfo application = new ApplicationInfo();
		application.setId(1L);
		testData.add(application);
		
		application = new ApplicationInfo();
		application.setId(2L);
		testData.add(application);
		
		return testData;
	}
	
	public DataPage<ApplicationInfo> fetchDataPage(int startRow, int pageSize) {
		DataPage<ApplicationInfo> dataPage;
		
	//	if (logger.isDebugEnabled()) {
	//		logger.debug("Fetching department applications data page at " + startRow + ", "+ pageSize+" sorted by "+ applications.getSortColumn());
	//	}
					
		// Get list of applications for the current department
		List<ApplicationInfo> applications;
	//	applications = testData();
		applications = departmentService.findApplicationsByDepartment(departmentInfo.getId());
	//	sort(applications);
		dataPage = new DataPage<ApplicationInfo>(applications.size(), 0, applications);
		return dataPage;
	}
	
	private void sort(List<ApplicationInfo> applications)
	{
		// Sorting not yet implemented
	}
	
	public String acceptApplication(){
		ApplicationInfo application = applicationsTable.getRowData();
		try {
			departmentService.acceptApplication(application.getId(), user.getId());
		} catch (Exception e) {
			// TODO Do something reasonable here to process the exception
			return Constants.FAILURE;
		}
		
		return Constants.SUCCESS;
	}
	
	public String rejectApplication(){
		ApplicationInfo application = applicationsTable.getRowData();
		try {
			departmentService.rejectApplication(application.getId());
		} catch (Exception e) {
			// TODO Do something reasonable here to process the exception
			return Constants.FAILURE;
		}
		
		return Constants.SUCCESS;
	}

	
	private class ApplicationsTable extends AbstractPagedTable<ApplicationInfo> {
	
		/**
		 * 
		 */
		private static final long serialVersionUID = -6195559560384739973L;

		@Override
		public DataPage<ApplicationInfo> getDataPage(int startRow, int pageSize) {
			// logger.debug("Starting method getDataPage");
			return fetchDataPage(startRow, pageSize);
		}
	}
}
