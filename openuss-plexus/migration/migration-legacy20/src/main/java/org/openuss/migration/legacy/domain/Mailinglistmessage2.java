package org.openuss.migration.legacy.domain;

import java.util.Date;

/**
 * @author Ingo Dueppe
 */
public class Mailinglistmessage2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = 1959115979172111433L;
	private String id;
	private String enrollmentPk;
	private Date ddate;
	private String title;
	private String body;

	// Constructors

	/** default constructor */
	public Mailinglistmessage2() {
	}

	/** minimal constructor */
	public Mailinglistmessage2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Mailinglistmessage2(String id, String enrollment2, Date ddate, String title, String body) {
		this.id = id;
		this.enrollmentPk = enrollment2;
		this.ddate = ddate;
		this.title = title;
		this.body = body;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEnrollmentPk() {
		return this.enrollmentPk;
	}

	public void setEnrollmentPk(String enrollment2) {
		this.enrollmentPk = enrollment2;
	}

	public Date getDdate() {
		return this.ddate;
	}

	public void setDdate(Date ddate) {
		this.ddate = ddate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
