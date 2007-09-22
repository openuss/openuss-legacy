package org.openuss.framework.jsfcontrols.tags.security;

import java.io.IOException;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.servlet.http.HttpServletRequest;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;

public class AuthorizeHandler extends TagHandler {
	
	private final TagAttribute ifAllGranted;
	private final TagAttribute ifAnyGranted;
	private final TagAttribute ifNotGranted;

	public AuthorizeHandler(final TagConfig config) {
		super(config);
		
		ifAllGranted = getAttribute("ifAllGranted");
		ifAnyGranted = getAttribute("ifAnyGranted");
		ifNotGranted = getAttribute("ifNotGranted");
	}

	public void apply(FaceletContext ctx, UIComponent parent) throws IOException, FacesException, FaceletException,	ELException {
		final HttpServletRequest request = (HttpServletRequest) ctx.getFacesContext().getExternalContext().getRequest();
		
		boolean isAllGranted = true;
		boolean isAnyGranted = true;
		boolean isNotGranted = true;
		
		if (ifAllGranted != null) {
			isAllGranted = Authorize.isAllGranted(ifAllGranted.getValue(), request);
		}
		
		if (ifAnyGranted != null) {
			isAnyGranted = Authorize.isAnyGranted(ifAnyGranted.getValue(), request);
		}
		
		if (ifNotGranted != null) {
			isNotGranted = Authorize.isNotGranted(ifNotGranted.getValue(), request);
		}
		
		if (isAllGranted && isAnyGranted && isNotGranted) {
			nextHandler.apply(ctx, parent);
		}
	}
	
	/**
	 * Checks if the user is in all of the needed roles.
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
