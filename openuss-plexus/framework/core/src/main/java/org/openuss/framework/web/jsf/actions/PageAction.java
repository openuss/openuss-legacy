package org.openuss.framework.web.jsf.actions;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;

import org.apache.commons.lang.StringUtils;

/**
 * Defines an action that is performed if the specified view is requested 
 * @author Ingo Dueppe
 */
public class PageAction {
	
	private String viewId;
	private boolean isPattern = false;
	private String pattern;
	private String action;
	
	private MethodBinding mbAction;
	
	/**
	 * Checks wether the requestViewId matches the action view pattern.
	 * @param viewId of the request
	 * @return true if the requestViewId matches the defined action pattern (viewId)
	 */
	public boolean matches(String requestViewId) {
		if (StringUtils.isBlank(requestViewId))
			return false;
		if (isPattern) {
			return requestViewId.startsWith(pattern);
		} else {
			return StringUtils.equals(viewId, requestViewId);
		}
	}
	
	/**
	 * Performs the action within the given faces context 
	 * @param facesContext
	 * @return null or the string outcome of the binded action method
	 */
	public String perform(FacesContext facesContext) {
		String outcome = null;
		if (mbAction == null) {
			final Application application = facesContext.getApplication();
			mbAction = application.createMethodBinding(action, null);
		}
		Object result = mbAction.invoke(facesContext, null);
		if (result instanceof String)
			outcome = (String) result;
		return outcome;
	}
	
	/**
	 * A EL expression that defines which method to invoke if the defined view is requested
	 * @return action 
	 */
	public String getAction() {
		return action;
	}
	
	/**
	 * Sets EL expression that defines which method to invoke if the defined view is requested.
	 * @param action
	 */
	public void setAction(String action) {
		this.action = action;
	}
	
	/**
	 * The viewId the action is listing on.
	 * @return
	 */
	public String getViewId() {
		return viewId;
	}
	
	/**
	 * Sets the viewId the action should listen for.
	 * @param viewId
	 */
	public void setViewId(String viewId) {
		this.viewId = viewId;
		if (viewId.endsWith("*")) {
			pattern = viewId.substring(0, viewId.length()-2);
			isPattern = true;
		} else {
			pattern = "";
			isPattern = false;
		}
			
	}

	/**
	 * Returns the action el expressed strip from el pre- and suffix.
	 * @return
	 */
	public String getFromAction() {
		String fromAction = action;
		if (action.startsWith("#{")) { 
			fromAction = StringUtils.substringBetween(action, "#{", "}");
		}
		else if (action.startsWith("${")) {
			fromAction = StringUtils.substringBetween(action, "${", "}");
		}
		return fromAction;
	}
	

}
