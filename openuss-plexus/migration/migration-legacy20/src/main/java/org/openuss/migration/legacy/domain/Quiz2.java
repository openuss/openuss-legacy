package org.openuss.migration.legacy.domain;

import java.util.Date;

/**
 * @author Ingo Dueppe
 */
public class Quiz2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = 2517151879295145466L;
	private String id;
	private String quizfilepk;
	private String enrollmentpk;
	private Date ddate;
	private String quizfilename;
	private String quizfilesize;
	private String title;
	private String answer;

	// Constructors

	/** default constructor */
	public Quiz2() {
	}

	/** minimal constructor */
	public Quiz2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Quiz2(String id, String quizfile2, String enrollmentpk, Date ddate, String quizfilename,
			String quizfilesize, String title, String answer) {
		this.id = id;
		this.quizfilepk = quizfile2;
		this.enrollmentpk = enrollmentpk;
		this.ddate = ddate;
		this.quizfilename = quizfilename;
		this.quizfilesize = quizfilesize;
		this.title = title;
		this.answer = answer;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuizfilepk() {
		return this.quizfilepk;
	}

	public void setQuizfilepk(String quizfilepk) {
		this.quizfilepk = quizfilepk;
	}

	public String getEnrollmentpk() {
		return this.enrollmentpk;
	}

	public void setEnrollmentpk(String enrollmentpk) {
		this.enrollmentpk = enrollmentpk;
	}

	public Date getDdate() {
		return this.ddate;
	}

	public void setDdate(Date ddate) {
		this.ddate = ddate;
	}

	public String getQuizfilename() {
		return this.quizfilename;
	}

	public void setQuizfilename(String quizfilename) {
		this.quizfilename = quizfilename;
	}

	public String getQuizfilesize() {
		return this.quizfilesize;
	}

	public void setQuizfilesize(String quizfilesize) {
		this.quizfilesize = quizfilesize;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
