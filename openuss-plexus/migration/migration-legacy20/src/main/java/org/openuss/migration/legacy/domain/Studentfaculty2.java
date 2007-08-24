package org.openuss.migration.legacy.domain;

/**
 * @author Ingo Dueppe
 */
public class Studentfaculty2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = -46337172658126153L;
	private String id;
	private Faculty2 faculty2;
	private Student2 student2;

	// Constructors

	/** default constructor */
	public Studentfaculty2() {
	}

	/** minimal constructor */
	public Studentfaculty2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Studentfaculty2(String id, Faculty2 faculty2, Student2 student2) {
		this.id = id;
		this.faculty2 = faculty2;
		this.student2 = student2;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Faculty2 getFaculty() {
		return this.faculty2;
	}

	public void setFaculty(Faculty2 faculty2) {
		this.faculty2 = faculty2;
	}

	public Student2 getStudent() {
		return this.student2;
	}

	public void setStudent(Student2 student2) {
		this.student2 = student2;
	}

}
