package org.openuss.web.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.statistics.OnlineStatisticService;
import org.openuss.web.Constants;

public class PlexusAuthenticationProcessingFilter extends AuthenticationProcessingFilter {
	
	private OnlineStatisticService onlineStatisticService;
	
	private SecurityService securityService;
	
	public PlexusAuthenticationProcessingFilter() {
		super();
	}

	@Override
	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
		super.onSuccessfulAuthentication(request, response, authResult);
		
		if (authResult.getPrincipal() instanceof User) {
			logger.debug("Principal is: "+authResult.getPrincipal());
			User details = (User) authResult.getPrincipal();
			User user = securityService.getUserByName(details.getUsername());
			// securityService.setLoginTime(user);
			request.getSession().setAttribute(Constants.USER_SESSION_KEY, user);
			
			onlineStatisticService.logSessionStart((Long)request.getSession().getAttribute(Constants.ONLINE_SESSION_ID));
		}
		
	}

	public OnlineStatisticService getOnlineStatisticService() {
		return onlineStatisticService;
	}
	
	public void setOnlineStatisticService(OnlineStatisticService onlineStatisticService) {
		this.onlineStatisticService = onlineStatisticService;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
}
