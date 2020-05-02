package org.openuss.framework.web.jsf.renderer;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

/**
 * Custom LabelRenderer component that adds asterisks for required fields. Based
 * off of an example from David Geary on the MyFaces Mailing list.
 * 
 * @author Matt Raible
 * @author Ingo Düppe
 */
public class LabelRenderer extends Renderer {

	public boolean getRendersChildren() {
		return false;
	}

	public void encodeBegin(FacesContext context, UIComponent component) throws java.io.IOException {
		ResponseWriter writer = context.getResponseWriter();

		Map<String, String> attrs = component.getAttributes();
		String id = attrs.get("for");

		UIInput input = (UIInput) component.findComponent(id);

		writer.startElement("label", component);

		String styleClass = (String) component.getAttributes().get("styleClass");

		if (styleClass != null) {
			writer.writeAttribute("class", styleClass, null);
		}

		String renderedId = (input != null) ? input.getClientId(context) : component.getClientId(context);
		writer.writeAttribute("for", renderedId, null);
		writer.write(attrs.get("value"));
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws java.io.IOException {
		ResponseWriter writer = context.getResponseWriter();

		Map<String, String> attrs = component.getAttributes();
		String id = attrs.get("for");

		UIInput input = (UIInput) component.findComponent(id);

		if ((input != null) && input.isRequired()) {
			writer.write(" <span class=\"requiredAsterix\">*</span>");
		}

		writer.endElement("label");
	}
}
