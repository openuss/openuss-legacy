package org.openuss.wiki;

import java.util.Date;

/**
 * <p>
 * the valueobject contains informations about the WikiSite
 * </p>
 * <p>
 * 
 * @author Projektseminar WS 07/08, Team Collaboration
 *         </p>
 */
public class WikiSiteContentInfo extends org.openuss.wiki.WikiSiteInfo implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -8200966464701703082L;

	public WikiSiteContentInfo() {
		super();
		this.text = null;
	}

	public WikiSiteContentInfo(String text, Long id, Long wikiSiteId, Long domainId, String name, boolean deleted,
			Long authorId, String authorName, Date creationDate, String note, boolean readOnly, boolean stable) {
		super(id, wikiSiteId, domainId, name, deleted, authorId, authorName, creationDate, note, readOnly, stable);
		this.text = text;
	}

	/**
	 * Copies constructor from other WikiSiteContentInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public WikiSiteContentInfo(WikiSiteContentInfo otherBean) {
		this(otherBean.getText(), otherBean.getId(), otherBean.getWikiSiteId(), otherBean.getDomainId(), otherBean
				.getName(), otherBean.isDeleted(), otherBean.getAuthorId(), otherBean.getAuthorName(), otherBean
				.getCreationDate(), otherBean.getNote(), otherBean.isReadOnly(), otherBean.isStable());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(WikiSiteContentInfo otherBean) {
		this.setText(otherBean.getText());
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

	private String text;

	/**
     * 
     */
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

}