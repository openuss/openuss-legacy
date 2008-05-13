package org.openuss.web.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopInfo;
import org.openuss.desktop.DesktopService2;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;

/**
 * 
 * This filter garanties that a userinfo object is available within session.
 * 
 * @author Ingo Dueppe
 * 
 */
public class AuthenticationAwareRequestFilter implements Filter {

	private static final Logger logger = Logger.getLogger(AuthenticationAwareRequestFilter.class);

	private SecurityService securityService;
	
	private DesktopService2 desktopService2;


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserInfo userInfo = (UserInfo) request.getAttribute(Constants.USER_SESSION_KEY);

		if (!ObjectUtils.equals(userInfo, auth.getPrincipal()) && auth.getPrincipal() instanceof UserInfo) {
			logger.debug("Principal is: " + auth.getPrincipal());
			UserInfo user = (UserInfo) auth.getPrincipal();
			// FIXME what about anonymous?
			user = securityService.getUser(user.getId());
			securityService.setLoginTime(user);
			request.setAttribute(Constants.USER_SESSION_KEY, user);

			if (user != null && user.getId() != null) {
				logger.debug("refreshing desktop object");
				try {
					DesktopInfo desktopInfo = desktopService2.findDesktopByUser(user.getId());
					logger.debug(desktopInfo.getId());
					request.setAttribute(Constants.DESKTOP_INFO, desktopInfo);
				} catch (DesktopException e) {
					logger.error(e);
				}
			}
		}
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
		// nothing to do!
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// nothing to do!
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public DesktopService2 getDesktopService2() {
		return desktopService2;
	}

	public void setDesktopService2(DesktopService2 desktopService2) {
		this.desktopService2 = desktopService2;
	}
}
