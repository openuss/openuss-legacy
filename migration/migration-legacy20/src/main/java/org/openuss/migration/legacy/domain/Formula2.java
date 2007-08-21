package org.openuss.migration.legacy.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ingo Dueppe
 */
public class Formula2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = -9091429322302101697L;
	private String id;
	private String text;
	private Set<Discussionitem2> discussionitem2s = new HashSet<Discussionitem2>(0);

	// Constructors

	/** default constructor */
	public Formula2() {
	}

	/** minimal constructor */
	public Formula2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Formula2(String id, String text, Set<Discussionitem2> discussionitem2s) {
		this.id = id;
		this.text = text;
		this.discussionitem2s = discussionitem2s;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Set<Discussionitem2> getDiscussionitems() {
		return this.discussionitem2s;
	}

	public void setDiscussionitems(Set<Discussionitem2> discussionitem2s) {
		this.discussionitem2s = discussionitem2s;
	}

}
