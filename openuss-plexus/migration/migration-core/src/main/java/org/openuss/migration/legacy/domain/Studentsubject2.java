package org.openuss.migration.legacy.domain;

/**
 * @author Ingo Dueppe
 */
public class Studentsubject2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = -2139616499194222380L;
	private String id;
	private Subject2 subject2;
	private Student2 student2;

	// Constructors

	/** default constructor */
	public Studentsubject2() {
	}

	/** minimal constructor */
	public Studentsubject2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Studentsubject2(String id, Subject2 subject2, Student2 student2) {
		this.id = id;
		this.subject2 = subject2;
		this.student2 = student2;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Subject2 getSubject() {
		return this.subject2;
	}

	public void setSubject(Subject2 subject2) {
		this.subject2 = subject2;
	}

	public Student2 getStudent() {
		return this.student2;
	}

	public void setStudent(Student2 student2) {
		this.student2 = student2;
	}

}
