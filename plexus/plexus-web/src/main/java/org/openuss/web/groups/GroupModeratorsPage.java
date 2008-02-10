package org.openuss.web.groups;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;


/**
 * 
 * @author Lutz D. Kramer
 *
 */
@Bean(name = "views$secured$groups$groupassistants", scope = Scope.REQUEST)
@View
public class GroupModeratorsPage extends AbstractGroupPage {
	
	private static final Logger logger = Logger.getLogger(GroupModeratorsPage.class);

	private ModeratorDataProvider data = new ModeratorDataProvider();

	@Property(value = "#{securityService}")
	protected SecurityService securityService;
	
	private Long userId;

	List<UserInfo> moderators;
	Set<Long> moderatorsUserIds;
	List<SelectItem> instituteMembers;
	
	String username;
	
	private DataPage<UserInfo> page;


	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumb();
	}
	
	// TODO - Lutz: Properties anpassen
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("group_command_options_assistants"));
		crumb.setHint(i18n("group_command_options_assistants"));
		breadcrumbs.addCrumb(crumb);
	}	
	
	private class ModeratorDataProvider extends AbstractPagedTable<UserInfo> {
		
		private static final long serialVersionUID = -5342817757466323535L;

		@Override
		public DataPage<UserInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				logger.debug("fetching group assistant list");
				List<UserInfo> moderators = getModerators();
				page = new DataPage<UserInfo>(moderators.size(), 0, moderators);
				sort(moderators);
			}
			return page;
		}
	}

	private List<UserInfo> getModerators() {
		if (moderators == null) {
			moderators = groupService.getModerators(groupInfo);
		}
		return moderators;
	}

	private Set<Long> getModeratorsUserIdMap() {
		if (moderatorsUserIds == null) {
			moderatorsUserIds = new HashSet<Long>();
			for (UserInfo moderators : getModerators()) {
				moderatorsUserIds.add(moderators.getId());
			}
		}
		return moderatorsUserIds;
	}

	public String showProfile() {
		UserInfo memberInfo = data.getRowData();
		User user = User.Factory.newInstance();
		user.setId(memberInfo.getId());
		setSessionBean("showuser", user);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}

	public String delete() {
		logger.debug("course member deleted");
		UserInfo moderator = data.getRowData();
		groupService.removeModerator(groupInfo, moderator.getId());
		// TODO - Lutz: Properties anpassen
		addMessage(i18n("message_group_removed_assistant", moderator.getUsername()));
		resetCachedData();
		return Constants.SUCCESS;
	}

	public String addModerator() {
		logger.debug("group moderator aspirant added");
		if (userId != null){
			User user = User.Factory.newInstance();
			user.setId(userId);
			groupService.addModerator(groupInfo, user.getId());
			// TODO - Lutz: Properties anpassen
			addMessage(i18n("message_group_add_assistant"));
			resetCachedData();
			userId  = null;
			return Constants.SUCCESS;
		} else {
			return addMember();
		}
	}

	private void resetCachedData() {
		moderators = null;
		moderatorsUserIds = null;
		page = null;
	}

	public String addMember() {
		logger.debug("course assistant aspirant added");
		User user = User.Factory.newInstance();
		if(securityService.getUserByName(username) != null){
		user = securityService.getUserByName(username);	
		} else {
			user = securityService.getUserByEmail(username);
		}
		try {
			groupService.addModerator(groupInfo, user.getId());
			// TODO - Lutz: Properties anpassen
			addMessage(i18n("message_group_add_assistant"));
			resetCachedData();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO - Lutz: Properties anpassen
			addError(i18n("groups_error_apply_member_at_group"));
			return Constants.FAILURE;
		}
		username = "";
		return Constants.SUCCESS;
	}
	
	public String save() {
		logger.debug("Course assistants page - saved");
		return Constants.SUCCESS;
	}

	public void changedModerator(ValueChangeEvent event) {
		logger.debug("changed group moderator");
	}

	public ModeratorDataProvider getData() {
		return data;
	}

	public void setData(ModeratorDataProvider data) {
		this.data = data;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
