package org.openuss.framework.jsfcontrols.components;

import java.io.IOException;

import javax.faces.component.UIOutput;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;

/**
 * Default Action for a JSF Form see 
 * <a href="http://www.jsftutorials.net/defaultActionTag.html">JSF Tutorial</a>
 * 
 * @author Ingo Düppe
 * @author sim
 */
public class UIDefaultAction extends UIOutput {

	public void encodeBegin(FacesContext context) throws IOException {
	}

	public void decode(FacesContext context) {
	}

	public void encodeEnd(FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		UIComponent actionComponent = super.getParent();
		String acId = actionComponent.getClientId(context);
		UIForm form = getForm(actionComponent);
		if (form != null) {
			String formId = form.getClientId(context);
			writer.startElement("script", null);
			writer.writeAttribute("type", "text/javascript", null);
			String functionBody = 
				   "{var keycode;" + 
				   "if (window.event) keycode = window.event.keyCode;" +
				   "else if (event) keycode = event.which;" +
				   "else return true;" + 
				   "if (keycode == 13) { " +
				   "document.getElementById('"+acId+"').click();return false; } " +
				   "else return true; }";

			String functionCode = "document.forms['" 
				+ formId 
				+ "'].onkeypress = new Function(\"" + functionBody + "\");";
			writer.write(functionCode);

			writer.endElement("script");
		}
	}

	private UIForm getForm(UIComponent component) {
		while (component != null) {
			if (component instanceof UIForm) {
				break;
			}
			component = component.getParent();
		}
		return (UIForm) component;
	}

}