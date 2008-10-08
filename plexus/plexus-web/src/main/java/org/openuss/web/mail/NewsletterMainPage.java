package org.openuss.web.mail;

import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.newsletter.MailDetail;
import org.openuss.newsletter.MailInfo;
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
	
	
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		if (isRedirected()){
			return;
		}
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
		setBean(Constants.NEWSLETTER_MAIL, md);
		return Constants.NEWSLETTER_NEWMAIL;
	}
	
	public String delMail(){
		MailInfo mi = data.getRowData();		
		getCourseNewsletterService().deleteMail(courseInfo, getCourseNewsletterService().getMail(mi));
		this.mail = new MailDetail();		
		addMessage(i18n("newsletter_mail_delete_successful"));		
		setBean(Constants.NEWSLETTER_MAIL, mail);
		return Constants.SUCCESS;
	}
	
	public String changeMail(){	
		MailInfo mi = data.getRowData();
		MailDetail md = getCourseNewsletterService().getMail(mi);
		setBean(Constants.NEWSLETTER_MAIL, md);
		return Constants.NEWSLETTER_NEWMAIL;
	}
	
	public void toggleNewsletterStatus(ActionEvent action){
		if (getNewsletter().isSubscribed()){
			getCourseNewsletterService().unsubscribe(courseInfo, user);	
			addMessage(i18n("newsletter_unsubscribe_success"));
		} else {
			getCourseNewsletterService().subscribe(courseInfo, user);
			addMessage(i18n("newsletter_subscribe_success"));
		}
	}
	
	public String sendMail(){
		getCourseNewsletterService().sendMail(courseInfo, getCourseNewsletterService().getMail(data.getRowData()));
		addMessage(i18n("newsletter_draft_sent"));	
		return Constants.SUCCESS;
	}
	
	public String showMail(){
		MailInfo mi = data.getRowData();
		mail = getCourseNewsletterService().getMail(mi);		
		setBean(Constants.NEWSLETTER_MAIL, mail);
		return Constants.NEWSLETTER_SHOWMAIL;
	}
	
	public String sendPlannedMailNow(){
		MailInfo mi = data.getRowData();
		MailDetail md = getCourseNewsletterService().getMail(mi);		
		md.setSendDate(new Date(System.currentTimeMillis()));
		getCourseNewsletterService().updateMail(courseInfo, md);
		addMessage(i18n("newsletter_planned_now_sent"));		
		return Constants.NEWSLETTER_MAIN;
		
	}
	
	public String stopMail(){
		MailInfo mailInfo = data.getRowData();
		getCourseNewsletterService().cancelSending(mailInfo);
		addMessage(i18n("newsletter_stop"));		
		return Constants.NEWSLETTER_MAIN;
	}
	
	public String listSubscribers(){		
		return Constants.NEWSLETTER_SUBSCRIBERS;
	}
	
	public NewsletterDataProvider getData() {
		return data;
	}

	public void setData(NewsletterDataProvider data) {
		this.data = data;
	}

}