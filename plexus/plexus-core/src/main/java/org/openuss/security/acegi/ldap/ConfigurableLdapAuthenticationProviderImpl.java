/**
 * 
 */
package org.openuss.security.acegi.ldap;

import java.util.List;
import java.util.Vector;

import org.acegisecurity.AcegiMessageSource;
import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.ldap.DefaultInitialDirContextFactory;
import org.acegisecurity.ldap.InitialDirContextFactory;
import org.acegisecurity.providers.ProviderManager;
import org.acegisecurity.providers.ProviderNotFoundException;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.dao.UserCache;
import org.acegisecurity.providers.ldap.LdapAuthenticationProvider;
import org.acegisecurity.providers.ldap.LdapAuthenticator;
import org.acegisecurity.providers.ldap.authenticator.BindAuthenticator;
import org.acegisecurity.userdetails.ldap.LdapUserDetails;
import org.acegisecurity.userdetails.ldap.LdapUserDetailsMapper;
import org.openuss.security.AttributeMappingKeys;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.security.ldap.LdapServerType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.Assert;

/**
 * Reads <code>LdapServerConfiguration</code>s using a <code>LdapConfigurationService</code>, sets up a list of
 * Acegi <code>LdapAuthenticationProvider</code>s and iterates an Acegi <code>Authentication</code> request 
 * through it by using an Acegi <code>AuthenticationManager</code>.
 * <p>
 * If the <code>LdapServerConfiguration</code> is null and thus no provider can be instantiated, a <code>ProviderNotFoundException</code>
 * is thrown.
 * Within each <code>LdapAuthenticationProvider</code> a <code>NullAuthoritiesPopulator</code> is used.
 * </p>
 *
 */

/**
 * @author Peter Schuh
 *
 */
public class ConfigurableLdapAuthenticationProviderImpl implements 
			 ConfigurableLdapAuthenticationProvider, MessageSourceAware, InitializingBean {

    //~ Instance fields ================================================================================================
	protected ProviderManager authenticationManager = new ProviderManager();
	protected LdapConfigurationService ldapConfigurationService;
	protected String defaultRole = null;
	protected MessageSourceAccessor messages = AcegiMessageSource.getAccessor();
	protected MessageSource messageSource;
	protected UserCache userCache;
	protected Vector<LdapAuthenticationProvider> ldapAuthenticationProviders = null;
	protected List<LdapServerConfiguration> ldapServerConfigurations = null;

	/** 
	 * @see org.openuss.security.acegi.ldap.ConfigurableLdapAuthenticationProvider#reconfigure()
	 */
	@SuppressWarnings("unchecked")
	public void reconfigure() {
		// Reset Providers
		ldapAuthenticationProviders.clear();
		// Get new configurations
		List<LdapServerConfiguration> ldapServerConfigurations = ldapConfigurationService.getEnabledLdapServerConfigurations();
		if (ldapServerConfigurations.size()>0) {
			// Instantiate and initialize providers and related objects
			for (LdapServerConfiguration ldapServerConfiguration : ldapServerConfigurations) {
				InitialDirContextFactory initialDirContextFactory = newInitialDirContextFactory(ldapServerConfiguration);
				LdapUserDetailsMapper ldapUserDetailsMapper = newLdapUserDetailsMapper(ldapServerConfiguration);
				LdapAuthenticator ldapAuthenticator = null;
				// Consider Microsoft Active Directory specific bind
				if (ldapServerConfiguration.getLdapServerType() == LdapServerType.ACTIVE_DIRECTORY) {
					ldapAuthenticator = newActiveDirectoryBindAuthenticator(ldapServerConfiguration, initialDirContextFactory, messageSource, ldapUserDetailsMapper);
				} else if (ldapServerConfiguration.getLdapServerType() == LdapServerType.OTHER) {
						   ldapAuthenticator = newBindAuthenticator(ldapServerConfiguration, initialDirContextFactory, messageSource, ldapUserDetailsMapper);
					   }
				LdapAuthenticationProvider ldapAuthenticationProvider = new LdapAuthenticationProvider(ldapAuthenticator);
				ldapAuthenticationProvider.setMessageSource(messageSource);
				ldapAuthenticationProvider.setUserCache(userCache);
				try {
					ldapAuthenticationProvider.afterPropertiesSet();
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(),e);
				}
				ldapAuthenticationProviders.add(ldapAuthenticationProvider);			
			}
			authenticationManager.setProviders(ldapAuthenticationProviders);
		}
		authenticationManager.setMessageSource(messageSource);
	}

	
	
	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.acegisecurity.providers.AuthenticationProvider#authenticate(org.acegisecurity.Authentication)
	 */
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (ldapServerConfigurations.size()==0)
			throw new ProviderNotFoundException(messages.getMessage("ProviderManager.providerNotFound", "No LDAP authentication provider found.")); 

		Authentication authResponse = authenticationManager.authenticate(authentication);
		
		// Authentication successful
		
		
		
		return authResponse;
	}
	
	
	/**
	 * Retrieves configuration of the LDAP server used for successful authentication.
	 * @param ldapServerConfigurations
	 * @param ldapUserDetails
	 * @return LdapServerConfiguration of corresponding LDAP server
	 */
	protected LdapServerConfiguration retrieveSuccessfulLdapServerConfiguration(List<LdapServerConfiguration> ldapServerConfigurations, LdapUserDetails ldapUserDetails) {
		LdapServerConfiguration usedLdapServerConfiguration = null;
		String dn = ldapUserDetails.getDn();
		dn = dn.toLowerCase().replaceAll("\\s+","");
		for (LdapServerConfiguration ldapServerConfiguration : ldapServerConfigurations) {
			if (dn.contains(ldapServerConfiguration.getRootDn().toLowerCase()))
				usedLdapServerConfiguration = ldapServerConfiguration;
		}		
		return usedLdapServerConfiguration;		
	}	
	
	protected void assignAttributes(LdapServerConfiguration ldapServerConfiguration, LdapUserDetails ldapUserDetails) {
		String value = "";
		String sourceKey = "";
		String destKey = "";
		
		// Assign authentication domain ID
		destKey = AttributeMappingKeys.AUTHENTICATIONDOMAINID_KEY;
		ldapUserDetails.getAttributes().put(destKey, ldapServerConfiguration.getAuthenticationDomainId());
		
		// Assign username
		
		// Assign firstname
		
		// Assign lastname
		
		// Assign email address
	}
	
	protected void assignDefaultRole(LdapUserDetails ldapUserDetails) {
		
	}
	
	
	
	
	
	
	

    //~ Instantiate and initialize objects ================================================================================================

	protected InitialDirContextFactory newInitialDirContextFactory(LdapServerConfiguration ldapServerConfiguration) {
		String url = ldapServerConfiguration.getProviderUrl();
        // preprocess url, so that it ends with the top-level domain. 
		url = url.toLowerCase().replaceAll("\\s+","");
    	url = url + " ";  	
    	url = url.replaceAll("[^a-z]+\\s", "");
    	// add port
    	url = url + ":" + ldapServerConfiguration.getPort().toString();
    	// add rootDn
    	String rootDn = ldapServerConfiguration.getRootDn().toLowerCase();
    	rootDn = rootDn.replaceAll("\\s+","");
    	rootDn = rootDn.replaceAll("/+","");
    	url = url + "/"+rootDn;
		DefaultInitialDirContextFactory defaultInitialDirContextFactory = new DefaultInitialDirContextFactory(url);
		defaultInitialDirContextFactory.setAuthenticationType(ldapServerConfiguration.getAuthenticationType().replaceAll("\\s+",""));
		defaultInitialDirContextFactory.setManagerDn(ldapServerConfiguration.getManagerDn().replaceAll("\\s+",""));
		defaultInitialDirContextFactory.setManagerPassword(ldapServerConfiguration.getManagerPassword());
		defaultInitialDirContextFactory.setMessageSource(messageSource);
		defaultInitialDirContextFactory.setUseConnectionPool(ldapServerConfiguration.getUseConnectionPool());
		defaultInitialDirContextFactory.setUseLdapContext(ldapServerConfiguration.getUseLdapContext());
		return defaultInitialDirContextFactory;
	}
	
	protected LdapUserDetailsMapper newLdapUserDetailsMapper(LdapServerConfiguration ldapServerConfiguration) {
		ExtendedLdapUserDetailsMapper extendedLdapUserDetailsMapper = new ExtendedLdapUserDetailsMapper();
		extendedLdapUserDetailsMapper.setRoleAttributes(ldapServerConfiguration.getRoleAttributeKeys());
		extendedLdapUserDetailsMapper.setGroupRoleAttributeKey(ldapServerConfiguration.getGroupRoleAttributeKey());
		return extendedLdapUserDetailsMapper;		
	}
	
	protected LdapAuthenticator newBindAuthenticator(LdapServerConfiguration ldapServerConfiguration, InitialDirContextFactory initialDirContextFactory, MessageSource messageSource, LdapUserDetailsMapper ldapUserDetailsMapper) {
		BindAuthenticator bindAuthenticator = new BindAuthenticator(initialDirContextFactory);
		bindAuthenticator.setMessageSource(messageSource);
		bindAuthenticator.setUserDetailsMapper(ldapUserDetailsMapper);
		bindAuthenticator.setUserDnPatterns(ldapServerConfiguration.getUserDnPatterns());
	    try {
			bindAuthenticator.afterPropertiesSet();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}		
		return bindAuthenticator; 
	}
	
	protected LdapAuthenticator newActiveDirectoryBindAuthenticator(LdapServerConfiguration ldapServerConfiguration, InitialDirContextFactory initialDirContextFactory, MessageSource messageSource, LdapUserDetailsMapper ldapUserDetailsMapper) {
		ActiveDirectoryBindAuthenticator activeDirectoryBindAuthenticator = new ActiveDirectoryBindAuthenticator(initialDirContextFactory);
		activeDirectoryBindAuthenticator.setMessageSource(messageSource);
		activeDirectoryBindAuthenticator.setUserDetailsMapper(ldapUserDetailsMapper);
		activeDirectoryBindAuthenticator.setUserDnPatterns(ldapServerConfiguration.getUserDnPatterns());
	    try {
			activeDirectoryBindAuthenticator.afterPropertiesSet();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}		
		return activeDirectoryBindAuthenticator; 
	}
	
	
	
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.ldapConfigurationService, "A ldapconfigurationservice must be set");
		Assert.notNull(this.userCache, "A user cache must be set");
        Assert.notNull(this.messageSource, "A message source must be set");
        reconfigure();
	}
	
	/**
	 * @see org.acegisecurity.providers.AuthenticationProvider#supports(java.lang.Class)
	 */
	public boolean supports(Class authentication) {
	    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
	
		
	//~ Getters and Setters ================================================================================================
	
	/* (non-Javadoc)
	 * @see org.springframework.context.MessageSourceAware#setMessageSource(org.springframework.context.MessageSource)
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
		this.messages = new MessageSourceAccessor(messageSource);
	}

	public LdapConfigurationService getLdapConfigurationService() {		
		return ldapConfigurationService;
	}

	public void setLdapConfigurationService(LdapConfigurationService ldapConfigurationService) {
		Assert.notNull(ldapConfigurationService, "LdapConfigurationService-Property must not be null");
		this.ldapConfigurationService = ldapConfigurationService;		
	}

	public UserCache getUserCache() {
		return userCache;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	public String getDefaultRole() {
		return defaultRole;
	}

	public void setDefaultRole(String defaultRole) {
		this.defaultRole = defaultRole;
	}

}
