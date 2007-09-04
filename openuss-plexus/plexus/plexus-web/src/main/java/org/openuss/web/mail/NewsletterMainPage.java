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
import org.openuss.newsletter.MailDetail;
import org.openuss.newsletter.MailInfo;
import org.openuss.newsletter.MailingStatus;
import org.openuss.security.User;
import org.openuss.web.Constants;

/**
 * 
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 *
 */
@Bean(name = "views$secured$newsletter$newsletter", scope = Scope.REQUEST)
@View
public class NewsletterMainPage extends AbstractNewsletterPage{
	
	private NewsletterDataProvider data = new NewsletterDataProvider();
	
	@Property(value= "#{"+Constants.USER+"}")
	private User user;
	
	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		newsletter = getCourseNewsletterService().getNewsletter(courseInfo);
		setSessionBean(Constants.NEWSLETTER_NEWSLETTER, newsletter);
		//remove last element, beccause current page is main
		setRequestBean(Constants.BREADCRUMBS, crumbs);
	}	
	
	private class NewsletterDataProvider extends AbstractPagedTable<MailInfo> {

		private static final long serialVersionUID = -2157142740537022537L;
		
		private DataPage<MailInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<MailInfo> getDataPage(int startRow, int pageSize) {		
			List<MailInfo> al = courseNewsletterService.getMails(courseInfo);			
			sort(al);
			page = new DataPage<MailInfo>(al.size(),0,al);
			return page;
		}
	}

	public String newMail(){
		MailDetail md = new MailDetail();
		md.setSendDate(new Date(System.currentTimeMillis()));
		setSessionBean(Constants.NEWSLETTER_MAIL, md);
		return Constants.NEWSLETTER_NEWMAIL;
	}
	
	public String delMail(){
		MailInfo mi = data.getRowData();		
		getCourseNewsletterService().deleteMail(courseInfo, getCourseNewsletterService().getMail(mi));
		this.mail = new MailDetail();
		return Constants.SUCCESS;
	}
	
	public String changeMail(){	
		MailInfo mi = data.getRowData();
		MailDetail md = getCourseNewsletterService().getMail(mi);
		md.setStatus(MailingStatus.DRAFT);
		getCourseNewsletterService().updateMail(courseInfo, md);
		setSessionBean(Constants.NEWSLETTER_MAIL, md);
		return Constants.NEWSLETTER_NEWMAIL;
	}
	
	public String toggleNewsletterStatus(){
		if (getNewsletter().isSubscribed()){
			getCourseNewsletterService().unsubscribe(courseInfo, user);			
		} else if (!getNewsletter().isSubscribed()){
			getCourseNewsletterService().subscribe(courseInfo, user);
		}
		return Constants.SUCCESS;
	}
	
	public String sendMail(){
		MailInfo mi = data.getRowData();
		MailDetail md = getCourseNewsletterService().getMail(mi);
		this.mail = md;
		setSessionBean(Constants.NEWSLETTER_MAIL, md);
		getCourseNewsletterService().sendMail(courseInfo, getMail());
		return Constants.SUCCESS;
	}
	
	public String showMail(){
		MailInfo mi = data.getRowData();
		MailDetail md = getCourseNewsletterService().getMail(mi);		
		this.mail = md;		
		setSessionBean(Constants.NEWSLETTER_MAIL, md);
		return Constants.NEWSLETTER_SHOWMAIL;
	}
	
	public String sendPlannedMailNow(){
		MailInfo mi = data.getRowData();
		MailDetail md = getCourseNewsletterService().getMail(mi);		
		md.setSendDate(new Date(System.currentTimeMillis()));
		getCourseNewsletterService().updateMail(courseInfo, md);
		return Constants.NEWSLETTER_MAIN;
		
	}
	
	public String stopMail(){
		MailInfo mailInfo = data.getRowData();
		getCourseNewsletterService().cancelSending(mailInfo);
		return Constants.NEWSLETTER_MAIN;
	}
	
	public String listSubscribers(){		
		return Constants.NEWSLETTER_SUBSCRIBERS;
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