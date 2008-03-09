package org.openuss.framework.web.facelets;

import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.openuss.framework.web.jsf.pages.Pages;
import org.openuss.framework.web.jsf.util.ConversationUtil;

import com.sun.facelets.FaceletViewHandler;

/**
 * Enables to use value expressions within view ids.
 * @author Ingo Dueppe
 */
public class DynamicViewHandler extends FaceletViewHandler {

	public DynamicViewHandler(ViewHandler parent) {
		super(parent);
	}

	@Override
	public String getActionURL(final FacesContext context, final String viewId) {
		String url = super.getActionURL(context, viewId);
		String parameters = getParameterString(viewId);
		if (parameters.length() > 0) {
			String path = getPath(viewId);
			if (isVBExpression(parameters)) {
				// convert paramters
				parameters = ConversationUtil.interpolate(parameters);
			}
			// generate action url
			url = super.getActionURL(context, path) + parameters;
		}
		return Pages.instance().encodePageParameters(context, url, viewId);
	}
	
	private String getPath(final String url) {
		String path = url;
		int paramIndex = url.indexOf('?');
		if (paramIndex > -1) {
			path = url.substring(0, paramIndex);
		}
		return path;
	}

	private String getParameterString(final String url) {
		String parameters = "";
		int paramIndex = url.indexOf('?');
		if (paramIndex > -1) {
			parameters = url.substring(paramIndex);
		}
		return parameters;
	}

	/**
	 * Determine whether String is a value binding expression or not.
	 * 
	 * @param expression
	 *            The expression to test for value bindingness
	 * @return true iff the expression contains a VB expression
	 */
	public boolean isVBExpression(final String expression) {
		boolean result = false;
		if (!StringUtils.isBlank(expression)) {
			// check for #{...}
			int start = expression.indexOf("#{");
			int end = expression.indexOf('}');
			result = (start != -1) && (start < end);
			
			// check for ${...}
			if (!result) {
				start = expression.indexOf("${");
				result = (start != -1) && (start < end);
			}
		}
		return result;
	}

}
