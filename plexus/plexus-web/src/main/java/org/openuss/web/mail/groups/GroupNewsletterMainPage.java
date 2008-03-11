package org.openuss.web.mail.groups;

import java.util.Date;
import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.newsletter.MailDetail;
import org.openuss.newsletter.MailInfo;
import org.openuss.newsletter.MailingStatus;
import org.openuss.security.User;
import org.openuss.web.Constants;

/**
 * 
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 * @author Lutz D. Kramer
 *
 */
@Bean(name = "views$secured$newsletter$groups$newsletter", scope = Scope.REQUEST)
@View
public class GroupNewsletterMainPage extends AbstractGroupNewsletterPage{
	
	private NewsletterDataProvider data = new NewsletterDataProvider();
	
	@Property(value= "#{"+Constants.USER+"}")
	private User user;
	
	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {	 // NOPMD by devopenuss on 11.03.08 14:27
		super.prerender();
		newsletter = getGroupNewsletterService().getNewsletter(groupInfo);
		setSessionBean(Constants.NEWSLETTER_NEWSLETTER, newsletter);
	}	
	
	private class NewsletterDataProvider extends AbstractPagedTable<MailInfo> {

		private static final long serialVersionUID = -2157142740537022537L;
		
		private DataPage<MailInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<MailInfo> getDataPage(int startRow, int pageSize) {		
			List<MailInfo> al = groupNewsletterService.getMails(groupInfo);			 // NOPMD by devopenuss on 11.03.08 14:27
			sort(al);
			page = new DataPage<MailInfo>(al.size(),0,al);
			return page;
		}
	}

	public String newMail(){
		MailDetail md = new MailDetail(); // NOPMD by devopenuss on 11.03.08 14:27
		md.setSendDate(new Date(System.currentTimeMillis()));
		setSessionBean(Constants.NEWSLETTER_MAIL, md);
		return Constants.GROUP_NEWSLETTER_NEWMAIL;
	}
	
	public String delMail(){
		MailInfo mi = data.getRowData();		 // NOPMD by devopenuss on 11.03.08 14:27
		getGroupNewsletterService().deleteMail(groupInfo, getGroupNewsletterService().getMail(mi));
		this.mail = new MailDetail();
		return Constants.SUCCESS;
	}
	
	public String changeMail(){	
		MailInfo mi = data.getRowData(); // NOPMD by devopenuss on 11.03.08 14:27
		MailDetail md = getGroupNewsletterService().getMail(mi); // NOPMD by devopenuss on 11.03.08 14:27
		md.setStatus(MailingStatus.DRAFT);
		getGroupNewsletterService().updateMail(groupInfo, md);
		setSessionBean(Constants.NEWSLETTER_MAIL, md);
		return Constants.GROUP_NEWSLETTER_NEWMAIL;
	}
	
	public String toggleNewsletterStatus(){
		if (getNewsletter().isSubscribed()){
			getGroupNewsletterService().unsubscribe(groupInfo, user);			
		} else if (!getNewsletter().isSubscribed()){
			getGroupNewsletterService().subscribe(groupInfo, user);
		}
		return Constants.SUCCESS;
	}
	
	public String sendMail(){
		MailInfo mi = data.getRowData(); // NOPMD by devopenuss on 11.03.08 14:27
		MailDetail md = getGroupNewsletterService().getMail(mi); // NOPMD by devopenuss on 11.03.08 14:27
		this.mail = md;
		setSessionBean(Constants.NEWSLETTER_MAIL, md);
		getGroupNewsletterService().sendMail(groupInfo, getMail());
		return Constants.SUCCESS;
	}
	
	public String showMail(){
		MailInfo mi = data.getRowData(); // NOPMD by devopenuss on 11.03.08 14:27
		MailDetail md = getGroupNewsletterService().getMail(mi);		 // NOPMD by devopenuss on 11.03.08 14:27
		this.mail = md;		
		setSessionBean(Constants.NEWSLETTER_MAIL, md);
		return Constants.GROUP_NEWSLETTER_SHOWMAIL;
	}
	
	public String sendPlannedMailNow(){
		MailInfo mi = data.getRowData(); // NOPMD by devopenuss on 11.03.08 14:27
		MailDetail md = getGroupNewsletterService().getMail(mi);		 // NOPMD by devopenuss on 11.03.08 14:27
		md.setSendDate(new Date(System.currentTimeMillis()));
		getGroupNewsletterService().updateMail(groupInfo, md);
		return Constants.GROUP_NEWSLETTER_MAIN;
		
	}
	
	public String stopMail(){
		MailInfo mailInfo = data.getRowData();
		getGroupNewsletterService().cancelSending(mailInfo);
		return Constants.GROUP_NEWSLETTER_MAIN;
	}
	
	public String listSubscribers(){		
		return Constants.GROUP_NEWSLETTER_SUBSCRIBERS;
	}
	
	public String exportSubscribers(){
		//TODO implement me
		return Constants.SUCCESS;
	}
	
	public NewsletterDataProvider getData() {
		return data;
	}

	public void setData(NewsletterDataProvider data) {
		this.data = data;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}