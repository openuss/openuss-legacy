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
		String title;
		ResponseWriter writer = context.getResponseWriter();
		ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{visit.locale}");
		String locale = (String)binding.getValue(getFacesContext());
		ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(locale));
		
		
		writer.startElement("div", this);
			writer.writeAttribute("class", "flexList", null);
			
			// Begin flexlist title
			// Render only if the title is specified as an attribute
			title = (String)getAttributes().get("title");
			if(title != null)
			{	
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
									writer.writeAttribute("src", "/theme-plexus/img/collapsed_triangle_white.jpg", null);
									writer.writeAttribute("style", "display: none;", null);
									writer.writeAttribute("id", "flexlist_arrow_collapsed" + this.getId(), null);
								writer.endElement("img");
								
								writer.startElement("img", this);
									writer.writeAttribute("src", "/theme-plexus/img/expanded_triangle_white.jpg", null);
									writer.writeAttribute("id", "flexlist_arrow_expanded" + this.getId(), null);
								writer.endElement("img");
							
								writer.write(title);
							writer.endElement("div");
						writer.endElement("a");
						
					writer.endElement("li");
				writer.endElement("ul");
			}
			// End flexlist title
			
			// Begin flexlist content
			writer.startElement("div", this);
				writer.writeAttribute("id", "flexlist_content" + this.getId(), null);
				
				// Render list of visible items
				writer.startElement("ul", this);
				writer.writeAttribute("class", "flexListItems", null);
				
					ArrayList visibleItems = (ArrayList)(getAttributes().get("visibleItems"));
					
					if(visibleItems == null || visibleItems.isEmpty())
					{
						// Render a predefined string if the list is empty
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
						// Iterate over the list of visible items
						i = visibleItems.iterator();
						while(i.hasNext()) {
							listItem = (ListItemDAO)i.next();
							writer.startElement("li", this);
								// Render the "meta info" on the right side of the list
								writer.startElement("div", this);
									writer.writeAttribute("class", "flexListItemRight", null);
									
									String metaInfo = listItem.getMetaInformation();
									if(metaInfo != null)
										writer.write( listItem.getMetaInformation());	
									
									// Show the "remove bookmark link" if the url is set
									String removeBookmarkUrl = listItem.getRemoveBookmarkUrl();
									if(removeBookmarkUrl != null && removeBookmarkUrl != "")
									{
										// Render separating space between remove bookmark url and meta text
										writer.write(" ");
										
										writer.startElement("a", this);
											writer.writeAttribute("href", removeBookmarkUrl, null);
											
											writer.startElement("span", this);
												writer.writeAttribute("class", "remove_bookmark", null);
												
												String linkTitle;
												try {
													linkTitle = (String)this.getAttributes().get("alternateRemoveBookmarkLinkTitle");
												} catch (Exception e) {
													linkTitle = null;
												}
												
												if(linkTitle != null && linkTitle != "")
													writer.writeAttribute("title", linkTitle, null);
												else
													writer.writeAttribute("title", "Remove Bookmark", null);
													
												writer.write("&nbsp;&nbsp;&nbsp;");
												
												writer.startElement("span", this);
												writer.writeAttribute("class", "remove_bookmark_hidden", null);
												if(linkTitle != null && linkTitle != "")
													writer.write(linkTitle);
												else
													writer.write("Remove bookmark");	
												writer.endElement("span");
												
											writer.endElement("span");
							
											
										writer.endElement("a");
									}
									
								writer.endElement("div");
								
								// Render the left side of the item
								writer.startElement("div", this);
									writer.writeAttribute("class", "flexListItemLeft", null);
									
									title = listItem.getTitle();
									
									if(title != null)
									{
										String url = listItem.getUrl();
										// If the url is set, render an a tag, otherwise just plain text
										if(url != null)
										{
											writer.startElement("a", this);
												writer.writeAttribute("href", url, null);
												writer.write(title);
											writer.endElement("a");
										}
										else
										{
											writer.write(title);
										}
									}
								writer.endElement("div");
							writer.endElement("li");
						}
					}
				writer.endElement("ul");
	
				// Render list of hidden items
				// Attribute "hiddenItems" is set by the backingbean and contains a list of items of type ArrayList
				ArrayList hiddenItems = (ArrayList)getAttributes().get("hiddenItems");
				
				if(hiddenItems != null && !hiddenItems.isEmpty())
				{
					writer.startElement("ul", this);
						writer.writeAttribute("class", "flexListItems", null);
						writer.writeAttribute("id", "hidden_items" + this.getId(), null);
						writer.writeAttribute("style", "display:none;", null);
					
						// Iterate over the list of hidden items
						i = hiddenItems.iterator();
						while(i.hasNext()) {
							listItem = (ListItemDAO)i.next();
							writer.startElement("li", this);
							
							// Render the "meta info" on the right side of the list
							writer.startElement("div", this);
								writer.writeAttribute("class", "flexListItemRight", null);

								String metaInfo = listItem.getMetaInformation();
								if(metaInfo != null)
									writer.write( listItem.getMetaInformation());
								
								// Show the "remove bookmark link" if the url is set
								String removeBookmarkUrl = listItem.getRemoveBookmarkUrl();
								if(removeBookmarkUrl != null && removeBookmarkUrl != "")
								{
									// Render separating space between remove bookmark url and meta text
									writer.write(" ");
									
									writer.startElement("a", this);
										writer.writeAttribute("href", removeBookmarkUrl, null);
										
											writer.startElement("span", this);
												writer.writeAttribute("class", "remove_bookmark", null);
										
												String linkTitle;
												try {
													linkTitle = (String)this.getAttributes().get("alternateRemoveBookmarkLinkTitle");
												} catch (Exception e) {
													linkTitle = null;
												}
										
												if(linkTitle != null && linkTitle != "")
													writer.writeAttribute("title", linkTitle, null);
												else
													writer.writeAttribute("title", "Remove Bookmark", null);					
													
												writer.write("&nbsp;&nbsp;&nbsp;");
												
												writer.startElement("span", this);
												writer.writeAttribute("class", "remove_bookmark_hidden", null);
												if(linkTitle != null && linkTitle != "")
													writer.write(linkTitle);
												else
													writer.write("Remove bookmark");	
												writer.endElement("span");
												
											writer.endElement("span");
										
									writer.endElement("a");
								}
								
							writer.endElement("div");
								
								writer.startElement("div", this);
									writer.writeAttribute("class", "flexListItemLeft", null);
										title = listItem.getTitle();
										
										if(title != null)
										{
											String url = listItem.getUrl();
											
											if(url != null)
											{
												writer.startElement("a", this);
													writer.writeAttribute("href", url, null);
													writer.write(title);
												writer.endElement("a");
											}
											else
											{
												writer.write(title);
											}
										}
								writer.endElement("div");
								
							writer.endElement("li");
						}
					writer.endElement("ul");
				
				
					// Render the list footer containing the buttons for showing and hiding the hidden items
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
						
			writer.endElement("div");
			// End flexlist content
			
		writer.endElement("div");
	}
	public void encodeEnd(FacesContext context) throws IOException {
	}
}
