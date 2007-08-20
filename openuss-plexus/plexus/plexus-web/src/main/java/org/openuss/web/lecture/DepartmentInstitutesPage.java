package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;


/**
 * 
 * @author Kai Stettner
 */
@Bean(name = "views$secured$lecture$departmentinstitutes", scope = Scope.REQUEST)
@View
public class DepartmentInstitutesPage extends AbstractDepartmentPage {
	
	private static final long serialVersionUID = -202786789652385870L;

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
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}	

}
