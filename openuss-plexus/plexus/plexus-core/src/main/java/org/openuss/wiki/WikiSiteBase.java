package org.openuss.wiki;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A WikiSite is related to a course and has a lot of WikiSiteVersions.
 * <p>
 * It has a name and can be marked as deleted.
 * </p>
 * 
 * @author Ingo Düppe
 */
public abstract class WikiSiteBase implements WikiSite, Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -8873604315015819470L;

	private Long id;

	/**
	 * @see org.openuss.wiki.WikiSite#getId()
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @see org.openuss.wiki.WikiSite#setId(Long id)
	 */
	public void setId(Long id) {
		this.id = id;
	}

	private Long domainId;

	/**
	 * @see org.openuss.wiki.WikiSite#getDomainId()
	 */
	public Long getDomainId() {
		return this.domainId;
	}

	/**
	 * @see org.openuss.wiki.WikiSite#setDomainId(Long domainId)
	 */
	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

	private String name;

	/**
	 * @see org.openuss.wiki.WikiSite#getName()
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @see org.openuss.wiki.WikiSite#setName(String name)
	 */
	public void setName(String name) {
		this.name = name;
	}

	private Boolean deleted = Boolean.valueOf(false);

	/**
	 * @see org.openuss.wiki.WikiSite#getDeleted()
	 */
	public Boolean getDeleted() {
		return this.deleted;
	}

	/**
	 * @see org.openuss.wiki.WikiSite#setDeleted(Boolean deleted)
	 */
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	private Boolean readOnly = Boolean.valueOf(false);

	/**
	 * @see org.openuss.wiki.WikiSite#getReadOnly()
	 */
	public Boolean getReadOnly() {
		return this.readOnly;
	}

	/**
	 * @see org.openuss.wiki.WikiSite#setReadOnly(Boolean readOnly)
	 */
	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	private Collection<WikiSiteVersion> wikiSiteVersions = new ArrayList<org.openuss.wiki.WikiSiteVersion>();

	/**
     * 
     */
	public Collection<org.openuss.wiki.WikiSiteVersion> getWikiSiteVersions() {
		return this.wikiSiteVersions;
	}

	public void setWikiSiteVersions(Collection<org.openuss.wiki.WikiSiteVersion> wikiSiteVersions) {
		this.wikiSiteVersions = wikiSiteVersions;
	}

	/**
	 * Returns <code>true</code> if the argument is an WikiSite instance and all
	 * identifiers for this entity equal the identifiers of the argument entity.
	 * Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof WikiSite)) {
			return false;
		}
		final WikiSite that = (WikiSite) object;
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