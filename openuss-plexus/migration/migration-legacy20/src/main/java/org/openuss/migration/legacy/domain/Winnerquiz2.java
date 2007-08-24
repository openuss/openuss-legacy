package org.openuss.migration.legacy.domain;

import java.util.Date;

/**
 * @author Ingo Dueppe
 */
public class Winnerquiz2 implements java.io.Serializable {

	// Fields    

	private static final long serialVersionUID = -4902575532022115763L;
	private String id;
	private Date ddate;
	private String quizid;
	private String winner;

	// Constructors

	/** default constructor */
	public Winnerquiz2() {
	}

	/** minimal constructor */
	public Winnerquiz2(String id) {
		this.id = id;
	}

	/** full constructor */
	public Winnerquiz2(String id, Date ddate, String quizid, String winner) {
		this.id = id;
		this.ddate = ddate;
		this.quizid = quizid;
		this.winner = winner;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDdate() {
		return this.ddate;
	}

	public void setDdate(Date ddate) {
		this.ddate = ddate;
	}

	public String getQuizid() {
		return this.quizid;
	}

	public void setQuizid(String quizid) {
		this.quizid = quizid;
	}

	public String getWinner() {
		return this.winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

}
