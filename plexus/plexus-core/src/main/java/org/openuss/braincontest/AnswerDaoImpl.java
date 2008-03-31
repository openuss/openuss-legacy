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
		targetVO.setSolverId(sourceEntity.getAnswerPk().getSolver().getId());
		targetVO.setContestId(sourceEntity.getAnswerPk().getContest().getId());
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#toAnswerInfo(org.openuss.braincontest.Answer)
	 */
	public AnswerInfo toAnswerInfo(final Answer entity) {
		return super.toAnswerInfo(entity);
	}

	/**
	 * @see org.openuss.braincontest.AnswerDao#answerInfoToEntity(org.openuss.braincontest.AnswerInfo)
	 */
	public Answer answerInfoToEntity(final AnswerInfo answerInfo) {
		Answer entity = findByContestAndSolver(answerInfo.getSolverId(), answerInfo.getContestId()); 
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

    /**
     * @see org.openuss.braincontest.AnswerDao#findByContestAndSolver(int, java.lang.Long, java.lang.Long)
     */
	@Override
    public java.lang.Object findByContestAndSolver(final int transform, final java.lang.Long solverId, final java.lang.Long contestId)
    {
        return this.findByContestAndSolver(transform, "from org.openuss.braincontest.Answer as answer where answer.answerPk.solver.id = ? and answer.answerPk.contest.id = ?", solverId, contestId);
    }
	
}