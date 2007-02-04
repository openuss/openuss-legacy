package org.openuss.migration.legacy.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ingo Dueppe
 */
public class Assistant2 implements java.io.Serializable {

	private static final long serialVersionUID = -5491665847415666667L;
	
	private String id;
	private String firstname;
	private String lastname;
	private String emailaddress;
	private String uusername;
	private String ppassword;
	private String ffunction;
	private String title;
	private String address;
	private String city;
	private String postcode;
	private String land;
	private String telephone;
	private String emailsmsgatewayaddress;
	private String subjectsmsgateway;
	private Character aactive;
	private String locale;
	private String designer;
	private Set<Consultancy2> consultancy2s = new HashSet<Consultancy2>(0);
	private Set<Faculty2> faculty2s = new HashSet<Faculty2>(0);
	private Set<Assistantenrollment2> assistantenrollment2s = new HashSet<Assistantenrollment2>(0);
	private Set<Assistantinformation2> assistantinformation2s = new HashSet<Assistantinformation2>(0);
	private Set<Assistantfaculty2> assistantfaculty2s = new HashSet<Assistantfaculty2>(0);

	// Constructors

	/** default constructor */
	public Assistant2() {
	}

	/** minimal constructor */
	public Assistant2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Assistant2(String id, String firstname, String lastname, String emailaddress, String uusername,
			String ppassword, String ffunction, String title, String address, String city, String postcode,
			String land, String telephone, String emailsmsgatewayaddress, String subjectsmsgateway, Character aactive,
			String locale, String designer, Set<Consultancy2> consultancy2s, Set<Faculty2> faculty2s,
			Set<Assistantenrollment2> assistantenrollment2s, Set<Assistantinformation2> assistantinformation2s,
			Set<Assistantfaculty2> assistantfaculty2s) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.emailaddress = emailaddress;
		this.uusername = uusername;
		this.ppassword = ppassword;
		this.ffunction = ffunction;
		this.title = title;
		this.address = address;
		this.city = city;
		this.postcode = postcode;
		this.land = land;
		this.telephone = telephone;
		this.emailsmsgatewayaddress = emailsmsgatewayaddress;
		this.subjectsmsgateway = subjectsmsgateway;
		this.aactive = aactive;
		this.locale = locale;
		this.designer = designer;
		this.consultancy2s = consultancy2s;
		this.faculty2s = faculty2s;
		this.assistantenrollment2s = assistantenrollment2s;
		this.assistantinformation2s = assistantinformation2s;
		this.assistantfaculty2s = assistantfaculty2s;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getFfunction() {
		return this.ffunction;
	}

	public void setFfunction(String ffunction) {
		this.ffunction = ffunction;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Set<Consultancy2> getConsultancies() {
		return this.consultancy2s;
	}

	public void setConsultancies(Set<Consultancy2> consultancy2s) {
		this.consultancy2s = consultancy2s;
	}

	public Set<Faculty2> getFaculties() {
		return this.faculty2s;
	}

	public void setFaculties(Set<Faculty2> faculty2s) {
		this.faculty2s = faculty2s;
	}

	public Set<Assistantenrollment2> getAssistantenrollments() {
		return this.assistantenrollment2s;
	}

	public void setAssistantenrollments(Set<Assistantenrollment2> assistantenrollment2s) {
		this.assistantenrollment2s = assistantenrollment2s;
	}

	public Set<Assistantinformation2> getAssistantinformations() {
		return this.assistantinformation2s;
	}

	public void setAssistantinformations(Set<Assistantinformation2> assistantinformation2s) {
		this.assistantinformation2s = assistantinformation2s;
	}

	public Set<Assistantfaculty2> getAssistantfaculties() {
		return this.assistantfaculty2s;
	}

	public void setAssistantfaculties(Set<Assistantfaculty2> assistantfaculty2s) {
		this.assistantfaculty2s = assistantfaculty2s;
	}

}
