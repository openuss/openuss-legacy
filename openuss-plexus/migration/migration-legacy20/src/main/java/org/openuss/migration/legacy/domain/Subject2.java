package org.openuss.migration.legacy.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ingo Dueppe
 */
public class Subject2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = -3909392933711293425L;
	private String id;
	private Faculty2 faculty2;
	private String name;
	private String remark;
	private Set<Enrollment2> enrollment2s = new HashSet<Enrollment2>(0);
	private Set<Studentsubject2> studentsubject2s = new HashSet<Studentsubject2>(0);

	// Constructors

	/** default constructor */
	public Subject2() {
	}

	/** minimal constructor */
	public Subject2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Subject2(String id, Faculty2 faculty2, String name, String remark, Set<Enrollment2> enrollment2s,
			Set<Studentsubject2> studentsubject2s) {
		this.id = id;
		this.faculty2 = faculty2;
		this.name = name;
		this.remark = remark;
		this.enrollment2s = enrollment2s;
		this.studentsubject2s = studentsubject2s;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<Enrollment2> getEnrollments() {
		return this.enrollment2s;
	}

	public void setEnrollments(Set<Enrollment2> enrollment2s) {
		this.enrollment2s = enrollment2s;
	}

	public Set<Studentsubject2> getStudentsubjects() {
		return this.studentsubject2s;
	}

	public void setStudentsubjects(Set<Studentsubject2> studentsubject2s) {
		this.studentsubject2s = studentsubject2s;
	}

}
