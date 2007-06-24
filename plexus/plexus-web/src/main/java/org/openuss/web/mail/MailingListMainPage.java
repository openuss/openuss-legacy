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
import org.openuss.mailinglist.MailingStatus;
import org.openuss.security.User;
import org.openuss.web.Constants;

/**
 * 
 * @author Ingo Dueppe
 *
 */
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
		mailingList = getCourseMailingListService().getMailingList(courseInfo);
		setSessionBean(Constants.MAILINGLIST_MAILINGLIST, mailingList);
		//remove last element, beccause current page is main
		crumbs.remove(crumbs.size()-1);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}	
	
	private class MailingListDataProvider extends AbstractPagedTable<MailInfo> {

		private static final long serialVersionUID = -2157142740537022537L;
		
		private DataPage<MailInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<MailInfo> getDataPage(int startRow, int pageSize) {		
			List<MailInfo> al = courseMailingListService.getMails(courseInfo);			
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
		getCourseMailingListService().deleteMail(courseInfo, getCourseMailingListService().getMail(mi));
		this.mail = new MailDetail();
		return Constants.SUCCESS;
	}
	
	public String changeMail(){	
		MailInfo mi = data.getRowData();
		MailDetail md = getCourseMailingListService().getMail(mi);
		md.setStatus(MailingStatus.DRAFT);
		getCourseMailingListService().updateMail(courseInfo, md);
		setSessionBean(Constants.MAILINGLIST_MAIL, md);
		return Constants.MAILINGLIST_NEWMAIL;
	}
	
	public String toggleMailingListStatus(){
		if (getMailingList().isSubscribed()){
			getCourseMailingListService().unsubscribe(courseInfo, user);			
		} else if (!getMailingList().isSubscribed()){
			getCourseMailingListService().subscribe(courseInfo, user);
		}
		return Constants.SUCCESS;
	}
	
	public String sendMail(){
		MailInfo mi = data.getRowData();
		MailDetail md = getCourseMailingListService().getMail(mi);
		this.mail = md;
		setSessionBean(Constants.MAILINGLIST_MAIL, md);
		getCourseMailingListService().sendMail(courseInfo, getMail());
		return Constants.SUCCESS;
	}
	
	public String showMail(){
		MailInfo mi = data.getRowData();
		MailDetail md = getCourseMailingListService().getMail(mi);		
		this.mail = md;		
		setSessionBean(Constants.MAILINGLIST_MAIL, md);
		return Constants.MAILINGLIST_SHOWMAIL;
	}
	
	public String sendPlannedMailNow(){
		MailInfo mi = data.getRowData();
		MailDetail md = getCourseMailingListService().getMail(mi);		
		md.setSendDate(new Date(System.currentTimeMillis()));
		getCourseMailingListService().updateMail(courseInfo, md);
		return Constants.MAILINGLIST_MAIN;
		
	}
	
	public String stopMail(){
		MailInfo mailInfo = data.getRowData();
		getCourseMailingListService().cancelSending(mailInfo);
		return Constants.MAILINGLIST_MAIN;
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