package org.openuss.web.system;

import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.ldap.AuthenticationDomainInfo;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

/** 
 * Backing Bean for the view secured/system/departments.xhtml
 * 
 * 
 *
 */
@Bean(name = "views$secured$system$ldap$ldap_domains", scope = Scope.REQUEST)
@View

public class LdapDomainsPage extends AbstractLdapDomainsOverviewPage{
	
	protected AuthenticationDomainTable authenticationDomains = new AuthenticationDomainTable();

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
	
	public AuthenticationDomainTable getAuthenticationDomains() {
			return authenticationDomains;
	}
	
	protected AuthenticationDomainInfo currentAuthenticationDomain() {
		AuthenticationDomainInfo authenticationDomain = authenticationDomains.getRowData();
		return authenticationDomain;
	}

	/**
	 * Store the selected authenticationDomain into session scope and go to authenticationDomain
	 * main page.
	 * 
	 * @return Outcome
	 */
	public String selectAuthenticationDomainAndEdit() {
		AuthenticationDomainInfo authenticationDomain = currentAuthenticationDomain();
		setSessionBean(Constants.AUTHENTICATIONDOMAIN_INFO, authenticationDomain);
		
		return Constants.LDAP_DOMAIN_REGISTRATION_STEP1_PAGE;
	}
	
	/**
	 * Store the selected department into session scope and go to authentication domain
	 * remove confirmation page.
	 * 
	 * @return Outcome
	 */
	public String selectAuthenticationDomainAndConfirmRemove() {
		AuthenticationDomainInfo currentAuthenticationDomain = currentAuthenticationDomain();
		setSessionBean(Constants.AUTHENTICATIONDOMAIN_INFO, currentAuthenticationDomain);

		return Constants.DOMAIN_CONFIRM_REMOVE_PAGE;
	}
		
	public String removeAuthenticationDomain() throws Exception {
		try {
			logger.debug("Starting method removeAuthenticationDomain");
			AuthenticationDomainInfo currentAuthenticationDomain  = (AuthenticationDomainInfo) getSessionBean(Constants.AUTHENTICATIONDOMAIN_INFO);
			if (currentAuthenticationDomain.getLdapServerIds() == null || currentAuthenticationDomain.getLdapServerIds().size()==0) {
				ldapConfigurationService.deleteDomain(currentAuthenticationDomain);
				setSessionBean(Constants.AUTHENTICATIONDOMAIN_INFO, null);
				return Constants.LDAP_DOMAIN_PAGE;
			} else {
				addError(i18n("message_ldap_authenticationdomain_still_in_use_cannot_be_removed"));
				return Constants.LDAP_DOMAIN_PAGE;
			  }
			}
			catch (Exception e) {
				addError(e.getMessage());
				addError(i18n("message_ldap_authenticationdomain_cannot_be_removed"));
				return Constants.LDAP_DOMAIN_PAGE;
			}		
	}	


	
	public String confirmRemoveAuthenticationDomain() {
		AuthenticationDomainInfo authenticationDomainInfo = currentAuthenticationDomain();
		setSessionBean(Constants.AUTHENTICATIONDOMAIN_INFO, authenticationDomainInfo);
		return "removed";
	}


	 
	protected class AuthenticationDomainTable extends AbstractPagedTable<AuthenticationDomainInfo> {
		
		private DataPage<AuthenticationDomainInfo> dataPage;
		
		@Override
		public DataPage<AuthenticationDomainInfo> getDataPage(int startRow, int pageSize) {

			if (dataPage == null) {
				if (logger.isDebugEnabled()) {
					logger.debug("fetch attributemappings data page at " + startRow + ", "+ pageSize+" sorted by "+authenticationDomains.getSortColumn());
				}
				List<AuthenticationDomainInfo> domainList = ldapConfigurationService.getAllDomains();
				

				if (domainList != null) {
					logger.info("Size:"+domainList.size());
				}
				
				sort(domainList);
				dataPage = new DataPage<AuthenticationDomainInfo>(domainList.size(),0,domainList);
			}
			
			return dataPage;

		}

	}
}