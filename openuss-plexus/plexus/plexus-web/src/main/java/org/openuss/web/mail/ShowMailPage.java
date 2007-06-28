package org.openuss.web.mail;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.course.newsletter.CourseNewsletterService;
import org.openuss.newsletter.MailDetail;
import org.openuss.newsletter.MailInfo;
import org.openuss.newsletter.NewsletterInfo;
import org.openuss.newsletter.MailingStatus;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * @author Ingo Dueppe
 *
 */
@Bean(name = "views$secured$newsletter$showmail", scope = Scope.REQUEST)
@View
public class ShowMailPage extends BasePage{
	
	@Property(value = "#{courseNewsletterService}")
	protected CourseNewsletterService courseNewsletterService;
	
	@Property(value = "#{" + Constants.NEWSLETTER_MAIL + "}")
	protected MailDetail mail;
	
	@Property(value = "#{" + Constants.NEWSLETTER_NEWSLETTER + "}")
	protected NewsletterInfo newsletter;

	
	@Prerender
	public void prerender() throws Exception {
		if (mail==null){
			addError(i18n("newsletter_mailaccess_impossible"));
			redirect(Constants.NEWSLETTER_MAIN);
			return;
		}			
		if (mail.getId() == null) {
			addError(i18n("newsletter_mailaccess_impossible"));
			redirect(Constants.NEWSLETTER_MAIN);
			return;
		}
		if (mail.getId()!=null){
			MailInfo mi = new MailInfo(); 
			mi.setId(mail.getId());
			mail = getCourseNewsletterService().getMail(mi);
			if (mail==null){
				addError(i18n("newsletter_mailaccess_impossible"));
				redirect(Constants.NEWSLETTER_MAIN);
				return;
			}
			
			if (mail.getStatus()==MailingStatus.DELETED){
				addError(i18n("newsletter_mailaccess_impossible"));
				redirect(Constants.NEWSLETTER_MAIN);
				return;
			}
			setSessionBean(Constants.NEWSLETTER_MAIL, mail);
			crumbs.clear();
		}
	}


	public CourseNewsletterService getCourseNewsletterService() {
		return courseNewsletterService;
	}


	public void setCourseNewsletterService(
			CourseNewsletterService courseNewsletterService) {
		this.courseNewsletterService = courseNewsletterService;
	}


	public MailDetail getMail() {
		return mail;
	}


	public void setMail(MailDetail mail) {
		this.mail = mail;
	}


	public NewsletterInfo getNewsletter() {
		return newsletter;
	}


	public void setNewsletter(NewsletterInfo newsletter) {
		this.newsletter = newsletter;
	}	
}
