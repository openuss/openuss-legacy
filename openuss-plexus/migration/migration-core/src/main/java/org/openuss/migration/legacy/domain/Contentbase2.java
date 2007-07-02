package org.openuss.migration.legacy.domain;

/**
 * @author Ingo Dueppe
 */
public class Contentbase2 implements java.io.Serializable {

	private static final long serialVersionUID = 1782052185806614951L;
	// Fields    

	private String id;
	private String scormdataId;
	private String title;
	private String controltype;
	private String lecturefileid;
	private String contentId;
	private String description;
	private String date;
	private String scormversion;

	// Constructors

	/** default constructor */
	public Contentbase2() {
	}

	/** minimal constructor */
	public Contentbase2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Contentbase2(String id, String scormdataId, String title, String controltype, String lecturefileid,
			String contentId, String description, String date, String scormversion) {
		this.id = id;
		this.scormdataId = scormdataId;
		this.title = title;
		this.controltype = controltype;
		this.lecturefileid = lecturefileid;
		this.contentId = contentId;
		this.description = description;
		this.date = date;
		this.scormversion = scormversion;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScormdataId() {
		return this.scormdataId;
	}

	public void setScormdataId(String scormdataId) {
		this.scormdataId = scormdataId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getControltype() {
		return this.controltype;
	}

	public void setControltype(String controltype) {
		this.controltype = controltype;
	}

	public String getLecturefileid() {
		return this.lecturefileid;
	}

	public void setLecturefileid(String lecturefileid) {
		this.lecturefileid = lecturefileid;
	}

	public String getContentId() {
		return this.contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
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

	public String getScormversion() {
		return this.scormversion;
	}

	public void setScormversion(String scormversion) {
		this.scormversion = scormversion;
	}

}
