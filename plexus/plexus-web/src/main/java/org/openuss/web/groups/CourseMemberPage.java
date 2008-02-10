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
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;


/**
 * 
 * @author Lutz D. Kramer
 *
 */
@Bean(name = "views$secured$course$courseparticipants", scope = Scope.REQUEST)
@View
public class CourseMemberPage extends AbstractGroupPage {
	
	private static final Logger logger = Logger.getLogger(CourseMemberPage.class);

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
	
	private class ParticipantDataProvider extends AbstractPagedTable<UserInfo> {

		private static final long serialVersionUID = -1918372320518667092L;
		
		private DataPage<UserInfo> page;

		@Override
		public DataPage<UserInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<UserInfo> member = groupService.getMembers(groupInfo);
				page = new DataPage<UserInfo>(member.size(), 0, member);
				sort(member);
			}
			return page;
		}
	}

	public String showProfile() {
		UserInfo member = data.getRowData();
		User user = User.Factory.newInstance();
		user.setId(member.getId());
		setSessionBean(Constants.SHOW_USER_PROFILE, user);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}

	public String delete() {
		logger.info("group member deleted");
		UserInfo member = data.getRowData();
		groupService.removeMember(groupInfo, member.getId());
		// TODO - Lutz: Properties anpassen
		addMessage(i18n("message_group_removed_participant",member.getUsername()));
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
