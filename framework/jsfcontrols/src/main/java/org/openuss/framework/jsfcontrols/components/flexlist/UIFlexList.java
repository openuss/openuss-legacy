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
	protected ResponseWriter writer;
	protected ResourceBundle bundle;
	
	public void encodeBegin(FacesContext context) throws IOException {
		writer = context.getResponseWriter();
		ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{visit.locale}");
		String locale = (String)binding.getValue(getFacesContext());
		bundle = ResourceBundle.getBundle("resources", new Locale(locale));
		
		writer.startElement("div", this);
			writer.writeAttribute("class", "flexList", null);
			writeHeader();
			writeContent();
			
		writer.endElement("div");
	}
	public void encodeEnd(FacesContext context) throws IOException {
	}
	
	//TODO remove li-element if css isnt broken afterwards
	private void writeHeader() throws IOException {
		String title = (String)getAttributes().get("title");
		//If title is not set, dont render
		if (title == null ) return;
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
	
	private void writeContent() throws IOException {
		
		writer.startElement("div", this);
		writer.writeAttribute("id", "flexlist_content" + this.getId(), null);
		
			// Render list of visible items
			writer.startElement("ul", this);
			writer.writeAttribute("class", "flexListItems", null);
		
			ArrayList<ListItemDAO> visibleItems = (ArrayList<ListItemDAO>)(getAttributes().get("visibleItems"));
			if(visibleItems == null || visibleItems.isEmpty())
			{
				writeEmptyItems();
			}
			else
			{
				writeListItems(visibleItems);
			}
			writer.endElement("ul");

			// Render list of hidden items
			ArrayList<ListItemDAO> hiddenItems = (ArrayList<ListItemDAO>)getAttributes().get("hiddenItems");
			
			if(hiddenItems != null && !hiddenItems.isEmpty())
			{
				writer.startElement("ul", this);
					writer.writeAttribute("class", "flexListItems", null);
					writer.writeAttribute("id", "hidden_items" + this.getId(), null);
					writer.writeAttribute("style", "display:none;", null);
				
					writeListItems(hiddenItems);
					
				writer.endElement("ul");
				// Render the list footer containing the buttons for showing and hiding the hidden items
				writeFooter();
			}
				
		writer.endElement("div");
	}
	
	private void writeEmptyItems() throws IOException {
		writer.startElement("li", this);
			writer.startElement("div", this);
			writer.writeAttribute("class", "flexListItemLeft", null);
			writer.writeAttribute("style", "font-style: italic;", null);
				writer.write( bundle.getString("flexlist_no_items") );
			writer.endElement("div");
		writer.endElement("li");
	}
	
	private void writeListItems(ArrayList<ListItemDAO> items)  throws IOException {
		Iterator<ListItemDAO> i;
		ListItemDAO listItem;
		
		i = items.iterator();
		while(i.hasNext()) {
			listItem = (ListItemDAO)i.next();
			writer.startElement("li", this);
				// Render the "meta info" on the right side of the list
				writeFlexListItemRight(listItem);
				
				// Render the left side of the item
				writeFlexListItemLeft(listItem);
			writer.endElement("li");
		}
	}
	private void writeFlexListItemLeft(ListItemDAO listItem) throws IOException {
		String title = listItem.getTitle();
		String url = listItem.getUrl();
		writer.startElement("div", this);
		writer.writeAttribute("class", "flexListItemLeft", null);

			if(title != null)
			{
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
	}
	protected void writeFlexListItemRight(ListItemDAO listItem) throws IOException {
		writer.startElement("div", this);
		writer.writeAttribute("class", "flexListItemRight", null);
			//write meta information
			String metaInfo = listItem.getMetaInformation();
			if(metaInfo != null) {
				writer.write( metaInfo);
			}
			
			// Show the "remove bookmark link" if the url is set
			String removeBookmarkUrl = listItem.getRemoveBookmarkUrl();
			String title;
			title = bundle.getString("flexlist_title_removeBookmarkUrl");
			if(removeBookmarkUrl != null && removeBookmarkUrl != "")
			{
				writer.startElement("a", this);
					writer.writeAttribute("href", removeBookmarkUrl, null);
					
					writer.startElement("span", this);
						writer.writeAttribute("class", "remove_bookmark", null);
						writer.writeAttribute("title", title, null);
						
					writer.endElement("span");
				writer.endElement("a");
			}
		
		writer.endElement("div");
	}
	private void writeFooter()  throws IOException {
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
}
