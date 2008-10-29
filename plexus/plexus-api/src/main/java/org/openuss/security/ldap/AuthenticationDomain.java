package org.openuss.security.ldap;

import java.util.Set;

import org.openuss.foundation.DomainObject;

public interface AuthenticationDomain extends DomainObject {

	public Long getId();

	public void setId(Long id);

	public String getName();

	public void setName(String name);

	public String getDescription();

	public void setDescription(String description);

	/**
	 * Optional URL to a web site, where the user can change her central
	 * password.
	 */
	public String getChangePasswordUrl();

	public void setChangePasswordUrl(String changePasswordUrl);

	public Set<org.openuss.security.ldap.LdapServer> getLdapServers();

	public void setLdapServers(Set<org.openuss.security.ldap.LdapServer> ldapServers);

	public AttributeMapping getAttributeMapping();

	public void setAttributeMapping(AttributeMapping attributeMapping);

}