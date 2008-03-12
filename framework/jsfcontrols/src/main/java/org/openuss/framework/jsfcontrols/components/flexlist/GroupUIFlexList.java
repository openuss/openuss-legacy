package org.openuss.framework.jsfcontrols.components.flexlist;

import java.io.IOException;

import org.apache.log4j.Logger;

public class GroupUIFlexList extends UIFlexList {
	
	protected void writeFlexListItemRight(ListItemDAO listItem) throws IOException {
		writer.startElement("div", this);
		writer.writeAttribute("class", "flexListItemRight", null);
			//write meta information
			String metaInfo = listItem.getMetaInformation();
			if(metaInfo != null) {
				writer.write( metaInfo);
			}
	
			//Render leave group
			
			// Show the "remove bookmark link" if the url is set
			String leaveGroup = listItem.getLeaveGroupUrl();
			String title = bundle.getString("flexlist_leave_group");
			if(leaveGroup != null && leaveGroup != "")
			{
				writer.startElement("a", this);
					writer.writeAttribute("href", leaveGroup, null);
					
					writer.startElement("span", this);
						writer.writeAttribute("class", "remove_bookmark", null);
						writer.writeAttribute("title", title, null);
						
					writer.endElement("span");
				writer.endElement("a");
			}
		
		writer.endElement("div");
	}
}