package org.openuss.framework.jsfcontrols.components.flexlist;

import java.io.IOException;

public class CourseUIFlexList extends UIFlexList {
	protected void writeFlexListItemRight(ListItemDAO listItem) throws IOException {
		writer.startElement("div", this);
		writer.writeAttribute("class", "flexListItemRight", null);
			//write meta information
			String metaInfo = listItem.getMetaInformation();
			if(metaInfo != null) {
				writer.write( metaInfo);
			}
			
			
			//Render newsletter status
			Boolean subscribed = listItem.getNewsletterSubscribed();
			String url = listItem.getNewsletterActionUrl();
			String cssClass;
			if (subscribed == null) {
				cssClass = "newsletter_none";
			}
			else {
				cssClass = (subscribed) ? "newsletter_subscribed" : "newsletter_notSubscribed";
			}
			String title;
			if (subscribed == null) {
				title = bundle.getString("flexlist_title_newsletter_none");
			}
			else {
				title = (subscribed) ? bundle.getString("flexlist_title_newsletter_unsubscribe") : bundle.getString("flexlist_title_newsletter_subscribe");
			}
			if (subscribed == null) {
				writer.startElement("span", this);
					writer.writeAttribute("class",cssClass, null);
					writer.writeAttribute("title", title, null);
					writer.startElement("span", this);
					writer.writeAttribute("title", title, null);
					writer.endElement("span");								
				writer.endElement("span");
			}
			else {
				writer.startElement("a", this);
				writer.writeAttribute("href", url, null);
									
					writer.writeAttribute("class", cssClass, null);
					writer.writeAttribute("title", title, null);
					writer.startElement("span", this);
					writer.writeAttribute("title", title, null);
					writer.endElement("span");
				writer.endElement("a");
			}
			
			//Render forum status
			subscribed = listItem.getForumSubscribed();
			url = listItem.getForumActionUrl();
			if (subscribed == null) {
				cssClass = "forum_none";
			}
			else {
				cssClass = (subscribed) ? "forum_subscribed" : "forum_notSubscribed";
			}
			if (subscribed == null) {
				title = bundle.getString("flexlist_title_forum_none");
			}
			else {
				title = (subscribed) ? bundle.getString("flexlist_title_forum_unsubscribe") : bundle.getString("flexlist_title_forum_subscribe");
			}
			if (subscribed == null) {
				writer.startElement("span", this);
				writer.writeAttribute("class",cssClass, null);
				writer.writeAttribute("title", title, null);
					writer.startElement("span", this);
						writer.writeAttribute("title", title, null);
					writer.endElement("span");			
				writer.endElement("span");
			}
			else {
				writer.startElement("a", this);
				writer.writeAttribute("href", url, null);
					writer.writeAttribute("class", cssClass, null);
					writer.writeAttribute("title", title, null);
					writer.startElement("span", this);
					writer.writeAttribute("title", title, null);
					writer.endElement("span");
				writer.endElement("a");
			}
			
			// Show the "remove bookmark link" if the url is set
			String removeBookmarkUrl = listItem.getRemoveBookmarkUrl();
			title = bundle.getString("flexlist_title_removeBookmarkUrl");
			if(removeBookmarkUrl != null && removeBookmarkUrl != "")
			{
				writer.startElement("a", this);
					writer.writeAttribute("href", removeBookmarkUrl, null);
					
					writer.writeAttribute("class", "remove_bookmark", null);
					writer.writeAttribute("title", title, null);
					//writer.write("&nbsp;");
					writer.startElement("span", this);
					writer.writeAttribute("title", title, null);
					writer.endElement("span");
				writer.endElement("a");
			}
		
		writer.endElement("div");
	}
}