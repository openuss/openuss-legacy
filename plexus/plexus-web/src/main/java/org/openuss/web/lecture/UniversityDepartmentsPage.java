package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;


/**
 * 
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$lecture$universitydepartments", scope = Scope.REQUEST)
@View
public class UniversityDepartmentsPage extends AbstractUniversityPage {
	
	private static final long serialVersionUID = -202789019652385870L;

	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		if (isRedirected()){
			return;
		}
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("university_command_departments"));
		crumb.setHint(i18n("university_command_departments"));
		
		breadcrumbs.loadUniversityCrumbs(universityInfo);
		breadcrumbs.addCrumb(crumb);
	}	

}
