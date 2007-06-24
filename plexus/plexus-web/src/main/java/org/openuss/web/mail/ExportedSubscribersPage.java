package org.openuss.web.mail;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;

/**
 * @author Ingo Dueppe
 */
@Bean(name = "views$secured$mailinglist$exportedsubscribers", scope = Scope.REQUEST)
@View
public class ExportedSubscribersPage extends AbstractMailingListPage {

	private String subscribersSemicolon;

	private String subscribersComma;

	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		mailingList = getCourseMailingListService().getMailingList(courseInfo);
		setSessionBean(Constants.MAILINGLIST_MAILINGLIST, mailingList);
		setSubscribersSemicolon(getCourseMailingListService().exportSubscribers(courseInfo));
		setSubscribersComma(getSubscribersSemicolon().replace(';', ','));
		setSessionBean(Constants.BREADCRUMBS, crumbs);
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("mailinglist_subscribertable_header"));
		crumb.setHint(i18n("mailinglist_subscribertable_header"));
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
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