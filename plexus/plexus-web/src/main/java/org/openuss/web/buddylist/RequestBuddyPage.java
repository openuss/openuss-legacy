package org.openuss.web.buddylist;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.buddylist.BuddyInfo;
import org.openuss.buddylist.BuddyService;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.User;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;


@Bean(name = "views$secured$buddylist$buddyrequest", scope = Scope.REQUEST)
@View
public class RequestBuddyPage extends BasePage {
	@Property(value= "#{"+Constants.SHOW_USER_PROFILE+"}")
	public User profile;
	

	@Property(value= "#{"+Constants.OPENUSS4US_CHOSEN_BUDDYINFO+"}")
	private BuddyInfo buddyInfo;
	
	private static final Logger logger = Logger.getLogger(RequestBuddyPage.class);
	
	private RequesttableDataProvider data = new RequesttableDataProvider();
	
	@Property(value = "#{buddyService}")
	private BuddyService buddyService;
	
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		addPageCrumb();
	}

		
	private void addPageCrumb() {
		breadcrumbs.loadBaseCrumbs();
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink(Constants.OPENUSS4US_BUDDYLIST);
		crumb.setName(i18n("openuss4us_command_buddylist"));
		crumb.setHint(i18n("openuss4us_command_buddylist"));
		breadcrumbs.addCrumb(crumb);
		BreadCrumb crumb2 = new BreadCrumb();
		crumb2.setName(i18n("openuss4us_command_buddylist_request"));
		crumb2.setHint(i18n("openuss4us_command_buddylist_request"));
		breadcrumbs.addCrumb(crumb2);
	}	

	private class RequesttableDataProvider extends AbstractPagedTable<BuddyInfo> {

		private static final long serialVersionUID = -2279124328223684525L;
		
		private DataPage<BuddyInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<BuddyInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<BuddyInfo> al = buddyService.getAllOpenRequests();
				sort(al);
				page = new DataPage<BuddyInfo>(al.size(),0,al);
			}
			return page;
		}
	}
	
	public String authorizeBuddy(){
		setBuddyInfo(this.data.getRowData());
		buddyService.authorizeBuddyRequest(buddyInfo, true);
		addMessage(i18n("openuss4us_message_authorizebuddy"));
		return Constants.OPENUSS4US_BUDDYLIST;
	}
	
	public String declineBuddy(){
		setBuddyInfo(this.data.getRowData());
		buddyService.authorizeBuddyRequest(buddyInfo, false);
		addMessage(i18n("openuss4us_message_declinebuddy"));
		return Constants.OPENUSS4US_BUDDYLIST;
	}
	
	public String addBuddy(){
		setBuddyInfo(this.data.getRowData());
		if(buddyService.isUserBuddy(buddyInfo.getRequesterId())){
			buddyService.authorizeBuddyRequest(buddyInfo, true);
			addMessage(i18n("openuss4us_message_authorizebuddy_mutual"));
			return Constants.OPENUSS4US_BUDDYLIST;
		}
		buddyService.authorizeBuddyRequest(buddyInfo, true);
		addMessage(i18n("openuss4us_message_authorizebuddy"));
		profile.setId(this.data.getRowData().getRequesterId());
		setSessionAttribute(Constants.SHOW_USER_PROFILE, profile);
		return "openuss4us_addbuddy";
		}
	
	
	
	public RequesttableDataProvider getData() {
		return data;
	}


	public void setData(RequesttableDataProvider data) {
		this.data = data;
	}
	
	public String linkProfile(){
		profile.setId(this.data.getRowData().getRequesterId());
		setSessionAttribute(Constants.SHOW_USER_PROFILE, profile);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}
		
	public void setBuddyService(BuddyService buddyService) {
		this.buddyService = buddyService;
	}

	public User getProfile() {
		return profile;
	}

	public void setProfile(User profile) {
		this.profile = profile;
	}

	public BuddyInfo getBuddyInfo() {
		return buddyInfo;
	}

	public void setBuddyInfo(BuddyInfo buddyInfo) {
		this.buddyInfo = buddyInfo;
	}
	
}
