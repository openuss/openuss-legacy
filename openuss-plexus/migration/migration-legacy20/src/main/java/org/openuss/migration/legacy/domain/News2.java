package org.openuss.migration.legacy.domain;

/**
 * @author Ingo Dueppe
 */
public class News2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = -7686967584459442424L;
	private String id;
	private String contentbaseId;
	private String title;
	private String description;
	private String date;

	// Constructors

	/** default constructor */
	public News2() {
	}

	/** minimal constructor */
	public News2(String id) {
		this.id = id;
	}

	/** full constructor */
	public News2(String id, String contentbaseId, String title, String description, String date) {
		this.id = id;
		this.contentbaseId = contentbaseId;
		this.title = title;
		this.description = description;
		this.date = date;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContentbaseId() {
		return this.contentbaseId;
	}

	public void setContentbaseId(String contentbaseId) {
		this.contentbaseId = contentbaseId;
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

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
