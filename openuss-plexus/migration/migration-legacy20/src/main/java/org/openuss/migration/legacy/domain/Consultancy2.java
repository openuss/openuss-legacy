package org.openuss.migration.legacy.domain;

import java.util.Date;

/**
 * @author Ingo Dueppe
 */
public class Consultancy2 implements java.io.Serializable {

	private static final long serialVersionUID = 3347006157711634660L;
	// Fields    

	private String id;
	private Enrollment2 enrollment2;
	private Assistant2 assistant2;
	private Date logintime;
	private Date logouttime;
	private Character isonline;

	// Constructors

	/** default constructor */
	public Consultancy2() {
	}

	/** minimal constructor */
	public Consultancy2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Consultancy2(String id, Enrollment2 enrollment2, Assistant2 assistant2, Date logintime, Date logouttime,
			Character isonline) {
		this.id = id;
		this.enrollment2 = enrollment2;
		this.assistant2 = assistant2;
		this.logintime = logintime;
		this.logouttime = logouttime;
		this.isonline = isonline;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Enrollment2 getEnrollment() {
		return this.enrollment2;
	}

	public void setEnrollment(Enrollment2 enrollment2) {
		this.enrollment2 = enrollment2;
	}

	public Assistant2 getAssistant() {
		return this.assistant2;
	}

	public void setAssistant(Assistant2 assistant2) {
		this.assistant2 = assistant2;
	}

	public Date getLogintime() {
		return this.logintime;
	}

	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}

	public Date getLogouttime() {
		return this.logouttime;
	}

	public void setLogouttime(Date logouttime) {
		this.logouttime = logouttime;
	}

	public Character getIsonline() {
		return this.isonline;
	}

	public void setIsonline(Character isonline) {
		this.isonline = isonline;
	}

}
