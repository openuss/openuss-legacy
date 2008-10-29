package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Init;
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
@Bean(name = "views$secured$lecture$registration$departmentstep1", scope = Scope.REQUEST)
public class DepartmentRegistrationStep1Page extends AbstractDepartmentRegistrationPage {

	@Init
	public void init() throws LectureException {
		breadcrumbs.init();
		
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("department_registration_step1_headline"));
		newCrumb.setHint(i18n("department_registration_step1_headline"));
		
		breadcrumbs.addCrumb(newCrumb);
		
	}
	
}
