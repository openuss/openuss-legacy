package org.openuss.framework.jsfcontrols.tags.security;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.Authentication;
import org.acegisecurity.acl.AclEntry;
import org.acegisecurity.acl.AclManager;
import org.acegisecurity.acl.basic.BasicAclEntry;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;

/**
 * Facelets Tag to check acegi acl permissions
 * 
 * @author Ingo Dueppe
 * 
 */
public class AclHandler extends TagHandler {
	private static final Logger logger = Logger.getLogger(AclHandler.class);

	private final TagAttribute domainObject;
	private final TagAttribute hasPermission;

	public AclHandler(final TagConfig config) {
		super(config);

		domainObject = getAttribute("domainObject");
		hasPermission = getAttribute("hasPermission");
	}

	public void apply(FaceletContext ctx, UIComponent parent) throws IOException, FacesException, FaceletException,
			ELException {
		if ((null == hasPermission) || StringUtils.isBlank(hasPermission.getValue())) {
			return; // skip entry
		}

		Object resolvedDomainObject = domainObject.getObject(ctx);
		Object value = hasPermission.getObject(ctx);
		
		Integer[] requiredIntegers = new Integer[0];
		if (value instanceof Integer) {
			requiredIntegers = new Integer[]{(Integer) value};
		} else if (value instanceof Long) {
			requiredIntegers = new Integer[]{((Long)value).intValue()};
		} else if (value instanceof String) {
			requiredIntegers = parseIntegersString((String)value);
		}


		if (resolvedDomainObject == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("domainObject resolved to null, so including tag body");
				nextHandler.apply(ctx, parent);
				return;
			}
		}

		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("SecurityContextHolder did not return a non-null Authentication object, so skipping tag body!");
			}
			return;
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		FacesContext facesContext = ctx.getFacesContext();

		AclManager aclManager = (AclManager) facesContext.getApplication().createValueBinding("#{aclManager}")
				.getValue(facesContext);

		AclEntry[] acls = aclManager.getAcls(resolvedDomainObject, auth);

		if (logger.isDebugEnabled()) {
			logger.debug("Authentication: '" + auth + "' has: " + ((acls == null) ? 0 : acls.length)
					+ " AclEntrys for domain object: '" + resolvedDomainObject + "' from AclManager: ' "
					+ aclManager.toString() + "'");
		}

		if ((acls != null) && acls.length > 0) {
			// Locate processable AclEntrys
			for (AclEntry aclEntry : acls) {
				if (aclEntry instanceof BasicAclEntry) {
					BasicAclEntry processableAcl = (BasicAclEntry) aclEntry;
					for (Integer required : requiredIntegers) {
						if (processableAcl.isPermitted(required)) {
							if (logger.isDebugEnabled()) {
								logger.debug("Including tag body as found permission: " + requiredIntegers
										+ " due to AclEntry: '" + processableAcl + "'");
								nextHandler.apply(ctx, parent);
								return;
							}
						}
					}
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("No permission, so skipping tag body");
		}
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

	private Integer[] parseIntegersString(String integersString) throws NumberFormatException {
		final Set integers = new HashSet();
		final StringTokenizer tokenizer;
		tokenizer = new StringTokenizer(integersString, ",", false);

		while (tokenizer.hasMoreTokens()) {
			String integer = tokenizer.nextToken();
			integers.add(new Integer(integer));
		}

		return (Integer[]) integers.toArray(new Integer[] {});
	}

}
