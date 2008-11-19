package org.openuss.web.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.GroupItem;
import org.openuss.security.SecurityService;
import org.openuss.web.BasePage;
import org.openuss.web.PageLinks;

/**
 * 
 * @author Ingo Dueppe
 */
@Bean(name="views$secured$system$groups", scope=Scope.REQUEST)
@View
public class GroupsPage extends BasePage{

	@Property (value="#{securityService}")
	private SecurityService securityService;

	private GroupTable table = new GroupTable();

	@Prerender
	public void prerender() {
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("admin_command_groups"));
		newCrumb.setLink(PageLinks.ADMIN_GROUPS);
		
		breadcrumbs.loadAdministrationCrumbs();
		breadcrumbs.addCrumb(newCrumb);
	}		
	
	private class GroupTable extends AbstractPagedTable<GroupItem> {
		
		private static final long serialVersionUID = 8632408249099301747L;
		
		private DataPage<GroupItem> page;
		
		@SuppressWarnings("unchecked")
		@Override
		public DataPage<GroupItem> getDataPage(int startRow, int pageSize) {
			if (page == null){
				List<GroupItem> groups = new ArrayList<GroupItem>(securityService.getAllGroups());
				sort(groups);
				page = new DataPage<GroupItem>(groups.size(), 0, groups);
			}
			return page;
		}
	}
	
	/* ------------ properties ----------------- */

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public GroupTable getTable() {
		return table;
	}

	public void setTable(GroupTable data) {
		this.table = data;
	}

}
