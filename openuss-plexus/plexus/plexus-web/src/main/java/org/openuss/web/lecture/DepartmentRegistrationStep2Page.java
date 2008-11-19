package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;

/**
 * 
 * @author Julian Reimann
 * @author Sebastian Roekens
 * 
 */
@View
@Bean(name = "views$secured$lecture$registration$departmentstep2", scope = Scope.REQUEST)
public class DepartmentRegistrationStep2Page extends AbstractDepartmentRegistrationPage {

	@Prerender
	public void prerender() throws LectureException {
		breadcrumbs.init();
		
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("department_registration_step2_headline"));
		newCrumb.setHint(i18n("department_registration_step2_headline"));
		
		breadcrumbs.addCrumb(newCrumb);
		
	}
	
}
