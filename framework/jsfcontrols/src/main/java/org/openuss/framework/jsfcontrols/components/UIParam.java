package org.openuss.framework.jsfcontrols.components;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * Default Action for a JSF Form see 
 * <a href="http://www.jsftutorials.net/defaultActionTag.html">JSF Tutorial</a>
 * 
 * @author sim
 */
public class UIParam extends UIOutput {

	public void encodeBegin(FacesContext context) throws IOException {
	}

	public void decode(FacesContext context) {
	}

	public void encodeEnd(FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		String name = (String) getAttributes().get("name");
		UIComponent actionComponent = super.getParent();

		// don't know if this call does any side effect.
		actionComponent.getClientId(context);

		UIForm form = getForm(actionComponent);
		if (form != null) {

			String method = (String) getAttributes().get("method");
			String sid = (String) getAttributes().get("sid");
			String value = (String) getAttributes().get("value");
			if (value == null)
				value = "";

			if (sid != null) {
				writer.writeAttribute("id", sid, "sid");
			} else {
				if (shouldWriteIdAttribute(this))
					writer.writeAttribute("id", this.getClientId(context), "id");
			}

			if (method == null)
				method = "post";
			if ("get".equalsIgnoreCase(method)) {
				String formId = form.getClientId(context);

				writer.startElement("script", null);

				writer.write("var f = document.forms['" + formId + "'];\n");
				writer.write("var faction=f.action;\n");
				writer.write("faction=(faction.indexOf('?')>-1?faction+'&':faction+'?');\n");

				writer.write("faction=faction+'" + name + "=" + value + "';\n");
				writer.write("f.action=faction;\n");
				writer.endElement("script");

			} else {
				writer.startElement("input", null);

				writer.writeAttribute("type", "hidden", "");
				setStringAttribute(context, "name", "name");
				setStringAttribute(context, "value", "value");
			}
		} else {
			throw new NullPointerException("Param tag with name " + name + " must be declared inside the form.");
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

	public void setStringAttribute(FacesContext context, String htmlTag, String cTag) throws IOException {
		String value = (String) getAttributes().get(cTag);
		if (value != null) {
			ResponseWriter writer = context.getResponseWriter();
			writer.writeAttribute(htmlTag, value, null);
		}
	}

	protected boolean shouldWriteIdAttribute(UIComponent component) {
		String id;
		return (null != (id = component.getId()) && !id.startsWith(UIViewRoot.UNIQUE_ID_PREFIX));
	}

}