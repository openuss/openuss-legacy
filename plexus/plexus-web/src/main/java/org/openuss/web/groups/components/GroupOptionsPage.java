package org.openuss.web.groups.components;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.xss.HtmlInputFilter;
import org.openuss.groups.GroupAccessType;
import org.openuss.groups.GroupService;
import org.openuss.groups.UserGroupInfo;
import org.openuss.web.Constants;

/**
 * 
 * @author Lutz D. Kramer
 * @author Thomas Jansing
 */
@Bean(name = "views$secured$groups$components$groupoptions", scope = Scope.REQUEST)
@View
public class GroupOptionsPage extends AbstractGroupPage {

	private static final Logger logger = Logger
			.getLogger(GroupOptionsPage.class);
	private static final long serialVersionUID = 8821048605517398410L;

	private Integer accessType = -1;
	private String password;
	/* ----- business logic ----- */

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
				logger
						.debug("---------- is not postback ---------- refreshing group");
				super.prerender();
			} else {
				// breadcrumbs.loadCourseCrumbs;

			}
		}
		setSessionBean(Constants.GROUP, groupInfo);
		if (accessType.compareTo(-1) == 0){
			accessType = groupInfo.getAccessType().getValue();
		}
		if (password == null){
			password = groupInfo.getPassword();
		}
		addPageCrumb();
	}

	// TODO - Lutz: Properties anpassen
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("group_command_options_config"));
		crumb.setHint(i18n("group_command_options_config"));
		breadcrumbs.addCrumb(crumb);
	}

	public String saveOptions() {
		logger.trace("saving group options");

		// XSS Filter Content
		groupInfo.setDescription(new HtmlInputFilter().filter(groupInfo
				.getDescription()));
		groupInfo.setAccessType(GroupAccessType.fromInteger(accessType));
		if (accessType < 2) {
			password = groupInfo.getPassword();
		}
		groupInfo.setPassword(password);

		// update group
		groupService.updateUserGroup(groupInfo);

		// TODO - Lutz: Properties anpassen
		addMessage(i18n("message_group_options_saved"));
		return Constants.GROUP_OPTIONS_PAGE;
	}

	public String cancelOptions() {
		// nothing to do - group will be automatically refreshed during
		// prerender phase.
		return Constants.GROUP_PAGE;
	}

	public void processAccessTypeChanged(ValueChangeEvent event) {
		Object accessTypeGroup = event.getNewValue();
		accessType = (Integer) accessTypeGroup;
		if (accessType < 2) {
			password = "Password";
		} else {
			password = groupInfo.getPassword();
		}
	}

	public List<SelectItem> getAccessTypes() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem(GroupAccessType.OPEN.getValue(),
				i18n("groupaccesstype_open")));
		items.add(new SelectItem(GroupAccessType.CLOSED.getValue(),
				i18n("groupaccesstype_closed")));
		items.add(new SelectItem(GroupAccessType.PASSWORD.getValue(),
				i18n("groupaccesstype_password")));
		return items;
	}

	/* ----- getter and setter ----- */

	public Integer getAccessType() {
		return accessType;
	}

	public void setAccessType(Integer accessType) {
		this.accessType = accessType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
