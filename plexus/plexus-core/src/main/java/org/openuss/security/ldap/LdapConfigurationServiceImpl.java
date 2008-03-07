// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.validator.UrlValidator;
import org.openuss.security.acegi.ldap.LdapServerConfiguration;

/**
 * @see org.openuss.security.ldap.LdapConfigurationService
 * @author Juergen de Braaf
 * @author Damian Kemner
 */
public class LdapConfigurationServiceImpl
    extends org.openuss.security.ldap.LdapConfigurationServiceBase
{

    
    /**
     * @see org.openuss.security.ldap.LdapConfigurationService#getEnabledLdapServerConfigurations()
     */
    @Override
    protected java.util.List<LdapServerConfiguration> handleGetEnabledLdapServerConfigurations() {
    	    	
    	List<LdapServerConfiguration> ldapServerConfigurationList = new ArrayList<LdapServerConfiguration>();
    	
    	List<LdapServer> ldapServerList = getLdapServerDao().findAllEnabledServers();
    	for (LdapServer ldapServer : ldapServerList) {

    		LdapServerConfiguration ldapServerConfiguration = new LdapServerConfiguration();
    		
    		ldapServerConfiguration.setAuthenticationDomainId(ldapServer.getAuthenticationDomain().getId());
    		ldapServerConfiguration.setAuthenticationType(ldapServer.getAuthenticationType());
    		ldapServerConfiguration.setEmailKey(ldapServer.getAuthenticationDomain().getAttributeMapping().getEmailKey());
    		ldapServerConfiguration.setFirstNameKey(ldapServer.getAuthenticationDomain().getAttributeMapping().getFirstNameKey());
    		ldapServerConfiguration.setLastNameKey(ldapServer.getAuthenticationDomain().getAttributeMapping().getLastNameKey());
    		ldapServerConfiguration.setLdapServerType(ldapServer.getLdapServerType());
    		ldapServerConfiguration.setPort(ldapServer.getPort());
    		ldapServerConfiguration.setProviderUrl(ldapServer.getProviderUrl());
    		ldapServerConfiguration.setRoleAttributeKeys((String[]) ldapServer.getAuthenticationDomain().getAttributeMapping().getRoleAttributeKeys().toArray());
    		ldapServerConfiguration.setRootDn(ldapServer.getRootDn());
    		ldapServerConfiguration.setUseConnectionPool(ldapServer.getUseConnectionPool());
    		ldapServerConfiguration.setUseLdapContext(ldapServer.getUseLdapContext());
    		ldapServerConfiguration.setUserDnPatterns((String[]) ldapServer.getUserDnPatterns().toArray());
    		ldapServerConfiguration.setUsernameKey(ldapServer.getAuthenticationDomain().getAttributeMapping().getUsernameKey());    		
    		ldapServerConfigurationList.add(ldapServerConfiguration);        	
		}   	
    	
//    	TODO has to be tested

    	return ldapServerConfigurationList;
    }

    /**
     * 
     */
    @Override
    protected LdapServerInfo handleCreateLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServer) throws Exception {
    	validateLdapServer(ldapServer);
    	
    	AuthenticationDomain domain = getAuthenticationDomainDao().load(ldapServer.getAuthenticationDomainId());
    	
    	//UserDnPatternSet set = getUserDnPatternSetDao().load(ldapServer.getUserDnPatternSetId());
    	
    	LdapServer ldapServerEntity = getLdapServerDao().create(
    			domain,
    			ldapServer.getAuthenticationType(), 
    			ldapServer.getDescription(), 
    			ldapServer.isEnabled(), 
    			ldapServer.getLdapServerType(), 
    			ldapServer.getPort(),
    			ldapServer.getProviderUrl(),
    			ldapServer.getRootDn(), 
    			ldapServer.getUseConnectionPool(),
    			ldapServer.getUseLdapContext(),
    			ldapServer.getUserDnPatterns()
    		);
    	
    	ldapServerEntity.setManagerDn(ldapServer.getManagerDn());
    	ldapServerEntity.setManagerPassword(ldapServer.getManagerPassword());
    	
		getLdapServerDao().update(ldapServerEntity);
		
		domain.getLdapServers().add(ldapServerEntity);
		getAuthenticationDomainDao().update(domain);
		
//		set.getLdapServers().add(ldapServerEntity);
//		getUserDnPatternSetDao().update(set);
    	
    	ldapServer.setId(ldapServerEntity.getId());
    	
    	return ldapServer;
    }
    
    /**
     * validates an LdapServerInfo object if it contains valid attributes
     * used to create and save (update) a LdapServer
     */
    protected void validateLdapServer(LdapServerInfo ldapServer) throws Exception {
    	if (StringUtils.isBlank(ldapServer.getProviderUrl())){
    		throw new LdapConfigurationServiceException("URL must not be empty!");
    	}
    	if (!handleIsValidURL(ldapServer.getProviderUrl())) {
    		throw new LdapConfigurationServiceException("URL must be a valid ldap-url!");
    	}
    	if (! (ldapServer.getPort() > 0)) {
    		throw new LdapConfigurationServiceException("Port must be not negative!");
    	}

    	// TODO: check if root dn is valid
    	// TODO: check if auth type is valid
    	
    	// TODO: check if valid manager DN
    	// TODO: other validation
    }
    
    
    /**
     * 
     */
    @Override
    protected boolean handleIsValidURL(String url) {
    	String[] schemes = {"ldap"};
    	UrlValidator urlValidator = new UrlValidator(schemes);
    	return urlValidator.isValid(url);
    }

    /**
     * 
     */
    @Override
    protected void handleDeleteLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServer) {  
    	LdapServer server = getLdapServerDao().ldapServerInfoToEntity(ldapServer);
    	
    	// remove ldap server from domain
    	AuthenticationDomain domain = getAuthenticationDomainDao().load(ldapServer.getAuthenticationDomainId());
    	if (domain != null) {
    		domain.getLdapServers().remove(server);
        	getAuthenticationDomainDao().update(domain);
    	}
    	
//    	// remove ldap server from user dn pattern set
//    	UserDnPatternSet set = getUserDnPatternSetDao().load(ldapServer.getUserDnPatternSetId());
//    	if (set != null) {
//    		set.getLdapServers().remove(server);
//        	getUserDnPatternSetDao().update(set);
//    	}
    	

    	getLdapServerDao().remove(ldapServer.getId());
    	ldapServer.setId(null);
    }

    /**
     * 
     */
    @Override
    protected void handleSaveLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServer) throws Exception {
//    	TODO check method ldapServerInfoEntity
    	validateLdapServer(ldapServer);
    	LdapServer ldapServerEntity = getLdapServerDao().ldapServerInfoToEntity(ldapServer);
    	ldapServerEntity.setAuthenticationDomain(getAuthenticationDomainDao().load(ldapServer.getAuthenticationDomainId()));    	
    	//ldapServerEntity.setUserDnPatternSet(getUserDnPatternSetDao().load(ldapServer.getUserDnPatternSetId()));    	
    	getLdapServerDao().update(ldapServerEntity);
    }

    /**
     * 
     */
    @Override
    protected java.util.List<LdapServerInfo> handleGetAllLdapServers() {
    	List<LdapServerInfo> ldapList = new ArrayList<LdapServerInfo>();
    	List<LdapServer> ldapEntityList = (List<LdapServer>) getLdapServerDao().loadAll();
    	
    	for (LdapServer ldapServer : ldapEntityList) {
    		ldapList.add(getLdapServerDao().toLdapServerInfo(ldapServer));    					
		}
    	
    	return ldapList;
    }

    /**
     * 
     */
    @Override
    protected java.util.List<LdapServerInfo> handleGetLdapServersByDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain) {
    	List<LdapServerInfo> ldapList = new ArrayList<LdapServerInfo>();
    	AuthenticationDomain authDomain = getAuthenticationDomainDao().load(domain.getId());
    	
    	// if domain does not exist simply return empty list 
    	if(authDomain != null) {
    		Set<LdapServer> ldapServerSet = authDomain.getLdapServers();
    		for (Iterator<LdapServer> iterator = ldapServerSet.iterator(); iterator.hasNext();) {    		
    			LdapServer ldapServer = iterator.next();
    			ldapList.add(getLdapServerDao().toLdapServerInfo(ldapServer));
    		}
    	}
		return ldapList;
    }

    /**
     * 
     */
    @Override
    protected AuthenticationDomainInfo handleCreateDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain) throws Exception {
    	if (StringUtils.isBlank(domain.getName())){
    		throw new LdapConfigurationServiceException("Name of new authentication domain must not be empty!");
    	}
    	AuthenticationDomain authDomainEntity = getAuthenticationDomainDao().create(domain.getName(), domain.getDescription());
    	if(domain.getAttributeMappingId() != null ) {
    		authDomainEntity.setAttributeMapping(getAttributeMappingDao().load(domain.getAttributeMappingId()));        	
    	}    	
    	domain.setId(authDomainEntity.getId());
    	getAuthenticationDomainDao().update(authDomainEntity);
    	
    	return domain;
    }

    /**
     * 
     */
    @Override
    protected void handleDeleteDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain){
    	// also delete ldap servers assigned to that domain
    	List<LdapServerInfo> alsoDeleteServersList = handleGetLdapServersByDomain(domain);
    	for(LdapServerInfo delete : alsoDeleteServersList) {
    		handleDeleteLdapServer(delete);
    	}
    	
    	getAuthenticationDomainDao().remove(domain.getId());
    }

    /**
     * 
     */
    @Override
    protected void handleSaveDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain) {    	
    	if (StringUtils.isBlank(domain.getName())){
    		throw new LdapConfigurationServiceException("Name of new authentication domain must not be empty!");
    	}
//    	TODO check method authenticationDomainInfoToEntity
    	AuthenticationDomain authDomain = getAuthenticationDomainDao().authenticationDomainInfoToEntity(domain);
    	if(domain.getAttributeMappingId() != null ) {
    		authDomain.setAttributeMapping(getAttributeMappingDao().load(domain.getAttributeMappingId()));    		
    	}
    	getAuthenticationDomainDao().update(authDomain);
    }

    /**
     * 
     */
    @Override
    protected java.util.List<AuthenticationDomainInfo> handleGetAllDomains() {
    	List<AuthenticationDomainInfo> authDomainInfoList = new ArrayList<AuthenticationDomainInfo>();
    	
    	List<AuthenticationDomain> authDomainEntityList = (List<AuthenticationDomain>) getAuthenticationDomainDao().loadAll();

    	for (AuthenticationDomain authenticationDomain : authDomainEntityList) {
    		
    		AuthenticationDomainInfo authenticationDomainInfo = getAuthenticationDomainDao().toAuthenticationDomainInfo(authenticationDomain);
    		if(authenticationDomain.getAttributeMapping() != null) {
    			authenticationDomainInfo.setAttributeMappingId(authenticationDomain.getAttributeMapping().getId());
    		}
			authDomainInfoList.add(authenticationDomainInfo);			
		}
    	
    	return authDomainInfoList;    	
    	
    }
    
    /**
     * 
     */
    @Override
    protected AuthenticationDomainInfo handleGetDomainById(Long authDomainId) {
    	AuthenticationDomain authenticationDomain = getAuthenticationDomainDao().load(authDomainId);
    	AuthenticationDomainInfo authenticationDomainInfo = new AuthenticationDomainInfo();
    	authenticationDomainInfo.setId(authenticationDomain.getId());
    	authenticationDomainInfo.setDescription(authenticationDomain.getDescription());
    	authenticationDomainInfo.setName(authenticationDomain.getName());
    	if(authenticationDomain.getAttributeMapping()!= null) {
    		authenticationDomainInfo.setAttributeMappingId(authenticationDomain.getAttributeMapping().getId());    		
    	}    	
    	
    	return authenticationDomainInfo;
    }
    
    /**
     * 
     */
    @Override
    protected void handleAddServerToDomain(org.openuss.security.ldap.LdapServerInfo server, org.openuss.security.ldap.AuthenticationDomainInfo domain) throws Exception {
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

		if (checkDomainContainsServer(ldapServer, authDomain)) throw new LdapConfigurationServiceException("Domain already contains server!");
		
		ldapServer.setAuthenticationDomain(authDomain);
		authDomain.getLdapServers().add(ldapServer);
		
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

	private boolean checkDomainContainsServer(LdapServer server, AuthenticationDomain domain) {
		Set<LdapServer> servers = domain.getLdapServers();
		if (servers.contains(server)) {			
			return true;
		} else return false;
	}

    /**
     * @deprecated remove server from domain not allowed?!
     */
	@Override
	protected void handleRemoveServerFromDomain(org.openuss.security.ldap.LdapServerInfo server, org.openuss.security.ldap.AuthenticationDomainInfo domain){
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

		if (!checkDomainContainsServer(ldapServer, authDomain)) throw new LdapConfigurationServiceException("Server not contained in domain!");
	
		authDomain.getLdapServers().remove(ldapServer);
		ldapServer.setAuthenticationDomain(null);
		
		domainDao.update(authDomain);
		serverDao.update(ldapServer);
    }

    /**
     * 
     */
	@Override
	protected AttributeMappingInfo handleCreateAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMapping) throws Exception {
    	
    	if (StringUtils.isBlank(attributeMapping.getMappingName())) {
    		throw new LdapConfigurationServiceException("Name of new attribute mapping must not be empty!");
    	}
//    	TODO check method attributeMappingInfoToEntity
    	AttributeMapping attributeMappingEntity = getAttributeMappingDao().create(
    			attributeMapping.getEmailKey(), 
    			attributeMapping.getFirstNameKey(), 
    			attributeMapping.getGroupRoleAttributeKey(), 
    			attributeMapping.getLastNameKey(), 
    			attributeMapping.getMappingName(), 
    			//getRoleAttributeKeySetDao().load(attributeMapping.getRoleAttributeKeySetId()),
    			attributeMapping.getUsernameKey()
    		);
       	attributeMapping.setId(attributeMappingEntity.getId());
    	return attributeMapping;    	
    }

    /**
     * 
     */
	@Override
	protected void handleDeleteAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMapping) {
    	getAttributeMappingDao().remove(attributeMapping.getId());
    }

    /**
     * 
     */
	@Override
	protected void handleSaveAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMapping) throws Exception {
    	if (StringUtils.isBlank(attributeMapping.getMappingName())) {
    		throw new LdapConfigurationServiceException("Name of new attribute mapping must not be empty!");
    	}
//    	TODO check method attributeMappingInfoToEntity
    	AttributeMapping attributeMappingEntity = getAttributeMappingDao().attributeMappingInfoToEntity(attributeMapping);
//    	attributeMappingEntity.setRoleAttributeKeySet(getRoleAttributeKeySetDao().load(attributeMapping.getRoleAttributeKeySetId()));
    	getAttributeMappingDao().update(attributeMappingEntity);    	
    }
     
	/**
     * 
     */
    @Override
    protected List<AttributeMappingInfo> handleGetAllAttributeMappings() {
    	
    	List<AttributeMappingInfo> attributeMappingInfoList = new ArrayList<AttributeMappingInfo>();
    	
    	Collection<AttributeMapping> attributeMappingEntityList = getAttributeMappingDao().loadAll();
    	for (Iterator<AttributeMapping> iterator = attributeMappingEntityList.iterator(); iterator.hasNext();) {
			AttributeMapping attributeMappingEntity = (AttributeMapping) iterator.next();
			attributeMappingInfoList.add(getAttributeMappingDao().toAttributeMappingInfo(attributeMappingEntity));			
		}
    	
    	return attributeMappingInfoList;
    }
	
    /**
     * 
     */
	@Override
	protected RoleAttributeKeyInfo handleCreateRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKey){
    	if (StringUtils.isBlank(roleAttributeKey.getName())){
    		throw new LdapConfigurationServiceException("Name of new attribute key must not be empty!");
    	}    	
    	RoleAttributeKey roleAttributeKeyEntity = getRoleAttributeKeyDao().create(roleAttributeKey.getName());    	
    	roleAttributeKey.setId(roleAttributeKeyEntity.getId());
    	
    	return roleAttributeKey;
    }

    /**
     * 
     */
	@Override
	protected void handleDeleteRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKey){
    	getRoleAttributeKeyDao().remove(roleAttributeKey.getId());
    }

    /**
     * 
     */
	@Override
	protected void handleSaveRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKey) throws Exception {
		if (StringUtils.isBlank(roleAttributeKey.getName())){
    		throw new LdapConfigurationServiceException("Name of new attribute key must not be empty!");
    	}    	
		RoleAttributeKey	key = getRoleAttributeKeyDao().roleAttributeKeyInfoToEntity(roleAttributeKey);
    	getRoleAttributeKeyDao().update(key);
    }
	
	/**
     * @TODO: impelement
     */ 
	@Override
	protected void handleAddRoleAttributeKeyToAttributeMapping(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKeyInfo, org.openuss.security.ldap.AttributeMappingInfo attributeMappingInfo){
    	
//		List<RoleAttributeKeyInfo> roleAttributeKeyInfoList = new ArrayList<RoleAttributeKeyInfo>();
//		RoleAttributeKeySet roleAttributeKeySetEntity = getRoleAttributeKeySetDao().load(roleAttributeKeySet.getId());
//    	
//    	List<RoleAttributeKey> roleAttributeKeyEntityList = roleAttributeKeySetEntity.getRoleAttributeKeys();
//    	for (Iterator<RoleAttributeKey> iterator = roleAttributeKeyEntityList.iterator(); iterator.hasNext();) {
//			RoleAttributeKey roleAttributeKeyEntity = iterator.next();    		
//			roleAttributeKeyInfoList.add(getRoleAttributeKeyDao().toRoleAttributeKeyInfo(roleAttributeKeyEntity));
//		}
    }
	
	
	/**
     * @TODO: impelement
     */ 
	@Override
	protected void handleRemoveRoleAttributeKeyFromAttributeMapping(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKeyInfo, org.openuss.security.ldap.AttributeMappingInfo attributeMappingInfo){
    	
//		List<RoleAttributeKeyInfo> roleAttributeKeyInfoList = new ArrayList<RoleAttributeKeyInfo>();
//		RoleAttributeKeySet roleAttributeKeySetEntity = getRoleAttributeKeySetDao().load(roleAttributeKeySet.getId());
//    	
//    	List<RoleAttributeKey> roleAttributeKeyEntityList = roleAttributeKeySetEntity.getRoleAttributeKeys();
//    	for (Iterator<RoleAttributeKey> iterator = roleAttributeKeyEntityList.iterator(); iterator.hasNext();) {
//			RoleAttributeKey roleAttributeKeyEntity = iterator.next();    		
//			roleAttributeKeyInfoList.add(getRoleAttributeKeyDao().toRoleAttributeKeyInfo(roleAttributeKeyEntity));
//		}
    }
	

    /**
     * 
     */
	@Override
	protected java.util.List<RoleAttributeKeyInfo> handleGetAllRoleAttributeKeysByMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMappingInfo){
    	
//		List<RoleAttributeKeyInfo> roleAttributeKeyInfoList = new ArrayList<RoleAttributeKeyInfo>();
//		RoleAttributeKeySet roleAttributeKeySetEntity = getRoleAttributeKeySetDao().load(roleAttributeKeySet.getId());
//    	
//    	List<RoleAttributeKey> roleAttributeKeyEntityList = roleAttributeKeySetEntity.getRoleAttributeKeys();
//    	for (Iterator<RoleAttributeKey> iterator = roleAttributeKeyEntityList.iterator(); iterator.hasNext();) {
//			RoleAttributeKey roleAttributeKeyEntity = iterator.next();    		
//			roleAttributeKeyInfoList.add(getRoleAttributeKeyDao().toRoleAttributeKeyInfo(roleAttributeKeyEntity));
//		}
    	
    	return null;
    }

 

    

	/**
     * 
     */
	@Override
	protected java.util.List<RoleAttributeKeyInfo> handleGetAllRoleAttributeKeys(){
		List<RoleAttributeKeyInfo> keyInfoList = new ArrayList<RoleAttributeKeyInfo>();
    	
    	List<RoleAttributeKey> keyEntityList = (List<RoleAttributeKey>) getRoleAttributeKeyDao().loadAll();

    	for (RoleAttributeKey roleAttributeKey : keyEntityList) {
    		
    		RoleAttributeKeyInfo roleAttributeKeyInfo = getRoleAttributeKeyDao().toRoleAttributeKeyInfo(roleAttributeKey);
			keyInfoList.add(roleAttributeKeyInfo);			
		}
    	
    	return keyInfoList;  
    }
	
  

    
	
	private RoleAttributeKey forceRoleAttributeKeyLoad(RoleAttributeKey key) {
		key = getRoleAttributeKeyDao().load(key.getId());
		if (key == null) {
			throw new LdapConfigurationServiceException("RoleAttributeKey not found!");
		}
		return key;
	}
	
	private AttributeMapping forceAttributeMappingLoad(AttributeMapping mapping) {
		mapping = getAttributeMappingDao().load(mapping.getId());
		if (mapping == null) {
			throw new LdapConfigurationServiceException("AttributeMapping not found");
		}
		return mapping;
	}


	


    

	@Override
	protected void handleAddDomainToAttributeMapping(
			AuthenticationDomainInfo domain, AttributeMappingInfo mapping)
			throws Exception {
		AuthenticationDomain authenticationDomain = getAuthenticationDomainDao().load(domain.getId());		
		AttributeMapping attributeMapping = getAttributeMappingDao().load(mapping.getId());
		authenticationDomain.setAttributeMapping(attributeMapping);
		getAuthenticationDomainDao().update(authenticationDomain);		
	}


	@Override
	protected UserDnPatternInfo handleCreateUserDnPattern(
			UserDnPatternInfo userDnPattern) throws Exception {		
		UserDnPattern userDnPatternEntity = getUserDnPatternDao().create(userDnPattern.getName());
		userDnPattern.setId(userDnPatternEntity.getId());
		
		return userDnPattern;
	}


	@Override
	protected void handleDeleteUserDnPattern(UserDnPatternInfo userDnPattern) throws Exception {
		UserDnPattern userDnPatternEntity = getUserDnPatternDao().load(userDnPattern.getId());
		getUserDnPatternDao().remove(userDnPatternEntity);		
	}
	
	/**
	 * @TODO: implement
	 */
	@Override
	protected void handleAddUserDnPatternToLdapServer(UserDnPatternInfo userDnPatternInfo, LdapServerInfo ldapServerInfo) throws Exception {
		//
	}
	
	/**
	 * @TODO: implement
	 */
	@Override
	protected void handleRemoveUserDnPatternFromLdapServer(UserDnPatternInfo userDnPatternInfo, LdapServerInfo ldapServerInfo) throws Exception {
		//
	}

	/**
	 * @TODO: implement
	 */
	@Override
	protected List<UserDnPatternInfo> handleGetAllUserDnPatterns() throws Exception {
		//
		return null;
	}
	
	/**
	 * @TODO: implement
	 */
	@Override
	protected List<UserDnPatternInfo> handleGetAllUserDnPatternsByLdapServer(LdapServerInfo ldapServerInfo) throws Exception {
		//
		return null;
	}

	/**
	 * 
	 * @see org.openuss.security.ldap.LdapConfigurationServiceBase#handleRemoveDomainFromAttributeMapping(org.openuss.security.ldap.AuthenticationDomainInfo, org.openuss.security.ldap.AttributeMappingInfo)
	 * @deprecated no use for it?!
	 */
	@Override
	protected void handleRemoveDomainFromAttributeMapping(
			AuthenticationDomainInfo domain, AttributeMappingInfo mapping)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * @see org.openuss.security.ldap.LdapConfigurationServiceBase#handleSaveUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo)
	 * @deprecated no use for it?!
	 */
	@Override
	protected void handleSaveUserDnPattern(UserDnPatternInfo userDnPattern)
			throws Exception {
	}
	
	

	


}