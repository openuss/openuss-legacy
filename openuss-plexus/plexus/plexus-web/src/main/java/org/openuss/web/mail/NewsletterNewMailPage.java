package org.openuss.web.mail;

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
 *
 */
@Bean(name = "views$secured$newsletter$newmail", scope = Scope.REQUEST)
@View
public class NewsletterNewMailPage extends AbstractNewsletterPage{
	
	@Property(value= "#{"+Constants.USER+"}")
	private User user;
	
	@Property(value = "#{"+Constants.NEWSLETTER_MAIL+"}")
	private MailDetail mail;
	
	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		newsletter = getCourseNewsletterService().getNewsletter(courseInfo);
		setSessionBean(Constants.NEWSLETTER_NEWSLETTER, newsletter);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
		addPageCrumb();
	}	
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("newsletter_newmail_header"));
		crumb.setHint(i18n("newsletter_newmail_header"));
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
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