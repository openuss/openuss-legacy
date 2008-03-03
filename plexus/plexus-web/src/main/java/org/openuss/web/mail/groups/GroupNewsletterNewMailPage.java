package org.openuss.web.mail.groups;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.newsletter.MailDetail;
import org.openuss.security.User;
import org.openuss.web.Constants;

/**
 * 
 * @author Ingo Dueppe
 * @author Lutz D. Kramer
 *
 */
@Bean(name = "views$secured$newsletter$groups$newmail", scope = Scope.REQUEST)
@View
public class GroupNewsletterNewMailPage extends AbstractGroupNewsletterPage{
	
	@Property(value= "#{"+Constants.USER+"}")
	private User user;
	
	@Property(value = "#{"+Constants.NEWSLETTER_MAIL+"}")
	private MailDetail mail;
	
	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		newsletter = getGroupNewsletterService().getNewsletter(groupInfo);
		setSessionBean(Constants.NEWSLETTER_NEWSLETTER, newsletter);
		addPageCrumb();
	}	
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("newsletter_newmail_header"));
		crumb.setHint(i18n("newsletter_newmail_header"));
		breadcrumbs.addCrumb(crumb);
	}

	public String saveDraft(){
		getGroupNewsletterService().updateMail(groupInfo, mail);
		return Constants.GROUP_NEWSLETTER_MAIN;
	}
	
	public String send(){
		getGroupNewsletterService().sendMail(groupInfo, mail);
		return Constants.GROUP_NEWSLETTER_MAIN;
	}
	
	public String sendDraft(){
		addMessage(i18n("newsletter_draft_send_message", getUser().getEmail()));
		getGroupNewsletterService().sendPreview(groupInfo, mail);
		return Constants.SUCCESS;	
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public MailDetail getMail() {
		return mail;
	}


	public void setMail(MailDetail mail) {
		this.mail = mail;
	}

}