// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.paperSubmission;
/**
 * @see org.openuss.paperSubmission.PaperSubmission
 */
public class PaperSubmissionDaoImpl
    extends org.openuss.paperSubmission.PaperSubmissionDaoBase
{
    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#toPaperSubmissionInfo(org.openuss.paperSubmission.PaperSubmission, org.openuss.paperSubmission.PaperSubmissionInfo)
     */
    public void toPaperSubmissionInfo(
        org.openuss.paperSubmission.PaperSubmission sourceEntity,
        org.openuss.paperSubmission.PaperSubmissionInfo targetVO)
    {
        // @todo verify behavior of toPaperSubmissionInfo
        super.toPaperSubmissionInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#toPaperSubmissionInfo(org.openuss.paperSubmission.PaperSubmission)
     */
    public org.openuss.paperSubmission.PaperSubmissionInfo toPaperSubmissionInfo(final org.openuss.paperSubmission.PaperSubmission entity)
    {
        // @todo verify behavior of toPaperSubmissionInfo
        return super.toPaperSubmissionInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.paperSubmission.PaperSubmission loadPaperSubmissionFromPaperSubmissionInfo(org.openuss.paperSubmission.PaperSubmissionInfo paperSubmissionInfo)
    {
        // @todo implement loadPaperSubmissionFromPaperSubmissionInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.paperSubmission.loadPaperSubmissionFromPaperSubmissionInfo(org.openuss.paperSubmission.PaperSubmissionInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.paperSubmission.PaperSubmission paperSubmission = this.load(paperSubmissionInfo.getId());
        if (paperSubmission == null)
        {
            paperSubmission = org.openuss.paperSubmission.PaperSubmission.Factory.newInstance();
        }
        return paperSubmission;
        */
    }

    
    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#paperSubmissionInfoToEntity(org.openuss.paperSubmission.PaperSubmissionInfo)
     */
    public org.openuss.paperSubmission.PaperSubmission paperSubmissionInfoToEntity(org.openuss.paperSubmission.PaperSubmissionInfo paperSubmissionInfo)
    {
        // @todo verify behavior of paperSubmissionInfoToEntity
        org.openuss.paperSubmission.PaperSubmission entity = this.loadPaperSubmissionFromPaperSubmissionInfo(paperSubmissionInfo);
        this.paperSubmissionInfoToEntity(paperSubmissionInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.paperSubmission.PaperSubmissionDao#paperSubmissionInfoToEntity(org.openuss.paperSubmission.PaperSubmissionInfo, org.openuss.paperSubmission.PaperSubmission)
     */
    public void paperSubmissionInfoToEntity(
        org.openuss.paperSubmission.PaperSubmissionInfo sourceVO,
        org.openuss.paperSubmission.PaperSubmission targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of paperSubmissionInfoToEntity
        super.paperSubmissionInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}