// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.braincontest;
/**
 * @see org.openuss.braincontest.BrainContest
 */
public class BrainContestDaoImpl
    extends org.openuss.braincontest.BrainContestDaoBase
{
    /**
     * @see org.openuss.braincontest.BrainContestDao#toBrainContestInfo(org.openuss.braincontest.BrainContest, org.openuss.braincontest.BrainContestInfo)
     */
    public void toBrainContestInfo(
        org.openuss.braincontest.BrainContest sourceEntity,
        org.openuss.braincontest.BrainContestInfo targetVO)
    {
        // @todo verify behavior of toBrainContestInfo
        super.toBrainContestInfo(sourceEntity, targetVO);
        // WARNING! No conversion for targetVO.answers (can't convert sourceEntity.getAnswers():org.openuss.braincontest.Answer to java.lang.Integer
    }


    /**
     * @see org.openuss.braincontest.BrainContestDao#toBrainContestInfo(org.openuss.braincontest.BrainContest)
     */
    public org.openuss.braincontest.BrainContestInfo toBrainContestInfo(final org.openuss.braincontest.BrainContest entity)
    {
        // @todo verify behavior of toBrainContestInfo
        return super.toBrainContestInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.braincontest.BrainContest loadBrainContestFromBrainContestInfo(org.openuss.braincontest.BrainContestInfo brainContestInfo)
    {
        // @todo implement loadBrainContestFromBrainContestInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.braincontest.loadBrainContestFromBrainContestInfo(org.openuss.braincontest.BrainContestInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.braincontest.BrainContest brainContest = this.load(brainContestInfo.getId());
        if (brainContest == null)
        {
            brainContest = org.openuss.braincontest.BrainContest.Factory.newInstance();
        }
        return brainContest;
        */
    }

    
    /**
     * @see org.openuss.braincontest.BrainContestDao#brainContestInfoToEntity(org.openuss.braincontest.BrainContestInfo)
     */
    public org.openuss.braincontest.BrainContest brainContestInfoToEntity(org.openuss.braincontest.BrainContestInfo brainContestInfo)
    {
        // @todo verify behavior of brainContestInfoToEntity
        org.openuss.braincontest.BrainContest entity = this.loadBrainContestFromBrainContestInfo(brainContestInfo);
        this.brainContestInfoToEntity(brainContestInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.braincontest.BrainContestDao#brainContestInfoToEntity(org.openuss.braincontest.BrainContestInfo, org.openuss.braincontest.BrainContest)
     */
    public void brainContestInfoToEntity(
        org.openuss.braincontest.BrainContestInfo sourceVO,
        org.openuss.braincontest.BrainContest targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of brainContestInfoToEntity
        super.brainContestInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}