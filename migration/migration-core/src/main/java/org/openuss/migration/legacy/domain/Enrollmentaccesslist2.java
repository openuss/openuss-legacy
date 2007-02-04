package org.openuss.migration.legacy.domain;

/**
 * @author Ingo Dueppe
 */
public class Enrollmentaccesslist2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = 1837213569373929672L;
	private String id;
	private Enrollment2 enrollment2;
	private Student2 student2;
	private Character accepted;

	// Constructors

	/** default constructor */
	public Enrollmentaccesslist2() {
	}

	/** minimal constructor */
	public Enrollmentaccesslist2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Enrollmentaccesslist2(String id, Enrollment2 enrollment2, Student2 student2, Character accepted) {
		this.id = id;
		this.enrollment2 = enrollment2;
		this.student2 = student2;
		this.accepted = accepted;
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

	public Student2 getStudent() {
		return this.student2;
	}

	public void setStudent(Student2 student2) {
		this.student2 = student2;
	}

	public Character getAccepted() {
		return this.accepted;
	}

	public void setAccepted(Character accepted) {
		this.accepted = accepted;
	}

}
