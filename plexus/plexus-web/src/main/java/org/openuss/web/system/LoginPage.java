package org.openuss.web.system;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;
import org.acegisecurity.ui.savedrequest.SavedRequest;

/**
 * 
 * @author Julian Reimann
 * @author Peter Schuh
 */
@View
@Bean(name = "views$public$login$login", scope = Scope.REQUEST)
public class LoginPage extends BasePage {
	
	protected String urlOfSavedRequest;
	protected String ssoUrl = "";
	protected String ssoHandler = "/Shibboleth.sso/WAYF/zivmiro01.uni-muenster.de?target=";
	
	@Property(value = "#{"+Constants.SYSTEM_PROPERTIES+"}")
	private SystemPropertiesBean systemPropertiesBean;
	
	@Prerender
	public void prerender() throws LectureException {
		breadcrumbs.init();
		
		BreadCrumb newCrumb = new BreadCrumb();
		
		newCrumb.setName(i18n("login_headline"));
		newCrumb.setHint(i18n("login_headline"));
			
		breadcrumbs.addCrumb(newCrumb);
		
		if (getSessionAttribute(AuthenticationProcessingFilter.ACEGI_SAVED_REQUEST_KEY) instanceof SavedRequest) {
			setUrlOfSavedRequest(((SavedRequest)getSessionAttribute(AuthenticationProcessingFilter.ACEGI_SAVED_REQUEST_KEY)).getFullRequestUrl());
			setSsoUrl(ssoHandler+urlOfSavedRequest);
		}
	}

	public String getUrlOfSavedRequest() {
		return urlOfSavedRequest;
	}

	public void setUrlOfSavedRequest(String urlOfSavedRequest) {
		this.urlOfSavedRequest = urlOfSavedRequest;
	}

	public String getSsoUrl() {
		return ssoUrl;
	}

	public void setSsoUrl(String ssoUrl) {
		this.ssoUrl = ssoUrl;
	}

	public SystemPropertiesBean getSystemPropertiesBean() {
		return systemPropertiesBean;
	}

	public void setSystemPropertiesBean(SystemPropertiesBean systemPropertiesBean) {
		this.systemPropertiesBean = systemPropertiesBean;
	}
	
}
