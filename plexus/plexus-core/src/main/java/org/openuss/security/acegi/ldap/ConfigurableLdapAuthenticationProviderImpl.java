/**
 * 
 */
package org.openuss.security.acegi.ldap;

import java.util.List;
import java.util.Vector;

import org.acegisecurity.AcegiMessageSource;
import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.AuthenticationManager;
import org.acegisecurity.ldap.DefaultInitialDirContextFactory;
import org.acegisecurity.ldap.InitialDirContextFactory;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.dao.UserCache;
import org.acegisecurity.providers.ldap.LdapAuthenticationProvider;
import org.acegisecurity.userdetails.ldap.LdapUserDetailsMapper;
import org.openuss.security.ldap.LdapConfigurationService;
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
	protected AuthenticationManager authenticationManager;
	protected LdapConfigurationService ldapConfigurationService;
	protected String defaultRole = null;
	protected MessageSourceAccessor messages = AcegiMessageSource.getAccessor();
	protected MessageSource messageSource;
	protected UserCache userCache;
	protected Vector<LdapAuthenticationProvider> ldapAuthenticationProviders = null;

	/** 
	 * @see org.openuss.security.acegi.ldap.ConfigurableLdapAuthenticationProvider#reconfigure()
	 */
	@SuppressWarnings("unchecked")
	public void reconfigure() {
		ldapAuthenticationProviders = null;
		List<LdapServerConfiguration> ldapServerConfigurations = ldapConfigurationService.getEnabledLdapServerConfigurations();

	}

	/* (non-Javadoc)
	 * @see org.acegisecurity.providers.AuthenticationProvider#authenticate(org.acegisecurity.Authentication)
	 */
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
		return null;
	}

	
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
	
	protected LdapUserDetailsMapper newLdapUserDetailsMapper() {
		LdapUserDetailsMapper mapper = new LdapUserDetailsMapper();
		mapper.setConvertToUpperCase(true);
		
		return mapper; 
	}
	
	
	
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.ldapConfigurationService, "A ldapconfigurationservice must be set");
		Assert.notNull(this.userCache, "A user cache must be set");
        Assert.notNull(this.messageSource, "A message source must be set");
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
