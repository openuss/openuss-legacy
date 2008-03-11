package org.openuss.web.mail.groups;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.groups.GroupNewsletterService;
import org.openuss.newsletter.MailDetail;
import org.openuss.newsletter.NewsletterInfo;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.web.groups.components.AbstractGroupPage;

/**
 * AbstractNewsletterPage
 * 
 * @author Sebastian Roekens
 * @author Ingo Dueppe
 * @author Lutz D. Kramer
 */

public class AbstractGroupNewsletterPage extends AbstractGroupPage {

	@Property(value = "#{groupNewsletterService}")
	protected GroupNewsletterService groupNewsletterService;

	@Property(value = "#{" + Constants.NEWSLETTER_MAIL + "}")
	protected MailDetail mail;

	@Property(value = "#{" + Constants.NEWSLETTER_NEWSLETTER + "}")
	protected NewsletterInfo newsletter;

	@Prerender
	public void prerender() throws Exception { // NOPMD by devopenuss on 11.03.08 14:27
		super.prerender();
		addNewsletterCrumb();
	}

	private void addNewsletterCrumb() {
		breadcrumbs.loadGroupCrumbs(groupInfo);

		BreadCrumb newsletterCrumb = new BreadCrumb();
		newsletterCrumb.setHint(i18n("course_command_newsletter"));
		newsletterCrumb.setName(i18n("course_command_newsletter"));
		newsletterCrumb.setLink(PageLinks.GROUP_NEWSLETTER);
		newsletterCrumb.addParameter("group", groupInfo.getId());
		breadcrumbs.addCrumb(newsletterCrumb);
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