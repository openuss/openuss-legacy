package org.openuss.web.servlets.xss;

import org.apache.log4j.Logger;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Cross Scripting Filter
 * 
 * @author Ingo Dueppe
 */
public class XssServletFilter implements Filter {

	private static final Logger logger = Logger.getLogger(XssServletFilter.class);

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			logger.debug("wrapping request with XssRequestWrapper.");
			XssRequestWrapper requestWrapper = new XssRequestWrapper((HttpServletRequest) request);
			chain.doFilter(requestWrapper, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	public void destroy() {
	}

	public void init(FilterConfig config) throws ServletException {
	}
}