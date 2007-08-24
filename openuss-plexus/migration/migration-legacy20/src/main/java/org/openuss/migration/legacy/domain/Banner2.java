package org.openuss.migration.legacy.domain;

import java.util.Date;

/**
 * @author Ingo Dueppe
 */
public class Banner2 implements java.io.Serializable {

	private static final long serialVersionUID = 7781314237296236931L;
	// Fields    

	private String id;
	private byte[] image;
	private String imageHeight;
	private String imageWidth;
	private String author;
	private Date uploaddate;
	private String title;
	private String description;
	private String bannerIsDefault;

	// Constructors

	/** default constructor */
	public Banner2() {
	}

	/** minimal constructor */
	public Banner2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Banner2(String id, byte[] image, String imageHeight, String imageWidth, String author, Date uploaddate,
			String title, String description, String bannerIsDefault) {
		this.id = id;
		this.image = image;
		this.imageHeight = imageHeight;
		this.imageWidth = imageWidth;
		this.author = author;
		this.uploaddate = uploaddate;
		this.title = title;
		this.description = description;
		this.bannerIsDefault = bannerIsDefault;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getImageHeight() {
		return this.imageHeight;
	}

	public void setImageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String getImageWidth() {
		return this.imageWidth;
	}

	public void setImageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getUploaddate() {
		return this.uploaddate;
	}

	public void setUploaddate(Date uploaddate) {
		this.uploaddate = uploaddate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBannerIsDefault() {
		return this.bannerIsDefault;
	}

	public void setBannerIsDefault(String bannerIsDefault) {
		this.bannerIsDefault = bannerIsDefault;
	}

}
