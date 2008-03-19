// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.braincontest;

/**
 * @see org.openuss.braincontest.Answer
 */
public class AnswerDaoImpl extends AnswerDaoBase {
	/**
	 * @see org.openuss.braincontest.AnswerDao#toAnswerInfo(org.openuss.braincontest.Answer,
	 *      org.openuss.braincontest.AnswerInfo)
	 */
	public void toAnswerInfo(final Answer sourceEntity, final AnswerInfo targetVO) {
		super.toAnswerInfo(sourceEntity, targetVO);
		targetVO.setImageId(sourceEntity.getImageId());
		targetVO.setSolverName(sourceEntity.getDisplayName());
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#toAnswerInfo(org.openuss.braincontest.Answer)
	 */
	public AnswerInfo toAnswerInfo(final Answer entity) {
		return super.toAnswerInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private Answer loadAnswerFromAnswerInfo(final AnswerInfo answerInfo) {
		Answer answer = null;
		if (answerInfo != null && answerInfo.getId() != null) {
			answer = this.load(answerInfo.getId());
		}
		if (answer == null) {
			answer = Answer.Factory.newInstance();
		}
		return answer;
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#answerInfoToEntity(org.openuss.braincontest.AnswerInfo)
	 */
	public Answer answerInfoToEntity(final AnswerInfo answerInfo) {
		Answer entity = this.loadAnswerFromAnswerInfo(answerInfo);
		this.answerInfoToEntity(answerInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#answerInfoToEntity(org.openuss.braincontest.AnswerInfo,
	 *      org.openuss.braincontest.Answer)
	 */
	public void answerInfoToEntity(final AnswerInfo sourceVO, final Answer targetEntity, final boolean copyIfNull) {
		super.answerInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}