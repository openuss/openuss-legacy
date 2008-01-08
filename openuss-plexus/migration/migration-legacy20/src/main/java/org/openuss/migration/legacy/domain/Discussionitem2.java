package org.openuss.migration.legacy.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ingo Dueppe
 */
public class Discussionitem2 implements java.io.Serializable {

	private static final long serialVersionUID = -1975167863211334985L;
	// Fields    

	private String id;
	private Discussionitem2 parent;
	private Formula2 formula2;
	private String discussionfilebase2;
	private String enrollment2;
	private String subject;
	private String ttext;
	private Date ddate;
	private String localestring;
	private String submitterpk;
	private String submittertype;
	private String discussionfilename;
	private String discussionsize;
	private Set<Discussionitem2> discussionitem2s = new HashSet<Discussionitem2>(0);

	// Constructors

	/** default constructor */
	public Discussionitem2() {
	}

	/** minimal constructor */
	public Discussionitem2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Discussionitem2(String id, Discussionitem2 discussionitem2, Formula2 formula2,
			String discussionfilebase2, String enrollment2, String subject, String ttext, Date ddate,
			String localestring, String submitterpk, String submittertype, String discussionfilename,
			String discussionsize, Set<Discussionitem2> discussionitem2s) {
		this.id = id;
		this.parent = discussionitem2;
		this.formula2 = formula2;
		this.discussionfilebase2 = discussionfilebase2;
		this.enrollment2 = enrollment2;
		this.subject = subject;
		this.ttext = ttext;
		this.ddate = ddate;
		this.localestring = localestring;
		this.submitterpk = submitterpk;
		this.submittertype = submittertype;
		this.discussionfilename = discussionfilename;
		this.discussionsize = discussionsize;
		this.discussionitem2s = discussionitem2s;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Discussionitem2 getParent() {
		return this.parent;
	}

	public void setParent(Discussionitem2 parent) {
		this.parent = parent;
	}

	public Formula2 getFormula() {
		return this.formula2;
	}

	public void setFormula(Formula2 formula2) {
		this.formula2 = formula2;
	}

	public String getDiscussionfilebase() {
		return this.discussionfilebase2;
	}

	public void setDiscussionfilebase(String discussionfilebase2) {
		this.discussionfilebase2 = discussionfilebase2;
	}

	public String getEnrollment() {
		return this.enrollment2;
	}

	public void setEnrollment(String enrollment2) {
		this.enrollment2 = enrollment2;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTtext() {
		return this.ttext;
	}

	public void setTtext(String ttext) {
		this.ttext = ttext;
	}

	public Date getDdate() {
		return this.ddate;
	}

	public void setDdate(Date ddate) {
		this.ddate = ddate;
	}

	public String getLocalestring() {
		return this.localestring;
	}

	public void setLocalestring(String localestring) {
		this.localestring = localestring;
	}

	public String getSubmitterpk() {
		return this.submitterpk;
	}

	public void setSubmitterpk(String submitterpk) {
		this.submitterpk = submitterpk;
	}

	public String getSubmittertype() {
		return this.submittertype;
	}

	public void setSubmittertype(String submittertype) {
		this.submittertype = submittertype;
	}

	public String getDiscussionfilename() {
		return this.discussionfilename;
	}

	public void setDiscussionfilename(String discussionfilename) {
		this.discussionfilename = discussionfilename;
	}

	public String getDiscussionsize() {
		return this.discussionsize;
	}

	public void setDiscussionsize(String discussionsize) {
		this.discussionsize = discussionsize;
	}

	public Set<Discussionitem2> getDiscussionitems() {
		return this.discussionitem2s;
	}

	public void setDiscussionitems(Set<Discussionitem2> discussionitem2s) {
		this.discussionitem2s = discussionitem2s;
	}

}
