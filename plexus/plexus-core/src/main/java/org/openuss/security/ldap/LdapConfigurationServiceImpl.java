// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.validator.UrlValidator;
import java.util.List;

/**
 * @see org.openuss.security.ldap.LdapConfigurationService
 * @author Damian Kemner
 */
public class LdapConfigurationServiceImpl
    extends org.openuss.security.ldap.LdapConfigurationServiceBase
{

    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#getLdapServerConfigurations()
     */
    protected java.util.List handleGetLdapServerConfigurations()
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleGetLdapServerConfigurations()
        return null;
    }
    
    /**
     * 
     */
    public java.util.List handleGetEnabledLdapServerConfigurations() {
    	return null;
    }

    /**
     * 
     */
    public void handleCreateLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServer) throws Exception {
    	if (StringUtils.isBlank(ldapServer.getProviderUrl())){
    		throw new LdapConfigurationServiceException("URL must not be empty!");
    	}
    	if (!handleIsValidURL(ldapServer.getProviderUrl())) {
    		throw new LdapConfigurationServiceException("URL must be a valid ldap-url!");
    	}
    	if (! (ldapServer.getPort()>0)) {
    		throw new LdapConfigurationServiceException("Port must be not negative!");
    	}

    	// TODO: check if root dn is valid
    	// TODO: check if auth type is valid
    	
    	if (StringUtils.isBlank(ldapServer.getManagerDn())){
    		throw new LdapConfigurationServiceException("Manager DN must not be empty!");
    	}
    	// TODO: check if valid manager DN
    	// TODO: other validation
    	
    	getLdapServerDao().create(
    			ldapServer.getProviderUrl(), 
    			ldapServer.getPort(), 
    			ldapServer.getRootDn(), 
    			ldapServer.getAuthenticationType(), 
    			ldapServer.getManagerDn(), 
    			ldapServer.getManagerPassword(), 
    			ldapServer.getUseConnectionPool(), 
    			ldapServer.getUseLdapContext(), 
    			ldapServer.getDescription(), 
    			ldapServer.getLdapServerType(), 
    			true);
    }
    
    
    /**
     * 
     */
    public boolean handleIsValidURL(String url) {
    	String[] schemes = {"ldap"};
    	UrlValidator urlValidator = new UrlValidator(schemes);
    	return urlValidator.isValid(url);
    }

    /**
     * 
     */
    public void handleDeleteLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServer) {
    	getLdapServerDao().remove(ldapServer.getId());
    }

    /**
     * 
     */
    public void handleSaveLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServer) {
    	LdapServerDao dao = getLdapServerDao();
    	LdapServer ldap = dao.ldapServerInfoToEntity(ldapServer);
    	dao.update(ldap);
    }

    /**
     * 
     */
    public java.util.List handleGetAllLdapServers() {
    	return (java.util.List) getLdapServerDao().loadAll();
    }

    /**
     * 
     */
    public java.util.List handleGetLdapServersByDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain) {
    	AuthenticationDomain authDomain = getAuthenticationDomainDao().load(domain.getId());
    	return (java.util.List) authDomain.getLdapServers();
    }

    /**
     * 
     */
    public void handleCreateDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain) throws Exception {
    	if (StringUtils.isBlank(domain.getName())){
    		throw new LdapConfigurationServiceException("Name of new authentication domain must not be empty!");
    	}
    	getAuthenticationDomainDao().create(domain.getName(), domain.getDescription());
    }

    /**
     * 
     */
    public void handleDeleteDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain){
    	getAuthenticationDomainDao().remove(domain.getId());
    }

    /**
     * 
     */
    public void handleSaveDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain) {
    	AuthenticationDomainDao dao = getAuthenticationDomainDao();
    	AuthenticationDomain authDomain = dao.authenticationDomainInfoToEntity(domain);
    	dao.update(authDomain);
    }

    /**
     * 
     */
    public java.util.List handleGetAllDomains() {
    	return (java.util.List) getAuthenticationDomainDao().loadAll();
    }

    
    /**
     * 
     */
    public void handleAddServerToDomain(org.openuss.security.ldap.LdapServerInfo server, org.openuss.security.ldap.AuthenticationDomainInfo domain) throws Exception {
    	AuthenticationDomainDao domainDao = getAuthenticationDomainDao();
    	LdapServerDao serverDao = getLdapServerDao();
    	
    	LdapServer ldapServer = serverDao.ldapServerInfoToEntity(server);
    	AuthenticationDomain authDomain  = domainDao.authenticationDomainInfoToEntity(domain);
    	
    	Validate.notNull(ldapServer, "Server must not be null");
		Validate.notNull(ldapServer.getId(), "Server must provide a valid id.");
		Validate.notNull(authDomain, "Domain must not be null");
		Validate.notNull(authDomain.getId(), "Domain must provide a valid id.");

		ldapServer = forceServerLoad(ldapServer);
		authDomain = forceDomainLoad(authDomain);

		checkDomainContainsServer(ldapServer, authDomain);
	
		authDomain.getLdapServers().add(ldapServer);
		ldapServer.setAuthenticationDomain(authDomain);
		
		domainDao.update(authDomain);
		serverDao.update(ldapServer);
	}
    
    private AuthenticationDomain forceDomainLoad(AuthenticationDomain domain) {
		domain = getAuthenticationDomainDao().load(domain.getId());
		if (domain == null) {
			throw new LdapConfigurationServiceException("Domain not found");
		}
		return domain;
	}

	private LdapServer forceServerLoad(LdapServer server) {
		server = getLdapServerDao().load(server.getId());
		if (server == null) {
			throw new LdapConfigurationServiceException("Server not found!");
		}
		return server;
	}

	private void checkDomainContainsServer(LdapServer server, AuthenticationDomain domain) {
		List servers = (List) domain.getLdapServers();
		if (servers.contains(server)) {
			throw new LdapConfigurationServiceException("Domain already contains server!");
		}
	}

    /**
     * 
     */
    public void handleRemoveServerFromDomain(org.openuss.security.ldap.LdapServerInfo server, org.openuss.security.ldap.AuthenticationDomainInfo domian){}

    /**
     * 
     */
    public void handleCreateAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMapping){}

    /**
     * 
     */
    public void handleDeleteAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMapping){}

    /**
     * 
     */
    public void handleSaveAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMapping){}

    

}