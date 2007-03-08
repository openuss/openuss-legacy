package org.openuss.web.lecture; 

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.UserInfo;

@Bean(name = "views$secured$enrollment$braincontesttoplist", scope = Scope.REQUEST)
@View
public class BrainContestTopListPage extends AbstractEnrollmentPage{
	private static final Logger logger = Logger.getLogger(BrainContestTopListPage.class);
	
	private BrainContestTopListDataProvider data = new BrainContestTopListDataProvider();

	private class BrainContestTopListDataProvider extends AbstractPagedTable<TopListEntry> {

		private DataPage<TopListEntry> page; 
		
		@Override 
		public DataPage<TopListEntry> getDataPage(int startRow, int pageSize) {		
			ArrayList<TopListEntry> al = new ArrayList<TopListEntry>();			
			UserInfo ui1 = new UserInfo(new Long(1234), "cag", "Sebastian", "Roekens", "abc123", "plexus@openuss-plexus.com", true, false, false, new Date(System.currentTimeMillis()));
			UserInfo ui2 = new UserInfo(new Long(12345), "dueppe", "Ingo", "Düppe", "12345", "plexus@openuss-plexus.com", true, true, false, new Date(System.currentTimeMillis()));
			UserInfo ui3 = new UserInfo(new Long(1111), "bundy", "Al", "Bundy", "dumpfbacke", "plexus@openuss-plexus.com", true, false, true, new Date(System.currentTimeMillis()));
			TopListEntry tle1 = new TopListEntry(1,new Date(System.currentTimeMillis()), ui1);
			TopListEntry tle2 = new TopListEntry(2,new Date(System.currentTimeMillis()), ui2);
			TopListEntry tle3 = new TopListEntry(3,new Date(System.currentTimeMillis()), ui3);
			al.add(tle1); al.add(tle2); al.add(tle3);  
			page = new DataPage<TopListEntry>(al.size(),0,al);
			return page;
		}
	}

	public BrainContestTopListDataProvider getData() {
		return data;
	}

	public void setData(BrainContestTopListDataProvider data) {
		this.data = data;
	}
	
}