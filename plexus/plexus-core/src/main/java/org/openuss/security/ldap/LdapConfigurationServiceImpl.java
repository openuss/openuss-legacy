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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.validator.UrlValidator;

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
    protected java.util.List<LdapServerInfo> handleGetEnabledLdapServerConfigurations() {
    	    	
    	List<LdapServerInfo> ldapServerInfoList = new ArrayList<LdapServerInfo>();
    	List<LdapServer> ldapServerList = getLdapServerDao().findAllEnabledServers();
    	for (LdapServer ldapServer : ldapServerList) {
			ldapServerInfoList.add(getLdapServerDao().toLdapServerInfo(ldapServer));						
		}   	
    	
    	return ldapServerInfoList;
    }

    /**
     * 
     */
    @Override
    protected LdapServerInfo handleCreateLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServer) throws Exception {
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
    	
    	LdapServer ldapServerEntity = getLdapServerDao().create(
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
    			ldapServer.isEnabled());
    	
    	ldapServerEntity.setAuthenticationDomain(getAuthenticationDomainDao().load(ldapServer.getAuthenticationDomainId()));    	
    	ldapServerEntity.setUserDnPatternSet(getUserDnPatternSetDao().load(ldapServer.getUserDnPatternSetId()));
    	getLdapServerDao().update(ldapServerEntity);
    	
    	ldapServer.setId(ldapServerEntity.getId());
    	
    	return ldapServer;
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
    	getLdapServerDao().remove(ldapServer.getId());
    }

    /**
     * 
     */
    @Override
    protected void handleSaveLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServer) {
//    	TODO check method ldapServerInfoEntity
    	LdapServer ldapServerEntity = getLdapServerDao().ldapServerInfoToEntity(ldapServer);
    	ldapServerEntity.setAuthenticationDomain(getAuthenticationDomainDao().load(ldapServer.getAuthenticationDomainId()));    	
    	ldapServerEntity.setUserDnPatternSet(getUserDnPatternSetDao().load(ldapServer.getUserDnPatternSetId()));    	
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
    	
    	Set<LdapServer> ldapServerSet = authDomain.getLdapServers();
    	for (Iterator<LdapServer> iterator = ldapServerSet.iterator(); iterator.hasNext();) {    		
			LdapServer ldapServer = iterator.next();
			ldapList.add(getLdapServerDao().toLdapServerInfo(ldapServer));
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
	protected AttributeMappingInfo handleCreateAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMapping){
    	
    	if (StringUtils.isBlank(attributeMapping.getMappingName())) {
    		throw new LdapConfigurationServiceException("Name of new attribute mapping must not be empty!");
    	}
//    	TODO check method attributeMappingInfoToEntity
    	AttributeMapping attributeMappingEntity = getAttributeMappingDao().attributeMappingInfoToEntity(attributeMapping);
    	attributeMappingEntity.setRoleAttributeKeySet(getRoleAttributeKeySetDao().load(attributeMapping.getRoleAttributeKeySetId()));
    	getAttributeMappingDao().create(attributeMappingEntity);
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
	protected void handleSaveAttributeMapping(org.openuss.security.ldap.AttributeMappingInfo attributeMapping) {
    	if (StringUtils.isBlank(attributeMapping.getMappingName())) {
    		throw new LdapConfigurationServiceException("Name of new attribute mapping must not be empty!");
    	}
//    	TODO check method attributeMappingInfoToEntity
    	AttributeMapping attributeMappingEntity = getAttributeMappingDao().attributeMappingInfoToEntity(attributeMapping);
    	attributeMappingEntity.setRoleAttributeKeySet(getRoleAttributeKeySetDao().load(attributeMapping.getRoleAttributeKeySetId()));
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
    	if (StringUtils.isBlank(roleAttributeKey.getRoleAttributeKey())){
    		throw new LdapConfigurationServiceException("Name of new attribute key must not be empty!");
    	}    	
    	RoleAttributeKey roleAttributeKeyEntity = getRoleAttributeKeyDao().create(roleAttributeKey.getRoleAttributeKey());    	
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
	protected void handleSaveRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKey){
    	RoleAttributeKey	key = getRoleAttributeKeyDao().roleAttributeKeyInfoToEntity(roleAttributeKey);
    	getRoleAttributeKeyDao().update(key);
    }

    /**
     * 
     */
	@Override
	protected java.util.List<RoleAttributeKeyInfo> handleGetAllAttributeKeysBySet(org.openuss.security.ldap.RoleAttributeKeySetInfo roleAttributeKeySet){
    	
		List<RoleAttributeKeyInfo> roleAttributeKeyInfoList = new ArrayList<RoleAttributeKeyInfo>();
		RoleAttributeKeySet roleAttributeKeySetEntity = getRoleAttributeKeySetDao().load(roleAttributeKeySet.getId());
    	
    	List<RoleAttributeKey> roleAttributeKeyEntityList = roleAttributeKeySetEntity.getRoleAttributeKeys();
    	for (Iterator<RoleAttributeKey> iterator = roleAttributeKeyEntityList.iterator(); iterator.hasNext();) {
			RoleAttributeKey roleAttributeKeyEntity = iterator.next();    		
			roleAttributeKeyInfoList.add(getRoleAttributeKeyDao().toRoleAttributeKeyInfo(roleAttributeKeyEntity));
		}
    	
    	return roleAttributeKeyInfoList;
    }

    /**
     * 
     */
	@Override
	protected RoleAttributeKeySetInfo handleCreateRoleAttributeKeySet(org.openuss.security.ldap.RoleAttributeKeySetInfo roleAttributeKeySet) {    	
    	
		RoleAttributeKeySet roleAttributeKeySetEntity = getRoleAttributeKeySetDao().create(roleAttributeKeySet.getName());
		roleAttributeKeySet.setId(roleAttributeKeySetEntity.getId());
		
    	List<Long> keyIds = roleAttributeKeySet.getRoleAttributeKeyIds();
    	for (Iterator<Long> iterator = keyIds.iterator(); iterator.hasNext();) {
			Long keyId = iterator.next();
			roleAttributeKeySetEntity.addRoleAttributeKey(getRoleAttributeKeyDao().load(keyId));  
		}    	
    	getRoleAttributeKeySetDao().update(roleAttributeKeySetEntity);    	
    	
    	return roleAttributeKeySet;
    }

    /**
     * 
     */
	@Override
	protected void handleDeleteRoleAttributeKeySet(org.openuss.security.ldap.RoleAttributeKeySetInfo roleAttributeKeySet){
    	getRoleAttributeKeySetDao().remove(roleAttributeKeySet.getId());
    }

    /**
     * 
     */
	@Override
	protected void handleSaveRoleAttributeKeySet(org.openuss.security.ldap.RoleAttributeKeySetInfo roleAttributeKeySet){
		RoleAttributeKeySet set = getRoleAttributeKeySetDao().roleAttributeKeySetInfoToEntity(roleAttributeKeySet);
    	
    	List<Long> keyIds = roleAttributeKeySet.getRoleAttributeKeyIds();    	
    	for (Long key : keyIds) {
    		set.addRoleAttributeKey(getRoleAttributeKeyDao().load(key));    			
		}
    	getRoleAttributeKeySetDao().update(set);    	
    }

    /**
     * 
     */
	@Override
	protected java.util.List<RoleAttributeKeySetInfo> handleGetAllRoleAttributeKeySets(){
		List<RoleAttributeKeySetInfo> roleAttributeKeySetInfoList = new ArrayList<RoleAttributeKeySetInfo>();
		Collection<RoleAttributeKeySet> roleAttributeKeySetEntityList = getRoleAttributeKeySetDao().loadAll();
		
		for (Iterator<RoleAttributeKeySet> iterator = roleAttributeKeySetEntityList.iterator(); iterator.hasNext();) {
			RoleAttributeKeySet roleAttributeKeySetEntity = iterator.next();
			RoleAttributeKeySetInfo roleAttributeKeySetInfo = getRoleAttributeKeySetDao().toRoleAttributeKeySetInfo(roleAttributeKeySetEntity);
			roleAttributeKeySetInfo.setRoleAttributeKeyIds(roleAttributeKeySetEntity.getAllRoleAttributeKeyIds());			
		}
		
    	return roleAttributeKeySetInfoList;
    }

    /**
     * 
     */
	@Override
	protected void handleAddRoleAttributeKeyToSet(org.openuss.security.ldap.RoleAttributeKeyInfo key, org.openuss.security.ldap.RoleAttributeKeySetInfo keySet){
    	RoleAttributeKeySetDao 	keySetDao 	= getRoleAttributeKeySetDao();
    	RoleAttributeKeyDao		keyDao 		= getRoleAttributeKeyDao();
    	
//    	TODO check method roleAttributeKeyInfoToEntity
//    	TODO check method roleAttributeKeySetInfoToEntity
    	RoleAttributeKey keyEntity = keyDao.roleAttributeKeyInfoToEntity(key);
    	RoleAttributeKeySet setEntity = keySetDao.roleAttributeKeySetInfoToEntity(keySet);
    	
    	Validate.notNull(keyEntity, "RoleAttributeKey must not be null");
		Validate.notNull(keyEntity.getId(), "RoleAttributeKey must provide a valid id.");
		Validate.notNull(setEntity, "RoleAttributeKeySet must not be null");
		Validate.notNull(setEntity.getId(), "RoleAttributeKeySet must provide a valid id.");

		keyEntity = forceRoleAttributeKeyLoad(keyEntity);
		setEntity = forceRoleAttributeKeySetLoad(setEntity);

		if (checkRoleAttributeKeySetContainsKey(keyEntity, setEntity)) throw new LdapConfigurationServiceException("RoleAttributeKey already contains key!");
	
		setEntity.getRoleAttributeKeys().add(keyEntity);
		keySetDao.update(setEntity);
    }    
	
	private RoleAttributeKeySet forceRoleAttributeKeySetLoad(RoleAttributeKeySet set) {
		set = getRoleAttributeKeySetDao().load(set.getId());
		if (set == null) {
			throw new LdapConfigurationServiceException("RoleAttributeKeySet not found");
		}
		return set;
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

	private boolean checkRoleAttributeKeySetContainsKey(RoleAttributeKey key, RoleAttributeKeySet set) {
		List<RoleAttributeKey> keys = set.getRoleAttributeKeys();
		if (keys.contains(key)) {
			return true;
		} else return false;
	}
	
	
	private boolean checkRoleAttributeKeySetContainsAttributeMapping(AttributeMapping mapping, RoleAttributeKeySet set) {
		List<AttributeMapping> keys = set.getAttributeMappings();
		if (keys.contains(mapping)) {
			return true;
		} else return false;
	}

    /**
     * 
     */
	@Override
	protected void handleRemoveRoleAttributeKeyFromSet(org.openuss.security.ldap.RoleAttributeKeyInfo key, org.openuss.security.ldap.RoleAttributeKeySetInfo keySet){
    	RoleAttributeKeySetDao 	setDao = getRoleAttributeKeySetDao();
    	RoleAttributeKeyDao		keyDao = getRoleAttributeKeyDao();
    	
    	RoleAttributeKey 	keyEntity	= keyDao.roleAttributeKeyInfoToEntity(key);
    	RoleAttributeKeySet setEntity 	= setDao.roleAttributeKeySetInfoToEntity(keySet);
    	
    	Validate.notNull(keyEntity, "RoleAttributeKey must not be null");
		Validate.notNull(keyEntity.getId(), "RoleAttributeKey must provide a valid id.");
		Validate.notNull(setEntity, "RoleAttributeKeySet must not be null");
		Validate.notNull(setEntity.getId(), "RoleAttributeKeySet must provide a valid id.");

		keyEntity = forceRoleAttributeKeyLoad(keyEntity);
		setEntity = forceRoleAttributeKeySetLoad(setEntity);

		if (!checkRoleAttributeKeySetContainsKey(keyEntity, setEntity)) throw new LdapConfigurationServiceException("RoleAttributeKey not contained in KeySet!");
	
		setEntity.getRoleAttributeKeys().remove(keyEntity);
		setDao.update(setEntity);
    }

	@Override
	protected void handleAddAttributeMappingToRoleAttributeKeySet(
			AttributeMappingInfo mapping, RoleAttributeKeySetInfo keySet)
			throws Exception {
		
		RoleAttributeKeySetDao 	keySetDao = getRoleAttributeKeySetDao();
    	AttributeMappingDao		mappingDao = getAttributeMappingDao();
    	
    	RoleAttributeKeySet setEntity = keySetDao.roleAttributeKeySetInfoToEntity(keySet);
//    	TODO check method attributeMappingInfoToEntity
    	AttributeMapping mappingEntity = mappingDao.attributeMappingInfoToEntity(mapping);
    	
    	Validate.notNull(mappingEntity, "AttributeMapping must not be null");
		Validate.notNull(mappingEntity.getId(), "AttributeMapping must provide a valid id.");
		Validate.notNull(setEntity, "RoleAttributeKeySet must not be null");
		Validate.notNull(setEntity.getId(), "RoleAttributeKeySet must provide a valid id.");

		mappingEntity = forceAttributeMappingLoad(mappingEntity);
		setEntity = forceRoleAttributeKeySetLoad(setEntity);

		if (checkRoleAttributeKeySetContainsAttributeMapping(mappingEntity, setEntity)) throw new LdapConfigurationServiceException("RoleAttributeKeySet already contains AttributeMapping!");
	
		setEntity.getAttributeMappings().add(mappingEntity);
		keySetDao.update(setEntity);
		
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
	protected void handleAddServerToUserDnPatternSet(LdapServerInfo server,
			UserDnPatternSetInfo userDnPatternSet) throws Exception {
		LdapServer ldapServer = getLdapServerDao().load(server.getId());
		UserDnPatternSet userDnPatternSetEntity = getUserDnPatternSetDao().load(userDnPatternSet.getId());
		ldapServer.setUserDnPatternSet(userDnPatternSetEntity);
		getLdapServerDao().update(ldapServer);
	}

	@Override
	protected void handleAddUserDnPatternToSet(
			UserDnPatternSetInfo userDnPattern,
			UserDnPatternSetInfo userDnPatternSet) throws Exception {
		UserDnPattern userDnPatternEntity = getUserDnPatternDao().load(userDnPattern.getId());
		UserDnPatternSet userDnPatternSetEntity = getUserDnPatternSetDao().load(userDnPatternSet.getId());				
		userDnPatternSetEntity.addUserDnPattern(userDnPatternEntity);
		getUserDnPatternSetDao().update(userDnPatternSetEntity);		
	}

	@Override
	protected UserDnPatternInfo handleCreateUserDnPattern(
			UserDnPatternInfo userDnPattern) throws Exception {		
		UserDnPattern userDnPatternEntity = getUserDnPatternDao().create(userDnPattern.getName());
		userDnPattern.setId(userDnPatternEntity.getId());
		
		return userDnPattern;
	}

	@Override
	protected UserDnPatternSetInfo handleCreateUserDnPatternSet(
			UserDnPatternSetInfo userDnPatternSet) throws Exception {
		UserDnPatternSet userDnPatternSetEntity = getUserDnPatternSetDao().create(userDnPatternSet.getName());
		
		List<UserDnPattern> userDnPatternEntityList = new ArrayList<UserDnPattern>();
		
		List<Long> idList = userDnPatternSet.getUserDnPatternIds();
		for (Iterator<Long> iterator = idList.iterator(); iterator.hasNext();) {
			userDnPatternEntityList.add(getUserDnPatternDao().load(iterator.next()));			
		}
		userDnPatternSetEntity.setUserDnPatterns(userDnPatternEntityList);	
		
		return userDnPatternSet;
	}

	@Override
	protected void handleDeleteUserDnPattern(UserDnPatternInfo userDnPattern)
			throws Exception {
		UserDnPattern userDnPatternEntity = getUserDnPatternDao().load(userDnPattern.getId());
		getUserDnPatternDao().remove(userDnPatternEntity);		
	}

	@Override
	protected void handleDeleteUserDnPatternSet(
			UserDnPatternSetInfo userDnPatternSet) throws Exception {
		UserDnPatternSet userDnPatternSetEntity = getUserDnPatternSetDao().load(userDnPatternSet.getId());
		getUserDnPatternSetDao().remove(userDnPatternSetEntity);
		
	}

	/**
	 * 
	 * @see org.openuss.security.ldap.LdapConfigurationServiceBase#handleRemoveAttributeMappingFromRoleAttributeKeySet(org.openuss.security.ldap.AttributeMappingInfo, org.openuss.security.ldap.RoleAttributeKeySetInfo)
	 * @deprecated no use for it?!
	 */
	@Override
	protected void handleRemoveAttributeMappingFromRoleAttributeKeySet(
			AttributeMappingInfo mapping, RoleAttributeKeySetInfo keySet)
			throws Exception {
		// TODO Auto-generated method stub
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
	 * @see org.openuss.security.ldap.LdapConfigurationServiceBase#handleRemoveServerFromUserDnPatternSet(org.openuss.security.ldap.LdapServerInfo, org.openuss.security.ldap.UserDnPatternSetInfo)
	 * @deprecated no use for it?!
	 */
	@Override
	protected void handleRemoveServerFromUserDnPatternSet(
			LdapServerInfo server, UserDnPatternSetInfo userDnPatternSet)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleRemoveUserDnPatternFromSet(
			UserDnPatternInfo userDnPattern,
			UserDnPatternSetInfo userDnPatternSet) throws Exception {
		UserDnPattern userDnPatternEntity = getUserDnPatternDao().load(userDnPattern.getId());
		UserDnPatternSet userDnPatternSetEntity = getUserDnPatternSetDao().load(userDnPatternSet.getId());
		userDnPatternSetEntity.removeUserDnPattern(userDnPatternEntity);
		getUserDnPatternSetDao().update(userDnPatternSetEntity);		
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

	@Override
	protected void handleSaveUserDnPatternSet(
			UserDnPatternSetInfo userDnPatternSet) throws Exception {	
		UserDnPatternSet userDnPatternSetEntity = getUserDnPatternSetDao().create(userDnPatternSet.getName());
		List<UserDnPattern> userDnPatternList = new ArrayList<UserDnPattern>();
		List<Long> idList = userDnPatternSet.getUserDnPatternIds();
		for (Iterator<Long> iterator = idList.iterator(); iterator.hasNext();) {
			userDnPatternList.add(getUserDnPatternDao().load(iterator.next()));
		}
		userDnPatternSetEntity.setUserDnPatterns(userDnPatternList);
		getUserDnPatternSetDao().update(userDnPatternSetEntity);
	}


}