package org.openuss.web.system;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.security.UserContactInfo;
import org.openuss.security.UserInfoDetails;
import org.openuss.security.UserPreferencesInfo;
import org.openuss.security.UserProfileInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

@Bean(name="views$public$user$registration$startProcess", scope=Scope.REQUEST)
@View
public class StartUserRegistrationPage extends BasePage{

	@Prerender
	public void prerender() throws Exception{
		super.prerender();
		user = new UserInfoDetails();
		user.setPreferences(new UserPreferencesInfo());
		user.setProfile(new UserProfileInfo());
		user.setContact(new UserContactInfo());
		user.getPreferences().setLocale(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getLocale().toString());
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