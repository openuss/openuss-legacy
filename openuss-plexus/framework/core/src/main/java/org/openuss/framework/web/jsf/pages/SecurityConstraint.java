package org.openuss.framework.web.jsf.pages;

import java.io.IOException;

import org.apache.log4j.Logger;

import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.servlet.http.HttpServletResponse;

import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.framework.web.jsf.util.FacesUtils;

/**
 * Security Constraint that will be checked if user enter a view
 * @author Ingo Dueppe
 */
public class SecurityConstraint {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SecurityConstraint.class);
	
	private ValueBinding domainObject;
	private int permissions;
	private MethodBinding onDeniedAction;
	
	public ValueBinding getDomainObject() {
		return domainObject;
	}
	public void setDomainObject(ValueBinding domainObject) {
		this.domainObject = domainObject;
	}
	public MethodBinding getOnDeniedAction() {
		return onDeniedAction;
	}
	public void setOnDeniedAction(MethodBinding onDeniedAction) {
		this.onDeniedAction = onDeniedAction;
	}
	public int getPermissions() {
		return permissions;
	}
	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}
	
	/**
	 * Checks if the current user has the permission to apply 
	 */
	public void performConstraint() {
		if (!hasPermission()) {
			if (onDeniedAction == null) {
				sendAccessDeniedError();
			} else {
				FacesUtils.handleNavigationOutcome(null, FacesUtils.perform(onDeniedAction));
			}
		}
	}		
	
	private void sendAccessDeniedError() {
		try {
			FacesUtils.sendError(HttpServletResponse.SC_FORBIDDEN);
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
	
	private boolean hasPermission() {
		Object resolvedDomainObject = domainObject.getValue(FacesContext.getCurrentInstance());
		return AcegiUtils.hasPermission(resolvedDomainObject, new Integer[]{permissions});
	}
		
}
