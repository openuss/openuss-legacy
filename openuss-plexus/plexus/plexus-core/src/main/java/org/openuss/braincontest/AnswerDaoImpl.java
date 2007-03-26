// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.braincontest;
/**
 * @see org.openuss.braincontest.Answer
 */
public class AnswerDaoImpl
    extends org.openuss.braincontest.AnswerDaoBase
{
    /**
     * @see org.openuss.braincontest.AnswerDao#toAnswerInfo(org.openuss.braincontest.Answer, org.openuss.braincontest.AnswerInfo)
     */
    public void toAnswerInfo(
        org.openuss.braincontest.Answer sourceEntity,
        org.openuss.braincontest.AnswerInfo targetVO)
    {
        // @todo verify behavior of toAnswerInfo
        super.toAnswerInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.braincontest.AnswerDao#toAnswerInfo(org.openuss.braincontest.Answer)
     */
    public org.openuss.braincontest.AnswerInfo toAnswerInfo(final org.openuss.braincontest.Answer entity)
    {
        // @todo verify behavior of toAnswerInfo
        return super.toAnswerInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.braincontest.Answer loadAnswerFromAnswerInfo(org.openuss.braincontest.AnswerInfo answerInfo)
    {
        // @todo implement loadAnswerFromAnswerInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.braincontest.loadAnswerFromAnswerInfo(org.openuss.braincontest.AnswerInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.braincontest.Answer answer = this.load(answerInfo.getId());
        if (answer == null)
        {
            answer = org.openuss.braincontest.Answer.Factory.newInstance();
        }
        return answer;
        */
    }

    
    /**
     * @see org.openuss.braincontest.AnswerDao#answerInfoToEntity(org.openuss.braincontest.AnswerInfo)
     */
    public org.openuss.braincontest.Answer answerInfoToEntity(org.openuss.braincontest.AnswerInfo answerInfo)
    {
        // @todo verify behavior of answerInfoToEntity
        org.openuss.braincontest.Answer entity = this.loadAnswerFromAnswerInfo(answerInfo);
        this.answerInfoToEntity(answerInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.braincontest.AnswerDao#answerInfoToEntity(org.openuss.braincontest.AnswerInfo, org.openuss.braincontest.Answer)
     */
    public void answerInfoToEntity(
        org.openuss.braincontest.AnswerInfo sourceVO,
        org.openuss.braincontest.Answer targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of answerInfoToEntity
        super.answerInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}