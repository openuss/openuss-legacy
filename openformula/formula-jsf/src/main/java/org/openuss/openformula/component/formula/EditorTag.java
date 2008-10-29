package org.openuss.openformula.component.formula;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentTag;

/**
 * The tag for my component
 */
public class EditorTag extends UIComponentTag {
	
	private String value;
	private String width;
	private String height;

	/**
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	public String getComponentType() {
		return Editor.COMPONENT_TYPE;
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	public String getRendererType() {
		return Editor.DEFAULT_RENDERER_TYPE;
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#setProperties
	 */
	protected void setProperties(UIComponent component) {
		super.setProperties(component);
		
		Editor editor = (Editor) component;

		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();

		// value
		if (value != null) {
			if (UIComponentTag.isValueReference(value)) {
				component.setValueBinding("value", application.createValueBinding(value));
			} else {
				component.getAttributes().put("value", value);
			}
		}
		
		if (width != null) {
			editor.setWidth(width);
		}
		
		if (height != null) {
			editor.setHeight(height);
		}

	}

	public void release() {
		super.release();
		this.value = null;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
}
