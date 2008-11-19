package org.openuss.lecture;

/**
 * <p>
 * An Organisation is a generic term for all institution like
 * University, Department or Institut in this application.
 * </p>
 * <p>
 * It is therefore abstract and contains the characteristics that
 * all institutions have in common - in particularly the
 * administration of the user membership.
 * </p>
 * <p>
 * @author Ron Haus
 * </p>
 * <p>
 * @author Florian Dondorf
 * </p>
 */
public abstract class OrganisationBase
    implements Organisation, java.io.Serializable
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 2329306029062867710L;

    private java.lang.Long id;

    /**
     * @see org.openuss.lecture.Organisation#getId()
     */
    public java.lang.Long getId()
    {
        return this.id;
    }

    /**
     * @see org.openuss.lecture.Organisation#setId(java.lang.Long id)
     */
    public void setId(java.lang.Long id)
    {
        this.id = id;
    }

    private java.lang.String name;

    /**
     * @see org.openuss.lecture.Organisation#getName()
     */
    public java.lang.String getName()
    {
        return this.name;
    }

    /**
     * @see org.openuss.lecture.Organisation#setName(java.lang.String name)
     */
    public void setName(java.lang.String name)
    {
        this.name = name;
    }

    private java.lang.String shortName;

    /**
     * @see org.openuss.lecture.Organisation#getShortName()
     */
    public java.lang.String getShortName()
    {
        return this.shortName;
    }

    /**
     * @see org.openuss.lecture.Organisation#setShortName(java.lang.String shortName)
     */
    public void setShortName(java.lang.String shortName)
    {
        this.shortName = shortName;
    }

    private java.lang.String shortcut;

    /**
     * @see org.openuss.lecture.Organisation#getShortcut()
     */
    public java.lang.String getShortcut()
    {
        return this.shortcut;
    }

    /**
     * @see org.openuss.lecture.Organisation#setShortcut(java.lang.String shortcut)
     */
    public void setShortcut(java.lang.String shortcut)
    {
        this.shortcut = shortcut;
    }

    private java.lang.String description;

    /**
     * @see org.openuss.lecture.Organisation#getDescription()
     */
    public java.lang.String getDescription()
    {
        return this.description;
    }

    /**
     * @see org.openuss.lecture.Organisation#setDescription(java.lang.String description)
     */
    public void setDescription(java.lang.String description)
    {
        this.description = description;
    }

    private java.lang.String ownerName;

    /**
     * @see org.openuss.lecture.Organisation#getOwnerName()
     */
    public java.lang.String getOwnerName()
    {
        return this.ownerName;
    }

    /**
     * @see org.openuss.lecture.Organisation#setOwnerName(java.lang.String ownerName)
     */
    public void setOwnerName(java.lang.String ownerName)
    {
        this.ownerName = ownerName;
    }

    private java.lang.String address;

    /**
     * @see org.openuss.lecture.Organisation#getAddress()
     */
    public java.lang.String getAddress()
    {
        return this.address;
    }

    /**
     * @see org.openuss.lecture.Organisation#setAddress(java.lang.String address)
     */
    public void setAddress(java.lang.String address)
    {
        this.address = address;
    }

    private java.lang.String postcode;

    /**
     * @see org.openuss.lecture.Organisation#getPostcode()
     */
    public java.lang.String getPostcode()
    {
        return this.postcode;
    }

    /**
     * @see org.openuss.lecture.Organisation#setPostcode(java.lang.String postcode)
     */
    public void setPostcode(java.lang.String postcode)
    {
        this.postcode = postcode;
    }

    private java.lang.String city;

    /**
     * @see org.openuss.lecture.Organisation#getCity()
     */
    public java.lang.String getCity()
    {
        return this.city;
    }

    /**
     * @see org.openuss.lecture.Organisation#setCity(java.lang.String city)
     */
    public void setCity(java.lang.String city)
    {
        this.city = city;
    }

    private java.lang.String country;

    /**
     * @see org.openuss.lecture.Organisation#getCountry()
     */
    public java.lang.String getCountry()
    {
        return this.country;
    }

    /**
     * @see org.openuss.lecture.Organisation#setCountry(java.lang.String country)
     */
    public void setCountry(java.lang.String country)
    {
        this.country = country;
    }

    private java.lang.String telephone;

    /**
     * @see org.openuss.lecture.Organisation#getTelephone()
     */
    public java.lang.String getTelephone()
    {
        return this.telephone;
    }

    /**
     * @see org.openuss.lecture.Organisation#setTelephone(java.lang.String telephone)
     */
    public void setTelephone(java.lang.String telephone)
    {
        this.telephone = telephone;
    }

    private java.lang.String telefax;

    /**
     * @see org.openuss.lecture.Organisation#getTelefax()
     */
    public java.lang.String getTelefax()
    {
        return this.telefax;
    }

    /**
     * @see org.openuss.lecture.Organisation#setTelefax(java.lang.String telefax)
     */
    public void setTelefax(java.lang.String telefax)
    {
        this.telefax = telefax;
    }

    private java.lang.String website;

    /**
     * @see org.openuss.lecture.Organisation#getWebsite()
     */
    public java.lang.String getWebsite()
    {
        return this.website;
    }

    /**
     * @see org.openuss.lecture.Organisation#setWebsite(java.lang.String website)
     */
    public void setWebsite(java.lang.String website)
    {
        this.website = website;
    }

    private java.lang.String email;

    /**
     * @see org.openuss.lecture.Organisation#getEmail()
     */
    public java.lang.String getEmail()
    {
        return this.email;
    }

    /**
     * @see org.openuss.lecture.Organisation#setEmail(java.lang.String email)
     */
    public void setEmail(java.lang.String email)
    {
        this.email = email;
    }

    private java.lang.String locale;

    /**
     * @see org.openuss.lecture.Organisation#getLocale()
     */
    public java.lang.String getLocale()
    {
        return this.locale;
    }

    /**
     * @see org.openuss.lecture.Organisation#setLocale(java.lang.String locale)
     */
    public void setLocale(java.lang.String locale)
    {
        this.locale = locale;
    }

    private java.lang.String theme;

    /**
     * @see org.openuss.lecture.Organisation#getTheme()
     */
    public java.lang.String getTheme()
    {
        return this.theme;
    }

    /**
     * @see org.openuss.lecture.Organisation#setTheme(java.lang.String theme)
     */
    public void setTheme(java.lang.String theme)
    {
        this.theme = theme;
    }

    private java.lang.Long imageId;

    /**
     * @see org.openuss.lecture.Organisation#getImageId()
     */
    public java.lang.Long getImageId()
    {
        return this.imageId;
    }

    /**
     * @see org.openuss.lecture.Organisation#setImageId(java.lang.Long imageId)
     */
    public void setImageId(java.lang.Long imageId)
    {
        this.imageId = imageId;
    }

    private boolean enabled;

    /**
     * @see org.openuss.lecture.Organisation#isEnabled()
     */
    public boolean isEnabled()
    {
        return this.enabled;
    }

    /**
     * @see org.openuss.lecture.Organisation#setEnabled(boolean enabled)
     */
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    private org.openuss.security.Membership membership;

    /**
     * 
     */
    public org.openuss.security.Membership getMembership()
    {
        return this.membership;
    }

    public void setMembership(org.openuss.security.Membership membership)
    {
        this.membership = membership;
    }

    /**
     * Returns <code>true</code> if the argument is an Organisation instance and all identifiers for this entity
     * equal the identifiers of the argument entity. Returns <code>false</code> otherwise.
     */
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof Organisation))
        {
            return false;
        }
        final Organisation that = (Organisation)object;
        if (this.id == null || that.getId() == null || !this.id.equals(that.getId()))
        {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code based on this entity's identifiers.
     */
    public int hashCode()
    {
        int hashCode = 0;
        hashCode = 29 * hashCode + (id == null ? 0 : id.hashCode());

        return hashCode;
    }

	
// HibernateEntity.vsl merge-point
}