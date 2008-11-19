package org.openuss.web.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.statistics.OnlineStatisticService;
import org.openuss.web.Constants;

public class PlexusAuthenticationProcessingFilter extends AuthenticationProcessingFilter {
	
	private OnlineStatisticService onlineStatisticService;
	
	private SecurityService securityService;
	
	@Override
	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
		super.onSuccessfulAuthentication(request, response, authResult);
		
		UserInfo userInfo = null;
		
		if (authResult.getPrincipal() instanceof String) {
			logger.debug("Principal is: "+authResult.getPrincipal());
			userInfo = securityService.getUserByName((String)authResult.getPrincipal());
		} else if (authResult.getPrincipal() instanceof UserInfo) {
			logger.debug("Principal is: "+authResult.getPrincipal());
			userInfo = securityService.getUserByName(((UserInfo)(authResult.getPrincipal())).getUsername());
		} else if (authResult.getPrincipal() instanceof UserInfo) {
			logger.debug("Principal is: "+authResult.getPrincipal());
			userInfo = securityService.getUserByName(((UserInfo) authResult.getPrincipal()).getUsername());
		} else if (authResult.getPrincipal() instanceof User) {
			logger.debug("Principal is: "+authResult.getPrincipal());
			userInfo = securityService.getUserByName(((User) authResult.getPrincipal()).getUsername());
		}
		
		request.getSession().setAttribute(Constants.USER_SESSION_KEY, userInfo);
		onlineStatisticService.logSessionStart((Long)request.getSession().getAttribute(Constants.ONLINE_SESSION_ID));
	}

	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		return super.requiresAuthentication(request, response);
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
