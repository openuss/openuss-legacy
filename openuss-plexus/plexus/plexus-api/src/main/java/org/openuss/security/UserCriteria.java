package org.openuss.security;

/**
 * 
 */
public class UserCriteria implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 3693399826273759130L;

	public UserCriteria() {
	}

	/**
	 * Constructor taking all properties.
	 */
	public UserCriteria(String username, String email, Boolean enabled, Boolean accountExpired, Boolean accountLocked,
			Boolean credentialsExpired, String firstName, String lastName)

	{
		this.username = username;
		this.email = email;
		this.enabled = enabled;
		this.accountExpired = accountExpired;
		this.accountLocked = accountLocked;
		this.credentialsExpired = credentialsExpired;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Copies constructor from other UserCriteria
	 */
	public UserCriteria(UserCriteria otherBean) {
		if (otherBean != null) {
			this.username = otherBean.getUsername();
			this.email = otherBean.getEmail();
			this.enabled = otherBean.getEnabled();
			this.accountExpired = otherBean.getAccountExpired();
			this.accountLocked = otherBean.getAccountLocked();
			this.credentialsExpired = otherBean.getCredentialsExpired();
			this.firstName = otherBean.getFirstName();
			this.lastName = otherBean.getLastName();
		}
	}

	/**
	 * The first result to retrieve.
	 */
	private Integer firstResult;

	/**
	 * Gets the first result to retrieve.
	 * 
	 * @return the first result to retrieve
	 */
	public Integer getFirstResult() {
		return this.firstResult;
	}

	/**
	 * Sets the first result to retrieve.
	 * 
	 * @param firstResult
	 *            the first result to retrieve
	 */
	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}

	/**
	 * The fetch size.
	 */
	private Integer fetchSize;

	/**
	 * Gets the fetch size.
	 * 
	 * @return the fetch size
	 */
	public Integer getFetchSize() {
		return this.fetchSize;
	}

	/**
	 * Sets the fetch size.
	 * 
	 * @param fetchSize
	 *            the fetch size
	 */
	public void setFetchSize(Integer fetchSize) {
		this.fetchSize = fetchSize;
	}

	/**
	 * The maximum size of the result set.
	 */
	private Integer maximumResultSize;

	/**
	 * Gets the maximum size of the search result.
	 * 
	 * @return the maximum size of the search result.
	 */
	public Integer getMaximumResultSize() {
		return this.maximumResultSize;
	}

	/**
	 * Sets the maxmimum size of the result.
	 * 
	 * @param maximumResultSize
	 *            A number indicating how many results will be returned.
	 */
	public void setMaximumResultSize(Integer maximumResultSize) {
		this.maximumResultSize = maximumResultSize;
	}

	private String username;

	/**
     * 
     */
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private String email;

	/**
     * 
     */
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private Boolean enabled;

	/**
     * 
     */
	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	private Boolean accountExpired;

	/**
     * 
     */
	public Boolean getAccountExpired() {
		return this.accountExpired;
	}

	public void setAccountExpired(Boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	private Boolean accountLocked;

	/**
     * 
     */
	public Boolean getAccountLocked() {
		return this.accountLocked;
	}

	public void setAccountLocked(Boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	private Boolean credentialsExpired;

	/**
     * 
     */
	public Boolean getCredentialsExpired() {
		return this.credentialsExpired;
	}

	public void setCredentialsExpired(Boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	private String firstName;

	/**
     * 
     */
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	private String lastName;

	/**
     * 
     */
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}