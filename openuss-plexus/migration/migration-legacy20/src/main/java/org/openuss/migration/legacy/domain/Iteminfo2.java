package org.openuss.migration.legacy.domain;

/**
 * @author Ingo Dueppe
 */
public class Iteminfo2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = 7696050928063830434L;
	private String id;
	private String title;
	private String contentbaseId;
	private String timelimitaction;
	private Integer thelevel;
	private String datafromlms;
	private String maxtimeallowed;

	// Constructors

	/** default constructor */
	public Iteminfo2() {
	}

	/** minimal constructor */
	public Iteminfo2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Iteminfo2(String id, String title, String contentbaseId, String timelimitaction, Integer thelevel,
			String datafromlms, String maxtimeallowed) {
		this.id = id;
		this.title = title;
		this.contentbaseId = contentbaseId;
		this.timelimitaction = timelimitaction;
		this.thelevel = thelevel;
		this.datafromlms = datafromlms;
		this.maxtimeallowed = maxtimeallowed;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentbaseId() {
		return this.contentbaseId;
	}

	public void setContentbaseId(String contentbaseId) {
		this.contentbaseId = contentbaseId;
	}

	public String getTimelimitaction() {
		return this.timelimitaction;
	}

	public void setTimelimitaction(String timelimitaction) {
		this.timelimitaction = timelimitaction;
	}

	public Integer getThelevel() {
		return this.thelevel;
	}

	public void setThelevel(Integer thelevel) {
		this.thelevel = thelevel;
	}

	public String getDatafromlms() {
		return this.datafromlms;
	}

	public void setDatafromlms(String datafromlms) {
		this.datafromlms = datafromlms;
	}

	public String getMaxtimeallowed() {
		return this.maxtimeallowed;
	}

	public void setMaxtimeallowed(String maxtimeallowed) {
		this.maxtimeallowed = maxtimeallowed;
	}

}
