package org.openuss.web.system;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.GroupItem;
import org.openuss.security.SecurityService;
import org.openuss.web.BasePage;

/**
 * 
 * @author Ingo Dueppe
 */
@Bean(name="views$secured$system$groups", scope=Scope.REQUEST)
@View
public class GroupsPage extends BasePage{

	private static final Logger logger = Logger.getLogger(GroupsPage.class);

	private static final long serialVersionUID = -3339596434048309514L;
	
	
	@Property (value="#{securityService}")
	private SecurityService securityService;

	private GroupTable table = new GroupTable();

	private class GroupTable extends AbstractPagedTable<GroupItem> {
		private DataPage<GroupItem> page;
		
		@Override
		public DataPage<GroupItem> getDataPage(int startRow, int pageSize) {
			logger.debug("getDataPage("+startRow+","+pageSize+")");
			if (page == null){
				List groups = new ArrayList(securityService.getAllGroups());
				sort(groups);
				page = new DataPage(groups.size(), 0, groups);
			}
			return page;
		}

		private void sort(List<GroupItem> groups) {
			if ("name".equalsIgnoreCase(table.getSortColumn())) {
				Collections.sort(groups,new NameComparator());
			} else if ("label".equalsIgnoreCase(table.getSortColumn())) {
				Collections.sort(groups,new LabelComparator());
			} else if ("password".equalsIgnoreCase(table.getSortColumn())) {
				Collections.sort(groups,new PasswordComparator());
			} else if ("type".equalsIgnoreCase(table.getSortColumn())) {
				Collections.sort(groups,new TypeComparator());
			}
		}
	}
	
	private class NameComparator implements Comparator<GroupItem> {
		public int compare(GroupItem g1, GroupItem g2) {
			if (table.isAscending()) {
				return g1.getName().compareToIgnoreCase(g2.getName());
			} else {
				return g2.getName().compareToIgnoreCase(g1.getName());
			}
		}
	}
	
	private class LabelComparator implements Comparator<GroupItem> {
		public int compare(GroupItem g1, GroupItem g2) {
			if (table.isAscending()) {
				return g1.getLabel().compareToIgnoreCase(g2.getLabel());
			} else {
				return g2.getLabel().compareToIgnoreCase(g1.getLabel());
			}
		}
	}

	private class TypeComparator implements Comparator<GroupItem> {
		public int compare(GroupItem g1, GroupItem g2) {
			if (table.isAscending()) {
				return g1.getGroupType().compareTo(g2.getGroupType());
			} else {
				return g2.getGroupType().compareTo(g1.getGroupType());
			}
		}
	}

	private class PasswordComparator implements Comparator<GroupItem> {
		public int compare(GroupItem g1, GroupItem g2) {
			if (table.isAscending()) {
				return g1.getPassword().compareToIgnoreCase(g2.getPassword());
			} else {
				return g2.getPassword().compareToIgnoreCase(g1.getPassword());
			}
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
