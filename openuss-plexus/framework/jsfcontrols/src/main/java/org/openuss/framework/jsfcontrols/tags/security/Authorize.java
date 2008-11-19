package org.openuss.framework.jsfcontrols.tags.security;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ingo Dueppe
 */
public class Authorize {

	
	/**
	 * Checks if the user is in all of the needed roles.
	 * @param roleList
	 * @param request
	 * @return true if user is in all of the needed roles
	 */
	static boolean isAllGranted(String roleList, final HttpServletRequest request) {
		String[] roles = roleList.split(",");
		boolean isAuthorized = false;
		for (String role : roles) {
			if (request.isUserInRole(role)) {
				isAuthorized = true;
			} else {
				isAuthorized = false;
				break;
			}
			
		}
		return isAuthorized;
	}

	/**
	 * Checks if the user is in any of the needed roles.
	 * @param roleList
	 * @param request
	 * @return true if the user is in any of the needed roles.
	 */
	static boolean isAnyGranted(String roleList, final HttpServletRequest request) {
		String[] roles = roleList.split(",");
		boolean isAuthorized = false;
		for (String role : roles) {
			if (request.isUserInRole(role)) {
				isAuthorized = true;
				break;
			};
		}
		return isAuthorized;
	}

	/**
	 * Checks if the user is in none of the defined roles.
	 * @param roleList comma seperated role list.
	 * @param request
	 * @return true if the user is in none of the defined roles.
	 */
	static boolean isNotGranted(String roleList, final HttpServletRequest request) {
		String[] roles = roleList.split(",");
		boolean isAuthorized = false;
		for (String role : roles) {
			if (request.isUserInRole(role)) {
				isAuthorized = true;
				break;
			};
		}
		return !isAuthorized;
	}

}
