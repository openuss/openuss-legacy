package org.openuss.web.mail;

import java.util.Date;
import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.mailinglist.SubscriberInfo;
import org.openuss.security.User;
import org.openuss.web.Constants;

@Bean(name = "views$secured$mailinglist$mailinglistsubscribers", scope = Scope.REQUEST)
@View
public class MailingListSubscribersPage extends AbstractMailingListPage{
	
	private SubscriberDataProvider data = new SubscriberDataProvider();
	
	@Property(value= "#{"+Constants.USER+"}")
	private User user;
	
	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		mailingList = getEnrollmentMailingListService().getMailingList(enrollmentInfo);
		setSessionBean(Constants.MAILINGLIST_MAILINGLIST, mailingList);
	}	
	
	private class SubscriberDataProvider extends AbstractPagedTable<SubscriberInfo> {


		private static final long serialVersionUID = -6827145859288709001L;

		private DataPage<SubscriberInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<SubscriberInfo> getDataPage(int startRow, int pageSize) {		
			List<SubscriberInfo> al = enrollmentMailingListService.getSubscribers(enrollmentInfo);			
			page = new DataPage<SubscriberInfo>(al.size(),0,al);
			return page;
		}
	}

	public String showProfile() {
		SubscriberInfo subscriberInfo = data.getRowData();
		User user = User.Factory.newInstance();
		user.setId(subscriberInfo.getUserId());
		setSessionBean("showuser", user);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}
	
	public String removeSubscriber(){
		User user = User.Factory.newInstance();
		user.setId(data.getRowData().getUserId());
		getEnrollmentMailingListService().unsubscribe(enrollmentInfo, user);
		 return Constants.SUCCESS;
	}
	

	public SubscriberDataProvider getData() {
		return data;
	}

	public void setData(SubscriberDataProvider data) {
		this.data = data;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	
}