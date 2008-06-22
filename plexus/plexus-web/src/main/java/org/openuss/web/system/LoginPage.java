package org.openuss.web.system;

import org.acegisecurity.ui.savedrequest.SavedRequest;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.security.AuthenticationController;

/**
 * 
 * @author Julian Reimann
 * @author Peter Schuh
 */
@View
@Bean(name = "views$public$login$login", scope = Scope.REQUEST)
public class LoginPage extends BasePage {
		
	protected String ssoUrl = "";
	
	@Property(value = "#{"+Constants.SYSTEM_PROPERTIES+"}")
	private SystemPropertiesBean systemPropertiesBean;
	
	@Prerender
	public void prerender() throws LectureException {
		breadcrumbs.init();
		
		BreadCrumb newCrumb = new BreadCrumb();
		
		newCrumb.setName(i18n("login_headline"));
		newCrumb.setHint(i18n("login_headline"));
			
		breadcrumbs.addCrumb(newCrumb);
		
		// Generate SSO link from request
		if (getSessionAttribute(AuthenticationProcessingFilter.ACEGI_SAVED_REQUEST_KEY) instanceof SavedRequest && !StringUtils.isBlank(getSystemPropertiesBean().getSHIBBOLETH_START_AUTH_URL())) {
			setSsoUrl(getSystemPropertiesBean().getSHIBBOLETH_START_AUTH_URL()+getSystemPropertiesBean().getOPENUSS_SERVER_URL()+((SavedRequest)getSessionAttribute(AuthenticationProcessingFilter.ACEGI_SAVED_REQUEST_KEY)).getRequestUrl());
		}
		else setSsoUrl(getSystemPropertiesBean().getSHIBBOLETH_START_AUTH_URL()+getSystemPropertiesBean().getOPENUSS_SERVER_URL());
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
