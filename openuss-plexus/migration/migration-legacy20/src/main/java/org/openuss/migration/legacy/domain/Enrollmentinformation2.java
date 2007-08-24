package org.openuss.migration.legacy.domain;

import java.util.Date;

/**
 * @author Ingo Dueppe
 */
public class Enrollmentinformation2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = -829436847895014538L;
	private String id;
	private String enrollmentpk;
	private Date ddate;
	private String ttext;

	// Constructors

	/** default constructor */
	public Enrollmentinformation2() {
	}

	/** minimal constructor */
	public Enrollmentinformation2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Enrollmentinformation2(String id, String enrollmentpk, Date ddate, String ttext) {
		this.id = id;
		this.enrollmentpk = enrollmentpk;
		this.ddate = ddate;
		this.ttext = ttext;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEnrollmentpk() {
		return this.enrollmentpk;
	}

	public void setEnrollmentpk(String enrollmentpk) {
		this.enrollmentpk = enrollmentpk;
	}

	public Date getDdate() {
		return this.ddate;
	}

	public void setDdate(Date ddate) {
		this.ddate = ddate;
	}

	public String getTtext() {
		return this.ttext;
	}

	public void setTtext(String ttext) {
		this.ttext = ttext;
	}

}
