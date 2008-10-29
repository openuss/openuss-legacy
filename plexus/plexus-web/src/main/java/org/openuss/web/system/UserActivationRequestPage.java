package org.openuss.web.system;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.BasePage;

@Bean(name="views$public$user$activate$request", scope=Scope.REQUEST)
@View
public class UserActivationRequestPage extends BasePage{

	@Prerender
	public void prerender() throws Exception{
		breadcrumbs.init();
		
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("user_activate_request_Subheading"));
		newCrumb.setHint(i18n("user_activate_request_Subheading"));
		
		breadcrumbs.addCrumb(newCrumb);
	}
}