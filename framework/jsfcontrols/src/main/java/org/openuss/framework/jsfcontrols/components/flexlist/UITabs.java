package org.openuss.framework.jsfcontrols.components.flexlist;

import javax.faces.component.UIOutput;

import java.io.IOException;
import java.util.Iterator;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.util.List;

public class UITabs extends UIOutput {
	public void encodeBegin(FacesContext context) throws IOException {
		Iterator<ListItemDAO> i = null;
		ListItemDAO listItem = null;
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
								
								String url = listItem.getUrl();
								if(url != null && url != "")
								{
									
									writer.startElement("a", this);
										writer.writeAttribute("class", "tab_details", null);
										writer.writeAttribute("style", "font-size: small; color: #5493D4;", null);
										writer.writeAttribute("href", url, null);
										
										writer.write(" (");
										String linkTitle;
										try {
											linkTitle = (String)this.getAttributes().get("alternateLinkTitle");
										} catch (Exception e) {
											linkTitle = null;
										}
										if(linkTitle != null && linkTitle != "")
											writer.write(linkTitle);
										else
											writer.write("Details");
										writer.write(")");
										
									writer.endElement("a");
									
								}
								
							writer.endElement("div");
							
							
						writer.endElement("div");
						
						// Render selection separator
						writer.startElement("div", this);
							writer.writeAttribute("class", "tab_arrow_sel_right", null);
						writer.endElement("div");
					}
					
					
					List<ListItemDAO> items = (List<ListItemDAO>)(getAttributes().get("items"));
					if(items != null && items.size() > 0)
					{
						i = items.iterator();
				
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
					}
					
				writer.endElement("div");
			writer.endElement("div");
		writer.endElement("div");
	}
	public void encodeEnd(FacesContext context) throws IOException {
	}
}
