package org.openuss.migration.legacy.domain;

import java.util.Date;

/**
 * @author Ingo Dueppe
 */
public class Lecturefile2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = -5983016684332737647L;
	private String id;
	private String lectureFileId;
	private Lecture2 lecture2;
	private Date ddate;
	private String title;
	private String lecturefilename;
	private String lecturefilesize;

	// Constructors

	/** default constructor */
	public Lecturefile2() {
	}

	/** minimal constructor */
	public Lecturefile2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Lecturefile2(String id, String lectureFileId, Lecture2 lecture2, Date ddate, String title,
			String lecturefilename, String lecturefilesize) {
		this.id = id;
		this.lectureFileId = lectureFileId;
		this.lecture2 = lecture2;
		this.ddate = ddate;
		this.title = title;
		this.lecturefilename = lecturefilename;
		this.lecturefilesize = lecturefilesize;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Lecture2 getLecture() {
		return this.lecture2;
	}

	public void setLecture(Lecture2 lecture2) {
		this.lecture2 = lecture2;
	}

	public Date getDdate() {
		return this.ddate;
	}

	public void setDdate(Date ddate) {
		this.ddate = ddate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLecturefilename() {
		return this.lecturefilename;
	}

	public void setLecturefilename(String lecturefilename) {
		this.lecturefilename = lecturefilename;
	}

	public String getLecturefilesize() {
		return this.lecturefilesize;
	}

	public void setLecturefilesize(String lecturefilesize) {
		this.lecturefilesize = lecturefilesize;
	}

	public String getLectureFileId() {
		return lectureFileId;
	}

	public void setLectureFileId(String lectureFileId) {
		this.lectureFileId = lectureFileId;
	}

}
