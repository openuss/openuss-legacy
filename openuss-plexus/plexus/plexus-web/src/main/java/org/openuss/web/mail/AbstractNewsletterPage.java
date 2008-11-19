package org.openuss.web.mail;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.course.newsletter.CourseNewsletterService;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.newsletter.MailDetail;
import org.openuss.newsletter.NewsletterInfo;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.web.course.AbstractCoursePage;

/**
 * AbstractNewsletterPage
 * 
 * @author Sebastian Roekens
 * @author Ingo Dueppe
 */

public class AbstractNewsletterPage extends AbstractCoursePage {

	@Property(value = "#{courseNewsletterService}")
	protected CourseNewsletterService courseNewsletterService;

	@Property(value = "#{" + Constants.NEWSLETTER_MAIL + "}")
	protected MailDetail mail;

	@Property(value = "#{" + Constants.NEWSLETTER_NEWSLETTER + "}")
	protected NewsletterInfo newsletter;

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (isRedirected()){
			return;
		}
		newsletter = getCourseNewsletterService().getNewsletter(courseInfo);
		setBean(Constants.NEWSLETTER_NEWSLETTER, newsletter);
		addNewsletterCrumb();
	}

	private void addNewsletterCrumb() {
		breadcrumbs.loadCourseCrumbs(courseInfo);
		
		BreadCrumb newsletterCrumb = new BreadCrumb();
		newsletterCrumb.setHint(i18n("course_command_newsletter"));
		newsletterCrumb.setName(i18n("course_command_newsletter"));
		newsletterCrumb.setLink(PageLinks.COURSE_NEWSLETTER);
		newsletterCrumb.addParameter("course", courseInfo.getId());
		breadcrumbs.addCrumb(newsletterCrumb);
	}

	public CourseNewsletterService getCourseNewsletterService() {
		return courseNewsletterService;
	}

	public void setCourseNewsletterService(CourseNewsletterService courseNewsletterService) {
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