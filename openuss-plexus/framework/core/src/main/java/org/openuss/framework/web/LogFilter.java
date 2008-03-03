package org.openuss.framework.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.log4j.Logger;

public class LogFilter implements Filter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LogFilter.class);
	
	private static int count = 0;

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		final int id = count++;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		SecurityContext securityContext = SecurityContextHolder.getContext();
	
		logger.debug("-------------------- Log Filter "+id+"-------------------------");
		logger.debug("--------> PathInfo = "+httpRequest.getPathInfo());
		logger.debug("--------> RequestUrl = "+httpRequest.getRequestURL());
		logger.debug("--------> RequestUri = "+httpRequest.getRequestURI());
		

		if (securityContext != null && securityContext.getAuthentication() != null) {
			logger.debug("--------> "+securityContext.getAuthentication().getName() );
			GrantedAuthority[] authorities = securityContext.getAuthentication().getAuthorities();
			String auth = "";
			for (GrantedAuthority authority : authorities) {
				auth += authority.getAuthority()+ " | ";
			}
			logger.debug("--------> "+auth);
		} else {
			logger.debug("--------> "+securityContext);
		}
		
		
		filterChain.doFilter(request, response);
		logger.debug("-------------------------------------------> close logfiler "+id);
	}

	public void init(FilterConfig config) throws ServletException {
		
	}

}
