package org.openuss.web.system;

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
@Bean(name = "views$public$login$logout", scope = Scope.REQUEST)
public class LogoutPage extends BasePage {

	@Prerender
	public void prerender() throws LectureException {
		breadcrumbs.init();
		
		BreadCrumb newCrumb = new BreadCrumb();
		
		newCrumb.setName(i18n("logout_headline"));
		newCrumb.setHint(i18n("logout_headline"));
			
		breadcrumbs.addCrumb(newCrumb);
		
	}
	
}
