package org.openuss.news;

import java.util.Date;
import java.util.List;

/**
 * @author Ingo Dueppe 
 */
public class NewsItemInfo implements java.io.Serializable, org.openuss.foundation.DomainObject {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 1383263341010459874L;

	public NewsItemInfo() {
		this.id = null;
		this.author = null;
		this.publishDate = null;
		this.expireDate = null;
		this.text = null;
		this.title = null;
		this.publisherName = null;
		this.publisherIdentifier = null;
		this.category = null;
		this.released = false;
		this.expired = false;
		this.publisherType = null;
	}

	public NewsItemInfo(Long id, String author, Date publishDate, Date expireDate, String text, String title,
			String publisherName, Long publisherIdentifier, org.openuss.news.NewsCategory category, boolean released,
			boolean expired, org.openuss.news.PublisherType publisherType) {
		this.id = id;
		this.author = author;
		this.publishDate = publishDate;
		this.expireDate = expireDate;
		this.text = text;
		this.title = title;
		this.publisherName = publisherName;
		this.publisherIdentifier = publisherIdentifier;
		this.category = category;
		this.released = released;
		this.expired = expired;
		this.publisherType = publisherType;
	}

	public NewsItemInfo(Long id, String author, Date publishDate, Date expireDate, String text, String title,
			String publisherName, Long publisherIdentifier, org.openuss.news.NewsCategory category, boolean released,
			boolean expired, org.openuss.news.PublisherType publisherType,
			List<org.openuss.documents.FileInfo> attachments) {
		this.id = id;
		this.author = author;
		this.publishDate = publishDate;
		this.expireDate = expireDate;
		this.text = text;
		this.title = title;
		this.publisherName = publisherName;
		this.publisherIdentifier = publisherIdentifier;
		this.category = category;
		this.released = released;
		this.expired = expired;
		this.publisherType = publisherType;
		this.attachments = attachments;
	}

	/**
	 * Copies constructor from other NewsItemInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public NewsItemInfo(NewsItemInfo otherBean) {
		this(otherBean.getId(), otherBean.getAuthor(), otherBean.getPublishDate(), otherBean.getExpireDate(), otherBean
				.getText(), otherBean.getTitle(), otherBean.getPublisherName(), otherBean.getPublisherIdentifier(),
				otherBean.getCategory(), otherBean.isReleased(), otherBean.isExpired(), otherBean.getPublisherType(),
				otherBean.getAttachments());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(NewsItemInfo otherBean) {
		this.setId(otherBean.getId());
		this.setAuthor(otherBean.getAuthor());
		this.setPublishDate(otherBean.getPublishDate());
		this.setExpireDate(otherBean.getExpireDate());
		this.setText(otherBean.getText());
		this.setTitle(otherBean.getTitle());
		this.setPublisherName(otherBean.getPublisherName());
		this.setPublisherIdentifier(otherBean.getPublisherIdentifier());
		this.setCategory(otherBean.getCategory());
		this.setReleased(otherBean.isReleased());
		this.setExpired(otherBean.isExpired());
		this.setPublisherType(otherBean.getPublisherType());
		this.setAttachments(otherBean.getAttachments());
	}

	private Long id;

	/**
	 * <p>
	 * The primary key for this entiy.
	 * </p>
	 */
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String author;

	/**
     * 
     */
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	private Date publishDate;

	/**
	 * <p>
	 * The point of time until this news should be published. If specified this
	 * property should be greater than <code>dateStart</code>.
	 * </p>
	 */
	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	private Date expireDate;

	/**
	 * <p>
	 * The point of time when this news should be published.
	 * </p>
	 * <p>
	 * This field is mandatory.
	 * </p>
	 */
	public Date getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	private String text;

	/**
	 * <p>
	 * The content of the news. The text will be saved as a html input. So the
	 * user can use <code>HTML</code> tags.
	 * </p>
	 * <p>
	 * This field is mandatory.
	 * </p>
	 */
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private String title;

	/**
	 * <p>
	 * the title of the news
	 * </p>
	 */
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private String publisherName;

	/**
	 * <p>
	 * The display name of the Publisher. This name will be presented to the
	 * user.
	 * </p>
	 */
	public String getPublisherName() {
		return this.publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	private Long publisherIdentifier;

	/**
     * 
     */
	public Long getPublisherIdentifier() {
		return this.publisherIdentifier;
	}

	public void setPublisherIdentifier(Long publisherIdentifier) {
		this.publisherIdentifier = publisherIdentifier;
	}

	private org.openuss.news.NewsCategory category;

	/**
     * 
     */
	public org.openuss.news.NewsCategory getCategory() {
		return this.category;
	}

	public void setCategory(org.openuss.news.NewsCategory category) {
		this.category = category;
	}

	private boolean released;

	/**
     * 
     */
	public boolean isReleased() {
		return this.released;
	}

	public void setReleased(boolean released) {
		this.released = released;
	}

	private boolean expired;

	/**
     * 
     */
	public boolean isExpired() {
		return this.expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	private org.openuss.news.PublisherType publisherType;

	/**
     * 
     */
	public org.openuss.news.PublisherType getPublisherType() {
		return this.publisherType;
	}

	public void setPublisherType(org.openuss.news.PublisherType publisherType) {
		this.publisherType = publisherType;
	}

	private List<org.openuss.documents.FileInfo> attachments;

	/**
	 * Get the attachments
	 * 
	 */
	public List<org.openuss.documents.FileInfo> getAttachments() {
		return this.attachments;
	}

	/**
	 * Sets the attachments
	 */
	public void setAttachments(List<org.openuss.documents.FileInfo> attachments) {
		this.attachments = attachments;
	}

	/**
	 * Returns <code>true</code> if the argument is an NewsItemInfo instance and
	 * all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof NewsItemInfo)) {
			return false;
		}
		final NewsItemInfo that = (NewsItemInfo) object;
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