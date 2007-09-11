package org.openuss.web.security;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.BasePage;

/**
 * 
 * @author Julian Reimann
 */
@View
@Bean(name = "views$public$user$registration$step2Preferences", scope = Scope.REQUEST)
public class UserRegistrationStep2Page extends BasePage {

	@Prerender
	public void prerender() throws LectureException {
		breadcrumbs.init();
		
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("user_registration2_Headline"));
		newCrumb.setHint(i18n("user_registration2_Headline"));
		
		breadcrumbs.addCrumb(newCrumb);
		
	}
	
}