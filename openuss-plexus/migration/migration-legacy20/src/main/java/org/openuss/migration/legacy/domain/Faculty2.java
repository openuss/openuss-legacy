package org.openuss.migration.legacy.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ingo Dueppe
 */
public class Faculty2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = -3612725734224376975L;
	private String id;
	private Assistant2 assistant2;
	private String name;
	private String owner;
	private String remark;
	private Character aactive;
	private String designer;
	private String locale;
	private String website;
	private String ttype;
	private Set<Enrollment2> enrollment2s = new HashSet<Enrollment2>(0);
	private Set<Subject2> subject2s = new HashSet<Subject2>(0);
	private Set<Semester2> semester2s = new HashSet<Semester2>(0);
	private Set<Assistantfaculty2> assistantfaculty2s = new HashSet<Assistantfaculty2>(0);
	private Set<Facultyinformation2> facultyinformation2s = new HashSet<Facultyinformation2>(0);
	private Set<Studentfaculty2> studentfaculty2s = new HashSet<Studentfaculty2>(0);

	// Constructors

	/** default constructor */
	public Faculty2() {
	}

	/** minimal constructor */
	public Faculty2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Faculty2(String id, Assistant2 assistant2, String name, String owner, String remark, Character aactive,
			String designer, String locale, String website, String ttype, Set<Enrollment2> enrollment2s,
			Set<Subject2> subject2s, Set<Semester2> semester2s, Set<Assistantfaculty2> assistantfaculty2s,
			Set<Facultyinformation2> facultyinformation2s, Set<Studentfaculty2> studentfaculty2s) {
		this.id = id;
		this.assistant2 = assistant2;
		this.name = name;
		this.owner = owner;
		this.remark = remark;
		this.aactive = aactive;
		this.designer = designer;
		this.locale = locale;
		this.website = website;
		this.ttype = ttype;
		this.enrollment2s = enrollment2s;
		this.subject2s = subject2s;
		this.semester2s = semester2s;
		this.assistantfaculty2s = assistantfaculty2s;
		this.facultyinformation2s = facultyinformation2s;
		this.studentfaculty2s = studentfaculty2s;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Assistant2 getAssistant() {
		return this.assistant2;
	}

	public void setAssistant(Assistant2 assistant2) {
		this.assistant2 = assistant2;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Character getAactive() {
		return this.aactive;
	}

	public void setAactive(Character aactive) {
		this.aactive = aactive;
	}

	public String getDesigner() {
		return this.designer;
	}

	public void setDesigner(String designer) {
		this.designer = designer;
	}

	public String getLocale() {
		return this.locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getTtype() {
		return this.ttype;
	}

	public void setTtype(String ttype) {
		this.ttype = ttype;
	}

	public Set<Enrollment2> getEnrollments() {
		return this.enrollment2s;
	}

	public void setEnrollments(Set<Enrollment2> enrollment2s) {
		this.enrollment2s = enrollment2s;
	}

	public Set<Subject2> getSubjects() {
		return this.subject2s;
	}

	public void setSubjects(Set<Subject2> subject2s) {
		this.subject2s = subject2s;
	}

	public Set<Semester2> getSemesters() {
		return this.semester2s;
	}

	public void setSemesters(Set<Semester2> semester2s) {
		this.semester2s = semester2s;
	}

	public Set<Assistantfaculty2> getAssistantinstitutes() {
		return this.assistantfaculty2s;
	}

	public void setAssistantinstitutes(Set<Assistantfaculty2> assistantfaculty2s) {
		this.assistantfaculty2s = assistantfaculty2s;
	}

	public Set<Facultyinformation2> getFacultyinformations() {
		return this.facultyinformation2s;
	}

	public void setFacultyinformations(Set<Facultyinformation2> facultyinformation2s) {
		this.facultyinformation2s = facultyinformation2s;
	}

	public Set<Studentfaculty2> getStudentinstitutes() {
		return this.studentfaculty2s;
	}

	public void setStudentinstitutes(Set<Studentfaculty2> studentfaculty2s) {
		this.studentfaculty2s = studentfaculty2s;
	}

}
