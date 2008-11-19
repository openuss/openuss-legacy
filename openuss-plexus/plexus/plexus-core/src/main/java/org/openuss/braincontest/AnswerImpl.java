// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.braincontest;

import org.openuss.security.User;

/**
 * @see org.openuss.braincontest.Answer
 */
public class AnswerImpl extends org.openuss.braincontest.AnswerBase implements org.openuss.braincontest.Answer {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 7085597565132883484L;

	/**
	 * @see org.openuss.braincontest.Answer#getDisplayName()
	 */
	public java.lang.String getDisplayName() {
		if (getAnswerPk() == null || getAnswerPk().getSolver() == null) {
			return null;
		}
		User solver = getAnswerPk().getSolver();
		return solver.getDisplayName()+" ("+solver.getUsername()+")";
	}

	/**
	 * @see org.openuss.braincontest.Answer#getImageId()
	 */
	public java.lang.Long getImageId() {
		if (getAnswerPk() == null || getAnswerPk().getSolver() != null) {
			return getAnswerPk().getSolver().getImageId();
		} else {
			return null;
		}
	}

}