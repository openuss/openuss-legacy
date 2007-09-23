package org.openuss.web.system;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.security.User;
import org.openuss.security.UserContact;
import org.openuss.security.UserPreferences;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

@Bean(name="views$public$user$activate$request", scope=Scope.REQUEST)
@View
public class UserActivationRequestPage extends BasePage{

	@Prerender
	public void prerender() throws Exception{
		super.prerender();
		
		breadcrumbs.init();
		
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("user_activate_request_Subheading"));
		newCrumb.setHint(i18n("user_activate_request_Subheading"));
		
		breadcrumbs.addCrumb(newCrumb);
	}
}