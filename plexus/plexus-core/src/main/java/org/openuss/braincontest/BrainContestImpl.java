// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.braincontest;

import java.util.Date;

/**
 * @see org.openuss.braincontest.BrainContest
 */
public class BrainContestImpl
    extends org.openuss.braincontest.BrainContestBase
	implements org.openuss.braincontest.BrainContest
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -7791661522910186680L;

    /**
     * @see org.openuss.braincontest.BrainContest#isReleased()
     */
    public boolean isReleased()
    {
        return getReleaseDate().before(new Date(System.currentTimeMillis()));
    }

    /**
     * @see org.openuss.braincontest.BrainContest#getAnswersCount()
     */
    public java.lang.Integer getAnswersCount()
    {
        if (getAnswers()==null) return 0;
        return getAnswers().size();
    }

	@Override
	public void addAnswer(Answer answer) {
		if (getAnswers() != null && answer != null) {
			getAnswers().add(answer);
			answer.setContest(this);
		}
	}

}