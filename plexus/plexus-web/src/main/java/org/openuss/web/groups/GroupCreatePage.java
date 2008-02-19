package org.openuss.web.groups;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.groups.GroupAccessType;
import org.openuss.groups.GroupService;
import org.openuss.groups.UserGroupInfo;
import org.openuss.lecture.AccessType;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;


/**
 * Backing bean for the group registration on the groupCreatePage
 * @author Thomas Jansing
 */

@Bean(name = Constants.GROUP_REGISTRATION_CONTROLLER, scope = Scope.SESSION)
@View
public class GroupCreatePage extends BasePage {
	
	private static final Logger logger = Logger.getLogger(GroupCreatePage.class);
	
//	@Property(value = "#{groupInfo}")
	protected UserGroupInfo groupInfo;
	@Property(value = "#{groupService}")
	protected GroupService groupService;
		
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
	
	public String register() {
		logger.debug("START CREATION GROUP");
		logger.debug("ACTUAL ACCESS TYPE: " + accessType);
		// create group info object
		UserGroupInfo groupInfo = new UserGroupInfo();
		groupInfo.setId(null);
		groupInfo.setName(name);
		groupInfo.setShortcut(shortcut);
		groupInfo.setDescription(description);
		groupInfo.setPassword(password);
		groupInfo.setAccessType(GroupAccessType.fromInteger(accessType));
		groupInfo.setCreator(user.getId());
		groupInfo.setCalendar(calendar);
		groupInfo.setChat(chat);
		groupInfo.setDocuments(documents);
		groupInfo.setForum(forum);
		groupInfo.setNewsletter(newsletter);

		logger.debug("Id: " + groupInfo.getId());
		logger.debug("Name: " + groupInfo.getName());
		logger.debug("Shortcut: " + groupInfo.getShortcut());
		logger.debug("Description: " + groupInfo.getDescription());
		logger.debug("Password: " + groupInfo.getPassword());
		logger.debug("AccessType: " + groupInfo.getAccessType());
		logger.debug("Creator: " + groupInfo.getCreator());
		logger.debug("Calendar: " + groupInfo.isCalendar());
		logger.debug("Chat: " + groupInfo.isChat());
		logger.debug("Documents: " + groupInfo.isDocuments());
		logger.debug("Forum: " + groupInfo.isForum());
		logger.debug("Newsletter: " + groupInfo.isNewsletter());

		// create group and set id
		Long newGroupId = groupService.createUserGroup(groupInfo, user.getId());
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
		
		logger.debug("END CREATION GROUP");
		return Constants.GROUP_PAGE;
	}

	public List<SelectItem> getAccessTypes() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem(GroupAccessType.OPEN.getValue(), i18n("groupaccesstype_open")));
		items.add(new SelectItem(GroupAccessType.CLOSED.getValue(), i18n("groupaccesstype_closed")));
		items.add(new SelectItem(GroupAccessType.PASSWORD.getValue(), i18n("groupaccesstype_password")));
		return items;
	}

	public void processAccessTypeChanged(ValueChangeEvent event) {
//		Object accessTypeGroup = event.getNewValue();
//		logger.debug("HERE IS THE ACCESS TYPE: " + (GroupAccessType)accessTypeGroup);
//		accessType = (GroupAccessType) accessTypeGroup;
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

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	
	public UserGroupInfo getGroupInfo() {
		return groupInfo;
	}

	
	public void setGroupInfo(UserGroupInfo groupInfo) {
		this.groupInfo = groupInfo;
	}

	
	
	
	
	

//	@Property(value = "#{groupInfo}")
//	protected UserGroupInfo groupInfo;
//	
//	@Property(value = "#{groupService}")
//	protected GroupService groupService;
//	
//	
//	
//	private List<SelectItem> localeItems;
//	private Long newGroupId;
//	
////	@Property(value = "#{groupInfo}")
////	protected UserGroupInfo groupInfo;
////	
////	private GroupService groupService;
//
////	@Preprocess
////	public void preprocess() throws GroupApplicationException {
////		
////		logger.debug("@PRE");
////		groupInfo = new UserGroupInfo();
////		
////		// groupInfo.setId(null);
////	}
//	
//	@Prerender
//	public void prerender() throws Exception {
//		super.prerender();
//		logger.debug("Honigkuchenpferd1");
//		if (groupInfo != null && groupInfo.getId() != null) {
//			logger.debug("Honigkuchenpferd2");
//			groupInfo = new UserGroupInfo();
//		}
//		if (groupInfo == null) {
//			logger.debug("Honigkuchenpferd3");
//			// groupInfo = new UserGroupInfo();
//			
//			// addError(i18n("group_page: groupInfo == null"));
//			// redirect(Constants.OUTCOME_BACKWARD);
//			
//		} else {
//			logger.debug("Honigkuchenpferd4");
//			BreadCrumb newCrumb = new BreadCrumb();
//			newCrumb.setName(i18n("openuss4us_groups_registration_headline"));
//			newCrumb.setHint(i18n("openuss4us_groups_registration_headline"));
//			breadcrumbs.loadOpenuss4usCrumbs();
//			breadcrumbs.addCrumb(newCrumb);
//			
//			groupInfo = new UserGroupInfo();
//			
//			// setSessionBean(Constants.GROUP_INFO, groupInfo);
//		}
//	}
//	
//	public List<SelectItem> getAccessTypes() {
//
//		localeItems = new ArrayList<SelectItem>();
//
//		SelectItem item1 = new SelectItem(GroupAccessType.OPEN, i18n("groupaccesstype_open"));
//		SelectItem item2 = new SelectItem(GroupAccessType.CLOSED, i18n("groupaccesstype_closed"));
//		SelectItem item3 = new SelectItem(GroupAccessType.PASSWORD, i18n("groupaccesstype_password"));
//
//		localeItems.add(item1);
//		localeItems.add(item2);
//		localeItems.add(item3);
//
//		return localeItems;
//	}
//	
//	
//	/**
//	 * Value Change Listener to switch password input text on and off.
//	 * 
//	 * @param event
//	 */ 
//	public void processAccessTypeChanged(ValueChangeEvent event) {
////		Object accessType = event.getNewValue();
////		groupInfo.setAccessType((GroupAccessType) accessType);
//	}
//	
//	public UserGroupInfo getGroupInfo() {
//		logger.debug("Honigkuchenpferd GetGroupInfo");
//		return groupInfo;
//	}
//	
//	public void setGroupInfo(UserGroupInfo newGroup) {
//		logger.debug("Honigkuchenpferd SetGroupInfo");
//		this.groupInfo = newGroup;
//	}
//
//	public GroupService getGroupService() {
//		logger.debug("Honigkuchenpferd GetGroupService");
//		return groupService;
//	}
//
//	public void setGroupService(GroupService groupService) {
//		logger.debug("Honigkuchenpferd SetGroupService");
//		this.groupService = groupService;
//	}
	
}
