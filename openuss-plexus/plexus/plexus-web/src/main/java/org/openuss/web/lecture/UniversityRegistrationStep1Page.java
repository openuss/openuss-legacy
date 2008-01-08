package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Init;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.BasePage;

/**
 * 
 * @author Julian Reimann
 */
@View
@Bean(name = "views$secured$lecture$registration$universitystep1", scope = Scope.REQUEST)
public class UniversityRegistrationStep1Page extends BasePage {

	@Init
	public void init()
	{
		breadcrumbs.init();
		
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("university_registration_step1_headline"));
		newCrumb.setHint(i18n("university_registration_step1_headline"));
		
		breadcrumbs.addCrumb(newCrumb);
	}
	
/*	@Prerender
	public void prerender() throws LectureException {
		breadcrumbs.init();
		
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("university_registration_step1_headline"));
		newCrumb.setHint(i18n("university_registration_step1_headline"));
		
		breadcrumbs.addCrumb(newCrumb);
		
	}
	*/
	
}

