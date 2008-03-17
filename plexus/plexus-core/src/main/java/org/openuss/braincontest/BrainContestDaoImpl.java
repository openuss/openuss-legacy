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
public class BrainContestDaoImpl extends
		org.openuss.braincontest.BrainContestDaoBase {
	/**
	 * @see org.openuss.braincontest.BrainContestDao#toBrainContestInfo(org.openuss.braincontest.BrainContest,
	 *      org.openuss.braincontest.BrainContestInfo)
	 */
	public void toBrainContestInfo(BrainContest sourceEntity, BrainContestInfo targetVO) {
		super.toBrainContestInfo(sourceEntity, targetVO);
		targetVO.setAnswers(sourceEntity.getAnswersCount());
        targetVO.setReleased(sourceEntity.getReleaseDate().before(new Date(System.currentTimeMillis())));
	}

	/**
	 * @see org.openuss.braincontest.BrainContestDao#toBrainContestInfo(org.openuss.braincontest.BrainContest)
	 */
	public BrainContestInfo toBrainContestInfo(final BrainContest entity) {
		return super.toBrainContestInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private BrainContest loadBrainContestFromInfo(BrainContestInfo info) {
		BrainContest brainContest = null;
		if (info.getId() != null) {
			brainContest = this.load(info.getId());
		}
		if (brainContest == null) {
			brainContest = BrainContest.Factory.newInstance();
		}
		return brainContest;
	}

	/**
	 * @see org.openuss.braincontest.BrainContestDao#brainContestInfoToEntity(org.openuss.braincontest.BrainContestInfo)
	 */
	public BrainContest brainContestInfoToEntity(BrainContestInfo info) {
		BrainContest entity = this.loadBrainContestFromInfo(info);
		this.brainContestInfoToEntity(info, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.braincontest.BrainContestDao#brainContestInfoToEntity(org.openuss.braincontest.BrainContestInfo,
	 *      org.openuss.braincontest.BrainContest)
	 */
	public void brainContestInfoToEntity(BrainContestInfo sourceVO,	BrainContest targetEntity,	boolean copyIfNull) {
		if (sourceVO.getTries()==null){
			sourceVO.setTries(targetEntity.getTries());
		}
		super.brainContestInfoToEntity(sourceVO, targetEntity, copyIfNull);
        if (sourceVO.getTries() != null)
        {
            targetEntity.setTries(sourceVO.getTries());
        }
        else if (sourceVO.getTries() == null)
        {
        	targetEntity.setTries(0);
        }

	}

}