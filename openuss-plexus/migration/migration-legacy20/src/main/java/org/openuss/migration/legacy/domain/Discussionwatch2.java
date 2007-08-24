package org.openuss.migration.legacy.domain;

/**
 * @author Ingo Dueppe
 */
public class Discussionwatch2 implements java.io.Serializable {

	private static final long serialVersionUID = -6621106991532101960L;
	// Fields    

	private String id;
	private String submitter;
	private String discussionitem;

	// Constructors

	/** default constructor */
	public Discussionwatch2() {
	}

	/** minimal constructor */
	public Discussionwatch2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Discussionwatch2(String id, String submitter, String discussionitem) {
		this.id = id;
		this.submitter = submitter;
		this.discussionitem = discussionitem;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubmitter() {
		return this.submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	public String getDiscussionitem() {
		return this.discussionitem;
	}

	public void setDiscussionitem(String discussionitem) {
		this.discussionitem = discussionitem;
	}

}
