package org.openuss.framework.jsfcontrols.tags.security;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * SecurityFunctions based on Acegi Security System
 * 
 * @author Ingo Dueppe
 *
 */
public class JsfSecurityFunctions {
	
	
	/**
	 * Checks if the user is in all of the needed roles.
	 * @param roleList
	 * @return true if user is in all of the needed roles
	 */
	public static boolean isAllGranted(String roleList) {
		final HttpServletRequest request = getRequest();
		return Authorize.isAllGranted(roleList, request);
	}

	/**
	 * Checks if the user is in any of the needed roles.
	 * @param roleList
	 * @return true if the user is in any of the needed roles.
	 */
	public static boolean isAnyGranted(String roleList) {
		final HttpServletRequest request = getRequest();
		return Authorize.isAnyGranted(roleList, request);
	}

	/**
	 * Checks if the user is in none of the defined roles.
	 * @param roleList comma seperated role list.
	 * @return true if the user is in none of the defined roles.
	 */
	public static boolean isNotGranted(String roleList) {
		final HttpServletRequest request = getRequest();
		return Authorize.isNotGranted(roleList, request);
	}

	/**
	 * Get current HttpServletRequest
	 * @return HttpServletRequest
	 */
	private static HttpServletRequest getRequest() {
		if (httpServletRequest == null)
			httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return httpServletRequest; 
	}
	
	private static HttpServletRequest httpServletRequest; // convenience field for unit testing

}
