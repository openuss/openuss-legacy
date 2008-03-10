package org.openuss.web.system;

import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.apache.tools.ant.taskdefs.Sync.MyCopy;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.ldap.LdapServerInfo;
import org.openuss.web.PageLinks;
import org.openuss.web.lecture.AbstractLdapServersOverviewPage;

/** 
 * Backing Bean for the view ssecured/system/ldap/ldap_servers.faces
 * 
 * 
 *
 */
@Bean(name = "views$secured$system$ldap$ldap_servers", scope = Scope.REQUEST)
@View

public class LdapServersPage extends AbstractLdapServersOverviewPage{
	
	@Prerender
	public void prerender() {
		try {
			super.prerender();
			addBreadCrumbs();
		} catch (Exception e) {			
		}		
	}
	
	/**
	 * Adds an additional BreadCrumb.
	 */
	 private void addBreadCrumbs() {		 
		 breadcrumbs.init();	
		 
		 BreadCrumb myBreadCrumb = new BreadCrumb();		 
		 myBreadCrumb.setLink(PageLinks.ADMIN_LDAP_INDEX);
		 myBreadCrumb.setName(i18n("ldap_index"));
		 myBreadCrumb.setHint(i18n("ldap_index"));
		 breadcrumbs.addCrumb(myBreadCrumb);
		 
		 myBreadCrumb = new BreadCrumb();
		 myBreadCrumb.setLink(PageLinks.ADMIN_LDAP_LDAPSERVER);
		 myBreadCrumb.setName(i18n("ldap_server"));
		 myBreadCrumb.setHint(i18n("ldap_server"));
		 breadcrumbs.addCrumb(myBreadCrumb);
	 }

	
	public DataPage<LdapServerInfo> fetchDataPage(int startRow, int pageSize) {
		if (dataPage == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch servers data page at " + startRow + ", "+ pageSize+" sorted by "+ldapServers.getSortColumn());
			}
			List<LdapServerInfo> serverList = ldapConfigurationService.getAllLdapServers();
			

			if (serverList != null) {
				logger.info("Size:"+serverList.size());
			}
			
			/*sort(roleAttributeKeyList);*/
			dataPage = new DataPage<LdapServerInfo>(serverList.size(),0,serverList);
		}
		
		/*
		 public DataPage<RoleAttributeKeyInfo> fetchDataPage(int startRow, int pageSize) {
		if (dataPage == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch roleattributekeys data page at " + startRow + ", "+ pageSize+" sorted by "+roleAttributeKeys.getSortColumn());
			}
			List<DepartmentInfo> officialDepartmentList = new ArrayList<DepartmentInfo>(departmentService.findDepartmentsByType(DepartmentType.OFFICIAL));
			List<DepartmentInfo> nonOfficialDepartmentList = new ArrayList<DepartmentInfo>(departmentService.findDepartmentsByType(DepartmentType.NONOFFICIAL));
			
			List<DepartmentInfo> departmentList = new ArrayList<DepartmentInfo>();
			for(DepartmentInfo departmentInfo : officialDepartmentList){
				departmentList.add(departmentInfo);
			}
			for(DepartmentInfo departmentInfo : nonOfficialDepartmentList){
				departmentList.add(departmentInfo);
			}

			logger.info("Departments:"+departmentList);
			if (departmentList != null) {
				logger.info("Size:"+departmentList.size());
			}
			
			sort(departmentList);
			dataPage = new DataPage<DepartmentInfo>(departmentList.size(),0,departmentList);
		}
		 */
		return dataPage;
	}
}