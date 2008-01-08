package org.openuss.web.groups; 

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.group.CustomInfoInfo;
import org.openuss.group.CustomInfoValueInfo;
import org.openuss.group.GroupAdminInformationInfo;
import org.openuss.group.GroupService;
import org.openuss.group.GroupWishInfo;
import org.openuss.group.WorkingGroupInfo;
import org.openuss.security.SecurityService;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

@Bean(name = "views$secured$groups$apply", scope = Scope.REQUEST)
@View
public class ApplyPage extends AbstractGroupPage{

	@Property(value = "#{groupService}")
	protected GroupService groupService;
	
	@Property(value = "#{securityService}")
	protected SecurityService securityService;
	
	@Property(value = "#{groupAdminInformationInfo}")
	protected GroupAdminInformationInfo groupAdminInformationInfo;	
	
	@Property(value = "#{"+Constants.GROUP_PARAMS+"}")
	protected List<CustomInfoValueInfo> params;
	
	protected Collection<SelectItem> groups;
	
	protected String group1;
	protected String group2;
	protected String group3;
	protected String group4;
	protected String group5;
	
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		groupAdminInformationInfo = groupService.getAdminInfo(courseInfo.getId());
		setSessionBean(Constants.GROUP_ADMIN_INFO, groupAdminInformationInfo);
		if (params!=null){
			List<CustomInfoInfo> paramInformation = getGroupService().getCustomParameters(groupAdminInformationInfo);
			params=new ArrayList<CustomInfoValueInfo>();
			CustomInfoValueInfo civi;
			for (CustomInfoInfo cii:paramInformation){
				civi = new CustomInfoValueInfo();
				civi.setCustomNameId(cii.getId());
				civi.setCustomName(cii.getCustomName());
				params.add(civi);
			}
			setSessionBean(Constants.GROUP_PARAMS, params);
		}
	}
	
	private void addWish(List<GroupWishInfo> wishes, String groupId){
		if (groupId == null||groupId.equals("")) {
			return;
		}
		Long id = new Long(groupId);
		int size = wishes.size();
		if (!contains(wishes, id)){
			GroupWishInfo gwi = new GroupWishInfo();
			gwi.setGroupId(id);
			gwi.setWeight(size);
			wishes.add(gwi);
		}
	}
	
	private boolean contains (List<GroupWishInfo> wishes, Long groupId){
		for (GroupWishInfo wish : wishes){
			if (wish.getGroupId().compareTo(groupId)==0) {
				return true;
			}			
		}
		return false;
		
	}
	
	public String applyUser(){
		if (groupAdminInformationInfo.getMatNr()||groupAdminInformationInfo.getSemester()||groupAdminInformationInfo.getStudies()){
			getSecurityService().saveUserProfile(user);
		}
		//getGroupService().saveCustomValues(user, params);
		addMessage(i18n(Constants.GROUP_ASPIRATION_CONFIRMED));
		List<GroupWishInfo> wishes = new ArrayList<GroupWishInfo>();
		addWish(wishes, group1);
		addWish(wishes, group2);
		addWish(wishes, group3);
		addWish(wishes, group4);
		addWish(wishes, group5);
		groupService.aspire(wishes);
		return Constants.COURSE_PAGE;
	}

	public Collection<SelectItem> getGroups() {
		if (groups==null){
			Collection<SelectItem> groupsSelectItems = new ArrayList<SelectItem>();
			List<WorkingGroupInfo> groupInfos = getGroupService().getGroups(courseInfo.getId());
			for (WorkingGroupInfo group : groupInfos){
				groupsSelectItems.add(new SelectItem(group.getId().toString(), getNameWithSizeInfo(group)));
			}
			groups = groupsSelectItems;
		}
		return groups;
	}

	public void setGroups(Collection<SelectItem> groups) {
		this.groups = groups;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public GroupAdminInformationInfo getGroupAdminInformationInfo() {
		return groupAdminInformationInfo;
	}

	public void setGroupAdminInformationInfo(
			GroupAdminInformationInfo groupAdminInformationInfo) {
		this.groupAdminInformationInfo = groupAdminInformationInfo;
	}

	public List<CustomInfoValueInfo> getParams() {
		return params;
	}

	public void setParams(List<CustomInfoValueInfo> params) {
		this.params = params;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public String getGroup1() {
		return group1;
	}

	public void setGroup1(String group1) {
		this.group1 = group1;
	}

	public String getGroup2() {
		return group2;
	}

	public void setGroup2(String group2) {
		this.group2 = group2;
	}

	public String getGroup3() {
		return group3;
	}

	public void setGroup3(String group3) {
		this.group3 = group3;
	}

	public String getGroup4() {
		return group4;
	}

	public void setGroup4(String group4) {
		this.group4 = group4;
	}

	public String getGroup5() {
		return group5;
	}

	public void setGroup5(String group5) {
		this.group5 = group5;
	}

}