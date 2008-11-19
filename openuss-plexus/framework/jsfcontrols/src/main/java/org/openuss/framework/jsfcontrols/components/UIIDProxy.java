package org.openuss.framework.jsfcontrols.components;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * Default Action for a JSF Form see 
 * <a href="http://www.jsftutorials.net/defaultActionTag.html">JSF Tutorial</a>
 * 
 * @author sim
 **/
public class UIIDProxy extends UIOutput {

	public void encodeBegin(FacesContext context) throws IOException {
	}

	public void decode(FacesContext context) {
	}
	public void encodeEnd(FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("span", null);
		UIComponent parent=super.getParent();
		String pid=parent.getClientId(context);
		String id = (String)getAttributes().get("id");
		writer.writeAttribute("id", id, "id");
		writer.writeAttribute("title", pid, "id");
		writer.endElement("span");
	}
}
