package org.openuss.web.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.ldap.LdapServerInfo;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

/** 
 * Backing Bean for the view secured/system/ldap/ldap_servers.faces
 * 
 * @author Juergen de Braaf
 * @author Christian Grelle
 * @author Peter Schuh
 */
@Bean(name = "views$secured$system$ldap$ldap_servers", scope = Scope.REQUEST)
@View
public class LdapServersPage extends AbstractLdapServersOverviewPage {
	
	protected static final Logger logger = Logger.getLogger(LdapServersPage.class);
	
	private LdapServerTable ldapServers = new LdapServerTable();
		
	
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
		 myBreadCrumb.setLink(PageLinks.ADMIN_LDAP_LDAPSERVER);
		 myBreadCrumb.setName(i18n("ldap_server"));
		 myBreadCrumb.setHint(i18n("ldap_server"));	 
		 breadcrumbs.addCrumb(myBreadCrumb);
	 }
	 

	public LdapServerInfo currentLdapServer() {
			LdapServerInfo ldapServerInfo = ldapServers.getRowData();
			return ldapServerInfo;
	}

	 /**
	  * Store the selected LdapServer into session scope and go to LdapServer main page.
	  * 
	  * @return Outcome
	  */
	public String selectLdapServerAndEdit() {
			
		LdapServerInfo ldapServerInfo = currentLdapServer();
		setSessionBean(Constants.LDAPSERVER_INFO, ldapServerInfo);			
			
		return Constants.LDAP_SERVER_REGISTRATION_STEP1_PAGE;
	}
	 
	/**
	 * Store the selected LdapServer into session scope and go to LdapServer remove confirmation page.
	 * 
	 * @return Outcome
	 */
	public String selectLdapServerAndConfirmRemove() {
		LdapServerInfo ldapServerInfo = currentLdapServer();
		setSessionBean(Constants.LDAPSERVER_INFO, ldapServerInfo);
		
		return Constants.SERVER_CONFIRM_REMOVE_PAGE;
	}
	
		
	public String removeLdapServer() throws Exception {
		try {			
			logger.debug("Starting method removeLdapServer");
			LdapServerInfo currentLdapServer = (LdapServerInfo) getSessionBean(Constants.LDAPSERVER_INFO);
			
//			delete LdapServer				
			ldapConfigurationService.deleteLdapServer(currentLdapServer);

			setSessionBean(Constants.LDAPSERVER_INFO, null);
			addMessage(i18n("message_ldap_ldapserver_removed"));
			return Constants.LDAP_SERVER_PAGE;			
		}
		catch (Exception e) {			
			addError(i18n("message_ldap_ldapserver_cannot_be_removed"));
			return Constants.LDAP_SERVER_PAGE;
		}
	}
		
	
	public LdapServerTable getLdapServerTable() {
			return ldapServers;
	}		
	
	
	protected class LdapServerTable extends AbstractPagedTable<LdapServerInfo> {
		
		private DataPage<LdapServerInfo> dataPage;		
			
		@Override
		public DataPage<LdapServerInfo> getDataPage(int startRow, int pageSize) {
			if (dataPage == null) {
				if (logger.isDebugEnabled()) {
					logger.debug("fetch ldapServers data page at " + startRow + ", "+ pageSize+" sorted by "+ldapServers.getSortColumn());
				}
				List<LdapServerInfo> ldapServerList = ldapConfigurationService.getAllLdapServers();
			
				logger.info("LdapServers:"+ldapServerList);
				if (ldapServerList != null) {
					logger.info("Size:"+ldapServerList.size());
				} else ldapServerList = new ArrayList<LdapServerInfo>();

				sort(ldapServerList);
				dataPage = new DataPage<LdapServerInfo>(ldapServerList.size(),0,ldapServerList);
			}
			
			return dataPage;
		}
	}//end class LdapServerTable




	public LdapServerTable getLdapServers() {
		return ldapServers;
	}

	public void setLdapServers(LdapServerTable ldapServers) {
		this.ldapServers = ldapServers;
	}
		
}//end class LdapServersPage
