package org.openuss.web.groups;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

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
import org.openuss.web.BasePage;
import org.openuss.web.Constants;


/**
 * Backing bean for the group creation
 * @author Thomas Jansing
 */

@View
@Bean(name = "views$secured$groups$groupcreate", scope = Scope.REQUEST)
public class GroupCreatePage extends BasePage {

	private List<SelectItem> localeItems;
	private Long newGroupId;
	
	@Property(value = "#{groupInfo2}")
	protected UserGroupInfo groupInfo;
	@Property(value = "#{groupService2}")
	protected GroupService groupService;
	
	@Prerender
	public void prerender() throws GroupApplicationException {
		
		groupInfo = new UserGroupInfo(); 
		
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
		
		// UserGroupInfo groupInfo2 = new UserGroupInfo();
		
//		groupInfo2.setCreator(user.getId());
		groupInfo.setAccessType(GroupAccessType.OPEN);


		newGroupId = groupService.createUserGroup(groupInfo);

		return Constants.OPENUSS4US_GROUPS;
	}
}
