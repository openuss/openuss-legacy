// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.braincontest;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * @see org.openuss.braincontest.BrainContest
 */
public class BrainContestImpl extends BrainContestBase implements BrainContest {
	
	private static final long serialVersionUID = -7791661522910186680L;

	/**
	 * @see org.openuss.braincontest.BrainContest#isReleased()
	 */
	public boolean isReleased() {
		return getReleaseDate().before(new Date(System.currentTimeMillis()));
	}

	/**
	 * @see org.openuss.braincontest.BrainContest#getAnswersCount()
	 */
	public Integer getAnswersCount() {
		if (getAnswers() == null) {
			return 0;
		} else {
			return getAnswers().size();
		}
	}

	@Override
	public void addAnswer(Answer answer) {
		if (getAnswers() != null && answer != null) {
			getAnswers().add(answer);
			answer.setContest(this);
		}
	}
	
	

	@Override
	public String getSolution() {
		return super.getSolution().trim();
	}

	@Override
	public boolean validateAnswer(String answer) {
		setTries(getTries()+1);
		return StringUtils.equalsIgnoreCase(answer.trim(), getSolution());
	}
}