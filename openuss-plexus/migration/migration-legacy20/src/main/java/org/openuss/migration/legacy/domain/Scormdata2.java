package org.openuss.migration.legacy.domain;

/**
 * @author Ingo Dueppe
 */
public class Scormdata2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = 5499150387734642128L;
	private String id;
	private String userscoinfobaseId;
	private byte[] data;

	// Constructors

	/** default constructor */
	public Scormdata2() {
	}

	/** minimal constructor */
	public Scormdata2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Scormdata2(String id, String userscoinfobaseId, byte[] data) {
		this.id = id;
		this.userscoinfobaseId = userscoinfobaseId;
		this.data = data;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserscoinfobaseId() {
		return this.userscoinfobaseId;
	}

	public void setUserscoinfobaseId(String userscoinfobaseId) {
		this.userscoinfobaseId = userscoinfobaseId;
	}

	public byte[] getData() {
		return this.data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
