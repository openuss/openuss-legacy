package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;

@Bean(name = "views$secured$enrollment$mailinglist", scope = Scope.REQUEST)
@View
public class MailingListPage extends AbstractEnrollmentPage {
	private static final Logger logger = Logger.getLogger(EnrollmentAspirantsPage.class);
	
	private MailingListDataProvider data = new MailingListDataProvider();
	
	public String delete() {
		return Constants.SUCCESS;
	}

	public String send() {
		return Constants.SUCCESS;
	}

	public String changeStatus(){
		return Constants.SUCCESS;
	}
		
	private class MailingListDataProvider extends AbstractPagedTable<UserInfo> {

		private DataPage<UserInfo> page; 
		
		@Override 
		public DataPage<UserInfo> getDataPage(int startRow, int pageSize) {
			ArrayList<UserInfo> al = new ArrayList<UserInfo>();			
			
			UserInfo ui1 = new UserInfo(new Long(1234), "cag", "Sebastian", "Roekens", "abc123", "plexus@openuss-plexus.com", true, false, false, new Date(System.currentTimeMillis()));
			UserInfo ui2 = new UserInfo(new Long(12345), "dueppe", "Ingo", "Düppe", "12345", "plexus@openuss-plexus.com", true, true, false, new Date(System.currentTimeMillis()));
			UserInfo ui3 = new UserInfo(new Long(1111), "bundy", "Al", "Bundy", "dumpfbacke", "plexus@openuss-plexus.com", true, false, true, new Date(System.currentTimeMillis()));
			al.add(ui1); al.add(ui2); al.add(ui3);
			page = new DataPage<UserInfo>(al.size(),0,al);
			return page;
		}
	}
	


}
