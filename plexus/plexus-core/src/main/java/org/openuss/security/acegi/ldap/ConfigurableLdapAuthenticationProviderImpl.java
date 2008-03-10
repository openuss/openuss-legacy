/**
 * 
 */
package org.openuss.security.acegi.ldap;

import java.util.List;
import java.util.Vector;

import javax.naming.NamingException;

import org.acegisecurity.AcegiMessageSource;
import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.ldap.DefaultInitialDirContextFactory;
import org.acegisecurity.ldap.InitialDirContextFactory;
import org.acegisecurity.providers.ProviderManager;
import org.acegisecurity.providers.ProviderNotFoundException;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.dao.UserCache;
import org.acegisecurity.providers.ldap.LdapAuthenticationProvider;
import org.acegisecurity.providers.ldap.LdapAuthenticator;
import org.acegisecurity.providers.ldap.authenticator.BindAuthenticator;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.ldap.LdapUserDetails;
import org.acegisecurity.userdetails.ldap.LdapUserDetailsImpl;
import org.acegisecurity.userdetails.ldap.LdapUserDetailsMapper;
import org.openuss.security.AttributeMappingKeys;
import org.openuss.security.Roles;
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
	protected String defaultRolePrefix = "ROLE_";
	protected MessageSourceAccessor messages = AcegiMessageSource.getAccessor();
	protected MessageSource messageSource;
	protected UserCache userCache;
	protected Vector<LdapAuthenticationProvider> ldapAuthenticationProviders = new Vector<LdapAuthenticationProvider>();
	protected List<LdapServerConfiguration> ldapServerConfigurations = null;

	
	/** 
	 * @see org.openuss.security.acegi.ldap.ConfigurableLdapAuthenticationProvider#reconfigure()
	 */
	@SuppressWarnings("unchecked")
	public void reconfigure() {
		// Reset Providers
		ldapAuthenticationProviders.clear();
		// Get new configurations
		ldapServerConfigurations = ldapConfigurationService.getEnabledLdapServerConfigurations();
		if (ldapServerConfigurations!=null && ldapServerConfigurations.size()>0) {
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
	
	
    //~ Authentication ================================================================================================	
	
	/* (non-Javadoc)
	 * @see org.acegisecurity.providers.AuthenticationProvider#authenticate(org.acegisecurity.Authentication)
	 */
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (ldapAuthenticationProviders.size()==0)
			throw new ProviderNotFoundException(messages.getMessage("ProviderManager.providerNotFound", "No LDAP authentication provider found.")); 

		Authentication authResponse = authenticationManager.authenticate(authentication);
		
		// Authentication successful
		
		LdapUserDetails ldapUserDetails = (LdapUserDetailsImpl) authResponse.getPrincipal();
		LdapServerConfiguration ldapServerConfiguration = retrieveSuccessfulLdapServerConfiguration(ldapUserDetails);
		
		assignAttributes(ldapServerConfiguration, ldapUserDetails);
		ldapUserDetails = assignDefaultRole(ldapUserDetails);
		
		Object principalToReturn = ldapUserDetails;
		
		return createSuccessAuthentication(principalToReturn, authentication, ldapUserDetails);
	}
	
	
	/**
	 * Retrieves configuration of the LDAP server which has successfully authenticated the user.
	 * @param ldapServerConfigurations
	 * @param ldapUserDetails
	 * @return LdapServerConfiguration of corresponding LDAP server
	 */
	protected LdapServerConfiguration retrieveSuccessfulLdapServerConfiguration(LdapUserDetails ldapUserDetails) {
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

		// Assign authentication domain name
		destKey = AttributeMappingKeys.AUTHENTICATIONDOMAINNAME_KEY;
		ldapUserDetails.getAttributes().put(destKey, ldapServerConfiguration.getAuthenticationDomainName());
		
		// Assign username
		destKey = AttributeMappingKeys.USERNAME_KEY;
		sourceKey = ldapServerConfiguration.getUsernameKey();
		Assert.notNull(sourceKey,messages.getMessage("ConfigurableLdapAuthenticationProvider.nullUsernameKey", "Username key must not be null."));		
		try {
			value = (String) ldapUserDetails.getAttributes().get(sourceKey).get();
			ldapUserDetails.getAttributes().put(destKey, value);
		} catch (NamingException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
				
		// Assign firstname
		destKey = AttributeMappingKeys.FIRSTNAME_KEY;
		sourceKey = ldapServerConfiguration.getFirstNameKey();
		Assert.notNull(sourceKey,messages.getMessage("ConfigurableLdapAuthenticationProvider.nullFirstnameKey", "Firstname key must not be null."));
		try {
			value = (String) ldapUserDetails.getAttributes().get(sourceKey).get();
			ldapUserDetails.getAttributes().put(destKey, value);
		} catch (NamingException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		
		// Assign lastname
		destKey = AttributeMappingKeys.LASTNAME_KEY;
		sourceKey = ldapServerConfiguration.getLastNameKey();
		Assert.notNull(sourceKey,messages.getMessage("ConfigurableLdapAuthenticationProvider.nullLastnameKey", "Lastname key must not be null."));
		try {
			value = (String) ldapUserDetails.getAttributes().get(sourceKey).get();
			ldapUserDetails.getAttributes().put(destKey, value);
		} catch (NamingException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		
		// Assign email address
		destKey = AttributeMappingKeys.EMAIL_KEY;
		sourceKey = ldapServerConfiguration.getEmailKey();
		Assert.notNull(sourceKey,messages.getMessage("ConfigurableLdapAuthenticationProvider.nullEmailKey", "E-Mail key must not be null."));
		try {
			value = (String) ldapUserDetails.getAttributes().get(sourceKey).get();
			ldapUserDetails.getAttributes().put(destKey, value);
		} catch (NamingException e) {
			throw new RuntimeException(e.getMessage(),e);
		}		
	}
	
	protected LdapUserDetails assignDefaultRole(LdapUserDetails ldapUserDetails) {
		LdapUserDetailsImpl.Essence essence = new LdapUserDetailsImpl.Essence(ldapUserDetails);
		essence.addAuthority(new GrantedAuthorityImpl(defaultRolePrefix+Roles.LDAPUSER.getName()));
		if (defaultRole!=null && !"".equals(defaultRole))
			essence.addAuthority(new GrantedAuthorityImpl(defaultRole));
		return essence.createUserDetails();
	}
	
   protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        // Ensure we return the original credentials the user supplied,
        // so subsequent attempts are successful even with encoded passwords.
        // Also ensure we return the original getDetails(), so that future
        // authentication events after cache expiry contain the details
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(principal,
        		authentication.getCredentials(), user.getAuthorities());
		result.setDetails(authentication.getDetails());
        return result;
	}
	
	
    //~ Instantiation and initializing methods for provider related objects ================================================================================================

	protected InitialDirContextFactory newInitialDirContextFactory(LdapServerConfiguration ldapServerConfiguration) {
		String url = ldapServerConfiguration.getProviderUrl();
		Assert.notNull(url,messages.getMessage("ConfigurableLdapAuthenticationProvider.nullProviderUrl", "Provider URL must not be null."));
        // preprocess url, so that it ends with the top-level domain. 
		url = url.toLowerCase().replaceAll("\\s+","");
    	url = url + " ";  	
    	url = url.replaceAll("[^a-z]+\\s", "");
    	url = url.toLowerCase().replaceAll("\\s+","");
    	// add port
    	url = url + ":" + ldapServerConfiguration.getPort().toString();
    	// add rootDn
    	String rootDn = ldapServerConfiguration.getRootDn();
		Assert.notNull(rootDn,messages.getMessage("ConfigurableLdapAuthenticationProvider.nullRootD", "Root DN must not be null."));
    	rootDn = rootDn.toLowerCase();
    	rootDn = rootDn.replaceAll("\\s+","");
    	rootDn = rootDn.replaceAll("/+","");
    	url = url + "/"+rootDn;
		DefaultInitialDirContextFactory defaultInitialDirContextFactory = new DefaultInitialDirContextFactory(url);
		String authenticationType = ldapServerConfiguration.getAuthenticationType();
		if (authenticationType == null || "".equals(authenticationType))
			authenticationType = "SIMPLE";
		defaultInitialDirContextFactory.setAuthenticationType(authenticationType.replaceAll("\\s+",""));
		if (ldapServerConfiguration.getManagerDn()!=null) 
			defaultInitialDirContextFactory.setManagerDn(ldapServerConfiguration.getManagerDn().replaceAll("\\s+",""));
		if (ldapServerConfiguration.getManagerPassword()!=null)
			defaultInitialDirContextFactory.setManagerPassword(ldapServerConfiguration.getManagerPassword());
		defaultInitialDirContextFactory.setMessageSource(messageSource);
		defaultInitialDirContextFactory.setUseConnectionPool(ldapServerConfiguration.getUseConnectionPool());
		defaultInitialDirContextFactory.setUseLdapContext(ldapServerConfiguration.getUseLdapContext());
		return defaultInitialDirContextFactory;
	}
	
	protected LdapUserDetailsMapper newLdapUserDetailsMapper(LdapServerConfiguration ldapServerConfiguration) {
		ExtendedLdapUserDetailsMapper extendedLdapUserDetailsMapper = new ExtendedLdapUserDetailsMapper();
		extendedLdapUserDetailsMapper.setRoleAttributes(ldapServerConfiguration.getRoleAttributeKeys());
		if (ldapServerConfiguration.getGroupRoleAttributeKey()!= null)
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


	public String getDefaultRolePrefix() {
		return defaultRolePrefix;
	}


	public void setDefaultRolePrefix(String defaultRolePrefix) {
		this.defaultRolePrefix = defaultRolePrefix;
	}

}
