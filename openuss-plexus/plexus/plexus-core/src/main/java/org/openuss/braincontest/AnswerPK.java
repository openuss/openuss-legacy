package org.openuss.braincontest;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.openuss.security.User;

/**
 * 
 * @author Ingo Dueppe
 * 
 */
public class AnswerPK implements Serializable {

	private static final long serialVersionUID = 1092934489770073831L;

	private User solver;

	public User getSolver() {
		return this.solver;
	}

	public void setSolver(User solver) {
		this.solver = solver;
	}

	private BrainContest contest;

	public BrainContest getContest() {
		return this.contest;
	}

	public void setContest(BrainContest contest) {
		this.contest = contest;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof AnswerPK)) {
			return false;
		}
		final AnswerPK that = (AnswerPK) object;
		return new EqualsBuilder().append(this.getSolver(), that.getSolver()).append(this.getContest(),
				that.getContest()).isEquals();
	}

	public int hashCode() {
		return new org.apache.commons.lang.builder.HashCodeBuilder().append(getSolver()).append(getContest())
				.toHashCode();
	}
}