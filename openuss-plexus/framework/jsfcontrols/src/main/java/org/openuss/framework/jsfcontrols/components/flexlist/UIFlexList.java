package org.openuss.framework.jsfcontrols.components.flexlist;

import javax.faces.component.UIOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class UIFlexList extends UIOutput {
	public void encodeBegin(FacesContext context) throws IOException {
		Iterator i;
		ListItemDAO listItem;
		ResponseWriter writer = context.getResponseWriter();
		
		writer.startElement("div", this);
			writer.writeAttribute("class", "flexList", null);
			
			writer.startElement("ul", this);
			writer.writeAttribute("class", "flexListItems", null);
			
				// render title if specified as attribute
				String title = (String)getAttributes().get("title");
				if(title != null) {
					writer.startElement("li", this);
					writer.writeAttribute("class", "header", null);
					
						writer.startElement("div", this);
							writer.writeAttribute("class", "flexListItemRight", null);
						writer.endElement("div");
						
						writer.startElement("div", this);
							writer.writeAttribute("class", "flexListItemLeft", null);
							writer.write( title );
						writer.endElement("div");
						
					writer.endElement("li");
				}
				
				// render list of visible items
				i = ((ArrayList<ListItemDAO>)(getAttributes().get("visibleItems"))).iterator();
				while(i.hasNext()) {
					listItem = (ListItemDAO)i.next();
					writer.startElement("li", this);
						writer.startElement("div", this);
						writer.writeAttribute("class", "flexListItemRight", null);
							writer.write( listItem.getTitle());
						writer.endElement("div");
						writer.startElement("div", this);
						writer.writeAttribute("class", "flexListItemLeft", null);
							writer.write( listItem.getMetaInformation());
						writer.endElement("div");
					writer.endElement("li");
				}
			writer.endElement("ul");

			// render list of hidden items
			writer.startElement("ul", this);
				writer.writeAttribute("class", "flexListItems", null);
				writer.writeAttribute("id", "hidden_items" + this.getId(), null);
				writer.writeAttribute("style", "display:none;", null);
			
				// attribute "hiddenItems" is set by the backingbean and contains a list of items of type ArrayList
				i = ((ArrayList<ListItemDAO>)(getAttributes().get("hiddenItems"))).iterator();
				while(i.hasNext()) {
					listItem = (ListItemDAO)i.next();
					writer.startElement("li", this);
					
						writer.startElement("div", this);
							writer.writeAttribute("class", "flexListItemRight", null);
							writer.write( listItem.getTitle());
						writer.endElement("div");
						
						writer.startElement("div", this);
							writer.writeAttribute("class", "flexListItemLeft", null);
							writer.write( listItem.getMetaInformation());
						writer.endElement("div");
						
					writer.endElement("li");
				}
			writer.endElement("ul");

			// render the list footer containing the buttons for showing and hiding the hidden items
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
							String javascript = "" +
							"new Effect.toggle('show_hidden_items" + this.getId() + "', 'blind', {duration:0.001}); " +
							"new Effect.toggle('hide_hidden_items" + this.getId() + "', 'blind', {duration:0.001}); " +
							"new Effect.toggle('hidden_items" + this.getId() + "', 'blind', {duration:0.001});";	
							writer.writeAttribute("onclick", javascript, null);
							
							writer.startElement("span", this);
								writer.writeAttribute("id", "show_hidden_items" + this.getId(), null);
								
								String showButtonTitle = (String)getAttributes().get("showButtonTitle");
								if(showButtonTitle != null)
									writer.write( showButtonTitle );
								else
									writer.write( "More Items...");
							writer.endElement("span");
							
							writer.startElement("span", this);
								writer.writeAttribute("id", "hide_hidden_items" + this.getId(), null);
								writer.writeAttribute("style", "display:none;", null);
								
								String hideButtonTitle = (String)getAttributes().get("hideButtonTitle");
								if(hideButtonTitle != null)
									writer.write( hideButtonTitle );
								else
									writer.write("Fewer Items...");
							writer.endElement("span");
							
						writer.endElement("a");
					writer.endElement("div");
				writer.endElement("li");
			writer.endElement("ul");
		writer.endElement("div");
	}
	public void encodeEnd(FacesContext context) throws IOException {
	}
}
