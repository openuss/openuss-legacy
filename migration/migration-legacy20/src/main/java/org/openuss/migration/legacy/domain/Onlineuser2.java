package org.openuss.migration.legacy.domain;

import java.util.Date;

/**
 * @author Ingo Dueppe
 */
public class Onlineuser2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = -2777052590752760435L;
	private String id;
	private String uuser;
	private Date logintime;
	private Date logouttime;
	private String isuseronline;

	// Constructors

	/** default constructor */
	public Onlineuser2() {
	}

	/** minimal constructor */
	public Onlineuser2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Onlineuser2(String id, String uuser, Date logintime, Date logouttime, String isuseronline) {
		this.id = id;
		this.uuser = uuser;
		this.logintime = logintime;
		this.logouttime = logouttime;
		this.isuseronline = isuseronline;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUuser() {
		return this.uuser;
	}

	public void setUuser(String uuser) {
		this.uuser = uuser;
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

	public String getIsuseronline() {
		return this.isuseronline;
	}

	public void setIsuseronline(String isuseronline) {
		this.isuseronline = isuseronline;
	}

}
