package org.openuss.migration.legacy.domain;

/**
 * @author Ingo Dueppe
 */
public class Assistantenrollment2 implements java.io.Serializable {

	private static final long serialVersionUID = -1234695673132864687L;
	// Fields    

	private String id;
	private Enrollment2 enrollment2;
	private Assistant2 assistant2;

	// Constructors

	/** default constructor */
	public Assistantenrollment2() {
	}

	/** minimal constructor */
	public Assistantenrollment2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Assistantenrollment2(String id, Enrollment2 enrollment2, Assistant2 assistant2) {
		this.id = id;
		this.enrollment2 = enrollment2;
		this.assistant2 = assistant2;
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

	public Assistant2 getAssistant() {
		return this.assistant2;
	}

	public void setAssistant(Assistant2 assistant2) {
		this.assistant2 = assistant2;
	}

}
