package org.openuss.migration.legacy.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ingo Dueppe
 */
public class Discussionfilebase2 implements java.io.Serializable {

	private static final long serialVersionUID = -5179008193038461501L;
	// Fields    

	private String id;
	private byte[] basefile;
	private Set<Discussionitem2> discussionitem2s = new HashSet<Discussionitem2>(0);

	// Constructors

	/** default constructor */
	public Discussionfilebase2() {
	}

	/** minimal constructor */
	public Discussionfilebase2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Discussionfilebase2(String id, byte[] basefile, Set<Discussionitem2> discussionitem2s) {
		this.id = id;
		this.basefile = basefile;
		this.discussionitem2s = discussionitem2s;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getBasefile() {
		return this.basefile;
	}

	public void setBasefile(byte[] basefile) {
		this.basefile = basefile;
	}

	public Set<Discussionitem2> getDiscussionitems() {
		return this.discussionitem2s;
	}

	public void setDiscussionitems(Set<Discussionitem2> discussionitem2s) {
		this.discussionitem2s = discussionitem2s;
	}

}
