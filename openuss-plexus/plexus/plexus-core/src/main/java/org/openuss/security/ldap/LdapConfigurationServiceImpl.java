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
import org.apache.log4j.Logger;
import org.openuss.security.acegi.ldap.LdapServerConfiguration;

/**
 * @see org.openuss.security.ldap.LdapConfigurationService
 * @author Juergen de Braaf
 * @author Damian Kemner
 */
public class LdapConfigurationServiceImpl extends LdapConfigurationServiceBase {

	private static final Logger logger = Logger.getLogger(LdapConfigurationServiceImpl.class);

	/**
	 * @see org.openuss.security.ldap.LdapConfigurationService#getEnabledLdapServerConfigurations()
	 */
	@Override
	protected List<LdapServerConfiguration> handleGetEnabledLdapServerConfigurations() {
		List<LdapServerConfiguration> ldapServerConfigurationList = new ArrayList<LdapServerConfiguration>();
		List<LdapServer> ldapServerList = getLdapServerDao().findAllEnabledServers();
		// logger.debug("ldapServerList.size():"+ldapServerList.size());
		if (ldapServerList != null) {

			for (LdapServer ldapServer : ldapServerList) {

				LdapServerConfiguration ldapServerConfiguration = new LdapServerConfiguration();

				ldapServerConfiguration.setAuthenticationDomainId(ldapServer.getAuthenticationDomain().getId());
				ldapServerConfiguration.setAuthenticationDomainName(ldapServer.getAuthenticationDomain().getName());
				ldapServerConfiguration.setAuthenticationType(ldapServer.getAuthenticationType());
				ldapServerConfiguration.setEmailKey(ldapServer.getAuthenticationDomain().getAttributeMapping().getEmailKey());
				ldapServerConfiguration.setFirstNameKey(ldapServer.getAuthenticationDomain().getAttributeMapping().getFirstNameKey());
				ldapServerConfiguration.setLastNameKey(ldapServer.getAuthenticationDomain().getAttributeMapping().getLastNameKey());
				ldapServerConfiguration.setLdapServerType(ldapServer.getLdapServerType());
				ldapServerConfiguration.setPort(ldapServer.getPort());
				ldapServerConfiguration.setProviderUrl(ldapServer.getProviderUrl());
				ldapServerConfiguration.setGroupRoleAttributeKey(ldapServer.getAuthenticationDomain().getAttributeMapping().getGroupRoleAttributeKey());

				List<RoleAttributeKey> roleAttributeKeys = ldapServer.getAuthenticationDomain().getAttributeMapping()
						.getRoleAttributeKeys();
				String[] roleAttributeKeysStringArray = new String[roleAttributeKeys.size()];
				Iterator<RoleAttributeKey> iterRoleAttributeKey = roleAttributeKeys.iterator();
				for (int i = 0; iterRoleAttributeKey.hasNext(); i++) {
					roleAttributeKeysStringArray[i] = iterRoleAttributeKey.next().getName();
				}
				ldapServerConfiguration.setRoleAttributeKeys(roleAttributeKeysStringArray);

				ldapServerConfiguration.setRootDn(ldapServer.getRootDn());
				ldapServerConfiguration.setUseConnectionPool(ldapServer.getUseConnectionPool());
				ldapServerConfiguration.setUseLdapContext(ldapServer.getUseLdapContext());

				List<UserDnPattern> userDnPatterns = ldapServer.getUserDnPatterns();
				String[] userDnPatternStringArray = new String[userDnPatterns.size()];
				Iterator<UserDnPattern> iterUserDnPattern = userDnPatterns.iterator();
				for (int i = 0; iterUserDnPattern.hasNext(); i++) {
					userDnPatternStringArray[i] = iterUserDnPattern.next().getName();
				}
				ldapServerConfiguration.setUserDnPatterns(userDnPatternStringArray);
				ldapServerConfiguration.setUsernameKey(ldapServer.getAuthenticationDomain().getAttributeMapping()
						.getUsernameKey());
				ldapServerConfigurationList.add(ldapServerConfiguration);
			}
		}

		// TODO has to be tested

		return ldapServerConfigurationList;
	}

	/**
	 * 
	 */
	@Override
	protected LdapServerInfo handleCreateLdapServer(LdapServerInfo ldapServerInfo) throws Exception {
		// check if valid ldap configuration otherwise throw exception
		validateLdapServer(ldapServerInfo);

		// load related entities (domain + user dn patterns)
		AuthenticationDomain domainEntity = getAuthenticationDomainDao().load(ldapServerInfo.getAuthenticationDomainId());
		
		List<UserDnPattern> userDnPatternEntities = new ArrayList<UserDnPattern>();
		for (Iterator<Long> iterator = ldapServerInfo.getUserDnPatternIds().iterator(); iterator.hasNext();) {
			UserDnPattern userDnPatternEntity = getUserDnPatternDao().load(iterator.next());
			userDnPatternEntities.add(userDnPatternEntity);
		}

		LdapServer ldapServer = getLdapServerDao().ldapServerInfoToEntity(ldapServerInfo);
		ldapServer.setAuthenticationDomain(domainEntity);
		ldapServer.setUserDnPatterns(userDnPatternEntities);
		getLdapServerDao().create(ldapServer);

		// update non-required attributes
		ldapServer.setManagerDn(ldapServerInfo.getManagerDn());
		ldapServer.setManagerPassword(ldapServerInfo.getManagerPassword());

		// also add ldap server to related user dn pattern entities
		getLdapServerDao().update(ldapServer);
		for (UserDnPattern userDnPatternEntity : userDnPatternEntities) {
			userDnPatternEntity.getLdapServers().add(ldapServer);
		}
		getUserDnPatternDao().update(userDnPatternEntities);

		// also add ldap server to related authentication domain
		domainEntity.getLdapServers().add(ldapServer);
		getAuthenticationDomainDao().update(domainEntity);

		ldapServerInfo.setId(ldapServer.getId());
		return ldapServerInfo;
	}

	/**
	 * validates an LdapServerInfo object if it contains valid attributes used
	 * to create and save (update) a LdapServer
	 */
	protected void validateLdapServer(LdapServerInfo ldapServer) throws Exception {
		if (StringUtils.isBlank(ldapServer.getProviderUrl())) {
			throw new LdapConfigurationServiceException("URL must not be empty!");
		}
		if (!ldapServer.getProviderUrl().contains("localhost")
				&& !handleIsValidURL(new String[] { "ldap" }, ldapServer.getProviderUrl())) {
			throw new LdapConfigurationServiceException("URL must be a valid ldap-url!");
		}
		if (!(ldapServer.getPort() > 0)) {
			throw new LdapConfigurationServiceException("Port must be not negative!");
		}
		if (ldapServer.getAuthenticationDomainId() == null) {
			throw new LdapConfigurationServiceException("Specify an authentication domain!");
		}
		if (ldapServer.getUserDnPatternIds().isEmpty()) {
			throw new LdapConfigurationServiceException("Specify at least one user dn pattern!");
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
	protected void handleDeleteLdapServer(org.openuss.security.ldap.LdapServerInfo ldapServer) {
		LdapServer server = getLdapServerDao().ldapServerInfoToEntity(ldapServer);

		// remove ldap server from domain
		AuthenticationDomain domain = getAuthenticationDomainDao().load(ldapServer.getAuthenticationDomainId());
		if (domain != null) {
			domain.getLdapServers().remove(server);
			getAuthenticationDomainDao().update(domain);
		}

		// remove ldap server from user dn patterns
		List<Long> userDnPatternIds = ldapServer.getUserDnPatternIds();
		for (Iterator<Long> iterator = userDnPatternIds.iterator(); iterator.hasNext();) {
			UserDnPattern userDnPattern = getUserDnPatternDao().load(iterator.next());
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
	protected void handleSaveLdapServer(LdapServerInfo ldapServer) throws Exception {
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
		AuthenticationDomain authenticationDomain = getAuthenticationDomainDao().load(
				ldapServer.getAuthenticationDomainId());
		ldapServerEntity.setAuthenticationDomain(authenticationDomain);
		authenticationDomain.getLdapServers().add(ldapServerEntity);

		// set user dn patterns
		List<UserDnPattern> userDnPatternEntities = new ArrayList<UserDnPattern>();
		for (Long id: (List<Long>) ldapServer.getUserDnPatternIds()) {
			UserDnPattern userDnPattern = getUserDnPatternDao().load(id);
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

		for (LdapServer ldapServerEntity : ldapEntityList) {
			LdapServerInfo ldapServerInfo = getLdapServerDao().toLdapServerInfo(ldapServerEntity);
			ldapList.add(ldapServerInfo);
		}

		return ldapList;
	}

	/**
	 * 
	 */
	@Override
	protected java.util.List<LdapServerInfo> handleGetLdapServersByDomain(
			org.openuss.security.ldap.AuthenticationDomainInfo domain) {
		List<LdapServerInfo> ldapList = new ArrayList<LdapServerInfo>();
		AuthenticationDomain authDomain = getAuthenticationDomainDao().load(domain.getId());

		// if domain does not exist simply return empty list
		if (authDomain != null) {
			Set<LdapServer> ldapServerSet = authDomain.getLdapServers();
			for (LdapServer ldapServer : ldapServerSet) {
				LdapServerInfo ldapServerInfo = getLdapServerDao().toLdapServerInfo(ldapServer);
				ldapList.add(ldapServerInfo);
			}
		}
		return ldapList;
	}

	/**
	 * validates authentication domains
	 */
	protected void validateAuthenticationDomain(AuthenticationDomainInfo authDomain) throws Exception {
		if (StringUtils.isBlank(authDomain.getName())) {
			throw new LdapConfigurationServiceException("Name of new authentication domain must not be empty!");
		}
		if (authDomain.getAttributeMappingId() == null) {
			throw new LdapConfigurationServiceException("Domain needs an attribute mapping!");
		}
	}

	/**
	 * create a new domain entity by domain info object
	 */
	@Override
	protected AuthenticationDomainInfo handleCreateDomain(AuthenticationDomainInfo domain) throws Exception {
		validateAuthenticationDomain(domain);

		// first load AttributeMapping
		AttributeMapping attributeMappingEntity = getAttributeMappingDao().load(domain.getAttributeMappingId());

		// create AuthenticationDomain
		AuthenticationDomain authDomainEntity = new AuthenticationDomainImpl();
		authDomainEntity.setName(domain.getName());
		authDomainEntity.setDescription(domain.getDescription());
		authDomainEntity.setAttributeMapping(attributeMappingEntity); 
		getAuthenticationDomainDao().create(authDomainEntity);

		if (!StringUtils.isBlank(domain.getChangePasswordUrl())) {
			authDomainEntity.setChangePasswordUrl(domain.getChangePasswordUrl());
			getAuthenticationDomainDao().update(authDomainEntity);
		}

		// save association AuthenticationDomain to AttributeMapping
		attributeMappingEntity.getAuthenticationDomains().add(authDomainEntity);

		// update AttributeMapping entity
		getAttributeMappingDao().update(attributeMappingEntity);

		// save new created id from DB to AuthenticationDomainInfo object
		domain.setId(authDomainEntity.getId());
		return domain;
	}

	/**
	 * 
	 */
	@Override
	protected void handleDeleteDomain(org.openuss.security.ldap.AuthenticationDomainInfo domain) {
		// also delete ldap servers assigned to that domain
		List<LdapServerInfo> alsoDeleteServersList = handleGetLdapServersByDomain(domain);
		for (LdapServerInfo delete : alsoDeleteServersList) {
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
	protected void handleSaveDomain(AuthenticationDomainInfo domainInfo) throws Exception {
		validateAuthenticationDomain(domainInfo);

		// TODO check method authenticationDomainInfoToEntity
		AuthenticationDomain authDomainEntity = getAuthenticationDomainDao().authenticationDomainInfoToEntity(domainInfo);

		// remove domain from all attribute mappings
		List<AttributeMapping> allMappings = (List<AttributeMapping>) getAttributeMappingDao().loadAll();
		for (AttributeMapping mapping : allMappings) {
			mapping.getAuthenticationDomains().remove(authDomainEntity);
		}
		getAttributeMappingDao().update(allMappings);

		// set attribute mapping
		AttributeMapping attributeMappingEntity = getAttributeMappingDao().load(domainInfo.getAttributeMappingId());
		attributeMappingEntity.getAuthenticationDomains().add(authDomainEntity);
		authDomainEntity.setAttributeMapping(attributeMappingEntity);

		// save to DB
		getAuthenticationDomainDao().update(authDomainEntity);
		getAttributeMappingDao().update(attributeMappingEntity);

		domainInfo.setId(authDomainEntity.getId());
	}

	/**
	 * 
	 */
	@Override
	protected java.util.List<AuthenticationDomainInfo> handleGetAllDomains() {
		List<AuthenticationDomainInfo> authDomainInfoList = new ArrayList<AuthenticationDomainInfo>();
		List<AuthenticationDomain> authDomainEntityList = (List<AuthenticationDomain>) getAuthenticationDomainDao()
				.loadAll();

		for (AuthenticationDomain authenticationDomain : authDomainEntityList) {
			AuthenticationDomainInfo authenticationDomainInfo = getAuthenticationDomainDao()
					.toAuthenticationDomainInfo(authenticationDomain);
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
		if (authenticationDomain.getAttributeMapping() != null) {
			authenticationDomainInfo.setAttributeMappingId(authenticationDomain.getAttributeMapping().getId());
		}
		List<Long> ldapServerIds = new ArrayList<Long>();
		for (LdapServer ldapServerEntity : authenticationDomain.getLdapServers()) {
			ldapServerIds.add(ldapServerEntity.getId());
		}
		authenticationDomainInfo.setLdapServerIds(ldapServerIds);

		return authenticationDomainInfo;
	}

	/**
	 * 
	 */
	@Override
	protected void handleAddServerToDomain(LdapServerInfo server, AuthenticationDomainInfo domain) throws Exception {
		AuthenticationDomainDao domainDao = getAuthenticationDomainDao();
		LdapServerDao serverDao = getLdapServerDao();

		LdapServer ldapServer = serverDao.ldapServerInfoToEntity(server);
		AuthenticationDomain authDomain = domainDao.authenticationDomainInfoToEntity(domain);

		Validate.notNull(ldapServer, "Server must not be null");
		Validate.notNull(ldapServer.getId(), "Server must provide a valid id.");
		Validate.notNull(authDomain, "Domain must not be null");
		Validate.notNull(authDomain.getId(), "Domain must provide a valid id.");

		ldapServer = forceServerLoad(ldapServer);
		authDomain = forceDomainLoad(authDomain);

		if (checkDomainContainsServer(ldapServer, authDomain)) {
			throw new LdapConfigurationServiceException("Domain already contains server!");
		}

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
		return servers.contains(server);
	}

	/**
	 * @deprecated remove server from domain not allowed?!
	 */
	@Override
	protected void handleRemoveServerFromDomain(LdapServerInfo server, AuthenticationDomainInfo domain) {
		AuthenticationDomainDao domainDao = getAuthenticationDomainDao();
		LdapServerDao serverDao = getLdapServerDao();

		LdapServer ldapServer = serverDao.ldapServerInfoToEntity(server);
		AuthenticationDomain authDomain = domainDao.authenticationDomainInfoToEntity(domain);

		Validate.notNull(ldapServer, "Server must not be null");
		Validate.notNull(ldapServer.getId(), "Server must provide a valid id.");
		Validate.notNull(authDomain, "Domain must not be null");
		Validate.notNull(authDomain.getId(), "Domain must provide a valid id.");

		ldapServer = forceServerLoad(ldapServer);
		authDomain = forceDomainLoad(authDomain);

		if (!checkDomainContainsServer(ldapServer, authDomain)) {
			throw new LdapConfigurationServiceException("Server not contained in domain!");
		}

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
		if (attributeMapping.getRoleAttributeKeyIds().isEmpty()) {
			throw new LdapConfigurationServiceException("Attribute mapping needs at least one role attribute key!");
		}
	}

	/**
	 * 
	 */
	@Override
	protected AttributeMappingInfo handleCreateAttributeMapping(AttributeMappingInfo attributeMappingInfo) throws Exception {
		validateAttributeMapping(attributeMappingInfo);

		// load related role attribute keys
		List<RoleAttributeKey> roleAttributeKeyEntities = new ArrayList<RoleAttributeKey>();
		
		// TODO check for missing role attributes ids.
		for (Long id : (List<Long>) attributeMappingInfo.getRoleAttributeKeyIds()) {
			RoleAttributeKey roleAttributeKeyEntity = getRoleAttributeKeyDao().load(id);
			roleAttributeKeyEntities.add(roleAttributeKeyEntity);
		}
		
		AttributeMapping attributeMappingEntity = getAttributeMappingDao().attributeMappingInfoToEntity(attributeMappingInfo);
		attributeMappingEntity.setRoleAttributeKeys(roleAttributeKeyEntities);
		
		getAttributeMappingDao().create(attributeMappingEntity);

		for (RoleAttributeKey entity : roleAttributeKeyEntities) {
			entity.getAttributeMappings().add(attributeMappingEntity);
		}
		getRoleAttributeKeyDao().update(roleAttributeKeyEntities);

		attributeMappingInfo.setId(attributeMappingEntity.getId());
		return attributeMappingInfo;
	}

	/**
	 * 
	 */
	@Override
	protected void handleDeleteAttributeMapping(AttributeMappingInfo attributeMapping) {
		AttributeMapping attributeMappingEntity = getAttributeMappingDao().load(attributeMapping.getId());

		// also delete related domains
		Set<AuthenticationDomain> toDeleteList = attributeMappingEntity.getAuthenticationDomains();
		for (AuthenticationDomain delete : toDeleteList) {
			handleDeleteDomain(getAuthenticationDomainDao().toAuthenticationDomainInfo(delete));
		}

		// also remove mapping from role attribute keys
		List<RoleAttributeKey> roleAttributeKeyEntities = attributeMappingEntity.getRoleAttributeKeys();
		for (Iterator<RoleAttributeKey> iterator = roleAttributeKeyEntities.iterator(); iterator.hasNext();) {
			RoleAttributeKey roleAttributeKey = getRoleAttributeKeyDao().load(iterator.next().getId());
			roleAttributeKey.getAttributeMappings().remove(attributeMappingEntity);
			getRoleAttributeKeyDao().update(roleAttributeKey);
		}

		// save to DB
		getAttributeMappingDao().remove(attributeMappingEntity);

		logger.debug("REMOVE ATTRIBUTE MAPPING: " + attributeMapping.getId());
	}

	/**
	 * 
	 */
	@Override
	protected void handleSaveAttributeMapping(AttributeMappingInfo attributeMappingInfo)throws Exception {
		validateAttributeMapping(attributeMappingInfo);

		AttributeMapping attributeMappingEntity = getAttributeMappingDao().load(attributeMappingInfo.getId());

		// remove attribute mapping from all role attribute keys
		List<RoleAttributeKey> allRoleAttributeKeys = (List<RoleAttributeKey>) getRoleAttributeKeyDao().loadAll();
		for (RoleAttributeKey entity : allRoleAttributeKeys) {
			entity.getAttributeMappings().remove(attributeMappingEntity);
		}
		getRoleAttributeKeyDao().update(allRoleAttributeKeys);

		// load related role attribute keys
		List<Long> roleAttributeKeyIds = attributeMappingInfo.getRoleAttributeKeyIds();
		List<RoleAttributeKey> roleAttributeKeyEntities = new ArrayList<RoleAttributeKey>();
		for (Long id : roleAttributeKeyIds) {
			roleAttributeKeyEntities.add(getRoleAttributeKeyDao().load(id));
		}
		
		attributeMappingEntity.setRoleAttributeKeys(roleAttributeKeyEntities);
		attributeMappingEntity.setEmailKey(attributeMappingInfo.getEmailKey());
		attributeMappingEntity.setFirstNameKey(attributeMappingInfo.getFirstNameKey());
		attributeMappingEntity.setGroupRoleAttributeKey(attributeMappingInfo.getGroupRoleAttributeKey());
		attributeMappingEntity.setLastNameKey(attributeMappingInfo.getLastNameKey());
		attributeMappingEntity.setMappingName(attributeMappingInfo.getMappingName());
		attributeMappingEntity.setUsernameKey(attributeMappingInfo.getUsernameKey());

		getAttributeMappingDao().update(attributeMappingEntity);

		for(RoleAttributeKey roleKey: roleAttributeKeyEntities) {
			roleKey.getAttributeMappings().add(attributeMappingEntity);
		}
		getRoleAttributeKeyDao().update(roleAttributeKeyEntities);
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
			AttributeMappingInfo attributeMappingInfo = getAttributeMappingDao().toAttributeMappingInfo(
					attributeMappingEntity);

			attributeMappingInfoList.add(attributeMappingInfo);
		}

		return attributeMappingInfoList;
	}

	/**
	 * validates a role attribute key to create or save
	 */
	protected void validateRoleAttributeKey(RoleAttributeKeyInfo roleAttributeKeyInfo) throws Exception {
		if (StringUtils.isBlank(roleAttributeKeyInfo.getName())) {
			throw new LdapConfigurationServiceException("Name of new attribute key must not be empty!");
		}
	}

	/**
	 * 
	 */
	@Override
	protected RoleAttributeKeyInfo handleCreateRoleAttributeKey(RoleAttributeKeyInfo roleAttributeKey) throws Exception {
		validateRoleAttributeKey(roleAttributeKey);

		RoleAttributeKey roleAttributeKeyEntity = RoleAttributeKey.Factory.newInstance(roleAttributeKey.getName()); 
		getRoleAttributeKeyDao().create(roleAttributeKeyEntity);
		roleAttributeKey.setId(roleAttributeKeyEntity.getId());
		roleAttributeKey.setAttributeMappingIds(roleAttributeKeyEntity.getAttributeMappings());

		return roleAttributeKey;
	}

	/**
	 * 
	 */
	@Override
	protected void handleDeleteRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKey) {
		RoleAttributeKey roleAttributeKeyEntity = getRoleAttributeKeyDao().load(roleAttributeKey.getId());

		// remove from attribute mappings
		List<AttributeMapping> attributeMappings = roleAttributeKeyEntity.getAttributeMappings();
		for (AttributeMapping mapping : attributeMappings) {
			mapping.getRoleAttributeKeys().remove(roleAttributeKeyEntity);
			// mapping does not contain any role attribute key anymore also
			// delte attribute mapping
			if (mapping.getRoleAttributeKeys().isEmpty()) {
				handleDeleteAttributeMapping(getAttributeMappingDao().toAttributeMappingInfo(mapping));
			} else {
				getAttributeMappingDao().update(mapping);
			}
		}

		getRoleAttributeKeyDao().remove(roleAttributeKey.getId());
	}

	/**
	 * @TODO: anpassen an neues model
	 */
	@Override
	protected void handleSaveRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKey)
			throws Exception {
		validateRoleAttributeKey(roleAttributeKey);

		RoleAttributeKey roleAttributeKeyEntity = getRoleAttributeKeyDao().load(roleAttributeKey.getId());
		roleAttributeKeyEntity.setName(roleAttributeKey.getName());
		getRoleAttributeKeyDao().update(roleAttributeKeyEntity);
	}

	/**
	 * 
	 */
	@Override
	protected void handleAddRoleAttributeKeyToAttributeMapping(
			org.openuss.security.ldap.RoleAttributeKeyInfo roleAttributeKeyInfo,
			org.openuss.security.ldap.AttributeMappingInfo attributeMappingInfo) {
		// Load entities
		AttributeMapping attributeMappingEntity = getAttributeMappingDao().load(attributeMappingInfo.getId());
		RoleAttributeKey roleAttributeKeyEntity = getRoleAttributeKeyDao().load(roleAttributeKeyInfo.getId());

		// check if attribute mapping already contains role attribute key
		if ((!attributeMappingEntity.getRoleAttributeKeys().contains(roleAttributeKeyEntity))
				&& (!roleAttributeKeyEntity.getAttributeMappings().contains(attributeMappingEntity))) {
			// otherwise add it
			attributeMappingEntity.getRoleAttributeKeys().add(roleAttributeKeyEntity);
			roleAttributeKeyEntity.getAttributeMappings().add(attributeMappingEntity);

			getAttributeMappingDao().update(attributeMappingEntity);
			getRoleAttributeKeyDao().update(roleAttributeKeyEntity);
			attributeMappingInfo.getRoleAttributeKeyIds().add(roleAttributeKeyEntity.getId());
			roleAttributeKeyInfo.getAttributeMappingIds().add(attributeMappingEntity.getId());
		}
	}

	/**
	 * 
	 */
	@Override
	protected void handleRemoveRoleAttributeKeyFromAttributeMapping(RoleAttributeKeyInfo roleAttributeKeyInfo, AttributeMappingInfo attributeMappingInfo) {
		// Load entities
		AttributeMapping attributeMappingEntity = getAttributeMappingDao().load(attributeMappingInfo.getId());
		RoleAttributeKey roleAttributeKeyEntity = getRoleAttributeKeyDao().load(roleAttributeKeyInfo.getId());

		if (attributeMappingEntity == null || roleAttributeKeyEntity == null) {
			return;
		}

		// check if attribute mapping contains role attribute key
		if ((attributeMappingEntity.getRoleAttributeKeys().contains(roleAttributeKeyEntity))
				&& (roleAttributeKeyEntity.getAttributeMappings().contains(attributeMappingEntity))) {
			logger.debug("handleRemoveRoleAttributeKeyFromAttributeMapping: REMOVE IT!!!");
			attributeMappingEntity.getRoleAttributeKeys().remove(roleAttributeKeyEntity);
			roleAttributeKeyEntity.getAttributeMappings().remove(attributeMappingEntity);

			getAttributeMappingDao().update(attributeMappingEntity);
			getRoleAttributeKeyDao().update(roleAttributeKeyEntity);
		}

		attributeMappingEntity = getAttributeMappingDao().load(attributeMappingInfo.getId());
		if (attributeMappingEntity.getRoleAttributeKeys().size() == 0) {
			logger
					.debug("handleRemoveRoleAttributeKeyFromAttributeMapping: if (attributeMappingEntity.getRoleAttributeKeys().size() == 0");
			handleDeleteAttributeMapping(attributeMappingInfo);
		}

	}

	/**
	 * 
	 */
	@Override
	protected List<RoleAttributeKeyInfo> handleGetAllRoleAttributeKeysByMapping(AttributeMappingInfo attributeMappingInfo) {
		AttributeMapping attributeMappingEntity = getAttributeMappingDao().load(attributeMappingInfo.getId());

		if (attributeMappingEntity != null) {
			List<RoleAttributeKey> roleAttributeKeyEntities = attributeMappingEntity.getRoleAttributeKeys();
			List<RoleAttributeKeyInfo> roleAttributeKeyInfos = new ArrayList<RoleAttributeKeyInfo>();

			for (RoleAttributeKey roleAttributeKeyEntity : roleAttributeKeyEntities) {
				RoleAttributeKeyInfo roleAttributeKeyInfo = getRoleAttributeKeyDao().toRoleAttributeKeyInfo(
						roleAttributeKeyEntity);
				roleAttributeKeyInfos.add(roleAttributeKeyInfo);
			}
			return roleAttributeKeyInfos;
		} else {
			return new ArrayList<RoleAttributeKeyInfo>();
		}
	}

	/**
	 * 
	 */
	@Override
	protected List<RoleAttributeKeyInfo> handleGetAllRoleAttributeKeys() {
		List<RoleAttributeKeyInfo> keyInfoList = new ArrayList<RoleAttributeKeyInfo>();
		List<RoleAttributeKey> keyEntityList = (List<RoleAttributeKey>) getRoleAttributeKeyDao().loadAll();
		for (RoleAttributeKey roleAttributeKey : keyEntityList) {
			RoleAttributeKeyInfo roleAttributeKeyInfo = getRoleAttributeKeyDao().toRoleAttributeKeyInfo(
					roleAttributeKey);
			keyInfoList.add(roleAttributeKeyInfo);
		}

		return keyInfoList;
	}

	/**
	 * 
	 */
	@Override
	protected void handleAddDomainToAttributeMapping(AuthenticationDomainInfo domain, AttributeMappingInfo mapping)
			throws Exception {
		AuthenticationDomain authenticationDomain = getAuthenticationDomainDao().load(domain.getId());
		AttributeMapping attributeMapping = getAttributeMappingDao().load(mapping.getId());
		authenticationDomain.setAttributeMapping(attributeMapping);
		getAuthenticationDomainDao().update(authenticationDomain);
		mapping.getAuthenticationDomainIds().add(authenticationDomain.getId());
	}

	/**
	 * 
	 */
	@Override
	protected UserDnPatternInfo handleCreateUserDnPattern(UserDnPatternInfo userDnPattern) throws Exception {
		UserDnPattern userDnPatternEntity = UserDnPattern.Factory.newInstance(userDnPattern.getName()); 
		getUserDnPatternDao().create(userDnPatternEntity);
		userDnPattern.setId(userDnPatternEntity.getId());

		List<Long> ldapServerIds = new ArrayList<Long>();
		for (LdapServer server : userDnPatternEntity.getLdapServers()) {
			ldapServerIds.add(server.getId());
		}
		userDnPattern.setLdapServerIds(ldapServerIds);

		return userDnPattern;
	}

	/**
	 * 
	 */
	@Override
	protected void handleDeleteUserDnPattern(UserDnPatternInfo userDnPattern) throws Exception {
		UserDnPattern userDnPatternEntity = getUserDnPatternDao().load(userDnPattern.getId());
		getUserDnPatternDao().remove(userDnPatternEntity);
	}

	/**
	 * 
	 */
	@Override
	protected void handleAddUserDnPatternToLdapServer(UserDnPatternInfo userDnPatternInfo, LdapServerInfo ldapServerInfo) throws Exception {

		LdapServer ldapServer = getLdapServerDao().load(ldapServerInfo.getId());
		UserDnPattern userDnPattern = getUserDnPatternDao().load(userDnPatternInfo.getId());
		ldapServer.getUserDnPatterns().add(userDnPattern);

		Set<LdapServer> ldapServers = userDnPattern.getLdapServers();
		ldapServers.add(ldapServer);
		userDnPattern.setLdapServers(ldapServers);

		getLdapServerDao().update(ldapServer);
		getUserDnPatternDao().update(userDnPattern);

		ldapServerInfo.getUserDnPatternIds().add(userDnPattern.getId());
	}

	/**
	 * 
	 */
	@Override
	protected void handleRemoveUserDnPatternFromLdapServer(UserDnPatternInfo userDnPatternInfo,	LdapServerInfo ldapServerInfo) throws Exception {
		// Load entities
		UserDnPattern userDnPatternEntity = getUserDnPatternDao().load(userDnPatternInfo.getId());
		LdapServer ldapServerEntity = getLdapServerDao().load(ldapServerInfo.getId());

		// return if null
		if (userDnPatternEntity == null || ldapServerEntity == null) {
			return;
		}

		userDnPatternEntity.getLdapServers().remove(ldapServerEntity);
		ldapServerEntity.getUserDnPatterns().remove(userDnPatternEntity);

		getLdapServerDao().update(ldapServerEntity);
		getUserDnPatternDao().update(userDnPatternEntity);

	}

	@Override
	protected void handleRemoveDomainFromAttributeMapping(AuthenticationDomainInfo domain, AttributeMappingInfo mapping) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void handleSaveUserDnPattern(UserDnPatternInfo userDnPattern) throws Exception {
		UserDnPattern userDnPatternEntity = getUserDnPatternDao().load(userDnPattern.getId());
		userDnPatternEntity.setName(userDnPattern.getName());
		getUserDnPatternDao().update(userDnPatternEntity);
	}

	// /**
	// * validates a role attribute key to create or save
	// */
	// private void validateUserDnPattern(UserDnPatternInfo userDnPatternInfo)
	// throws Exception {
	// if (StringUtils.isBlank(userDnPatternInfo.getName())){
	// throw new LdapConfigurationServiceException("Name of new user dn pattern
	// musst not be empty!");
	// }
	// }

	/**
	 * 
	 */
	@Override
	protected List<UserDnPatternInfo> handleGetAllUserDnPatterns() throws Exception {
		List<UserDnPattern> userDnPatternEntities = (List<UserDnPattern>) getUserDnPatternDao().loadAll();

		List<UserDnPatternInfo> userDnPatternInfos = new ArrayList<UserDnPatternInfo>();
		for (UserDnPattern userDnPatternEntity : userDnPatternEntities) {
			UserDnPatternInfo userDnPatternInfo = getUserDnPatternDao().toUserDnPatternInfo(userDnPatternEntity);
			userDnPatternInfos.add(userDnPatternInfo);
		}

		return userDnPatternInfos;
	}

	/**
	 * 
	 */
	@Override
	protected List<UserDnPatternInfo> handleGetAllUserDnPatternsByLdapServer(LdapServerInfo ldapServerInfo)
			throws Exception {
		LdapServer ldapServerEntity = getLdapServerDao().load(ldapServerInfo.getId());
		List<UserDnPattern> userDnPatternEntities = ldapServerEntity.getUserDnPatterns();

		List<UserDnPatternInfo> userDnPatternInfos = new ArrayList<UserDnPatternInfo>();

		for (UserDnPattern userDnPatternEntity : userDnPatternEntities) {
			UserDnPatternInfo userDnPatternInfo = getUserDnPatternDao().toUserDnPatternInfo(userDnPatternEntity);
			userDnPatternInfos.add(userDnPatternInfo);
		}

		return userDnPatternInfos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openuss.security.ldap.LdapConfigurationServiceBase#handleGetAttributeMappingById(java.lang.Long)
	 */
	@Override
	protected AttributeMappingInfo handleGetAttributeMappingById(Long id) throws Exception {
		if (id == null) {
			throw new LdapConfigurationServiceException("Cannot load attribute mapping: Id must not be null!");
		}
		AttributeMapping attributeMappingEntity = getAttributeMappingDao().load(id);
		if (attributeMappingEntity == null) {
			throw new LdapConfigurationServiceException("Attribute mapping not found in DB!");
		}

		AttributeMappingInfo attributeMappingInfo = getAttributeMappingDao().toAttributeMappingInfo(
				attributeMappingEntity);

		return attributeMappingInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openuss.security.ldap.LdapConfigurationServiceBase#handleGetLdapServerById(java.lang.Long)
	 */
	@Override
	protected LdapServerInfo handleGetLdapServerById(Long id) throws Exception {
		if (id == null) {
			throw new LdapConfigurationServiceException("Cannot load ldap server: Id must not be null!");
		}
		LdapServer ldapServerEntity = getLdapServerDao().load(id);
		if (ldapServerEntity == null) {
			throw new LdapConfigurationServiceException("Ldap server not found in DB!");
		}

		LdapServerInfo ldapServerInfo = getLdapServerDao().toLdapServerInfo(ldapServerEntity);

		return ldapServerInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openuss.security.ldap.LdapConfigurationServiceBase#handleGetRoleAttributeKeyById(java.lang.Long)
	 */
	@Override
	protected RoleAttributeKeyInfo handleGetRoleAttributeKeyById(Long id) throws Exception {
		if (id == null) {
			throw new LdapConfigurationServiceException("Cannot load role attribute key: Id must not be null!");
		}
		RoleAttributeKey roleAttributeKeyEntity = getRoleAttributeKeyDao().load(id);
		if (roleAttributeKeyEntity == null) {
			throw new LdapConfigurationServiceException("Role attribute key not found in DB!");
		}

		RoleAttributeKeyInfo roleAttributeKeyInfo = getRoleAttributeKeyDao().toRoleAttributeKeyInfo(
				roleAttributeKeyEntity);

		return roleAttributeKeyInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openuss.security.ldap.LdapConfigurationServiceBase#handleGetUserDnPatternById(java.lang.Long)
	 */
	@Override
	protected UserDnPatternInfo handleGetUserDnPatternById(Long id) throws Exception {
		if (id == null) {
			throw new LdapConfigurationServiceException("Cannot load user dn pattern: Id must not be null!");
		}
		UserDnPattern userDnPatternEntity = getUserDnPatternDao().load(id);
		if (userDnPatternEntity == null) {
			throw new LdapConfigurationServiceException("User dn pattern not found in DB!");
		}

		UserDnPatternInfo userDnPatternInfo = getUserDnPatternDao().toUserDnPatternInfo(userDnPatternEntity);

		return userDnPatternInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openuss.security.ldap.LdapConfigurationServiceBase#handleIsValidRoleAttributeKey(org.openuss.security.ldap.RoleAttributeKeyInfo)
	 */
	@Override
	protected boolean handleIsValidRoleAttributeKey(RoleAttributeKeyInfo roleAttributeKey) throws Exception {
		// check if role attribute key already exists
		if (null != getRoleAttributeKeyDao().findByName(roleAttributeKey.getName())) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openuss.security.ldap.LdapConfigurationServiceBase#handleIsValidUserDnPattern(org.openuss.security.ldap.UserDnPatternInfo)
	 */
	@Override
	protected boolean handleIsValidUserDnPattern(UserDnPatternInfo userDnPattern) throws Exception {
		// check if role attribute key already exists
		if (null != getUserDnPatternDao().findByName(userDnPattern.getName())) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * validates if an url is a valid url and protocol
	 * 
	 * @param schemes
	 *            e.g. {"ldap"} {"http"}
	 * @param url
	 * @return boolean
	 */
	@Override
	protected boolean handleIsValidURL(String[] schemes, String url) {
		// String[] schemes = {"ldap"};

		// allow "localhost" as url
		if (url.contains("//localhost")) {
			return true;
		}
		UrlValidator urlValidator = new UrlValidator(schemes);
		return urlValidator.isValid(url);
	}

	@Override
	protected boolean handleIsValidAttributeMappingName(AttributeMappingInfo attributeMappingInfo) throws Exception {
		// check if role AttributeMappingInfo already exists
		if (null != getAttributeMappingDao().findByName(attributeMappingInfo.getMappingName())) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected boolean handleIsValidAuthenticationDomainName(AuthenticationDomainInfo authenticationDomainInfo)
			throws Exception {
		// check if role AuthenticationDomainInfo already exists
		if (null != getAuthenticationDomainDao().findByName(authenticationDomainInfo.getName())) {
			return false;
		} else
			return true;
	}

}