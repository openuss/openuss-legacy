package org.openuss.migration.legacy.domain;

/**
 * @author Ingo Dueppe
 */
public class Userscoinfo2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = 3122604713861607816L;
	private String id;
	private String studentid;
	private String userscoinfobaseId;

	// Constructors

	/** default constructor */
	public Userscoinfo2() {
	}

	/** minimal constructor */
	public Userscoinfo2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Userscoinfo2(String id, String studentid, String userscoinfobaseId) {
		this.id = id;
		this.studentid = studentid;
		this.userscoinfobaseId = userscoinfobaseId;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStudentid() {
		return this.studentid;
	}

	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	public String getUserscoinfobaseId() {
		return this.userscoinfobaseId;
	}

	public void setUserscoinfobaseId(String userscoinfobaseId) {
		this.userscoinfobaseId = userscoinfobaseId;
	}

}
