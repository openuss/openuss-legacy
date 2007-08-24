package org.openuss.migration.legacy.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ingo Dueppe
 */
public class Lecture2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = 2068072077344196819L;
	private String id;
	private Enrollment2 enrollment2;
	private Date ddate;
	private String title;
	private Set<Lecturefile2> lecturefile2s = new HashSet<Lecturefile2>(0);

	// Constructors

	/** default constructor */
	public Lecture2() {
	}

	/** minimal constructor */
	public Lecture2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Lecture2(String id, Enrollment2 enrollment2, Date ddate, String title, Set<Lecturefile2> lecturefile2s) {
		this.id = id;
		this.enrollment2 = enrollment2;
		this.ddate = ddate;
		this.title = title;
		this.lecturefile2s = lecturefile2s;
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

	public Set<Lecturefile2> getLecturefiles() {
		return this.lecturefile2s;
	}

	public void setLecturefiles(Set<Lecturefile2> lecturefile2s) {
		this.lecturefile2s = lecturefile2s;
	}

}
