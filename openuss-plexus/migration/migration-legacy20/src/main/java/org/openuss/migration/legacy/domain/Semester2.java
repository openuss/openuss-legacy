package org.openuss.migration.legacy.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ingo Dueppe
 */
public class Semester2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = 6287007895470979555L;
	private String id;
	private Faculty2 faculty2;
	private String name;
	private Character aactive;
	private String remark;
	private Set<Enrollment2> enrollment2s = new HashSet<Enrollment2>(0);

	// Constructors

	/** default constructor */
	public Semester2() {
	}

	/** minimal constructor */
	public Semester2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Semester2(String id, Faculty2 faculty2, String name, Character aactive, String remark,
			Set<Enrollment2> enrollment2s) {
		this.id = id;
		this.faculty2 = faculty2;
		this.name = name;
		this.aactive = aactive;
		this.remark = remark;
		this.enrollment2s = enrollment2s;
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

	public Character getAactive() {
		return this.aactive;
	}

	public void setAactive(Character aactive) {
		this.aactive = aactive;
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

}
