package org.openuss.web.mail;

import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.newsletter.SubscriberInfo;
import org.openuss.security.User;
import org.openuss.web.Constants;

/**
 * 
 * @author Ingo Dueppe
 *
 */
@Bean(name = "views$secured$newsletter$newslettersubscribers", scope = Scope.REQUEST)
@View
public class NewsletterSubscribersPage extends AbstractNewsletterPage{
	
	private SubscriberDataProvider data = new SubscriberDataProvider();
	
	@Property(value= "#{"+Constants.USER+"}")
	private User user;
	
	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		newsletter = getCourseNewsletterService().getNewsletter(courseInfo);
		setSessionBean(Constants.NEWSLETTER_NEWSLETTER, newsletter);
		addPageCrumb();
	}	
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("newsletter_subscribertable_header"));
		crumb.setHint(i18n("newsletter_subscribertable_header"));
		breadcrumbs.addCrumb(crumb);
	}
	
	private class SubscriberDataProvider extends AbstractPagedTable<SubscriberInfo> {


		private static final long serialVersionUID = -6827145859288709001L;

		private DataPage<SubscriberInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<SubscriberInfo> getDataPage(int startRow, int pageSize) {		
			List<SubscriberInfo> al = courseNewsletterService.getSubscribers(courseInfo);			
			page = new DataPage<SubscriberInfo>(al.size(),0,al);
			return page;
		}
	}

	public String showProfile() {
		SubscriberInfo subscriberInfo = data.getRowData();
		User user = User.Factory.newInstance();
		user.setId(subscriberInfo.getUserId());
		setSessionBean("showuser", user);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}
	
	public String removeSubscriber(){
		User user = User.Factory.newInstance();
		user.setId(data.getRowData().getUserId());
		getCourseNewsletterService().unsubscribe(courseInfo, user);
		 return Constants.SUCCESS;
	}
	
	public String exportSubscribers(){
		return Constants.NEWSLETTER_EXPORT;
	}

	public SubscriberDataProvider getData() {
		return data;
	}

	public void setData(SubscriberDataProvider data) {
		this.data = data;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	
}