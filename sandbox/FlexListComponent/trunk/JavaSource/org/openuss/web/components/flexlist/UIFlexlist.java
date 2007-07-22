package org.openuss.web.components.flexlist;

import javax.faces.component.UIOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class UIFlexlist extends UIOutput {
	public void encodeBegin(FacesContext context) throws IOException {
		Iterator i;
		ListItemDAO listItem;
		ResponseWriter writer = context.getResponseWriter();
		
		writer.startElement("div", this);
		writer.writeAttribute("class", "flexList", null);
			writer.startElement("ul", this);
			writer.writeAttribute("class", "flexListItems", null);
				writer.startElement("li", this);
				writer.writeAttribute("class", "header", null);
					writer.startElement("div", this);
					writer.writeAttribute("class", "flexListItemRight", null);
					writer.endElement("div");
					writer.startElement("div", this);
					writer.writeAttribute("class", "flexListItemLeft", null);
					writer.write( getAttributeString("title") );
					writer.endElement("div");
				writer.endElement("li");
				
				i = ((ArrayList)(getAttributes().get("visibleItems"))).iterator();
				while(i.hasNext()) {
					listItem = (ListItemDAO)i.next();
					writer.startElement("li", this);
						writer.startElement("div", this);
						writer.writeAttribute("class", "flexListItemRight", null);
							writer.write( listItem.getTitle());
						writer.endElement("div");
						writer.startElement("div", this);
						writer.writeAttribute("class", "flexListItemLeft", null);
							writer.write( listItem.getInformation());
						writer.endElement("div");
					writer.endElement("li");
				}
			writer.endElement("ul");

			writer.startElement("ul", this);
			writer.writeAttribute("class", "flexListItems", null);
			writer.writeAttribute("id", "hidden_flexListItems1", null);
			writer.writeAttribute("style", "display:none;", null);
			
				i = ((ArrayList)(getAttributes().get("hiddenItems"))).iterator();
				
				while(i.hasNext()) {
					listItem = (ListItemDAO)i.next();
					writer.startElement("li", this);
						writer.startElement("div", this);
						writer.writeAttribute("class", "flexListItemRight", null);
							writer.write( listItem.getTitle());
						writer.endElement("div");
						writer.startElement("div", this);
						writer.writeAttribute("class", "flexListItemLeft", null);
							writer.write( listItem.getInformation());
						writer.endElement("div");
					writer.endElement("li");
				}
			writer.endElement("ul");

			writer.startElement("ul", this);
			writer.writeAttribute("class", "flexListBottom", null);
				writer.startElement("li", this);
					writer.startElement("div", this);
					writer.writeAttribute("class", "flexListItemRight", null);
					writer.endElement("div");
					writer.startElement("div", this);
					writer.writeAttribute("class", "flexListItemLeft", null);
					writer.startElement("a", this);
						writer.writeAttribute("href", "javascript:void(0)", null);
						writer.writeAttribute("style", "color: #5493D4;", null);
						writer.writeAttribute("onclick", "new Effect.toggle('show_hidden_flexlist1', 'blind', {duration:0.001}); new Effect.toggle('hide_hidden_flexlist1', 'blind', {duration:0.001}); new Effect.toggle('hidden_flexListItems1', 'blind', {duration:0.001});", null);
						writer.startElement("span", this);
							writer.writeAttribute("id", "show_hidden_flexlist1", null);
							writer.write( getAttributeString("showButtonTitle") );
						writer.endElement("span");
						writer.startElement("span", this);
							writer.writeAttribute("id", "hide_hidden_flexlist1", null);
							writer.writeAttribute("style", "display:none;", null);
							writer.write( getAttributeString("hideButtonTitle") );
						writer.endElement("span");						
					writer.endElement("a");
					writer.endElement("div");
				writer.endElement("li");
			writer.endElement("ul");
		writer.endElement("div");
	}
	public void encodeEnd(FacesContext context) throws IOException {
	}
	
	public String getAttributeString(String attributeName)
	{
		return (String)(getAttributes().get(attributeName));
	}
}
