package org.openuss.migration.legacy.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ingo Dueppe
 */
public class Quizfile2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = -1314231880666553004L;
	private String id;
	private byte[] basefile;
	private Set<Quiz2> quiz2s = new HashSet<Quiz2>(0);

	// Constructors

	/** default constructor */
	public Quizfile2() {
	}

	/** minimal constructor */
	public Quizfile2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Quizfile2(String id, byte[] basefile, Set<Quiz2> quiz2s) {
		this.id = id;
		this.basefile = basefile;
		this.quiz2s = quiz2s;
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

	public Set<Quiz2> getQuizs() {
		return this.quiz2s;
	}

	public void setQuizs(Set<Quiz2> quiz2s) {
		this.quiz2s = quiz2s;
	}

}
