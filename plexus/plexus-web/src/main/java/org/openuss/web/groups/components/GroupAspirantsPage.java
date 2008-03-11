package org.openuss.web.groups.components;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.calendar.CalendarApplicationException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.groups.UserGroupMemberInfo;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.web.Constants;

/**
 * @author Lutz D. Kramer
 */
@Bean(name = "views$secured$groups$components$groupaspirants", scope = Scope.REQUEST)
@View
public class GroupAspirantsPage extends AbstractGroupPage {

	private static final Logger logger = Logger
			.getLogger(GroupAspirantsPage.class);

	@Property(value = "#{securityService}")
	private SecurityService securityService;
	
	private GroupsDataProvider data = new GroupsDataProvider();
	private DataPage<UserGroupMemberInfo> page;
	List<UserGroupMemberInfo> members; // NOPMD by devopenuss on 11.03.08 14:24
	
	

	/* ----- private classes ----- */

	private class GroupsDataProvider extends AbstractPagedTable<UserGroupMemberInfo> {

		private static final long serialVersionUID = -5342817757466323535L;

		@Override
		public DataPage<UserGroupMemberInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				logger.debug("fetching group list");
				List<UserGroupMemberInfo> aspirants = groupService.getAspirants(groupInfo);
				page = new DataPage<UserGroupMemberInfo>(aspirants.size(), 0, aspirants);
				sort(aspirants);
			}
			return page;
		}
	}

	/* ----- business logic ----- */

	public String linkProfile() {
		User profile = User.Factory.newInstance();
		profile.setId(this.data.getRowData().getUserId());
		setSessionAttribute(Constants.SHOW_USER_PROFILE, profile);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}

	public String accept() throws CalendarApplicationException {
		UserGroupMemberInfo member = data.getRowData();
		groupService.acceptAspirant(groupInfo, member.getUserId());
		if (getSecurityService().getUser(member.getUserId()).getProfile().isSubscribeCalenderEntries()){
			calendarService.addSubscriptionForUser(calendarInfo, member.getUserId());
		}
		return Constants.SUCCESS;	
	}
	
	public String reject(){
		UserGroupMemberInfo member = data.getRowData();
		groupService.rejectAspirant(groupInfo, member.getUserId());
		return Constants.SUCCESS;
	}
	
	/* ----- getter and setter ----- */

	public GroupsDataProvider getData() {
		return data;
	}

	public void setData(GroupsDataProvider data) {
		this.data = data;
	}

	public DataPage<UserGroupMemberInfo> getPage() {
		return page;
	}

	public void setPage(DataPage<UserGroupMemberInfo> page) {
		this.page = page;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

}
