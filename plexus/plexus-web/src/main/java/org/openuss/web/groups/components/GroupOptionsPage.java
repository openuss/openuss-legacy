package org.openuss.web.groups.components;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.xss.HtmlInputFilter;
import org.openuss.groups.GroupAccessType;
import org.openuss.groups.UserGroupInfo;
import org.openuss.lecture.LectureException;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;


/**
 * 
 * @author Lutz D. Kramer
 * @author Thomas Jansing
 */
@Bean(name = "views$secured$groups$components$groupoptions", scope = Scope.REQUEST)
@View
public class GroupOptionsPage extends AbstractGroupPage {
	
	private static final Logger logger = Logger.getLogger(GroupOptionsPage.class);

	private static final long serialVersionUID = 8821048605517398410L;

	@Prerender
	@Override
	public void prerender() throws Exception {
		if (groupInfo == null) {
			groupInfo = (UserGroupInfo) getSessionBean(Constants.GROUP);
		}
		if (groupInfo == null) {
			// TODO - Lutz: Properties anpassen
			addMessage(i18n("message_error_group_page"));
			redirect(Constants.OUTCOME_BACKWARD);
		} else {
			if (!isPostBack()) {
				logger.debug("---------- is not postback ---------- refreshing group");
				super.prerender();
			} else {
				breadcrumbs.loadCourseCrumbs;
				
			}
		}
		setSessionBean(Constants.GROUP, groupInfo);
		addPageCrumb();
	}

	// TODO - Lutz: Properties anpassen
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("group_command_options_config"));
		crumb.setHint(i18n("group_command_options_config"));
		breadcrumbs.addCrumb(crumb);
	}

	/**
	 * Save changes of the group
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String saveOptions() {
		logger.trace("saving group options");
		
		// TODO move to business layer
		UserGroupInfo groupOld = getGroupService().getGroupInfo(groupInfo.getId());
		if (groupOld.getAccessType() == GroupAccessType.CLOSED && groupInfo.getAccessType() != GroupAccessType.OPEN) {
			List<UserInfo> aspirants = groupService.getAspirants(groupInfo);
			for(UserInfo aspirant:aspirants){
				groupService.rejectAspirant(groupOld, aspirant.getId());
			}
		}
		// XSS Filter Content
		groupInfo.setDescription(new HtmlInputFilter().filter(groupInfo.getDescription()));
		
		groupService.updateUserGroup(groupInfo);
		//TODO - Lutz: Properties anpassen
		addMessage(i18n("message_group_options_saved"));
		return Constants.GROUP_OPTIONS_PAGE;
	}

	/**
	 * Cancel changes to the Options. Redirect to the previous page
	 * 
	 * @return outcome
	 */
	public String cancelOptions() {
		// nothing to do - group will be automatically refreshed during prerender phase.
			return Constants.GROUP_PAGE;
	}

	/**
	 * Value Change Listener to switch password input text on and off.
	 * 
	 * @param event
	 */
	public void processAccessTypeChanged(ValueChangeEvent event) {
		Object accessType = event.getNewValue();
		groupInfo.setAccessType((GroupAccessType) accessType);
	}

	public List<SelectItem> getAccessTypes() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		// TODO - Lutz: Properties anpassen
		items.add(new SelectItem(GroupAccessType.OPEN, i18n("group_options_access_open")));
		items.add(new SelectItem(GroupAccessType.CLOSED, i18n("group_options_access_closed")));
		items.add(new SelectItem(GroupAccessType.PASSWORD, i18n("group_options_access_password")));
		return items;
	}


}
