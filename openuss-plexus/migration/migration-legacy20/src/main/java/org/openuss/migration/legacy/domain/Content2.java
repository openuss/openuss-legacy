package org.openuss.migration.legacy.domain;

/**
 * Content entity of openuss 2.0
 * 
 * @author Ingo Dueppe
 */
public class Content2 implements java.io.Serializable {

	private static final long serialVersionUID = 4302048942914923728L;
	// Fields    

	private String id;
	private String enrollementid;

	// Constructors

	/** default constructor */
	public Content2() {
	}

	/** minimal constructor */
	public Content2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Content2(String id, String enrollementid) {
		this.id = id;
		this.enrollementid = enrollementid;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEnrollementid() {
		return this.enrollementid;
	}

	public void setEnrollementid(String enrollementid) {
		this.enrollementid = enrollementid;
	}

}
