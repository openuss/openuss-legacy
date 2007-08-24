package org.openuss.migration.legacy.domain;

/**
 * @author Ingo Dueppe
 */
public class Userscoinfobase2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = -4356040434581690156L;
	private String id;
	private String entry;
	private String iteminfoId;
	private String lessonstatus;
	private String exit;

	// Constructors

	/** default constructor */
	public Userscoinfobase2() {
	}

	/** minimal constructor */
	public Userscoinfobase2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Userscoinfobase2(String id, String entry, String iteminfoId, String lessonstatus, String exit) {
		this.id = id;
		this.entry = entry;
		this.iteminfoId = iteminfoId;
		this.lessonstatus = lessonstatus;
		this.exit = exit;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEntry() {
		return this.entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getIteminfoId() {
		return this.iteminfoId;
	}

	public void setIteminfoId(String iteminfoId) {
		this.iteminfoId = iteminfoId;
	}

	public String getLessonstatus() {
		return this.lessonstatus;
	}

	public void setLessonstatus(String lessonstatus) {
		this.lessonstatus = lessonstatus;
	}

	public String getExit() {
		return this.exit;
	}

	public void setExit(String exit) {
		this.exit = exit;
	}

}
