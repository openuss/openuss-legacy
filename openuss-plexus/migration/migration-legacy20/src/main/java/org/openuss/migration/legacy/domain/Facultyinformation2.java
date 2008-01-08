package org.openuss.migration.legacy.domain;

import java.util.Date;

/**
 * @author Ingo Dueppe
 */
public class Facultyinformation2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = -3373613598818552341L;
	private String id;
	private Faculty2 faculty2;
	private Date ddate;
	private String ttext;
	private String content;
	private String ffilename;
	private String fileid;
	private String ffilesize;

	// Constructors

	/** default constructor */
	public Facultyinformation2() {
	}

	/** minimal constructor */
	public Facultyinformation2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Facultyinformation2(String id, Faculty2 faculty2, Date ddate, String ttext, String content, String ffilename,
			String fileid, String ffilesize) {
		this.id = id;
		this.faculty2 = faculty2;
		this.ddate = ddate;
		this.ttext = ttext;
		this.content = content;
		this.ffilename = ffilename;
		this.fileid = fileid;
		this.ffilesize = ffilesize;
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

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFfilename() {
		return this.ffilename;
	}

	public void setFfilename(String ffilename) {
		this.ffilename = ffilename;
	}

	public String getFileid() {
		return this.fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public String getFfilesize() {
		return this.ffilesize;
	}

	public void setFfilesize(String ffilesize) {
		this.ffilesize = ffilesize;
	}

}
