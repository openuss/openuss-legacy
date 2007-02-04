package org.openuss.framework.jsfcontrols.tags;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;


/**
 * Default Action for a JSF Form see
 * 
 * <a href="http://www.jsftutorials.net/defaultActionTag.html">JSF Tutorial</a>
 * 
 * @author sim
 */
public class ParamTag extends UIComponentTag {
	
	String name;
	String value;
	String sid;
	String method;
	
	/**
	 * @return Returns the method.
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method The method to set.
	 */
	public void setMethod(String method) {
		this.method = method;
	}
		/**
	 * @return Returns the sid.
	 */
	public String getSid() {
		return sid;
	}
	/**
	 * @param sid The sid to set.
	 */
	public void setSid(String sid) {
		this.sid = sid;
	}
/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}
	public String getComponentType() {
		return "org.j4j.param";
	}

	public String getRendererType() {
		return null;
	}
	
	protected void setProperties(UIComponent component) {

		super.setProperties(component);
		setString( component , "sid" , sid );
		setString( component , "name" , name );
		setString( component , "value" , value );
		setString( component , "method" , method );
		
	}
	
	 public static void setString(UIComponent component, String attributeName,
			String attributeValue) {
		if (attributeValue == null)
			return;
		if (UIComponentTag.isValueReference(attributeValue))
			setValueBinding(component, attributeName, attributeValue);
		else
			component.getAttributes().put(attributeName, attributeValue);
	}
	 
		public static void setValueBinding(UIComponent component,
				String attributeName, String attributeValue) {
			FacesContext context = FacesContext.getCurrentInstance();
			Application app = context.getApplication();
			ValueBinding vb = app.createValueBinding(attributeValue);
			component.setValueBinding(attributeName, vb);
		}

	
}
