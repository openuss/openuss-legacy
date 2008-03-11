package org.openuss.web.groups;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.xss.HtmlInputFilter;
import org.openuss.groups.GroupAccessType;
import org.openuss.groups.UserGroupInfo;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

/**
 * Backing bean for the group registration on the groupCreatePage
 * 
 * @author Lutz D. Kramer
 * @author Thomas Jansing
 */
@Bean(name = "views$secured$groups$groupcreate", scope = Scope.REQUEST)
@View
public class GroupsCreatePage extends AbstractGroupsPage {

	private String name;
	private String shortcut;
	private String password;
	private String description;
	private Integer accessType;
	private boolean calendar = true;
	private boolean chat = false;
	private boolean documents = true;
	private boolean forum = true;
	private boolean newsletter = true;

	/* ----- business logic ----- */

	@Override
	@Prerender
	public void prerender() throws Exception { // NOPMD by devopenuss on 11.03.08 14:22
		super.prerender();
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setLink(contextPath()
				+ "/views/secured/groups/groupcreate.faces");
		newCrumb.setName(i18n("openuss4us_command_groups_create"));
		newCrumb.setHint(i18n("openuss4us_command_groups_create"));
		breadcrumbs.addCrumb(newCrumb);
	}

	public String register() { // NOPMD by devopenuss on 11.03.08 14:22
		if (groupService.isUniqueShortcut(shortcut)) {

			// create group info object
			UserGroupInfo groupInfo = new UserGroupInfo();
			groupInfo.setId(null);
			groupInfo.setName(name);
			groupInfo.setShortcut(shortcut);
			// XSS Filter Content
			groupInfo.setDescription(new HtmlInputFilter().filter(description));
			if (accessType < 2) {
				password = null;
			}
			groupInfo.setPassword(password);
			groupInfo.setAccessType(GroupAccessType.fromInteger(accessType));
			groupInfo.setCreator(user.getId());
			groupInfo.setCalendar(calendar);
			groupInfo.setChat(chat);
			groupInfo.setDocuments(documents);
			groupInfo.setForum(forum);
			groupInfo.setNewsletter(newsletter);

			// create group and set id
			Long newGroupId = groupService.createUserGroup(groupInfo, user
					.getId());
			groupInfo.setId(newGroupId);

			// clear fields
			name = null;
			shortcut = null;
			description = null;
			password = null;
			calendar = true;
			chat = false;
			documents = true;
			forum = true;
			newsletter = true;
			accessType = 0;
			return (PageLinks.GROUP_PAGE + "?group=" + newGroupId);
		} else {
			addError(i18n("group_no_unique_shortcut_error"));
			return Constants.OPENUSS4US_GROUPS_CREATE;
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

	public void processAccessTypeChanged(ValueChangeEvent event) {
		Object accessTypeGroup = event.getNewValue();
		accessType = (Integer) accessTypeGroup;
		if (accessType < 2) {
			password = "Password";
		} else {
			password = null;
		}
	}

	/* ----- getter and setter ----- */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortcut() {
		return shortcut;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAccessType() {
		return accessType;
	}

	public void setAccessType(Integer accessType) {
		this.accessType = accessType;
	}

	public boolean isCalendar() {
		return calendar;
	}

	public void setCalendar(boolean calendar) {
		this.calendar = calendar;
	}

	public boolean isChat() {
		return chat;
	}

	public void setChat(boolean chat) {
		this.chat = chat;
	}

	public boolean isDocuments() {
		return documents;
	}

	public void setDocuments(boolean documents) {
		this.documents = documents;
	}

	public boolean isForum() {
		return forum;
	}

	public void setForum(boolean forum) {
		this.forum = forum;
	}

	public boolean isNewsletter() {
		return newsletter;
	}

	public void setNewsletter(boolean newsletter) {
		this.newsletter = newsletter;
	}

}
