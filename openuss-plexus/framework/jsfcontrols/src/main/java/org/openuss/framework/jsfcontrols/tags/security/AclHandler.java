package org.openuss.framework.jsfcontrols.tags.security;

import java.io.IOException;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.el.MethodBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.framework.web.jsf.util.FacesUtils;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;

/**
 * Facelets Tag to check acegi acl permissions
 * 
 * The tag has the following attributes:
 * <ul>
 * 	<li><code>domainObject</code> - defines the domain object on which the current authority must have permissions on.</li>
 *  <li><code>hasPermission</code> - defines the permission bit mask the authority must have.</li>
 *  <li><code>onErrorAction</code> - jsf action method binding to define an action that is performed if permission is denied</li>
 *  <li><code>ifNot="error"</code> - if is set the tag sends a 403 forbidden access error if permission is denied</li>
 * </ul>
 * 
 * @author Ingo Dueppe
 * 
 */
public class AclHandler extends TagHandler {
	private static final String ERROR = "error";

	static final Logger logger = Logger.getLogger(AclHandler.class);

	private final TagAttribute domainObject;
	private final TagAttribute hasPermission;
	private final TagAttribute onErrorAction;
	private final TagAttribute ifNot;

	public AclHandler(final TagConfig config) {
		super(config);

		domainObject = getRequiredAttribute("domainObject");
		hasPermission = getRequiredAttribute("hasPermission");
		ifNot = getAttribute("ifNot");
		onErrorAction = getAttribute("onErrorAction");
		
	}

	public void apply(final FaceletContext faceletContext, final UIComponent parent) throws IOException, FacesException, FaceletException,	ELException {
		if ((null == hasPermission) || StringUtils.isBlank(hasPermission.getValue())) {
			return; // skip entry
		}
		
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("SecurityContextHolder did not return a non-null Authentication object, so skipping tag body!");
			}
			return; // skip enty
		}

		if (domainObject.getObject(faceletContext) == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("domainObject resolved to null, so including tag body");
			}
			nextHandler.apply(faceletContext, parent);
			return; // apply entry
		}
		checkACLs(faceletContext, parent);
	}

	private void checkACLs(final FaceletContext faceletContext, final UIComponent parent) throws IOException {
		Object resolvedDomainObject = domainObject.getObject(faceletContext);
		Integer[] requiredIntegers = requiredIntegers(faceletContext);

		if (AcegiUtils.hasPermission(resolvedDomainObject, requiredIntegers)) {
			nextHandler.apply(faceletContext, parent);
		} else {		
			logger.debug("No permission to see the body!");
			permissionDenied(faceletContext);
		}
	}
	
	private void permissionDenied(FaceletContext faceletContext) throws IOException {
		if (onErrorAction != null) {
			performOnErrorAction(faceletContext);
		} else if (ifNot != null && StringUtils.equals(ifNot.getValue(),ERROR)) {
			FacesUtils.sendError(HttpServletResponse.SC_FORBIDDEN);
		} 
	}

	private void performOnErrorAction(FaceletContext faceletContext) {
		String expression = onErrorAction.getValue();
		if (FacesUtils.isExpressionStatement(expression) ) {
			MethodBinding actionMethod = faceletContext.getFacesContext().getApplication().createMethodBinding(expression, null);
			FacesUtils.handleNavigationOutcome(expression, FacesUtils.perform(actionMethod));
		} else {
			FacesUtils.handleNavigationOutcome(null, expression);
		}
	}

	/**
	 * Determins from a input object the required integers
	 * @param value
	 * @return required integers
	 */
	private Integer[] requiredIntegers(FaceletContext faceletContext) {
		Object value = hasPermission.getObject(faceletContext);
		Integer[] requiredIntegers = new Integer[0];
		if (value instanceof Integer) {
			requiredIntegers = new Integer[]{(Integer) value};
		} else if (value instanceof Long) {
			requiredIntegers = new Integer[]{((Long)value).intValue()};
		} else if (value instanceof String) {
			requiredIntegers = AcegiUtils.parseIntegersString((String)value);
		}
		return requiredIntegers;
	}

	/**
	 * Checks if the user is in all of the needed roles.
	 * 
	 * @param roleList
	 * @return true if user is in all of the needed roles
	 */
	public boolean isAllGranted(String roleList, HttpServletRequest request) {
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

}
