package org.openuss.web.buddylist;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.buddylist.*;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.User;
import org.openuss.web.Constants;
import org.openuss.web.BasePage;

/**
 * 
 * @author Thomas Jansing
 * @author Ralf Plattfaut
 *
 */
@Bean(name = "views$secured$buddylist$buddylist", scope = Scope.REQUEST)
@View
public class BuddylistMainPage extends BasePage {
	@Property(value= "#{"+Constants.SHOW_USER_PROFILE+"}")
	public User profile;
	
	@Property(value= "#{"+Constants.OPENUSS4US_CHOSEN_BUDDYINFO+"}")
	private BuddyInfo buddyInfo;
	
	private List<String> usedTags = new ArrayList<String>();
	
	private static final Logger logger = Logger.getLogger(BuddylistMainPage.class);
	
	private BuddytableDataProvider data = new BuddytableDataProvider();
	
	@Property(value = "#{buddyService}")
	private BuddyService buddyService;
	
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		addPageCrumb();
		usedTags = buddyService.getAllUsedTags();
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("openuss4us_command_buddylist"));
		crumb.setHint(i18n("openuss4us_command_buddylist"));
		breadcrumbs.loadOpenuss4usCrumbs();
		breadcrumbs.addCrumb(crumb);
	}	

	private class BuddytableDataProvider extends AbstractPagedTable<BuddyInfo> {

		private static final long serialVersionUID = -2279124328223684525L;
		
		private DataPage<BuddyInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<BuddyInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<BuddyInfo> al = buddyService.getBuddyList();
				sort(al);
				page = new DataPage<BuddyInfo>(al.size(),0,al);
			}
			return page;
		}
	}
	
	public BuddytableDataProvider getData() {
		return data;
	}

	public void setData(BuddytableDataProvider data) {
		this.data = data;
	}
	
	public String linkProfile(){
		profile.setId(this.data.getRowData().getUserId());
		setSessionAttribute(Constants.SHOW_USER_PROFILE, profile);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}
	
	public String linkEditTags(){
		profile.setId(this.data.getRowData().getUserId());
		setSessionAttribute(Constants.SHOW_USER_PROFILE, profile);
		setBuddyInfo(this.data.getRowData());
		setSessionBean(Constants.OPENUSS4US_CHOSEN_BUDDYINFO, buddyInfo);
		return Constants.OPENUSS4US_EDITTAGS;
	}
	
	public String linkDeleteBuddy(){
		profile.setId(this.data.getRowData().getUserId());
		setSessionAttribute(Constants.SHOW_USER_PROFILE, profile);
		setBuddyInfo(this.data.getRowData());
		setSessionBean(Constants.OPENUSS4US_CHOSEN_BUDDYINFO, buddyInfo);
		return Constants.OPENUSS4US_DELETEBUDDY;
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

	public List<String> getUsedTags() {
		return usedTags;
	}

	public void setUsedTags(List<String> usedTags) {
		this.usedTags = usedTags;
	}
	
}