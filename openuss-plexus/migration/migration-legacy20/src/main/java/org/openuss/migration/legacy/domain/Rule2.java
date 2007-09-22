package org.openuss.migration.legacy.domain;

import java.util.Date;

/**
 * @author Ingo Dueppe
 */
public class Rule2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = 1831948207388347708L;
	private String id;
	private String aactive;
	private String name;
	private String link;
	private String description;
	private String banner;
	private String bannerposition;
	private Date creationdate;
	private Date lastmodifieddate;
	private byte[] conditions;

	// Constructors

	/** default constructor */
	public Rule2() {
	}

	/** minimal constructor */
	public Rule2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Rule2(String id, String aactive, String name, String link, String description, String banner,
			String bannerposition, Date creationdate, Date lastmodifieddate, byte[] conditions) {
		this.id = id;
		this.aactive = aactive;
		this.name = name;
		this.link = link;
		this.description = description;
		this.banner = banner;
		this.bannerposition = bannerposition;
		this.creationdate = creationdate;
		this.lastmodifieddate = lastmodifieddate;
		this.conditions = conditions;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAactive() {
		return this.aactive;
	}

	public void setAactive(String aactive) {
		this.aactive = aactive;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBanner() {
		return this.banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getBannerposition() {
		return this.bannerposition;
	}

	public void setBannerposition(String bannerposition) {
		this.bannerposition = bannerposition;
	}

	public Date getCreationdate() {
		return this.creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public Date getLastmodifieddate() {
		return this.lastmodifieddate;
	}

	public void setLastmodifieddate(Date lastmodifieddate) {
		this.lastmodifieddate = lastmodifieddate;
	}

	public byte[] getConditions() {
		return this.conditions;
	}

	public void setConditions(byte[] conditions) {
		this.conditions = conditions;
	}

}
