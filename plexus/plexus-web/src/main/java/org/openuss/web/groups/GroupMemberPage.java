package org.openuss.web.groups;

import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.groups.GroupMemberInfo;
import org.openuss.security.User;
import org.openuss.web.Constants;


/**
 * 
 * @author Lutz D. Kramer
 *
 */
@Bean(name = "views$secured$course$courseparticipants", scope = Scope.REQUEST)
@View
public class GroupMemberPage extends AbstractGroupPage {
	
	private static final Logger logger = Logger.getLogger(GroupMemberPage.class);

	private ParticipantDataProvider data = new ParticipantDataProvider();

	public String save() {
		logger.debug("Group member page - saved");
		return Constants.SUCCESS;
	}

	public void changedMember(ValueChangeEvent event){
		logger.debug("changed group members");
	}

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumb();
	}
	
	//TODO - Lutz: Properties anpassen
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("group_command_options_participants"));
		crumb.setHint(i18n("group_command_options_participants"));
		breadcrumbs.addCrumb(crumb);
	}
	
	private class ParticipantDataProvider extends AbstractPagedTable<GroupMemberInfo> {

		private static final long serialVersionUID = -1918372320518667092L;
		
		private DataPage<GroupMemberInfo> page;

		@Override
		public DataPage<GroupMemberInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<GroupMemberInfo> member = groupService.getMember(groupInfo);
				page = new DataPage<GroupMemberInfo>(member.size(), 0, member);
				sort(member);
			}
			return page;
		}
	}

	public String showProfile() {
		GroupMemberInfo member = data.getRowData();
		User user = User.Factory.newInstance();
		user.setId(member.getUserId());
		setSessionBean(Constants.SHOW_USER_PROFILE, user);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}

	public String delete() {
		logger.info("group member deleted");
		GroupMemberInfo member = data.getRowData();
		groupService.removeMember(member.getId());
		// TODO - Lutz: Properties anpassen
		addMessage(i18n("message_group_removed_participant",member.getUserName()));
		return Constants.SUCCESS;
	}

	// ----------------------- PROPERTIES ---------------------------------

	public ParticipantDataProvider getData() {
		return data;
	}

	public void setData(ParticipantDataProvider data) {
		this.data = data;
	}

}
