package org.openuss.web.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentType;
import org.openuss.web.PageLinks;
import org.openuss.web.lecture.AbstractDepartmentsOverviewPage;

/** 
 * Backing Bean for the view secured/system/departments.xhtml
 * 
 * @author Malte Stockmann
 * @author Sebastian Roekens
 *
 */
@Bean(name = "views$secured$system$departments", scope = Scope.REQUEST)
@View

public class DepartmentsPage extends AbstractDepartmentsOverviewPage{
	
	@Prerender
	public void prerender() {
		try {
			super.prerender();
			if (isRedirected()){
				return;
			}
		} catch (Exception e) {
			
		}
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("departmentList_header"));
		newCrumb.setHint(i18n("departmentList_header"));
		newCrumb.setLink(PageLinks.ADMIN_DEPARTMENTS);
		
		breadcrumbs.loadAdministrationCrumbs();
		breadcrumbs.addCrumb(newCrumb);
	}
	
	@SuppressWarnings("unchecked")
	public List<DepartmentInfo> fetchDepartmentList(int startRow, int pageSize) {
		if (logger.isDebugEnabled()) {
			logger.debug("fetch institutes data page at " + startRow + ", "+ pageSize+" sorted by "+departments.getSortColumn());
		}
		List<DepartmentInfo> officialDepartmentList = departmentService.findDepartmentsByType(DepartmentType.OFFICIAL);
		List<DepartmentInfo> nonOfficialDepartmentList = departmentService.findDepartmentsByType(DepartmentType.NONOFFICIAL);
		
		List<DepartmentInfo> departmentList = new ArrayList<DepartmentInfo>();
		
		departmentList.addAll(officialDepartmentList);
		departmentList.addAll(nonOfficialDepartmentList);
		
		return departmentList;
	}
}