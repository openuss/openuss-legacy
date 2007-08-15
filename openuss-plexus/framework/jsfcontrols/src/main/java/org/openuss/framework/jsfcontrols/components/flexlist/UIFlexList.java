package org.openuss.framework.jsfcontrols.components.flexlist;

import javax.faces.component.UIOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;;

public class UIFlexList extends UIOutput {
	public void encodeBegin(FacesContext context) throws IOException {
		Iterator i;
		ListItemDAO listItem;
		ResponseWriter writer = context.getResponseWriter();
		ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{visit.locale}");
		String locale = (String)binding.getValue(getFacesContext());
		ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(locale));
		
		
		writer.startElement("div", this);
			writer.writeAttribute("class", "flexList", null);
			
			// render title if specified as attribute
			String title = (String)getAttributes().get("title");
			if(title != null) {
				
				writer.startElement("ul", this);
				writer.writeAttribute("class", "flexListItems", null);
			
				
					writer.startElement("li", this);
					writer.writeAttribute("class", "header", null);
					
						writer.startElement("div", this);
							writer.writeAttribute("class", "flexListItemRight", null);
						writer.endElement("div");
						
						writer.startElement("a", this);
							writer.writeAttribute("href", "javascript:void(0)", null);
							String javascript = "" +
								"Element.toggle('flexlist_arrow_expanded" + this.getId() + "'); " + 
								"Element.toggle('flexlist_arrow_collapsed" + this.getId() + "'); " +
								"Element.toggle('flexlist_content" + this.getId() + "');";
							writer.writeAttribute("onclick", javascript, null);
							
							writer.startElement("div", this);
								writer.writeAttribute("class", "flexListItemLeft", null);
								
								writer.startElement("img", this);
									writer.writeAttribute("src", "img/collapsed_triangle_white.jpg", null);
									writer.writeAttribute("style", "display: none;", null);
									writer.writeAttribute("id", "flexlist_arrow_collapsed" + this.getId(), null);
								writer.endElement("img");
								
								writer.startElement("img", this);
									writer.writeAttribute("src", "img/expanded_triangle_white.jpg", null);
									writer.writeAttribute("id", "flexlist_arrow_expanded" + this.getId(), null);
								writer.endElement("img");
							
								writer.write(title);
							writer.endElement("div");
						writer.endElement("a");
						
					writer.endElement("li");
				writer.endElement("ul");
			}
				
			writer.startElement("div", this);
				writer.writeAttribute("id", "flexlist_content" + this.getId(), null);
				
				// Begin flexlist content
				// render list of visible items
				writer.startElement("ul", this);
				writer.writeAttribute("class", "flexListItems", null);
				
					
					ArrayList visibleItems = (ArrayList)(getAttributes().get("visibleItems"));
					
					if(visibleItems == null || visibleItems.isEmpty())
					{
						writer.startElement("li", this);
							writer.startElement("div", this);
							writer.writeAttribute("class", "flexListItemLeft", null);
							writer.writeAttribute("style", "font-style: italic;", null);
								writer.write( bundle.getString("flexlist_no_items") );
							writer.endElement("div");
						writer.endElement("li");
					}
					else
					{
						i = visibleItems.iterator();
						while(i.hasNext()) {
							listItem = (ListItemDAO)i.next();
							writer.startElement("li", this);
								writer.startElement("div", this);
								writer.writeAttribute("class", "flexListItemRight", null);
								String metaInfo = listItem.getMetaInformation();
								if(metaInfo != null)
									writer.write( listItem.getMetaInformation());
								writer.endElement("div");
								writer.startElement("div", this);
								writer.writeAttribute("class", "flexListItemLeft", null);
									writer.write( listItem.getTitle());						
								writer.endElement("div");
							writer.endElement("li");
						}
					}
				writer.endElement("ul");
	
				// render list of hidden items
				ArrayList hiddenItems = (ArrayList)getAttributes().get("hiddenItems");
				
				if(hiddenItems != null && !hiddenItems.isEmpty())
				{
					writer.startElement("ul", this);
						writer.writeAttribute("class", "flexListItems", null);
						writer.writeAttribute("id", "hidden_items" + this.getId(), null);
						writer.writeAttribute("style", "display:none;", null);
					
						// attribute "hiddenItems" is set by the backingbean and contains a list of items of type ArrayList
						i = hiddenItems.iterator();
						while(i.hasNext()) {
							listItem = (ListItemDAO)i.next();
							writer.startElement("li", this);
							
								writer.startElement("div", this);
									writer.writeAttribute("class", "flexListItemRight", null);
									String metaInfo = listItem.getMetaInformation();
									if(metaInfo != null)
										writer.write(metaInfo);
								writer.endElement("div");
								
								writer.startElement("div", this);
									writer.writeAttribute("class", "flexListItemLeft", null);
										writer.write( listItem.getTitle());
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
									"Element.toggle('show_hidden_items" + this.getId() + "'); " +
									"Element.toggle('hide_hidden_items" + this.getId() + "'); " +
									"Element.toggle('hidden_items" + this.getId() + "');";
									//For sliding-animation-effect uncomment this ...
									//"new Effect.toggle('show_hidden_items" + this.getId() + "', 'blind', {duration:1.0}); " +
									//"new Effect.toggle('hide_hidden_items" + this.getId() + "', 'blind', {duration:1.0}); " +
									//"new Effect.toggle('hidden_items" + this.getId() + "', 'blind', {duration:1.0});";
									writer.writeAttribute("onclick", javascript, null);
									
									writer.startElement("span", this);
										writer.writeAttribute("id", "show_hidden_items" + this.getId(), null);
										
										String showButtonTitle = (String)getAttributes().get("showButtonTitle");
										if(showButtonTitle != null)
											writer.write( showButtonTitle );
										else
											writer.write(bundle.getString("flexlist_more_items"));
									writer.endElement("span");
									
									writer.startElement("span", this);
										writer.writeAttribute("id", "hide_hidden_items" + this.getId(), null);
										writer.writeAttribute("style", "display:none;", null);
										
										String hideButtonTitle = (String)getAttributes().get("hideButtonTitle");
										if(hideButtonTitle != null)
											writer.write( hideButtonTitle );
										else
											writer.write(bundle.getString("flexlist_less_items"));
									writer.endElement("span");
									
								writer.endElement("a");
							writer.endElement("div");
						writer.endElement("li");
					writer.endElement("ul");
				}
				
				// End flexlist content
							
			writer.endElement("div");
		writer.endElement("div");
	}
	public void encodeEnd(FacesContext context) throws IOException {
	}
}
