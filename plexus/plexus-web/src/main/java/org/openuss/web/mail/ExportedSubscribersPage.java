package org.openuss.web.mail;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;

/**
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$newsletter$exportedsubscribers", scope = Scope.REQUEST)
@View
public class ExportedSubscribersPage extends AbstractNewsletterPage {

	private String subscribersSemicolon;

	private String subscribersComma;

	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (isRedirected()){
			return;
		}
		setSubscribersSemicolon(getCourseNewsletterService().exportSubscribers(courseInfo));
		setSubscribersComma(getSubscribersSemicolon().replace(';', ','));
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("newsletter_subscribertable_header"));
		crumb.setHint(i18n("newsletter_subscribertable_header"));
		breadcrumbs.addCrumb(crumb);
	}

	public String getSubscribersSemicolon() {
		return subscribersSemicolon;
	}

	public void setSubscribersSemicolon(String subscribers1) {
		this.subscribersSemicolon = subscribers1;
	}

	public String getSubscribersComma() {
		return subscribersComma;
	}

	public void setSubscribersComma(String subscribers2) {
		this.subscribersComma = subscribers2;
	}

}