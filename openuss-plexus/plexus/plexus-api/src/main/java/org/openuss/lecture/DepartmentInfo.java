package org.openuss.lecture;

/**
 *@author Ingo Dueppe
 */
public class DepartmentInfo implements java.io.Serializable, org.openuss.foundation.DomainObject {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -1624820186924459075L;

	public DepartmentInfo() {
		this.id = null;
		this.shortcut = null;
		this.departmentType = null;
		this.ownerName = null;
		this.enabled = false;
		this.defaultDepartment = false;
	}

	public DepartmentInfo(Long id, String shortcut, org.openuss.lecture.DepartmentType departmentType,
			String ownerName, boolean enabled, boolean defaultDepartment) {
		this.id = id;
		this.shortcut = shortcut;
		this.departmentType = departmentType;
		this.ownerName = ownerName;
		this.enabled = enabled;
		this.defaultDepartment = defaultDepartment;
	}

	public DepartmentInfo(Long id, String name, String shortName, String description, String shortcut,
			org.openuss.lecture.DepartmentType departmentType, String ownerName, String address, String postcode,
			String city, String country, String telephone, String telefax, String website, String email, String locale,
			String theme, Long imageId, boolean enabled, Long universityId, boolean defaultDepartment) {
		this.id = id;
		this.name = name;
		this.shortName = shortName;
		this.description = description;
		this.shortcut = shortcut;
		this.departmentType = departmentType;
		this.ownerName = ownerName;
		this.address = address;
		this.postcode = postcode;
		this.city = city;
		this.country = country;
		this.telephone = telephone;
		this.telefax = telefax;
		this.website = website;
		this.email = email;
		this.locale = locale;
		this.theme = theme;
		this.imageId = imageId;
		this.enabled = enabled;
		this.universityId = universityId;
		this.defaultDepartment = defaultDepartment;
	}

	/**
	 * Copies constructor from other DepartmentInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public DepartmentInfo(DepartmentInfo otherBean) {
		this(otherBean.getId(), otherBean.getName(), otherBean.getShortName(), otherBean.getDescription(), otherBean
				.getShortcut(), otherBean.getDepartmentType(), otherBean.getOwnerName(), otherBean.getAddress(),
				otherBean.getPostcode(), otherBean.getCity(), otherBean.getCountry(), otherBean.getTelephone(),
				otherBean.getTelefax(), otherBean.getWebsite(), otherBean.getEmail(), otherBean.getLocale(), otherBean
						.getTheme(), otherBean.getImageId(), otherBean.isEnabled(), otherBean.getUniversityId(),
				otherBean.isDefaultDepartment());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(DepartmentInfo otherBean) {
		this.setId(otherBean.getId());
		this.setName(otherBean.getName());
		this.setShortName(otherBean.getShortName());
		this.setDescription(otherBean.getDescription());
		this.setShortcut(otherBean.getShortcut());
		this.setDepartmentType(otherBean.getDepartmentType());
		this.setOwnerName(otherBean.getOwnerName());
		this.setAddress(otherBean.getAddress());
		this.setPostcode(otherBean.getPostcode());
		this.setCity(otherBean.getCity());
		this.setCountry(otherBean.getCountry());
		this.setTelephone(otherBean.getTelephone());
		this.setTelefax(otherBean.getTelefax());
		this.setWebsite(otherBean.getWebsite());
		this.setEmail(otherBean.getEmail());
		this.setLocale(otherBean.getLocale());
		this.setTheme(otherBean.getTheme());
		this.setImageId(otherBean.getImageId());
		this.setEnabled(otherBean.isEnabled());
		this.setUniversityId(otherBean.getUniversityId());
		this.setDefaultDepartment(otherBean.isDefaultDepartment());
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

	private String name;

	/**
     * 
     */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String shortName;

	/**
     * 
     */
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
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

	private String shortcut;

	/**
     * 
     */
	public String getShortcut() {
		return this.shortcut;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}

	private org.openuss.lecture.DepartmentType departmentType;

	/**
     * 
     */
	public org.openuss.lecture.DepartmentType getDepartmentType() {
		return this.departmentType;
	}

	public void setDepartmentType(org.openuss.lecture.DepartmentType departmentType) {
		this.departmentType = departmentType;
	}

	private String ownerName;

	/**
     * 
     */
	public String getOwnerName() {
		return this.ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	private String address;

	/**
     * 
     */
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	private String postcode;

	/**
     * 
     */
	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	private String city;

	/**
     * 
     */
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	private String country;

	/**
     * 
     */
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	private String telephone;

	/**
     * 
     */
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	private String telefax;

	/**
     * 
     */
	public String getTelefax() {
		return this.telefax;
	}

	public void setTelefax(String telefax) {
		this.telefax = telefax;
	}

	private String website;

	/**
     * 
     */
	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
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

	private String locale;

	/**
     * 
     */
	public String getLocale() {
		return this.locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	private String theme;

	/**
     * 
     */
	public String getTheme() {
		return this.theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	private Long imageId;

	/**
     * 
     */
	public Long getImageId() {
		return this.imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	private boolean enabled;

	/**
     * 
     */
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	private Long universityId;

	/**
     * 
     */
	public Long getUniversityId() {
		return this.universityId;
	}

	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}

	private boolean defaultDepartment;

	/**
     * 
     */
	public boolean isDefaultDepartment() {
		return this.defaultDepartment;
	}

	public void setDefaultDepartment(boolean defaultDepartment) {
		this.defaultDepartment = defaultDepartment;
	}

	/**
	 * Returns <code>true</code> if the argument is an DepartmentInfo instance
	 * and all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof DepartmentInfo)) {
			return false;
		}
		final DepartmentInfo that = (DepartmentInfo) object;
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