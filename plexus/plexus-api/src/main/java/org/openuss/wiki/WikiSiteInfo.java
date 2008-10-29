package org.openuss.wiki;

import java.util.Date;

/**
 * <p>
 * the valueobject contains informations about the WikiSiteVersion
 * </p>
 * <p>
 * 
 * @author Projektseminar WS 07/08, Team Collaboration
 *         </p>
 */
public class WikiSiteInfo implements java.io.Serializable, org.openuss.foundation.DomainObject {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 7996113011163353986L;

	public WikiSiteInfo() {
		this.id = null;
		this.wikiSiteId = null;
		this.domainId = null;
		this.name = null;
		this.deleted = false;
		this.authorId = null;
		this.authorName = null;
		this.creationDate = null;
		this.note = null;
		this.readOnly = false;
		this.stable = false;
	}

	public WikiSiteInfo(Long id, Long wikiSiteId, Long domainId, String name, boolean deleted, Long authorId,
			String authorName, Date creationDate, String note, boolean readOnly, boolean stable) {
		this.id = id;
		this.wikiSiteId = wikiSiteId;
		this.domainId = domainId;
		this.name = name;
		this.deleted = deleted;
		this.authorId = authorId;
		this.authorName = authorName;
		this.creationDate = creationDate;
		this.note = note;
		this.readOnly = readOnly;
		this.stable = stable;
	}

	/**
	 * Copies constructor from other WikiSiteInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public WikiSiteInfo(WikiSiteInfo otherBean) {
		this(otherBean.getId(), otherBean.getWikiSiteId(), otherBean.getDomainId(), otherBean.getName(), otherBean
				.isDeleted(), otherBean.getAuthorId(), otherBean.getAuthorName(), otherBean.getCreationDate(),
				otherBean.getNote(), otherBean.isReadOnly(), otherBean.isStable());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(WikiSiteInfo otherBean) {
		this.setId(otherBean.getId());
		this.setWikiSiteId(otherBean.getWikiSiteId());
		this.setDomainId(otherBean.getDomainId());
		this.setName(otherBean.getName());
		this.setDeleted(otherBean.isDeleted());
		this.setAuthorId(otherBean.getAuthorId());
		this.setAuthorName(otherBean.getAuthorName());
		this.setCreationDate(otherBean.getCreationDate());
		this.setNote(otherBean.getNote());
		this.setReadOnly(otherBean.isReadOnly());
		this.setStable(otherBean.isStable());
	}

	private Long id;

	/**
	 * <p>
	 * This is the wikiSiteVersionId that must be named id for DomainObject
	 * reasons.
	 * </p>
	 */
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long wikiSiteId;

	/**
     * 
     */
	public Long getWikiSiteId() {
		return this.wikiSiteId;
	}

	public void setWikiSiteId(Long wikiSiteId) {
		this.wikiSiteId = wikiSiteId;
	}

	private Long domainId;

	/**
     * 
     */
	public Long getDomainId() {
		return this.domainId;
	}

	public void setDomainId(Long domainId) {
		this.domainId = domainId;
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

	private boolean deleted;

	/**
     * 
     */
	public boolean isDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	private Long authorId;

	/**
     * 
     */
	public Long getAuthorId() {
		return this.authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	private String authorName;

	/**
     * 
     */
	public String getAuthorName() {
		return this.authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	private Date creationDate;

	/**
     * 
     */
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	private String note;

	/**
     * 
     */
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	private boolean readOnly;

	/**
     * 
     */
	public boolean isReadOnly() {
		return this.readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	private boolean stable;

	/**
     * 
     */
	public boolean isStable() {
		return this.stable;
	}

	public void setStable(boolean stable) {
		this.stable = stable;
	}

	/**
	 * Returns <code>true</code> if the argument is an WikiSiteInfo instance and
	 * all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof WikiSiteInfo)) {
			return false;
		}
		final WikiSiteInfo that = (WikiSiteInfo) object;
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