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

@Bean(name="views$public$user$registration$startProcess", scope=Scope.REQUEST)
@View
public class StartUserRegistrationPage extends BasePage{

	@Prerender
	public void prerender() throws Exception{
		super.prerender();
		user = User.Factory.newInstance();
		user.setPreferences(UserPreferences.Factory.newInstance());
		user.getPreferences().setLocale(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getLocale().toString());
		user.setContact(UserContact.Factory.newInstance());
		setSessionBean(Constants.USER, user);
		
		breadcrumbs.init();
		
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("user_registration_headline"));
		newCrumb.setHint(i18n("user_registration_headline"));
		
		breadcrumbs.addCrumb(newCrumb);
	}
	
	public String lastStepRegistration(){
		return Constants.STEP3;
	}
}