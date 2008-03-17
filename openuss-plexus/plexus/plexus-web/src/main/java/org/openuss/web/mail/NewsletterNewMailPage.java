package org.openuss.web.mail;

import java.util.Date;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.newsletter.MailDetail;
import org.openuss.newsletter.MailInfo;
import org.openuss.newsletter.MailingStatus;
import org.openuss.web.Constants;

/**
 * 
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 *
 */
@Bean(name = "views$secured$newsletter$newmail", scope = Scope.REQUEST)
@View
public class NewsletterNewMailPage extends AbstractNewsletterPage{
	
	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		if (isRedirected()){
			return;
		}
		if (mail!=null && mail.getId()!=null){
			MailInfo mi = new MailInfo();
			mi.setId(mail.getId());
			mail =  getCourseNewsletterService().getMail(mi);
			if (mail == null){
				mail = new MailDetail();
			}
			mail.setStatus(MailingStatus.DRAFT);
			setBean(Constants.NEWSLETTER_MAIL, mail);
		}
		if (mail!=null && mail.getId()==null){
			mail.setSendDate(new Date(System.currentTimeMillis()));
			setBean(Constants.NEWSLETTER_MAIL, mail);
		}
		addPageCrumb();
	}	
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("newsletter_newmail_header"));
		crumb.setHint(i18n("newsletter_newmail_header"));
		breadcrumbs.addCrumb(crumb);
	}

	public String saveDraft(){
		getCourseNewsletterService().updateMail(courseInfo, mail);
		return Constants.NEWSLETTER_MAIN;
	}
	
	public String send(){
		getCourseNewsletterService().sendMail(courseInfo, mail);
		return Constants.NEWSLETTER_MAIN;
	}
	
	public String sendDraft(){
		addMessage(i18n("newsletter_draft_send_message", getUser().getEmail()));
		getCourseNewsletterService().sendPreview(courseInfo, mail);
		return Constants.SUCCESS;	
	}
}