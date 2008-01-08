package org.openuss.migration.legacy.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ingo Dueppe
 */
public class Studentfilebase2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = 7638754904888025409L;
	private String id;
	private byte[] basefile;
	private Set<Studentinformation2> studentinformation2s = new HashSet<Studentinformation2>(0);

	// Constructors

	/** default constructor */
	public Studentfilebase2() {
	}

	/** minimal constructor */
	public Studentfilebase2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Studentfilebase2(String id, byte[] basefile, Set<Studentinformation2> studentinformation2s) {
		this.id = id;
		this.basefile = basefile;
		this.studentinformation2s = studentinformation2s;
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

	public Set<Studentinformation2> getStudentinformations() {
		return this.studentinformation2s;
	}

	public void setStudentinformations(Set<Studentinformation2> studentinformation2s) {
		this.studentinformation2s = studentinformation2s;
	}

}
