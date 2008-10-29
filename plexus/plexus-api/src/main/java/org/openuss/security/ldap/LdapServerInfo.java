package org.openuss.security.ldap;

import java.util.List;

/**
 * 
 */
public class LdapServerInfo implements java.io.Serializable, org.openuss.foundation.DomainObject {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -3577409487584048932L;

	public LdapServerInfo() {
		this.id = null;
		this.providerUrl = null;
		this.port = null;
		this.rootDn = null;
		this.authenticationType = null;
		this.useConnectionPool = null;
		this.useLdapContext = null;
		this.description = null;
		this.ldapServerType = null;
		this.enabled = false;
		this.authenticationDomainId = null;
		this.userDnPatternIds = null;
	}

	public LdapServerInfo(Long id, String providerUrl, Integer port, String rootDn, String authenticationType,
			Boolean useConnectionPool, Boolean useLdapContext, String description,
			org.openuss.security.ldap.LdapServerType ldapServerType, boolean enabled, Long authenticationDomainId,
			List userDnPatternIds) {
		this.id = id;
		this.providerUrl = providerUrl;
		this.port = port;
		this.rootDn = rootDn;
		this.authenticationType = authenticationType;
		this.useConnectionPool = useConnectionPool;
		this.useLdapContext = useLdapContext;
		this.description = description;
		this.ldapServerType = ldapServerType;
		this.enabled = enabled;
		this.authenticationDomainId = authenticationDomainId;
		this.userDnPatternIds = userDnPatternIds;
	}

	public LdapServerInfo(Long id, String providerUrl, Integer port, String rootDn, String authenticationType,
			String managerDn, String managerPassword, Boolean useConnectionPool, Boolean useLdapContext,
			String description, org.openuss.security.ldap.LdapServerType ldapServerType, boolean enabled,
			Long authenticationDomainId, List userDnPatternIds) {
		this.id = id;
		this.providerUrl = providerUrl;
		this.port = port;
		this.rootDn = rootDn;
		this.authenticationType = authenticationType;
		this.managerDn = managerDn;
		this.managerPassword = managerPassword;
		this.useConnectionPool = useConnectionPool;
		this.useLdapContext = useLdapContext;
		this.description = description;
		this.ldapServerType = ldapServerType;
		this.enabled = enabled;
		this.authenticationDomainId = authenticationDomainId;
		this.userDnPatternIds = userDnPatternIds;
	}

	/**
	 * Copies constructor from other LdapServerInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public LdapServerInfo(LdapServerInfo otherBean) {
		this(otherBean.getId(), otherBean.getProviderUrl(), otherBean.getPort(), otherBean.getRootDn(), otherBean
				.getAuthenticationType(), otherBean.getManagerDn(), otherBean.getManagerPassword(), otherBean
				.getUseConnectionPool(), otherBean.getUseLdapContext(), otherBean.getDescription(), otherBean
				.getLdapServerType(), otherBean.isEnabled(), otherBean.getAuthenticationDomainId(), otherBean
				.getUserDnPatternIds());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(LdapServerInfo otherBean) {
		this.setId(otherBean.getId());
		this.setProviderUrl(otherBean.getProviderUrl());
		this.setPort(otherBean.getPort());
		this.setRootDn(otherBean.getRootDn());
		this.setAuthenticationType(otherBean.getAuthenticationType());
		this.setManagerDn(otherBean.getManagerDn());
		this.setManagerPassword(otherBean.getManagerPassword());
		this.setUseConnectionPool(otherBean.getUseConnectionPool());
		this.setUseLdapContext(otherBean.getUseLdapContext());
		this.setDescription(otherBean.getDescription());
		this.setLdapServerType(otherBean.getLdapServerType());
		this.setEnabled(otherBean.isEnabled());
		this.setAuthenticationDomainId(otherBean.getAuthenticationDomainId());
		this.setUserDnPatternIds(otherBean.getUserDnPatternIds());
	}

	private Long id;

	/**
     * 
     */
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	private Integer port = 389;

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

	private String description;

	/**
     * 
     */
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	private boolean enabled = true;

	/**
     * 
     */
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	private List userDnPatternIds;

	/**
     * 
     */
	public List getUserDnPatternIds() {
		return this.userDnPatternIds;
	}

	public void setUserDnPatternIds(List userDnPatternIds) {
		this.userDnPatternIds = userDnPatternIds;
	}

	/**
	 * Returns <code>true</code> if the argument is an LdapServerInfo instance
	 * and all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof LdapServerInfo)) {
			return false;
		}
		final LdapServerInfo that = (LdapServerInfo) object;
		if (this.id == null || that.getId() == null || !this.id.equals(that.getId())) {
			return false;
		}

		return true;
	}

	/**
	 * Returns a hash code based on this entity's identifiers.
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode = 29 * hashCode + (id == null ? 0 : id.hashCode());

		return hashCode;
	}

}