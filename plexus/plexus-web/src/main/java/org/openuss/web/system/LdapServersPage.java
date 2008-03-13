package org.openuss.web.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.security.ldap.LdapServerInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.web.lecture.LdapServerRegistrationController;

/** 
 * Backing Bean for the view secured/system/ldap/ldap_servers.faces
 * 
 * @author Juergen de Braaf
 * @author Christian Grelle
 * @author Peter Schuh
 */
@Bean(name = "views$secured$system$ldap$ldap_servers", scope = Scope.REQUEST)
@View
public class LdapServersPage extends BasePage {
	
	protected static final Logger logger = Logger.getLogger(LdapServersPage.class);
	
	private LdapServerTable ldapServerTable = new LdapServerTable();
	
	@Property(value = "#{"+Constants.LDAP_SERVER_REGISTRATION_CONTROLLER+"}")
	protected LdapServerRegistrationController ldapServerRegistrationController;
	
	@Property(value = "#{ldapConfigurationService}")
	protected LdapConfigurationService ldapConfigurationService;
	
	
	
	@Prerender
	public void prerender() {
		try {			
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
	 
	public LdapConfigurationService getLdapConfigurationService() {
		return ldapConfigurationService;
	}

	public void setLdapConfigurationService(LdapConfigurationService ldapConfigurationService) {
		this.ldapConfigurationService = ldapConfigurationService;
	}
	 
	public LdapServerRegistrationController getLdapServerRegistrationController() {
		return ldapServerRegistrationController;
	}

	public void setLdapServerRegistrationController(
			LdapServerRegistrationController ldapServerRegistrationController) {
		this.ldapServerRegistrationController = ldapServerRegistrationController;
	}

	protected LdapServerInfo currentLdapServer() {
			LdapServerInfo ldapServerInfo = ldapServerTable.getRowData();
			return ldapServerInfo;
	}

	 /**
	  * Store the selected LdapServer into session scope and go to LdapServer main page.
	  * 
	  * @return Outcome
	  */
	public String selectLdapServerAndEdit() {			
		setSessionBean(Constants.SERVER_INFO, currentLdapServer());			
			
		return Constants.LDAP_SERVER_REGISTRATION_STEP1_PAGE;
	}
	 
	/**
	 * Store the selected LdapServer into session scope and go to LdapServer remove confirmation page.
	 * 
	 * @return Outcome
	 */
	public String selectLdapServerAndConfirmRemove() {
		setSessionBean(Constants.SERVER_INFO, currentLdapServer());
		
		return Constants.SERVER_CONFIRM_REMOVE_PAGE;
	}
	
		
	public String removeLdapServer() throws Exception {
		try {			
			logger.debug("Starting method removeLdapServer");
			LdapServerInfo currentLdapServer = (LdapServerInfo) getSessionBean(Constants.SERVER_INFO);
			
//			delete LdapServer				
			ldapConfigurationService.deleteLdapServer(currentLdapServer);

			setSessionBean(Constants.SERVER_INFO, null);
			return Constants.LDAP_SERVER_PAGE;			
		}
		catch (Exception e) {			
			addError(i18n("message_ldap_ldapserver_cannot_be_removed"));
			return Constants.LDAP_SERVER_PAGE;
		}
	}
		
	
	public LdapServerTable getLdapServerTable() {
			return ldapServerTable;
	}		
			
	
	protected class LdapServerTable extends AbstractPagedTable<LdapServerInfo> {
		
		private DataPage<LdapServerInfo> dataPage;		
	
		@SuppressWarnings( { "unchecked" })
		@Override
		public DataPage<LdapServerInfo> getDataPage(int startRow, int pageSize) {
			if (dataPage == null) {
				if (logger.isDebugEnabled()) {
					logger.debug("fetch ldapServers data page at " + startRow + ", "+ pageSize+" sorted by "+ldapServerTable.getSortColumn());
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
		
}//end class LdapServersPage
