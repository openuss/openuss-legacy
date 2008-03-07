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
    	// check if valid ldap configuration otherwise throw exception
    	validateLdapServer(ldapServer);
    	
    	// load related entities (domain + user dn patterns)
    	AuthenticationDomain domainEntity = getAuthenticationDomainDao().load(ldapServer.getAuthenticationDomainId());
    	List<UserDnPattern> userDnPatternEntities = new ArrayList<UserDnPattern>();
    	for (Iterator<UserDnPatternInfo> iterator = ldapServer.getUserDnPatterns().iterator(); iterator.hasNext();) {    		
			UserDnPattern userDnPatternEntity = getUserDnPatternDao().load(iterator.next().getId());
			userDnPatternEntities.add(userDnPatternEntity);
		}
    	
    	// create new ldap server entity
    	LdapServer ldapServerEntity = getLdapServerDao().create(
    			domainEntity,
    			ldapServer.getAuthenticationType(), 
    			ldapServer.getDescription(), 
    			ldapServer.isEnabled(), 
    			ldapServer.getLdapServerType(), 
    			ldapServer.getPort(),
    			ldapServer.getProviderUrl(),
    			ldapServer.getRootDn(), 
    			ldapServer.getUseConnectionPool(),
    			ldapServer.getUseLdapContext(),
    			userDnPatternEntities
    		);
    	
    	// update non-required attributes
    	ldapServerEntity.setManagerDn(ldapServer.getManagerDn());
    	ldapServerEntity.setManagerPassword(ldapServer.getManagerPassword());
    	
    	// also add ldap server to related user dn pattern entities
		getLdapServerDao().update(ldapServerEntity);
		for (UserDnPattern userDnPatternEntity : userDnPatternEntities) {
			userDnPatternEntity.getLdapServers().add(ldapServerEntity);
		}
		getUserDnPatternDao().update(userDnPatternEntities);
		
		// also add ldap server to related authentication domain
		domainEntity.getLdapServers().add(ldapServerEntity);
		getAuthenticationDomainDao().update(domainEntity);
		
    	
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
    	if (ldapServer.getAuthenticationDomainId() == null) {
    		throw new LdapConfigurationServiceException("Specify an authentication domain!");
    	}
    	if (ldapServer.getUserDnPatterns().isEmpty()) {
    		throw new LdapConfigurationServiceException("Specify at least one user dn pattern!");
    	}

    	// TODO: check if root dn is valid
    	// TODO: check if auth type is valid
    	
    	// TODO: check if valid manager DN
    	// TODO: other validation
    }
    
    
    
    /**
     * validates if an url is a valid url and protocol is ldap:// only
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
    	
    	// remove ldap server from user dn patterns
    	List<UserDnPatternInfo> userDnPatternInfos = ldapServer.getUserDnPatterns();
    	for (Iterator<UserDnPatternInfo> iterator = userDnPatternInfos.iterator(); iterator.hasNext();) {    		
			UserDnPattern userDnPattern = getUserDnPatternDao().load(iterator.next().getId());
			userDnPattern.getLdapServers().remove(server);
			getUserDnPatternDao().update(userDnPattern);
		}

    	getLdapServerDao().remove(ldapServer.getId());
    	ldapServer.setId(null);
    }
    
    

    /**
     * 
     */
    @Override
    protected void handleSaveLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServer) throws Exception {
    	// validate if it is a valid ldap configuration
    	validateLdapServer(ldapServer);
    	
    	// TODO: check method ldapServerInfoEntity
    	LdapServer ldapServerEntity = getLdapServerDao().ldapServerInfoToEntity(ldapServer);
    	
    	// remove server from all domains
    	List<AuthenticationDomain> allDomains = (List<AuthenticationDomain>) getAuthenticationDomainDao().loadAll();
    	for (AuthenticationDomain domain : allDomains) {
    		domain.getLdapServers().remove(ldapServerEntity);
    	}
    	getAuthenticationDomainDao().update(allDomains);
    	
    	// remove server from all user dn patterns
    	List<UserDnPattern> allPatterns = (List<UserDnPattern>) getUserDnPatternDao().loadAll();
    	for (UserDnPattern pattern : allPatterns) {
    		pattern.getLdapServers().remove(ldapServerEntity);
    	}
    	getUserDnPatternDao().update(allPatterns);
    	
    	// set domain
    	AuthenticationDomain authenticationDomain = getAuthenticationDomainDao().load(ldapServer.getAuthenticationDomainId());
    	ldapServerEntity.setAuthenticationDomain(authenticationDomain);
    	authenticationDomain.getLdapServers().add(ldapServerEntity);
    	    	
    	// set user dn patterns
    	List<UserDnPattern> userDnPatternEntities = new ArrayList<UserDnPattern>();
    	for (Iterator<UserDnPattern> iterator = ldapServerEntity.getUserDnPatterns().iterator(); iterator.hasNext();) {    		
			UserDnPattern userDnPattern = getUserDnPatternDao().load(iterator.next().getId());
			userDnPattern.getLdapServers().add(ldapServerEntity);
			userDnPatternEntities.add(userDnPattern);
		}
    	ldapServerEntity.setUserDnPatterns(userDnPatternEntities);
    	
    	// save to DB
    	getAuthenticationDomainDao().update(authenticationDomain);
    	getUserDnPatternDao().update(userDnPatternEntities);
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
     * validates authentication domains
     */
    protected void validateAuthenticationDomain(AuthenticationDomainInfo authDomain) throws Exception {
    	if (StringUtils.isBlank(authDomain.getName())){
    		throw new LdapConfigurationServiceException("Name of new authentication domain must not be empty!");
    	}
    	if (authDomain.getAttributeMappingId() == null){
    		throw new LdapConfigurationServiceException("Domain needs an attribute mapping!");
    	}
    }

    /**
     * 
     */
    @Override
    protected AuthenticationDomainInfo handleCreateDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain) throws Exception {
    	validateAuthenticationDomain(domain);
    	
    	AuthenticationDomain authDomainEntity = getAuthenticationDomainDao().create(domain.getName(), domain.getDescription());
    	AttributeMapping attributeMappingEntity = getAttributeMappingDao().load(domain.getAttributeMappingId());
    	attributeMappingEntity.getAuthenticationDomains().add(authDomainEntity);
    	authDomainEntity.setAttributeMapping(attributeMappingEntity);
    	
    	getAuthenticationDomainDao().update(authDomainEntity);
    	getAttributeMappingDao().update(attributeMappingEntity);
    	
    	domain.setId(authDomainEntity.getId());
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
    	
    	// also remove domain from related attribute mapping
    	AttributeMapping attributeMapping = getAttributeMappingDao().load(domain.getAttributeMappingId());
    	attributeMapping.getAuthenticationDomains().remove(domain);
    	getAttributeMappingDao().update(attributeMapping);
    	
    	getAuthenticationDomainDao().remove(domain.getId());
    }

    /**
     * 
     */
    @Override
    protected void handleSaveDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain) throws Exception {    	
    	validateAuthenticationDomain(domain);
    	
    	// TODO check method authenticationDomainInfoToEntity
    	AuthenticationDomain authDomainEntity = getAuthenticationDomainDao().authenticationDomainInfoToEntity(domain);
    	
    	// remove domain from all attribute mappings
    	List<AttributeMapping> allMappings = (List<AttributeMapping>) getAttributeMappingDao().loadAll();
    	for (AttributeMapping mapping : allMappings) {
    		mapping.getAuthenticationDomains().remove(authDomainEntity);
    	}
    	getAttributeMappingDao().update(allMappings);
    	
    	// set attribute mapping
    	AttributeMapping attributeMappingEntity = getAttributeMappingDao().load(domain.getAttributeMappingId());
    	attributeMappingEntity.getAuthenticationDomains().add(authDomainEntity);
    	authDomainEntity.setAttributeMapping(attributeMappingEntity);
    	
    	// save to DB
    	getAuthenticationDomainDao().update(authDomainEntity);
    	getAttributeMappingDao().update(attributeMappingEntity);
    	
    	domain.setId(authDomainEntity.getId());
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
	 * validates an attribute mapping object
	 */
	protected void validateAttributeMapping(AttributeMappingInfo attributeMapping) throws Exception {
		if (StringUtils.isBlank(attributeMapping.getMappingName())) {
    		throw new LdapConfigurationServiceException("Name of new attribute mapping must not be empty!");
    	}
		if (attributeMapping.getRoleAttributeKeys().isEmpty()) {
			throw new LdapConfigurationServiceException("Attribute mapping needs at least one role attribute key!");
		}
	}
	

    /**
     * 
     */
	@Override
	protected AttributeMappingInfo handleCreateAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMapping) throws Exception {
    	validateAttributeMapping(attributeMapping);
    	
    	// load related role attribute keys
    	List<RoleAttributeKey> roleAttributeKeyEntities = new ArrayList<RoleAttributeKey>();
    	for (Iterator<RoleAttributeKeyInfo> iterator = attributeMapping.getRoleAttributeKeys().iterator(); iterator.hasNext();) {    		
			RoleAttributeKey roleAttributeKeyEntity = getRoleAttributeKeyDao().load(iterator.next().getId());
			roleAttributeKeyEntities.add(roleAttributeKeyEntity);
		}
    	
    	AttributeMapping attributeMappingEntity = getAttributeMappingDao().create(
    			attributeMapping.getEmailKey(), 
    			attributeMapping.getFirstNameKey(), 
    			attributeMapping.getGroupRoleAttributeKey(), 
    			attributeMapping.getLastNameKey(), 
    			attributeMapping.getMappingName(),
    			roleAttributeKeyEntities,
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
		AttributeMapping attributeMappingEntity = getAttributeMappingDao().load(attributeMapping.getId());
		
		// also delete related domains
    	Set<AuthenticationDomain> toDeleteList = attributeMappingEntity.getAuthenticationDomains();
    	for(AuthenticationDomain delete : toDeleteList) {
    		handleDeleteDomain(getAuthenticationDomainDao().toAuthenticationDomainInfo(delete));
    	}
		
		// also remove mapping from role attribute keys
    	List<RoleAttributeKeyInfo> roleAttributeKeyInfos = attributeMapping.getRoleAttributeKeys();
    	for (Iterator<RoleAttributeKeyInfo> iterator = roleAttributeKeyInfos.iterator(); iterator.hasNext();) {
    		RoleAttributeKey roleAttributeKey = getRoleAttributeKeyDao().load(iterator.next().getId());
    		roleAttributeKey.getAttributeMappings().remove(attributeMappingEntity);
    		getRoleAttributeKeyDao().update(roleAttributeKey);
    	}
		
		// save to DB
		getAttributeMappingDao().remove(attributeMapping.getId());
    }

    /**
     * 
     */
	@Override
	protected void handleSaveAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMapping) throws Exception {
    	validateAttributeMapping(attributeMapping);
    	
    	AttributeMapping attributeMappingEntity = getAttributeMappingDao().load(attributeMapping.getId());
    	
    	// remove attribute mapping from all role attribute keys
    	List<RoleAttributeKey> allRoleAttributeKeys = (List<RoleAttributeKey>) getRoleAttributeKeyDao().loadAll();
    	for (RoleAttributeKey entity : allRoleAttributeKeys) {
    		entity.getAttributeMappings().remove(attributeMappingEntity);
    	}
    	getRoleAttributeKeyDao().update(allRoleAttributeKeys);
    	
    	
    	// load related role attribute keys
    	List<RoleAttributeKeyInfo> roleAttributeKeyInfos = attributeMapping.getRoleAttributeKeys();
    	List<RoleAttributeKey> roleAttributeKeyEntities = new ArrayList<RoleAttributeKey>();
    	for (RoleAttributeKeyInfo info : roleAttributeKeyInfos) {
    		roleAttributeKeyEntities.add(getRoleAttributeKeyDao().load(info.getId()));
    	}
    	attributeMappingEntity.setRoleAttributeKeys(roleAttributeKeyEntities);

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
     * validates a role attribute key to create or save
     */
    protected void validateRoleAttributeKey(RoleAttributeKeyInfo roleAttributeKeyInfo) throws Exception {
    	if (StringUtils.isBlank(roleAttributeKeyInfo.getName())){
    		throw new LdapConfigurationServiceException("Name of new attribute key must not be empty!");
    	} 
    }
    
    /**
     * 
     */
	@Override
	protected RoleAttributeKeyInfo handleCreateRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKey) throws Exception{
    	validateRoleAttributeKey(roleAttributeKey);
    	
    	RoleAttributeKey roleAttributeKeyEntity = getRoleAttributeKeyDao().create(roleAttributeKey.getName());    	
    	roleAttributeKey.setId(roleAttributeKeyEntity.getId());
    	
    	return roleAttributeKey;
    }

    /**
     * 
     */
	@Override
	protected void handleDeleteRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKey){
		RoleAttributeKey roleAttributeKeyEntity = getRoleAttributeKeyDao().load(roleAttributeKey.getId());
		
		// remove from attribute mappings
		List<AttributeMapping> attributeMappings = roleAttributeKeyEntity.getAttributeMappings();
		for (AttributeMapping mapping : attributeMappings) {
			mapping.getRoleAttributeKeys().remove(roleAttributeKeyEntity);
			// mapping does not contain any role attribute key anymore also delte attribute mapping
			if (mapping.getRoleAttributeKeys().isEmpty()) {
				handleDeleteAttributeMapping(getAttributeMappingDao().toAttributeMappingInfo(mapping));
			} else getAttributeMappingDao().update(mapping);
		}
		
    	getRoleAttributeKeyDao().remove(roleAttributeKey.getId());
    }

	/**
	 * @TODO: anpassen an neues model
	 */
	@Override
	protected void handleSaveRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKey) throws Exception {
		validateRoleAttributeKey(roleAttributeKey);
		
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
		AttributeMapping attributeMappingEntity = getAttributeMappingDao().load(attributeMappingInfo.getId());
		List<RoleAttributeKey> roleAttributeKeyEntities = attributeMappingEntity.getRoleAttributeKeys();
		
		List<RoleAttributeKeyInfo> roleAttributeKeyInfos = new ArrayList<RoleAttributeKeyInfo>();
		for (RoleAttributeKey key : roleAttributeKeyEntities) {
			roleAttributeKeyInfos.add(getRoleAttributeKeyDao().toRoleAttributeKeyInfo(key));
		}
    	
    	return roleAttributeKeyInfos;
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
	
    

	/**
	 * @TODO: anpassen an neues model
	 */
	@Override
	protected void handleAddDomainToAttributeMapping(AuthenticationDomainInfo domain, AttributeMappingInfo mapping) throws Exception {
		AuthenticationDomain authenticationDomain = getAuthenticationDomainDao().load(domain.getId());		
		AttributeMapping attributeMapping = getAttributeMappingDao().load(mapping.getId());
		authenticationDomain.setAttributeMapping(attributeMapping);
		getAuthenticationDomainDao().update(authenticationDomain);		
	}


	/**
	 * @TODO: anpassen an neues model
	 */
	@Override
	protected UserDnPatternInfo handleCreateUserDnPattern(UserDnPatternInfo userDnPattern) throws Exception {		
		UserDnPattern userDnPatternEntity = getUserDnPatternDao().create(userDnPattern.getName());
		userDnPattern.setId(userDnPatternEntity.getId());
		
		return userDnPattern;
	}


	/**
	 * @TODO: anpassen an neues model
	 */
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
	 * 
	 */
	@Override
	protected List<UserDnPatternInfo> handleGetAllUserDnPatterns() throws Exception {
		List<UserDnPattern> userDnPatternEntities = (List<UserDnPattern>) getUserDnPatternDao().loadAll();
		
		List<UserDnPatternInfo> userDnPatternInfos = new ArrayList<UserDnPatternInfo>();
		for (UserDnPattern entity : userDnPatternEntities) {
			userDnPatternInfos.add(getUserDnPatternDao().toUserDnPatternInfo(entity));
		}

		return userDnPatternInfos;
	}
	
	/**
	 * 
	 */
	@Override
	protected List<UserDnPatternInfo> handleGetAllUserDnPatternsByLdapServer(LdapServerInfo ldapServerInfo) throws Exception {
		LdapServer ldapServerEntity = getLdapServerDao().load(ldapServerInfo.getId());
		List<UserDnPattern> userDnPatternEntities = ldapServerEntity.getUserDnPatterns();
		
		List<UserDnPatternInfo> userDnPatternInfos = new ArrayList<UserDnPatternInfo>();
		
		for (UserDnPattern entity : userDnPatternEntities) {
			userDnPatternInfos.add(getUserDnPatternDao().toUserDnPatternInfo(entity));
		}
    	
    	return userDnPatternInfos;
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