package org.openuss.framework.jsfcontrols.tags.security;

import java.io.IOException;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.el.MethodBinding;
import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.framework.web.jsf.util.FacesUtils;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagException;
import com.sun.facelets.tag.TagHandler;

/**
 * Facelets Tag to check Acegi ACL permissions
 * 
 * The tag has the following attributes:
 * <ul>
 * 	<li><code>domainObject</code> - defines the domain object on which the current authority must have permissions on.</li>
 *  <li><code>hasPermission</code> - defines the permission bit mask the authority must have.</li>
 *  <li><code>onErrorAction</code> - JSF action method binding to define an action that is performed if permission is denied</li>
 *  <li><code>ifNot="error"</code> - if is set the tag sends a 403 forbidden access error if permission is denied</li>
 *  <li><code>hasNotPermission</code> - defines the permission bit mask the authority must not have.</li>
 * </ul>
 * 
 * @author Ingo Dueppe
 * 
 */
public class AclHandler extends TagHandler {
	private static final String ERROR = "error";

	private static final Logger logger = Logger.getLogger(AclHandler.class);

	private final TagAttribute domainObject;
	private final TagAttribute onErrorAction;
	private final TagAttribute ifNot;
	
	private final TagAttribute permission;
	
	private final boolean reverted;

	public AclHandler(final TagConfig config) throws TagException {
		super(config);

		domainObject = getRequiredAttribute("domainObject");
		
		if (getAttribute("hasPermission") == null) {
			permission = getAttribute("hasNotPermission");
			reverted = true;
		} else {
			permission = getAttribute("hasPermission");
			reverted = false;
		}
		ifNot = getAttribute("ifNot");
		onErrorAction = getAttribute("onErrorAction");
		validatePermissionAttributes();
		
	}

	private void validatePermissionAttributes() {
		if (permission == null) {
			throw new TagException(this.tag, "Need to define eather hasPermission or hasNotPermission");
		} 
	}

	public void apply(final FaceletContext faceletContext, final UIComponent parent) throws IOException, FacesException, FaceletException,	ELException {
		if (StringUtils.isBlank(permission.getValue())) {
			return; // skip entry
		}
		
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("SecurityContextHolder did not return a non-null Authentication object, so skipping tag body!");
			}
			return; // skip entry
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

		if (considerReverted(AcegiUtils.hasPermission(resolvedDomainObject, requiredIntegers))) {
			nextHandler.apply(faceletContext, parent);
		} else {		
			logBodySkipped(resolvedDomainObject);
			permissionDenied(faceletContext);
		}
	}

	private void logBodySkipped(Object resolvedDomainObject) {
		if (logger.isDebugEnabled()) {
			if (reverted) {
				logger.debug("Has permission on "+ReflectionToStringBuilder.toString( resolvedDomainObject )+", due to reverted the body is skiped!");
			} else {					
				logger.debug("No permission on "+ReflectionToStringBuilder.toString( resolvedDomainObject )+" to see the body!");
			}
			
		}
	}
	
	private boolean considerReverted(final boolean value) {
		if (reverted) {
			return !value;
		} else {
			return value;
		}
	}
	
	private void permissionDenied(final FaceletContext faceletContext) throws IOException {
		if (onErrorAction != null) {
			performOnErrorAction(faceletContext);
		} else if (ifNot != null && StringUtils.equals(ifNot.getValue(),ERROR)) {
			throw new AccessDeniedException("Access denied on domain object "+domainObject.getValue());
//			FacesUtils.sendError(HttpServletResponse.SC_FORBIDDEN);
		} 
	}

	private void performOnErrorAction(final FaceletContext faceletContext) {
		String expression = onErrorAction.getValue();
		if (FacesUtils.isExpressionStatement(expression) ) {
			MethodBinding actionMethod = faceletContext.getFacesContext().getApplication().createMethodBinding(expression, null);
			FacesUtils.handleNavigationOutcome(expression, FacesUtils.perform(actionMethod));
		} else {
			FacesUtils.handleNavigationOutcome(null, expression);
		}
	}

	/**
	 * Determines from a input object the required integers
	 * @param value
	 * @return required integers
	 */
	private Integer[] requiredIntegers(final FaceletContext faceletContext) {
		final Object value = permission.getObject(faceletContext);
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
	public boolean isAllGranted(final String roleList, final HttpServletRequest request) {
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
