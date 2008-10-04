package org.openuss.migration;

public class CentralUserDataImpl implements CentralUserData {

	protected String username;
	protected String firstName;
	protected String lastName;
	protected String email;
	protected String authenticationDomainName="";
	protected Long authenticationDomainId;

	public String getUsername() {
		return username;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmail() {
		return email;
	}
	public String getAuthenticationDomainName() {
		return authenticationDomainName;
	}
	public Long getAuthenticationDomainId() {
		return authenticationDomainId;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setAuthenticationDomainName(String authenticationDomainName) {
		this.authenticationDomainName = authenticationDomainName;
	}
	public void setAuthenticationDomainId(Long authenticationDomainId) {
		this.authenticationDomainId = authenticationDomainId;
	}	
}
