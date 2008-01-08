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
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.group.CustomInfoInfo;
import org.openuss.group.GroupAdminInformationInfo;
import org.openuss.group.GroupService;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

@Bean(name = "views$secured$groups$administration", scope = Scope.REQUEST)
@View
public class AdministrationPage extends AbstractCoursePage{
	
	private CustomDataProvider customData = new CustomDataProvider();
	
	@Property(value = "#{groupAdminInformationInfo}")
	protected GroupAdminInformationInfo groupAdminInformationInfo;	

	@Property(value = "#{groupService}")
	protected GroupService groupService;	
	
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		groupAdminInformationInfo = groupService.getAdminInfo(courseInfo.getId());
		setSessionBean(Constants.GROUP_ADMIN_INFO, groupAdminInformationInfo);
	}	
	
	private class CustomDataProvider extends AbstractPagedTable<CustomInfoInfo> {
		
		private static final long serialVersionUID = 5974442506189912053L;
		
		private DataPage<CustomInfoInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<CustomInfoInfo> getDataPage(int startRow, int pageSize) {		
			List<CustomInfoInfo> al = groupService.getCustomParameters(groupAdminInformationInfo);
			page = new DataPage<CustomInfoInfo>(al.size(),0,al);
			return page;
		}
	}
	
	public Collection<SelectItem> getPrefCount(){
		List<SelectItem> prefCount= new ArrayList<SelectItem>();
		prefCount.add(new SelectItem(1,"1"));
		prefCount.add(new SelectItem(2,"2"));
		prefCount.add(new SelectItem(3,"3"));
		prefCount.add(new SelectItem(4,"4"));
		prefCount.add(new SelectItem(5,"5"));
		return prefCount;		
	}
	
	public String save(){
		getGroupService().setAdminInfo(groupAdminInformationInfo, courseInfo.getId());
		addMessage(i18n("group_admin_saved_message"));
		return Constants.SUCCESS;
	}
	
	public String custom(){
		getGroupService().setAdminInfo(groupAdminInformationInfo, courseInfo.getId());
		addMessage(i18n("group_admin_saved_message"));
		return Constants.GROUPS_ADMIN_CUSTOM;
	}
	
	public CustomDataProvider getCustomData() {
		return customData;
	}


	public void setCustomData(CustomDataProvider customData) {
		this.customData = customData;
	}

	public GroupAdminInformationInfo getGroupAdminInformationInfo() {
		return groupAdminInformationInfo;
	}

	public void setGroupAdminInformationInfo(
			GroupAdminInformationInfo groupAdminInformationInfo) {
		this.groupAdminInformationInfo = groupAdminInformationInfo;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

}