package org.openuss.openformula.component.formula;

import javax.faces.component.UIInput;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;

/**
 * The OpenUSS OpenFormula component
 * @author Ingo Düppe
 */
public class Editor extends UIInput implements ValueHolder {
	
	public static final String COMPONENT_TYPE = "org.openuss.openformula.component.formula.Editor";
	public static final String DEFAULT_RENDERER_TYPE = "org.openuss.openformula.component.formula.EditorRenderer";
	public static final String COMPONENT_FAMILY = "javax.faces.Input";
	
	private String width;
	private String height;
	
	public Editor() {
		// do nothing
	}

	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		width = (String) values[1];
		height = (String) values[2];
		super.restoreState(context, values[0]);
	}

	public Object saveState(FacesContext context) {
		Object values[] = new Object[3];
		values[0] = super.saveState(context);
		values[1] = width;
		values[2] = height;
		return values;
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
