package org.openuss.migration.legacy.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ingo Dueppe
 */
public class Student2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = -5571797324273549299L;
	private String id;
	private String personalid;
	private String firstname;
	private String lastname;
	private String emailaddress;
	private String uusername;
	private String ppassword;
	private String yyear;
	private String studies;
	private String emailsmsgatewayaddress;
	private String subjectsmsgateway;
	private Character aactive;
	private String locale;
	private String designer;
	private String address;
	private String city;
	private String postcode;
	private String land;
	private String telephone;
	private Set<Studentfaculty2> studentfaculty2s = new HashSet<Studentfaculty2>(0);
	private Set<Enrollmentaccesslist2> enrollmentaccesslist2s = new HashSet<Enrollmentaccesslist2>(0);
	private Set<Studentinformation2> studentinformation2s = new HashSet<Studentinformation2>(0);
	private Set<Studentsubject2> studentsubject2s = new HashSet<Studentsubject2>(0);
	private Set<Studentenrollment2> studentenrollment2s = new HashSet<Studentenrollment2>(0);

	// Constructors

	/** default constructor */
	public Student2() {
	}

	/** minimal constructor */
	public Student2(String id, String emailaddress, String uusername, String ppassword) {
		this.id = id;
		this.emailaddress = emailaddress;
		this.uusername = uusername;
		this.ppassword = ppassword;
	}

	/** full constructor */
	public Student2(String id, String personalid, String firstname, String lastname, String emailaddress,
			String uusername, String ppassword, String yyear, String studies, String emailsmsgatewayaddress,
			String subjectsmsgateway, Character aactive, String locale, String designer, String address, String city,
			String postcode, String land, String telephone, Set<Studentfaculty2> studentfaculty2s,
			Set<Enrollmentaccesslist2> enrollmentaccesslist2s, Set<Studentinformation2> studentinformation2s,
			Set<Studentsubject2> studentsubject2s, Set<Studentenrollment2> studentenrollment2s) {
		this.id = id;
		this.personalid = personalid;
		this.firstname = firstname;
		this.lastname = lastname;
		this.emailaddress = emailaddress;
		this.uusername = uusername;
		this.ppassword = ppassword;
		this.yyear = yyear;
		this.studies = studies;
		this.emailsmsgatewayaddress = emailsmsgatewayaddress;
		this.subjectsmsgateway = subjectsmsgateway;
		this.aactive = aactive;
		this.locale = locale;
		this.designer = designer;
		this.address = address;
		this.city = city;
		this.postcode = postcode;
		this.land = land;
		this.telephone = telephone;
		this.studentfaculty2s = studentfaculty2s;
		this.enrollmentaccesslist2s = enrollmentaccesslist2s;
		this.studentinformation2s = studentinformation2s;
		this.studentsubject2s = studentsubject2s;
		this.studentenrollment2s = studentenrollment2s;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPersonalid() {
		return this.personalid;
	}

	public void setPersonalid(String personalid) {
		this.personalid = personalid;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmailaddress() {
		return this.emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getUusername() {
		return this.uusername;
	}

	public void setUusername(String uusername) {
		this.uusername = uusername;
	}

	public String getPpassword() {
		return this.ppassword;
	}

	public void setPpassword(String ppassword) {
		this.ppassword = ppassword;
	}

	public String getYyear() {
		return this.yyear;
	}

	public void setYyear(String yyear) {
		this.yyear = yyear;
	}

	public String getStudies() {
		return this.studies;
	}

	public void setStudies(String studies) {
		this.studies = studies;
	}

	public String getEmailsmsgatewayaddress() {
		return this.emailsmsgatewayaddress;
	}

	public void setEmailsmsgatewayaddress(String emailsmsgatewayaddress) {
		this.emailsmsgatewayaddress = emailsmsgatewayaddress;
	}

	public String getSubjectsmsgateway() {
		return this.subjectsmsgateway;
	}

	public void setSubjectsmsgateway(String subjectsmsgateway) {
		this.subjectsmsgateway = subjectsmsgateway;
	}

	public Character getAactive() {
		return this.aactive;
	}

	public void setAactive(Character aactive) {
		this.aactive = aactive;
	}

	public String getLocale() {
		return this.locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getDesigner() {
		return this.designer;
	}

	public void setDesigner(String designer) {
		this.designer = designer;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getLand() {
		return this.land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Set<Studentfaculty2> getStudentfaculties() {
		return this.studentfaculty2s;
	}

	public void setStudentfaculties(Set<Studentfaculty2> studentfaculty2s) {
		this.studentfaculty2s = studentfaculty2s;
	}

	public Set<Enrollmentaccesslist2> getEnrollmentaccesslists() {
		return this.enrollmentaccesslist2s;
	}

	public void setEnrollmentaccesslists(Set<Enrollmentaccesslist2> enrollmentaccesslist2s) {
		this.enrollmentaccesslist2s = enrollmentaccesslist2s;
	}

	public Set<Studentinformation2> getStudentinformations() {
		return this.studentinformation2s;
	}

	public void setStudentinformations(Set<Studentinformation2> studentinformation2s) {
		this.studentinformation2s = studentinformation2s;
	}

	public Set<Studentsubject2> getStudentsubjects() {
		return this.studentsubject2s;
	}

	public void setStudentsubjects(Set<Studentsubject2> studentsubject2s) {
		this.studentsubject2s = studentsubject2s;
	}

	public Set<Studentenrollment2> getStudentenrollments() {
		return this.studentenrollment2s;
	}

	public void setStudentenrollments(Set<Studentenrollment2> studentenrollment2s) {
		this.studentenrollment2s = studentenrollment2s;
	}

}
