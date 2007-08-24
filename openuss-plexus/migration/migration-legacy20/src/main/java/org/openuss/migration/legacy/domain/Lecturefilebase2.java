package org.openuss.migration.legacy.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ingo Dueppe
 */
public class Lecturefilebase2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = 8712238654855983360L;
	private String id;
	private byte[] basefile;
	private Set<Lecturefile2> lecturefile2s = new HashSet<Lecturefile2>(0);

	// Constructors

	/** default constructor */
	public Lecturefilebase2() {
	}

	/** minimal constructor */
	public Lecturefilebase2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Lecturefilebase2(String id, byte[] basefile, Set<Lecturefile2> lecturefile2s) {
		this.id = id;
		this.basefile = basefile;
		this.lecturefile2s = lecturefile2s;
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

	public Set<Lecturefile2> getLecturefiles() {
		return this.lecturefile2s;
	}

	public void setLecturefiles(Set<Lecturefile2> lecturefile2s) {
		this.lecturefile2s = lecturefile2s;
	}

}
