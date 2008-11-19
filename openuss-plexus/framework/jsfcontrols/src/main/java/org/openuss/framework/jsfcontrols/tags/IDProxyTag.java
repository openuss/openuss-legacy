package org.openuss.framework.jsfcontrols.tags;

import javax.faces.webapp.UIComponentTag;

/**
 * Default Action for a JSF Form see
 * 
 * <a href="http://www.jsftutorials.net/defaultActionTag.html">JSF Tutorial</a>
 * 
 * @author sim
 */
public class IDProxyTag extends UIComponentTag {
	public String getComponentType() {
		return "org.j4j.idProxy";
	}

	public String getRendererType() {
		return "org.j4j.idProxy";
	}
	
}
