package org.openuss.security.acegi.ldap;

/**
 * 
 */
public class LdapServerConfiguration implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 915044076590050618L;

	public LdapServerConfiguration() {
		this.providerUrl = null;
		this.port = null;
		this.rootDn = null;
		this.authenticationType = null;
		this.managerDn = null;
		this.managerPassword = null;
		this.useConnectionPool = null;
		this.useLdapContext = null;
		this.ldapServerType = null;
		this.authenticationDomainId = null;
		this.authenticationDomainName = null;
		this.userDnPatterns = null;
		this.usernameKey = null;
		this.firstNameKey = null;
		this.lastNameKey = null;
		this.emailKey = null;
		this.roleAttributeKeys = null;
		this.groupRoleAttributeKey = null;
	}

	public LdapServerConfiguration(String providerUrl, Integer port, String rootDn, String authenticationType,
			String managerDn, String managerPassword, Boolean useConnectionPool, Boolean useLdapContext,
			org.openuss.security.ldap.LdapServerType ldapServerType, Long authenticationDomainId,
			String authenticationDomainName, String[] userDnPatterns, String usernameKey, String firstNameKey,
			String lastNameKey, String emailKey, String[] roleAttributeKeys, String groupRoleAttributeKey) {
		this.providerUrl = providerUrl;
		this.port = port;
		this.rootDn = rootDn;
		this.authenticationType = authenticationType;
		this.managerDn = managerDn;
		this.managerPassword = managerPassword;
		this.useConnectionPool = useConnectionPool;
		this.useLdapContext = useLdapContext;
		this.ldapServerType = ldapServerType;
		this.authenticationDomainId = authenticationDomainId;
		this.authenticationDomainName = authenticationDomainName;
		this.userDnPatterns = userDnPatterns;
		this.usernameKey = usernameKey;
		this.firstNameKey = firstNameKey;
		this.lastNameKey = lastNameKey;
		this.emailKey = emailKey;
		this.roleAttributeKeys = roleAttributeKeys;
		this.groupRoleAttributeKey = groupRoleAttributeKey;
	}

	/**
	 * Copies constructor from other LdapServerConfiguration
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public LdapServerConfiguration(LdapServerConfiguration otherBean) {
		this(otherBean.getProviderUrl(), otherBean.getPort(), otherBean.getRootDn(), otherBean.getAuthenticationType(),
				otherBean.getManagerDn(), otherBean.getManagerPassword(), otherBean.getUseConnectionPool(), otherBean
						.getUseLdapContext(), otherBean.getLdapServerType(), otherBean.getAuthenticationDomainId(),
				otherBean.getAuthenticationDomainName(), otherBean.getUserDnPatterns(), otherBean.getUsernameKey(),
				otherBean.getFirstNameKey(), otherBean.getLastNameKey(), otherBean.getEmailKey(), otherBean
						.getRoleAttributeKeys(), otherBean.getGroupRoleAttributeKey());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(LdapServerConfiguration otherBean) {
		this.setProviderUrl(otherBean.getProviderUrl());
		this.setPort(otherBean.getPort());
		this.setRootDn(otherBean.getRootDn());
		this.setAuthenticationType(otherBean.getAuthenticationType());
		this.setManagerDn(otherBean.getManagerDn());
		this.setManagerPassword(otherBean.getManagerPassword());
		this.setUseConnectionPool(otherBean.getUseConnectionPool());
		this.setUseLdapContext(otherBean.getUseLdapContext());
		this.setLdapServerType(otherBean.getLdapServerType());
		this.setAuthenticationDomainId(otherBean.getAuthenticationDomainId());
		this.setAuthenticationDomainName(otherBean.getAuthenticationDomainName());
		this.setUserDnPatterns(otherBean.getUserDnPatterns());
		this.setUsernameKey(otherBean.getUsernameKey());
		this.setFirstNameKey(otherBean.getFirstNameKey());
		this.setLastNameKey(otherBean.getLastNameKey());
		this.setEmailKey(otherBean.getEmailKey());
		this.setRoleAttributeKeys(otherBean.getRoleAttributeKeys());
		this.setGroupRoleAttributeKey(otherBean.getGroupRoleAttributeKey());
	}

	private String providerUrl;

	/**
     * 
     */
	public String getProviderUrl() {
		return this.providerUrl;
	}

	public void setProviderUrl(String providerUrl) {
		this.providerUrl = providerUrl;
	}

	private Integer port;

	/**
     * 
     */
	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	private String rootDn;

	/**
     * 
     */
	public String getRootDn() {
		return this.rootDn;
	}

	public void setRootDn(String rootDn) {
		this.rootDn = rootDn;
	}

	private String authenticationType;

	/**
     * 
     */
	public String getAuthenticationType() {
		return this.authenticationType;
	}

	public void setAuthenticationType(String authenticationType) {
		this.authenticationType = authenticationType;
	}

	private String managerDn;

	/**
     * 
     */
	public String getManagerDn() {
		return this.managerDn;
	}

	public void setManagerDn(String managerDn) {
		this.managerDn = managerDn;
	}

	private String managerPassword;

	/**
     * 
     */
	public String getManagerPassword() {
		return this.managerPassword;
	}

	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
	}

	private Boolean useConnectionPool;

	/**
     * 
     */
	public Boolean getUseConnectionPool() {
		return this.useConnectionPool;
	}

	public void setUseConnectionPool(Boolean useConnectionPool) {
		this.useConnectionPool = useConnectionPool;
	}

	private Boolean useLdapContext;

	/**
     * 
     */
	public Boolean getUseLdapContext() {
		return this.useLdapContext;
	}

	public void setUseLdapContext(Boolean useLdapContext) {
		this.useLdapContext = useLdapContext;
	}

	private org.openuss.security.ldap.LdapServerType ldapServerType;

	/**
     * 
     */
	public org.openuss.security.ldap.LdapServerType getLdapServerType() {
		return this.ldapServerType;
	}

	public void setLdapServerType(org.openuss.security.ldap.LdapServerType ldapServerType) {
		this.ldapServerType = ldapServerType;
	}

	private Long authenticationDomainId;

	/**
     * 
     */
	public Long getAuthenticationDomainId() {
		return this.authenticationDomainId;
	}

	public void setAuthenticationDomainId(Long authenticationDomainId) {
		this.authenticationDomainId = authenticationDomainId;
	}

	private String authenticationDomainName;

	/**
     * 
     */
	public String getAuthenticationDomainName() {
		return this.authenticationDomainName;
	}

	public void setAuthenticationDomainName(String authenticationDomainName) {
		this.authenticationDomainName = authenticationDomainName;
	}

	private String[] userDnPatterns;

	/**
     * 
     */
	public String[] getUserDnPatterns() {
		return this.userDnPatterns;
	}

	public void setUserDnPatterns(String[] userDnPatterns) {
		this.userDnPatterns = userDnPatterns;
	}

	private String usernameKey;

	/**
     * 
     */
	public String getUsernameKey() {
		return this.usernameKey;
	}

	public void setUsernameKey(String usernameKey) {
		this.usernameKey = usernameKey;
	}

	private String firstNameKey;

	/**
     * 
     */
	public String getFirstNameKey() {
		return this.firstNameKey;
	}

	public void setFirstNameKey(String firstNameKey) {
		this.firstNameKey = firstNameKey;
	}

	private String lastNameKey;

	/**
     * 
     */
	public String getLastNameKey() {
		return this.lastNameKey;
	}

	public void setLastNameKey(String lastNameKey) {
		this.lastNameKey = lastNameKey;
	}

	private String emailKey;

	/**
     * 
     */
	public String getEmailKey() {
		return this.emailKey;
	}

	public void setEmailKey(String emailKey) {
		this.emailKey = emailKey;
	}

	private String[] roleAttributeKeys;

	/**
     * 
     */
	public String[] getRoleAttributeKeys() {
		return this.roleAttributeKeys;
	}

	public void setRoleAttributeKeys(String[] roleAttributeKeys) {
		this.roleAttributeKeys = roleAttributeKeys;
	}

	private String groupRoleAttributeKey;

	/**
     * 
     */
	public String getGroupRoleAttributeKey() {
		return this.groupRoleAttributeKey;
	}

	public void setGroupRoleAttributeKey(String groupRoleAttributeKey) {
		this.groupRoleAttributeKey = groupRoleAttributeKey;
	}

}