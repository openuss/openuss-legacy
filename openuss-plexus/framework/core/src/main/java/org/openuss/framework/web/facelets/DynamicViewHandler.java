package org.openuss.framework.web.facelets;

import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.commons.lang.StringUtils;
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
	public String getActionURL(FacesContext context, String viewId) {
		String url = viewId;
		String parameters = getParameterString(url);
		if (parameters.length() > 0 &&  isVBExpression(parameters)) {
			parameters = ConversationUtil.interpolate(parameters);
		}
		// save parameters 
		
		return super.getActionURL(context, url) + parameters;
		
	}
	
	private String getParameterString(String url) {
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
	public boolean isVBExpression(String expression) {
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
