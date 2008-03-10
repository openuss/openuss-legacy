package org.openuss.web.system;

import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.ldap.AuthenticationDomainInfo;
import org.openuss.web.PageLinks;
import org.openuss.web.lecture.AbstractLdapDomainsOverviewPage;

/** 
 * Backing Bean for the view secured/system/departments.xhtml
 * 
 * 
 *
 */
@Bean(name = "views$secured$system$ldap$ldap_domains", scope = Scope.REQUEST)
@View

public class LdapDomainsPage extends AbstractLdapDomainsOverviewPage{
	
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
		 breadcrumbs.loadAdministrationCrumbs();	
		 
		 BreadCrumb myBreadCrumb = new BreadCrumb();		 
		 myBreadCrumb.setLink(PageLinks.ADMIN_LDAP_INDEX);
		 myBreadCrumb.setName(i18n("ldap_index"));
		 myBreadCrumb.setHint(i18n("ldap_index"));
		 breadcrumbs.addCrumb(myBreadCrumb);
		 
		 myBreadCrumb = new BreadCrumb();
		 myBreadCrumb.setLink(PageLinks.ADMIN_LDAP_AUTHENTICATIONDOMAIN);
		 myBreadCrumb.setName(i18n("ldap_domain"));
		 myBreadCrumb.setHint(i18n("ldap_domain_hint"));
		 breadcrumbs.addCrumb(myBreadCrumb);
	 }
	
	public DataPage<AuthenticationDomainInfo> fetchDataPage(int startRow, int pageSize) {
		if (dataPage == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch attributemappings data page at " + startRow + ", "+ pageSize+" sorted by "+authenticationDomains.getSortColumn());
			}
			List<AuthenticationDomainInfo> domainList = ldapConfigurationService.getAllDomains();
			

			if (domainList != null) {
				logger.info("Size:"+domainList.size());
			}
			
			/*sort(roleAttributeKeyList);*/
			dataPage = new DataPage<AuthenticationDomainInfo>(domainList.size(),0,domainList);
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