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
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.security.User;
import org.openuss.web.Constants;
import org.openuss.web.BasePage;

/**
 * 
 * @author Thomas Jansing
 * @author Ralf Plattfaut
 *
 */
@Bean(name = "views$secured$buddylist$edittags", scope = Scope.REQUEST)
@View
public class EditTagsPage extends BasePage {
	@Property(value= "#{"+Constants.SHOW_USER_PROFILE+"}")
	public User profile;
	
	private String newTag = "";
	
	private BuddyTagsDataProvider buddyTags = new BuddyTagsDataProvider();
	
	private static final Logger logger = Logger.getLogger(EditTagsPage.class);
	
	@Property(value = "#{buddyService}")
	private BuddyService buddyService;
	
	@Property(value= "#{"+Constants.OPENUSS4US_CHOSEN_BUDDYINFO+"}")
	private BuddyInfo buddyInfo;
	
	private List<String> usedTags = new ArrayList<String>();
	
	public BuddyInfo getBuddyInfo() {
		return buddyInfo;
	}

	public void setBuddyInfo(BuddyInfo buddyInfo) {
		this.buddyInfo = buddyInfo;
	}

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
	
	public String addTag(){
		logger.debug("Add tag '" + newTag + "' to " + buddyInfo.getName());
		try {
			buddyService.addTag(buddyInfo, newTag);
			addMessage(i18n("openuss4us_message_buddylist_tag_add"));
		} catch (BuddyApplicationException e) {
			addError(i18n("openuss4us_error_buddylist_tag_add"));
		}
		return Constants.OPENUSS4US_BUDDYLIST;
	}

	
	public String deleteTag(){
		String tag = this.buddyTags.getRowData();
		logger.debug("Delete Tag: " + tag);
		buddyService.deleteTag(buddyInfo, tag);
		return Constants.OPENUSS4US_BUDDYLIST;
	}
	
	private class BuddyTagsDataProvider extends AbstractPagedTable<String> {

		private static final long serialVersionUID = -2279124324233684525L;
		
		private DataPage<String> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<String> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<String> al = buddyInfo.getTags();
				page = new DataPage<String>(al.size(),0,al);
			}
			return page;
		}
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

	public String getNewTag() {
		return newTag;
	}

	public void setNewTag(String newTag) {
		this.newTag = newTag;
	}

	public BuddyTagsDataProvider getBuddyTags() {
		return buddyTags;
	}

	public void setBuddyTags(BuddyTagsDataProvider buddyTags) {
		this.buddyTags = buddyTags;
	}

	public List<String> getUsedTags() {
		return usedTags;
	}

	public void setUsedTags(List<String> usedTags) {
		this.usedTags = usedTags;
	}
	
}
