package org.openuss.migration.legacy.domain;

import java.util.Date;

/**
 * @author Ingo Dueppe
 */
public class Studentinformation2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = -2619123218628199486L;
	private String id;
	private Studentfilebase2 studentfilebase2;
	private Student2 student2;
	private Date ddate;
	private String ttext;
	private Character ispublic;
	private Character email;
	private Character telephone;
	private Character address;
	private Character description;
	private Character image;

	// Constructors

	/** default constructor */
	public Studentinformation2() {
	}

	/** minimal constructor */
	public Studentinformation2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Studentinformation2(String id, Studentfilebase2 studentfilebase2, Student2 student2, Date ddate, String ttext,
			Character ispublic, Character email, Character telephone, Character address, Character description,
			Character image) {
		this.id = id;
		this.studentfilebase2 = studentfilebase2;
		this.student2 = student2;
		this.ddate = ddate;
		this.ttext = ttext;
		this.ispublic = ispublic;
		this.email = email;
		this.telephone = telephone;
		this.address = address;
		this.description = description;
		this.image = image;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Studentfilebase2 getStudentfilebase() {
		return this.studentfilebase2;
	}

	public void setStudentfilebase(Studentfilebase2 studentfilebase2) {
		this.studentfilebase2 = studentfilebase2;
	}

	public Student2 getStudent() {
		return this.student2;
	}

	public void setStudent(Student2 student2) {
		this.student2 = student2;
	}

	public Date getDdate() {
		return this.ddate;
	}

	public void setDdate(Date ddate) {
		this.ddate = ddate;
	}

	public String getTtext() {
		return this.ttext;
	}

	public void setTtext(String ttext) {
		this.ttext = ttext;
	}

	public Character getIspublic() {
		return this.ispublic;
	}

	public void setIspublic(Character ispublic) {
		this.ispublic = ispublic;
	}

	public Character getEmail() {
		return this.email;
	}

	public void setEmail(Character email) {
		this.email = email;
	}

	public Character getTelephone() {
		return this.telephone;
	}

	public void setTelephone(Character telephone) {
		this.telephone = telephone;
	}

	public Character getAddress() {
		return this.address;
	}

	public void setAddress(Character address) {
		this.address = address;
	}

	public Character getDescription() {
		return this.description;
	}

	public void setDescription(Character description) {
		this.description = description;
	}

	public Character getImage() {
		return this.image;
	}

	public void setImage(Character image) {
		this.image = image;
	}

}
