package org.openuss.framework.web.jsf.pages;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openuss.framework.utilities.DomainObjectUtility;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.framework.web.jsf.util.FacesUtils;

/**
 * Security Constraint that will be checked if user enter a view
 * 
 * @author Ingo Dueppe
 */
public class SecurityConstraint {

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
		Long identifier = DomainObjectUtility.identifierFromObject(resolvedDomainObject);
		if (resolvedDomainObject == null || identifier == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("domain object or it's id is null - sure you can access not existing objects."+resolvedDomainObject);
			}
			return true;
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("checking permission on "+resolvedDomainObject);
			}
			return AcegiUtils.hasPermission(resolvedDomainObject, new Integer[] { permissions });
		}
	}

}
