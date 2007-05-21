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
import org.openuss.mailinglist.MailDetail;
import org.openuss.mailinglist.MailInfo;
import org.openuss.security.User;
import org.openuss.web.Constants;

@Bean(name = "views$secured$mailinglist$mailinglist", scope = Scope.REQUEST)
@View
public class MailingListMainPage extends AbstractMailingListPage{
	
	private MailingListDataProvider data = new MailingListDataProvider();
	
	@Property(value= "#{"+Constants.USER+"}")
	private User user;
	
	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		mailingList = getEnrollmentMailingListService().getMailingList(enrollmentInfo);
		setSessionBean(Constants.MAILINGLIST_MAILINGLIST, mailingList);
	}	
	
	private class MailingListDataProvider extends AbstractPagedTable<MailInfo> {

		private static final long serialVersionUID = -2157142740537022537L;
		
		private DataPage<MailInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<MailInfo> getDataPage(int startRow, int pageSize) {		
			List<MailInfo> al = enrollmentMailingListService.getMails(enrollmentInfo);			
			page = new DataPage<MailInfo>(al.size(),0,al);
			return page;
		}
	}

	public String newMail(){
		MailDetail md = new MailDetail();
		md.setSendDate(new Date(System.currentTimeMillis()));
		setSessionBean(Constants.MAILINGLIST_MAIL, md);
		return Constants.MAILINGLIST_NEWMAIL;
	}
	
	public String delMail(){
		MailInfo mi = data.getRowData();		
		getEnrollmentMailingListService().deleteMail(enrollmentInfo, getEnrollmentMailingListService().getMail(mi));
		return Constants.SUCCESS;
	}
	
	public String changeMail(){	
		MailInfo mi = data.getRowData();
		setSessionBean(Constants.MAILINGLIST_MAIL, getEnrollmentMailingListService().getMail(mi));
		return Constants.MAILINGLIST_NEWMAIL;
	}
	
	public String toggleMailingListStatus(){
		if (getMailingList().isSubscribed()){
			getEnrollmentMailingListService().unsubscribe(enrollmentInfo, user);			
		} else if (!getMailingList().isSubscribed()){
			getEnrollmentMailingListService().subscribe(enrollmentInfo, user);
		}
		return Constants.SUCCESS;
	}
	
	public String sendMail(){
		MailInfo mi = data.getRowData();
		MailDetail md = getEnrollmentMailingListService().getMail(mi);
		this.mail = md;
		setSessionBean(Constants.MAILINGLIST_MAIL, md);
		getEnrollmentMailingListService().sendMail(enrollmentInfo, getMail());
		return Constants.SUCCESS;
	}
	
	public String showMail(){
		MailInfo mi = data.getRowData();
		MailDetail md = getEnrollmentMailingListService().getMail(mi);		
		this.mail = md;		
		setSessionBean(Constants.MAILINGLIST_MAIL, md);
		return Constants.MAILINGLIST_SHOWMAIL;
	}
	
	public String listSubscribers(){		
		return Constants.MAILINGLIST_SUBSCRIBERS;
	}
	
	public String exportSubscribers(){
		//TODO implement me
		return Constants.SUCCESS;
	}
	
	public MailingListDataProvider getData() {
		return data;
	}

	public void setData(MailingListDataProvider data) {
		this.data = data;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}