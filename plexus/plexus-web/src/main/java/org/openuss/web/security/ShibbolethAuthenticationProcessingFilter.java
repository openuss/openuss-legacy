package org.openuss.web.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.ui.AbstractProcessingFilter;

public class ShibbolethAuthenticationProcessingFilter extends AbstractProcessingFilter {

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Asserts for properties
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (!(request instanceof HttpServletRequest)) {
			throw new ServletException("Can only process HttpServletRequest");
		}

		if (!(response instanceof HttpServletResponse)) {
			throw new ServletException("Can only process HttpServletResponse");
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String username = httpRequest.getHeader("SHIB_REMOTE_USER");
		httpRequest.setAttribute("doMigration", "true");
		chain.doFilter(request, response);
		

	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefaultFilterProcessesUrl() {
		// TODO Auto-generated method stub
		return null;
	}

}
