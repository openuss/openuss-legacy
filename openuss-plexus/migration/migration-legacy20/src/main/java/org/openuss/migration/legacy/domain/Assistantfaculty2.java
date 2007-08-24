package org.openuss.migration.legacy.domain;

/**
 * @author Ingo Dueppe
 */
public class Assistantfaculty2 implements java.io.Serializable {
	
	private static final long serialVersionUID = 8639527278274038491L;
	// Fields    

	private String id;
	private Faculty2 faculty2;
	private Assistant2 assistant2;
	private Character aactive;
	private Character isadmin;

	// Constructors

	/** default constructor */
	public Assistantfaculty2() {
	}

	/** minimal constructor */
	public Assistantfaculty2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Assistantfaculty2(String id, Faculty2 faculty2, Assistant2 assistant2, Character aactive, Character isadmin) {
		this.id = id;
		this.faculty2 = faculty2;
		this.assistant2 = assistant2;
		this.aactive = aactive;
		this.isadmin = isadmin;
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

	public Assistant2 getAssistant() {
		return this.assistant2;
	}

	public void setAssistant(Assistant2 assistant2) {
		this.assistant2 = assistant2;
	}

	public Character getAactive() {
		return this.aactive;
	}

	public void setAactive(Character aactive) {
		this.aactive = aactive;
	}

	public Character getIsadmin() {
		return this.isadmin;
	}

	public void setIsadmin(Character isadmin) {
		this.isadmin = isadmin;
	}

}
