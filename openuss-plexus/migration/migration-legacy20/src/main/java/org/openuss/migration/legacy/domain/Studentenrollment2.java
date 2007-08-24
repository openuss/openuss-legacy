package org.openuss.migration.legacy.domain;

/**
 * @author Ingo Dueppe
 */
public class Studentenrollment2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = -5668813331784114305L;
	private String id;
	private Enrollment2 enrollment2;
	private Student2 student2;
	private String withpassword;

	// Constructors

	/** default constructor */
	public Studentenrollment2() {
	}

	/** minimal constructor */
	public Studentenrollment2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Studentenrollment2(String id, Enrollment2 enrollment2, Student2 student2, String withpassword) {
		this.id = id;
		this.enrollment2 = enrollment2;
		this.student2 = student2;
		this.withpassword = withpassword;
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

	public String getWithpassword() {
		return this.withpassword;
	}

	public void setWithpassword(String withpassword) {
		this.withpassword = withpassword;
	}

}
