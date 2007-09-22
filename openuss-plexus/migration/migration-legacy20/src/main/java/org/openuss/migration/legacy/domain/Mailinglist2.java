package org.openuss.migration.legacy.domain;

/**
 * @author Ingo Dueppe
 */
public class Mailinglist2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = -4746566496398977076L;
	private String id;
	private String enrollmentpk;
	private String memberpk;

	// Constructors

	/** default constructor */
	public Mailinglist2() {
	}

	/** minimal constructor */
	public Mailinglist2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Mailinglist2(String id, String enrollmentpk, String memberpk) {
		this.id = id;
		this.enrollmentpk = enrollmentpk;
		this.memberpk = memberpk;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberpk() {
		return this.memberpk;
	}

	public void setMemberpk(String memberpk) {
		this.memberpk = memberpk;
	}

	public String getEnrollmentpk() {
		return enrollmentpk;
	}

	public void setEnrollmentpk(String enrollmentpk) {
		this.enrollmentpk = enrollmentpk;
	}

}
