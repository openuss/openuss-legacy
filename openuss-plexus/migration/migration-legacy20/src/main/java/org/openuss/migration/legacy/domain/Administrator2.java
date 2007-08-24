package org.openuss.migration.legacy.domain;

/**
 * @author Ingo Dueppe
 */
public class Administrator2 implements java.io.Serializable {

	private static final long serialVersionUID = 67934398235670859L;
	private String id;
	private String firstname;
	private String lastname;
	private String emailaddress;
	private String uusername;
	private String ppassword;
	private Character aactive;
	private String locale;

	// Constructors

	/** default constructor */
	public Administrator2() {
	}

	/** minimal constructor */
	public Administrator2(String id, String emailaddress, String uusername, String ppassword) {
		this.id = id;
		this.emailaddress = emailaddress;
		this.uusername = uusername;
		this.ppassword = ppassword;
	}

	/** full constructor */
	public Administrator2(String id, String firstname, String lastname, String emailaddress, String uusername,
			String ppassword, Character aactive, String locale) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.emailaddress = emailaddress;
		this.uusername = uusername;
		this.ppassword = ppassword;
		this.aactive = aactive;
		this.locale = locale;
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

}
