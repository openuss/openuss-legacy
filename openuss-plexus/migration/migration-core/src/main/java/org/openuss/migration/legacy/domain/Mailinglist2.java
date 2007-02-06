package org.openuss.migration.legacy.domain;

/**
 * @author Ingo Dueppe
 */
public class Mailinglist2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = -4746566496398977076L;
	private String id;
	private Enrollment2 enrollment2;
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
	public Mailinglist2(String id, Enrollment2 enrollment2, String memberpk) {
		this.id = id;
		this.enrollment2 = enrollment2;
		this.memberpk = memberpk;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Enrollment2 getEnrollment() {
		return this.enrollment2;
	}

	public void setEnrollment(Enrollment2 enrollment2) {
		this.enrollment2 = enrollment2;
	}

	public String getMemberpk() {
		return this.memberpk;
	}

	public void setMemberpk(String memberpk) {
		this.memberpk = memberpk;
	}

}
