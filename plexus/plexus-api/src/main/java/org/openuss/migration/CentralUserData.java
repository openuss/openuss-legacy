package org.openuss.migration;

public interface CentralUserData {

	public abstract String getUsername();

	public abstract void setUsername(String username);

	public abstract String getFirstName();

	public abstract void setFirstName(String firstName);

	public abstract String getLastName();

	public abstract void setLastName(String lastName);

	public abstract String getEmail();

	public abstract void setEmail(String email);

	public abstract Long getAuthenticationDomainId();

	public abstract void setAuthenticationDomainId(Long authenticationDomainId);

	public abstract String getAuthenticationDomainName();

	public abstract void setAuthenticationDomainName(
			String authenticationDomainName);

}