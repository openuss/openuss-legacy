package org.openuss.migration.legacy.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ingo Dueppe
 */
public class Assistantfilebase2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = -2356478355045688238L;
	private String id;
	private byte[] basefile;
	private Set<Assistantinformation2> assistantinformation2s = new HashSet<Assistantinformation2>(0);

	// Constructors

	/** default constructor */
	public Assistantfilebase2() {
	}

	/** minimal constructor */
	public Assistantfilebase2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Assistantfilebase2(String id, byte[] basefile, Set<Assistantinformation2> assistantinformation2s) {
		this.id = id;
		this.basefile = basefile;
		this.assistantinformation2s = assistantinformation2s;
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

	public Set<Assistantinformation2> getAssistantinformations() {
		return this.assistantinformation2s;
	}

	public void setAssistantinformations(Set<Assistantinformation2> assistantinformation2s) {
		this.assistantinformation2s = assistantinformation2s;
	}

}
