

package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.ApplicationInfo;
import org.openuss.lecture.LectureException;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;

/**
 * Application page to manage institute application for membership 
 *  
 * @author Tianyu Wang
 */
@Bean(name = "views$secured$lecture$departmentapplicationspage", scope = Scope.REQUEST)
@View
public class DepartmentApplicationsPage extends AbstractDepartmentPage {
	private static final Logger logger = Logger.getLogger(DepartmentApplicationsPage.class);
	
	private ApplicationsTable applications = new ApplicationsTable();
	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		addPageCrumb();
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n(""));
		crumb.setHint(i18n(""));
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}
	
	
	private class ApplicationsTable extends AbstractPagedTable<ApplicationInfo> {

		private static final long serialVersionUID = 7717723162072514379L;
		
		private DataPage<ApplicationInfo> page; 
		
		@Override 
		public DataPage<ApplicationInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<ApplicationInfo> applications = new ArrayList<ApplicationInfo>();
				ApplicationInfo a = new ApplicationInfo();
				a.setId(1L);
				applications.add(a);
				page = new DataPage<ApplicationInfo>(applications.size(),0,applications);
				sort(applications);
			}
			return page;
		}
	}

	public String accept(){
		ApplicationInfo app = applications.getRowData();
		departmentService.acceptApplication(app.getId(), user.getId());
		return Constants.SUCCESS;
	}
	
	public String reject(){
		ApplicationInfo app = applications.getRowData();
		departmentService.rejectApplication(app.getId());
		return Constants.SUCCESS;
	}
	

	public ApplicationsTable getApplications() {
		return applications;
	}

	public void setApplications(ApplicationsTable applications) {
		this.applications = applications;
	}
}