package org.openuss.framework.jsfcontrols.tags;

import javax.faces.webapp.UIComponentTag;

/**
 * Default Action for a JSF Form see
 * 
 * <a href="http://www.jsftutorials.net/defaultActionTag.html">JSF Tutorial</a>
 * 
 * @author sim
 */
public class DefaultActionTag extends UIComponentTag {
	
	public String getComponentType() {
		return "org.openuss.framework.jsfcontrols.components.defaultAction";
	}

	public String getRendererType() {
		return null;
	}

}