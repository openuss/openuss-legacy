package org.openuss.web.groups;

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
import org.openuss.groups.GroupAccessType;
import org.openuss.groups.GroupApplicationException;
import org.openuss.groups.GroupService;
import org.openuss.groups.UserGroupInfo;
import org.openuss.lecture.AccessType;
import org.openuss.lecture.InstituteInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;


/**
 * Backing bean for the group registration on the groupCreatePage
 * @author Thomas Jansing
 */

@Bean(name = Constants.GROUP_REGISTRATION_CONTROLLER, scope = Scope.REQUEST)
@View
public class GroupCreatePage extends AbstractGroupPage {

	private static final Logger logger = Logger.getLogger(GroupCreatePage.class);
	
	private List<SelectItem> localeItems;
	private Long newGroupId;
	
	// private UserGroupInfo groupInfo;
	
	// private GroupService groupService;

	@Prerender
	public void prerender() throws GroupApplicationException {
		
		breadcrumbs.loadOpenuss4usCrumbs();
		
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("openuss4us_groups_registration_headline"));
		newCrumb.setHint(i18n("openuss4us_groups_registration_headline"));
		breadcrumbs.addCrumb(newCrumb);
		
	}
	
	public List<SelectItem> getAccessTypes() {

		localeItems = new ArrayList<SelectItem>();

		SelectItem item1 = new SelectItem(GroupAccessType.OPEN, i18n("groupaccesstype_open"));
		SelectItem item2 = new SelectItem(GroupAccessType.CLOSED, i18n("groupaccesstype_closed"));
		SelectItem item3 = new SelectItem(GroupAccessType.PASSWORD, i18n("groupaccesstype_password"));

		localeItems.add(item1);
		localeItems.add(item2);
		localeItems.add(item3);

		return localeItems;
	}
	
	public String register() {
		// TODO Thomas: Implement Security Tests etc.
		
		// FIXME ACCESS TYPE in groupcreate.xhtml 		

		groupInfo.setAccessType(GroupAccessType.OPEN);

		// CREATE GROUP
		newGroupId = groupService.createUserGroup(groupInfo, user.getId());
		groupInfo.setId(newGroupId);
	
		setSessionBean(Constants.GROUP_INFO, groupInfo);
		return Constants.GROUP_PAGE;
	}
	
	/**
	 * Value Change Listener to switch password input text on and off.
	 * 
	 * @param event
	 */ 
	public void processAccessTypeChanged(ValueChangeEvent event) {
//		Object accessType = event.getNewValue();
//		groupInfo.setAccessType((GroupAccessType) accessType);
	}
	
	public UserGroupInfo getGroupInfo() {
		return groupInfo;
	}
	
	public void setGroupInfo(UserGroupInfo newGroup) {
		this.groupInfo = newGroup;
	}
	
}
