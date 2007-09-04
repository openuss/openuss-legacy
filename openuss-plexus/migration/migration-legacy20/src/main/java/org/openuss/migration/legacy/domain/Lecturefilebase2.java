package org.openuss.migration.legacy.domain;


/**
 * @author Ingo Dueppe
 */
public class Lecturefilebase2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = 8712238654855983360L;
	private String id;
	private byte[] basefile;

	// Constructors

	/** default constructor */
	public Lecturefilebase2() {
	}

	/** minimal constructor */
	public Lecturefilebase2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Lecturefilebase2(String id, byte[] basefile) {
		this.id = id;
		this.basefile = basefile;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getBasefile() {
		return this.basefile;
	}

	public void setBasefile(byte[] basefile) {
		this.basefile = basefile;
	}

}
