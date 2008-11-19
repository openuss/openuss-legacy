package org.openuss.lecture;

/**
 * <p>
 * An Organisation is a generic term for all institution like University,
 * Department or Institut in this application.
 * </p>
 * <p>
 * It is therefore abstract and contains the characteristics that all
 * institutions have in common - in particularly the administration of the user
 * membership.
 * </p>
 * <p>
 * 
 * @author Ron Haus
 *         </p>
 *         <p>
 * @author Florian Dondorf
 *         </p>
 */
public interface Organisation extends org.openuss.foundation.DomainObject {

	/**
     * 
     */
	public Long getId();

	public void setId(Long id);

	/**
	 * <p>
	 * Name of the organisation.
	 * </p>
	 */
	public String getName();

	public void setName(String name);

	/**
	 * <p>
	 * Short name of the organisation. This name shouldn't contain words longer
	 * than 15 Characters. I.e Wirtschafts- wissenschaftliche Fakultät.
	 * </p>
	 */
	public String getShortName();

	public void setShortName(String shortName);

	/**
     * 
     */
	public String getShortcut();

	public void setShortcut(String shortcut);

	/**
     * 
     */
	public String getDescription();

	public void setDescription(String description);

	/**
     * 
     */
	public String getOwnerName();

	public void setOwnerName(String ownerName);

	/**
     * 
     */
	public String getAddress();

	public void setAddress(String address);

	/**
     * 
     */
	public String getPostcode();

	public void setPostcode(String postcode);

	/**
     * 
     */
	public String getCity();

	public void setCity(String city);

	/**
     * 
     */
	public String getCountry();

	public void setCountry(String country);

	/**
     * 
     */
	public String getTelephone();

	public void setTelephone(String telephone);

	/**
     * 
     */
	public String getTelefax();

	public void setTelefax(String telefax);

	/**
     * 
     */
	public String getWebsite();

	public void setWebsite(String website);

	/**
     * 
     */
	public String getEmail();

	public void setEmail(String email);

	/**
     * 
     */
	public String getLocale();

	public void setLocale(String locale);

	/**
     * 
     */
	public String getTheme();

	public void setTheme(String theme);

	/**
     * 
     */
	public Long getImageId();

	public void setImageId(Long imageId);

	/**
     * 
     */
	public boolean isEnabled();

	public void setEnabled(boolean enabled);

	/**
	 * Modified
	 * 
	 */
	public org.openuss.security.Membership getMembership();

	public void setMembership(org.openuss.security.Membership membership);

	// Interface HibernateEntity.vsl merge-point
}