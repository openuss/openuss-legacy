package org.openuss.web.lecture;

import java.util.List;
import java.util.ArrayList;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.LectureException;
import org.openuss.security.SecurityService;
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
	
	private Integer departmentType;

	public Integer getDepartmentType() {
		departmentType=departmentInfo.getDepartmentType().getValue();
		return departmentType;
	}

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
	
	
	

}
