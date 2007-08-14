package org.openuss.framework.jsfcontrols.components.flexlist;

import javax.faces.component.UIOutput;

import java.io.IOException;
import java.util.Iterator;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.util.List;

public class UITabs extends UIOutput {
	public void encodeBegin(FacesContext context) throws IOException {
		Iterator<ListItemDAO> i;
		ListItemDAO listItem;
		ResponseWriter writer = context.getResponseWriter();
		
		writer.startElement("div", this);
			writer.writeAttribute("id", "flexlist_tabs", null);
			
			writer.startElement("div", this);
				writer.writeAttribute("class", "header_normal", null);
			
			
				writer.startElement("div", this);
					writer.writeAttribute("class", "header_normal_left_bg", null);
				writer.endElement("div");
				
				writer.startElement("div", this);
					writer.writeAttribute("class", "header_normal_right_bg", null);
				writer.endElement("div");
				
				writer.startElement("div", this);
					writer.writeAttribute("class", "tabs", null);
					
					listItem = (ListItemDAO)getAttributes().get("currentItem");
					
					if(listItem != null)
					{
						// Render selected university
						writer.startElement("div", this);
							writer.writeAttribute("id", "tab_selected", null);
							writer.startElement("div", this);
								writer.writeAttribute("class", "tab_content", null);
								
								String title = listItem.getTitle();
								if(title != null)
									writer.write(title);
								
							writer.endElement("div");
						writer.endElement("div");
						
						// Render selection separator
						writer.startElement("div", this);
							writer.writeAttribute("class", "tab_arrow_sel_right", null);
						writer.endElement("div");
					}
					
					
					i = ((List<ListItemDAO>)(getAttributes().get("items"))).iterator();
				
					while(i.hasNext())
					{
						listItem = i.next();
						
						// Render deselected university
						writer.startElement("a", this);
							writer.writeAttribute("class", "tab_deselected", null);
							
							String url = listItem.getUrl();
							if(url != null)
								writer.writeAttribute("href", url, null);
							
							writer.startElement("div", this);
								writer.writeAttribute("class", "tab_content", null);
								
								String title = listItem.getTitle();
								if(title != null)
									writer.write(title);
								
							writer.endElement("div");
						writer.endElement("a");
						
						// Render deselected separator
						writer.startElement("div", this);
							writer.writeAttribute("class", "tab_arrow", null);
						writer.endElement("div");
					}
					
				writer.endElement("div");
			writer.endElement("div");
		writer.endElement("div");
	}
	public void encodeEnd(FacesContext context) throws IOException {
	}
}
