package org.openuss.web.discussion; 

import java.util.ArrayList;
import java.sql.Date;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.enrollment.AbstractEnrollmentPage;


@Bean(name = "views$secured$discussion$discussions", scope = Scope.REQUEST)
@View
public class DiscussionMainPage extends AbstractEnrollmentPage{
	private static final Logger logger = Logger.getLogger(DiscussionMainPage.class);
	
	private DiscussionDataProvider data = new DiscussionDataProvider();
	
	private class DiscussionDataProvider extends AbstractPagedTable<DiscussionTableEntry> {

		private DataPage<DiscussionTableEntry> page; 
		
		@Override 
		public DataPage<DiscussionTableEntry> getDataPage(int startRow, int pageSize) {		
			ArrayList<DiscussionTableEntry> al = new ArrayList<DiscussionTableEntry>();		
			DiscussionTableEntry dte1 = new DiscussionTableEntry(new Boolean(false), "Klausur SS 06 Aufgabe 6", 1, "Düppe", 23, new Date(System.currentTimeMillis()), "Dewanto", 1);
			DiscussionTableEntry dte2 = new DiscussionTableEntry(new Boolean(true), "Was läßt sich rechnen?", 2, "Grob", 40, new Date(System.currentTimeMillis()), "Dewanto", 1);
			DiscussionTableEntry dte3 = new DiscussionTableEntry(new Boolean(false), "Zu Hilfe, Unterlagen", 38, "Dewanto", 442, new Date(System.currentTimeMillis()), "Dewanto", 4);
			al.add(dte1); al.add(dte2); al.add(dte3);
			page = new DataPage<DiscussionTableEntry>(al.size(),0,al);
			return page;
		}
	}
	

	public DiscussionDataProvider getData() {
		return data;
	}


	public void setData(DiscussionDataProvider data) {
		this.data = data;
	}
	
}