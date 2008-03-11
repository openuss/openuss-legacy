package org.openuss.web.mail.groups;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.groups.GroupNewsletterService;
import org.openuss.newsletter.MailDetail;
import org.openuss.newsletter.MailInfo;
import org.openuss.newsletter.MailingStatus;
import org.openuss.newsletter.NewsletterInfo;
import org.openuss.web.Constants;
import org.openuss.web.groups.components.AbstractGroupPage;

/**
 * 
 * @author Ingo Dueppe
 * @author Lutz D. kramer
 *
 */
@Bean(name = "views$secured$newsletter$groups$showmail", scope = Scope.REQUEST)
@View
public class ShowMailPage extends AbstractGroupPage {
	
	@Property(value = "#{groupNewsletterService}")
	protected GroupNewsletterService groupNewsletterService;
	
	@Property(value = "#{" + Constants.NEWSLETTER_MAIL + "}")
	protected MailDetail mail;
	
	@Property(value = "#{" + Constants.NEWSLETTER_NEWSLETTER + "}")
	protected NewsletterInfo newsletter;

	
	@Prerender
	public void prerender() throws Exception { // NOPMD by devopenuss on 11.03.08 14:27
		 // NOPMD by devopenuss on 11.03.08 14:43
		if (mail==null){
			addError(i18n("newsletter_mailaccess_impossible")); // NOPMD by devopenuss on 11.03.08 14:28
			redirect(Constants.NEWSLETTER_MAIN); // NOPMD by devopenuss on 11.03.08 14:43
			return;
		}			
		if (mail.getId() == null) {
			addError(i18n("newsletter_mailaccess_impossible"));
			redirect(Constants.NEWSLETTER_MAIN);
			return;
		}
		if (mail.getId()!=null){
			MailInfo mi = new MailInfo();  // NOPMD by devopenuss on 11.03.08 14:28
			mi.setId(mail.getId()); // NOPMD by devopenuss on 11.03.08 14:43
			mail = getGroupNewsletterService().getMail(mi);
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
			
			BreadCrumb newCrumb = new BreadCrumb();
			newCrumb.setName(mail.getSubject());
			
			breadcrumbs.loadGroupCrumbs(groupInfo);
			breadcrumbs.addCrumb(newCrumb);
			
		}
	}


	public GroupNewsletterService getGroupNewsletterService() {
		return groupNewsletterService;
	}


	public void setGroupNewsletterService(
			GroupNewsletterService groupNewsletterService) {
		this.groupNewsletterService = groupNewsletterService;
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
