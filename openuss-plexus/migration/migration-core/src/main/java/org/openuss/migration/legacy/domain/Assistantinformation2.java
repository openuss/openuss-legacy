package org.openuss.migration.legacy.domain;

import java.util.Date;

/**
 * @author Ingo Dueppe
 */
public class Assistantinformation2 implements java.io.Serializable {

	private static final long serialVersionUID = -3515754783165206788L;
	// Fields    

	private String id;
	private Assistantfilebase2 assistantfilebase2;
	private Assistant2 assistant2;
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
	public Assistantinformation2() {
	}

	/** minimal constructor */
	public Assistantinformation2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Assistantinformation2(String id, Assistantfilebase2 assistantfilebase2, Assistant2 assistant2, Date ddate,
			String ttext, Character ispublic, Character email, Character telephone, Character address,
			Character description, Character image) {
		this.id = id;
		this.assistantfilebase2 = assistantfilebase2;
		this.assistant2 = assistant2;
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

	public Assistantfilebase2 getAssistantfilebase() {
		return this.assistantfilebase2;
	}

	public void setAssistantfilebase(Assistantfilebase2 assistantfilebase2) {
		this.assistantfilebase2 = assistantfilebase2;
	}

	public Assistant2 getAssistant() {
		return this.assistant2;
	}

	public void setAssistant(Assistant2 assistant2) {
		this.assistant2 = assistant2;
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
