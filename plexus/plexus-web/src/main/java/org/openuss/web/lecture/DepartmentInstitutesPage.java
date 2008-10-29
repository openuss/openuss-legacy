package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;


/**
 * 
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$lecture$departmentinstitutes", scope = Scope.REQUEST)
@View
public class DepartmentInstitutesPage extends AbstractDepartmentPage {
	
	private static final long serialVersionUID = -202786789652385870L;
	
	private Integer departmentType;

	public Integer getDepartmentType() {
		departmentType=departmentInfo.getDepartmentType().getValue();
		return departmentType;
	}

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
		crumb.setName(i18n("department_command_institutes"));
		crumb.setHint(i18n("department_command_institutes"));
		
		breadcrumbs.loadDepartmentCrumbs(departmentInfo);
		breadcrumbs.addCrumb(crumb);
	}	

}
