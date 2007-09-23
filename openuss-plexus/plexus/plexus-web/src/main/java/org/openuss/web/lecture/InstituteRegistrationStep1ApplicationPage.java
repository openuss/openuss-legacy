package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Init;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.BasePage;

/**
 * 
 * @author Julian Reimann
 */
@View
@Bean(name = "views$secured$lecture$registration$step1application", scope = Scope.REQUEST)
public class InstituteRegistrationStep1ApplicationPage extends BasePage {

	@Init
	public void init() throws LectureException {
		breadcrumbs.init();
		
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("institute_registration_step1application_headline"));
		newCrumb.setHint(i18n("institute_registration_step1application_headline"));
		
		breadcrumbs.addCrumb(newCrumb);
		
	}
	
}
